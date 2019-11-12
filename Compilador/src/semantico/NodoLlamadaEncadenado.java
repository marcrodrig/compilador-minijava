package semantico;

import java.util.List;

import gc.GeneradorCodigo;
import lexico.Token;
import main.CompiladorMiniJava;

public class NodoLlamadaEncadenado extends Encadenado {
	private Token token;
	private List<NodoExpresion> argumentosActuales;
	private Metodo metodo;
	
	public NodoLlamadaEncadenado(Token token, List<NodoExpresion> argumentosActuales) {
		this.token = token;
		this.argumentosActuales = argumentosActuales;
	}
	
	public List<NodoExpresion> getArgsActuales() {
		return argumentosActuales;
	}

	public int getNroLinea() {
		return token.getNroLinea();
	}
	
	public int getNroColumna() {
		return token.getNroColumna();
	}
	
	@Override
	public TipoRetorno chequear(TipoRetorno tipo) throws ExcepcionSemantico {
		if (tipo instanceof TipoClase) {
			Clase clase = CompiladorMiniJava.ts.getClase(tipo.getNombre());
			List<Unidad> metodos = clase.getTodosMetodosPorNombre(token.getLexema());
			metodo = null;
			if (metodos != null) {
				for (Unidad u : metodos) {
					Metodo met = (Metodo) u;
					if (met.getCantidadParametros() == argumentosActuales.size())
						metodo = met;
				}
			}
			if (metodo == null) // no hay visibilidad en metodos, ver
				throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
						+ "] Error semántico: El método " + token.getLexema() + " con " + argumentosActuales.size()
						+ " parámetros de la clase " + clase.getNombre() + " no es un método válido.");
			int posicion = 1;
			boolean conforma = true;
			while (conforma && posicion <= metodo.getCantidadParametros()) {
				conforma = metodo.getParametroPorPosicion(1).getTipo()
						.esCompatible(argumentosActuales.get(posicion - 1).chequear());
				posicion++;
			}
			if (!conforma)
				throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
						+ "] Error semántico: El tipo del parámetro nro " + (posicion - 1)
						+ " no es compatible con el método " + metodo.getNombre() + " declarado con misma aridad.");
			if (getEncadenado() == null)
				return metodo.getTipo();
			else
				return getEncadenado().chequear(metodo.getTipo());
		} else
			throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
					+ "] Error semántico: La llamada encadenada debe tener como receptor un tipo clase y el tipo receptor es "
					+ tipo.getNombre() + ".");
	}

	@Override
	protected void generar() {
		if (metodo.getFormaMetodo().equals("dynamic")) {
			if(!metodo.getTipo().getNombre().equals("void")) {
				GeneradorCodigo.getInstance().write("\tRMEM 1\t; Reservo lugar para el retorno");
				GeneradorCodigo.getInstance().write("\tSWAP");
			}
			for (NodoExpresion exp : argumentosActuales) {
				exp.generar();
				GeneradorCodigo.getInstance().write("\tSWAP"); }
			GeneradorCodigo.getInstance().write("\tDUP");
			GeneradorCodigo.getInstance().write("\tLOADREF 0");
			GeneradorCodigo.getInstance().write("\tLOADREF " + metodo.getOffset() + "\t; Cargo la dirección del método " + metodo.getNombre() + " (clase " + metodo.declaradaEn().getNombre() + ")");
			GeneradorCodigo.getInstance().write("\tCALL");
		} else {
			if(!metodo.getTipo().getNombre().equals("void")) {
				GeneradorCodigo.getInstance().write("\tRMEM 1\t; Reservo lugar para el retorno");
				GeneradorCodigo.getInstance().write("\tSWAP");
			}	// o else?
				GeneradorCodigo.getInstance().write("\tPOP");
				for (NodoExpresion exp : argumentosActuales) {
					exp.generar();
					GeneradorCodigo.getInstance().write("\tSWAP"); }
				GeneradorCodigo.getInstance().write("\tPUSH " + metodo.getLabel() +"\t; Apilo la etiqueta del metodo");
				GeneradorCodigo.getInstance().write("\tCALL\t; Llamo al metodo");
		}
		Encadenado encadenado = getEncadenado();
		if (encadenado != null)
			encadenado.generar();
	}
}
