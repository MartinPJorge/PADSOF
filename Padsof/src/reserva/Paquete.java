/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reserva;

import cat.quickdb.annotation.Column;
import cat.quickdb.db.AdminBase;
import catalogo.InfoHotel;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    
    public Paquete() {}
    
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
    
    private ArrayList<ReservaHotel> obtainReservasHotel(AdminBase admin) throws ClassNotFoundException, SQLException {
        String query = "SELECT rh.id, r.id FROM ReservaHotel AS rh"
                + " JOIN Reserva AS r ON r.id = rh.parent_id "
                + " JOIN PaqueteReserva AS pr ON r.id = pr.related"
                + " WHERE pr.base = " + this.getId();
        String queryResH = "SELECT * FROM ReservaHotel WHERE id = ";
        Reserva r = new Reserva();
        ArrayList<ReservaHotel> resHoteles = new ArrayList<ReservaHotel>();
        
        
        Object[] o = admin.obtainJoin(query, 2);
        for(Object cadena : o) {
            String[] fila = (String[]) cadena;
            
            // Obtenemos la reserva
            admin.obtain(r, "id = " + fila[1]);
            
            // Obtenemos a mano los campos de la reserva de hotel
            InfoHotel info = new InfoHotel();
            Object[] o2 = admin.obtainJoin(queryResH + fila[0], 6);
            String[] fila2 = (String[])o2[0];
            
            ReservaHotel resH = new ReservaHotel();
            resH.setId(Integer.parseInt(fila2[0]));
            admin.obtain(info, "id = " + fila2[1]);
            resH.setInfoHotel(info);
            resH.setTipoHabitacion(fila2[2]);
            resH.setSuplemento(fila2[3]);
            ReservaHotel.setMargen(Double.parseDouble(fila2[4]), "admin");  
            
            resH.setParent(r);
            
            resHoteles.add(resH);
        }
        
        return resHoteles;
    }
    
    /**
     * Este m&eacute;todo ha de llamarse sobre un paquete nada 
     * m&aacute;s hayamos volcado la informaci&oacute;n desde la 
     * estructura en BD, para cargar correctamente todas las reservas 
     * asociadas.
     * @param admin 
     */
    public void cargarDatosPaqueteSQL(AdminBase admin) throws ClassNotFoundException, SQLException {
        List<Reserva> reservas = new ArrayList<Reserva>();
        Object[] o;
        String query;
        Reserva r = new Reserva();

        
        // Reservas del IMSERSO
        ReservaViajeIMSERSO resIMS = new ReservaViajeIMSERSO();
        query = "SELECT rIMS.id FROM ReservaViajeIMSERSO AS rIMS"
                + " JOIN Reserva AS r ON r.id = rIMS.parent_id "
                + " JOIN PaqueteReserva AS pr ON r.id = pr.related"
                + " WHERE pr.base = " + this.getId();
        o = admin.obtainJoin(query, 1);
        for(Object ids : o) {
            String[] num = (String[]) ids;
            admin.obtain(resIMS, "id = " + num[0]);
            
            reservas.add(resIMS);
        }
        
        // Reservas de hotel
        for(ReservaHotel rH : obtainReservasHotel(admin)) {
            reservas.add(rH);
        }

        // Reservas de viajes organizados
        ReservaViajOrg resVO = new ReservaViajOrg();
        query = "SELECT rVO.id FROM ReservaViajOrg AS rVO"
                + " JOIN Reserva AS r ON r.id = rVO.parent_id "
                + " JOIN PaqueteReserva AS pr ON r.id = pr.related"
                + " WHERE pr.base = " + this.getId();
        o = admin.obtainJoin(query, 1);
        for(Object ids : o) {
            String[] num = (String[]) ids;
            admin.obtain(resVO, "id = " + num[0]);
            
            reservas.add(resVO);
        }
        
        this.setReservas((ArrayList<Reserva>)reservas);
    }
    
    /**
     * 
     * @return reservas de los viajes organizados
     */
    public ArrayList<ReservaViajOrg> getReservasVO() {
        ArrayList<ReservaViajOrg> reservas = new ArrayList<ReservaViajOrg>();
        
        for(Reserva r : this.getReservas()) {
            if(r.getTipoReserva().equals("reservaVO")) {
                reservas.add((ReservaViajOrg)r);
            }
        }
        
        return reservas;
    }
    
    
    /**
     * 
     * @return reservas de los hoteles
     */
    public ArrayList<ReservaHotel> getReservasHotel() {
        ArrayList<ReservaHotel> reservas = new ArrayList<ReservaHotel>();
        
        for(Reserva r : this.getReservas()) {
            if(r.getTipoReserva().equals("reservaHotel")) {
                reservas.add((ReservaHotel)r);
            }
        }
        
        return reservas;
    }
    
    
    /**
     * 
     * @return reservas de los viajes del IMSERSO
     */
    public ArrayList<ReservaViajeIMSERSO> getReservasIMSERSO() {
        ArrayList<ReservaViajeIMSERSO> reservas = new ArrayList<ReservaViajeIMSERSO>();
        
        for(Reserva r : this.getReservas()) {
            if(r.getTipoReserva().equals("reservaIMSERSO")) {
                reservas.add((ReservaViajeIMSERSO)r);
            }
        }
        
        return reservas;
    }
}
