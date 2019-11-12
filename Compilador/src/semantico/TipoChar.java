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
		int tam = token.getLexema().length();
		if (tam == 4) {
			char c = token.getLexema().charAt(2);
			if (c == 't')
				GeneradorCodigo.getInstance().write("\tPUSH 9\t; Apilo TAB");
			else
				if (c == 'n')
					GeneradorCodigo.getInstance().write("\tPUSH 10\t; Apilo ENTER");
				else
					GeneradorCodigo.getInstance().write("\tPUSH " + (int) c + "\t; Apilo el caracter " + c);
			
		} else
			GeneradorCodigo.getInstance().write("\tPUSH " + (int) token.getLexema().charAt(1) + "\t; Apilo el caracter " + token.getLexema().charAt(1));
	}
}
