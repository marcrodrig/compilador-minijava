package semantico;

import lexico.Token;

public class TipoChar extends TipoPrimitivo {
	
	public TipoChar(Token token) {
		super(token);
	}
	
	@Override
	public boolean esCompatible(TipoRetorno tipo) {
		return (tipo instanceof TipoChar);
	}
}
