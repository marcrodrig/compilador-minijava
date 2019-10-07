package semantico;

import java.util.List;

public class NodoDecVarsLocales extends NodoSentencia {
	private List<Variable> varsLocales;
	
	public NodoDecVarsLocales(List<Variable> varsLocales) {
		this.varsLocales = varsLocales;
	}
	
}
