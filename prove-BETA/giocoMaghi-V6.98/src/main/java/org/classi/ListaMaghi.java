package org.classi;
import java.util.Vector;

public class ListaMaghi {

    private static Vector<Mago> listaMaghi =  new Vector<>();

    public static boolean addMago(Mago mago){

        listaMaghi.add(mago);
        return true;
    }

    public static boolean removeMago(Mago mago){

        return listaMaghi.remove(mago);
    }

    public static int findMago(String nome){

        for(int i=0;i<listaMaghi.size();i++){
                if(listaMaghi.get(i).getNome().equals(nome)){return i;}
            }
            return -1;

    }

    public static int getLength(){

        return listaMaghi.size();
    }

    public static void print() {

            for (Mago m : listaMaghi) {
                System.out.println(m);
            }
    }//print

    public static Mago getMago(int i){

        return listaMaghi.get(i);
    }
    public static Vector<Mago> getMaghi(){


        return listaMaghi;
    }

    public static void clean(){
        listaMaghi.clear();
    }

}
