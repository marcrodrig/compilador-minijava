package test.java;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import main.Principal;
import semantico.Clase;
import semantico.Constructor;
import semantico.Metodo;
import semantico.Parametro;
import semantico.TablaSimbolos;
import semantico.Unidad;
import semantico.VariableInstancia;

class AnalizadorSemanticoTest {

	@BeforeEach
	void init(TestInfo testInfo) {
		String displayName = testInfo.getDisplayName();
		System.out.println(displayName);
	}

	@AfterEach
	void resetTS() {
		System.out.println();
		Principal.ts.reset();
	}

/*	@Test
	void testRapido() {
		String[] args = { "src/test/resources/semantico/rapido.txt" };
		Principal.main(args);
		/*
		 * TablaSimbolos ts = TablaSimbolos.getInstance();
		 * assertEquals(5,Principal.ts.getClases().size()); Clase a =
		 * Principal.ts.getClase("A"); Clase b = Principal.ts.getClase("B");
		 * assertEquals(1,a.cantidadAtributos()); VariableInstancia va =
		 * a.getAtributoPorNombre("a1"); assertEquals("int", va.getTipo().getNombre());
		 * assertEquals(1,b.cantidadAtributos()); VariableInstancia vb =
		 * b.getAtributoPorNombre("a1"); assertEquals("String",
		 * vb.getTipo().getNombre());
		// 
	}*/

	@Test
	@DisplayName("TEST: Excepción esperada método main ausente")
	void testExcepcionEsperadaMetodoMainAusente() {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaMetodoMainAusente.txt" };
		/*AnalizadorSintactico analizadorSintactico;
		TablaSimbolos ts = TablaSimbolos.getInstance();
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.start();
			ExcepcionSemantico e = assertThrows(ExcepcionSemantico.class, () -> {
				ts.controlesSemanticos();
			});
			assertEquals("Error semántico: Método main sin definir.", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico | ExcepcionPanicMode e1) {
			fail("No debería suceder esto");
		}
		ts.reset();*/
		Principal.main(args);
	}

	@Test
	@DisplayName("TEST: Chequeo definición método main, un main con parámetros")
	void testExcepcionEsperadaMetodoMainConParametros() {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaMetodoMainConParametros.txt" };
		Principal.main(args);
	}

	@Test
	@DisplayName("TEST: Recuperación semántica dos métodos main en clases distintas sin relación de herencia")
	void testExcepcionEsperadaDosMetodosMainRecuperacionSemantica() {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaDosMetodosMain.txt" };
		Principal.main(args);
	}

	@Test
	@DisplayName("TEST: Recuperación semántica declaración clase Object")
	void testExcepcionEsperadaDeclaracionClaseObjectRecuperacionSemantica() {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaDeclaracionClaseObject.txt" };
		Principal.main(args);
	}

	@Test
	@DisplayName("TEST: Recuperación semántica declaración clase System y Object")
	void testExcepcionEsperadaDeclaracionClaseSystemObjectRecuperacionSemantica() {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaDeclaracionClaseSystem.txt" };
		Principal.main(args);
	}

	@Test
	@DisplayName("TEST: Recuperación semántica superclase sin declarar")
	void testExcepcionEsperadaSuperclaseSinDeclararRecuperacionSemantica() {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaSuperclaseSinDeclarar.txt" };
		Principal.main(args);
	}

	@Test
	@DisplayName("TEST: Recuperación semántica herencia circular 1")
	void testExcepcionEsperadaHerenciaCircular1RecuperacionSemantica() {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaHerenciaCircular1.txt" };
		Principal.main(args);
	}

	@Test
	@DisplayName("TEST: Recuperación semántica herencia circular 2")
	void testExcepcionEsperadaHerenciaCircular2RecuperacionSemantica() {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaHerenciaCircular2.txt" };
		Principal.main(args);
	}

