package org.classi;
import java.util.Iterator;
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

//    public static synchronized void pulisciSquadreVuote() {
//        Iterator<Vector<Mago>> iterator = vettoreDiVettori.iterator();
//        while (iterator.hasNext()) {
//            Vector<Mago> squadra = iterator.next();
//            if (squadra.isEmpty()) {
//                iterator.remove();
//            }
//        }
//    }
//
//    public static synchronized void rimuoviMago(Mago morto) {
//        for (int i = 0; i < vettoreDiVettori.size(); i++) {
//            Vector<Mago> squadra = vettoreDiVettori.get(i);
//            squadra.remove(morto);
//            if (squadra.isEmpty()) {
//                vettoreDiVettori.remove(i);
//                i--;
//            }
//        }
//    }

    public static synchronized void rimuoviMago(Mago m) {
        for (Vector<Mago> squadra : vettoreDiVettori) {
            squadra.remove(m);
        }
        pulisciSquadreVuote();
    }

    public static synchronized void pulisciSquadreVuote() {
        vettoreDiVettori.removeIf(Vector::isEmpty);
    }




}
