package main;

import java.io.FileNotFoundException;

import semantico.TablaSimbolos;
import sintactico.AnalizadorSintactico;


/**
 * Clase Principal. Módulo principal de la Etapa 3.
 * 
 * @author Rodríguez, Marcelo
 *
 */
public class Principal {
	/**
	 * Método principal de la Etapa 3. En args se tendrán los parámetros de entrada
	 * para la ejecución del Analizador Sintáctico. Se mostrará por consola la
	 * salida del análisis sintáctico. El primer argumento si se ingresa lo llamamos
	 * "IN_FILE" IN_FILE debe ser la ruta de un archivo de extensión .txt o .java.
	 * 
	 * @param args los parámetros de entrada de la ejecución del Proyecto
	 */
	
	public static TablaSimbolos ts;
	
	public static void main(String[] args) {
		if (args.length > 1 || args.length == 0) {
			String nuevaLinea = System.lineSeparator();
			System.out.println("Cantidad de parámetros inválida.");
			System.out.println(
					"Modo de uso: <PROGRAM_NAME> <IN_FILE>" + nuevaLinea + "IN_FILE: archivo extensión .java o .txt");
		} else {
			String archivoEntrada = args[0];
			String extensionArchivoEntrada = getFileExtension(archivoEntrada);
			if (validarEntrada(extensionArchivoEntrada)) {
				AnalizadorSintactico analizadorSintactico;
				try {
					ts = TablaSimbolos.getInstance();
					analizadorSintactico = new AnalizadorSintactico(args[0]);
					boolean modoPanico = analizadorSintactico.start();
					if (modoPanico)
						System.out.println("Se completó el análisis sintáctico.");
					else
						System.out.println("El análisis sintáctico fue exitoso, no se encontraron errores.");
					ts.chequeoDeclaraciones();
					ts.consolidacion();
				} catch (FileNotFoundException e1) {
					System.out.println("El archivo de entrada no existe");
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			} else {
				String nuevaLinea = System.lineSeparator();
				System.out.println("Error en la extensión del archivo de entrada.");
				System.out.println("Modo de uso: <PROGRAM_NAME> <IN_FILE>" + nuevaLinea
						+ "IN_FILE: archivo extensión .java o .txt");
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

}