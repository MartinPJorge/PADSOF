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
import myexception.ClosedPackageExc;
import myexception.NoResultsExc;
import myexception.PermissionExc;
import org.junit.*;
import static org.junit.Assert.*;
import reserva.*;

/**
 * Test de la clase Administrador
 *
 * @author Jorge Martin, Ivan Marquez
 * @version 1.0
 */
public class AdministradorTest {

    private AdminBase admin;
    private Administrador adm;
    private Vendedor v1;
    private Cliente c1;
    private Cliente c2;
    private Paquete p1;
    private Paquete p2;

    public AdministradorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {

        // Reserva Hotel
        InfoHotel infoHotel = new InfoHotel("Palace", "España", "Madrid", null,
                null, null, 5, 300, 400, 450, 40, 80, 120, "lujoso");
        ReservaHotel resHotel = new ReservaHotel(1, 4, 1994, "simple", "supMP",
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
        c1 = new Cliente("Miguel", "Posada", "5647382D", 11, 3, 1993);
        c2 = new Cliente("Alejandro", "Morcillo", "2837465E", 20, 4, 1994);
        p1 = new Paquete(0, 1, c1.getDNI(), adm.getIdUsr());
        p2 = new Paquete(1, 1, c2.getDNI(), adm.getIdUsr());
        InfoViajOrg infoVO = new InfoViajOrg("Países Bajos", null, null,
                400, 5, 4, "martes, miércoles",
                "Madrid", "Bélgica, Holanda", "--");
        ReservaViajOrg reservaVO = new ReservaViajOrg(2, 9, 2007, 5, infoVO);
        ReservaViajOrg reservaVO2 = new ReservaViajOrg(2, 9, 2007, 2, infoVO);
        try {
            p1.addReserva(reservaVO);
            p1.addReserva(resHotel);
            p1.addReserva(reservaVuelo);
            p1.addReserva(reserva2);
            p1.addReserva(reservaVO2);
            p2.addReserva(reservaVO);
        } catch (ClosedPackageExc ex) {
            Logger.getLogger(AdministradorTest.class.getName()).log(Level.SEVERE, null, ex);
        }


        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, "testDBAdministrador");
        try {
            admin = p1.guardar(admin);
            admin = p2.guardar(admin);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AdministradorTest.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error al guardar un paquete en la base de datos!");
        }
        admin.close();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getPaquetes method, of class Vendedor. Administrador example.
     * Misma prueba de obtencion de paquetes de la BD, ahora con un
     * Administrador.
     */
    @Test
    public void testGetPaquetes() {
        try {
            List<Paquete> lleno = new ArrayList<Paquete>();
            lleno.add(p1);
            lleno.add(p2);

            List<Paquete> packsBDAdm = adm.getPaquetes("testDBAdministrador");
            for (Paquete p : lleno) {
                boolean contenidoEn = false;
                for (Paquete pDB : packsBDAdm) {
                    if (p.getIdPaq() == pDB.getIdPaq()) {
                        contenidoEn = true;
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
     * Test of DB procedures. En este test, en lugar de comprobar algun metodo
     * de la clase Administrador, comprobamos que podemos guardar objetos de
     * dicha clase en la DB y obtenerlos sin problemas.
     */
    @Test
    public void testAdministradorDB() {
        try {
            admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, "testDBAdministrador");
            admin.save(adm);
            Vendedor a = new Vendedor();
            admin.obtain(a, "idUsr=" + adm.getIdUsr());
            assertEquals(a.getDNI(), adm.getDNI());
            assertEquals(true, adm.isAdmin());
            admin.close();
        } catch (PermissionExc ex) {
            Logger.getLogger(AdministradorTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Error al obtener un Administrador de la BD.");
        }
    }
}
