package semantico;

import lexico.Token;

public class NodoLiteral extends NodoOperando {
	private Token token;
	
	public NodoLiteral(Token token) {
		this.token = token;
	}

	@Override
	public Tipo chequear() throws ExcepcionSemantico {
		switch (token.getNombre()) {
			case "true":
			case "false":
				return new TipoBoolean(token);
			case "charLiteral":
				return new TipoChar(new Token("char", "char", token.getNroLinea(), token.getNroColumna()));
			case "intLiteral":
				return new TipoInt(new Token("int", "int", token.getNroLinea(), token.getNroColumna()));
			case "stringLiteral":
				return new TipoChar(new Token("String", "String", token.getNroLinea(), token.getNroColumna()));
			default:
				return null;
		}
	}
	
}
