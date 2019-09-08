package main;

import java.io.FileNotFoundException;



public class AnalizadorSintactico {

	private AnalizadorLexico analizadorLexico;
	private Token tokenActual;
	
	public AnalizadorSintactico(String archivoEntrada) throws FileNotFoundException, ExcepcionLexico {
		analizadorLexico = new AnalizadorLexico(archivoEntrada);
		tokenActual = analizadorLexico.getToken();
	}

	private void match(String nombre) throws ExcepcionLexico, ExcepcionSintactico {
		if (nombre.equals(tokenActual.getNombre()))
			tokenActual = analizadorLexico.getToken();
		else
			throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico.\nEsperado: " + nombre + "\nEncontrado: " + tokenActual.getNombre());
	}

	public void inicial() throws ExcepcionLexico, ExcepcionSintactico {
		clase();
		otroInicial();
		match("EOF");
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
		/*	default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico. Se espera la declaración de un atributo, constructor, método o }.\nEsperado: public, protected, private, idClase, static, dynamic o }\nEncontrado: " + tokenActual.getNombre());*/
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
	/*		default:
				throw new ExcepcionSintactico("Error sintáctico: se esperaba la forma de método (static o dynamic) " + tokenActual.getNroLinea());
			}*/
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

	private void atributo() throws ExcepcionLexico, ExcepcionSintactico {
		visibilidad();
		tipo();
		listaDecAtrs();
		asignacion();
		match(";");
	}

	private void rSentenciaLadoIzquierdo() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
		case "=":
			asignacion();
			break;
		case "==":
		case "!=":
			opIg();
			expresion();
			break;
		case "+":
		case "-":
			opAd();
			expresion();
			break;
		case ">":
		case ">=":
		case "<":
		case "<=":
			opComp();
			expresion();
			break;
		case ";":
			break;
		default:
			throw new ExcepcionSintactico("Error sintáctico en rSentenciaLadoIzquierda " + tokenActual.getNroLinea());
		}
	}

	private void asignacion() throws ExcepcionLexico, ExcepcionSintactico {	
	switch (tokenActual.getNombre()) {
		case "=":
		match("=");
		expresion();
		break;
		case ";":
			break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico. Se espera la declaración de una asignación o ;.\nEsperado: = o ;\nEncontrado: " + tokenActual.getNombre());
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
	/* 
	 * 	NO HACE FALTA CREO
	
			throw new ExcepcionSintactico("Error sintáctico visibildad " + tokenActual.getNroLinea());
	*/
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
				/* NO HACE FALTA CREO
			default:
				throw new ExcepcionSintactico("Error sintáctico tipoPrimitivo " + tokenActual.getNroLinea());
			}*/
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
			throw new ExcepcionSintactico("Error sintáctico rlistaDecAtrs creo que esperaba ; " + tokenActual.getNroLinea());
		}
	}

	private void ctor() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
		case "idClase":
			match("idClase");
			argsFormales();
			bloque();
			break;
		default:
			throw new ExcepcionSintactico("Error sintáctico ctor " + tokenActual.getNroLinea());
		}
	}

	private void argsFormales() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("(")) {
			match("(");
			argumentos();
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
				match(")");
				break;
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico en la declaración de tipo de argumentos formales.\nEsperado: idClase, boolean, char, int, String\nEncontrado: " + tokenActual.getNombre());
			}
	}

	private void listaArgsFormales() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "boolean":
			case "char":
			case "int":
			case "String":
			case "idClase":
				argFormal();
				rArgFormal();
				break;
			default:
				throw new ExcepcionSintactico("Error sintáctico listaArgsFormales " + tokenActual.getNroLinea());
		}
	}

	private void argFormal() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
		case "boolean":
		case "char":
		case "int":
		case "String":
		case "idClase":
			tipo();
			match("idMetVar");
			break;
		default:
			throw new ExcepcionSintactico("Error sintáctico argFormal " + tokenActual.getNroLinea());
		}
	}

	private void rArgFormal() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
		case ",":
			match(",");
			listaArgsFormales();
			break;
		case ")":
			match(")");
			break;
		default:
			throw new ExcepcionSintactico("Error sintáctico rArgFormal " + tokenActual.getNroLinea());
		}
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
			case "new": //// seguir acaaaAAAAAAAA
			case "!": // SEGUIIIR
				otrasSentencias();
				break;
			case "}":
				break;
			default:
				throw new ExcepcionSintactico("Error sintáctico sentencias " + tokenActual.getNroLinea());
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
		case "new": // seguir acaaaa
		case "!": // SEGUIIIR
			sentencia();
			otrasSentencias();
			break;
		case "}":
			break;
		default:
			throw new ExcepcionSintactico("Error sintáctico otrasSentencias " + tokenActual.getNroLinea());
		}
	}

	private void sentencia() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
		case ";":
			match(";");
			break;
		case "idMetVar":
			ladoIzquierdo();
			rSentenciaLadoIzquierdo();
			match(";");
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
			match(";");
			break;
		case "boolean":
		case "char":
		case "int":
		case "String":
			tipoPrimitivo();
			listaDecVars();
			asignacion();
			match(";");
			break;
		case "idClase":
			match("idClase");
			rClaseSentencia();
			match(";");
			break;
		case "if":
			match("if");
			match("(");
			expresion();
			match(")");
			sentencia();
			rSentenciaIf();
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
			match(";");
			break;
		default:
			throw new ExcepcionSintactico("Error sintáctico sentencia " + tokenActual.getNroLinea());
		}
	}

	private void rClaseSentencia() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
		case "idMetVar":
			listaDecVars();
			asignacion();
			break;
		case ".":
			match(".");
			llamadaMetodo();
			encadenadoExpSen();
			break;
		default:
			throw new ExcepcionSintactico("Error sintáctico rClaseSentencia " + tokenActual.getNroLinea());
		}
	}

	private void llamadaMetodo() throws ExcepcionLexico, ExcepcionSintactico {
		match("idMetVar");
		argsActuales();
	}

	private void retorno() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals(";"))
		{ }
		else { // CHEQUEAR
			expresion();
		}
	}

	private void rSentenciaIf() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("else")) {
			match("else");
			sentencia();
		} else { }
	}

	private void listaDecVars() throws ExcepcionLexico, ExcepcionSintactico {
		match("idMetVar");
		rListaDecVars();
	}

	private void rListaDecVars() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
		case ",":
			match(",");
			listaDecVars();
			break;
		case ";": // siguientes
		case "asignacion":
			break;
		default:
			throw new ExcepcionSintactico("Error sintáctico rListaDecVars " + tokenActual.getNroLinea());
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
		case "idMetVar":
		case "idClase":
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

	private void ladoIzquierdo() throws ExcepcionLexico, ExcepcionSintactico {
		match("idMetVar");
		//ver si ahrehar para THIS
		rMetVar();
		encadenadoExpSen();
	}

	private void expresion() throws ExcepcionLexico, ExcepcionSintactico {
		expOr();
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
			// ??
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
		default:
			throw new ExcepcionSintactico("Error sintáctico opIg " + tokenActual.getNroLinea());
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
			RExpComp();
			break;
		default:
			throw new ExcepcionSintactico("Error sintáctico expComp " + tokenActual.getNroLinea());
		}
	}

	private void RExpComp() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
		case "<":
		case "<=":
		case ">":
		case ">=":
			opComp();
			expAd();
			break;
		default:
			break; // ?
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
		default:
			break; // ?
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
		default:
			break; // ?
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
		default:
			break; //?

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
		default: { break; } ///?
		//throw new ExcepcionSintactico("Error sintáctico mul " + tokenActual.getNroLinea());
		//	break; //?

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
		default: 
			throw new ExcepcionSintactico("Error sintáctico opMul " + tokenActual.getNroLinea());
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
		case "idMetVar": /* chequear */
		case "idClase":
		case "new":
			operando();
			break;
		default:
			throw new ExcepcionSintactico("Error sintáctico expUn " + tokenActual.getNroLinea());
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
		default:
			throw new ExcepcionSintactico("Error sintáctico opUn " + tokenActual.getNroLinea());
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
		case "idMetVar": /* chequear */
		case "idClase":
		case "new":
			primario();
			encadenadoExpSen();
			break;
		}
	}

	private void encadenadoExpSen() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals(".")) {
			encadenado();
		} else { }
	}

	private void encadenado() throws ExcepcionLexico, ExcepcionSintactico {
		llamadoIdEncadenado();
		rEncadenado();
	}

	private void llamadoIdEncadenado() throws ExcepcionLexico, ExcepcionSintactico {
		match(".");
		match("idMetVar");
		rLlamadoIdEncadenado();
	}

	private void rLlamadoIdEncadenado() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("(")) {
			argsActuales();
		} else { }
	}

	private void rEncadenado() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals(".")) {
			encadenado();
		} else { } // ver siguientes??
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
		default:
			throw new ExcepcionSintactico("Error sintáctico literal " + tokenActual.getNroLinea());
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
			rMetVar();
			break;
		case "idClase":
			llamadaEstatica();
			break;
		case "new":
			llamadaConstructor();
			break;
		default:
			throw new ExcepcionSintactico("Error sintáctico primario " + tokenActual.getNroLinea());
		}
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

	private void accesoThis() throws ExcepcionLexico, ExcepcionSintactico {
		match("this");
	}

	private void rMetVar() throws ExcepcionLexico, ExcepcionSintactico {
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
			throw new ExcepcionSintactico("Error sintáctico argsActuales " + tokenActual.getNroLinea());
		}
	}

	private void listaExpresiones() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals(")"))
		{ } else {
			listaExps();
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
			throw new ExcepcionSintactico("Error sintáctico rListaExps " + tokenActual.getNroLinea());

		}
	}
}