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
				CompiladorMiniJava.ts.setRSem();
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
		GeneradorCodigo.getInstance().write(getLabel() + ":");
		GeneradorCodigo.getInstance().write("\tLOADFP\t; Guardo enlace dinámico");
		GeneradorCodigo.getInstance().write("\tLOADSP\t; Inicializo FP");
		GeneradorCodigo.getInstance().write("\tSTOREFP");
		GeneradorCodigo.getInstance().newLine();
		Clase claseActual = CompiladorMiniJava.ts.getClaseActual();
		// Asignación atributos inline
		if (claseActual.getCantidadInlineAtrs() > 0) {
				GeneradorCodigo.getInstance().write("\t;Inicio Generación asignación atributos inline");
				List<NodoSentencia> inlineAtrs = claseActual.getInlineAtrs();
				for(NodoSentencia sentenciaInlineAtr : inlineAtrs)
					sentenciaInlineAtr.generar();
				GeneradorCodigo.getInstance().write("\t;Fin Generación asignación atributos inline");
		}
        NodoBloque bloque = getBloque();
        CompiladorMiniJava.ts.setBloqueActual(bloque);
        if (bloque == null)
        	GeneradorCodigo.getInstance().write("\tNOP");
        else {
        	GeneradorCodigo.getInstance().write("\tRMEM " + bloque.getCantidadVarsLocales() + "\t; Reservo espacio para vars locales");
            bloque.generar();
        }
        
        GeneradorCodigo.getInstance().write("\tSTOREFP\t; Restablezco el contexto");
        GeneradorCodigo.getInstance().write("\tRET " + (getCantidadParametros() + 1) + "\t; Retorno y libero espacio de los parametros del metodo y del THIS " + getNombre());
        GeneradorCodigo.getInstance().newLine();
		
	}

}
