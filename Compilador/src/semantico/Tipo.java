package semantico;

import lexico.Token;

public abstract class Tipo extends TipoRetorno {
	private Token token;
	
	public Tipo(Token token) {
		this.token = token;
	}
		
	public String getNombre() {
		return token.getNombre();
	}

	public int getNroLinea() {
		return token.getNroLinea();
	}

	public int getNroColumna() {
		return token.getNroColumna();
	}
}
