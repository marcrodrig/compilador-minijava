package semantico;

import lexico.Token;
import main.CompiladorMiniJava;

public class TipoClase extends Tipo {

	private String nombreTipo;

	public TipoClase(Token token) {
		super(token);
		this.nombreTipo = token.getLexema();
	}

	public String getNombre() {
		return nombreTipo;
	}

	@Override
	public boolean esCompatible(TipoRetorno tipo) {
		if (tipo.getNombre().equals("void") || tipo.getNombre().equals("Object"))
			return true;
		Clase clase = CompiladorMiniJava.tablaSimbolos.getClase(getNombre());
		if (clase != null) {
			Clase claseReceptora = CompiladorMiniJava.tablaSimbolos.getClase(tipo.getNombre());
			if (claseReceptora != null) {
				while (!claseReceptora.getNombre().equals("Object")) {
					if (claseReceptora.getNombre().equals(clase.getNombre()))
						return true;
					claseReceptora = CompiladorMiniJava.tablaSimbolos.getClase(claseReceptora.getSuperclase());
				}
				return false;
			} else
				return false;
		} else
			return false;
	}

}