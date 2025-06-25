package org.classi;

import java.util.Random;
import java.util.Vector;

public class ListaNomiMaghi {

    private static Vector <String> nomiMaghi =  new Vector<>();

//    public listaNomiMaghi() {
//        nomiMaghi = new Vector<>();
//    }


    public static void generaNomiMaghi() {

        nomiMaghi.add("Merlino");
        nomiMaghi.add("Gandalf");
        nomiMaghi.add("Harry Potter");
        nomiMaghi.add("Albus Silente");
        nomiMaghi.add("Severus Piton");
        nomiMaghi.add("Morgana");
        nomiMaghi.add("Radagast");
        nomiMaghi.add("Saruman");
        nomiMaghi.add("Doctor Strange");
        nomiMaghi.add("Rincewind");
        nomiMaghi.add("Elminster");
        nomiMaghi.add("Prospero");
        nomiMaghi.add("Zatanna");
        nomiMaghi.add("Willow Rosenberg");
        nomiMaghi.add("Mordenkainen");
        nomiMaghi.add("Yennefer");
        nomiMaghi.add("Geralt di Rivia");
        nomiMaghi.add("Rasputin");
        nomiMaghi.add("Balthazar Blake");
        nomiMaghi.add("Howl Pendragon");
        nomiMaghi.add("Morgause");
        nomiMaghi.add("Baba Yaga");
        nomiMaghi.add("The Ancient One");
        nomiMaghi.add("Morgan Le Fay");
        nomiMaghi.add("Shazam");
        nomiMaghi.add("Dr. Fate");
        nomiMaghi.add("Circe");
        nomiMaghi.add("Vecna");
        nomiMaghi.add("Raistlin Majere");
        nomiMaghi.add("Allanon");
        nomiMaghi.add("Pug");
        nomiMaghi.add("Jonathan Strange");
        nomiMaghi.add("Mr Norrell");
        nomiMaghi.add("Skeletor");
        nomiMaghi.add("Nicol Bolas");
        nomiMaghi.add("Orko");
        nomiMaghi.add("Felix Felicis");
        nomiMaghi.add("Patience Terrence");
        nomiMaghi.add("Nox Mortem");
        nomiMaghi.add("Eldrin Stormseeker");
        nomiMaghi.add("Azarak");
        nomiMaghi.add("Zorander");
        nomiMaghi.add("Kael'thas Sunstrider");
        nomiMaghi.add("Kel'Thuzad");
        nomiMaghi.add("Archimonde");
        nomiMaghi.add("Medivh");
        nomiMaghi.add("Khagdar");
        nomiMaghi.add("Tasha");
        nomiMaghi.add("Dalamar");
        nomiMaghi.add("Xanatos");
        nomiMaghi.add("Aldebrand");
        nomiMaghi.add("Velkan Draymor");
        nomiMaghi.add("Syrenna Blackthorn");
        nomiMaghi.add("Voramir il Muto");
        nomiMaghi.add("Malakar il Nero");
        nomiMaghi.add("Zephyra Moonshade");
        nomiMaghi.add("Oblivionis");
        nomiMaghi.add("Torrak Doomseer");
        nomiMaghi.add("Elandra la Rossa");
        nomiMaghi.add("Veylor Duskbane");
        nomiMaghi.add("Nimue");
        nomiMaghi.add("Thassalon il Vecchio");
        nomiMaghi.add("Galdrion il Saggio");
        nomiMaghi.add("Lunaria Frostwhisper");
        nomiMaghi.add("Solonar Shadowmend");
        nomiMaghi.add("Morwen Nightbrook");
        nomiMaghi.add("Ezren");
        nomiMaghi.add("Lyra Starfall");
        nomiMaghi.add("Thorne Wyrmbinder");
        nomiMaghi.add("Kalthor Bloodbane");
        nomiMaghi.add("Silvarion Lightbringer");
        nomiMaghi.add("Arven Shadowthorn");
        nomiMaghi.add("Velora Darkmist");
        nomiMaghi.add("Cyrus Spellfire");
        nomiMaghi.add("Drelik Bonecaller");
        nomiMaghi.add("Malthis Emberfall");
        nomiMaghi.add("Sevrak Doomwhisper");
        nomiMaghi.add("Nymeris Moonveil");
        nomiMaghi.add("Orion Stormrider");
        nomiMaghi.add("Vaelora Nightgale");
        nomiMaghi.add("Therion Darkspire");
        nomiMaghi.add("Varyn Emberclaw");
        nomiMaghi.add("Azeroth il Primigenio");
        nomiMaghi.add("Kaelen Swiftspell");
        nomiMaghi.add("Eryndor il Bianco");
        nomiMaghi.add("Sythril Dreambinder");
        nomiMaghi.add("Zarion Flameheart");
        nomiMaghi.add("Vorath Shadewalker");
        nomiMaghi.add("Lorana Starweaver");
        nomiMaghi.add("Gavrik Doomlash");
        nomiMaghi.add("Moriel il Grigio");
        nomiMaghi.add("Talandra Mistvale");
        nomiMaghi.add("Zephyr Stormsong");
        nomiMaghi.add("Kelvorn Shadowflame");
        nomiMaghi.add("Vaelor the Mystic");
        nomiMaghi.add("Darian Spellshade");
        nomiMaghi.add("Sylthra Blackveil");
        nomiMaghi.add("Iskandar il Profondo");
        nomiMaghi.add("Calithra Ombrafulgida");
        nomiMaghi.add("Vorath il Veggente");
        nomiMaghi.add("Selene delle Nebbie");
    }

    public static String getNomeMago() {
        if (nomiMaghi.isEmpty()) {
            return "MagoSenzaNome" + new Random().nextInt(1000);
        } else {
            int index = new Random().nextInt(nomiMaghi.size());
            return nomiMaghi.remove(index);//+new Random().nextInt(1000);
        }
//        int index = new Random().nextInt(nomiMaghi.size());
//        return nomiMaghi.get(index)+new Random().nextInt(1000);
    }


}