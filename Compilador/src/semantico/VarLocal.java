package semantico;

import lexico.Token;

public class VarLocal extends VariableMetodo {

	private boolean memoriaYaReservada;
	
	public VarLocal(Token token, Tipo tipo) {
		super(token, tipo);
	}

	public boolean memoriaReservada() {
		return memoriaYaReservada;
	}
	
	public void setMR() {
		memoriaYaReservada = true;
	}
	
}
