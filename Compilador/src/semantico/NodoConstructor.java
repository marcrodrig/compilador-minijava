package semantico;

import java.util.List;
import lexico.Token;

public class NodoConstructor extends NodoPrimario {
	private Token token;
	private List<NodoExpresion> argumentosActuales;
	
	public NodoConstructor(Token token, List<NodoExpresion> argumentosActuales) {
		this.token = token;
		this.argumentosActuales = argumentosActuales;
	}
}
