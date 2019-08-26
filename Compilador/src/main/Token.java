package main;

/**
 * Clase Token. Un token registra su nombre, lexema y n�meros de l�nea/columna donde se ley� de un archivo de entrada
 * @author Rodr�guez, Marcelo
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
	 * El n�mero de l�nea donde se encuentra el token
	 */
	private int numeroLinea;
	/**
	 * El n�mero de columna donde comienza el token
	 */
	private int numeroColumna;

	/**
	 * 
	 * @param nombre El nombre del token a crear
	 * @param lexema El lexema del token a crear
	 * @param numeroLinea El n�mero de l�nea donde se encuentra el token a crear
	 * @param numeroColumna El n�mero de columna donde comienza el token a crear
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
	 * @return El n�mero de l�nea del token
	 */
	public int getNroLinea() {
		return numeroLinea;
	}
	
	/**
	 * @return El n�mero de columna del token
	 */
	public int getNroColumna() {
		return numeroColumna;
	}

	/**
	 * Retorna toda la informaci�n del token en un String
	 */
	@Override
	public String toString() {
		return "Token ["+numeroLinea+":"+numeroColumna+"] [Nombre: " + nombre + "\t\t\tLexema: " + lexema +"]" ;
	}

}
