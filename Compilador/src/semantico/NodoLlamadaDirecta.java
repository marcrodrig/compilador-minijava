package semantico;

import java.util.List;

import gc.GeneradorCodigo;
import lexico.Token;
import main.CompiladorMiniJava;

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
		Clase claseActual = CompiladorMiniJava.tablaSimbolos.getClaseActual();
		List<Unidad> metodos = claseActual.getTodosMetodosPorNombre(token.getLexema());
		metodo = null;
		if (metodos != null) {
			for (Unidad u : metodos) {
				Metodo met = (Metodo) u;
				if (met.getCantidadParametros() == argumentosActuales.size())
					metodo = met;
			}
		}
		if (metodo == null)
			throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
					+ "] Error sem�ntico: El m�todo " + token.getLexema() + " con " + argumentosActuales.size()
					+ " par�metros de la clase " + claseActual.getNombre() + " no es un m�todo v�lido.");
		if (metodo.getFormaMetodo().equals("static") && !metodo.declaradaEn().getNombre().equals(claseActual.getNombre()))
			throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna() + "] Error sem�ntico: El m�todo "
							+ token.getLexema() + " con " + argumentosActuales.size() + " par�metros de la clase "
							+ claseActual.getNombre() + " es est�tico, no se puede llamar directamente.");
		if (metodo.getFormaMetodo().equals("dynamic")) {
			Unidad unidadActual = CompiladorMiniJava.tablaSimbolos.getUnidadActual();
			if (unidadActual instanceof Metodo) {
				Metodo metodoContexto = (Metodo) unidadActual;
				if (metodoContexto.getFormaMetodo().equals("static"))
					throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
							+ "] Error sem�ntico: El m�todo " + token.getLexema() + " con " + argumentosActuales.size()
							+ " par�metros de la clase " + claseActual.getNombre()
							+ " es din�mico, no se puede llamar directamente en un contexto est�tico.");
			}
		}
		int posicion = 1;
		boolean conforma = true;
		while (conforma && posicion <= metodo.getCantidadParametros()) {
			conforma = metodo.getParametroPorPosicion(posicion).getTipo().esCompatible(argumentosActuales.get(posicion - 1).chequear());
			posicion++;
		}
		if (!conforma)
			throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
					+ "] Error sem�ntico: El tipo del par�metro nro " + (posicion - 1)
					+ " no es compatible con el m�todo " + metodo.getNombre() + " declarado con misma aridad.");
		Encadenado encadenado = getEncadenado();
		if (encadenado == null)
			return metodo.getTipo();
		else
			return encadenado.chequear(metodo.getTipo());
	}

	@Override
	protected void generar() {
		GeneradorCodigo generadorCodigo = GeneradorCodigo.getInstance();
		if (metodo.getFormaMetodo().equals("dynamic")) {
			generadorCodigo.write("\tLOAD 3\t; Apilo THIS");
			if (!metodo.getTipo().getNombre().equals("void")) {
				generadorCodigo.write("\tRMEM 1\t; Reservo lugar para el retorno");
				generadorCodigo.write("\tSWAP");
			}
			for (NodoExpresion exp : argumentosActuales) {
				exp.generar();
				generadorCodigo.write("\tSWAP");
			}
			generadorCodigo.write("\tDUP");
			generadorCodigo.write("\tLOADREF 0");
			generadorCodigo.write("\tLOADREF " + metodo.getOffset() + "\t; Cargo la direcci�n del m�todo "
					+ metodo.getNombre() + " (clase " + metodo.declaradaEn().getNombre() + ")");
			generadorCodigo.write("\tCALL");
		} else {
			if (!metodo.getTipo().getNombre().equals("void")) {
				generadorCodigo.write("\tRMEM 1\t; Reservo lugar para el retorno");
				generadorCodigo.write("\tSWAP");
			}
			for (NodoExpresion exp : argumentosActuales) {
				exp.generar();
				generadorCodigo.write("\tSWAP");
			}
			generadorCodigo.write("\tPUSH " + metodo.getLabel() + "\t; Apilo la etiqueta del metodo");
			generadorCodigo.write("\tCALL\t; Llamo al metodo");
		}
		Encadenado encadenado = getEncadenado();
		if (encadenado != null)
			encadenado.generar();
	}
}
