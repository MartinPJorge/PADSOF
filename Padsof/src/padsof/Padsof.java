/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package padsof;

import cat.quickdb.db.AdminBase;
import catalogo.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import myexception.ClosedPackageExc;
import myexception.FailedLoginExc;
import myexception.NoResultsExc;
import persona.Administrador;
import persona.Vendedor;
import reserva.*;

/*
 * Usar Date, GregorianCalendar y SimpleDateFormat
 */

/**
 *
 * @author e265923
 */
public class Padsof {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, ParseException, IOException, SQLException, ClassNotFoundException, NoResultsExc, ClosedPackageExc {

        
        // Creamos los catalogos
        CatalogoHotel hoteles = new CatalogoHotel("Hoteles.csv");
        CatalogoViajIMSERSO imserso = new CatalogoViajIMSERSO("ViajesIMSERSO.csv");
        CatalogoViajOrg viajesOrg = new CatalogoViajOrg("ViajesOrganizados.csv");
        
        
        // Fechas.
        Date start= new GregorianCalendar(2014, 0, 1).getTime();
        Date end= new GregorianCalendar(2014, 0, 1).getTime();
        
        // Obtenemos vuelos a reservar
        List<String> vuelos = Vuelos.obtenerVuelosCiudad("BERLIN", 
                "LONDON", start, end);
        
        ReservaVuelo vuelo1 = Vuelos.reservar(vuelos.get(0), "Juan Palomero", "324362738"); 
        ReservaVuelo vuelo2 = Vuelos.reservar(vuelos.get(1), "Daniel Jimenez", "252729372"); 
        
        
        // Buscamos hoteles ingleses
        List<InfoHotel> resHoteles= hoteles.buscaHotel(null, "España", null,-1, -1, -1, 
                            -1, -1, -1,-1, null);
        ReservaHotel hotel1 = new ReservaHotel(1, 6, 2013, "simple", "supD", resHoteles.get(0));
        ReservaHotel hotel2 = new ReservaHotel(21, 5, 2013, "doble", "supMP", resHoteles.get(1));
        
        
        
        // Buscamos viajes organizados que duren 6 dias
        List<InfoViajOrg> resVOs = viajesOrg.buscarViajeOrg(null, 6, -1, -1, null, null);
        ReservaViajOrg vo1 = new ReservaViajOrg(2, 8, 2013, 4, resVOs.get(0));
        ReservaViajOrg vo2 = new ReservaViajOrg(12, 7, 2013, 2, resVOs.get(1));
        
        
        // Buscamos viajes del IMSERSO por 358€ que duren 15 dias.
        List<InfoViajeIMSERSO> viajesIMSERSO = imserso.buscarViajeOrg(null, 15, -1, 358, null, null);
        ReservaViajeIMSERSO vIMSERSO = new ReservaViajeIMSERSO(30, 10, 2013, 2, viajesIMSERSO.get(0));

        
        
        // Registramos el administrador
        Booking booking = new Booking("BookingDB");
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, booking.bookingDBName);
        Administrador adm = new Administrador("admin", "Marquez", "12345678A", 1, 1, 1990, 0, "1234");
        admin.save(adm);
        admin.close();
        try {
            booking.login(0, "1234");
        } catch (FailedLoginExc ex) {
            Logger.getLogger(Padsof.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        // Registramos a las personas
        booking.darAltaVendedor("Marcos", "Montero", "11223344B", 25, 2, 1992, 1, "12345", adm.getIdUsr());
        booking.darAltaVendedor("Julio", "Moreno", "99887766C", 16, 1, 1991, 2, "54321", adm.getIdUsr());
        booking.registrarCliente("Miguel", "Posada", "5647382D", 11, 3, 1993);
        booking.registrarCliente("Alejandro", "Morcillo", "2837465E", 20, 4, 1994);
        
        
        // Sacamos los vendedores de la BD
        Vendedor marcos = new Vendedor();
        Vendedor julio = new Vendedor();
        try {
            marcos = booking.buscarVendedor(1);
            julio = booking.buscarVendedor(2);
        } catch (NoResultsExc ex) {
            Logger.getLogger(Padsof.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        // Anadimos reservas a paquetes y los guardamos.
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, booking.bookingDBName);
        
        Paquete p1 = new Paquete(0, 1, "Miguel", marcos.getIdUsr());
        Paquete p2 = new Paquete(1, 1, "Alejandro", julio.getIdUsr());
        
        try {
            p1.addReserva(vo1);
            p1.addReserva(hotel1);
            p1.addReserva(vIMSERSO);
            p1.addReserva(vo2);
            
            p2.addReserva(vuelo1);
            p2.addReserva(vuelo2);
            p2.addReserva(hotel2);
        } catch (ClosedPackageExc ex) {
            Logger.getLogger(Padsof.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Guardamos los paquetes.
        admin = p2.guardar(admin);
        admin = p1.guardar(admin);
        
        
        // Cargamos uno de los paquetes
        admin.close();
        p2 = booking.buscarPaquete(1);
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, booking.bookingDBName);
        p2.cargarDatosPaqueteSQL(admin);


        // Confirmamos las reservas del segundo paquete.
        List<Reserva> reservas = p2.getReservas();
        for(Reserva nav : reservas) {
            nav.setEstado("confirmado", admin);
        }
        admin.close();
        
        // Obtenemos los datos de facturacion
        double facTotal = booking.factTotal();
        double facHoteles = booking.factHoteles(1);
        double facVendedor = booking.factTotal(1);
        double facVO = booking.factViajesOrg(1);
        double facVuelos = booking.factVuelos(1);
        
        System.out.printf("Facturacion total: %.2f\n", facTotal);
        System.out.printf("Facturacion de hoteles por el vendedor 1: %.2f\n", facHoteles);
        System.out.printf("Facturacion del vendedor 1: %.2f\n", facVendedor);
        System.out.printf("Facturacion de viajes organizados por el vendedor 2: %.2f\n", facVO);
        System.out.printf("Facturacion por vuelos: %.2f\n", facVuelos);
        
        // Se obtendra 0 en la facturacion por vuelos porque no se ha confirmado ninguno
    }
}
