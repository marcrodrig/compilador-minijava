package semantico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lexico.Token;

public class Clase {
	private Token token;
	private String superclase;
	private HashMap<String, VariableInstancia> atributos;
	private Map<String,List<Metodo>> metodos;
	private List<Constructor> constructores;
	private boolean visitadoHerenciaCircular;
	private boolean metodosConsolidados;
	private boolean atributosConsolidados;
	private boolean metodoMain;
	
	public Clase(Token token, String superclase) {
		this.token = token;
		this.superclase = superclase;
		atributos = new HashMap<String, VariableInstancia>();
		metodos = new HashMap<>();
		constructores = new ArrayList<Constructor>();
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
	
	public boolean tieneMetodoMain() {
		return metodoMain;
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

	public int getNroLinea() {
		return token.getNroLinea();
	}

	public String getSuperclase() {
		return superclase;
	}
	
	public boolean chequeoDeclaraciones() throws ExcepcionSemantico {
		chequeoExistenciaSuperclase();
		chequeoHerenciaCircular();
		chequeoAtributos();
		chequeoConstructores();
		chequeoMetodos();
		return metodoMain;
	}
	
	private void chequeoMetodos() throws ExcepcionSemantico {
		for (List<Metodo> listaMetodos : metodos.values()) {
			for (Metodo metodo : listaMetodos) {
				for (Parametro paramMetodo : metodo.getParametros().values())
					paramMetodo.chequeoDeclaracionTipoClase();
				if (metodo.isMetodoMain())
					metodoMain = true;
				//if ()
				/*
				 * CHEQUEAR existencia metodo main	
				 */
			if (listaMetodos.size() > 1) {
					metodo.chequeoMetodosSobrecargados(listaMetodos);
				}
			else // tiene un método solo
				listaMetodos.get(0).chequeoExistenciaTipoRetorno();
		}
		}
	}

	private void chequeoAtributos() throws ExcepcionSemantico {
		TablaSimbolos ts = TablaSimbolos.getInstance();
		for (VariableInstancia varIns : atributos.values())
			if (varIns.getTipo() instanceof TipoClase)
				if (ts.getClase(varIns.getTipo().getNombre()) == null) 
					throw new ExcepcionSemantico("[" + varIns.getToken().getNroLinea() + "] Error semántico: El tipo clase \"" + varIns.getTipo().getNombre() + "\" no está definido." );	
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
		} else {
		for (Constructor ctor1 : constructores) {
			for (Parametro paramMetodo : ctor1.getParametros().values())
				paramMetodo.chequeoDeclaracionTipoClase();
			for (Constructor ctor2 : constructores) {
				if (ctor1.getCantidadParametros() == ctor2.getCantidadParametros() && ctor1 != ctor2) {
					int posicion = 1;
					while (posicion <= ctor1.getParametros().size()) {
						Parametro p1 = ctor1.getParametroPorPosicion(posicion);
						Parametro p2 = ctor2.getParametroPorPosicion(posicion);
						if (p1.getTipo().getNombre().equals(p2.getTipo().getNombre())) {
							throw new ExcepcionSemantico("[" + ctor2.getToken().getNroLinea() + "] Error semántico: Constructor con misma signatura ya definido." );
						}
						posicion++;
					}
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

	public void consolidacion() throws ExcepcionSemantico {
			consolidacionAtributos();
			consolidacionMetodos();		
	}
	
	private void consolidacionAtributos() throws ExcepcionSemantico {
		if (superclase != null) { // si no es object
			TablaSimbolos ts = TablaSimbolos.getInstance();
			String ancestro = superclase;
			while (!ts.getClase(ancestro).isAtributosConsolidados()) {
				ts.getClase(ancestro).consolidacionAtributos();
			}
			HashMap<String,VariableInstancia> atributosAncestro = ts.getClase(ancestro).getAtributos();
			for (String nombreAtributoAncestro : atributosAncestro.keySet()) {
				if (getAtributoPorNombre(nombreAtributoAncestro) == null) {
					atributos.put(nombreAtributoAncestro, atributosAncestro.get(nombreAtributoAncestro));
				}
			}
			setAtributosConsolidados();
		}
		}

	public VariableInstancia getAtributoPorNombre(String nombreAtributo) {
		return atributos.get(nombreAtributo);
	}

	private boolean isAtributosConsolidados() {
		return atributosConsolidados;
	}

	public void setMetodosConsolidados() {
		metodosConsolidados = true;
	}
	
	private void consolidacionMetodos() throws ExcepcionSemantico {
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
						if (met1.getCantidadParametros() == met2.getCantidadParametros()) {
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

	public HashMap<String, VariableInstancia> getAtributos() {
		return atributos;
	}

	public void setAtributosConsolidados() {
		atributosConsolidados = true;
	}

	public int cantidadAtributos() {
		return atributos.size();
	}
}
