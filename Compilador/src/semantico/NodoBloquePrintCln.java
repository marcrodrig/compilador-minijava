package semantico;

import gc.GeneradorCodigo;

public class NodoBloquePrintCln extends NodoBloque {
	
	public void generar() {
		GeneradorCodigo generadorCodigo = GeneradorCodigo.getInstance();
		generadorCodigo.write("\tLOAD 3");
		generadorCodigo.write("\tCPRINT");
		generadorCodigo.write("\tPRNLN");
	}
}