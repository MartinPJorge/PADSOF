/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reserva;

import cat.quickdb.annotation.Column;
import cat.quickdb.annotation.Properties;
import catalogo.InfoViajeIMSERSO;

/**
 *
 * @author Jorge
 */
public class ReservaViajeIMSERSO extends Reserva {
    private int id;
    private static double margen = 0.0;
    private int numPersonas;
    @Column(type=Properties.TYPES.FOREIGNKEY)
    private InfoViajeIMSERSO infoViajeIMSERSO;
    
    public ReservaViajeIMSERSO() {this.infoViajeIMSERSO = null;}
    
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

    public static void setMargen(double margen) {
        ReservaViajeIMSERSO.margen = margen;
    }

    public void setNumPersonas(int numPersonas) {
        this.numPersonas = numPersonas;
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
