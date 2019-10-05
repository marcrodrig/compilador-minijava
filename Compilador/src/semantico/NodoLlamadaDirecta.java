package semantico;

import java.util.List;
import lexico.Token;

public class NodoLlamadaDirecta extends NodoPrimario {
	private Token token;
	private List<NodoExpresion> argumentosActuales;
	
	public NodoLlamadaDirecta(Token token, List<NodoExpresion> argumentosActuales) {
		this.token = token;
		this.argumentosActuales = argumentosActuales;
	}
}
