/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reserva;

import cat.quickdb.annotation.Column;
import cat.quickdb.annotation.Properties;
import cat.quickdb.db.AdminBase;
import catalogo.InfoViajOrg;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Jorge
 */
public class ReservaViajOrg extends Reserva {
    private int id;
    private static double margen = 0.0;
    private int numPersonas;
    @Column(type=Properties.TYPES.FOREIGNKEY)
    private InfoViajOrg infoViajOrg;
    
    public ReservaViajOrg() {this.infoViajOrg = null;}
    
    public ReservaViajOrg(int dia, int mes, int year, int numPersonas,
            InfoViajOrg infoViajOrg) {
        super(dia, mes, year, infoViajOrg.getPrecio(), "reservaVO");
        
        this.numPersonas = numPersonas;
        this.infoViajOrg = infoViajOrg;
        
        this.calcularPrecio();
    }
    
    @Override
    public int getId() {
        return id;
    }

    public static double getMargen() {
        return ReservaViajOrg.margen;
    }    

    public int getNumPersonas() {
        return this.numPersonas;
    }

    public InfoViajOrg getInfoViajOrg() {
        return this.infoViajOrg;
    }
    
    @Override
    public void setId(int id) {
        this.id = id;
    }

    public static void setMargen(double margen) {
        ReservaViajOrg.margen = margen;
    }

    public void setNumPersonas(int numPersonas) {
        this.numPersonas = numPersonas;
    }

    public void setInfoViajOrg(InfoViajOrg infoViajOrg) {
        this.infoViajOrg = infoViajOrg;
    }
    
    /**
     * Calcula el precio de la reserva.
     */
    @Override
    public void calcularPrecio() {
        double precio;
        
        precio = this.numPersonas * this.precio;
        precio += precio * this.margen;
        
        this.precio = precio;
    }
    
    
    /**
     * Cambia el margen de beneficios de las reservas de viajes organizados
     * de que el administrador lo solicite.
     * @param margen
     * @param usuario 
     */
    private static void setMargen(double margen, String usuario) {
        if(usuario.equals("admin")) {
            setMargen(margen);
        }
    }
    
    /**
     * Cambia el margen de beneficio de las reservas de VO, solo si es el 
     * administrador el que solicita la acci&oacute;n. Adem&aacute;s se 
     * encarga de actualizar los registros de la BD.
     * 
     * @param margen
     * @param usuario
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public static void setMargenSQL(double margen, String usuario, AdminBase admin) throws ClassNotFoundException, SQLException {
        double ant = ReservaViajOrg.margen;
        
        if(usuario.equals("admin")) {
            ReservaViajOrg.setMargen(margen);
            
            // Cerramos la conexion del QuickDB
            String nombreBD = admin.getDB().name();
            admin.close();
            
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:vendedores.db");
            Statement stmt =  conn.createStatement();
            stmt.executeUpdate("UPDATE ReservaViajOrg SET margen="+
            ReservaViajOrg.getMargen()+" WHERE id > -1");
            stmt.close();
            conn.close();
            
            // Volvemos a abrir la conexion QuickDB
            admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, nombreBD);
        }        
    }
        
}
