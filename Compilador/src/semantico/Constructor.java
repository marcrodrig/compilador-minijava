package semantico;

import java.util.LinkedHashMap;
import java.util.List;
import lexico.Token;
import main.Principal;

public class Constructor extends Unidad {

	public Constructor(Token token, LinkedHashMap<String, Parametro> parametros) {
		super(token, parametros);
	}
	
	public void chequeoDeclaraciones(List<Unidad> constructores) throws ExcepcionSemantico {
		//List<Unidad> constructores = Principal.ts.getClase(declaradaEn().getNombre()).getConstructores();
		for (Parametro paramConstructor : getParametros().values())
			try {
				paramConstructor.chequeoDeclaraciones();
			} catch (ExcepcionSemantico e) {
				Principal.ts.setRS();
				System.out.println(e.toString());
			}
		for (Unidad ctor : constructores) {
			if (this != ctor) {
				if (getCantidadParametros() == ctor.getCantidadParametros()) {
					/*
					 * boolean iguales = true; if (getCantidadParametros() >= 1) { int posicion = 1;
					 * while (iguales && posicion <= getCantidadParametros()) { Parametro p1 =
					 * getParametroPorPosicion(posicion); Parametro p2 =
					 * ctor.getParametroPorPosicion(posicion); iguales =
					 * p1.getTipo().getNombre().equals(p2.getTipo().getNombre()); posicion++; } } if
					 * (iguales)
					 */
					throw new ExcepcionSemantico("[" + ctor.getNroLinea() + ":" + ctor.getNroColumna()
							+ "] Error semántico: Constructor duplicado (misma cantidad de argumentos formales).");
				}
			}
		}
	}

}
