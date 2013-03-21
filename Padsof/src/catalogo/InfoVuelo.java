/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo;

import java.util.Date;

/**
 *
 * @author e265923
 */
public class InfoVuelo {
    final private String origen;
    final private String destino;
    final private Date salida;
    final private Date llegada;
    final private int asientos;
    final private double precio;

    public InfoVuelo(String origen, String destino, Date salida, Date llegada, int asientos, double precio) {
        this.origen = origen;
        this.destino = destino;
        this.salida = salida;
        this.llegada = llegada;
        this.asientos = asientos;
        this.precio = precio;
    }
    
    public int getAsientos() {
        return asientos;
    }

    public String getDestino() {
        return destino;
    }

    public Date getLlegada() {
        return llegada;
    }

    public String getOrigen() {
        return origen;
    }

    public double getPrecio() {
        return precio;
    }

    public Date getSalida() {
        return salida;
    }
    
    @Override
    public String toString() {
        return "InfoVuelo{" + "origen=" + origen + ", destino=" + destino +
                ", salida=" + salida + ", llegada=" + llegada + ", asientos=" +
                asientos + ", precio=" + precio + '}';
    }

}
