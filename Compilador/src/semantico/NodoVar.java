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

	public void setEsLadoIzqAsig() {
		esLadoIzqAsig = true;
	}

	@Override
	public TipoRetorno chequear() throws ExcepcionSemantico {
		Unidad unidadActual = CompiladorMiniJava.tablaSimbolos.getUnidadActual();
		Tipo tipo = null;
		HashMap<String, VariableMetodo> varsLocalesParamsUnidadActual = unidadActual.getVarsLocalesParams();
		if (varsLocalesParamsUnidadActual.containsKey(token.getLexema()))
			tipo = varsLocalesParamsUnidadActual.get(token.getLexema()).getTipo();
		else {
			Clase claseActual = CompiladorMiniJava.tablaSimbolos.getClaseActual();
			VariableInstancia varIns = claseActual.getAtributoPorNombre(token.getLexema());
			if (varIns != null) {
				Unidad unidad = CompiladorMiniJava.tablaSimbolos.getUnidadActual();
				if (unidad instanceof Metodo) {
					Metodo metodo = (Metodo) unidad;
					if (metodo.getFormaMetodo().equals("static"))
						throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
								+ "] Error semántico: En método estático no se puede acceder a un atributo de instancia.");
				}
				tipo = varIns.getTipo();
			} else
				throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna() + "] Error semántico: Variable \""
								+ token.getLexema() + "\" inválida en " + unidadActual.getNombre() + ".");
		}
		Encadenado encadenado = getEncadenado();
		if (encadenado == null)
			return tipo;
		else
			return getEncadenado().chequear(tipo);
	}

	@Override
	protected void generar() {
		Encadenado encadenado = getEncadenado();
		Clase claseActual = CompiladorMiniJava.tablaSimbolos.getClaseActual();
		VariableInstancia varIns = claseActual.getAtributoPorNombre(token.getLexema());
		Unidad unidadActual = CompiladorMiniJava.tablaSimbolos.getUnidadActual();
		Variable varLocal = CompiladorMiniJava.tablaSimbolos.getBloqueActual().getVarLocal(token.getLexema());
		Parametro param = unidadActual.getParametroPorNombre(token.getLexema());
		GeneradorCodigo generadorCodigo = GeneradorCodigo.getInstance();
		if (varLocal != null && varIns == null) {
				if (!esLadoIzqAsig || encadenado != null)
					generadorCodigo.write("\tLOAD " + varLocal.getOffset());
			else
				generadorCodigo.write("\tSTORE " + varLocal.getOffset());
		} if (param != null) {
				if (!esLadoIzqAsig || encadenado != null)
					generadorCodigo.write("\tLOAD " + param.getOffset());
				else
					generadorCodigo.write("\tSTORE " + param.getOffset());
			} else {
		if (varIns != null) {
			generadorCodigo.write("\tLOAD 3");
			if (!esLadoIzqAsig || encadenado != null)
				generadorCodigo.write("\tLOADREF " + varIns.getOffset());
			else {
				generadorCodigo.write("\tSWAP");
				generadorCodigo.write("\tSTOREREF " + varIns.getOffset());
			}
			}
			}
		if (encadenado != null)
			encadenado.generar();
	}

}
