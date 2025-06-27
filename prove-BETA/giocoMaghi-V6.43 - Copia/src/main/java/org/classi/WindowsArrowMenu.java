package org.classi;

import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * Menu interattivo per Windows che usa le frecce direzionali
 * Funziona direttamente con la console Windows senza librerie esterne
 */
@Component("windowsArrowMenu")
public class WindowsArrowMenu {
    
    private static final String CLEAR_SCREEN = "\033[2J\033[H";
    private static final String HIDE_CURSOR = "\033[?25l";
    private static final String SHOW_CURSOR = "\033[?25h";
    private static final String RESET = "\033[0m";
    private static final String HIGHLIGHT = "\033[1;47;30m"; // Bianco su nero, grassetto
    private static final String NORMAL = "\033[0m";
    
    public WindowsArrowMenu() {
        // Nascondi il cursore
        System.out.print(HIDE_CURSOR);
    }

    public void cleanup() {
        // Mostra il cursore
        System.out.print(SHOW_CURSOR);
    }

    private void clearScreen() {
        System.out.print(CLEAR_SCREEN);
        System.out.flush();
    }

    private int readKeyWindows() throws IOException {
        int ch = System.in.read();
        
        // Gestisce le sequenze di escape per le frecce
        if (ch == 224) { // Codice speciale per Windows
            int arrow = System.in.read();
            switch (arrow) {
                case 72: return 'w'; // Freccia su -> W
                case 80: return 's'; // Freccia giù -> S
                case 77: return 'd'; // Freccia destra -> D  
                case 75: return 'a'; // Freccia sinistra -> A
            }
        }
        
        // Gestisce sequenze ANSI (terminali moderni)
        if (ch == 27) { // ESC
            if (System.in.available() > 0) {
                int next1 = System.in.read();
                if (next1 == 91 && System.in.available() > 0) { // [
                    int next2 = System.in.read();
                    switch (next2) {
                        case 65: return 'w'; // Freccia su
                        case 66: return 's'; // Freccia giù
                        case 67: return 'd'; // Freccia destra
                        case 68: return 'a'; // Freccia sinistra
                    }
                }
            }
            return 27; // ESC per uscire
        }

        return Character.toLowerCase(ch);
    }

    private void printMenuItem(String text, boolean selected) {
        if (selected) {
            System.out.println(HIGHLIGHT + " > " + text + " < " + RESET);
        } else {
            System.out.println("   " + text);
        }
    }

    private int showInteractiveMenu(String title, String[] options) {
        int selected = 0;
        boolean done = false;

        try {
            while (!done) {
                clearScreen();
                
                // Stampa il titolo
                System.out.println();
                System.out.println("=".repeat(50));
                System.out.println(title);
                System.out.println("=".repeat(50));
                System.out.println();

                // Stampa le opzioni
                for (int i = 0; i < options.length; i++) {
                    printMenuItem(options[i], i == selected);
                }

                System.out.println();
                System.out.println("=".repeat(50));
                System.out.println("🎮 Usa FRECCE ↑↓ o WASD per navigare");
                System.out.println("⏎  Premi INVIO per selezionare");
                System.out.println("❌ Premi Q o ESC per uscire");
                System.out.println("=".repeat(50));
                System.out.flush();

                // Leggi input
                int key = readKeyWindows();

                switch (key) {
                    case 'w': // Su (W o freccia su)
                        selected = (selected - 1 + options.length) % options.length;
                        break;
                    case 's': // Giù (S o freccia giù)  
                        selected = (selected + 1) % options.length;
                        break;
                    case 10: // Enter (LF)
                    case 13: // Enter (CR)
                        done = true;
                        break;
                    case 'q': // Quit rapido
                        selected = options.length - 1; // Ultima opzione (ESCI)
                        done = true;
                        break;
                    case 27: // ESC
                        selected = options.length - 1; // Ultima opzione (ESCI)
                        done = true;
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Errore nella lettura dell'input: " + e.getMessage());
            // Fallback: usa l'ultima opzione selezionata
        }

        clearScreen();
        return selected;
    }

    public int menu() {
        String[] options = {
                "1️⃣  Versione OG",
                "2️⃣  Versione Nuova ed Automatica", 
                "3️⃣  Gioco a Squadre",
                "4️⃣  Modifica frequenza Log-console",
                "❌ ESCI"
        };

        int choice = showInteractiveMenu("🧙‍♂️ MENU PRINCIPALE - GIOCO DEI MAGHI 🧙‍♀️", options);

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
                "🧙‍♂️ Crea mago",
                "🗑️  Elimina mago",
                "📋 Schede magiche",
                "⚔️  Fai nascere screzi",
                "🔥 Lotta magica",
                "❌ ESCI"
        };

        int choice = showInteractiveMenu("⚔️ VERSIONE OG ⚔️", options);

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
                "🎲 Genera N Maghi",
                "🗑️  Elimina mago", 
                "📋 Schede magiche",
                "💥 Apocalisse Magica",
                "❌ ESCI"
        };

        int choice = showInteractiveMenu("🤖 VERSIONE AUTOMATICA 🤖", options);

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
                "👥 Genera Squadre",
                "📋 Schede magiche",
                "⚡ Ragnarok Magico", 
                "❌ ESCI"
        };

        int choice = showInteractiveMenu("🏆 GIOCO A SQUADRE 🏆", options);

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
                "🔍 Debug (Dettagliato)",
                "📊 Intermedio", 
                "📝 Minimo"
        };

        int choice = showInteractiveMenu("⚙️ SCEGLI LIVELLO LOG ⚙️", options);

        // Ritorna 1, 2, 3
        return choice + 1;
    }
}