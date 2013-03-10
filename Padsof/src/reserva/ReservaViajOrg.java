/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reserva;

import cat.quickdb.annotation.Column;
import cat.quickdb.annotation.Properties;
import catalogo.InfoViajOrg;

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
        
}
