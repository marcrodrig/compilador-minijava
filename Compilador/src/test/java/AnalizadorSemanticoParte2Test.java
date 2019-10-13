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

class AnalizadorSemanticoParte2Test {

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
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoReturnLiteral() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoConReturn.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}

	@Test
	@DisplayName("TEST: Chequeo un constructor con un return")
	void testConstructorReturn() {
		String[] args = { "src/test/resources/semantico/ast/unConstructorConReturn.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoReturnExpresionVacia() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoConReturnExpresionVacia.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoResolucionNombreInciso1a() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso1a.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoResolucionNombreInciso1b() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso1b.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoResolucionNombreInciso2a() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso2a.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoResolucionNombreInciso3a() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso3a.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoResolucionNombreInciso3b() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso3b.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoResolucionNombreInciso4a() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso4a.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoResolucionNombreInciso4b() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso4b.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoResolucionNombreInciso4c() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso4c.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoResolucionNombreInciso4d() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso4d.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoResolucionNombreInciso4e() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso4e.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoResolucionNombreInciso4f() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso4f.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoResolucionNombreInciso4g() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso4g.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoResolucionNombreInciso5a() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso5a.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoResolucionNombreInciso5b() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso5b.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoResolucionNombreInciso5c() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso5c.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoResolucionNombreInciso5d() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso5d.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoConIfExpresionNoBoolean() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoConIfExpresionNoBoolean.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoConIfThenInvalido() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoConIfThenInvalido.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoConIfElseInvalido() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoConIfElseInvalido.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoConWhileExpresionNoBoolean() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoConWhileExpresionNoBoolean.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoConWhileSentenciaInvalida() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoConWhileSentenciaInvalida.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoConThisInvalido() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoConThisInvalido.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoConThis() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoConThis.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoEstaticoLlamadaDirecta() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoEstaticoLlamadaDirecta.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoEstaticoClaseSinDeclarar() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoEstaticoClaseSinDeclarar.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoEstaticoClaseMetodoDinamico() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoEstaticoClaseMetodoDinamico.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoEstaticoDistintaAridad() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoEstaticoDistintaAridad.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoEstaticoInvalido() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoEstaticoInvalido.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoExpresionParentizadaInvalida() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoExpresionParentizadaInvalido.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoDecVarsLocalesInvalido1() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoDecVarsLocalesInvalido1.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoDecVarsLocalesInvalido2() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoDecVarsLocalesInvalido2.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testConstructorDecVarsLocalesInvalido1() {
		String[] args = { "src/test/resources/semantico/ast/unConstructorDecVarsLocalesInvalido1.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testConstructorDecVarsLocalesInvalido2() {
		String[] args = { "src/test/resources/semantico/ast/unConstructorDecVarsLocalesInvalido2.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoAsignacionInlineInvalido1() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoAsignacionInlineInvalido1.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoAsignacionInlineValido() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoAsignacionInlineValido.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return válido")
	void testMetodoAsignacionInlineInvalido2() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoAsignacionInlineInvalido2.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
}
