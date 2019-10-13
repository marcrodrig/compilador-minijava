package semantico;

import lexico.Token;
import main.Principal;

public class TipoClase extends Tipo {
	
	private String nombreTipo;
	
	public TipoClase(Token token) {
		super(token);
		this.nombreTipo = token.getLexema();
	}

	@Override
	public boolean esCompatible(TipoRetorno tipo) {
        if (!this.getNombre().equals("null")) {
            if (tipo.getNombre().equals("null")) {
                return true;
            }
            Clase clase = Principal.ts.getClase(getNombre());
            if (clase != null) {
            	Clase claseReceptora = Principal.ts.getClase(tipo.getNombre());
                if (claseReceptora != null) {
                    while (!claseReceptora.getNombre().equals("Object")) {
                        if (claseReceptora.getNombre().equals(clase.getNombre())) {
                            return true;
                        }
                        claseReceptora = Principal.ts.getClase(claseReceptora.getSuperclase());
                    }
                    return false;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
	
	public String getNombre() {
		return nombreTipo;
		
	}
	
}