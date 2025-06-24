package org.main;

import org.classi.Mago;
import org.classi.Tools;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) {
        Scanner tastiera = new Scanner(System.in);
        String risposta;

        do {
            Queue<Mago> codaTurni = new LinkedList<>();
            AtomicBoolean duelloInCorso = new AtomicBoolean(true);

            Mago mago1 = new Mago("Merlino", -1, codaTurni, duelloInCorso);
            Mago mago2 = new Mago("Voldemort", mago1.getTotale(), codaTurni, duelloInCorso);
            mago1.setNemico(mago2);
            mago2.setNemico(mago1);

            System.out.println("\n-- Schede iniziali --");
            System.out.println(mago1);
            System.out.println(mago2);

            mago1.start();
            mago2.start();

            try {
                mago1.join();
                mago2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.print("Vuoi fare un'altra partita? (si/no): ");
            risposta = Tools.rimuoviSpazi(tastiera.nextLine()).toLowerCase();
        } while (risposta.equals("si"));

        System.out.println("Programma terminato.");
    }
}