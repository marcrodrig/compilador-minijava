package test.java;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Rule;
import org.junit.Test;
import main.AnalizadorLexico;
import main.Token;

public class AnalizadorLexicoTokensNoPalabrasReservadasTest {

	@Rule
    public TestCasePrinterRule pr = new TestCasePrinterRule(System.out);
	
	@Test
	public void testIdMetVar() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestIdMetVar.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("idMetVar", token.getNombre());
			assertEquals("id78_MKf_", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testIdMetVarBlancos() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestIdMetVarBlancos.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("idMetVar", token.getNombre());
			assertEquals("id78_DespuesBlancos", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(7, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testIdClase() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestIdClase.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("idClase", token.getNombre());
			assertEquals("Principal_1", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testEntero() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestEntero.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("intLiteral", token.getNombre());
			assertEquals("1984", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testCaracterSinBarraInvertida() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestCaracter.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("charLiteral", token.getNombre());
			assertEquals("\'¿'", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testCaracterConBarraInvertida() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico(
					"src/test/resources/lexico/TestCaracterBarraInvertida.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("charLiteral", token.getNombre());
			assertEquals("\'\\@'", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testParentesisApertura() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestParentesisApertura.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("(", token.getNombre());
			assertEquals("(", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testParentesisCierre() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestParentesisCierre.txt");
			Token token = analizadorLexico.getToken();
			assertEquals(")", token.getNombre());
			assertEquals(")", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testLlaveApertura() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestLlaveApertura.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("{", token.getNombre());
			assertEquals("{", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testLlaveCierre() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestLlaveCierre.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("}", token.getNombre());
			assertEquals("}", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testComa() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestComa.txt");
			Token token = analizadorLexico.getToken();
			assertEquals(",", token.getNombre());
			assertEquals(",", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testPunto() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestPunto.txt");
			Token token = analizadorLexico.getToken();
			assertEquals(".", token.getNombre());
			assertEquals(".", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testPuntoComa() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestPuntoComa.txt");
			Token token = analizadorLexico.getToken();
			assertEquals(";", token.getNombre());
			assertEquals(";", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testMayor() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestMayor.txt");
			Token token = analizadorLexico.getToken();
			assertEquals(">", token.getNombre());
			assertEquals(">", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testMayorIgual() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestMayorIgual.txt");
			Token token = analizadorLexico.getToken();
			assertEquals(">=", token.getNombre());
			assertEquals(">=", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testMenor() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestMenor.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("<", token.getNombre());
			assertEquals("<", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testMenorIgual() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestMenorIgual.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("<=", token.getNombre());
			assertEquals("<=", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testNegacion() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestNegacion.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("!", token.getNombre());
			assertEquals("!", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testDistinto() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestDistinto.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("!=", token.getNombre());
			assertEquals("!=", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testAsignacion() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestAsignacion.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("=", token.getNombre());
			assertEquals("=", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testComparacion() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestComparacion.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("==", token.getNombre());
			assertEquals("==", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testAnd() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestAnd.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("&&", token.getNombre());
			assertEquals("&&", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testOr() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestOr.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("||", token.getNombre());
			assertEquals("||", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testSuma() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestSuma.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("+", token.getNombre());
			assertEquals("+", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testResta() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestResta.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("-", token.getNombre());
			assertEquals("-", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testProducto() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestProducto.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("*", token.getNombre());
			assertEquals("*", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testModulo() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestModulo.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("%", token.getNombre());
			assertEquals("%", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testCociente() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestCociente.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("/", token.getNombre());
			assertEquals("/", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testComentarioSimple() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestComentarioSimple.txt");
			Token token = analizadorLexico.getToken();
			assertNull(token);
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testComentarioMultilinea() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestComentarioMultilinea.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("EOF",token.getNombre());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testCadenaMultilinea() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestCadena.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("stringLiteral", token.getNombre());
			assertEquals("\"cadena \r\n multilinea?\"", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testCadenaSimple() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestCadena2.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("stringLiteral", token.getNombre());
			assertEquals("\"cadena simple\"", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}
	
	@Test
	public void testCadenaConBarraInvertidaYCaracterDistintoDeN() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestCadena3.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("stringLiteral", token.getNombre());
			assertEquals("\"\\t\"", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}
	
	@Test
	public void testCadenaVaciaValida() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestCadena4.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("stringLiteral", token.getNombre());
			assertEquals("\"\"", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}
	
	@Test
	public void testCadenaSoloBarraInvetidaN() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestCadena5.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("stringLiteral", token.getNombre());
			assertEquals("\"\r\n\"", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}
	
}
