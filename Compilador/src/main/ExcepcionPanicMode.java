package main;

@SuppressWarnings("serial")
public class ExcepcionPanicMode extends Exception {
	/**
	 * El mensaje de error de ExcepcionPanicMode
	 */
	String error;
	
	/**
	 * @param mensaje El mensaje de error
	 */
	public ExcepcionPanicMode(String mensaje) {
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
