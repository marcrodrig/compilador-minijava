package main;

import java.io.FileNotFoundException;

import semantico.TablaSimbolos;
import sintactico.AnalizadorSintactico;


/**
 * Clase Principal. M�dulo principal de la Etapa 3.
 * 
 * @author Rodr�guez, Marcelo
 *
 */
public class Principal {
	/**
	 * M�todo principal de la Etapa 3. En args se tendr�n los par�metros de entrada
	 * para la ejecuci�n del Analizador Sint�ctico. Se mostrar� por consola la
	 * salida del an�lisis sint�ctico. El primer argumento si se ingresa lo llamamos
	 * "IN_FILE" IN_FILE debe ser la ruta de un archivo de extensi�n .txt o .java.
	 * 
	 * @param args los par�metros de entrada de la ejecuci�n del Proyecto
	 */
	
	public static TablaSimbolos ts;
	
	public static void main(String[] args) {
		if (args.length > 1 || args.length == 0) {
			String nuevaLinea = System.lineSeparator();
			System.out.println("Cantidad de par�metros inv�lida.");
			System.out.println(
					"Modo de uso: <PROGRAM_NAME> <IN_FILE>" + nuevaLinea + "IN_FILE: archivo extensi�n .java o .txt");
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
						System.out.println("Se complet� el an�lisis sint�ctico.");
					else
						System.out.println("El an�lisis sint�ctico fue exitoso, no se encontraron errores.");
					ts.chequeoDeclaraciones();
					ts.consolidacion();
				} catch (FileNotFoundException e1) {
					System.out.println("El archivo de entrada no existe");
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			} else {
				String nuevaLinea = System.lineSeparator();
				System.out.println("Error en la extensi�n del archivo de entrada.");
				System.out.println("Modo de uso: <PROGRAM_NAME> <IN_FILE>" + nuevaLinea
						+ "IN_FILE: archivo extensi�n .java o .txt");
			}
		}
	}

	/**
	 * @param filename La ruta de un archivo
	 * @return La extensi�n de la ruta del archivo pasada como par�metro
	 */
	private static String getFileExtension(String filename) {
		if (filename.lastIndexOf(".") != -1 && filename.lastIndexOf(".") != 0)
			return filename.substring(filename.lastIndexOf(".") + 1);
		else
			return "";
	}

	/**
	 * @param extensionEntrada String de la extensi�n de un archivo
	 * @return true si extensionEntrada es igual a "txt" o "java", false en caso
	 *         contrario
	 */
	private static boolean validarEntrada(String extensionEntrada) {
		return extensionEntrada.equals("txt") || extensionEntrada.equals("java");
	}

}