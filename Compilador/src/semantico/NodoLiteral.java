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
				return new TipoChar(token);
			case "intLiteral":
				return new TipoInt(token);
			case "stringLiteral":
				return new TipoString(token);
			default:
				return null;
		}
	}
	
}
