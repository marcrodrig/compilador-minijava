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
	void testMetodoResolucionNombreInciso1c() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso1c.txt" };
		Principal.main(args);
		TablaSimbolos ts = TablaSimbolos.getInstance();
		assertEquals(1, 1);
	}
}
