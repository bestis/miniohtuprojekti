/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bibTexKoodit;

/**
 *
 * @author Ronnie
 */

// Osaanko?
import java.util.*;

public class Parseri {

    static String bibTexTulos = "";
    static boolean testId = false;
    static String viiteId;
    static String IdCharit = "ABCDEFGHIJKLMNOPQRST0123456789";
    static Random rng = new Random();
    static List<HashMap> viitteet;
//    int lisattyjaViitteita;

    public Parseri(List<HashMap> kirjat) {
        this.viitteet = kirjat;
//        this.lisattyjaViitteita = lisattyjaViitteita;
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

    public static String muutaBibTexMuotoonKirja(String type, String id, String author,
            String title, String year, String publisher) {

        bibTexTulos = bibTexTulos.concat("@" + type + "{");
        bibTexTulos = bibTexTulos.concat(id + ", \n");
        bibTexTulos = bibTexTulos.concat("author = {" + author + "}, \n");
        bibTexTulos = bibTexTulos.concat("title = {" + title + "}, \n");
        bibTexTulos = bibTexTulos.concat("year = {" + year + "}, \n");
        bibTexTulos = bibTexTulos.concat("publisher = {" + publisher + "}, \n");
        bibTexTulos = bibTexTulos.concat("} \n");
        return bibTexTulos;
    }

    public static String muutaBibTexMuotoonArtikkeli(String type, String id, String author,
            String title, String year, String month, String journal, String volume,
            String pages, String address, String publisher) {

        bibTexTulos = bibTexTulos.concat("@" + type + "{");
        bibTexTulos = bibTexTulos.concat(id + ", \n");
        bibTexTulos = bibTexTulos.concat("author = {" + author + "}, \n");
        bibTexTulos = bibTexTulos.concat("title = {" + title + "}, \n");
        bibTexTulos = bibTexTulos.concat("year = {" + year + "}, \n");
        bibTexTulos = bibTexTulos.concat("month = {" + month + "}, \n");
        bibTexTulos = bibTexTulos.concat("journal = {" + journal + "}, \n");
        bibTexTulos = bibTexTulos.concat("volume = {" + volume + "}, \n");
        bibTexTulos = bibTexTulos.concat("pages = {" + pages + "}, \n");
        bibTexTulos = bibTexTulos.concat("address = {" + address + "}, \n");
        bibTexTulos = bibTexTulos.concat("publisher = {" + publisher + "}, \n");
        bibTexTulos = bibTexTulos.concat("} \n");
        //to do
        return bibTexTulos;
    }

    public static String muutaBibTexMuotoonInproceedings(String type, String id, String author,
            String title, String booktitle, String year, String publisher) {

        bibTexTulos = bibTexTulos.concat("@" + type + "{");
        bibTexTulos = bibTexTulos.concat(id + ", \n");
        bibTexTulos = bibTexTulos.concat("author = {" + author + "}, \n");
        bibTexTulos = bibTexTulos.concat("title = {" + title + "}, \n");
        bibTexTulos = bibTexTulos.concat("booktitle = {" + booktitle + "}, \n");
        bibTexTulos = bibTexTulos.concat("year = {" + year + "}, \n");
        bibTexTulos = bibTexTulos.concat("publisher = {" + publisher + "}, \n");
        bibTexTulos = bibTexTulos.concat("} \n");
        //to do
        return bibTexTulos;
    }

    public String getBibTex() {
        bibTexTulos = "";
        HashMap<String, String> viite;
        
        for (int i = 0; i < viitteet.size(); i++) {
            viite = viitteet.get(i);
            System.out.println(viite);
            if (viite.get("type").equals("book")) {
                String id = viite.get("id");
                String author = viite.get("author");
                String title = viite.get("title");
                String year = viite.get("year");
                String publisher = viite.get("publisher");
                muutaBibTexMuotoonKirja("book", id, author, title, year, publisher);

            } else if (viite.get("type").equals("article")) {
                String id = viite.get("id");
                String author = viite.get("author");
                String title = viite.get("title");
                String year = viite.get("year");
                String month = viite.get("month");
                String journal = viite.get("journal");
                String volume = viite.get("volume");
                String pages = viite.get("pages");
                String address = viite.get("address");
                String publisher = viite.get("publisher");
                muutaBibTexMuotoonArtikkeli("aricle", id, author, title, year, month, journal, volume, pages, address, publisher);


            } else if (viite.get("type").equals("inproceedings")) {
                String id = viite.get("id");
                String author = viite.get("author");
                String title = viite.get("title");
                String booktitle = viite.get("booktitle");
                String year = viite.get("year");
                String publisher = viite.get("publisher");
                muutaBibTexMuotoonInproceedings("inproceedings", id, author, title,
                        booktitle, year, publisher);
            }
        }
//        lisattyjaViitteita++;
        return bibTexTulos;
    }

    public static String generoiId(Random rng, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
//        for (int i = 0; i < viitteet.size(); i++) {
//        }
        return new String(text);
    }
}
