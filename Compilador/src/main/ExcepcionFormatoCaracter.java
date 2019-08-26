package main;


/**
 * Clase ExcepcionFormatoCaracter, generada por el Analizador Léxico
 * @author Rodríguez, Marcelo
 *
 */
@SuppressWarnings("serial")
public class ExcepcionFormatoCaracter extends Exception {
	/**
	 * El mensaje de error de ExcepcionFormatoCaracter
	 */
	String error;
	
	/**
	 * @param mensaje El mensaje de error
	 */
	public ExcepcionFormatoCaracter(String mensaje) {
		error = mensaje;
	}
	
	/**
	 * Retorna el mensaje de error
	 */
	@Override
	public String toString() {
		return error;
	}
}