	@Test
	@DisplayName("TEST: Chequeo clases predefinidas")
	void testUnaClaseConMetodoMainChequeoClasesPredefinidas() {
		String[] args = { "src/test/resources/semantico/unaClaseConMetodoMainChequeoClasesPredefinidas.txt" };
		Principal.main(args);
		assertEquals(3, Principal.ts.getClases().size());
		Clase object = Principal.ts.getClase("Object");
		Clase system = Principal.ts.getClase("System");
		Clase claseVacia = Principal.ts.getClase("Clase");
		assertEquals(0, object.cantidadMetodos());
		assertEquals(1, claseVacia.cantidadMetodos());
		assertEquals(10, system.cantidadMetodos());
		List<Unidad> lm1 = system.getTodosMetodosPorNombre("read");
		List<Unidad> lm2 = system.getTodosMetodosPorNombre("printB");
		List<Unidad> lm3 = system.getTodosMetodosPorNombre("printC");
		List<Unidad> lm4 = system.getTodosMetodosPorNombre("printI");
		List<Unidad> lm5 = system.getTodosMetodosPorNombre("printS");
		List<Unidad> lm6 = system.getTodosMetodosPorNombre("println");
		List<Unidad> lm7 = system.getTodosMetodosPorNombre("printBln");
		List<Unidad> lm8 = system.getTodosMetodosPorNombre("printCln");
		List<Unidad> lm9 = system.getTodosMetodosPorNombre("printIln");
		List<Unidad> lm10 = system.getTodosMetodosPorNombre("printSln");
		assertEquals(1, lm1.size());
		assertEquals(1, lm2.size());
		assertEquals(1, lm3.size());
		assertEquals(1, lm4.size());
		assertEquals(1, lm5.size());
		assertEquals(1, lm6.size());
		assertEquals(1, lm7.size());
		assertEquals(1, lm8.size());
		assertEquals(1, lm9.size());
		assertEquals(1, lm10.size());
		Metodo m1 = (Metodo) lm1.get(0);
		assertEquals("static", m1.getFormaMetodo());
		assertFalse(m1.isMetodoFinal());
		assertEquals("int", m1.getTipo().getNombre());
		assertEquals(0, m1.getParametros().size());
		Metodo m2 = (Metodo) lm2.get(0);
		assertEquals("static", m2.getFormaMetodo());
		assertFalse(m2.isMetodoFinal());
		assertEquals("void", m2.getTipo().getNombre());
		HashMap<String, Parametro> parametros2 = m2.getParametros();
		assertEquals(1, parametros2.size());
		Parametro p2 = parametros2.get("b");
		assertEquals(1, p2.getPosicion());
		assertEquals("boolean", p2.getTipo().getNombre());
		Metodo m3 = (Metodo) lm3.get(0);
		assertEquals("static", m3.getFormaMetodo());
		assertFalse(m3.isMetodoFinal());
		assertEquals("void", m3.getTipo().getNombre());
		HashMap<String, Parametro> parametros3 = m3.getParametros();
		assertEquals(1, parametros3.size());
		Parametro p3 = parametros3.get("c");
		assertEquals(1, p3.getPosicion());
		assertEquals("char", p3.getTipo().getNombre());
		Metodo m4 = (Metodo) lm4.get(0);
		assertEquals("static", m4.getFormaMetodo());
		assertFalse(m4.isMetodoFinal());
		assertEquals("void", m4.getTipo().getNombre());
		HashMap<String, Parametro> parametros4 = m4.getParametros();
		assertEquals(1, parametros4.size());
		Parametro p4 = parametros4.get("i");
		assertEquals(1, p4.getPosicion());
		assertEquals("int", p4.getTipo().getNombre());
		Metodo m5 = (Metodo) lm5.get(0);
		assertEquals("static", m5.getFormaMetodo());
		assertFalse(m5.isMetodoFinal());
		assertEquals("void", m5.getTipo().getNombre());
		HashMap<String, Parametro> parametros5 = m5.getParametros();
		assertEquals(1, parametros5.size());
		Parametro p5 = parametros5.get("s");
		assertEquals(1, p5.getPosicion());
		assertEquals("String", p5.getTipo().getNombre());
		Metodo m6 = (Metodo) lm6.get(0);
		assertEquals("static", m6.getFormaMetodo());
		assertFalse(m6.isMetodoFinal());
		assertEquals("void", m6.getTipo().getNombre());
		HashMap<String, Parametro> parametros6 = m6.getParametros();
		assertEquals(0, parametros6.size());
		Metodo m7 = (Metodo) lm7.get(0);
		assertEquals("static", m7.getFormaMetodo());
		assertFalse(m7.isMetodoFinal());
		assertEquals("void", m7.getTipo().getNombre());
		HashMap<String, Parametro> parametros7 = m7.getParametros();
		assertEquals(1, parametros7.size());
		Parametro p7 = parametros7.get("b");
		assertEquals(1, p7.getPosicion());
		assertEquals("boolean", p7.getTipo().getNombre());
		Metodo m8 = (Metodo) lm8.get(0);
		assertEquals("static", m8.getFormaMetodo());
		assertFalse(m8.isMetodoFinal());
		assertEquals("void", m8.getTipo().getNombre());
		HashMap<String, Parametro> parametros8 = m8.getParametros();
		assertEquals(1, parametros8.size());
		Parametro p8 = parametros8.get("c");
		assertEquals(1, p8.getPosicion());
		assertEquals("char", p8.getTipo().getNombre());
		Metodo m9 = (Metodo) lm9.get(0);
		assertEquals("static", m9.getFormaMetodo());
		assertFalse(m9.isMetodoFinal());
		assertEquals("void", m9.getTipo().getNombre());
		HashMap<String, Parametro> parametros9 = m9.getParametros();
		assertEquals(1, parametros9.size());
		Parametro p9 = parametros9.get("i");
		assertEquals(1, p9.getPosicion());
		assertEquals("int", p9.getTipo().getNombre());
		Metodo m10 = (Metodo) lm10.get(0);
		assertEquals("static", m10.getFormaMetodo());
		assertFalse(m10.isMetodoFinal());
		assertEquals("void", m10.getTipo().getNombre());
		HashMap<String, Parametro> parametros10 = m10.getParametros();
		assertEquals(1, parametros10.size());
		Parametro p10 = parametros10.get("s");
		assertEquals(1, p10.getPosicion());
		assertEquals("String", p10.getTipo().getNombre());
	}

