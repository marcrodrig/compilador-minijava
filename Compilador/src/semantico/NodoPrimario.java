package semantico;

public abstract class NodoPrimario extends NodoOperando {

	private Encadenado cadena;
	
	public Encadenado getEncadenado() {
		return cadena;
	}
	
	public void setEncadenado(Encadenado cadena) {
		this.cadena = cadena;		
	}
	
}
