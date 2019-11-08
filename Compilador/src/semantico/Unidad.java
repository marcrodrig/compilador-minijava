package semantico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import gc.GeneradorCodigo;
import lexico.Token;
import main.Principal;

public abstract class Unidad {
	private Token token;
	private LinkedHashMap<String, Parametro> parametros;
	private HashMap<String, Variable> varsParams;
	private Clase declaradaEn;
	private NodoBloque bloque;
	private int offset, offsetVarLocal, offsetVarIns;
	private String label;
	
	public Unidad(Token token, LinkedHashMap<String, Parametro> parametros) {
		this.token = token;
		this.parametros = parametros;
		TablaSimbolos ts = TablaSimbolos.getInstance();
		this.declaradaEn = ts.getClaseActual();
		varsParams = new HashMap<String, Variable>();
	}

	public String getNombre() {
		return token.getLexema();
	}

	public int getNroLinea() {
		return token.getNroLinea();
	}

	public int getNroColumna() {
		return token.getNroColumna();
	}

	public HashMap<String, Parametro> getParametros() {
		return parametros;
	}

	// posicion va desde 1
	public Parametro getParametroPorPosicion(int posicion) {
		ArrayList<Parametro> listaParametros = new ArrayList<Parametro>(parametros.values());
		return listaParametros.get(posicion - 1);
	}

	public Parametro getParametroPorNombre(String string) {
		return parametros.get(string);
	}
	
	public int getCantidadParametros() {
		return parametros.size();
	}
	
	public int getCantidadVariables() {
		return Math.abs(getCantidadParametros() - varsParams.size());
	}
	
	public Clase declaradaEn() {
		return declaradaEn;
	}
	
	public NodoBloque getBloque() {
		return bloque;
	}
	
	public void setBloque(NodoBloque bloque) {
		this.bloque = bloque;
	}
	
	public abstract void chequeoDeclaraciones(List<Unidad> unidades) throws ExcepcionSemantico;

	public void insertarVarLocalParamMetodo(Variable varMet) {
		varMet.setOffset(getOffsetVarLocal());
        setOffsetVarLocal(getOffsetVarLocal() - 1);
		varsParams.put(varMet.getNombre(),(VariableMetodo) varMet);
	}
	
	public void insertarVarIns(Variable varIns) {
		varsParams.put(varIns.getNombre(),(VariableInstancia) varIns);
	}

	public HashMap<String, Variable> getVarsParams() {
		return varsParams;
	}

	public Variable getVarLocalParamPorNombre(String string) {
		return varsParams.get(string);
	}
	
	public void chequeoSentencias() throws ExcepcionSemantico {
		Principal.ts.setBloqueActual(null);
		if (bloque != null)
			bloque.chequear();
		
	}
	
	public String getLabel() {
		if (label == null)
			label = "" + getNombre() + "_" + declaradaEn.getNombre() + "_" + GeneradorCodigo.getInstance().nLabel();
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
	
	public int getOffsetVarLocal() {
		return offsetVarLocal;
	}
	
	public void setOffsetVarLocal(int offset) {
		offsetVarLocal = offset;
	}
	
	public int getOffsetVarIns() {
		return offsetVarIns;
	}
	
	public void setOffsetVarIns(int offset) {
		offsetVarIns = offset;
	}
	
	protected abstract void generar();
}
