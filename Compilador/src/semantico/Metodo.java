package semantico;

import java.util.HashMap;
import java.util.List;
import lexico.Token;
import main.Principal;

public class Metodo extends Unidad {
	private String formaMetodo;
	private boolean metodoFinal;
	private TipoRetorno tipo;

	public Metodo(Token token, String formaMetodo, TipoRetorno tipo, boolean metodoFinal,
			HashMap<String, Parametro> parametros) {
		super(token, parametros);
		this.formaMetodo = formaMetodo;
		this.metodoFinal = metodoFinal;
		this.tipo = tipo;
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

	private void chequeoMetodosSobrecargados(List<Unidad> listaMetodos) throws ExcepcionSemantico {
		for (Unidad unidad : listaMetodos) {
			Metodo metodo = (Metodo) unidad;
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
				throw new ExcepcionSemantico("[" + getNroLinea() + ":" + getNroColumna()
						+ "] Error semántico: El tipo de retorno " + tipo.getNombre() + " del método "
						+ getNombre() + " no está definido.");
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
			throw new ExcepcionSemantico("[" + getNroLinea() + ":" + getNroColumna()
					+ "] Error semántico: Incorrecta redefinición de método " + getNombre() + ".");
		}
		if (redefinido && met2.isMetodoFinal())
			throw new ExcepcionSemantico("[" + getNroLinea() + ":" + getNroColumna()
					+ "] Error semántico: El método " + getNombre() + " no se puede sobreescribir.");
	}

	public boolean isMetodoMain() {
		return getNombre().equals("main") && getTipo().getNombre().equals("void") && getFormaMetodo().equals("static")
				&& getCantidadParametros() == 0;
	}

	public void chequeoDeclaraciones(List<Unidad> listaMetodos) throws ExcepcionSemantico {
		for (Parametro paramMetodo : getParametros().values())
			try {
				paramMetodo.chequeoDeclaraciones();
			} catch (ExcepcionSemantico e) {
				Principal.ts.setRS();
				System.out.println(e.toString());
			}
		chequeoMetodosSobrecargados(listaMetodos);
	}

}