package semantico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import lexico.Token;
import main.Principal;

public class Constructor {
	private Token token;
	private LinkedHashMap<String, Parametro> parametros;
	
	public Constructor(Token token, LinkedHashMap<String, Parametro> parametros) {
		this.token = token;
		this.parametros = parametros;
	}
	
	public String getNombre() {
		return token.getLexema();
	}
	
	public int getNroLinea() {
		return token.getNroLinea();
	}
	
	private int getNroColumna() {
		return token.getNroColumna();
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
			try {
				paramConstructor.chequeoDeclaraciones();
			} catch (ExcepcionSemantico e) {
				Principal.ts.setRS();
				System.out.println(e.toString());
			}
		for (Constructor ctor : constructores) {
			if (this != ctor) {
				if (getCantidadParametros() == ctor.getCantidadParametros()) {
					boolean iguales = true;
					if (getCantidadParametros() >= 1) {
						int posicion = 1;
						while (iguales && posicion <= getCantidadParametros()) { // ver si menor o menor o igual
							Parametro p1 = getParametroPorPosicion(posicion);
							Parametro p2 = ctor.getParametroPorPosicion(posicion);
							iguales = p1.getTipo().getNombre().equals(p2.getTipo().getNombre());
							posicion++;
						}
					} 
					if (iguales)
						throw new ExcepcionSemantico("[" + ctor.getNroLinea() + ":" + ctor.getNroColumna()
								+ "] Error semántico: Constructor duplicado.");
				}
			}
		}
	}

}
