package main;

@SuppressWarnings("serial")
public class ExcepcionFormatoOr extends Exception {
	String error;
	
	public ExcepcionFormatoOr(String mensaje) {
		error = mensaje;
	}
	
	@Override
	public String toString() {
		return error;
	}
}
