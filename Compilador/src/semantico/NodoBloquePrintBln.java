package semantico;

import gc.GeneradorCodigo;

public class NodoBloquePrintBln extends NodoBloque {
	
	public void generar() {
		GeneradorCodigo generadorCodigo = GeneradorCodigo.getInstance();
		generadorCodigo.write("\tLOAD 3");
		generadorCodigo.write("\tBPRINT");
		generadorCodigo.write("\tPRNLN");
	}
}