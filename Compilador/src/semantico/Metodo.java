package semantico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lexico.Token;
import main.Principal;

public class Metodo {
	private Token token;
	private String formaMetodo;
	private boolean metodoFinal;
	private TipoRetorno tipo;
	private HashMap<String, Parametro> parametros;

	public Metodo(Token token, String formaMetodo, TipoRetorno tipo, boolean metodoFinal,
			HashMap<String, Parametro> parametros) {
		this.token = token;
		this.formaMetodo = formaMetodo;
		this.metodoFinal = metodoFinal;
		this.tipo = tipo;
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

	public String getFormaMetodo() {
		return formaMetodo;
	}

	public boolean isMetodoFinal() {
		return metodoFinal;
	}

	public TipoRetorno getTipo() {
		return tipo;
	}

	public HashMap<String, Parametro> getParametros() {
		return parametros;
	}

	public Parametro getParametroPorNombre(String string) {
		return parametros.get(string);
	}

	// posicion va desde 1
	public Parametro getParametroPorPosicion(int posicion) {
		ArrayList<Parametro> listaParametros = new ArrayList<Parametro>(parametros.values());
		return listaParametros.get(posicion - 1);
	}

	public int getCantidadParametros() {
		return parametros.size();
	}

	private void chequeoMetodosSobrecargados(List<Metodo> listaMetodos) throws ExcepcionSemantico {
		for (Metodo metodo : listaMetodos) {
			metodo.chequeoExistenciaTipoRetorno();
			if (this != metodo && getCantidadParametros() == metodo.getCantidadParametros())
				/*
				 * boolean iguales = true; if (getCantidadParametros() >= 1) { int posicion = 1;
				 * while (posicion <= getCantidadParametros()) { // ver si menor o menor o igual
				 * Parametro p1 = getParametroPorPosicion(posicion); Parametro p2 =
				 * metodo.getParametroPorPosicion(posicion); iguales =
				 * p1.getTipo().getNombre().equals(p2.getTipo().getNombre()); posicion++; } if
				 * (iguales)
				 */
				throw new ExcepcionSemantico("[" + metodo.getNroLinea() + ":" + metodo.getNroColumna()
						+ "] Error semántico: Método duplicado (misma cantidad de argumentos formales).");
			// } else
			// throw new ExcepcionSemantico("[" + metodo.getNroLinea() + "] Error semántico:
			// Método duplicado.");
		}
	}

	private void chequeoExistenciaTipoRetorno() throws ExcepcionSemantico {
		if (tipo instanceof TipoClase)
			if (Principal.ts.getClase(tipo.getNombre()) == null)
				throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
						+ "] Error semántico: El tipo de retorno " + tipo.getNombre() + " del método "
						+ token.getLexema() + " no está definido.");
	}

	public void chequeoRedefinicionMetodo(Metodo met2) throws ExcepcionSemantico {
		boolean redefinido = getTipo().getNombre().equals(met2.getTipo().getNombre())
				&& getFormaMetodo().equals(met2.getFormaMetodo());
		int posicion = 1;
		while (redefinido && posicion <= getCantidadParametros()) {
			Parametro p1 = getParametroPorPosicion(posicion);
			Parametro p2 = met2.getParametroPorPosicion(posicion);
			redefinido = p1.getTipo().getNombre().equals(p2.getTipo().getNombre());
			posicion++;
		}
		if (!redefinido) {
			throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
					+ "] Error semántico: Incorrecta redefinición de método " + getNombre() + ".");
		}
		if (redefinido && met2.isMetodoFinal())
			throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
					+ "] Error semántico: El método " + getNombre() + " no se puede sobreescribir.");
	}

	public boolean isMetodoMain() {
		return getNombre().equals("main") && getTipo().getNombre().equals("void") && getFormaMetodo().equals("static")
				&& getCantidadParametros() == 0;
	}

	public boolean chequeoDeclaraciones(List<Metodo> listaMetodos) throws ExcepcionSemantico {
		for (Parametro paramMetodo : parametros.values())
			try {
				paramMetodo.chequeoDeclaraciones();
			} catch (ExcepcionSemantico e) {
				Principal.ts.setRS();
				System.out.println(e.toString());
			}
		chequeoMetodosSobrecargados(listaMetodos);
		return isMetodoMain();
	}

}