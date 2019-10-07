package semantico;

import java.util.List;

public class NodoAsignacion extends NodoSentencia {
	private List<Variable> varsLocales;
	private NodoExpresion ladoIzquierdo;
	private NodoExpresion ladoDerecho;
	
	public NodoAsignacion(List<Variable> varsLocales, NodoExpresion ladoIzquierdo, NodoExpresion ladoDerecho) {
		this.varsLocales = varsLocales;
		this.ladoIzquierdo = ladoIzquierdo;
		this.ladoDerecho = ladoDerecho;
	}
}
