/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persona;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 *
 * @author e265923
 */
public class Persona {
    private int id;
    private String nombre;
    private String apellido;
    private String DNI;
    private String fechaNac;
    
    /**
     * 
     */
    public Persona() {
    }
    
    /**
     * 
     * @param nombre
     * @param apellido
     * @param DNI
     * @param fechaNac
     */
    public Persona(String nombre, String apellido, String DNI, int dia, int mes,
            int year){
        GregorianCalendar cal = new GregorianCalendar(dia, mes+1, year);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.fechaNac = sdf.format(cal.getTime());
        
        this.nombre = nombre;
        this.apellido = apellido;
        this.DNI = DNI;
    }
    
//    public Persona(int dia, int mes, int year, String nombre, String apellido, 
//            String DNI){
//        GregorianCalendar cal = new GregorianCalendar(dia, mes, year);
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        
//        this.nombre = nombre;
//        this.apellido = apellido;
//        this.DNI = DNI;
//        this.fechaNac = sdf.format(cal.getTime());
//    }
    
    
    
    /**
     * 
     * @return
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
     * @return
     */
    public String getNombre(){
        return this.nombre;
    }
    
    /**
     * 
     * @param nombre
     */
    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    
    /**
     * 
     * @return
     */
    public String getApellido(){
        return this.apellido;
    }

    
    /**
     * 
     * @param apellido
     */
    public void setApellido(String apellido){
        this.apellido=apellido;
    }
    
    /**
     * 
     * @return
     */
    public String getDNI(){
        return this.DNI;
    }
    
    /**
     * 
     * @param DNI
     */
    public void setDNI(String DNI){
        this.DNI=DNI;
    }
    
    /**
     * 
     * @return
     */
    public String getFechaNac(){
        return this.fechaNac;
    }
    
    /**
     * 
     * @param fechaNac
     */
    public void setFechaNac(String fechaNac){
        this.fechaNac = fechaNac;
    }
    
    /**
     * 
     * @return
     */
    @Override
    public String toString(){
        String p = this.nombre + this.apellido + this.DNI + this.fechaNac;
        return p;
}
    
    /**
     * 
     */
    public void mostrarDatos(){
        System.out.println(this.toString());
    }
}
