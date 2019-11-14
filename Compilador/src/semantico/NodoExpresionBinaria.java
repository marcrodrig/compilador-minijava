package semantico;

import gc.GeneradorCodigo;
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
		TipoRetorno tipoLadoIzq = null;
		if(izq != null)
			tipoLadoIzq = izq.chequear();
		TipoRetorno tipoLadoDer = null;
		if(der != null)
			tipoLadoDer = der.chequear();
		switch (operador.getLexema()) {
			case "+":
			case "-":
			case "*":
			case "/":
			case "%":
				if (tipoLadoIzq instanceof TipoInt && tipoLadoDer instanceof TipoInt)
					return new TipoInt(operador);
				else
					throw new ExcepcionSemantico("[" + operador.getNroLinea()
							+ "] Error semántico: Tipos incompatibles en expresión binaria con el operador \""
							+ operador.getLexema() + "\".");
			case "&&":
			case "||":
				if (tipoLadoIzq instanceof TipoBoolean && tipoLadoDer instanceof TipoBoolean)
					return new TipoBoolean(operador);
				else
					throw new ExcepcionSemantico("[" + operador.getNroLinea()
							+ "] Error semántico: Tipos incompatibles en expresión binaria con el operador \""
							+ operador.getLexema() + "\".");
			case "==":
			case "!=":
				if (tipoLadoIzq.esCompatible(tipoLadoDer))
					return new TipoBoolean(operador);
				else
					throw new ExcepcionSemantico("[" + operador.getNroLinea()
							+ "] Error semántico: Tipos incompatibles en expresión binaria con el operador \""
							+ operador.getLexema() + "\".");
			case "<":
			case ">":
			case "<=":
			case ">=":
				if (tipoLadoIzq instanceof TipoInt && tipoLadoDer instanceof TipoInt)
					return new TipoBoolean(operador);
				else
					throw new ExcepcionSemantico("[" + operador.getNroLinea()
							+ "] Error semántico: Tipos incompatibles en expresión binaria con el operador \""
							+ operador.getLexema() + "\".");
			default:
				return null;
			}
	}

	@Override
	protected void generar() {
		izq.generar();
		der.generar();
		switch (operador.getLexema()) {
			case "+": {
				GeneradorCodigo.getInstance().write("\tADD\t; Suma");
				break;
			}
			case "-": {
				GeneradorCodigo.getInstance().write("\tSUB\t; Resta");
				break;
			}
			case "*": {
				GeneradorCodigo.getInstance().write("\tMUL\t; Producto");
				break;
			}
			case "/": {
				GeneradorCodigo.getInstance().write("\tDIV\t; Cociente");
				break;
			}
			case "%": {
				GeneradorCodigo.getInstance().write("\tMOD\t; Modulo");
				break;
			}
			case "&&": {
				GeneradorCodigo.getInstance().write("\tAND\t; &&");
				break;
			}
			case "||": {
				GeneradorCodigo.getInstance().write("\tOR\t; ||");
				break;
			}
			case "==": {
				GeneradorCodigo.getInstance().write("\tEQ\t; Igual");
				break;
			}
			case "!=": {
				GeneradorCodigo.getInstance().write("\tGT\t; Distinto");
				break;
			}
			case "<": {
				GeneradorCodigo.getInstance().write("\tLT\t; Menor");
				break;
			}
			case ">": {
				GeneradorCodigo.getInstance().write("\tGT\t; Mayor");
				break;
			}
			case "<=": {
				GeneradorCodigo.getInstance().write("\tLE\t; Menor o igual");
				break;
			}
			case ">=": {
				GeneradorCodigo.getInstance().write("\tGE\t; Mayor o igual");
				break;
			}
		}
	}
}
