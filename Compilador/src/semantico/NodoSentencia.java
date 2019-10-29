package semantico;

public abstract class NodoSentencia {

	protected abstract void chequear() throws ExcepcionSemantico;

	protected abstract void generar();

}
