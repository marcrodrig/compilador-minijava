package semantico;

import java.util.ArrayList;
import java.util.List;

public class NodoBloque extends NodoSentencia {
	private List<NodoSentencia> sentencias;
	
	public NodoBloque() {
		sentencias = new ArrayList<NodoSentencia>();
	}
	
	public void insertarSentencia(NodoSentencia sentencia) {
		sentencias.add(sentencia);
	}
}
