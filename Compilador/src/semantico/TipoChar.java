package semantico;

import gc.GeneradorCodigo;
import lexico.Token;

public class TipoChar extends TipoPrimitivo {
	
	public TipoChar(Token token) {
		super(token);
	}
	
	@Override
	public boolean esCompatible(TipoRetorno tipo) {
		return (tipo instanceof TipoChar);
	}

	public static void generar(Token token) {
		/*
		 * chequear bien todos los caracteres, con \
		 */
		GeneradorCodigo.getInstance().write("\tPUSH " + (int) token.getLexema().charAt(1) + "\t; Apilo el caracter " + token.getLexema().charAt(1));
	}
}
