package semantico;

import lexico.Token;

public class Parametro {
	private Token token;
	private Tipo tipo;
	private int posicion;
	
	public Parametro(Token token, Tipo tipo) {
		this.token = token;
		this.tipo = tipo;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public int getPosicion() {
		return posicion;
	}
	
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	public String getNombre() {
		return token.getLexema();
	}
	
	public void chequeoDeclaraciones() throws ExcepcionSemantico {
		TablaSimbolos ts = TablaSimbolos.getInstance();
		if (tipo instanceof TipoClase)
		if (ts.getClase(tipo.getNombre()) == null) 
			throw new ExcepcionSemantico("[" + token.getNroLinea() + "] Error sem�ntico: El tipo clase " + tipo.getNombre() + " del par�metro formal " + token.getLexema() + " no est� definido.");	
		
	}
}
