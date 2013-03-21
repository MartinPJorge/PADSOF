/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myexception;

/**
 * Se lanzar&aacute; una excepcion de tipo FailedLoginExc (que extiende MyException) cuando
 * se produzca un fallo al autentificarse para entrar a la aplicaci&oacute;n.
 * @author Ivan Marquez, Jorge Martin
 * @version 1.0
 */
public class FailedLoginExc extends MyException{

    public FailedLoginExc() {
        super("El usuario o la contraseña introducidos son incorrectos.");
    }
    
}
