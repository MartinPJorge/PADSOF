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
public class Vendedor extends Persona{
    private int id;
    private String password;
    
    public Vendedor() {}
    
    public Vendedor(int dia, int mes, int year, String nombre, String apellido,
            String DNI, String password){
        super(dia, mes, year, nombre, apellido, DNI);
        this.password=password;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString(){
        String v = super.toString() + this.id + this.password;
        return v;
}
    @Override
    public void mostrarDatos(){
        System.out.println(this.toString());
    }
}
