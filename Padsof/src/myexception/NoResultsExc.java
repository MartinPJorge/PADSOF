/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myexception;

/**
 * Se lanzar&aacute; una excepcion de tipo NoResultsExc (que extiende MyException) cuando
 * se realice una buacute;squeda o consulta y esta no produzca ninguacute;n resultado.
 * @author Ivan Marquez, Jorge Martin
 * @version 1.0
 */
public class NoResultsExc extends MyException{
    
    public NoResultsExc() {
        super("La búsqueda no produjo ningún resultado.");
    }
}
