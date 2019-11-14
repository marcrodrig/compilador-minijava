package semantico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import gc.GeneradorCodigo;
import main.CompiladorMiniJava;

public class NodoBloque extends NodoSentencia {
	private NodoBloque padre;
	private List<NodoSentencia> sentencias;
	private HashMap<String, Variable> varsLocales;

	public NodoBloque() {
		sentencias = new ArrayList<NodoSentencia>();
		varsLocales = new HashMap<String, Variable>();
	}

	public NodoBloque getPadre() {
		return padre;
	}
	
	public void setPadre(NodoBloque padre) {
		this.padre = padre;
	}
	
	public void insertarSentencia(NodoSentencia sentencia) {
		sentencias.add(sentencia);
	}

	public Variable getVarLocal(String nombreVarLocal) {
		Variable vLoc = varsLocales.get(nombreVarLocal);
		NodoBloque bloquePadre = padre;
		if(bloquePadre != null && vLoc == null)
			while(bloquePadre != null) {
				if(vLoc == null)
					vLoc = bloquePadre.getVarLocal(nombreVarLocal);
				bloquePadre = bloquePadre.getPadre();
			}
		return vLoc;
	}

	public void agregarVariableLocal(Variable v) {
		varsLocales.put(v.getNombre(), v);
	}

	public int getCantidadVarsLocales() {
		return varsLocales.size();
	}

	@Override
	protected void chequear() throws ExcepcionSemantico {
		CompiladorMiniJava.tablaSimbolos.setBloqueActual(this);
		for (NodoSentencia sentencia : sentencias) {
			try {
				sentencia.chequear();
			} catch (ExcepcionSemantico e) {
				CompiladorMiniJava.tablaSimbolos.setRSem();
				System.out.println(e.toString());
			}
		}
		Unidad unidadActual = CompiladorMiniJava.tablaSimbolos.getUnidadActual();
		for (Variable v : varsLocales.values())
			unidadActual.eliminarVarLocal(v.getNombre());
	}

	public void generar() {
		GeneradorCodigo generadorCodigo = GeneradorCodigo.getInstance();
		CompiladorMiniJava.tablaSimbolos.setBloqueActual(this);
		generadorCodigo.write("\tRMEM " + getCantidadVarsLocales() + "\t; Reservo espacio para vars locales");
		for (NodoSentencia sentencia : sentencias)
			sentencia.generar();
		generadorCodigo.write("\tFMEM " + varsLocales.size() + " # Libero espacio de variables locales al bloque");
	}

}
