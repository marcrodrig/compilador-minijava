package semantico;

import lexico.Token;

public abstract class Variable {
	private Token token;
	private Tipo tipo;
	
	public Variable(Token token, Tipo tipo){
		this.token = token;
		this.tipo = tipo;
	}
	
	public String getNombreTipo() {
		return tipo.getNombre();
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
	
	public void chequeoDeclaraciones() throws ExcepcionSemantico {
		TablaSimbolos ts = TablaSimbolos.getInstance();
		if (getTipo() instanceof TipoClase)
			if (ts.getClase(getTipo().getNombre()) == null)
				throw new ExcepcionSemantico("[" + getTipo().getNroLinea() + ":" + getTipo().getNroColumna()
						+ "] Error semántico: El tipo clase \"" + getTipo().getNombre() + "\" no está definido.");
	}
}
