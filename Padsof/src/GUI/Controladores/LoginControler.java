/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controladores;

import GUI.Ventanas.Login;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import myexception.NoResultsExc;
import padsof.Booking;
import persona.Vendedor;

/**
 *
 * @author Jorge
 */
public class LoginControler implements ActionListener{
    private Login loginVentana;
    private Booking aplicacion;
    
    public LoginControler(Login loginVentana, Booking aplicacion) {
        this.loginVentana = loginVentana;
        this.aplicacion = aplicacion;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            //Comprobamos si los campos se han rellenado
            if(this.loginVentana.getPassword().equals("") || this.loginVentana.getId().equals("")) {
                JOptionPane.showMessageDialog(null, "Error, hay campos sin rellenar.");
                return;
            }
            
            int idUsr;
            //Obtenemos el vendedor y lo ponemos en la sesion actual
            try {
                idUsr = Integer.parseInt(this.loginVentana.getId());
            }
            catch(Exception ex) {
                JOptionPane.showMessageDialog(null, "Error, el ID de usuario debe de ser un número.");
                return;
            }
            
            /*if(idUsr == null) {
                JOptionPane.showMessageDialog(null, "Error, el ID de usuario es un número.");
                return;
            } */
            
            Vendedor vendedor = aplicacion.buscarVendedor(idUsr);
            String password = vendedor.getPassword();

            
            //Comprobamos la contrasena introducida
            if(password.equals(this.loginVentana.getPassword()) == false) {
                JOptionPane.showMessageDialog(null, "Fallo de autentificación.\nLa contraseña introducida no es válida.");
                return;
            }
            
            //Indicamos el usuario actual
            aplicacion.setSesion(aplicacion.buscarVendedor(idUsr));
            
            //Cambiamos de ventana
            String pulsado = ((JButton)e.getSource()).getText();
            this.loginVentana.cambiarVentana(this.loginVentana.claveVentana(pulsado));
            
        } catch (NoResultsExc ex) {
            JOptionPane.showMessageDialog(null, "Fallo de autentificación.\nNo se ha encontrado ningún usuario con esos datos.");
        }
    }

}
