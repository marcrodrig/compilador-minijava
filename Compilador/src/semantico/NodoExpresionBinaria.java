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
}
