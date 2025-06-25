package org.classi;
import java.util.Vector;

public class SquadMaghi {

    public static Vector<Vector<Mago>> vettoreDiVettori = new Vector<>();

    public static synchronized void generaSquadre(Vector<Mago> squadra){
        Vector<Mago> copiaSquadra = new Vector<>(squadra);
        vettoreDiVettori.add(copiaSquadra);
    }

    public static synchronized void print(){
        for (int r = 0; r < vettoreDiVettori.size(); r++) {
            System.out.println("Squadra "+(r+1)+" -+-+-+-+-+-+-+-+-+-+-+-+-+-+-");
            for (int c = 0; c < vettoreDiVettori.get(r).size(); c++) {
                System.out.print("Mago "+(c+1)+": ");
                System.out.print(vettoreDiVettori.get(r).get(c) + "\t");
            }
            System.out.println();
            System.out.println();
        }
    }

    public static synchronized boolean isEmpty(){
        return vettoreDiVettori.isEmpty();
    }

    public static synchronized Mago getMago(int a, int b){
        return vettoreDiVettori.get(a).get(b);
    }

    // NUOVA funzione per rimuovere squadre vuote
    public static synchronized void pulisciSquadreVuote(){
        for (int i = 0; i < vettoreDiVettori.size(); i++) {
            if (vettoreDiVettori.get(i).isEmpty()) {
                vettoreDiVettori.remove(i);
                i--; // evita di saltare elementi
            }
        }
    }

    public static synchronized void rimuoviMago(Mago morto) {
        for (int i = 0; i < vettoreDiVettori.size(); i++) {
            Vector<Mago> squadra = vettoreDiVettori.get(i);
            // Rimuovo tutte le occorrenze del mago morto in questa squadra
            squadra.removeIf(m -> m.equals(morto));
        }
        // Rimuovo squadre vuote
        pulisciSquadreVuote();
    }

}
