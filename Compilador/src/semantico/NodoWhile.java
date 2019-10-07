package semantico;

public class NodoWhile extends NodoSentencia {
	private NodoExpresion condicion;
	private NodoSentencia sentencia;

	public NodoWhile(NodoExpresion condicion, NodoSentencia sentencia) {
		this.condicion = condicion;
		this.sentencia = sentencia;
	}
}
