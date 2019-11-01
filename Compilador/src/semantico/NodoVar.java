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
		HashMap<String, Variable> varsParamsUnidadActual = unidadActual.getVarsParams(); 
		if (varsParamsUnidadActual.containsKey(token.getLexema()))
			tipo = varsParamsUnidadActual.get(token.getLexema()).getTipo();
		else {
			Clase claseActual = Principal.ts.getClaseActual();
			VariableInstancia varIns = claseActual.getAtributoPorNombre(token.getLexema());
			if (varIns != null) { // && !varIns.getVisibilidad().equals("private")) {
				Unidad unidad = Principal.ts.getUnidadActual();
				if (unidad instanceof Metodo) {
					Metodo metodo = (Metodo) unidad;
					if (metodo.getFormaMetodo().equals("static")) //&& varIns instanceof VariableInstancia)
						throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
						+ "] Error semántico: En método estático no se puede acceder a un atributo de instancia.");
				}
				tipo = varIns.getTipo();
			}
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
