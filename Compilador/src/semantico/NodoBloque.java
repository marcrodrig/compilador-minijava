package semantico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import gc.GeneradorCodigo;
import main.Principal;

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

	@Override
	protected void chequear() throws ExcepcionSemantico {
		//soy el bloque actual
		Principal.ts.setBloqueActual(this);
		for(NodoSentencia sentencia : sentencias) {
			try {
			sentencia.chequear();
		} catch (ExcepcionSemantico e) {
			Principal.ts.setRS();
			System.out.println(e.toString());
		}
		}
	}

	public void generar() {
		for(NodoSentencia sentencia : sentencias) {
			sentencia.generar();
		}
        GeneradorCodigo.getInstance().write("\tFMEM " + varsLocales.size() + " # Libero espacio de variables locales al bloque");
	}

	public void agregarVariableLocal(Variable v) {
		varsLocales.put(v.getNombre(), v);
	}

}
