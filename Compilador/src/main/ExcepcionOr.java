package main;

public class ExcepcionOr extends Exception {
	String error;
	
	public ExcepcionOr(String mensaje) {
		error = mensaje;
	}
	
	@Override
	public String toString() {
		return error;
	}
}
