package semantico;

import lexico.Token;

public class VariableInstancia extends Variable {
	private String visibilidad;

	public VariableInstancia(Token token, Tipo tipo, String visibilidad) {
		super(token,tipo);
		this.visibilidad = visibilidad;
	}

	public String getVisibilidad() {
		return visibilidad;
	}

}
