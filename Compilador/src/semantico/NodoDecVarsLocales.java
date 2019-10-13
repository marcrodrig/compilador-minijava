package semantico;

import java.util.List;

import main.Principal;

public class NodoDecVarsLocales extends NodoSentencia {
	private List<Variable> varsLocales;
	
	public NodoDecVarsLocales(List<Variable> varsLocales) {
		this.varsLocales = varsLocales;
	}

	@Override
	protected void chequear() throws ExcepcionSemantico {
		Unidad unidadActual = Principal.ts.getUnidadActual();
		for (Variable varLocal : varsLocales) {
			if (unidadActual.getVarsParams().get(varLocal.getNombre()) == null)
				unidadActual.insertarVarMetodo(varLocal);
			else
				throw new ExcepcionSemantico("[" + varLocal.getNroLinea() + ":" + varLocal.getNroColumna() + "] Error semántico: Nombre de variable local \"" + varLocal.getNombre() + "\" repetido a un parámetro u otra variable local.");
		}
	}
	
}
