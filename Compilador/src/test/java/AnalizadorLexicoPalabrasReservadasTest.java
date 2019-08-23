package test.java;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.AnalizadorLexico;
import main.Token;

class AnalizadorLexicoPalabrasReservadasTest {

	@Test
	void testPalabraClaveBoolean() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestBoolean.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("boolean", token.getNombre());
		assertEquals("boolean", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}

	@Test
	void testPalabraClaveChar() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestChar.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("char", token.getNombre());
		assertEquals("char", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testPalabraClaveClass() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestClass.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("class", token.getNombre());
		assertEquals("class", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testPalabraClaveDynamic() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestDynamic.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("dynamic", token.getNombre());
		assertEquals("dynamic", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testPalabraClaveElse() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestElse.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("else", token.getNombre());
		assertEquals("else", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testPalabraClaveExtends() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestExtends.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("extends", token.getNombre());
		assertEquals("extends", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testPalabraClaveFalse() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestFalse.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("false", token.getNombre());
		assertEquals("false", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testPalabraClaveFinal() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestFinal.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("final", token.getNombre());
		assertEquals("final", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testPalabraClaveIf() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestIf.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("if", token.getNombre());
		assertEquals("if", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testPalabraClaveInt() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestInt.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("int", token.getNombre());
		assertEquals("int", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testPalabraClaveNew() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestNew.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("new", token.getNombre());
		assertEquals("new", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testPalabraClaveNull() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestNull.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("null", token.getNombre());
		assertEquals("null", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testPalabraClavePrivate() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestPrivate.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("private", token.getNombre());
		assertEquals("private", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testPalabraClaveProtected() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestProtected.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("protected", token.getNombre());
		assertEquals("protected", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testPalabraClavePublic() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestPublic.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("public", token.getNombre());
		assertEquals("public", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testPalabraClaveReturn() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestReturn.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("return", token.getNombre());
		assertEquals("return", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testPalabraClaveStatic() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestStatic.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("static", token.getNombre());
		assertEquals("static", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testPalabraClaveString() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestString.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("String", token.getNombre());
		assertEquals("String", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testPalabraClaveThis() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestThis.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("this", token.getNombre());
		assertEquals("this", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testPalabraClaveTrue() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestTrue.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("true", token.getNombre());
		assertEquals("true", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testPalabraClaveVoid() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestVoid.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("void", token.getNombre());
		assertEquals("void", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}
	
	@Test
	void testPalabraClaveWhile() throws Exception {
		AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestWhile.txt");
		Token token = analizadorLexico.getToken();
		assertEquals("while", token.getNombre());
		assertEquals("while", token.getLexema());
		assertEquals(1, token.getNroLinea());
		assertEquals(1, token.getNroColumna());
	}

}