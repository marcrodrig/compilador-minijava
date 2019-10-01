package semantico;

import lexico.Token;
import main.Principal;

public class Parametro {
	private Token token;
	private Tipo tipo;
	private int posicion;

	public Parametro(Token token, Tipo tipo) {
		this.token = token;
		this.tipo = tipo;
	}

	public String getNombre() {
		return token.getLexema();
	}

	public int getNroLinea() {
		return token.getNroLinea();
	}

	public int getNroColumna() {
		return token.getNroColumna();
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

	public void chequeoDeclaraciones() throws ExcepcionSemantico {
		if (tipo instanceof TipoClase)
			if (Principal.ts.getClase(tipo.getNombre()) == null)
				throw new ExcepcionSemantico("[" + tipo.getNroLinea() + ":" + tipo.getNroColumna()
						+ "] Error semántico: El tipo clase " + tipo.getNombre() + " del parámetro formal "
						+ token.getLexema() + " no está definido.");
	}

}