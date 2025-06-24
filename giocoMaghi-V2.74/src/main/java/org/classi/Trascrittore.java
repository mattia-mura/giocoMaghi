package org.classi;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Trascrittore {
    private static final String PATH = "C:\\Users\\mattia.mu\\Desktop\\giocoMaghi-V1.91 - Copia\\src\\files\\cronologiaIncontri.txt";
    private static BufferedWriter writer;

    static {
        try {
            writer = new BufferedWriter(new FileWriter(PATH, true)); // true = append
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void scrivi(String testo) {
        try {
            writer.write(testo);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void chiudi() {
        try {
            if (writer != null) {
                writer.write("_______________________");
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeAll(){
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
