package semantico;

import lexico.Token;

public class NodoExpresionUnaria extends NodoExpresion {
	private NodoExpresion expresion;
	private Token operador;
	
	public NodoExpresionUnaria(NodoExpresion expresionUnaria, Token operador) {
		expresion = expresionUnaria;
		this.operador = operador;
	}

	@Override
	public TipoRetorno chequear() throws ExcepcionSemantico {
		TipoRetorno tipoExpresion = expresion.chequear();
		switch (operador.getLexema()) {
		case "+":
		case "-":
			if (tipoExpresion.getNombre().equals("int"))
				return new TipoInt(operador);
			else
				throw new ExcepcionSemantico("[" + operador.getNroLinea()
						+ "] Error semántico: Tipos incompatibles en expresión unaria con el operador \""
						+ operador.getLexema() + "\".");
		case "!":
			if (tipoExpresion.getNombre().equals("boolean"))
				return new TipoBoolean(operador);
			else
				throw new ExcepcionSemantico("[" + operador.getNroLinea()
						+ "] Error semántico: Tipos incompatibles en expresión unaria con el operador \""
						+ operador.getLexema() + "\".");
		default:
			return null;
		}
	}

}
