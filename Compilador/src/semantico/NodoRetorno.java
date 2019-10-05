package semantico;

public class NodoRetorno extends NodoSentencia {
	private NodoExpresion expresion;
	
	public NodoRetorno(NodoExpresion expresion) {
		this.expresion = expresion;
	}
}
