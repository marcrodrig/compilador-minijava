package semantico;

public class TipoVoid extends TipoRetorno {
	private String nombre = "void";

	public String getNombreTipo() {
		return nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	@Override
	public boolean esCompatible(TipoRetorno tipo) {
		return (tipo instanceof TipoVoid);
	}

}