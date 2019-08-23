package test.java;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.AnalizadorLexico;
import main.Token;

class AnalizadorLexicoTokensNoPalabrasReservadasTest {

	@Test
	void testIdMetVar() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestIdMetVar.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("idMetVar", token.getNombre());
		assertEquals("id78_MKf_", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testIdMetVarBlancos() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestIdMetVarBlancos.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("idMetVar", token.getNombre());
		assertEquals("id78_DespuesBlancos", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(7, token.getNroColumna());
	}
	
	@Test
	void testIdClase() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestIdClase.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("idClase", token.getNombre());
		assertEquals("Principal_1", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}

	@Test
	void testEntero() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestEntero.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("entero", token.getNombre());
		assertEquals("1984", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testCaracterSinBarraInvertida() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestCaracter.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("caracter", token.getNombre());
		assertEquals("\'¿'", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testCaracterConBarraInvertida() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestCaracterBarraInvertida.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("caracter", token.getNombre());
		assertEquals("\'\\@'", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testParentesisApertura() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestParentesisApertura.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("parentesisApertura", token.getNombre());
		assertEquals("(", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testParentesisCierre() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestParentesisCierre.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("parentesisCierre", token.getNombre());
		assertEquals(")", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testLlaveApertura() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestLlaveApertura.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("llaveApertura", token.getNombre());
		assertEquals("{", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testLlaveCierre() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestLlaveCierre.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("llaveCierre", token.getNombre());
		assertEquals("}", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testComa() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestComa.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("coma", token.getNombre());
		assertEquals(",", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testPunto() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestPunto.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("punto", token.getNombre());
		assertEquals(".", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testPuntoComa() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestPuntoComa.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("puntoComa", token.getNombre());
		assertEquals(";", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testMayor() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestMayor.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("mayor", token.getNombre());
		assertEquals(">", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testMayorIgual() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestMayorIgual.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("mayorIgual", token.getNombre());
		assertEquals(">=", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testMenor() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestMenor.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("menor", token.getNombre());
		assertEquals("<", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testMenorIgual() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestMenorIgual.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("menorIgual", token.getNombre());
		assertEquals("<=", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testNegacion() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestNegacion.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("negacion", token.getNombre());
		assertEquals("!", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testDistinto() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestDistinto.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("distinto", token.getNombre());
		assertEquals("!=", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testAsignacion() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestAsignacion.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("asignacion", token.getNombre());
		assertEquals("=", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testComparacion() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestComparacion.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("comparacion", token.getNombre());
		assertEquals("==", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testAnd() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestAnd.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("and", token.getNombre());
		assertEquals("&&", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testOr() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestOr.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("or", token.getNombre());
		assertEquals("||", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testSuma() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestSuma.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("suma", token.getNombre());
		assertEquals("+", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testResta() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestResta.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("resta", token.getNombre());
		assertEquals("-", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testProducto() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestProducto.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("producto", token.getNombre());
		assertEquals("*", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testModulo() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestModulo.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("modulo", token.getNombre());
		assertEquals("%", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	
	@Test
	void testCociente() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestCociente.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("cociente", token.getNombre());
		assertEquals("/", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testComentarioSimple() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestComentarioSimple.txt");
		Token token = analizadorLexico.getToken();
		assertNull(token);
	}
	
	@Test
	void testComentarioMultilinea() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestComentarioMultilinea.txt");
		Token token = analizadorLexico.getToken();
		assertNull(token);
	}
	
}
