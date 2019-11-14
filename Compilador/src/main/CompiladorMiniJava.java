package main;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import gc.GeneradorCodigo;
import semantico.TablaSimbolos;
import sintactico.AnalizadorSintactico;

/**
 * Clase CompiladorMiniJava. M�dulo principal de la Etapa 5.
 * 
 * @author Rodr�guez, Marcelo
 *
 */
public class CompiladorMiniJava {
	
	public static TablaSimbolos tablaSimbolos;
	
	public static void main(String[] args) {
		if (args.length > 2 || args.length == 0) {
			String nuevaLinea = System.lineSeparator();
			System.out.println("Cantidad de par�metros inv�lida.");
			System.out.println("Modo de uso: CompiladorMiniJava <IN_FILE> <OUT_FILE>" + nuevaLinea
					+ "IN_FILE: archivo fuente MiniJava a compilar (extensi�n .java o .txt)" + nuevaLinea
					+ "OUT_FILE: Par�metro Opcional. Ruta de archivo de generaci�n de c�digo intermedio (extensi�n .ceaism)");
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
							System.out.println("Se complet� el an�lisis, no se genera c�digo intermedio porque se recuper� de errores sint�cticos y/o sem�nticos.");
						else {
							System.out.println("El an�lisis fue exitoso, todas las entidades han sido correctamente declaradas.");
							tablaSimbolos.generar();
							System.out.println("Generaci�n de c�digo intermedio: " + archivoSalida);
						}
					} catch (FileNotFoundException e1) {
						System.out.println("El archivo de entrada no existe");
					} catch (Exception e) {
						System.out.println(e.toString());
					}
				} else {
					String nuevaLinea = System.lineSeparator();
					System.out.println("Error en la extensi�n del archivo de entrada.");
					System.out.println("Modo de uso: CompiladorMiniJava <IN_FILE> <OUT_FILE>" + nuevaLinea
							+ "IN_FILE: archivo extensi�n .java o .txt" + nuevaLinea
							+ "OUT_FILE: Par�metro Opcional. Ruta de archivo de generaci�n de c�digo intermedio (extensi�n .ceaism)");
				}
			} else {
				System.out.println("Error en la extensi�n del archivo de generaci�n de c�digo intermedio.");
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

	/**
	 * @param extensionSalida String de la extensi�n de un archivo
	 * @return true si extensionSalida es igual a "ceaism", false en caso
	 *         contrario
	 */
	private static boolean validarSalida(String extensionSalida) {
		return extensionSalida.equals("ceiasm");
	}

}