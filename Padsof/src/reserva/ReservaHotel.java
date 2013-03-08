/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reserva;

import java.util.Date;

/**
 *
 * @author e265832
 */

enum Pension{MEDIA, COMPLETA, DESAYUNO}
enum Habitacion{SUITE, INDIVIDUAL, MATRIMONIO, TRIPLE}

public class ReservaHotel extends Reserva {
    
    final private String nombre;
    private Habitacion tipoHabitacion;
    private Pension tipoPension;
    private static double margen = 0.1;


    public ReservaHotel(Habitacion tipoHabitacion, Pension tipoPension,
            Date fechaInicio, double precio, String nombre) {
        super(fechaInicio, precio);
        this.tipoHabitacion = tipoHabitacion;
        this.tipoPension = tipoPension;
        this.nombre = nombre;
    }

    public Habitacion getTipoHabitacion() {
        return tipoHabitacion;
    }

    public Pension getTipoPension() {
        return tipoPension;
    }
    
    public static double getMargen() {
        return ReservaHotel.margen;
    }

    public void setTipoPension(Pension tipoPension) {
        this.tipoPension = tipoPension;
    }
    
    public void setTipoHabitacion(Habitacion tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }
    
    public static void setMargen(double margen) {
        ReservaHotel.margen = margen;
    }
    
    public String getNombre() {
        return nombre;
    }
}
