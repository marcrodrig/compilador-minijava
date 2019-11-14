package main;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import gc.GeneradorCodigo;
import semantico.TablaSimbolos;
import sintactico.AnalizadorSintactico;

/**
 * Clase CompiladorMiniJava. Módulo principal de la Etapa 5.
 * 
 * @author Rodríguez, Marcelo
 *
 */
public class CompiladorMiniJava {
	
	public static TablaSimbolos tablaSimbolos;
	
	public static void main(String[] args) {
		if (args.length > 2 || args.length == 0) {
			String nuevaLinea = System.lineSeparator();
			System.out.println("Cantidad de parámetros inválida.");
			System.out.println("Modo de uso: CompiladorMiniJava <IN_FILE> <OUT_FILE>" + nuevaLinea
					+ "IN_FILE: archivo fuente MiniJava a compilar (extensión .java o .txt)" + nuevaLinea
					+ "OUT_FILE: Parámetro Opcional. Ruta de archivo de generación de código intermedio (extensión .ceaism)");
		} else {
			String archivoEntrada = args[0];
			String extensionArchivoEntrada = getFileExtension(archivoEntrada);
			String archivoSalida = null;
			if (args.length == 2)
				archivoSalida = args[1];
			else {
		        Path path = Paths.get(archivoEntrada); 
		        Path fileName = path.getFileName(); 
				String[] archivo = fileName.toString().split("\\.");
				archivoSalida = System.getProperty("user.dir") + "/" + archivo[0] + ".ceiasm";
			}
			String extensionArchivoSalida = getFileExtension(archivoSalida);
			if (validarSalida(extensionArchivoSalida)) {
				GeneradorCodigo.archivoSalida = archivoSalida;
				if (validarEntrada(extensionArchivoEntrada)) {
					AnalizadorSintactico analizadorSintactico;
					try {
						tablaSimbolos = TablaSimbolos.getInstance();
						analizadorSintactico = new AnalizadorSintactico(args[0]);
						analizadorSintactico.start();
						tablaSimbolos.controlesSemanticos();
						if (tablaSimbolos.recuperacionSintactica() || tablaSimbolos.recuperacionSemantica())
							System.out.println("Se completó el análisis, no se genera código intermedio porque se recuperó de errores sintácticos y/o semánticos.");
						else {
							System.out.println("El análisis fue exitoso, todas las entidades han sido correctamente declaradas.");
							tablaSimbolos.generar();
							System.out.println("Generación de código intermedio: " + archivoSalida);
						}
					} catch (FileNotFoundException e1) {
						System.out.println("El archivo de entrada no existe");
					} catch (Exception e) {
						System.out.println(e.toString());
					}
				} else {
					String nuevaLinea = System.lineSeparator();
					System.out.println("Error en la extensión del archivo de entrada.");
					System.out.println("Modo de uso: CompiladorMiniJava <IN_FILE> <OUT_FILE>" + nuevaLinea
							+ "IN_FILE: archivo extensión .java o .txt" + nuevaLinea
							+ "OUT_FILE: Parámetro Opcional. Ruta de archivo de generación de código intermedio (extensión .ceaism)");
				}
			} else {
				System.out.println("Error en la extensión del archivo de generación de código intermedio.");
			}
		}
	}

	/**
	 * @param filename La ruta de un archivo
	 * @return La extensión de la ruta del archivo pasada como parámetro
	 */
	private static String getFileExtension(String filename) {
		if (filename.lastIndexOf(".") != -1 && filename.lastIndexOf(".") != 0)
			return filename.substring(filename.lastIndexOf(".") + 1);
		else
			return "";
	}

	/**
	 * @param extensionEntrada String de la extensión de un archivo
	 * @return true si extensionEntrada es igual a "txt" o "java", false en caso
	 *         contrario
	 */
	private static boolean validarEntrada(String extensionEntrada) {
		return extensionEntrada.equals("txt") || extensionEntrada.equals("java");
	}

	/**
	 * @param extensionSalida String de la extensión de un archivo
	 * @return true si extensionSalida es igual a "ceaism", false en caso
	 *         contrario
	 */
	private static boolean validarSalida(String extensionSalida) {
		return extensionSalida.equals("ceiasm");
	}

}