	@Test
	@DisplayName("TEST: Recuperación semántica nombre de atributo repetido")
	void testExcepcionEsperadaNombreAtributoRepetidoRecuperacionSemantica() {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaNombreAtributoRepetido.txt" };
		Principal.main(args);
	}

	@Test
	@DisplayName("TEST: Recuperación semántica atributo de tipo clase sin declarar")
	void testExcepcionEsperadaAtributoTipoClaseSinDeclararRecuperacionSemantica() {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaAtributoTipoClaseSinDeclarar.txt" };
		Principal.main(args);
	}

	@Test
	@DisplayName("TEST: Chequeo agregación constructor predefinido para clase sin constructor")
	void testSinConstructorChequeoAgregacionConstructorPredefinido() {
		String[] args = { "src/test/resources/semantico/sinConstructorChequeoPredefinido.txt" };
		Principal.main(args);
		assertEquals(3, Principal.ts.getClases().size());
		Clase prueba = Principal.ts.getClase("Prueba");
		assertEquals(1, prueba.getConstructores().size());
		Unidad ctor = prueba.getConstructor(1);
		Constructor constructor = (Constructor) ctor;
		assertEquals("Prueba", constructor.getNombre());
		assertEquals(0, constructor.getParametros().size());
	}

	@Test
	@DisplayName("TEST: Chequeo un constructor sin argumentos formales")
	void testUnConstructorSinArgsFormales() {
		String[] args = { "src/test/resources/semantico/unConstructorSinArgsFormales.txt" };
		Principal.main(args);
		assertEquals(3, Principal.ts.getClases().size());
		Clase prueba = Principal.ts.getClase("Prueba");
		assertEquals(1, prueba.getConstructores().size());
		Constructor ctor = (Constructor) prueba.getConstructor(1);
		assertEquals("Prueba", ctor.getNombre());
		assertEquals(0, ctor.getParametros().size());
	}

	@Test
	@DisplayName("TEST: Recuperación semántica un constructor con nombre inválido")
	void testExcepcionEsperadaNombreConstructorDistintoDeNombreClaseRecuperacionSemantica() {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaNombreConstructorDistintoDeNombreClase.txt" };
		Principal.main(args);
	}

