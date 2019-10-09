package semantico;

import lexico.Token;

public class TipoBoolean extends TipoPrimitivo {
	private Token token;
	
	public TipoBoolean(Token token) {
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
		return (tipo instanceof TipoBoolean);
	}
}
