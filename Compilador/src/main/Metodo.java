package main;

import java.util.HashMap;

public class Metodo {
	private String nombre;
	private String formaMetodo;
	private boolean metodoFinal;
	private TipoRetorno tipo;
	private HashMap<String, Parametro> parametros;
	
	public Metodo(String nombre, String formaMetodo, TipoRetorno tipo, boolean metodoFinal, HashMap<String, Parametro> parametros) {
		this.nombre = nombre;
		this.formaMetodo = formaMetodo;
		this.metodoFinal = metodoFinal;
		this.tipo = tipo;
		this.parametros = parametros;
	}
	
	public String getNombre() {
		return nombre;
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

}
