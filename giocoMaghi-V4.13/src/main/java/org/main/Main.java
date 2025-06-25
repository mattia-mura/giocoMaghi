package org.main;

import org.classi.Mago;
import org.classi.Tools;
import org.classi.Menu;
import org.classi.Trascrittore;
import org.classi.ListaMaghi;
import org.classi.ListaNomiMaghi;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    public static void main(String[] args) {
        Scanner tastiera = new Scanner(System.in);
        Menu menu = new Menu();
        int scelta;
        ListaNomiMaghi.generaNomiMaghi();

        do {
            Queue<Mago> codaTurni = new LinkedList<>();
            AtomicBoolean duelloInCorso = new AtomicBoolean(true);
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

                                if (ListaMaghi.addMago(new Mago(nome, 35, 30, codaTurni, duelloInCorso))) {
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

                            case 6: {
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
                                //Vector<Mago> temp = ListaMaghi.getMaghi();
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
                                        }
                                    }

                                    magnaMorti();
                                }
                                ListaMaghi.getMago(0).interrupt();
                                ListaMaghi.clean();
                                System.out.println();
                                System.out.println("Battaglia finita, il vincitore è stato congedato nel mondo della magia!");
                            }
                            break;

                            case 0:
                                break;

                            default:
                                System.out.println("Comando non valido!");
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
                                    String nomeMago = ListaNomiMaghi.getNomeMago();
                                    Mago nuovoMago = new Mago(nomeMago, 35, 30, codaTurni, duelloInCorso);
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
                                //Vector<Mago> temp = ListaMaghi.getMaghi();
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
                                        }
                                    }

                                    magnaMorti();
                                }
                                ListaMaghi.getMago(0).interrupt();
                                ListaMaghi.clean();
                                System.out.println();
                                System.out.println("Battaglia finita, il vincitore è stato congedato nel mondo della magia!");
                            }
                            break;

                            case 0:
                                break;

                            default:
                                System.out.println("Comando non valido!");
                        }
                    } while (sceltaMenu2 != 0);
                }
                break;

                case 3:{

                }
                break;

            }//switch
        } while (scelta != 0);

        System.out.println("Programma terminato.");
        Trascrittore.closeAll();
    }

    private static int ricerca(String s) {
        return ListaMaghi.findMago(s);
    }

    private static void magnaMorti() {
        Vector<Mago> maghi = ListaMaghi.getMaghi();
        for (int i = 0; i < maghi.size(); i++) {
            if (maghi.get(i).getVita() <= 0) {
                maghi.remove(i);
                i--; // per evitare di saltare elementi dopo la rimozione
            }
        }
    }
}
