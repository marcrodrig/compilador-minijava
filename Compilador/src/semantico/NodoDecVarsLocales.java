package semantico;

import java.util.List;
import gc.GeneradorCodigo;
import main.Principal;

public class NodoDecVarsLocales extends NodoSentencia {
	private List<Variable> vars;
	
	public NodoDecVarsLocales(List<Variable> vars) {
		this.vars = vars;
	}

	@Override
	protected void chequear() throws ExcepcionSemantico {
		Unidad unidadActual = Principal.ts.getUnidadActual();
		for (Variable var : vars) {
			Tipo tipoVarLocal = var.getTipo();
			if (tipoVarLocal instanceof TipoClase) {
				if (Principal.ts.getClase(tipoVarLocal.getNombre()) == null)
					throw new ExcepcionSemantico("[" + tipoVarLocal.getNroLinea() + ":" + tipoVarLocal.getNroColumna()
							+ "] Error semántico: El tipo clase \"" + tipoVarLocal.getNombre() + "\" no está definido.");
			}
			if (unidadActual.getVarsParams().get(var.getNombre()) == null)
				unidadActual.insertarVarMetodo(var);
			else
				throw new ExcepcionSemantico("[" + var.getNroLinea() + ":" + var.getNroColumna() + "] Error semántico: Nombre de variable local \"" + var.getNombre() + "\" repetido a un parámetro u otra variable local.");
		}
	}

	@Override
	protected void generar() {
		GeneradorCodigo.getInstance().write("RMEM " + vars.size() + "\t; Reservo espacio para vars locales");
	}
	
}
