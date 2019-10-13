package semantico;

import java.util.List;

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
			if (unidadActual.getVarsParams().get(var.getNombre()) == null)
				unidadActual.insertarVarMetodo(var);
			else
				throw new ExcepcionSemantico("[" + var.getNroLinea() + ":" + var.getNroColumna() + "] Error sem�ntico: Nombre de variable local \"" + var.getNombre() + "\" repetido a un par�metro u otra variable local.");
		}
	}
	
}
