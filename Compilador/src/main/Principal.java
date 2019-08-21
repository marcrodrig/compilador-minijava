package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

public class Principal {

	public static void main(String[] args)  {
		PrintStream consola = System.out;
		if (args.length > 2 || args.length == 0) {
			System.out.println("Cantidad de parámetros inválida.\nModo de uso: <PROGRAM_NAME> <IN_FILE> [OUT_FILE]");
		} else if (args.length == 2) {
			// Creo el archivo de salida, en args[0] está el nombre del archivo
			PrintStream archivoSalida;
			try {
				archivoSalida = new PrintStream(new File(args[1]));
			
			System.setOut(archivoSalida);
			
			
			
			} catch (FileNotFoundException e) {
				System.out.println("ERROR");
			}
		} 
		if (args.length > 0) {
		AnalizadorLexico analizadorLexico;
		try {
			analizadorLexico = new AnalizadorLexico(args[0]);
		
		
		while (analizadorLexico.nextToken()) {
			Token token;
		
				try {
					token = analizadorLexico.getToken();
					if (token != null) // token == null -> EOF
					System.out.println(token.toString());
				} catch (IOException | ExcepcionCaracterInvalido | ExcepcionFormatoCaracter | ExcepcionAnd | ExcepcionOr
						| ExcepcionComentarioMultilinea e) {
					System.out.println(e.toString());
				}

		}
		} catch (FileNotFoundException e1) {
			System.out.println("El archivo de entrada no existe");
			/* VER CASO ARCHIVO DE SALIDA NO ES CONSOLA
			 * 
			 */
		}
		}
		if (args.length == 2) {
			System.setOut(consola);
		}
	}
}