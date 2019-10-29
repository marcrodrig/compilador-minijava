package semantico;

import gc.GeneradorCodigo;
import lexico.Token;

public class TipoBoolean extends TipoPrimitivo {
	
	public TipoBoolean(Token token) {
		super(token);
	}
	
	@Override
	public boolean esCompatible(TipoRetorno tipo) {
		return (tipo instanceof TipoBoolean);
	}

	public static void generar(Token token) {
		if (token.getLexema().equals("true"))
			GeneradorCodigo.getInstance().write("\tPUSH 1\t; true");
		else
			GeneradorCodigo.getInstance().write("\tPUSH 0\t; false");
	}
}
