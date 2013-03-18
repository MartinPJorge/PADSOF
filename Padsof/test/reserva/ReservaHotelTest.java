/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reserva;

import cat.quickdb.db.AdminBase;
import catalogo.InfoHotel;
import catalogo.InfoViajeIMSERSO;
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
public class ReservaHotelTest {
    private static AdminBase admin;
    
    public ReservaHotelTest() {
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
     * Test of setMargenSQL method, of class ReservaHotel.
     */
    @Test
    public void testSetMargenSQL() throws Exception {
        System.out.println("setMargenSQL");
        double margen = 0.2;
        String usuario = "admin";
        InfoHotel info = new InfoHotel("Palace", "", "", "", "", "", 
                5, 1, 1, 1, 1, 1, 1, "");
        ReservaHotel rH = new ReservaHotel(1, 1, 1992, "", "", info);
        
        // Guardamos la informacion.
        admin.save(rH);
        admin.close();
        ReservaHotel.setMargenSQL(margen, usuario, "testReserva");
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, "testReserva");
        
        // Obtenemos la columna de margen de la BD.
        Object[] o = admin.obtainJoin("SELECT rH.margen FROM ReservaHotel AS rH"
                + " WHERE rH.id = 1", 1);
        
        boolean sonIguales = (Double.parseDouble(((String[])(o[0]))[0]) == margen);
        assertEquals(sonIguales, true);
    }
}
