package semantico;

import gc.GeneradorCodigo;
import main.CompiladorMiniJava;

public class NodoRetorno extends NodoSentencia {
	private NodoExpresion expresion;
	private Metodo metodo;

	public NodoRetorno(NodoExpresion expresion) {
		this.expresion = expresion;
	}

	@Override
	protected void chequear() throws ExcepcionSemantico {
		Unidad unidadActual = CompiladorMiniJava.tablaSimbolos.getUnidadActual();
		if (unidadActual instanceof Constructor) {
			throw new ExcepcionSemantico("[" + unidadActual.getNroLinea() + ":" + unidadActual.getNroColumna()
					+ "] Error semántico: El constructor " + unidadActual.getNombre() + " tiene una sentencia return.");
		} else {
			metodo = (Metodo) unidadActual;
			TipoRetorno tipoMetodo = metodo.getTipo();
			/*if (expresion == null) {
				if (!tipoMetodo.getNombre().equals("void") && !(tipoMetodo instanceof TipoClase))
					throw new ExcepcionSemantico("[" + unidadActual.getNroLinea() + ":" + unidadActual.getNroColumna()
							+ "] Error semántico: El método " + unidadActual.getNombre()
							+ " debe tener tipo de retorno void.");
			} else {*/
				TipoRetorno tipoExpresion = expresion.chequear();
				if (!tipoMetodo.esCompatible(tipoExpresion))
					throw new ExcepcionSemantico("[" + unidadActual.getNroLinea() + ":" + unidadActual.getNroColumna()
							+ "] Error semántico: El método " + unidadActual.getNombre()
							+ " tiene un tipo de retorno incompatible.");
			
		}
	}

	@Override
	protected void generar() {
		GeneradorCodigo generadorCodigo = GeneradorCodigo.getInstance();
		if (!metodo.getTipo().getNombre().equals("void"))
			expresion.generar();
		if (metodo.getFormaMetodo().equals("static"))
			generadorCodigo.write("\tSTORE " + (3 + metodo.getCantidadParametros())
					+ "\t; Valor de retorno del metodo " + metodo.getNombre());
		else
			generadorCodigo.write("\tSTORE " + (4 + metodo.getCantidadParametros())
					+ "\t; Valor de retorno del metodo " + metodo.getNombre());
		if (metodo.getBloque().getCantidadVarsLocales() > 0)
			generadorCodigo.write("\tFMEM " + (metodo.getBloque().getCantidadVarsLocales())
					+ "\t; Libero espacio de variable locales (metodo " + metodo.getNombre() + ")");
		generadorCodigo.write("\tSTOREFP");
		if (metodo.getFormaMetodo().equals("static"))
			generadorCodigo.write("\tRET " + metodo.getCantidadParametros()
					+ "\t; Retorno y libero espacio de los parametros (metodo " + metodo.getNombre() + ")");
		else
			generadorCodigo.write("\tRET " + (metodo.getCantidadParametros() + 1)
					+ "\t; Retorno y libero espacio del this y los parametros (metodo " + metodo.getNombre() + ")");
	}

}
