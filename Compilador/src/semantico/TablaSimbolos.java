package semantico;

import java.util.ArrayList;
import java.util.HashMap;

import lexico.Token;

public class TablaSimbolos {
	private static TablaSimbolos instanciaUnica;
	private  HashMap<String, Clase> clases;
	private Clase claseActual;
	private Metodo metodoActual;

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
		object.setMetodosConsolidados();
		instanciaUnica.insertarClase(object);
		Token tSystem = new Token("idClase", "System", 0, 0);
		Clase system = new Clase(tSystem,"Object");
		HashMap<String, Parametro> parametros = new HashMap<String, Parametro>();
		Token token = new Token("idMetVar", "read", 0, 0);
		Metodo metodo = new Metodo(token, "static", new TipoInt(), false, parametros);
		instanciaUnica.setClaseActual(system);
		instanciaUnica.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		Parametro param = new Parametro("b", new TipoBoolean());
		param.setPosicion(1);
		parametros.put("b", param);
		token = new Token("idMetVar", "printB", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		param = new Parametro("c", new TipoChar());
		param.setPosicion(1);
		parametros.put("c", param);
		token = new Token("idMetVar", "printC", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		param = new Parametro("i", new TipoInt());
		param.setPosicion(1);
		parametros.put("i", param);
		token = new Token("idMetVar", "printI", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		param = new Parametro("s", new TipoString());
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
		param = new Parametro("b", new TipoBoolean());
		param.setPosicion(1);
		parametros.put("b", param);
		token = new Token("idMetVar", "printBln", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		param = new Parametro("c", new TipoChar());
		param.setPosicion(1);
		parametros.put("c", param);
		token = new Token("idMetVar", "printCln", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		param = new Parametro("i", new TipoInt());
		param.setPosicion(1);
		parametros.put("i", param);
		token = new Token("idMetVar", "printIln", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		param = new Parametro("s", new TipoString());
		param.setPosicion(1);
		parametros.put("s", param);
		token = new Token("idMetVar", "printSln", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.insertarMetodo(metodo);
		system.setMetodosConsolidados();
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
/*	public void mostrarClases() {
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
	}*/

	public Clase getClase(String nombreClase) {
		return clases.get(nombreClase);
	}
	
	public void reset() {
		instanciaUnica = null;
	}
	
	public void chequeoDeclaraciones() throws ExcepcionSemantico {
		for (Clase c : clases.values()) {
			c.chequeoDeclaraciones();
		}
	}

	public void consolidacion() throws ExcepcionSemantico {
		for (Clase c : clases.values()) {
			c.consolidacion();
		}
	}
	
	public Clase getClaseActual() {
		return claseActual;
	}

	public void insertarConstructor(Constructor ctor) {
		claseActual.getConstructores().add(ctor);	
	}

	public void setMetodoActual(Metodo met) {
		metodoActual = met;
	}
	
	public Metodo getMetodoActual() {
		return metodoActual;
	}

	public void insertarMetodo(Metodo met) {
		claseActual.getMetodos().computeIfAbsent(met.getNombre(), k -> new ArrayList<>()).add(met);
	}
} 
