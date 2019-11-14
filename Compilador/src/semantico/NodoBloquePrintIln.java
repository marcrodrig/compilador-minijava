package semantico;

import gc.GeneradorCodigo;

public class NodoBloquePrintIln extends NodoBloque {
	
	public void generar() {
		GeneradorCodigo generadorCodigo = GeneradorCodigo.getInstance();
		generadorCodigo.write("\tLOAD 3");
		generadorCodigo.write("\tIPRINT");
		generadorCodigo.write("\tPRNLN");
	}
}