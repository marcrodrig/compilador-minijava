package semantico;

import java.util.LinkedHashMap;
import java.util.List;
import gc.GeneradorCodigo;
import lexico.Token;
import main.Principal;

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
	
	private void chequeoMetodosSobrecargados(List<Unidad> listaMetodos) throws ExcepcionSemantico {
		for (Unidad unidad : listaMetodos) {
			Metodo metodo = (Metodo) unidad;
			metodo.chequeoExistenciaTipoRetorno();
			if (this != metodo && getCantidadParametros() == metodo.getCantidadParametros())
				throw new ExcepcionSemantico("[" + metodo.getNroLinea() + ":" + metodo.getNroColumna()
						+ "] Error semántico: Método duplicado (misma cantidad de argumentos formales).");
		}
	}

	private void chequeoExistenciaTipoRetorno() throws ExcepcionSemantico {
		if (tipo instanceof TipoClase)
			if (Principal.ts.getClase(tipo.getNombre()) == null)
				throw new ExcepcionSemantico("[" + getNroLinea() + ":" + getNroColumna()
						+ "] Error semántico: El tipo de retorno " + tipo.getNombre() + " del método "
						+ getNombre() + " no está definido.");
	}

	public void chequeoRedefinicionMetodo(Metodo met2) throws ExcepcionSemantico {
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
			throw new ExcepcionSemantico("[" + getNroLinea() + ":" + getNroColumna()
					+ "] Error semántico: El método " + getNombre() + " no se puede sobreescribir.");
	}

	public boolean isMetodoMain() {
		return getNombre().equals("main") && getTipo().getNombre().equals("void") && getFormaMetodo().equals("static")
				&& getCantidadParametros() == 0;
	}

	public void chequeoDeclaraciones(List<Unidad> listaMetodos) throws ExcepcionSemantico {
		for (Parametro paramMetodo : getParametros().values())
			try {
				paramMetodo.chequeoDeclaraciones();
				if (formaMetodo.equals("static")) {
	                paramMetodo.setOffset(2 + getCantidadParametros() - paramMetodo.getPosicion());
	            } else {
	                paramMetodo.setOffset(3 + getCantidadParametros() - paramMetodo.getPosicion());
	            }    /**
	                 * VER SI AGREGAR
	                 * vars.put(p.getNombre(), p);
	                 */
			} catch (ExcepcionSemantico e) {
				Principal.ts.setRS();
				System.out.println(e.toString());
			}
		chequeoMetodosSobrecargados(listaMetodos);
	}

	@Override
	protected void generar() {
		GeneradorCodigo.getInstance().write(getLabel() + ":");

		GeneradorCodigo.getInstance().write("\tLOADFP\t; Guardo enlace dinámico");
		GeneradorCodigo.getInstance().write("\tLOADSP\t; Inicializo FP");
		GeneradorCodigo.getInstance().write("\tSTOREFP");
		GeneradorCodigo.getInstance().newLine();
		NodoBloque bloque = getBloque();
        bloque.generar();
        GeneradorCodigo.getInstance().write("\tSTOREFP\t; Restablezco el contexto");
        if (formaMetodo.equals("static")) {
        	GeneradorCodigo.getInstance().write("\tRET " + getCantidadParametros() + "\t; Retorno y libero espacio de los parametros del metodo " + getNombre());
        } else
        GeneradorCodigo.getInstance().write("\tRET " + (getCantidadParametros() + 1) + "\t; Retorno y libero espacio de los parametros del metodo y del THIS " + getNombre());
		//newLine?
	}

}