/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persona;

import java.util.Date;

/**
 *
 * @author e265923
 */
public class Cliente extends Persona{

    /**
     * 
     */
    public Cliente() {
    }

    /**
     * 
     * @param nombre
     * @param apellido
     * @param DNI
     * @param fechaNac
     */
    public Cliente(String nombre, String apellido, String DNI, Date fechaNac) {
        super(nombre, apellido, DNI, fechaNac);
    }

    @Override
    public String toString() {
        String c = super.toString();
        return c;
    }
    
    @Override
    public void mostrarDatos(){
        System.out.println(this.toString());
    }

}
