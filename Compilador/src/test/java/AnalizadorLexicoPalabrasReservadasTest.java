package test.java;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Rule;
import org.junit.Test;
import main.AnalizadorLexico;
import main.Token;

public class AnalizadorLexicoPalabrasReservadasTest {

	@Rule
    public TestCasePrinterRule pr = new TestCasePrinterRule(System.out);
	
	@Test
	public void testPalabraClaveBoolean() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestBoolean.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("boolean", token.getNombre());
		assertEquals("boolean", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}

	@Test
	public void testPalabraClaveChar() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestChar.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("char", token.getNombre());
		assertEquals("char", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	public void testPalabraClaveClass() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestClass.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("class", token.getNombre());
		assertEquals("class", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	public void testPalabraClaveDynamic() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestDynamic.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("dynamic", token.getNombre());
		assertEquals("dynamic", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	public void testPalabraClaveElse() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestElse.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("else", token.getNombre());
		assertEquals("else", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	public void testPalabraClaveExtends() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestExtends.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("extends", token.getNombre());
		assertEquals("extends", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	public void testPalabraClaveFalse() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestFalse.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("false", token.getNombre());
		assertEquals("false", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	public void testPalabraClaveFinal() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestFinal.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("final", token.getNombre());
		assertEquals("final", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	public void testPalabraClaveIf() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestIf.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("if", token.getNombre());
		assertEquals("if", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	public void testPalabraClaveInt() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestInt.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("int", token.getNombre());
		assertEquals("int", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	public void testPalabraClaveNew() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestNew.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("new", token.getNombre());
		assertEquals("new", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	public void testPalabraClaveNull() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestNull.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("null", token.getNombre());
		assertEquals("null", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	public void testPalabraClavePrivate() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestPrivate.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("private", token.getNombre());
		assertEquals("private", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	public void testPalabraClaveProtected() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestProtected.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("protected", token.getNombre());
		assertEquals("protected", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	public void testPalabraClavePublic() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestPublic.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("public", token.getNombre());
		assertEquals("public", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	public void testPalabraClaveReturn() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestReturn.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("return", token.getNombre());
		assertEquals("return", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	public void testPalabraClaveStatic() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestStatic.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("static", token.getNombre());
		assertEquals("static", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	public void testPalabraClaveString() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestString.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("String", token.getNombre());
		assertEquals("String", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	public void testPalabraClaveThis() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestThis.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("this", token.getNombre());
		assertEquals("this", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	public void testPalabraClaveTrue() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestTrue.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("true", token.getNombre());
		assertEquals("true", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	public void testPalabraClaveVoid() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestVoid.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("void", token.getNombre());
		assertEquals("void", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	public void testPalabraClaveWhile() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/lexico/TestWhile.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("while", token.getNombre());
		assertEquals("while", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}

}