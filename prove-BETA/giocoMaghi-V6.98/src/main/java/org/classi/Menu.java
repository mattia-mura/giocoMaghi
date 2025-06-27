package org.classi;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.shell.component.StringInput;
import org.springframework.shell.component.support.SelectorItem;
import org.springframework.shell.style.TemplateExecutor;
import org.springframework.shell.style.ThemeResolver;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.stereotype.Component;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
@ShellComponent
public class Menu {
    private final Scanner scanner;
    private boolean useFallback = false;

    public Menu() {
        this.scanner = new Scanner(System.in);
        if (System.console() == null || System.getenv("TERM") == null) {
            useFallback = true;
        }
    }

    private int showInteractiveMenu(String title, List<String> options, boolean showZero) {
        try {
            List<SelectorItem<String>> items = options.stream()
                    .map(option -> SelectorItem.of(option, option))
                    .collect(Collectors.toList());

            if (showZero) {
                items.add(SelectorItem.of("0- ESCI", "0- ESCI"));
            }

            // Creazione del componente con il context
            StringSelectInputContext context = new StringSelectInputContext();

            // Simulazione selezione (sostituisci con la tua logica effettiva)
            // Qui dovresti avere la logica per gestire l'input dell'utente
            // e impostare l'item selezionato nel context
            // context.setResultItem(items.get(0)); // Esempio: seleziona il primo item

            if (context.getResultItem() != null) {
                String selected = context.getResultItem().getItem();
                return Integer.parseInt(selected.split("-")[0].trim());
            }
            return 0;
        } catch (Exception e) {
            System.err.println("Errore menu interattivo, usando fallback: " + e.getMessage());
            return showTraditionalMenu(title, options, showZero);
        }
    }

    private int showTraditionalMenu(String title, List<String> options, boolean showZero) {
        System.out.println("_____ " + title + " _____\n");
        for (String option : options) {
            System.out.println(option);
        }
        if (showZero) {
            System.out.println("\n0- ESCI");
        }
        System.out.print("\nInserisci il numero dell'opzione: ");
        return Integer.parseInt(scanner.nextLine());
    }

    // Mantieni tutti gli altri metodi esattamente come erano
    public int menu1() {
        return showInteractiveMenu(
                "Menu",
                List.of(
                        "1- Crea mago",
                        "2- Elimina mago",
                        "3- Schede magiche",
                        "4- Fai nascere screzi",
                        "5- Lotta magica"
                ),
                true
        );
    }

    public int menu2() {
        return showInteractiveMenu(
                "Menu",
                List.of(
                        "1- Genera N Maghi",
                        "2- Elimina mago",
                        "3- Schede magiche",
                        "4- Apocalisse Magica"
                ),
                true
        );
    }

    public int menu3() {
        return showInteractiveMenu(
                "Menu",
                List.of(
                        "1- Genera Squadre",
                        "2- Schede magiche",
                        "3- Ragnarok Magico"
                ),
                true
        );
    }

    public int menuLivello() {
        return showInteractiveMenu(
                "Livello Log",
                List.of(
                        "1- Debug (Dettagliato)",
                        "2- Intermedio",
                        "3- Minimo"
                ),
                false
        );
    }

    public int menu() {
        return showInteractiveMenu(
                "Menu Principale",
                List.of(
                        "1- Versione OG",
                        "2- Versione Nuova ed Automatica",
                        "3- Gioco a Squadre",
                        "4- Modifica frequenza Log"
                ),
                true
        );
    }

    private Terminal getTerminal() throws IOException {
        return TerminalBuilder.builder()
                .system(true)
                .build();
    }

    private ResourceLoader getResourceLoader() {
        return new DefaultResourceLoader();
    }

    private TemplateExecutor getTemplateExecutor() throws IOException {
        return new TemplateExecutor((ThemeResolver) getTerminal());
    }

    public void close() {
        scanner.close();
    }
}


