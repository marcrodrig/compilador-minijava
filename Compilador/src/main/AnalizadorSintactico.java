package main;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnalizadorSintactico {

	private AnalizadorLexico analizadorLexico;
	private Token tokenActual;
	private boolean modoPanico, ifPanico;
	private int ultimaLineaAnalizada, ultimaColumnaAnalizada;
	TablaSimbolos ts;
	
	public AnalizadorSintactico(String archivoEntrada) throws FileNotFoundException, ExcepcionLexico {
		analizadorLexico = new AnalizadorLexico(archivoEntrada);
		tokenActual = analizadorLexico.getToken();
		modoPanico = false;
		ifPanico = false;
		ts = TablaSimbolos.getInstance();
	}

	private void match(String nombre) throws ExcepcionLexico, ExcepcionSintactico {
		if (nombre.equals(tokenActual.getNombre())) {
			ultimaLineaAnalizada = tokenActual.getNroLinea();
			ultimaColumnaAnalizada = tokenActual.getNroColumna() + tokenActual.getLexema().length();
			tokenActual = analizadorLexico.getToken();			
		}
		else			
			throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico.\nEsperado: " + nombre + "\nEncontrado: " + tokenActual.getNombre());
	}

	public boolean start() throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode, ExcepcionSemantico {
		inicial();
		match("EOF");
		return modoPanico;
	}
	
	private void inicial() throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode, ExcepcionSemantico {
		clase();
		otroInicial();
	}

	private void otroInicial() throws  ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode, ExcepcionSemantico {
		if (tokenActual.getNombre().equals("class")) {			// primeros
			clase();
			otroInicial();
		} else if (tokenActual.getNombre().equals("EOF")) {		// siguientes
		} else
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico en la declaración de una clase.\nEsperado: class\nEncontrado: " + tokenActual.getNombre());
	}
	
	private void clase() throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode, ExcepcionSemantico {
		match("class");		// primeros
	//	String nombreClase = tokenActual.getLexema();
		Token token = tokenActual;
		match("idClase");
		String superclase = herencia();
		Clase clase = new Clase(token, superclase);
		ts.setClaseActual(clase);
		match("{");
		miembros();
		if (ts.getClase(clase.getNombre()) == null)
			ts.insertarClase(clase);
		else
			throw new ExcepcionSemantico("[" + clase.getToken().getNroLinea() + "] Error semántico: La clase " + clase.getNombre() + " ya está definida." );
		match("}");
	}

	private String herencia() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("extends")) {	// primeros
			match("extends");
			try {
				String superclase = tokenActual.getLexema();
				match("idClase");
				return superclase;
			} catch (ExcepcionSintactico e) {
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico en la declaración de herencia de una clase.\nEsperado: idClase\nEncontrado: " + tokenActual.getNombre());
			}
		} else 
			if (tokenActual.getNombre().equals("{")) {		// siguientes
				return "Object";
			} else
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico en la declaración de una clase.\nEsperado: extends o {\nEncontrado: " + tokenActual.getNombre());
	}
	
	private void miembros() throws ExcepcionSintactico, ExcepcionLexico, ExcepcionPanicMode, ExcepcionSemantico {
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
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico. Se espera la declaración de un atributo, constructor, método o }.\nEsperado: public, protected, private, idClase, static, dynamic o }\nEncontrado: " + tokenActual.getNombre());		}
	}
	
	private void miembro() throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode, ExcepcionSemantico {
		switch (tokenActual.getNombre()) {
			case "public":		// primeros
			case "protected":	// primeros
			case "private":		// primeros
				atributo();
				break;
			case "idClase":		// primeros
				ctor();
				break;
			case "static":		// primeros
			case "dynamic":		// primeros
				metodo();
				break;
		}
	}

	private void atributo() throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode, ExcepcionSemantico {
		int panicoLinea = tokenActual.getNroLinea();
		int panicoColumna = tokenActual.getNroColumna();
		visibilidad();
		try {
			tipo();
			listaDecAtrs();
			decAsig();
			match(";");
		} catch (ExcepcionSintactico e) {
			modoPanicoAtributo(panicoLinea, panicoColumna);
		}
	}
	
	private void modoPanicoAtributo(int nroLineaComienzo, int nroColumnaComienzo) throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode, ExcepcionSemantico {
		int nroLineaFin = ultimaLineaAnalizada;
		int nroColumnaFin = ultimaColumnaAnalizada;
		modoPanico = true;
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
			System.out.println("[" + nroLineaComienzo + "]: declaración/asignación de atributos sintácticamente inválida.");
			throw new ExcepcionPanicMode("No se puede recuperar de la última sentencia inválida.");		
		}
		else {
			System.out.println("[" + nroLineaComienzo + ":" + nroColumnaComienzo + " - " + nroLineaFin + ":" + nroColumnaFin + "]: declaración/asignación de atributos sintácticamente inválida.");
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
	
	private void visibilidad() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "public":			// primeros
				match("public");
				break;
			case "protected":		// primeros
				match("protected");
				break;
			case "private":			// primeros
				match("private");
				break;
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
				match("idClase");
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico en tipo.\nEsperado: idClase, boolean, char, int o String\nEncontrado: " + tokenActual.getNombre());
		}
		return tipo;
	}

	private Tipo tipoPrimitivo() throws ExcepcionLexico, ExcepcionSintactico {
		Tipo tipo = null;
		switch (tokenActual.getNombre()) {
			case "boolean":			// primeros
				match("boolean");
				tipo = new TipoBoolean();
				break;
			case "char":			// primeros
				match("char");
				tipo = new TipoChar();
				break;
			case "int":				// primeros
				match("int");
				tipo = new TipoInt();
				break;
			case "String":			// primeros
				match("String");
				tipo = new TipoString();
				break;
		}
		return tipo;
	}

	private void listaDecAtrs() throws ExcepcionLexico, ExcepcionSintactico {
		match("idMetVar");	// primeros
		rlistaDecAtrs();
	}

	private void rlistaDecAtrs() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case ",":				// primeros
				match(",");
				listaDecAtrs();
				break;
			case ";": 				// siguientes
			case "=": 				// siguientes
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico en la declaración/asignación de atributos.\nEsperado: ; o =\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private void decAsig() throws ExcepcionLexico, ExcepcionSintactico {	
		switch (tokenActual.getNombre()) {
			case "=":				// primeros
				match("=");
				expresion();
				break;
			case ";":				// siguientes
				break;
		}
	}
	
	private void metodo() throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode {
		formaMetodo();
		fnl();
		tipoMetodo();
		match("idMetVar");
		argsFormales();
		bloque();
	}

	private void formaMetodo() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "static":			// primeros
				match("static");
				break;
			case "dynamic":			// primeros
				match("dynamic");
				break;
		}
	}

	private void fnl() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "final":			// primeros
				match("final");
				break;
			case "void":			// siguientes
			case "idClase":			// siguientes
			case "boolean":			// siguientes
			case "char":			// siguientes
			case "int":				// siguientes
			case "String":			// siguientes
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico en método.\nEsperado: final o tipo de método\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private void tipoMetodo() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "void":		// primeros
				match("void");
				break;
			case "idClase":		// primeros
			case "boolean":		// primeros
			case "char":		// primeros
			case "int":			// primeros
			case "String":		// primeros
				tipo();
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico en tipo método.\nEsperado: idClase, boolean, char, int, String o void\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private List<Parametro> argsFormales() throws ExcepcionLexico, ExcepcionSintactico {
		List<Parametro> argsFormales = null;
		if (tokenActual.getNombre().equals("(")) {	// primeros
			match("(");
			argsFormales = argumentos();
			match(")");
		} else {
			throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico en la declaración de argumentos formales.\nEsperado: (\nEncontrado: " + tokenActual.getNombre());
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
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico en argumentos formales.\nEsperado: ) o tipo\nEncontrado: " + tokenActual.getNombre());
			}
		return argsFormales;
	}

	private void listaArgsFormales(List<Parametro> argsFormales) throws ExcepcionLexico, ExcepcionSintactico {
	//	List<Parametro> lista = 
				argFormal(argsFormales);
		//argsFormales = 
				rArgFormal(argsFormales);
	}

	private void argFormal(List<Parametro> argsFormales) throws ExcepcionLexico, ExcepcionSintactico {
		Tipo tipoParametro = tipo();
		String nombreParametro = tokenActual.getLexema();
		match("idMetVar");
		Parametro param = new Parametro(nombreParametro, tipoParametro);
		argsFormales.add(param);
		//return argsFormales;
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
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico en los argumentos formales.\nEsperado: ) o ,\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private void ctor() throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode, ExcepcionSemantico {
		//String nombreConstructor = tokenActual.getLexema();
		Token token = tokenActual; 
		match("idClase");	// primeros
		if (!ts.getClaseActual().getNombre().equals(token.getLexema()))
			throw new ExcepcionSemantico("[" + token.getNroLinea() + "] Error semántico: El nombre del constructor es inválido, debe ser " + ts.getClaseActual().getNombre() + ".");
		List<Parametro> listaArgsFormales = argsFormales();
		HashMap<String, Parametro> parametros = new HashMap<String, Parametro>();
		int posicion = 1;
		for (Parametro param : listaArgsFormales) {
			param.setPosicion(posicion);
			parametros.put(param.getNombre(), param);
			posicion++;
		}
		Constructor ctor = new Constructor(token, parametros);
		ts.insertarConstructor(ctor);
		bloque();
	}

	private void bloque() throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode {
		match("{");		// primeros
		sentencias();
		match("}");
	}

	private void sentencias() throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode {
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
				sentencia();
				sentencias();
				break;
			case "}":				// siguientes
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico. Se espera la declaración de una sentencia o }.\nEsperado: idMetVar, (, boolean, char, int, String, idClase, if, while, {, return, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this, } o ;\nEncontrado: " + tokenActual.getNombre());
		}		
	}

	private void sentencia() throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode {
		int panicoLinea = tokenActual.getNroLinea();
		int panicoColumna = tokenActual.getNroColumna();
		switch (tokenActual.getNombre()) {
			case ";":								// primeros
				match(";");
				break;
			case "idMetVar":						// primeros
				try {
					ladoIzquierdo();
					ladoDerechoIdMetVar();
					match(";");
				} catch (ExcepcionSintactico e) {
					modoPanicoBloque(panicoLinea, panicoColumna);
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
					sentenciaSimple();
					match(";");
				} catch (ExcepcionSintactico e) {
					modoPanicoBloque(panicoLinea, panicoColumna);
				}
				break;
			case "boolean":							// primeros
			case "char":							// primeros
			case "int":								// primeros
			case "String":							// primeros
				tipoPrimitivo();
				try {
					listaDecVars();
					decAsig();
					match(";");
				} catch (ExcepcionSintactico e) {
					modoPanicoBloque(panicoLinea, panicoColumna);
				}
				break;
			case "idClase":							// primeros
				match("idClase");
				try {
					ladoDerechoIdClase();
					match(";");
				} catch (ExcepcionSintactico e) {
					modoPanicoBloque(panicoLinea, panicoColumna);
				}
				break;
			case "if":								// primeros
				match("if");
				try {
					match("(");
					expresion();
					match(")");
					ifPanico = true;
					panicoLinea = tokenActual.getNroLinea();
					panicoColumna = tokenActual.getNroColumna();
					sentencia();
					ifPanico = false;
					rSentenciaIf();
				} catch (ExcepcionSintactico e) {
					modoPanicoBloque(panicoLinea, panicoColumna);
				}
				break;
			case "while":							// primeros
				match("while");
				try {
					match("(");
					expresion();
					match(")");
					sentencia();
				} catch (ExcepcionSintactico e) {
					modoPanicoBloque(panicoLinea, panicoColumna);
				}
				break;
			case "{":								// primeros
				bloque();
				break;
			case "return":							// primeros
				match("return");
				try {
					retorno();
					match(";");
				} catch (ExcepcionSintactico e) {
					modoPanicoBloque(panicoLinea, panicoColumna);
				}
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico. Se espera la declaración de una sentencia o }.\nEsperado: ;, idMetVar, (, boolean, char, int, String, idClase, if, while, {, return, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this o }\nEncontrado: " + tokenActual.getNombre());
		}
	}
	
	private void modoPanicoBloque(int nroLineaComienzo, int nroColumnaComienzo) throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode {
		int nroLineaFin = ultimaLineaAnalizada;
		int nroColumnaFin = ultimaColumnaAnalizada;
		modoPanico = true;
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
			System.out.println("[" + nroLineaComienzo + "]: sentencia sintácticamente inválida");
			throw new ExcepcionPanicMode("No se puede recuperar de la última sentencia sintácticamente inválida.");
		} else {
			System.out.println("[" + nroLineaComienzo + ":" + nroColumnaComienzo + " - " + nroLineaFin + ":" + nroColumnaFin + "]: sentencia sintácticamente inválida.");
			switch (tokenActual.getNombre()) {
				case "}":
					break;
				case "else":
					if (ifPanico)
						rSentenciaIf();
					else
						throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico. Se espera la declaración de una sentencia o }.\nEsperado: ;, idMetVar, (, boolean, char, int, String, idClase, if, while, {, return, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this o }\nEncontrado: " + tokenActual.getNombre());
					break;
				default:
					sentencias();
					break;
			}
		}
	}
	
	private void ladoIzquierdo() throws ExcepcionLexico, ExcepcionSintactico {
		match("idMetVar");			// primeros
		rLlamadaoIdEncadenado();
		rEncadenado();
	}
	
	private void ladoDerechoIdMetVar() throws ExcepcionLexico, ExcepcionSintactico {		
		switch (tokenActual.getNombre()) {
			case "=":					// primeros
				decAsig();
				break;
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
				desparentizada();
				break;
			case ";":					// siguientes
				break;				
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico en sentencia simple desparentizada.\nEsperado: =, &&, ||, *, /, %, +, -, ==, !=, >, >=, <, <=, o ;\nEncontrado: " + tokenActual.getNombre());
		}
	}
	
	private void sentenciaSimple() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "(":				// primeros
				match("(");
				expresion();
				match(")");
				break;
			case "+":				// primeros
			case "-":				// primeros
			case "!":				// primeros
				opUn();
				expUn();
				desparentizada();
				break;
			case "null":			// primeros
			case "true":			// primeros
			case "false":			// primeros
			case "intLiteral":		// primeros
			case "charLiteral":		// primeros
			case "stringLiteral":	// primeros
				literal();
				desparentizada();
				break;
			case "this":			// primeros
				accesoThis();
				rEncadenado();
				desparentizada();
				break;
			case "new":				// primeros
				llamadaConstructor();
				rEncadenado();
				desparentizada();
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico sentencia simple");
			}
	}
	
	private void listaDecVars() throws ExcepcionLexico, ExcepcionSintactico {
		match("idMetVar");	// primeros
		rListaDecVars();
	}

	private void rListaDecVars() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case ",":				// primeros
				match(",");
				listaDecVars();
				break;
			case ";":				// siguientes
			case "=":				// siguientes
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico en la declaración/asignación de variables.\nEsperado: ;, = o ,\nEncontrado: " + tokenActual.getNombre());
			}
	}
	
	private void ladoDerechoIdClase() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {	// primeros
			case "idMetVar":
				listaDecVars();
				decAsig();
				break;
			case ".":						// primeros
				match(".");
				llamadaMetodo();
				rEncadenado();
				desparentizada();
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico en sentencia comenzada con idClase.\nEsperado: . o idMetVar\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private void rSentenciaIf() throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode {
		if (tokenActual.getNombre().equals("else")) {	// primeros
			match("else");
			sentencia();
		} else { }
	}
	
	private void retorno() throws ExcepcionLexico, ExcepcionSintactico {
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
				expresion();
				break;
			case ";":				// siguientes
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico en expresión de retorno.\nEsperado: +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this, new, (, idMetVar, idClase o ;\nEncontrado: " + tokenActual.getNombre());
		}
	}
	
	private void desparentizada() throws ExcepcionLexico, ExcepcionSintactico {
		switch(tokenActual.getNombre()) {
			case "&&":					// primeros
				and();
				desparentizada();
				break;
			case "||":					// primeros
				or();
				desparentizada();
				break;
			case "*":					// primeros
			case "/":					// primeros
			case "%":					// primeros
				mul();
				desparentizada();
				break;
			case "+":					// primeros
			case "-":					// primeros
				ad();
				desparentizada();
				break;
			case "==":					// primeros
			case "!=":					// primeros
				ig();
				desparentizada();
				break;
			case ">":					// primeros
			case ">=":					// primeros
			case "<":					// primeros
			case "<=":					// primeros
				rExpComp();
				desparentizada();
				break;
			case ";":					// siguientes
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico en sentencia simple desparentizada.\nEsperado: =, &&, ||, *, /, %, +, -, ==, !=, >, >=, <, <=, o ;\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private void expresion() throws ExcepcionLexico, ExcepcionSintactico {
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
				expOr();
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico en expresión.\nEsperado: +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, (, this, idMetVar, idClase o new\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private void expOr() throws ExcepcionLexico, ExcepcionSintactico {
		expAnd();
		or();
	}

	private void or() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("||")) {	// primeros
			match("||");
			expAnd();
			or();
		} else { }
	}

	private void expAnd() throws ExcepcionLexico, ExcepcionSintactico {
		expIg();
		and();
	}

	private void and() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("&&")) {	// primeros
			match("&&");
			expIg();
			and();
		} else { }
	}

	private void expIg() throws ExcepcionLexico, ExcepcionSintactico {
		expComp();
		ig();
	}

	private void ig() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "==":		// primeros
			case "!=":		// primeros
				opIg();
				expComp();
				ig();
				break;
		}
	}

	private void expComp() throws ExcepcionLexico, ExcepcionSintactico {
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
				expAd();
				rExpComp();
				break;
		}
	}

	private void rExpComp() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "<":		// primeros
			case "<=":		// primeros
			case ">":		// primeros
			case ">=":		// primeros
				opComp();
				expAd();
				break;
		}
	}

	private void expAd() throws ExcepcionLexico, ExcepcionSintactico {
		expMul();
		ad();	
	}

	private void ad() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "+":		// primeros
			case "-":		// primeros
				opAd();
				expMul();
				ad();
				break;
			}
	}

	private void expMul() throws ExcepcionLexico, ExcepcionSintactico {
		expUn();
		mul();
	}

	private void mul() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "*":		// primeros
			case "/":		// primeros
			case "%":		// primeros
				opMul();
				expUn();
				mul();
				break;
			case ";":
			case "":
				break;
		}
	}
	
	private void expUn() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "+":				// primeros
			case "-":				// primeros
			case "!":				// primeros
				opUn();
				expUn();
				break;
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
				operando();
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico en expresión unaria.\nEsperado: +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, (, this, idMetVar, idClase o new\nEncontrado: " + tokenActual.getNombre());
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

	private void operando() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "null":			// primeros
			case "true":			// primeros
			case "false":			// primeros
			case "intLiteral":		// primeros
			case "charLiteral":		// primeros
			case "stringLiteral":	// primeros
				literal();
				break;
			case "(":				// primeros
			case "this":			// primeros
			case "idMetVar":		// primeros
			case "idClase":			// primeros
			case "new":				// primeros
				primario();
				rEncadenado();
				break;
		}
	}
	
	private void literal() throws ExcepcionLexico, ExcepcionSintactico {
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
	}

	private void primario() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "(":							// primeros
				match("(");
				expresion();
				match(")");
				break;
			case "this":						// primeros
				accesoThis();
				break;
			case "idMetVar":					// primeros
				match("idMetVar");
				rLlamadaoIdEncadenado();
				break;
			case "idClase":						// primeros
				llamadaEstatica();
				break;
			case "new":							// primeros
				llamadaConstructor();
				break;
		}
	}
	
	private void accesoThis() throws ExcepcionLexico, ExcepcionSintactico {
		match("this");	// primeros
	}
	
	private void llamadaEstatica() throws ExcepcionLexico, ExcepcionSintactico {
		match("idClase");	// primeros
		match(".");
		llamadaMetodo();
	}

	private void llamadaConstructor() throws ExcepcionLexico, ExcepcionSintactico {
		match("new");		// primeros
		match("idClase");
		argsActuales();
	}

	private void llamadaMetodo() throws ExcepcionLexico, ExcepcionSintactico {
		match("idMetVar");	// primeros
		argsActuales();
	}
	
	private void encadenado() throws ExcepcionLexico, ExcepcionSintactico {
		llamadaoIdEncadenado();
		rEncadenado();
	}

	private void llamadaoIdEncadenado() throws ExcepcionLexico, ExcepcionSintactico {
		match(".");					// primeros
		match("idMetVar");
		rLlamadaoIdEncadenado();
	}

	private void rLlamadaoIdEncadenado() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("(")) {	// primeros
			argsActuales();
		} else { }
	}
	
	private void rEncadenado() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals(".")) {	// primeros
			encadenado();
		} else { }
	}

	private void argsActuales() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("(")) {	// primeros
			match("(");
			listaExpresiones();
			match(")");
		} else {
			throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico en la declaración de argumentos actuales.\nEsperado: (\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private void listaExpresiones() throws ExcepcionLexico, ExcepcionSintactico {
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
				listaExps();
			case ")":				// siguientes
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico en lista de expresiones.\nEsperado: (, idMetVar, idClase, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this o )\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private void listaExps() throws ExcepcionLexico, ExcepcionSintactico {
		expresion();
		rListaExps();
	}

	private void rListaExps() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case ",":			// primeros
				match(",");
				listaExps();
				break;
			case ")":			// siguientes
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sintáctico en lista de expresiones.\nEsperado: (, idMetVar, idClase, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this o )\nEncontrado: " + tokenActual.getNombre());
		}
	}
}