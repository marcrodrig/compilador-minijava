package semantico;

import gc.GeneradorCodigo;

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
			throw new ExcepcionSemantico("[" + nroLinea + ":" + nroColumna	+ "] Error semántico: El tipo de la condición del if no es booleano.");
	}

	@Override
	protected void generar() {
		condicion.generar();
		String labelFinIf = "finIf" + GeneradorCodigo.getInstance().nLabel();
	    String labelElse = "else" + GeneradorCodigo.getInstance().nLabel();
		GeneradorCodigo.getInstance().write("\tBF " + labelElse);
		entonces.generar();
		GeneradorCodigo.getInstance().write("\tJUMP " + labelFinIf);
		GeneradorCodigo.getInstance().write(labelElse + ":NOP");
		if (sino != null)
			sino.generar();
		GeneradorCodigo.getInstance().write(labelFinIf + ":NOP");
	}
	
}
