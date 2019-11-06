package semantico;

import java.util.List;

import gc.GeneradorCodigo;
import lexico.Token;
import main.Principal;

public class NodoLlamadaDirecta extends NodoPrimario {
	private Token token;
	private List<NodoExpresion> argumentosActuales;
	private Metodo metodo;
	
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
		metodo = null;
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
/*
 * Si emet es no est�tico:
 * 		Gen LOAD 3 ) //cargo futuro this
		Si emet no retorna void :
			Gen RMEM 1 ) //Reservo lugar para el retorno
			Gen SWAP
		Para cada nexp en paramsAct
			nexp.generar() //Genero c�digo de param actual
			Gen	SWAP
		Gen DUP ) //Duplico this para no perderlo
		Gen LOADREF 0 ) //Cargo la VT
		Gen LOADREF emet.offset ()()) //Cargo la dir de emet
		Gen	CALL
  Si emet es est�tico :
	�	Completar!!!
  Si cadena es no nulo
		cadena.generar
 */
	@Override
	protected void generar() {
		if (metodo.getFormaMetodo().equals("dynamic")) {
			GeneradorCodigo.getInstance().write("\tLOAD 3\t; Apilo THIS");
			if(!metodo.getTipo().getNombre().equals("void")) {
				GeneradorCodigo.getInstance().write("\tRMEM 1\t; Reservo lugar para el retorno");
				GeneradorCodigo.getInstance().write("\tSWAP");
			}
			for (NodoExpresion exp : argumentosActuales) {
				exp.generar();
				GeneradorCodigo.getInstance().write("\tSWAP"); }
			GeneradorCodigo.getInstance().write("\tDUP");
			GeneradorCodigo.getInstance().write("\tLOADREF 0");
			GeneradorCodigo.getInstance().write("\tLOADREF " + metodo.getOffset() + "\t; Cargo la direcci�n del m�todo");
			GeneradorCodigo.getInstance().write("\tCALL");
		} else {
			/*
			 * COMPLETAR
			 */
		}
		if (getEncadenado() != null)
			getEncadenado().generar();
	}
}
