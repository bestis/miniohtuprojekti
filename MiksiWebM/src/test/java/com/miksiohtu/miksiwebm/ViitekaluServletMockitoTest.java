/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miksiohtu.miksiwebm;

import java.beans.XMLEncoder;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.Random;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author nakke
 */
public class ViitekaluServletMockitoTest {

    public ViitekaluServletMockitoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testHeader() {
        ViitekaluServlet vk = new ViitekaluServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        PrintWriter out = mock(PrintWriter.class);

        vk.header(request, response, out);
        verify(out).println(eq("<title>ViitekaluServlet</title>"));
    }

    @Test
    public void testFooter() {
        ViitekaluServlet vk = new ViitekaluServlet();

        ServletContext cntxt = mock(ServletContext.class);
        PrintWriter out = mock(PrintWriter.class);

        vk.footer(out, cntxt);
        verify(out).println(eq("</html>"));
    }

    @Test
    public void listBibtex() {
        ViitekaluServlet vk = new ViitekaluServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        PrintWriter out = mock(PrintWriter.class);
        ServletContext cntxt = mock(ServletContext.class);
        
        vk.listBibtex(request, response, out, cntxt);
        verify(out).println(eq("<br />BibTeX:<br />"));
    }

    @Test
    public void testGeneroiId() {
        Random rng = new Random();
        String IdCharit = "ABCDEFGHIJKLMNOPQRST0123456789";
        ViitekaluServlet vk = new ViitekaluServlet();
        String result = vk.generoiId(rng, IdCharit, 5);

        assertEquals(5, result.length());

    }
}
