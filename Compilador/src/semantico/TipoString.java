package semantico;

import lexico.Token;

public class TipoString extends TipoPrimitivo {
	
	public TipoString(Token token) {
		super(token);
	}

	@Override
	public boolean esCompatible(TipoRetorno tipo) {
		return (tipo instanceof TipoString);
	}
}
