/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reserva;

import cat.quickdb.db.AdminBase;
import catalogo.InfoHotel;
import catalogo.InfoViajOrg;
import catalogo.InfoViajeIMSERSO;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test de la clase Paquete
 *
 * @author Jorge Martin, Ivan Marquez
 * @version 1.0
 */
public class PaqueteTest {

    private static AdminBase admin;

    public PaqueteTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, "testReserva");
    }

    @AfterClass
    public static void tearDownClass() {
        admin.close();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of compPaquete method, of class Paquete.
     */
    @Test
    public void testCompPaquete() throws Exception {
        System.out.println("compPaquete");
        Paquete instance = new Paquete(0, 1, "Juan", 12);
        InfoViajeIMSERSO info = new InfoViajeIMSERSO("Ibiza", 123, 1, 2, "", "", "", "");
        ReservaViajeIMSERSO rIMSERSO = new ReservaViajeIMSERSO(1, 2, 1993, 2, info);
        instance.addReserva(rIMSERSO);

        String fechaMasCercana = instance.compPaquete();
        assertEquals(instance.getAbierto(), 0);
    }

    /**
     * Test of cargarDatosPaqueteSQL method, of class Paquete.
     */
    @Test
    public void testCargarDatosPaqueteSQL() throws Exception {
        System.out.println("cargarDatosPaqueteSQL");

        Paquete paquete = new Paquete(0, 1, "Lucas", 1);

        // Reserva IMSERSO
        InfoViajeIMSERSO info = new InfoViajeIMSERSO("Ibiza", 23, 3, 2, null, null, null, null);
        ReservaViajeIMSERSO reserva = new ReservaViajeIMSERSO(1, 2, 1997, 2, info);


        // Reservas viajes organizados
        InfoViajOrg infoVO = new InfoViajOrg("Países Bajos", null, null,
                400, 5, 4, "martes, miércoles",
                "Madrid", "Bélgica, Holanda", "--");
        ReservaViajOrg reservaVO = new ReservaViajOrg(2, 9, 2007, 5, infoVO);

        // Reserva Hotel
        InfoHotel infoHotel = new InfoHotel("Palace", "España", "Madrid", null,
                null, null, 5, 300, 400, 450, 40, 80, 120, "lujoso");
        ReservaHotel resHotel = new ReservaHotel(1, 4, 1994, "simple", "supMP",
                infoHotel);

        // ReservaVuelo
        GregorianCalendar cal = new GregorianCalendar(2014, 0, 1);
        Date salida = cal.getTime();
        cal = new GregorianCalendar(2014, 0, 6);
        Date llegada = cal.getTime();
        List<String> vuelos = Vuelos.obtenerVuelos("KABUL INTERNATIONAL", "PERTH JANDAKOT", salida, llegada);
        ReservaVuelo reservaVuelo = Vuelos.reservar(vuelos.get(0), "PacoPalomero", "52372839");

        // Metemos las reservas
        paquete.addReserva(reserva);
        paquete.addReserva(reservaVO);
        paquete.addReserva(resHotel);
        paquete.addReserva(reservaVuelo);


        // Guardamos el paquete
        admin = paquete.guardar(admin);


        paquete = new Paquete();
        admin.obtain(paquete, "cliente = 'Lucas'");

        paquete.cargarDatosPaqueteSQL(admin);

        ReservaHotel resSaved = paquete.getReservasHotel().get(0);

        boolean sonIguales = (resSaved.getSuplemento().equals(resHotel.getSuplemento()));
        assertEquals(true, sonIguales);
    }
}
