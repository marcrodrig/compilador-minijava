package test.java;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import gc.GeneradorCodigo;
import main.CompiladorMiniJava;

class InferenciaTiposInline {

	@BeforeEach
	void init(TestInfo testInfo) {
		String displayName = testInfo.getDisplayName();
		System.out.println(displayName);
	}

	@AfterEach
	void resetTS() {
		System.out.println();
		CompiladorMiniJava.tablaSimbolos.reset();
		GeneradorCodigo.reset();
	}
/*
	@Test
	void testAtributoInferencia() {
		String[] args = { "src/test/resources/inferenciaTipos/atributoInferencia.txt" };
		CompiladorMiniJava.main(args);
	}
*/
	@Test
	void testAtributoInferenciaInvalido() {
		String[] args = { "src/test/resources/inferenciaTipos/atributoInferenciaInvalido.txt" };
		CompiladorMiniJava.main(args);
	}
}
