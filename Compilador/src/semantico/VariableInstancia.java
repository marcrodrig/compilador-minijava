package semantico;

import lexico.Token;

public class VariableInstancia {
	private Token token;
	private Tipo tipo;
	private String visibilidad;
	
	public VariableInstancia(Token token, Tipo tipo, String visibilidad) {
		this.token = token;
		this.tipo = tipo;
		this.visibilidad = visibilidad;
	}

	public String getNombre() {
		return token.getLexema();
	}

	public int getNroLinea() {
		return token.getNroLinea();
	}
	
	public Tipo getTipo() {
		return tipo;
	}

	public String getVisibilidad() {
		return visibilidad;
	}

	public void chequeoDeclaraciones() throws ExcepcionSemantico {
		TablaSimbolos ts = TablaSimbolos.getInstance();
		if (getTipo() instanceof TipoClase)
			if (ts.getClase(getTipo().getNombre()) == null)
				throw new ExcepcionSemantico("[" + getNroLinea() + "] Error semántico: El tipo clase \""
						+ getTipo().getNombre() + "\" no está definido.");
	}

}
