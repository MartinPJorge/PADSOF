/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Excepciones;

/**
 *
 * @author Jorge
 */
public class SinSeleccionarEx extends Exception{
    public SinSeleccionarEx() {
        super("Elige un elemento");
    }
    
    @Override
    public String toString(){
        return "Elige un elemento";
    }
}
