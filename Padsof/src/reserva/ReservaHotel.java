/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reserva;

import cat.quickdb.annotation.Column;
import cat.quickdb.annotation.Properties;
import catalogo.InfoHotel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase ReservaHotel
 *
 * @author Jorge Martin, Ivan Marquez
 * @version 1.0
 */
public class ReservaHotel extends Reserva {

    private int id;
    @Column(type = Properties.TYPES.FOREIGNKEY)
    private InfoHotel infoHotel;
    private String tipoHabitacion;  // "simple" - "doble" - "triple"
    private String suplemento;  // "supD" - "supMP" - "supPC"
    private static double margen = 0.0;
    private int dias;

    public ReservaHotel() {
        this.infoHotel = null;
    }
    
    public ReservaHotel(int dia, int mes, int year, String tipoHabitacion,
            String suplemento, int dias, InfoHotel infoHotel) {
        super(dia, mes, year, 0, "reservaHotel");

        this.infoHotel = infoHotel;
        this.tipoHabitacion = tipoHabitacion;
        this.suplemento = suplemento;
        this.dias = dias;

        this.calcularPrecio();
    }

    public ReservaHotel(int dia, int mes, int year, String tipoHabitacion,
            String suplemento, InfoHotel infoHotel) {
        this(dia, mes, year, tipoHabitacion, suplemento, 0, infoHotel);
    }

    @Override
    public int getId() {
        return this.id;
    }

    public InfoHotel getInfoHotel() {
        return infoHotel;
    }

    public String getTipoHabitacion() {
        return this.tipoHabitacion;
    }

    public String getSuplemento() {
        return suplemento;
    }

    public static double getMargen() {
        return margen;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void setInfoHotel(InfoHotel infoHotel) {
        this.infoHotel = infoHotel;
    }

    public void setTipoHabitacion(String tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    public void setSuplemento(String suplemento) {
        this.suplemento = suplemento;
    }

    /**
     *
     * @param margen
     */
    private static void setMargen(double margen) {
        ReservaHotel.margen = margen;
    }

    /**
     * Cambia el margen de beneficios de las reservas de hoteles en caso de que
     * el administrador lo solicite.
     *
     * @param margen
     * @param usuario
     */
    public static void setMargen(double margen, String usuario) {
        if (usuario.equals("admin")) {
            setMargen(margen);
        }
    }
    
    public int getDia() {
        return this.dias;
    }

    public int getDias() {
        return dias;
    }
    
    public int getSuperID() {
        return super.getId();
    }

    /**
     * Cambia el margen de beneficio de las reservas de hotel, solo si es el
     * administrador el que solicita la acci&oacute;n. Adem&aacute;s se encarga
     * de actualizar los registros de la BD.
     *
     * @param margen
     * @param usuario
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void setMargenSQL(double margen, String usuario, String nombreBD) throws ClassNotFoundException, SQLException {

        if (usuario.equals("admin")) {
            ReservaHotel.setMargen(margen);

            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + nombreBD + ".db");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE ReservaHotel SET margen="
                    + ReservaHotel.getMargen() + " WHERE id > -1");
            stmt.close();
            conn.close();
        }
    }

    /**
     * Calcula el precio de la reserva en funci&oacute;n de los servicios
     * contratados, y asigna el resultado al campo 'precio' de la clase.
     */
    public void calcularPrecio() {
        double precio = 0;

        // Miramos el tipo de habitacion.
        if (this.tipoHabitacion.equals("simple")) {
            precio += this.infoHotel.getPrecioSimple();
        } else if (this.tipoHabitacion.equals("doble")) {
            precio += this.infoHotel.getPrecioDoble();
        } else if (this.tipoHabitacion.equals("triple")) {
            precio += this.infoHotel.getPrecioTriple();
        }

        // Miramos los suplementos.
        if (this.suplemento.equals("supD")) {
            precio += this.infoHotel.getSupDesayuno();
        } else if (this.suplemento.equals("supMP")) {
            precio += this.infoHotel.getSupMP();
        } else if (this.suplemento.equals("supPC")) {
            precio += this.infoHotel.getSupPC();
        }

        precio = (ReservaHotel.margen == 0) ? 
                (precio * this.dias) : (precio * this.dias * ReservaHotel.margen);
        this.precio = precio;
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
}
