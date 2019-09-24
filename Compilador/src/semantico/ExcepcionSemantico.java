package semantico;

@SuppressWarnings("serial")
public class ExcepcionSemantico extends Exception {
	/**
	 * El mensaje de error de ExcepcionSemantico
	 */
	String error;
	
	/**
	 * @param mensaje El mensaje de error
	 */
	public ExcepcionSemantico(String mensaje) {
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