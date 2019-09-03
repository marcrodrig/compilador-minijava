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

import main.PrincipalEtapa1;

public class PrincipalTest {
	
	@Rule
    public TestCasePrinterRule pr = new TestCasePrinterRule(System.out);

	@Test
	public void testSinIngresarArchivoEntrada() {
	    String[] args = {};
	    PrincipalEtapa1.main(args);
	}
	
	@Test
	public void testSinIngresarArchivoEntradaContenido() {
		PrintStream consola = System.out;
		String pathSalida = "src/test/resources/SalidaTestSinIngresarArchivoEntradaContenido.txt";
		String pathSalidaEsperada = "src/test/resources/salida1Esperada.txt";
		PrintStream salida;
		try {
			salida = new PrintStream(new File(pathSalida)); // Creo el archivo de salida
			System.setOut(salida);
	    String[] args = {};
	    PrincipalEtapa1.main(args);
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
	    PrincipalEtapa1.main(args);
	}
	
	@Test
	public void testConArchivoEntradaInexistenteContenido()  {
		PrintStream consola = System.out;
		String pathSalida = "src/test/resources/SalidaTestConArchivoEntradaInexistenteContenido.txt";
		String pathSalidaEsperada = "src/test/resources/salida2Esperada.txt";
		PrintStream salida;
		try {
			salida = new PrintStream(new File(pathSalida)); // Creo el archivo de salida
			System.setOut(salida);
		    String[] args = {"/NoExiste.txt"};
		    PrincipalEtapa1.main(args);
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
	    PrincipalEtapa1.main(args);
	}
	
	@Test
	public void testArchivoEntradaSinExtensionContenido()  {
		PrintStream consola = System.out;
		String pathSalida = "src/test/resources/SalidaTestArchivoEntradaSinExtensionContenido.txt";
		String pathSalidaEsperada = "src/test/resources/salida4Esperada.txt";
		PrintStream salida;
		try {
			salida = new PrintStream(new File(pathSalida)); // Creo el archivo de salida
			System.setOut(salida);
		    String[] args = {"/sinextension"};
		    PrincipalEtapa1.main(args);
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
	    String[] args = {"src/test/resources/TestSuma.txt"};
	    PrincipalEtapa1.main(args);
	}
	
	@Test
	public void testSalidaConsolaContenido() {
		PrintStream consola = System.out;
		String pathSalida = "src/test/resources/SalidaTestSalidaConsolaContenido.txt";
		String pathSalidaEsperada = "src/test/resources/salida5Esperada.txt";
		PrintStream salida;
		try {
			salida = new PrintStream(new File(pathSalida)); // Creo el archivo de salida
			System.setOut(salida);
		    String[] args = {"src/test/resources/TestSuma.txt"};
		    PrincipalEtapa1.main(args);
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
		String pathSalida = "src/test/resources/SalidaTestSalidaArchivo.txt";
		String pathSalidaEsperada = "src/test/resources/salida6Esperada.txt";
		PrintStream salida;
		System.out.println("Archivo de salida en: " + pathSalida);
		try {
			salida = new PrintStream(new File(pathSalida)); // Creo el archivo de salida
			System.setOut(salida);
	    String[] args = {"src/test/resources/TestSuma.txt",pathSalida};
	    PrincipalEtapa1.main(args);
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
	    String[] args = {"src/test/resources/TestSuma.txt",archivoSalida};
	    PrincipalEtapa1.main(args);
	}	
	
	@Test
	public void testArchivoSalidaSinExtensionContenido()  {
		PrintStream consola = System.out;
		String pathSalida = "src/test/resources/SalidaTestArchivoSalidaSinExtensionContenido.txt";
		String pathSalidaEsperada = "src/test/resources/salida7Esperada.txt";
		PrintStream salida;
		System.out.println("Archivo de salida en: " + pathSalida);
		try {
			salida = new PrintStream(new File(pathSalida)); // Creo el archivo de salida
			System.setOut(salida);
			String archivoSalida = "C:/sinextension";
		    String[] args = {"src/test/resources/TestSuma.txt",archivoSalida};
		    PrincipalEtapa1.main(args);
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
	    String[] args = {"src/test/resources/TestSuma.txt",archivoSalida};
	    PrincipalEtapa1.main(args);
	}
	
	@Test
	public void testSalidaArchivoInvalidoContenido()  {
		PrintStream consola = System.out;
		String pathSalida = "src/test/resources/SalidaTestSalidaArchivoInvalidoContenido.txt";
		String pathSalidaEsperada = "src/test/resources/salida8Esperada.txt";
		PrintStream salida;
		System.out.println("Archivo de salida en: " + pathSalida);
		try {
			salida = new PrintStream(new File(pathSalida)); // Creo el archivo de salida
			System.setOut(salida);
			String archivoSalida = "C:/out:salida.txt";
		    String[] args = {"src/test/resources/TestSuma.txt",archivoSalida};
		    PrincipalEtapa1.main(args);
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
		String pathSalida = "src/test/resources/SalidaTestArchivoSalidaConErrorLexicoDespuesDeTokenValidoContenido.txt";
		String pathSalidaEsperada = "src/test/resources/salida9Esperada.txt";
		PrintStream salida;
		System.out.println("Archivo de salida en: " + pathSalida);
		try {
			salida = new PrintStream(new File(pathSalida)); // Creo el archivo de salida
			System.setOut(salida);
			String[] args = {"src/test/resources/TestCaracterInvalido.txt",pathSalida};
		    PrincipalEtapa1.main(args);
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
		String[] args = {"src/test/resources/TestClaseCompleta.txt"};
	    PrincipalEtapa1.main(args);
	}
	
	@Test
	public void testClaseCompletaContenido()  {
		System.out.println("Archivo de salida en: src/test/resources/salida3.txt");
		PrintStream consola = System.out;
		String pathSalida = "src/test/resources/SalidaTestClaseCompletaContenido.txt";
		String pathSalidaEsperada = "src/test/resources/salida3Esperada.txt";
		PrintStream salida;
		try {
			salida = new PrintStream(new File(pathSalida)); // Creo el archivo de salida
			System.setOut(salida);
			String[] args = {"src/test/resources/TestClaseCompleta.txt"};
		    PrincipalEtapa1.main(args);
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
		String[] args = {"src/test/resources/TestRapido.txt"};
		PrincipalEtapa1.main(args);
	}
	
}