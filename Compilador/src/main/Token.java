package main;

/**
 * Clase Token. Un token registra su nombre, lexema y números de línea/columna donde se leyó de un archivo de entrada
 * @author Rodríguez, Marcelo
 *
 */
public class Token {
	/**
	 * El nombre del token
	 */
	private String nombre;
	/**
	 * El lexema del token
	 */
	private String lexema;
	/**
	 * El número de línea donde se encuentra el token
	 */
	private int numeroLinea;
	/**
	 * El número de columna donde comienza el token
	 */
	private int numeroColumna;

	/**
	 * 
	 * @param nombre El nombre del token a crear
	 * @param lexema El lexema del token a crear
	 * @param numeroLinea El número de línea donde se encuentra el token a crear
	 * @param numeroColumna El número de columna donde comienza el token a crear
	 */
	public Token(String nombre, String lexema, int numeroLinea, int numeroColumna) {
		this.nombre = nombre;
		this.lexema = lexema;
		this.numeroLinea = numeroLinea;
		this.numeroColumna = numeroColumna;
	}

	/**
	 * @return El nombre del token
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @return El nombre del lexema
	 */
	public String getLexema() {
		return lexema;
	}

	/**
	 * @return El número de línea del token
	 */
	public int getNroLinea() {
		return numeroLinea;
	}
	
	/**
	 * @return El número de columna del token
	 */
	public int getNroColumna() {
		return numeroColumna;
	}

	/**
	 * Retorna toda la información del token en un String
	 */
	@Override
	public String toString() {
		return "Token ["+numeroLinea+":"+numeroColumna+"] [Nombre: " + nombre + "\t\t\tLexema: " + lexema +"]" ;
	}

}
