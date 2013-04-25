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
 * Clase Reserva
 *
 * @author Jorge Martin, Ivan Marquez
 * @version 1.0
 */
public class Reserva {

    private int id;
    private String tipoReserva;  // "reservaHotel" - "reservaVO" - "reservaIMSERSO"
    // - "reservaVuelo"
    private String fechaInicio;
    protected double precio;
    private String estado;  // "reservado" - "confirmado" - "cancelado" 
    //                  _ "canceladoTrasConfirmar" 
    //  "cancelado" <- |
    //                  - "canceladoTrasReservar"    

    public Reserva() {
    }

    public Reserva(int dia, int mes, int year, double precio,
            String tipoReserva) {
        GregorianCalendar cal = new GregorianCalendar(year, mes, dia);
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
        /*if (this.estado != null && estado.equals("cancelado")) {
            if (this.estado.equals("reservado")) {
                this.estado = "canceladoTrasReservar";
            } else if (this.estado.equals("confirmado")) {
                this.estado = "canceladoTrasConfirmar";
            }
        } else {
            this.estado = estado;
        }*/
        this.estado = estado;
    }

    @Override
    public String toString() {
        return this.tipoReserva + this.fechaInicio + this.estado;
    }

    /**
     * Cambia el estado de la reserva del paquete dentro de la BD.<br/> Los
     * posibles estados que podemos pasar son: <ul> <li>"cancelado"</li>
     * <li>"confirmado"</li> <li>"reservado"</li> </ul> <br/><u>Nota</u>:<br/>
     * Para llamar a este m&eacute;todo, previamente se debe de cargar en la
     * instancia de reserva sobre la que se llama a este m&eacute;todo, una
     * reserva de la BD para que esta tenga id.
     *
     * @param estado
     * @param admin
     */
    public void setEstado(String estado, AdminBase admin) {
        if (this.tipoReserva.equals("reservaHotel")) {
            this.setEstadoHotel(estado, admin);
        } else if (this.tipoReserva.equals("reservaVO")) {
            this.setEstadoViajOrg(estado, admin);
        } else if (this.tipoReserva.equals("reservaIMSERSO")) {
            this.setEstadoViajeIMSERSO(estado, admin);
        } else {
            this.setEstadoVuelo(estado, admin);
        }
    }

    /**
     * Cambia el estado de la reserva de un vuelo, y por tanto se encarga de
     * actualizar los datos almacenados en la API de vuelos.
     *
     * @param estado
     * @param admin
     */
    private void setEstadoVuelo(String estado, AdminBase admin) {
        // Cambiamos la parte relativa a la BD.
        Reserva r = new Reserva();

        Object[] o = admin.obtainJoin("SELECT rh.parent_id FROM ReservaVuelo AS rh"
                + " WHERE rh.id = " + this.getId(), 1);
        admin.obtain(r, "id = " + ((String[]) o[0])[0]);

        r.setEstado(estado);
        this.setEstado(estado);
        admin.modify(r);


        // Conectamos con la API.
        ReservaVuelo rVuelo = (ReservaVuelo) this;

        if (estado.equals("confirmado")) {
            Vuelos.confirmar(rVuelo.getLocalizador());
        } else {
            Vuelos.cancelar(rVuelo.getLocalizador());
        }
    }

    /**
     * Se encarga de cambiar el estado de la reserva de un viaje del IMSERSO.
     *
     * @param estado
     * @param admin
     */
    private void setEstadoViajeIMSERSO(String estado, AdminBase admin) {
        Reserva r = new Reserva();

        Object[] o = admin.obtainJoin("SELECT rh.parent_id FROM ReservaViajeIMSERSO AS rh"
                + " WHERE rh.id = " + this.getId(), 1);
        admin.obtain(r, "id = " + ((String[]) o[0])[0]);


        r.setEstado(estado);
        this.setEstado(estado);
        admin.modify(r);
    }

    /**
     * Se encarga de cambiar el estado de la reserva de un viaje organizado.
     *
     * @param estado
     * @param admin
     */
    private void setEstadoViajOrg(String estado, AdminBase admin) {
        Reserva r = new Reserva();

        Object[] o = admin.obtainJoin("SELECT rh.parent_id FROM ReservaViajOrg AS rh"
                + " WHERE rh.id = " + this.getId(), 1);
        admin.obtain(r, "id = " + ((String[]) o[0])[0]);


        r.setEstado(estado);
        this.setEstado(estado);
        admin.modify(r);
    }

    /**
     * Cambia el estado de la reserva de un hotel.
     *
     * @param estado
     * @param admin
     */
    private void setEstadoHotel(String estado, AdminBase admin) {
        Reserva r = new Reserva();

        Object[] o = admin.obtainJoin("SELECT rh.parent_id FROM ReservaHotel AS rh"
                + " WHERE rh.id = " + this.getId(), 1);
        admin.obtain(r, "id = " + ((String[]) o[0])[0]);


        r.setEstado(estado);
        this.setEstado(estado);
        admin.modify(r);
    }

    /**
     * Calcula la cantidad pagada de la reserva.
     */
    public double calcularPagado() {
        if (this.tipoReserva.equals("reservaHotel")) {
            if (this.estado.equals("confirmado") || this.estado.equals("canceladoTrasConfirmar")) {
                return this.precio;
            } else {
                return this.precio * 0.10;
            }
        } else if (this.tipoReserva.equals("reservaVO")) {
            if (this.estado.equals("confirmado") || this.estado.equals("canceladoTrasConfirmar")) {
                return this.precio;
            } else {
                return this.precio * 0.10;
            }
        } else if (this.tipoReserva.equals("reservaIMSERSO")) {
            if (this.estado.equals("confirmado") || this.estado.equals("canceladoTrasConfirmar")) {
                return this.precio;
            } else {
                return this.precio * 0.10;
            }
        } else {
            if (this.estado.equals("confirmado") || this.estado.equals("canceladoTrasConfirmar")) {
                return this.precio;
            } else {
                return 0.0;
            }
        }
    }
}
