package semantico;

import java.util.List;

import gc.GeneradorCodigo;
import lexico.Token;
import main.CompiladorMiniJava;

public class NodoLlamadaEstatica extends NodoPrimario {
	private Token tokenIdClase;
	private Token tokenIdMetVar;
	private List<NodoExpresion> argsActuales;
	private Metodo metodo;

	public NodoLlamadaEstatica(Token tokenIdClase, Token tokenIdMetVar, List<NodoExpresion> argsActuales) {
		this.tokenIdClase = tokenIdClase;
		this.tokenIdMetVar = tokenIdMetVar;
		this.argsActuales = argsActuales;
	}
	
	public int getNroLinea() {
		return tokenIdClase.getNroLinea();
	}
	
	public int getNroColumna() {
		return tokenIdClase.getNroColumna();
	}

	@Override
	public TipoRetorno chequear() throws ExcepcionSemantico {
		Clase clase = CompiladorMiniJava.ts.getClase(tokenIdClase.getLexema());
		if (clase != null) {
		List<Unidad> metodos = clase.getTodosMetodosPorNombre(tokenIdMetVar.getLexema());
		if (metodos != null) {
			for (Unidad u : metodos) {
				Metodo met = (Metodo) u;
				if(met.getCantidadParametros() == argsActuales.size())
					metodo = met;
			}
		}
		if (metodo == null)  // no hay visibilidad en metodos, ver
			throw new ExcepcionSemantico("[" + tokenIdMetVar.getNroLinea() + ":" + tokenIdMetVar.getNroColumna()
			+ "] Error semántico: El método " + tokenIdMetVar.getLexema() + " con " + argsActuales.size() + " parámetros de la clase " + clase.getNombre() + " no es un método válido.");
		if (!metodo.getFormaMetodo().equals("static"))
			throw new ExcepcionSemantico("[" + tokenIdMetVar.getNroLinea() + ":" + tokenIdMetVar.getNroColumna()
			+ "] Error semántico: El método " + tokenIdMetVar.getLexema() + " con " + argsActuales.size() + " parámetros de la clase " + clase.getNombre() + " no es estático.");
		int posicion = 1;
		boolean conforma = true;
		while (conforma && posicion <= metodo.getCantidadParametros()) {
			conforma = metodo.getParametroPorPosicion(1).getTipo().esCompatible(argsActuales.get(posicion-1).chequear());
			posicion++;
		}
		if (!conforma)
			throw new ExcepcionSemantico("[" + tokenIdMetVar.getNroLinea() + ":" + tokenIdMetVar.getNroColumna()
			+ "] Error semántico: El tipo del parámetro nro " + (posicion-1) + " no es compatible con el método " + metodo.getNombre() + " declarado con misma aridad.");
		if (getEncadenado() == null)
			return metodo.getTipo();
		else
			return getEncadenado().chequear(metodo.getTipo());
		} else
			throw new ExcepcionSemantico("[" + tokenIdClase.getNroLinea() + ":" + tokenIdClase.getNroColumna()
			+ "] Error semántico: La clase " + tokenIdClase.getLexema() + " no está declarada.");
		
	}

	@Override
	protected void generar() {
		if(!metodo.getTipo().getNombre().equals("void"))
			GeneradorCodigo.getInstance().write("\tRMEM 1\t; Reservo memoria para el retorno del metodo");
		for (NodoExpresion exp : argsActuales)
			exp.generar();
		GeneradorCodigo.getInstance().write("\tPUSH " + metodo.getLabel() +"\t; Apilo la etiqueta del metodo");
		GeneradorCodigo.getInstance().write("\tCALL\t; Llamo al metodo");
		Encadenado encadenado = getEncadenado();
		if (encadenado != null)
			encadenado.generar();
	}
}