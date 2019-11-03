package semantico;

import gc.GeneradorCodigo;

public class NodoBloquePrintSln extends NodoBloque {
	
	public void generar() {
		GeneradorCodigo.getInstance().write("\tLOAD 3");
		GeneradorCodigo.getInstance().write("\tSPRINT");
		GeneradorCodigo.getInstance().write("\tPRNLN");
	}
}