/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reserva;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import padsof.Cliente;
import padsof.Vendedor;

/**
 *
 * @author Jorge
 */
public class Paquete {
    final private int id;
    private boolean abierto;
    final private  Cliente cliente;
    final private  Vendedor vendedor;
    private List<Reserva> reservas;
    
    public Paquete(int id, boolean abierto, Cliente cliente, Vendedor vendedor) {
        this.id = id;
        this.abierto = abierto;
        this.cliente=cliente;
        this.vendedor=vendedor;
        this.reservas= new ArrayList<Reserva>();
    }

    public int getId() {
        return id;
    }

    public boolean isAbierto() {
        return abierto;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    
    public double getPrecioTotal() {
        double precioTotal = 0.0;
        
        for(Reserva r : this.reservas) {
            precioTotal += r.getPrecio();
        }
        
        return precioTotal;
    }
    
    public int numReservas() {
        return this.reservas.size();
    }

    public void setAbierto(boolean abierto) {
        this.abierto = abierto;
    }

    public void addReserva(Reserva reserva) {
        this.reservas.add(reserva);
    }
    
    public Date compPaquete() {
        Date hoy = new Date();
        Date cercana = this.reservas.get(0).getFechaInicio();
        boolean algunaEmpezada = false;
        
        for(int i = 0; i < this.reservas.size(); i++) {
            Reserva r = this.reservas.get(i);
            
            if(r.getFechaInicio().equals(hoy)) {
                this.setAbierto(false);
                return r.getFechaInicio();
            }
            
            if(r.getFechaInicio().before(cercana)) {
                cercana = r.getFechaInicio();
            }
        }
        
        return cercana;
    }
}
