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
			throw new ExcepcionSintactico("Error sintáctico match " + tokenActual.getNroLinea());
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
			case "llaveCierre":
				break;
			default:
				throw new ExcepcionSintactico("Error sintáctico Miembros esperaba } creo" + tokenActual.getNroLinea());
		}
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
		}
	}
	
	private void atributo() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "public":
			case "protected":
			case "private":
				visibilidad();
				tipo();
				listaDecAtrs();
				break;
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
		switch (tokenActual.getNombre()) {
			case "idMetVar":
				match("idMetVar");
				rlistaDecAtrs();
				break;
			default:
				throw new ExcepcionSintactico("Error sintáctico listaDecAtrs " + tokenActual.getNroLinea());
	}
}

	private void rlistaDecAtrs() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "coma":
				match("coma");
				listaDecAtrs();
				break;
			case "puntoComa":
				match("puntoComa");
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
		if (tokenActual.getNombre().equals("llaveApertura")) {
			match("llaveApertura");
			sentencias();
		} else
			throw new ExcepcionSintactico("Error sintáctico bloque " + tokenActual.getNroLinea());
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
				otrasSentencias();
				break;
			case "llaveCierre":
				match("llaveCierre");
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
				sentencia();
				otrasSentencias();
				break;
			case "llaveCierre":
				match("llaveCierre");
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
				asignacion();
				match("puntoComa");
				break;
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
			default:
				throw new ExcepcionSintactico("Error sintáctico sentencia " + tokenActual.getNroLinea());
		}
	}

	private void asignacion() {
		ladoIzquierdo();
		match("asignacion");
		expresion();
	}
	
}