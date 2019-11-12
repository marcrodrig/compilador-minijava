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
				return new TipoBoolean(new Token("boolean", token.getLexema(), token.getNroLinea(), token.getNroColumna()));
			case "charLiteral":
				return new TipoChar(new Token("char", token.getLexema(), token.getNroLinea(), token.getNroColumna()));
			case "intLiteral":
				return new TipoInt(new Token("int", token.getLexema(), token.getNroLinea(), token.getNroColumna()));
			case "stringLiteral":
				return new TipoString(new Token("String", token.getLexema(), token.getNroLinea(), token.getNroColumna()));
			default:
				return null;
		}
	}

	@Override
	protected void generar() {
		switch (token.getNombre()) {
		case "true":
		case "false":
			TipoBoolean.generar(token);
			break;
		case "charLiteral":
			TipoChar.generar(token);
			break;
		case "intLiteral":
			TipoInt.generar(token);
			break;
		case "stringLiteral":
			TipoString.generar(token);
			break;
	}
		
	}
	
}
