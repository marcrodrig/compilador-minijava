package main;

public class Token {
	private String nombre;
	private String lexema;
	private int numeroLinea;
	private int numeroColumna;

	public Token(String nombre, String lexema, int numeroLinea, int numeroColumna) {
		this.nombre = nombre;
		this.lexema = lexema;
		this.numeroLinea = numeroLinea;
		this.numeroColumna = numeroColumna;
	}

	public String getNombre() {
		return nombre;
	}

	public String getLexema() {
		return lexema;
	}

	public int getNroLinea() {
		return numeroLinea;
	}
	
	public int getNroColumna() {
		return numeroColumna;
	}

	@Override
	public String toString() {
		return "Token ["+numeroLinea+":"+numeroColumna+"] [Nombre: " + nombre + "\t\t\tLexema: " + lexema +"]" ;
	}

}
