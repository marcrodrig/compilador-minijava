package semantico;

import gc.GeneradorCodigo;

public class NodoSentenciaSimple extends NodoSentencia {
	private NodoExpresion expresion;
	private TipoRetorno tipoExp;
	
	public NodoSentenciaSimple(NodoExpresion expresion) {
		this.expresion = expresion;
	}

	@Override
	protected void chequear() throws ExcepcionSemantico {
		tipoExp = expresion.chequear();
	}

	@Override
	protected void generar() {
		expresion.generar();
		// ver encadenado??
		if (!tipoExp.getNombre().equals("void"))
			GeneradorCodigo.getInstance().write("\tPOP");
	}
}
