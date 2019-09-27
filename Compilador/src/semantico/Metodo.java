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

	private int getNroLinea() {
		return token.getNroLinea();
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
	
	public HashMap<String, Parametro> getParametros() {
		return parametros;
	}

	public Parametro getParametroPorNombre(String string) {
		return parametros.get(string);
	}

	// posicion va desde 1
	public Parametro getParametroPorPosicion(int posicion) {
		ArrayList<Parametro> listaParametros = new ArrayList<Parametro>(parametros.values());
		return listaParametros.get(posicion-1);
	}
	
	public int getCantidadParametros() {
		return parametros.size();
	}
	
	private void chequeoMetodosSobrecargados(List<Metodo> listaMetodos) throws ExcepcionSemantico {
		for (Metodo metodo : listaMetodos) {
			metodo.chequeoExistenciaTipoRetorno();
			if (this != metodo && getCantidadParametros() == metodo.getCantidadParametros())
				throw new ExcepcionSemantico("[" + metodo.getNroLinea() + "] Error semántico: Método con mismo nombre y cantidad de parámetros ya definido." );
		}
	}

	private void chequeoExistenciaTipoRetorno() throws ExcepcionSemantico {
		TablaSimbolos ts = TablaSimbolos.getInstance();
		if (tipo instanceof TipoClase)
			if (ts.getClase(tipo.getNombre()) == null) 
				throw new ExcepcionSemantico("[" + token.getNroLinea() + "] Error semántico: El tipo de retorno " + tipo.getNombre() + " del método " + token.getLexema() + " no está definido.");	
	}
	
	public boolean sobreescribeMetodo(Metodo met2) throws ExcepcionSemantico {
		if (met2.isMetodoFinal())
			throw new ExcepcionSemantico("[" + token.getNroLinea() + "] Error semántico: El método " + getNombre() + " no se puede sobreescribir.");
		else 
		{
			boolean redefinido = getTipo().getNombre().equals(met2.getTipo().getNombre()) && getFormaMetodo().equals(met2.getFormaMetodo());
			int posicion = 1;
			while (redefinido && posicion <= getCantidadParametros()) {  // ¿¿ < 0 <= ??
				Parametro p1 = getParametroPorPosicion(posicion);
				Parametro p2 = met2.getParametroPorPosicion(posicion);
				redefinido = p1.getTipo().getNombre().equals(p2.getTipo().getNombre()) ;
				posicion++;
			}
			return redefinido;
		}
	}

	public boolean isMetodoMain() {
		return getNombre().equals("main") && getFormaMetodo().equals("static") && getCantidadParametros() == 0;
	}

	public boolean chequeoDeclaraciones(List<Metodo> listaMetodos) throws ExcepcionSemantico {
		for (Parametro paramMetodo : parametros.values())
			paramMetodo.chequeoDeclaraciones();	
		chequeoMetodosSobrecargados(listaMetodos);
		return isMetodoMain();
	}
	
}