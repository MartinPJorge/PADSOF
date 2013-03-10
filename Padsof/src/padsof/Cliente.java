/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package padsof;

import java.sql.Date;

/**
 *
 * @author e265923
 */
public class Cliente extends Persona{

    int id;
    
    public Cliente() {}
    
    public Cliente(int dia, int mes, int year, String nombre, String apellido, String DNI) {
        super(dia, mes, year, nombre, apellido, DNI);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
    
    

}
