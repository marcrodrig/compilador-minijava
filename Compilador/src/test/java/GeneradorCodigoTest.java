package test.java;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import gc.GeneradorCodigo;
import main.Principal;

class GeneradorCodigoTest {

	@BeforeEach
	void init(TestInfo testInfo) {
		String displayName = testInfo.getDisplayName();
		System.out.println(displayName);
	}

	@AfterEach
	void resetTS() {
		System.out.println();
		Principal.ts.reset();
		GeneradorCodigo.reset();
	}

	@Test
	void testRapido() {
		String[] args = { "src/test/resources/gci/rapido.txt" };
		Principal.main(args);
	}

	@Test
	void testImpresionLiteralTrue() {
		String[] args = { "src/test/resources/gci/impresionLiteralTrue.txt" };
		Principal.main(args);
	}
	
	@Test
	void testImpresionLiteralFalse() {
		String[] args = { "src/test/resources/gci/impresionLiteralFalse.txt" };
		Principal.main(args);
	}
	/*@Test
	@DisplayName("TEST: Excepción esperada método main ausente")
	void testExcepcionEsperadaMetodoMainAusente() {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaMetodoMainAusente.txt" };
		/*AnalizadorSintactico analizadorSintactico;
		TablaSimbolos ts = TablaSimbolos.getInstance();
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.start();
			ExcepcionSemantico e = assertThrows(ExcepcionSemantico.class, () -> {
				ts.controlesSemanticos();
			});
			assertEquals("Error semántico: Método main sin definir.", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico | ExcepcionPanicMode e1) {
			fail("No debería suceder esto");
		}
		ts.reset();
		Principal.main(args);
	}*/

}
