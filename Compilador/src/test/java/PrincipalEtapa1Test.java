package test.java;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import org.junit.Rule;
import org.junit.Test;

import main.Principal;

public class PrincipalEtapa1Test {
	
	@Rule
    public TestCasePrinterRule pr = new TestCasePrinterRule(System.out);

	@Test
	public void testSinIngresarArchivoEntrada() {
	    String[] args = {};
	    Principal.main(args);
	}
	
	@Test
	public void testSinIngresarArchivoEntradaContenido() {
		PrintStream consola = System.out;
		String pathSalida = "src/test/resources/lexico/SalidaTestSinIngresarArchivoEntradaContenido.txt";
		String pathSalidaEsperada = "src/test/resources/lexico/salida1Esperada.txt";
		PrintStream salida;
		try {
			salida = new PrintStream(new File(pathSalida)); // Creo el archivo de salida
			System.setOut(salida);
	    String[] args = {};
	    Principal.main(args);
	    System.setOut(consola);
		}catch (FileNotFoundException e) {
			System.out.println("Error en la creación del archivo de salida.");
		}
	    try {
			assertTrue(mismoContenidoArchivosTexto(pathSalidaEsperada, pathSalida));
		} catch (IOException e) {
			// ignorar
			fail("No debería suceder esto");
		}
	}
	
	@Test
	public void testConArchivoEntradaInexistente()  {
	    String[] args = {"/NoExiste.txt"};
	    Principal.main(args);
	}
	
	@Test
	public void testConArchivoEntradaInexistenteContenido()  {
		PrintStream consola = System.out;
		String pathSalida = "src/test/resources/lexico/SalidaTestConArchivoEntradaInexistenteContenido.txt";
		String pathSalidaEsperada = "src/test/resources/lexico/salida2Esperada.txt";
		PrintStream salida;
		try {
			salida = new PrintStream(new File(pathSalida)); // Creo el archivo de salida
			System.setOut(salida);
		    String[] args = {"/NoExiste.txt"};
		    Principal.main(args);
		    System.setOut(consola);
		}catch (FileNotFoundException e) {
			System.out.println("Error en la creación del archivo de salida.");
		}
	    try {
			assertTrue(mismoContenidoArchivosTexto(pathSalidaEsperada, pathSalida));
		} catch (IOException e) {
			// ignorar
			fail("No debería suceder esto");
		}
	}
	
	@Test
	public void testArchivoEntradaSinExtension()  {
	    String[] args = {"/sinextension"};
	    Principal.main(args);
	}
	
	@Test
	public void testArchivoEntradaSinExtensionContenido()  {
		PrintStream consola = System.out;
		String pathSalida = "src/test/resources/lexico/SalidaTestArchivoEntradaSinExtensionContenido.txt";
		String pathSalidaEsperada = "src/test/resources/lexico/salida4Esperada.txt";
		PrintStream salida;
		try {
			salida = new PrintStream(new File(pathSalida)); // Creo el archivo de salida
			System.setOut(salida);
		    String[] args = {"/sinextension"};
		    Principal.main(args);
		    System.setOut(consola);
			}catch (FileNotFoundException e) {
				System.out.println("Error en la creación del archivo de salida.");
			}
		    try {
				assertTrue(mismoContenidoArchivosTexto(pathSalidaEsperada, pathSalida));
			} catch (IOException e) {
				// ignorar
				fail("No debería suceder esto");
			}
	}
	
	@Test
	public void testSalidaConsola() {
	    String[] args = {"src/test/resources/lexico/TestSuma.txt"};
	    Principal.main(args);
	}
	
	@Test
	public void testSalidaConsolaContenido() {
		PrintStream consola = System.out;
		String pathSalida = "src/test/resources/lexico/SalidaTestSalidaConsolaContenido.txt";
		String pathSalidaEsperada = "src/test/resources/lexico/salida5Esperada.txt";
		PrintStream salida;
		try {
			salida = new PrintStream(new File(pathSalida)); // Creo el archivo de salida
			System.setOut(salida);
		    String[] args = {"src/test/resources/lexico/TestSuma.txt"};
		    Principal.main(args);
		    System.setOut(consola);
			}catch (FileNotFoundException e) {
				System.out.println("Error en la creación del archivo de salida.");
			}
		    try {
				assertTrue(mismoContenidoArchivosTexto(pathSalidaEsperada, pathSalida));
			} catch (IOException e) {
				// ignorar
				fail("No debería suceder esto");
			}
	}
	
	@Test
	public void testSalidaArchivoContenido()  {
		PrintStream consola = System.out;
		String pathSalida = "src/test/resources/lexico/SalidaTestSalidaArchivo.txt";
		String pathSalidaEsperada = "src/test/resources/lexico/salida6Esperada.txt";
		PrintStream salida;
		System.out.println("Archivo de salida en: " + pathSalida);
		try {
			salida = new PrintStream(new File(pathSalida)); // Creo el archivo de salida
			System.setOut(salida);
	    String[] args = {"src/test/resources/lexico/TestSuma.txt",pathSalida};
	    Principal.main(args);
	    System.setOut(consola);
		}catch (FileNotFoundException e) {
			System.out.println("Error en la creación del archivo de salida.");
		}
	    try {
			assertTrue(mismoContenidoArchivosTexto(pathSalidaEsperada, pathSalida));
		} catch (IOException e) {
			// ignorar
			fail("No debería suceder esto");
		}
	}
	
