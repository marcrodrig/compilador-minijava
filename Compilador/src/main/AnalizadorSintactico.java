package main;

import java.io.FileNotFoundException;

public class AnalizadorSintactico {

	private AnalizadorLexico analizadorLexico;
	private Token tokenActual;
	private boolean modoPanico, ifPanico;
	
	public AnalizadorSintactico(String archivoEntrada) throws FileNotFoundException, ExcepcionLexico {
		analizadorLexico = new AnalizadorLexico(archivoEntrada);
		tokenActual = analizadorLexico.getToken();
		modoPanico = false;
		ifPanico = false;
	}

	private void match(String nombre) throws ExcepcionLexico, ExcepcionSintactico {
		if (nombre.equals(tokenActual.getNombre()))
			tokenActual = analizadorLexico.getToken();
		else			
			throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico.\nEsperado: " + nombre + "\nEncontrado: " + tokenActual.getNombre());
	}

	public boolean start() throws ExcepcionLexico, ExcepcionSintactico {
		inicial();
		match("EOF");
		return modoPanico;
	}
	
	public void inicial() throws ExcepcionLexico, ExcepcionSintactico {
		clase();
		otroInicial();
	}

	private void otroInicial() throws  ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("class")) {			// primeros
			clase();
			otroInicial();
		} else if (tokenActual.getNombre().equals("EOF")) {		// siguientes
		} else
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en la declaración de una clase.\nEsperado: class\nEncontrado: " + tokenActual.getNombre());
	}
	
	private void clase() throws ExcepcionLexico, ExcepcionSintactico {
		match("class");		// primeros
		match("idClase");
		herencia();
		match("{");
		miembros();
		match("}");
	}

	private void herencia() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("extends")) {	// primeros
			match("extends");
			try {
			match("idClase");
			} catch (ExcepcionSintactico e) {
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en la declaración de herencia de una clase.\nEsperado: idClase\nEncontrado: " + tokenActual.getNombre());
			}
		} else 
			if (tokenActual.getNombre().equals("{")) {		// siguientes
			} else
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en la declaración de una clase.\nEsperado: extends o {\nEncontrado: " + tokenActual.getNombre());
	}
	
	private void miembros() throws ExcepcionSintactico, ExcepcionLexico {
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
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico. Se espera la declaración de un atributo, constructor, método o }.\nEsperado: public, protected, private, idClase, static, dynamic o }\nEncontrado: " + tokenActual.getNombre());		}
	}
	
	private void miembro() throws ExcepcionLexico, ExcepcionSintactico {
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

	private void atributo() throws ExcepcionLexico, ExcepcionSintactico {
		int panicoLinea = tokenActual.getNroLinea();
		visibilidad();
		tipo();
		listaDecAtrs();
		decAsig();
		modoPanicoAtributo(panicoLinea);
	}
	
	private void modoPanicoAtributo(int nroLinea) throws ExcepcionLexico, ExcepcionSintactico {
		try {
			match(";");
		} catch (ExcepcionSintactico e) {
			switch (tokenActual.getNombre()) {
				case "}":
					modoPanico = true;
					System.out.println("[" + nroLinea + "]: declaración/asignación de atributos con ; faltante");
					break;
				case "public":
				case "protected":
				case "private":
				case "idClase":
				case "static":
				case "dynamic":
					modoPanico = true;
					System.out.println("[" + nroLinea + "]: declaración/asignación de atributos con ; faltante");
					miembro();
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

	private void tipo() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "boolean":			// primeros
			case "char":			// primeros
			case "int":				// primeros
			case "String":			// primeros
				tipoPrimitivo();
				break;
			case "idClase":			// primeros
				match("idClase");
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en tipo.\nEsperado: idClase, boolean, char, int o String\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private void tipoPrimitivo() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "boolean":			// primeros
				match("boolean");
				break;
			case "char":			// primeros
				match("char");
				break;
			case "int":				// primeros
				match("int");
				break;
			case "String":			// primeros
				match("String");
				break;
		}
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
			case "public":			// modo pánico
			case "protected":		// modo pánico
			case "private":			// modo pánico
			case "idClase":			// modo pánico
			case "static":			// modo pánico
			case "dynamic":			// modo pánico
			case "}":				// modo pánico
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en la declaración/asignación de atributos.\nEsperado: ; o =\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private void decAsig() throws ExcepcionLexico, ExcepcionSintactico {	
		switch (tokenActual.getNombre()) {
			case "=":				// primeros
				match("=");
				expresion();
				break;
			case ";":				// siguientes
			case "idMetVar":		// modo pánico
			case "+":				// modo pánico
			case "-":				// modo pánico
			case "!":				// modo pánico
			case "null":			// modo pánico
			case "true":			// modo pánico
			case "false":			// modo pánico
			case "intLiteral":		// modo pánico
			case "charLiteral":		// modo pánico
			case "stringLiteral":	// modo pánico
			case "this":			// modo pánico
			case "new":				// modo pánico
			case "(":				// modo pánico
			case "boolean":			// modo pánico
			case "char":			// modo pánico
			case "int":				// modo pánico
			case "String":			// modo pánico
			case "idClase":			// modo pánico
			case "if":				// modo pánico
			case "while":			// modo pánico
			case "{":				// modo pánico
			case "return":			// modo pánico
			case "}":				// modo pánico
			case "else":			// modo pánico
			case "public":			// modo pánico
			case "protected":		// modo pánico
			case "private":			// modo pánico
			case "static":			// modo pánico
			case "dynamic":			// modo pánico
				break;
			default:
					/** chequear si se da **/
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico.\nEsperado: = o ;\nEncontrado: " + tokenActual.getNombre());
		}
	}
	
	private void metodo() throws ExcepcionLexico, ExcepcionSintactico {
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
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en método.\nEsperado: final o tipo de método\nEncontrado: " + tokenActual.getNombre());
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
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en tipo método.\nEsperado: idClase, boolean, char, int, String o void\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private void argsFormales() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("(")) {	// primeros
			match("(");
			argumentos();
			match(")");
		} else {
			throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en la declaración de argumentos formales.\nEsperado: (\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private void argumentos() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "boolean":				// primeros
			case "char":				// primeros
			case "int":					// primeros
			case "String":				// primeros
			case "idClase":				// primeros
				listaArgsFormales();
				break;
			case ")":					// siguientes
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en argumentos formales.\nEsperado: ) o tipo\nEncontrado: " + tokenActual.getNombre());
			}
	}

	private void listaArgsFormales() throws ExcepcionLexico, ExcepcionSintactico {
		argFormal();
		rArgFormal();
	}

	private void argFormal() throws ExcepcionLexico, ExcepcionSintactico {
		tipo();
		match("idMetVar");
	}

	private void rArgFormal() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case ",":					// primeros
				match(",");
				listaArgsFormales();
				break;
			case ")":					// siguientes
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en los argumentos formales.\nEsperado: ) o ,\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private void ctor() throws ExcepcionLexico, ExcepcionSintactico {
		match("idClase");	// primeros
		argsFormales();
		bloque();
	}

	private void bloque() throws ExcepcionLexico, ExcepcionSintactico {
		match("{");		// primeros
		sentencias();
		match("}");
	}

	private void sentencias() throws ExcepcionLexico, ExcepcionSintactico {
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
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico. Se espera la declaración de una sentencia o }.\nEsperado: idMetVar, (, boolean, char, int, String, idClase, if, while, {, return, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this, } o ;\nEncontrado: " + tokenActual.getNombre());
		}		
	}

	private void sentencia() throws ExcepcionLexico, ExcepcionSintactico {
		int panicoLinea = tokenActual.getNroLinea();
		switch (tokenActual.getNombre()) {
			case ";":								// primeros
				match(";");
				break;
			case "idMetVar":						// primeros
				ladoIzquierdo();
				ladoDerechoIdMetVar();
				modoPanicoBloque(panicoLinea);
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
				sentenciaSimple();
				modoPanicoBloque(panicoLinea);
				break;
			case "boolean":							// primeros
			case "char":							// primeros
			case "int":								// primeros
			case "String":							// primeros
				tipoPrimitivo();
				listaDecVars();
				decAsig();
				modoPanicoBloque(panicoLinea);
				break;
			case "idClase":							// primeros
				match("idClase");
				ladoDerechoIdClase();
				modoPanicoBloque(panicoLinea);
				break;
			case "if":								// primeros
				match("if");
				match("(");
				expresion();
				match(")");
				ifPanico = true;
				sentencia();
				ifPanico = false;
				rSentenciaIf();
				break;
			case "while":							// primeros
				match("while");
				match("(");
				expresion();
				match(")");
				sentencia();
				break;
			case "{":								// primeros
				bloque();
				break;
			case "return":							// primeros
				match("return");
				retorno();
				modoPanicoBloque(panicoLinea);
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico. Se espera la declaración de una sentencia o }.\nEsperado: ;, idMetVar, (, boolean, char, int, String, idClase, if, while, {, return, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this o }\nEncontrado: " + tokenActual.getNombre());
		}
	}
	
	private void modoPanicoBloque(int nroLinea) throws ExcepcionLexico, ExcepcionSintactico {
		try {
			match(";");
		} catch (ExcepcionSintactico e) {
			switch (tokenActual.getNombre()) {
				case "}":
					modoPanico = true;
					System.out.println("[" + nroLinea + "]: sentencia con ; faltante");
					break;
				case "idMetVar":
				case "+":
				case "-":
				case "!":
				case "null":
				case "true":
				case "false":
				case "intLiteral":
				case "charLiteral":
				case "stringLiteral":
				case "this":
				case "new":
				case "(":
				case "boolean":
				case "char":
				case "int":
				case "String":
				case "idClase":
				case "if":
				case "while":
				case "{":
				case "return":
					modoPanico = true;
					System.out.println("[" + nroLinea + "]: sentencia con ; faltante");
					sentencia();
					break;
				case "else":
					if (ifPanico) {
						modoPanico = true;
						System.out.println("[" + nroLinea + "]: sentencia con ; faltante");
						rSentenciaIf();
					} else {
						modoPanico = true;
						System.out.println("[" + nroLinea + "]: sentencia con ; faltante");
						throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico. Se espera la declaración de una sentencia o }.\nEsperado: ;, idMetVar, (, boolean, char, int, String, idClase, if, while, {, return, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this o }\nEncontrado: " + tokenActual.getNombre());
					}
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
			case "idMetVar":			// modo pánico
			case "!":					// modo pánico
			case "null":				// modo pánico
			case "true":				// modo pánico
			case "false":				// modo pánico
			case "intLiteral":			// modo pánico
			case "charLiteral":			// modo pánico
			case "stringLiteral":		// modo pánico
			case "this":				// modo pánico
			case "new":					// modo pánico
			case "(":					// modo pánico
			case "boolean":				// modo pánico
			case "char":				// modo pánico
			case "int":					// modo pánico
			case "String":				// modo pánico
			case "idClase":				// modo pánico
			case "if":					// modo pánico
			case "while":				// modo pánico
			case "{":					// modo pánico
			case "return":				// modo pánico
			case "}":					// modo pánico
			case "else":				// modo pánico
				break;				
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en sentencia simple desparentizada.\nEsperado: =, &&, ||, *, /, %, +, -, ==, !=, >, >=, <, <=, o ;\nEncontrado: " + tokenActual.getNombre());
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
			case "null":			// primeros
			case "true":			// primeros
			case "false":			// primeros
			case "intLiteral":		// primeros
			case "charLiteral":		// primeros
			case "stringLiteral":	// primeros
			case "this":			// primeros
			case "new":				// primeros
									// Queda afuera idMetVar (<Sentencia> ::= <LadoIzquierdo> <LadoDerechoIdMetVar> ;)
									// Queda afuera idCLase  (<Sentencia> ::= idClase <LadoDerechoIdClase> ;)
				expresion();
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico sentencia simple");
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
			case "idMetVar":		// modo pánico
			case "+":				// modo pánico
			case "-":				// modo pánico
			case "!":				// modo pánico
			case "null":			// modo pánico
			case "true":			// modo pánico
			case "false":			// modo pánico
			case "intLiteral":		// modo pánico
			case "charLiteral":		// modo pánico
			case "stringLiteral":	// modo pánico
			case "this":			// modo pánico
			case "new":				// modo pánico
			case "(":				// modo pánico
			case "boolean":			// modo pánico
			case "char":			// modo pánico
			case "int":				// modo pánico
			case "String":			// modo pánico
			case "idClase":			// modo pánico
			case "if":				// modo pánico
			case "while":			// modo pánico
			case "{":				// modo pánico
			case "}":				// modo pánico
			case "return":			// modo pánico
			case "else":			// modo pánico
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en la declaración/asignación de variables.\nEsperado: ;, = o ,\nEncontrado: " + tokenActual.getNombre());
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
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en sentencia comenzada con idClase.\nEsperado: . o idMetVar\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private void rSentenciaIf() throws ExcepcionLexico, ExcepcionSintactico {
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
			case "boolean":			// modo pánico
			case "char":			// modo pánico
			case "int":				// modo pánico
			case "String":			// modo pánico
			case "if":				// modo pánico
			case "while":			// modo pánico
			case "{":				// modo pánico
			case "return":			// modo pánico
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en expresión de retorno.\nEsperado: +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this, new, (, idMetVar, idClase o ;\nEncontrado: " + tokenActual.getNombre());
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
			case "idMetVar":			// modo pánico
			case "!":					// modo pánico
			case "null":				// modo pánico
			case "true":				// modo pánico
			case "false":				// modo pánico
			case "intLiteral":			// modo pánico
			case "charLiteral":			// modo pánico
			case "stringLiteral":		// modo pánico
			case "this":				// modo pánico
			case "new":					// modo pánico
			case "(":					// modo pánico
			case "boolean":				// modo pánico
			case "char":				// modo pánico
			case "int":					// modo pánico
			case "String":				// modo pánico
			case "idClase":				// modo pánico
			case "if":					// modo pánico
			case "while":				// modo pánico
			case "{":					// modo pánico
			case "return":				// modo pánico
			case "}":					// modo pánico
			case "else":				// modo pánico
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en sentencia simple desparentizada.\nEsperado: =, &&, ||, *, /, %, +, -, ==, !=, >, >=, <, <=, o ;\nEncontrado: " + tokenActual.getNombre());
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
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en expresión.\nEsperado: +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, (, this, idMetVar, idClase o new\nEncontrado: " + tokenActual.getNombre());
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
			// excepción?
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
		// excepción?
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
		// excepción?
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
		}
		// excepción
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
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en expresión unaria.\nEsperado: +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, (, this, idMetVar, idClase o new\nEncontrado: " + tokenActual.getNombre());
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
		// excepción?
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
		// excepción
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
		// excepción?
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
		// excepción?
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
		// excepción?
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
		// excepción?
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
			throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en la declaración de argumentos actuales.\nEsperado: (\nEncontrado: " + tokenActual.getNombre());
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
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en lista de expresiones.\nEsperado: (, idMetVar, idClase, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this o )\nEncontrado: " + tokenActual.getNombre());
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
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en lista de expresiones.\nEsperado: (, idMetVar, idClase, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this o )\nEncontrado: " + tokenActual.getNombre());
		}
	}
}