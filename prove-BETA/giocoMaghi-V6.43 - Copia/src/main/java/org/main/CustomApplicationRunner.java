package org.main;

import org.shell.MaghiShellCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CustomApplicationRunner implements CommandLineRunner {

    @Autowired
    private MaghiShellCommands maghiCommands;

    @Override
    public void run(String... args) throws Exception {
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println("🧙‍♂️ BENVENUTO NEL GIOCO DEI MAGHI! 🧙‍♀️");
        System.out.println("=".repeat(60));
        System.out.println();
        System.out.println("🎮 Comandi disponibili:");
        System.out.println("   start       - Avvia il gioco completo");
        System.out.println("   test-menu   - Prova il menu con frecce");
        System.out.println("   menu        - Menu principale");
        System.out.println("   crea-mago   - Crea nuovo mago");
        System.out.println("   maghi       - Lista maghi");
        System.out.println("   help        - Mostra tutti i comandi");
        System.out.println("   exit        - Esci dal programma");
        System.out.println();
        System.out.println("=".repeat(60));
        
        // Avvia la shell personalizzata
        startCustomShell();
    }

    private void startCustomShell() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.print("shell:> ");
            String input = scanner.nextLine().trim().toLowerCase();

            try {
                switch (input) {
                    case "start":
                        System.out.println("🚀 Avvio gioco completo...\n");
                        maghiCommands.start();
                        break;
                    case "test-menu":
                        System.out.println("🎮 Test menu interattivo...\n");
                        maghiCommands.testInteractiveMenu();
                        break;
                    case "menu":
                        System.out.println("📋 Menu principale...\n");
                        maghiCommands.menu();
                        break;
                    case "crea-mago":
                        System.out.println("🧙‍♂️ Creazione mago...\n");
                        maghiCommands.creaMago("");
                        break;
                    case "maghi":
                        System.out.println("📜 Lista maghi...\n");
                        maghiCommands.listaMaghi();
                        break;
                    case "help":
                        showHelp();
                        break;
                    case "exit":
                    case "quit":
                        System.out.println("👋 Arrivederci!");
                        running = false;
                        break;
                    case "":
                        // Comando vuoto, continua
                        break;
                    default:
                        System.out.println("❌ Comando non riconosciuto: " + input);
                        System.out.println("💡 Digita 'help' per vedere i comandi disponibili");
                        break;
                }
            } catch (Exception e) {
                System.err.println("❌ Errore nell'esecuzione del comando: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        scanner.close();
        System.exit(0);
    }

    private void showHelp() {
        System.out.println();
        System.out.println("🎮 COMANDI DISPONIBILI:");
        System.out.println("=".repeat(40));
        System.out.println("start       - Avvia il gioco completo");
        System.out.println("test-menu   - Prova il menu con frecce");
        System.out.println("menu        - Menu principale");
        System.out.println("crea-mago   - Crea nuovo mago");
        System.out.println("maghi       - Lista maghi esistenti");
        System.out.println("help        - Mostra questa guida");
        System.out.println("exit/quit   - Esci dal programma");
        System.out.println("=".repeat(40));
        System.out.println();
    }
}