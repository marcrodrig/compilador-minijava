package semantico;

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
			throw new ExcepcionSemantico("[" + nroLinea + ":" + nroColumna	+ "] Error sem�ntico: El tipo de la condici�n del while no es booleano.");
	}
}
