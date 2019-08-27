package main;

/**
 * Clase ExcepcionFormatoString, generada por el Analizador Léxico
 * 
 * @author Rodríguez, Marcelo
 *
 */
@SuppressWarnings("serial")
public class ExcepcionFormatoString extends Exception {
	/**
	 * El mensaje de error de ExcepcionFormatoString
	 */
	String error;

	/**
	 * @param mensaje El mensaje de error
	 */
	public ExcepcionFormatoString(String mensaje) {
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