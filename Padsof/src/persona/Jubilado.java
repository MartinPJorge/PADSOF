/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persona;

import java.util.Date;

/**
 *
 * @author ivan
 */
public class Jubilado extends Cliente{
    int id;
    private long numSegSocial;

    /**
     * Constructor vacío para Jubilado (necesario para la BD).
     */
    public Jubilado() {
    }

    /**
     * 
     * @param numSegSocial
     * @param nombre
     * @param apellido
     * @param DNI
     * @param dia 
     * @param mes
     * @param anio  
     */
    public Jubilado(long numSegSocial, String nombre, String apellido, String DNI, int dia, int mes, int anio) {
        super(nombre, apellido, DNI, dia, mes, anio);
        this.numSegSocial = numSegSocial;
    }

    /**
     * 
     * @return numSegSocial
     */
    public long getNumSegSocial() {
        return numSegSocial;
    }

    /**
     * 
     * @param numSegSocial
     */
    public void setNumSegSocial(long numSegSocial) {
        this.numSegSocial = numSegSocial;
    }

    @Override
    public String toString() {
        String j = super.toString() + this.numSegSocial;
        return j;
    }

    @Override
    public void mostrarDatos(){
        System.out.println(this.toString());
    }
    
}
