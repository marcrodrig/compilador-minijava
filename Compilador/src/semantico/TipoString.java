package semantico;

import lexico.Token;

public class TipoString extends TipoPrimitivo {
	private Token token;
	
	public TipoString(Token token) {
		this.token = token;
	}
	
	public String getNombreTipo() {
		return token.getLexema();
	}
	
	public String getNombre() {
		return token.getLexema();
	}

	public int getNroLinea() {
		return token.getNroLinea();
	}

	public int getNroColumna() {
		return token.getNroColumna();
	}
	
	@Override
	public boolean esCompatible(TipoRetorno tipo) {
		return (tipo instanceof TipoString);
	}
}
