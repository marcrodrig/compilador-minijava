package main;

public class ExcepcionFormatoCaracter extends Exception {
	String error;
	
	public ExcepcionFormatoCaracter(String mensaje) {
		error = mensaje;
	}
	
	@Override
	public String toString() {
		return error;
	}
}
