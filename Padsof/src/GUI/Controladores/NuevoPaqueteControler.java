/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controladores;

import GUI.Ventanas.AddVuelo;
import GUI.Ventanas.NuevoPaquete;
import GUI.Ventanas.Ventana;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import padsof.Booking;

/**
 *
 * @author Jorge
 */
public class NuevoPaqueteControler implements ActionListener{
    private NuevoPaquete ventana;
    private Booking aplicacion;
    
    public NuevoPaqueteControler(NuevoPaquete ventana, Booking aplicacion) {
        this.ventana = ventana;
        this.aplicacion = aplicacion;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String pulsado = ((JButton)e.getSource()).getText();
        
        Ventana nuevaVen = this.ventana.cambiarVentana(this.ventana.claveVentana(pulsado));
        
        //En las nuevas ventanas les damos el paquete
        if(pulsado.equals(this.ventana.getAddVuelo().getText())) {
            ((AddVuelo)nuevaVen).setCurrentPaq(this.ventana.getPaqActual());
        }
    }
    
}
