package test.java;

import main.AnalizadorSintactico;
import main.ExcepcionLexico;
import main.ExcepcionPanicMode;
import main.ExcepcionSemantico;
import main.ExcepcionSintactico;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.Test;

class AnalizadorSintacticoTest {

	@Test
	void testExcepcionEsperadaArchivoVacio() {
		String[] args = { "src/test/resources/sintactico/excepcionEsperadaArchivoVacio.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> {
				analizadorSintactico.start();
			});
			assertEquals("[1:1] Error sintáctico.\nEsperado: class\nEncontrado: EOF", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaSinClassAlPrincipio() {
		String[] args = { "src/test/resources/sintactico/excepcionEsperadaSinClassAlPrincipio.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> {
				analizadorSintactico.start();
			});
			assertEquals("[1:1] Error sintáctico.\nEsperado: class\nEncontrado: idClase", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClassSeguidoDeLlaves() {
		String[] args = { "src/test/resources/sintactico/excepcionEsperadaClassSeguidoDeLlaves.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> {
				analizadorSintactico.start();
			});
			assertEquals("[1:7] Error sintáctico.\nEsperado: idClase\nEncontrado: {", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseTokenSumaDespuesDeIdClase() {
		String[] args = { "src/test/resources/sintactico/excepcionEsperadaClaseTokenSumaDespuesDeIdClase.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> {
				analizadorSintactico.start();
			});
			assertEquals(
					"[1:13] Error sintáctico en la declaración de una clase.\nEsperado: extends o {\nEncontrado: +",
					e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseSinLlaveCierre() {
		String[] args = { "src/test/resources/sintactico/excepcionEsperadaClaseSinLlaveCierre.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> {
				analizadorSintactico.start();
			});
			assertEquals(
					"[1:15] Error sintáctico. Se espera la declaración de un atributo, constructor, método o }.\nEsperado: public, protected, private, idClase, static, dynamic o }\nEncontrado: EOF",
					e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaExtendsSeguidoDeLlave() {
		String[] args = { "src/test/resources/sintactico/excepcionEsperadaClaseExtendsSeguidoDeLlave.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> {
				analizadorSintactico.start();
			});
			assertEquals(
					"[1:22] Error sintáctico en la declaración de herencia de una clase.\nEsperado: idClase\nEncontrado: {",
					e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaSegundaClaseSinClassAlPrincipio() {
		String[] args = { "src/test/resources/sintactico/excepcionEsperadaSegundaClaseSinClassAlPrincipio.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> {
				analizadorSintactico.start();
			});
			assertEquals("[3:1] Error sintáctico en la declaración de una clase.\nEsperado: class\nEncontrado: idClase",
					e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConMetodoSoloFormaMetodo() {
		String[] args = { "src/test/resources/sintactico/excepcionEsperadaClaseConMetodoSoloFormaMetodo.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> {
				analizadorSintactico.start();
			});
			assertEquals("[3:1] Error sintáctico en método.\n" + "Esperado: final o tipo de método\n" + "Encontrado: }",
					e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConMetodoSoloFormaMetodoYFinal() {
		String[] args = { "src/test/resources/sintactico/excepcionEsperadaClaseConMetodoSoloFormaMetodoYFinal.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> {
				analizadorSintactico.start();
			});
			assertEquals("[3:1] Error sintáctico en tipo método.\n"
					+ "Esperado: idClase, boolean, char, int, String o void\n" + "Encontrado: }", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConMetodoSoloFormaMetodoYTipo() {
		String[] args = { "src/test/resources/sintactico/excepcionEsperadaClaseConMetodoSoloFormaMetodoYTipo.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> {
				analizadorSintactico.start();
			});
			assertEquals("[3:1] Error sintáctico.\n" + "Esperado: idMetVar\n" + "Encontrado: }", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConMetodoSoloFormaMetodoTipoIdMetVar() {
		String[] args = {
				"src/test/resources/sintactico/excepcionEsperadaClaseConMetodoSoloFormaMetodoTipoIdMetVar.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> {
				analizadorSintactico.start();
			});
			assertEquals("[3:1] Error sintáctico en la declaración de argumentos formales.\n" + "Esperado: (\n"
					+ "Encontrado: }", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConMetodoSoloFormaMetodoTipoIdMetVarParentesisApertura() {
		String[] args = {
				"src/test/resources/sintactico/excepcionEsperadaClaseConMetodoSoloFormaMetodoTipoIdMetVarParentesisApertura.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> {
				analizadorSintactico.start();
			});
			assertEquals("[3:1] Error sintáctico en argumentos formales.\n" + "Esperado: ) o tipo\n" + "Encontrado: }",
					e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConMetodoConArgsFormalesSoloTipo() {
		String[] args = { "src/test/resources/sintactico/excepcionEsperadaClaseConMetodoConArgsFormalesSoloTipo.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> {
				analizadorSintactico.start();
			});
			assertEquals("[3:1] Error sintáctico.\n" + "Esperado: idMetVar\n" + "Encontrado: }", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConMetodoConArgsFormalesSinParentesisCierre() {
		String[] args = {
				"src/test/resources/sintactico/excepcionEsperadaClaseConMetodoConArgsFormalesSinParentesisCierre.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> {
				analizadorSintactico.start();
			});
			assertEquals("[3:1] Error sintáctico en los argumentos formales.\n" + "Esperado: ) o ,\n" + "Encontrado: }",
					e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConMetodoSinBloque() {
		String[] args = { "src/test/resources/sintactico/excepcionEsperadaClaseConMetodoSinBloque.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> {
				analizadorSintactico.start();
			});
			assertEquals("[3:1] Error sintáctico.\n" + "Esperado: {\n" + "Encontrado: }", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConMetodoConLlaveApertura() {
		String[] args = { "src/test/resources/sintactico/excepcionEsperadaClaseConMetodoConLlaveApertura.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> {
				analizadorSintactico.start();
			});
			assertEquals("[2:28] Error sintáctico. Se espera la declaración de una sentencia o }.\n"
					+ "Esperado: idMetVar, (, boolean, char, int, String, idClase, if, while, {, return, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this, } o ;\n"
					+ "Encontrado: EOF", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConMetodoConUnaSolaSentenciaSinLlaveCierre() {
		String[] args = {
				"src/test/resources/sintactico/excepcionEsperadaClaseConMetodoConUnaSolaSentenciaSinLlaveCierre.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> {
				analizadorSintactico.start();
			});
			assertEquals("[3:10] Error sintáctico. Se espera la declaración de una sentencia o }.\n"
					+ "Esperado: idMetVar, (, boolean, char, int, String, idClase, if, while, {, return, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this, } o ;\n"
					+ "Encontrado: EOF", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConCtorSoloIdClase() {
		String[] args = { "src/test/resources/sintactico/excepcionEsperadaClaseConCtorSoloIdClase.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> {
				analizadorSintactico.start();
			});
			assertEquals("[3:1] Error sintáctico en la declaración de argumentos formales.\n" + "Esperado: (\n"
					+ "Encontrado: }", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConCtorSoloIdClaseParentesisApertura() {
		String[] args = {
				"src/test/resources/sintactico/excepcionEsperadaClaseConCtorSoloIdClaseParentesisApertura.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> {
				analizadorSintactico.start();
			});
			assertEquals("[3:1] Error sintáctico en argumentos formales.\n" + "Esperado: ) o tipo\n" + "Encontrado: }",
					e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConCtorSinBloque() {
		String[] args = { "src/test/resources/sintactico/excepcionEsperadaClaseConCtorSinBloque.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> {
				analizadorSintactico.start();
			});
			assertEquals("[3:1] Error sintáctico.\n" + "Esperado: {\n" + "Encontrado: }", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConCtorConBloqueSinSentencias() {
		String[] args = { "src/test/resources/sintactico/excepcionEsperadaClaseConCtorConBloqueSinSentencias.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> {
				analizadorSintactico.start();
			});
			assertEquals("[2:19] Error sintáctico. Se espera la declaración de una sentencia o }.\n"
					+ "Esperado: idMetVar, (, boolean, char, int, String, idClase, if, while, {, return, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this, } o ;\n"
					+ "Encontrado: EOF", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConCtorConUnaSolaSentenciaSinLlaveCierre() {
		String[] args = {
				"src/test/resources/sintactico/excepcionEsperadaClaseConCtorConUnaSolaSentenciaSinLlaveCierre.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> {
				analizadorSintactico.start();
			});
			assertEquals("[3:10] Error sintáctico. Se espera la declaración de una sentencia o }.\n"
					+ "Esperado: idMetVar, (, boolean, char, int, String, idClase, if, while, {, return, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this, } o ;\n"
					+ "Encontrado: EOF", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaSentenciasSinPuntoComasRecuperacionModoPanicoYErrorSintactico() {
		String[] args = {
				"src/test/resources/sintactico/excepcionEsperadaSentenciasSinPuntoComasRecuperacionModoPanicoYErrorSintactico.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> {
				analizadorSintactico.start();
			});
			assertEquals("[16:9] Error sintáctico. Se espera la declaración de una sentencia o }.\n"
					+ "Esperado: idMetVar, (, boolean, char, int, String, idClase, if, while, {, return, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this, } o ;\n"
					+ "Encontrado: *", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaSegundoMiembroIncorrecto() {
		String[] args = { "src/test/resources/sintactico/excepcionEsperadaSegundoMiembroIncorrecto.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> {
				analizadorSintactico.start();
			});
			assertEquals(
					"[3:5] Error sintáctico. Se espera la declaración de un atributo, constructor, método o }.\nEsperado: public, protected, private, idClase, static, dynamic o }\nEncontrado: +",
					e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testUnaClaseVacía() throws ExcepcionSemantico {
		String[] args = { "src/test/resources/sintactico/unaClaseVacia.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.start();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico | ExcepcionPanicMode e) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testVariasClasesVacias() throws ExcepcionSemantico {
		String[] args = { "src/test/resources/sintactico/variasClasesVacias.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.start();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico | ExcepcionPanicMode e) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testNombreAtributoIncorrectoRecuperacionModoPanico() throws ExcepcionSemantico {
		String[] args = { "src/test/resources/sintactico/nombreAtributoIncorrectoRecuperacionModoPanico.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.start();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico | ExcepcionPanicMode e) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testDeclaracionListaAtributosSinAsignacion() throws ExcepcionSemantico {
		String[] args = { "src/test/resources/sintactico/declaracionListaAtributosSinAsignacion.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.start();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico | ExcepcionPanicMode e) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testDeclaracionListaAtributosConAsignacion() throws ExcepcionSemantico {
		String[] args = { "src/test/resources/sintactico/declaracionListaAtributosConAsignacion.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.start();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico | ExcepcionPanicMode e) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testClaseConExtendsVaciaValido() throws ExcepcionSemantico {
		String[] args = { "src/test/resources/sintactico/claseConExtendsVaciaValido.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.start();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico | ExcepcionPanicMode e) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testClaseConDeclaracionAtributoSoloVisibilidad() throws ExcepcionSemantico {
		String[] args = { "src/test/resources/sintactico/claseConDeclaracionAtributoSoloVisibilidad.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.start();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico | ExcepcionPanicMode e) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testClaseConDeclaracionAtributoSoloVisibilidadYTipo() throws ExcepcionSemantico {
		String[] args = { "src/test/resources/sintactico/claseConDeclaracionAtributoSoloVisibilidadYTipo.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.start();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico | ExcepcionPanicMode e) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testClaseConDeclaracionAtributoConPuntoComaFaltanteRecuperacionModoPanico() throws ExcepcionSemantico {
		String[] args = {
				"src/test/resources/sintactico/claseConDeclaracionAtributoConPuntoComaFaltanteRecuperacionModoPanico.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.start();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico | ExcepcionPanicMode e) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testClaseConAsignacionAtributoSinExpresion() throws ExcepcionSemantico {
		String[] args = { "src/test/resources/sintactico/claseConAsignacionAtributoSinExpresion.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.start();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico | ExcepcionPanicMode e) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testClaseConAsignacionAtributoConPuntoComaFaltante() throws ExcepcionSemantico {
		String[] args = { "src/test/resources/sintactico/claseConAsignacionAtributoConPuntoComaFaltante.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.start();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico | ExcepcionPanicMode e) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testMetodoConListaExpresionesIncorrectoAlPrincipioRecuperacionModoPanico() throws ExcepcionSemantico {
		String[] args = {
				"src/test/resources/sintactico/metodoConListaExpresionesIncorrectoAlPrincipioRecuperacionModoPanico.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.start();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico | ExcepcionPanicMode e) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testMetodoConListaExpresionesIncorrectoAlFinalRecuperacionModoPanico() throws ExcepcionSemantico {
		String[] args = {
				"src/test/resources/sintactico/metodoConListaExpresionesIncorrectoAlFinalRecuperacionModoPanico.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.start();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico | ExcepcionPanicMode e) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testSentenciasSinPuntoComaRecuperacionModoPanico() throws ExcepcionSemantico {
		String[] args = { "src/test/resources/sintactico/sentenciasSinPuntoComaRecuperacionModoPanico.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.start();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico | ExcepcionPanicMode e) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testSentenciasSimpleParentizadasDesparentizadasIdMetVarAlPrincipio() throws ExcepcionSemantico {
		String[] args = {
				"src/test/resources/sintactico/sentenciasSimpleParentizadasDesparentizadasIdMetVarAlPrincipio.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.start();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico | ExcepcionPanicMode e) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testSentenciasSimpleParentizadasDesparentizadasIdClaseAlPrincipio() throws ExcepcionSemantico {
		String[] args = {
				"src/test/resources/sintactico/sentenciasSimpleParentizadasDesparentizadasIdClaseAlPrincipio.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.start();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico | ExcepcionPanicMode e) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testSentenciaIncorrectaEnIfRecuperacionModoPanico() throws ExcepcionSemantico {
		String[] args = { "src/test/resources/sintactico/sentenciaIncorrectaEnIfRecuperacionModoPanico.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.start();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico | ExcepcionPanicMode e) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testIfThenElseConPuntoComaFaltantesEnThenRecuperacionModoPanico() throws ExcepcionSemantico {
		String[] args = {
				"src/test/resources/sintactico/ifThenElseConPuntoComaFaltantesEnThenRecuperacionModoPanico.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.start();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico | ExcepcionPanicMode e) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testReturnTodasValidasConYSinPuntoComaRecuperacionModoPanico() throws ExcepcionSemantico {
		String[] args = {
				"src/test/resources/sintactico/returnTodasValidasConYSinPuntoComaRecuperacionModoPanico.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.start();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico | ExcepcionPanicMode e) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testArgsActualesIncorrectoEnLlamadaConstructorRecuperacionModoPanico() throws ExcepcionSemantico {
		String[] args = {
				"src/test/resources/sintactico/argsActualesIncorrectoEnLlamadaConstructorRecuperacionModoPanico.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.start();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico | ExcepcionPanicMode e) {
			fail("No debería suceder esto");
		}
	}
	/*
	 * @Test public void testRapido() { String[] args =
	 * {"src/test/resources/sintactico/rapido.txt"}; Principal.main(args); }
	 */
}
