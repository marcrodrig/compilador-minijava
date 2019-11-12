package semantico;

import gc.GeneradorCodigo;
import lexico.Token;
import main.CompiladorMiniJava;

public class NodoThis extends NodoPrimario {
	private Token token;
	private boolean esLadoIzqAsig;
	
	public NodoThis(Token token) {
		this.token = token;
	}

	@Override
	public TipoRetorno chequear() throws ExcepcionSemantico {
		Clase clase = CompiladorMiniJava.ts.getUnidadActual().declaradaEn();
		TipoRetorno tipoClase = new TipoClase(new Token("IdClase", clase.getNombre(), token.getNroLinea(), token.getNroColumna()));
		Unidad unidad = CompiladorMiniJava.ts.getUnidadActual();
		if (unidad instanceof Metodo) {
			Metodo metodo = (Metodo) unidad;
			if (metodo.getFormaMetodo().equals("static"))
				throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
				+ "] Error semántico: This no se puede utilizar en un método estático.");
		}
		if (getEncadenado() == null)
			return tipoClase;
		else
			return getEncadenado().chequear(tipoClase);
	}

	public void setEsLadoIzqAsig() {
		esLadoIzqAsig = true;
	}
	
	@Override
	protected void generar() {
		 GeneradorCodigo.getInstance().write("\tLOAD 3\t; Cargo THIS");
		 Encadenado encadenado = getEncadenado();
		 if (encadenado != null)
			 encadenado.generar();
	}
}
