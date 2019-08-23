package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;

public class AnalizadorLexico {

	private BufferedReader lector;
	private int estado;
	private int caracterEntero;
	private int numeroLinea;
	private int numeroColumna;
	private char caracterActual;
	private LinkedHashMap<String, String> palabrasReservadas;
	private boolean stop = false;

	public AnalizadorLexico(String archivoEntrada) throws FileNotFoundException {
		lector = new BufferedReader(new FileReader(archivoEntrada));
		try {
			caracterEntero = lector.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		numeroLinea = 1;
		numeroColumna = 1;
		palabrasReservadas = generarTablaPalabrasReservadas();
	}

	private LinkedHashMap<String, String> generarTablaPalabrasReservadas() {
		LinkedHashMap<String, String> tabla = new LinkedHashMap<String, String>();
		tabla.put("boolean", "boolean");
		tabla.put("char", "char");
		tabla.put("class", "class");
		tabla.put("dynamic", "dynamic");
		tabla.put("else", "else");
		tabla.put("extends", "extends");
		tabla.put("false", "false");
		tabla.put("final", "final");
		tabla.put("if", "if");
		tabla.put("int", "int");
		tabla.put("new", "new");
		tabla.put("null", "null");
		tabla.put("private", "private");
		tabla.put("protected", "protected");
		tabla.put("public", "public");
		tabla.put("return", "return");
		tabla.put("static", "static");
		tabla.put("String", "String");
		tabla.put("this", "this");
		tabla.put("true", "true");
		tabla.put("void", "void");
		tabla.put("while", "while");
		return tabla;
	}

	public boolean nextToken() {
		return stop == false; // caracterEntero == 1-> EOF
	}
  
	public Token getToken() throws  IOException, ExcepcionCaracterInvalido, ExcepcionFormatoCaracter, ExcepcionFormatoAnd, ExcepcionFormatoOr, ExcepcionFormatoComentarioMultilinea {
		estado = 0;
		String lexema = "";
		Token token = null;
		if (caracterEntero == -1)
			stop = true;
		while (nextToken()) {
			caracterActual = (char) caracterEntero;
			/*if (caracterActual == '\n') {
				caracterEntero = lector.read();
				caracterActual = (char) caracterEntero;
				numeroLinea++;
				
			}*/
			switch (estado) {
			case 0: {
				if (caracterActual == ' ' || caracterActual == '\t' || caracterActual == '\r') {
					
					caracterEntero = lector.read();
					//caracterActual = (char) caracterEntero;
				} else
				if (caracterActual == '\n') {
					caracterEntero = lector.read();
					numeroLinea++;
					numeroColumna = 1;
					break;
				}
				else if (Character.isLowerCase(caracterActual)) {
					lexema += caracterActual;
					caracterEntero = lector.read();
					estado = 1; // identificador método y variable, o chequeo palabra clave
				//	break;
				} else if (Character.isUpperCase(caracterActual)) {
					//System.out.println("jo");
					lexema += caracterActual;
					caracterEntero = lector.read();
					estado = 2; // identificador método de clase, o chequeo palabra clave String
					//break;
				} else if (Character.isDigit(caracterActual)) {
					lexema += caracterActual;
					caracterEntero = lector.read();
					estado = 3; // entero
					//break;
				} else if (caracterEntero == -1) {
					stop = true;
					break;
				}  
				else {
					switch (caracterActual) {
					case 39: { // comilla simple
						lexema += caracterActual;
						caracterEntero = lector.read();
						estado = 4; // caracter
						break;
					} case '(': {
						estado = 8;
						break;
					}
					case ')': {
						estado = 9;
						break;
					}
					case '{': {
						estado = 10;
						break;
					}
					case '}': {
						estado = 11;
						break;
					}
					case ',': {
						estado = 12;
						break;
					}
					case '.': {
						estado = 13;
						break;
					}
					case ';': {
						estado = 14;
						break;
					}
					case '>': {
						lexema += caracterActual;
						caracterEntero = lector.read();
						estado = 15;
						break;
					}
					case '<': {
						lexema += caracterActual;
						caracterEntero = lector.read();
						estado = 17;
						break;
					}
					case '!': {
						lexema += caracterActual;
						caracterEntero = lector.read();
						estado = 19;
						break;
					}
					case '=': {
						lexema += caracterActual;
						caracterEntero = lector.read();
						estado = 21;
						break;
					}
					case '&': {
						lexema += caracterActual;
						caracterEntero = lector.read();
						estado = 23;
						break;
					}
					case '|': {
						lexema += caracterActual;
						caracterEntero = lector.read();
						estado = 25;
						break;
					}
					case '+': {
						estado = 27;
						break;
					}
					case '-': {
						estado = 28;
						break;
					}
					case '*': {
						estado = 29;
						break;
					}
					case '%': {
						estado = 30;
						break;
					}
					case '/': {
						lexema += caracterActual;
						caracterEntero = lector.read();
						estado = 31;
						break;
					}
					default: {
					stop = true;
					throw new ExcepcionCaracterInvalido("ERROR LEXICO: Linea "+numeroLinea+": caracter no válido.");
				}
					
					}
				}
				// Actualizo número de columna
				if (caracterActual == '\t')
					numeroColumna += 4;
				else if (caracterActual != '\n' || caracterActual != '\r')
					numeroColumna++;
				break;
			}
			case 1: {
				//System.out.print(caracterActual);
				if (Character.isLowerCase(caracterActual) || Character.isUpperCase(caracterActual)
						|| Character.isDigit(caracterActual) || caracterActual == '_') {
					lexema += caracterActual;
					caracterEntero = lector.read();
					numeroColumna++;
				} else {
					String tokenLexema = palabrasReservadas.get(lexema);
					if (tokenLexema == null)
						return new Token("idMetVar", lexema, numeroLinea, numeroColumna - lexema.length());
					else
						return new Token(tokenLexema, tokenLexema, numeroLinea, numeroColumna - lexema.length());
					//estado = 0;
				}
				break;
			}
			case 2: {
				if (Character.isLowerCase(caracterActual) || Character.isUpperCase(caracterActual)
						|| Character.isDigit(caracterActual) || caracterActual == '_') {
					lexema += caracterActual;
					caracterEntero = lector.read();
					numeroColumna++;
				} else {
					String tokenLexema = palabrasReservadas.get(lexema);
					if (tokenLexema == null)
						return new Token("idClase", lexema, numeroLinea, numeroColumna - lexema.length());
					else
						return new Token(tokenLexema, tokenLexema, numeroLinea, numeroColumna - lexema.length());
				//	estado = 0;
				}
				break;
			}
			case 3: {
				if (Character.isDigit(caracterActual)) {
					lexema += caracterActual;
					caracterEntero = lector.read();
					numeroColumna++;
				} else {
					return new Token("entero",lexema,numeroLinea, numeroColumna - lexema.length());
					//estado = 0;
				}
					
				break;
			}
			case 4: {
				if(caracterActual == 92) { // caracter \
					lexema += caracterActual;
					caracterEntero = lector.read();
					numeroColumna++;
					estado = 5;
				} else if (caracterActual != 10 || caracterActual !=39) {
					lexema += caracterActual;
					caracterEntero = lector.read();
					numeroColumna++;
					estado = 6;
					break;
					} else
						stop = true;
				break;
			}
			case 5: {
				if (caracterEntero == -1) {
					stop = true;
					throw new ExcepcionFormatoCaracter("ERROR LEXICO: Linea "+numeroLinea+": formato caracter inválido.");
				} else {
					lexema += caracterActual;
					caracterEntero = lector.read();
					numeroColumna++;
					estado = 6;
				}
				break;	
			}
			case 6: {
				if (caracterActual == 39) { // '
					numeroColumna++;
					estado = 7;
				} else {
					
				stop = true;
					throw new ExcepcionFormatoCaracter("ERROR LEXICO: Linea "+numeroLinea+": formato caracter inválido.");
				} break;
			}
			case 7: {
				if (caracterActual == 39) { // '
					lexema += caracterActual;
					return new Token("caracter",lexema,numeroLinea, numeroColumna - lexema.length());
				} else
					stop = true;
				break;
			}
			case 8: {
				lexema += caracterActual;
				caracterEntero = lector.read();
				//estado = 0;
				return new Token("parentesisApertura",lexema,numeroLinea, numeroColumna - lexema.length());
				//break;
			}
			case 9: {
				lexema += caracterActual;
				caracterEntero = lector.read();
				//estado = 0;
				return token = new Token("parentesisCierre",lexema,numeroLinea, numeroColumna - lexema.length());
			//	break;
			}
			case 10: {
				lexema += caracterActual;
				caracterEntero = lector.read();
				//estado = 0;
				return new Token("llaveApertura",lexema,numeroLinea, numeroColumna - lexema.length());
				//break;
			}
			case 11: {
				lexema += caracterActual;
				caracterEntero = lector.read();
				//estado = 0;
				return new Token("llaveCierre",lexema,numeroLinea, numeroColumna - lexema.length());
			//	break;
			}
			case 12: {
				lexema += caracterActual;
				caracterEntero = lector.read();
				//estado = 0;
				return new Token("coma",lexema,numeroLinea, numeroColumna - lexema.length());
			//	break;
			}
			case 13: {
				lexema += caracterActual;
				caracterEntero = lector.read();
			//	estado = 0;
				return new Token("punto",lexema,numeroLinea, numeroColumna - lexema.length());
			//	break;
			}
			case 14: {
				lexema += caracterActual;
				caracterEntero = lector.read();
				//estado = 0;
				return new Token("puntoComa",lexema,numeroLinea, numeroColumna - lexema.length());
			//	break;
			}
			case 15: {
				if (caracterActual == '=') {
					numeroColumna++;
					estado = 16;
				} else {
					//lexema += caracterActual;
					caracterEntero = lector.read();
					return new Token("mayor",lexema,numeroLinea, numeroColumna - lexema.length());
					//estado = 0;
				}
				break;
			}
			case 16: {
				lexema += caracterActual;
				caracterEntero = lector.read();
			//	estado = 0;
				return new Token("mayorIgual",lexema,numeroLinea, numeroColumna - lexema.length());
				//break;
			}
			case 17: {
				if (caracterActual == '=') {
					numeroColumna++;
					estado = 18;
				} else {
					//lexema += caracterActual;
					caracterEntero = lector.read();
					return new Token("menor",lexema,numeroLinea, numeroColumna - lexema.length());
					//estado = 0;
				}
				break;
			}
			case 18: {
				lexema += caracterActual;
				caracterEntero = lector.read();
			//	estado = 0;
				return new Token("menorIgual",lexema,numeroLinea, numeroColumna - lexema.length());
			//	break;
			}
			case 19: {
				if (caracterActual == '=') {
					numeroColumna++;
					estado = 20;
				} else {
					//lexema += caracterActual;
					caracterEntero = lector.read();
					return new Token("negacion",lexema,numeroLinea,numeroColumna - lexema.length());
		//			estado = 0;
				}
				break;
			}
			case 20: {
				lexema += caracterActual;
				caracterEntero = lector.read();
				//estado = 0;
				return new Token("distinto",lexema,numeroLinea,numeroColumna - lexema.length());
				//break;
			}
			case 21: {
				if (caracterActual == '=') {
					numeroColumna++;
					estado = 22;
				} else {
					//caracterEntero = lector.read();
					return new Token("asignacion",lexema,numeroLinea,numeroColumna - lexema.length());
					//estado = 0;
				}
				break;
			}
			case 22: {
				lexema += caracterActual;
				caracterEntero = lector.read();
				//estado = 0;
				return new Token("comparacion",lexema,numeroLinea,numeroColumna - lexema.length());
				//break;
			}
			case 23: {
				if (caracterActual == '&') {
					//caracterEntero = lector.read();
					numeroColumna++;
					estado = 24;
				} else {
					stop = true;
					throw new ExcepcionFormatoAnd("ERROR LEXICO: Línea "+numeroLinea+": error en palabra reservada &&.");
					
				}
				break;
			}
			case 24: {
				if (caracterActual == '&') {
					lexema += caracterActual;
					caracterEntero = lector.read();
					
					//estado = 0;
					return new Token("and",lexema,numeroLinea, numeroColumna - lexema.length());
				//	break;
				}
				/* else {
					stop = true;
					throw new ExcepcionFormatoAnd("ERROR LEXICO: Línea "+numeroLinea+": error en token and.");
					
				}*/
			}
			case 25: {
				if (caracterActual == '|') {
					numeroColumna++;
					//caracterEntero = lector.read();
					estado = 26;
				} else {
					stop = true;
					throw new ExcepcionFormatoOr("ERROR LEXICO: Línea "+numeroLinea+": error en token or.");
					
				}
				break;
			}
			case 26: {
				if (caracterActual == '|') {
					lexema += caracterActual;
					caracterEntero = lector.read();
					//estado = 0;
					return new Token("or",lexema,numeroLinea,numeroColumna - lexema.length());
					//break;
				} 
				/*else {
					stop = true;
					throw new ExcepcionFormatoOr("ERROR LEXICO: Línea "+numeroLinea+": error en token or.");
					
				}*/
			}
			case 27: {
				lexema += caracterActual;
				caracterEntero = lector.read();
				//estado = 0;
				return new Token("suma",lexema,numeroLinea,numeroColumna - lexema.length());
				//break;
			}
			case 28: {
				lexema += caracterActual;
				caracterEntero = lector.read();
			//	estado = 0;
				return new Token("resta",lexema,numeroLinea,numeroColumna - lexema.length());
			//	break;
			}
			case 29: {
				lexema += caracterActual;
				caracterEntero = lector.read();
			//	estado = 0;
				return new Token("producto",lexema,numeroLinea,numeroColumna - lexema.length());
		//		break;
			}
			case 30: {
				lexema += caracterActual;
				caracterEntero = lector.read();
				//estado = 0;
				return new Token("modulo",lexema,numeroLinea,numeroColumna - lexema.length());
				//break;
			}
			
			case 31: {
				if (caracterActual == '*') { //multilínea
					lexema = "";
					caracterEntero = lector.read();
					caracterActual = (char) caracterEntero;
					if (caracterActual == '/') {
						caracterEntero = lector.read();
						estado = 0;
				}
					else {
					while (caracterActual != '*' && caracterEntero != -1) {
						caracterEntero = lector.read();
						caracterActual = (char) caracterEntero;
						if (caracterActual == '\n') {
							numeroLinea++;
							numeroColumna = 1;
						}
					}
					if (caracterActual == '*') {
						caracterEntero = lector.read();
						caracterActual = (char) caracterEntero;
						if (caracterActual == '/') {
							caracterEntero = lector.read();
							estado = 0;
						}
					}
					if (caracterEntero == -1) {
						stop = true;
						throw new ExcepcionFormatoComentarioMultilinea("ERROR LEXICO: Linea "+numeroLinea+": comentario multinea sin cerrar.");
					}}
					break;
				} else if (caracterActual == '/') {
					lexema = "";
					caracterEntero = lector.read();
					caracterActual = (char) caracterEntero;
					while (caracterActual != '\n' && caracterEntero != -1) {
						caracterEntero = lector.read();
						caracterActual = (char) caracterEntero;
					}
					if (caracterActual == '\n') {
						caracterEntero = lector.read();
						numeroLinea++;
						numeroColumna = 1;
					}
					else if (caracterEntero == -1)
						stop = true;
					estado = 0;
					break;
				} else {
					caracterEntero = lector.read();
				//	estado = 0;
					return new Token("cociente",lexema,numeroLinea,numeroColumna - lexema.length());
				//	break;
				}
			
			}/*
			default: {
		
				throw new ExcepcionCaracterInvalido("ERROR LEXICO: Linea "+numeroLinea+": caracter no válido.");
			}*/
				
			}
		}
		return token;
	}
}
