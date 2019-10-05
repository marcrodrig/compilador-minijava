package semantico;

import lexico.Token;

public class NodoLiteral extends NodoOperando {
	private Token token;
	
	public NodoLiteral(Token token) {
		this.token = token;
	}
}
