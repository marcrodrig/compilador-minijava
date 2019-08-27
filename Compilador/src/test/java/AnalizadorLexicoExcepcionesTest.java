package test.java;

import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import main.AnalizadorLexico;
import main.ExcepcionCaracterInvalido;
import main.ExcepcionFormatoAnd;
import main.ExcepcionFormatoCaracter;
import main.ExcepcionFormatoComentarioMultilinea;
import main.ExcepcionFormatoOr;
import main.ExcepcionFormatoString;
import main.Token;

public class AnalizadorLexicoExcepcionesTest {
	
	@Rule
    public TestCasePrinterRule pr = new TestCasePrinterRule(System.out);

	@Test
	public void testExcepcionCaracterInvalidoDespuesDeTokenValido1() throws ExcepcionCaracterInvalido {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestCaracterInvalido.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("idMetVar", token.getNombre());
			assertEquals("numero", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
			Exception e = assertThrows(ExcepcionCaracterInvalido.class, () -> analizadorLexico.getToken());
			assertEquals("ERROR LEXICO: Linea 1: caracter no válido.", e.toString());
		} catch (IOException | ExcepcionFormatoCaracter | ExcepcionFormatoAnd | ExcepcionFormatoOr | ExcepcionFormatoComentarioMultilinea | ExcepcionFormatoString e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testExcepcionCaracterInvalidoDespuesDeTokenValido2() throws ExcepcionCaracterInvalido {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestCaracterInvalido2.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("idClase", token.getNombre());
			assertEquals("Persona", token.getLexema());
			assertEquals(3, token.getNroLinea());
			assertEquals(5, token.getNroColumna());
			Exception e = assertThrows(ExcepcionCaracterInvalido.class, () -> analizadorLexico.getToken());
			assertEquals("ERROR LEXICO: Linea 3: caracter no válido.", e.toString());
		} catch (IOException | ExcepcionFormatoCaracter | ExcepcionFormatoAnd | ExcepcionFormatoOr	| ExcepcionFormatoComentarioMultilinea | ExcepcionFormatoString e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testExcepcionFormatoCaracter1() throws ExcepcionFormatoCaracter {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico(
					"src/test/resources/TestFormatoCaracterInvalido.txt");
			Exception e = assertThrows(ExcepcionFormatoCaracter.class, () -> analizadorLexico.getToken());
			assertEquals("ERROR LEXICO: Linea 1: formato caracter inválido.", e.toString());
		} catch (FileNotFoundException e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testExcepcionFormatoCaracter2() throws ExcepcionFormatoCaracter {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico(
					"src/test/resources/TestFormatoCaracterInvalido2.txt");
			Exception e = assertThrows(ExcepcionFormatoCaracter.class, () -> analizadorLexico.getToken());
			assertEquals("ERROR LEXICO: Linea 1: formato caracter inválido.", e.toString());
		} catch (FileNotFoundException e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testAndInvalido() throws ExcepcionFormatoAnd {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestAndInvalido.txt");
			Exception e = assertThrows(ExcepcionFormatoAnd.class, () -> analizadorLexico.getToken());
			assertEquals("ERROR LEXICO: Línea 5: error en formato &&", e.toString());
		} catch (FileNotFoundException e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testOrInvalido() throws ExcepcionFormatoOr {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestOrInvalido.txt");
			Exception e = assertThrows(ExcepcionFormatoOr.class, () -> analizadorLexico.getToken());
			assertEquals("ERROR LEXICO: Línea 2: error en formato ||", e.toString());
		} catch (FileNotFoundException e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testComentarioMultilineaInvalido() throws ExcepcionFormatoComentarioMultilinea {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico(
					"src/test/resources/TestComentarioMultilineaInvalido.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("public", token.getNombre());
			assertEquals("public", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
			token = analizadorLexico.getToken();
			assertEquals("class", token.getNombre());
			assertEquals("class", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(8, token.getNroColumna());
			token = analizadorLexico.getToken();
			assertEquals("idClase", token.getNombre());
			assertEquals("Clase", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(14, token.getNroColumna());
			token = analizadorLexico.getToken();
			assertEquals("llaveApertura", token.getNombre());
			assertEquals("{", token.getLexema());
			assertEquals(2, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
			Exception e = assertThrows(ExcepcionFormatoComentarioMultilinea.class, () -> analizadorLexico.getToken());
			assertEquals("ERROR LEXICO: Linea 7: comentario multinea sin cerrar.", e.toString());
		} catch (IOException | ExcepcionCaracterInvalido | ExcepcionFormatoCaracter | ExcepcionFormatoAnd | ExcepcionFormatoOr | ExcepcionFormatoString e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	public void testStringMultilineaInvalido() throws ExcepcionFormatoString {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico(
					"src/test/resources/TestStringMultilineaInvalido.txt");
			Exception e = assertThrows(ExcepcionFormatoString.class, () -> analizadorLexico.getToken());
			assertEquals("ERROR LEXICO: Linea 1: error en formato de String, String sin cerrar o con un salto de línea.", e.toString());
		} catch (FileNotFoundException e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}
	
}