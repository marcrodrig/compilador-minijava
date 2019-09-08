package test.java;

import main.AnalizadorSintactico;
import main.ExcepcionLexico;
import main.ExcepcionSintactico;
import main.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.FileNotFoundException;
import org.junit.jupiter.api.Test;

class SintacticoTest {
	
	@Test
	public void testRapido() {
		String[] args = {"src/test/resources/sintactico/rapido.txt"};
		Principal.main(args);
	
	}
	
	@Test
	void testExcepcionEsperadaSinClassAlPrincipio() {
		String[] args = {"src/test/resources/sintactico/sinClassAlPrincipio.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[1] Error sintáctico.\nEsperado: class\nEncontrado: idClase", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}
	
	@Test
	void testExcepcionEsperadaSegundaClaseSinClassAlPrincipio() {
		String[] args = {"src/test/resources/sintactico/segundaClaseSinClassAlPrincipio.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[3] Error sintáctico en la declaración de una clase.\nEsperado: class\nEncontrado: idClase", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}
	
	@Test
	void testExcepcionEsperadaClaseSinLlaveCierre() {
		String[] args = {"src/test/resources/sintactico/claseSinLlaveCierre.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[1] Error sintáctico. Se espera la declaración de un atributo, constructor, método o }.\nEsperado: public, protected, private, idClase, static, dynamic o }\nEncontrado: EOF", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}
	
	@Test
	void testExcepcionEsperadaSegundoMiembroIncorrecto() {
		String[] args = {"src/test/resources/sintactico/segundoMiembroIncorrecto.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[3] Error sintáctico. Se espera la declaración de un atributo, constructor, método o }.\nEsperado: public, protected, private, idClase, static, dynamic o }\nEncontrado: +", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}
	
	@Test
	void testExcepcionEsperadaArchivoVacío() {
		String[] args = {"src/test/resources/sintactico/archivoVacío.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[1] Error sintáctico.\nEsperado: class\nEncontrado: EOF", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}
	
	@Test
	void testExcepcionEsperadaClassSeguidoDeLlave() {
		String[] args = {"src/test/resources/sintactico/classSeguidoDeLlave.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[1] Error sintáctico.\nEsperado: idClase\nEncontrado: {", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseSumaDespuesDeIdClase() {
		String[] args = {"src/test/resources/sintactico/claseSumaDespuesDeIdClase.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[1] Error sintáctico en la declaración de una clase.\nEsperado: extends o {\nEncontrado: +", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}	
	
	@Test
	void testExcepcionEsperadaExtendsSeguidoDeLlave() {
		String[] args = {"src/test/resources/sintactico/extendsSeguidoDeLlave.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[1] Error sintáctico en la declaración de herencia de una clase.\nEsperado: idClase\nEncontrado: {", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No debería suceder esto");
		}
	}
	
	@Test
	void testUnaClaseVacía() {
		String[] args = {"src/test/resources/sintactico/unaClaseVacía.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.inicial();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico e) {
			fail("No debería suceder esto");
		}
	}

	@Test
	void testVariasClasesVacías() {
		String[] args = {"src/test/resources/sintactico/variasClasesVacías.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.inicial();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico e) {
			fail("No debería suceder esto");
		}
	}
	
	@Test
	void testClaseConExtendsVacía() {
		String[] args = {"src/test/resources/sintactico/claseConExtendsVacía.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.inicial();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico e) {
			fail("No debería suceder esto");
		}
	}
	
	@Test
	void testDeclaracionListaAtributosSinAsignacion() {
		String[] args = {"src/test/resources/sintactico/declaracionListaAtributosSinAsignacion.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.inicial();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico e) {
			fail("No debería suceder esto");
		}
	}
	
	@Test
	void testDeclaracionListaAtributosConAsignacion() {
		String[] args = {"src/test/resources/sintactico/declaracionListaAtributosConAsignacion.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.inicial();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico e) {
			fail("No debería suceder esto");
		}
	}
	
}
