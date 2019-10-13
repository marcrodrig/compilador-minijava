package semantico;

import java.util.List;

import main.Principal;

public class NodoAsignacion extends NodoSentencia {
	private List<Variable> vars;
	private NodoExpresion ladoIzquierdo;
	private NodoExpresion ladoDerecho;
	private int nroLinea;
	
	public NodoAsignacion(List<Variable> vars, NodoExpresion ladoIzquierdo, NodoExpresion ladoDerecho, int nroLinea) {
		this.vars = vars;
		this.ladoIzquierdo = ladoIzquierdo;
		this.ladoDerecho = ladoDerecho;
		this.nroLinea = nroLinea;
	}

	@Override
	protected void chequear() throws ExcepcionSemantico {
		if (vars != null) { // asignación inline en constructor/método o atributo
			Unidad unidadActual = Principal.ts.getUnidadActual();
			for (Variable var : vars) {
				if (var instanceof VarLocal) {
				if (unidadActual.getVarsParams().get(var.getNombre()) == null)
					unidadActual.insertarVarMetodo(var);
				else
					throw new ExcepcionSemantico("[" + var.getNroLinea() + ":" + var.getNroColumna() + "] Error semántico: Nombre de variable local \"" + var.getNombre() + "\" repetido a un parámetro u otra variable local.");
				} else {
						if (Principal.ts.getClaseActual().getAtributos().get(var.getNombre()) == null)
							Principal.ts.insertarAtributo(var);
						else
							throw new ExcepcionSemantico("[" + var.getNroLinea() + ":" + var.getNroColumna() + "] Error semántico: Nombre de atributo \"" + var.getNombre() + "\" repetido.");
				}
			}
			TipoRetorno tipoLadoDerecho = ladoDerecho.chequear();
			Variable primerVar = vars.get(0);
			TipoRetorno tipoAsignacionInline = primerVar.getTipo();
			if(!tipoAsignacionInline.esCompatible(tipoLadoDerecho))
				throw new ExcepcionSemantico("[" + nroLinea + "] Error semántico: Tipos incompatibles en asignación inline.");
		} else { // método lado izquierdo
			TipoRetorno tipoLadoIzquierdo = ladoIzquierdo.chequear();
			TipoRetorno tipoLadoDerecho = ladoDerecho.chequear();
			if(!tipoLadoIzquierdo.esCompatible(tipoLadoDerecho))
				throw new ExcepcionSemantico("[" + nroLinea
				+ "] Error semántico: Tipos incompatibles en asignación con una llamada a izquierda.");
		}
	}
}
