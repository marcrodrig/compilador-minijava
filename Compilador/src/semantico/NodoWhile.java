package semantico;

import gc.GeneradorCodigo;

public class NodoWhile extends NodoSentencia {
	private int nroLinea;
	private int nroColumna;
	private NodoExpresion condicion;
	private NodoSentencia sentencia;

	public NodoWhile(int nroLinea, int nroColumna, NodoExpresion condicion, NodoSentencia sentencia) {
		this.nroLinea = nroLinea;
		this.nroColumna = nroColumna;
		this.condicion = condicion;
		this.sentencia = sentencia;
	}
	
	@Override
	protected void chequear() throws ExcepcionSemantico {
		TipoRetorno tipoCondicion = condicion.chequear();
		if (tipoCondicion instanceof TipoBoolean) {
			sentencia.chequear();
		}
		else 
			throw new ExcepcionSemantico("[" + nroLinea + ":" + nroColumna	+ "] Error semántico: El tipo de la condición del while no es booleano.");
	}

	@Override
	protected void generar() {
		String labelWhile = "while" + GeneradorCodigo.getInstance().nLabel();
		GeneradorCodigo.getInstance().write(labelWhile + ":NOP");
		condicion.generar();
		String labelFinWhile = "finWhile" + GeneradorCodigo.getInstance().nLabel();
		GeneradorCodigo.getInstance().write("\tBF " + labelFinWhile);
		sentencia.generar();
		GeneradorCodigo.getInstance().write("\tJUMP " + labelWhile);
		GeneradorCodigo.getInstance().write(labelFinWhile + ":NOP");
	}
}
