package semantico;

import lexico.Token;
import main.Principal;

public class TipoClase extends TipoReferencia {
	private Token token;
	
	public TipoClase(Token token) {
		this.token = token;
	}
	
	public String getNombre() {
		return token.getLexema();
	}

	public int getNroLinea() {
		return token.getNroLinea();
	}

	public int getNroColumna() {
		return token.getNroColumna();
	}

	@Override
	public boolean esCompatible(TipoRetorno tipo) {
        //System.out.println("El tipo Clase: "+this.getNombre()+" El tipo pasado por param: "+tipo.getNombre());
        if (!this.getNombre().equals("null")) {
            if (tipo.getNombre().equals("null")) {
                return true;
            }
            Clase clase = Principal.ts.getClase(getNombre());
            if (clase != null) {
            	Clase claseReceptora = Principal.ts.getClase(tipo.getNombre());
                if (claseReceptora != null) {
                   // Clase claseActual = AnalizadorSintactico.getTs().getClase(this.getNombre());
                   // Clase claseThis = AnalizadorSintactico.getTs().getClase(tipo.getNombre());
                    while (!claseReceptora.getNombre().equals("Object")) {
                        if (claseReceptora.getNombre().equals(clase.getNombre())) {
                            return true;
                        }
                        claseReceptora = Principal.ts.getClase(claseReceptora.getSuperclase());
                    }
                    return false;
                    //throw new Exception("El tipo " + nombre + " no es compatible con " + tipo.getNombre() + " en la linea " + linea);
                } else {
                    return false;
                    //throw new Exception("El tipo " + tipo.getNombre() + " no existe, en la linea " + linea);
                }
            } else {
                return false;
                // throw new Exception("El tipo " + nombre + " no existe, en la linea " + linea);

            }

        } else {
            return false;
            //throw new Exception("El tipo null no es compatible en la linea " + nombre);
        }

    }
/*
    public boolean esCompatibleBinario(TipoClase tipo) {
        Clase actual = AnalizadorSintactico.getTs().getClase(nombre);
        if (this.getNombre().equals("null") || tipo.getNombre().equals("null")) {
           return true;
        }
        while (!actual.getNombre().equals("Object")) {
            Clase aux = AnalizadorSintactico.getTs().getClase(tipo.getNombre());
            while (!aux.getNombre().equals("Object")){
                if(actual.getNombre().equals(aux.getNombre())){
                    return true;
                }
                aux = AnalizadorSintactico.getTs().getClase(aux.getHereda());
            }
            actual = AnalizadorSintactico.getTs().getClase(actual.getHereda());
        }

        return false;

	}*/
	
}