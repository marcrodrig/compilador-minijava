package semantico;

import java.util.HashMap;

import lexico.Token;
import main.Principal;

public class NodoVar extends NodoPrimario {
	private Token token;
	
	public NodoVar(Token token) {
		this.token = token;
	}

	@Override
	public TipoRetorno chequear() throws ExcepcionSemantico {
		Unidad unidadActual = Principal.ts.getUnidadActual();
		Tipo tipo = null;
		HashMap<String, VariableMetodo> varsParamsUnidadActual = unidadActual.getVarsParams(); 
		if (varsParamsUnidadActual.containsKey(token.getLexema()))
			tipo = varsParamsUnidadActual.get(token.getLexema()).getTipo();
		else {
			Clase claseActual = Principal.ts.getClaseActual();
			VariableInstancia varIns = claseActual.getAtributoPorNombre(token.getLexema());
			if (varIns != null && !varIns.getVisibilidad().equals("private"))
				tipo = varIns.getTipo();
			else
				throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
				+ "] Error semántico: Variable \"" + token.getLexema() + "\" inválida en " + unidadActual.getNombre() + ".");
		}
		if (getEncadenado() == null)
			return tipo;
		else
			return getEncadenado().chequear(tipo);
	}
}
