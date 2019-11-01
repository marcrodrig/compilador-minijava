package semantico;

import gc.GeneradorCodigo;
import lexico.Token;

public class TipoString extends TipoPrimitivo {
	
	public TipoString(Token token) {
		super(token);
	}

	@Override
	public boolean esCompatible(TipoRetorno tipo) {
		return (tipo instanceof TipoString);
	}

	public static void generar(Token token) {
		GeneradorCodigo.getInstance().write(".DATA");
		String nLabel = GeneradorCodigo.getInstance().nLabel();
		GeneradorCodigo.getInstance().write("string" + nLabel + ": DW " + token.getLexema() + ",0");
		GeneradorCodigo.getInstance().write(".CODE");
		GeneradorCodigo.getInstance().write("PUSH string" + nLabel + " # Apilo etiqueta del String");
	}
}
