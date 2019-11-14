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
		Clase clase = CompiladorMiniJava.tablaSimbolos.getClase(tokenIdClase.getLexema());
		if (clase != null) {
		List<Unidad> metodos = clase.getTodosMetodosPorNombre(tokenIdMetVar.getLexema());
		if (metodos != null)
			for (Unidad u : metodos) {
				Metodo met = (Metodo) u;
				if(met.getCantidadParametros() == argsActuales.size())
					metodo = met;
			}
		if (metodo == null)  // no hay visibilidad en metodos, ver
			throw new ExcepcionSemantico("[" + tokenIdMetVar.getNroLinea() + ":" + tokenIdMetVar.getNroColumna()
			+ "] Error sem�ntico: El m�todo " + tokenIdMetVar.getLexema() + " con " + argsActuales.size() + " par�metros de la clase " + clase.getNombre() + " no es un m�todo v�lido.");
		if (!metodo.getFormaMetodo().equals("static"))
			throw new ExcepcionSemantico("[" + tokenIdMetVar.getNroLinea() + ":" + tokenIdMetVar.getNroColumna()
			+ "] Error sem�ntico: El m�todo " + tokenIdMetVar.getLexema() + " con " + argsActuales.size() + " par�metros de la clase " + clase.getNombre() + " no es est�tico.");
		int posicion = 1;
		boolean conforma = true;
		while (conforma && posicion <= metodo.getCantidadParametros()) {
			conforma = metodo.getParametroPorPosicion(posicion).getTipo().esCompatible(argsActuales.get(posicion-1).chequear());
			posicion++;
		}
		if (!conforma)
			throw new ExcepcionSemantico("[" + tokenIdMetVar.getNroLinea() + ":" + tokenIdMetVar.getNroColumna()
			+ "] Error sem�ntico: El tipo del par�metro nro " + (posicion-1) + " no es compatible con el m�todo " + metodo.getNombre() + " declarado con misma aridad.");
		if (getEncadenado() == null)
			return metodo.getTipo();
		else
			return getEncadenado().chequear(metodo.getTipo());
		} else
			throw new ExcepcionSemantico("[" + tokenIdClase.getNroLinea() + ":" + tokenIdClase.getNroColumna()
			+ "] Error sem�ntico: La clase " + tokenIdClase.getLexema() + " no est� declarada.");
		
	}

	@Override
	protected void generar() {
		GeneradorCodigo generadorCodigo = GeneradorCodigo.getInstance();
		if(!metodo.getTipo().getNombre().equals("void"))
			generadorCodigo.write("\tRMEM 1\t; Reservo memoria para el retorno del metodo");
		for (NodoExpresion exp : argsActuales)
			exp.generar();
		generadorCodigo.write("\tPUSH " + metodo.getLabel() +"\t; Apilo la etiqueta del metodo");
		generadorCodigo.write("\tCALL\t; Llamo al metodo");
		Encadenado encadenado = getEncadenado();
		if (encadenado != null)
			encadenado.generar();
	}
}