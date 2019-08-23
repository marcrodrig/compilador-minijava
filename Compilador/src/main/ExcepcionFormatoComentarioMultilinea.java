package main;

@SuppressWarnings("serial")
public class ExcepcionFormatoComentarioMultilinea extends Exception {
	String error;
	
	public ExcepcionFormatoComentarioMultilinea(String mensaje) {
		error = mensaje;
	}
	
	@Override
	public String toString() {
		return error;
	}
}