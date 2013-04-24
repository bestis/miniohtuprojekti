/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miksiohtu.miksiwebm;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import bibTexKoodit.Parseri;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author bestis
 */
public class ViitekaluServlet extends HttpServlet {

    private String VIITEFILE = "/home/jmtollik/viitteet.xml";
    // Kirjat
    List<HashMap> viitteet = new ArrayList();
//  int lisattyjaViitteita = -1;
    Random rng = new Random();
    String IdCharit = "ABCDEFGHIJKLMNOPQRST0123456789";

    /**
     * Print default header
     *
     * @param request
     * @param response
     * @param out
     */
    protected void header(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        response.setContentType("text/html;charset=UTF-8");
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>ViitekaluServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>ViitekaluServlet (@ad) (" + request.getContextPath() + ")</h1>");

        out.println("<nav>");
        out.println("<a href=\"?action=add\">Lis&auml;&auml; l&auml;hde</a> | ");
        out.println("<a href=\"?action=list\">Listaa l&auml;hteet</a> | ");
        out.println("<a href=\"?action=showbibtex\">N&auml;yt&auml; BibTeX</a> | ");
        out.println("<a href=\"?action=bibtex\">Lataa BibTeX</a>");
        out.println("</nav>");
    }

    /**
     * Print footer
     *
     * @param out
     * @param cntxt
     */
    protected void footer(PrintWriter out, ServletContext cntxt) {
        int latauksia = 0;
        try {
            latauksia = (Integer) cntxt.getAttribute("hitcount");
        } catch (Exception e) {
            //
        }
        out.println("<br /><br />Latauksia " + latauksia + ", Kirjoja " + viitteet.size() + "<br />");
        cntxt.setAttribute("hitcount", latauksia + 1);

        out.println("</body>");
        out.println("</html>");

    }

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @param isPost boolean is request post request
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, boolean isPost)
            throws ServletException, IOException {

        ServletContext cntxt = getServletContext();

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        try {

            // Handle action parameter
            String action;
            try {
                action = request.getParameter("action");
                if (action == null) {
                    action = "";
                }
            } catch (Exception e) {
                action = "";
            }

            if (action.equals("bibtex")) {
                exportBibTex(response, out);
            } else if (action.equals("list")) {
                listBibtexReadable(request, response, out, cntxt);
            } else if (action.equals("showbibtex")) {
                listBibtex(request, response, out, cntxt);
            } else if (action.equals("add")) {
                addBibTex(request, response, out, isPost, cntxt);
            } else {
                header(request, response, out);
                out.println("<h1>Tervetuloa!</h1>");
                footer(out, cntxt);
            }
        } finally {
            out.close();
        }
    }

    @Override
    public void init() throws ServletException {
        try {
            super.init();
            // Load old
            XMLDecoder d = new XMLDecoder(new BufferedInputStream(new FileInputStream(this.VIITEFILE)));
            this.viitteet = (List<HashMap>) d.readObject();
            d.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ViitekaluServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void destroy() {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(new File(this.VIITEFILE)));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            XMLEncoder xmlEncoder = new XMLEncoder(bos);
            xmlEncoder.writeObject(viitteet);
            xmlEncoder.flush();
            writer.write(bos.toString());
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(ViitekaluServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(ViitekaluServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response, false);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response, true);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void exportBibTex(HttpServletResponse response, PrintWriter out) {
        System.out.println(viitteet.size());
        Parseri parseri = new Parseri(viitteet);
        response.setContentType("application/x-bibtex;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=viittet.bib");
        out.println(parseri.getBibTex());
        out.close();
    }

    private void listBibtexReadable(HttpServletRequest request, HttpServletResponse response, PrintWriter out, ServletContext cntxt) {
        header(request, response, out);
        out.println("<script type=\"text/javascript\">"
                + "function naytaLahde(id){ document.getElementById(id).style.display='block';}"
                + "</script>");

        out.println("<<h2><br />L&auml;hdelistaus</h2>");
        Iterator<HashMap> it = viitteet.iterator();
        int i = 1;
        while (it.hasNext()) {
            HashMap kirja = it.next();
            out.println("<a href=\"#\" onClick=\"naytaLahde('" + i + "')\"><h3>L&auml;hde " + i + ":</h3></a>");
            out.println("<div class=\"lahde\" id=" + i + " style=\"display:none\">");
            Iterator hit = kirja.entrySet().iterator();
            while (hit.hasNext()) {
                Map.Entry pairs = (Map.Entry) hit.next();
                if (pairs.getKey().equals("id")) {
                    continue;
                }
                
                out.println("<b>" + pairs.getKey() + "</b>: " + pairs.getValue() + "<br />");
                
            }
            out.println("</div>");
            out.println("<br />");
            //Do something with obj
            i++;
        }
        out.println("<br /><br />");

        footer(out, cntxt);
    }

    private void listBibtex(HttpServletRequest request, HttpServletResponse response, PrintWriter out, ServletContext cntxt) {
        header(request, response, out);
        out.println("<br />BibTeX:<br />");
        Parseri parseri = new Parseri(viitteet);
        out.println("<textarea name=\"bibtex\" cols=\"50\" rows=\"30\">");
        out.println(parseri.getBibTex());
        out.println("</textarea>");
        out.close();
    }

    private void addBibTex(HttpServletRequest request, HttpServletResponse response, PrintWriter out, boolean isPost, ServletContext cntxt) {
        header(request, response, out);

        // Post?
        if (isPost) {
            HashMap<String, String> viite = new HashMap();
            Enumeration paramNames = request.getParameterNames();
            viite.put("id", generoiId(rng, IdCharit, 5));
            while (paramNames.hasMoreElements()) {
                String paramName = (String) paramNames.nextElement();
                if (paramName.equals("action")) {
                    continue;
                }
                String paramValue = (String) request.getParameter(paramName);
                viite.put(paramName, paramValue);
                //out.print(paramName+"=="+paramValue);
            }
            viitteet.add(viite);
//            lisattyjaViitteita++;
        }

        // Lis&auml;yslomake


        out.println("<script type=\"text/javascript\">"
                + "function naytaKirja(id){ document.getElementById(id).style.display='block';"
                + "document.getElementById('artikkeli').style.display='none';"
                + "document.getElementById('inproceedings').style.display='none';}"
                + "</script>");

        out.println("<script type=\"text/javascript\">"
                + "function naytaArtikkeli(id){ document.getElementById(id).style.display='block';"
                + "document.getElementById('kirja').style.display='none';"
                + "document.getElementById('inproceedings').style.display='none';}"
                + "</script>");

        out.println("<script type=\"text/javascript\">"
                + "function naytaInproceedings(id) {document.getElementById(id).style.display='block';"
                + "document.getElementById('artikkeli').style.display='none';"
                + "document.getElementById('kirja').style.display='none';}"
                + "</script>");

        out.println("<script type=\"text/javascript\">function validateForm(formName)"
                + "{for(var i=0; i<document.forms[formName].elements.length; i++){ "
                + "var x=document.forms[formName].elements[i].value;"
                + "if (x==null || x==\"\")"
                + "   {alert(\"Tyhjiä kenttiä!\");"
                + "   return false;}}"
                + "var y= document.forms[formName][\"year\"].value;"
                + "if(isNaN(y)||y.length != 4)"
                + "   {alert(\"Anna oikea vuosi!\");"
                + "   return false;}"
                + "if(formName ===\"artikkelilomake\"){"
                + "y=document.forms[formName][\"number\"].value;"
                + "if(isNaN(y)){alert(\"Anna oikea numero!\");"
                + "return false;}"
                + "y=document.forms[formName][\"volume\"].value;"
                + "if(isNaN(y)){alert(\"Anna oikea volume!\");"
                + "return false;}"
                + "y=document.forms[formName][\"pages\"].value;"
                + "var k=y.charAt(0); if(isNaN(k)){alert(\"Anna sivut esim. muodossa 111--2222!\");return false;}  "
                + "var sivuvaliNahty=false;"
                + "for(var j=1;j<y.length;j++){"
                + "if(j==y.length/2 && sivuvaliNahty==false){alert(\"Anna sivut esim. muodossa 111--2222!\");return false;}"
                + "k=y.charAt(j); if(sivuvaliNahty==false){if(isNaN(k)){if(k===\"-\"){"
                + "k=y.charAt(j+1);"
                + "if(k===\"-\"){sivuvaliNahty=true;j++;}else{alert(\"Anna sivut esim. muodossa 111--2222!\");return false;}} else{alert(\"Anna oikeat sivut!\");return false;}  "
                + "}}else{if(isNan(k)){alert(\"Anna sivut esim. muodossa 111--2222!\");return false;}"
                + "}}if(sivuvaliNahty==false){alert(\"Anna sivut esim. muodossa 111--2222!\");return false;} if(y.charAt(y.length-1)===\"-\"){alert(\"Anna oikeat sivut!\");return false;}"
                + "y=document.forms[formName][\"month\"].value;"
                + "var oikeaKuukausi=false;"
                + "var months = [\"jan\", \"feb\", \"mar\", \"apr\", \"may\", \"jun\", \"jul\", \"aug\", \"sep\", \"oct\", \"nov\", \"dec\"];"
                + "for(var j=0; j<months.length;j++){ var p=months[j];"
                + "if(p===y){oikeaKuukausi=true; break;}}"
                + "if(oikeaKuukausi==false){alert(\"Anna oikea kuukausi esim. feb!\");return false;}"
                + "}}</script>");


        out.println("Book:<input type=\"radio\" name=\"tyyppi\" onClick=\"naytaKirja('kirja');\"> ");
        out.println("Article:<input type=\"radio\" name=\"tyyppi\" onClick=\"naytaArtikkeli('artikkeli');\" > ");
        out.println("Inproceedings:<input type=\"radio\" name=\"tyyppi\" onClick=\"naytaInproceedings('inproceedings');\"  >");


        out.println("<div class=\"kirjalomake\" id=\"kirja\" style=\"display:none\">"
                + "<br /><br /><form name=\"kirjalomake\" method=\"post\" action=\"?action=add\" onsubmit=\"return validateForm(name)\">"
                + "<input type=\"hidden\" name=\"type\" value=\"book\" />"
                + "Julkaisija:<br /><input type=\"text\" name=\"publisher\" /><br />"
                + "Kirjoittaja:<br /><input type=\"text\" name=\"author\" /><br />"
                + "Nimike:<br /><input type=\"text\" name=\"title\" /><br />"
                + "Vuosi:<br /><input type=\"text\" name=\"year\" /><br />"
                + "<input type=\"submit\" value=\"Lis&auml;&auml;\" /><br />"
                + "</form><br /><br /></div>");

        out.println("<div class=\"artikkelilomake\" id=\"artikkeli\" style=\"display:none\">"
                + "<br /><br /><form name=\"artikkelilomake\" method=\"post\" action=\"?action=add\"onsubmit=\"return validateForm(name)\">"
                + "<input type=\"hidden\" name=\"type\" value=\"article\" />"
                + "Journal:<br /><input type=\"text\" name=\"journal\" /><br />"
                + "Volume:<br /><input type=\"text\" name=\"volume\" /><br />"
                + "Number:<br /><input type=\"text\" name=\"number\" /><br />"
                + "Month:<br /><input type=\"text\" name=\"month\" /><br />"
                + "Year:<br /><input type=\"text\" name=\"year\" /><br />"
                + "Pages:<br /><input type=\"text\" name=\"pages\" /><br />"
                + "Kirjoittaja:<br /><input type=\"text\" name=\"author\" /><br />"
                + "Nimike:<br /><input type=\"text\" name=\"title\" /><br />"
                + "Julkaisija:<br /><input type=\"text\" name=\"publisher\" /><br />"
                + "Osoite:<br /><input type=\"text\" name=\"address\" /><br />"
                + "<input type=\"submit\" value=\"Lis&auml;&auml;\" /><br />"
                + "</form><br /><br /></div>");

        out.println("<div class=\"inproceedingslomake\" id=\"inproceedings\" style=\"display:none\">"
                + "<br /><br /><form name=\"inproceedingslomake\" method=\"post\" action=\"?action=add\"onsubmit=\"return validateForm(name)\">"
                + "<input type=\"hidden\" name=\"type\" value=\"inproceedings\" />"
                + "Kirjoittaja:<br /><input type=\"text\" name=\"author\" /><br />"
                + "Nimike:<br /><input type=\"text\" name=\"title\" /><br />"
                + "Booktitle:<br /><input type=\"text\" name=\"booktitle\" /><br />"
                + "Vuosi:<br /><input type=\"text\" name=\"year\" /><br />"
                + "Julkaisija:<br /><input type=\"text\" name=\"publisher\" /><br />"
                + "<input type=\"submit\" value=\"Lis&auml;&auml;\" /><br />"
                + "</form><br /><br /></div>");





        try {
            String arvo = request.getParameter("arvo");
            if (!arvo.isEmpty()) {
                out.println("Arvo oli: " + arvo + "<br />");
            }
        } catch (Exception e) {
            //
        }

        footer(out, cntxt);
    }

    public String generoiId(Random rng, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }
}
