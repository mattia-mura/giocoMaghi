package org.classi;
import java.util.Scanner;

public class Tools {

    public static int getIntero() {
        Scanner tastiera = new Scanner(System.in);

        int intero = 0;
        boolean ok;

        do {

            ok = true;
            try {

                String input = tastiera.nextLine().trim();
                intero = Integer.parseInt(input);

            } catch (NumberFormatException e) {
                System.out.print("\nErrore inserisci un numero valido.  --> ");
                ok = false;
            }
        } while (!ok);

        return intero;
    } //  intero Try-Catch

    public static String rimuoviSpazi(String s) {
        if (s == null) {
            return "";
        }

        String risultato = "";
        boolean spazioPrima = false;

        for (int i = 0; i <= s.length() - 1; i++) {
            char c = s.charAt(i);

            if (c == ' ') {
                if (!spazioPrima) {
                    risultato += ' ';
                    spazioPrima = true;
                }
            } else {
                risultato += c;
                spazioPrima = false;
            }
        }

        return risultato;
    }

    public static float getFloat() {
        Scanner scanner = new Scanner(System.in);

        float floatNum = 0;
        boolean ok;

        do {

            ok = true;
            try {

                String input = scanner.nextLine().trim();
                floatNum = Float.parseFloat(input);

            } catch (NumberFormatException e) {
                System.out.print("\nErrore inserisci un numero valido.  --> ");
                ok = false;
            }
        } while (!ok);

        return floatNum;
    }

    public static double getDouble() {
        Scanner scanner = new Scanner(System.in);

        double doubleNum = 0;
        boolean ok;

        do {

            ok = true;
            try {

                String input = scanner.nextLine().trim();
                doubleNum = Double.parseDouble(input);
                // Non funziona

            } catch (NumberFormatException e) {
                System.out.print("\nErrore inserisci un numero valido.  --> ");
                ok = false;
            }
        } while (!ok);

        return doubleNum;
    } // double Try-Catch

    private static boolean isLettera(char c) {
        return ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'));
    }

    private static boolean isNumero(char c) {
        return (c >= '0' && c <= '9');
    }

    private static boolean allowedSpecialChar(char c) {
        return ((c == ' ') || (c == '.') || (c == '-') || (c == '_'));
    }

    public static int convertitore(String s) {

        int i = -1;
        s.trim();
        try {
            i = (int) Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return i;
        }

        return i;
    } // convertitore

    public static double convertitoreDouble(String s) {

        double i = -1;
        s.trim();
        try {
            i = (double) Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return i;
        }

        return i;
    } // convertitoreDouble

    public static double cifreDopoVirgola(double n) {

        String s = "";
        s += n;
        String s1 = "";
        int posPunto = 0;
        boolean trovato = false;

        for (int i = 0; i < s.length() && !trovato; i++) {
            if (s.charAt(i) == '.') {
                posPunto = i;
                trovato = true;
            }
        }

        for (int i = 0; i < posPunto; i++) {
            s1 += s.charAt(i);
        }

        int maxPuntoCifre = 3;

        for (int i = posPunto; i < s.length() && maxPuntoCifre != 0; i++) {
            s1 += s.charAt(i);
            maxPuntoCifre--;
        }

        return convertitoreDouble(s1);
    } // cifreDopoVirgola

    public static boolean controlloNomeUtente(String x) {
        x = x.trim();
        for (int i = 0; i < x.length(); i++) {
            if ((!isLettera(x.charAt(i))) || (!isNumero(x.charAt(i)))) return false;
        }
        if (x.length() < 3 || x.length() > 16) {
            return false;
        }
        return true;
    }

    public static boolean controlloPassword(String x) {
        x = x.trim();
        int contNumeri = 0;
        int contLettere = 0;
        for (int i = 0; i < x.length(); i++) {
            if (x.charAt(i) == ' ') {
                return false;
            }
            if (isNumero(x.charAt(i))) {
                contNumeri++;
            }
            if (isLettera(x.charAt(i))) {
                contLettere++;
            }
        }
        if (contNumeri < 1 || contLettere < 1) {
            return false;
        }
        if (x.length() < 8 || x.length() > 16) {
            return false;
        }
        return true;
    }

}
