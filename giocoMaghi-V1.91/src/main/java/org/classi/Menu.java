package org.classi;
import org.classi.Tools;

public class Menu {

    public Menu(){

    }

    public int menu1(){
        System.out.println();
        System.out.println("_____Menu_____");
        System.out.println("1- Crea mago");
        System.out.println("2- Elimina mago");
        System.out.println("3- Schede magiche");
        System.out.println("4- Fai nascere screzi");
        System.out.println("5- Lotta magica");
        System.out.println();
        System.out.println("0- ESCI");
        int x = Tools.getIntero();
        while( x > 5 || x < 0){
            System.out.println("Inserisci un numero da 0 a 5!");
            x = Tools.getIntero();
        }
        return x;
    }
}
