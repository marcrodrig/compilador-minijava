package semantico;

import gc.GeneradorCodigo;

public class NodoBloquePrintC extends NodoBloque {
	
	public void generar() {
		GeneradorCodigo.getInstance().write("\tLOAD 3");
		GeneradorCodigo.getInstance().write("\tCPRINT");
	}
}
