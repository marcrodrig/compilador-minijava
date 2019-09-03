package main;

@SuppressWarnings("serial")
public class ExcepcionSintactico extends Exception {
	/**
	 * El mensaje de error de ExcepcionSintactico
	 */
	String error;
	
	/**
	 * @param mensaje El mensaje de error
	 */
	public ExcepcionSintactico(String mensaje) {
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