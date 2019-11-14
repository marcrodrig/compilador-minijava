package semantico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import gc.GeneradorCodigo;
import lexico.Token;

public class TablaSimbolos {

	private static TablaSimbolos instanciaUnica;
	private LinkedHashMap<String, Clase> clases;
	private Clase claseActual;
	private Unidad unidadActual;
	private Metodo main;
	private boolean recuperacionSintactica, recuperacionSemantica;
	private NodoBloque bloqueActual;

	private TablaSimbolos() {
		clases = new LinkedHashMap<String, Clase>();
	}

	private synchronized static void createInstance() {
		if (instanciaUnica == null) {
			instanciaUnica = new TablaSimbolos();
			instanciaUnica.inicializarClasesPredefinidas();
		}
	}

	public static TablaSimbolos getInstance() {
		createInstance();
		return instanciaUnica;
	}

	public void reset() {
		instanciaUnica = null;
	}

	public HashMap<String, Clase> getClases() {
		return clases;
	}

	public Clase getClaseActual() {
		return claseActual;
	}

	public void setClaseActual(Clase clase) {
		claseActual = clase;
	}

	public Unidad getUnidadActual() {
		return unidadActual;
	}

	public void setUnidadActual(Unidad unidad) {
		unidadActual = unidad;
	}

	public Clase getClase(String nombreClase) {
		return clases.get(nombreClase);
	}

	public void insertarUnidad(Unidad unidad) {
		claseActual.getUnidades().computeIfAbsent(unidad.getNombre(), k -> new ArrayList<>()).add(unidad);
	}

	public void insertarClase(Clase clase) {
		clases.put(clase.getNombre(), clase);
	}

	public void insertarAtributo(VariableInstancia varIns) {
		claseActual.insertarAtributo(varIns);
	}

	public boolean recuperacionSintactica() {
		return recuperacionSintactica;
	}

	public boolean recuperacionSemantica() {
		return recuperacionSemantica;
	}

	public void setRSin() {
		recuperacionSintactica = true;
	}

	public void setRSem() {
		recuperacionSemantica = true;
	}

	public NodoBloque getBloqueActual() {
		return bloqueActual;
	}

	public void setBloqueActual(NodoBloque bloque) {
		this.bloqueActual = bloque;
	}

	public void setBloque(NodoBloque bloque) {
		unidadActual.setBloque(bloque);
	}

