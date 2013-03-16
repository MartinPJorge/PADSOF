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
    private long numSegSocial;

    /**
     * 
     */
    public Jubilado() {
    }

    /**
     * 
     * @param numSegSocial
     * @param nombre
     * @param apellido
     * @param DNI
     * @param fechaNac
     */
    public Jubilado(long numSegSocial, String nombre, String apellido, String DNI, Date fechaNac) {
        super(nombre, apellido, DNI, fechaNac);
        this.numSegSocial = numSegSocial;
    }

    /**
     * 
     * @return
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
