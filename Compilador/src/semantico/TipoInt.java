package semantico;

import lexico.Token;

public class TipoInt extends TipoPrimitivo {
	private Token token;
	
	public TipoInt(Token token) {
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
