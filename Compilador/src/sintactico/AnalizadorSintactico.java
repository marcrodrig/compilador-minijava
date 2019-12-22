package sintactico;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import lexico.AnalizadorLexico;
import lexico.ExcepcionLexico;
import lexico.Token;
import semantico.*;
import main.CompiladorMiniJava;

public class AnalizadorSintactico {

	private AnalizadorLexico analizadorLexico;
	private Token tokenActual;
	private boolean ifPanico;
	private int ultimaLineaAnalizada, ultimaColumnaAnalizada;
	
	public AnalizadorSintactico(String archivoEntrada) throws FileNotFoundException, ExcepcionLexico {
		analizadorLexico = new AnalizadorLexico(archivoEntrada);
		tokenActual = analizadorLexico.getToken();
	}

	private void match(String nombre) throws ExcepcionLexico, ExcepcionSintactico {
		if (nombre.equals(tokenActual.getNombre())) {
			ultimaLineaAnalizada = tokenActual.getNroLinea();
			ultimaColumnaAnalizada = tokenActual.getNroColumna() + tokenActual.getLexema().length();
			tokenActual = analizadorLexico.getToken();			
		}
		else
			if (nombre.equals(";"))
				throw new ExcepcionSintactico("Error sintáctico: ; faltante.\nEsperado: " + nombre + "\nEncontrado: " + tokenActual.getNombre());
			else
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico.\nEsperado: " + nombre + "\nEncontrado: " + tokenActual.getNombre());
	}

	public void start() throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode {
		inicial();
		match("EOF");
	}
	
	private void inicial() throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode {
		try {
		clase();
		} catch (ExcepcionSemantico e) {
			System.out.println(e.toString());
			CompiladorMiniJava.tablaSimbolos.setRSem();
		}
		otroInicial();
	}

	private void otroInicial() throws  ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode {
		if (tokenActual.getNombre().equals("class")) {			// primeros
			try {
				clase();
			} catch (ExcepcionSemantico e) {
				System.out.println(e.toString());
				CompiladorMiniJava.tablaSimbolos.setRSem();
			}
			otroInicial();
		} else if (tokenActual.getNombre().equals("EOF")) {		// siguientes
		} else
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico: Declaración de una clase inválida.\nEsperado: class\nEncontrado: " + tokenActual.getNombre());
	}

	private void clase() throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode, ExcepcionSemantico {
		match("class");		// primeros
		Token token = tokenActual;
		match("idClase");
		String superclase = herencia();
		Clase clase = new Clase(token, superclase);
		CompiladorMiniJava.tablaSimbolos.setClaseActual(clase);
		match("{");
		miembros();
		match("}");
		if (CompiladorMiniJava.tablaSimbolos.getClase(clase.getNombre()) == null)
			CompiladorMiniJava.tablaSimbolos.insertarClase(clase);
		else
			throw new ExcepcionSemantico("[" + clase.getNroLinea() + ":" + clase.getNroColumna() + "] Error semántico: La clase " + clase.getNombre() + " ya está definida." );
	}

