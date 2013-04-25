/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controladores;

import GUI.Ventanas.Margenes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import padsof.Booking;
import reserva.ReservaHotel;
import reserva.ReservaViajOrg;
import reserva.ReservaVuelo;

/**
 * Clase controladora de la Ventana Margenes
 *
 * @author Jorge Martín Pérez
 * @author Iván Márquez Pardo
 * @version 1.0
 */
public class MargenesControl implements ActionListener {

    private Booking aplic;
    private Margenes vista;

    /**
     * Constructor del controlador
     *
     * @param vista
     * @param aplic
     */
    public MargenesControl(Margenes vista, Booking aplic) {
        this.aplic = aplic;
        this.vista = vista;

        this.vista.getmHoteles().setText(String.valueOf(ReservaHotel.getMargen()));
        this.vista.getmViajes().setText(String.valueOf(ReservaViajOrg.getMargen()));
        this.vista.getmVuelos().setText(String.valueOf(ReservaVuelo.getMargen()));
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
        if (fuente == vista.getAtras()) {
            this.vista.cambiarVentana(this.vista.claveVentana(textBut));
        } else if (fuente == vista.getModify()) {
            try {
                String margHotel = vista.getmHoteles().getText();
                String margViaje = vista.getmViajes().getText();
                String margVuelo = vista.getmVuelos().getText();

                Double mH = Double.parseDouble(margHotel);
                Double mVI = Double.parseDouble(margViaje);
                Double mVO = Double.parseDouble(margVuelo);

                ReservaHotel.setMargenSQL(mH, "admin", this.aplic.getBookingDBName());
                ReservaViajOrg.setMargenSQL(mVI, "admin", this.aplic.getBookingDBName());
                ReservaVuelo.setMargenSQL(mVO, "admin", this.aplic.getBookingDBName());

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MargenesControl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(MargenesControl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
