package semantico;

import lexico.Token;
import main.Principal;

public class NodoThis extends NodoPrimario {
	private Token token;
	
	public NodoThis(Token token) {
		this.token = token;
	}

	@Override
	public TipoRetorno chequear() throws ExcepcionSemantico {
		Clase clase = Principal.ts.getUnidadActual().declaradaEn();
		TipoRetorno tipoClase = new TipoClase(new Token("IdClase", clase.getNombre(), token.getNroLinea(), token.getNroColumna()));
		if (getEncadenado() == null)
			return tipoClase;
		else
			return getEncadenado().chequear(tipoClase);
	}
}
