package org.classi;

public class Menu {
    public Menu() {}

    public int menu1() {
        System.out.println();
        System.out.println("_____Menu_____");
        System.out.println("1- Crea Mago");
        System.out.println("2- Elimina Mago");
        System.out.println("3- Genero N Maghi Casuali (max 100)");
        System.out.println("4- Vedi Schede Magiche");
        System.out.println("5- Fai Nascere Screzi");
        //System.out.println("6- Assengna Manualmente i Nemici");
        System.out.println("6- Lotta Magica");
        //System.out.println("8- Apocalisse Magica");
        System.out.println();
        System.out.println("0- ESCI");
        int x = Tools.getIntero();
        while (x > 8 || x < 0) {
            System.out.println("Inserisci un numero da 0 a 6!");
            x = Tools.getIntero();
        }
        return x;
    }
}
