package semantico;

public class NodoExpresionParentizada extends NodoPrimario {
	private NodoExpresion expresion;
	
	public NodoExpresionParentizada(NodoExpresion expresion) {
		this.expresion = expresion;
	}

	@Override
	public TipoRetorno chequear() throws ExcepcionSemantico {
		TipoRetorno tipoExpresion = expresion.chequear();
		Encadenado encadenado = getEncadenado();
		if (encadenado == null)
			return tipoExpresion;
		else
			return encadenado.chequear(tipoExpresion);
	}

	@Override
	protected void generar() {
		expresion.generar();
		// ver encadenado
	}
	
}
