package semantico;

import java.util.HashMap;
import gc.GeneradorCodigo;
import lexico.Token;
import main.CompiladorMiniJava;

public class NodoVar extends NodoPrimario {
	private Token token;
	private boolean esLadoIzqAsig;
	
	public NodoVar(Token token) {
		this.token = token;
	}

	@Override
	public TipoRetorno chequear() throws ExcepcionSemantico {
		Unidad unidadActual = CompiladorMiniJava.ts.getUnidadActual();
		Tipo tipo = null;
		HashMap<String, VariableMetodo> varsLocalesParamsUnidadActual = unidadActual.getVarsLocalesParams(); 
		if (varsLocalesParamsUnidadActual.containsKey(token.getLexema()))
			tipo = varsLocalesParamsUnidadActual.get(token.getLexema()).getTipo();
		else {
			Clase claseActual = CompiladorMiniJava.ts.getClaseActual();
			VariableInstancia varIns = claseActual.getAtributoPorNombre(token.getLexema());
			if (varIns != null) {
				Unidad unidad = CompiladorMiniJava.ts.getUnidadActual();
				if (unidad instanceof Metodo) {
					Metodo metodo = (Metodo) unidad;
					if (metodo.getFormaMetodo().equals("static"))
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
		Encadenado encadenado = getEncadenado();
		Clase claseActual = CompiladorMiniJava.ts.getClaseActual();
		VariableInstancia varIns = claseActual.getAtributoPorNombre(token.getLexema());
		Unidad unidadActual = CompiladorMiniJava.ts.getUnidadActual();
		Variable varLocal = unidadActual.getBloque().getVarLocal(token.getLexema());
		Parametro param = unidadActual.getParametroPorNombre(token.getLexema());
		if (varIns != null) {
			GeneradorCodigo.getInstance().write("\tLOAD 3");
			if (!esLadoIzqAsig || encadenado != null)
				GeneradorCodigo.getInstance().write("\tLOADREF " + varIns.getOffset());
			else {
				GeneradorCodigo.getInstance().write("\tSWAP");
				GeneradorCodigo.getInstance().write("\tSTOREREF " + varIns.getOffset());
			}
		} else {
			if (varLocal != null) {
				if (!esLadoIzqAsig || encadenado != null)
					GeneradorCodigo.getInstance().write("\tLOAD " + varLocal.getOffset());
				else
					GeneradorCodigo.getInstance().write("\tSTORE " + varLocal.getOffset());
			} else { // es parámetro
				if (param != null) {
					if (!esLadoIzqAsig || encadenado != null)
						GeneradorCodigo.getInstance().write("\tLOAD " + param.getOffset());
					else
						GeneradorCodigo.getInstance().write("\tSTORE " + param.getOffset());
				}
			}
		}
		if (encadenado != null)
			encadenado.generar();
	}

}
