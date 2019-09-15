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
	/*
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
	
	@Test
	void testSentenciasSimpleParentizadasDesparentizadasIdClaseAlPrincipio() {
		String[] args = {"src/test/resources/sintactico/sentenciasSimpleParentizadasDesparentizadasIdClaseAlPrincipio.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.inicial();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico e) {
			fail("No deber�a suceder esto");
		}
	}
	
	@Test
	void testSentenciasSimpleParentizadasDesparentizadasIdMetVarAlPrincipio() {
		String[] args = {"src/test/resources/sintactico/sentenciasSimpleParentizadasDesparentizadasIdMetVarAlPrincipio.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.inicial();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico e) {
			fail("No deber�a suceder esto");
		}
	}
	
	@Test
	void testExcepcionEsperadaClaseConCtorConBloqueSinSentencias() {
		String[] args = {"src/test/resources/sintactico/claseConCtorConBloqueSinSentencias.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[2] Error sint�ctico. Se espera la declaraci�n de una sentencia o }.\n" + 
					"Esperado: idMetVar, (, boolean, char, int, String, idClase, if, while, {, return, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this, } o ;\n" + 
					"Encontrado: EOF", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No deber�a suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConCtorSinBloque() {
		String[] args = {"src/test/resources/sintactico/claseConCtorSinBloque.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[3] Error sint�ctico.\n" + 
					"Esperado: {\n" + 
					"Encontrado: }", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No deber�a suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConCtorSoloIdClaseParentesisApertura() {
		String[] args = {"src/test/resources/sintactico/claseConCtorSoloIdClaseParentesisApertura.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[3] Error sint�ctico en argumentos formales.\n" + 
					"Esperado: ) o tipo\n" + 
					"Encontrado: }", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No deber�a suceder esto");
		}
	}
	
	@Test
	void testExcepcionEsperadaClaseConCtorSoloIdClase() {
		String[] args = {"src/test/resources/sintactico/claseConCtorSoloIdClase.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[3] Error sint�ctico en la declaraci�n de argumentos formales.\n" + 
					"Esperado: (\n" + 
					"Encontrado: }", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No deber�a suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConCtorConUnaSolaSentenciaSinLlaveCierre() {
		String[] args = {"src/test/resources/sintactico/claseConCtorConUnaSolaSentenciaSinLlaveCierre.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[3] Error sint�ctico. Se espera la declaraci�n de una sentencia o }.\n" + 
					"Esperado: idMetVar, (, boolean, char, int, String, idClase, if, while, {, return, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this, } o ;\n" + 
					"Encontrado: EOF", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No deber�a suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConMetodoConUnaSolaSentenciaSinLlaveCierre() {
		String[] args = {"src/test/resources/sintactico/claseConMetodoConUnaSolaSentenciaSinLlaveCierre.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[3] Error sint�ctico. Se espera la declaraci�n de una sentencia o }.\n" + 
					"Esperado: idMetVar, (, boolean, char, int, String, idClase, if, while, {, return, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this, } o ;\n" + 
					"Encontrado: EOF", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No deber�a suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConMetodoConBloqueSinSentencias() {
		String[] args = {"src/test/resources/sintactico/claseConMetodoConBloqueSinSentencias.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[2] Error sint�ctico. Se espera la declaraci�n de una sentencia o }.\n" + 
					"Esperado: idMetVar, (, boolean, char, int, String, idClase, if, while, {, return, new, +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, this, } o ;\n" + 
					"Encontrado: EOF", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No deber�a suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConMetodoSinBloque() {
		String[] args = {"src/test/resources/sintactico/claseConMetodoSinBloque.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[3] Error sint�ctico.\n" + 
					"Esperado: {\n" + 
					"Encontrado: }", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No deber�a suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConMetodoSoloFormaMetodoTipoIdMetVar() {
		String[] args = {"src/test/resources/sintactico/claseConMetodoSoloFormaMetodoTipoIdMetVar.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[3] Error sint�ctico en la declaraci�n de argumentos formales.\n" + 
					"Esperado: (\n" + 
					"Encontrado: }", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No deber�a suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConMetodoConArgsFormalesSinParentesisCierre() {
		String[] args = {"src/test/resources/sintactico/claseConMetodoConArgsFormalesSinParentesisCierre.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[3] Error sint�ctico en los argumentos formales.\n" + 
					"Esperado: ) o ,\n" + 
					"Encontrado: }", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No deber�a suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConMetodoConArgsFormalesSoloTipo() {
		String[] args = {"src/test/resources/sintactico/claseConMetodoConArgsFormalesSoloTipo.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[3] Error sint�ctico.\n" + 
					"Esperado: idMetVar\n" + 
					"Encontrado: }", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No deber�a suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConMetodoSoloFormaMetodoTipoIdMetVarParentesisApertura() {
		String[] args = {"src/test/resources/sintactico/claseConMetodoSoloFormaMetodoTipoIdMetVarParentesisApertura.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[3] Error sint�ctico en argumentos formales.\n" + 
					"Esperado: ) o tipo\n" + 
					"Encontrado: }", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No deber�a suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConMetodoSoloFormaMetodoYTipo() {
		String[] args = {"src/test/resources/sintactico/claseConMetodoSoloFormaMetodoYTipo.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[3] Error sint�ctico.\n" + 
					"Esperado: idMetVar\n" + 
					"Encontrado: }", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No deber�a suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConMetodoSoloFormaMetodoYFinal() {
		String[] args = {"src/test/resources/sintactico/claseConMetodoSoloFormaMetodoYFinal.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[3] Error sint�ctico en tipo m�todo.\n" + 
					"Esperado: idClase, boolean, char, int, String o void\n" + 
					"Encontrado: }", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No deber�a suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConDeclaracionAtributoConPuntoComaFaltante() {
		String[] args = {"src/test/resources/sintactico/claseConDeclaracionAtributoConPuntoComaFaltante.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.inicial();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico e) {
			fail("No deber�a suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConMetodoSoloFormaMetodo() {
		String[] args = {"src/test/resources/sintactico/claseConMetodoSoloFormaMetodo.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[3] Error sint�ctico en m�todo.\n" + 
					"Esperado: final o tipo de m�todo\n" + 
					"Encontrado: }", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No deber�a suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConAsignacionAtributoConPuntoComaFaltante() {
		String[] args = {"src/test/resources/sintactico/claseConAsignacionAtributoConPuntoComaFaltante.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			analizadorSintactico.inicial();
		} catch (FileNotFoundException | ExcepcionLexico | ExcepcionSintactico e) {
			fail("No deber�a suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConAsignacionAtributoSinExpresion() {
		String[] args = {"src/test/resources/sintactico/claseConAsignacionAtributoSinExpresion.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[3] Error sint�ctico en expresi�n.\n" + 
					"Esperado: +, -, !, null, true, false, intLiteral, charLiteral, stringLiteral, (, this, idMetVar, idClase o new\n" + 
					"Encontrado: }", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No deber�a suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConDeclaracionAtributoSoloVisibilidadYTipo() {
		String[] args = {"src/test/resources/sintactico/claseConDeclaracionAtributoSoloVisibilidadYTipo.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[3] Error sint�ctico.\n" + 
					"Esperado: idMetVar\n" + 
					"Encontrado: }", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No deber�a suceder esto");
		}
	}

	@Test
	void testExcepcionEsperadaClaseConDeclaracionAtributoSoloVisibilidad() {
		String[] args = {"src/test/resources/sintactico/claseConDeclaracionAtributoSoloVisibilidad.txt"};
		AnalizadorSintactico analizadorSintactico;
		try {
			analizadorSintactico = new AnalizadorSintactico(args[0]);
			ExcepcionSintactico e = assertThrows(ExcepcionSintactico.class, () -> { analizadorSintactico.inicial();});
			assertEquals("[3] Error sint�ctico en tipo.\n" + 
					"Esperado: idClase, boolean, char, int o String\n" + 
					"Encontrado: }", e.toString());
		} catch (FileNotFoundException | ExcepcionLexico e1) {
			fail("No deber�a suceder esto");
		}
	}*/
}
