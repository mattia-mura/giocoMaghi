package org.classi;

import java.util.Queue;
import java.util.Random;
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

    public Mago(String nome, int max, Queue<Mago> codaTurni, AtomicBoolean duelloInCorso) {
        this.nome = nome;
        this.codaTurni = codaTurni;
        this.duelloInCorso = duelloInCorso;
        int[] ar = generaStats(max);
        this.forza = ar[0];
        this.resistenza = ar[1];
        this.velocità = ar[2];
        this.vita = ar[3] * 10;
        this.totale = (max == -1) ? ar[4] : max;
        this.abilita = generaAbilita();
        this.nemico = null;
    }

    private int[] generaStats(int max) {
        Random rnd = new Random();
        if (max == -1) {
            int a = rnd.nextInt(10) + 1;
            int b = rnd.nextInt(10) + 1;
            int c = rnd.nextInt(10) + 1;
            int d = rnd.nextInt(10) + 1;
            int sum = a + b + c + d;
            return new int[]{a, b, c, d, sum};
        } else {
            int sum;
            int a, b, c, d;
            do {
                a = rnd.nextInt(10) + 1;
                b = rnd.nextInt(10) + 1;
                c = rnd.nextInt(10) + 1;
                d = rnd.nextInt(10) + 1;
                sum = a + b + c + d;
            } while (sum != max);
            return new int[]{a, b, c, d};
        }
    }

    private String generaAbilita() {
        switch (new Random().nextInt(5) + 1) {
            case 1:
                return "Addestrato";
            case 2:
                return "Adrenalinico";
            case 3:
                return "Corpo d'acciaio";
            case 4:
                return "Ispirato dalla magia";
            default:
                return "Volontà insormontabili";
        }
    }

    private synchronized int calcolaAttacco() {
        int dado = rnd.nextInt(10) + 1;
        int bonus = forza / 4;
        int attacco = dado + bonus;

        // Ispirato dalla magia
        boolean ignoraResistenza = false;
        if (abilita.equals("Ispirato dalla magia")) {
            int chance = rnd.nextInt(100) + 1;
            int percentuale = 5 + rnd.nextInt(6); // 5-10%
            if (chance <= percentuale) {
                System.out.println(nome + " attiva 'Ispirato dalla magia': ignora resistenza!");
                Trascrittore.scrivi(nome + " attiva 'Ispirato dalla magia': ignora resistenza!");
                ignoraResistenza = true;
            }
        }

        if (!ignoraResistenza) {
            double trntResistenza = (nemico.getResistenza() / 100.0) * 30.0;
            if (attacco < nemico.getResistenza()) {
                if (attacco < nemico.getResistenza() + trntResistenza) {
                    attacco = (int) ((attacco / 100.0) * 25);
                } else {
                    attacco = (int) ((attacco / 100.0) * 50);
                }
            } else {
                attacco = (int) ((attacco / 100.0) * 75);
            }
        }

        return attacco;
    }

    @Override
    public void run() {
        try {
            while (duelloInCorso.get() && vita > 0 && nemico.getVita() > 0) {

                long delay = 5000L / Math.max(1, velocità);
                Thread.sleep(delay);

                synchronized (codaTurni) {
                    if (!duelloInCorso.get()) break;
                    codaTurni.add(this);
                    System.out.println(nome + " è pronto e si mette in coda.");
                    Trascrittore.scrivi(nome + " è pronto e si mette in coda.");
                    while (codaTurni.peek() != this && duelloInCorso.get()) {
                        codaTurni.wait();
                    }
                    if (!duelloInCorso.get()) break;
                }

                // Applico abilità prima dell'attacco
                applicaAbilita();

                int danno = calcolaAttacco();
                System.out.println(nome + " infligge " + danno + " a " + nemico.getNome());
                Trascrittore.scrivi(nome + " infligge " + danno + " a " + nemico.getNome());
                nemico.subisciDanno(danno);

                synchronized (codaTurni) {
                    codaTurni.poll();
                    codaTurni.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (vita > 0) {
                System.out.println(nome + " dichiara: ho vinto lo scontro!");
                Trascrittore.scrivi(nome + " dichiara: ho vinto lo scontro!");
            } else {
                System.out.println(nome + " dichiara: sono stato sconfitto.");
                Trascrittore.scrivi(nome + " dichiara: sono stato sconfitto.");
            }
        }
//        try {
//            wait(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        Trascrittore.chiudi();
    }

    private void applicaAbilita() {
        switch (abilita) {
            case "Addestrato":
                forza = Math.min(forza + 2, 10);
                resistenza = Math.min(resistenza + 2, 10);
                velocità = Math.min(velocità + 2, 10);
                System.out.println(nome + " usa 'Addestrato': statistiche aumentate!");
                Trascrittore.scrivi(nome + " usa 'Addestrato': statistiche aumentate!");
                break;

            case "Adrenalinico":
                if (vita < (totale * 10 * 0.3)) {
                    if (!adrenalinaAttivata) {
                        adrenalinaAttivata = true;
                        turniAdrenalina = 3;
                        velocità *= 2;
                        Trascrittore.scrivi(nome + " entra in modalità 'Adrenalinico': velocità raddoppiata!");
                    }
                }
                if (turniAdrenalina > 0) {
                    turniAdrenalina--;
                    if (turniAdrenalina == 0) {
                        velocità /= 2;
                        Trascrittore.scrivi(nome + " termina 'Adrenalinico': velocità ripristinata.");
                    }
                }
                break;

            case "Volontà insormontabili":
                int chance = rnd.nextInt(100) + 1;
                int percentuale = 10 + rnd.nextInt(6); // 10-15%
                if (chance <= percentuale) {
                    int guarigione = rnd.nextInt(4) + 1;
                    vita += guarigione;
                    System.out.println(nome + " usa 'Volontà insormontabili': recupera " + guarigione + " punti vita!");
                    Trascrittore.scrivi(nome + " usa 'Volontà insormontabili': recupera " + guarigione + " punti vita!");
                }
                break;
        }
    }

    private synchronized void subisciDanno(int danno) {
        // Corpo d'acciaio
        if (abilita.equals("Corpo d'acciaio")) {
            int chance = rnd.nextInt(100) + 1;
            int percentuale = 15 + rnd.nextInt(6); // 15-20%
            if (chance <= percentuale) {
                danno = (int) (danno * 0.75);
                System.out.println(nome + " attiva 'Corpo d'acciaio': danno ridotto!");
                Trascrittore.scrivi(nome + " attiva 'Corpo d'acciaio': danno ridotto!");
            }
        }

        this.vita -= danno;
        System.out.println(this.nome + " ora ha " + this.vita + " vita.");
        Trascrittore.scrivi(this.nome + " ora ha " + this.vita + " vita.");

        if (this.vita <= 0) {
            System.out.println(this.nome + " è stato sconfitto!");
            Trascrittore.scrivi(this.nome + " è stato sconfitto!");
            duelloInCorso.set(false);
            synchronized (codaTurni) {
                codaTurni.clear();
                codaTurni.notifyAll();
            }
        }
    }

    // Getter e setter
    public int getVita() {
        return vita;
    }

    public int getResistenza() {
        return resistenza;
    }

    public String getNome() {
        return nome;
    }

    public int getTotale() {
        return totale;
    }

    public void setNemico(Mago nemico) {
        this.nemico = nemico;
    }

    public Mago getNemico() {
        return this.nemico;
    }

    public String toString() {
        return '\n' +
                "-Nome=" + nome + '\n' +
                "-Vita=" + vita + '\n' +
                "-Forza=" + forza + '\n' +
                "-Resistenza=" + resistenza + '\n' +
                "-Velocità=" + velocità + '\n' +
                "-Abilita='" + abilita + '\'' + '\n' +
                "---------------------------------------" + '\n';
    }
}
