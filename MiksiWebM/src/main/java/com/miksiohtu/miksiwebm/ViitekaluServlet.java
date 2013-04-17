/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miksiohtu.miksiwebm;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bestis
 */
public class ViitekaluServlet extends HttpServlet {
    
    private String VIITEFILE="/home/jmtollik/viitteet.xml";
    
    // Kirjat
    List<HashMap> viitteet = new ArrayList();
    int lisattyjaViitteita = -1;

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
        out.println("<a href=\"?action=bibtex\">Lataa BIBTEX</a>");
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
                printList(request, response, out, cntxt);
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
    public void init() throws ServletException
    {
	try
	{
	    super.init();
	    // Load old
	    XMLDecoder d = new XMLDecoder(new BufferedInputStream(new FileInputStream(this.VIITEFILE)));
	    this.viitteet = (List<HashMap>) d.readObject();
	    d.close();
	}
	catch (FileNotFoundException ex)
	{
	    Logger.getLogger(ViitekaluServlet.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
  
    @Override
    public void destroy()
    {
	BufferedWriter writer = null;
	try
	{
	    writer = new BufferedWriter(new FileWriter(new File(this.VIITEFILE)));
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    XMLEncoder xmlEncoder = new XMLEncoder(bos);
	    xmlEncoder.writeObject(viitteet);
	    xmlEncoder.flush();
	    writer.write(bos.toString());
	    writer.close();
	}
	catch (IOException ex)
	{
	    Logger.getLogger(ViitekaluServlet.class.getName()).log(Level.SEVERE, null, ex);
	}
	finally
	{
	    try
	    {
		if (writer != null)
		{
		    writer.close();
		}
	    }
	    catch (IOException ex)
	    {
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
        Parseri parseri = new Parseri(viitteet, lisattyjaViitteita);
        response.setContentType("application/x-bibtex;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=viittet.bib");
        out.println(parseri.getBibTex());
        out.close();
    }

    private void printList(HttpServletRequest request, HttpServletResponse response, PrintWriter out, ServletContext cntxt) {
        header(request, response, out);

        out.println("<br />L&auml;hdelistaus<br /><br />");
        Iterator<HashMap> it = viitteet.iterator();
        int i = 1;
        while (it.hasNext()) {
            HashMap kirja = it.next();
            out.println("L&auml;hde " + i + ":<br />");
            Iterator hit = kirja.entrySet().iterator();
            while (hit.hasNext()) {
                Map.Entry pairs = (Map.Entry) hit.next();
                out.println(pairs.getKey() + " == " + pairs.getValue() + "<br />");
            }
            //Do something with obj
            i++;
        }
        out.println("<br /><br />");

        footer(out, cntxt);
    }

    private void addBibTex(HttpServletRequest request, HttpServletResponse response, PrintWriter out, boolean isPost, ServletContext cntxt) {
        header(request, response, out);

        // Post?
        if (isPost) {
            HashMap<String, String> viite = new HashMap();
            Enumeration paramNames = request.getParameterNames();
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
                lisattyjaViitteita++;

            

        }

        // Lis&auml;yslomake

        out.println("<script type=\"text/javascript\">function naytaKirja(id){ document.getElementById(id).style.display='block';"
                + "document.getElementById('artikkeli').style.display='none';"
                + "document.getElementById('inproceedings').style.display='none';}</script>");
        out.println("<script type=\"text/javascript\">function naytaArtikkeli(id){ document.getElementById(id).style.display='block';"
                + "document.getElementById('kirja').style.display='none';"
                + "document.getElementById('inproceedings').style.display='none';}</script>");
        out.println("<script type=\"text/javascript\">function naytaInproceedings(id) {document.getElementById(id).style.display='block';"
                + "document.getElementById('artikkeli').style.display='none';"
                + "document.getElementById('kirja').style.display='none';}</script>");

        out.println("Book:<input type=\"radio\" name=\"tyyppi\" onClick=\"naytaKirja('kirja');\"> ");
        out.println("Article:<input type=\"radio\" name=\"tyyppi\" onClick=\"naytaArtikkeli('artikkeli');\" > ");
        out.println("Inproceedings:<input type=\"radio\" name=\"tyyppi\" onClick=\"naytaInproceedings('inproceedings');\"  >");


        out.println("<div class=\"kirjalomake\" id=\"kirja\" style=\"display:none\">"
                + "<br /><br /><form method=\"post\" action=\"?action=add\">"
                + "<input type=\"hidden\" name=\"type\" value=\"book\" />"
                + "Julkaisija:<br /><input type=\"text\" name=\"publisher\" /><br />"
                + "Kirjoittaja:<br /><input type=\"text\" name=\"author\" /><br />"
                + "Nimike:<br /><input type=\"text\" name=\"title\" /><br />"
                + "Vuosi:<br /><input type=\"text\" name=\"year\" /><br />"
                + "<input type=\"submit\" value=\"Lis&auml;&auml;\" /><br />"
                + "</form><br /><br /></div>");

        out.println("<div class=\"artikkelilomake\" id=\"artikkeli\" style=\"display:none\">"
                + "<br /><br /><form method=\"post\" action=\"?action=add\">"
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
                + "<br /><br /><form method=\"post\" action=\"?action=add\">"
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
    
    
}

