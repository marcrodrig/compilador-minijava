package semantico;

import gc.GeneradorCodigo;

public class NodoBloquePrintBln extends NodoBloque {
	
	public void generar() {
		GeneradorCodigo.getInstance().write("\tLOAD 3");
		GeneradorCodigo.getInstance().write("\tBPRINT");
		GeneradorCodigo.getInstance().write("\tPRNLN");
	}
}