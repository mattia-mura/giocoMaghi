package org.classi;

import java.util.Queue;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;

public class Mago extends Thread {
    private final String nome;
    private int forza;
    private int resistenza;
    private int velocità;
    private int vita;
    private Mago nemico;
    private final String abilita;
    private final Queue<Mago> codaTurni;
    private final int totale;
    private final AtomicBoolean duelloInCorso;
    private int turniAdrenalina = 0;
    private final Random rnd = new Random();
    private boolean adrenalinaAttivata = false;
    private int velocitaOriginale;
    private String elemento;
    private int squadra;
    private static final String[] COLORI = {
            "\033[0m",  // Text Reset
            "\033[0;31m",     // RED
            "\033[0;32m",   // GREEN
            "\033[0;33m",  // YELLOW
            "\033[0;34m",    // BLUE
            "\033[0;35m",  // PURPLE
            "\033[0;36m",    // CYAN
            "\033[0;37m",   // WHITE
            "\033[0;30m",   // BLACK
    };
    private String coloreSquadra;
    private String coloreElemento;
    private int livelloVisioneLog;


    public Mago(String nome, int max, int min, Queue<Mago> codaTurni, AtomicBoolean duelloInCorso, int squadra, int livelloVisioneLog) {
        this.nome = nome;
        this.codaTurni = codaTurni;
        this.duelloInCorso = duelloInCorso;
        int[] ar = generaStats(max, min);
        this.forza = ar[0];
        this.resistenza = ar[1];
        this.velocità = ar[2];
        this.vita = ar[3] * 10;
        this.totale = ar[4];
        this.abilita = generaAbilita();
        this.nemico = null;
        this.velocitaOriginale = velocità;
        this.elemento = generaElemento();
        this.squadra = squadra;
        setColori();
        this.livelloVisioneLog = livelloVisioneLog;
    }

    private void setColori() {
        if (this.squadra <= -1){
            this.coloreSquadra = COLORI[0];
        } else {
            this.coloreSquadra = COLORI[(this.squadra + 1)];
        }
        switch (this.elemento) {
            case "Ghiaccio": this.coloreElemento = "\033[0;96m"; break;
            case "Fulmine": this.coloreElemento = "\033[0;94m"; break;
            case "Fuoco":   this.coloreElemento = "\033[0;91m"; break;
            case "Oscuro":  this.coloreElemento = "\033[1;35m"; break;
            default:         this.coloreElemento = "\033[1;33m"; // Terra (giallo)
        }
    }

    private int[] generaStats(int max, int min) {
        int sum, a, b, c, d;
        do {
            a = rnd.nextInt(10) + 1;
            b = rnd.nextInt(10) + 1;
            c = rnd.nextInt(10) + 1;
            d = rnd.nextInt(10) + 1;
            sum = a + b + c + d;
        } while (sum > max || sum < min);
        return new int[]{a, b, c, d, sum};
    }

    private String generaAbilita() {
        switch (rnd.nextInt(5) + 1) {
            case 1: return "Addestrato";
            case 2: return "Adrenalinico";
            case 3: return "Corpo d'acciaio";
            case 4: return "Ispirato dalla magia";
            default: return "Volontà insormontabili";
        }
    }

    private String generaElemento() {
        switch (rnd.nextInt(5) + 1) {
            case 1: return "Ghiaccio";
            case 2: return "Fulmine";
            case 3: return "Fuoco";
            case 4: return "Oscuro";
            default: return "Terra";
        }
    }

    private boolean superficacia(String elementoB, String elementoA) {
        return (elementoB.equals("Fuoco") && elementoA.equals("Ghiaccio")) ||
                (elementoB.equals("Ghiaccio") && elementoA.equals("Fulmine")) ||
                (elementoB.equals("Fulmine") && elementoA.equals("Oscuro")) ||
                (elementoB.equals("Oscuro") && elementoA.equals("Terra")) ||
                (elementoB.equals("Terra") && elementoA.equals("Fuoco"));
    }