	private LinkedHashMap<String, Clase> inicializarClasesPredefinidas() {
		Token tObject = new Token("idClase", "Object", 0, 0);
		Clase object = new Clase(tObject, null);
		object.setConsolidada();
		instanciaUnica.insertarClase(object);
		instanciaUnica.setClaseActual(object);
		Token tSystem = new Token("idClase", "System", 0, 0);
		Clase system = new Clase(tSystem, "Object");
		instanciaUnica.setClaseActual(system);
		LinkedHashMap<String, Parametro> parametros = new LinkedHashMap<String, Parametro>();
		Token token = new Token("idMetVar", "read", 0, 0);
		Token tokenInt = new Token("int", "int", 0, 0);
		Metodo metodo = new Metodo(token, "static", new TipoInt(tokenInt), false, parametros);
		instanciaUnica.setUnidadActual(metodo);
		NodoBloque bloque = new NodoBloqueRead();
		instanciaUnica.setBloque(bloque);
		instanciaUnica.setClaseActual(system);
		instanciaUnica.insertarUnidad(metodo);
		parametros = new LinkedHashMap<String, Parametro>();
		Token tokenBoolean = new Token("boolean", "boolean", 0, 0);
		Parametro param = new Parametro(new Token("idMetVar", "b", 0, 0), new TipoBoolean(tokenBoolean));
		param.setPosicion(1);
		parametros.put("b", param);
		token = new Token("idMetVar", "printB", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.setUnidadActual(metodo);
		bloque = new NodoBloquePrintB();
		instanciaUnica.setBloque(bloque);
		instanciaUnica.insertarUnidad(metodo);
		parametros = new LinkedHashMap<String, Parametro>();
		Token tokenChar = new Token("char", "char", 0, 0);
		param = new Parametro(new Token("idMetVar", "c", 0, 0), new TipoChar(tokenChar));
		param.setPosicion(1);
		parametros.put("c", param);
		token = new Token("idMetVar", "printC", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.setUnidadActual(metodo);
		bloque = new NodoBloquePrintC();
		instanciaUnica.setBloque(bloque);
		instanciaUnica.insertarUnidad(metodo);
		parametros = new LinkedHashMap<String, Parametro>();
		tokenInt = new Token("int", "int", 0, 0);
		param = new Parametro(new Token("idMetVar", "i", 0, 0), new TipoInt(tokenInt));
		param.setPosicion(1);
		parametros.put("i", param);
		token = new Token("idMetVar", "printI", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.setUnidadActual(metodo);
		bloque = new NodoBloquePrintI();
		instanciaUnica.setBloque(bloque);
		instanciaUnica.insertarUnidad(metodo);
		parametros = new LinkedHashMap<String, Parametro>();
		Token tokenString = new Token("String", "String", 0, 0);
		param = new Parametro(new Token("idMetVar", "s", 0, 0), new TipoString(tokenString));
		param.setPosicion(1);
		parametros.put("s", param);
		token = new Token("idMetVar", "printS", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.setUnidadActual(metodo);
		bloque = new NodoBloquePrintS();
		instanciaUnica.setBloque(bloque);
		instanciaUnica.insertarUnidad(metodo);
		parametros = new LinkedHashMap<String, Parametro>();
		token = new Token("idMetVar", "println", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.setUnidadActual(metodo);
		bloque = new NodoBloquePrintln();
		instanciaUnica.setBloque(bloque);
		instanciaUnica.insertarUnidad(metodo);
		parametros = new LinkedHashMap<String, Parametro>();
		tokenBoolean = new Token("boolean", "boolean", 0, 0);
		param = new Parametro(new Token("idMetVar", "b", 0, 0), new TipoBoolean(tokenBoolean));
		param.setPosicion(1);
		parametros.put("b", param);
		token = new Token("idMetVar", "printBln", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.setUnidadActual(metodo);
		bloque = new NodoBloquePrintBln();
		instanciaUnica.setBloque(bloque);
		instanciaUnica.insertarUnidad(metodo);
		parametros = new LinkedHashMap<String, Parametro>();
		tokenChar = new Token("char", "char", 0, 0);
		param = new Parametro(new Token("idMetVar", "c", 0, 0), new TipoChar(tokenChar));
		param.setPosicion(1);
		parametros.put("c", param);
		token = new Token("idMetVar", "printCln", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.setUnidadActual(metodo);
		bloque = new NodoBloquePrintCln();
		instanciaUnica.setBloque(bloque);
		instanciaUnica.insertarUnidad(metodo);
		parametros = new LinkedHashMap<String, Parametro>();
		tokenInt = new Token("int", "int", 0, 0);
		param = new Parametro(new Token("idMetVar", "i", 0, 0), new TipoInt(tokenInt));
		param.setPosicion(1);
		parametros.put("i", param);
		token = new Token("idMetVar", "printIln", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.setUnidadActual(metodo);
		bloque = new NodoBloquePrintIln();
		instanciaUnica.setBloque(bloque);
		instanciaUnica.insertarUnidad(metodo);
		parametros = new LinkedHashMap<String, Parametro>();
		tokenString = new Token("String", "String", 0, 0);
		param = new Parametro(new Token("idMetVar", "s", 0, 0), new TipoString(tokenString));
		param.setPosicion(1);
		parametros.put("s", param);
		token = new Token("idMetVar", "printSln", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.setUnidadActual(metodo);
		bloque = new NodoBloquePrintSln();
		instanciaUnica.setBloque(bloque);
		instanciaUnica.insertarUnidad(metodo);
		system.setConsolidada();
		instanciaUnica.insertarClase(system);
		return clases;
	}

	public void chequeoMain(Clase clase, Metodo metodoMain) throws ExcepcionSemantico {
		if (main == null)
			main = metodoMain;
		else {
			if (!getClase(clase.getSuperclase()).tieneMetodoMain())
				throw new ExcepcionSemantico("[" + clase.getTodosMetodosPorNombre("main").get(0).getNroLinea()
						+ "] Error semántico: Método main ya definido.");
		}
	}

	public void controlesSemanticos() throws ExcepcionSemantico {
		instanciaUnica.chequeoDeclaraciones();
		instanciaUnica.consolidacion();
		instanciaUnica.chequeoSentencias();
	}

	private void chequeoDeclaraciones() throws ExcepcionSemantico {
		for (Clase c : clases.values()) {
			try {
				setClaseActual(c);
				c.chequeoDeclaraciones();
			} catch (ExcepcionSemantico e) {
				getInstance().setRSem();
				System.out.println(e.toString());
			}
		}
		if (main == null)
			throw new ExcepcionSemantico("Error semántico: Método main sin definir.");
	}

	private void consolidacion() throws ExcepcionSemantico {
		for (Clase c : clases.values()) {
			setClaseActual(c);
			if (!c.tieneHerenciaCircular() && !c.estaConsolidada())
				c.consolidacion();
		}
	}

	private void chequeoSentencias() {
		for (Clase c : clases.values()) {
			setClaseActual(c);
			c.chequeoSentencias();
		}
	}

	public void generar() {
		GeneradorCodigo generadorCodigo = GeneradorCodigo.getInstance();
		generadorCodigo.write(".CODE");
		generadorCodigo.write("PUSH simple_heap_init");
		generadorCodigo.write("CALL");
		generadorCodigo.write("PUSH " + main.getLabel());
		generadorCodigo.write("CALL");
		generadorCodigo.write("HALT");
		generadorCodigo.newLine();

		generadorCodigo.write("simple_heap_init:");
		generadorCodigo.write("\tRET 0\t; Retorna inmediatamente");
		generadorCodigo.newLine();

		generadorCodigo.write("simple_malloc:");
		generadorCodigo.write("\tLOADFP\t; Inicialización unidad");
		generadorCodigo.write("\tLOADSP");
		generadorCodigo.write("\tSTOREFP\t; Finaliza inicialización del RA");
		generadorCodigo.write("\tLOADHL\t; hl");
		generadorCodigo.write("\tDUP\t; hl");
		generadorCodigo.write("\tPUSH 1\t; 1");
		generadorCodigo.write("\tADD\t; hl + 1");
		generadorCodigo.write("\tSTORE 4\t; Guarda el resultado (un puntero a la primer celda de la región de memoria)");
		generadorCodigo.write("\tLOAD 3\t; Carga cantidad de celdas a alojar (parámetro que debe ser positivo)");
		generadorCodigo.write("\tADD");
		generadorCodigo.write("\tSTOREHL\t; Mueve el heap limit (hl). Expande el heap");
		generadorCodigo.write("\tSTOREFP");
		generadorCodigo.write("\tRET 1\t; Retorna eliminando el parámetro");
		generadorCodigo.newLine();
		for (Clase c : clases.values()) {
			setClaseActual(c);
			generadorCodigo.write("# Definición de la clase " + c.getNombre());
			c.generar();
		}
		generadorCodigo.close();
	}
}
