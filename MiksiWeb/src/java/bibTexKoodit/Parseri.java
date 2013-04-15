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
    static List<HashMap> kirjat;
    int lisattyjaViitteita;

    public Parseri(List<HashMap> kirjat,int lisattyjaViitteita) {
        this.kirjat = kirjat;
        this.lisattyjaViitteita=lisattyjaViitteita;


    }

    public Parseri() {
    }
    /*
     public static void tiedostoTallennus(String tiedostonimi) {
     if (onMuutettu == true) {
     try {
     PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(tiedostonimi, true)));
     out.println(bibTexTulos);
     out.close();
     } catch (IOException e) {
     }
     } else {
     System.out.println("Kutsu ensin metodia muutaBibTexMuotoon!");
     }

     }
     * 
     */

    public static String muutaBibTexMuotoonKirja(String type, String author,
            String title, String year, String publisher) {
        viiteId = generoiId(rng,IdCharit,5);
        

        bibTexTulos = bibTexTulos.concat("@" + type + "{");
        //joku id vissiin vielä tähän väliin?
        bibTexTulos = bibTexTulos.concat(viiteId + ", \n");
        bibTexTulos = bibTexTulos.concat("author = {" + author + "}, \n");
        bibTexTulos = bibTexTulos.concat("title = {" + title + "}, \n");
        bibTexTulos = bibTexTulos.concat("year = {" + year + "}, \n");
        bibTexTulos = bibTexTulos.concat("julkaisija = {" + publisher + "}, \n");
        bibTexTulos = bibTexTulos.concat("} \n");

        onMuutettu = true;
        return bibTexTulos;
    }

    public String getBibTex() {
        HashMap<String, String> kirja;

        for (int i = lisattyjaViitteita; i < kirjat.size(); i++) {
            kirja = kirjat.get(i);

            String author = kirja.get("author");
            String title = kirja.get("title");
            String year = kirja.get("year");
            String publisher = kirja.get("publisher");
            muutaBibTexMuotoonKirja("book", author, title, year, publisher);

        }

        lisattyjaViitteita++;
        return bibTexTulos;
    }
    public static String generoiId(Random rng, String characters, int length)
{
    char[] text = new char[length];
    for (int i = 0; i < length; i++)
    {
        text[i] = characters.charAt(rng.nextInt(characters.length()));
    }
    for(int i=0; i<kirjat.size(); i++){
        
    }
    return new String(text);
}
}
