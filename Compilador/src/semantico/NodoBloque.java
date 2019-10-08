package semantico;

import java.util.ArrayList;
import java.util.List;

import main.Principal;

public class NodoBloque extends NodoSentencia {
	private NodoBloque padre;
	private List<NodoSentencia> sentencias;
	
	public NodoBloque() {
		sentencias = new ArrayList<NodoSentencia>();
	}
	
	public void insertarSentencia(NodoSentencia sentencia) {
		sentencias.add(sentencia);
	}

	public void setPadre(NodoBloque padre) {
		this.padre = padre;
	}
	
	public void chequear() throws ExcepcionSemantico {
		for(NodoSentencia sentencia : sentencias) {
			sentencia.chequear();
		}
	}

	public NodoBloque getPadre() {
		return padre;
	}
}
