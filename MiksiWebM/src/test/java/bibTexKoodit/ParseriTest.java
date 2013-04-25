/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bibTexKoodit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Ronnie
 */
public class ParseriTest {

    public ParseriTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //

    @Test
    public void syotetytParametritBibTexmuotoonKirja() {
        List<HashMap> kirjat = new ArrayList();
        String type = "book";
        String id = "JB3Q5";
        String author = "Vihavainen";
        String title = "Clean code";
        String year = "2008";
        String publisher = "jokuvaan";
        String tulos;

        Parseri parser = new Parseri(kirjat);
        parser.bibTexTulos = "";
        tulos = parser.muutaBibTexMuotoonKirja(type, id, author, title, year, publisher);


        String odotettuTulos = "";
        odotettuTulos = odotettuTulos.concat("@" + type + "{");
        odotettuTulos = odotettuTulos.concat(id + ", \n");
        odotettuTulos = odotettuTulos.concat("  author = {" + author + "}, \n");
        odotettuTulos = odotettuTulos.concat("  title = {" + title + "}, \n");
        odotettuTulos = odotettuTulos.concat("  year = {" + year + "}, \n");
        odotettuTulos = odotettuTulos.concat("  publisher = {" + publisher + "}, \n");
        odotettuTulos = odotettuTulos.concat("} \n");

        assertEquals(odotettuTulos, tulos);
    }

    @Test
    public void syotetytParametritBibTexmuotoonArticle() {
        List<HashMap> kirjat = new ArrayList();
        String type = "article";
        String id = "JB3Q5";
        String author = "Vihavainen";
        String title = "Clean monkeys";
        String year = "2008";
        String month = "january";
        String journal = "Nature";
        String volume = "13";
        String pages = "250-258";
        String address = "Helsinki";
        String publisher = "jokuvaan";
        String tulos;

        Parseri parser = new Parseri(kirjat);
        parser.bibTexTulos = "";
        tulos = parser.muutaBibTexMuotoonArtikkeli(type, id, author, title, year, month, journal, volume, pages, address, publisher);


        String odotettuTulos = "";
        odotettuTulos = odotettuTulos.concat("@" + type + "{");
        odotettuTulos = odotettuTulos.concat(id + ", \n");
        odotettuTulos = odotettuTulos.concat("  author = {" + author + "}, \n");
        odotettuTulos = odotettuTulos.concat("  title = {" + title + "}, \n");
        odotettuTulos = odotettuTulos.concat("  year = {" + year + "}, \n");
        odotettuTulos = odotettuTulos.concat("  month = {" + month + "}, \n");
        odotettuTulos = odotettuTulos.concat("  journal = {" + journal + "}, \n");
        odotettuTulos = odotettuTulos.concat("  volume = {" + volume + "}, \n");
        odotettuTulos = odotettuTulos.concat("  pages = {" + pages + "}, \n");
        odotettuTulos = odotettuTulos.concat("  address = {" + address + "}, \n");
        odotettuTulos = odotettuTulos.concat("  publisher = {" + publisher + "}, \n");
        odotettuTulos = odotettuTulos.concat("} \n");

        assertEquals(odotettuTulos, tulos);
    }
    @Test
    public void syotetytParametritBibTexmuotoonInproc() {
        List<HashMap> kirjat = new ArrayList();
        String type = "inproceedings";
        String id = "JB3Q5";
        String author = "Vihavainen";
        String title = "Clean socks";
        String booktitle = "Laundry for dummies";
        String year = "1994";
        String publisher = "jokuvaan";
        String tulos;

        Parseri parser = new Parseri(kirjat);
        parser.bibTexTulos = "";
        tulos = parser.muutaBibTexMuotoonInproceedings(type, id, author, title, booktitle, year, publisher);


        String odotettuTulos = "";
        odotettuTulos = odotettuTulos.concat("@" + type + "{");
        odotettuTulos = odotettuTulos.concat(id + ", \n");
        odotettuTulos = odotettuTulos.concat("  author = {" + author + "}, \n");
        odotettuTulos = odotettuTulos.concat("  title = {" + title + "}, \n");
        odotettuTulos = odotettuTulos.concat("  booktitle = {" + booktitle + "}, \n");;
        odotettuTulos = odotettuTulos.concat("  year = {" + year + "}, \n");
        odotettuTulos = odotettuTulos.concat("  publisher = {" + publisher + "}, \n");
        odotettuTulos = odotettuTulos.concat("} \n");

        assertEquals(odotettuTulos, tulos);
    }
    @Test
    public void lueHashmapistaArvoja() {
        List<HashMap> kirjat = new ArrayList();
        HashMap<String, String> kirja = new HashMap();
        kirja.put("type", "book");
        kirja.put("id", "ABCD");
        kirja.put("author", "vihavainen");
        kirja.put("title", "clean code");
        kirja.put("year", "2008");
        kirja.put("publisher", "2008");
        kirjat.add(kirja);
//        Parseri parser = new Parseri(kirjat);
//        System.out.println(parser.getBibTex());
    }
}