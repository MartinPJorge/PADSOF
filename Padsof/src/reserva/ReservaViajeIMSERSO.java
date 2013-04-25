/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reserva;

import cat.quickdb.annotation.Column;
import cat.quickdb.annotation.Properties;
import cat.quickdb.db.AdminBase;
import catalogo.InfoViajeIMSERSO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase ReservaViajeIMSERSO
 *
 * @author Jorge Martin, Ivan Marquez
 * @version 1.0
 */
public class ReservaViajeIMSERSO extends Reserva {

    private int id;
    private static double margen = 0.0;
    private int numPersonas;
    @Column(type = Properties.TYPES.FOREIGNKEY)
    private InfoViajeIMSERSO infoViajeIMSERSO;

    public ReservaViajeIMSERSO() {
        this.infoViajeIMSERSO = null;
    }

    public ReservaViajeIMSERSO(int dia, int mes, int year, int numPersonas,
            InfoViajeIMSERSO infoViajeIMSERSO) {
        super(dia, mes, year, infoViajeIMSERSO.getPrecio(), "reservaIMSERSO");

        this.numPersonas = numPersonas;
        this.infoViajeIMSERSO = infoViajeIMSERSO;

        this.calcularPrecio();
    }

    @Override
    public int getId() {
        return id;
    }

    public static double getMargen() {
        return ReservaViajeIMSERSO.margen;
    }

    public int getNumPersonas() {
        return numPersonas;
    }

    public InfoViajeIMSERSO getInfoViajeIMSERSO() {
        return this.infoViajeIMSERSO;
    }

    public void setInfoViajeIMSERSO(InfoViajeIMSERSO infoViajeIMSERSO) {
        this.infoViajeIMSERSO = infoViajeIMSERSO;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setNumPersonas(int numPersonas) {
        this.numPersonas = numPersonas;
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

    public static void setMargen(double margen) {
        ReservaViajeIMSERSO.margen = margen;
    }

    /**
     * Cambia el margen de beneficios de las reservas de viajes organizados de
     * que el administrador lo solicite.
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
        if (usuario.equals("admin")) {
            ReservaViajeIMSERSO.setMargen(margen);

            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + nombreBD + ".db");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE ReservaViajeIMSERSO SET margen="
                    + ReservaViajeIMSERSO.getMargen() + " WHERE id > -1");
            stmt.close();
            conn.close();
            setMargen(margen,usuario);
        }
    }

    /**
     *
     * @return cantidad pagada de la reserva
     */
    public double calularPagado() {
        if (this.getEstado().equals("reservado") || this.getEstado().equals("canceladoTrasReservar")) {
            return 0.1 * this.getPrecio();
        } else {
            return this.precio;
        }
    }
    
    /**
     * Saca el m&aacute;rgen de la BD, y se lo asigna a todas las reservas de hoteles.
     * @param admin 
     */
    public static void sacaMargenBD(AdminBase admin) {
        Object[] filas = admin.obtainJoin("SELECT margen FROM ReservaViajeIMSERSO", 1);
        
        //Si la tabla no existe
        if(filas == null){return;}
        
        ReservaViajeIMSERSO.margen = Double.parseDouble((String)(((Object[])filas[0])[0]));
    }
}
