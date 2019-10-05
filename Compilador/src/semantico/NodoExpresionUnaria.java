package semantico;

import lexico.Token;

public class NodoExpresionUnaria extends NodoExpresion {
	private NodoExpresion expresion;
	private Token operador;
	
	public NodoExpresionUnaria(NodoExpresion expresionUnaria, Token operador) {
		expresion = expresionUnaria;
		this.operador = operador;
	}

}
