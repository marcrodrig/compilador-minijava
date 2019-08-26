package main;

/**
 * Clase ExcepcionFormatoOr, generada por el Analizador Léxico
 * @author Rodríguez, Marcelo
 *
 */
@SuppressWarnings("serial")
public class ExcepcionFormatoOr extends Exception {
	/**
	 * El mensaje de error
	 */
	String error;
	
	/**
	 * @param mensaje El mensaje de error
	 */
	public ExcepcionFormatoOr(String mensaje) {
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
