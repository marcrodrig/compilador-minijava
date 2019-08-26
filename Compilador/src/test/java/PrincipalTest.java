package test.java;

import static org.junit.Assert.assertTrue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.Principal;

public class PrincipalTest {
	
/*	PrintStream consola;
	@Before 
	void antes() {
		consola = System.out;
	}
	@After
	void despues() {
		System.setOut(consola);;
	}*/

	@Test
	public void testSinIngresarArchivoEntrada() throws IOException  {
		PrintStream consola = System.out;
		String pathSalida = "D:/salida1.txt";
		String pathSalidaEsperada = "D:/salida1Esperada.txt";
		PrintStream salida;
		try {
			salida = new PrintStream(new File(pathSalida)); // Creo el archivo de salida
			System.setOut(salida);
		
		//System.out.println("TEST Principal: sin un archivo de entrada");
	    String[] args = {};
	    Principal.main(args);
	    System.setOut(consola);
		}catch (FileNotFoundException e) {
			System.out.println("ERROR: Error en la creación del archivo de salida.");
		}
	    assertTrue(mismoContenidoArchivosTexto(pathSalidaEsperada, pathSalida));
	}
	
	@Test
	public void testConArchivoEntradaInexistente()  {
		//System.out.println("TEST Principal: con un archivo de entrada inexistente");
	    String[] args = {"/NoExiste.txt"};
	    Principal.main(args);
	//    System.out.println();
	}
	
	@Test
	public void testArchivoEntradaSinExtension()  {
	//	System.out.println("TEST Principal: con un archivo de entrada con un caracter no valido");
	    String[] args = {"/sinextension"};
	    Principal.main(args);
//	    System.out.println();
	}
	
	@Test
	public void testSalidaConsola() {
	//    System.out.println("TEST Principal: salida consola del token suma");
	    String[] args = {"src/test/resources/TestSuma.txt"};
	    Principal.main(args);
//	    System.out.println();
	}
	
	@Test
	public void testSalidaArchivo()  {
	//	System.out.println("TEST Principal: salida en archivo D:/out1.txt del token suma");
		String archivoSalida = "D:/out1.txt";
	    String[] args = {"src/test/resources/TestSuma.txt",archivoSalida};
	    Principal.main(args);
	//    System.out.println();
	}
	
	@Test
	public void testArchivoSalidaSinExtension()  {
		System.out.println("TEST Principal: archivo salida sin extension");
		String archivoSalida = "D:/sinextension";
	    String[] args = {"src/test/resources/TestSuma.txt",archivoSalida};
	    Principal.main(args);
	    System.out.println();
	}	
	
	@Test
	public void testSalidaArchivoInvalido()  {
		//System.out.println("TEST Principal: salida en archivo D:/out:salida.txt del token suma");
		String archivoSalida = "D:/out:salida.txt";
	    String[] args = {"src/test/resources/TestSuma.txt",archivoSalida};
	    Principal.main(args);
	  //  System.out.println();
	}
	
	@Test
	public void testArchivoSalidaConErrorLexico()  {
	//	System.out.println("TEST Principal: salida en archivo D:/out2.txt de un error léxico");
		String archivoSalida = "D:/out2.txt";
	    String[] args = {"src/test/resources/TestFormatoCaracterInvalido.txt",archivoSalida};
	    Principal.main(args);
	    System.out.println();
	}
	
	@Test
	public void testArchivoSalidaConErrorLexicoDespuesDeTokenValido()  {
		String archivoSalida = "D:/out3.txt";
		String[] args = {"src/test/resources/TestCaracterInvalido.txt",archivoSalida};
	    System.out.println("salida "+archivoSalida);
	    Principal.main(args);
	}
	
	@Test
	public void testClaseCompleta()  {
		System.out.println("TEST Principal: salida en consola de una clase extensa");
		String[] args = {"src/test/resources/TestClaseCompleta.txt"};
	    Principal.main(args);
	    System.out.println();
	}
	
	private boolean mismoContenidoArchivosTexto(String pathArchivo1, String pathArchivo2) throws IOException {
		BufferedReader reader1 = new BufferedReader(new FileReader(pathArchivo1));
		BufferedReader reader2 = new BufferedReader(new FileReader(pathArchivo2));
		String line1 = reader1.readLine();
		String line2 = reader2.readLine();
		boolean areEqual = true;
		while (line1 != null || line2 != null) {
			if (line1 == null || line2 == null) {
				areEqual = false;
				break;
			} else if (!line1.equalsIgnoreCase(line2)) {
				areEqual = false;
				break;
			}
			line1 = reader1.readLine();
			line2 = reader2.readLine();
		}
		reader1.close();
		reader2.close();
		return areEqual;
	}
	
	@Test
	public void testRapido() {
		String[] args = {"src/test/resources/TestRapido.txt"};
		Principal.main(args);
	}
	
}