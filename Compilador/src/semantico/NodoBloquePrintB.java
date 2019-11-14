package semantico;

import gc.GeneradorCodigo;

public class NodoBloquePrintB extends NodoBloque {
	
	public void generar() {
		GeneradorCodigo generadorCodigo = GeneradorCodigo.getInstance();
		generadorCodigo.write("\tLOAD 3");
		generadorCodigo.write("\tBPRINT");
	}
}