    private synchronized int calcolaAttacco() {
        int dado = rnd.nextInt(10) + 1;
        int bonus = forza / 4;
        int attacco = dado + bonus;
        boolean ignoraResistenza = false;

        if (abilita.equals("Ispirato dalla magia")) {
            int chance = rnd.nextInt(100) + 1;
            int percentuale = 5 + rnd.nextInt(6);
            if (chance <= percentuale) {
                if(this.livelloVisioneLog == 1){System.out.println(coloreSquadra + nome + COLORI[0] + " attiva 'Ispirato dalla magia': ignora resistenza!");}
                ignoraResistenza = true;
            }
        }

        int resistenzaNemico = nemico.getResistenza();
        if (superficacia(this.elemento, this.nemico.getElemento())) {
            resistenzaNemico -= 2;
            if (resistenzaNemico < 0) resistenzaNemico = 0;
        }

        if (!ignoraResistenza) {
            double percentualeResistenza = (resistenzaNemico / 100.0) * 30.0;
            if (attacco < resistenzaNemico) {
                if (attacco < resistenzaNemico + percentualeResistenza) attacco = (int) ((attacco / 100.0) * 25);
                else attacco = (int) ((attacco / 100.0) * 50);
            } else {
                attacco = (int) ((attacco / 100.0) * 75);
            }
        }

        return attacco;
    }

