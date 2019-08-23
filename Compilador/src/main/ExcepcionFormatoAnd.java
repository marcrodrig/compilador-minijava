package main;

@SuppressWarnings("serial")
public class ExcepcionFormatoAnd extends Exception {
	String error;
	
	public ExcepcionFormatoAnd(String mensaje) {
		error = mensaje;
	}
	
	@Override
	public String toString() {
		return error;
	}
}