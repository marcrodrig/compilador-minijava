package semantico;

import java.util.List;

import lexico.Token;

public class NodoLlamadaEncadenado extends Encadenado {
	private Token token;
	private List<NodoExpresion> argumentosActuales;
	
	public NodoLlamadaEncadenado(Encadenado cadena, Token token, List<NodoExpresion> argumentosActuales) {
		super(cadena);
		this.token = token;
		this.argumentosActuales = argumentosActuales;
	}
}
