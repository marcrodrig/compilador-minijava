package main;

@SuppressWarnings("serial")
public class ExcepcionCaracterInvalido extends Exception {
	String error;
	
	public ExcepcionCaracterInvalido(String mensaje) {
		error = mensaje;
	}
	
	@Override
	public String toString() {
		return error;
	}
}