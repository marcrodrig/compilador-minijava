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
					throw new ExcepcionSemantico("[" + ctor.getNroLinea() + ":" + ctor.getNroColumna()
							+ "] Error semántico: Constructor duplicado (misma cantidad de argumentos formales).");
				}
			}
		}
	}

}
