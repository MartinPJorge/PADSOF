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
public class ReservaViajeIMSERSO extends Reserva {
    private static double margen = 0.3;
    final private String nombre;
    
    public ReservaViajeIMSERSO(Date fechaInicio, double precio, String nombre) {
        super(fechaInicio, precio);
        this.nombre = nombre;
    }
    
    public static double getMargen() {
        return ReservaViajeIMSERSO.margen;
    }
    
    public static void setMargen(double margen) {
        ReservaViajeIMSERSO.margen = margen;
    }
}
