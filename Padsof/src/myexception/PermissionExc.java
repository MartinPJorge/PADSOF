/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myexception;

/**
 * Se lanzar&aacute; una excepcion de tipo FailedLoginExc (que extiende MyException) cuando
 * se intente realizar una operacioacute;n para la que no tenemos permisos/privilegios.
 * @author Ivan Marquez, Jorge Martin
 * @version 1.0
 */
public class PermissionExc extends MyException{

    public PermissionExc() {
        super("No tiene permisos suficientes para llevar a cabo esta acción.");
    }

    
}