	@Test
	@DisplayName("TEST: Chequeo un constructor con 1 argumento formal")
	void testUnConstructorUnArgFormal() {
		String[] args = { "src/test/resources/semantico/unConstructorUnArgFormal.txt" };
		Principal.main(args);
		assertEquals(3, Principal.ts.getClases().size());
		Clase prueba = Principal.ts.getClase("Prueba");
		assertEquals(1, prueba.getConstructores().size());
		Constructor ctor = (Constructor) prueba.getConstructor(1);
		assertEquals("Prueba", ctor.getNombre());
		assertEquals(1, ctor.getParametros().size());
		Parametro p = ctor.getParametros().get("x");
		assertEquals("int", p.getTipo().getNombre());
	}

	@Test
	@DisplayName("TEST: Recuperación semántica constructor con dos parámetros con el mismo nombre")
	void testExcepcionEsperadaConstructorDosParametrosMismoNombreRecuperacionSemantica() {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaConstructorDosParametrosMismoNombre.txt" };
		Principal.main(args);
	}

	@Test
	@DisplayName("TEST: Recuperación semántica constructor con argumentos de tipo clase sin declarar")
	void testExcepcionEsperadaConstructorDosArgFormalTipoClaseSinDeclararRecuperacionSemantica() {
		String[] args = {
				"src/test/resources/semantico/excepcionEsperadaConstructorArgFormalTipoClaseSinDeclarar.txt" };
		Principal.main(args);
	}

	@Test
	@DisplayName("TEST: Chequeo dos constructores misma cantidad de argumentos formales pero distinto tipo")
	void testDosConstructoresMismaCantidadArgsFormalesDistintoTipo() {
		String[] args = { "src/test/resources/semantico/dosConstructoresMismaCantidadArgsFormalesDistintoTipo.txt" };
		Principal.main(args);
		assertEquals(3, Principal.ts.getClases().size());
		Clase prueba = Principal.ts.getClase("Prueba");
		assertEquals(2, prueba.getConstructores().size());
		Constructor ctor1 = (Constructor) prueba.getConstructor(1);
		Constructor ctor2 = (Constructor) prueba.getConstructor(2);
		assertEquals("Prueba", ctor1.getNombre());
		assertEquals("Prueba", ctor2.getNombre());
		assertEquals(1, ctor1.getParametros().size());
		Parametro p1 = ctor1.getParametros().get("i");
		assertEquals("int", p1.getTipo().getNombre());
		assertEquals(1, ctor2.getParametros().size());
		Parametro p2 = ctor2.getParametros().get("b");
		assertEquals("boolean", p2.getTipo().getNombre());
	}

	@Test
	@DisplayName("TEST: Recuperación semántica dos constructores con misma signatura")
	void testExcepcionEsperadaDosConstructoresMismaSignaturaRecuperacionSemantica() {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaDosConstructoresMismaSignatura.txt" };
		Principal.main(args);
	}

	@Test
	@DisplayName("TEST: Chequeo un constructor con 3 argumentos formales")
	void testUnConstructorTresArgsFormales() {
		String[] args = { "src/test/resources/semantico/unConstructorTresArgsFormales.txt" };
		Principal.main(args);
		assertEquals(3, Principal.ts.getClases().size());
		Clase prueba = Principal.ts.getClase("Prueba");
		assertEquals(1, prueba.getConstructores().size());
		Constructor ctor = (Constructor) prueba.getConstructor(1);
		assertEquals("Prueba", ctor.getNombre());
		assertEquals(3, ctor.getParametros().size());
		Parametro p1 = ctor.getParametros().get("i");
		assertEquals("int", p1.getTipo().getNombre());
		assertEquals(1, p1.getPosicion());
		Parametro p2 = ctor.getParametros().get("b");
		assertEquals("boolean", p2.getTipo().getNombre());
		assertEquals(2, p2.getPosicion());
		Parametro p3 = ctor.getParametros().get("c");
		assertEquals("char", p3.getTipo().getNombre());
		assertEquals(3, p3.getPosicion());
	}

	@Test
	@DisplayName("TEST: Chequeo un método sin argumentos formales")
	void testUnMetodoSinArgsFormales() {
		String[] args = { "src/test/resources/semantico/unMetodoSinArgsFormales.txt" };
		Principal.main(args);
		assertEquals(3, Principal.ts.getClases().size());
		Clase prueba = Principal.ts.getClase("Prueba");
		assertEquals(2, prueba.cantidadMetodos());
		Metodo m1 = (Metodo) prueba.getTodosMetodosPorNombre("met1").get(0);
		assertEquals("static", m1.getFormaMetodo());
		assertFalse(m1.isMetodoFinal());
		assertEquals("void", m1.getTipo().getNombre());
		assertEquals(0, m1.getParametros().size());
	}

