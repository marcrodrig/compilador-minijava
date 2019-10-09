package semantico;

import main.Principal;

public abstract class TipoRetorno {
	public abstract String getNombre();

	/*public boolean conformaTipo(TipoRetorno tipoExpresion) {
		String tipo = getNombre();
		String nombreTipoExpresion = tipoExpresion.getNombreTipo();
		/*
		 * CHEQUEAR ESTO int e intLiteral
		 
		if (!tipo.equals(nombreTipoExpresion)) {
			boolean conforma = true;
			if (this instanceof TipoClase) {
			Clase claseRetorno = Principal.ts.getClase(tipo);
			while(conforma && !claseRetorno.getSuperclase().equals(null) ) {
				// ver aca
			}
			return conforma;
			} else return false;
		} else
		return true;
	}*/

	public abstract boolean esCompatible(TipoRetorno tipo);
}
