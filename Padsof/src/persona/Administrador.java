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
 * Clase Administrador
 *
 * @author Jorge Martin, Ivan Marquez
 * @version 1.0
 */
public class Administrador extends Vendedor {

    int id;

    /**
     * Constructor vacío para Administrador (necesario para la BD).
     */
    public Administrador() {
    }

    /**
     *
     * @param nombre
     * @param apellido
     * @param DNI
     * @param dia 
     * @param mes 
     * @param anio 
     * @param id
     * @param password
     */
    public Administrador(String nombre, String apellido, String DNI, int dia, int mes, int anio,
            int id, String password) {
        super(nombre, apellido, DNI, dia, mes, anio, id, password, id);
    }

    @Override
    public String toString() {
        String a = super.toString();
        return a;
    }

    @Override
    public void mostrarDatos() {
        System.out.println(this.toString());
    }
}
