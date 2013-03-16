/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reserva;

import cat.quickdb.db.AdminBase;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 *
 * @author e265832
 */

public class Reserva {
    private int id;
    private String tipoReserva;  // "reservaHotel" - "reservaVO" - "reservaIMSERSO"
    private String fechaInicio;
    protected double precio;
    private String estado;  // "reservado" - "confirmado" - "cancelado"
    
    
    public Reserva() {}
    
    public Reserva(int dia, int mes, int year, double precio,
            String tipoReserva) {
        GregorianCalendar cal = new GregorianCalendar(year, mes+1, dia);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.fechaInicio = sdf.format(cal.getTime());
        this.precio = precio;
        this.estado = "reservado";
        this.tipoReserva = tipoReserva;
    }

    public int getId() {
        return id;
    }

    public String getFechaInicio() {
        return this.fechaInicio;
    }

    public String getTipoReserva() {
        return this.tipoReserva;
    }

    public double getPrecio() {
        return precio;
    }

    public String getEstado() {
        return estado;
    }
    
    public void setTipoReserva(String tipoReserva) {
        this.tipoReserva = tipoReserva;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public void setEstado(String estado, AdminBase admin) {
        Reserva r = new Reserva();
        Object[] o = admin.obtainJoin("SELECT rh.parent_id FROM ReservaHotel AS rh"
                + " WHERE rh.id = " + this.getId(), 1);
        admin.obtain(r, "id = " + ((String[])o[0])[0]);
        
        r.setEstado("confirmado");
        admin.modify(r);
    }
    
    /**
     * Calcula el precio de la reserva en funci&oacute;n de los servicios 
     * contratados.
     */
    public void calcularPrecio() {}
}
