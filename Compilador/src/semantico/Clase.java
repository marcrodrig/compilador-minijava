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
	private List<Constructor> constructores;
	private Map<String, List<Metodo>> metodos;
	private boolean visitadoHerenciaCircular;
	private boolean atributosConsolidados;
	private boolean metodosConsolidados;
	private boolean metodoMain;

	public Clase(Token token, String superclase) {
		this.token = token;
		this.superclase = superclase;
		atributos = new HashMap<String, VariableInstancia>();
		constructores = new ArrayList<Constructor>();
		metodos = new HashMap<>();
	}

	public String getNombre() {
		return token.getLexema();
	}

	public int getNroLinea() {
		return token.getNroLinea();
	}

	public String getSuperclase() {
		return superclase;
	}

	public HashMap<String, VariableInstancia> getAtributos() {
		return atributos;
	}

	public VariableInstancia getAtributoPorNombre(String nombreAtributo) {
		return atributos.get(nombreAtributo);
	}

	public int cantidadAtributos() {
		return atributos.size();
	}

	public List<Constructor> getConstructores() {
		return constructores;
	}

	// Posición desde 1
	public Constructor getConstructor(int index) {
		return constructores.get(index - 1);
	}

	public Map<String, List<Metodo>> getTodosMetodos() {
		return metodos;
	}

	public List<Metodo> getTodosMetodosPorNombre(String nombreMetodo) {
		return metodos.get(nombreMetodo);
	}

	public int cantidadMetodos() {
		int cantidad = 0;
		for (List<Metodo> listaMetodos : metodos.values())
			cantidad += listaMetodos.size();
		return cantidad;
	}

	public boolean getVisitadoHC() {
		return visitadoHerenciaCircular;
	}

	public void setHC() {
		visitadoHerenciaCircular = true;
	}

	private boolean isAtributosConsolidados() {
		return atributosConsolidados;
	}

	public void setAtributosConsolidados() {
		atributosConsolidados = true;
	}

	public boolean isMetodosConsolidados() {
		return metodosConsolidados;
	}

	public void setMetodosConsolidados() {
		metodosConsolidados = true;
	}

	public boolean chequeoDeclaraciones() throws ExcepcionSemantico {
		chequeoExistenciaSuperclase();
		chequeoHerenciaCircular();
		chequeoAtributos();
		chequeoConstructores();
		chequeoMetodos();
		return metodoMain;
	}

	private void chequeoExistenciaSuperclase() throws ExcepcionSemantico {
		TablaSimbolos ts = TablaSimbolos.getInstance();
		if (ts.getClase(superclase) == null && !getNombre().equals("Object"))
			throw new ExcepcionSemantico("[" + token.getNroLinea() + "] Error semántico: La superclase " + superclase
					+ " de " + getNombre() + " no está definida.");
	}

	private void chequeoHerenciaCircular() throws ExcepcionSemantico {
		String ancestro = superclase;
		boolean continuar = !getNombre().equals("Object") && !ancestro.equals("Object");
		while (continuar) {
			TablaSimbolos ts = TablaSimbolos.getInstance();
			if (!ts.getClase(ancestro).getVisitadoHC()) {
				ancestro = ts.getClase(ancestro).getSuperclase();
				if (getNombre().equals(ancestro)) {
					throw new ExcepcionSemantico("[" + token.getNroLinea() + "] Error semántico: La clase "
							+ getNombre() + " tiene herencia circular.");
				}
				continuar = !ancestro.equals("Object");
			} else
				continuar = false;
		}
		setHC();
	}

	private void chequeoAtributos() throws ExcepcionSemantico {
		for (VariableInstancia varIns : atributos.values())
			varIns.chequeoDeclaraciones();
	}

	private void chequeoConstructores() throws ExcepcionSemantico {
		if (constructores.isEmpty()) {
			agregarConstructorPredefinido();
		} else {
			for (Constructor ctor : constructores)
				ctor.chequeoDeclaraciones(constructores);
		}
	}

	private void agregarConstructorPredefinido() {
		Token token = new Token("idClase", getNombre(), 0, 0);
		Constructor ctor = new Constructor(token, new HashMap<String, Parametro>());
		constructores.add(ctor);
	}

	private void chequeoMetodos() throws ExcepcionSemantico {
		for (List<Metodo> listaMetodos : metodos.values())
			for (Metodo metodo : listaMetodos) {
				boolean chequeoMain = metodo.chequeoDeclaraciones(listaMetodos);
				if (!metodoMain)
					metodoMain = chequeoMain;
			}
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
			HashMap<String, VariableInstancia> atributosAncestro = ts.getClase(ancestro).getAtributos();
			for (String nombreAtributoAncestro : atributosAncestro.keySet()) {
				if (getAtributoPorNombre(nombreAtributoAncestro) == null) {
					atributos.put(nombreAtributoAncestro, atributosAncestro.get(nombreAtributoAncestro));
				}
			}
			setAtributosConsolidados();
		}
	}

	private void consolidacionMetodos() throws ExcepcionSemantico {
		if (!isMetodosConsolidados()) {
			TablaSimbolos ts = TablaSimbolos.getInstance();
			String ancestro = superclase;
			while (!ts.getClase(ancestro).isMetodosConsolidados())
				ts.getClase(ancestro).consolidacionMetodos();
			Map<String, List<Metodo>> metodosAncestro = ts.getClase(ancestro).getTodosMetodos();
			for (String nombreMetodoAncestro : metodosAncestro.keySet()) {
				if (getTodosMetodosPorNombre(nombreMetodoAncestro) == null) {
					metodos.put(nombreMetodoAncestro, metodosAncestro.get(nombreMetodoAncestro));
				} else {
					List<Metodo> metodosActual = metodos.get(nombreMetodoAncestro);
					List<Metodo> metodosAncestroMismoNombre = metodosAncestro.get(nombreMetodoAncestro);
					for (Metodo met1 : metodosActual)
						for (Metodo met2 : metodosAncestroMismoNombre)
							if (met1.getCantidadParametros() == met2.getCantidadParametros()) {
								if (!met1.sobreescribeMetodo(met2))
									metodosActual.add(met2);
							} else
								metodosActual.add(met2);
				}
			}
			setMetodosConsolidados();
		}
	}

}
