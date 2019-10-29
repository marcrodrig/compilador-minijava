package semantico;

import gc.GeneradorCodigo;

public class NodoBloquePrintB extends NodoBloque {
	public NodoBloquePrintB() {
	}
	
	public void generar() {
		GeneradorCodigo.getInstance().write("\tLOAD 3");
		GeneradorCodigo.getInstance().write("\tBPRINT");
	}
}
