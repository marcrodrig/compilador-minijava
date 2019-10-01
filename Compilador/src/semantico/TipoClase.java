package semantico;

import lexico.Token;

public class TipoClase extends TipoReferencia {
	private Token token;
	
	public TipoClase(Token token) {
		this.token = token;
	}
	
	public String getNombre() {
		return token.getLexema();
	}

	public int getNroLinea() {
		return token.getNroLinea();
	}

	public int getNroColumna() {
		return token.getNroColumna();
	}
	
}