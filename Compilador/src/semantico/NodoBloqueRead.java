package semantico;

import gc.GeneradorCodigo;

public class NodoBloqueRead extends NodoBloque {
	
	public void generar() {
		GeneradorCodigo.getInstance().write("\tREAD");
		GeneradorCodigo.getInstance().write("\tPUSH 48");
		GeneradorCodigo.getInstance().write("\tSUB");
		GeneradorCodigo.getInstance().write("\tSTORE 3");
		GeneradorCodigo.getInstance().write("\tSTOREFP");
		GeneradorCodigo.getInstance().write("\tRET 0");
	}
}
