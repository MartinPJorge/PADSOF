/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package padsof;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author e265923
 */
public class Administrador extends Vendedor{
    private List<Vendedor> subord;

    public Administrador(String nombre, String apellido, String DNI, Date fechaNac, int id, String password) {
        super(nombre, apellido, DNI, fechaNac, id, password, null);
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

    public void setSubord(ArrayList<Vendedor> subord) {
        this.subord = subord;
    }
     
    
    
}
