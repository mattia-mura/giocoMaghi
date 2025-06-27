package org.classi;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.terminal.Attributes;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("advancedInteractiveMenu")
public class AdvancedInteractiveMenu {
    private static final String RESET = "\033[0m";
    private static final String HIGHLIGHT = "\033[7m"; // Reverse video
    private static final String CLEAR_SCREEN = "\033[2J\033[H";
    private static final String HIDE_CURSOR = "\033[?25l";
    private static final String SHOW_CURSOR = "\033[?25h";
    
    private Terminal terminal;

    public AdvancedInteractiveMenu() {
        try {
            this.terminal = TerminalBuilder.builder()
                    .system(true)
                    .build();
            // Nascondi il cursore all'inizio
            System.out.print(HIDE_CURSOR);
        } catch (IOException e) {
            System.err.println("Errore nell'inizializzazione del terminale: " + e.getMessage());
        }
    }

    public void cleanup() {
        // Mostra il cursore alla fine
        System.out.print(SHOW_CURSOR);
        if (terminal != null) {
            try {
                terminal.close();
            } catch (IOException e) {
                System.err.println("Errore nella chiusura del terminale: " + e.getMessage());
            }
        }
    }

    private void clearScreen() {
        System.out.print(CLEAR_SCREEN);
        System.out.flush();
    }

    private int readKey() throws IOException {
        if (terminal == null) {
            return System.in.read();
        }
        
        // Salva gli attributi originali del terminale
        var originalAttributes = terminal.getAttributes();
        
        try {
            terminal.enterRawMode();
            int ch = terminal.reader().read();

            // Gestisce sequenze di escape per frecce direzionali
            if (ch == 27) { // ESC
                // Controlla se ci sono altri caratteri disponibili
                if (terminal.reader().ready()) {
                    int next1 = terminal.reader().read();
                    if (next1 == 91 && terminal.reader().ready()) { // [
                        int next2 = terminal.reader().read();
                        switch (next2) {
                            case 65: return 'w'; // Freccia su -> W
                            case 66: return 's'; // Freccia giù -> S
                            case 67: return 'd'; // Freccia destra -> D
                            case 68: return 'a'; // Freccia sinistra -> A
                        }
                    }
                }
                // Se non è una sequenza di frecce, ritorna ESC
                return 27;
            }

            return Character.toLowerCase(ch);
        } finally {
            // Ripristina gli attributi originali del terminale
            terminal.setAttributes(originalAttributes);
        }
    }

    private void printMenuItem(String text, boolean selected) {
        if (selected) {
            System.out.println(HIGHLIGHT + text + RESET);
        } else {
            System.out.println(text);
        }
    }

    private int showInteractiveMenu(String title, String[] options) {
        int selected = 0;
        boolean done = false;

        try {
            while (!done) {
                clearScreen();

                // Stampa il titolo
                System.out.println(title);
                System.out.println();

                // Stampa le opzioni
                for (int i = 0; i < options.length; i++) {
                    printMenuItem(options[i], i == selected);
                }

                System.out.println();
                System.out.println("Usa FRECCE o WASD per navigare, INVIO per selezionare, Q per uscire");
                System.out.flush();

                // Leggi input
                int key = readKey();

                switch (key) {
                    case 'w': // Su
                        selected = (selected - 1 + options.length) % options.length;
                        break;
                    case 's': // Giù
                        selected = (selected + 1) % options.length;
                        break;
                    case 10: // Invio (LF)
                    case 13: // Invio (CR)
                        done = true;
                        break;
                    case 'q': // Quit rapido
                        selected = options.length - 1; // Ultima opzione (solitamente EXIT)
                        done = true;
                        break;
                    case 27: // ESC - per uscire
                        selected = options.length - 1;
                        done = true;
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Errore nella lettura dell'input: " + e.getMessage());
        }

        clearScreen();
        return selected;
    }

    public int menu() {
        String[] options = {
                "1- Versione OG",
                "2- Versione Nuova ed Automatica",
                "3- Gioco a Squadre",
                "4- Modifica frequenza Log-console",
                "0- ESCI"
        };

        int choice = showInteractiveMenu("===== MENU PRINCIPALE =====", options);

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
                "1- Crea mago",
                "2- Elimina mago",
                "3- Schede magiche",
                "4- Fai nascere screzi",
                "5- Lotta magica",
                "0- ESCI"
        };

        int choice = showInteractiveMenu("===== VERSIONE OG =====", options);

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
                "1- Genera N Maghi",
                "2- Elimina mago",
                "3- Schede magiche",
                "4- Apocalisse Magica",
                "0- ESCI"
        };

        int choice = showInteractiveMenu("===== VERSIONE AUTOMATICA =====", options);

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
                "1- Genera Squadre",
                "2- Schede magiche",
                "3- Ragnarok Magico",
                "0- ESCI"
        };

        int choice = showInteractiveMenu("===== GIOCO A SQUADRE =====", options);

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
                "1- Debug (Dettagliato)",
                "2- Intermedio",
                "3- Minimo"
        };

        int choice = showInteractiveMenu("===== SCEGLI LIVELLO LOG =====", options);

        // Mappa la selezione al numero corrispondente (1, 2, 3)
        return choice + 1;
    }
}