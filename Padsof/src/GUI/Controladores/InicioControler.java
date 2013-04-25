/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controladores;

import GUI.Ventanas.DatosCliente;
import GUI.Ventanas.Inicio;
import GUI.Ventanas.ModificarPaquete;
import GUI.Ventanas.Ventana;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import myexception.PermissionExc;
import padsof.Booking;

/**
 * Clase controladora de la Ventana Inicio
 *
 * @author Jorge Martín Pérez
 * @author Iván Márquez Pardo
 * @version 1.0
 */
public class InicioControler implements ActionListener{
    private Inicio ventanaInicio;
    private Booking aplicacion;
    
    public InicioControler(Inicio ventanaInicio, Booking aplicacion) {
        this.ventanaInicio = ventanaInicio;
        this.aplicacion = aplicacion;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String pulsado = ((JButton)e.getSource()).getText();
        
        //Miramos si no es admin e intenta acceder donde no puede
        if(pulsado.equals("Facturación") || pulsado.equals("Modificar márgenes") || pulsado.equals("Gestión de usuarios")) {
            try {
                if(this.aplicacion.getSesion().isAdmin() == false) {
                    JOptionPane.showMessageDialog(null, "No puedes acceder a esta sección.");
                    return;
                }
            } catch (PermissionExc ex) {
                JOptionPane.showMessageDialog(null, "Error al obtener la sesión actual.");
            }
        }
        
        
        Ventana ventana = this.ventanaInicio.cambiarVentana(this.ventanaInicio.claveVentana(pulsado));
        
        
        if(pulsado.equals("Modificar")) {
            ModificarPaquete ventanuco = (ModificarPaquete) ventana;
            ModificarPaqueteControler controlador = (ModificarPaqueteControler) ventana.getControlador();
            controlador.resetearCampos();
        }
        
        
        else if(pulsado.equals("Crear")) {
            DatosCliente ventanuco = (DatosCliente) ventana;
            DatosClienteControler controlador = (DatosClienteControler) ventanuco.getControlador();
            controlador.resetCampos();
        }
        
        else if(pulsado.equals("Salir")) {
            this.aplicacion.setSesion(null);
        }
    }
}
