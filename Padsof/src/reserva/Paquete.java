/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reserva;

import cat.quickdb.annotation.Column;
import cat.quickdb.annotation.Properties;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private int id;
    private int abierto;
    private String cliente;
    private String vendedor;
    @Column(collectionClass="Reserva")
    private ArrayList<Reserva> reservas;

    
    public Paquete() {
        this.cliente = null;
        /*this.vendedor = null;*/
    }
    
    public Paquete(int abierto, String cliente, String vendedor) {
        this.abierto = abierto;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.reservas = new ArrayList<Reserva>();
    }

    public int getId() {
        return id;
    }

    public int getAbierto() {
        return abierto;
    }

    public ArrayList<Reserva> getReservas() {
        return this.reservas;
    }

    public String getCliente() {
        return cliente;
    }

    public String getVendedor() {
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

    public void setId(int id) {
        this.id = id;
    }
    
    public void setAbierto(int abierto) {
        this.abierto = abierto;
    }

    public void setReservas(ArrayList<Reserva> reservas) {
        this.reservas = reservas;
    }
    
    
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public void addReserva(Reserva reserva) {
        this.reservas.add(reserva);
    }
    
    public String compPaquete() throws ParseException {
        Date hoy = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date cercana = sdf.parse(this.reservas.get(0).getFechaInicio());
        boolean algunaEmpezada = false;
        
        for(Reserva r : this.reservas) {
            
            if(sdf.parse(r.getFechaInicio()).equals(hoy)) {
                this.setAbierto(0);
                return r.getFechaInicio();
            }
            
            if(sdf.parse(r.getFechaInicio()).before(cercana)) {
                cercana = sdf.parse(r.getFechaInicio());
            }
        }
        
        return sdf.format(cercana);
    }
}
