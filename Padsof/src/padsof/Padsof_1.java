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
import java.util.ArrayList;
import persona.Vendedor;
import reserva.*;

/*
 * Usar Date, GregorianCalendar y SimpleDateFormat
 */

/**
 *
 * @author e265923
 */
public class Padsof_1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException, SQLException, InterruptedException, ClassNotFoundException {

        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite,"vendedores");
        CatalogoHotel catalogo = new CatalogoHotel("Hoteles.csv");
        
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
        
        
        
        // Personas asociadas al paquete
        String c = "Mario";
        String v = "Luis";
        Paquete paquete = new Paquete(1, c, v);
        Paquete paquete2 = new Paquete(1,c,v);
        Paquete paquete3 = new Paquete(1,c,v);
        
        // Anadimos las reservas
        paquete.addReserva(reserva);
        paquete.addReserva(reservaVO);
        paquete.addReserva(reservaVO2);
        paquete.addReserva(resHotel);
        
        // Anadimos las reservas
        paquete2.addReserva(reserva2);
        paquete2.addReserva(resHotel2);
        
        // Anadimos las reservas
        paquete3.addReserva(reserva3);
        paquete3.addReserva(resHotel3);
        
        
        
        Vendedor paco = new Vendedor("Paco", "PPorras", "47844956J", 1, 3, 1993, 1, "pass", "juan");
        paco.addPaquete(paquete);
        paco.addPaquete(paquete2);
        paco.addPaquete(paquete3);
  
        // Reseteamos las variables
        paquete = new Paquete();
        paquete2 = new Paquete();
        resHotel = new ReservaHotel();
        paco = new Vendedor();
        
        
        //admin.save(paco);        

        
        admin.obtain(paco, "id = 1");
        paquete = paco.getPaquete().get(0);
        paquete.cargarDatosPaqueteSQL(admin);
        
        paquete.getReservasHotel().get(0).setEstado("confirmado", admin);

        
        
        admin.close();
        
        
        // Actualizamos los indices incorrectos.
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:vendedores.db");
        Statement stmt =  conn.createStatement();
        stmt.executeUpdate("UPDATE PaqueteReserva SET related=id WHERE id > 0");
        stmt.close();
        conn.close();
        
    }
}
