package semantico;

import gc.GeneradorCodigo;

public class NodoBloquePrintIln extends NodoBloque {
	
	public void generar() {
		GeneradorCodigo.getInstance().write("\tLOAD 3");
		GeneradorCodigo.getInstance().write("\tIPRINT");
		GeneradorCodigo.getInstance().write("\tPRNLN");
	}
}