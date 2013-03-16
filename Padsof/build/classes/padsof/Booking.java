/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package padsof;

import cat.quickdb.db.AdminBase;
import java.util.*;
import persona.Administrador;
import persona.Cliente;
import persona.Vendedor;
import reserva.*;

/**
 *
 * @author e265923
 */
public class Booking {
    Vendedor sesion;
    String clienteDBName;
    String vendedorDBName;
    String paqueteDBName;

    /**
     *
     * @param clienteDBName
     * @param vendedorDBName
     * @param paqueteDBName
     */
    public Booking(String clienteDBName, String vendedorDBName, String paqueteDBName) {
        this.clienteDBName = clienteDBName;
        this.vendedorDBName = vendedorDBName;
        this.paqueteDBName = paqueteDBName;
    }

    /**
     *
     * @return
     */
    public String getClienteDBName() {
        return clienteDBName;
    }

    /**
     *
     * @param clienteDBName
     */
    public void setClienteDBName(String clienteDBName) {
        this.clienteDBName = clienteDBName;
    }

    /**
     *
     * @return
     */
    public String getPaqueteDBName() {
        return paqueteDBName;
    }

    /**
     *
     * @param paqueteDBName
     */
    public void setPaqueteDBName(String paqueteDBName) {
        this.paqueteDBName = paqueteDBName;
    }

    /**
     *
     * @return
     */
    public String getVendedorDBName() {
        return vendedorDBName;
    }

    /**
     *
     * @param vendedorDBName
     */
    public void setVendedorDBName(String vendedorDBName) {
        this.vendedorDBName = vendedorDBName;
    }

    /**
     *
     * @param id
     * @return
     */
    public Paquete buscarPaquete(int id) {
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.paqueteDBName);
        Paquete p = new Paquete();
        if (admin.obtain(p, "idPaq='" + id + "'") == false) {
            admin.close();
            return null;
        } else {
            admin.close();
            return p;
        }

    }

    private int maxIdPaquete() {
        int max = 0;
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.paqueteDBName);
        List<Paquete> paquetes = new ArrayList<>();
        paquetes = admin.obtainAll(paquetes, "1=1");
        admin.close();
        if (paquetes.isEmpty()) {
            return max;
        } else {
            Collections.sort(paquetes, new IdComparator());
            return paquetes.get(0).getId();
        }
    }

    /**
     *
     * @param c
     * @param v
     */
    public void crearPaquete(Cliente c, Vendedor v) {
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.paqueteDBName);
        Paquete p = new Paquete(this.maxIdPaquete()+1, 1, c.getNombre(), v.getNombre());
        if (admin.save(p) == false) {
            admin.close();
            return;//throw exception
        } else {
            admin.close();
        }
    }

    /**
     *
     * @param DNI
     * @return
     */
    public Cliente buscarCliente(String DNI) {

        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.clienteDBName);
        Cliente c = new Cliente();
        if (admin.obtain(c, "DNI='" + DNI + "'") == false) {
            admin.close();
            return null;
        } else {
            admin.close();
            return c;
        }
    }

    /**
     *
     * @param idUsr
     * @return
     */
    public Vendedor buscarVendedor(int idUsr) {
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.vendedorDBName);
        Vendedor v = new Vendedor();
        if (admin.obtain(v, "idUsr='" + idUsr + "'") == false) {
            admin.close();
            return null;
        } else {
            admin.close();
            return v;
        }
    }

    /**
     *
     * @param nombre
     * @param apellidos
     * @param DNI
     * @param fechaNac
     * @return
     */
    public Cliente registrarCliente(String nombre, String apellidos, String DNI,
            Date fechaNac) {

        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.clienteDBName);
        Cliente c = new Cliente(nombre, apellidos, DNI, fechaNac);
        if (admin.save(c) == false) {
            admin.close();
            return null;
        } else {
            admin.close();
            return c;//void?
        }
    }

    /**
     *
     * @param nombre
     * @param apellido
     * @param DNI
     * @param fechaNac
     * @param idUsr
     * @param password
     * @param administrador
     * @return
     */
    public Vendedor darAltaVendedor(String nombre, String apellido, String DNI, Date fechaNac, int idUsr, String password,
            String adm) {
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.clienteDBName);
        Vendedor v = new Vendedor(nombre, apellido, DNI, fechaNac, idUsr, password, adm);
        if (admin.save(v) == false) {
            admin.close();
            return null;
        } else {
            admin.close();
            return v;//void?
        }
    }

    /**
     *
     * @param idUsr
     * @return
     */
    public double factVuelos(int idUsr) {
        double fact = 0.0;
        Vendedor v = this.buscarVendedor(idUsr);
        List<Paquete> paquetes = v.getPaquete();
        for (Paquete p : paquetes) {
            List<Reserva> reservas = p.getReservas();
            for (Reserva r : reservas) {
                if (r instanceof ReservaVuelo) {
                    fact += r.getPrecio();
                }
            }
        }
        return fact;
    }

    /**
     *
     * @param idUsr
     * @return
     */
    public double factHoteles(int idUsr) {
        double fact = 0.0;
        Vendedor v = this.buscarVendedor(idUsr);
        List<Paquete> paquetes = v.getPaquete();
        for (Paquete p : paquetes) {
            List<Reserva> reservas = p.getReservas();
            for (Reserva r : reservas) {
                if (r instanceof ReservaHotel) {
                    fact += r.getPrecio();
                }
            }
        }
        return fact;
    }

    /**
     *
     * @param idUsr
     * @return
     */
    public double factViajesOrg(int idUsr) {
        double fact = 0.0;
        Vendedor v = this.buscarVendedor(idUsr);
        List<Paquete> paquetes = v.getPaquete();
        for (Paquete p : paquetes) {
            List<Reserva> reservas = p.getReservas();
            for (Reserva r : reservas) {
                if (r instanceof ReservaViajOrg || r instanceof ReservaViajeIMSERSO) {
                    fact += r.getPrecio();
                }
            }
        }
        return fact;
    }

    /**
     *
     * @param idUsr
     * @return
     */
    public double factTotal(int idUsr) {
        double fact = 0.0;
        Vendedor v = this.buscarVendedor(idUsr);
        List<Paquete> paquetes = v.getPaquete();
        for (Paquete p : paquetes) {
            List<Reserva> reservas = p.getReservas();
            for (Reserva r : reservas) {
                fact += r.getPrecio();
            }
        }
        return fact;
    }
}
