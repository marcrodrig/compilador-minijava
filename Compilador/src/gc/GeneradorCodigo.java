package gc;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GeneradorCodigo {
	private static GeneradorCodigo instanciaUnica;
	private static BufferedWriter generador;
	public static String archivoSalida;
	private int nLabel = 0;
	
	private GeneradorCodigo() {
		try {
			generador = new BufferedWriter(new FileWriter(archivoSalida));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private synchronized static void createInstance() {
		if (instanciaUnica == null) {
			instanciaUnica = new GeneradorCodigo();
		}
	}

	public static GeneradorCodigo getInstance() {
		createInstance();
		return instanciaUnica;
	}
	
	public static void reset() {
		instanciaUnica = null;
	}
	
	public void write(String cadena) {
		try {
			generador.write(cadena);
			generador.newLine();
			generador.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void newLine() {
		try {
			generador.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String nLabel() {
		return ""+nLabel++;
	}
	
	public void close() {
		try {
			generador.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
