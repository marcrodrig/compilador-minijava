package test.java;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import main.Principal;

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
/*
	@Test
	void testRapido() {
		String[] args = { "src/test/resources/semantico/ast/rapido.txt" };
		Principal.main(args); 
	}
*/
	@Test
	@DisplayName("TEST: Chequeo un método con un return true, válido")
	void testMetodoReturnLiteral() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoConReturn.txt" };
		Principal.main(args);
	}

	@Test
	@DisplayName("TEST: Chequeo un constructor con un return")
	void testConstructorReturn() {
		String[] args = { "src/test/resources/semantico/ast/unConstructorConReturn.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con un return con expresión vacía, válido")
	void testMetodoReturnExpresionVacia() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoConReturnExpresionVacia.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo resolución de nombre de un método no declarado en la misma clase")
	void testMetodoResolucionNombreInciso1a() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso1a.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo resolución de nombre de un método declarado en la misma clase pero con distinta signatura")
	void testMetodoResolucionNombreInciso1b() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso1b.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo resolución de nombre de una llamada encadenada")
	void testMetodoResolucionNombreInciso2a() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso2a.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo resolución de nombre inciso 3, acceso a variable inválido")
	void testMetodoResolucionNombreInciso3a() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso3a.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo resolución de nombre inciso 3, inválido llamada encadenada con un método no declarado")
	void testMetodoResolucionNombreInciso3b() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso3b.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo resolución de variable encadenada, inválido debe recibir tipo clase")
	void testMetodoResolucionNombreInciso4a() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso4a.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo resolución de variable encadenada, válido")
	void testMetodoResolucionNombreInciso4b() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso4b.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo resolución de variable encadenada, inválido atributo inexistente")
	void testMetodoResolucionNombreInciso4c() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso4c.txt" };
		Principal.main(args);
	}
	/**
	 * SEGUIR DE ACÁ
	 */
	@Test
	@DisplayName("TEST: Chequeo resolución de variable encadenada, válido")
	void testMetodoResolucionNombreInciso4d() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso4d.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo resolución de variable encadenada, válido")
	void testMetodoResolucionNombreInciso4e() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso4e.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo resolución de variable encadenada, válido")
	void testMetodoResolucionNombreInciso4f() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso4f.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: TEST: Chequeo resolución de variable encadenada con herencia, válido")
	void testMetodoResolucionNombreInciso4g() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso4g.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo resolución constructor clase no declarada, inválido")
	void testMetodoResolucionNombreInciso5a() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso5a.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo resolución constructor predefinido, inválido")
	void testMetodoResolucionNombreInciso5b() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso5b.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo resolución constructor con parámetro incorrecto, inválido")
	void testMetodoResolucionNombreInciso5c() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso5c.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo resolución constructor inexistente, inválido")
	void testMetodoResolucionNombreInciso5d() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso5d.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo condición de un if con una expresión no booleana, inválido")
	void testMetodoConIfExpresionNoBoolean() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoConIfExpresionNoBoolean.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo sentencia then de un if con un método no declarado, inválido")
	void testMetodoConIfThenInvalido() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoConIfThenInvalido.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo sentencia else de un if con un método no declarado, inválido")
	void testMetodoConIfElseInvalido() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoConIfElseInvalido.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo condición de un while con una expresión no booleana, inválido")
	void testMetodoConWhileExpresionNoBoolean() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoConWhileExpresionNoBoolean.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo sentencia en un while con un método no declarado, inválido")
	void testMetodoConWhileSentenciaInvalida() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoConWhileSentenciaInvalida.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo this con un método no declarado, inválido")
	void testMetodoConThisInvalido() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoConThisInvalido.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo this con un método declarado, válido")
	void testMetodoConThis() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoConThis.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo llamada a método estático directamente, inválido")
	void testMetodoEstaticoLlamadaDirecta() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoEstaticoLlamadaDirecta.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo llamada a método estático con una clase sin declarar, inválido")
	void testMetodoEstaticoClaseSinDeclarar() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoEstaticoClaseSinDeclarar.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo llamada a método dynamic como si fuese static, inválido")
	void testMetodoEstaticoClaseMetodoDinamico() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoEstaticoClaseMetodoDinamico.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo llamada a método estático inexistente, inválido")
	void testMetodoEstaticoDistintaAridad() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoEstaticoDistintaAridad.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo llamada a método estático con un tipo de argumento incompatible, inválido")
	void testMetodoEstaticoInvalido() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoEstaticoInvalido.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo expresión parentizada con tipos incompatibles en expresión binaria, inválido")
	void testMetodoExpresionParentizadaInvalida() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoExpresionParentizadaInvalido.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo declaracion de vars con nombre de variable repetido a un parametro en un método, inválido")
	void testMetodoDecVarsLocalesInvalido1() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoDecVarsLocalesInvalido1.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo declaracion de vars con nombre de variable repetido a otra var en un método, inválido")
	void testMetodoDecVarsLocalesInvalido2() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoDecVarsLocalesInvalido2.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo declaracion de vars con nombre de variable repetido a un parametro en un constructor, inválido")
	void testConstructorDecVarsLocalesInvalido1() {
		String[] args = { "src/test/resources/semantico/ast/unConstructorDecVarsLocalesInvalido1.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo declaracion de vars con nombre de variable repetido a otra var en un constructor, inválido")
	void testConstructorDecVarsLocalesInvalido2() {
		String[] args = { "src/test/resources/semantico/ast/unConstructorDecVarsLocalesInvalido2.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con una asignacion inline de una variable con tipo incompatibles, inválido")
	void testMetodoAsignacionInlineInvalido1() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoAsignacionInlineInvalido1.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con una asignacion inline de una variable con tipo incompatibles, inválido")
	void testMetodoAsignacionInlineValido() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoAsignacionInlineValido.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un método con una asignacion inline con una llamada a método en lado izquierdo con tipo incompatibles, inválido")
	void testMetodoAsignacionInlineInvalido2() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoAsignacionInlineInvalido2.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo asignación inline de atributo con tipos incompatibles, inválido")
	void testMetodoAsignacionInlineAtrInvalido1() {
		String[] args = { "src/test/resources/semantico/ast/asignacionInlineAtrInvalido1.txt" };
		Principal.main(args);
	}

}
