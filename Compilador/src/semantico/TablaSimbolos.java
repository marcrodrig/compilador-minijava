package semantico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import lexico.Token;

public class TablaSimbolos {

	private static TablaSimbolos instanciaUnica;
	private LinkedHashMap<String, Clase> clases;
	private Clase claseActual;
	private Metodo metodoActual;
	private boolean mainDefinido;
	private boolean recuperacionSemantica;

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

	private LinkedHashMap<String, Clase> inicializarClasesPredefinidas() {
		Token tObject = new Token("idClase", "Object", 0, 0);
		Clase object = new Clase(tObject, null);
		object.setMetodosConsolidados();
		object.setAtributosConsolidados();
		instanciaUnica.insertarClase(object);
		Token tSystem = new Token("idClase", "System", 0, 0);
		Clase system = new Clase(tSystem, "Object");
		HashMap<String, Parametro> parametros = new HashMap<String, Parametro>();
		Token token = new Token("idMetVar", "read", 0, 0);
		Token tokenInt = new Token("int", "int", 0, 0);
		Metodo metodo = new Metodo(token, "static", new TipoInt(tokenInt), false, parametros);
		instanciaUnica.setClaseActual(system);
		instanciaUnica.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		Token tokenBoolean = new Token("boolean", "boolean", 0, 0);
		Parametro param = new Parametro(new Token("idMetVar", "b", 0, 0), new TipoBoolean(tokenBoolean));
		param.setPosicion(1);
		parametros.put("b", param);
		token = new Token("idMetVar", "printB", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		Token tokenChar = new Token("char", "char", 0, 0);
		param = new Parametro(new Token("idMetVar", "c", 0, 0), new TipoChar(tokenChar));
		param.setPosicion(1);
		parametros.put("c", param);
		token = new Token("idMetVar", "printC", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		tokenInt = new Token("int", "int", 0, 0);
		param = new Parametro(new Token("idMetVar", "i", 0, 0), new TipoInt(tokenInt));
		param.setPosicion(1);
		parametros.put("i", param);
		token = new Token("idMetVar", "printI", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		Token tokenString = new Token("String", "String", 0, 0);
		param = new Parametro(new Token("idMetVar", "s", 0, 0), new TipoString(tokenString));
		param.setPosicion(1);
		parametros.put("s", param);
		token = new Token("idMetVar", "printS", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		token = new Token("idMetVar", "println", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		tokenBoolean = new Token("boolean", "boolean", 0, 0);
		param = new Parametro(new Token("idMetVar", "b", 0, 0), new TipoBoolean(tokenBoolean));
		param.setPosicion(1);
		parametros.put("b", param);
		token = new Token("idMetVar", "printBln", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		tokenChar = new Token("char", "char", 0, 0);
		param = new Parametro(new Token("idMetVar", "c", 0, 0), new TipoChar(tokenChar));
		param.setPosicion(1);
		parametros.put("c", param);
		token = new Token("idMetVar", "printCln", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		tokenInt = new Token("int", "int", 0, 0);
		param = new Parametro(new Token("idMetVar", "i", 0, 0), new TipoInt(tokenInt));
		param.setPosicion(1);
		parametros.put("i", param);
		token = new Token("idMetVar", "printIln", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		tokenString = new Token("String", "String", 0, 0);
		param = new Parametro(new Token("idMetVar", "s", 0, 0), new TipoString(tokenString));
		param.setPosicion(1);
		parametros.put("s", param);
		token = new Token("idMetVar", "printSln", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.insertarMetodo(metodo);
		system.setMetodosConsolidados();
		system.setAtributosConsolidados();
		instanciaUnica.insertarClase(system);
		return clases;
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

	public Metodo getMetodoActual() {
		return metodoActual;
	}

	public void setMetodoActual(Metodo met) {
		metodoActual = met;
	}

	public Clase getClase(String nombreClase) {
		return clases.get(nombreClase);
	}

	public void insertarClase(Clase clase) {
		clases.put(clase.getNombre(), clase);
	}

	public void insertarAtributo(VariableInstancia varIns) {
		claseActual.getAtributos().put(varIns.getNombre(), varIns);
	}

	public void insertarConstructor(Constructor ctor) {
		claseActual.getConstructores().add(ctor);
	}

	public void insertarMetodo(Metodo met) {
		claseActual.getTodosMetodos().computeIfAbsent(met.getNombre(), k -> new ArrayList<>()).add(met);
	}

	public boolean recuperacionSemantica() {
		return recuperacionSemantica;
	}

	public void setRS() {
		recuperacionSemantica = true;
	}

	public void chequeoDeclaraciones() throws ExcepcionSemantico {
		for (Clase c : clases.values()) {
			try {
				c.chequeoDeclaraciones();
			} catch (ExcepcionSemantico e) {
				// mainDefinido = c.tieneMetodoMain();
				getInstance().setRS();
				System.out.println(e.toString());
			}
		}
		if (!mainDefinido)
			throw new ExcepcionSemantico("Error semántico: Método main sin definir.");
	}

	public void consolidacion() throws ExcepcionSemantico {
		for (Clase c : clases.values()) {
			if (!c.tieneHerenciaCircular())
				c.consolidacion();
		}
	}

	public void chequeoMain(Clase clase) throws ExcepcionSemantico {
		if (!mainDefinido)
			mainDefinido = true;
		else {
			if (!getClase(clase.getSuperclase()).tieneMetodoMain())
				throw new ExcepcionSemantico("[" + clase.getTodosMetodosPorNombre("main").get(0).getNroLinea()
						+ "] Error semántico: Método main ya definido.");
		}
	}

}
