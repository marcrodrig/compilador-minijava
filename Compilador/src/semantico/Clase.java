package semantico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lexico.Token;
import main.Principal;

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
	private boolean hc;

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

	public int getNroColumna() {
		return token.getNroColumna();
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

	public boolean tieneMetodoMain() {
		return metodoMain;
	}

	public boolean tieneHerenciaCircular() {
		return hc;
	}

	public void chequeoDeclaraciones() throws ExcepcionSemantico {
		chequeoExistenciaSuperclase();
		chequeoHerenciaCircular();
		chequeoAtributos();
		chequeoConstructores();
		chequeoMetodos();
		if (metodoMain) {
			Principal.ts.chequeoMain(this);
		}
	}

	private void chequeoExistenciaSuperclase() throws ExcepcionSemantico {
		if (Principal.ts.getClase(superclase) == null && !getNombre().equals("Object"))
			throw new ExcepcionSemantico("[" + token.getNroLinea() + "] Error semántico: La superclase " + superclase
					+ " de " + getNombre() + " no está definida.");
	}

	private void chequeoHerenciaCircular() throws ExcepcionSemantico {
		if (getNombre().equals(superclase)) {
			hc = true;
			throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
					+ "] Error semántico: La clase " + getNombre() + " tiene herencia circular.");
		}
		String ancestro = superclase;
		boolean continuar = !getNombre().equals("Object") && !ancestro.equals("Object");
		while (continuar) {
			if (Principal.ts.getClases().containsKey(ancestro) && !Principal.ts.getClase(ancestro).getVisitadoHC()) {
				ancestro = Principal.ts.getClase(ancestro).getSuperclase();
				if (getNombre().equals(ancestro)) {
					hc = true;
					throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
							+ "] Error semántico: La clase " + getNombre() + " tiene herencia circular.");
				}
				continuar = !ancestro.equals("Object");
			} else
				continuar = false;
		}
		setHC();
	}

	private void chequeoAtributos() {
		for (VariableInstancia varIns : atributos.values())
			try {
				varIns.chequeoDeclaraciones();
			} catch (ExcepcionSemantico e) {
				Principal.ts.setRS();
				System.out.println(e.toString());
			}
	}

	private void chequeoConstructores() {
		if (constructores.isEmpty()) {
			agregarConstructorPredefinido();
		} else {
			for (Constructor ctor : constructores)
				try {
					ctor.chequeoDeclaraciones(constructores);
				} catch (ExcepcionSemantico e) {
					Principal.ts.setRS();
					System.out.println(e.toString());
				}
		}
	}

	private void agregarConstructorPredefinido() {
		Token token = new Token("idClase", getNombre(), 0, 0);
		Constructor ctor = new Constructor(token, new LinkedHashMap<String, Parametro>());
		constructores.add(ctor);
	}

	private void chequeoMetodos() {
		for (List<Metodo> listaMetodos : metodos.values())
			for (Metodo metodo : listaMetodos) {
				try {
					boolean chequeoMain = metodo.chequeoDeclaraciones(listaMetodos);
					if (!metodoMain)
						metodoMain = chequeoMain;
				} catch (ExcepcionSemantico e) {
					Principal.ts.setRS();
					System.out.println(e.toString());
				}
			}
	}

	public void consolidacion() throws ExcepcionSemantico {
		consolidacionAtributos();
		consolidacionMetodos();
	}

	private void consolidacionAtributos() {
		if (superclase != null && Principal.ts.getClase(superclase) != null) { // si no es object y está declarada
			String ancestro = superclase;
			while (!Principal.ts.getClase(ancestro).isAtributosConsolidados()) {
				Principal.ts.getClase(ancestro).consolidacionAtributos();
			}
			HashMap<String, VariableInstancia> atributosAncestro = Principal.ts.getClase(ancestro).getAtributos();
			for (String nombreAtributoAncestro : atributosAncestro.keySet()) {
				if (getAtributoPorNombre(nombreAtributoAncestro) == null) {
					atributos.put(nombreAtributoAncestro, atributosAncestro.get(nombreAtributoAncestro));
				}
			}
		}
		setAtributosConsolidados();
	}

	private void consolidacionMetodos() {
		if (!isMetodosConsolidados()) {
			String ancestro = superclase;
			if (Principal.ts.getClase(ancestro) != null) {
				while (!Principal.ts.getClase(ancestro).isMetodosConsolidados())
					Principal.ts.getClase(ancestro).consolidacionMetodos();
				Map<String, List<Metodo>> metodosAncestro = Principal.ts.getClase(ancestro).getTodosMetodos();
				for (String nombreMetodoAncestro : metodosAncestro.keySet()) {
					if (getTodosMetodosPorNombre(nombreMetodoAncestro) == null) {
						metodos.put(nombreMetodoAncestro, metodosAncestro.get(nombreMetodoAncestro));
						// chequear si setear método main
					} else {
						List<Metodo> metodosActual = metodos.get(nombreMetodoAncestro);
						List<Metodo> metodosAncestroMismoNombre = metodosAncestro.get(nombreMetodoAncestro);
						Metodo metodoAgregar = null;
						for (Metodo met1 : metodosActual)
							for (Metodo met2 : metodosAncestroMismoNombre)
								if (met1.getCantidadParametros() == met2.getCantidadParametros()) {
									try {
										met1.chequeoRedefinicionMetodo(met2);
									} catch (ExcepcionSemantico e) {
										Principal.ts.setRS();
										System.out.println(e.toString());
									}
								} else {
									metodoAgregar = met2;
								}
						if (metodoAgregar != null) {
							metodosActual.add(metodoAgregar);
							if (metodoAgregar.isMetodoMain())
								metodoMain = true;
						}
					}
				}
			}
			setMetodosConsolidados();
		}
	}

}
