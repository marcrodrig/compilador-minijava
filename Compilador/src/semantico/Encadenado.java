package semantico;

public abstract class Encadenado {
	private Encadenado cadena;
	private boolean ladoIzq;
	
	public Encadenado() {
		
	}
	
	//seguir??
	public void setCadena(Encadenado cadena) {
		this.cadena = cadena;
	}
}
