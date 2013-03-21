/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persona;

import cat.quickdb.db.AdminBase;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author ivan
 */
public class JubiladoTest {
    private AdminBase admin;
    private Jubilado j1; 
    private Jubilado j2;
    
    public JubiladoTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        j1 = new Jubilado(111119999, "Miguel", "Posada", "5647382D", 11, 3, 1993);
        j2 = new Jubilado(222228888, "Alejandro", "Morcillo", "2837465E", 20, 4, 1994);
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, "testDBJubilado");
        admin.save(j1);
        admin.save(j2);
        admin.close();
    }
    
    @After
    public void tearDown() {
    }


    /**
     * Test of DB procedures.
     * En este test, en lugar de comprobar algun metodo de la clase Jubilado, comprobamos que
     * podemos guardar objetos de dicha clase en la DB y obtenerlos sin problemas.
     */
    @Test
    public void testVendedorDB() {
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, "testDBJubilado");
        Jubilado j = new Jubilado();
        admin.obtain(j, "numSegSocial="+j1.getNumSegSocial());
        assertEquals(j.getNumSegSocial(), j1.getNumSegSocial());
        j=new Jubilado();
        admin.obtain(j, "numSegSocial="+j2.getNumSegSocial());
        assertEquals(j.getNumSegSocial(), j2.getNumSegSocial());
        admin.close();
    }
}
