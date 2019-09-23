package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Clase {
	private Token token;
	private String superclase;
//	private HashMap<String, VariableInstancia> atributos;
	private HashMap<String, Metodo> metodos;
	private List<Constructor> constructores;
	
	public Clase(Token token, String superclase) {
		this.token = token;
		this.superclase = superclase;
		metodos = new HashMap<String, Metodo>();
		constructores = new ArrayList<Constructor>();
	}

	public String getNombre() {
		return token.getLexema();
	}
	
	public void insertarMetodo(Metodo metodo) {
		metodos.put(metodo.getNombre(), metodo);
	}

	public HashMap<String, Metodo> getMetodos() {
		return metodos;
	}
	
	public List<Constructor> getConstructores() {
		return constructores;
	}
	
	
	public Metodo getMetodo(String nombreMetodo) {
		return metodos.get(nombreMetodo);
	}

	public Token getToken() {
		return token;
	}

	public void chequeoDeclaraciones() throws ExcepcionSemantico {
		chequeoHerenciaCircular();
		chequeoConstructores();
	}
	
	private void chequeoHerenciaCircular() throws ExcepcionSemantico {
		if (getNombre().equals(superclase)) {
			throw new ExcepcionSemantico("[" + token.getNroLinea() + "] Error semántico: La clase " + getNombre() + " tiene herencia circular." );
		}
	}

	private void chequeoConstructores() throws ExcepcionSemantico {
		if (constructores.isEmpty()) {
			agregarConstructorPredefinido();
		}
		for (Constructor ctor1 : constructores) {
			for (Constructor ctor2 : constructores) {
				HashMap<String, Parametro> parametrosCtor1 = ctor1.getParametros();
				HashMap<String, Parametro> parametrosCtor2 = ctor2.getParametros();
				if (parametrosCtor1.size() == parametrosCtor2.size() && ctor1 != ctor2) {
					int posicion = 1;
					while (posicion <= ctor1.getParametros().size()) {
						Parametro p1 = ctor1.getParametro(posicion);
						Parametro p2 = ctor2.getParametro(posicion);
						if (p1.getTipo().getNombre().equals(p2.getTipo().getNombre())) {
							throw new ExcepcionSemantico("[" + ctor2.getToken().getNroLinea() + "] Error semántico: Constructor con misma signatura ya definido." );
						}
						posicion++;
					}
				}
			}
		}
	}

	private void agregarConstructorPredefinido() {
		Token token = new Token("idClase", getNombre(), 0, 0);
		Constructor ctor = new Constructor(token, new HashMap<String, Parametro>());
		constructores.add(ctor);
	}

	// Posición desde 1
	public Constructor getConstructor(int posicion) {
		return constructores.get(posicion-1);
	}
	
}
