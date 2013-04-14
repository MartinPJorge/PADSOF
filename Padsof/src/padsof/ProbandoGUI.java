/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package padsof;

import GUI.Ventanas.BookingFrame;
import cat.quickdb.db.AdminBase;
import persona.Administrador;
import persona.Cliente;
import persona.Vendedor;
import reserva.Paquete;

/**
 *
 * @author Jorge
 */
public class ProbandoGUI {
    public static void main(String[] args) {
        // Registramos el administrador
        Booking booking = new Booking("BookingDB");
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, booking.bookingDBName);
        Administrador adm = new Administrador("admin", "Marquez", "12345678A", 1, 1, 1990, 0, "1234");
        admin.save(adm);
        admin.close();
        //inicializacion(booking);
        
        BookingFrame ppal = new BookingFrame(booking);
        
    }
    
    public static void inicializacion(Booking booking) {
        Cliente boogieMan = booking.registrarCliente("Boogie", "Man", "2837465E", 20, 4, 1994);
        Vendedor sandman = new Vendedor("Mr", "Sandman", "99887766C", 16, 1, 1991, 2, "54321", -1);
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, booking.bookingDBName);
        Administrador adm = new Administrador("admin", "Marquez", "12345678A", 1, 1, 1990, 0, "1234");
        admin.save(adm);
        admin.close();
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, booking.bookingDBName); 
        Paquete ghost = booking.crearPaquete(boogieMan, sandman);
    }
}