	private String herencia() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("extends")) {	// primeros
			match("extends");
			try {
				String superclase = tokenActual.getLexema();
				match("idClase");
				return superclase;
			} catch (ExcepcionSintactico e) {
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico: Declaración de herencia de una clase inválida.\nEsperado: idClase\nEncontrado: " + tokenActual.getNombre());
			}
		} else 
			if (tokenActual.getNombre().equals("{")) {		// siguientes
				return "Object";
			} else
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico: Declaración de una clase inválida.\nEsperado: extends o {\nEncontrado: " + tokenActual.getNombre());
	}
	
	private void miembros() throws ExcepcionSintactico, ExcepcionLexico, ExcepcionPanicMode {
		switch (tokenActual.getNombre()) {
			case "public":		// primeros
			case "protected":	// primeros
			case "private":		// primeros
			case "idClase":		// primeros
			case "static":		// primeros
			case "dynamic":		// primeros
				miembro();
				miembros();
				break;
			case "}":			// siguientes
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico: Se espera la declaración de un atributo, constructor, método o }.\nEsperado: public, protected, private, idClase, static, dynamic o }\nEncontrado: " + tokenActual.getNombre());		}
	}
	
	private void miembro() throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode {
		switch (tokenActual.getNombre()) {
			case "public":		// primeros
			case "protected":	// primeros
			case "private":		// primeros
				try {
				atributo();
				} catch (ExcepcionSemantico e) {
					System.out.println(e.toString());
					CompiladorMiniJava.tablaSimbolos.setRSem();
				}
				break;
			case "idClase":		// primeros
				try {
				ctor();
				} catch (ExcepcionSemantico e) {
					System.out.println(e.toString());
					CompiladorMiniJava.tablaSimbolos.setRSem();
				}
				break;
			case "static":		// primeros
			case "dynamic":		// primeros
				try {
				metodo();
				} catch (ExcepcionSemantico e) {
					System.out.println(e.toString());
					CompiladorMiniJava.tablaSimbolos.setRSem();
				}
				break;
		}
	}

	private void atributo() throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode, ExcepcionSemantico {
		int panicoLinea = tokenActual.getNroLinea();
		int panicoColumna = tokenActual.getNroColumna();
		String modificadorVisibilidad = visibilidad();
		try {
			Tipo tipo = tipoInline();
			List<Token> listaTokenVariablesInstancia = new ArrayList<Token>();
			listaDecAtrs(listaTokenVariablesInstancia);
			List<Variable> varsInstancia = new ArrayList<Variable>();
			for (Token tokenVarIns : listaTokenVariablesInstancia) {
				VariableInstancia varIns = new VariableInstancia(tokenVarIns,tipo,modificadorVisibilidad);
				varsInstancia.add(varIns);
			}
			NodoSentencia sentencia = decAsig(varsInstancia,null);
			match(";");
			if (!(sentencia instanceof NodoDecVarsLocales)) {
				NodoAsignacion asig = (NodoAsignacion) sentencia;
				for (Variable var : asig.getVars()) {
					VariableInstancia varIns = (VariableInstancia) var;
					if (CompiladorMiniJava.tablaSimbolos.getClaseActual().getAtributoPorNombre(varIns.getNombre()) == null)
						CompiladorMiniJava.tablaSimbolos.insertarAtributo(varIns);
					else
						throw new ExcepcionSemantico("[" + varIns.getNroLinea() + ":" + varIns.getNroColumna() + "] Error semántico: Nombre de atributo \"" + varIns.getNombre() + "\" repetido.");
				}
				CompiladorMiniJava.tablaSimbolos.getClaseActual().insertarAsignacionInlineAtributo(sentencia);
			} else {
				for (Variable var : varsInstancia) {
					VariableInstancia varIns = (VariableInstancia) var;
					if (CompiladorMiniJava.tablaSimbolos.getClaseActual().getAtributos().get(varIns.getNombre()) == null)
						CompiladorMiniJava.tablaSimbolos.insertarAtributo(varIns);
					else
						throw new ExcepcionSemantico("[" + varIns.getNroLinea() + ":" + varIns.getNroColumna() + "] Error semántico: Nombre de atributo \"" + varIns.getNombre() + "\" repetido.");
				}
			}
		} catch (ExcepcionSintactico e) {
			modoPanicoAtributo(panicoLinea, panicoColumna, e.toString());
		}
	}
	
	private Tipo tipoInline() throws ExcepcionLexico, ExcepcionSintactico {
		Tipo tipo = null;
		switch (tokenActual.getNombre()) {
			case "boolean":			// primeros
			case "char":			// primeros
			case "int":				// primeros
			case "String":			// primeros
				tipo = tipoPrimitivo();
				break;
			case "idClase":			// primeros
				tipo = new TipoClase(tokenActual);
				match("idClase");
				break;
			case "idMetVar":		// siguientes
				break;
			default:
				throw new ExcepcionSintactico("Error sintáctico: tipo inválido u nombre de variable inválida.\nEsperado: idClase, boolean, char, int, String o idMetVar\nEncontrado: " + tokenActual.getNombre());
		}
		return tipo;
	}
	
	private void modoPanicoAtributo(int nroLineaComienzo, int nroColumnaComienzo, String mensajeError) throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode {
		int nroLineaFin = ultimaLineaAnalizada;
		int nroColumnaFin = ultimaColumnaAnalizada;
		CompiladorMiniJava.tablaSimbolos.setRSin();
		ArrayList<String> siguientesValidos = new ArrayList<String>();
		siguientesValidos.add("public");
		siguientesValidos.add("protected");
		siguientesValidos.add("private");
		siguientesValidos.add("idClase");
		siguientesValidos.add("static");
		siguientesValidos.add("dynamic");
		siguientesValidos.add("}");
		if (!siguientesValidos.contains(tokenActual.getNombre())) {
			while(!siguientesValidos.contains(tokenActual.getNombre()) && !tokenActual.getNombre().equals("EOF")) {
				if (nroLineaFin != tokenActual.getNroLinea())
					nroLineaFin = tokenActual.getNroLinea();
				nroColumnaFin = tokenActual.getNroColumna() + tokenActual.getLexema().length();
				tokenActual = analizadorLexico.getToken();
			}
		}
		if (tokenActual.getNombre().equals("EOF")) {
			System.out.println("[" + nroLineaComienzo + "]: declaración/asignación de atributos sintácticamente inválida.\nCausa:\n" + mensajeError);
			throw new ExcepcionPanicMode("No se puede recuperar de la última sentencia inválida.");		
		}
		else {
			System.out.println("[" + nroLineaComienzo + ":" + nroColumnaComienzo + " - " + nroLineaFin + ":" + nroColumnaFin + "]: declaración/asignación de atributos sintácticamente inválida.\nCausa:\n" + mensajeError);
			switch (tokenActual.getNombre()) {
					case "}":
						break;
					case "public":
					case "protected":
					case "private":
					case "idClase":
					case "static":
					case "dynamic":
						miembros();
						break;
				}
			}
	}
	
	private String visibilidad() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "public":			// primeros
				match("public");
				return "public";
			case "protected":		// primeros
				match("protected");
				return "protected";
			case "private":			// primeros
				match("private");
				return "private";
			default:
				return null;
		}
	}

	private Tipo tipo() throws ExcepcionLexico, ExcepcionSintactico {
		Tipo tipo = null;
		switch (tokenActual.getNombre()) {
			case "boolean":			// primeros
			case "char":			// primeros
			case "int":				// primeros
			case "String":			// primeros
				tipo = tipoPrimitivo();
				break;
			case "idClase":			// primeros
				tipo = new TipoClase(tokenActual);
				match("idClase");
				break;
			default:
				throw new ExcepcionSintactico("Error sintáctico: tipo inválido.\nEsperado: idClase, boolean, char, int o String\nEncontrado: " + tokenActual.getNombre());
		}
		return tipo;
	}

	private Tipo tipoPrimitivo() throws ExcepcionLexico, ExcepcionSintactico {
		Tipo tipo = null;
		switch (tokenActual.getNombre()) {
			case "boolean":			// primeros
				tipo = new TipoBoolean(tokenActual);
				match("boolean");
				break;
			case "char":			// primeros
				tipo = new TipoChar(tokenActual);
				match("char");
				break;
			case "int":				// primeros
				tipo = new TipoInt(tokenActual);
				match("int");
				break;
			case "String":			// primeros
				tipo = new TipoString(tokenActual);
				match("String");
				break;
		}
		return tipo;
	}

	private void listaDecAtrs(List<Token> listaTokenVariablesInstancia) throws ExcepcionLexico, ExcepcionSintactico {
		Token tokenVarIns = tokenActual;
		match("idMetVar");	// primeros
		listaTokenVariablesInstancia.add(tokenVarIns);
		rlistaDecAtrs(listaTokenVariablesInstancia);
	}

	private void rlistaDecAtrs(List<Token> listaTokenVariablesInstancia) throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case ",":				// primeros
				match(",");
				listaDecAtrs(listaTokenVariablesInstancia);
				break;
			case ";": 				// siguientes
			case "=": 				// siguientes
				break;
			default:
				throw new ExcepcionSintactico("Error sintáctico: Declaración/asignación de atributos inválida.\nEsperado: ; o =\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private NodoSentencia decAsig(List<Variable> vars, NodoExpresion izq) throws ExcepcionLexico, ExcepcionSintactico {	
		switch (tokenActual.getNombre()) {
			case "=":				// primeros
				int nroLinea = tokenActual.getNroLinea();
				match("=");
				NodoExpresion exp = expresion();
				if (vars != null) // asignacion inline
					return new NodoAsignacion(vars, null, exp, nroLinea);
				else // metodo lado izquierdo 
				{
					if (izq instanceof NodoPrimario) {
						NodoPrimario np = (NodoPrimario) izq;
					if (np instanceof NodoVar) {
						NodoVar nv = (NodoVar) izq;
						nv.setEsLadoIzqAsig();
					}
					/*if (np instanceof NodoThis) {
						NodoThis nt = (NodoThis) np;
						nt.setEsLadoIzqAsig();
					}*/
					Encadenado ultimoEncadenado = np.getEncadenado();
					if (ultimoEncadenado != null) {
						while(ultimoEncadenado.getEncadenado() != null) {
							ultimoEncadenado.setEsLadoIzqAsig();
							ultimoEncadenado = ultimoEncadenado.getEncadenado();
						}
						ultimoEncadenado.setEsLadoIzqAsig();
					}
					}
					return new NodoAsignacion(null, izq, exp, nroLinea);
				}
			case ";":				// siguientes
				if (vars.get(0).getTipo() == null)
					if (vars.size() == 1)
						throw new ExcepcionSintactico("Error sintáctico: Se omitió el tipo para la declaración o se omitió la expresión para una asignación inline para la variable " + vars.get(0).getNombre() + ".");
					else {
						String v = "";
						for(Variable va : vars)
							v += " " + va.getNombre() + ",";
						String cadV = v.substring(0, v.length() - 1);
						throw new ExcepcionSintactico("Error sintáctico: Se omitió el tipo para la declaración o se omitió la expresión para una asignación inline para las variables" + cadV + ".");
					}
				return new NodoDecVarsLocales(vars);
			default:
				return null; // o excepcion, ver cual
		}
	}
	
	private void metodo() throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode, ExcepcionSemantico {
		String formaMetodo = formaMetodo();
		boolean metodoFinal = fnl();
		TipoRetorno tipo = tipoMetodo();
		Token token = tokenActual;
		match("idMetVar");
		List<Parametro> listaArgsFormales = argsFormales();
		LinkedHashMap<String, Parametro> parametros = new LinkedHashMap<String, Parametro>();
		Metodo met = new Metodo(token, formaMetodo, tipo, metodoFinal, parametros);
		CompiladorMiniJava.tablaSimbolos.setUnidadActual(met);
		CompiladorMiniJava.tablaSimbolos.setBloque(null);
		NodoBloque bloque = bloque();
		CompiladorMiniJava.tablaSimbolos.setBloque(bloque);
		int posicion = 1;
		for (Parametro param : listaArgsFormales) {
			param.setPosicion(posicion);
			if (parametros.get(param.getNombre()) == null) {
				parametros.put(param.getNombre(), param);
				CompiladorMiniJava.tablaSimbolos.getUnidadActual().insertarParametro(param);
			}
			else
				throw new ExcepcionSemantico("[" + param.getNroLinea() + ":" + param.getNroColumna() +"] Error semántico: Nombre de parámetro \"" + param.getNombre() + "\" repetido.");
			posicion++;
		}
		CompiladorMiniJava.tablaSimbolos.insertarUnidad(met);
	}

	private String formaMetodo() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "static":			// primeros
				match("static");
				return "static";
			case "dynamic":			// primeros
				match("dynamic");
				return "dynamic";
			default : 
				return null;
		}
	}

	private boolean fnl() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "final":			// primeros
				match("final");
				return true;
			case "void":			// siguientes
			case "idClase":			// siguientes
			case "boolean":			// siguientes
			case "char":			// siguientes
			case "int":				// siguientes
			case "String":			// siguientes
				return false;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico: Declaración de método inválida.\nEsperado: final o tipo de método\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private TipoRetorno tipoMetodo() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "void":		// primeros
				match("void");
				return new TipoVoid();
			case "idClase":		// primeros
			case "boolean":		// primeros
			case "char":		// primeros
			case "int":			// primeros
			case "String":		// primeros
				return tipo();
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico: Declaración de tipo de método inválido.\nEsperado: idClase, boolean, char, int, String o void\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private List<Parametro> argsFormales() throws ExcepcionLexico, ExcepcionSintactico {
		List<Parametro> argsFormales = null;
		if (tokenActual.getNombre().equals("(")) {	// primeros
			match("(");
			argsFormales = argumentos();
			match(")");
		} else {
			throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico: Argumentos formales inválidos.\nEsperado: (\nEncontrado: " + tokenActual.getNombre());
		}
		return argsFormales;
	}

	private List<Parametro> argumentos() throws ExcepcionLexico, ExcepcionSintactico {
		List<Parametro> argsFormales = new ArrayList<Parametro>();
		switch (tokenActual.getNombre()) {
			case "boolean":				// primeros
			case "char":				// primeros
			case "int":					// primeros
			case "String":				// primeros
			case "idClase":				// primeros
				listaArgsFormales(argsFormales);
				break;
			case ")":					// siguientes
				//argsFormales = new ArrayList<Parametro>();
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico: Argumentos formales inválidos.\nEsperado: ) o tipo\nEncontrado: " + tokenActual.getNombre());
			}
		return argsFormales;
	}

	private void listaArgsFormales(List<Parametro> argsFormales) throws ExcepcionLexico, ExcepcionSintactico {
		argFormal(argsFormales);
		rArgFormal(argsFormales);
	}

	private void argFormal(List<Parametro> argsFormales) throws ExcepcionLexico, ExcepcionSintactico {
		Tipo tipoParametro = tipo();
		Token tokenParametro = tokenActual;
		match("idMetVar");
		Parametro param = new Parametro(tokenParametro, tipoParametro);
		argsFormales.add(param);
	}

	private void rArgFormal(List<Parametro> argsFormales) throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case ",":					// primeros
				match(",");
				listaArgsFormales(argsFormales);
				break;
			case ")":					// siguientes
				//argsFormales = new ArrayList<Parametro>();
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico: Argumentos formales inválidos.\nEsperado: ) o ,\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private void ctor() throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode, ExcepcionSemantico {
		Token token = tokenActual; 
		match("idClase");	// primeros
		List<Parametro> listaArgsFormales = argsFormales();
		LinkedHashMap<String, Parametro> parametros = new LinkedHashMap<String, Parametro>();
		Constructor ctor = new Constructor(token, parametros);
		CompiladorMiniJava.tablaSimbolos.setUnidadActual(ctor);
		CompiladorMiniJava.tablaSimbolos.setBloque(null);
		NodoBloque bloque = bloque();
		CompiladorMiniJava.tablaSimbolos.setBloque(bloque);
		int posicion = 1;
		for (Parametro param : listaArgsFormales) {
			param.setPosicion(posicion);
			if (parametros.get(param.getNombre()) == null) {
				parametros.put(param.getNombre(), param);
				CompiladorMiniJava.tablaSimbolos.getUnidadActual().insertarParametro(param);
			}
			else
				throw new ExcepcionSemantico("[" + param.getNroLinea() + ":" + param.getNroColumna() + "] Error semántico: Nombre de parámetro \"" + param.getNombre() + "\" repetido.");
			posicion++;
		}
		if (!CompiladorMiniJava.tablaSimbolos.getClaseActual().getNombre().equals(token.getLexema()))
			throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna() + "] Error semántico: El nombre del constructor es inválido, debe ser " + CompiladorMiniJava.tablaSimbolos.getClaseActual().getNombre() + ".");
		CompiladorMiniJava.tablaSimbolos.insertarUnidad(ctor);
	}

	private NodoBloque bloque() throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode, ExcepcionSemantico {
		match("{");		// primeros
		NodoBloque bloque = new NodoBloque();
		bloque.setPadre(CompiladorMiniJava.tablaSimbolos.getBloqueActual());
		CompiladorMiniJava.tablaSimbolos.setBloqueActual(bloque);
		sentencias(bloque);
		match("}");
		CompiladorMiniJava.tablaSimbolos.setBloqueActual(bloque.getPadre());
		return bloque;
	}

	private NodoBloque sentencias(NodoBloque bloque) throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode, ExcepcionSemantico {
		switch (tokenActual.getNombre()) {
			case ";":				// primeros
			case "idMetVar":		// primeros
			case "(":				// primeros
			case "boolean":			// primeros
			case "char":			// primeros
			case "int":				// primeros
			case "String":			// primeros
			case "idClase":			// primeros
			case "if":				// primeros
			case "while":			// primeros
			case "{":				// primeros
			case "return":			// primeros
			case "new":				// primeros
			case "+":				// primeros
			case "-":				// primeros
			case "!":				// primeros
			case "null":			// primeros
			case "true":			// primeros
			case "false":			// primeros
			case "intLiteral":		// primeros
			case "charLiteral":		// primeros
			case "stringLiteral":	// primeros
			case "this":			// primeros
				NodoSentencia sentencia = sentencia();
				if (sentencia != null ) {
				bloque.insertarSentencia(sentencia);
				sentencias(bloque);
				return bloque; }
			case "}":				// siguientes
				return bloque;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico: Se espera la declaración de una sentencia o }.\nEsperado: idMetVar, (, boolean, char, int, String, idClase, if, while, {, return, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this, } o ;\nEncontrado: " + tokenActual.getNombre());
		}		
	}

	private NodoSentencia sentencia() throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode, ExcepcionSemantico {
		int panicoLinea = tokenActual.getNroLinea();
		int panicoColumna = tokenActual.getNroColumna();
		switch (tokenActual.getNombre()) {
			case ";":								// primeros
				match(";");
				return new NodoPuntoComa();
			case "idMetVar":						// primeros
				try {
					NodoExpresion exp = ladoIzquierdo();
					NodoSentencia sent = ladoDerechoIdMetVar(exp);
					match(";");
					return sent;
				} catch (ExcepcionSintactico e) {
					modoPanicoBloque(panicoLinea, panicoColumna, e.toString());
				}
				break;
			case "+":								// primeros
			case "-":								// primeros
			case "!":								// primeros
			case "null":							// primeros
			case "true":							// primeros
			case "false":							// primeros
			case "intLiteral":						// primeros
			case "charLiteral":						// primeros
			case "stringLiteral":					// primeros
			case "this":							// primeros
			case "new":								// primeros
			case "(":								// primeros
				try {
					NodoSentenciaSimple sentenciaSimple = sentenciaSimple();
					match(";");
					return sentenciaSimple;
				} catch (ExcepcionSintactico e) {
					modoPanicoBloque(panicoLinea, panicoColumna, e.toString());
				}
				break;
			case "boolean":							// primeros
			case "char":							// primeros
			case "int":								// primeros
			case "String":							// primeros
				Tipo tipo = tipoPrimitivo();
				try {
					List<Token> listaTokenVariablesLocales = new ArrayList<Token>();
					listaDecVars(listaTokenVariablesLocales);
					List<Variable> varsLocales = new ArrayList<Variable>();
					for (Token tokenVarLocal : listaTokenVariablesLocales) {
						VarLocal varLocal = new VarLocal(tokenVarLocal,tipo);
						varsLocales.add(varLocal);
					}
					NodoSentencia sentenciaDecAsig = decAsig(varsLocales, null);
					match(";");
					return sentenciaDecAsig;
				} catch (ExcepcionSintactico e) {
					modoPanicoBloque(panicoLinea, panicoColumna, e.toString());
				}
				break;
			case "idClase":							// primeros
				Token tokenIdClase = tokenActual;
				match("idClase");
				try {
					NodoSentencia sentencia = ladoDerechoIdClase(tokenIdClase);
					match(";");
					return sentencia;
				} catch (ExcepcionSintactico e) {
					modoPanicoBloque(panicoLinea, panicoColumna, e.toString());
				}
				break;
			case "if":								// primeros
				int nroLineaIf = tokenActual.getNroLinea();
				int nroColumnaIf = tokenActual.getNroColumna();
				match("if");
				NodoExpresion condicion = null;
				NodoSentencia entonces = null;
				try {
					match("(");
					condicion = expresion();
					match(")");
					ifPanico = true;
					entonces = sentencia();
					ifPanico = false;
				} catch (ExcepcionSintactico e) {
					modoPanicoBloque(panicoLinea, panicoColumna, e.toString());
				}
				NodoSentencia sino = rSentenciaIf();
				return new NodoIf(nroLineaIf,nroColumnaIf,condicion,entonces,sino);
			case "while":							// primeros
				Token tkWhile = tokenActual;
				match("while");
				try {
					match("(");
					NodoExpresion condicionW = expresion();
					match(")");
					NodoSentencia sentWhile = sentencia();
					return new NodoWhile(tkWhile.getNroLinea(), tkWhile.getNroColumna(), condicionW, sentWhile);
				} catch (ExcepcionSintactico e) {
					modoPanicoBloque(panicoLinea, panicoColumna, e.toString());
				}
				break;
			case "{":								// primeros
				return bloque();
			case "return":							// primeros
				match("return");
				try {
					NodoExpresion expresionRetorno = retorno();
					match(";");
					return new NodoRetorno(expresionRetorno);
				} catch (ExcepcionSintactico e) {
					modoPanicoBloque(panicoLinea, panicoColumna, e.toString());
				}
				break;
			default:
				throw new ExcepcionSintactico("Error sintáctico: Se espera la declaración de una sentencia.\nEsperado: ;, idMetVar, (, boolean, char, int, String, idClase, if, while, {, return, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral o this.\nEncontrado: " + tokenActual.getNombre());
		}
		return null; // caso excepcional
	}
	
	private void modoPanicoBloque(int nroLineaComienzo, int nroColumnaComienzo, String mensajeError) throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode, ExcepcionSemantico {
		int nroLineaFin = ultimaLineaAnalizada;
		int nroColumnaFin = ultimaColumnaAnalizada;
		CompiladorMiniJava.tablaSimbolos.setRSin();
		ArrayList<String> siguientesValidos = new ArrayList<String>();
		siguientesValidos.add("}");
		siguientesValidos.add(";");
		siguientesValidos.add("idMetVar");
		siguientesValidos.add("+");
		siguientesValidos.add("-");
		siguientesValidos.add("!");
		siguientesValidos.add("null");
		siguientesValidos.add("true");
		siguientesValidos.add("false");
		siguientesValidos.add("intLiteral");
		siguientesValidos.add("charLiteral");
		siguientesValidos.add("stringLiteral");
		siguientesValidos.add("this");
		siguientesValidos.add("new");
		siguientesValidos.add("(");
		siguientesValidos.add("boolean");
		siguientesValidos.add("char");
		siguientesValidos.add("int");
		siguientesValidos.add("String");
		siguientesValidos.add("idClase");
		siguientesValidos.add("if");
		siguientesValidos.add("while");
		siguientesValidos.add("{");
		siguientesValidos.add("return");
		siguientesValidos.add("else");
		if (!siguientesValidos.contains(tokenActual.getNombre())) {
			while(!siguientesValidos.contains(tokenActual.getNombre()) && !tokenActual.getNombre().equals("EOF")) {
				if (nroLineaFin != tokenActual.getNroLinea())
					nroLineaFin = tokenActual.getNroLinea();
				nroColumnaFin = tokenActual.getNroColumna() + tokenActual.getLexema().length();
				tokenActual = analizadorLexico.getToken();
			}
		}
		if (tokenActual.getNombre().equals("EOF")) {
			System.out.println("[" + nroLineaComienzo + "]: sentencia sintácticamente inválida.\nCausa:\n" + mensajeError);
			throw new ExcepcionPanicMode("No se puede recuperar de la última sentencia sintácticamente inválida.");
		} else {
			System.out.println("[" + nroLineaComienzo + ":" + nroColumnaComienzo + " - " + nroLineaFin + ":" + nroColumnaFin + "]: sentencia sintácticamente inválida.\nCausa:\n" + mensajeError);
			switch (tokenActual.getNombre()) {
				case "}":
					break;
				case "else":
					if (ifPanico)
						rSentenciaIf();
					else
						throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico: Se espera la declaración de una sentencia o }.\nEsperado: ;, idMetVar, (, boolean, char, int, String, idClase, if, while, {, return, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this o }\nEncontrado: " + tokenActual.getNombre());
					break;
				default:
					sentencias(CompiladorMiniJava.tablaSimbolos.getBloqueActual());
					break;
			}
		}
	}
	
	private NodoExpresion ladoIzquierdo() throws ExcepcionLexico, ExcepcionSintactico {
		NodoPrimario exp = null;
		Token token = tokenActual;
		match("idMetVar"); // primeros
		Encadenado encadenado = rLlamadaoIdEncadenado(token); // ver bien
		if (encadenado == null)
			exp = new NodoVar(token);
		else {
			if (encadenado instanceof NodoLlamadaEncadenado) {
				NodoLlamadaEncadenado llamadaEnc = (NodoLlamadaEncadenado) encadenado;
				List<NodoExpresion> argsActuales = llamadaEnc.getArgsActuales();
				exp = new NodoLlamadaDirecta(token, argsActuales);
			} else {
				exp = new NodoVar(token);
				exp.setEncadenado(encadenado);
			}
		}
		Encadenado cadena = rEncadenado();
		exp.setEncadenado(cadena);
		return exp;
	}
	
	private NodoSentencia ladoDerechoIdMetVar(NodoExpresion exp) throws ExcepcionLexico, ExcepcionSintactico {		
		switch (tokenActual.getNombre()) {
			case "=":					// primeros
				if (exp instanceof NodoVar && ((NodoVar) exp).getEncadenado() == null) {
					NodoVar nv = (NodoVar) exp;
					List<Token> listaTokenVariablesLocales = new ArrayList<Token>();
					listaTokenVariablesLocales.add(nv.getToken());
					List<Variable> varsLocales = new ArrayList<Variable>();
					for (Token tokenVarLocal : listaTokenVariablesLocales) {
						VarLocal varLocal = new VarLocal(tokenVarLocal,null);
						varsLocales.add(varLocal);
					}
					NodoSentencia sentenciaDecAsig = decAsig(varsLocales, null);
					return sentenciaDecAsig;
				} else
				return decAsig(null, exp);
			case "&&":					// primeros
			case "||":					// primeros
			case "*":					// primeros
			case "/":					// primeros
			case "%":					// primeros
			case "+":					// primeros
			case "-":					// primeros
			case "==":					// primeros
			case "!=":					// primeros
			case ">":					// primeros
			case ">=":					// primeros
			case "<":					// primeros
			case "<=":					// primeros
				return new NodoSentenciaSimple(desparentizada(exp));
			case ";":					// siguientes
				return new NodoSentenciaSimple(exp);	
			case ",":
				if (exp instanceof NodoVar && ((NodoVar) exp).getEncadenado() == null) {
					NodoVar nv = (NodoVar) exp;
					List<Token> listaTokenVariablesLocales = new ArrayList<Token>();
					listaTokenVariablesLocales.add(nv.getToken());
					rListaDecVars(listaTokenVariablesLocales);
					List<Variable> varsLocales = new ArrayList<Variable>();
					for (Token tokenVarLocal : listaTokenVariablesLocales) {
						VarLocal varLocal = new VarLocal(tokenVarLocal,null);
						varsLocales.add(varLocal);
					}
					NodoSentencia sentenciaDecAsig = decAsig(varsLocales, null);
					return sentenciaDecAsig;
				}
			default:
				throw new ExcepcionSintactico("Error sintáctico: Sentencia simple desparentizada inválida.\nEsperado: =, &&, ||, *, /, %, +, -, ==, !=, >, >=, <, <=, o ;\nEncontrado: " + tokenActual.getNombre());
		}
	}
	
	private NodoSentenciaSimple sentenciaSimple() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "(":				// primeros
				match("(");
				NodoExpresion expresion = expresion();
				match(")");
				NodoExpresionParentizada expresionParentizada = new NodoExpresionParentizada(expresion);
				return rDesparentizada(expresionParentizada);
			case "+":				// primeros
			case "-":				// primeros
			case "!":				// primeros
				Token operador = tokenActual;
				opUn();
				NodoExpresion exp = expUn();
				NodoExpresionUnaria expresionUnaria = new NodoExpresionUnaria(exp, operador);
				return new NodoSentenciaSimple(desparentizada(expresionUnaria));
			case "null":			// primeros
			case "true":			// primeros
			case "false":			// primeros
			case "intLiteral":		// primeros
			case "charLiteral":		// primeros
			case "stringLiteral":	// primeros
				NodoOperando literal = literal();
				return new NodoSentenciaSimple(desparentizada(literal));
			case "this":			// primeros
				NodoPrimario accesoThis = accesoThis();
				Encadenado cadena = rEncadenado();
				accesoThis.setEncadenado(cadena);
				return new NodoSentenciaSimple(desparentizada(accesoThis));
			case "new":				// primeros
				NodoPrimario ctor = llamadaConstructor();
				Encadenado cadenaCtor = rEncadenado();
				ctor.setEncadenado(cadenaCtor);
				return new NodoSentenciaSimple(desparentizada(ctor));
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico: sentencia simple inválida.");
			}
	}
	
	private NodoSentenciaSimple rDesparentizada(NodoExpresionParentizada expresion) throws ExcepcionLexico, ExcepcionSintactico {
		Encadenado cadena = rEncadenado();
		expresion.setEncadenado(cadena);
		NodoExpresion exp = desparentizada(expresion);
		return new NodoSentenciaSimple(exp);
	}

	private void listaDecVars(List<Token> listaTokenVariablesLocales) throws ExcepcionLexico, ExcepcionSintactico {
		Token tokenVarLocal = tokenActual;
		match("idMetVar");	// primeros
		listaTokenVariablesLocales.add(tokenVarLocal);
		rListaDecVars(listaTokenVariablesLocales);
	}

	private void rListaDecVars(List<Token> listaTokenVariablesLocales) throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case ",":				// primeros
				match(",");
				listaDecVars(listaTokenVariablesLocales);
				break;
			case ";":				// siguientes
			case "=":				// siguientes
				break;
			default:
				throw new ExcepcionSintactico("Error sintáctico: Declaración/asignación de variables inválida.\nEsperado: ;, = o ,\nEncontrado: " + tokenActual.getNombre());
			}
	}
	
	private NodoSentencia ladoDerechoIdClase(Token tokenIdClase) throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {	// primeros
			case "idMetVar":
				Tipo tipo = new TipoClase(tokenIdClase);
				List<Token> listaTokenVariablesLocales = new ArrayList<Token>();
				listaDecVars(listaTokenVariablesLocales);
				List<Variable> varsLocales = new ArrayList<Variable>();
				for (Token tokenVarLocal : listaTokenVariablesLocales) {
					VarLocal varLocal = new VarLocal(tokenVarLocal,tipo);
					varsLocales.add(varLocal);
				}
				return decAsig(varsLocales, null);
			case ".":						// primeros
				match(".");
				NodoPrimario prim = llamadaMetodo(tokenIdClase);
				Encadenado cadena = rEncadenado();
				prim.setEncadenado(cadena);
				if (tokenActual.getNombre().equals("="))
					return decAsig(null, prim);
				else
					return new NodoSentenciaSimple(desparentizada(prim));
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico: Error en sentencia comenzada con idClase.\nEsperado: . o idMetVar\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private NodoSentencia rSentenciaIf() throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode, ExcepcionSemantico {
		int panicoLinea = tokenActual.getNroLinea();
		int panicoColumna = tokenActual.getNroColumna();
		if (tokenActual.getNombre().equals("else")) {	// primeros
			match("else");
			try {	
				return sentencia();
			} catch (ExcepcionSintactico e) {
				modoPanicoBloque(panicoLinea, panicoColumna, e.toString());
			}
		} else {
			
		}
		return null;
	}
	
	private NodoExpresion retorno() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "+":				// primeros
			case "-":				// primeros
			case "!":				// primeros
			case "null":			// primeros
			case "true":			// primeros
			case "false":			// primeros
			case "intLiteral":		// primeros
			case "charLiteral":		// primeros
			case "stringLiteral":	// primeros
			case "this":			// primeros
			case "new":				// primeros
			case "(":				// primeros
			case "idMetVar":		// primeros
			case "idClase":			// primeros
				return expresion();
			case ";":				// siguientes
				return null;
			default:
				throw new ExcepcionSintactico("Error sintáctico: Error en expresión de retorno.\nEsperado: +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this, new, (, idMetVar, idClase o ;\nEncontrado: " + tokenActual.getNombre());
		}
	}
	
	private NodoExpresion desparentizada(NodoExpresion expresion) throws ExcepcionLexico, ExcepcionSintactico {
		switch(tokenActual.getNombre()) {
			case "&&":					// primeros
				NodoExpresion expAnd = and(expresion);
				return desparentizada(expAnd);
			case "||":					// primeros
				NodoExpresion expOr = or(expresion);
				return desparentizada(expOr);
			case "*":					// primeros
			case "/":					// primeros
			case "%":					// primeros
				NodoExpresion expMul = mul(expresion);
				return desparentizada(expMul);
			case "+":					// primeros
			case "-":					// primeros
				NodoExpresion expAd = ad(expresion);
				return desparentizada(expAd);
			case "==":					// primeros
			case "!=":					// primeros
				NodoExpresion expIg = ig(expresion);
				return desparentizada(expIg);
			case ">":					// primeros
			case ">=":					// primeros
			case "<":					// primeros
			case "<=":					// primeros
				NodoExpresion expComp = rExpComp(expresion);
				return desparentizada(expComp);
			case ";":					// siguientes
				return expresion;
			default:
				throw new ExcepcionSintactico("Error sintáctico: Sentencia simple desparentizada inválida.\nEsperado: &&, ||, *, /, %, +, -, ==, !=, >, >=, <, <=, o ;\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private NodoExpresion expresion() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "+":				// primeros
			case "-":				// primeros
			case "!":				// primeros
			case "null":			// primeros
			case "true":			// primeros
			case "false":			// primeros
			case "intLiteral":		// primeros
			case "charLiteral":		// primeros
			case "stringLiteral":	// primeros
			case "this":			// primeros
			case "new":				// primeros
			case "(":				// primeros
			case "idMetVar":		// primeros
			case "idClase":			// primeros
				return expOr();
			default:
				throw new ExcepcionSintactico("Error sintáctico: Expresión inválida.\nEsperado: +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, (, this, idMetVar, idClase o new\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private NodoExpresion expOr() throws ExcepcionLexico, ExcepcionSintactico {
		NodoExpresion exp = expAnd();
		return or(exp);
	}

	private NodoExpresion or(NodoExpresion exp) throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("||")) {	// primeros
			Token operador = tokenActual;
			match("||");
			NodoExpresion der = expAnd();
			NodoExpresion expresionBinaria = new NodoExpresionBinaria(operador, exp, der);
			return or(expresionBinaria);
		} else {
			return exp;
		}
	}

	private NodoExpresion expAnd() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "+":				// primeros
			case "-":				// primeros
			case "!":				// primeros
			case "null":			// primeros
			case "true":			// primeros
			case "false":			// primeros
			case "intLiteral":		// primeros
			case "charLiteral":		// primeros
			case "stringLiteral":	// primeros
			case "this":			// primeros
			case "new":				// primeros
			case "(":				// primeros
			case "idMetVar":		// primeros
			case "idClase":			// primeros
				NodoExpresion exp = expIg();
				return and(exp);
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico: Expresión inválida.\nEsperado: +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, (, this, idMetVar, idClase o new\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private NodoExpresion and(NodoExpresion exp) throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("&&")) {	// primeros
			Token operador = tokenActual;
			match("&&");
			NodoExpresion der = expIg();
			NodoExpresion expresionBinaria = new NodoExpresionBinaria(operador, exp, der);
			return and(expresionBinaria);
		} else {
			return exp;
		}
	}

	private NodoExpresion expIg() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "+":				// primeros
			case "-":				// primeros
			case "!":				// primeros
			case "null":			// primeros
			case "true":			// primeros
			case "false":			// primeros
			case "intLiteral":		// primeros
			case "charLiteral":		// primeros
			case "stringLiteral":	// primeros
			case "this":			// primeros
			case "new":				// primeros
			case "(":				// primeros
			case "idMetVar":		// primeros
			case "idClase":			// primeros
				NodoExpresion exp = expComp();
				return ig(exp);
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico: Expresión inválida.\nEsperado: +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, (, this, idMetVar, idClase o new\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private NodoExpresion ig(NodoExpresion exp) throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "==":		// primeros
			case "!=":		// primeros
				Token operador = tokenActual;
				opIg();
				NodoExpresion der = expComp();
				NodoExpresion expresionBinaria = new NodoExpresionBinaria(operador, exp, der);
				return ig(expresionBinaria);
			default:
				return exp;
		}
	}

	private NodoExpresion expComp() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "+":				// primeros
			case "-":				// primeros
			case "!":				// primeros
			case "null":			// primeros
			case "true":			// primeros
			case "false":			// primeros
			case "intLiteral":		// primeros
			case "charLiteral":		// primeros
			case "stringLiteral":	// primeros
			case "(":				// primeros
			case "this":			// primeros
			case "idMetVar":		// primeros
			case "idClase":			// primeros
			case "new":				// primeros
				NodoExpresion exp = expAd();
				return rExpComp(exp);
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico: Expresión inválida.\nEsperado: +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, (, this, idMetVar, idClase o new\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private NodoExpresion rExpComp(NodoExpresion exp) throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "<":		// primeros
			case "<=":		// primeros
			case ">":		// primeros
			case ">=":		// primeros
				Token operador = tokenActual;
				opComp();
				NodoExpresion der = expAd();
				NodoExpresion expresionBinaria = new NodoExpresionBinaria(operador, exp, der);
				return expresionBinaria;
			default:
				return exp;
		}
	}

	private NodoExpresion expAd() throws ExcepcionLexico, ExcepcionSintactico {
		NodoExpresion exp = expMul();
		return ad(exp);
	}

	private NodoExpresion ad(NodoExpresion exp) throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "+":		// primeros
			case "-":		// primeros
				Token operador = tokenActual;
				opAd();
				NodoExpresion der = expMul();
				NodoExpresion expresionBinaria = new NodoExpresionBinaria(operador, exp, der);
				return ad(expresionBinaria);
			default:
				return exp;
			}
	}

	private NodoExpresion expMul() throws ExcepcionLexico, ExcepcionSintactico {
		NodoExpresion exp = expUn();
		return mul(exp);
	}

	private NodoExpresion mul(NodoExpresion exp) throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "*":		// primeros
			case "/":		// primeros
			case "%":		// primeros
				Token operador = tokenActual;
				opMul();
				NodoExpresion der = expUn();
				NodoExpresion expresionBinaria = new NodoExpresionBinaria(operador, exp, der);
				return mul(expresionBinaria);
			default:
				return exp;
		}
	}
	
	private NodoExpresion expUn() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "+":				// primeros
			case "-":				// primeros
			case "!":				// primeros
				Token operador = tokenActual;
				opUn();
				NodoExpresion expresionUnaria = expUn();
				return new NodoExpresionUnaria(expresionUnaria,operador);
			case "null":			// primeros
			case "true":			// primeros
			case "false":			// primeros
			case "intLiteral":		// primeros
			case "charLiteral":		// primeros
			case "stringLiteral":	// primeros
			case "(":				// primeros
			case "this":			// primeros
			case "idMetVar":		// primeros
			case "idClase":			// primeros
			case "new":				// primeros
				return operando();
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico: Expresión unaria inválida.\nEsperado: +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, (, this, idMetVar, idClase o new\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private void opIg() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "==":			// primeros
				match("==");
				break;
			case "!=":			// primeros
				match("!=");
				break;
		}
	}
	
	private void opComp() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "<":			// primeros
				match("<");
				break;
			case "<=":			// primeros
				match("<=");
				break;
			case ">":			// primeros
				match(">");
				break;
			case ">=":			// primeros
				match(">=");
				break;
		}
	}
	
	private void opAd() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "+":			// primeros
				match("+");
				break;
			case "-":			// primeros
				match("-");
				break;
		}
	}

	private void opUn() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "+":			// primeros
				match("+");
				break;
			case "-":			// primeros
				match("-");
				break;
			case "!":			// primeros
				match("!");
				break;
		}
	}
	
	private void opMul() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "*":			// primeros
				match("*");
				break;
			case "/":			// primeros
				match("/");
				break;
			case "%":			// primeros
				match("%");
				break;
		}
	}

	private NodoExpresion operando() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "null":			// primeros
			case "true":			// primeros
			case "false":			// primeros
			case "intLiteral":		// primeros
			case "charLiteral":		// primeros
			case "stringLiteral":	// primeros
				return literal();
			case "(":				// primeros
			case "this":			// primeros
			case "idMetVar":		// primeros
			case "idClase":			// primeros
			case "new":				// primeros
				NodoPrimario primario = primario();
				Encadenado cadena = rEncadenado();
				primario.setEncadenado(cadena);
				return primario;
			default:
				return null; //agregado por cuestión del lenguaje
		}
	}
	
	private NodoOperando literal() throws ExcepcionLexico, ExcepcionSintactico {
		Token token = tokenActual;
		switch (tokenActual.getNombre()) {
			case "null":						// primeros
				match("null");
				break;
			case "true":						// primeros
				match("true");
				break;
			case "false":						// primeros
				match("false");
				break;
			case "intLiteral":					// primeros
				match("intLiteral");
				break;
			case "charLiteral":					// primeros
				match("charLiteral");
				break;
			case "stringLiteral":				// primeros
				match("stringLiteral");
				break;
		}
		return new NodoLiteral(token);
	}

	private NodoPrimario primario() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "(":							// primeros
				match("(");
				NodoExpresion expresion = expresion();
				match(")");
				return new NodoExpresionParentizada(expresion);
			case "this":						// primeros
				return accesoThis();
			case "idMetVar":					// primeros
				Token token = tokenActual;
				match("idMetVar");
			//	ladoIzq = true;
				Encadenado encadenado = rLlamadaoIdEncadenado(token); // ver bien
			//	ladoIzq = false;
				if (encadenado == null)
					return new NodoVar(token);
				else {
					if (encadenado instanceof NodoLlamadaEncadenado) {
						NodoLlamadaEncadenado enc = (NodoLlamadaEncadenado) encadenado;
						List<NodoExpresion> argsActuales = enc.getArgsActuales();
						return new NodoLlamadaDirecta(token,argsActuales);
						//return new NodoLlamadaDirecta(token,argsActuales,ladoIzq);
					}
				}
			case "idClase":						// primeros
				return llamadaEstatica();
			case "new":							// primeros
				return llamadaConstructor();
			default:
				return null; //agregado por cuestión del lenguaje
		}
	}
	
	private NodoPrimario accesoThis() throws ExcepcionLexico, ExcepcionSintactico {
		Token token = tokenActual;
		match("this");	// primeros
		return new NodoThis(token);
	}
	
	private NodoPrimario llamadaEstatica() throws ExcepcionLexico, ExcepcionSintactico {
		Token tokenIdClase = tokenActual;
		match("idClase");	// primeros
		match(".");
		return llamadaMetodo(tokenIdClase);
	}

	private NodoPrimario llamadaConstructor() throws ExcepcionLexico, ExcepcionSintactico {
		match("new");		// primeros
		Token token = tokenActual;
		match("idClase");
		List<NodoExpresion> argsActuales = argsActuales();
		return new NodoConstructor(token,argsActuales);
	}

	private NodoPrimario llamadaMetodo(Token tokenIdClase) throws ExcepcionLexico, ExcepcionSintactico {
		Token tokenIdMetVar = tokenActual;
		match("idMetVar");	// primeros
		List<NodoExpresion> argsActuales = argsActuales();
		return new NodoLlamadaEstatica(tokenIdClase,tokenIdMetVar,argsActuales);
	}
	
	private Encadenado encadenado() throws ExcepcionLexico, ExcepcionSintactico {
		Encadenado enc = llamadaoIdEncadenado();
		if (enc != null) {
			Encadenado cadena = rEncadenado();
			enc.setCadena(cadena);
			//return enc;
		}
		return enc;
	}

	private Encadenado llamadaoIdEncadenado() throws ExcepcionLexico, ExcepcionSintactico {
		match(".");					// primeros
		Token tokenIdMetVar = tokenActual;
		match("idMetVar");
		Encadenado enc = rLlamadaoIdEncadenado(tokenIdMetVar);
		if (enc == null)
			enc = new NodoVarEncadenado(tokenIdMetVar);
		return enc;
	}

	private Encadenado rLlamadaoIdEncadenado(Token tokenIdMetVar) throws ExcepcionLexico, ExcepcionSintactico {
		/**
		 * CORREGIR
		 */
		
		if (tokenActual.getNombre().equals("(")) {	// primeros
			List<NodoExpresion> argsActuales = argsActuales();
			return new NodoLlamadaEncadenado(tokenIdMetVar,argsActuales);
		} else {// ver bien
		// estaba antes	if (tokenActual.getNombre().equals("idMetVar"))
			//	return null;
			//}
				//return new NodoVarEncadenado(tokenIdMetVar); // ver bien
		//estaba antes	else
	//antes			return null;
		//	if (tokenActual.getNombre().equals("idMetVar"))
		//		return new NodoVarEncadenado(tokenActual);
		//	else
				return null;
		}
	}
	
	private Encadenado rEncadenado() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals(".")) {	// primeros
			return encadenado();
		} else { return null; }
	}

	private List<NodoExpresion> argsActuales() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("(")) {	// primeros
			match("(");
			List<NodoExpresion> argsActuales = listaExpresiones();
			match(")");
			return argsActuales;
			
		} else {
			throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico: Declaración de argumentos actuales inválida.\nEsperado: (\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private List<NodoExpresion> listaExpresiones() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "idMetVar":		// primeros
			case "(":				// primeros
			case "idClase":			// primeros
			case "new":				// primeros
			case "+":				// primeros
			case "-":				// primeros
			case "!":				// primeros
			case "null":			// primeros
			case "true":			// primeros
			case "false":			// primeros
			case "intLiteral":		// primeros
			case "charLiteral":		// primeros
			case "stringLiteral":	// primeros
			case "this":			// primeros
				List<NodoExpresion> argsActuales = new ArrayList<NodoExpresion>();
				listaExps(argsActuales);
				return argsActuales;
			case ")":				// siguientes
				return new ArrayList<NodoExpresion>();
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico: Lista de expresiones inválida.\nEsperado: (, idMetVar, idClase, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this o )\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private void listaExps(List<NodoExpresion> argsActuales) throws ExcepcionLexico, ExcepcionSintactico {
		NodoExpresion expresion = expresion();
		argsActuales.add(expresion);
		rListaExps(argsActuales);
	}

	private void rListaExps(List<NodoExpresion> argsActuales) throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case ",":			// primeros
				match(",");
				listaExps(argsActuales);
				break;
			case ")":			// siguientes
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico: Lista de expresiones inválida.\nEsperado: (, idMetVar, idClase, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this o )\nEncontrado: " + tokenActual.getNombre());
		}
	}
}