/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persona;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Clase Persona
 *
 * @author Jorge Martin, Ivan Marquez
 * @version 1.0
 */
public class Persona {

    private int id;
    private String nombre;
    private String apellido;
    private String DNI;
    private String fechaNac;

    /**
     * Constructor vacío para Persona (necesario para la BD).
     */
    public Persona() {
    }

    /**
     *
     * @param nombre
     * @param apellido
     * @param DNI
     * @param dia
     * @param mes
     * @param year
     */
    public Persona(String nombre, String apellido, String DNI, int dia, int mes,
            int year) {
        GregorianCalendar cal = new GregorianCalendar(year, mes, dia);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.fechaNac = sdf.format(cal.getTime());

        this.nombre = nombre;
        this.apellido = apellido;
        this.DNI = DNI;
    }

    /**
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return nombre
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return apellido
     */
    public String getApellido() {
        return this.apellido;
    }

    /**
     *
     * @param apellido
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     *
     * @return DNI
     */
    public String getDNI() {
        return this.DNI;
    }

    /**
     *
     * @param DNI
     */
    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    /**
     *
     * @return fechaNac
     */
    public String getFechaNac() {
        return this.fechaNac;
    }

    /**
     *
     * @param fechaNac
     */
    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    /**
     *
     * @return String con info de Persona
     */
    @Override
    public String toString() {
        String p = this.nombre + this.apellido + this.DNI + this.fechaNac;
        return p;
    }

    /**
     * Muestra los datos de Persona por pantalla.
     */
    public void mostrarDatos() {
        System.out.println(this.toString());
    }
}
