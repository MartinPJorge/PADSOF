/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package padsof;

import cat.quickdb.db.AdminBase;
import java.sql.SQLException;
import java.text.ParseException;
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
 *
 * @author e265923
 */
public class Booking {
    Vendedor sesion;
    String bookingDBName;

    /**
     *
     * @param bookingDBName
     */
    public Booking(String bookingDBName) {
        this.sesion = null;
        this.bookingDBName = bookingDBName;
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

    
        
    /**
     * Busca en la BD un Vendedor con idUsr y password iguales a los pasados como argumento.
     * Si lo encuentra, lo asigna al atributo sesion de Booking, si no lo encuentra, lanza una Exception.
     * @param idUsr
     * @param password
     * @throws FailedLoginExc 
     */
    public void login(int idUsr, String password) throws FailedLoginExc{
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.bookingDBName);
        Vendedor v = new Vendedor();
        if(admin.obtain(v, "idUsr="+idUsr+" AND password='"+password+"'")==false){
            admin.close();
            throw (new FailedLoginExc());
        }
        this.sesion=v;
        admin.close();
    }
    
    /**
     * Busca en la BD un Paquete por su campo idPaq.
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
     * Busca un Vendedor por su campo idUsr.
     * @param idUsr
     * @return Vendedor encontrado.
     * @throws NoResultsExc  
     */
    public Vendedor buscarVendedor(int idUsr) throws NoResultsExc{
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
     * Revisa la BD de Paquetes para encontrar el Paquete con mayor idPaq de todos y devolver
     * su idPaq + 1. 
     * Este metodo sera util a la hora de crear un nuevo Paquete, ya que nos proporcionara
     * el id que tendra el nuevo Paquete creado.
     * @return idPaq 
     */
    private int maxIdPaquete() {
        int max = 0;
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.bookingDBName);
        List<Paquete> paquetes = new ArrayList<>();
//        paquetes = admin.obtainAll(paquetes, "1=1");
        String queryIds = "SELECT p.id FROM Paquete AS p";
        Object[] o = admin.obtainJoin(queryIds, 1);
        if(o==null)
            return max;
        for(Object nav : o) {
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
     * Crea un nuevo Paquete (que de momento estara vacio) y lo guarda en la BD de Paquetes
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
            if(admin.obtain(p2, "idPaq="+p.getIdPaq())==false){
                admin.close();
                return null;                       
            }
            p2.cargarDatosPaqueteSQL(admin);
            admin.close();
            
            return p2;
        } catch (ParseException| SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }


    /**
     * Crea un nuevo Cliente y lo guarda en la BD.
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
            if(isAdmin==false)
                throw new PermissionExc();
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
            if(admin==false)
                throw new PermissionExc();
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
     * Recibe un idUsr de un Vendedor, lo busca, y devuelve su Facturación de servicios (Vuelos).
     * @param idUsr
     * @return Facturacion de Vuelos del Vendedor
     */
    public double factVuelos(int idUsr) {
        double fact = 0.0;
        try {
            Vendedor v = this.buscarVendedor(idUsr);
            List<Paquete> paquetes = v.getPaquetes(this.bookingDBName);
            AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.bookingDBName);
            for (Paquete p : paquetes) {
                List<Reserva> reservas = p.getReservas();
                for (Reserva r : reservas) {
                    if (r instanceof ReservaVuelo) {
                        fact += r.calcularPagado();
                    }
                }
            }
            admin.close();
            return fact;
        } catch (NoResultsExc ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se ha podido completar la operación.");
            return -1;
        }

    }

