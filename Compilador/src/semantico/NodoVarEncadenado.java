package semantico;

import gc.GeneradorCodigo;
import lexico.Token;
import main.CompiladorMiniJava;

public class NodoVarEncadenado extends Encadenado {
	private Token token;
	private VariableInstancia atributo;

	public NodoVarEncadenado(Token token) {
		this.token = token;
	}

	@Override
	public TipoRetorno chequear(TipoRetorno tipo) throws ExcepcionSemantico {
		if (tipo instanceof TipoClase) {
			Clase clase = CompiladorMiniJava.tablaSimbolos.getClase(tipo.getNombre());
			atributo = clase.getAtributoPorNombre(token.getLexema());
			if (atributo == null)
				throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
						+ "] Error semántico: No existe el atributo \"" + token.getLexema() + "\" en la clase "
						+ tipo.getNombre() + ".");
			if (atributo.getVisibilidad().equals("private") && !CompiladorMiniJava.tablaSimbolos.getUnidadActual().declaradaEn().getNombre().equals(tipo.getNombre()) && !CompiladorMiniJava.tablaSimbolos.getUnidadActual().declaradaEn().esDescendiente(clase.getNombre()))
				throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna() + "] Error semántico: El atributo \""
								+ token.getLexema() + "\" en la clase " + tipo.getNombre() + " es privado.");
			else {
				Tipo tipoAtributo = atributo.getTipo();
				Encadenado encadenado = getEncadenado();
				if (encadenado == null)
					return tipoAtributo;
				else
					return encadenado.chequear(tipoAtributo);
			}
		} else
			throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
					+ "] Error semántico: La variable encadenada debe tener como receptor un tipo clase.");
	}

	@Override
	protected void generar() {
		Encadenado encadenado = getEncadenado();
		GeneradorCodigo generadorCodigo = GeneradorCodigo.getInstance();
		if (esLadoIzqAsig() && encadenado == null) {
			generadorCodigo.write("\tSWAP");
			generadorCodigo.write("\tSTOREREF " + atributo.getOffset() + "\t; Guardo el valor del atributo " + atributo.getNombre());
		} else {
			generadorCodigo.write("\tLOADREF " + atributo.getOffset());
			if (encadenado != null)
				encadenado.generar();
		}
	}
}
