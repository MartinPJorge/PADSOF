/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Excepciones;

/**
 *
 * @author Jorge
 */
public class NoEsAncianoException extends Exception{    
    public NoEsAncianoException() {}
    
    @Override
    public String toString() {
        return "Error el cliente no es de la 3ª edad.\nNo se puede llevar a cabo la operación.";
    }
}
