package test.java;

import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import main.AnalizadorLexico;
import main.ExcepcionCaracterInvalido;
import main.ExcepcionFormatoCaracter;
import main.Principal;
import main.Token;

class AnalizadorLexicoTest {
	
	private PrintStream consola;
	
	@Before
	void antesTest( ) {
		consola = System.out;
	}
	
	@After
	void despuesTest( ) {
		System.setOut(consola);
	}
	
	@Test
	void testPalabraClaveBoolean() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestBoolean.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("boolean", token.getNombre());
		assertEquals("boolean", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}

	@Test
	void testPalabraClaveChar() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestChar.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("char", token.getNombre());
		assertEquals("char", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testPalabraClaveClass() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestClass.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("class", token.getNombre());
		assertEquals("class", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testPalabraClaveDynamic() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestDynamic.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("dynamic", token.getNombre());
		assertEquals("dynamic", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testPalabraClaveElse() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestElse.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("else", token.getNombre());
		assertEquals("else", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testPalabraClaveExtends() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestExtends.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("extends", token.getNombre());
		assertEquals("extends", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testPalabraClaveFalse() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestFalse.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("false", token.getNombre());
		assertEquals("false", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testPalabraClaveFinal() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestFinal.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("final", token.getNombre());
		assertEquals("final", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testPalabraClaveIf() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestIf.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("if", token.getNombre());
		assertEquals("if", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testPalabraClaveInt() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestInt.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("int", token.getNombre());
		assertEquals("int", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testPalabraClaveNew() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestNew.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("new", token.getNombre());
		assertEquals("new", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testPalabraClaveNull() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestNull.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("null", token.getNombre());
		assertEquals("null", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testPalabraClavePrivate() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestPrivate.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("private", token.getNombre());
		assertEquals("private", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testPalabraClaveProtected() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestProtected.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("protected", token.getNombre());
		assertEquals("protected", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testPalabraClavePublic() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestPublic.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("public", token.getNombre());
		assertEquals("public", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testPalabraClaveReturn() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestReturn.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("return", token.getNombre());
		assertEquals("return", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testPalabraClaveStatic() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestStatic.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("static", token.getNombre());
		assertEquals("static", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testPalabraClaveString() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestString.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("String", token.getNombre());
		assertEquals("String", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testPalabraClaveThis() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestThis.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("this", token.getNombre());
		assertEquals("this", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testPalabraClaveTrue() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestTrue.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("true", token.getNombre());
		assertEquals("true", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testPalabraClaveVoid() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestVoid.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("void", token.getNombre());
		assertEquals("void", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testPalabraClaveWhile() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestWhile.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("while", token.getNombre());
		assertEquals("while", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testIdMetVar() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestIdMetVar.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("idMetVar", token.getNombre());
		assertEquals("id78_MKf_", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testIdMetVarBlancos() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestIdMetVarBlancos.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("idMetVar", token.getNombre());
		assertEquals("id78_DespuesBlancos", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testIdClase() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestIdClase.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("idClase", token.getNombre());
		assertEquals("Principal_1", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testExcepcionCaracterInvalidoDespuesDeTokenValido1() throws Exception  {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestCaracterInvalido.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("idMetVar", token.getNombre());
		assertEquals("numero", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertThrows(ExcepcionCaracterInvalido.class, () ->  analizadorLexico.getToken());
	}
	
	@Test
	void testExcepcionCaracterInvalidoDespuesDeTokenValido2() throws Exception  {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestCaracterInvalido2.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("idClase", token.getNombre());
		assertEquals("Persona", token.getLexema());
		assertEquals(3, token.getNroLinea());
		assertThrows(ExcepcionCaracterInvalido.class, () ->  analizadorLexico.getToken());
	}
	
	@Test
	void testEntero() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestEntero.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("entero", token.getNombre());
		assertEquals("1984", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testCaracterSinBarraInvertida() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestCaracter.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("caracter", token.getNombre());
		assertEquals("\'¿'", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testCaracterConBarraInvertida() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestCaracterBarraInvertida.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("caracter", token.getNombre());
		assertEquals("\'\\@'", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testParentesisApertura() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestParentesisApertura.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("parentesisApertura", token.getNombre());
		assertEquals("(", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testParentesisCierre() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestParentesisCierre.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("parentesisCierre", token.getNombre());
		assertEquals(")", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testLlaveApertura() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestLlaveApertura.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("llaveApertura", token.getNombre());
		assertEquals("{", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testLlaveCierre() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestLlaveCierre.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("llaveCierre", token.getNombre());
		assertEquals("}", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testComa() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestComa.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("coma", token.getNombre());
		assertEquals(",", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testPunto() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestPunto.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("punto", token.getNombre());
		assertEquals(".", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testPuntoComa() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestPuntoComa.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("puntoComa", token.getNombre());
		assertEquals(";", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testMayor() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestMayor.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("mayor", token.getNombre());
		assertEquals(">", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testMayorIgual() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestMayorIgual.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("mayorIgual", token.getNombre());
		assertEquals(">=", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testMenor() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestMenor.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("menor", token.getNombre());
		assertEquals("<", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testMenorIgual() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestMenorIgual.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("menorIgual", token.getNombre());
		assertEquals("<=", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testNegacion() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestNegacion.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("negacion", token.getNombre());
		assertEquals("!", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testDistinto() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestDistinto.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("distinto", token.getNombre());
		assertEquals("!=", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testAsignacion() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestAsignacion.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("asignacion", token.getNombre());
		assertEquals("=", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testComparacion() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestComparacion.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("comparacion", token.getNombre());
		assertEquals("==", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testAnd() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestAnd.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("and", token.getNombre());
		assertEquals("&&", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testOr() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestOr.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("or", token.getNombre());
		assertEquals("||", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testSuma() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestSuma.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("suma", token.getNombre());
		assertEquals("+", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testResta() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestResta.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("resta", token.getNombre());
		assertEquals("-", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testProducto() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestProducto.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("producto", token.getNombre());
		assertEquals("*", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	@Test
	void testModulo() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestModulo.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("modulo", token.getNombre());
		assertEquals("%", token.getLexema());
		assertEquals(1, token.getNroLinea());
	}
	
	
	@Test
	void testCociente() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestCociente.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("cociente", token.getNombre());
		assertEquals("/", token.getLexema());
		assertEquals(1, token.getNroLinea());
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
	
	@Test
	void testFormatoCaracterInvalido1() throws FileNotFoundException  {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestFormatoCaracterInvalido.txt");
	    assertThrows(ExcepcionFormatoCaracter.class, () ->  analizadorLexico.getToken());
	}
	
	@Test
	void testFormatoCaracterInvalido2() throws FileNotFoundException  {
	    AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestFormatoCaracterInvalido2.txt");
	    assertThrows(ExcepcionFormatoCaracter.class, () ->  analizadorLexico.getToken());
	}

	@Test
	void testArchivoEntradaInexistente()  {
	    String[] args = {};
	    Principal.main(args);
	}
	
	@Test
	public void testSalidaConsola() throws Exception {
	    System.out.println("Test main consola");
	    String[] args = {"src/test/resources/TestSuma.txt"};
	    Principal.main(args);
	}
	
	@Test
	void testSalidaArchivo()  {
		String archivoSalida = "D:/out1.txt";
	    String[] args = {"src/test/resources/TestSuma.txt",archivoSalida};
	    System.out.println("salida "+archivoSalida);
	    Principal.main(args);
	}
	
	@Test
	void testArchivoSalidaConErrorLexico()  {
		String archivoSalida = "D:/out2.txt";
	    String[] args = {"src/test/resources/TestFormatoCaracterInvalido.txt",archivoSalida};
	    System.out.println("salida "+archivoSalida);
	    Principal.main(args);
	}
}