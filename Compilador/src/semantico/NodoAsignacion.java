package semantico;

import java.util.List;

import gc.GeneradorCodigo;
import main.CompiladorMiniJava;

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

	public List<Variable> getVars() {
		return vars;
	}

	@Override
	protected void chequear() throws ExcepcionSemantico {
		if (vars != null) { // asignación inline en constructor/método o atributo
			Unidad unidadActual = CompiladorMiniJava.tablaSimbolos.getUnidadActual();
			for (Variable var : vars) {
				Tipo tipoVarLocal = var.getTipo();
				if (tipoVarLocal instanceof TipoClase)
					if (CompiladorMiniJava.tablaSimbolos.getClase(tipoVarLocal.getNombre()) == null)
						throw new ExcepcionSemantico("[" + tipoVarLocal.getNroLinea() + ":"
								+ tipoVarLocal.getNroColumna() + "] Error semántico: El tipo clase \""
								+ tipoVarLocal.getNombre() + "\" no está definido.");
				if (var instanceof VarLocal)
					if (unidadActual.getVarsLocalesParams().get(var.getNombre()) == null) {
						VariableMetodo v = (VariableMetodo) var;
						unidadActual.insertarVarLocal(v);
					} else
						throw new ExcepcionSemantico("[" + var.getNroLinea() + ":" + var.getNroColumna()
								+ "] Error semántico: Nombre de variable local \"" + var.getNombre()
								+ "\" repetido a un parámetro u otra variable local.");
			}
			TipoRetorno tipoLadoDerecho;
			if (ladoDerecho == null)
				tipoLadoDerecho = null;
			else
				tipoLadoDerecho = ladoDerecho.chequear();
			Variable primerVar = vars.get(0);
			TipoRetorno tipoAsignacionInline = primerVar.getTipo();
			if (!tipoAsignacionInline.esCompatible(tipoLadoDerecho))
				throw new ExcepcionSemantico("[" + nroLinea + "] Error semántico: Tipos incompatibles en asignación inline.");
		} else { // método lado izquierdo
			if (ladoIzquierdo instanceof NodoLlamadaDirecta) {
				NodoLlamadaDirecta nodo = (NodoLlamadaDirecta) ladoIzquierdo;
				if (nodo.getEncadenado() == null)
					throw new ExcepcionSemantico("[" + nodo.getNroLinea() + ":" + nodo.getNroColumna()
							+ "] Error semántico: No se puede asignar un valor a un método.");
			}
			if (ladoIzquierdo instanceof NodoLlamadaEstatica) {
				NodoLlamadaEstatica nodo = (NodoLlamadaEstatica) ladoIzquierdo;
				if (nodo.getEncadenado() == null)
					throw new ExcepcionSemantico("[" + nodo.getNroLinea() + ":" + nodo.getNroColumna()
							+ "] Error semántico: No se puede asignar un valor a un método.");
			}
			if (ladoIzquierdo instanceof NodoPrimario) {
				NodoPrimario nodoP = (NodoPrimario) ladoIzquierdo;
				Encadenado ultimoEncadenado = nodoP.getEncadenado();
				if (ultimoEncadenado != null)
					while (ultimoEncadenado.getEncadenado() != null)
						ultimoEncadenado = ultimoEncadenado.getEncadenado();
				if (ultimoEncadenado instanceof NodoLlamadaEncadenado) {
					NodoLlamadaEncadenado nodo = (NodoLlamadaEncadenado) ultimoEncadenado;
					throw new ExcepcionSemantico("[" + nodo.getNroLinea() + ":" + nodo.getNroColumna()
							+ "] Error semántico: No se puede asignar un valor a un método.");
				}
			}
			TipoRetorno tipoLadoIzquierdo = ladoIzquierdo.chequear();
			TipoRetorno tipoLadoDerecho;
			if (ladoDerecho == null)
				tipoLadoDerecho = null;
			else
				tipoLadoDerecho = ladoDerecho.chequear();
			if (!tipoLadoIzquierdo.esCompatible(tipoLadoDerecho))
				throw new ExcepcionSemantico("[" + nroLinea
						+ "] Error semántico: Tipos incompatibles en asignación con una llamada a izquierda.");
		}
	}

	@Override
	protected void generar() {
		if (vars == null) {
			ladoDerecho.generar();
			ladoIzquierdo.generar();
		} else {
			GeneradorCodigo generadorCodigo = GeneradorCodigo.getInstance();
			Unidad unidadActual = CompiladorMiniJava.tablaSimbolos.getUnidadActual();
			if (vars.get(0) instanceof VariableMetodo) {
				generadorCodigo.write("\tRMEM " + vars.size() + "\t; Reservo espacio para vars locales");
				for (Variable v : vars) {
					Variable varParam = unidadActual.getVarLocalParamPorNombre(v.getNombre());
					ladoDerecho.generar();
					CompiladorMiniJava.tablaSimbolos.getBloqueActual().agregarVariableLocal(varParam);
					generadorCodigo.write("\tSTORE " + varParam.getOffset());
				}
			} else {
				Clase claseActual = CompiladorMiniJava.tablaSimbolos.getClaseActual();
				for (Variable v : vars) {
					VariableInstancia varIns = claseActual.getAtributoPorNombre(v.getNombre());
					ladoDerecho.generar();
					generadorCodigo.write("\tLOAD 3");
					generadorCodigo.write("\tSWAP");
					generadorCodigo.write("\tSTOREREF " + varIns.getOffset());
				}
			}
		}
	}
}
