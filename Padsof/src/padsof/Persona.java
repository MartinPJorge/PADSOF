/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package padsof;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 *
 * @author e265923
 */
public class Persona {
    int id;
    private String nombre;
    private String apellido;
    private String DNI;
    private String fechaNac;
    
    public Persona() {}
    
    public Persona(int dia, int mes, int year, String nombre, String apellido, 
            String DNI){
        GregorianCalendar cal = new GregorianCalendar(dia, mes, year);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        this.nombre = nombre;
        this.apellido = apellido;
        this.DNI = DNI;
        this.fechaNac = sdf.format(cal.getTime());
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    
    public String getApellido(){
        return this.apellido;
    }
    
    public void setApellido(String apellido){
        this.apellido=apellido;
    }
    
    public String getDNI(){
        return this.DNI;
    }
    
    public void setDNI(String DNI){
        this.DNI=DNI;
    }
    
    public String getFechaNac(){
        return this.fechaNac;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setFechaNac(String fechaNac){
        this.fechaNac=fechaNac;
    }
    
    @Override
    public String toString(){
        String p = this.nombre + this.apellido + this.DNI + this.fechaNac;
        return p;
    }
    
    public void mostrarDatos(){
        System.out.println(this.toString());
    }
}
