/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package padsof;

import java.util.Date;

/**
 *
 * @author e265923
 */
public class Vendedor extends Persona{
    private int id;
    private String password;
    private Administrador jefe;
    
    public Vendedor(String nombre, String apellido, String DNI, Date fechaNac, int id, String password,
            Administrador jefe){
        super(nombre, apellido, DNI, fechaNac);
        this.id=id;
        this.password=password;
        this.jefe=jefe;
}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public Administrador getJefe() {
        return jefe;
    }

    public void setJefe(Administrador jefe) {
        this.jefe = jefe;
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
