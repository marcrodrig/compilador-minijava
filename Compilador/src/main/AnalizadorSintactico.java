package main;

import java.io.FileNotFoundException;

public class AnalizadorSintactico {

	private AnalizadorLexico analizadorLexico;
	private Token tokenActual;
	private boolean modoPanicoBloque, modoPanicoAtributo, ifPanico;
	private int panicoLinea;
	
	public AnalizadorSintactico(String archivoEntrada) throws FileNotFoundException, ExcepcionLexico {
		analizadorLexico = new AnalizadorLexico(archivoEntrada);
		tokenActual = analizadorLexico.getToken();
		modoPanicoBloque = false;
		modoPanicoAtributo = false;
		ifPanico = false;
	}

	private void match(String nombre) throws ExcepcionLexico, ExcepcionSintactico {
		if (nombre.equals(tokenActual.getNombre()))
			tokenActual = analizadorLexico.getToken();
		else			
			throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico.\nEsperado: " + nombre + "\nEncontrado: " + tokenActual.getNombre());
	}

	public boolean inicial() throws ExcepcionLexico, ExcepcionSintactico {
		clase();
		otroInicial();
		match("EOF");
		return modoPanicoBloque || modoPanicoAtributo;
	}

	private void otroInicial() throws  ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("class")) {
			clase();
			otroInicial();
		} else if (tokenActual.getNombre().equals("EOF")) { }
			else
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en la declaración de una clase.\nEsperado: class\nEncontrado: " + tokenActual.getNombre());
	}
	
	private void clase() throws ExcepcionLexico, ExcepcionSintactico {
		match("class");
		match("idClase");
		herencia();
		match("{");
		miembros();
		match("}");
	}

	private void herencia() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("extends")) {
			match("extends");
			try {
			match("idClase");
			} catch (ExcepcionSintactico e) {
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en la declaración de herencia de una clase.\nEsperado: idClase\nEncontrado: " + tokenActual.getNombre());
			}
		} else 
			if (tokenActual.getNombre().equals("{")) { }
			else
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en la declaración de una clase.\nEsperado: extends o {\nEncontrado: " + tokenActual.getNombre());
	}
	
	private void miembros() throws ExcepcionSintactico, ExcepcionLexico {
		switch (tokenActual.getNombre()) {
			case "public":
			case "protected":
			case "private":
			case "idClase":
			case "static":
			case "dynamic":
				miembro();
				otroMiembros();
				break;
			case "}":
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico. Se espera la declaración de un atributo, constructor, método o }.\nEsperado: public, protected, private, idClase, static, dynamic o }\nEncontrado: " + tokenActual.getNombre());		}
	}

	private void otroMiembros() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "public":
			case "protected":
			case "private":
			case "idClase":
			case "static":
			case "dynamic":
				miembro();
				otroMiembros();
				break;
			case "}":
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico. Se espera la declaración de un atributo, constructor, método o }.\nEsperado: public, protected, private, idClase, static, dynamic o }\nEncontrado: " + tokenActual.getNombre());
		}
	}
	
	private void miembro() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "public":
			case "protected":
			case "private":
				atributo();
				break;
			case "idClase":
				ctor();
				break;
			case "static":
			case "dynamic":
				metodo();
				break;
			case "}":
				break;
		}
	}

	private void atributo() throws ExcepcionLexico, ExcepcionSintactico {
		panicoLinea = tokenActual.getNroLinea();
		visibilidad();
		tipo();
		listaDecAtrs();
		asignacion();
	//	match(";");
		modoPanicoAtributo(panicoLinea);
	}
	
	private void modoPanicoAtributo(int nroLinea) throws ExcepcionLexico, ExcepcionSintactico {
		try {
			match(";");
		} catch (ExcepcionSintactico e) {
			switch (tokenActual.getNombre()) {
				case "}":
					modoPanicoAtributo = true;
					System.out.println("[" + nroLinea + "]: declaración/asignación de atributos con ; faltante");
					break;
				case "public":
				case "protected":
				case "private":
				case "idClase":
				case "static":
				case "dynamic":
					modoPanicoAtributo = true;
					System.out.println("[" + nroLinea + "]: declaración/asignación de atributos con ; faltante");
					miembro();
					break;
			}
		}	
	}
	
	private void visibilidad() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "public":
				match("public");
				break;
			case "protected":
				match("protected");
				break;
			case "private":
				match("private");
				break;
		}
	}

	private void tipo() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "boolean":
			case "char":
			case "int":
			case "String":
				tipoPrimitivo();
				break;
			case "idClase":
				match("idClase");
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en tipo.\nEsperado: idClase, boolean, char, int o String\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private void tipoPrimitivo() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "boolean":
				match("boolean");
				break;
			case "char":
				match("char");
				break;
			case "int":
				match("int");
				break;
			case "String":
				match("String");
				break;
		}
	}

	private void listaDecAtrs() throws ExcepcionLexico, ExcepcionSintactico {
		match("idMetVar");
		rlistaDecAtrs();
	}

	private void rlistaDecAtrs() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case ",":
				match(",");
				listaDecAtrs();
				break;
			case ";":
			case "=":
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en la declaración/asignación de atributos.\nEsperado: ;, = o ,\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private void asignacion() throws ExcepcionLexico, ExcepcionSintactico {	
		switch (tokenActual.getNombre()) {
			case "=":
				match("=");
				expresion();
				break;
			case ";":
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
			case "}":
			//case "else":
				break;
			default:
					/** chequear si se da **/
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico. Se espera la declaración de una asignación.\nEsperado: = o ;\nEncontrado: " + tokenActual.getNombre());
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
			case "static":
				match("static");
				break;
			case "dynamic":
				match("dynamic");
				break;
		}
	}

	private void fnl() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
		case "final":
			match("final");
			break;
		case "void":
		case "idClase":
		case "boolean":
		case "char":
		case "int":
		case "String":
			break;
		default:
			throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en tipo de método.\nEsperado: void, idClase, boolean, char, int o String\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private void tipoMetodo() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "void":
				match("void");
				break;
			case "idClase":
			case "boolean":
			case "char":
			case "int":
			case "String":
				tipo();
				break;
		}
	}

	private void argsFormales() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("(")) {
			match("(");
			argumentos();
			match(")");
		} else {
			throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en la declaración de argumentos formales.\nEsperado: (\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private void argumentos() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "boolean":
			case "char":
			case "int":
			case "String":
			case "idClase":
				listaArgsFormales();
				break;
			case ")":
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en la declaración de tipo de argumentos formales.\nEsperado: idClase, boolean, char, int, String\nEncontrado: " + tokenActual.getNombre());
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
			case ",":
				match(",");
				listaArgsFormales();
				break;
			case ")":
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en los argumentos formales.\nEsperado: ) o ,\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private void ctor() throws ExcepcionLexico, ExcepcionSintactico {
		match("idClase");
		argsFormales();
		bloque();
	}

	private void bloque() throws ExcepcionLexico, ExcepcionSintactico {
		match("{");
		sentencias();
		match("}");
	}

	private void sentencias() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case ";":
			case "idMetVar":
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
			case "new":
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
				otrasSentencias();
				break;
			case "}":
			//case "else":
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico. Se espera la declaración de una sentencia o }.\nEsperado: idMetVar, (, boolean, char, int, String, idClase, if, while, {, return, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this o }\nEncontrado: " + tokenActual.getNombre());
		}		
	}

	private void otrasSentencias() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case ";":
			case "idMetVar":
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
			case "new":
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
				sentencia();
				otrasSentencias();
				break;
			case "}":
			//case "else":
				break;
				/** CHEQUEAR SI SE DA ESTA EXCEPCION */
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico. Se espera la declaración de una sentencia o }.\nEsperado: idMetVar, (, boolean, char, int, String, idClase, if, while, {, return, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this o }\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private void sentencia() throws ExcepcionLexico, ExcepcionSintactico {
		panicoLinea = tokenActual.getNroLinea();
		switch (tokenActual.getNombre()) {
			case ";":
				match(";");
				break;
			case "idMetVar":
				ladoIzquierdo();
				ladoDerechoIdMetVar(panicoLinea);
	//			match(";");
				modoPanicoBloque(panicoLinea);
				break;
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
				sentenciaSimple();
	//			match(";");
				if (tokenActual.getNombre().equals("else")) {
					modoPanicoBloque = true;
					System.out.println("[" + panicoLinea + "]: sentencia con ; faltante");	
				}
				modoPanicoBloque(panicoLinea);
				break;
			case "boolean":
			case "char":
			case "int":
			case "String":
				tipoPrimitivo();
				listaDecVars(panicoLinea);
				asignacion();
	//			match(";");
				modoPanicoBloque(panicoLinea);
				break;
			case "idClase":
				match("idClase");
				ladoDerechoIdClase(panicoLinea);
	//			match(";");
				modoPanicoBloque(panicoLinea);
				break;
			case "if":
				match("if");
				match("(");
				expresion();
				match(")");
				//sentencia();
				boolean panicoThen = modoPanicoIfThen();
				ifPanico = true;
				rSentenciaIf();
				ifPanico = false;
				break;
			case "while":
				match("while");
				match("(");
				expresion();
				match(")");
				sentencia();
				break;
			case "{":
				bloque();
				break;
			case "return":
				match("return");
				retorno();
	//			match(";");
				modoPanicoBloque(panicoLinea);
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico. Se espera la declaración de una sentencia.\nEsperado: ;, idMetVar, (, boolean, char, int, String, idClase, if, while, {, return, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral o this\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private boolean modoPanicoIfThen() {
		// TODO Auto-generated method stub
		return false;
	}

	private void modoPanicoBloque(int nroLinea) throws ExcepcionLexico, ExcepcionSintactico {
		try {
			match(";");
		} catch (ExcepcionSintactico e) {
			switch (tokenActual.getNombre()) {
				case "}":
					modoPanicoBloque = true;
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
					modoPanicoBloque = true;
					System.out.println("[" + nroLinea + "]: sentencia con ; faltante");
					sentencia();
					//if (ifPanico) ifPanico = false;
					break;
				/*case "else":
					if (ifPanico) {
						modoPanicoBloque = true;
						System.out.println("[" + nroLinea + "]: sentencia con ; faltante");
						rSentenciaIf();
					} else 
						throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico. Se espera la declaración de una sentencia o }.\nEsperado: idMetVar, (, boolean, char, int, String, idClase, if, while, {, return, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this o }\nEncontrado: " + tokenActual.getNombre());
					break;*/
			}
		}	
	}
	
	private void ladoIzquierdo() throws ExcepcionLexico, ExcepcionSintactico {
		match("idMetVar");
		rLlamadaoIdEncadenado();
		encadenadoExpSen();
	}
	
	private void ladoDerechoIdMetVar(int nroLinea) throws ExcepcionLexico, ExcepcionSintactico {		
		switch (tokenActual.getNombre()) {
			case "=":
				asignacion();
				break;
			case "&&":
			case "||":
			case "*":
			case "/":
			case "%":
			case "+":
			case "-":
			case "==":
			case "!=":
			case ">":
			case ">=":
			case "<":
			case "<=":
				rDesparentizada(nroLinea);
				break;
			case "else":
				System.out.println("[" + nroLinea + "]: sentencia con ; faltante");
				break;
			case ";":
			case "idMetVar":
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
			case "}":
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en sentencia simple desparentizada.\nEsperado: =, &&, ||, *, /, %, +, -, ==, !=, >, >=, <, <=, o ;\nEncontrado: " + tokenActual.getNombre());
		}
	}
	
	private void sentenciaSimple() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
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
				expresion();
				break;
			case "(":
				match("(");
				expresion();
				match(")");
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico sentencia simple");
			}
	}
	
	private void listaDecVars(int nroLinea) throws ExcepcionLexico, ExcepcionSintactico {
		match("idMetVar");
		rListaDecVars(nroLinea);
	}

	private void rListaDecVars(int nroLinea) throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case ",":
				match(",");
				listaDecVars(nroLinea);
				break;
			/*case "else":
				modoPanicoBloque = true;
				System.out.println("[" + nroLinea + "]: sentencia con ; faltante");
				break;*/
			case ";":
			case "=":
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
			case "}":
			case "return":
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en la declaración/asignación de variables.\nEsperado: ;, = o ,\nEncontrado: " + tokenActual.getNombre());
			}
	}
	
	private void ladoDerechoIdClase(int nroLinea) throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "idMetVar":
				listaDecVars(nroLinea);
				asignacion();
				break;
			case ".":
				match(".");
				llamadaMetodo();
				encadenadoExpSen();
				rDesparentizada(nroLinea);
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en sentencia comenzada con idClase.\nEsperado: . o idMetVar\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private void rSentenciaIf() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("else")) {
			match("else");
			sentencia();
		} else { }
	}
	
	private void retorno() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
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
			case "idMetVar":
			case "idClase":
				expresion();
				break;
			case ";":
			case "boolean":
			case "char":
			case "int":
			case "String":
			case "if":
			case "while":
			case "{":
			case "}":
			case "return":
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en expresión de retorno.\nEsperado: +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this, new, (, idMetVar, idClase o ;\nEncontrado: " + tokenActual.getNombre());
		}
	}
	
	private void encadenadoExpSen() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals(".")) {
			encadenado();
		} else { }
	}
	
	private void rDesparentizada(int nroLinea) throws ExcepcionLexico, ExcepcionSintactico {
		switch(tokenActual.getNombre()) {
			case "&&":
				and();
				rDesparentizada(nroLinea);
				break;
			case "||":
				or();
				rDesparentizada(nroLinea);
				break;
			case "*":
			case "/":
			case "%":
				mul();
				rDesparentizada(nroLinea);
				break;
			case "+":
			case "-":
				ad();
				rDesparentizada(nroLinea);
				break;
			case "==":
			case "!=":
				ig();
				rDesparentizada(nroLinea);
				break;
			case ">":
			case ">=":
			case "<":
			case "<=":
				rExpComp();
				rDesparentizada(nroLinea);
				break;
			/*case "else":
				System.out.println("[" + nroLinea + "]: sentencia con ; faltante");
				break;*/
			case ";":
			case "idMetVar":
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
			case "}":
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en sentencia simple desparentizada.\nEsperado: =, &&, ||, *, /, %, +, -, ==, !=, >, >=, <, <=, o ;\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private void expresion() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
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
			case "idMetVar":
			case "idClase":
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
		if (tokenActual.getNombre().equals("||")) {
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
		if (tokenActual.getNombre().equals("&&")) {
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
			case "==":
			case "!=":
				opIg();
				expComp();
				ig();
				break;
		}
	}

	private void expComp() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "+":
			case "-":
			case "!":
			case "null":
			case "true":
			case "false":
			case "intLiteral":
			case "charLiteral":
			case "stringLiteral":
			case "(":
			case "this":
			case "idMetVar":
			case "idClase":
			case "new":
				expAd();
				rExpComp();
				break;
		}
	}

	private void rExpComp() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "<":
			case "<=":
			case ">":
			case ">=":
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
			case "+":
			case "-":
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
			case "*":
			case "/":
			case "%":
				opMul();
				expUn();
				mul();
				break;
		}
	}
	
	private void expUn() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "+":
			case "-":
			case "!":
				opUn();
				expUn();
				break;
			case "null":
			case "true":
			case "false":
			case "intLiteral":
			case "charLiteral":
			case "stringLiteral":
			case "(":
			case "this":
			case "idMetVar":
			case "idClase":
			case "new":
				operando();
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en expresión unaria.\nEsperado: +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, (, this, idMetVar, idClase o new\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private void opIg() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "==":
				match("==");
				break;
			case "!=":
				match("!=");
				break;
		}
	}
	
	private void opComp() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "<":
				match("<");
				break;
			case "<=":
				match("<=");
				break;
			case ">":
				match(">");
				break;
			case ">=":
				match(">=");
				break;
		}
	}
	
	private void opAd() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "+":
				match("+");
				break;
			case "-":
				match("-");
				break;
		}
	}

	private void opUn() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "+":
				match("+");
				break;
			case "-":
				match("-");
				break;
			case "!":
				match("!");
				break;
		}
	}
	
	private void opMul() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "*":
				match("*");
				break;
			case "/":
				match("/");
				break;
			case "%":
				match("%");
				break;
		}
	}

	private void operando() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "null":
			case "true":
			case "false":
			case "intLiteral":
			case "charLiteral":
			case "stringLiteral":
				literal();
				break;
			case "(":
			case "this":
			case "idMetVar":
			case "idClase":
			case "new":
				primario();
				encadenadoExpSen();
				break;
		}
	}
	
	private void literal() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "null":
				match("null");
				break;
			case "true":
				match("true");
				break;
			case "false":
				match("false");
				break;
			case "intLiteral":
				match("intLiteral");
				break;
			case "charLiteral":
				match("charLiteral");
				break;
			case "stringLiteral":
				match("stringLiteral");
				break;
		}
	}

	private void primario() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "(":
				match("(");
				expresion();
				match(")");
				break;
			case "this":
				accesoThis();
				break;
			case "idMetVar":
				match("idMetVar");
				rLlamadaoIdEncadenado();
				break;
			case "idClase":
				llamadaEstatica();
				break;
			case "new":
				llamadaConstructor();
				break;
		}
	}
	
	private void accesoThis() throws ExcepcionLexico, ExcepcionSintactico {
		match("this");
	}
	
	private void llamadaEstatica() throws ExcepcionLexico, ExcepcionSintactico {
		match("idClase");
		match(".");
		llamadaMetodo();
	}

	private void llamadaConstructor() throws ExcepcionLexico, ExcepcionSintactico {
		match("new");
		match("idClase");
		argsActuales();
	}

	private void llamadaMetodo() throws ExcepcionLexico, ExcepcionSintactico {
		match("idMetVar");
		argsActuales();
	}
	
	private void encadenado() throws ExcepcionLexico, ExcepcionSintactico {
		llamadaoIdEncadenado();
		rEncadenado();
	}

	private void llamadaoIdEncadenado() throws ExcepcionLexico, ExcepcionSintactico {
		match(".");
		match("idMetVar");
		rLlamadaoIdEncadenado();
	}

	private void rEncadenado() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals(".")) {
			encadenado();
		} else { }
	}
	
	private void rLlamadaoIdEncadenado() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("(")) {
			argsActuales();
		} else { }
	}

	private void argsActuales() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("(")) {
			match("(");
			listaExpresiones();
			match(")");
		} else {
			throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en la declaración de argumentos actuales.\nEsperado: (\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private void listaExpresiones() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "idMetVar":
			case "(":
			case "idClase":
			case "new":
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
				listaExps();
			case ")":
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
		case ",":
			match(",");
			listaExps();
			break;
		case ")":
			break;
		default:
			throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en lista de expresiones.\nEsperado: (, idMetVar, idClase, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this o )\nEncontrado: " + tokenActual.getNombre());
		}
	}
}