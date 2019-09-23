package main;

public class Parametro {
	private String nombre;
	private Tipo tipo;
	private int posicion;
	
	public Parametro(String nombre, Tipo tipo) {
		this.nombre = nombre;
		this.tipo = tipo;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public int getPosicion() {
		return posicion;
	}
	
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	public String getNombre() {
		return nombre;
	}
}
