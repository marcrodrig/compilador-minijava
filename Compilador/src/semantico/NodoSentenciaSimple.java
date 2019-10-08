package semantico;

public class NodoSentenciaSimple extends NodoSentencia {
	private NodoExpresion expresion;
	
	public NodoSentenciaSimple(NodoExpresion expresion) {
		this.expresion = expresion;
	}

	@Override
	protected void chequear() throws ExcepcionSemantico {
		expresion.chequear();
	}
}
