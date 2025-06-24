package org.classi;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Mago extends Thread {
    private final String nome;
    private final int forza;
    private final int resistenza;
    private final int velocità;
    private int vita;
    private Mago nemico;
    private final String abilita;
    private final Queue<Mago> codaTurni;
    private final int totale;
    private final AtomicBoolean duelloInCorso;

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
        int dado = new Random().nextInt(10) + 1;
        int bonus = forza / 4;
        int attacco = dado + bonus;
        double trntResistenza = (nemico.getResistenza() / 100.0) * 30.0;
        if (attacco < nemico.getResistenza()) {
            if (attacco < nemico.getResistenza() + trntResistenza) {
                attacco = (int) ((attacco / 100.0) * 25);
            } else {
                attacco = (int) ((attacco / 100.0) * 50);
            }
        } else if (attacco > nemico.getResistenza()) {
            attacco = (int) ((attacco / 100.0) * 75);
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
                    // [HIGHLIGHT] evitiamo di mettersi in coda se il duello è già finito
                    if (!duelloInCorso.get()) break;
                    codaTurni.add(this);
                    System.out.println(nome + " è pronto e si mette in coda.");
                    while (codaTurni.peek() != this && duelloInCorso.get()) {
                        codaTurni.wait();
                    }
                    if (!duelloInCorso.get()) break;
                }

                int danno = calcolaAttacco();
                System.out.println(nome + " infligge " + danno + " a " + nemico.getNome());
                nemico.subisciDanno(danno);

                synchronized (codaTurni) {
                    codaTurni.poll();
                    codaTurni.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            // Dichiarazione di vittoria o sconfitta dal thread stesso
            if (vita > 0) {
                System.out.println(nome + " dichiara: ho vinto il duello!");
            } else {
                System.out.println(nome + " dichiara: sono stato sconfitto.");
            }
        }
    }


    private synchronized void subisciDanno(int danno) {
        this.vita -= danno;
        System.out.println(this.nome + " ora ha " + this.vita + " vita.");
        if (this.vita <= 0) {
            System.out.println(this.nome + " è stato sconfitto!");
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

    public String toString() {
        return '\n' +
                "-Nome=" + nome + '\n' +
                "-Vita=" + vita + '\n' +
                "-Forza=" + forza + '\n' +
                "-Resistenza=" + resistenza + '\n' +
                "-Velocità=" + velocità + '\n' +
                "-Abilita='" + abilita + '\'' + '\n' +
                //"-Totale=" + totale + '\n'+
                "---------------------------------------" + '\n';
    }
}