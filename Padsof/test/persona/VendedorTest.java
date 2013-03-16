/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persona;

import cat.quickdb.db.AdminBase;
import catalogo.InfoViajOrg;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;
import reserva.Paquete;
import reserva.ReservaViajOrg;

/**
 * http://documentation.quickdb.googlecode.com/hg/apidocs/quickdb/exception/package-tree.html
 * @author ivan
 */
public class VendedorTest {
    
    public VendedorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        Administrador adm = new Administrador("Ivan", "Marquez", "12345678A", new Date(), 0, "1234");
        Vendedor v1 = new Vendedor("Marcos", "Montero", "11223344B", new Date(), 0, "12345", adm.getNombre());
        Vendedor v2 = new Vendedor("Julio", "Moreno", "99887766C", new Date(), 0, "54321", adm.getNombre());
        Cliente c1 = new Cliente("Miguel", "Posada", "5647382D", new Date());
        Cliente c2 = new Cliente("Alejandro", "Morcillo", "2837465E", new Date());
        Paquete p1 = new Paquete(0, 1, c1.getDNI(), v1.getDNI()); //v1.getUsr()
        Paquete p2 = new Paquete(1, 1, c2.getDNI(), v1.getDNI());
        InfoViajOrg infoVO = new InfoViajOrg("Países Bajos",null, null, 
                                            400, 5, 4, "martes, miércoles",
                                            "Madrid", "Bélgica, Holanda", "--");
        ReservaViajOrg reservaVO = new ReservaViajOrg(2, 9, 2007, 5,infoVO);
        ReservaViajOrg reservaVO2 = new ReservaViajOrg(2, 9, 2007, 2,infoVO);
        p1.addReserva(reservaVO);
        p1.addReserva(reservaVO2);
        p2.addReserva(reservaVO);
        v1.añadirPaquete(p1);
        v1.añadirPaquete(p2);
  
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, "testDB");
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getPaquete method, of class Vendedor.
     */
    @Test
    public void testGetPaquete() {
        List<Paquete> vacio = new ArrayList<Paquete>();
        List<Paquete> lleno = new ArrayList<Paquete>();
        lleno.add(p1);
        lleno.add(p2);
        assertEquals(v1.getPaquete(), lleno);
        assertEquals(v2.getPaquete(), vacio);
    }

    /**
     * Test of setPaquete method, of class Vendedor.
     */
    @Test
    public void testSetPaquete() {
        List<Paquete> lleno = new ArrayList<Paquete>();
        lleno.add(p1);
        lleno.add(p2);
        v2.setPaquete(lleno);
        assertEquals(v2.getPaquete(), lleno);
        assertEquals(v1.getPaquete(), v2.getPaquete());
    }

    /**
     * Test of getIdUsr method, of class Vendedor.
     */
    @Test
    public void testGetIdUsr() {
        System.out.println("getIdUsr");
        Vendedor instance = new Vendedor();
        int expResult = 0;
        int result = instance.getIdUsr();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setIdUsr method, of class Vendedor.
     */
    @Test
    public void testSetIdUsr() {
        System.out.println("setIdUsr");
        int idUsr = 0;
        Vendedor instance = new Vendedor();
        instance.setIdUsr(idUsr);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPassword method, of class Vendedor.
     */
    @Test
    public void testGetPassword() {
        System.out.println("getPassword");
        Vendedor instance = new Vendedor();
        String expResult = "";
        String result = instance.getPassword();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAdministrador method, of class Vendedor.
     */
    @Test
    public void testGetAdministrador() {
        System.out.println("getAdministrador");
        Vendedor instance = new Vendedor();
        Administrador expResult = null;
        
        assertEquals(expResult, adm);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAdministrador method, of class Vendedor.
     */
    @Test
    public void testSetAdministrador() {
        System.out.println("setAdministrador");
        Administrador administrador = null;
        Vendedor instance = new Vendedor();
        instance.setAdministrador(administrador);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPassword method, of class Vendedor.
     */
    @Test
    public void testSetPassword() {
        System.out.println("setPassword");
        String password = "";
        Vendedor instance = new Vendedor();
        instance.setPassword(password);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Vendedor.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Vendedor instance = new Vendedor();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mostrarDatos method, of class Vendedor.
     */
    @Test
    public void testMostrarDatos() {
        System.out.println("mostrarDatos");
        Vendedor instance = new Vendedor();
        instance.mostrarDatos();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
