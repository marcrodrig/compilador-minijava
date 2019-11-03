package semantico;

import GCI.GenCode;

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
	
	/*
	 *  if (!exp.check().getNombre().equals("boolean")) {
            throw new Exception("El tipo de la expresion dentro del if en la linea " + linea + " no es de tipo boolean");
        }

        String finIf = "finIf" + GenCode.gen().genLabel();
        String lElse = "else" + GenCode.gen().genLabel();

        GenCode.gen().write("BF " + lElse + " # Salto si la sentencia es falsa");

        sen.check();

        GenCode.gen().write("JUMP " + finIf + " # Salto al fin del if para que no ejecute el else");
        GenCode.gen().write(lElse + ": NOP # Codigo del else");

        sen2.check();

        GenCode.gen().write(finIf + ": NOP # Etiqueta fin if");
        GenCode.gen().nl();
	 */
}
