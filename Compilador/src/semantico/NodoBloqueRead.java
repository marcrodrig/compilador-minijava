package semantico;

import gc.GeneradorCodigo;

public class NodoBloqueRead extends NodoBloque {
	
	public void generar() {
		GeneradorCodigo generadorCodigo = GeneradorCodigo.getInstance();
		generadorCodigo.write("\tREAD");
		generadorCodigo.write("\tSTORE 3");
		generadorCodigo.write("\tSTOREFP");
		generadorCodigo.write("\tRET 0");
	}
}
