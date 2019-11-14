package semantico;

public abstract class Encadenado {
	private Encadenado cadena;
	private boolean esLadoIzqAsig;

	public Encadenado getEncadenado() {
		return cadena;
	}
	
	public void setCadena(Encadenado cadena) {
		this.cadena = cadena;
	}

	public boolean esLadoIzqAsig() {
		return esLadoIzqAsig;
	}
	
	public void setEsLadoIzqAsig() {
		esLadoIzqAsig = true;
	}
	
	public abstract TipoRetorno chequear(TipoRetorno tipo) throws ExcepcionSemantico;
	
	protected abstract void generar();
}
