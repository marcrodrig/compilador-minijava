package semantico;

public abstract class NodoExpresion {
	public abstract TipoRetorno chequear() throws ExcepcionSemantico;

	protected abstract void generar();
}
