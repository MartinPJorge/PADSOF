/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reserva;

import cat.quickdb.db.AdminBase;
import catalogo.InfoViajOrg;
import catalogo.InfoViajeIMSERSO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test de la clase ReservaViajOrg
 *
 * @author Jorge Martin, Ivan Marquez
 * @version 1.0
 */
public class ReservaViajOrgTest {

    private static AdminBase admin;

    public ReservaViajOrgTest() {
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
     * Test of setMargenSQL method, of class ReservaViajOrg.
     */
    @Test
    public void testSetMargenSQL() throws Exception {
        System.out.println("setMargenSQL");
        double margen = 0.3;
        String usuario = "admin";
        InfoViajOrg info = new InfoViajOrg("Países Bajos", null, null, 0.0, 1, 1,
                null, null, null, null);
        ReservaViajOrg rVO = new ReservaViajOrg(1, 2, 1993, 2, info);

        // Guardamos la informacion.
        admin.save(rVO);
        admin.close();
        ReservaViajOrg.setMargenSQL(margen, usuario, "testReserva");
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, "testReserva");

        // Obtenemos la columna de margen de la BD.
        Object[] o = admin.obtainJoin("SELECT rVO.margen FROM ReservaViajOrg AS rVO"
                + " WHERE rVO.id = 1", 1);

        boolean sonIguales = (Double.parseDouble(((String[]) (o[0]))[0]) == margen);
        assertEquals(sonIguales, true);
    }
}
