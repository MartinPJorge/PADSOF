/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reserva;

import cat.quickdb.db.AdminBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Clase ReservaVuelo
 *
 * @author Jorge Martin, Ivan Marquez
 * @version 1.0
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
    
    public static void setMargen(double margen, String user) {
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
    
    public int getSuperID() {
        return super.getId();
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
            setMargen(margen, usuario);
        }        
    }
    
    /**
     * Saca el m&aacute;rgen de la BD, y se lo asigna a todas las reservas de vuelos.
     * @param admin 
     */
    public static void sacaMargenBD(AdminBase admin) {
        Object[] filas = admin.obtainJoin("SELECT margen FROM ReservaVuelo", 1);
        
        //Si la tabla no existe
        if(filas == null){return;}
        
        ReservaVuelo.margen = Double.parseDouble((String)(((Object[])filas[0])[0]));
    }
}
