package sintactico;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import lexico.AnalizadorLexico;
import lexico.ExcepcionLexico;
import lexico.Token;
import semantico.Clase;
import semantico.Constructor;
import semantico.ExcepcionSemantico;
import semantico.Metodo;
import semantico.Parametro;
import semantico.Tipo;
import semantico.TipoBoolean;
import semantico.TipoChar;
import semantico.TipoClase;
import semantico.TipoInt;
import semantico.TipoRetorno;
import semantico.TipoString;
import semantico.TipoVoid;
import semantico.VariableInstancia;
import main.Principal;

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
				throw new ExcepcionSintactico("Error sint�ctico: ; faltante.\nEsperado: " + nombre + "\nEncontrado: " + tokenActual.getNombre());
			else
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sint�ctico.\nEsperado: " + nombre + "\nEncontrado: " + tokenActual.getNombre());
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
			Principal.ts.setRS();
			//otroInicial();
		}
		otroInicial();
	}

	private void otroInicial() throws  ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode {
		if (tokenActual.getNombre().equals("class")) {			// primeros
			try {
				clase();
			} catch (ExcepcionSemantico e) {
				System.out.println(e.toString());
				Principal.ts.setRS();
				//otroInicial();
			}
			otroInicial();
		} else if (tokenActual.getNombre().equals("EOF")) {		// siguientes
		} else
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sint�ctico: Declaraci�n de una clase inv�lida.\nEsperado: class\nEncontrado: " + tokenActual.getNombre());
	}

	private void clase() throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode, ExcepcionSemantico {
		match("class");		// primeros
		Token token = tokenActual;
		match("idClase");
		String superclase = herencia();
		Clase clase = new Clase(token, superclase);
		Principal.ts.setClaseActual(clase);
		match("{");
		miembros();
		match("}");
		if (Principal.ts.getClase(clase.getNombre()) == null)
			Principal.ts.insertarClase(clase);
		else
			throw new ExcepcionSemantico("[" + clase.getNroLinea() + ":" + clase.getNroColumna() + "] Error sem�ntico: La clase " + clase.getNombre() + " ya est� definida." );
	}

	private String herencia() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("extends")) {	// primeros
			match("extends");
			try {
				String superclase = tokenActual.getLexema();
				match("idClase");
				return superclase;
			} catch (ExcepcionSintactico e) {
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sint�ctico: Declaraci�n de herencia de una clase inv�lida.\nEsperado: idClase\nEncontrado: " + tokenActual.getNombre());
			}
		} else 
			if (tokenActual.getNombre().equals("{")) {		// siguientes
				return "Object";
			} else
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sint�ctico: Declaraci�n de una clase inv�lida.\nEsperado: extends o {\nEncontrado: " + tokenActual.getNombre());
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
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sint�ctico: Se espera la declaraci�n de un atributo, constructor, m�todo o }.\nEsperado: public, protected, private, idClase, static, dynamic o }\nEncontrado: " + tokenActual.getNombre());		}
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
					Principal.ts.setRS();
					//miembros();
				}
				break;
			case "idClase":		// primeros
				try {
				ctor();
				} catch (ExcepcionSemantico e) {
					System.out.println(e.toString());
					Principal.ts.setRS();
					//miembros();
				}
				break;
			case "static":		// primeros
			case "dynamic":		// primeros
				try {
				metodo();
				} catch (ExcepcionSemantico e) {
					System.out.println(e.toString());
					Principal.ts.setRS();
					//miembros();
				}
				break;
		}
	}

	private void atributo() throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode, ExcepcionSemantico {
		int panicoLinea = tokenActual.getNroLinea();
		int panicoColumna = tokenActual.getNroColumna();
		String modificadorVisibilidad = visibilidad();
		try {
			Tipo tipo = tipo();
			List<Token> listaTokenVariablesInstancia = new ArrayList<Token>();
			listaDecAtrs(listaTokenVariablesInstancia);
			decAsig();
			match(";");
			for (Token tokenAtributo : listaTokenVariablesInstancia) {
				VariableInstancia varIns = new VariableInstancia(tokenAtributo,tipo,modificadorVisibilidad);
				if (Principal.ts.getClaseActual().getAtributos().get(varIns.getNombre()) == null)
					Principal.ts.insertarAtributo(varIns);
				else
					throw new ExcepcionSemantico("[" + tokenAtributo.getNroLinea() + ":" + tokenAtributo.getNroColumna() + "] Error sem�ntico: Nombre de atributo \"" + tokenAtributo.getLexema() + "\" repetido.");
			}
		} catch (ExcepcionSintactico e) {
			modoPanicoAtributo(panicoLinea, panicoColumna, e.toString());
		}
	}
	
	private void modoPanicoAtributo(int nroLineaComienzo, int nroColumnaComienzo, String mensajeError) throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode {
		int nroLineaFin = ultimaLineaAnalizada;
		int nroColumnaFin = ultimaColumnaAnalizada;
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
			System.out.println("[" + nroLineaComienzo + "]: declaraci�n/asignaci�n de atributos sint�cticamente inv�lida.\nCausa:\n" + mensajeError);
			throw new ExcepcionPanicMode("No se puede recuperar de la �ltima sentencia inv�lida.");		
		}
		else {
			System.out.println("[" + nroLineaComienzo + ":" + nroColumnaComienzo + " - " + nroLineaFin + ":" + nroColumnaFin + "]: declaraci�n/asignaci�n de atributos sint�cticamente inv�lida.\nCausa:\n" + mensajeError);
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
				throw new ExcepcionSintactico("Error sint�ctico: tipo inv�lido.\nEsperado: idClase, boolean, char, int o String\nEncontrado: " + tokenActual.getNombre());
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
				throw new ExcepcionSintactico("Error sint�ctico: Declaraci�n/asignaci�n de atributos inv�lida.\nEsperado: ; o =\nEncontrado: " + tokenActual.getNombre());
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
	
	private void metodo() throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode, ExcepcionSemantico {
		String formaMetodo = formaMetodo();
		boolean metodoFinal = fnl();
		TipoRetorno tipo = tipoMetodo();
		Token token = tokenActual;
		match("idMetVar");
		List<Parametro> listaArgsFormales = argsFormales();
		HashMap<String, Parametro> parametros = new HashMap<String, Parametro>();
		Metodo met = new Metodo(token, formaMetodo, tipo, metodoFinal, parametros);
		Principal.ts.setMetodoActual(met);
		bloque();
		int posicion = 1;
		for (Parametro param : listaArgsFormales) {
			param.setPosicion(posicion);
			if (parametros.get(param.getNombre()) == null)
				parametros.put(param.getNombre(), param);
			else
				throw new ExcepcionSemantico("[" + param.getNroLinea() + ":" + param.getNroColumna() +"] Error sem�ntico: Nombre de par�metro \"" + param.getNombre() + "\" repetido.");
			posicion++;
		}
		Principal.ts.insertarMetodo(met);
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
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sint�ctico: Declaraci�n de m�todo inv�lida.\nEsperado: final o tipo de m�todo\nEncontrado: " + tokenActual.getNombre());
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
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sint�ctico: Declaraci�n de tipo de m�todo inv�lido.\nEsperado: idClase, boolean, char, int, String o void\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private List<Parametro> argsFormales() throws ExcepcionLexico, ExcepcionSintactico {
		List<Parametro> argsFormales = null;
		if (tokenActual.getNombre().equals("(")) {	// primeros
			match("(");
			argsFormales = argumentos();
			match(")");
		} else {
			throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sint�ctico: Argumentos formales inv�lidos.\nEsperado: (\nEncontrado: " + tokenActual.getNombre());
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
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sint�ctico: Argumentos formales inv�lidos.\nEsperado: ) o tipo\nEncontrado: " + tokenActual.getNombre());
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
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sint�ctico: Argumentos formales inv�lidos.\nEsperado: ) o ,\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private void ctor() throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode, ExcepcionSemantico {
		Token token = tokenActual; 
		match("idClase");	// primeros
		List<Parametro> listaArgsFormales = argsFormales();
		bloque();
		if (!Principal.ts.getClaseActual().getNombre().equals(token.getLexema()))
			throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna() + "] Error sem�ntico: El nombre del constructor es inv�lido, debe ser " + Principal.ts.getClaseActual().getNombre() + ".");
		LinkedHashMap<String, Parametro> parametros = new LinkedHashMap<String, Parametro>();
		int posicion = 1;
		for (Parametro param : listaArgsFormales) {
			param.setPosicion(posicion);
			if (parametros.get(param.getNombre()) == null)
				parametros.put(param.getNombre(), param);
			else
				throw new ExcepcionSemantico("[" + param.getNroLinea() + ":" + param.getNroColumna() + "] Error sem�ntico: Nombre de par�metro \"" + param.getNombre() + "\" repetido.");
			posicion++;
		}
		Constructor ctor = new Constructor(token, parametros);
		Principal.ts.insertarConstructor(ctor);
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
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sint�ctico: Se espera la declaraci�n de una sentencia o }.\nEsperado: idMetVar, (, boolean, char, int, String, idClase, if, while, {, return, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this, } o ;\nEncontrado: " + tokenActual.getNombre());
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
					sentenciaSimple();
					match(";");
				} catch (ExcepcionSintactico e) {
					modoPanicoBloque(panicoLinea, panicoColumna, e.toString());
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
					modoPanicoBloque(panicoLinea, panicoColumna, e.toString());
				}
				break;
			case "idClase":							// primeros
				match("idClase");
				try {
					ladoDerechoIdClase();
					match(";");
				} catch (ExcepcionSintactico e) {
					modoPanicoBloque(panicoLinea, panicoColumna, e.toString());
				}
				break;
			case "if":								// primeros
				match("if");
				try {
					match("(");
					expresion();
					match(")");
					ifPanico = true;
					sentencia();
					ifPanico = false;
				} catch (ExcepcionSintactico e) {
					modoPanicoBloque(panicoLinea, panicoColumna, e.toString());
				}
				rSentenciaIf();
				break;
			case "while":							// primeros
				match("while");
				try {
					match("(");
					expresion();
					match(")");
					sentencia();
				} catch (ExcepcionSintactico e) {
					modoPanicoBloque(panicoLinea, panicoColumna, e.toString());
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
					modoPanicoBloque(panicoLinea, panicoColumna, e.toString());
				}
				break;
			default:
				throw new ExcepcionSintactico("Error sint�ctico: Se espera la declaraci�n de una sentencia.\nEsperado: ;, idMetVar, (, boolean, char, int, String, idClase, if, while, {, return, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral o this.\nEncontrado: " + tokenActual.getNombre());
		}
	}
	
	private void modoPanicoBloque(int nroLineaComienzo, int nroColumnaComienzo, String mensajeError) throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode {
		int nroLineaFin = ultimaLineaAnalizada;
		int nroColumnaFin = ultimaColumnaAnalizada;
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
			System.out.println("[" + nroLineaComienzo + "]: sentencia sint�cticamente inv�lida.\nCausa:\n" + mensajeError);
			throw new ExcepcionPanicMode("No se puede recuperar de la �ltima sentencia sint�cticamente inv�lida.");
		} else {
			System.out.println("[" + nroLineaComienzo + ":" + nroColumnaComienzo + " - " + nroLineaFin + ":" + nroColumnaFin + "]: sentencia sint�cticamente inv�lida.\nCausa:\n" + mensajeError);
			switch (tokenActual.getNombre()) {
				case "}":
					break;
				case "else":
					if (ifPanico)
						rSentenciaIf();
					else
						throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sint�ctico: Se espera la declaraci�n de una sentencia o }.\nEsperado: ;, idMetVar, (, boolean, char, int, String, idClase, if, while, {, return, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this o }\nEncontrado: " + tokenActual.getNombre());
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
				throw new ExcepcionSintactico("Error sint�ctico: Sentencia simple desparentizada inv�lida.\nEsperado: =, &&, ||, *, /, %, +, -, ==, !=, >, >=, <, <=, o ;\nEncontrado: " + tokenActual.getNombre());
		}
	}
	
	private void sentenciaSimple() throws ExcepcionLexico, ExcepcionSintactico {
		switch (tokenActual.getNombre()) {
			case "(":				// primeros
				match("(");
				expresion();
				match(")");
				rDesparentizada();
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
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sint�ctico: sentencia simple inv�lida.");
			}
	}
	
	private void rDesparentizada() throws ExcepcionLexico, ExcepcionSintactico {
		rEncadenado();
		desparentizada();
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
				throw new ExcepcionSintactico("Error sint�ctico: Declaraci�n/asignaci�n de variables inv�lida.\nEsperado: ;, = o ,\nEncontrado: " + tokenActual.getNombre());
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
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sint�ctico: Error en sentencia comenzada con idClase.\nEsperado: . o idMetVar\nEncontrado: " + tokenActual.getNombre());
		}
	}

	private void rSentenciaIf() throws ExcepcionLexico, ExcepcionSintactico, ExcepcionPanicMode {
		int panicoLinea = tokenActual.getNroLinea();
		int panicoColumna = tokenActual.getNroColumna();
		if (tokenActual.getNombre().equals("else")) {	// primeros
			match("else");
			try {	
				sentencia();
			} catch (ExcepcionSintactico e) {
				modoPanicoBloque(panicoLinea, panicoColumna, e.toString());
			}
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
				throw new ExcepcionSintactico("Error sint�ctico: Error en expresi�n de retorno.\nEsperado: +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this, new, (, idMetVar, idClase o ;\nEncontrado: " + tokenActual.getNombre());
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
				throw new ExcepcionSintactico("Error sint�ctico: Sentencia simple desparentizada inv�lida.\nEsperado: =, &&, ||, *, /, %, +, -, ==, !=, >, >=, <, <=, o ;\nEncontrado: " + tokenActual.getNombre());
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
				throw new ExcepcionSintactico("Error sint�ctico: Expresi�n inv�lida.\nEsperado: +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, (, this, idMetVar, idClase o new\nEncontrado: " + tokenActual.getNombre());
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
			expIg();
			and();
			break;
		default:
			throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sint�ctico: Expresi�n inv�lida.\nEsperado: +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, (, this, idMetVar, idClase o new\nEncontrado: " + tokenActual.getNombre());
	}
	}

	private void and() throws ExcepcionLexico, ExcepcionSintactico {
		if (tokenActual.getNombre().equals("&&")) {	// primeros
			match("&&");
			expIg();
			and();
		} else { }
	}

	private void expIg() throws ExcepcionLexico, ExcepcionSintactico {
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
			expComp();
			ig();
			break;
		default:
			throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sint�ctico: Expresi�n inv�lida.\nEsperado: +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, (, this, idMetVar, idClase o new\nEncontrado: " + tokenActual.getNombre());
	}
		
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
			default:
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sint�ctico: Expresi�n inv�lida.\nEsperado: +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, (, this, idMetVar, idClase o new\nEncontrado: " + tokenActual.getNombre());
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
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sint�ctico: Expresi�n unaria inv�lida.\nEsperado: +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, (, this, idMetVar, idClase o new\nEncontrado: " + tokenActual.getNombre());
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
			throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sint�ctico: Declaraci�n de argumentos actuales inv�lida.\nEsperado: (\nEncontrado: " + tokenActual.getNombre());
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
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sint�ctico: Lista de expresiones inv�lida.\nEsperado: (, idMetVar, idClase, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this o )\nEncontrado: " + tokenActual.getNombre());
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
				throw new ExcepcionSintactico("[" + tokenActual.getNroLinea() + ":" + tokenActual.getNroColumna() + "] Error sint�ctico: Lista de expresiones inv�lida.\nEsperado: (, idMetVar, idClase, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this o )\nEncontrado: " + tokenActual.getNombre());
		}
	}
}