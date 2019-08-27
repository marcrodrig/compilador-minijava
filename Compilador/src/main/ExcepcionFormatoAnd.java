package main;

/**
 * Clase ExcepcionFormatoAnd, generada por el Analizador Léxico
 * @author Rodríguez, Marcelo
 *
 */
@SuppressWarnings("serial")
public class ExcepcionFormatoAnd extends Exception {
	/**
	 * El mensaje de error de ExcepcionFormatoAnd
	 */
	String error;
	
	/**
	 * @param mensaje El mensaje de error
	 */
	public ExcepcionFormatoAnd(String mensaje) {
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