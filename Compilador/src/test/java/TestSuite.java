package test.java;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@SelectPackages({"test.java"})
@Suite.SuiteClasses({
	AnalizadorLexicoExcepcionesTest.class,
	AnalizadorLexicoPalabrasReservadasTest.class,
	AnalizadorLexicoTokensNoPalabrasReservadasTest.class,
	PrincipalEtapa1Test.class})
public class TestSuite {

}