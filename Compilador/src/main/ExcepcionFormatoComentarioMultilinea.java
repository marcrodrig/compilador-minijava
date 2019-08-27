package main;

/**
 * Clase ExcepcionFormatoComentarioMultilinea, generada por el Analizador Léxico
 * 
 * @author Rodríguez, Marcelo
 *
 */
@SuppressWarnings("serial")
public class ExcepcionFormatoComentarioMultilinea extends Exception {
	/**
	 * El mensaje de error de ExcepcionFormatoComentarioMultilinea
	 */
	String error;

	/**
	 * @param mensaje El mensaje de error
	 */
	public ExcepcionFormatoComentarioMultilinea(String mensaje) {
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