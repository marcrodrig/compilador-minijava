package semantico;

import java.util.List;

import gc.GeneradorCodigo;
import lexico.Token;
import main.Principal;

public class NodoConstructor extends NodoPrimario {
	private Token token;
	private List<NodoExpresion> argumentosActuales;
	private Constructor ctor;

	public NodoConstructor(Token token, List<NodoExpresion> argumentosActuales) {
		this.token = token;
		this.argumentosActuales = argumentosActuales;
	}

	@Override
	public TipoRetorno chequear() throws ExcepcionSemantico {
		Clase clase = Principal.ts.getClase(token.getLexema());
		if (clase == null)
			throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
					+ "] Error sem�ntico: La clase \"" + token.getLexema() + "\" no est� declarada.");
		List<Unidad> constructores = clase.getConstructores();
		ctor = null;
		for (Unidad u : constructores)
			if (u.getCantidadParametros() == argumentosActuales.size())
				ctor = (Constructor) u;
		if (ctor == null)
			throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
					+ "] Error sem�ntico: No existe el constructor de la clase \"" + token.getLexema() + "\" con "
					+ argumentosActuales.size() + " par�metros.");
		int posicion = 1;
		boolean conforma = true;
		while (conforma && posicion <= ctor.getCantidadParametros()) {
			conforma = ctor.getParametroPorPosicion(1).getTipo()
					.esCompatible(argumentosActuales.get(posicion - 1).chequear());
			posicion++;
		}
		if (!conforma)
			throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
					+ "] Error sem�ntico: El tipo del par�metro nro " + (posicion - 1)
					+ " no es compatible con el del constructor declarado con misma aridad.");
		if (getEncadenado() == null)
			return new TipoClase(token);
		else
			return getEncadenado().chequear(new TipoClase(token));
	}
	
	@Override
	protected void generar() {
		Clase claseActual = ctor.declaradaEn();
		// Creaci�n CIR
		GeneradorCodigo.getInstance().write("\tRMEM 1\t; Creaci�n CIR");
		GeneradorCodigo.getInstance().write("\tPUSH " + (claseActual.cantidadAtributos() + 1) + "\t; Reservo lugar para variables de instancia y VT");
		GeneradorCodigo.getInstance().write("\tPUSH simple_malloc");
		GeneradorCodigo.getInstance().write("\tCALL");
		// Asignaci�n de la VT al CIR creado
		GeneradorCodigo.getInstance().write("\tDUP\t; Asignaci�n de la VT al CIR creado");
		GeneradorCodigo.getInstance().write("\tPUSH VT_" + ctor.getNombre());
		GeneradorCodigo.getInstance().write("\tSTOREREF 0\t;Guardo referecia a VT");
		GeneradorCodigo.getInstance().write("\tDUP");
		// Proceso argumentos actuales
		for (NodoExpresion exp : argumentosActuales) {
			exp.generar();
			GeneradorCodigo.getInstance().write("\tSWAP"); }
		// Proceso encadenado
		if(getEncadenado() != null) {
			getEncadenado().generar();
		} else {
		// Llamada
			GeneradorCodigo.getInstance().write("\tPUSH " + ctor.getLabel() + "\t; Apilo etiqueta del constructor");
			GeneradorCodigo.getInstance().write("\tCALL");
		}
	}
	 
}
