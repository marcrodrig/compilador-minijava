package semantico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lexico.Token;

public class Metodo {
	private Token token;
	private String formaMetodo;
	private boolean metodoFinal;
	private TipoRetorno tipo;
	private HashMap<String, Parametro> parametros;
	private boolean consolidado;
	
	public Metodo(Token token, String formaMetodo, TipoRetorno tipo, boolean metodoFinal, HashMap<String, Parametro> parametros) {
		this.token = token;
		this.formaMetodo = formaMetodo;
		this.metodoFinal = metodoFinal;
		this.tipo = tipo;
		this.parametros = parametros;
	}
	
	public String getNombre() {
		return token.getLexema();
	}

	public int cantidadParametros() {
		return parametros.size();
	}
	
	public HashMap<String, Parametro> getParametros() {
		return parametros;
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

	public Parametro getParametroPorNombre(String string) {
		return parametros.get(string);
	}

	// posicion va desde 1
		public Parametro getParametro(int posicion) {
			ArrayList<Parametro> listaParametros = new ArrayList<Parametro>(parametros.values());
			return listaParametros.get(posicion-1);
		}
	
	public void chequeoMetodosSobrecargados(List<Metodo> listaMetodos) throws ExcepcionSemantico {
			for (Metodo metodo : listaMetodos) {
				metodo.chequeoExistenciaTipoRetorno();
				if (this != metodo) {
					int posicion = 1;
					while (posicion <= this.getParametros().size()) {
						Parametro p1 = this.getParametro(posicion);
						Parametro p2 = metodo.getParametro(posicion);
						if (p1.getTipo().getNombre().equals(p2.getTipo().getNombre())) {
							throw new ExcepcionSemantico("[" + metodo.getToken().getNroLinea() + "] Error semántico: Método con misma signatura ya definido." );
						}
						posicion++;
					}
				}
			}
		}

	public void chequeoExistenciaTipoRetorno() throws ExcepcionSemantico {
		TablaSimbolos ts = TablaSimbolos.getInstance();
		if (tipo instanceof TipoClase)
		if (ts.getClase(tipo.getNombre()) == null) 
			throw new ExcepcionSemantico("[" + token.getNroLinea() + "] Error semántico: El tipo de retorno " + tipo.getNombre() + " del método " + token.getLexema() + " no está definido.");	
	}

	private Token getToken() {
		return token;
	}

	public boolean sobreescribeMetodo(Metodo met2) {
		boolean redefinido = getTipo().getNombre().equals(met2.getTipo().getNombre()) && getFormaMetodo().equals(met2.getFormaMetodo());
	
		int posicion = 1;
		while (redefinido && posicion <= cantidadParametros()) {
			Parametro p1 = getParametro(posicion);
			Parametro p2 = met2.getParametro(posicion);
			redefinido = p1.getTipo().getNombre().equals(p2.getTipo().getNombre()) ;
			posicion++;
		}
		return redefinido;
	}


	
}
