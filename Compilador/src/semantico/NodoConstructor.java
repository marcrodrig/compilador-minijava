package semantico;

import java.util.List;
import lexico.Token;
import main.Principal;

public class NodoConstructor extends NodoPrimario {
	private Token token;
	private List<NodoExpresion> argumentosActuales;

	public NodoConstructor(Token token, List<NodoExpresion> argumentosActuales) {
		this.token = token;
		this.argumentosActuales = argumentosActuales;
	}

	@Override
	public TipoRetorno chequear() throws ExcepcionSemantico {
		Clase clase = Principal.ts.getClase(token.getLexema());
		if (clase == null)
			throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
					+ "] Error semántico: La clase \"" + token.getLexema() + "\" no está declarada.");
		List<Unidad> constructores = clase.getConstructores();
		Constructor ctor = null;
		for (Unidad u : constructores)
			if (u.getCantidadParametros() == argumentosActuales.size())
				ctor = (Constructor) u;
		if (ctor == null)
			throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
					+ "] Error semántico: No existe el constructor de la clase \"" + token.getLexema() + "\" con "
					+ argumentosActuales.size() + " parámetros.");
		int posicion = 1;
		boolean conforma = true;
		while (conforma && posicion <= ctor.getCantidadParametros()) {
			conforma = ctor.getParametroPorPosicion(1).getTipo()
					.esCompatible(argumentosActuales.get(posicion - 1).chequear());
			posicion++;
		}
		if (!conforma)
			throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
					+ "] Error semántico: El tipo del parámetro nro " + (posicion - 1)
					+ " no es compatible con el del constructor declarado con misma aridad.");
		if (getEncadenado() == null)
			return new TipoClase(token);
		else
			return getEncadenado().chequear(new TipoClase(token));
	}
}
