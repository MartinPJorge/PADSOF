/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package padsof;

import cat.quickdb.db.AdminBase;
import catalogo.CatalogoHotel;
import catalogo.CatalogoViajIMSERSO;
import catalogo.CatalogoViajOrg;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import myexception.FailedLoginExc;
import myexception.NoResultsExc;
import myexception.PermissionExc;
import persona.Cliente;
import persona.Vendedor;
import reserva.*;

/**
 * Clase Booking
 *
 * @author Jorge Martin, Ivan Marquez
 * @version 1.0
 */
public class Booking {

    Vendedor sesion;
    String bookingDBName;
    private static String csvHoteles = "Hoteles.csv";
    private static String csvIMSERSO = "ViajesIMSERSO.csv";
    private static String csvViajOrg = "ViajesOrganizados.csv";
    private CatalogoHotel catalogoHotel;
    private CatalogoViajOrg catalogoViajOrg;
    private CatalogoViajIMSERSO catalogoViajIMSERSO;

    /**
     *
     * @param bookingDBName
     */
    public Booking(String bookingDBName) {
        this.sesion = null;
        this.bookingDBName = bookingDBName;
        
        try {
            this.catalogoHotel = new CatalogoHotel(csvHoteles);
            this.catalogoViajOrg = new CatalogoViajOrg(csvViajOrg);
            this.catalogoViajIMSERSO = new CatalogoViajIMSERSO(csvIMSERSO);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getBookingDBName() {
        return bookingDBName;
    }

    public void setBookingDBName(String BookingDBName) {
        this.bookingDBName = BookingDBName;
    }

    /**
     *
     * @return Vendedor (que ha iniciado sesion)
     */
    public Vendedor getSesion() {
        return sesion;
    }

    /**
     *
     * @param sesion
     */
    public void setSesion(Vendedor sesion) {
        this.sesion = sesion;
    }

    public static String getCsvHoteles() {
        return csvHoteles;
    }

    public static void setCsvHoteles(String csvHoteles) {
        Booking.csvHoteles = csvHoteles;
    }

    public static String getCsvIMSERSO() {
        return csvIMSERSO;
    }

    public static void setCsvIMSERSO(String csvIMSERSO) {
        Booking.csvIMSERSO = csvIMSERSO;
    }

    public static String getCsvViajOrg() {
        return csvViajOrg;
    }

    public static void setCsvViajOrg(String csvViajOrg) {
        Booking.csvViajOrg = csvViajOrg;
    }

    public CatalogoHotel getCatalogoHotel() {
        return catalogoHotel;
    }

    public CatalogoViajOrg getCatalogoViajOrg() {
        return catalogoViajOrg;
    }

    public CatalogoViajIMSERSO getCatalogoViajIMSERSO() {
        return catalogoViajIMSERSO;
    }
    
    

    /**
     * Busca en la BD un Vendedor con idUsr y password iguales a los pasados
     * como argumento. Si lo encuentra, lo asigna al atributo sesion de Booking,
     * si no lo encuentra, lanza una Exception.
     *
     * @param idUsr
     * @param password
     * @throws FailedLoginExc
     */
    public void login(int idUsr, String password) throws FailedLoginExc {
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.bookingDBName);
        Vendedor v = new Vendedor();
        if (admin.obtain(v, "idUsr=" + idUsr + " AND password='" + password + "'") == false) {
            admin.close();
            throw (new FailedLoginExc());
        }
        this.sesion = v;
        admin.close();
    }

    /**
     * Busca en la BD un Paquete por su campo idPaq.
     * <br/><u>Nota</u>:<br/>
     * Hay que cerrar toda conexi&oacute;n con la BD antes de llamar a este m&eacute;todo.
     * @param id
     * @return Paquete encontrado
     */
    public Paquete buscarPaquete(int id) throws NoResultsExc {
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.bookingDBName);
        Paquete p = new Paquete();
        if (admin.obtain(p, "idPaq=" + id) == false) {
            admin.close();
            throw (new NoResultsExc());
        } else {
            admin.close();
            return p;
        }
    }

