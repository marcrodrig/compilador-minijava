package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Clase Principal.
 * Módulo principal de la Etapa 1.
 * @author Rodríguez, Marcelo
 *
 */
public class Principal {
/**
 * Método principal de la Etapa 1.
 * En args se tendrán los parámetros de entrada para la ejecución del Analizador Léxico.
 * Dependiendo de estos argumentos, se mostrará por con consola o en un archivo la salida del análisis léxico.
 * El primer argumento si se ingresa lo llamamos "IN_FILE", y el segundo si se ingresa lo llamamos "OUT_FILE"
 * IN_FILE debe ser la ruta de un archivo de extensión .txt o .java.
 * OUT_FILE debe ser la ruta de un archivo de extensión .txt o .doc.
 * @param args los parámetros de entrada de la ejecución del Proyecto
 */
	public static void main(String[] args) {
		PrintStream consola = System.out;
		if (args.length > 2 || args.length == 0) {
			String nuevaLinea = System.lineSeparator();
			System.out.println("Cantidad de parámetros inválida.");
			System.out.println("Modo de uso: <PROGRAM_NAME> <IN_FILE> [OUT_FILE]" + nuevaLinea + "IN_FILE: archivo extensión .java o .txt" + nuevaLinea + "OUT_FILE: archivo extensión .txt o .doc");
		} else {
			String archivoEntrada = args[0];
			String extensionArchivoEntrada = getFileExtension(archivoEntrada);
			//System.out.println(extensionArchivoEntrada);
			if (validarEntrada(extensionArchivoEntrada)) {
				AnalizadorLexico analizadorLexico;
				boolean continuar = true;
				if (args.length == 2) {
					String archivoSalida = args[1];
					String extensionArchivoSalida = getFileExtension(archivoSalida);
					if (validarSalida(extensionArchivoSalida)) {
						PrintStream salida;
						try {
							salida = new PrintStream(new File(archivoSalida)); // Creo el archivo de salida
							System.setOut(salida);
						} catch (FileNotFoundException e) {
							System.out.println("ERROR: Error en la creación del archivo de salida.");
						}
					} else {
						continuar = false;
						String nuevaLinea = System.lineSeparator();
						System.out.println("Error en la extensión del archivo de salida.");
						System.out.println("Modo de uso: <PROGRAM_NAME> <IN_FILE> [OUT_FILE]" + nuevaLinea + "IN_FILE: archivo extensión .java o .txt" + nuevaLinea + "OUT_FILE: archivo extensión .txt o .doc");
					}
				}
					if (continuar) {
				try {
					analizadorLexico = new AnalizadorLexico(args[0]);
					// Solicito tokens hasta el token = null que representa el fin del archivo (EOF)
					while (analizadorLexico.nextToken()) {
						Token token;
						try {
							token = analizadorLexico.getToken();
							if (token != null) // token == null -> EOF
								System.out.println(token.toString());
						} catch (ExcepcionCaracterInvalido | ExcepcionFormatoCaracter
								| ExcepcionFormatoAnd | ExcepcionFormatoOr | ExcepcionFormatoComentarioMultilinea | ExcepcionFormatoString e) {
							System.out.println(e.toString());
						}
					}
				} catch (FileNotFoundException e1) {
					if (args.length == 2) {
						System.setOut(consola);
					}
					System.out.println("El archivo de entrada no existe");
				}
				}
			} else {
				String nuevaLinea = System.lineSeparator();
				System.out.println("Error en la extensión del archivo de entrada.");
				System.out.println("Modo de uso: <PROGRAM_NAME> <IN_FILE> [OUT_FILE]" + nuevaLinea + "IN_FILE: archivo extensión .java o .txt" + nuevaLinea + "OUT_FILE: archivo extensión .txt o .doc");

			
				}
		}
		if (args.length == 2) {
			System.setOut(consola);
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
	 * @return true si extensionEntrada es igual a "txt" o "java", false en caso contrario
	 */
	private static boolean validarEntrada(String extensionEntrada) {
		return extensionEntrada.equals("txt") || extensionEntrada.equals("java");
	}

	/**
	 * @param extensionSalida String de la extensión de un archivo
	 * @return true si extensionSalida es igual a "txt" o "doc", false en caso contrario
	 */
	private static boolean validarSalida(String extensionSalida) {
		return extensionSalida.equals("txt") || extensionSalida.equals("doc");
	}

}