/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reserva;

import cat.quickdb.annotation.Column;
import cat.quickdb.annotation.Properties;
import catalogo.InfoHotel;
import java.sql.Date;

/**
 *
 * @author e265832
 */

public class ReservaHotel extends Reserva {
    
    private int id;
    @Column(type=Properties.TYPES.FOREIGNKEY)
    private InfoHotel infoHotel;
    private String tipoHabitacion;  // "simple" - "doble" - "triple"
    private String suplemento;  // "supD" - "supMP" - "supPC"
    private static double margen = 0.0;
    
    public ReservaHotel() {this.infoHotel = null;}
    
    public ReservaHotel(int dia, int mes, int year, String tipoHabitacion, 
            String suplemento, InfoHotel infoHotel) {
        super(dia, mes, year,0, "reservaHotel");
        
        this.infoHotel = infoHotel;
        this.tipoHabitacion = tipoHabitacion;
        this.suplemento = suplemento;
        
        this.calcularPrecio();
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

    public static void setMargen(double margen) {
        ReservaHotel.margen = margen;
    }
        
    /**
     * Calcula el precio de la reserva en funci&oacute;n de los servicios 
     * contratados, y asigna el resultado al campo 'precio' de la clase.
     */
    @Override
    public void calcularPrecio() {
        double precio = 0;
        
        // Miramos el tipo de habitacion.
        if(this.tipoHabitacion.equals("simple")) {
            precio += this.infoHotel.getPrecioSimple();
        }
        else if(this.tipoHabitacion.equals("doble")) {
            precio += this.infoHotel.getPrecioDoble();
        }
        else if(this.tipoHabitacion.equals("triple")) {
            precio += this.infoHotel.getPrecioTriple();
        }
        
        // Miramos los suplementos.
        if(this.suplemento.equals("supD")) {
            precio += this.infoHotel.getSupDesayuno();
        }
        else if(this.suplemento.equals("supMP")) {
            precio += this.infoHotel.getSupMP();
        }
        else if(this.suplemento.equals("supPC")) {
            precio += this.infoHotel.getSupPC();
        }
        
        precio += precio * ReservaHotel.margen;
        this.precio = precio;
    }
}
