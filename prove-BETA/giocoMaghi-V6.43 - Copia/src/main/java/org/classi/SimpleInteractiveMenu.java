package org.classi;

import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Scanner;

/**
 * Versione semplificata del menu interattivo per compatibilità con tutti i terminali Windows
 * Usa input numerico tradizionale come fallback
 */
@Component("simpleInteractiveMenu")
public class SimpleInteractiveMenu {
    
    private Scanner scanner = new Scanner(System.in);
    
    private void clearScreen() {
        // Tenta di pulire lo schermo
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[2J\033[H");
            }
        } catch (Exception e) {
            // Se fallisce, usa semplicemente delle nuove righe
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
        System.out.flush();
    }

    private void printMenuItem(String text, int index, boolean showNumbers) {
        if (showNumbers) {
            System.out.println((index + 1) + "- " + text);
        } else {
            System.out.println(text);
        }
    }

    private int showSimpleMenu(String title, String[] options) {
        clearScreen();

        // Stampa il titolo
        System.out.println(title);
        System.out.println("=".repeat(title.length()));
        System.out.println();

        // Stampa le opzioni
        for (int i = 0; i < options.length; i++) {
            printMenuItem(options[i], i, false);
        }

        System.out.println();
        System.out.print("Inserisci il numero della tua scelta: ");
        
        int choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice < 1 || choice > options.length) {
                System.out.println("Scelta non valida! Riprova.");
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return showSimpleMenu(title, options);
            }
            return choice - 1; // Ritorna indice base 0
        } catch (NumberFormatException e) {
            System.out.println("Inserisci un numero valido!");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            return showSimpleMenu(title, options);
        }
    }

    public int menu() {
        String[] options = {
                "Versione OG",
                "Versione Nuova ed Automatica", 
                "Gioco a Squadre",
                "Modifica frequenza Log-console",
                "ESCI"
        };

        int choice = showSimpleMenu("===== MENU PRINCIPALE =====", options);

        // Mappa la selezione al numero corrispondente
        switch (choice) {
            case 0: return 1;
            case 1: return 2;
            case 2: return 3;
            case 3: return 4;
            case 4: return 0;
            default: return 0;
        }
    }

    public int menu1() {
        String[] options = {
                "Crea mago",
                "Elimina mago",
                "Schede magiche",
                "Fai nascere screzi",
                "Lotta magica",
                "ESCI"
        };

        int choice = showSimpleMenu("===== VERSIONE OG =====", options);

        // Mappa la selezione al numero corrispondente
        switch (choice) {
            case 0: return 1;
            case 1: return 2;
            case 2: return 3;
            case 3: return 4;
            case 4: return 5;
            case 5: return 0;
            default: return 0;
        }
    }

    public int menu2() {
        String[] options = {
                "Genera N Maghi",
                "Elimina mago",
                "Schede magiche",
                "Apocalisse Magica",
                "ESCI"
        };

        int choice = showSimpleMenu("===== VERSIONE AUTOMATICA =====", options);

        // Mappa la selezione al numero corrispondente
        switch (choice) {
            case 0: return 1;
            case 1: return 2;
            case 2: return 3;
            case 3: return 4;
            case 4: return 0;
            default: return 0;
        }
    }

    public int menu3() {
        String[] options = {
                "Genera Squadre",
                "Schede magiche",
                "Ragnarok Magico",
                "ESCI"
        };

        int choice = showSimpleMenu("===== GIOCO A SQUADRE =====", options);

        // Mappa la selezione al numero corrispondente
        switch (choice) {
            case 0: return 1;
            case 1: return 2;
            case 2: return 3;
            case 3: return 0;
            default: return 0;
        }
    }

    public int menuLivello() {
        String[] options = {
                "Debug (Dettagliato)",
                "Intermedio", 
                "Minimo"
        };

        int choice = showSimpleMenu("===== SCEGLI LIVELLO LOG =====", options);

        // Mappa la selezione al numero corrispondente (1, 2, 3)
        return choice + 1;
    }

    public void cleanup() {
        // Nessuna pulizia speciale necessaria per la versione semplice
        System.out.println("\nGrazie per aver giocato!");
    }
}