package semantico;

public class TipoClase implements TipoReferencia {

	String nombre;
	
	public TipoClase(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
	
}