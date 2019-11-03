package semantico;

import java.util.LinkedHashMap;
import java.util.List;

import gc.GeneradorCodigo;
import lexico.Token;
import main.Principal;

public class Constructor extends Unidad {

	public Constructor(Token token, LinkedHashMap<String, Parametro> parametros) {
		super(token, parametros);
	}
	
	public void chequeoDeclaraciones(List<Unidad> constructores) throws ExcepcionSemantico {
		for (Parametro paramConstructor : getParametros().values())
			try {
				paramConstructor.chequeoDeclaraciones();
				paramConstructor.setOffset(2 + getCantidadParametros() - paramConstructor.getPosicion());
              /**
                 * VER SI AGREGAR
                 * vars.put(p.getNombre(), p);
                 */
			} catch (ExcepcionSemantico e) {
				Principal.ts.setRS();
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
    
        NodoBloque bloque = getBloque();
        if (bloque == null)
        	GeneradorCodigo.getInstance().write("\tNOP");
        else
            bloque.generar();
        

        GeneradorCodigo.getInstance().write("\tSTOREFP\t; Restablezco el contexto");
        GeneradorCodigo.getInstance().write("\tRET " + (getCantidadParametros() + 1) + "\t; Retorno y libero espacio de los parametros del metodo y del THIS " + getNombre());

        GeneradorCodigo.getInstance().newLine();
		
	}

}
