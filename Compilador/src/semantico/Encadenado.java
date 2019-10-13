package semantico;

public abstract class Encadenado {
	private Encadenado cadena;
	//private boolean ladoIzq;
	
	public Encadenado() {
		
	}

	public Encadenado getEncadenado() {
		return cadena;
	}
	
	public void setCadena(Encadenado cadena) {
		this.cadena = cadena;
	}

	public abstract TipoRetorno chequear(TipoRetorno tipo) throws ExcepcionSemantico;
	
}
