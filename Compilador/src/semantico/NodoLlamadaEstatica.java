package semantico;

import java.util.List;

import lexico.Token;

public class NodoLlamadaEstatica extends NodoPrimario {
	private Token tokenIdClase;
	private Token tokenIdMetVar;
	private List<NodoExpresion> argsActuales;

	public NodoLlamadaEstatica(Token tokenIdClase, Token tokenIdMetVar, List<NodoExpresion> argsActuales) {
		this.tokenIdClase = tokenIdClase;
		this.tokenIdMetVar = tokenIdMetVar;
		this.argsActuales = argsActuales;
	}
	
}
