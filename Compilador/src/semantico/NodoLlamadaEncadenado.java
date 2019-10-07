package semantico;

import java.util.List;

import lexico.Token;

public class NodoLlamadaEncadenado extends Encadenado {
	private Token token;
	private List<NodoExpresion> argumentosActuales;
	
	public NodoLlamadaEncadenado(Token token, List<NodoExpresion> argumentosActuales) {
		this.token = token;
		this.argumentosActuales = argumentosActuales;
	}
	
	public List<NodoExpresion> getArgsActuales() {
		return argumentosActuales;
	}
}
