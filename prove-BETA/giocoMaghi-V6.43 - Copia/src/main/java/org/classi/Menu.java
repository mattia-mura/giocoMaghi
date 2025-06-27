package org.classi;

public class Menu {
    private InteractiveMenu interactiveMenu;
    private boolean useInteractive;

    public Menu() {
        // Prova a determinare se il sistema supporta la modalità interattiva
        this.useInteractive = isInteractiveModeSupported();
        if (useInteractive) {
            this.interactiveMenu = new InteractiveMenu();
        }
    }

    private boolean isInteractiveModeSupported() {
        // Controlla se siamo su un sistema Unix-like con supporto per stty
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("linux") || os.contains("mac") || os.contains("unix");
    }

    public void cleanup() {
        if (useInteractive && interactiveMenu != null) {
            interactiveMenu.cleanup();
        }
    }

    public int menu1() {
        if (useInteractive) {
            return interactiveMenu.menu1();
        }

        // Fallback alla versione originale
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
        if (useInteractive) {
            return interactiveMenu.menu2();
        }

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
        if (useInteractive) {
            return interactiveMenu.menu3();
        }

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

    public int menuLivello(){
        if (useInteractive) {
            return interactiveMenu.menuLivello();
        }

        System.out.println();
        System.out.println("_Scegli la quantità di log da visualizzare_");
        System.out.println("1- Debug (Dettagliato)");
        System.out.println("2- Intermedio");
        System.out.println("3- Minimo");
        System.out.println();
        int x = Tools.getIntero();
        while (x > 3 || x < 1) {
            System.out.println("Inserisci un numero da 1 a 3 !");
            x = Tools.getIntero();
        }
        return x;
    }

    public int menu(){
        if (useInteractive) {
            return interactiveMenu.menu();
        }

        System.out.println();
        System.out.println("_____Menu_____");
        System.out.println("1- Versione OG");
        System.out.println("2- Versione Nuova ed Automatica");
        System.out.println("3- Gioco a Squadre");
        System.out.println("4- Modifica frequenza Log-console");
        System.out.println();
        System.out.println("0- ESCI");
        int x = Tools.getIntero();
        while (x > 4 || x < 0) {
            System.out.println("Inserisci un numero da 0 a 4 !");
            x = Tools.getIntero();
        }
        return x;
    }
}