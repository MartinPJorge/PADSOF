/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controladores;

import GUI.Ventanas.Facturacion;
import GUI.Ventanas.Gestion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import padsof.Booking;

/**
 *
 * @author ivan
 */
public class GestionControl implements ActionListener{

    private Booking aplic;
    private Gestion vista;

    public GestionControl(Gestion vista, Booking aplic) {
        this.aplic = aplic;
        this.vista = vista;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object fuente = e.getSource();
        String textBut = ((JButton) fuente).getText();
        this.vista.cambiarVentana(this.vista.claveVentana(textBut));
    }
    
}
