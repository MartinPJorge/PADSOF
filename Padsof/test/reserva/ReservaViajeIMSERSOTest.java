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
 * Test de la clase ReservaViajeIMSERSO
 *
 * @author Jorge Martin, Ivan Marquez
 * @version 1.0
 */
public class ReservaViajeIMSERSOTest {

    private static AdminBase admin;

    public ReservaViajeIMSERSOTest() {
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
     * Test of setMargenSQL method, of class ReservaViajeIMSERSO.
     */
    @Test
    public void testSetMargenSQL() throws Exception {
        System.out.println("setMargenSQL");
        double margen = 0.2;
        String usuario = "admin";
        InfoViajeIMSERSO info = new InfoViajeIMSERSO("Ibiza", 123, 1, 2, "", "", "", "");
        ReservaViajeIMSERSO rIMSERSO = new ReservaViajeIMSERSO(1, 2, 1993, 2, info);

        // Guardamos la informacion.
        admin.save(rIMSERSO);
        admin.close();
        ReservaViajeIMSERSO.setMargenSQL(margen, usuario, "testReserva");
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, "testReserva");

        // Obtenemos la columna de margen de la BD.
        Object[] o = admin.obtainJoin("SELECT rIMS.margen FROM ReservaViajeIMSERSO AS rIMS"
                + " WHERE rIMS.id = 1", 1);

        boolean sonIguales = (Double.parseDouble(((String[]) (o[0]))[0]) == margen);
        assertEquals(sonIguales, true);
    }
}
