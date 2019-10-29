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
	@DisplayName("TEST: Chequeo un m�todo con un return true, v�lido")
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
	@DisplayName("TEST: Chequeo un m�todo con un return con expresi�n vac�a, v�lido")
	void testMetodoReturnExpresionVacia() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoConReturnExpresionVacia.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo resoluci�n de nombre de un m�todo no declarado en la misma clase")
	void testMetodoResolucionNombreInciso1a() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso1a.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo resoluci�n de nombre de un m�todo declarado en la misma clase pero con distinta signatura")
	void testMetodoResolucionNombreInciso1b() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso1b.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo resoluci�n de nombre de una llamada encadenada")
	void testMetodoResolucionNombreInciso2a() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso2a.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo resoluci�n de nombre inciso 3, acceso a variable inv�lido")
	void testMetodoResolucionNombreInciso3a() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso3a.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo resoluci�n de nombre inciso 3, inv�lido llamada encadenada con un m�todo no declarado")
	void testMetodoResolucionNombreInciso3b() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso3b.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo resoluci�n de variable encadenada, inv�lido debe recibir tipo clase")
	void testMetodoResolucionNombreInciso4a() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso4a.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo resoluci�n de variable encadenada, v�lido")
	void testMetodoResolucionNombreInciso4b() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso4b.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo resoluci�n de variable encadenada, inv�lido atributo inexistente")
	void testMetodoResolucionNombreInciso4c() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso4c.txt" };
		Principal.main(args);
	}
	/**
	 * SEGUIR DE AC�
	 */
	@Test
	@DisplayName("TEST: Chequeo resoluci�n de variable encadenada, v�lido")
	void testMetodoResolucionNombreInciso4d() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso4d.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo resoluci�n de variable encadenada, v�lido")
	void testMetodoResolucionNombreInciso4e() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso4e.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo resoluci�n de variable encadenada, v�lido")
	void testMetodoResolucionNombreInciso4f() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso4f.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: TEST: Chequeo resoluci�n de variable encadenada con herencia, v�lido")
	void testMetodoResolucionNombreInciso4g() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso4g.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo resoluci�n constructor clase no declarada, inv�lido")
	void testMetodoResolucionNombreInciso5a() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso5a.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo resoluci�n constructor predefinido, inv�lido")
	void testMetodoResolucionNombreInciso5b() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso5b.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo resoluci�n constructor con par�metro incorrecto, inv�lido")
	void testMetodoResolucionNombreInciso5c() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso5c.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo resoluci�n constructor inexistente, inv�lido")
	void testMetodoResolucionNombreInciso5d() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoResolucionNombreInciso5d.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo condici�n de un if con una expresi�n no booleana, inv�lido")
	void testMetodoConIfExpresionNoBoolean() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoConIfExpresionNoBoolean.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo sentencia then de un if con un m�todo no declarado, inv�lido")
	void testMetodoConIfThenInvalido() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoConIfThenInvalido.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo sentencia else de un if con un m�todo no declarado, inv�lido")
	void testMetodoConIfElseInvalido() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoConIfElseInvalido.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo condici�n de un while con una expresi�n no booleana, inv�lido")
	void testMetodoConWhileExpresionNoBoolean() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoConWhileExpresionNoBoolean.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo sentencia en un while con un m�todo no declarado, inv�lido")
	void testMetodoConWhileSentenciaInvalida() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoConWhileSentenciaInvalida.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo this con un m�todo no declarado, inv�lido")
	void testMetodoConThisInvalido() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoConThisInvalido.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo this con un m�todo declarado, v�lido")
	void testMetodoConThis() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoConThis.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo llamada a m�todo est�tico directamente, inv�lido")
	void testMetodoEstaticoLlamadaDirecta() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoEstaticoLlamadaDirecta.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo llamada a m�todo est�tico con una clase sin declarar, inv�lido")
	void testMetodoEstaticoClaseSinDeclarar() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoEstaticoClaseSinDeclarar.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo llamada a m�todo dynamic como si fuese static, inv�lido")
	void testMetodoEstaticoClaseMetodoDinamico() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoEstaticoClaseMetodoDinamico.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo llamada a m�todo est�tico inexistente, inv�lido")
	void testMetodoEstaticoDistintaAridad() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoEstaticoDistintaAridad.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo llamada a m�todo est�tico con un tipo de argumento incompatible, inv�lido")
	void testMetodoEstaticoInvalido() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoEstaticoInvalido.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo expresi�n parentizada con tipos incompatibles en expresi�n binaria, inv�lido")
	void testMetodoExpresionParentizadaInvalida() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoExpresionParentizadaInvalido.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo declaracion de vars con nombre de variable repetido a un parametro en un m�todo, inv�lido")
	void testMetodoDecVarsLocalesInvalido1() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoDecVarsLocalesInvalido1.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo declaracion de vars con nombre de variable repetido a otra var en un m�todo, inv�lido")
	void testMetodoDecVarsLocalesInvalido2() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoDecVarsLocalesInvalido2.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo declaracion de vars con nombre de variable repetido a un parametro en un constructor, inv�lido")
	void testConstructorDecVarsLocalesInvalido1() {
		String[] args = { "src/test/resources/semantico/ast/unConstructorDecVarsLocalesInvalido1.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo declaracion de vars con nombre de variable repetido a otra var en un constructor, inv�lido")
	void testConstructorDecVarsLocalesInvalido2() {
		String[] args = { "src/test/resources/semantico/ast/unConstructorDecVarsLocalesInvalido2.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un m�todo con una asignacion inline de una variable con tipo incompatibles, inv�lido")
	void testMetodoAsignacionInlineInvalido1() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoAsignacionInlineInvalido1.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un m�todo con una asignacion inline de una variable con tipo incompatibles, inv�lido")
	void testMetodoAsignacionInlineValido() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoAsignacionInlineValido.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo un m�todo con una asignacion inline con una llamada a m�todo en lado izquierdo con tipo incompatibles, inv�lido")
	void testMetodoAsignacionInlineInvalido2() {
		String[] args = { "src/test/resources/semantico/ast/unMetodoAsignacionInlineInvalido2.txt" };
		Principal.main(args);
	}
	
	@Test
	@DisplayName("TEST: Chequeo asignaci�n inline de atributo con tipos incompatibles, inv�lido")
	void testMetodoAsignacionInlineAtrInvalido1() {
		String[] args = { "src/test/resources/semantico/ast/asignacionInlineAtrInvalido1.txt" };
		Principal.main(args);
	}

}
