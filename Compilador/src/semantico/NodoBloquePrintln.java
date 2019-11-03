package semantico;

import gc.GeneradorCodigo;

public class NodoBloquePrintln extends NodoBloque {
	
	public void generar() {
		GeneradorCodigo.getInstance().write("\tPRNLN");
	}
}
