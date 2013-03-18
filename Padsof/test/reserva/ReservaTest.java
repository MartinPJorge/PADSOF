/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reserva;

import cat.quickdb.db.AdminBase;
import catalogo.InfoViajeIMSERSO;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Jorge
 */
public class ReservaTest {
    private static AdminBase admin;
    
    public ReservaTest() {
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
     * Test of setEstado method, of class Reserva.
     */
    @Test
    public void testSetEstado() {
        System.out.println("setEstado");
        InfoViajeIMSERSO info = new InfoViajeIMSERSO("Ibiza", 1, 1, 1, null, null, null, null);
        ReservaViajeIMSERSO rIMS = new ReservaViajeIMSERSO(1, 1, 1991, 2, info);
        
        // Guardamos la reserva y cambiamos el estado.
        admin.save(rIMS);
        admin.obtain(rIMS, "id = 1");
        rIMS.setEstado("cancelado", admin);

        // Obtenemos lo guardado
        rIMS = new ReservaViajeIMSERSO();
        admin.obtain(rIMS, "id = 1");
        
        assertEquals("canceladoTrasReservar", rIMS.getEstado());
    }
}
