/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Excepciones;

/**
 *
 * @author Jorge
 */
public class FechaInvalidaEx extends Exception{
    public FechaInvalidaEx() {
        super("La fecha introducida no respeta el formato:\ndd/mm/yyyy");
    }
    
    @Override
    public String toString() {
        return "La fecha introducida no respeta el formato:\ndd/mm/yyyy";
    }
}
