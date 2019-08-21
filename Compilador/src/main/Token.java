package main;

public class Token {
	private String nombre;
	private String lexema;
	private int nroLinea;

	public Token(String nombre, String lexema, int nroLinea) {
		this.nombre = nombre;
		this.lexema = lexema;
		this.nroLinea = nroLinea;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLexema() {
		return lexema;
	}

	public void setLexema(String lexema) {
		this.lexema = lexema;
	}

	public int getNroLinea() {
		return nroLinea;
	}

	public void setNroLinea(int nroLinea) {
		this.nroLinea = nroLinea;
	}

	@Override
	public String toString() {
		return "Token [Nombre: " + nombre + "\t\t\tLexema: " + lexema + "\t\t\tNúmero de línea: " + nroLinea + "]";
	}

}
