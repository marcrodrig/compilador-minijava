package main;

public class ExcepcionAnd extends Exception {
	String error;
	
	public ExcepcionAnd(String mensaje) {
		error = mensaje;
	}
	
	@Override
	public String toString() {
		return error;
	}
}
