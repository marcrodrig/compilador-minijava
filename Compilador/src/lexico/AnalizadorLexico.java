package lexico;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * Clase Analizador Léxico, encargada de realizar el análisis léxico y proveer
 * tokens reconocidos con la información necesaria
 * 
 * @author Rodríguez, Marcelo
 *
 */
public class AnalizadorLexico {
	/**
	 * Lector del archivo de entrada
	 */
	private BufferedReader lector;
	/**
	 * Número de estado en el que se encuentra el análisis para obtener el próximo
	 * token
	 */
	private int estado;
	/**
	 * Valor entero del último caracter leído
	 */
	private int caracterEntero;
	/**
	 * Número de línea en que se encuentra el analizador
	 */
	private int numeroLinea;
	/**
	 * Número de columna en que se encuentra el analizador
	 */
	private int numeroColumna;
	/**
	 * Catacter que se analiza
	 */
	private char caracterActual;
	/**
	 * Mapeo con las palabras reservadas
	 */
	private LinkedHashMap<String, String> palabrasReservadas;
	/**
	 * Indica si el análisis se debe detener
	 */
	private boolean stop = false;

	/**
	 * @param archivoEntrada El parámetro archivoEntrada define la ruta del archivo
	 *                       de texto que se analizará
	 * @throws FileNotFoundException si no encuentra el archivo de entrada
	 */
	public AnalizadorLexico(String archivoEntrada) throws FileNotFoundException {
		lector = new BufferedReader(new FileReader(archivoEntrada));
		try {
			caracterEntero = lector.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		numeroLinea = 1;
		numeroColumna = 1;
		palabrasReservadas = generarTablaPalabrasReservadas();
	}

	/**
	 * Método que inicializa la tabla de palabras reservadas
	 * 
	 * @return El mapeo con clave y valor igual al String de la palabra reservada
	 */
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

	/**
	 * Método que verifica si se puede solicitar un token
	 * 
	 * @return true si se puede solicitar un token, false en caso contrario
	 */
	public boolean nextToken() {
		return stop == false; // caracterEntero == 1 -> EOF
	}

	/**
	 * Método que retorna el próximo token, teniendo en cuenta el archivo de entrada
	 * 
	 * @return el siguiente token correspondiente al archivo de entrada
	 * @throws ExcepcionCaracterInvalido            si en el análisis se encuentra
	 *                                              un caracter inválido
	 * @throws ExcepcionFormatoCaracter             si en el token caracter no se
	 *                                              respeta su fomato
	 * @throws ExcepcionFormatoAnd                  si no se respeta el formato and:
	 *                                              &&
	 * @throws ExcepcionFormatoOr                   si no se respeta el formato or :
	 *                                              ||
	 * @throws ExcepcionFormatoComentarioMultilinea si no se cierra correctamente un
	 *                                              comentario multilinea
	 * @throws ExcepcionFormatoString               si no se respeta el formato de
	 *                                              un String
	 */
	public Token getToken() throws ExcepcionCaracterInvalido, ExcepcionFormatoCaracter, ExcepcionFormatoAnd,
			ExcepcionFormatoOr, ExcepcionFormatoComentarioMultilinea, ExcepcionFormatoString {
		Token token = null;
		estado = 0;
		String lexema = "";
		if (caracterEntero == -1) {
			stop = true;
			return new Token("EOF", "$", numeroLinea, numeroColumna);
		}
		try {
			while (nextToken()) {
				caracterActual = (char) caracterEntero;
				switch (estado) {
					case 0: {
						if (caracterActual == ' ' || caracterActual == '\t' || caracterActual == '\r') {
							caracterEntero = lector.read();
						} else if (caracterActual == '\n') {
							caracterEntero = lector.read();
							numeroLinea++;
							numeroColumna = 1;
							break;
						} else if (Character.isLowerCase(caracterActual)) {
							lexema += caracterActual;
							caracterEntero = lector.read();
							estado = 1; // identificador método y variable, o chequeo palabra clave
						} else if (Character.isUpperCase(caracterActual)) {
							lexema += caracterActual;
							caracterEntero = lector.read();
							estado = 2; // identificador método de clase, o chequeo palabra clave String
						} else if (Character.isDigit(caracterActual)) {
							lexema += caracterActual;
							caracterEntero = lector.read();
							estado = 3; // entero
						} else if (caracterEntero == -1) {
							stop = true;
							return new Token("EOF", "$", numeroLinea, numeroColumna);
						} else {
							switch (caracterActual) {
								case 39: { // comilla simple
									lexema += caracterActual;
									caracterEntero = lector.read();
									estado = 4; // caracter
									break;
								}
								case '(': {
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
								case '"': {
									lexema += caracterActual;
									caracterEntero = lector.read();
									estado = 32;
									break;
								}
								default: {
									stop = true;
									throw new ExcepcionCaracterInvalido(
											"ERROR LEXICO: Linea " + numeroLinea + ": caracter no válido.");
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
						}
						break;
					}
					case 3: {
						if (Character.isDigit(caracterActual)) {
							lexema += caracterActual;
							caracterEntero = lector.read();
							numeroColumna++;
						} else {
							return new Token("intLiteral", lexema, numeroLinea, numeroColumna - lexema.length());
						}
						break;
					}
					case 4: {
						if (caracterActual == 39) { // caracter '
							stop = true;
							throw new ExcepcionFormatoCaracter(
									"ERROR LEXICO: Linea " + numeroLinea + ": formato caracter inválido.");
						} else if (caracterActual == 92) { // caracter \
							lexema += caracterActual;
							caracterEntero = lector.read();
							numeroColumna++;
							estado = 5;
						} else if (caracterActual != 10 || caracterActual != 39) {
							lexema += caracterActual;
							caracterEntero = lector.read();
							numeroColumna++;
							estado = 6;
						}
						break;
					}
					case 5: {
						if (caracterEntero == -1) {
							stop = true;
							throw new ExcepcionFormatoCaracter(
									"ERROR LEXICO: Linea " + numeroLinea + ": formato caracter inválido.");
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
							throw new ExcepcionFormatoCaracter(
									"ERROR LEXICO: Linea " + numeroLinea + ": formato caracter inválido.");
						}
					}
					case 7: {
						lexema += caracterActual;
						caracterEntero = lector.read();
						return new Token("charLiteral", lexema, numeroLinea, numeroColumna - lexema.length());
					}
					case 8: {
						lexema += caracterActual;
						caracterEntero = lector.read();
						return new Token("(", lexema, numeroLinea, numeroColumna - lexema.length());
					}
					case 9: {
						lexema += caracterActual;
						caracterEntero = lector.read();
						return token = new Token(")", lexema, numeroLinea, numeroColumna - lexema.length());
					}
					case 10: {
						lexema += caracterActual;
						caracterEntero = lector.read();
						return new Token("{", lexema, numeroLinea, numeroColumna - lexema.length());
					}
					case 11: {
						lexema += caracterActual;
						caracterEntero = lector.read();
						return new Token("}", lexema, numeroLinea, numeroColumna - lexema.length());
					}
					case 12: {
						lexema += caracterActual;
						caracterEntero = lector.read();
						return new Token(",", lexema, numeroLinea, numeroColumna - lexema.length());
					}
					case 13: {
						lexema += caracterActual;
						caracterEntero = lector.read();
						return new Token(".", lexema, numeroLinea, numeroColumna - lexema.length());
					}
					case 14: {
						lexema += caracterActual;
						caracterEntero = lector.read();
						return new Token(";", lexema, numeroLinea, numeroColumna - lexema.length());
					}
					case 15: {
						if (caracterActual == '=') {
							numeroColumna++;
							estado = 16;
						} else
							return new Token(">", lexema, numeroLinea, numeroColumna - lexema.length());
					}
					case 16: {
						lexema += caracterActual;
						caracterEntero = lector.read();
						return new Token(">=", lexema, numeroLinea, numeroColumna - lexema.length());
					}
					case 17: {
						if (caracterActual == '=') {
							numeroColumna++;
							estado = 18;
						} else
							return new Token("<", lexema, numeroLinea, numeroColumna - lexema.length());
					}
					case 18: {
						lexema += caracterActual;
						caracterEntero = lector.read();
						return new Token("<=", lexema, numeroLinea, numeroColumna - lexema.length());
					}
					case 19: {
						if (caracterActual == '=') {
							numeroColumna++;
							estado = 20;
						} else {
							return new Token("!", lexema, numeroLinea, numeroColumna - lexema.length());
						}
						break;
					}
					case 20: {
						lexema += caracterActual;
						caracterEntero = lector.read();
						return new Token("!=", lexema, numeroLinea, numeroColumna - lexema.length());
					}
					case 21: {
						if (caracterActual == '=') {
							numeroColumna++;
							estado = 22;
						} else {
							return new Token("=", lexema, numeroLinea, numeroColumna - lexema.length());
						}
						break;
					}
					case 22: {
						lexema += caracterActual;
						caracterEntero = lector.read();
						return new Token("==", lexema, numeroLinea, numeroColumna - lexema.length());
					}
					case 23: {
						if (caracterActual == '&') {
							numeroColumna++;
							estado = 24;
						} else {
							stop = true;
							throw new ExcepcionFormatoAnd("ERROR LEXICO: Línea " + numeroLinea + ": error en formato &&");
						}
					}
					case 24: {
						lexema += caracterActual;
						caracterEntero = lector.read();
						return new Token("&&", lexema, numeroLinea, numeroColumna - lexema.length());
					}
					case 25: {
						if (caracterActual == '|') {
							numeroColumna++;
							estado = 26;
						} else {
							stop = true;
							throw new ExcepcionFormatoOr("ERROR LEXICO: Línea " + numeroLinea + ": error en formato ||");
						}
						break;
					}
					case 26: {
						lexema += caracterActual;
						caracterEntero = lector.read();
						return new Token("||", lexema, numeroLinea, numeroColumna - lexema.length());
					}
					case 27: {
						lexema += caracterActual;
						caracterEntero = lector.read();
						return new Token("+", lexema, numeroLinea, numeroColumna - lexema.length());
					}
					case 28: {
						lexema += caracterActual;
						caracterEntero = lector.read();
						return new Token("-", lexema, numeroLinea, numeroColumna - lexema.length());
					}
					case 29: {
						lexema += caracterActual;
						caracterEntero = lector.read();
						return new Token("*", lexema, numeroLinea, numeroColumna - lexema.length());
					}
					case 30: {
						lexema += caracterActual;
						caracterEntero = lector.read();
						return new Token("%", lexema, numeroLinea, numeroColumna - lexema.length());
					}
					case 31: {
						if (caracterActual == '*') { // multilínea
							lexema = "";
							caracterEntero = lector.read();
							caracterActual = (char) caracterEntero;
							if (caracterActual == '\t') {
								numeroColumna += 4;
							} else
								numeroColumna++;
							if (caracterActual == '/') {
								caracterEntero = lector.read();
								numeroColumna++;
								estado = 0;
							} else {
								boolean continuar = true;
								while (continuar && caracterActual != '*' && caracterEntero != -1) {
									caracterEntero = lector.read();
									if (caracterEntero == -1) {
										stop = true;
										throw new ExcepcionFormatoComentarioMultilinea(
												"[" + numeroLinea + ":"+ numeroColumna + "] ERROR LEXICO: comentario multinea sin cerrar.");
									}
									// numeroColumna++;
									caracterActual = (char) caracterEntero;
									if (caracterActual == '\t') {
										numeroColumna += 4;
									} else if (caracterActual == '\n') {
										numeroLinea++;
										numeroColumna = 1;
									} else
										numeroColumna++;
									if (caracterActual == '*') {
										caracterEntero = lector.read();
										if (caracterEntero == -1) {
											stop = true;
											throw new ExcepcionFormatoComentarioMultilinea(
													"[" + numeroLinea + ":"+ numeroColumna + "] ERROR LEXICO: comentario multinea sin cerrar.");
										}
										numeroColumna++;
										caracterActual = (char) caracterEntero;
										if (caracterActual == '/') {
											caracterEntero = lector.read();
											numeroColumna++;
											estado = 0;
											continuar = false;
										}
									}
								}
							}
							break;
						} else if (caracterActual == '/') {
							lexema = "";
							caracterEntero = lector.read();
							numeroColumna++;
							caracterActual = (char) caracterEntero;
							while (caracterActual != '\n' && caracterEntero != -1) {
								caracterEntero = lector.read();
								numeroColumna++;
								caracterActual = (char) caracterEntero;
							}
							if (caracterActual == '\n') {
								caracterEntero = lector.read();
								numeroLinea++;
								numeroColumna = 1;
							} else if (caracterEntero == -1)
								stop = true;
							estado = 0;
							break;
						} else {
							return new Token("/", lexema, numeroLinea, numeroColumna - lexema.length());
						}
					}
					case 32: {
						while (caracterActual != '\\' && caracterEntero != -1 && caracterActual != '"'
								&& caracterActual != '\n') {
							lexema += caracterActual;
							caracterEntero = lector.read();
							caracterActual = (char) caracterEntero;
							numeroColumna++;
						}
						switch (caracterActual) {
							case '"': {
								estado = 35;
								break;
							}
							case '\\': {
								caracterEntero = lector.read();
								caracterActual = (char) caracterEntero;
								numeroColumna++;
								estado = 33;
								break;
							}
							default: {
								throw new ExcepcionFormatoString("ERROR LEXICO: Linea " + numeroLinea
										+ ": error en formato de String, String sin cerrar o con un salto de línea.");
							}
						}
						break;
					}
					case 33: {
						if (caracterActual == 'n') {
							String nuevaLinea = System.lineSeparator();
							lexema += nuevaLinea;
							caracterEntero = lector.read();
							caracterActual = (char) caracterEntero;
							numeroColumna++;
							estado = 34;
						} else {
							lexema += '\\';
							lexema += caracterActual;
							caracterEntero = lector.read();
							caracterActual = (char) caracterEntero;
							numeroColumna++;
							estado = 32;
						}
						break;
					}
					case 34: {
						if (caracterActual == '"') {
							estado = 35;
						} else {
							lexema += caracterActual;
							caracterEntero = lector.read();
							caracterActual = (char) caracterEntero;
							numeroColumna++;
							estado = 32;
						}
						break;
					}
					case 35: {
						lexema += caracterActual;
						caracterEntero = lector.read();
						numeroColumna++;
						return new Token("stringLiteral", lexema, numeroLinea, numeroColumna - lexema.length());
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return token;
	}
}