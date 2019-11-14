package semantico;

import gc.GeneradorCodigo;
import lexico.Token;

public class TipoInt extends TipoPrimitivo {
	
	public TipoInt(Token token) {
		super(token);
	}

	@Override
	public boolean esCompatible(TipoRetorno tipo) {
		return (tipo instanceof TipoInt);
	}
	
	public static void generar(Token token) {
		GeneradorCodigo.getInstance().write("\tPUSH " + token.getLexema() + "\t; Apilo literal entero " + token.getLexema());
	}
}