	@Test
	@DisplayName("TEST: Recuperación semántica método con el tipo de retorno sin declarar")
	void testExcepcionEsperadaMetodoRetornoClaseSinDeclararRecuperacionSemantica() {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaMetodoRetornoClaseSinDeclarar.txt" };
		Principal.main(args);
	}

	@Test
	@DisplayName("TEST: Chequeo un método con 1 argumento formal")
	void testUnMetodoUnArgFormal() {
		String[] args = { "src/test/resources/semantico/unMetodoUnArgFormal.txt" };
		Principal.main(args);
		assertEquals(3, Principal.ts.getClases().size());
		Clase prueba = Principal.ts.getClase("Prueba");
		assertEquals(2, prueba.cantidadMetodos());
		Metodo m1 = (Metodo) prueba.getTodosMetodosPorNombre("met1").get(0);
		assertEquals("dynamic", m1.getFormaMetodo());
		assertTrue(m1.isMetodoFinal());
		assertEquals("Prueba", m1.getTipo().getNombre());
		assertEquals(1, m1.getParametros().size());
		Parametro p = m1.getParametroPorNombre("x");
		assertEquals("int", p.getTipo().getNombre());
	}

	@Test
	@DisplayName("TEST: Recuperación semántica método con argumentos de tipo clase sin declarar")
	void testExcepcionEsperadaMetodoDosArgFormalTipoClaseSinDeclararRecuperacionSemantica() {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaMetodoArgFormalTipoClaseSinDeclarar.txt" };
		Principal.main(args);
	}

	@Test
	@DisplayName("TEST: Recuperación semántica método con dos parámetros con el mismo nombre")
	void testExcepcionEsperadaMetodoDosParametrosMismoNombreRecuperacionSemantica() {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaMetodoDosParametrosMismoNombre.txt" };
		Principal.main(args);
	}

	@Test
	@DisplayName("TEST: Recuperación semántica dos métodos con misma signatura")
	void testExcepcionEsperadaDosMetodosMismaSignaturaRecuperacionSemantica() {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaDosMetodosMismaSignatura.txt" };
		Principal.main(args);
	}

	@Test
	@DisplayName("TEST: Chequeo un método con 3 argumentos formales")
	void testUnMetodoTresArgsFormales() {
		String[] args = { "src/test/resources/semantico/unMetodoTresArgsFormales.txt" };
		Principal.main(args);
		assertEquals(3, Principal.ts.getClases().size());
		Clase prueba = Principal.ts.getClase("Prueba");
		assertEquals(2, prueba.cantidadMetodos());
		Metodo m1 = (Metodo) prueba.getTodosMetodosPorNombre("met1").get(0);
		assertEquals("dynamic", m1.getFormaMetodo());
		assertFalse(m1.isMetodoFinal());
		assertEquals("Object", m1.getTipo().getNombre());
		assertEquals(3, m1.getParametros().size());
		Parametro p = m1.getParametroPorNombre("i");
		assertEquals("int", p.getTipo().getNombre());
		p = m1.getParametroPorNombre("b");
		assertEquals("boolean", p.getTipo().getNombre());
		p = m1.getParametroPorNombre("c");
		assertEquals("char", p.getTipo().getNombre());
	}

	@Test
	@DisplayName("TEST: Recuperación semántica incorrecta redefinición de método")
	void testExcepcionEsperadaIncorrectaRedefinicionMetodoRecuperacionSemantica() {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaIncorrectaRedefinicionMetodo.txt" };
		Principal.main(args);
	}

