/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reserva;

import GUI.Recursos.DateValidator;
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
import javax.swing.JOptionPane;
import myexception.ClosedPackageExc;

/**
 * Clase Paquete
 *
 * @author Jorge Martin, Ivan Marquez
 * @version 1.0
 */
public class Paquete {

    private int id;
    private int idPaq;
    private int abierto;
    private String cliente;
    private int vendedor;
    @Column(collectionClass = "Reserva")
    private ArrayList<Reserva> reservas;

    public Paquete() {
    }

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

        for (Reserva r : this.reservas) {
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
    

    /**
     * Añade una reserva al paquete si este no esta cerrado.
     * Si esta cerrado, se lanza una Exception
     * @param reserva
     * @throws ClosedPackageExc 
     */
    public void addReserva(Reserva reserva) throws ClosedPackageExc {
        if (this.getAbierto() == 0) {
            throw (new ClosedPackageExc());
        }
        if (reserva == null) {
            return;
        }
        this.reservas.add(reserva);
    }

    /**
     * Comprueba si alguna de las Reservas del Paquete empieza hoy o ya ha
     * pasado: si es as&iacute; , cierra el Paquete.
     *
     * @return
     * @throws ParseException
     */
    public String compPaquete() throws ParseException {
        Date hoy = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date cercana = sdf.parse(this.reservas.get(0).getFechaInicio());

        for (Reserva r : this.reservas) {
            // Si hay alguna reserva que empiece hoy, o ya haya pasado; cerramos.
            if (sdf.parse(r.getFechaInicio()).equals(hoy) || sdf.parse(r.getFechaInicio()).before(hoy)) {
                this.setAbierto(0);
                return r.getFechaInicio();
            }

            if (sdf.parse(r.getFechaInicio()).before(cercana)) {
                cercana = sdf.parse(r.getFechaInicio());
            }
        }

        return sdf.format(cercana);
    }

    /**
     * Comprueba si las Reservas del Paquete: si TODAS est&aacute;n canceladas,
     * lo cierra.
     *
     * @throws ParseException
     */
    public void compReservasValidas() throws ParseException {
        boolean validas = false;
        for (Reserva r : this.reservas) {
            if (r.getEstado().equals("reservado") | r.getEstado().equals("confirmado")) {
                validas = false;
                break;
            }
        }
        if (validas == false) {
            this.setAbierto(0);
        }
    }

    /**
     * Obtiene las reservas de hotel asociadas al Paquete, a trav&eacute;s de la
     * nombreBD.
     *
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
        if (o == null) {
            return resHoteles;
        }

        for (Object cadena : o) {
            String[] fila = (String[]) cadena;

            // Obtenemos la reserva
            admin.obtain(r, "id = " + fila[1]);

            // Obtenemos la informaci&oacute;n asociada.
            InfoHotel info = new InfoHotel();
            Object[] o2 = admin.obtainJoin(queryResH + fila[0], 6);
            String[] fila2 = (String[]) o2[0];

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
     * Obtiene las reservas de vuelos asociadas al paquete, a trav&eacute;s de
     * la nombreBD.
     *
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
        if (o == null) {
            return resVuelos;
        }

        for (Object cadena : o) {
            String[] fila = (String[]) cadena;

            // Obtenemos la reserva
            admin.obtain(r, "id = " + fila[1]);

            // Obtenemos a mano los campos de la reserva de vuelo
            Object[] o2 = admin.obtainJoin(queryResV + fila[0], 6);
            String[] fila2 = (String[]) o2[0];


            // Obtenemos la fecha guardada
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fInicio = sdf.parse(r.getFechaInicio());
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(fInicio);

            // Obtenemos a mano el precio de la reserva
            Object[] o3 = admin.obtainJoin("SELECT precio FROM Reserva where id=" + Integer.parseInt(fila2[5]), 1);
            String[] fila3 = (String[]) o3[0];

            // Creamos la reserva del vuelo
            ReservaVuelo resV = new ReservaVuelo(cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR), fila2[2],
                    fila2[3], fila2[4], Double.parseDouble(fila3[0]));

            resV.setId(Integer.parseInt(fila2[0]));
            resV.setEstado(r.getEstado());
            resVuelos.add(resV);
        }

        return resVuelos;
    }

    /**
     * Este m&eacute;todo ha de llamarse sobre un paquete nada m&aacute;s
     * hayamos volcado la informaci&oacute;n desde la estructura en nombreBD,
     * para cargar correctamente todas las reservas asociadas.
     *
     * @param admin
     */
    public void cargarDatosPaqueteSQL(AdminBase admin) throws ClassNotFoundException, SQLException, ParseException {
        List<Reserva> reservas = new ArrayList<Reserva>();
        Object[] o = null;
        String query;
        Reserva r = new Reserva();


        // Reservas del IMSERSO
        if(this.contieneResViajeIMSERSO()) {
            
            query = "SELECT rIMS.id, r.estado FROM ReservaViajeIMSERSO AS rIMS"
                    + " JOIN Reserva AS r ON r.id = rIMS.parent_id "
                    + " JOIN PaqueteReserva AS pr ON r.id = pr.related"
                    + " WHERE pr.base = " + this.getId();
            o = admin.obtainJoin(query, 2);
            if (o != null) {
                for (Object ids : o) {
                    ReservaViajeIMSERSO resIMS = new ReservaViajeIMSERSO();
                    String[] num = (String[]) ids;
                    admin.obtain(resIMS, "id = " + num[0]);

                    resIMS.setEstado(num[1], admin);
                    reservas.add(resIMS);
                }
            }
        }

        // Reservas de hotel
        if(this.contieneResHotel()) {
            for (ReservaHotel rH : obtainReservasHotel(admin)) {
                reservas.add(rH);
            }
        }

        // Reservas de viajes organizados
        if(this.contieneResViajOrg()) {
            ReservaViajOrg resVO = new ReservaViajOrg();
            query = "SELECT rVO.id, r.estado FROM ReservaViajOrg AS rVO"
                    + " JOIN Reserva AS r ON r.id = rVO.parent_id "
                    + " JOIN PaqueteReserva AS pr ON r.id = pr.related"
                    + " WHERE pr.base = " + this.getId();
            o = admin.obtainJoin(query, 2);
            if (o != null) {
                for (Object ids : o) {
                    String[] num = (String[]) ids;
                    admin.obtain(resVO, "id = " + num[0]);

                    resVO.setEstado(num[1], admin);
                    reservas.add(resVO);
                }
            }
        }

        // Reservas de vuelos
        if(this.contieneResVuelo()) {
            for (ReservaVuelo nav : obtainReservasVuelos(admin)) {
                reservas.add(nav);
            }
        }


        this.setReservas((ArrayList<Reserva>) reservas);
    }

    /**
     * <u>Nota</u>:<br/> No llamar a este m&eacute;todo hasta que no se haya
     * cargado la info. SQL del paquete (invocar m&eacute;todo
     * 'cargarDatosPaqueteSQL()').
     *
     * @return reservas de los viajes organizados
     */
    public ArrayList<ReservaViajOrg> getReservasVO() {
        ArrayList<ReservaViajOrg> reservas = new ArrayList<ReservaViajOrg>();

        for (Reserva r : this.getReservas()) {
            if (r.getTipoReserva().equals("reservaVO")) {
                reservas.add((ReservaViajOrg) r);
            }
        }

        return reservas;
    }

    /**
     * <u>Nota</u>:<br/> No llamar a este m&eacute;todo hasta que no se haya
     * cargado la info. SQL del paquete (invocar m&eacute;todo
     * 'cargarDatosPaqueteSQL()').
     *
     * @return reservas de los hoteles
     */
    public ArrayList<ReservaHotel> getReservasHotel() {
        ArrayList<ReservaHotel> reservas = new ArrayList<ReservaHotel>();

        for (Reserva r : this.getReservas()) {
            if (r.getTipoReserva().equals("reservaHotel")) {
                reservas.add((ReservaHotel) r);
            }
        }

        return reservas;
    }

    /**
     * <u>Nota</u>:<br/> No llamar a este m&eacute;todo hasta que no se haya
     * cargado la info. SQL del paquete (invocar m&eacute;todo
     * 'cargarDatosPaqueteSQL()').
     *
     * @return reservas de los viajes del IMSERSO
     */
    public ArrayList<ReservaViajeIMSERSO> getReservasIMSERSO() {
        ArrayList<ReservaViajeIMSERSO> reservas = new ArrayList<ReservaViajeIMSERSO>();

        for (Reserva r : this.getReservas()) {
            if (r.getTipoReserva().equals("reservaIMSERSO")) {
                reservas.add((ReservaViajeIMSERSO) r);
            }
        }

        return reservas;
    }

    /**
     * <u>Nota</u>:<br/> No llamar a este m&eacute;todo hasta que no se haya
     * cargado la info. SQL del paquete (invocar m&eacute;todo
     * 'cargarDatosPaqueteSQL()').
     *
     * @return las reservas de vuelos
     */
    public ArrayList<ReservaVuelo> getReservasVuelos() {
        ArrayList<ReservaVuelo> reservas = new ArrayList<ReservaVuelo>();

        for (Reserva r : this.getReservas()) {
            if (r.getTipoReserva().equals("reservaVuelo")) {
                reservas.add((ReservaVuelo) r);
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

        for (Reserva r : this.reservas) {
            pagado += r.calcularPagado();
        }

        return pagado;
    }

    /**
     * Guarda el paquete en la BD. <br/><u>Nota:</u><br/> Tras llamar al
     * m&eacute;todo, reasignar la conexi&oacute;n al retorno.
     *
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
        while (tokens.hasMoreTokens()) {
            nombreBD = tokens.nextToken(":");
        }
        tokens = new StringTokenizer(nombreBD, ".");
        nombreBD = tokens.nextToken(".");


        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(urlBD);
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("UPDATE PaqueteReserva SET related=id WHERE id > 0");

        stmt.close();
        conn.close();

        return AdminBase.initialize(AdminBase.DATABASE.SQLite, nombreBD);
    }

    /**
     * Modifica el paquete en la BD. <br/><u>Nota:</u><br/> Tras llamar al
     * m&eacute;todo, reasignar la conexi&oacute;n al retorno.
     *
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
        while (tokens.hasMoreTokens()) {
            nombreBD = tokens.nextToken(":");
        }
        tokens = new StringTokenizer(nombreBD, ".");
        nombreBD = tokens.nextToken(".");


        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(urlBD);
        Statement stmt = conn.createStatement();
        //stmt.executeUpdate("UPDATE PaqueteReserva SET related=id+1 WHERE id >= 0");
        stmt.close();
        conn.close();

        return AdminBase.initialize(AdminBase.DATABASE.SQLite, nombreBD);
    }
    
    
    /**
     * Dada una conexi&oacute;n, devuelve el nombre de la BD.
     * @param admin
     * @return
     * @throws SQLException 
     */
    public static String getDBName(AdminBase admin) throws SQLException {
        String urlBD = admin.getConex().getConnection().getMetaData().getURL();
        String nombreBD = "";

        // Conseguimos el nombre de la nombreBD
        StringTokenizer tokens = new StringTokenizer(urlBD, ":");
        while (tokens.hasMoreTokens()) {
            nombreBD = tokens.nextToken(":");
        }
        tokens = new StringTokenizer(nombreBD, ".");
        nombreBD = tokens.nextToken(".");
        
        return nombreBD;
    }
    
    /**
     * Dada una conexi&oacute;n de quickDB, devuelve una de JDBC.
     * @param admin
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static Connection quickDBtoJDBC(AdminBase admin) throws SQLException, ClassNotFoundException {
        String urlBD = admin.getConex().getConnection().getMetaData().getURL();
        admin.close();

        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(urlBD);
        
        return conn;
    }
    
    /**
     * Actualiza en la BD los nuevos servicios introducidos en el paquete.
     * @param admin
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public AdminBase actualizarSQL(AdminBase admin) throws SQLException, ClassNotFoundException {        
        if(this.contieneResHotel()) {
            admin = actualizarHotelesSQL(admin);
        }
        if(this.contieneResVuelo()) {
            admin = actualizarVuelosSQL(admin);
        }
        if(this.contieneResViajOrg()) {
            admin = actualizarViajOrgSQL(admin);
        }
        if(this.contieneResViajeIMSERSO()) {
            admin = actualizarViajeImsersoSQL(admin);
        }
        
        return admin;
    }
    
    /**
     * Actualiza en la base de datos los nuevos hoteles introducidos en el paquete.
     * @param admin
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public AdminBase actualizarHotelesSQL(AdminBase admin) throws SQLException, ClassNotFoundException {
        String nombreBD = getDBName(admin);
        
        //Obtenemos el listado de los nuevos hoteles
        for(ReservaHotel r : this.getReservasHotel()) {
            if(r.getId() == 0) {
                admin.save(r);
            
                //Obtenemos el indice que ocupa la reserva
                Object[] filas = admin.obtainJoin("SELECT MAX(id) FROM ReservaHotel", 1);
                Object[] fila = (Object[])filas[0];
                int lastCol = Integer.parseInt((String)fila[0]);
                
                r = new ReservaHotel();
                

                filas = admin.obtainJoin("SELECT parent_id FROM ReservaHotel WHERE id = "+lastCol, 1);
                fila = (Object[])filas[0];
                int parentId = Integer.parseInt((String)fila[0]);
                
                filas = admin.obtainJoin("SELECT MAX(id) FROM PaqueteReserva", 1);
                fila = (Object[])filas[0];
                int lastColPaqRes = Integer.parseInt((String)fila[0]);
                
                
                
                Connection conn = quickDBtoJDBC(admin);
                Statement stmt = conn.createStatement();
                
                stmt.executeUpdate("INSERT INTO PaqueteReserva "
                + "VALUES (" + (++lastColPaqRes) + "," + this.id + "," + parentId + ")");
                
                conn.close();
                stmt.close();
                
                admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, nombreBD);
            }
        }
        return admin;
    }
    
    /**
     * Actualiza en la base de datos los nuevos hoteles introducidos en el paquete.
     * @param admin
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public AdminBase actualizarVuelosSQL(AdminBase admin) throws SQLException, ClassNotFoundException {
        String nombreBD = getDBName(admin);
        
        //Obtenemos el listado de los nuevos hoteles
        for(ReservaVuelo r : this.getReservasVuelos()) {
            if(r.getId() == 0) {
                admin.save(r);
            
                //Obtenemos el indice que ocupa la reserva
                Object[] filas = admin.obtainJoin("SELECT MAX(id) FROM ReservaVuelo", 1);
                Object[] fila = (Object[])filas[0];
                int lastCol = Integer.parseInt((String)fila[0]);
                

                filas = admin.obtainJoin("SELECT parent_id FROM ReservaVuelo WHERE id = "+lastCol, 1);
                fila = (Object[])filas[0];
                int parentId = Integer.parseInt((String)fila[0]);
                
                filas = admin.obtainJoin("SELECT MAX(id) FROM PaqueteReserva", 1);
                fila = (Object[])filas[0];
                int lastColPaqRes = Integer.parseInt((String)fila[0]);
                
                
                Connection conn = quickDBtoJDBC(admin);
                Statement stmt = conn.createStatement();
                
                stmt.executeUpdate("INSERT INTO PaqueteReserva "
                + "VALUES (" + (++lastColPaqRes) + "," + this.id + "," + parentId + ")");
                
                conn.close();
                stmt.close();
                
                admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, nombreBD);
            }
        }
        
        return admin;
    }
    
    /**
     * Actualiza en la base de datos los nuevos hoteles introducidos en el paquete.
     * @param admin
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public AdminBase actualizarViajOrgSQL(AdminBase admin) throws SQLException, ClassNotFoundException {
        String nombreBD = getDBName(admin);
        
        //Obtenemos el listado de los nuevos hoteles
        for(ReservaViajOrg r : this.getReservasVO()) {
            if(r.getId() == 0) {
                admin.save(r);
            
                //Obtenemos el indice que ocupa la reserva
                Object[] filas = admin.obtainJoin("SELECT MAX(id) FROM ReservaViajOrg", 1);
                Object[] fila = (Object[])filas[0];
                int lastCol = Integer.parseInt((String)fila[0]);
                

                filas = admin.obtainJoin("SELECT parent_id FROM ReservaViajOrg WHERE id = "+lastCol, 1);
                fila = (Object[])filas[0];
                int parentId = Integer.parseInt((String)fila[0]);
                
                filas = admin.obtainJoin("SELECT MAX(id) FROM PaqueteReserva", 1);
                fila = (Object[])filas[0];
                int lastColPaqRes = Integer.parseInt((String)fila[0]);
                
                
                
                Connection conn = quickDBtoJDBC(admin);
                Statement stmt = conn.createStatement();
                
                stmt.executeUpdate("INSERT INTO PaqueteReserva "
                + "VALUES (" + (++lastColPaqRes) + "," + this.id + "," + parentId + ")");
                
                conn.close();
                stmt.close();
                
                admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, nombreBD);
            }
        }
        return admin;
    }

    
    /**
     * Actualiza en la base de datos los nuevos hoteles introducidos en el paquete.
     * @param admin
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public AdminBase actualizarViajeImsersoSQL(AdminBase admin) throws SQLException, ClassNotFoundException {
        String nombreBD = getDBName(admin);
        
        //Obtenemos el listado de los nuevos hoteles
        for(ReservaViajeIMSERSO r : this.getReservasIMSERSO()) {
            if(r.getId() == 0) {
                admin.save(r);
            
                //Obtenemos el indice que ocupa la reserva
                Object[] filas = admin.obtainJoin("SELECT MAX(id) FROM ReservaViajeIMSERSO", 1);
                Object[] fila = (Object[])filas[0];
                int lastCol = Integer.parseInt((String)fila[0]);
                
                try {
                    admin.obtain(r, "id = "+lastCol);
                }
                catch(NullPointerException ex) {
                    //Hacer algo
                }

                filas = admin.obtainJoin("SELECT parent_id FROM ReservaViajeIMSERSO WHERE id = "+lastCol, 1);
                fila = (Object[])filas[0];
                int parentId = Integer.parseInt((String)fila[0]);
                
                filas = admin.obtainJoin("SELECT MAX(id) FROM PaqueteReserva", 1);
                fila = (Object[])filas[0];
                int lastColPaqRes = Integer.parseInt((String)fila[0]);
                
                
                
                Connection conn = quickDBtoJDBC(admin);
                Statement stmt = conn.createStatement();
                
                stmt.executeUpdate("INSERT INTO PaqueteReserva "
                + "VALUES (" + (++lastColPaqRes) + "," + this.id + "," + parentId + ")");
                
                conn.close();
                stmt.close();
                
                admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, nombreBD);
            }
        }
        

        
        return admin;
    }
    
    
    /**
     * Imprime las reservas.
     */
    public void mostrarReservas() {
        int i = 1;
        System.out.println("Reservas del paquete:");
        for (Reserva r : this.getReservas()) {
            System.out.println(i + ".-" + r.toString());
        }
    }
    
    /**
     * Determina si el paquete contiene alguna reserva de hotel.
     * @return "reservaVuelo"
     */
    public boolean contieneResHotel() {        
        for(Reserva r : this.reservas) {
            if(r.getTipoReserva().equals("reservaHotel")) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Determina si el paquete contiene alguna reserva de viaje organizado.
     * @return "reservaVuelo"
     */
    public boolean contieneResViajOrg() {        
        for(Reserva r : this.reservas) {
            if(r.getTipoReserva().equals("reservaVO")) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Determina si el paquete contiene alguna reserva de viaje del IMSERSO.
     * @return "reservaVuelo"
     */
    public boolean contieneResViajeIMSERSO() {        
        for(Reserva r : this.reservas) {
            if(r.getTipoReserva().equals("reservaIMSERSO")) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Determina si el paquete contiene alguna reserva de vuelos.
     * @return "reservaVuelo"
     */
    public boolean contieneResVuelo() {        
        for(Reserva r : this.reservas) {
            if(r.getTipoReserva().equals("reservaVuelo")) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Se encarga de actualizar el estado de un paquete y de sus reservas tanto 
     * en la informaci&acute;n de la instancia como en la BD.<br/>
     * <u>Nota</u>:<br/>
     * Es necesario reasignar la conexi&oacute;n pasada al retorno.
     * @param admin
     * @param nuevoEstado
     * @return AdminBase - la conexi&oacute;n a la BD
     */
    public AdminBase actualizarEstado(AdminBase admin, String nuevoEstado) {
        //Abrir
        if(nuevoEstado.equals("Abierto")) {
            this.abierto = 1;
            return admin;
        }
        
        //Cerrar
        else {
            this.abierto = 0;
            Connection c = null;
            Statement stmt = null;
            String dbName = null;
            
            //Abrimos una conexion con JDBC
            try {
                dbName = Paquete.getDBName(admin);
                c = Paquete.quickDBtoJDBC(admin);
                stmt = c.createStatement();
                stmt.executeUpdate("UPDATE Paquete SET abierto = 0 WHERE id = " + this.id);
            } catch (    SQLException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Error en la base de datos.");
            }
            
            
            //Actualizamos el estado de las reservas en la BD
            for(Reserva r : this.reservas) {
                if(r.getEstado().equals("cancelado") == false) {
                    r.setEstado(nuevoEstado);
                    
                    try {
                        String q = "UPDATE Reserva SET estado = 'cancelado' WHERE id = " + r.getId();
                        stmt.executeUpdate(q);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    }
                }
            }
            
            return AdminBase.initialize(AdminBase.DATABASE.SQLite, dbName);
        }
    }
    
    /**
     * Actualiza los estados de las reservas del paquete en la BD.
     * @param admin 
     */
    public void actualizarEstadoReservas(AdminBase admin) {
        for(Reserva r : this.reservas) {
            r.setEstado(r.getEstado(), admin);
        }
    }
    
    /**
     * Obtiene la fecha de inicio m&aacute;s cercana de un paquete.
     * @return 
     */
    public Reserva obtenerPrimeraReserva() {
        Reserva resCerca = this.getReservas().get(0);
        
        for(Reserva r : this.getReservas()) {
            String fecha = r.getFechaInicio();
            
            if(DateValidator.compareDates(r.getFechaInicio(), resCerca.getFechaInicio()) < 0) {
                resCerca = r;
            }
        }
        
        return resCerca;
    }
    
    /**
     * Carga los estados de las reservas del paquete.
     * @param admin 
     */
    public void cargarEstadoReservas(AdminBase admin) {
        for(Reserva r : this.getReservas()) {
            Object[] filas = admin.obtainJoin("SELECT estado FROM Reserva WHERE id = "+r.getId(), 1);
            Object[] fila = (Object[])filas[0];
            r.setEstado((String)fila[0]);
        }
    }
}
