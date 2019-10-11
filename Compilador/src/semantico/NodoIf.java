package semantico;

public class NodoIf extends NodoSentencia {
	private int nroLinea;
	private int nroColumna;
	private NodoExpresion condicion;
	private NodoSentencia entonces;
	private NodoSentencia sino;
	
	public NodoIf(int nroLinea, int nroColumna, NodoExpresion condicion, NodoSentencia entonces, NodoSentencia sino) {
		this.nroLinea = nroLinea;
		this.nroColumna = nroColumna;
		this.condicion = condicion;
		this.entonces = entonces;
		this.sino = sino;
	}

	@Override
	protected void chequear() throws ExcepcionSemantico {
		TipoRetorno tipoCondicion = condicion.chequear();
		if (tipoCondicion instanceof TipoBoolean) {
			entonces.chequear();
			if (sino != null)
				sino.chequear();
		}
		else 
			throw new ExcepcionSemantico("[" + nroLinea + ":" + nroColumna	+ "] Error sem�ntico: El tipo de la condici�n del if no es booleano.");
	}
}
