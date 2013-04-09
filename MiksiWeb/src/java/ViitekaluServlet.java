/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
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
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
    {
	response.setContentType("text/html;charset=UTF-8");
	PrintWriter out = response.getWriter();
	try
	{
	    /* TODO output your page here. You may use following sample code. */
	    out.println("<!DOCTYPE html>");
	    out.println("<html>");
	    out.println("<head>");
	    out.println("<title>Servlet ViitekaluServlet</title>");	    
	    out.println("</head>");
	    out.println("<body>");
	    out.println("<h1>Servlet ViitekaluServlet at " + request.getContextPath() + "</h1>");
	 
	    try
	    {
		String action = request.getParameter("action");
		if (action.equals("42"))
	        {
		    out.println("Hidden bonus feature print list");
	        }
	    }
	    catch (Exception e)
	    {
		//
	    }
	    
	    try
	    {
		String arvo = request.getParameter("arvo");
		if (!arvo.isEmpty())
		{
		    out.println("Arvo oli: "+arvo+"<br />");
		}
	    }
	    catch (Exception e)
	    {
		//
	    }
	
	    out.println("<form method=\"post\" action=\"?\"><input type=\"text\" name=\"arvo\" /><input type=\"submit\" value=\"Lisää\" /></form>");
	    
	    ServletContext cntxt = getServletContext();
	    int latauksia = 0;
	    try
	    {
		latauksia = (Integer) cntxt.getAttribute("hitcount");
	    }
	    catch (Exception e)
	    {
		//
	    }
	    out.println("Latauksia "+latauksia+"<br />");
	    cntxt.setAttribute("hitcount", latauksia+1);
	    
	    out.println("<a href=\"?action=42\">42?</a><br />");
	    out.println("</body>");
	    out.println("</html>");
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
	processRequest(request, response);
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
	processRequest(request, response);
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
