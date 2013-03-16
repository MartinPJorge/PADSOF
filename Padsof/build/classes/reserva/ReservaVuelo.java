/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reserva;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Jorge
 */
public class ReservaVuelo extends Reserva {
    private static double margen = 0.05;
    private String pasajero;
    final private String code;
    // Falta el objeto con la informacion
    // Falta el cliente que sera pasajero, y que sustituira al String
    
    public ReservaVuelo(Date fechaInicio, double precio, String pasajero, String code) {
        super(fechaInicio, precio);
        this.pasajero = pasajero;
        this.code = code;
    }
    
    public static double getMargen() {
        return ReservaVuelo.margen;
    }
    
    public static void setMargen(double margen) {
        ReservaVuelo.margen = margen;
    }
}
