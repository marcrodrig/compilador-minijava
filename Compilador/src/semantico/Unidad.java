package semantico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import lexico.Token;
import main.Principal;

public abstract class Unidad {
	private Token token;
	private LinkedHashMap<String, Parametro> parametros;
	private HashMap<String, VariableMetodo> varsParams;
	private Clase declaradaEn;
	private NodoBloque bloque;
	
	public Unidad(Token token, LinkedHashMap<String, Parametro> parametros) {
		this.token = token;
		this.parametros = parametros;
		TablaSimbolos ts = TablaSimbolos.getInstance();
		this.declaradaEn = ts.getClaseActual();
		varsParams = new HashMap<String, VariableMetodo>();
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
	
	public Clase declaradaEn() {
		return declaradaEn;
	}
	
	public void setBloque(NodoBloque bloque) {
		this.bloque = bloque;
	}
	
	public abstract void chequeoDeclaraciones(List<Unidad> unidades) throws ExcepcionSemantico;

	public void insertarVarMetodo(Variable varMet) {
		varsParams.put(varMet.getNombre(),(VariableMetodo) varMet);
	}

	public HashMap<String, VariableMetodo> getVarsParams() {
		return varsParams;
	}

	public void chequeoSentencias() throws ExcepcionSemantico {
		Principal.ts.setBloqueActual(null);
		bloque.chequear();
	}
}
