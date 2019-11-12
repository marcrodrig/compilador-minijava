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
	
	public void insertarSentencia(NodoSentencia sentencia) {
		sentencias.add(sentencia);
	}

	public void setPadre(NodoBloque padre) {
		this.padre = padre;
	}
	
	public NodoBloque getPadre() {
		return padre;
	}
	
	public int getCantidadVarsLocales() {
		return varsLocales.size();
	}

	@Override
	protected void chequear() throws ExcepcionSemantico {
		//soy el bloque actual
		CompiladorMiniJava.ts.setBloqueActual(this);
		for(NodoSentencia sentencia : sentencias) {
			try {
				sentencia.chequear();
			} catch (ExcepcionSemantico e) {
				CompiladorMiniJava.ts.setRSem();
				System.out.println(e.toString());
			}
		}
		Unidad unidadActual = CompiladorMiniJava.ts.getUnidadActual();
        for (Variable v : varsLocales.values())
			unidadActual.eliminarVarLocal(v.getNombre());
	}

	public void generar() {
		for(NodoSentencia sentencia : sentencias)
			sentencia.generar();
        GeneradorCodigo.getInstance().write("\tFMEM " + varsLocales.size() + " # Libero espacio de variables locales al bloque");
        
        /*
         * //CAMBIAR OFF VARS LOCALES
		ASintactico.getTs().getMain().setOffVar(ASintactico.getTs().getMain().getOffVar()-varLocales.size());
         */
	}

	public void agregarVariableLocal(Variable v) {
		varsLocales.put(v.getNombre(), v);
	}
	
	public Variable getVarLocal(String nombreVarLocal) {
		return varsLocales.get(nombreVarLocal);
	}

}
