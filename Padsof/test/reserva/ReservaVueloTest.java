/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reserva;

import cat.quickdb.db.AdminBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
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
public class ReservaVueloTest {
    private static AdminBase admin;
    
    
    public ReservaVueloTest() {
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
     * Test of setMargenSQL method, of class ReservaVuelo.
     * 
     */
    @Test
    public void testSetMargenSQL() throws Exception {
        System.out.println("setMargenSQL");
        double margen = 0.1;
        String usuario = "admin";
        ReservaVuelo rVuelo = new ReservaVuelo(1, 1, 1998, "Juan", "32r2", "loc", 0.0);
        
        // Guardamos la informacion.
        admin.save(rVuelo);
        admin.close();
        ReservaVuelo.setMargenSQL(margen, usuario, "testReserva");
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, "testReserva");
        
        // Obtenemos la columna de margen de la BD.
        Object[] o = admin.obtainJoin("SELECT rV.margen FROM ReservaVuelo AS rV"
                + " WHERE rV.id = 1", 1);
        
        boolean sonIguales = (Double.parseDouble(((String[])(o[0]))[0]) == margen);
        assertEquals(sonIguales, true);
    }
}
