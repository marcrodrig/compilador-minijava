package semantico;

import lexico.Token;

public class Parametro extends VariableMetodo {
	private int posicion;

	public Parametro(Token token, Tipo tipo) {
		super(token,tipo);
	}

	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion - 1;
	}

}