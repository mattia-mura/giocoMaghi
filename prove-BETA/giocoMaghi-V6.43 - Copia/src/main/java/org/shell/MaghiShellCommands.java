package org.shell;

import org.classi.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class MaghiShellCommands {


    @Autowired
    private WindowsArrowMenu interactiveMenu;
    
    @Autowired
    private SimpleInteractiveMenu simpleMenu;
    
    @Autowired
    private BasicTextMenu basicMenu;

    private int livello = 1;
    private Scanner tastiera = new Scanner(System.in);
    private boolean useSimpleMenu = false; // Prova menu avanzato
    private boolean useBasicMenu = false; // Disabilita menu testuale di base
    private boolean forceCustomMenu = true; // Forza l'uso del menu personalizzato

    // Metodi per gestire il fallback automatico
    private int getMenuChoice() {
        // Forza sempre l'uso del menu personalizzato Windows
        if (forceCustomMenu || !useBasicMenu) {
            try {
                System.out.println("🎮 Caricamento menu interattivo con frecce...\n");
                return interactiveMenu.menu();
            } catch (Exception e) {
                System.out.println("❌ Errore menu frecce: " + e.getMessage());
                System.out.println("📋 Uso menu testuale di backup...\n");
            }
        }
        
        // Fallback al menu testuale
        if (useBasicMenu) {
            return basicMenu.menu();
        }
        
        return simpleMenu.menu();
    }
    
    private int getMenu1Choice() {
        if (useBasicMenu) {
            return basicMenu.menu1();
        }
        
        try {
            if (!useSimpleMenu) {
                return interactiveMenu.menu1();
            }
        } catch (Exception e) {
            useSimpleMenu = true;
        }
        return simpleMenu.menu1();
    }
    
    private int getMenu2Choice() {
        if (useBasicMenu) {
            return basicMenu.menu2();
        }
        
        try {
            if (!useSimpleMenu) {
                return interactiveMenu.menu2();
            }
        } catch (Exception e) {
            useSimpleMenu = true;
        }
        return simpleMenu.menu2();
    }
    
    private int getMenu3Choice() {
        if (useBasicMenu) {
            return basicMenu.menu3();
        }
        
        try {
            if (!useSimpleMenu) {
                return interactiveMenu.menu3();
            }
        } catch (Exception e) {
            useSimpleMenu = true;
        }
        return simpleMenu.menu3();
    }
    
    private int getMenuLivelloChoice() {
        if (useBasicMenu) {
            return basicMenu.menuLivello();
        }
        
        try {
            if (!useSimpleMenu) {
                return interactiveMenu.menuLivello();
            }
        } catch (Exception e) {
            useSimpleMenu = true;
        }
        return simpleMenu.menuLivello();
    }

    public void start() {
        try {
            ListaNomiMaghi.generaNomiMaghi();
            boolean prima = true;
            int scelta;

            do {
                Queue<Mago> codaTurni = new LinkedList<>();
                AtomicBoolean duelloInCorso = new AtomicBoolean(true);
                
                if (prima) {
                    System.out.println("Benvenuto nel Gioco dei Maghi!");
                    if (!useSimpleMenu) {
                        System.out.println("Usa FRECCE o WASD per navigare nei menu");
                    }
                    System.out.println();
                    livello = getMenuLivelloChoice();
                    System.out.println("Grazie per aver inserito la tua preferenza, potrai modificarla in ogni momento dal menù principale!");
                    prima = false;
                }

                scelta = getMenuChoice();

                switch (scelta) {
                    case 1:
                        handleVersioneOG(codaTurni, duelloInCorso);
                        break;
                    case 2:
                        handleVersioneAutomatica(codaTurni, duelloInCorso);
                        break;
                    case 3:
                        handleGiocoSquadre(codaTurni, duelloInCorso);
                        break;
                    case 4:
                        livello = getMenuLivelloChoice();
                        System.out.println("Livello di log aggiornato!");
                        break;
                    case 0:
                        System.out.println("Grazie per aver giocato!");
                        break;
                }
            } while (scelta != 0);
        } finally {
            cleanup();
        }
    }

    public void menu() {
        try {
            int choice = getMenuChoice();
            System.out.println("Hai selezionato l'opzione: " + choice);
        } finally {
            cleanup();
        }
    }


    public void setLogLevel() {
        try {
            livello = getMenuLivelloChoice();
            System.out.println("Livello di log impostato a: " + livello);
        } finally {
            cleanup();
        }
    }
    

    public void forceSimpleMenu() {
        useSimpleMenu = true;
        System.out.println("Modalità menu semplice attivata.");
    }
    

    public void tryAdvancedMenu() {
        useSimpleMenu = false;
        useBasicMenu = false;
        System.out.println("Tentativo menu avanzato attivato.");
    }
    

    public void useBasicMenu() {
        useBasicMenu = true;
        System.out.println("Menu testuale di base attivato - funziona sempre!");
    }
    

    public void testInteractiveMenu() {
        System.out.println("🎮 Testando menu interattivo...");
        System.out.println("Usa FRECCE ↑↓ o WASD per navigare, INVIO per selezionare");
        
        try {
            int choice = interactiveMenu.menu();
            System.out.println("✅ Menu funziona! Hai selezionato: " + choice);
        } catch (Exception e) {
            System.out.println("❌ Errore menu: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void cleanup() {
        if (useBasicMenu) {
            basicMenu.cleanup();
        } else if (!useSimpleMenu) {
            interactiveMenu.cleanup();
        } else {
            simpleMenu.cleanup();
        }
    }

    public void creaMago(String nome) {
        Queue<Mago> codaTurni = new LinkedList<>();
        AtomicBoolean duelloInCorso = new AtomicBoolean(true);
        
        if (nome.isEmpty()) {
            do {
                System.out.print("Inserisci il nome del mago da inserire: ");
                nome = tastiera.next();
                nome = Tools.rimuoviSpazi(nome);
                if (ListaMaghi.findMago(nome) != -1) {
                    System.out.println("Nome già in uso, scegline un altro!");
                    nome = "";
                }
            } while (nome.equals(""));
        }
        
        int squadMaghetto = -1 * (ListaMaghi.getLength() + 1);
        if (ListaMaghi.addMago(new Mago(nome, 35, 30, codaTurni, duelloInCorso, squadMaghetto, livello))) {
            System.out.println("Mago " + nome + " creato con successo!");
        } else {
            System.out.println("Impossibile creare il mago, riprova!");
        }
    }


    public void listaMaghi() {
        System.out.println("\n-- Elenco Maghi --");
        ListaMaghi.print();
        System.out.println();
    }

    public void eliminaMago(String nome) {
        nome = Tools.rimuoviSpazi(nome);
        if (ListaMaghi.removeMago(ListaMaghi.getMago(ListaMaghi.findMago(nome)))) {
            System.out.println("Mago " + nome + " eliminato con successo!");
        } else {
            System.out.println("Impossibile eliminare il mago " + nome + " (non trovato)!");
        }
    }

    private void handleVersioneOG(Queue<Mago> codaTurni, AtomicBoolean duelloInCorso) {
        int sceltaMenu1;
        do {
            sceltaMenu1 = getMenu1Choice();
            switch (sceltaMenu1) {
                case 1: // Crea mago
                    creaMagoInterattivo(codaTurni, duelloInCorso);
                    break;
                case 2: // Elimina mago
                    eliminaMagoInterattivo();
                    break;
                case 3: // Schede magiche
                    listaMaghi();
                    break;
                case 4: // Fai nascere screzi
                    assegnaNemici();
                    break;
                case 5: // Lotta magica
                    avviaLottaMagica(duelloInCorso);
                    break;
                case 0: // ESCI
                    break;
                default:
                    System.out.println("Comando non valido!");
                    break;
            }
        } while (sceltaMenu1 != 0);
    }

    private void handleVersioneAutomatica(Queue<Mago> codaTurni, AtomicBoolean duelloInCorso) {
        int sceltaMenu2;
        do {
            sceltaMenu2 = getMenu2Choice();
            switch (sceltaMenu2) {
                case 1: // Genera N Maghi
                    generaMaghi(codaTurni, duelloInCorso);
                    break;
                case 2: // Elimina mago
                    eliminaMagoInterattivo();
                    break;
                case 3: // Schede magiche
                    listaMaghi();
                    break;
                case 4: // Apocalisse Magica
                    avviaApocalisseMagica(duelloInCorso);
                    break;
                case 0: // ESCI
                    break;
                default:
                    System.out.println("Comando non valido!");
                    break;
            }
        } while (sceltaMenu2 != 0);
    }

    private void handleGiocoSquadre(Queue<Mago> codaTurni, AtomicBoolean duelloInCorso) {
        int sceltaMenu3;
        do {
            sceltaMenu3 = getMenu3Choice();
            switch (sceltaMenu3) {
                case 1: // Genera Squadre
                    generaSquadre(codaTurni, duelloInCorso);
                    break;
                case 2: // Schede magiche
                    listaMaghi();
                    break;
                case 3: // Ragnarok Magico
                    avviaRagnarok(duelloInCorso);
                    break;
                case 0: // ESCI
                    break;
                default:
                    System.out.println("Comando non valido!");
                    break;
            }
        } while (sceltaMenu3 != 0);
    }

    // Metodi di supporto privati
    private void creaMagoInterattivo(Queue<Mago> codaTurni, AtomicBoolean duelloInCorso) {
        String nome;
        do {
            System.out.print("\nInserisci il nome del mago da inserire: ");
            nome = tastiera.next();
            nome = Tools.rimuoviSpazi(nome);
            if (ListaMaghi.findMago(nome) != -1) {
                System.out.println("Nome già in uso, scegline un altro!");
                nome = "";
            }
        } while (nome.equals(""));
        
        int squadMaghetto = -1 * (ListaMaghi.getLength() + 1);
        if (ListaMaghi.addMago(new Mago(nome, 35, 30, codaTurni, duelloInCorso, squadMaghetto, livello))) {
            System.out.println("Mago aggiunto con successo");
        } else {
            System.out.println("Impossibile aggiungere il Mago, riprova!");
        }
    }

    private void eliminaMagoInterattivo() {
        String nome;
        do {
            System.out.print("\nInserisci il nome del mago da eliminare: ");
            nome = tastiera.next();
            nome = Tools.rimuoviSpazi(nome);
        } while (nome.equals(""));
        
        if (ListaMaghi.removeMago(ListaMaghi.getMago(ListaMaghi.findMago(nome)))) {
            System.out.println("Mago eliminato con successo");
        } else {
            System.out.println("Impossibile eliminare il Mago, riprova!");
        }
    }

    private void assegnaNemici() {
        if (ListaMaghi.getLength() < 2) {
            System.out.println("Servono almeno due maghi per assegnare i bersagli.");
            return;
        }
        
        for (Mago m : ListaMaghi.getMaghi()) {
            boolean errore;
            do {
                errore = false;
                System.out.print("Inserisci il nome del mago che " + m.getNome() + " punterà: ");
                String nomeNemico = tastiera.next();
                nomeNemico = Tools.rimuoviSpazi(nomeNemico);
                if (nomeNemico.equals(m.getNome())) {
                    errore = true;
                    System.out.println("Il nemico non può essere se stesso.");
                    continue;
                }
                int index = ListaMaghi.findMago(nomeNemico);
                if (index != -1) {
                    m.setNemico(ListaMaghi.getMago(index));
                } else {
                    errore = true;
                    System.out.println("Nome non trovato. Riprova.");
                }
            } while (errore);
        }
    }

    private void avviaLottaMagica(AtomicBoolean duelloInCorso) {
        duelloInCorso.set(true);
        if (ListaMaghi.getLength() < 2) {
            System.out.println("Impossibile far startare la lotta magica!");
            System.out.println("Devi avere almeno 2 maghi e tutti devono avere un nemico!");
        } else {
            for (Mago m : ListaMaghi.getMaghi()) {
                m.start();
            }

            while (duelloInCorso.get()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            magnaMorti();
            
            // Interrupt all remaining threads
            for (Mago m : ListaMaghi.getMaghi()) {
                if (m.isAlive()) {
                    m.interrupt();
                }
            }

            ListaMaghi.clean();
            System.out.println();
            System.out.println("Battaglia finita, il vincitore è stato congedato nel mondo della magia!");
        }
    }

    private void generaMaghi(Queue<Mago> codaTurni, AtomicBoolean duelloInCorso) {
        int x;
        do {
            System.out.println("Dimmi quanti maghi vuoi generare (da 2 a 100)");
            x = Tools.getIntero();
        } while (x < 2 || x > 100);

        for (int i = 0; i < x; i++) {
            int squadMaghetto = -1 * (ListaMaghi.getLength() + 1);
            String nomeMago = ListaNomiMaghi.getNomeMago();
            Mago nuovoMago = new Mago(nomeMago, 35, 30, codaTurni, duelloInCorso, squadMaghetto, livello);
            ListaMaghi.addMago(nuovoMago);
        }
        System.out.println("Fatto!");
    }

    private void avviaApocalisseMagica(AtomicBoolean duelloInCorso) {
        duelloInCorso.set(true);
        if (ListaMaghi.getLength() < 2) {
            System.out.println("Impossibile far startare la lotta magica!");
            System.out.println("Devi avere almeno 2 maghi e tutti devono avere un nemico!");
        } else {
            for (Mago m : ListaMaghi.getMaghi()) {
                m.assegnaNuovoNemico2();
            }

            for (Mago m : ListaMaghi.getMaghi()) {
                m.start();
            }

            while (duelloInCorso.get()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            magnaMorti();
            
            for (Mago m : ListaMaghi.getMaghi()) {
                if (m.isAlive()) {
                    m.interrupt();
                }
            }

            ListaMaghi.clean();
            System.out.println();
            System.out.println("Battaglia finita, il vincitore è stato congedato nel mondo della magia!");
        }
    }

    private void generaSquadre(Queue<Mago> codaTurni, AtomicBoolean duelloInCorso) {
        if (SquadMaghi.isEmpty()) {
            int nSquad, nMaghi;
            do {
                System.out.println("Inserisci quante Squadre vuoi generare (da 2 a 8) :");
                nSquad = Tools.getIntero();
            } while (nSquad < 2 || nSquad > 8);

            do {
                System.out.println("Inserisci quanti Maghi vuoi per Squadra (da 2 a 4) :");
                nMaghi = Tools.getIntero();
            } while (nMaghi < 2 || nMaghi > 4);

            for (int i = 0; i < nSquad; i++) {
                Vector<Mago> v = new Vector<>();
                for (int j = 0; j < nMaghi; j++) {
                    String nomeMago = ListaNomiMaghi.getNomeMago();
                    Mago nuovoMago = new Mago(nomeMago, 35, 30, codaTurni, duelloInCorso, i, livello);
                    v.add(nuovoMago);
                    ListaMaghi.addMago(nuovoMago);
                }
                SquadMaghi.generaSquadre(v);
            }
            System.out.println("Fatto!");
        } else {
            System.out.println("Hai già delle squadre create, scatena il RAGNAROK prima di crearne altre!");
        }
    }

    private void avviaRagnarok(AtomicBoolean duelloInCorso) {
        duelloInCorso.set(true);
        if (SquadMaghi.isEmpty()) {
            System.out.println("Non ci sono squadre! Genera prima le squadre.");
        } else {
            for (Mago m : ListaMaghi.getMaghi()) {
                m.assegnaNuovoNemico2();
            }

            for (Mago m : ListaMaghi.getMaghi()) {
                m.start();
            }

            while (duelloInCorso.get()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            magnaMorti();
            
            for (Mago m : ListaMaghi.getMaghi()) {
                if (m.isAlive()) {
                    m.interrupt();
                }
            }

            ListaMaghi.clean();
            SquadMaghi.pulisciSquadreVuote();
            System.out.println();
            System.out.println("Ragnarok finito, la squadra vincitrice è stata congedata nel mondo della magia!");
        }
    }

    private void magnaMorti() {
        synchronized (ListaMaghi.getMaghi()) {
            Vector<Mago> copiaMaghi = new Vector<>(ListaMaghi.getMaghi());

            Vector<Mago> morti = new Vector<>();
            for (Mago m : copiaMaghi) {
                if (m.getVita() <= 0) {
                    morti.add(m);
                }
            }

            for (Mago morto : morti) {
                ListaMaghi.removeMago(morto);
                SquadMaghi.rimuoviMago(morto);
            }
        }
    }
}