/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myexception;

/**
 * Creamos la clase MyException (que extiende Exception) para recoger y controlar las excepciones 
 * que pueda lanzar nuestra aplicaci&oacute;n.
 * @author Ivan Marquez, Jorge Martin
 * @version 1.0
 */
public abstract class MyException extends Exception{
    
    public MyException(String s){
        System.out.println(s);
    }
}
