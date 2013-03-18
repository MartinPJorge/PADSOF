/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reserva;

import cat.quickdb.db.AdminBase;
import es.uam.eps.pads.services.ServicesFactory;
import es.uam.eps.pads.services.flights.FlightsProvider;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

/**
 *
 * @author Jorge
 */
public class ReservaVuelo extends Reserva {
    private int id;
    private static double margen = 0.0;
    private String pasajero;
    private String pasajeroDNI;
    final private String localizador;

    public ReservaVuelo() {this.localizador = null;}
    
    public ReservaVuelo(int dia, int mes, int year, String pasajero, 
            String pasajeroDNI, String localizador, double precio) {
        super(dia, mes, year, precio, "reservaVuelo");
        
        this.pasajero = pasajero;
        this.pasajeroDNI = pasajeroDNI;
        this.localizador = localizador;
    }
    
    @Override
    public int getId() {
        return this.id;
    }
    
    @Override
    public void setId(int id) {
        this.id = id;
    }

    public static double getMargen() {
        return margen;
    }

    private static void setMargen(double margen) {
        ReservaVuelo.margen = margen;
    }
    
    public void setMargen(double margen, String user) {
        if(user.equals("admin")) {
            setMargen(margen);
        }
    }

    public String getPasajero() {
        return pasajero;
    }

    public void setPasajero(String pasajero) {
        this.pasajero = pasajero;
    }

    public String getPasajeroDNI() {
        return pasajeroDNI;
    }

    public void setPasajeroDNI(String pasajeroDNI) {
        this.pasajeroDNI = pasajeroDNI;
    }
    
    public String getLocalizador() {
        return this.localizador;
    }
    
    /**
     * 
     * @return cantidad pagada de la reserva
     */
    @Override
    public double calcularPagado() {
        if(this.getEstado().equals("reservado") || this.getEstado().equals("canceladoTrasReservar")) {
            return 0.0;
        }
        else {
            return this.precio;
        }
    }
    
    /**
     * Cambia el margen de beneficio de las reservas de VO, solo si es el 
     * administrador el que solicita la acci&oacute;n. Adem&aacute;s se 
     * encarga de actualizar los registros de la BD.
     * <br/><u>Nota:</u><br/>
     * Es necesario cerrar toda conexi&oacute;n con la BD antes de llamar a este 
     * m&eacute;todo, adem&aacute;s de pasar por argumento el nombre de la BD a 
     * utilizar en este momento.
     * 
     * @param margen
     * @param usuario
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public static void setMargenSQL(double margen, String usuario, String nombreBD) throws ClassNotFoundException, SQLException {
        if(usuario.equals("admin")) {
            ReservaVuelo.setMargen(margen);
            
            
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + nombreBD + ".db");
            Statement stmt =  conn.createStatement();
            stmt.executeUpdate("UPDATE ReservaVuelo SET margen="+
            ReservaVuelo.getMargen()+" WHERE id > -1");
            stmt.close();
            conn.close();
        }        
    }
}
