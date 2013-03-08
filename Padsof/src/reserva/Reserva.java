/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reserva;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author e265832
 */

public abstract class Reserva {
    
    private Date fechaInicio;
    private double precio;
    private Estado estado;
    
    
    protected Reserva(Date fechaInicio, double precio) {
        this.fechaInicio = fechaInicio;
        this.precio = precio;
        this.estado = Estado.RESERVADO;
    }

    public Estado getEstado() {
        return estado;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }
    
    public double getPrecio() {
        return precio;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }


    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
