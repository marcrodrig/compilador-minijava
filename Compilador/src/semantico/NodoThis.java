package semantico;

import gc.GeneradorCodigo;
import lexico.Token;
import main.CompiladorMiniJava;

public class NodoThis extends NodoPrimario {
	private Token token;
	
	public NodoThis(Token token) {
		this.token = token;
	}

	@Override
	public TipoRetorno chequear() throws ExcepcionSemantico {
		Clase clase = CompiladorMiniJava.tablaSimbolos.getUnidadActual().declaradaEn();
		TipoRetorno tipoClase = new TipoClase(new Token("IdClase", clase.getNombre(), token.getNroLinea(), token.getNroColumna()));
		Unidad unidad = CompiladorMiniJava.tablaSimbolos.getUnidadActual();
		if (unidad instanceof Metodo) {
			Metodo metodo = (Metodo) unidad;
			if (metodo.getFormaMetodo().equals("static"))
				throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
				+ "] Error semántico: This no se puede utilizar en un método estático.");
		}
		Encadenado encadenado = getEncadenado();
		if (encadenado == null)
			return tipoClase;
		else
			return encadenado.chequear(tipoClase);
	}
	
	@Override
	protected void generar() {
		 GeneradorCodigo.getInstance().write("\tLOAD 3\t; Cargo THIS");
		 Encadenado encadenado = getEncadenado();
		 if (encadenado != null)
			 encadenado.generar();
	}
}
