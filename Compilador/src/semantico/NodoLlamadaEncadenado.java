package semantico;

import java.util.List;

import lexico.Token;
import main.Principal;

public class NodoLlamadaEncadenado extends Encadenado {
	private Token token;
	private List<NodoExpresion> argumentosActuales;
	
	public NodoLlamadaEncadenado(Token token, List<NodoExpresion> argumentosActuales) {
		this.token = token;
		this.argumentosActuales = argumentosActuales;
	}
	
	public List<NodoExpresion> getArgsActuales() {
		return argumentosActuales;
	}

	@Override
	public TipoRetorno chequear(TipoRetorno tipo) throws ExcepcionSemantico {
		/**
		 * hacer control nodo llamada encadenada
		 */
		if (tipo instanceof TipoClase) {
		Clase clase = Principal.ts.getClase(tipo.getNombre());
		List<Unidad> metodos = clase.getTodosMetodosPorNombre(token.getLexema());
		Metodo metodo = null;
		if (metodos != null) {
		for (Unidad u : metodos) {
			Metodo met = (Metodo) u;
			if(met.getCantidadParametros() == argumentosActuales.size())
				metodo = met;
		}
		}
		if (metodo == null)  // no hay visibilidad en metodos, ver
			throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
			+ "] Error semántico: El método " + token.getLexema() + " con " + argumentosActuales.size() + " parámetros de la clase " + clase.getNombre() + " no es un método válido.");
		int posicion = 1;
		boolean conforma = true;
		while (conforma && posicion <= metodo.getCantidadParametros()) {
			conforma = metodo.getParametroPorPosicion(1).getTipo().conformaTipo(argumentosActuales.get(posicion-1).chequear());
			posicion++;
		}
		if (!conforma)
			throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
			+ "] Error semántico: El tipo del parámetro nro " + (posicion-1) + " no es compatible con el método " + metodo.getNombre() + " declarado con misma aridad.");
		if (getEncadenado() == null)
			return metodo.getTipo();
		else
			return getEncadenado().chequear(metodo.getTipo());
		} else
			throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
			+ "] Error semántico: La llamada encadenada debe tener como receptor un tipo clase y el tipo receptor es " + tipo.getNombre() + ".");
	}
}
