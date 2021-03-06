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
    public void syotetytParametritBibTexmuotoon() {
        String type = "book";
        String id = "BA04";
        String author = "Vihavainen";
        String title = "Clean code";
        String year = "2008";
        String publisher = "jokuvaan";
        String tulos;

        Parseri parser = new Parseri();
        tulos = parser.muutaBibTexMuotoonKirja(type, author, title, year , publisher);

        String odotettuTulos = "";
        odotettuTulos = odotettuTulos.concat("@" + type + "{");
        odotettuTulos = odotettuTulos.concat(id + ", \n");
        odotettuTulos = odotettuTulos.concat("author = {" + author + "}, \n");
        odotettuTulos = odotettuTulos.concat("title = {" + title + "}, \n");
        odotettuTulos = odotettuTulos.concat("year = {" + year + "}, \n");
        odotettuTulos = odotettuTulos.concat("julkaisija = {" + publisher + "}, \n");
        odotettuTulos = odotettuTulos.concat("} \n");

        assertEquals(odotettuTulos, tulos);

    }
     @Test
     public void lueHashmapistaArvoja(){
         List<HashMap> kirjat = new ArrayList();
         HashMap<String, String> kirja = new HashMap();
         kirja.put("author", "vihavainen");
         kirja.put("title", "clean code");
         kirja.put("year", "2008");
         kirjat.add(kirja);
         Parseri parser = new Parseri(kirjat,1);
         System.out.println(parser.getBibTex());
     }
}