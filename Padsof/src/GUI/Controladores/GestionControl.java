/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controladores;

import GUI.Ventanas.Gestion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import padsof.Booking;

/**
 * Clase controladora de la Ventana Gestion
 *
 * @author Jorge Martín Pérez
 * @author Iván Márquez Pardo
 * @version 1.0
 */
public class GestionControl implements ActionListener {

    private Booking aplic;
    private Gestion vista;

    /**
     * Constructor del controlador
     *
     * @param vista
     * @param aplic
     */
    public GestionControl(Gestion vista, Booking aplic) {
        this.aplic = aplic;
        this.vista = vista;
    }

    /**
     * Método que lleva a cabo el control efectivo.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object fuente = e.getSource();
        String textBut = ((JButton) fuente).getText();
        this.vista.cambiarVentana(this.vista.claveVentana(textBut));
    }
}
