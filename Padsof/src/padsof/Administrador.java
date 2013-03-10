/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package padsof;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author e265923
 */
public class Administrador extends Vendedor{
    int id;
    private List<Vendedor> subord;
    
    public Administrador() {}
    
    public Administrador(String nombre, String apellido, String DNI, Date fechaNac, 
            String password) {
        super(nombre, apellido, DNI, fechaNac, password);
        this.subord = new ArrayList<Vendedor>(); 
    }

    @Override
    public String toString() {
        String a = super.toString() + subord;
        return a;
    }

    public List<Vendedor> getSubord() {
        return subord;
    }
    
    public int getId() {
        return this.id;
    }

    public void setSubord(ArrayList<Vendedor> subord) {
        this.subord = subord;
    }
     
    public void setId(int id) {
        this.id = id;
    }
    
}
