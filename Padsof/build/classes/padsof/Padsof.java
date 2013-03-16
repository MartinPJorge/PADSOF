/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package padsof;

import asdf.InfoVuelo;
import cat.quickdb.db.AdminBase;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import catalogo.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
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
    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
        
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite,"vendedores");
        
        // Reserva IMSERSO
        InfoViajeIMSERSO info = new InfoViajeIMSERSO("Ibiza", 23, 3, 2, null, null, null, null);
        ReservaViajeIMSERSO reserva = new ReservaViajeIMSERSO(1, 2, 1997, 2, info);
        
        
        // Reservas viajes organizados
        InfoViajOrg infoVO = new InfoViajOrg("Países Bajos",null, null, 
                                            400, 5, 4, "martes, miércoles",
                                            "Madrid", "Bélgica, Holanda", "--");
        ReservaViajOrg reservaVO = new ReservaViajOrg(2, 9, 2007, 5,infoVO);
        ReservaViajOrg reservaVO2 = new ReservaViajOrg(2, 9, 2007, 2,infoVO);
        
        
        // Reserva Hotel
        InfoHotel infoHotel = new InfoHotel("Palace", "España", "Madrid", null,
                null, null, 5, 300, 400, 450, 40, 80, 120, "lujoso");
        ReservaHotel resHotel = new ReservaHotel(1, 4, 1994, "simple","supMP", 
                                                infoHotel);
        
        
        // Personas asociadas al paquete
        String c = "Mario";
        String v = "Luis";
        Paquete paquete = new Paquete(0, 1, c, v);
        
        // Anadimos las reservas
        paquete.addReserva(reserva);
        paquete.addReserva(reservaVO);
        paquete.addReserva(reservaVO2);
        paquete.addReserva(resHotel);
        
        
        
        Vendedor paco = new Vendedor("Paco", "PPorras", "47844956J", 1, 3, 1993, 1, "pass", "juan");
        paco.addPaquete(paquete);
        
        
        // Reseteamos las variables
        /*paquete = new Paquete();
        resHotel = new ReservaHotel();*/
        
        admin.save(paquete);
        
        
        /*Reserva r = new Reserva();
        ArrayList arr = new ArrayList();
        admin.obtain(paquete, "id = 1");
        admin.close();*/
        
        
        
        
    }
}
