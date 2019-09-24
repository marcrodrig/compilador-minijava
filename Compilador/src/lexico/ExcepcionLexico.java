package lexico;

@SuppressWarnings("serial")
public class ExcepcionLexico extends Exception {

	/**
	 * El mensaje de error de ExcepcionLexico
	 */
	String error;
	
	/**
	 * @param mensaje El mensaje de error
	 */
	public ExcepcionLexico(String mensaje) {
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
