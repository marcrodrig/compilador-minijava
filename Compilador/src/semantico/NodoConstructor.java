package semantico;

import java.util.List;

import gc.GeneradorCodigo;
import lexico.Token;
import main.CompiladorMiniJava;

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
		Clase clase = CompiladorMiniJava.tablaSimbolos.getClase(token.getLexema());
		if (clase == null)
			throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
					+ "] Error semántico: La clase \"" + token.getLexema() + "\" no está declarada.");
		List<Unidad> constructores = clase.getConstructores();
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
			conforma = ctor.getParametroPorPosicion(posicion).getTipo().esCompatible(argumentosActuales.get(posicion - 1).chequear());
			posicion++;
		}
		if (!conforma)
			throw new ExcepcionSemantico("[" + token.getNroLinea() + ":" + token.getNroColumna()
					+ "] Error semántico: El tipo del parámetro nro " + (posicion - 1)
					+ " no es compatible con el del constructor declarado con misma aridad.");
		Encadenado encadenado = getEncadenado();
		if (encadenado == null)
			return new TipoClase(token);
		else
			return encadenado.chequear(new TipoClase(token));
	}

	@Override
	protected void generar() {
		GeneradorCodigo generadorCodigo = GeneradorCodigo.getInstance();
		Clase claseActual = ctor.declaradaEn();
		// Creación CIR
		generadorCodigo.write("\tRMEM 1\t; Creación CIR (clase " + claseActual.getNombre() + ")");
		generadorCodigo.write("\tPUSH " + (claseActual.cantidadAtributos() + 1)
				+ "\t; Reservo lugar para variables de instancia y VT (clase " + claseActual.getNombre() + ")");
		generadorCodigo.write("\tPUSH simple_malloc");
		generadorCodigo.write("\tCALL");
		// Asignación de la VT al CIR creado
		generadorCodigo.write("\tDUP\t; Asignación de la VT al CIR creado");
		generadorCodigo.write("\tPUSH VT_" + ctor.getNombre());
		generadorCodigo.write("\tSTOREREF 0\t;Guardo referecia a VT");
		generadorCodigo.write("\tDUP");
		// Proceso argumentos actuales
		for (NodoExpresion exp : argumentosActuales) {
			exp.generar();
			generadorCodigo.write("\tSWAP");
		}
		// Llamada a constructor
		generadorCodigo.write("\tPUSH " + ctor.getLabel() + "\t; Apilo etiqueta del constructor " + ctor.getLabel());
		generadorCodigo.write("\tCALL\t; Llamo al constuctor " + ctor.getLabel());
		// Proceso encadenado
		Encadenado encadenado = getEncadenado();
		if (encadenado != null)
			encadenado.generar();
	}

}
