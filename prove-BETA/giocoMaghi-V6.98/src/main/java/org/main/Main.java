
package org.main;

import org.classi.*;
        import java.util.Vector;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) {
        Scanner tastiera = new Scanner(System.in);
        Menu menu = new Menu();
        int livello = 1;
        int scelta;
        ListaNomiMaghi.generaNomiMaghi();
        boolean prima = true;

        try {
            do {
                Vector<Mago> codaTurni = new Vector<>();
                AtomicBoolean duelloInCorso = new AtomicBoolean(true);

                if(prima) {
                    System.out.println("Benvenuto nel Gioco dei Maghi");
                    System.out.println();
                    livello = menu.menuLivello();
                    System.out.println("Grazie per aver inserito la tua preferenza, potrai modificarla in ogni momento dal menù principale!");
                    prima = false;
                }

                scelta = menu.menu();

                switch (scelta) {
                    case 1: {
                        int sceltaMenu1;
                        do {
                            sceltaMenu1 = menu.menu1();
                            switch (sceltaMenu1) {
                                case 1: {
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
                                    int squadMaghetto = -1*(ListaMaghi.getLength()+1);
                                    if (ListaMaghi.addMago(new Mago(nome, 35, 30, codaTurni, duelloInCorso, squadMaghetto, livello))) {
                                        System.out.println("Mago aggiunto con successo");
                                    } else {
                                        System.out.println("Impossibile aggiungere il Mago, riprova!");
                                    }
                                }
                                break;

                                case 2: {
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
                                break;

                                case 3: {
                                    System.out.println("\n-- Schede iniziali --");
                                    ListaMaghi.print();
                                    System.out.println();
                                }
                                break;

                                case 4: {
                                    boolean errore;
                                    if (ListaMaghi.getLength() < 2) {
                                        System.out.println("Servono almeno due maghi per assegnare i bersagli.");
                                        break;
                                    }
                                    for (Mago m : ListaMaghi.getMaghi()) {
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
                                break;

                                case 5: {
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
                                    }

                                    SquadMaghi.pulisciSquadreVuote();

                                    for (Mago m : ListaMaghi.getMaghi()) {
                                        if (m.isAlive()) {
                                            m.interrupt();
                                        }
                                    }

                                    ListaMaghi.clean();
                                    System.out.println();
                                    System.out.println("Battaglia finita, il vincitore è stato congedato nel mondo della magia!");
                                }
                                break;

                                case 0: break;
                                default: System.out.println("Comando non valido!"); break;
                            }
                        } while (sceltaMenu1 != 0);
                    }
                    break;

                    case 2: {
                        int sceltaMenu2;
                        do {
                            sceltaMenu2 = menu.menu2();
                            switch (sceltaMenu2) {
                                case 1: {
                                    int x;
                                    do {
                                        System.out.println("Dimmi quanti maghi vuoi generare (da 2 a 100)");
                                        x = Tools.getIntero();
                                    } while (x < 2 || x > 100);

                                    for (int i = 0; i < x; i++) {
                                        int squadMaghetto = -1*(ListaMaghi.getLength()+1);
                                        String nomeMago = ListaNomiMaghi.getNomeMago();
                                        Mago nuovoMago = new Mago(nomeMago, 35, 30, codaTurni, duelloInCorso, squadMaghetto, livello);
                                        ListaMaghi.addMago(nuovoMago);
                                    }
                                    System.out.println("Fatto!");
                                }
                                break;

                                case 2: {
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
                                break;

                                case 3: {
                                    System.out.println("\n-- Schede iniziali --");
                                    ListaMaghi.print();
                                    System.out.println();
                                }
                                break;

                                case 4: {
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
                                break;

                                case 0: break;
                                default: System.out.println("Comando non valido!"); break;
                            }
                        } while (sceltaMenu2 != 0);
                    }
                    break;

                    case 3: {
                        int sceltaMenu3 = 0;
                        int nMaghi = 0;
                        int nSquad = 0;

                        do {
                            sceltaMenu3 = menu.menu3();
                            switch (sceltaMenu3) {
                                case 1: {
                                    if (SquadMaghi.isEmpty()) {
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
                                        System.out.println("Hai già delle aquadre create, scatena il RAGNAROCK prima di crearne altre!");
                                    }
                                }
                                break;

                                case 2: {
                                    System.out.println("\n-- Schede iniziali --");
                                    SquadMaghi.print();
                                    System.out.println();
                                }
                                break;

                                case 3: {
                                    if (SquadMaghi.isEmpty()) {
                                        System.out.println("Crea le squadre prima!");
                                    } else {
                                        duelloInCorso.set(true);

                                        for (Mago m : ListaMaghi.getMaghi()) {
                                            m.assegnaNuovoNemico2();
                                        }

                                        System.out.println("Inizia il Ragnarok Magico!");

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
                                        SquadMaghi.pulisciSquadreVuote();

                                        for (Mago m : ListaMaghi.getMaghi()) {
                                            if (m.isAlive()) {
                                                m.interrupt();
                                            }
                                        }

                                        SquadMaghi.vettoreDiVettori.clear();
                                        ListaMaghi.clean();
                                        System.out.println();
                                        System.out.println("Battaglia finita, il vincitore è stato congedato nel mondo della magia!");
                                    }
                                }
                                break;

                                case 0: break;
                                default: System.out.println("Comando non valido!"); break;
                            }
                        } while (sceltaMenu3 != 0);
                    }
                    break;

                    case 4: {
                        livello = menu.menuLivello();
                        System.out.println("Impostazioni Modificate!");
                    }
                    break;

                    case 0: break;
                    default: System.out.println("Comando non valido!"); break;

                }
            } while (scelta != 0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            menu.close();
            tastiera.close();
        }
    }

    public static void magnaMorti() {
        // ... IMPLEMENTAZIONE ORIGINALE ...
    }
}