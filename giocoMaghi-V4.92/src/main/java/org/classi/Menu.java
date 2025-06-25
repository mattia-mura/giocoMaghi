package org.classi;

public class Menu {
    public Menu() {

    }

    public int menu1() {
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
        while (x > 5 || x < 0) {
            System.out.println("Inserisci un numero da 0 a 5 !");
            x = Tools.getIntero();
        }
        return x;
    }

    public int menu2(){
        System.out.println();
        System.out.println("_____Menu_____");
        System.out.println("1- Genera N Maghi");
        System.out.println("2- Elimina mago");
        System.out.println("3- Schede magiche");
        System.out.println("4- Apocalisse Magica");
        System.out.println();
        System.out.println("0- ESCI");
        int x = Tools.getIntero();
        while (x > 4 || x < 0) {
            System.out.println("Inserisci un numero da 0 a 4 !");
            x = Tools.getIntero();
        }
        return x;
    }

    public int menu3(){
        System.out.println();
        System.out.println("_____Menu_____");
        System.out.println("1- Genera Squadre");
        System.out.println("2- Schede magiche");
        System.out.println("3- Ragnarok Magico");
        System.out.println();
        System.out.println("0- ESCI");
        int x = Tools.getIntero();
        while (x > 3 || x < 0) {
            System.out.println("Inserisci un numero da 0 a 3 !");
            x = Tools.getIntero();
        }
        return x;
    }

    public int menu(){
        System.out.println("Bnevenuto nel Gioco dei Maghi");
        System.out.println("_____Menu_____");
        System.out.println("1- Versione OG");
        System.out.println("2- Versione Nuova ed Automatica");
        System.out.println("3- Gioco a Squadre");
        System.out.println();
        System.out.println("0- ESCI");
        int x = Tools.getIntero();
        while (x > 3 || x < 0) {
            System.out.println("Inserisci un numero da 0 a 2 !");
            x = Tools.getIntero();
        }
        return x;
    }
}
