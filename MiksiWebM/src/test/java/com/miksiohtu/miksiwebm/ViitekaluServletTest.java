/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miksiohtu.miksiwebm;

import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author bestis
 */
public class ViitekaluServletTest
{
    
    HtmlUnitDriver driver=null;
    
    public ViitekaluServletTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }

    /**
     * Test of header method, of class ViitekaluServlet.
     */
    @Test
    public void testHeader()
    {
	/*driver = new HtmlUnitDriver();
        driver.get("http://localhost:9999/MiksiWebM/ViitekaluServlet");
	System.out.println("header");
	System.out.println(driver.getPageSource());
	assertTrue(driver.getPageSource().contains("<nav>"));*/
    }

    /**
     * Test of footer method, of class ViitekaluServlet.
     */
    @Test
    public void testFooter()
    {
	/*driver = new HtmlUnitDriver();
        driver.get("http://localhost:9999/MiksiWebM/ViitekaluServlet");
	System.out.println("footer");
	System.out.println(driver.getPageSource());
	assertTrue(driver.getPageSource().contains("Latauksia"));*/
    }

    /**
     * Test of processRequest method, of class ViitekaluServlet.
     */
    @Test
    public void testProcessRequest() throws Exception
    {
	/*System.out.println("processRequest");
	HttpServletRequest request = null;
	HttpServletResponse response = null;
	boolean isPost = false;
	ViitekaluServlet instance = new ViitekaluServlet();
	instance.processRequest(request, response, isPost);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");*/
    }

    /**
     * Test of init method, of class ViitekaluServlet.
     */
    @Test
    public void testInit() throws Exception
    {
	/*System.out.println("init");
	ViitekaluServlet instance = new ViitekaluServlet();
	instance.init();
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");*/
    }

    /**
     * Test of destroy method, of class ViitekaluServlet.
     */
    @Test
    public void testDestroy()
    {
	/*System.out.println("destroy");
	ViitekaluServlet instance = new ViitekaluServlet();
	instance.destroy();
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");*/
    }

    /**
     * Test of doGet method, of class ViitekaluServlet.
     */
    @Test
    public void testDoGet() throws Exception
    {
	/*System.out.println("doGet");
	HttpServletRequest request = null;
	HttpServletResponse response = null;
	ViitekaluServlet instance = new ViitekaluServlet();
	instance.doGet(request, response);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");*/
    }

    /**
     * Test of doPost method, of class ViitekaluServlet.
     */
    @Test
    public void testDoPost() throws Exception
    {
	/*System.out.println("doPost");
	HttpServletRequest request = null;
	HttpServletResponse response = null;
	ViitekaluServlet instance = new ViitekaluServlet();
	instance.doPost(request, response);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");*/
    }

    /**
     * Test of getServletInfo method, of class ViitekaluServlet.
     */
    @Test
    public void testGetServletInfo()
    {
	/*System.out.println("getServletInfo");
	ViitekaluServlet instance = new ViitekaluServlet();
	String expResult = "";
	String result = instance.getServletInfo();
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");*/
    }

    /**
     * Test of generoiId method, of class ViitekaluServlet.
     */
    @Test
    public void testGeneroiId()
    {
	/*System.out.println("generoiId");
	Random rng = null;
	String characters = "";
	int length = 0;
	ViitekaluServlet instance = new ViitekaluServlet();
	String expResult = "";
	String result = instance.generoiId(rng, characters, length);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");*/
    }
}