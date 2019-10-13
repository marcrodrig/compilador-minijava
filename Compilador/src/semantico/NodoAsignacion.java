package semantico;

import java.util.List;

import main.Principal;

public class NodoAsignacion extends NodoSentencia {
	private List<Variable> varsLocales;
	private NodoExpresion ladoIzquierdo;
	private NodoExpresion ladoDerecho;
	private int nroLinea;
	
	public NodoAsignacion(List<Variable> varsLocales, NodoExpresion ladoIzquierdo, NodoExpresion ladoDerecho, int nroLinea) {
		this.varsLocales = varsLocales;
		this.ladoIzquierdo = ladoIzquierdo;
		this.ladoDerecho = ladoDerecho;
		this.nroLinea = nroLinea;
	}

	@Override
	protected void chequear() throws ExcepcionSemantico {
		if (varsLocales != null) { // asignación inline
			Unidad unidadActual = Principal.ts.getUnidadActual();
			for (Variable varLocal : varsLocales) {
				if (unidadActual.getVarsParams().get(varLocal.getNombre()) == null)
					unidadActual.insertarVarMetodo(varLocal);
				else
					throw new ExcepcionSemantico("[" + varLocal.getNroLinea() + ":" + varLocal.getNroColumna() + "] Error semántico: Nombre de variable local \"" + varLocal.getNombre() + "\" repetido a un parámetro u otra variable local.");
			}
			TipoRetorno tipoLadoDerecho = ladoDerecho.chequear();
			Variable primerVar = varsLocales.get(0);
			TipoRetorno tipoAsignacionInline = primerVar.getTipo();
			if(!tipoAsignacionInline.esCompatible(tipoLadoDerecho))
				throw new ExcepcionSemantico("[" + nroLinea
				+ "] Error semántico: Tipos incompatibles en asignación inline.");
		} else { // método lado izquierdo
			TipoRetorno tipoLadoIzquierdo = ladoIzquierdo.chequear();
			TipoRetorno tipoLadoDerecho = ladoDerecho.chequear();
			if(!tipoLadoIzquierdo.esCompatible(tipoLadoDerecho))
				throw new ExcepcionSemantico("[" + nroLinea
				+ "] Error semántico: Tipos incompatibles en asignación con una llamada a izquierda.");
		}
	}
}
