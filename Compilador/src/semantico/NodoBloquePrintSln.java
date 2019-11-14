package semantico;

import gc.GeneradorCodigo;

public class NodoBloquePrintSln extends NodoBloque {
	
	public void generar() {
		GeneradorCodigo generadorCodigo = GeneradorCodigo.getInstance();
		generadorCodigo.write("\tLOAD 3");
		generadorCodigo.write("\tSPRINT");
		generadorCodigo.write("\tPRNLN");
	}
}