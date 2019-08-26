package test.java;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import main.AnalizadorLexico;
import main.Token;

public class AnalizadorLexicoTokensNoPalabrasReservadasTest {

	@Test
	public void testIdMetVar() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestIdMetVar.txt");
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestIdMetVarBlancos.txt");
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestIdClase.txt");
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestEntero.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("entero", token.getNombre());
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestCaracter.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("caracter", token.getNombre());
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
					"src/test/resources/TestCaracterBarraInvertida.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("caracter", token.getNombre());
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestParentesisApertura.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("parentesisApertura", token.getNombre());
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestParentesisCierre.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("parentesisCierre", token.getNombre());
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestLlaveApertura.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("llaveApertura", token.getNombre());
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestLlaveCierre.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("llaveCierre", token.getNombre());
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestComa.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("coma", token.getNombre());
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestPunto.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("punto", token.getNombre());
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestPuntoComa.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("puntoComa", token.getNombre());
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestMayor.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("mayor", token.getNombre());
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestMayorIgual.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("mayorIgual", token.getNombre());
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestMenor.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("menor", token.getNombre());
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestMenorIgual.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("menorIgual", token.getNombre());
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestNegacion.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("negacion", token.getNombre());
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestDistinto.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("distinto", token.getNombre());
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestAsignacion.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("asignacion", token.getNombre());
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestComparacion.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("comparacion", token.getNombre());
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestAnd.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("and", token.getNombre());
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestOr.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("or", token.getNombre());
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestSuma.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("suma", token.getNombre());
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestResta.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("resta", token.getNombre());
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestProducto.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("producto", token.getNombre());
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestModulo.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("modulo", token.getNombre());
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestCociente.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("cociente", token.getNombre());
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestComentarioSimple.txt");
			Token token = analizadorLexico.getToken();
			assertNull(token);
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testComentarioMultilinea() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestComentarioMultilinea.txt");
			Token token = analizadorLexico.getToken();
			assertNull(token);
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testCadenaMultilinea() {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestCadena.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("cadena", token.getNombre());
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestCadena2.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("cadena", token.getNombre());
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestCadena3.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("cadena", token.getNombre());
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestCadena4.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("cadena", token.getNombre());
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
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestCadena5.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("cadena", token.getNombre());
			assertEquals("\"\r\n\"", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
		} catch (Exception e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}
	
}
