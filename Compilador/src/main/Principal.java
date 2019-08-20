package main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class Principal {

	public static void main(String[] args) throws Exception {
		if (args.length > 2) {
			System.out.println("Cantidad de par·metros inv·lida.\nModo de uso: <PROGRAM_NAME> <IN_FILE> [OUT_FILE]\n");
		} else if (args.length == 2) {
			// Creo el archivo de salida, en args[0] est√° el nombre del archivo
			FileOutputStream f = new FileOutputStream(args[1]);
			System.setOut(new PrintStream(f));
		}

		AnalizadorLexico analizadorLexico = new AnalizadorLexico(args[0]);
		// ManejadorArchivo ma = new ManejadorArchivo();
		while (analizadorLexico.nextToken()) {
			Token token = analizadorLexico.getToken();
			System.out.print(token.toString());
		}
	}
}