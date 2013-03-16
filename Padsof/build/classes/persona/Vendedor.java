/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persona;

import cat.quickdb.annotation.Column;
import java.util.ArrayList;
import reserva.Paquete;

/**
 *
 * @author e265923
 */
public class Vendedor extends Persona{
    private int id;
    private String jefe;
    private String password;
    @Column(collectionClass="reserva.Paquete")
    private ArrayList<Paquete> paquete;
    
    
    /**
     * 
     */
    public Vendedor() {}
    
    /**
     * 
     * @param nombre
     * @param apellido
     * @param DNI
     * @param fechaNac
     * @param idUsr
     * @param password
     * @param jefe
     */
    public Vendedor(String nombre, String apellido, String DNI, int dia, int mes,
            int year, int idUsr, String password, String jefe){
        super(nombre, apellido, DNI, dia, mes, year);
        this.id = idUsr;
        this.password = password;
        this.jefe = jefe;
        this.paquete = new ArrayList<Paquete>();
}

    /**
     * 
     * @return
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * 
     * @param idUsr
     */
    @Override
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * 
     * @return
     */
    public ArrayList<Paquete> getPaquete() {
        return this.paquete;
    }

    /**
     * 
     * @param paquete
     */
    public void setPaquete(ArrayList<Paquete> paquete) {
        this.paquete = paquete;
    }


    /**
     * 
     * @return
     */
    public String getPassword() {
        return password;
    }

    public String getJefe() {
        return jefe;
    }

    public void setJefe(String jefe) {
        this.jefe = jefe;
    }

    
    /**
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void addPaquete(Paquete p){
        this.paquete.add(p);
    }
    
    /*public void addPaquete(List<Paquete> lp){
        this.paquete.addAll(lp);
    }*/
    
    /**
     * 
     * @return
     */
    @Override
    public String toString(){
        String v = super.toString() + this.id + this.password;
        return v;
}
    /**
     * 
     */
    @Override
    public void mostrarDatos(){
        System.out.println(this.toString());
    }
}
