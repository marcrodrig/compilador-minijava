package main;

import java.io.FileNotFoundException;

public class AnalizadorSintactico {

	private AnalizadorLexico analizadorLexico;
	private Token tokenActual;

	public AnalizadorSintactico(String archivoEntrada) throws FileNotFoundException, ExcepcionCaracterInvalido, ExcepcionFormatoCaracter, ExcepcionFormatoAnd, ExcepcionFormatoOr, ExcepcionFormatoComentarioMultilinea, ExcepcionFormatoString {
		analizadorLexico = new AnalizadorLexico(archivoEntrada);
		tokenActual = analizadorLexico.getToken();
	}

	private void match(String nombre) throws ExcepcionLexico, ExcepcionSintactico {
		if (nombre.equals(tokenActual.getNombre()))
			tokenActual = analizadorLexico.getToken();
		else
			throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico match. Esperaba " + nombre + " pero hay un " + tokenActual.getNombre());
	}

	public void inicial() throws ExcepcionLexico, ExcepcionSintactico {
		clase();
		otroInicial();
	}

	private void clase() throws ExcepcionLexico, ExcepcionSintactico {
		match("class");
		match("idClase");
		herencia();
		match("llaveApertura");
		miembros();
		match("llaveCierre");
	}

	private void herencia() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("extends")) {
			match("extends");
			match("idClase");
		} else { }
		//throw new ExcepcionSintactico("Error sintáctico herencia " + tokenActual.getNroLinea());
	}

	private void otroInicial() throws  ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("class")) {
			// token == null -> EOF
			clase();
			otroInicial();
		} else /* VER ESTO EOF U OTRA COSA */
			if (!tokenActual.getNombre().equals("EOF"))
				throw new ExcepcionSintactico("Error sintáctico otroInicial" + tokenActual.getNroLinea());
	}

	private void miembros() throws ExcepcionSintactico, ExcepcionLexico {
	/*	switch (tokenActual.getNombre()) {
		case "public":
		case "protected":
		case "private":
		case "idClase":
		case "static":
		case "dynamic":*/
		miembro();
		otroMiembros();
/*			break;
		case "llaveCierre":
			break;
		default:
			throw new ExcepcionSintactico("Error sintáctico Miembros esperaba } creo" + tokenActual.getNroLinea());
		}*/
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
			/*case "llaveCierre":
				break;
			default:
				throw new ExcepcionSintactico("Error sintáctico Miembros " + tokenActual.getNroLinea());*/
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
		default:
			break;
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
		default:
			throw new ExcepcionSintactico("Error sintáctico: se esperaba la forma de método (static o dynamic) " + tokenActual.getNroLinea());
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
			throw new ExcepcionSintactico("Error sintáctico: se esperaba tipo de método " + tokenActual.getNroLinea());
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
		// agregar try catch para mejorar el mensaje de error?
		tipo();
		listaDecAtrs();
		asignacionInline();
		match("puntoComa");
	}

	private void asignacionInline() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
		case "asignacion":
			match("asignacion");
			expresion();
			break;
		case "puntoComa":
			break;
		default:
			throw new ExcepcionSintactico("Error sintáctico en asignación " + tokenActual.getNroLinea());
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
		default:
			throw new ExcepcionSintactico("Error sintáctico visibildad " + tokenActual.getNroLinea());
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
			throw new ExcepcionSintactico("Error sintáctico tipo " + tokenActual.getNroLinea());
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
		default:
			throw new ExcepcionSintactico("Error sintáctico tipoPrimitivo " + tokenActual.getNroLinea());
		}
	}

	private void listaDecAtrs() throws ExcepcionLexico, ExcepcionSintactico {
		match("idMetVar");
		rlistaDecAtrs();
	}

	private void rlistaDecAtrs() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
		case "coma":
			match("coma");
			listaDecAtrs();
			break;
		case "puntoComa":
		case "asignacion":
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
		if (tokenActual.getNombre().equals("parentesisApertura")) {
			match("parentesisApertura");
			argumentos();
		} else {
			throw new ExcepcionSintactico("Error sintáctico argsFormales " + tokenActual.getNroLinea());
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
		case "parentesisCierre":
			match("parentesisCierre");
			break;
		default:
			throw new ExcepcionSintactico("Error sintáctico argumentos " + tokenActual.getNroLinea());
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
		case "coma":
			match("coma");
			listaArgsFormales();
			break;
		case "parentesisCierre":
			match("parentesisCierre");
			break;
		default:
			throw new ExcepcionSintactico("Error sintáctico rArgFormal " + tokenActual.getNroLinea());
		}
	}

	private void bloque() throws ExcepcionLexico, ExcepcionSintactico {
		match("llaveApertura");
		sentencias();
		match("llaveCierre");
	}

	private void sentencias() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "puntoComa":
			case "idMetVar":
			case "parentesisApertura":
			case "boolean":
			case "char":
			case "int":
			case "String":
			case "idClase":
			case "if":
			case "while":
			case "llaveApertura":
			case "return":
			case "new": //// seguir acaaaAAAAAAAA
				otrasSentencias();
				break;
			case "llaveCierre":
				break;
			default:
				throw new ExcepcionSintactico("Error sintáctico sentencias " + tokenActual.getNroLinea());
		}		
	}

	private void otrasSentencias() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
		case "puntoComa":
		case "idMetVar":
		case "parentesisApertura":
		case "boolean":
		case "char":
		case "int":
		case "String":
		case "idClase":
		case "if":
		case "while":
		case "llaveApertura":
		case "return":
		case "new": // seguir acaaaa
			sentencia();
			otrasSentencias();
			break;
		case "llaveCierre":
			break;
		default:
			throw new ExcepcionSintactico("Error sintáctico otrasSentencias " + tokenActual.getNroLinea());
		}
	}

	private void sentencia() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
		case "puntoComa":
			match("puntoComa");
			break;
		case "idMetVar":
			ladoIzquierdo();
			rMetVarSentencia();
			match("puntoComa");
			break;
		case "suma":
		case "resta":
		case "negacion":
		case "null":
		case "true":
		case "false":
		case "entero":
		case "caracter":
		case "cadena":
		case "this":
		case "new":
		case "parentesisApertura":
			sentenciaSimple();
			match("puntoComa");
			break;
		case "boolean":
		case "char":
		case "int":
		case "String":
			tipoPrimitivo();
			listaDecVars();
			asignacionInline();
			match("puntoComa");
			break;
		case "idClase":
			match("idClase");
			rClaseSentencia();
			break;
		case "if":
			match("if");
			match("parentesisApertura");
			expresion();
			match("parentesisCierre");
			sentencia();
			rSentenciaIf();
			break;
		case "while":
			match("while");
			match("parentesisApertura");
			expresion();
			match("parentesisCierre");
			sentencia();
			break;
		case "llaveApertura":
			bloque();
			break;
		case "return":
			match("return");
			retorno();
			match("puntoComa");
			break;
		default:
			throw new ExcepcionSintactico("Error sintáctico sentencia " + tokenActual.getNroLinea());
		}
	}

	private void rMetVarSentencia() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
		case "asignacion":
			match("asignacion");
			expresion();
			break;
		case "puntoComa":
			break;
		default:
			throw new ExcepcionSintactico("Error sintáctico rMetVarSentencia " + tokenActual.getNroLinea());
		}
	}

	private void rClaseSentencia() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
		case "idMetVar":
			listaDecVars();
			asignacionInline();
			break;
		case "punto":
			match("punto");
			llamadaMetodo();
			encadenadoOp();
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
		if (tokenActual.getNombre().equals("puntoComa"))
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
		case "coma":
			match("coma");
			listaDecVars();
			break;
		case "puntoComa": // siguientes
		case "asignacion":
			break;
		default:
			throw new ExcepcionSintactico("Error sintáctico rListaDecVars " + tokenActual.getNroLinea());
		}
	}

	private void sentenciaSimple() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
		case "suma":
		case "resta":
		case "negacion":
		case "null":
		case "true":
		case "false":
		case "entero":
		case "caracter":
		case "cadena":
		case "this":
		case "idMetVar":
		case "idClase":
		case "new":
			expresion();
			break;
		case "parentesisApertura":
			match("parentesisApertura");
			expresion();
			match("parentesisCierre");
			break;
		default:
			throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() +"] Error sintáctico sentencia simple");
		}
	}
