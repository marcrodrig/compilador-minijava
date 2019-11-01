package semantico;

import gc.GeneradorCodigo;

public class NodoBloquePrintI extends NodoBloque {
	public void generar() {
		GeneradorCodigo.getInstance().write("\tLOAD 3");
		GeneradorCodigo.getInstance().write("\tIPRINT");
	}
}
