package semantico;

import java.util.List;
import main.CompiladorMiniJava;

public class NodoDecVarsLocales extends NodoSentencia {
	private List<Variable> vars;
	
	public NodoDecVarsLocales(List<Variable> vars) {
		this.vars = vars;
		for (Variable v : vars)
			if (!(v instanceof VariableInstancia))
				CompiladorMiniJava.ts.getBloqueActual().agregarVariableLocal(v);
	}

	@Override
	protected void chequear() throws ExcepcionSemantico {
		Unidad unidadActual = CompiladorMiniJava.ts.getUnidadActual();
		for (Variable var : vars) {
			Tipo tipoVarLocal = var.getTipo();
			if (tipoVarLocal instanceof TipoClase) {
				if (CompiladorMiniJava.ts.getClase(tipoVarLocal.getNombre()) == null)
					throw new ExcepcionSemantico("[" + tipoVarLocal.getNroLinea() + ":" + tipoVarLocal.getNroColumna()
							+ "] Error semántico: El tipo clase \"" + tipoVarLocal.getNombre() + "\" no está definido.");
			}
			if (unidadActual.getVarsLocalesParams().get(var.getNombre()) == null) {
				VariableMetodo v = (VariableMetodo) var;
				unidadActual.insertarVariableMetodo(v);
			}
			else
				throw new ExcepcionSemantico("[" + var.getNroLinea() + ":" + var.getNroColumna() + "] Error semántico: Nombre de variable local \"" + var.getNombre() + "\" repetido a un parámetro u otra variable local.");
		}
	}

	@Override
	protected void generar() {
		// Nada, reservo el espacio total anteriormente
	}
	
}
