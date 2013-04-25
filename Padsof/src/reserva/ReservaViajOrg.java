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
 * Clase ReservaViajOrg
 *
 * @author Jorge Martin, Ivan Marquez
 * @version 1.0
 */
public class ReservaViajOrg extends Reserva {

    private int id;
    private static double margen = 0.0;
    private int numPersonas;
    @Column(type = Properties.TYPES.FOREIGNKEY)
    private InfoViajOrg infoViajOrg;

    public ReservaViajOrg() {
        this.infoViajOrg = null;
    }

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
    
    public int getSuperID() {
        return super.getId();
    }

    /**
     * Calcula el precio de la reserva.
     */
    public void calcularPrecio() {
        double precio;

        precio = this.numPersonas * this.precio;
        precio += precio * this.margen;

        this.precio = precio;
    }

    /**
     * Cambia el margen de beneficios de las reservas de viajes organizados cuando
     * el administrador lo solicite.
     *
     * @param margen
     * @param usuario
     */
    private static void setMargen(double margen, String usuario) {
        if (usuario.equals("admin")) {
            setMargen(margen);
        }
    }

    /**
     * Cambia el margen de beneficio de las reservas de VO, solo si es el
     * administrador el que solicita la acci&oacute;n. Adem&aacute;s se encarga
     * de actualizar los registros de la BD. <br/><u>Nota:</u><br/> Es necesario
     * cerrar toda conexi&oacute;n con la BD antes de llamar a este
     * m&eacute;todo, adem&aacute;s de pasar por argumento el nombre de la BD a
     * utilizar en este momento.
     *
     * @param margen
     * @param usuario
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void setMargenSQL(double margen, String usuario, String nombreBD) throws ClassNotFoundException, SQLException {
        double ant = ReservaViajOrg.margen;

        if (usuario.equals("admin")) {
            ReservaViajOrg.setMargen(margen);

            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + nombreBD + ".db");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE ReservaViajOrg SET margen="
                    + ReservaViajOrg.getMargen() + " WHERE id > -1");
            stmt.close();
            conn.close();
            setMargen(margen, usuario);
        }
    }

    /**
     *
     * @return cantidad pagada de la reserva
     */
    @Override
    public double calcularPagado() {
        if (this.getEstado().equals("reservado") || this.getEstado().equals("canceladoTrasReservar")) {
            return 0.1 * this.getPrecio();
        } else {
            return this.precio;
        }
    }
    
    /**
     * Saca el m&aacute;rgen de la BD, y se lo asigna a todas las reservas de viajes 
     * organizados.
     * @param admin 
     */
    public static void sacaMargenBD(AdminBase admin) {
        Object[] filas = admin.obtainJoin("SELECT margen FROM ReservaViajOrg", 1);
        
        //Si la tabla no existe
        if(filas == null){return;}
        
        ReservaViajOrg.margen = Double.parseDouble((String)(((Object[])filas[0])[0]));
    }
}
