package test.java;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import main.AnalizadorLexico;
import main.ExcepcionCaracterInvalido;
import main.ExcepcionFormatoAnd;
import main.ExcepcionFormatoCaracter;
import main.ExcepcionFormatoComentarioMultilinea;
import main.ExcepcionFormatoOr;
import main.Token;

class AnalizadorLexicoExcepcionesTest {

	@Test
	void testExcepcionCaracterInvalidoDespuesDeTokenValido1() throws ExcepcionCaracterInvalido {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestCaracterInvalido.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("idMetVar", token.getNombre());
			assertEquals("numero", token.getLexema());
			assertEquals(1, token.getNroLinea());
			assertEquals(1, token.getNroColumna());
			assertThrows(ExcepcionCaracterInvalido.class, () -> analizadorLexico.getToken());
		} catch (IOException | ExcepcionFormatoCaracter | ExcepcionFormatoAnd | ExcepcionFormatoOr
				| ExcepcionFormatoComentarioMultilinea e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	void testExcepcionCaracterInvalidoDespuesDeTokenValido2() throws ExcepcionCaracterInvalido {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestCaracterInvalido2.txt");
			Token token = analizadorLexico.getToken();
			assertEquals("idClase", token.getNombre());
			assertEquals("Persona", token.getLexema());
			assertEquals(3, token.getNroLinea());
			assertEquals(5, token.getNroColumna());
			assertThrows(ExcepcionCaracterInvalido.class, () -> analizadorLexico.getToken());
		} catch (IOException | ExcepcionFormatoCaracter | ExcepcionFormatoAnd | ExcepcionFormatoOr
				| ExcepcionFormatoComentarioMultilinea e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	void testExcepcionFormatoCaracter1() throws ExcepcionFormatoCaracter {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico(
					"src/test/resources/TestFormatoCaracterInvalido.txt");
			assertThrows(ExcepcionFormatoCaracter.class, () -> analizadorLexico.getToken());
		} catch (FileNotFoundException e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	void testExcepcionFormatoCaracter2() throws ExcepcionFormatoCaracter {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico(
					"src/test/resources/TestFormatoCaracterInvalido2.txt");
			assertThrows(ExcepcionFormatoCaracter.class, () -> analizadorLexico.getToken());
		} catch (FileNotFoundException e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	void testAndInvalido() throws ExcepcionFormatoAnd {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestAndInvalido.txt");
			assertThrows(ExcepcionFormatoAnd.class, () -> analizadorLexico.getToken());
		} catch (FileNotFoundException e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	void testOrInvalido() throws ExcepcionFormatoOr {
		try {
			AnalizadorLexico analizadorLexico = new AnalizadorLexico("src/test/resources/TestOrInvalido.txt");
			assertThrows(ExcepcionFormatoOr.class, () -> analizadorLexico.getToken());
		} catch (FileNotFoundException e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

	@Test
	void testComentarioMultilineaInvalido() throws ExcepcionFormatoComentarioMultilinea {
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
			assertThrows(ExcepcionFormatoComentarioMultilinea.class, () -> analizadorLexico.getToken());
		} catch (IOException | ExcepcionCaracterInvalido | ExcepcionFormatoCaracter | ExcepcionFormatoAnd
				| ExcepcionFormatoOr e) {
			fail("No debería lanzar esta excepción: " + e.getClass().getSimpleName());
		}
	}

}
