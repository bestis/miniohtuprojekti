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

public class Parseri {

    static String bibTexTulos = "";
    static boolean onMuutettu = false;
    static String jokuId = "BA04"; //placeholder

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

    public String muutaBibTexMuotoon(String type, String author,
            String title, String year, String publisher) {

        bibTexTulos = bibTexTulos.concat("@" + type + "{");
        //joku id vissiin vielä tähän väliin?
        bibTexTulos = bibTexTulos.concat(jokuId + ", \n");
        bibTexTulos = bibTexTulos.concat("author = {" + author + "}, \n");
        bibTexTulos = bibTexTulos.concat("title = {" + title + "}, \n");
        bibTexTulos = bibTexTulos.concat("year = {" + year + "}, \n");
        bibTexTulos = bibTexTulos.concat("julkaisija = {" + publisher + "}, \n");
        bibTexTulos = bibTexTulos.concat("} \n");

        onMuutettu = true;
        return bibTexTulos;
    }
}
