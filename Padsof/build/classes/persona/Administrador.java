/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persona;

import cat.quickdb.annotation.Column;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author e265923
 */
public class Administrador extends Vendedor{
    @Column(collectionClass="Vendedor")
    private List<Vendedor> vendedor;

    /**
     * 
     */
    public Administrador() {
    }
    
    /**
     * 
     * @param nombre
     * @param apellido
     * @param DNI
     * @param fechaNac
     * @param id
     * @param password
     */
    public Administrador(String nombre, String apellido, String DNI, Date fechaNac, int id, String password) {
        super(nombre, apellido, DNI, fechaNac, id, password, null);
        this.vendedor = new ArrayList<Vendedor>(); 
    }

    /**
     * 
     * @return
     */
    public List<Vendedor> getVendedor() {
        return vendedor;
    }

    /**
     * 
     * @param vendedor
     */
    public void setVendedor(List<Vendedor> vendedor) {
        this.vendedor = vendedor;
    }

    @Override
    public String toString() {
        String a = super.toString() + vendedor;
        return a;
    }

    @Override
    public void mostrarDatos() {
        System.out.println(this.toString());
    }
    
    
}