/*
	private void asignacion() throws ExcepcionLexico, ExcepcionSintactico {
		//ladoIzquierdo();
		match("asignacion");
		expresion();
		//match("puntoComa");
	}
*/
	private void ladoIzquierdo() throws ExcepcionLexico, ExcepcionSintactico {
		match("idMetVar");
		//ver si ahrehar para THIS
		rMetVar();
		encadenadoOp();
	}
	/*
	private void encadenadoAsignacion() throws ExcepcionLexico, ExcepcionSintactico {
		idEncadenado();
		otroEncadenadoAsignacion();
	}

	private void otroEncadenadoAsignacion() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
		case "punto":
		case "parentesisApertura":
			idEncadenado();
			otroEncadenadoAsignacion();
			break;
		case "asignacion":
			break;
		default:
			throw new ExcepcionSintactico("Error sintáctico otroEncadenadoAsignacion " + tokenActual.getNroLinea());
		}
		/*	if (tokenActual.getNombre().equals("punto")) {
			idEncadenado();
			otroIdsEncadenados();
		} else if (tokenActual.getNombre().equals("asignacion")) {   }
		else throw new ExcepcionSintactico("Error sintáctico otroIdsEncadenados " + tokenActual.getNroLinea());*/
	//}
	/*
	private void idEncadenado() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
		case "punto":
			match("punto");
			match("idMetVar");
			rIdEncadenado();
			break;
		case "parentesisApertura":
			argsActuales();
			break;
		}
	}

	private void rIdEncadenado() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
		case "parentesisApertura":
			argsActuales();
			break;
		case "asignacion":
			break;
			//default:
			//	throw new ExcepcionSintactico("Error sintáctico rIdEncadenado " + tokenActual.getNroLinea());*/
	//	}
	//	}*/

	private void expresion() throws ExcepcionLexico, ExcepcionSintactico {
		expOr();
	}

	private void expOr() throws ExcepcionLexico, ExcepcionSintactico {
		expAnd();
		or();
	}

	private void or() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("or")) {
			match("or");
			expAnd();
			or();
		} else { }
	}

	private void expAnd() throws ExcepcionLexico, ExcepcionSintactico {
		expIg();
		and();
	}

	private void and() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("and")) {
			match("and");
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
		case "comparacion":
		case "distinto":
			opIg();
			expComp();
			ig();
			break;
			// ??
		}
	}

	private void opIg() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
		case "comparacion":
			match("comparacion");
			break;
		case "distinto":
			match("distinto");
			break;
		default:
			throw new ExcepcionSintactico("Error sintáctico opIg " + tokenActual.getNroLinea());
		}
	}

	private void expComp() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
		case "suma":
		case "resta":
		case "negacion":
		case "null":
		case "true":
		case "false":
		case "entero":
		case "caracter":
		case "cadena":
		case "parentesisApertura":
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
		case "menor":
		case "menorIgual":
		case "mayor":
		case "mayorIgual":
			opComp();
			expAd();
			break;
		default:
			break; // ?
		}
	}

	private void opComp() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
		case "menor":
			match("menor");
			break;
		case "menorIgual":
			match("menorIgual");
			break;
		case "mayor":
			match("mayor");
			break;
		case "mayorIgual":
			match("mayorIgual");
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
		case "suma":
		case "resta":
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
		case "suma":
			match("suma");
			break;
		case "resta":
			match("resta");
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
		case "producto":
		case "cociente":
		case "modulo":
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
		case "producto":
			match("producto");
			break;
		case "cociente":
			match("cociente");
			break;
		case "modulo":
			match("modulo");
			break;
		default: 
			throw new ExcepcionSintactico("Error sintáctico opMul " + tokenActual.getNroLinea());
		}
	}

	private void expUn() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
		case "suma":
		case "resta":
		case "negacion":
			opUn();
			expUn();
			break;
		case "null":
		case "true":
		case "false":
		case "entero":
		case "caracter":
		case "cadena":
		case "parentesisApertura":
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
		case "suma":
			match("suma");
			break;
		case "resta":
			match("resta");
			break;
		case "negacion":
			match("negacion");
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
		case "entero":
		case "caracter":
		case "cadena":
			literal();
			break;
		case "parentesisApertura":
		case "this":
		case "idMetVar": /* chequear */
		case "idClase":
		case "new":
			primario();
			encadenadoOp();
			break;
		}
	}

	private void encadenadoOp() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("punto")) {
			encadenado();
		} else { }
	}

	private void encadenado() throws ExcepcionLexico, ExcepcionSintactico {
		llamadoIdEncadenado();
		rEncadenado();
	}

	private void llamadoIdEncadenado() throws ExcepcionLexico, ExcepcionSintactico {
		match("punto");
		match("idMetVar");
		rLlamadoIdEncadenado();
	}

	private void rLlamadoIdEncadenado() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("parentesisApertura")) {
			argsActuales();
		} else { }
	}

	private void rEncadenado() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("punto")) {
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
		case "entero":
			match("entero");
			break;
		case "caracter":
			match("caracter");
			break;
		case "cadena":
			match("cadena");
			break;
		default:
			throw new ExcepcionSintactico("Error sintáctico literal " + tokenActual.getNroLinea());
		}
	}

	private void primario() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
		case "parentesisApertura":
			match("parentesisApertura");
			expresion();
			match("parentesisCierre");
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
		match("punto");
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
		if (tokenActual.getNombre().equals("parentesisApertura")) {
			argsActuales();
		} else { }
	}

	private void argsActuales() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("parentesisApertura")) {
			match("parentesisApertura");
			listaExpresiones();
			match("parentesisCierre");
		} else {
			throw new ExcepcionSintactico("Error sintáctico argsActuales " + tokenActual.getNroLinea());
		}
	}

	private void listaExpresiones() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("parentesisCierre"))
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
		case "coma":
			match("coma");
			listaExps();
			break;
		case "parentesisCierre":
			break;
		default:
			throw new ExcepcionSintactico("Error sintáctico rListaExps " + tokenActual.getNroLinea());

		}
	}
}