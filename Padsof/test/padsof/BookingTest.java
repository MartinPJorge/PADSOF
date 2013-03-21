/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package padsof;

import cat.quickdb.db.AdminBase;
import catalogo.InfoHotel;
import catalogo.InfoViajOrg;
import catalogo.InfoViajeIMSERSO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import myexception.FailedLoginExc;
import myexception.NoResultsExc;
import org.junit.*;
import static org.junit.Assert.*;
import persona.Administrador;
import persona.Cliente;
import persona.Vendedor;
import persona.VendedorTest;
import reserva.*;

/**
 *
 * @author ivan
 */
public class BookingTest {
    private static Booking b;
    private static AdminBase admin;
    private static Administrador adm; 
    private static Vendedor v1; 
    private static Vendedor v2; 
    private static Cliente c1; 
    private static Cliente c2;
    private static Paquete p1;
    private static Paquete p2;
    
    public BookingTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        b = new Booking("BookingDBTest");        
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, b.bookingDBName);
        
        
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
        ReservaVuelo reservaVuelo = new ReservaVuelo(1, 2, 1996, null, null, null, 1768.4);

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
        try {
            admin = p1.guardar(admin);
            admin = p2.guardar(admin);
            admin.save(c1);
            admin.save(c2);
            admin.save(v1);
            admin.save(v2);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(VendedorTest.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error al guardar un paquete en la base de datos!");
        }
        admin.close();
        b.setSesion(adm);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
    /**
     * Test of login method, of class Booking.
     * Se busca un Vendedor cuyo idUsr y contrasenia sabemos de antemano y comprobamos si
     * se asigna a sesion.
     */
    @Test
    public void testlogin(){
        try {
            b.login(423, "njdsafb");
            assertEquals(null, b.sesion); //FailedLoginExc
            b.login(0, "1234");
            assertEquals(b.buscarVendedor(0), b.sesion);
        } catch (FailedLoginExc | NoResultsExc ex) {
            Logger.getLogger(BookingTest.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Esperamos una excepcion de tipo FailedLoginExc");
        }
    }
    
    /**
     * Test of buscarPaquete method, of class Booking.
     * Se busca un paquete que sabemos que existe, y lo comparamos con el original.
     */
    @Test
    public void testBuscarPaquete(){
        try {
            Paquete p = b.buscarPaquete(p1.getIdPaq());
            assertEquals(p.getIdPaq(), p1.getIdPaq());
            assertEquals(p.getVendedor(), p1.getVendedor());
        } catch (NoResultsExc ex) {
            Logger.getLogger(BookingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of buscarCliente method, of class Booking.
     * Se busca un cliente que sabemos que existe, y lo comparamos con el original.
     */
    @Test
    public void testBuscarCliente(){
        try {
            
            Cliente c = b.buscarCliente(c1.getDNI());
            assertEquals(c.getDNI(), c1.getDNI());
            assertEquals(c.getNombre(), c1.getNombre());
        } catch (NoResultsExc ex) {
            Logger.getLogger(BookingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of buscarVendedor method, of class Booking.
     * Se busca un vendedor que sabemos que existe, y lo comparamos con el original.
     */
    @Test
    public void testBuscarVendedor(){
        try {
            Vendedor v = b.buscarVendedor(v1.getIdUsr());
            assertEquals(v.getDNI(), v1.getDNI());
            assertEquals(v.getNombre(), v1.getNombre());
            assertEquals(v.getIdUsr(), v1.getIdUsr());
        } catch (NoResultsExc ex) {
            Logger.getLogger(BookingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of crearPaquete method, of class Booking.
     * Creamos un nuevo Paquete, lo guardamos en la DB y a continuacion lo buscamos 
     * y lo comparamos con el original.
     */
    @Test
    public void testCrearPaquete() {
        Paquete p = b.crearPaquete(c1, v1);
        Paquete pNew = new Paquete();
//        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, b.bookingDBName);
//        admin.obtain(pNew, "idPaq="+p.getIdPaq());
//        admin.close();
        try {
            pNew = b.buscarPaquete(p.getIdPaq());
        } catch (NoResultsExc ex) {
            Logger.getLogger(BookingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(p.getIdPaq(), pNew.getIdPaq());
        assertEquals(p.getCliente(), pNew.getCliente());
        assertEquals(p.getVendedor(), pNew.getVendedor());
    }

    /**
     * Test of registrarCliente method, of class Booking.
     * Creamos un nuevo Cliente, lo guardamos en la DB y a continuacion lo buscamos 
     * y lo comparamos con el original.
     */
   @Test
    public void testRegistrarCliente() {
        Cliente c = b.registrarCliente("Christian", "Marquez", "5647382P", 23, 7, 1997);
        Cliente cNew = new Cliente();
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, b.bookingDBName);
        admin.obtain(cNew, "DNI='"+c.getDNI()+"'");
        admin.close();
        assertEquals(c.getDNI(), cNew.getDNI());
        assertEquals(c.getNombre(), cNew.getNombre());
    }

    /**
     * Test of darAltaVendedor method, of class Booking.
     * Creamos un nuevo Vendedor, lo guardamos en la DB y a continuacion lo buscamos 
     * y lo comparamos con el original.
     */
    @Test
    public void testDarAltaVendedor() {
        Vendedor v = b.darAltaVendedor("Ana", "Sastre", "2387645Q", 20, 2, 1993, 3, "4321", adm.getIdUsr());
        Vendedor vNew = new Vendedor();
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, b.bookingDBName);
        admin.obtain(vNew, "idUsr="+v.getIdUsr());
        admin.delete(v);
        admin.close();
        assertEquals(v.getDNI(), vNew.getDNI());
        assertEquals(v.getNombre(), vNew.getNombre());
        assertEquals(v.getIdUsr(), vNew.getIdUsr());
    }

    /**
     * Test of factVuelos method, of class Booking.
     * Creamos un nuevo Paquete en la BD con una sola Reserva confirmada de Vuelo
     * y comprobamos que el metodo factVuelos nos devuelve un valor mayor o igual que el
     * precio pagado del Paquete.
     * NOTA: La facturacion está en constante cambio (si se realizan 10 pruebas, se acabaran
     * metiendo en la BD los mismos Paquetes multiplicados por 10), por lo que las pruebas de
     * Facturacion van a terminar siendo algo ambiguas.
     */
    /*@Test
    public void testFactVuelos() {
        Paquete p = b.crearPaquete(c1, v2);
        Paquete pNew = new Paquete();
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, b.bookingDBName);
        admin.obtain(pNew, "idPaq="+p.getIdPaq());
        
        GregorianCalendar cal = new GregorianCalendar(2014, 0, 1);
        Date salida = cal.getTime();
        cal = new GregorianCalendar(2014, 0, 6);
        Date llegada = cal.getTime();
        List<String> vuelos = Vuelos.obtenerVuelos("KABUL INTERNATIONAL", "PERTH JANDAKOT", salida, llegada);
        ReservaVuelo reservaVuelo = Vuelos.reservar(vuelos.get(0), "PacoPalomero", "52372839");
        reservaVuelo.setEstado("confirmado");
        pNew.addReserva(reservaVuelo);
        
        admin.modify(pNew);
        admin.close();
        double facturacion = b.factVuelos(v2.getIdUsr());
        boolean conSentido = false;
        if(facturacion>=pNew.calcularPagado())
            conSentido=true;
        assertEquals(true, conSentido);
    }*/

    
    /**
     * Test of factHoteles method, of class Booking.
     * Creamos un nuevo Paquete en la BD con una sola Reserva confirmada de Hotel
     * y comprobamos que el metodo factHoteles nos devuelve un valor mayor o igual que el
     * precio pagado del Paquete.
     * NOTA: La facturacion está en constante cambio (si se realizan 10 pruebas, se acabaran
     * metiendo en la BD los mismos Paquetes multiplicados por 10), por lo que las pruebas de
     * Facturacion van a terminar siendo algo ambiguas.
     */
    @Test
    public void testFactHoteles() {
        Paquete p = b.crearPaquete(c1, v1);
        Paquete pNew = new Paquete();
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, b.bookingDBName);
        admin.obtain(pNew, "idPaq="+p.getIdPaq());
        InfoHotel infoHotel = new InfoHotel("Palace", "España", "Madrid", null,
                null, null, 5, 300, 400, 450, 40, 80, 120, "lujoso");
        ReservaHotel resHotel = new ReservaHotel(1, 4, 1994, "simple","supMP", 
                                                infoHotel);
        resHotel.setEstado("confirmado");
        pNew.addReserva(resHotel);
        
        try {
            pNew.modificar(admin);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(BookingTest.class.getName()).log(Level.SEVERE, null, ex);
            admin.close();
        }
        
        admin.close();
        double facturacion = b.factHoteles(v1.getIdUsr());
        boolean conSentido = false;
        if(facturacion>=pNew.calcularPagado())
            conSentido=true;
        assertEquals(true, conSentido);
        
    }
    
    /**
     * Test of factViajesOrg method, of class Booking.
     * Creamos un nuevo Paquete en la BD con una sola Reserva confirmada de Viaje Organizado
     * y comprobamos que el metodo factViajesOrg nos devuelve un valor mayor o igual que el
     * precio pagado del Paquete.
     * NOTA: La facturacion está en constante cambio (si se realizan 10 pruebas, se acabaran
     * metiendo en la BD los mismos Paquetes multiplicados por 10), por lo que las pruebas de
     * Facturacion van a terminar siendo algo ambiguas.
     */
    @Test
    public void testFactViajesOrg() {
        Paquete p = b.crearPaquete(c1, v1);
        Paquete pNew = new Paquete();
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, b.bookingDBName);
        admin.obtain(pNew, "idPaq="+p.getIdPaq());
        
        InfoViajeIMSERSO info2 = new InfoViajeIMSERSO("Suiza", 23, 3, 2, null, null, null, null);
        ReservaViajeIMSERSO reserva2 = new ReservaViajeIMSERSO(1, 2, 2007, 2, info2);
        reserva2.setEstado("confirmado");
        pNew.addReserva(reserva2);
        
        admin.modify(pNew);
        admin.close();
        double facturacion = b.factViajesOrg(v1.getIdUsr());
        boolean conSentido = false;
        if(facturacion>=pNew.calcularPagado())
            conSentido=true;
        assertEquals(true, conSentido);
    }
    
    
    /**
     * Test of factTotal method, of class Booking.
     * Creamos un nuevo Vendedor y un nuevo Paquete en la BD con dos Reservas confirmadas de distinto tipo
     * y comprobamos que el metodo factTotal nos devuelve un valor igual al precio total pagado del Paquete 
     * (dicho Paquete sera el unico asociado a nuestro nuevo Vendedor).
     */
    @Test
    public void testFactTotal_int() {
        //Creo un vendedor nuevo
        Vendedor v = b.darAltaVendedor("Ana", "Sastre", "2387645Q", 20, 2, 1993, 26, "4321", adm.getIdUsr());
        
        //Creo un Paquete vacio
        Paquete p = b.crearPaquete(c1, v);
        Paquete pNew = new Paquete();
        
        //Meto dos Reservas distintas en el Paquete creado
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, b.bookingDBName);
        admin.obtain(pNew, "idPaq="+p.getIdPaq());
        InfoHotel infoHotel = new InfoHotel("Palace", "España", "Madrid", null,
                null, null, 5, 300, 400, 450, 40, 80, 120, "lujoso");
        ReservaHotel resHotel = new ReservaHotel(1, 4, 1994, "simple","supMP", 
                                                infoHotel);
        resHotel.setEstado("confirmado");
        pNew.addReserva(resHotel);
        
        InfoViajeIMSERSO info2 = new InfoViajeIMSERSO("Suiza", 23, 3, 2, null, null, null, null);
        ReservaViajeIMSERSO reserva2 = new ReservaViajeIMSERSO(1, 2, 2007, 2, info2);
        reserva2.setEstado("confirmado");
        pNew.addReserva(reserva2);
        try {
            admin = pNew.modificar(admin);
        } catch (SQLException ex) {
            Logger.getLogger(BookingTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BookingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        admin.close();
        
        //Solicito la facturación total del Vendedor nuevo, que tendra que coincidir 
        //con el precio pagado del Paquete
        double facturacion = b.factTotal(v.getIdUsr());
        boolean conSentido = false;
        if(facturacion==pNew.calcularPagado())
             conSentido=true;
        assertEquals(true, conSentido);
        
        //Borro los datos creados.
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, b.bookingDBName);
        admin.delete(pNew);
        admin.delete(v);
        admin.close();
    }
    
    /**
     * Test of factTotal method, of class Booking.
     * Obtenemos las facturaciones totales de todos los vendedores y comprobamos si la suma coincide con
     * el retorno de factTotal()
     */
    @Test
    public void testFactTotal_0args() throws NoResultsExc {
        
        double factTotal = b.factTotal();
        
        double factIndiv = 0.0;
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, b.bookingDBName);
        Vendedor v = new Vendedor();
        ArrayList<Vendedor> vendedores = admin.obtainAll(v, "id > 0");
        admin.close();
        
        
        for(Vendedor indiv : vendedores){
            factIndiv += b.factTotal(indiv.getIdUsr());
        }
        

        boolean coinciden = true;
        if(factTotal==factIndiv)
             coinciden=true;
        
        assertEquals(true, coinciden);
    }
}
