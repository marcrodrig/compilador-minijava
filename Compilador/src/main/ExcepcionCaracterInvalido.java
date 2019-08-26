package main;

/**
 * Clase ExcepcionCaracterInvalido, generada por el Analizador Léxico
 * @author Rodríguez, Marcelo
 *
 */
@SuppressWarnings("serial")
public class ExcepcionCaracterInvalido extends Exception {
	/**
	 * El mensaje de eror de ExcepcionCaracterInvalido
	 */
	String error;
	
	/**
	 * @param mensaje El mensaje de error
	 */
	public ExcepcionCaracterInvalido(String mensaje) {
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