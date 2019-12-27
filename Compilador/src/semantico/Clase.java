package semantico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import gc.GeneradorCodigo;
import lexico.Token;
import main.CompiladorMiniJava;

public class Clase {
	private Token token;
	private String superclase;
	private LinkedHashMap<String, VariableInstancia> atributos;
	private LinkedHashMap<String, List<Unidad>> unidades;
	private boolean visitadoHerenciaCircular, hc, consolidada, sentenciasChequeadas;
	private Metodo metodoMain;
	private List<NodoSentencia> inlineAtrs;

	public Clase(Token token, String superclase) {
		this.token = token;
		this.superclase = superclase;
		atributos = new LinkedHashMap<String, VariableInstancia>();
		unidades = new LinkedHashMap<String, List<Unidad>>();
		inlineAtrs = new LinkedList<NodoSentencia>();
	}

	public boolean sentenciasChequeadas() {
		return sentenciasChequeadas;
	}
	
	public void setSC() {
		sentenciasChequeadas = true;
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
	
	public void insertarAtributo(VariableInstancia varIns) {
		atributos.put(varIns.getNombre(), varIns);
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

	private List<Unidad> getTodasUnidades() {
		List<Unidad> todasUnidades = new LinkedList<Unidad>();
		for (String nombreUnidad : unidades.keySet()) {
			List<Unidad> listaUnidad = unidades.get(nombreUnidad);
			todasUnidades.addAll(listaUnidad);
		}
		return todasUnidades;
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

	public LinkedHashMap<String, List<Unidad>> getTodosMetodos() {
		LinkedHashMap<String, List<Unidad>> metodos = new LinkedHashMap<>(unidades);
		metodos.remove(getNombre());
		return metodos;
	}
	
	public List<Metodo> listaMetodosDynamic() {
		LinkedHashMap<String, List<Unidad>> metodos = new LinkedHashMap<>(unidades);
		metodos.remove(getNombre());
		List<Metodo> l = new ArrayList<Metodo>(getCantidadMetodosDynamic());
		for (String nombre : metodos.keySet()) {
			for (Unidad u : getTodosMetodosPorNombre(nombre)) {
				Metodo met = (Metodo) u;
				if (met.getFormaMetodo().equals("dynamic"))
					l.add(met);
			}
		}
		Collections.sort(l, new MetodoOffsetComparator());
		//System.out.println("TAM " + l.size());
		return l;
	}

	public List<Unidad> getTodosMetodosPorNombre(String nombreMetodo) {
		List<Unidad> lista = getTodosMetodos().get(nombreMetodo);
		if (lista == null)
			return new LinkedList<Unidad>();
		else
			return lista;
	}

	public int cantidadMetodos() {
		int cantidad = 0;
		for (List<Unidad> listaMetodos : getTodosMetodos().values())
			cantidad += listaMetodos.size();
		return cantidad;
	}
	
	private int getCantidadMetodosDynamic() {
		int cantidad = 0;
		for (List<Unidad> listaMetodos : getTodosMetodos().values())
			for (Unidad u : listaMetodos) {
				Metodo metodo = (Metodo) u;
				if (metodo.getFormaMetodo().equals("dynamic"))
					cantidad++;
			}
		return cantidad;
	}
	
	private boolean tieneMetodo(Metodo metodo) {
		boolean tiene = false;
		for (Unidad u : getTodosMetodosPorNombre(metodo.getNombre())) {
			Metodo met = (Metodo) u;
			if (!tiene)
				tiene = metodo == met;
		}
		return tiene;
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
	
	public void setConsolidada() {
		consolidada = true;
	}

	public boolean tieneHerenciaCircular() {
		return hc;
	}
	
	public boolean tieneMetodoMain() {
		return metodoMain != null;
	}

	public List<NodoSentencia> getInlineAtrs() {
		return inlineAtrs;
	}

	public int getCantidadInlineAtrs() {
		return inlineAtrs.size();
	//	Clase clasePadre = CompiladorMiniJava.tablaSimbolos.getClase(superclase);
	//	boolean continuar = true;
	//	if (clasePadre != null) {
	//		int cantAtrInl = clasePadre.getInlineAtrs().size();
		//	if (cantAtrInl > 0)
	//			cant += cantAtrInl;
		//	else
				/**
				 * 
				 */
				//continuar = false;
			//clasePadre = CompiladorMiniJava.tablaSimbolos.getClase(clasePadre.getSuperclase());
	//	}
	//	return cant;
	}

	public void insertarAsignacionInlineAtributo(NodoSentencia sentencia) {
		inlineAtrs.add(sentencia);
	}

	private int getCantidadAtrs() {
		int cant = atributos.size();
		if (superclase != null)
			cant += CompiladorMiniJava.tablaSimbolos.getClase(superclase).getCantidadAtrs();
		return cant;
	}
	
	private void agregarConstructorPredefinido() {
		Token token = new Token("idClase", getNombre(), 0, 0);
		Unidad ctor = new Constructor(token, new LinkedHashMap<String, Parametro>());
		CompiladorMiniJava.tablaSimbolos.setUnidadActual(ctor);
		CompiladorMiniJava.tablaSimbolos.setBloque(null);
		CompiladorMiniJava.tablaSimbolos.insertarUnidad(ctor);
	}

	public boolean esDescendiente(String nombreClase) {
		boolean descendiente = false;
		Clase hereda = CompiladorMiniJava.tablaSimbolos.getClase(getSuperclase());
		while (!false && !hereda.getNombre().equals("Object"))
			descendiente = hereda.getNombre().equals(nombreClase);
		hereda = CompiladorMiniJava.tablaSimbolos.getClase(hereda.getSuperclase());
		return descendiente;
	}
	
	private void setOffsetAtrs() {
		Clase clasePadre = CompiladorMiniJava.tablaSimbolos.getClase(superclase);
		if (clasePadre != null) { // No es Object
			int cantAtrsPadre = clasePadre.getCantidadAtrs();
			for (VariableInstancia v : atributos.values())
				v.setOffset(++cantAtrsPadre);
		}
	}
	
	private void setOffsetMetodos() {
		Clase clasePadre = CompiladorMiniJava.tablaSimbolos.getClase(superclase);
		if (clasePadre != null) { // No es Object
			int cantMetodosPadre = clasePadre.getCantidadMetodosDynamic();
			for (String nombreMetodo : getTodosMetodos().keySet()) {
				for (Unidad u : getTodosMetodosPorNombre(nombreMetodo)) {
					Metodo metodo = (Metodo) u;
					boolean redefine = false;
					for (Unidad u2 : clasePadre.getTodosMetodosPorNombre(nombreMetodo))
						if (!redefine) {
							Metodo metodo2 = (Metodo) u2;
							try {
								redefine = metodo.chequeoRedefinicionMetodo(metodo2);
							} catch (ExcepcionSemantico e) {
								e.printStackTrace();
							}
							if (redefine)
								metodo.setOffset(metodo2.getOffset());
						}
					if (!redefine && !metodo.getNombre().equals(getNombre())
							&& !metodo.getFormaMetodo().equals("static")) {
						if (!clasePadre.tieneMetodo(metodo)) {
							metodo.setOffset(cantMetodosPadre++);
						}
					}
				}
			}
		}
	}
	
	public void chequeoDeclaraciones() throws ExcepcionSemantico {
		chequeoExistenciaSuperclase();
		chequeoHerenciaCircular();
		chequeoAtrsInline();
		chequeoAtributos();
		chequeoConstructores();
		chequeoMetodos();
	}

	private void chequeoExistenciaSuperclase() throws ExcepcionSemantico {
		if (CompiladorMiniJava.tablaSimbolos.getClase(superclase) == null && !getNombre().equals("Object"))
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
			if (CompiladorMiniJava.tablaSimbolos.getClases().containsKey(ancestro) && !CompiladorMiniJava.tablaSimbolos.getClase(ancestro).getVisitadoHC()) {
				ancestro = CompiladorMiniJava.tablaSimbolos.getClase(ancestro).getSuperclase();
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
				CompiladorMiniJava.tablaSimbolos.setRSem();
				System.out.println(e.toString());
			}
		setOffsetAtrs();
	}

	private void chequeoConstructores() {
		List<Unidad> constructores = getConstructores();
		if (constructores.isEmpty()) {
			agregarConstructorPredefinido();
		} else {
			for (Unidad ctor : constructores)
				try {
					ctor.chequeoDeclaraciones(constructores);
				} catch (ExcepcionSemantico e) {
					CompiladorMiniJava.tablaSimbolos.setRSem();
					System.out.println(e.toString());
				}
		}
	}

	private void chequeoMetodos() {
		for (List<Unidad> listaMetodos : getTodosMetodos().values())
			for (Unidad unidad : listaMetodos) {
				Metodo metodo = (Metodo) unidad;
				try {
					if (metodo.isMetodoMain()) {
						metodoMain = metodo;
						CompiladorMiniJava.tablaSimbolos.chequeoMain(this, metodoMain);
					} else
						metodo.chequeoDeclaraciones(listaMetodos);
				} catch (ExcepcionSemantico e) {
					CompiladorMiniJava.tablaSimbolos.setRSem();
					System.out.println(e.toString());
				}
			}
		setOffsetMetodos();
	}

	public void consolidacion() throws ExcepcionSemantico {
		if (CompiladorMiniJava.tablaSimbolos.getClase(superclase) != null) {
			while (!CompiladorMiniJava.tablaSimbolos.getClase(superclase).estaConsolidada())
				CompiladorMiniJava.tablaSimbolos.getClase(superclase).consolidacion();
			consolidacionAtributos();
			consolidacionMetodos();
			setConsolidada();
		}
	}

	private void consolidacionAtributos() {
		HashMap<String, VariableInstancia> atributosAncestro = CompiladorMiniJava.tablaSimbolos.getClase(superclase).getAtributos();
		for (String nombreAtributoAncestro : atributosAncestro.keySet())
			if (getAtributoPorNombre(nombreAtributoAncestro) == null)
				atributos.put(nombreAtributoAncestro, atributosAncestro.get(nombreAtributoAncestro));
	}

	private void chequeoAtrsInline() {
		Clase clasePadre = CompiladorMiniJava.tablaSimbolos.getClase(superclase);
		boolean continuar = true;
		while (clasePadre != null && continuar) {

			List <NodoSentencia> lns = clasePadre.getInlineAtrs();
			if (!lns.isEmpty()) {
				for (NodoSentencia inlineAtr : lns) {
					insertarAsignacionInlineAtributo(inlineAtr);
				}
				continuar = false;
			}
			clasePadre = CompiladorMiniJava.tablaSimbolos.getClase(clasePadre.getSuperclase());
		}
	}
	
	private void consolidacionMetodos() {
		Map<String, List<Unidad>> metodosAncestro = CompiladorMiniJava.tablaSimbolos.getClase(superclase).getTodosMetodos();
		for (String nombreMetodoAncestro : metodosAncestro.keySet()) {
			if (getTodosMetodosPorNombre(nombreMetodoAncestro).isEmpty()) {
				unidades.put(nombreMetodoAncestro,CompiladorMiniJava.tablaSimbolos.getClase(superclase).getTodosMetodosPorNombre(nombreMetodoAncestro));
			} else {
				List<Unidad> metodosActual = unidades.get(nombreMetodoAncestro);
				List<Unidad> metodosAncestroMismoNombre = metodosAncestro.get(nombreMetodoAncestro);
				List<Unidad> listaMetodoAgregar = new LinkedList<Unidad>();
				for (Unidad met1 : metodosActual)
					for (Unidad met2 : metodosAncestroMismoNombre) {
						Metodo metodo1 = (Metodo) met1;
						Metodo metodo2 = (Metodo) met2;
						if (met1.getCantidadParametros() == met2.getCantidadParametros()) {
							try {
								metodo1.chequeoRedefinicionMetodo(metodo2);
							} catch (ExcepcionSemantico e) {
								CompiladorMiniJava.tablaSimbolos.setRSem();
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
						CompiladorMiniJava.tablaSimbolos.insertarUnidad(metodo);
				}
			}
		}
	}

	public void chequeoSentencias() {
		if (!sentenciasChequeadas) {
			for (NodoSentencia inlineAtr : inlineAtrs) {
				try {
					inlineAtr.chequear();
				} catch (ExcepcionSemantico e) {
					CompiladorMiniJava.tablaSimbolos.setRSem();
					System.out.println(e.toString());
				}
			}
			for (Unidad u : getTodasUnidades()) {
				if (u.declaradaEn().getNombre().equals(CompiladorMiniJava.tablaSimbolos.getClaseActual().getNombre())) {
					CompiladorMiniJava.tablaSimbolos.setUnidadActual(u);
					try {
						u.chequeoSentencias();
					} catch (ExcepcionSemantico e) {
						CompiladorMiniJava.tablaSimbolos.setRSem();
						System.out.println(e.toString());
					}
				}
			}
			setSC();
		}
	}

	public void generar() {
		GeneradorCodigo generadorCodigo = GeneradorCodigo.getInstance();
		generadorCodigo.write(".DATA");
		generadorCodigo.write("VT_" + getNombre() + ":");
		if (getCantidadMetodosDynamic() == 0)
			generadorCodigo.write("DW 0");
		else
		//	for (List<Unidad> listaMetodos : getTodosMetodos().values())
				for (Metodo metodo : listaMetodosDynamic()) {
					//Metodo metodo = (Metodo) u;
					//if (metodo.getFormaMetodo().equals("dynamic"))
						generadorCodigo.write("DW " + metodo.getLabel());
				}
		generadorCodigo.write(".CODE");
		for (Unidad u : getTodasUnidades()) {
			if (u.declaradaEn().getNombre().equals(CompiladorMiniJava.tablaSimbolos.getClaseActual().getNombre())) {
				CompiladorMiniJava.tablaSimbolos.setUnidadActual(u);
				u.generar();
			}
		}
	}

}