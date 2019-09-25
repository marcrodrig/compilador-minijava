package semantico;

import lexico.Token;

public class VariableInstancia {
	private Token token;
	private Tipo tipo;
	private String visibilidad;
	
	public VariableInstancia(Token token, Tipo tipo, String visibilidad) {
		this.token = token;
		this.tipo = tipo;
		this.visibilidad = visibilidad;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public String getVisibilidad() {
		return visibilidad;
	}
	
	public String getNombre() {
		return token.getLexema();
	}

	public Token getToken() {
		return token;
	}
}