    /**
     * Recibe un idUsr de un Vendedor, lo busca, y devuelve su Facturación de servicios (Hoteles).
     * @param idUsr
     * @return Facturacion de Hoteles del Vendedor
     */
    public double factHoteles(int idUsr) {
        try {
            double fact = 0.0;
            Vendedor v = this.buscarVendedor(idUsr);
            List<Paquete> paquetes = v.getPaquetes(this.bookingDBName);
            AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.bookingDBName);
            for (Paquete p : paquetes) {
                List<Reserva> reservas = p.getReservas();
                for (Reserva r : reservas) {
                    if (r instanceof ReservaHotel) {
                        fact += r.calcularPagado();
                    }
                }
            }
            admin.close();
            return fact;
        } catch (NoResultsExc ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se ha podido completar la operación.");
            return -1;
        }
    }

    /**
     * Recibe un idUsr de un Vendedor, lo busca, y devuelve su Facturación de servicios 
     * (Viajes Organizados y Viajes del IMSERSO).
     * @param idUsr
     * @return Facturacion de Viajes Organizados y del IMSERSO del Vendedor
     */
    public double factViajesOrg(int idUsr) {
        try {
            double fact = 0.0;
            Vendedor v = this.buscarVendedor(idUsr);
            List<Paquete> paquetes = v.getPaquetes(this.bookingDBName);
            AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.bookingDBName);
            for (Paquete p : paquetes) {
                List<Reserva> reservas = p.getReservas();
                for (Reserva r : reservas) {
                    if (r instanceof ReservaViajOrg || r instanceof ReservaViajeIMSERSO) {
                        fact += p.calcularPagado();
                    }
                }
            }
            admin.close();
            return fact;
        } catch (NoResultsExc ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se ha podido completar la operación.");
            return -1;
        }
    }

    /**
     * Recibe un idUsr de un Vendedor, lo busca, y devuelve su Facturación total.
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
        
        if(paquetes == null)
            return 0.0;

        for (Paquete p : paquetes) {
            fact += p.calcularPagado();
        }

        return fact;
        
       /* Vendedor v = new Vendedor();
        try {
            v = this.buscarVendedor(idUsr);
        } catch (NoResultsExc ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        double facturacion = 0.0;
        Paquete p = new Paquete();
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.bookingDBName);
        Object[] o = admin.obtainJoin("SELECT p.id FROM Paquete AS p WHERE p.vendedor = " + v.getIdUsr(), 1);
        
        if(o == null) {return 0.0;}
        
        for(Object nav : o) {
            String[] fila = (String[]) nav;
            admin.obtain(p, "id = " + fila[0]);
            try {
                p.cargarDatosPaqueteSQL(admin);
            } catch (    ClassNotFoundException | SQLException | ParseException ex) {
                Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
                admin.close();
            }
            facturacion += p.calcularPagado();
        }
        
        admin.close();
        return facturacion;*/
    }

    /**
     * Devuelve la Facturación total de todos los servicios reservados por todos los Vendedores.
     * @return Facturacion de total de todos los Vendedores.
     */
    public double factTotal() {
        
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.bookingDBName);
        Paquete p = new Paquete();
        double facturacion = 0.0;
        List<Paquete> paquete = new ArrayList<Paquete>();
        Object[] o = admin.obtainJoin("SELECT p.id FROM Paquete AS p", 1);
        for(Object nav : o) {
            try {
                String[]fila = (String[])nav;
                admin.obtain(p, "id = " + fila[0]);
                p.cargarDatosPaqueteSQL(admin);
                facturacion += p.calcularPagado();
            } catch (    ClassNotFoundException | SQLException | ParseException ex) {
                Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
                admin.close();
            }
        }
        
        
        admin.close();
        
        
        return facturacion;
        
        
        
        
//        
//        double fact = 0.0;
//        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.bookingDBName);
//        Paquete p = new Paquete();
//        List<Paquete> paquetes = new ArrayList<Paquete>();
//        
//        Object[] o = admin.obtainJoin("SELECT p.id FROM Paquete AS p", 1);
//        if(o==null)
//            return 0.0;
//        for(Object nav : o) {
//            String[] id = (String[]) nav;
//            admin.obtain(p, "id = " + id[0]);
//            paquetes.add(p);
//        }
//        
//        for(Paquete nav : paquetes) {
//            try {
//                nav.cargarDatosPaqueteSQL(admin);
//            } catch (    ClassNotFoundException | SQLException | ParseException ex) {
//                Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        
//        admin.close();
//        for(Paquete nav : paquetes) {
//            fact += nav.calcularPagado();
//        }
//        
//        return fact;
    }
    
    //-----Boceto de un posible menú interactivo.-----
//    public public Paquete modificarPaquete(Paquete p) {
//        Scanner scan = new Scanner(System.in);
//        int opcion = 0;
//        while (opcion != 5) {
//            System.out.println("Elija una opción:");
//            System.out.println("1.-Añadir reservas.");
//            System.out.println("2.-Confirmar reservas.");
//            System.out.println("3.-Cancelar reservas.");
//            System.out.println("4.-Ver el precio del paquete.");
//            System.out.println("5.-Salir.");
//            opcion = scan.nextInt();
//            switch (opcion) {
//                case 1:
//                    //Buscar y añadir reserva.
//                    break;
//                case 2:
//                    //Confirmar
//                    break;
//                case 3:
//                    //Cancelar
//                    break;
//                case 4:
//                    System.out.println("Precio total del paquete:" + p.getPrecioTotal());
//                    System.out.println("Pagado:" + p.calcularPagado());
//                    break;
//                case 5:
//                    return p;
//                default:
//                    System.out.println("Opción incorrecta.");
//            }
//
//        }
//    }
}
