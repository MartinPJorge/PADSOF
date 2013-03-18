/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package padsof;

import cat.quickdb.db.AdminBase;
import catalogo.InfoHotel;
import catalogo.InfoViajOrg;
import catalogo.InfoViajeIMSERSO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import reserva.Paquete;
import reserva.ReservaHotel;
import reserva.ReservaViajOrg;
import reserva.ReservaViajeIMSERSO;
import reserva.ReservaVuelo;
import reserva.Vuelos;

/**
 *
 * @author Jorge
 */
public class Prueba2 {
    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException, ClassNotFoundException, SQLException {
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite,"reservas");
        
        
        // Reserva IMSERSO
        InfoViajeIMSERSO info = new InfoViajeIMSERSO("Ibiza", 23, 3, 2, null, null, null, null);
        ReservaViajeIMSERSO reserva = new ReservaViajeIMSERSO(1, 2, 1997, 2, info);
        
        
        // Reservas viajes organizados
        InfoViajOrg infoVO = new InfoViajOrg("Países Bajos",null, null, 
                                            400, 5, 4, "martes, miércoles",
                                            "Madrid", "Bélgica, Holanda", "--");
        ReservaViajOrg reservaVO = new ReservaViajOrg(2, 9, 2007, 5,infoVO);
        
        // Reserva Hotel
        InfoHotel infoHotel = new InfoHotel("Palace", "España", "Madrid", null,
                null, null, 5, 300, 400, 450, 40, 80, 120, "lujoso");
        ReservaHotel resHotel = new ReservaHotel(1, 4, 1994, "simple","supMP", 
                                                infoHotel);
        
        
        // Reserva IMSERSO - 2
        InfoViajeIMSERSO info2 = new InfoViajeIMSERSO("Suiza", 23, 3, 2, null, null, null, null);
        ReservaViajeIMSERSO reserva2 = new ReservaViajeIMSERSO(1, 2, 2007, 2, info2);
        
        
        // Reserva Hotel - 2
        InfoHotel infoHotel2 = new InfoHotel("Richs", "España", "Valladolid", null,
                null, null, 5, 300, 400, 450, 40, 80, 120, "cutre");
        ReservaHotel resHotel2 = new ReservaHotel(1, 4, 1994, "doble","supMP", 
                                                infoHotel2);
        
        // Reserva Hotel - 3
        InfoHotel infoHotel3 = new InfoHotel("Melia", "España", "Valencia", null,
                null, null, 4, 300, 400, 450, 40, 80, 120, "adecuado");
        ReservaHotel resHotel3 = new ReservaHotel(1, 4, 1994, "doble","supMP", 
                                                infoHotel3);
        
        
        // Reserva IMSERSO - 3
        InfoViajeIMSERSO info3 = new InfoViajeIMSERSO("Noruega", 23, 3, 2, null, null, null, null);
        ReservaViajeIMSERSO reserva3 = new ReservaViajeIMSERSO(1, 2, 2007, 2, info3);
        
        // ReservaVuelo
        GregorianCalendar cal = new GregorianCalendar(2014, 0, 1);
        Date salida = cal.getTime();
        cal = new GregorianCalendar(2014, 0, 6);
        Date llegada = cal.getTime();
        List<String> vuelos = Vuelos.obtenerVuelos("KABUL INTERNATIONAL", "PERTH JANDAKOT", salida, llegada);
        ReservaVuelo reservaVuelo = Vuelos.reservar(vuelos.get(0), "PacoPalomero", "52372839");
        
        
        // Personas asociadas al paquete
        String c = "Mario";
        String v = "Luis";
        Paquete paquete = new Paquete(1, c, v);
        Paquete paquete2 = new Paquete(1,c,v);
        Paquete paquete3 = new Paquete(1,c,v);
        
        // Anadimos las reservas
        paquete.addReserva(reserva);
        paquete.addReserva(reservaVO);
        paquete.addReserva(resHotel);
        
        // Anadimos las reservas
        paquete2.addReserva(reserva2);
        //paquete2.addReserva(resHotel2);
        paquete2.addReserva(reservaVuelo);
        
        // Anadimos las reservas
        paquete3.addReserva(reserva3);
        paquete3.addReserva(resHotel3);
        
        // Guardamos paquetes
        paquete.guardar(admin);
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite,"reservas");
        paquete2.guardar(admin);
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite,"reservas");
        paquete3.guardar(admin);
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite,"reservas");
        
        
        // Reseteamos las variables
        /*paquete = new Paquete();
        paquete2 = new Paquete();
        resHotel = new ReservaHotel();
        
        
        admin.obtain(paquete, "id = 1");
        paquete.cargarDatosPaqueteSQL(admin);
        
        for(Reserva r : paquete.getReservas()) {
            r.setEstado("confirmado", admin);
        }*/
        
        
        admin.close();
    }
}
