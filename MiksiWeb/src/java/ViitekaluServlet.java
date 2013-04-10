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

/**
 *
 * @author bestis
 */
public class ViitekaluServlet extends HttpServlet
{
    // Kirjat
    List<HashMap> kirjat = new ArrayList();

    /**
     * Print default header
     *
     * @param request
     * @param response
     * @param out
     */
    protected void header(HttpServletRequest request, HttpServletResponse response, PrintWriter out)
    {
	response.setContentType("text/html;charset=UTF-8");
	out.println("<!DOCTYPE html>");
	out.println("<html>");
	out.println("<head>");
	out.println("<title>ViitekaluServlet</title>");
	out.println("</head>");
	out.println("<body>");
	out.println("<h1>ViitekaluServlet at " + request.getContextPath() + "</h1>");

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
    protected void footer(PrintWriter out, ServletContext cntxt)
    {
	int latauksia = 0;
	try
	{
	    latauksia = (Integer) cntxt.getAttribute("hitcount");
	}
	catch (Exception e)
	{
	    //
	}
	out.println("Latauksia " + latauksia + ", Kirjoja " + kirjat.size() + "<br />");
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
	    throws ServletException, IOException
    {

	ServletContext cntxt = getServletContext();

	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");

	PrintWriter out = response.getWriter();
	try
	{

	    // Handle action parameter
	    String action;
	    try
	    {
		action = request.getParameter("action");
		if (action == null)
		{
		    action = "";
		}
	    }
	    catch (Exception e)
	    {
		action = "";
	    }

	    // BIBTEX-export
	    if (action.equals("bibtex"))
	    {
		response.setContentType("application/x-bibtex;charset=UTF-8");
		out.println("BIBTEX");
	    }
	    else if (action.equals("list"))
	    {
		header(request, response, out);

		out.println("<br />L&auml;hdelistaus<br /><br />");
		Iterator<HashMap> it = kirjat.iterator();
		int i = 1;
		while (it.hasNext())
		{
		    HashMap kirja = it.next();
		    out.println("L&auml;hde " + i + ":<br />");
		    Iterator hit = kirja.entrySet().iterator();
		    while (hit.hasNext())
		    {
			Map.Entry pairs = (Map.Entry) hit.next();
			out.println(pairs.getKey() + " == " + pairs.getValue() + "<br />");
		    }
		    //Do something with obj
		    i++;
		}
		out.println("<br /><br />");

		footer(out, cntxt);
	    }
	    else if (action.equals("add"))
	    {
		header(request, response, out);

		// Post?
		if (isPost)
		{
		    HashMap<String, String> kirja = new HashMap();
		    Enumeration paramNames = request.getParameterNames();
		    while (paramNames.hasMoreElements())
		    {
			String paramName = (String) paramNames.nextElement();
			if (paramName.equals("action"))
			{
			    continue;
			}
			String paramValue = (String) request.getParameter(paramName);
			kirja.put(paramName, paramValue);
			//out.print(paramName+"=="+paramValue);
		    }
		    kirjat.add(kirja);
		}

		// Lis&auml;yslomake
		out.println("<br /><br /><form method=\"post\" action=\"?action=add\">"
			+ "Kirjoittaja:<br /><input type=\"text\" name=\"author\" /><br />"
			+ "Nimike:<br /><input type=\"text\" name=\"title\" /><br />"
			+ "Vuosi:<br /><input type=\"text\" name=\"year\" /><br />"
			+ "<input type=\"submit\" value=\"Lis&auml;&auml;\" /><br />"
			+ "</form><br /><br />");

		try
		{
		    String arvo = request.getParameter("arvo");
		    if (!arvo.isEmpty())
		    {
			out.println("Arvo oli: " + arvo + "<br />");
		    }
		}
		catch (Exception e)
		{
		    //
		}

		footer(out, cntxt);
	    }
	    else
	    {
		header(request, response, out);
		out.println("<h1>Tervetuloa!</h1>");
		footer(out, cntxt);
	    }
	}
	finally
	{
	    out.close();
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
	    throws ServletException, IOException
    {
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
	    throws ServletException, IOException
    {
	processRequest(request, response, true);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
	return "Short description";
    }// </editor-fold>
}
