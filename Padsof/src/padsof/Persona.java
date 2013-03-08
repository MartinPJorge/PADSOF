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
public class Persona {
    private String nombre;
    private String apellido;
    private String DNI;
    private Date fechaNac;
    
    public Persona(String nombre, String apellido, String DNI, Date fechaNac){
        this.nombre=nombre;
        this.apellido=apellido;
        this.DNI=DNI;
        this.fechaNac=fechaNac;
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
    
    public Date getFechaNac(){
        return this.fechaNac;
    }
    
    public void setFechaNac(Date fechaNac){
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
