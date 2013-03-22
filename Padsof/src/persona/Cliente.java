/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persona;

import java.util.Date;

/**
 * Clase Cliente
 *
 * @author Jorge Martin, Ivan Marquez
 * @version 1.0
 */
public class Cliente extends Persona {

    int id;

    /**
     * Constructor vacío para Cliente (necesario para la BD).
     */
    public Cliente() {
    }

    /**
     *
     * @param nombre
     * @param apellido
     * @param DNI
     * @param dia
     * @param mes
     * @param anio
     */
    public Cliente(String nombre, String apellido, String DNI, int dia, int mes, int anio) {
        super(nombre, apellido, DNI, dia, mes, anio);
    }

    @Override
    public String toString() {
        String c = super.toString();
        return c;
    }

    @Override
    public void mostrarDatos() {
        System.out.println(this.toString());
    }
}
