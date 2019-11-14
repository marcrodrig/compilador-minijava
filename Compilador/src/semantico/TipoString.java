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
		GeneradorCodigo generadorCodigo = GeneradorCodigo.getInstance();
		generadorCodigo.write(".DATA");
		String nLabel = generadorCodigo.nLabel();
		generadorCodigo.write("string" + nLabel + ": DW " + token.getLexema() + ",0");
		generadorCodigo.write(".CODE");
		generadorCodigo.write("\tPUSH string" + nLabel + "\t; Apilo etiqueta del String");
	}
}
