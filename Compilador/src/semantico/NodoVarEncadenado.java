package semantico;

import lexico.Token;

public class NodoVarEncadenado extends Encadenado {
	private Token token;
		
	public NodoVarEncadenado(Token token, Encadenado cadena) {
		super(cadena);
		this.token = token;
	}

}
