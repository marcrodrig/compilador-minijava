package semantico;

import java.util.HashMap;

import gc.GeneradorCodigo;
import lexico.Token;
import main.Principal;

public class NodoVar extends NodoPrimario {
	private Token token;
	boolean esLadoIzqAsig;
	
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

	public void setEsLadoIzqAsig() {
		esLadoIzqAsig = true;
	}

	@Override
	protected void generar() {
		/*Si evar es variable de instancia
			Gen LOAD 3
			Si(no es ladoIzquierdoAsig o cadena no es nulo)
				Gen LOADREF evar.offset
			Sino
				Gen SWAP
				Gen STOREREF evar.offset
		Sino si evar es parámetro/local
			Si(no es ladoIzquierdoAsig o cadena no es nulo
				Gen LOAD evar.offset
			Sino
				Gen STORE evar.offset
		Si cadena es no nulo
			cadena.generar*/
		Encadenado encadenado = getEncadenado();
		Clase claseActual = Principal.ts.getClaseActual();
		VariableInstancia varIns = claseActual.getAtributoPorNombre(token.getLexema());
		if (varIns != null) {
			GeneradorCodigo.getInstance().write("\tLOAD 3");
			if (!esLadoIzqAsig || encadenado != null)
				GeneradorCodigo.getInstance().write("\tLOADREF " + varIns.getOffset());
			else {
				GeneradorCodigo.getInstance().write("\tSWAP");
				GeneradorCodigo.getInstance().write("\tSTOREREF " + varIns.getOffset());
			}
		} else {
			Unidad unidadActual = Principal.ts.getUnidadActual();
			Variable varParam = unidadActual.getVarParamPorNombre(token.getLexema());
			if (varParam != null) {
				if (!esLadoIzqAsig || encadenado != null)
					GeneradorCodigo.getInstance().write("\tLOAD " + varParam.getOffset());
				else {
					GeneradorCodigo.getInstance().write("\tSTORE " + varParam.getOffset());
				}
			}
		}
		if (encadenado != null)
			encadenado.generar();
	}
	
}
