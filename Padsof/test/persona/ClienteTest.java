/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persona;

import cat.quickdb.db.AdminBase;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Test de la clase Cliente
 *
 * @author Jorge Martin, Ivan Marquez
 * @version 1.0
 */
public class ClienteTest {

    private AdminBase admin;
    private Cliente c1;
    private Cliente c2;

    public ClienteTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        c1 = new Cliente("Miguel", "Posada", "5647382D", 11, 3, 1993);
        c2 = new Cliente("Alejandro", "Morcillo", "2837465E", 20, 4, 1994);
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, "testDBCliente");
        admin.save(c1);
        admin.save(c2);
        admin.close();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of DB procedures. En este test, en lugar de comprobar algun metodo
     * de la clase Cliente, comprobamos que podemos guardar objetos de dicha
     * clase en la DB y obtenerlos sin problemas.
     */
    @Test
    public void testVendedorDB() {
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, "testDBCliente");
        Persona c = new Persona();
        admin.obtain(c, "DNI='" + c1.getDNI() + "'");
        assertEquals(c.getDNI(), c1.getDNI());
        c = new Persona();
        admin.obtain(c, "DNI='" + c2.getDNI() + "'");
        assertEquals(c.getDNI(), c2.getDNI());
        admin.close();
    }
}
