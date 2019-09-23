package main;

import java.util.HashMap;

public class TablaSimbolos {
	private static TablaSimbolos instanciaUnica;
	private  HashMap<String, Clase> clases;
	private Clase claseActual;
//	private Metodo metodoActual;

	private TablaSimbolos() {
		clases = new HashMap<String, Clase>();
	}

	 private synchronized static void createInstance() {
	        if (instanciaUnica == null) { 
	            instanciaUnica = new TablaSimbolos();
	            instanciaUnica.inicializarClases();
	        }
	    }
	
	 
	public static TablaSimbolos getInstance() {
		createInstance();

        return instanciaUnica;
	}
	
	private HashMap<String, Clase> inicializarClases() {
		Token tObject = new Token("idClase", "Object", 0, 0);
		Clase object = new Clase(tObject,null);
		instanciaUnica.insertarClase(object);
		Token tSystem = new Token("idClase", "System", 0, 0);
		Clase system = new Clase(tSystem,"Object");
		HashMap<String, Parametro> parametros = new HashMap<String, Parametro>();
		Metodo metodo = new Metodo("read", "static", new TipoInt(), false, parametros);
		system.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		Parametro param = new Parametro("b", new TipoBoolean());
		param.setPosicion(1);
		parametros.put("b", param);
		metodo = new Metodo("printB", "static", new TipoVoid(), false, parametros);
		system.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		param = new Parametro("c", new TipoChar());
		param.setPosicion(1);
		parametros.put("c", param);
		metodo = new Metodo("printC", "static", new TipoVoid(), false, parametros);
		system.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		param = new Parametro("i", new TipoInt());
		param.setPosicion(1);
		parametros.put("i", param);
		metodo = new Metodo("printI", "static", new TipoVoid(), false, parametros);
		system.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		param = new Parametro("s", new TipoString());
		param.setPosicion(1);
		parametros.put("s", param);
		metodo = new Metodo("printS", "static", new TipoVoid(), false, parametros);
		system.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		metodo = new Metodo("println", "static", new TipoVoid(), false, parametros);
		system.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		param = new Parametro("b", new TipoBoolean());
		param.setPosicion(1);
		parametros.put("b", param);
		metodo = new Metodo("printBln", "static", new TipoVoid(), false, parametros);
		system.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		param = new Parametro("c", new TipoChar());
		param.setPosicion(1);
		parametros.put("c", param);
		metodo = new Metodo("printCln", "static", new TipoVoid(), false, parametros);
		system.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		param = new Parametro("i", new TipoInt());
		param.setPosicion(1);
		parametros.put("i", param);
		metodo = new Metodo("printIln", "static", new TipoVoid(), false, parametros);
		system.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		param = new Parametro("s", new TipoString());
		param.setPosicion(1);
		parametros.put("s", param);
		metodo = new Metodo("printSln", "static", new TipoVoid(), false, parametros);
		system.insertarMetodo(metodo);
		instanciaUnica.insertarClase(system);
		return clases;
	}

	public void setClaseActual(Clase clase) {
		claseActual = clase;		
	}

	public void insertarClase(Clase clase) {
		clases.put(clase.getNombre(), clase);
	}

	public HashMap<String, Clase> getClases() {
		return clases;		
	}
	
	// BORRAR DESPUES
	public void mostrarClases() {
		for (Clase c : clases.values()) {
			System.out.println("Clase: " + c.getNombre());
			for (Metodo m : c.getMetodos().values()) {
				System.out.println("Método: " + m.getNombre());
				System.out.println("Forma Método: " + m.getFormaMetodo());
				System.out.println("¿Es final?: " + m.isMetodoFinal());
				System.out.println("Tipo Retorno: " + m.getTipo().getNombre());
				for (Parametro p : m.getParametros().values()) {
					System.out.println("Parámetro[" + p.getPosicion() + "]: nombre: " + p.getNombre() + " tipo: " + p.getTipo().getNombre());
				}
			}
		}
	}

	public Clase getClase(String nombreClase) {
		return clases.get(nombreClase);
	}
	
	public void reset() {
		instanciaUnica = null;
	}
	
	public void chequeoDeclaraciones() throws ExcepcionSemantico {
		for (Clase c : clases.values()) {
			System.out.println(c.getNombre());
			c.chequeoDeclaraciones();
		}
	}

	public Clase getClaseActual() {
		return claseActual;
	}

	public void insertarConstructor(Constructor ctor) {
		claseActual.getConstructores().add(ctor);	
	}
} 