    @Override
    public void run() {
        try {
            while (duelloInCorso.get() && vita > 0 && !Thread.currentThread().isInterrupted()) {
                Thread.sleep(5000L / Math.max(1, velocità));

                synchronized (codaTurni) {
                    if (!duelloInCorso.get() || Thread.currentThread().isInterrupted()) break;
                    codaTurni.add(this);
                    while (codaTurni.peek() != this && duelloInCorso.get() && !Thread.currentThread().isInterrupted()) {
                        codaTurni.wait();
                    }
                    if (!duelloInCorso.get() || Thread.currentThread().isInterrupted()) break;
                }

                applicaAbilita();

                // Verifica e assegna un nuovo nemico se necessario
                if (nemico == null || nemico.getVita() <= 0 || nemico.equals(this) ||
                        (nemico.getSquadra() >= 0 && this.getSquadra() >= 0 && nemico.getSquadra() == this.getSquadra())) {
                    assegnaNuovoNemico2();
                    if (nemico == null) {
                        verificaCondizioniTerminazione();
                        synchronized (codaTurni) {
                            codaTurni.poll();
                            codaTurni.notifyAll();
                        }
                        continue;
                    }
                }

                // Doppio controllo di sicurezza
                if (nemico == this) {
                    System.err.println("ERRORE CRITICO: " + this.nome + " sta per attaccare se stesso!");
                    System.err.println("Forzo una nuova ricerca del nemico...");
                    assegnaNuovoNemico2();
                    synchronized (codaTurni) {
                        codaTurni.poll();
                        codaTurni.notifyAll();
                    }
                    continue;
                }

                // Controllo squadra (solo per squadre valide >= 0)
                if (this.squadra >= 0 && nemico.getSquadra() >= 0 && this.squadra == nemico.getSquadra()) {
                    System.err.println("ERRORE CRITICO: " + this.nome + " (squadra " + this.squadra +
                            ") sta per attaccare " + nemico.nome + " (squadra " + nemico.squadra + ") della stessa squadra!");
                    System.err.println("Forzo una nuova ricerca del nemico...");
                    assegnaNuovoNemico2();
                    synchronized (codaTurni) {
                        codaTurni.poll();
                        codaTurni.notifyAll();
                    }
                    continue;
                }

                int danno = calcolaAttacco();
                if (nemico != null) {
                    if (this.livelloVisioneLog<=2){
                        System.out.println(coloreSquadra + nome + COLORI[0] + " infligge " + coloreElemento + danno + COLORI[0] +
                                " a " + nemico.getColoreSquadra() + nemico.getNome() + COLORI[0]);
                    }
                    nemico.infliggiDanno(danno);
                }

                synchronized (codaTurni) {
                    codaTurni.poll();
                    codaTurni.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (this.livelloVisioneLog<=2){
                if (this.squadra >= -1) {
                    System.out.println(coloreSquadra + nome + (vita > 0 ? " dichiara: ho vinto il duello!" : " dichiara: sono stato sconfitto.") + "\u001B[0m");
                }
                else{
                    System.out.println(coloreElemento + nome + (vita > 0 ? " dichiara: ho vinto il duello!" : " dichiara: sono stato sconfitto.") + "\u001B[0m");
                }
            }
        }
    }

    private void applicaAbilita() {
        switch (abilita) {
            case "Addestrato":
                forza = Math.min(forza + 2, 10);
                resistenza = Math.min(resistenza + 2, 10);
                velocità = Math.min(velocità + 2, 10);
                if(this.livelloVisioneLog == 1){System.out.println(coloreSquadra + nome + "\u001B[0m usa 'Addestrato': statistiche aumentate!");}
                Trascrittore.scrivi(nome + " usa 'Addestrato': statistiche aumentate!");
                break;

            case "Adrenalinico":
                if (vita < (totale * 10 * 0.3)) {
                    if (!adrenalinaAttivata) {
                        adrenalinaAttivata = true;
                        turniAdrenalina = 3;
                        velocità = velocitaOriginale * 2;
                        if(this.livelloVisioneLog == 1){System.out.println(coloreSquadra + nome + "\u001B[0m entra in modalità 'Adrenalinico'!");}
                        Trascrittore.scrivi(nome + " entra in modalità 'Adrenalinico'!");
                    }
                }
                if (turniAdrenalina > 0) {
                    turniAdrenalina--;
                    if (turniAdrenalina == 0) {
                        velocità = velocitaOriginale;
                        if(this.livelloVisioneLog == 1){System.out.println(coloreSquadra + nome + "\u001B[0m termina 'Adrenalinico': velocità normale.");}
                        Trascrittore.scrivi(nome + " termina 'Adrenalinico': velocità normale.");
                    }
                }
                break;

            case "Volontà insormontabili":
                int chance = rnd.nextInt(100) + 1;
                int percentuale = 10 + rnd.nextInt(6);
                if (chance <= percentuale) {
                    int guarigione = rnd.nextInt(4) + 1;
                    vita += guarigione;
                    if(this.livelloVisioneLog == 1){System.out.println(coloreSquadra + nome + "\u001B[0m usa 'Volontà insormontabili': recupera " + guarigione + " vita!");}
                    Trascrittore.scrivi(nome + " usa 'Volontà insormontabili': recupera " + guarigione + " vita!");
                }
                break;
        }
    }

    private synchronized void infliggiDanno(int danno) {
        // Applica l'abilità "Corpo d'acciaio" se il mago che riceve il danno ce l'ha
        if (this.abilita.equals("Corpo d'acciaio")) {
            int chance = rnd.nextInt(100) + 1;
            int percentuale = 15 + rnd.nextInt(6);
            if (chance <= percentuale) {
                danno = (int) (danno * 0.75);
                if (this.livelloVisioneLog == 1) {
                    System.out.println(coloreSquadra + nome + "\u001B[0m attiva 'Corpo d'acciaio': danno ridotto!");
                }
                Trascrittore.scrivi(nome + " attiva 'Corpo d'acciaio': danno ridotto!");
            }
        }

        // Infliggi il danno a questo mago (quello che riceve il danno)
        this.vita -= danno;
        Trascrittore.scrivi(this.nome + " ora ha " + this.vita + " vita.");
    }

    // Nuovo metodo per verificare le condizioni di terminazione con debug
    private synchronized void verificaCondizioniTerminazione() {
        if (ListaMaghi.getLength() == 1) {
            System.out.println(ListaMaghi.getMago(0).coloreSquadra + "La battaglia è finita! " + ListaMaghi.getMago(0).getNome() + " è il vincitore!" + "\u001B[0m");
            Trascrittore.scrivi("La battaglia è finita! " + ListaMaghi.getMago(0).getNome() + " è il vincitore!");
            duelloInCorso.set(false);
            synchronized (codaTurni) {
                codaTurni.clear();
                codaTurni.notifyAll();
            }
        } else if (SquadMaghi.vettoreDiVettori.size() == 1 && !SquadMaghi.isEmpty()) {
            Vector<Mago> squadraVincente = SquadMaghi.vettoreDiVettori.get(0);
            if (!squadraVincente.isEmpty()) {
                int numeroSquadra = squadraVincente.get(0).getSquadra();
                System.out.println(this.coloreSquadra + "La squadra " + numeroSquadra + " ha vinto il Ragnarok!" + "\u001B[0m");
                Trascrittore.scrivi("La squadra " + numeroSquadra + " ha vinto il Ragnarok!");
                duelloInCorso.set(false);
                synchronized (codaTurni) {
                    codaTurni.clear();
                    codaTurni.notifyAll();
                }
            }
        } else if (ListaMaghi.getLength() > 1) {
            int squadraComune = -2;
            boolean tuttaStessaSquadra = true;

            for (Mago m : ListaMaghi.getMaghi()) {
                if (m.getVita() <= 0) continue;
                if (squadraComune == -2) {
                    squadraComune = m.getSquadra();
                } else if (squadraComune != m.getSquadra()) {
                    tuttaStessaSquadra = false;
                    break;
                }
            }

            if (tuttaStessaSquadra && squadraComune != -1) {
                System.out.println(this.coloreSquadra + "La squadra " + squadraComune + " ha vinto il Ragnarok!" + "\u001B[0m");
                Trascrittore.scrivi("La squadra " + squadraComune + " ha vinto il Ragnarok!");
                duelloInCorso.set(false);
                synchronized (codaTurni) {
                    codaTurni.clear();
                    codaTurni.notifyAll();
                }
            }
        }
    }

    public synchronized void assegnaNuovoNemico2() {
        Vector<Mago> candidati = new Vector<>();

        if (ListaMaghi.getLength() <= 1) {
            Trascrittore.scrivi("Non ci sono più maghi con cui combattere!");
            this.nemico = null;
            return;
        }

        // Trova tutti i maghi validi come nemici
        for (Mago m : ListaMaghi.getMaghi()) {
            if (!m.equals(this) && m.getVita() > 0) {
                // Se entrambi hanno squadre valide (>= 0), devono essere di squadre diverse
                if (this.squadra >= 0 && m.getSquadra() >= 0) {
                    if (this.squadra != m.getSquadra()) {
                        candidati.add(m);
                    }
                } else {
                    // Se almeno uno ha squadra -1 (individuale), può essere nemico
                    candidati.add(m);
                }
            }
        }

        if (candidati.isEmpty()) {
            this.nemico = null;
            Trascrittore.scrivi(this.nome + " non ha più nemici validi!");
        } else {
            this.nemico = candidati.get(rnd.nextInt(candidati.size()));
            if (this.livelloVisioneLog == 1) {
                System.out.println(coloreSquadra + nome + "\u001B[0m" + " ha scelto " +
                        nemico.getColoreSquadra() + nemico.getNome() + "\u001B[0m" + " come nuovo nemico!");
            }
        }
    }

    public void powerUP(Mago maghettoFurbetto) {
        int x = (int) (Math.random() * 4 + 1);
        int y = 0;
        do {
            y = (int) (Math.random() * 4 + 1);
        } while (x == y);

        if (x == 1 || y == 1) { maghettoFurbetto.addForza(); }
        if (x == 2 || y == 2) { maghettoFurbetto.addResistenza(); }
        if (x == 3 || y == 3) { maghettoFurbetto.addVita(); }
        if (x == 4 || y == 4) { maghettoFurbetto.addVelocita(); }
    }

    // Getter e setter
    public synchronized int getVita() { return vita; }

    public synchronized String getAbilita(){return abilita;}
    public synchronized int getResistenza() { return resistenza; }
    public synchronized void addForza(){this.forza++;}
    public synchronized void addResistenza(){this.resistenza++;}
    public synchronized void addVita(){this.vita+=10;}
    public synchronized void addVelocita(){this.velocità++;}
    public synchronized String getNome() { return nome; }
    public synchronized int getSquadra() { return squadra; }
    public synchronized void setSquadra(int squadra) {
        this.squadra = squadra;
        setColori(); // Aggiorna i colori quando cambia squadra
    }
    public synchronized void setNemico(Mago nemico) { this.nemico = nemico; }
    public synchronized Mago getNemico() { return nemico; }
    public synchronized String getElemento() {return this.elemento;}
    public synchronized String getColoreSquadra(){return this.coloreSquadra;}
    public synchronized boolean isNemicoMorto(){
        if (this.nemico == null || this.nemico.getVita() <= 0){return true;}
        return false;
    }

    public synchronized void setVita(int danno){this.vita = this.vita-danno;}

    @Override
    public String toString() {
        String nomeNemico = (nemico != null) ? nemico.getNome() : "Nessuno";
        return "\n-Nome=" + nome +
                "\n-Nome del nemico=" + nomeNemico +
                "\n-Vita=" + vita +
                "\n-Forza=" + forza +
                "\n-Resistenza=" + resistenza +
                "\n-Velocità=" + velocità +
                "\n-Abilita='" + abilita + '\'' +
                "\n-Elemento='" + elemento + '\'' +
                "\n---------------------------------------\n";
    }
}