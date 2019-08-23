package test.java;

import org.junit.jupiter.api.Test;
import main.Principal;

class PrincipalTest {

	@Test
	void testSinIngresarArchivoEntrada()  {
		System.out.println("TEST Principal: sin un archivo de entrada");
	    String[] args = {};
	    Principal.main(args);
	    System.out.println();
	}
	
	@Test
	void testConArchivoEntradaInexistente()  {
		System.out.println("TEST Principal: con un archivo de entrada inexistente");
	    String[] args = {"/NoExiste.txt"};
	    Principal.main(args);
	    System.out.println();
	}
	
	@Test
	public void testSalidaConsola() {
	    System.out.println("TEST Principal: salida consola del token suma");
	    String[] args = {"src/test/resources/TestSuma.txt"};
	    Principal.main(args);
	    System.out.println();
	}
	
	@Test
	void testSalidaArchivo()  {
		System.out.println("TEST Principal: salida en archivo D:/out1.txt del token suma");
		String archivoSalida = "D:/out1.txt";
	    String[] args = {"src/test/resources/TestSuma.txt",archivoSalida};
	    Principal.main(args);
	    System.out.println();
	}
	
	@Test
	void testSalidaArchivoInvalido()  {
		System.out.println("TEST Principal: salida en archivo D:/out:salida.txt del token suma");
		String archivoSalida = "D:/out:salida.txt";
	    String[] args = {"src/test/resources/TestSuma.txt",archivoSalida};
	    Principal.main(args);
	    System.out.println();
	}
	
	@Test
	void testArchivoSalidaConErrorLexico()  {
		System.out.println("TEST Principal: salida en archivo D:/out2.txt de un error léxico");
		String archivoSalida = "D:/out2.txt";
	    String[] args = {"src/test/resources/TestFormatoCaracterInvalido.txt",archivoSalida};
	    Principal.main(args);
	    System.out.println();
	}
	
	@Test
	void testArchivoSalidaConErrorLexicoDespuesDeTokenValido()  {
		String archivoSalida = "D:/out3.txt";
		String[] args = {"src/test/resources/TestCaracterInvalido.txt",archivoSalida};
	    System.out.println("salida "+archivoSalida);
	    Principal.main(args);
	}
	
	@Test
	void testClaseCompleta()  {
		System.out.println("TEST Principal: salida en consola de una clase extensa");
		String[] args = {"src/test/resources/TestClaseCompleta.txt"};
	    Principal.main(args);
	    System.out.println();
	}
	
}