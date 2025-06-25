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

//    private static final Vector<Mago> maghi = ListaMaghi.getMaghi();
//            new Vector<>();

    public Mago(String nome, int max, int min, Queue<Mago> codaTurni, AtomicBoolean duelloInCorso) {
        this.nome = nome;
        this.codaTurni = codaTurni;
        this.duelloInCorso = duelloInCorso;
        int[] ar = generaStats(max,min);
        this.forza = ar[0];
        this.resistenza = ar[1];
        this.velocità = ar[2];
        this.vita = ar[3] * 10;
        this.totale = ar[4];
        this.abilita = generaAbilita();
        this.nemico = null;
        this.velocitaOriginale = velocità;
        this.elemento = generaElemento();
    }

    private int[] generaStats(int max, int min) {
//        if (max == -1) {
//            int a = rnd.nextInt(10) + 1;
//            int b = rnd.nextInt(10) + 1;
//            int c = rnd.nextInt(10) + 1;
//            int d = rnd.nextInt(10) + 1;
//            int sum = a + b + c + d;
//            return new int[]{a, b, c, d, sum};
//        } else {}
        int sum;
        int a, b, c, d;
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
            default : return "Terra";
        }
    }

    private boolean superficacia(String elementoB, String elementoA){
        if ( elementoB.equals("Fuoco") && elementoA.equals("Ghiaccio") ) {return true;}
        if ( elementoB.equals("Ghiaccio") && elementoA.equals("Fulmine") ) {return true;}
        if ( elementoB.equals("Fulmine") && elementoA.equals("Oscuro") ) {return true;}
        if ( elementoB.equals("Oscuro") && elementoA.equals("Terra") ) {return true;}
        if ( elementoB.equals("Terra") && elementoA.equals("Fuoco") ) {return true;}
        return false;
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
                System.out.println(nome + " attiva 'Ispirato dalla magia': ignora resistenza!");
                Trascrittore.scrivi(nome + " attiva 'Ispirato dalla magia': ignora resistenza!");
                ignoraResistenza = true;
            }
        }

        int resistenzaNemico = nemico.getResistenza();
        if ( superficacia(this.elemento,this.nemico.getElemento()) ){
            resistenzaNemico -= 2;
            if ( resistenzaNemico < 0 ){resistenzaNemico=0;}
        }
        if (!ignoraResistenza) {
            double trntResistenza = (resistenzaNemico / 100.0) * 30.0;
            if (attacco < resistenzaNemico) {
                if (attacco < resistenzaNemico + trntResistenza) {
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
            while (duelloInCorso.get() && vita > 0) {
                long delay = 5000L / Math.max(1, velocità);
                Thread.sleep(delay);

                synchronized (codaTurni) {
                    if (!duelloInCorso.get()) break;
                    codaTurni.add(this);
                    while (codaTurni.peek() != this && duelloInCorso.get()) {
                        codaTurni.wait();
                    }
                    if (!duelloInCorso.get()) break;
                }

                applicaAbilita();

                if (nemico == null || nemico.getVita() <= 0) {
                    assegnaNuovoNemico2();
                    if (nemico == null) {
                        synchronized (codaTurni) {
                            codaTurni.poll();
                            codaTurni.notifyAll();
                        }
                        continue;
                    }
                }

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
                System.out.println(nome + " dichiara: ho vinto il duello!");
                Trascrittore.scrivi(nome + " dichiara: ho vinto il duello!");
            } else {
                System.out.println(nome + " dichiara: sono stato sconfitto.");
                Trascrittore.scrivi(nome + " dichiara: sono stato sconfitto.");
            }
        }
    }

    private void applicaAbilita() {
        switch (abilita) {
            case "Addestrato":
                forza = Math.min(forza + 2, 10);
                resistenza = Math.min(resistenza + 2, 10);
                velocità = Math.min(velocità + 2, 10);
                Trascrittore.scrivi(nome + " usa 'Addestrato': statistiche aumentate!");
                break;

            case "Adrenalinico":
                if (vita < (totale * 10 * 0.3)) {
                    if (!adrenalinaAttivata) {
                        adrenalinaAttivata = true;
                        turniAdrenalina = 3;
                        velocità = velocitaOriginale * 2;
                        Trascrittore.scrivi(nome + " entra in modalità 'Adrenalinico'!");
                    }
                }
                if (turniAdrenalina > 0) {
                    turniAdrenalina--;
                    if (turniAdrenalina == 0) {
                        velocità = velocitaOriginale;
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
                    Trascrittore.scrivi(nome + " usa 'Volontà insormontabili': recupera " + guarigione + " vita!");
                }
                break;
        }
    }

    private synchronized void subisciDanno(int danno) {
        if (abilita.equals("Corpo d'acciaio")) {
            int chance = rnd.nextInt(100) + 1;
            int percentuale = 15 + rnd.nextInt(6);
            if (chance <= percentuale) {
                danno = (int) (danno * 0.75);
                Trascrittore.scrivi(nome + " attiva 'Corpo d'acciaio': danno ridotto!");
            }
        }

        this.vita -= danno;
        Trascrittore.scrivi(this.nome + " ora ha " + this.vita + " vita.");

        if (this.vita <= 0) {
            Trascrittore.scrivi(this.nome + " è stato sconfitto!");
//            maghi.remove(this);
            ListaMaghi.removeMago(this);
            for (Mago m : ListaMaghi.getMaghi()) {
                if (m.getNemico() == this) {
                    m.assegnaNuovoNemico2();
                }
            }

            for (Mago m : ListaMaghi.getMaghi()) {
                if (m.getNemico() == this) {
                    powerUP(m);
                    System.out.println(m.getNome() + " ha ricevuto un powerUP!");
                    Trascrittore.scrivi(m.getNome() + " ha ricevuto un powerUP!");
                }
            }

//            for (Mago m : ListaMaghi.getMaghi()) {
//                if (m.getNemico() == this) {
//                    m.assegnaNuovoNemico();
//                }
//            }

            if (ListaMaghi.getLength() == 1) {
                Trascrittore.scrivi("Il duello è finito! " + ListaMaghi.getMago(0) + " è il vincitore!");
                duelloInCorso.set(false);
                synchronized (codaTurni) {
                    codaTurni.clear();
                    codaTurni.notifyAll();
                }
            }
        }
    }

    public synchronized void assegnaNuovoNemico() {

            if (ListaMaghi.getLength() <= 1) {
                nemico = null;
            } else {
                Mago nuovoNemico;
                do {
                    nuovoNemico = ListaMaghi.getMago(rnd.nextInt(ListaMaghi.getLength()));
                } while (nuovoNemico == this || nuovoNemico.getVita() <= 0);
                nemico = nuovoNemico;
                Trascrittore.scrivi(nome + " ha scelto " + nemico.getNome() + " come nuovo nemico!");
            }
    }

    public synchronized void assegnaNuovoNemico2() {
        if (ListaMaghi.getLength() <= 1) {
            nemico = null;
            return;
        }
        Vector <Mago> potenzialiNemici = new Vector<Mago>();
        for (Mago m : ListaMaghi.getMaghi()) {
            if (m != this && m.getVita() > 0) {
                potenzialiNemici.add(m);
            }
        }
        if (potenzialiNemici.isEmpty()) {
            nemico = null;
            return;
        }
        nemico = potenzialiNemici.get(rnd.nextInt(potenzialiNemici.size()));
        Trascrittore.scrivi(nome + " ha scelto " + nemico.getNome() + " come nuovo nemico!");
    }


    public void powerUP( Mago maghettoFurbetto){
        int x = (int) (Math.random()*4+1);
        int y = 0;
        do{
            y = (int) (Math.random()*4+1);
        }while(x == y);

        if (x == 1 || y == 1){maghettoFurbetto.addForza();}
        if (x == 2 || y == 2){maghettoFurbetto.addResistenza();}
        if (x == 3 || y == 3){maghettoFurbetto.addVita();}
        if (x == 4 || y == 4){maghettoFurbetto.addVelocita();}
    }

    // Getter e setter
    public int getVita() { return vita; }
    public int getResistenza() { return resistenza; }
    public void addForza(){this.forza++;}
    public void addResistenza(){this.resistenza++;}
    public void addVita(){this.vita+=10;}
    public void addVelocita(){this.velocità++;}
    public String getNome() { return nome; }
    public int getTotale() { return totale; }
    public void setNemico(Mago nemico) { this.nemico = nemico; }
    public Mago getNemico() { return nemico; }
    public String getElemento() {return this.elemento;}

    @Override
    public String toString() {
        return '\n' +
                "-Nome=" + nome + '\n' +
                "-Vita=" + vita + '\n' +
                "-Forza=" + forza + '\n' +
                "-Resistenza=" + resistenza + '\n' +
                "-Velocità=" + velocità + '\n' +
                "-Abilita='" + abilita + '\'' + '\n' +
                "-Elemento='" + elemento + '\'' + '\n' +
                "---------------------------------------" + '\n';
    }
}