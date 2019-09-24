package semantico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lexico.Token;

public class Clase {
	private Token token;
	private String superclase;
//	private HashMap<String, VariableInstancia> atributos;
	//private HashMap<String, ArrayList<Metodo>> metodos;
	private Map<String,List<Metodo>> metodos;
	private List<Constructor> constructores;
	private boolean visitadoHerenciaCircular;
	private boolean metodosConsolidados;
	
	public Clase(Token token, String superclase) {
		this.token = token;
		this.superclase = superclase;
		metodos = new HashMap<>();
		constructores = new ArrayList<Constructor>();
		visitadoHerenciaCircular = false;
		metodosConsolidados = false;
	}

	public String getNombre() {
		return token.getLexema();
	}
	
	public void setHC() {
		visitadoHerenciaCircular = true;
	}
	
	public boolean getVisitadoHC() {
		return visitadoHerenciaCircular;
	}
	/*
	public void insertarMetodo(Metodo metodo) {
		metodos.put(metodo.getNombre(), metodo);
	}*/

	public Map<String, List<Metodo>> getTodosMetodos() {
		return metodos;
	}
	
	public int cantidadMetodos() {
		int cantidad = 0;
		for (List<Metodo> listaMetodos: metodos.values())
			cantidad += listaMetodos.size();
		return cantidad;
	}
	
	public List<Constructor> getConstructores() {
		return constructores;
	}
	
	
	public List<Metodo> getMetodosPorNombre(String nombreMetodo) {
		return metodos.get(nombreMetodo);
	}

	public Token getToken() {
		return token;
	}

	public String getSuperclase() {
		return superclase;
	}
	
	public void chequeoDeclaraciones() throws ExcepcionSemantico {
		chequeoExistenciaSuperclase();
		chequeoHerenciaCircular();
		chequeoConstructores();
		for (List<Metodo> listaMetodos : metodos.values()) {
			if (listaMetodos.size() > 1)
				for (Metodo metodo : listaMetodos) {
					metodo.chequeoMetodosSobrecargados(listaMetodos);
				}
			else // tiene un método solo
				listaMetodos.get(0).chequeoExistenciaTipoRetorno();
		}
	}
	
	private void chequeoExistenciaSuperclase() throws ExcepcionSemantico {
		TablaSimbolos ts = TablaSimbolos.getInstance();
		if (ts.getClase(superclase) == null && !getNombre().equals("Object")) 
			throw new ExcepcionSemantico("[" + token.getNroLinea() + "] Error semántico: La superclase " + superclase + " de " + getNombre() + " no está definida." );
	}

	private void chequeoHerenciaCircular() throws ExcepcionSemantico {
		String ancestro = superclase;
		boolean continuar = !getNombre().equals("Object") && !ancestro.equals("Object");
		while(continuar) {
			TablaSimbolos ts = TablaSimbolos.getInstance();
			if (!ts.getClase(ancestro).getVisitadoHC()) {
			ancestro = ts.getClase(ancestro).getSuperclase();
			if (getNombre().equals(ancestro)) {
				throw new ExcepcionSemantico("[" + token.getNroLinea() + "] Error semántico: La clase " + getNombre() + " tiene herencia circular." );
			}
			continuar = !ancestro.equals("Object");
		} else
			continuar = false;
		}
		setHC();
	}
	
	public boolean isMetodosConsolidados() {
		return metodosConsolidados;
	}
	
	private void chequeoConstructores() throws ExcepcionSemantico {
		if (constructores.isEmpty()) {
			agregarConstructorPredefinido();
		}
		for (Constructor ctor1 : constructores) {
			for (Constructor ctor2 : constructores) {
				HashMap<String, Parametro> parametrosCtor1 = ctor1.getParametros();
				HashMap<String, Parametro> parametrosCtor2 = ctor2.getParametros();
				if (parametrosCtor1.size() == parametrosCtor2.size() && ctor1 != ctor2) {
					int posicion = 1;
					while (posicion <= ctor1.getParametros().size()) {
						Parametro p1 = ctor1.getParametro(posicion);
						Parametro p2 = ctor2.getParametro(posicion);
						if (p1.getTipo().getNombre().equals(p2.getTipo().getNombre())) {
							throw new ExcepcionSemantico("[" + ctor2.getToken().getNroLinea() + "] Error semántico: Constructor con misma signatura ya definido." );
						}
						posicion++;
					}
				}
			}
		}
	}

	private void agregarConstructorPredefinido() {
		Token token = new Token("idClase", getNombre(), 0, 0);
		Constructor ctor = new Constructor(token, new HashMap<String, Parametro>());
		constructores.add(ctor);
	}

	// Posición desde 1
	public Constructor getConstructor(int posicion) {
		return constructores.get(posicion-1);
	}

	public HashMap<String, List<Metodo>> getMetodos() {
		return (HashMap<String, List<Metodo>>) metodos;
	}

	public void consolidacion() {
	//	for (List<Metodo> listaMetodos : metodos.values()) {
	//		for (Metodo metodo : listaMetodos) 
				consolidacionMetodos();		
	}
	//}

	public void setMetodosConsolidados() {
		metodosConsolidados = true;
	}
	
	public void consolidacionMetodos() {
		if (superclase != null) { // si no es object
		TablaSimbolos ts = TablaSimbolos.getInstance();
		String ancestro = superclase;
		while (!ts.getClase(ancestro).isMetodosConsolidados()) {
			ts.getClase(ancestro).consolidacionMetodos();
		}
		Map<String,List<Metodo>> metodosAncestro = ts.getClase(ancestro).getTodosMetodos();
		for (String nombreMetodoAncestro : metodosAncestro.keySet()) {
			if (getMetodosPorNombre(nombreMetodoAncestro) == null) {
				metodos.put(nombreMetodoAncestro, metodosAncestro.get(nombreMetodoAncestro));
			} else {
				List<Metodo> metodosActual = metodos.get(nombreMetodoAncestro);
				List<Metodo> metodosAncestroMismoNombre = metodosAncestro.get(nombreMetodoAncestro);
				for (Metodo met1 : metodosActual) {
					for (Metodo met2 : metodosAncestroMismoNombre) {
						if (met1.cantidadParametros() == met2.cantidadParametros()) {
							if (!met1.sobreescribeMetodo(met2)) {
								metodosActual.add(met2);
							}
						} else 
							metodosActual.add(met2);
					}
				}
				
			}
		}
		setMetodosConsolidados();
	}
	}
}
