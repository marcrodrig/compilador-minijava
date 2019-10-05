package semantico;

import lexico.Token;

public class NodoVar extends NodoPrimario {
	private Token token;
	
	public NodoVar(Token token) {
		this.token = token;
	}
}
