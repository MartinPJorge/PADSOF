/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reserva;

import es.uam.eps.pads.services.InvalidParameterException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import org.junit.*;

/**
 * Test de la clase Vuelos
 *
 * @author Jorge Martin, Ivan Marquez
 * @version 1.0
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
        System.out.println("parametrosObtenerVuelos");
        String salida = "";
        String llegada = "";
        Date ida = null;
        Date vuelta = null;
        List result;
        try {
            result = Vuelos.obtenerVuelos(salida, llegada, ida, vuelta);
        } catch (InvalidParameterException ex) {
            Logger.getLogger(VuelosTest.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        assertEquals(0, result.size());
    }
    
    /**
     * Test of obtenerVuelos method, of class Vuelos.<br/>
     * Se encarga de ver que el m&eacute;todo devuelve una lista vac&iacute;a en 
     * caso de que no se encuentren resultados.
     */
    @Test
    public void testObtenerVuelosNoExisten() {
        System.out.println("obtenerVuelosNoExisten");
        Date ida = null;
        Date vuelta = null;
        List result;
        try {
            result = Vuelos.obtenerVuelos("Jarafuel", "Requena", ida, vuelta);
        } catch (InvalidParameterException ex) {
            Logger.getLogger(VuelosTest.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
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
    
    /**
     * Comprobamos que se obtienen resultados en la consulta de vuelos disponibles 
     * entre 2 aeropuertos.
     */
    @Test
    public void testObtenerVuelos() {
        System.out.println("obtenerVuelos");
        List<String> vuelos;
        try {
            vuelos = Vuelos.obtenerVuelos("KABUL INTERNATIONAL", 
       "PERTH JANDAKOT", new Date(), new Date());
        } catch (InvalidParameterException ex) {
            Logger.getLogger(VuelosTest.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        assertEquals(false, vuelos.isEmpty());
    }
    
    /**
     * Prueba a obtener todos los vuelos existentes entre Berl&iacute;n y Londres 
     * entre las fechas especificadas.
     */
    @Test
    public void testObtenerVuelosCiudad() {
        System.out.println("obtenerVuelosCiudad");
        
        
        Date start= new GregorianCalendar(2014, 0, 1).getTime();
        Date end= new GregorianCalendar(2014, 0, 1).getTime();
        List<String> vuelos = Vuelos.obtenerVuelosCiudad("BERLIN", 
                "LONDON", start, end);
        assertEquals(false, vuelos.isEmpty());
    }
}
