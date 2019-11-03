package semantico;

import gc.GeneradorCodigo;

public class NodoBloquePrintCln extends NodoBloque {
	
	public void generar() {
		GeneradorCodigo.getInstance().write("\tLOAD 3");
		GeneradorCodigo.getInstance().write("\tCPRINT");
		GeneradorCodigo.getInstance().write("\tPRNLN");
	}
}