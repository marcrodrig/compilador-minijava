package semantico;

public class NodoIf extends NodoSentencia {
	private NodoExpresion condicion;
	private NodoSentencia entonces;
	private NodoSentencia sino;
	
	public NodoIf(NodoExpresion condicion, NodoSentencia entonces, NodoSentencia sino) {
		this.condicion = condicion;
		this.entonces = entonces;
		this.sino = sino;
	}
}
