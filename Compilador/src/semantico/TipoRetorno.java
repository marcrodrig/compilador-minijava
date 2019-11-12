package semantico;

public abstract class TipoRetorno {
	public abstract String getNombre();

	public abstract boolean esCompatible(TipoRetorno tipo);
}
