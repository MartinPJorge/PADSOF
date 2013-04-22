/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controladores;

import GUI.Ventanas.Inicio;
import GUI.Ventanas.ModificarPaquete;
import GUI.Ventanas.Ventana;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import padsof.Booking;

/**
 *
 * @author Jorge
 */
public class InicioControler implements ActionListener{
    private Inicio ventanaInicio;
    
    public InicioControler(Inicio ventanaInicio) {
        this.ventanaInicio = ventanaInicio;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String pulsado = ((JButton)e.getSource()).getText();
        
        Ventana ventana = this.ventanaInicio.cambiarVentana(this.ventanaInicio.claveVentana(pulsado));
        if(pulsado.equals("Modificar")) {
            ModificarPaquete ventanuco = (ModificarPaquete) ventana;
            ModificarPaqueteControler controlador = (ModificarPaqueteControler) ventana.getControlador();
            controlador.resetearCampos();
        }
    }
}
