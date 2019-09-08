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
			assertEquals("[1] Error sint�ctico.\nEsperado: class\nEncontrado: idClase", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No deber�a suceder esto");
		}
	}
	
	@Test
	void testExcepcionEsperadaSegundaClaseSinClassAlPrincipio() {
		String[] args = {"src/test/resources/sintactico/segundaClaseSinClassAlPrincipio.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[3] Error sint�ctico en la declaraci�n de una clase.\nEsperado: class\nEncontrado: idClase", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No deber�a suceder esto");
		}
	}
	
	@Test
	void testExcepcionEsperadaClaseSinLlaveCierre() {
		String[] args = {"src/test/resources/sintactico/claseSinLlaveCierre.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[1] Error sint�ctico. Se espera la declaraci�n de un atributo, constructor, m�todo o }.\nEsperado: public, protected, private, idClase, static, dynamic o }\nEncontrado: EOF", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No deber�a suceder esto");
		}
	}
	
	@Test
	void testExcepcionEsperadaSegundoMiembroIncorrecto() {
		String[] args = {"src/test/resources/sintactico/segundoMiembroIncorrecto.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[3] Error sint�ctico. Se espera la declaraci�n de un atributo, constructor, m�todo o }.\nEsperado: public, protected, private, idClase, static, dynamic o }\nEncontrado: +", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No deber�a suceder esto");
		}
	}
	
	@Test
	void testExcepcionEsperadaArchivoVac�o() {
		String[] args = {"src/test/resources/sintactico/archivoVac�o.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[1] Error sint�ctico.\nEsperado: class\nEncontrado: EOF", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No deber�a suceder esto");
		}
	}
	
	@Test
	void testExcepcionEsperadaClassSeguidoDeLlave() {
		String[] args = {"src/test/resources/sintactico/classSeguidoDeLlave.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[1] Error sint�ctico.\nEsperado: idClase\nEncontrado: {", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No deber�a suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseSumaDespuesDeIdClase() {
		String[] args = {"src/test/resources/sintactico/claseSumaDespuesDeIdClase.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[1] Error sint�ctico en la declaraci�n de una clase.\nEsperado: extends o {\nEncontrado: +", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No deber�a suceder esto");
		}
	}	
	
	@Test
	void testExcepcionEsperadaExtendsSeguidoDeLlave() {
		String[] args = {"src/test/resources/sintactico/extendsSeguidoDeLlave.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[1] Error sint�ctico en la declaraci�n de herencia de una clase.\nEsperado: idClase\nEncontrado: {", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No deber�a suceder esto");
		}
	}
	
	@Test
	void testUnaClaseVac�a() {
		String[] args = {"src/test/resources/sintactico/unaClaseVac�a.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.inicial();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico e) {
			fail("No deber�a suceder esto");
		}
	}

	@Test
	void testVariasClasesVac�as() {
		String[] args = {"src/test/resources/sintactico/variasClasesVac�as.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.inicial();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico e) {
			fail("No deber�a suceder esto");
		}
	}
	
	@Test
	void testClaseConExtendsVac�a() {
		String[] args = {"src/test/resources/sintactico/claseConExtendsVac�a.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.inicial();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico e) {
			fail("No deber�a suceder esto");
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
			fail("No deber�a suceder esto");
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
			fail("No deber�a suceder esto");
		}
	}
	
}
