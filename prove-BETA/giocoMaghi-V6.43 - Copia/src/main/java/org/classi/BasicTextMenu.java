package org.classi;

import org.springframework.stereotype.Component;
import java.util.Scanner;

/**
 * Menu testuale di base - funziona sempre
 */
@Component("basicTextMenu")
public class BasicTextMenu {
    
    private Scanner scanner = new Scanner(System.in);
    
    public int menu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("        MENU PRINCIPALE");
        System.out.println("=".repeat(40));
        System.out.println("1. Versione OG");
        System.out.println("2. Versione Nuova ed Automatica");
        System.out.println("3. Gioco a Squadre");
        System.out.println("4. Modifica frequenza Log-console");
        System.out.println("0. ESCI");
        System.out.println("=".repeat(40));
        System.out.print("Inserisci la tua scelta (0-4): ");
        
        try {
            int choice = scanner.nextInt();
            return choice;
        } catch (Exception e) {
            scanner.nextLine(); // Pulisce il buffer
            System.out.println("Scelta non valida! Riprova.");
            return menu();
        }
    }
    
    public int menu1() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("        VERSIONE OG");
        System.out.println("=".repeat(40));
        System.out.println("1. Crea mago");
        System.out.println("2. Elimina mago");
        System.out.println("3. Schede magiche");
        System.out.println("4. Fai nascere screzi");
        System.out.println("5. Lotta magica");
        System.out.println("0. ESCI");
        System.out.println("=".repeat(40));
        System.out.print("Inserisci la tua scelta (0-5): ");
        
        try {
            int choice = scanner.nextInt();
            return choice;
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Scelta non valida! Riprova.");
            return menu1();
        }
    }
    
    public int menu2() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("        VERSIONE AUTOMATICA");
        System.out.println("=".repeat(40));
        System.out.println("1. Genera N Maghi");
        System.out.println("2. Elimina mago");
        System.out.println("3. Schede magiche");
        System.out.println("4. Apocalisse Magica");
        System.out.println("0. ESCI");
        System.out.println("=".repeat(40));
        System.out.print("Inserisci la tua scelta (0-4): ");
        
        try {
            int choice = scanner.nextInt();
            return choice;
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Scelta non valida! Riprova.");
            return menu2();
        }
    }
    
    public int menu3() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("        GIOCO A SQUADRE");
        System.out.println("=".repeat(40));
        System.out.println("1. Genera Squadre");
        System.out.println("2. Schede magiche");
        System.out.println("3. Ragnarok Magico");
        System.out.println("0. ESCI");
        System.out.println("=".repeat(40));
        System.out.print("Inserisci la tua scelta (0-3): ");
        
        try {
            int choice = scanner.nextInt();
            return choice;
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Scelta non valida! Riprova.");
            return menu3();
        }
    }
    
    public int menuLivello() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("        SCEGLI LIVELLO LOG");
        System.out.println("=".repeat(40));
        System.out.println("1. Debug (Dettagliato)");
        System.out.println("2. Intermedio");
        System.out.println("3. Minimo");
        System.out.println("=".repeat(40));
        System.out.print("Inserisci la tua scelta (1-3): ");
        
        try {
            int choice = scanner.nextInt();
            return choice;
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Scelta non valida! Riprova.");
            return menuLivello();
        }
    }
    
    public void cleanup() {
        // Nessuna pulizia necessaria per il menu testuale di base
    }
}