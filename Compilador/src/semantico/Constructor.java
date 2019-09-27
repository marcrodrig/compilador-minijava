package semantico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lexico.Token;

public class Constructor {
	private Token token;
	private HashMap<String, Parametro> parametros;
	
	public Constructor(Token token, HashMap<String, Parametro> parametros) {
		this.token = token;
		this.parametros = parametros;
	}
	
	public String getNombre() {
		return token.getLexema();
	}
	
	public int getNroLinea() {
		return token.getNroLinea();
	}

	public HashMap<String, Parametro> getParametros() {
		return parametros;
	}
	
	// posicion va desde 1
	public Parametro getParametroPorPosicion(int posicion) {
		ArrayList<Parametro> listaParametros = new ArrayList<Parametro>(parametros.values());
		return listaParametros.get(posicion-1);
	}
	
	public int getCantidadParametros() {
		return parametros.size();
	}

	public void chequeoDeclaraciones(List<Constructor> constructores) throws ExcepcionSemantico {
		for (Parametro paramConstructor : getParametros().values())
			paramConstructor.chequeoDeclaraciones();
		for (Constructor ctor : constructores) {
			if (this != ctor && getCantidadParametros() == ctor.getCantidadParametros()) {
				int posicion = 1;
				while (posicion <= getCantidadParametros()) { // ver si menor o menor o igual
					Parametro p1 = getParametroPorPosicion(posicion);
					Parametro p2 = ctor.getParametroPorPosicion(posicion);
					if (p1.getTipo().getNombre().equals(p2.getTipo().getNombre()))
						throw new ExcepcionSemantico("[" + ctor.getNroLinea()
								+ "] Error semántico: Constructor con misma signatura ya definido.");
					posicion++;
				}
			}
		}
	}
	
}
