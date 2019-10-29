package semantico;

import lexico.Token;
import main.Principal;

public class NodoVarEncadenado extends Encadenado {
	private Token token;
		
	public NodoVarEncadenado(Token token) {
		this.token = token;
	}

	@Override
	public TipoRetorno chequear(TipoRetorno tipo) throws ExcepcionSemantico {
		if (tipo instanceof TipoClase) {
			Clase clase = Principal.ts.getClase(tipo.getNombre());
			VariableInstancia atributo = clase.getAtributoPorNombre(token.getLexema());
			if (atributo == null)
				throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
						+ "] Error semántico: No existe el atributo \"" + token.getLexema() + "\" en la clase "
						+ tipo.getNombre() + ".");
				if (atributo.getVisibilidad().equals("private") && !Principal.ts.getUnidadActual().declaradaEn().getNombre().equals(tipo.getNombre()) && !Principal.ts.getUnidadActual().declaradaEn().esDescendiente(clase.getNombre()))
					throw new ExcepcionSemantico(
							"[" + token.getNroLinea() + ":" + token.getNroColumna() + "] Error semántico: El atributo \""
									+ token.getLexema() + "\" en la clase " + tipo.getNombre() + " es privado.");
				else { // es public o protected
					Unidad unidad = Principal.ts.getUnidadActual();
					if (unidad instanceof Metodo) {
						Metodo metodo = (Metodo) unidad;
						if (metodo.getFormaMetodo().equals("static") && atributo instanceof VariableInstancia)
							throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
							+ "] Error semántico: En método estático no se puede acceder a un atributo de instancia.");
					}
					Tipo tipoAtributo = atributo.getTipo();
					if (getEncadenado() == null)
						return tipoAtributo;
					else
						return getEncadenado().chequear(tipoAtributo);
				}
		} else
			throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
					+ "] Error semántico: La variable encadenada debe tener como receptor un tipo clase.");
	}

}
