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
			+ "] Error sem�ntico: El m�todo " + token.getLexema() + " con " + argumentosActuales.size() + " par�metros de la clase " + claseActual.getNombre() + " no es un m�todo v�lido.");
		if (metodo.getFormaMetodo().equals("static") && !metodo.declaradaEn().getNombre().equals(claseActual.getNombre()))
			throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
			+ "] Error sem�ntico: El m�todo " + token.getLexema() + " con " + argumentosActuales.size() + " par�metros de la clase " + claseActual.getNombre() + " es est�tico, no se puede llamar directamente.");
		if (metodo.getFormaMetodo().equals("dynamic")) {
			Unidad unidadActual = Principal.ts.getUnidadActual();
			if (unidadActual instanceof Metodo) {
				Metodo metodoContexto = (Metodo) unidadActual;
				if (metodoContexto.getFormaMetodo().equals("static"))
			throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
			+ "] Error sem�ntico: El m�todo " + token.getLexema() + " con " + argumentosActuales.size() + " par�metros de la clase " + claseActual.getNombre() + " es din�mico, no se puede llamar directamente en un contexto est�tico.");
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
			+ "] Error sem�ntico: El tipo del par�metro nro " + (posicion-1) + " no es compatible con el m�todo " + metodo.getNombre() + " declarado con misma aridad.");
		if (getEncadenado() == null)
			return metodo.getTipo();
		else
			return getEncadenado().chequear(metodo.getTipo());
	}
}