	@Test
	public void testArchivoSalidaSinExtension()  {
		String archivoSalida = "C:/sinextension";
	    String[] args = {"src/test/resources/lexico/TestSuma.txt",archivoSalida};
	    Principal.main(args);
	}	
	
	@Test
	public void testArchivoSalidaSinExtensionContenido()  {
		PrintStream consola = System.out;
		String pathSalida = "src/test/resources/lexico/SalidaTestArchivoSalidaSinExtensionContenido.txt";
		String pathSalidaEsperada = "src/test/resources/lexico/salida7Esperada.txt";
		PrintStream salida;
		System.out.println("Archivo de salida en: " + pathSalida);
		try {
			salida = new PrintStream(new File(pathSalida)); // Creo el archivo de salida
			System.setOut(salida);
			String archivoSalida = "C:/sinextension";
		    String[] args = {"src/test/resources/lexico/TestSuma.txt",archivoSalida};
		    Principal.main(args);
		    System.setOut(consola);
			}catch (FileNotFoundException e) {
				System.out.println("Error en la creación del archivo de salida.");
			}
		    try {
				assertTrue(mismoContenidoArchivosTexto(pathSalidaEsperada, pathSalida));
			} catch (IOException e) {
				// ignorar
				fail("No debería suceder esto");
			}
	}
	
	@Test
	public void testSalidaArchivoInvalido()  {
		String archivoSalida = "C:/out:salida.txt";
	    String[] args = {"src/test/resources/lexico/TestSuma.txt",archivoSalida};
	    Principal.main(args);
	}
	
	@Test
	public void testSalidaArchivoInvalidoContenido()  {
		PrintStream consola = System.out;
		String pathSalida = "src/test/resources/lexico/SalidaTestSalidaArchivoInvalidoContenido.txt";
		String pathSalidaEsperada = "src/test/resources/lexico/salida8Esperada.txt";
		PrintStream salida;
		System.out.println("Archivo de salida en: " + pathSalida);
		try {
			salida = new PrintStream(new File(pathSalida)); // Creo el archivo de salida
			System.setOut(salida);
			String archivoSalida = "C:/out:salida.txt";
		    String[] args = {"src/test/resources/lexico/TestSuma.txt",archivoSalida};
		    Principal.main(args);
		    System.setOut(consola);
			}catch (FileNotFoundException e) {
				System.out.println("Error en la creación del archivo de salida.");
			}
		    try {
				assertTrue(mismoContenidoArchivosTexto(pathSalidaEsperada, pathSalida));
			} catch (IOException e) {
				// ignorar
				fail("No debería suceder esto");
			}
	}
	
	@Test
	public void testArchivoSalidaConErrorLexicoDespuesDeTokenValidoContenido()  {
		PrintStream consola = System.out;
		String pathSalida = "src/test/resources/lexico/SalidaTestArchivoSalidaConErrorLexicoDespuesDeTokenValidoContenido.txt";
		String pathSalidaEsperada = "src/test/resources/lexico/salida9Esperada.txt";
		PrintStream salida;
		System.out.println("Archivo de salida en: " + pathSalida);
		try {
			salida = new PrintStream(new File(pathSalida)); // Creo el archivo de salida
			System.setOut(salida);
			String[] args = {"src/test/resources/lexico/TestCaracterInvalido.txt",pathSalida};
		    Principal.main(args);
		    System.setOut(consola);
			}catch (FileNotFoundException e) {
				System.out.println("Error en la creación del archivo de salida.");
			}
		    try {
				assertTrue(mismoContenidoArchivosTexto(pathSalidaEsperada, pathSalida));
			} catch (IOException e) {
				// ignorar
				fail("No debería suceder esto");
			}
	}
	
	@Test
	public void testClaseCompletaConsola()  {;
		String[] args = {"src/test/resources/lexico/TestClaseCompleta.txt"};
	    Principal.main(args);
	}
	
	@Test
	public void testClaseCompletaContenido()  {
		System.out.println("Archivo de salida en: src/test/resources/lexico/salida3.txt");
		PrintStream consola = System.out;
		String pathSalida = "src/test/resources/lexico/SalidaTestClaseCompletaContenido.txt";
		String pathSalidaEsperada = "src/test/resources/lexico/salida3Esperada.txt";
		PrintStream salida;
		try {
			salida = new PrintStream(new File(pathSalida)); // Creo el archivo de salida
			System.setOut(salida);
			String[] args = {"src/test/resources/lexico/TestClaseCompleta.txt"};
		    Principal.main(args);
		    System.setOut(consola);
			}catch (FileNotFoundException e) {
				System.out.println("Error en la creación del archivo de salida.");
			}
		    try {
				assertTrue(mismoContenidoArchivosTexto(pathSalidaEsperada, pathSalida));
			} catch (IOException e) {
				// ignorar
				fail("No debería suceder esto");
			}
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
		String[] args = {"src/test/resources/lexico/TestRapido.txt"};
		Principal.main(args);
	}
	
}