package semantico;

import lexico.Token;

public class TipoBoolean extends TipoPrimitivo {
	
	public TipoBoolean(Token token) {
		super(token);
	}
	
	@Override
	public boolean esCompatible(TipoRetorno tipo) {
		return (tipo instanceof TipoBoolean);
	}
}
