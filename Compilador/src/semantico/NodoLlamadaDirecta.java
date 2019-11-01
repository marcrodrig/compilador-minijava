package semantico;

import java.util.List;
import lexico.Token;
import main.Principal;

public class NodoLlamadaDirecta extends NodoPrimario {
	private Token token;
	private List<NodoExpresion> argumentosActuales;
	
	public NodoLlamadaDirecta(Token token, List<NodoExpresion> argumentosActuales) {
		this.token = token;
		this.argumentosActuales = argumentosActuales;
	}
	
	public int getNroLinea() {
		return token.getNroLinea();
	}
	
	public int getNroColumna() {
		return token.getNroColumna();
	}

	@Override
	public TipoRetorno chequear() throws ExcepcionSemantico {
		Clase claseActual = Principal.ts.getClaseActual();
		List<Unidad> metodos = claseActual.getTodosMetodosPorNombre(token.getLexema());
		Metodo metodo = null;
		if (metodos != null) {
		for (Unidad u : metodos) {
			Metodo met = (Metodo) u;
			if(met.getCantidadParametros() == argumentosActuales.size())
				metodo = met;
		}
		}
		if (metodo == null)
			throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
			+ "] Error semántico: El método " + token.getLexema() + " con " + argumentosActuales.size() + " parámetros de la clase " + claseActual.getNombre() + " no es un método válido.");
		if (metodo.getFormaMetodo().equals("static") && !metodo.declaradaEn().getNombre().equals(claseActual.getNombre()))
			throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
			+ "] Error semántico: El método " + token.getLexema() + " con " + argumentosActuales.size() + " parámetros de la clase " + claseActual.getNombre() + " es estático, no se puede llamar directamente.");
		if (metodo.getFormaMetodo().equals("dynamic")) {
			Unidad unidadActual = Principal.ts.getUnidadActual();
			if (unidadActual instanceof Metodo) {
				Metodo metodoContexto = (Metodo) unidadActual;
				if (metodoContexto.getFormaMetodo().equals("static"))
			throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
			+ "] Error semántico: El método " + token.getLexema() + " con " + argumentosActuales.size() + " parámetros de la clase " + claseActual.getNombre() + " es dinámico, no se puede llamar directamente en un contexto estático.");
		}
		}
		int posicion = 1;
		boolean conforma = true;
		while (conforma && posicion <= metodo.getCantidadParametros()) {
			conforma = metodo.getParametroPorPosicion(1).getTipo().esCompatible(argumentosActuales.get(posicion-1).chequear());
			posicion++;
		}
		if (!conforma)
			throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
			+ "] Error semántico: El tipo del parámetro nro " + (posicion-1) + " no es compatible con el método " + metodo.getNombre() + " declarado con misma aridad.");
		if (getEncadenado() == null)
			return metodo.getTipo();
		else
			return getEncadenado().chequear(metodo.getTipo());
	}
}