	@Test
	@DisplayName("TEST: Chequeo correcta redefinición de método")
	void testCorrectaRedefinicionMetodo() {
		String[] args = { "src/test/resources/semantico/correctaRedefinicionMetodo.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		Clase a = ts.getClase("A");
		Clase c = ts.getClase("C");
		assertEquals(1, a.cantidadMetodos());
		assertEquals(2, c.cantidadMetodos());
		Metodo a_met1 = (Metodo) a.getTodosMetodosPorNombre("met1").get(0);
		Metodo c_met1 = (Metodo) c.getTodosMetodosPorNombre("met1").get(0);
		assertNotEquals(a_met1, c_met1);
		Parametro pA = a_met1.getParametroPorPosicion(1);
		Parametro pC = c_met1.getParametroPorPosicion(1);
		assertEquals("x", pA.getNombre());
		assertEquals("boolean", pA.getTipo().getNombre());
		assertEquals("y", pC.getNombre());
		assertEquals("boolean", pC.getTipo().getNombre());
	}

	@Test
	@DisplayName("TEST: Recuperación semántica redefinición de un método final")
	void testExcepcionEsperadaConsolidacionMetodosSobreescrituraFinalRecuperacionSemantica() {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaConsolidacionMetodosSobreescrituraFinal.txt" };
		Principal.main(args);
	}

	@Test
	@DisplayName("TEST: Chequeo sobrecarga de métodos, método sin y con modificador final")
	void testSobrecargaMetodosConUnoFinalMismaClase() {
		String[] args = { "src/test/resources/semantico/sobrecargaMetodosConUnoFinal.txt" };
		Principal.main(args);
		assertEquals(3, Principal.ts.getClases().size());
		Clase a = Principal.ts.getClase("A");
		assertEquals(3, a.cantidadMetodos());
		Metodo m1 = (Metodo) a.getTodosMetodosPorNombre("m1").get(0);
		assertEquals("dynamic", m1.getFormaMetodo());
		assertFalse(m1.isMetodoFinal());
		assertEquals("void", m1.getTipo().getNombre());
		assertEquals(0, m1.getParametros().size());
		Metodo m2 = (Metodo) a.getTodosMetodosPorNombre("m1").get(1);
		assertEquals("dynamic", m2.getFormaMetodo());
		assertTrue(m2.isMetodoFinal());
		assertEquals("void", m2.getTipo().getNombre());
		assertEquals(1, m2.getParametros().size());
		Parametro p = m2.getParametroPorNombre("i");
		assertEquals("int", p.getTipo().getNombre());
	}

	@Test
	@DisplayName("TEST: Chequeo sobrecarga de métodos, método final en superclase y sobrecargado en la otra")
	void testSobrecargaMetodosConUnoFinalDistintasClases() {
		String[] args = { "src/test/resources/semantico/sobrecargaMetodoFinalDistintasClases.txt" };
		Principal.main(args);
		assertEquals(6, Principal.ts.getClases().size());
		Clase b = Principal.ts.getClase("B");
		assertEquals(3, b.cantidadMetodos());
		Metodo b_m3 = (Metodo) b.getTodosMetodosPorNombre("m3").get(0);
		assertTrue(b_m3.isMetodoFinal());
		Clase c = Principal.ts.getClase("C");
		assertEquals(5, c.cantidadMetodos());
		assertEquals(2, c.getTodosMetodosPorNombre("m3").size());
		Clase d = Principal.ts.getClase("D");
		assertEquals(1, d.cantidadMetodos());
	}

	@Test
	@DisplayName("TEST: Chequeo consolidación de atributos")
	void testConsolidacionAtributos() {
		String[] args = { "src/test/resources/semantico/consolidacionAtributos.txt" };
		Principal.main(args);
		assertEquals(5, Principal.ts.getClases().size());
		Clase a = Principal.ts.getClase("A");
		Clase b = Principal.ts.getClase("B");
		Clase c = Principal.ts.getClase("C");
		assertEquals(2, a.cantidadAtributos());
		assertEquals(4, b.cantidadAtributos());
		assertEquals(6, c.cantidadAtributos());
		VariableInstancia v1A = a.getAtributoPorNombre("v1");
		assertEquals("public", v1A.getVisibilidad());
		assertEquals("int", v1A.getTipo().getNombre());
		VariableInstancia v2A = a.getAtributoPorNombre("v2");
		assertEquals("public", v2A.getVisibilidad());
		assertEquals("int", v2A.getTipo().getNombre());
		VariableInstancia v1B = b.getAtributoPorNombre("v1");
		assertEquals(v1A, v1B);
		VariableInstancia v2B = b.getAtributoPorNombre("v2");
		assertEquals(v2A, v2B);
		VariableInstancia v3B = b.getAtributoPorNombre("v3");
		assertEquals("public", v3B.getVisibilidad());
		assertEquals("String", v3B.getTipo().getNombre());
		VariableInstancia v4B = b.getAtributoPorNombre("v4");
		assertEquals("private", v4B.getVisibilidad());
		assertEquals("boolean", v4B.getTipo().getNombre());
		VariableInstancia v1C = c.getAtributoPorNombre("v1");
		assertEquals(v1C, v1A);
		VariableInstancia v2C = c.getAtributoPorNombre("v2");
		assertEquals(v2C, v2A);
		VariableInstancia v3C = c.getAtributoPorNombre("v3");
		assertEquals(v3C, v3B);
		VariableInstancia v4C = c.getAtributoPorNombre("v4");
		assertEquals(v4C, v4B);
		VariableInstancia v5C = c.getAtributoPorNombre("v5");
		assertEquals("public", v5C.getVisibilidad());
		assertEquals("int", v5C.getTipo().getNombre());
		VariableInstancia v6C = c.getAtributoPorNombre("v6");
		assertEquals("public", v6C.getVisibilidad());
		assertEquals("int", v6C.getTipo().getNombre());
	}

	@Test
	@DisplayName("TEST: Chequeo consolidación de métodos")
	void testConsolidacionMetodos() {
		String[] args = { "src/test/resources/semantico/consolidacionMetodos.txt" };
		Principal.main(args);
		assertEquals(5, Principal.ts.getClases().size());
		Clase a = Principal.ts.getClase("A");
		Clase b = Principal.ts.getClase("B");
		Clase c = Principal.ts.getClase("C");
		assertEquals(2, a.cantidadMetodos());
		assertEquals(3, b.cantidadMetodos());
		assertEquals(5, c.cantidadMetodos());
		Metodo m1A = (Metodo) a.getTodosMetodosPorNombre("m1").get(0);
		assertEquals("dynamic", m1A.getFormaMetodo());
		assertEquals("void", m1A.getTipo().getNombre());
		assertEquals(0, m1A.getCantidadParametros());
		Metodo m2A = (Metodo) a.getTodosMetodosPorNombre("m2").get(0);
		assertEquals("dynamic", m2A.getFormaMetodo());
		assertEquals("void", m2A.getTipo().getNombre());
		assertEquals(1, m2A.getCantidadParametros());
		Parametro p1 = m2A.getParametroPorNombre("p");
		assertEquals(1, p1.getPosicion());
		assertEquals("int", p1.getTipo().getNombre());
		Metodo m1B = (Metodo) b.getTodosMetodosPorNombre("m1").get(0);
		assertEquals(m1A, m1B);
		Metodo m2B = (Metodo) b.getTodosMetodosPorNombre("m2").get(0);
		assertEquals("dynamic", m2B.getFormaMetodo());
		assertEquals("void", m2B.getTipo().getNombre());
		assertEquals(1, m2B.getCantidadParametros());
		Parametro p2 = m2B.getParametroPorNombre("x");
		assertEquals(1, p2.getPosicion());
		assertEquals("int", p2.getTipo().getNombre());
		Metodo m3B = (Metodo) b.getTodosMetodosPorNombre("m3").get(0);
		assertEquals("dynamic", m3B.getFormaMetodo());
		assertEquals("void", m3B.getTipo().getNombre());
		assertEquals(0, m3B.getCantidadParametros());
		Metodo m1C = (Metodo) c.getTodosMetodosPorNombre("m1").get(0);
		assertEquals(m1A, m1C);
		Metodo m2C = (Metodo) c.getTodosMetodosPorNombre("m2").get(0);
		assertEquals(m2B, m2C);
		Metodo m3C = (Metodo) c.getTodosMetodosPorNombre("m3").get(0);
		assertEquals("dynamic", m3C.getFormaMetodo());
		assertEquals("void", m3C.getTipo().getNombre());
		assertEquals(0, m3C.getCantidadParametros());
		Metodo m4C = (Metodo) c.getTodosMetodosPorNombre("m4").get(0);
		assertEquals("dynamic", m4C.getFormaMetodo());
		assertEquals("void", m4C.getTipo().getNombre());
		assertEquals(0, m4C.getCantidadParametros());
	}

	@Test
	@DisplayName("TEST: Recuperación semántica de todo")
	void testRecuperacionSemantica() {
		String[] args = { "src/test/resources/semantico/recuperacionSemantica.txt" };
		Principal.main(args);
	}
}
