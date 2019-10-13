package semantico;

import lexico.Token;

public class NodoExpresionBinaria extends NodoExpresion {
	private Token operador;
	private NodoExpresion izq;
	private NodoExpresion der;
	
	public NodoExpresionBinaria(Token operador, NodoExpresion izq, NodoExpresion der) {
		this.operador = operador;
		this.izq = izq;
		this.der = der;
	}

	@Override
	public TipoRetorno chequear() throws ExcepcionSemantico {
		TipoRetorno tipoLadoIzq = izq.chequear();
		TipoRetorno tipoLadoDer = der.chequear();
		switch (operador.getLexema()) {
		case "+":
        case "-":
        case "*":
        case "/":
        case "%":
        	if (tipoLadoIzq.getNombre().equals("int") && tipoLadoDer.getNombre().equals("int"))
        		return new TipoInt(operador);
        	else
        		throw new ExcepcionSemantico("[" + operador.getNroLinea() + "] Error semántico: Tipos incompatibles en expresión binaria con el operador \"" + operador.getLexema() + "\".");
        case "&&":
        case "||":
        	if (tipoLadoIzq.getNombre().equals("boolean") && tipoLadoDer.getNombre().equals("boolean"))
        		return new TipoBoolean(operador);
        	else
        		throw new ExcepcionSemantico("[" + operador.getNroLinea() + "] Error semántico: Tipos incompatibles en expresión binaria con el operador \"" + operador.getLexema() + "\".");
		case "==":
		case "!=":
			if(tipoLadoIzq.esCompatible(tipoLadoDer))
				return new TipoBoolean(operador);
        	else
        		throw new ExcepcionSemantico("[" + operador.getNroLinea() + "] Error semántico: Tipos incompatibles en expresión binaria con el operador \"" + operador.getLexema() + "\".");
		default:
		return null;
	}
	}
}
