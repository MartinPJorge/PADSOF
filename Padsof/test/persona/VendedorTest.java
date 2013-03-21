/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persona;

import cat.quickdb.db.AdminBase;
import catalogo.InfoHotel;
import catalogo.InfoViajOrg;
import catalogo.InfoViajeIMSERSO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import myexception.NoResultsExc;
import myexception.PermissionExc;
import org.junit.*;
import reserva.Paquete;
import reserva.ReservaViajOrg;
import static org.junit.Assert.*;
import reserva.*;


/**
 * http://documentation.quickdb.googlecode.com/hg/apidocs/quickdb/exception/package-tree.html
 * @author ivan
 */
public class VendedorTest {
    private AdminBase admin;
    private Administrador adm; 
    private Vendedor v1; 
    private Vendedor v2; 
    private Cliente c1; 
    private Cliente c2;
    private Paquete p1;
    private Paquete p2;
    
    public VendedorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() throws SQLException {
        
        // Reserva Hotel
        InfoHotel infoHotel = new InfoHotel("Palace", "España", "Madrid", null,
                null, null, 5, 300, 400, 450, 40, 80, 120, "lujoso");
        ReservaHotel resHotel = new ReservaHotel(1, 4, 1994, "simple","supMP", 
                                                infoHotel);
        
        // Reserva IMSERSO - 2
        InfoViajeIMSERSO info2 = new InfoViajeIMSERSO("Suiza", 23, 3, 2, null, null, null, null);
        ReservaViajeIMSERSO reserva2 = new ReservaViajeIMSERSO(1, 2, 2007, 2, info2);
        
        // ReservaVuelo
        GregorianCalendar cal = new GregorianCalendar(2014, 0, 1);
        Date salida = cal.getTime();
        cal = new GregorianCalendar(2014, 0, 6);
        Date llegada = cal.getTime();
        List<String> vuelos = Vuelos.obtenerVuelos("KABUL INTERNATIONAL", "PERTH JANDAKOT", salida, llegada);
        ReservaVuelo reservaVuelo = Vuelos.reservar(vuelos.get(0), "PacoPalomero", "52372839");

        adm = new Administrador("Ivan", "Marquez", "12345678A", 1, 1, 1990, 0, "1234");
        v1 = new Vendedor("Marcos", "Montero", "11223344B", 25, 2, 1992, 1, "12345", adm.getIdUsr());
        v2 = new Vendedor("Julio", "Moreno", "99887766C", 16, 1, 1991, 2, "54321", adm.getIdUsr());
        c1 = new Cliente("Miguel", "Posada", "5647382D", 11, 3, 1993);
        c2 = new Cliente("Alejandro", "Morcillo", "2837465E", 20, 4, 1994);
        p1 = new Paquete(0, 1, c1.getDNI(), v1.getIdUsr());
        p2 = new Paquete(1, 1, c2.getDNI(), v1.getIdUsr());
        InfoViajOrg infoVO = new InfoViajOrg("Países Bajos", null, null,
                400, 5, 4, "martes, miércoles",
                "Madrid", "Bélgica, Holanda", "--");
        ReservaViajOrg reservaVO = new ReservaViajOrg(2, 9, 2007, 5, infoVO);
        ReservaViajOrg reservaVO2 = new ReservaViajOrg(2, 9, 2007, 2, infoVO);
        p1.addReserva(reservaVO);
        p1.addReserva(resHotel);
        p1.addReserva(reservaVuelo);
        p1.addReserva(reserva2);
        p1.addReserva(reservaVO2);
        p2.addReserva(reservaVO);

        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, "testDBVendedor");
        try {
            admin = p1.guardar(admin);
            admin = p2.guardar(admin);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VendedorTest.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error al guardar un paquete en la base de datos!");
        }
        admin.close();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getPaquetes method, of class Vendedor.
     * Creamos una lista de paquetes (lleno) con los paquetes que hemos guardado en la BD relacionados
     * con el vendedor 1 (v1). Obtenemos los paquetes de la BD con getPaquetes y comprobamos
     * que coinciden.
     */
    @Test
    public void testGetPaquetes() {
        try {
            List<Paquete> lleno = new ArrayList<Paquete>();
            lleno.add(p1);
            lleno.add(p2);
            
            List<Paquete> packsBDV1 = v1.getPaquetes("testDBVendedor");
            for(Paquete p : lleno){
                boolean contenidoEn = false;
                for(Paquete pDB : packsBDV1){
                    if(p.getIdPaq()==pDB.getIdPaq()){
                        contenidoEn=true;
                        break;
                    }
                }
                assertEquals(true, contenidoEn);
            }
        } catch (NoResultsExc ex) {
            Logger.getLogger(VendedorTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Los paquetes obtenidos de la BD no coinciden con los originales.");
        }
    }
    
    /**
     * Test of isAdmin method, of class Vendedor.
     * Comprobamos que, efectivamente, el método isAdmin() nos detecta si somos o no
     * administradores (incluso llamando al método desde un objeto de tipo Vendedor
     * que en tiempo de ejecución sea un Administrador).
     */
    @Test
    public void testIsAdmin() {
        try {
            Vendedor v = adm;
            assertEquals(adm.isAdmin(), true);
            assertEquals(v.isAdmin(), true);
        } catch (PermissionExc ex) {
            Logger.getLogger(VendedorTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("No detectamos bien si somos Administrador.");
        }
    }
    
    
    /**
     * Test of DB procedures.
     * En este test, en lugar de comprobar algun metodo de la clase Vendedor, comprobamos que
     * podemos guardar objetos de dicha clase en la DB y obtenerlos sin problemas.
     */
    @Test
    public void testVendedorDB() {
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, "testDBVendedor");
        admin.save(v1);
        admin.save(v2);
        Vendedor v = new Vendedor();
        admin.obtain(v, "idUsr="+v1.getIdUsr());
        assertEquals(v.getDNI(), v1.getDNI());
        v=new Vendedor();
        admin.obtain(v, "idUsr="+v2.getIdUsr());
        assertEquals(v.getDNI(), v2.getDNI());      
        admin.close();
    }
    
}
