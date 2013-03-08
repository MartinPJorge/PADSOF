/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reserva;

import java.util.Calendar;
import java.util.Date;
import catalogo.InfoViajOrg;

/**
 *
 * @author Jorge
 */
public class ReservaViajOrg extends Reserva {
    private static double margen = 0.3;
    private int numPersonas;
    private final String nombre;
    private InfoViajOrg info;

    public ReservaViajOrg(int numPersonas, Date fechaInicio, double precio,
            String nombre, InfoViajOrg info) {
        super(fechaInicio, precio);
        this.numPersonas = numPersonas;
        this.nombre = nombre;
        this.info = info;
    }

    public int getNumPersonas() {
        return numPersonas;
    }

    public String getNombre() {
        return nombre;
    }
    
    public static double getMargen() {
        return ReservaViajOrg.margen;
    }

    public InfoViajOrg getInfo() {
        return info;
    }
    
    public double getPrecioTotal() {
        return this.getPrecio() * (1 + this.getMargen());
    }
    
    public void setNumPersonas(int numPersonas) {
        this.numPersonas = numPersonas;
    }
    
    public static void setMargen(double margen) {
        ReservaViajOrg.margen = margen;
    }

    public void setInfo(InfoViajOrg info) {
        this.info = info;
    }
    
}
