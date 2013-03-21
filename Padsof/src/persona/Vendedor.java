/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persona;

import cat.quickdb.annotation.Column;
import cat.quickdb.db.AdminBase;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import myexception.NoResultsExc;
import myexception.PermissionExc;
import reserva.Paquete;


/**
 *
 * @author e265923
 */
public class Vendedor extends Persona{
    private int id;
    private int idUsr;
    private String password;
    private int jefe;
//    @Column(collectionClass="reserva.Paquete")
//    private ArrayList<Paquete> paquete;
    
    
    /**
     * Constructor vacío para Vendedor (necesario para la BD).
     */
    public Vendedor() {}
    
    /**
     * 
     * @param nombre
     * @param apellido
     * @param DNI
     * @param dia 
     * @param idUsr
     * @param mes 
     * @param anio 
     * @param password
     * @param jefe
     */
    public Vendedor(String nombre, String apellido, String DNI, int dia, int mes,
            int anio, int idUsr, String password, int jefe){
        super(nombre, apellido, DNI, dia, mes, anio);
        this.idUsr = idUsr;
        this.password = password;
        this.jefe = jefe;
}

    /**
     * 
     * @return id
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * 
     */
    @Override
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 
     * @return idUsr
     */
    public int getIdUsr() {
        return idUsr;
    }

    /**
     * 
     * @param idUsr
     */
    public void setIdUsr(int idUsr) {
        this.idUsr = idUsr;
    }
    

    /**
     * Busca en la BD los Paquetes relacionados con un Vendedor y los devuelve en forma
     * de ArrayList<Paquete>.
     * Si no encuentra ningún paquete para el vendedor en cuestión, devuelve una Excepcion.
     * @param PacksBDname
     * @return ArrayList<Paquete> con los paquetes del vendedor
     * @throws NoResultsExc
     */
    public ArrayList<Paquete> getPaquetes(String PacksBDname) throws NoResultsExc {
        ArrayList<Paquete> packs = new ArrayList<Paquete>();
        Paquete paquete = new Paquete();
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, PacksBDname);
        String query = "vendedor="+this.getIdUsr();
        
        String queryIds = "SELECT p.id FROM Paquete AS p WHERE p.vendedor =" + this.getIdUsr();
        Object[] o = admin.obtainJoin(queryIds, 1);
        if(o==null) {
            admin.close();
            return null;
        }
        for(Object nav : o) {
            String[] res = (String[]) nav;
            Paquete paq = new Paquete();
            admin.obtain(paq, "id = " + res[0]);
            packs.add(paq);
        }
        
       // admin.obtain(paquete, query);
       // packs=admin.obtainAll(paquete, query);
        for (Paquete p : packs) {
            try {
                p.cargarDatosPaqueteSQL(admin);
            } catch (ClassNotFoundException | SQLException | ParseException ex) {
                Logger.getLogger(Vendedor.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Se produjo un error al cargar las reservas de los paquetes.");
                admin.close();
                return null;
            }
        }
        admin.close();
        return packs;
    }
    
    /**
     * 
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     * @return jefe
     */
    public int getJefe() {
        return jefe;
    }

    /**
     * 
     * @param jefe
     */
    public void setJefe(int jefe) {
        this.jefe = jefe;
    }

    
    /**
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    
    /**
     * Determina si el Vendedor sobre el que llamamos el m&eacute;todo es un Administrador o no
     * @return boolean
     */
    public boolean isAdmin() throws PermissionExc{
        boolean isAdmin;
        isAdmin = (this.getIdUsr()==0 && this.getJefe()==this.getIdUsr());
        return isAdmin;
    }
    
    /**
     * 
     * @return String con info de Vendedor
     */
    @Override
    public String toString(){
        String v = super.toString() + this.id + this.password;
        return v;
}
    /**
     * Muestra los datos de Vendedor por pantalla.
     */
    @Override
    public void mostrarDatos(){
        System.out.println(this.toString());
    }
}
