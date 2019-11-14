package semantico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import gc.GeneradorCodigo;
import lexico.Token;
import main.CompiladorMiniJava;

public abstract class Unidad {
	private Token token;
	private LinkedHashMap<String, Parametro> parametros;
	private HashMap<String, VariableMetodo> varsLocalesParams;
	private Clase declaradaEn;
	private NodoBloque bloque;
	private int offset, offsetVarLocal, offsetVarIns;
	private String label;
	
	public Unidad(Token token, LinkedHashMap<String, Parametro> parametros) {
		this.token = token;
		this.parametros = parametros;
		varsLocalesParams = new HashMap<String, VariableMetodo>();
		TablaSimbolos ts = TablaSimbolos.getInstance();
		this.declaradaEn = ts.getClaseActual();
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

	// posición va desde 1
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
	
	public HashMap<String, VariableMetodo> getVarsLocalesParams() {
		return varsLocalesParams;
	}

	public Variable getVarLocalParamPorNombre(String string) {
		return varsLocalesParams.get(string);
	}
	
	public int getCantidadVarsParams() {
		return varsLocalesParams.size();
	}
	
	public void insertarVariableMetodo(VariableMetodo var) {
		if (var instanceof VarLocal)
			insertarVarLocal(var);
		else
			insertarParametro(var);
	}
	
	public void insertarVarLocal(VariableMetodo varLoc) {
		varLoc.setOffset(getOffsetVarLocal());
        setOffsetVarLocal(getOffsetVarLocal() - 1);
		varsLocalesParams.put(varLoc.getNombre(),(VarLocal) varLoc);
	}
	
	public void insertarParametro(VariableMetodo param) {
		varsLocalesParams.put(param.getNombre(),(Parametro) param);
	}
	
	public void eliminarVarLocal(String nombreVarLocal) {
		varsLocalesParams.remove(nombreVarLocal);		
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
	
	public String getLabel() {
		if (label == null)
			label = "" + getNombre() + "_" + declaradaEn.getNombre() + "_" + GeneradorCodigo.getInstance().nLabel();
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public void chequeoSentencias() throws ExcepcionSemantico {
		CompiladorMiniJava.tablaSimbolos.setBloqueActual(null);
		if (bloque != null)
			bloque.chequear();
	}

	public abstract void chequeoDeclaraciones(List<Unidad> unidades) throws ExcepcionSemantico;
	
	protected abstract void generar();
	
}