    /**
     * Busca en la BD un Cliente por su campo DNI.
     *
     * @param DNI
     * @return Cliente encontrado
     * @throws NoResultsExc
     */
    public Cliente buscarCliente(String DNI) throws NoResultsExc {

        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.bookingDBName);
        Cliente c = new Cliente();
        if (admin.obtain(c, "DNI='" + DNI + "'") == false) {
            admin.close();
            throw (new NoResultsExc());
        } else {
            admin.close();
            return c;
        }
    }
    
    /**
     * Busca todos los paquetes reservados para el cliente con un DNI que pasamos 
     * por argumento.
     * @param dniCliente
     * @return paquetes encontrados.
     */
    public List<Paquete> buscarPaquetesPorCliente(String dniCliente) throws NoResultsExc {
        List<Paquete> resultados = new ArrayList<Paquete>();
        Paquete paqVacio = new Paquete();
        
        //Obtenemos los idPaq del cliente
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.bookingDBName);
        Object[] idPaqsObj = admin.obtainJoin("SELECT p.idPaq FROM Paquete AS p WHERE cliente = '" + dniCliente + "'", 1);
        admin.close();
        
        //Buscamos el paquete por su idPaq
        for(Object idPaq : idPaqsObj) {
            String[] fila = (String[])idPaq;
            paqVacio = buscarPaquete(Integer.parseInt(fila[0]));
            resultados.add(paqVacio);
        }
        
        return resultados;
    }

    /**
     * Busca un Vendedor por su campo idUsr.
     *
     * @param idUsr
     * @return Vendedor encontrado.
     * @throws NoResultsExc
     */
    public Vendedor buscarVendedor(int idUsr) throws NoResultsExc {
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.bookingDBName);

        Vendedor v = new Vendedor();

        if (admin.obtain(v, "idUsr=" + idUsr) == false) {
            admin.close();
            throw (new NoResultsExc());
        } else {
            admin.close();
            return v;
        }
    }

    /**
     * Revisa la BD de Paquetes para encontrar el Paquete con mayor idPaq de
     * todos y devolver su idPaq + 1. Este metodo sera util a la hora de crear
     * un nuevo Paquete, ya que nos proporcionara el id que tendra el nuevo
     * Paquete creado.
     *
     * @return idPaq
     */
    public int maxIdPaquete() {
        int max = 0;
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.bookingDBName);
        List<Paquete> paquetes = new ArrayList<>();

        Object[] existe = admin.obtainJoin("SELECT name FROM sqlite_master WHERE type='table' AND name='Paquete'", 1);
        if(existe == null){
            admin.close();
            return 0;
        }
        
        String queryIds = "SELECT p.id FROM Paquete AS p";
        Object[] o = admin.obtainJoin(queryIds, 1);
        if (o == null) {
            admin.close();
            return max;
        }
        for (Object nav : o) {
            String[] res = (String[]) nav;
            Paquete paq = new Paquete();
            admin.obtain(paq, "id = " + res[0]);
            paquetes.add(paq);
        }
        admin.close();
        if (paquetes.isEmpty()) {
            return max;
        } else {
            Collections.sort(paquetes, new IdComparator());
            return paquetes.get(0).getId() + 1;
        }
    }

    /**
     * Crea un nuevo Paquete (que de momento estar&aacute; vacio) y lo guarda en la BD
     * de Paquetes
     *
     * @param c
     * @param v
     * @return
     */
    public Paquete crearPaquete(Cliente c, Vendedor v) {
        try {
            Paquete p = new Paquete(this.maxIdPaquete(), 1, c.getNombre(), v.getIdUsr());
            AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.bookingDBName);
            admin = p.guardar(admin);
            Paquete p2 = new Paquete();
            if (admin.obtain(p2, "idPaq=" + p.getIdPaq()) == false) {
                admin.close();
                return null;
            }
            p2.cargarDatosPaqueteSQL(admin);
            admin.close();

            return p2;
        } catch (ParseException | SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Crea un nuevo Cliente y lo guarda en la BD.
     *
     * @param nombre
     * @param apellidos
     * @param DNI
     * @param dia
     * @param mes
     * @param anio
     * @return Cliente creado
     */
    public Cliente registrarCliente(String nombre, String apellidos, String DNI,
            int dia, int mes, int anio) {

        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.bookingDBName);
        Cliente c = new Cliente(nombre, apellidos, DNI, dia, mes, anio);
        if (admin.save(c) == false) {
            admin.close();
            return null;
        } else {
            admin.close();
            return c;
        }
    }

    /**
     * Crea un nuevo Vendedor y lo guarda en la BD.
     *
     * @param nombre
     * @param apellido
     * @param DNI
     * @param dia
     * @param mes
     * @param idUsr
     * @param anio
     * @param password
     * @param jefe
     * @return Vendedor creado
     */
    public Vendedor darAltaVendedor(String nombre, String apellido, String DNI, int dia, int mes, int anio,
            int idUsr, String password, int jefe) {
        try {
            boolean isAdmin = sesion.isAdmin();
            if (isAdmin == false) {
                throw new PermissionExc();
            }
            AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.bookingDBName);
            Vendedor v = new Vendedor(nombre, apellido, DNI, dia, mes, anio, idUsr, password, jefe);
            if (admin.save(v) == false) {
                admin.close();
                return null;
            } else {
                admin.close();
                return v;
            }
        } catch (PermissionExc ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * M&eacute;todo para ver la facturaci&oacute;n de manera interactiva.
     */
    public void facturacion() {
        try {
            boolean admin = sesion.isAdmin();
            if (admin == false) {
                throw new PermissionExc();
            }
            Scanner scan = new Scanner(System.in);
            int opcion = 0, idUsr = -1;
            while (opcion != 4) {

                System.out.println("Elija una opción:");
                System.out.println("1.-Ver facturación total.");
                System.out.println("2.-Ver facturación total de un vendedor.");
                System.out.println("3.-Ver facturación por servicios de un vendedor.");
                System.out.println("4.-Salir.");
                opcion = scan.nextInt();
                switch (opcion) {
                    case 1:
                        System.out.println("Facturación total realizada:" + this.factTotal());
                        break;
                    case 2:
                        System.out.println("Introduzca el idUsr del vendedor:");
                        idUsr = scan.nextInt();
                        System.out.println("Facturación total realizada por el vendedor con idUsr="
                                + idUsr + " : " + this.factTotal(idUsr));
                        break;
                    case 3:
                        System.out.println("Introduzca el idUsr del vendedor:");
                        idUsr = scan.nextInt();
                        System.out.println("Facturación realizada por el vendedor con idUsr=" + idUsr + " :");
                        System.out.println("Facturación de Hoteles:" + this.factHoteles(idUsr));
                        System.out.println("Facturación de Vuelos:" + this.factVuelos(idUsr));
                        System.out.println("Facturación de Viajes Organizados:" + this.factViajesOrg(idUsr));
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Opción incorrecta.");
                }
            }
        } catch (PermissionExc ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Recibe un idUsr de un Vendedor, lo busca, y devuelve su Facturación de
     * servicios (Vuelos).
     *
     * @param idUsr
     * @return Facturacion de Vuelos del Vendedor
     */
    public double factVuelos(int idUsr) {
        GregorianCalendar cal = new GregorianCalendar(1970, 0, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaInicial;
        try {
            fechaInicial = sdf.parse(sdf.format(cal.getTime()));
        } catch (ParseException ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
            return 0.0;
        }
        return factVuelos(idUsr, fechaInicial);

    }

    /**
     * Recibe un idUsr de un Vendedor, lo busca, y devuelve su Facturación de
     * servicios (Vuelos) posterior a fromDate.
     *
     * @param idUsr
     * @param fromDate
     * @return Facturacion de Vuelos del Vendedor
     */
    public double factVuelos(int idUsr, Date fromDate) {
        double fact = 0.0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Vendedor v = this.buscarVendedor(idUsr);
            List<Paquete> paquetes = v.getPaquetes(this.bookingDBName);
            AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.bookingDBName);
            for (Paquete p : paquetes) {
                List<Reserva> reservas = p.getReservas();
                for (Reserva r : reservas) {
                    if (r instanceof ReservaVuelo) {
                        if (sdf.parse(r.getFechaInicio()).equals(fromDate)
                                || sdf.parse(r.getFechaInicio()).after(fromDate)) {
                            fact += r.calcularPagado();
                        }
                    }
                }
            }
            admin.close();
            return fact;
        } catch (ParseException | NoResultsExc ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se ha podido completar la operación.");
            return -1;
        }

    }

    /**
     * Recibe un idUsr de un Vendedor, lo busca, y devuelve su Facturación de
     * servicios (Hoteles).
     *
     * @param idUsr
     * @return Facturacion de Hoteles del Vendedor
     */
    public double factHoteles(int idUsr) {
        GregorianCalendar cal = new GregorianCalendar(1970, 0, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaInicial;
        try {
            fechaInicial = sdf.parse(sdf.format(cal.getTime()));
        } catch (ParseException ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
            return 0.0;
        }
        return factHoteles(idUsr, fechaInicial);
    }

    /**
     * Recibe un idUsr de un Vendedor, lo busca, y devuelve su Facturación de
     * servicios (Hoteles) posterior a fromDate.
     *
     * @param idUsr
     * @return Facturacion de Hoteles del Vendedor
     */
    public double factHoteles(int idUsr, Date fromDate) {
        try {
            double fact = 0.0;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Vendedor v = this.buscarVendedor(idUsr);
            List<Paquete> paquetes = v.getPaquetes(this.bookingDBName);
            AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.bookingDBName);
            for (Paquete p : paquetes) {
                List<Reserva> reservas = p.getReservas();
                for (Reserva r : reservas) {
                    if (r instanceof ReservaHotel) {
                        if (sdf.parse(r.getFechaInicio()).equals(fromDate)
                                || sdf.parse(r.getFechaInicio()).after(fromDate)) {
                            fact += r.calcularPagado();
                        }
                    }
                }
            }
            admin.close();
            return fact;
        } catch (ParseException | NoResultsExc ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se ha podido completar la operación.");
            return -1;
        }
    }

    /**
     * Recibe un idUsr de un Vendedor, lo busca, y devuelve su Facturación de
     * servicios (Viajes Organizados y Viajes del IMSERSO).
     *
     * @param idUsr
     * @return Facturacion de Viajes Organizados y del IMSERSO del Vendedor
     */
    public double factViajesOrg(int idUsr) {
        GregorianCalendar cal = new GregorianCalendar(1970, 0, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaInicial;
        try {
            fechaInicial = sdf.parse(sdf.format(cal.getTime()));
        } catch (ParseException ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
            return 0.0;
        }
        return factViajesOrg(idUsr, fechaInicial);
    }

    /**
     * Recibe un idUsr de un Vendedor, lo busca, y devuelve su Facturación de
     * servicios (Viajes Organizados y Viajes del IMSERSO) posterior a untilDate.
     *
     * @param idUsr
     * @return Facturacion de Viajes Organizados y del IMSERSO del Vendedor
     */
    public double factViajesOrg(int idUsr, Date fromDate) {
        try {
            double fact = 0.0;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Vendedor v = this.buscarVendedor(idUsr);
            List<Paquete> paquetes = v.getPaquetes(this.bookingDBName);
            AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.bookingDBName);
            for (Paquete p : paquetes) {
                List<Reserva> reservas = p.getReservas();
                for (Reserva r : reservas) {
                    if (r instanceof ReservaViajOrg || r instanceof ReservaViajeIMSERSO) {
                        if (sdf.parse(r.getFechaInicio()).equals(fromDate)
                                || sdf.parse(r.getFechaInicio()).after(fromDate)) {
                            fact += p.calcularPagado();
                        }
                    }
                }
            }
            admin.close();
            return fact;
        } catch (ParseException | NoResultsExc ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se ha podido completar la operación.");
            return -1;
        }
    }

    /**
     * Recibe un idUsr de un Vendedor, lo busca, y devuelve su Facturación
     * total.
     *
     * @param idUsr
     * @return Facturacion total del Vendedor
     */
    public double factTotal(int idUsr) {
        Vendedor v;
        List<Paquete> paquetes;
        double fact = 0.0;
        try {
            v = this.buscarVendedor(idUsr);
            paquetes = v.getPaquetes(this.bookingDBName);
        } catch (NoResultsExc ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se ha podido completar la operación.");
            return 0.0;
        }

        if (paquetes == null) {
            return 0.0;
        }

        for (Paquete p : paquetes) {
            fact += p.calcularPagado();
        }

        return fact;

    }

    /**
     * Devuelve la Facturación total de todos los servicios reservados por todos
     * los Vendedores.
     * <br/><u>Nota</u>:<br/>
     * Antes de llamar a este m&eacute;todo es necesario haber cerrado toda posible 
     * conexi&oacute;n con la BD.
     *
     * @return Facturacion de total de todos los Vendedores.
     */
    public double factTotal() {

        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.bookingDBName);
        Paquete p = new Paquete();
        double facturacion = 0.0;
        Object[] o = admin.obtainJoin("SELECT p.id FROM Paquete AS p", 1);
        for (Object nav : o) {
            try {
                String[] fila = (String[]) nav;
                admin.obtain(p, "id = " + fila[0]);
                p.cargarDatosPaqueteSQL(admin);
                facturacion += p.calcularPagado();
            } catch (ClassNotFoundException | SQLException | ParseException ex) {
                Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
                admin.close();
            }
        }

        admin.close();
        return facturacion;

    }
    
    
    /**
     * Se encarga de guardar en la BD el paquete pasado por argumento.
     * @param p
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public void savePaquete(Paquete p) throws SQLException, ClassNotFoundException {
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.bookingDBName);
        admin = p.guardar(admin);
        //admin.save(admin);
        admin.close();
    }
    
    /**
     * Comprueba que la tabla PaqueteReserva no tenga ceros en la columna 'related'.
     * @param admin
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static AdminBase checkAsignaReservas(AdminBase admin) throws SQLException, ClassNotFoundException {
        String nombreBD = Paquete.getDBName(admin);
        boolean primeraVez = false;

        Object[] filas = admin.obtainJoin("SELECT related FROM PaqueteReserva", 1);
        for(Object fila : filas) {
            String[] cols = (String[]) fila;
            if(cols[0].equals("0")) {
                primeraVez = true;
            }
        }
        
        //Actualizamos la asociacion
        Connection conn = Paquete.quickDBtoJDBC(admin);
        Statement stmt = conn.createStatement();

        if(primeraVez) {
            stmt.executeUpdate("UPDATE PaqueteReserva SET related=(id+1) WHERE id >= 0");
        }
        
        //Cerramos JDBC y devolvemos la conexi&oacute;n recibida.
        stmt.close();
        conn.close();
        return AdminBase.initialize(AdminBase.DATABASE.SQLite, nombreBD);
    }
    
    
    /**
     * Controla la situaci&oacute;n particular en la que la columna de id de la 
     * tabla de asociaci&oacute;n, queda por encima de la columna de 'related's.
     * @param admin
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public AdminBase desbordaAsociacion(AdminBase admin) throws SQLException, ClassNotFoundException {
        String nombreBD = Paquete.getDBName(admin);
        boolean primeraVez = false;

        //Minimo indice de los ids
        Double minIz = Double.POSITIVE_INFINITY;;
        Object[] filas = admin.obtainJoin("SELECT id FROM PaqueteReserva", 1);
        for(Object fila : filas) {
            String[] cols = (String[]) fila;
            if(Double.parseDouble(cols[0]) < minIz) {
                minIz = Double.parseDouble(cols[0]);
            }
        }
        
        //Maximo indice de los relateds
        Double minDer = Double.POSITIVE_INFINITY;;
        filas = admin.obtainJoin("SELECT related FROM PaqueteReserva", 1);
        for(Object fila : filas) {
            String[] cols = (String[]) fila;
            if(Double.parseDouble(cols[0]) < minDer) {
                minDer = Double.parseDouble(cols[0]);
            }
        }
        
        
        //Actualizamos la asociacion
        Connection conn = Paquete.quickDBtoJDBC(admin);
        Statement stmt = conn.createStatement();

        if(minIz > minDer) {
            
            stmt.executeUpdate("UPDATE PaqueteReserva SET id = (related-1) WHERE id >= 0");
        }
        
        //Cerramos JDBC y devolvemos la conexi&oacute;n recibida.
        stmt.close();
        conn.close();
        return AdminBase.initialize(AdminBase.DATABASE.SQLite, nombreBD);
    }
}
