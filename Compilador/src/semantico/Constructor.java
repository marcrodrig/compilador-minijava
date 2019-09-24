package semantico;

import java.util.ArrayList;
import java.util.HashMap;

import lexico.Token;

public class Constructor {
	//private String nombre;
	private Token token;
	private HashMap<String, Parametro> parametros;
	
	public Constructor(Token token, HashMap<String, Parametro> parametros) {
		this.token = token;
		this.parametros = parametros;
	}
	
	public String getNombre() {
		return token.getLexema();
	}

	public HashMap<String, Parametro> getParametros() {
		return parametros;
	}
	
	// posicion va desde 1
	public Parametro getParametro(int posicion) {
		ArrayList<Parametro> listaParametros = new ArrayList<Parametro>(parametros.values());
		return listaParametros.get(posicion-1);
	}

	public Token getToken() {
		return token;
	}
}
