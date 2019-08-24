package main;

@SuppressWarnings("serial")
public class ExcepcionFormatoString extends Exception {
	String error;
	
	public ExcepcionFormatoString(String mensaje) {
		error = mensaje;
	}
	
	@Override
	public String toString() {
		return error;
	}
}