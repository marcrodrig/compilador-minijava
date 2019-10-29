package semantico;

import java.util.ArrayList;
import java.util.List;

import main.Principal;

public class NodoBloque extends NodoSentencia {
	private NodoBloque padre;
	private List<NodoSentencia> sentencias;
	//varsLocales??
	
	public NodoBloque() {
		sentencias = new ArrayList<NodoSentencia>();
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
			sentencia.chequear();
		}
	}

	public void generar() {
		for(NodoSentencia sentencia : sentencias) {
			sentencia.generar();
		}
	}

}
