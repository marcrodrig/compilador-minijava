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
		if (vars != null) { // asignaci�n inline en constructor/m�todo o atributo
			Unidad unidadActual = Principal.ts.getUnidadActual();
			for (Variable var : vars) {
				Tipo tipoVarLocal = var.getTipo();
				if (tipoVarLocal instanceof TipoClase) {
					if (Principal.ts.getClase(tipoVarLocal.getNombre()) == null)
						throw new ExcepcionSemantico("[" + tipoVarLocal.getNroLinea() + ":" + tipoVarLocal.getNroColumna()
								+ "] Error sem�ntico: El tipo clase \"" + tipoVarLocal.getNombre() + "\" no est� definido.");
				}
				if (var instanceof VarLocal) {
				if (unidadActual.getVarsParams().get(var.getNombre()) == null)
					unidadActual.insertarVarMetodo(var);
				else
					throw new ExcepcionSemantico("[" + var.getNroLinea() + ":" + var.getNroColumna() + "] Error sem�ntico: Nombre de variable local \"" + var.getNombre() + "\" repetido a un par�metro u otra variable local.");
				} else {
						if (Principal.ts.getClaseActual().getAtributos().get(var.getNombre()) == null)
							Principal.ts.insertarAtributo(var);
						else
							throw new ExcepcionSemantico("[" + var.getNroLinea() + ":" + var.getNroColumna() + "] Error sem�ntico: Nombre de atributo \"" + var.getNombre() + "\" repetido.");
				}
			}
			TipoRetorno tipoLadoDerecho = ladoDerecho.chequear();
			Variable primerVar = vars.get(0);
			TipoRetorno tipoAsignacionInline = primerVar.getTipo();
			if(!tipoAsignacionInline.esCompatible(tipoLadoDerecho))
				throw new ExcepcionSemantico("[" + nroLinea + "] Error sem�ntico: Tipos incompatibles en asignaci�n inline.");
		} else { // m�todo lado izquierdo
			if (ladoIzquierdo instanceof NodoLlamadaDirecta) {
				NodoLlamadaDirecta nodo = (NodoLlamadaDirecta) ladoIzquierdo;
			if (nodo.getEncadenado() == null)
				throw new ExcepcionSemantico("[" + nodo.getNroLinea() + ":" + nodo.getNroColumna()
				+ "] Error sem�ntico: No se puede asignar un valor a un m�todo.");
			}
			if (ladoIzquierdo instanceof NodoLlamadaEstatica) {
				NodoLlamadaEstatica nodo = (NodoLlamadaEstatica) ladoIzquierdo;
			if (nodo.getEncadenado() == null)
				throw new ExcepcionSemantico("[" + nodo.getNroLinea() + ":" + nodo.getNroColumna()
				+ "] Error sem�ntico: No se puede asignar un valor a un m�todo.");
			}
			/*
			 * ver que pasa con nodo ctor y eso, los demas
			 */
			if (ladoIzquierdo instanceof NodoPrimario) {
				NodoPrimario nodoP = (NodoPrimario) ladoIzquierdo;
				Encadenado ultimoEncadenado = nodoP.getEncadenado();
				if (ultimoEncadenado != null)
					while(ultimoEncadenado.getEncadenado() != null) {
						ultimoEncadenado = ultimoEncadenado.getEncadenado();
					}
				if (ultimoEncadenado instanceof NodoLlamadaEncadenado) {
					NodoLlamadaEncadenado nodo = (NodoLlamadaEncadenado) ultimoEncadenado;
					throw new ExcepcionSemantico("[" + nodo.getNroLinea() + ":" + nodo.getNroColumna()
					+ "] Error sem�ntico: No se puede asignar un valor a un m�todo.");
				}
			}
			TipoRetorno tipoLadoIzquierdo = ladoIzquierdo.chequear();
			TipoRetorno tipoLadoDerecho;
			if (ladoDerecho == null)
				tipoLadoDerecho = null;
			else
				tipoLadoDerecho = ladoDerecho.chequear();
			if(!tipoLadoIzquierdo.esCompatible(tipoLadoDerecho))
				throw new ExcepcionSemantico("[" + nroLinea
				+ "] Error sem�ntico: Tipos incompatibles en asignaci�n con una llamada a izquierda.");
		}
	}

	@Override
	protected void generar() {
		if (vars == null) {
			ladoDerecho.generar();
			ladoIzquierdo.generar();
		}
		/*
		 * ver caso contrario
		 */
	}
}
