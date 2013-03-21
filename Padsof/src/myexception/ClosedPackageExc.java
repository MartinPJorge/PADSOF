/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myexception;

/**
 * Se lanzar&aacute; una excepcion de tipo ClosedPackageExc (que extiende MyException) cuando
 * se intente modificar un paquete que no est&aacute; abierto.
 * @author Ivan Marquez, Jorge Martin
 * @version 1.0
 */
public class ClosedPackageExc extends MyException{

    public ClosedPackageExc() {
        super("El paquete seleccionado se encuentra cerrado y no se puede modificar.");
    }
    
}
