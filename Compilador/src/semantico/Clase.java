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
	private Map<String, List<Unidad>> unidades;
	private boolean visitadoHerenciaCircular;
	private boolean consolidada;
	private Metodo metodoMain;
	private boolean hc;

	public Clase(Token token, String superclase) {
		this.token = token;
		this.superclase = superclase;
		atributos = new HashMap<String, VariableInstancia>();
		unidades = new HashMap<>();
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

	public Map<String, List<Unidad>> getUnidades() {
		return unidades;
	}
	
	public List<Unidad> getConstructores() {
		List<Unidad> constructores = unidades.get(getNombre());
		if (constructores == null)
			constructores = new ArrayList<Unidad>();
		return constructores;
	}
	
	// Posición desde 1
	public Unidad getConstructor(int index) {
		return getConstructores().get(index - 1);
	}

	public Map<String, List<Unidad>> getTodosMetodos() {
		Map<String, List<Unidad>> metodos = new HashMap<>(unidades);
		metodos.remove(getNombre());
		return metodos;
	}

	public List<Unidad> getTodosMetodosPorNombre(String nombreMetodo) {
		return getTodosMetodos().get(nombreMetodo);
	}

	public int cantidadMetodos() {
		int cantidad = 0;
		for (List<Unidad> listaMetodos : getTodosMetodos().values())
			cantidad += listaMetodos.size();
		return cantidad;
	}

	public boolean getVisitadoHC() {
		return visitadoHerenciaCircular;
	}

	public void setHC() {
		visitadoHerenciaCircular = true;
	}

	public boolean estaConsolidada() {
		return consolidada;
	}
	
	public boolean tieneMetodoMain() {
		return metodoMain != null;
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
		List <Unidad> constructores = getConstructores();
		if (constructores.isEmpty()) {
			agregarConstructorPredefinido();
		} else {
			for (Unidad ctor : constructores)
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
		Unidad ctor = new Constructor(token, new LinkedHashMap<String, Parametro>());
		Principal.ts.setUnidadActual(ctor);
		NodoBloque bloque = new NodoBloque();
		Principal.ts.setBloque(bloque);
		Principal.ts.insertarUnidad(ctor);
	}

	private void chequeoMetodos() {
		for (List<Unidad> listaMetodos : getTodosMetodos().values())
			for (Unidad unidad : listaMetodos) {
				Metodo metodo = (Metodo) unidad;
				try {
					if (metodo.isMetodoMain()) {
						metodoMain = metodo;
						Principal.ts.chequeoMain(this, metodoMain);
					} else
						metodo.chequeoDeclaraciones(listaMetodos);
				} catch (ExcepcionSemantico e) {
					Principal.ts.setRS();
					System.out.println(e.toString());
				}
			}
	}

	public void consolidacion() throws ExcepcionSemantico {
		if (Principal.ts.getClase(superclase) != null) {
			while (!Principal.ts.getClase(superclase).estaConsolidada())
				Principal.ts.getClase(superclase).consolidacion();
			consolidacionAtributos();
			consolidacionMetodos();
			setConsolidada();
		}
	}

	public void setConsolidada() {
		consolidada = true;
	}

	private void consolidacionAtributos() {
		HashMap<String, VariableInstancia> atributosAncestro = Principal.ts.getClase(superclase).getAtributos();
		for (String nombreAtributoAncestro : atributosAncestro.keySet())
			if (getAtributoPorNombre(nombreAtributoAncestro) == null)
				atributos.put(nombreAtributoAncestro, atributosAncestro.get(nombreAtributoAncestro));
	}

	private void consolidacionMetodos() {
		Map<String, List<Unidad>> metodosAncestro = Principal.ts.getClase(superclase).getTodosMetodos();
		for (String nombreMetodoAncestro : metodosAncestro.keySet()) {
			if (getTodosMetodosPorNombre(nombreMetodoAncestro) == null) {
				unidades.put(nombreMetodoAncestro, Principal.ts.getClase(superclase).getTodosMetodosPorNombre(nombreMetodoAncestro));
				// chequear si setear método main
			} else {
				List<Unidad> metodosActual = unidades.get(nombreMetodoAncestro);
				List<Unidad> metodosAncestroMismoNombre = metodosAncestro.get(nombreMetodoAncestro);
				List<Unidad> listaMetodoAgregar = new ArrayList<Unidad>();
				for (Unidad met1 : metodosActual)
					for (Unidad met2 : metodosAncestroMismoNombre) {
						Metodo metodo1 = (Metodo) met1;
						Metodo metodo2 = (Metodo) met2;
						if (met1.getCantidadParametros() == met2.getCantidadParametros()) {
							try {
								metodo1.chequeoRedefinicionMetodo(metodo2);
							} catch (ExcepcionSemantico e) {
								Principal.ts.setRS();
								System.out.println(e.toString());
							}
						} else {
							listaMetodoAgregar.add(metodo2);
					if (metodo2.isMetodoMain())
						metodoMain = metodo2;
				}
					}
				if (!listaMetodoAgregar.isEmpty()) {
					for (Unidad metodo : listaMetodoAgregar)
						Principal.ts.insertarUnidad(metodo);
				}
			}
		}
	}

	public void chequeoSentencias() throws ExcepcionSemantico {
		for(Unidad u : getTodasUnidades()) {
			Principal.ts.setUnidadActual(u);
			u.chequeoSentencias();
		}
		
	}

	private List<Unidad> getTodasUnidades() {
		List<Unidad> todasUnidades = new ArrayList<Unidad>();
		for(String nombreUnidad : unidades.keySet()) {
			List<Unidad> listaUnidad = unidades.get(nombreUnidad);
			todasUnidades.addAll(listaUnidad);
		}
		return todasUnidades;
	}

}
