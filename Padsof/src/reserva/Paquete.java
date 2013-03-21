/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reserva;

import cat.quickdb.annotation.Column;
import cat.quickdb.db.AdminBase;
import catalogo.InfoHotel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author Jorge
 */
public class Paquete {
    private int id;
    private int idPaq;
    private int abierto;
    private String cliente;
    private int vendedor;
    @Column(collectionClass="Reserva")
    private ArrayList<Reserva> reservas;

    
    public Paquete() {}
    
    public Paquete(int idPaq, int abierto, String cliente, int vendedor) {
        this.idPaq = idPaq;
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

    public int getVendedor() {
        return vendedor;
    }

    public void setVendedor(int vendedor) {
        this.vendedor = vendedor;
    }
   

    public int getIdPaq() {
        return idPaq;
    }

    public void setIdPaq(int idPaq) {
        this.idPaq = idPaq;
    }

    public void addReserva(Reserva reserva) {
        if(reserva == null) {return;}
        this.reservas.add(reserva);
    }

    public String compPaquete() throws ParseException {
        Date hoy = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date cercana = sdf.parse(this.reservas.get(0).getFechaInicio());
        
        for(Reserva r : this.reservas) {
            // Si hay alguna reserva que empiece hoy, o ya haya pasado; cerramos.
            if(sdf.parse(r.getFechaInicio()).equals(hoy) || sdf.parse(r.getFechaInicio()).before(hoy)) {
                this.setAbierto(0);
                return r.getFechaInicio();
            }
            
            if(sdf.parse(r.getFechaInicio()).before(cercana)) {
                cercana = sdf.parse(r.getFechaInicio());
            }
        }
        
        return sdf.format(cercana);
    }
    
    
    /**
     * Obtiene las reservas de hotel asociadas al Paquete, a trav&eacute;s de la 
     * nombreBD.
     * @param admin
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    private ArrayList<ReservaHotel> obtainReservasHotel(AdminBase admin) throws ClassNotFoundException, SQLException, ParseException {
        String query = "SELECT rh.id, r.id FROM ReservaHotel AS rh"
                + " JOIN Reserva AS r ON r.id = rh.parent_id "
                + " JOIN PaqueteReserva AS pr ON r.id = pr.related"
                + " WHERE pr.base = " + this.getId();
        String queryResH = "SELECT * FROM ReservaHotel WHERE id = ";
        Reserva r = new Reserva();
        ArrayList<ReservaHotel> resHoteles = new ArrayList<ReservaHotel>();
        
        
        Object[] o = admin.obtainJoin(query, 2);
        if(o == null) {
            return resHoteles;
        }
        
        for(Object cadena : o) {
            String[] fila = (String[]) cadena;
            
            // Obtenemos la reserva
            admin.obtain(r, "id = " + fila[1]);
            
            // Obtenemos la informaci&oacute;n asociada.
            InfoHotel info = new InfoHotel();
            Object[] o2 = admin.obtainJoin(queryResH + fila[0], 6);
            String[] fila2 = (String[])o2[0];
            
            // Obtenemos la fecha guardada
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fInicio = sdf.parse(r.getFechaInicio());
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(fInicio);
            
            // Creamos la instancia de la reserva
            admin.obtain(info, "id = " + fila2[1]);
            ReservaHotel resH = new ReservaHotel(cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR), 
                    fila2[2], fila2[3], info);
            resH.setId(Integer.parseInt(fila2[0]));
            ReservaHotel.setMargen(Double.parseDouble(fila2[4]), "admin");
            
            resH.setEstado(r.getEstado());
            resHoteles.add(resH);
        }
        
        return resHoteles;
    }
    
    
    /**
     * Obtiene las reservas de vuelos asociadas al paquete, a trav&eacute;s de la 
     * nombreBD.
     * @param admin
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws ParseException 
     */
    private ArrayList<ReservaVuelo> obtainReservasVuelos(AdminBase admin) throws ClassNotFoundException, SQLException, ParseException {
        String query = "SELECT rV.id, r.id FROM ReservaVuelo AS rV "
                + "JOIN PaqueteReserva AS pr ON rV.parent_id = pr.related "
                + "JOIN Reserva AS r ON r.id = rV.parent_id "
                + "WHERE pr.base = " + this.getId();

        String queryResV = "SELECT * FROM ReservaVuelo WHERE id = ";
        Reserva r = new Reserva();
        ArrayList<ReservaVuelo> resVuelos = new ArrayList<ReservaVuelo>();
        
        
        // Obtenemos los ID's de los vuelos y sus reservas padre.
        Object[] o = admin.obtainJoin(query, 2);
        if(o == null) {
            return resVuelos;
        }
        
        for(Object cadena : o) {
            String[] fila = (String[]) cadena;
            
            // Obtenemos la reserva
            admin.obtain(r, "id = " + fila[1]);
            
            // Obtenemos a mano los campos de la reserva de hotel
            InfoHotel info = new InfoHotel();
            Object[] o2 = admin.obtainJoin(queryResV + fila[0], 6);
            String[] fila2 = (String[])o2[0];
            
            
            // Obtenemos la fecha guardada
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fInicio = sdf.parse(r.getFechaInicio());
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(fInicio);
            
            // Creamos la reserva del vuelo
            ReservaVuelo resV = new ReservaVuelo(cal.get(Calendar.DAY_OF_MONTH), 
                    cal.get(Calendar.MONTH)+1, cal.get(Calendar.YEAR), fila2[2], 
                    fila2[3], fila2[4], Double.parseDouble(fila2[1]));
            
            resV.setId(Integer.parseInt(fila2[0]));
            
            resVuelos.add(resV);
        }
        
        return resVuelos;
    }
    
    
    /**
     * Este m&eacute;todo ha de llamarse sobre un paquete nada 
     * m&aacute;s hayamos volcado la informaci&oacute;n desde la 
     * estructura en nombreBD, para cargar correctamente todas las reservas 
     * asociadas.
     * @param admin 
     */
    public void cargarDatosPaqueteSQL(AdminBase admin) throws ClassNotFoundException, SQLException, ParseException {
        List<Reserva> reservas = new ArrayList<Reserva>();
        Object[] o;
        String query;
        Reserva r = new Reserva();

        
        // Reservas del IMSERSO
        ReservaViajeIMSERSO resIMS = new ReservaViajeIMSERSO();
        query = "SELECT rIMS.id, r.estado FROM ReservaViajeIMSERSO AS rIMS"
                + " JOIN Reserva AS r ON r.id = rIMS.parent_id "
                + " JOIN PaqueteReserva AS pr ON r.id = pr.related"
                + " WHERE pr.base = " + this.getId();
        o = admin.obtainJoin(query, 2);
        if(o != null) {
            for(Object ids : o) {
                String[] num = (String[]) ids;
                admin.obtain(resIMS, "id = " + num[0]);

                resIMS.setEstado(num[1], admin);
                reservas.add(resIMS);
            }
        }
        
        // Reservas de hotel
        for(ReservaHotel rH : obtainReservasHotel(admin)) {
            reservas.add(rH);
        }

        // Reservas de viajes organizados
        ReservaViajOrg resVO = new ReservaViajOrg();
        query = "SELECT rVO.id, r.estado FROM ReservaViajOrg AS rVO"
                + " JOIN Reserva AS r ON r.id = rVO.parent_id "
                + " JOIN PaqueteReserva AS pr ON r.id = pr.related"
                + " WHERE pr.base = " + this.getId();
        o = admin.obtainJoin(query, 2);
        if(o != null) {
            for(Object ids : o) {
                String[] num = (String[]) ids;
                admin.obtain(resVO, "id = " + num[0]);

                resVO.setEstado(num[1], admin);
                reservas.add(resVO);
            }
        }
        
        // Reservas de vuelos
        for(ReservaVuelo nav : obtainReservasVuelos(admin)) {
            reservas.add(nav);
        }
        
        
        this.setReservas((ArrayList<Reserva>)reservas);
    }
    
    /**
     * <u>Nota</u>:<br/>
     * No llamar a este m&eacute;todo hasta que no se haya cargado la info. SQL 
     * del paquete (invocar m&eacute;todo 'cargarDatosPaqueteSQL()').
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
     * <u>Nota</u>:<br/>
     * No llamar a este m&eacute;todo hasta que no se haya cargado la info. SQL 
     * del paquete (invocar m&eacute;todo 'cargarDatosPaqueteSQL()').
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
     * <u>Nota</u>:<br/>
     * No llamar a este m&eacute;todo hasta que no se haya cargado la info. SQL 
     * del paquete (invocar m&eacute;todo 'cargarDatosPaqueteSQL()').
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
    
    
    /**
     * <u>Nota</u>:<br/>
     * No llamar a este m&eacute;todo hasta que no se haya cargado la info. SQL 
     * del paquete (invocar m&eacute;todo 'cargarDatosPaqueteSQL()').
     * @return las reservas de vuelos
     */
    public ArrayList<ReservaVuelo> getReservasVuelos() {
        ArrayList<ReservaVuelo> reservas = new ArrayList<ReservaVuelo>();
        
        for(Reserva r : this.getReservas()) {
            if(r.getTipoReserva().equals("reservaVuelo")) {
                reservas.add((ReservaVuelo)r);
            }
        }
        
        return reservas;
    }
    
    /**
     * 
     * @return la cantidad pagada del paquete
     */
    public double calcularPagado() {
        double pagado = 0.0;
        
        for(Reserva r : this.reservas) {
            pagado += r.calcularPagado();
        }
        
        return pagado;
    }
    
    /**
     * Guarda el paquete en la BD.
     * <br/><u>Nota:</u><br/>
     * Tras llamar al m&eacute;todo, reasignar la conexi&oacute;n al retorno.
     * @param admin
     * @return AdminBase - la conexi&oacute;n a la BD.
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public AdminBase guardar(AdminBase admin) throws SQLException, ClassNotFoundException {
        admin.save(this);
        String urlBD = admin.getConex().getConnection().getMetaData().getURL();
        String nombreBD = "";
        admin.close();
        
        // Conseguimos el nombre de la nombreBD
        StringTokenizer tokens = new StringTokenizer(urlBD, ":");
        while(tokens.hasMoreTokens()) {
            nombreBD = tokens.nextToken(":");
        }
        tokens = new StringTokenizer(nombreBD, ".");
        nombreBD = tokens.nextToken(".");
        
        
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(urlBD);
        Statement stmt =  conn.createStatement();
        stmt.executeUpdate("UPDATE PaqueteReserva SET related=id WHERE id > 0");
        
        stmt.close();
        conn.close();
        
        return AdminBase.initialize(AdminBase.DATABASE.SQLite, nombreBD);
    }
    
    /**
     * Modifica el paquete en la BD.
     * <br/><u>Nota:</u><br/>
     * Tras llamar al m&eacute;todo, reasignar la conexi&oacute;n al retorno.
     * @param admin
     * @return AdminBase - la conexi&oacute;n a la BD.
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public AdminBase modificar(AdminBase admin) throws SQLException, ClassNotFoundException {
        admin.modify(this);
        String urlBD = admin.getConex().getConnection().getMetaData().getURL();
        String nombreBD = "";
        admin.close();
        
        // Conseguimos el nombre de la nombreBD
        StringTokenizer tokens = new StringTokenizer(urlBD, ":");
        while(tokens.hasMoreTokens()) {
            nombreBD = tokens.nextToken(":");
        }
        tokens = new StringTokenizer(nombreBD, ".");
        nombreBD = tokens.nextToken(".");
        
        
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(urlBD);
        Statement stmt =  conn.createStatement();
        stmt.executeUpdate("UPDATE PaqueteReserva SET related=id WHERE id > 0");
        stmt.close();
        conn.close();
        
        return AdminBase.initialize(AdminBase.DATABASE.SQLite, nombreBD);
    }

    /**
     * Imprime las reservas.
     */
    public void mostrarReservas(){
        int i=1;
        System.out.println("Reservas del paquete:");
        for(Reserva r:this.getReservas()){
            System.out.println(i+".-"+r.toString());
        }
    }
}
