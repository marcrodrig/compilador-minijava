package semantico;

import lexico.Token;

public class TipoInt extends TipoPrimitivo {
	
	public TipoInt(Token token) {
		super(token);
	}

	@Override
	public boolean esCompatible(TipoRetorno tipo) {
		return (tipo instanceof TipoInt);
	}
	
}
