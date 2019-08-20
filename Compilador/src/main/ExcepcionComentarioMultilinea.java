package main;

public class ExcepcionComentarioMultilinea extends Exception {
	String error;
	
	public ExcepcionComentarioMultilinea(String mensaje) {
		error = mensaje;
	}
	
	@Override
	public String toString() {
		return error;
	}
}