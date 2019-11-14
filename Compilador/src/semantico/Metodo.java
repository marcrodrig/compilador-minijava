package semantico;

import java.util.LinkedHashMap;
import java.util.List;
import gc.GeneradorCodigo;
import lexico.Token;
import main.CompiladorMiniJava;

public class Metodo extends Unidad {
	private String formaMetodo;
	private boolean metodoFinal;
	private TipoRetorno tipo;

	public Metodo(Token token, String formaMetodo, TipoRetorno tipo, boolean metodoFinal,
			LinkedHashMap<String, Parametro> parametros) {
		super(token, parametros);
		this.formaMetodo = formaMetodo;
		this.metodoFinal = metodoFinal;
		this.tipo = tipo;
	}

	public String getFormaMetodo() {
		return formaMetodo;
	}

	public boolean isMetodoFinal() {
		return metodoFinal;
	}

	public TipoRetorno getTipo() {
		return tipo;
	}

	public boolean isMetodoMain() {
		return getNombre().equals("main") && getTipo().getNombre().equals("void") && getFormaMetodo().equals("static")
				&& getCantidadParametros() == 0;
	}

	private void chequeoExistenciaTipoRetorno() throws ExcepcionSemantico {
		if (tipo instanceof TipoClase)
			if (CompiladorMiniJava.tablaSimbolos.getClase(tipo.getNombre()) == null)
				throw new ExcepcionSemantico("[" + getNroLinea() + ":" + getNroColumna() + "] Error semántico: El tipo de retorno "
								+ tipo.getNombre() + " del método " + getNombre() + " no está definido.");
	}

	private void chequeoMetodosSobrecargados(List<Unidad> listaMetodos) throws ExcepcionSemantico {
		for (Unidad unidad : listaMetodos) {
			Metodo metodo = (Metodo) unidad;
			metodo.chequeoExistenciaTipoRetorno();
			if (this != metodo && getCantidadParametros() == metodo.getCantidadParametros())
				throw new ExcepcionSemantico("[" + metodo.getNroLinea() + ":" + metodo.getNroColumna()
						+ "] Error semántico: Método duplicado (misma cantidad de argumentos formales).");
		}
	}

	public boolean chequeoRedefinicionMetodo(Metodo met2) throws ExcepcionSemantico {
		boolean redefinido = getTipo().getNombre().equals(met2.getTipo().getNombre())
				&& getFormaMetodo().equals(met2.getFormaMetodo());
		int posicion = 1;
		while (redefinido && posicion <= getCantidadParametros()) {
			Parametro p1 = getParametroPorPosicion(posicion);
			Parametro p2 = met2.getParametroPorPosicion(posicion);
			redefinido = p1.getTipo().getNombre().equals(p2.getTipo().getNombre());
			posicion++;
		}
		if (!redefinido) {
			throw new ExcepcionSemantico("[" + getNroLinea() + ":" + getNroColumna()
					+ "] Error semántico: Incorrecta redefinición de método " + getNombre() + ".");
		}
		if (redefinido && met2.isMetodoFinal())
			throw new ExcepcionSemantico("[" + getNroLinea() + ":" + getNroColumna() + "] Error semántico: El método "
					+ getNombre() + " no se puede sobreescribir.");
		return redefinido;
	}

	public void chequeoDeclaraciones(List<Unidad> listaMetodos) throws ExcepcionSemantico {
		for (Parametro paramMetodo : getParametros().values())
			try {
				paramMetodo.chequeoDeclaraciones();
				if (formaMetodo.equals("static")) {
					paramMetodo.setOffset(2 + getCantidadParametros() - paramMetodo.getPosicion());
				} else {
					paramMetodo.setOffset(3 + getCantidadParametros() - paramMetodo.getPosicion());
				}
			} catch (ExcepcionSemantico e) {
				CompiladorMiniJava.tablaSimbolos.setRSem();
				System.out.println(e.toString());
			}
		chequeoMetodosSobrecargados(listaMetodos);
	}

	@Override
	protected void generar() {
		GeneradorCodigo generadorCodigo = GeneradorCodigo.getInstance();
		generadorCodigo.write(getLabel() + ":");
		generadorCodigo.write("\tLOADFP\t; Guardo enlace dinámico");
		generadorCodigo.write("\tLOADSP\t; Inicializo FP");
		generadorCodigo.write("\tSTOREFP");
		generadorCodigo.newLine();
		NodoBloque bloque = getBloque();
		bloque.generar();
		generadorCodigo.write("\tSTOREFP\t; Restablezco el contexto");
		if (formaMetodo.equals("static"))
			generadorCodigo.write("\tRET " + getCantidadParametros()
					+ "\t; Retorno y libero espacio de los parametros del metodo " + getNombre());
		else
			generadorCodigo.write("\tRET " + (getCantidadParametros() + 1)
					+ "\t; Retorno y libero espacio de los parametros del metodo y del THIS " + getNombre());
	}

}