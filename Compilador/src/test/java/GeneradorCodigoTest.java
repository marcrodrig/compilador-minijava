package test.java;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
	
	@Test
	void testImpresionLiteralEntero() {
		String[] args = { "src/test/resources/gci/impresionLiteralEntero.txt" };
		Principal.main(args);
	}
	
	@Test
	void testImpresionLiteralChar() {
		String[] args = { "src/test/resources/gci/impresionLiteralChar.txt" };
		Principal.main(args);
	}

	@Test
	void testImpresionLiteralString() {
		String[] args = { "src/test/resources/gci/impresionLiteralString.txt" };
		Principal.main(args);
	}
	
	@Test
	void testReadImpresionEntero() {
		String[] args = { "src/test/resources/gci/readImpresionEntero.txt" };
		Principal.main(args);
	}
	
	@Test
	void testImpresionTodosPrintln() {
		String[] args = { "src/test/resources/gci/impresionTodosPrintln.txt" };
		Principal.main(args);
	}
	
	@Test
	void testImpresionIfConYSinElse() {
		String[] args = { "src/test/resources/gci/impresionIfConYSinElse.txt" };
		Principal.main(args);
	}
	
	@Test
	void testImpresionWhile() {
		String[] args = { "src/test/resources/gci/impresionWhile.txt" };
		Principal.main(args);
	}
	
	@Test
	void testImpresionConstructorLlamadaDirectaReturn() {
		String[] args = { "src/test/resources/gci/impresionConstructorLlamadaDirectaReturn.txt" };
		Principal.main(args);
	}
	
	@Test
	void testJ() {
		String[] args = { "src/test/resources/gci/juju.txt" };
		Principal.main(args);
	}
	
}
