/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reserva;

import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jorge
 */
public class VuelosTest {
    
    public VuelosTest() {
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

    /**
     * Test of obtenerVuelos method, of class Vuelos.<br/>
     * Se encarga de comprobar que el m&eacute;todo funciona para par&aacute;metros 
     * incorrectos.
     */
    @Test
    public void testParametrosObtenerVuelos() {
        System.out.println("obtenerVuelos");
        String salida = "";
        String llegada = "";
        Date ida = null;
        Date vuelta = null;
        List expResult = null;
        List result = Vuelos.obtenerVuelos(salida, llegada, ida, vuelta);
        assertEquals(0, result.size());
    }
    
    /**
     * Test of obtenerVuelos method, of class Vuelos.<br/>
     * Se encarga de ver que el m&eacute;todo devuelve una lista vac&iacute;a en 
     * caso de que no se encuentren resultados.
     */
    @Test
    public void testObtenerVuelosNoExisten() {
        System.out.println("obtenerVuelos");
        String salida = "";
        String llegada = "";
        Date ida = null;
        Date vuelta = null;
        List expResult = null;
        List result = Vuelos.obtenerVuelos("Jarafuel", "Requena", ida, vuelta);
        assertEquals(0, result.size());
    }

    /**
     * Test of reservar method, of class Vuelos.<br/>
     * Se encarga de comprobar que el m&eacute;todo funciona para par&aacute;metros 
     * incorrectos.
     */
    @Test
    public void testReservar() {
        System.out.println("reservar");
        String vuelo = "";
        String nombreApellido = "";
        String DNI = "";
        ReservaVuelo expResult = null;
        ReservaVuelo result = Vuelos.reservar(vuelo, nombreApellido, DNI);
        assertEquals(expResult, result);
    }
}
