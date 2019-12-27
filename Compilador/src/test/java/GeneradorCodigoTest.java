package test.java;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import gc.GeneradorCodigo;
import main.CompiladorMiniJava;

class GeneradorCodigoTest {

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

	@Test
	void testRapido() {
		String[] args = { "src/test/resources/gci/c3.txt" };
		CompiladorMiniJava.main(args);
	}

	@Test
	void testImpresionLiteralTrue() {
		String[] args = { "src/test/resources/gci/impresionLiteralTrue.txt" };
		CompiladorMiniJava.main(args);
	}
	
	@Test
	void testImpresionLiteralFalse() {
		String[] args = { "src/test/resources/gci/impresionLiteralFalse.txt" };
		CompiladorMiniJava.main(args);
	}
	
	@Test
	void testImpresionLiteralEntero() {
		String[] args = { "src/test/resources/gci/impresionLiteralEntero.txt" };
		CompiladorMiniJava.main(args);
	}
	
	@Test
	void testImpresionLiteralChar() {
		String[] args = { "src/test/resources/gci/impresionLiteralChar.txt" };
		CompiladorMiniJava.main(args);
	}
	
	@Test
	void testImpresionLiteralCharConBarraInvertida() {
		String[] args = { "src/test/resources/gci/impresionLiteralCharConBarraInvertida.txt" };
		CompiladorMiniJava.main(args);
	}

	@Test
	void testImpresionLiteralString() {
		String[] args = { "src/test/resources/gci/impresionLiteralString.txt" };
		CompiladorMiniJava.main(args);
	}
	
	@Test
	void testReadImpresionASCIIcaracter() {
		String[] args = { "src/test/resources/gci/readImpresionASCIIcaracter.txt" };
		CompiladorMiniJava.main(args);
	}
	
	@Test
	void testImpresionAsignacionInlineAtributoVarEncadenado() {
		String[] args = { "src/test/resources/gci/impresionAsignacionInlineAtributoVarEncadenado.txt" };
		CompiladorMiniJava.main(args);
	}
	
	@Test
	void testImpresionTodosPrintln() {
		String[] args = { "src/test/resources/gci/impresionTodosPrintln.txt" };
		CompiladorMiniJava.main(args);
	}
	
	@Test
	void testImpresionIfConYSinElse() {
		String[] args = { "src/test/resources/gci/impresionIfConYSinElse.txt" };
		CompiladorMiniJava.main(args);
	}
	
	@Test
	void testImpresionWhile() {
		String[] args = { "src/test/resources/gci/impresionWhile.txt" };
		CompiladorMiniJava.main(args);
	}
	
	@Test
	void testImpresionConstructorLlamadaDirectaReturn() {
		String[] args = { "src/test/resources/gci/impresionConstructorLlamadaDirectaReturn.txt" };
		CompiladorMiniJava.main(args);
	}
	
	@Test
	void testImpresionAsignacionAtributoEnMetodoThisReturn() {
		String[] args = { "src/test/resources/gci/impresionAsignacionAtributoEnMetodoThisReturn.txt" };
		CompiladorMiniJava.main(args);
	}
	
	@Test
	void testImpresionAsignacionAtributo() {
		String[] args = { "src/test/resources/gci/impresionAsignacionAtributo.txt" };
		CompiladorMiniJava.main(args);
	}
	
	@Test
	void testImpresionAsignacionInlineAtributo() {
		String[] args = { "src/test/resources/gci/impresionAsignacionInlineAtributo.txt" };
		CompiladorMiniJava.main(args);
	}
	
	@Test
	void testImpresionAsignacionInlineAtributoDosConstructores() {
		String[] args = { "src/test/resources/gci/impresionAsignacionInlineAtributoDosConstructores.txt" };
		CompiladorMiniJava.main(args);
	}
	
	@Test
	void testChequeoCreacionVTsHerencia() {
		String[] args = { "src/test/resources/gci/chequeoCreacionVTsHerencia.txt" };
		CompiladorMiniJava.main(args);
	}
	
	@Test
	void testChequeoA() {
		String[] args = { "src/test/resources/gci/chequeoA.txt" };
		CompiladorMiniJava.main(args);
	}
	
	@Test
	void testChequeoB() {
		String[] args = { "src/test/resources/gci/chequeoB.txt" };
		CompiladorMiniJava.main(args);
	}
	
	@Test
	void testImpresionVarEncadenado() {
		String[] args = { "src/test/resources/gci/impresionVarEncadenado.txt" };
		CompiladorMiniJava.main(args);
	}
	
	@Test
	void testImpresion3ConstructoresDistintos() {
		String[] args = { "src/test/resources/gci/impresion3ConstructoresDistintos.txt" };
		CompiladorMiniJava.main(args);
	}

}
