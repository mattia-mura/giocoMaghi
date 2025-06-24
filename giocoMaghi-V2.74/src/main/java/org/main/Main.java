package org.main;

import org.classi.Mago;
import org.classi.Tools;
import org.classi.Menu;
import org.classi.Trascrittore;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) {
        Scanner tastiera = new Scanner(System.in);
        Menu menu = new Menu();
        Vector<Mago> v = new Vector<>();
        int scelta;
        boolean amici = true;
        boolean primo = true;
        int totale = -1;

        do {
            Queue<Mago> codaTurni = new LinkedList<>();
            AtomicBoolean duelloInCorso = new AtomicBoolean(true);

            scelta = menu.menu1();

            switch (scelta) {
                case 1: {
                    String nome;
                    do {
                        System.out.print("\nInserisci il nome del mago da inserire: ");
                        nome = tastiera.next();
                        nome = Tools.rimuoviSpazi(nome);
                    } while (nome.equals(""));
                    if (v.add(new Mago(nome, totale, codaTurni, duelloInCorso))) {
                        System.out.println("Mago aggiunto con successo");
                        if (primo) {
                            primo = false;
                            totale = v.getFirst().getTotale();
                        }
                    } else {
                        System.out.println("Impossibile aggiungere il Mago, riprova!");
                    }
                } break;

                case 2: {
                    String nome;
                    do {
                        System.out.print("\nInserisci il nome del mago da eliminare: ");
                        nome = tastiera.next();
                        nome = Tools.rimuoviSpazi(nome);
                    } while (nome.equals(""));
                    int index = ricerca(v, nome);
                    if (index == -1) {
                        System.out.println("Impossibile eliminare il Mago, riprova!");
                    } else {
                        v.remove(index);
                        System.out.println("Mago eliminato con successo");
                    }
                } break;

                case 3: {
                    System.out.println("\n-- Schede iniziali --");
                    for (Mago m : v) {
                        System.out.println(m);
                    }
                    System.out.println();
                } break;

                case 4: {
                    String nomeNemico;
                    boolean errore;
                    System.out.println("Inserisci per ogni mago il bersaglio da attaccare:");
                    for (Mago m : v) {
                        do {
                            errore = false;
                            System.out.print("Inserisci il nome del mago che " + m.getNome() + " punterà: ");
                            nomeNemico = tastiera.next();
                            nomeNemico = Tools.rimuoviSpazi(nomeNemico);
                            if (nomeNemico.equals(m.getNome())) {
                                errore = true;
                                System.out.println("Il nemico non può essere se stesso");
                                continue;
                            }
                            int index = ricerca(v, nomeNemico);
                            if (index != -1) {
                                m.setNemico(v.get(index));
                            } else {
                                System.out.println("Il nome non esiste!");
                                errore = true;
                            }
                        } while (errore);
                        System.out.println("Nemico inserito correttamente!");
                    }
//                    System.out.println("\nTutti i maghi hanno un bersaglio ora");
                } break;

                case 5: {
                    int cont = 0;
                    for (Mago m : v) {
                        if (m.getNemico() != null) cont++;
                    }
                    if (cont == v.size()) amici = false;

                    if (v.size() < 2 || amici) {
                        System.out.println("Impossibile far startare la lotta magica!");
                        System.out.println("Devi avere almeno 2 maghi e i maghi non devono essere più amici!");
                    } else {
                        for (Mago m : v) {
                            m.start();
                        }
                        for (Mago m : v) {
                            try {
                                m.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } break;

                case 0:
                    break;

                default:
                    System.out.println("Comando non valido!");
            }
        } while (scelta != 0);

        System.out.println("Programma terminato.");
        Trascrittore.closeAll();
    }

    private static int ricerca(Vector<Mago> v, String s) {
        for (int i = 0; i < v.size(); i++) {
            if (v.get(i).getNome().equals(s)) return i;
        }
        return -1;
    }
}
