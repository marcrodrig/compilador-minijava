package test.java;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import lexico.ExcepcionLexico;
import main.Principal;
import semantico.Clase;
import semantico.Constructor;
import semantico.ExcepcionSemantico;
import semantico.Metodo;
import semantico.Parametro;
import semantico.TablaSimbolos;
import sintactico.AnalizadorSintactico;
import sintactico.ExcepcionPanicMode;
import sintactico.ExcepcionSintactico;

class AnalizadorSemanticoTest {

	@AfterEach
	public void resetTS() {
		Principal.ts.reset();
	}
	
	@Test public void testUnaClaseVaciaChequeoClasesPredefinidas() { 
		String[] args = {"src/test/resources/semantico/unaClaseVaciaChequeoClasesPredefinidas.txt"};
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(3,ts.getClases().size());
		Clase object = ts.getClase("Object");
		Clase system = ts.getClase("System");
		Clase claseVacia = ts.getClase("ClaseVacia");
		assertEquals(0,object.cantidadMetodos());
		assertEquals(0,claseVacia.cantidadMetodos());
		assertEquals(10,system.cantidadMetodos());
		List<Metodo> lm1 = system.getMetodosPorNombre("read");
		List<Metodo> lm2 = system.getMetodosPorNombre("printB");
		List<Metodo> lm3 = system.getMetodosPorNombre("printC");
		List<Metodo> lm4 = system.getMetodosPorNombre("printI");
		List<Metodo> lm5 = system.getMetodosPorNombre("printS");
		List<Metodo> lm6 = system.getMetodosPorNombre("println");
		List<Metodo> lm7 = system.getMetodosPorNombre("printBln");
		List<Metodo> lm8 = system.getMetodosPorNombre("printCln");
		List<Metodo> lm9 = system.getMetodosPorNombre("printIln");
		List<Metodo> lm10 = system.getMetodosPorNombre("printSln");
		assertEquals(1,lm1.size());
		assertEquals(1,lm2.size());
		assertEquals(1,lm3.size());
		assertEquals(1,lm4.size());
		assertEquals(1,lm5.size());
		assertEquals(1,lm6.size());
		assertEquals(1,lm7.size());
		assertEquals(1,lm8.size());
		assertEquals(1,lm9.size());
		assertEquals(1,lm10.size());
		Metodo m1 = lm1.get(0);
		assertEquals("static", m1.getFormaMetodo());
		assertFalse(m1.isMetodoFinal());
		assertEquals("int",m1.getTipo().getNombre());
		assertEquals(0, m1.getParametros().size());
		Metodo m2 = lm2.get(0);
		assertEquals("static", m2.getFormaMetodo());
		assertFalse(m2.isMetodoFinal());
		assertEquals("void",m2.getTipo().getNombre());
		HashMap<String, Parametro> parametros2 = m2.getParametros();
		assertEquals(1, parametros2.size());
		Parametro p2 = parametros2.get("b");
		assertEquals(1, p2.getPosicion());
		assertEquals("boolean",p2.getTipo().getNombre());
		Metodo m3 = lm3.get(0);
		assertEquals("static", m3.getFormaMetodo());
		assertFalse(m3.isMetodoFinal());
		assertEquals("void",m3.getTipo().getNombre());
		HashMap<String, Parametro> parametros3 = m3.getParametros();
		assertEquals(1, parametros3.size());
		Parametro p3 = parametros3.get("c");
		assertEquals(1, p3.getPosicion());
		assertEquals("char",p3.getTipo().getNombre());
		Metodo m4 = lm4.get(0);
		assertEquals("static", m4.getFormaMetodo());
		assertFalse(m4.isMetodoFinal());
		assertEquals("void",m4.getTipo().getNombre());
		HashMap<String, Parametro> parametros4 = m4.getParametros();
		assertEquals(1, parametros4.size());
		Parametro p4 = parametros4.get("i");
		assertEquals(1, p4.getPosicion());
		assertEquals("int",p4.getTipo().getNombre());
		Metodo m5 = lm5.get(0);
		assertEquals("static", m5.getFormaMetodo());
		assertFalse(m5.isMetodoFinal());
		assertEquals("void",m5.getTipo().getNombre());
		HashMap<String, Parametro> parametros5 = m5.getParametros();
		assertEquals(1, parametros5.size());
		Parametro p5 = parametros5.get("s");
		assertEquals(1, p5.getPosicion());
		assertEquals("String",p5.getTipo().getNombre());
		Metodo m6 = lm6.get(0);
		assertEquals("static", m6.getFormaMetodo());
		assertFalse(m6.isMetodoFinal());
		assertEquals("void",m6.getTipo().getNombre());
		HashMap<String, Parametro> parametros6 = m6.getParametros();
		assertEquals(0, parametros6.size());
		Metodo m7 = lm7.get(0);
		assertEquals("static", m7.getFormaMetodo());
		assertFalse(m7.isMetodoFinal());
		assertEquals("void",m7.getTipo().getNombre());
		HashMap<String, Parametro> parametros7 = m7.getParametros();
		assertEquals(1, parametros7.size());
		Parametro p7 = parametros7.get("b");
		assertEquals(1, p7.getPosicion());
		assertEquals("boolean",p7.getTipo().getNombre());
		Metodo m8 = lm8.get(0);
		assertEquals("static", m8.getFormaMetodo());
		assertFalse(m8.isMetodoFinal());
		assertEquals("void",m8.getTipo().getNombre());
		HashMap<String, Parametro> parametros8 = m8.getParametros();
		assertEquals(1, parametros8.size());
		Parametro p8 = parametros8.get("c");
		assertEquals(1, p8.getPosicion());
		assertEquals("char",p8.getTipo().getNombre());
		Metodo m9 = lm9.get(0);
		assertEquals("static", m9.getFormaMetodo());
		assertFalse(m9.isMetodoFinal());
		assertEquals("void",m9.getTipo().getNombre());
		HashMap<String, Parametro> parametros9 = m9.getParametros();
		assertEquals(1, parametros9.size());
		Parametro p9 = parametros9.get("i");
		assertEquals(1, p9.getPosicion());
		assertEquals("int",p9.getTipo().getNombre());
		Metodo m10 = lm10.get(0);
		assertEquals("static", m10.getFormaMetodo());
		assertFalse(m10.isMetodoFinal());
		assertEquals("void",m10.getTipo().getNombre());
		HashMap<String, Parametro> parametros10 = m10.getParametros();
		assertEquals(1, parametros10.size());
		Parametro p10 = parametros10.get("s");
		assertEquals(1, p10.getPosicion());
		assertEquals("String",p10.getTipo().getNombre());
	}
/*
	@Test public void testRapido() { 
		String[] args = {"src/test/resources/semantico/rapido.txt"};
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(3,ts.getClases().size());
		Clase object = ts.getClase("Object");
		Clase system = ts.getClase("System");
		Clase claseOtra = ts.getClase("ClaseOtra");
		assertEquals(0,object.getMetodos().size());
		assertEquals(0,claseOtra.getMetodos().size());
		assertEquals(10,system.getMetodos().size());
	}
	*/
	@Test
	void testExcepcionEsperadaDeclaracionClaseObject() {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaDeclaracionClaseObject.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSemantico e = assertThrows(ExcepcionSemantico.class, () -> {
				analizadorSintactico.start();
			});
			assertEquals("[1] Error semántico: La clase Object ya está definida.", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
		Principal.main(args);
	}
	
	@Test
	void testExcepcionEsperadaDeclaracionClaseSystem() {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaDeclaracionClaseSystem.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSemantico e = assertThrows(ExcepcionSemantico.class, () -> {
				analizadorSintactico.start();
			});
			assertEquals("[1] Error semántico: La clase System ya está definida.", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
		Principal.main(args);
	}
	
	@Test
	void testExcepcionEsperadaHerenciaCircular1() throws ExcepcionSemantico {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaHerenciaCircular1.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			TablaSimbolos ts = TablaSimbolos.getInstance();
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.start();
			ExcepcionSemantico e = assertThrows(ExcepcionSemantico.class, () -> {
				ts.chequeoDeclaraciones();
			});
			assertEquals("[3] Error semántico: La clase A tiene herencia circular.", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico | ExcepcionPanicMode e) {
			fail("No debería suceder esto");
		}
		Principal.main(args);
	}
	
	@Test
	void testExcepcionEsperadaHerenciaCircular2() throws ExcepcionSemantico {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaHerenciaCircular2.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			TablaSimbolos ts = TablaSimbolos.getInstance();
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.start();
			ExcepcionSemantico e = assertThrows(ExcepcionSemantico.class, () -> {
				ts.chequeoDeclaraciones();
			});
			assertEquals("[5] Error semántico: La clase A tiene herencia circular.", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico | ExcepcionPanicMode e) {
			fail("No debería suceder esto");
		}
		Principal.main(args);
	}
	
	@Test public void testUnConstructorSinArgsFormales() { 
		String[] args = {"src/test/resources/semantico/unConstructorSinArgsFormales.txt"};
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(3,ts.getClases().size());
		Clase prueba = ts.getClase("Prueba");
		assertEquals(1,prueba.getConstructores().size());
		Constructor ctor = prueba.getConstructor(1);
		assertEquals("Prueba", ctor.getNombre());
		assertEquals(0, ctor.getParametros().size());
	}
	
	@Test public void testUnConstructorUnArgFormal() { 
		String[] args = {"src/test/resources/semantico/unConstructorUnArgFormal.txt"};
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(3,ts.getClases().size());
		Clase prueba = ts.getClase("Prueba");
		assertEquals(1,prueba.getConstructores().size());
		Constructor ctor = prueba.getConstructor(1);
		assertEquals("Prueba", ctor.getNombre());
		assertEquals(1, ctor.getParametros().size());
		Parametro p = ctor.getParametros().get("x");
		assertEquals("int", p.getTipo().getNombre());
	}
	
	@Test public void testUnConstructorTresArgsFormales() { 
		String[] args = {"src/test/resources/semantico/unConstructorTresArgsFormales.txt"};
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(3,ts.getClases().size());
		Clase prueba = ts.getClase("Prueba");
		assertEquals(1,prueba.getConstructores().size());
		Constructor ctor = prueba.getConstructor(1);
		assertEquals("Prueba", ctor.getNombre());
		assertEquals(3, ctor.getParametros().size());
		Parametro p1 = ctor.getParametros().get("i");
		assertEquals("int", p1.getTipo().getNombre());
		assertEquals(1,p1.getPosicion());
		Parametro p2 = ctor.getParametros().get("b");
		assertEquals("boolean", p2.getTipo().getNombre());
		assertEquals(2,p2.getPosicion());
		Parametro p3 = ctor.getParametros().get("c");
		assertEquals("char", p3.getTipo().getNombre());
		assertEquals(3,p3.getPosicion());
	}
	
	@Test public void testSinConstructorChequeoPredefinido() { 
		String[] args = {"src/test/resources/semantico/sinConstructorChequeoPredefinido.txt"};
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(3,ts.getClases().size());
		Clase prueba = ts.getClase("Prueba");
		assertEquals(1,prueba.getConstructores().size());
		Constructor ctor = prueba.getConstructor(1);
		assertEquals("Prueba", ctor.getNombre());
		assertEquals(0, ctor.getParametros().size());
	}
	
	@Test
	void testExcepcionEsperadaNombreConstructorDistintoDeNombreClase() throws ExcepcionSemantico {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaNombreConstructorDistintoDeNombreClase.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSemantico e = assertThrows(ExcepcionSemantico.class, () -> {
				analizadorSintactico.start();
			});
			assertEquals("[2] Error semántico: El nombre del constructor es inválido, debe ser Prueba.", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e) {
			fail("No debería suceder esto");
		}
		Principal.main(args);
	}
	
	@Test
	void testExcepcionEsperadaDosConstructoresMismaSignatura() throws ExcepcionSemantico, ExcepcionSintactico, ExcepcionPanicMode {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaDosConstructoresMismaSignatura.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			TablaSimbolos ts = TablaSimbolos.getInstance();
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.start();
			ExcepcionSemantico e = assertThrows(ExcepcionSemantico.class, () -> {
				ts.chequeoDeclaraciones();
			});
			assertEquals("[3] Error semántico: Constructor con misma signatura ya definido.", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e) {
			fail("No debería suceder esto");
		}
		Principal.main(args);
	}
	
	@Test
	void testDosConstructoresMismaCantidadArgsFormalesDistintoTipo() { 
		String[] args = {"src/test/resources/semantico/dosConstructoresMismaCantidadArgsFormalesDistintoTipo.txt"};
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(3,ts.getClases().size());
		Clase prueba = ts.getClase("Prueba");
		assertEquals(2,prueba.getConstructores().size());
		Constructor ctor1 = prueba.getConstructor(1);
		Constructor ctor2 = prueba.getConstructor(2);
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
	void testExcepcionEsperadaConstructorDosParametrosMismoNombre() throws ExcepcionSemantico, ExcepcionSintactico, ExcepcionPanicMode {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaConstructorDosParametrosMismoNombre.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSemantico e = assertThrows(ExcepcionSemantico.class, () -> {
				analizadorSintactico.start();
			});
			assertEquals("[2] Error semántico: Nombre de parámetro \"i\" repetido.", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e) {
			fail("No debería suceder esto");
		}
		Principal.main(args);
	}
	
	@Test
	void testExcepcionEsperadaSuperclaseSinDeclarar() throws ExcepcionSemantico, ExcepcionSintactico, ExcepcionPanicMode {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaSuperclaseSinDeclarar.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			TablaSimbolos ts = TablaSimbolos.getInstance();
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.start();
			ExcepcionSemantico e = assertThrows(ExcepcionSemantico.class, () -> {
				ts.chequeoDeclaraciones();
			});
			assertEquals("[1] Error semántico: La superclase B de A no está definida.", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e) {
			fail("No debería suceder esto");
		}
		Principal.main(args);
	}
	
	@Test
	void testUnMetodoSinArgsFormales() { 
		String[] args = {"src/test/resources/semantico/unMetodoSinArgsFormales.txt"};
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(3,ts.getClases().size());
		Clase prueba = ts.getClase("Prueba");
		assertEquals(1,prueba.cantidadMetodos());
		Metodo m1 = prueba.getMetodosPorNombre("met1").get(0);
		assertEquals("static", m1.getFormaMetodo());
		assertFalse(m1.isMetodoFinal());
		assertEquals("void",m1.getTipo().getNombre());
		assertEquals(0, m1.getParametros().size());
	}
	
	@Test
	void testUnMetodoUnArgFormal() { 
		String[] args = {"src/test/resources/semantico/unMetodoUnArgFormal.txt"};
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(3,ts.getClases().size());
		Clase prueba = ts.getClase("Prueba");
		assertEquals(1,prueba.cantidadMetodos());
		Metodo m1 = prueba.getMetodosPorNombre("met1").get(0);
		assertEquals("dynamic", m1.getFormaMetodo());
		assertTrue(m1.isMetodoFinal());
		assertEquals("Prueba",m1.getTipo().getNombre());
		assertEquals(1, m1.getParametros().size());
		Parametro p = m1.getParametroPorNombre("x");
		assertEquals("int", p.getTipo().getNombre());
	}
	
	@Test 
	void testUnMetodoTresArgsFormales() { 
		String[] args = {"src/test/resources/semantico/unMetodoTresArgsFormales.txt"};
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(3,ts.getClases().size());
		Clase prueba = ts.getClase("Prueba");
		assertEquals(1,prueba.cantidadMetodos());
		Metodo m1 = prueba.getMetodosPorNombre("met1").get(0);
		assertEquals("dynamic", m1.getFormaMetodo());
		assertFalse(m1.isMetodoFinal());
		assertEquals("Object",m1.getTipo().getNombre());
		assertEquals(3, m1.getParametros().size());
		Parametro p = m1.getParametroPorNombre("i");
		assertEquals("int", p.getTipo().getNombre());
		p = m1.getParametroPorNombre("b");
		assertEquals("boolean", p.getTipo().getNombre());
		p = m1.getParametroPorNombre("c");
		assertEquals("char", p.getTipo().getNombre());
	}
	
	@Test
	void testExcepcionEsperadaMetodoDosParametrosMismoNombre() throws ExcepcionSemantico, ExcepcionSintactico, ExcepcionPanicMode {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaMetodoDosParametrosMismoNombre.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSemantico e = assertThrows(ExcepcionSemantico.class, () -> {
				analizadorSintactico.start();
			});
			assertEquals("[3] Error semántico: Nombre de parámetro \"i\" repetido.", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e) {
			fail("No debería suceder esto");
		}
		Principal.main(args);
	}
	
	@Test
	void testExcepcionEsperadaDosMetodosMismaSignatura() throws ExcepcionSemantico, ExcepcionSintactico, ExcepcionPanicMode {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaDosMetodosMismaSignatura.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			TablaSimbolos ts = TablaSimbolos.getInstance();
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.start();
			ExcepcionSemantico e = assertThrows(ExcepcionSemantico.class, () -> {
				ts.chequeoDeclaraciones();
			});
			assertEquals("[3] Error semántico: Método con misma signatura ya definido.", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e) {
			fail("No debería suceder esto");
		}
		Principal.main(args);
	}
	
	@Test
	void testExcepcionEsperadaMetodoRetornoClaseSinDeclarar() throws ExcepcionSemantico, ExcepcionSintactico, ExcepcionPanicMode {
		String[] args = { "src/test/resources/semantico/excepcionEsperadaMetodoRetornoClaseSinDeclarar.txt" };
		AnalizadorSintactico analizadorSintactico;
		try {
			TablaSimbolos ts = TablaSimbolos.getInstance();
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.start();
			ExcepcionSemantico e = assertThrows(ExcepcionSemantico.class, () -> {
				ts.chequeoDeclaraciones();
			});
			assertEquals("[2] Error semántico: El tipo de retorno B del método met1 no está definido.", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e) {
			fail("No debería suceder esto");
		}
		Principal.main(args);
	}
	
	@Test
	void testConsolidacionMetodos() { 
		String[] args = {"src/test/resources/semantico/consolidacionMetodos.txt"};
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(5,ts.getClases().size());
		Clase a = ts.getClase("A");
		Clase b = ts.getClase("B");
		Clase c = ts.getClase("C");
		assertEquals(2,a.cantidadMetodos());
		assertEquals(3,b.cantidadMetodos());
		assertEquals(4,c.cantidadMetodos());
		Metodo m1A = a.getMetodosPorNombre("m1").get(0);
		assertEquals("dynamic",m1A.getFormaMetodo());
		assertEquals("void",m1A.getTipo().getNombre());
		assertEquals(0,m1A.cantidadParametros());
		Metodo m2A = a.getMetodosPorNombre("m2").get(0);
		assertEquals("dynamic",m2A.getFormaMetodo());
		assertEquals("void",m2A.getTipo().getNombre());
		assertEquals(1,m2A.cantidadParametros());
		Parametro p1 = m2A.getParametroPorNombre("p");
		assertEquals(1,p1.getPosicion());
		assertEquals("int",p1.getTipo().getNombre());
		Metodo m1B = b.getMetodosPorNombre("m1").get(0);
		assertEquals(m1A,m1B);
		Metodo m1C = c.getMetodosPorNombre("m1").get(0);
		assertEquals(m1A,m1C);
		/*
		 * SEGUIR ACA
		 */
	}
}
