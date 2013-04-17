/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bibTexKoodit;

/**
 *
 * @author Ronnie
 */
import java.io.*;
import java.util.*;

public class Parseri {

    static String bibTexTulos = "";
    static boolean onMuutettu = false;
    static String viiteId;
    static String IdCharit = "ABCDEFGHIJKLMNOPQRST0123456789";
    static Random rng = new Random();
    static List<HashMap> viitteet;
    int lisattyjaViitteita;

    public Parseri(List<HashMap> kirjat, int lisattyjaViitteita) {
        this.viitteet = kirjat;
        this.lisattyjaViitteita = lisattyjaViitteita;


    }

    public Parseri() {
    }
    /*
     * public static void tiedostoTallennus(String tiedostonimi) { if
     * (onMuutettu == true) { try { PrintWriter out = new PrintWriter(new
     * BufferedWriter(new FileWriter(tiedostonimi, true)));
     * out.println(bibTexTulos); out.close(); } catch (IOException e) { } } else
     * { System.out.println("Kutsu ensin metodia muutaBibTexMuotoon!"); }
     *
     * }
     *
     */

    public static String muutaBibTexMuotoonKirja(String type, String author,
            String title, String year, String publisher) {
        viiteId = generoiId(rng, IdCharit, 5);


        bibTexTulos = bibTexTulos.concat("@" + type + "{");
        bibTexTulos = bibTexTulos.concat(viiteId + ", \n");
        bibTexTulos = bibTexTulos.concat("author = {" + author + "}, \n");
        bibTexTulos = bibTexTulos.concat("title = {" + title + "}, \n");
        bibTexTulos = bibTexTulos.concat("year = {" + year + "}, \n");
        bibTexTulos = bibTexTulos.concat("julkaisija = {" + publisher + "}, \n");
        bibTexTulos = bibTexTulos.concat("} \n");

        onMuutettu = true;
        return bibTexTulos;
    }

    public static String muutaBibTexMuotoonArtikkeli(String type, String author,
            String title, String year, String month, String journal, String volume,
            String pages, String address, String publisher) {
        viiteId = generoiId(rng, IdCharit, 5);

        bibTexTulos = bibTexTulos.concat("@" + type + "{");
        bibTexTulos = bibTexTulos.concat(viiteId + ", \n");
        bibTexTulos = bibTexTulos.concat("author = {" + author + "}, \n");
        bibTexTulos = bibTexTulos.concat("title = {" + title + "}, \n");
        bibTexTulos = bibTexTulos.concat("year = {" + year + "}, \n");
        bibTexTulos = bibTexTulos.concat("month = {" + month + "}, \n");
        bibTexTulos = bibTexTulos.concat("journal = {" + journal + "}, \n");
        bibTexTulos = bibTexTulos.concat("volume = {" + volume + "}, \n");
        bibTexTulos = bibTexTulos.concat("pages = {" + pages + "}, \n");
        bibTexTulos = bibTexTulos.concat("address = {" + address + "}, \n");
        bibTexTulos = bibTexTulos.concat("publisher = {" + publisher + "}, \n");
        return bibTexTulos;
    }

    public static String muutaBibTexMuotoonInproceedings() {
        //to do
        return bibTexTulos;
    }

    public String getBibTex() {
        HashMap<String, String> viite;

        for (int i = lisattyjaViitteita; i < viitteet.size(); i++) {
            viite = viitteet.get(i);

            if (viite.get("type").equals("book")) {
                String author = viite.get("author");
                String title = viite.get("title");
                String year = viite.get("year");
                String publisher = viite.get("publisher");
                muutaBibTexMuotoonKirja("book", author, title, year, publisher);
            } else if (viite.get("type").equals("article")) {
                String author = viite.get("author");
                String title = viite.get("title");
                String year = viite.get("year");
                String month = viite.get("month");
                String journal = viite.get("journal");
                String volume = viite.get("volume");
                String pages = viite.get("pages");
                String address = viite.get("address");
                String publisher = viite.get("publisher");
                muutaBibTexMuotoonArtikkeli("article", author, title, year, month, journal, volume, pages, address, publisher);


            } else if (viite.get("type").equals("inproceedings")) {
                String author = viite.get("author");
                String title = viite.get("title");
                String booktitle = viite.get("booktitle");
                String year = viite.get("year");
                String publisher = viite.get("publisher");
                muutaBibTexMuotoonInproceedings("article", author, title, booktitle, year, publisher);


            }

        }

        lisattyjaViitteita++;
        return bibTexTulos;
    }

    public static String generoiId(Random rng, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        for (int i = 0; i < viitteet.size(); i++) {
        }
        return new String(text);
    }
}
