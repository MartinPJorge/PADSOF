/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Excepciones;

/**
 *
 * @author Jorge
 */
public class SinRellenarEx extends Exception{
    public SinRellenarEx() {
        super("Faltan campos por rellenar");
    }
    
    @Override
    public String toString() {
        return "Faltan campos por rellenar";
    }
}
