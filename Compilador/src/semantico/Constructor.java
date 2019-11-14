package semantico;

import java.util.LinkedHashMap;
import java.util.List;
import gc.GeneradorCodigo;
import lexico.Token;
import main.CompiladorMiniJava;

public class Constructor extends Unidad {

	public Constructor(Token token, LinkedHashMap<String, Parametro> parametros) {
		super(token, parametros);
	}

	public void chequeoDeclaraciones(List<Unidad> constructores) throws ExcepcionSemantico {
		for (Parametro paramConstructor : getParametros().values())
			try {
				paramConstructor.chequeoDeclaraciones();
				paramConstructor.setOffset(3 + getCantidadParametros() - paramConstructor.getPosicion());
			} catch (ExcepcionSemantico e) {
				CompiladorMiniJava.tablaSimbolos.setRSem();
				System.out.println(e.toString());
			}
		for (Unidad ctor : constructores) {
			if (this != ctor) {
				if (getCantidadParametros() == ctor.getCantidadParametros()) {
					throw new ExcepcionSemantico("[" + ctor.getNroLinea() + ":" + ctor.getNroColumna()
							+ "] Error semántico: Constructor duplicado (misma cantidad de argumentos formales).");
				}
			}
		}
	}

	@Override
	protected void generar() {
		GeneradorCodigo generadorCodigo = GeneradorCodigo.getInstance();
		generadorCodigo.write(getLabel() + ":");
		generadorCodigo.write("\tLOADFP\t; Guardo enlace dinámico");
		generadorCodigo.write("\tLOADSP\t; Inicializo FP");
		generadorCodigo.write("\tSTOREFP");
		generadorCodigo.newLine();
		Clase claseActual = CompiladorMiniJava.tablaSimbolos.getClaseActual();
		// Asignación atributos inline
		if (claseActual.getCantidadInlineAtrs() > 0) {
			generadorCodigo.write("\t;Inicio Generación asignación atributos inline");
			List<NodoSentencia> inlineAtrs = claseActual.getInlineAtrs();
			for (NodoSentencia sentenciaInlineAtr : inlineAtrs)
				sentenciaInlineAtr.generar();
			generadorCodigo.write("\t;Fin Generación asignación atributos inline");
		}
		NodoBloque bloque = getBloque();
		if (bloque != null)
			bloque.generar();
		generadorCodigo.write("\tSTOREFP\t; Restablezco el contexto");
		generadorCodigo.write("\tRET " + (getCantidadParametros() + 1)
				+ "\t; Retorno y libero espacio de los parametros del metodo y del THIS " + getNombre());
		generadorCodigo.newLine();
	}

}