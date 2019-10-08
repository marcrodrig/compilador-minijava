package semantico;

import main.Principal;

public class NodoRetorno extends NodoSentencia {
	private NodoExpresion expresion;
	
	public NodoRetorno(NodoExpresion expresion) {
		this.expresion = expresion;
	}

	@Override
	protected void chequear() throws ExcepcionSemantico {
		Unidad unidadActual = Principal.ts.getUnidadActual();
		if (unidadActual instanceof Constructor) {
			throw new ExcepcionSemantico("[" + unidadActual.getNroLinea() + ":" + unidadActual.getNroColumna()
			+ "] Error semántico: El constructor " + unidadActual.getNombre() + " tiene una sentencia return.");
		}
		else {
			Metodo metodo = (Metodo) unidadActual;
			TipoRetorno tipoMetodo = metodo.getTipo();
			if (expresion == null) {
				if(!tipoMetodo.getNombre().equals("void"))
					throw new ExcepcionSemantico("[" + unidadActual.getNroLinea() + ":" + unidadActual.getNroColumna()
					+ "] Error semántico: El método " + unidadActual.getNombre() + " debe tener tipo de retorno void.");
			} else {
			TipoRetorno tipoExpresion = expresion.chequear();
			if (!tipoMetodo.conformaTipo(tipoExpresion))
				throw new ExcepcionSemantico("[" + unidadActual.getNroLinea() + ":" + unidadActual.getNroColumna()
				+ "] Error semántico: El método " + unidadActual.getNombre() + " debe tener tipo de retorno " + tipoExpresion.getNombre() + ".");
			}
		}
	}
}
