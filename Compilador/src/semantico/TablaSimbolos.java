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
	
	public void reset() {
		instanciaUnica = null;
	}
	
	private HashMap<String, Clase> inicializarClases() {
		Token tObject = new Token("idClase", "Object", 0, 0);
		Clase object = new Clase(tObject,null);
		object.setMetodosConsolidados();
		object.setAtributosConsolidados();
		instanciaUnica.insertarClase(object);
		Token tSystem = new Token("idClase", "System", 0, 0);
		Clase system = new Clase(tSystem,"Object");
		HashMap<String, Parametro> parametros = new HashMap<String, Parametro>();
		Token token = new Token("idMetVar", "read", 0, 0);
		Metodo metodo = new Metodo(token, "static", new TipoInt(), false, parametros);
		instanciaUnica.setClaseActual(system);
		instanciaUnica.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		Parametro param = new Parametro(new Token("idMetVar", "b", 0, 0), new TipoBoolean());
		param.setPosicion(1);
		parametros.put("b", param);
		token = new Token("idMetVar", "printB", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		param = new Parametro(new Token("idMetVar", "c", 0, 0), new TipoChar());
		param.setPosicion(1);
		parametros.put("c", param);
		token = new Token("idMetVar", "printC", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		param = new Parametro(new Token("idMetVar", "i", 0, 0), new TipoInt());
		param.setPosicion(1);
		parametros.put("i", param);
		token = new Token("idMetVar", "printI", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		param = new Parametro(new Token("idMetVar", "s", 0, 0), new TipoString());
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
		param = new Parametro(new Token("idMetVar", "b", 0, 0), new TipoBoolean());
		param.setPosicion(1);
		parametros.put("b", param);
		token = new Token("idMetVar", "printBln", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		param = new Parametro(new Token("idMetVar", "c", 0, 0), new TipoChar());
		param.setPosicion(1);
		parametros.put("c", param);
		token = new Token("idMetVar", "printCln", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		param = new Parametro(new Token("idMetVar", "i", 0, 0), new TipoInt());
		param.setPosicion(1);
		parametros.put("i", param);
		token = new Token("idMetVar", "printIln", 0, 0);
		metodo = new Metodo(token, "static", new TipoVoid(), false, parametros);
		instanciaUnica.insertarMetodo(metodo);
		parametros = new HashMap<String, Parametro>();
		param = new Parametro(new Token("idMetVar", "s", 0, 0), new TipoString());
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
		claseActual.getMetodos().computeIfAbsent(met.getNombre(), k -> new ArrayList<>()).add(met);
	}
	
	public void chequeoDeclaraciones() throws ExcepcionSemantico {
		boolean main = false;
		for (Clase c : clases.values()) {
			boolean tieneMain = c.chequeoDeclaraciones();
			if (!main)
				main = tieneMain;
		}
		if (!main)
			throw new ExcepcionSemantico("Error semántico: Método main sin definir.");	 
	}

	public void consolidacion() throws ExcepcionSemantico {
		for (Clase c : clases.values()) {
			c.consolidacion();
		}
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
} 
