package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

public class Principal {

	public static void main(String[] args) {
		PrintStream consola = System.out;
		if (args.length > 2 || args.length == 0) {
			String nuevaLinea = System.lineSeparator();
			System.out.println("Cantidad de parámetros inválida." + nuevaLinea + "Modo de uso: <PROGRAM_NAME> <IN_FILE> [OUT_FILE]");
		} else if (args.length == 2) {
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
				System.out.println("Modo de uso: <PROGRAM_NAME> <IN_FILE> [OUT_FILE]\n"
						+ "IN_FILE: archivo extensión .java o .txt\n"
						+ "OUT_FILE: archivo extensión .txt, .doc o .docx");
			}
		}
		if (args.length > 0) {
			String archivoEntrada = args[0];
			String extensionArchivoEntrada = getFileExtension(archivoEntrada);
			if (validarEntrada(extensionArchivoEntrada)) {
				AnalizadorLexico analizadorLexico;
				try {
					analizadorLexico = new AnalizadorLexico(args[0]);
					// Solicito tokens hasta el token = null que representa el fin del archivo (EOF)
					while (analizadorLexico.nextToken()) {
						Token token;
						try {
							token = analizadorLexico.getToken();
							if (token != null) // token == null -> EOF
								System.out.println(token.toString());
						} catch (IOException | ExcepcionCaracterInvalido | ExcepcionFormatoCaracter
								| ExcepcionFormatoAnd | ExcepcionFormatoOr | ExcepcionFormatoComentarioMultilinea | ExcepcionFormatoString e) {
							System.out.println(e.toString());
						}
					}
				} catch (FileNotFoundException e1) {
					System.out.println("El archivo de entrada no existe");
				}
			} else {
				System.out.println("Modo de uso: <PROGRAM_NAME> <IN_FILE> [OUT_FILE]\n"
						+ "IN_FILE: archivo extensión .java o .txt\n"
						+ "OUT_FILE: archivo extensión .txt, .doc o .docx");
			}
		}
		if (args.length == 2) {
			System.setOut(consola);
		}
	}

	private static String getFileExtension(String filename) {
		if (filename.lastIndexOf(".") != -1 && filename.lastIndexOf(".") != 0)
			return filename.substring(filename.lastIndexOf(".") + 1);
		else
			return "";
	}

	private static boolean validarEntrada(String extensionEntrada) {
		return extensionEntrada.equals("txt") || extensionEntrada.equals("java");
	}

	private static boolean validarSalida(String extensionSalida) {
		return extensionSalida.equals("txt") || extensionSalida.equals("doc") || extensionSalida.equals("docx");
	}

}