/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controladores;

import GUI.Excepciones.FechaInvalidaEx;
import GUI.Excepciones.NoEsAncianoException;
import GUI.Recursos.DateValidator;
import GUI.Ventanas.DatosCliente;
import GUI.Ventanas.NuevoPaquete;
import GUI.Ventanas.Ventana;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import myexception.NoResultsExc;
import padsof.Booking;
import persona.Cliente;
import reserva.Paquete;

/**
 *
 * @author Jorge
 */
public class DatosClienteControler implements ActionListener{
    private DatosCliente ventana;
    private Booking aplicacion;
    
    public DatosClienteControler(DatosCliente loginVentana, Booking aplicacion) {
        this.ventana = loginVentana;
        this.aplicacion = aplicacion;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JTextField DNIreg = (JTextField)this.ventana.getRegistradoCliente().getTextos().get(0);
        Cliente c = null;
        
        //Nuevo cliente
        if(DNIreg.getText().equals("")) {
            try {
                c = registrarCliente();
            } catch (    FechaInvalidaEx | NoEsAncianoException ex) {
                JOptionPane.showMessageDialog(null, ex);
                return;
            }

        }
        //Cliento registrado
        else {
            try {
                c = this.aplicacion.buscarCliente(DNIreg.getText());
            } catch (NoResultsExc ex) {
                JOptionPane.showMessageDialog(null, "Fallo de autentificación.\nNo se ha encontrado ningún usuario con esos datos.");
                return;
            }
        }
        
        //Cambiamos de ventana
        String pulsado = ((JButton)e.getSource()).getText();
        NuevoPaquete nuevaVentana = (NuevoPaquete)this.ventana.cambiarVentana(this.ventana.claveVentana(pulsado));
        nuevaVentana.setClaveVentanaAnt("Inicio");
        
        //Creamos el nuevo paquete
        int idMax = this.aplicacion.maxIdPaquete();
        Paquete paquete = new Paquete(idMax, 1, c.getDNI(), this.aplicacion.getSesion().getId());
        nuevaVentana.setPaqActual(paquete);
        nuevaVentana.actualizarEncabezado();
        
        nuevaVentana.mostrarInfo();
    }

    private Cliente registrarCliente() throws FechaInvalidaEx, NoEsAncianoException {
        //ArrayList<Component> componentes = this.ventana.getNuevoCliente().getTextos();
        JTextField nombre = (JTextField)this.ventana.getNuevoCliente().getTextos().get(0);
        JTextField apellido = (JTextField)this.ventana.getNuevoCliente().getTextos().get(1);
        JTextField dni = (JTextField)this.ventana.getNuevoCliente().getTextos().get(2);
        JTextField fNacimiento = (JTextField)this.ventana.getNuevoCliente().getTextos().get(3);
        String fNac = fNacimiento.getText();
        
        //Comprobamos que la fecha introducida coincida con el formato
        try {
            comprobarFecha(fNacimiento.getText()); 
        } catch (FechaInvalidaEx ex) {
            throw ex;
        }
        
        //Si esta seleccionada la casilla de 3ªedad vemos si es un jubilado
        int year = DateValidator.obtainYear(fNac);
        if(this.ventana.getEdad3().isEnabled()) {
            if(Calendar.getInstance().get(Calendar.YEAR) - year < 65) {
                throw new NoEsAncianoException();
            }
        }
        
        //Creamos el cliente y lo registramos
        Cliente cliente =  new Cliente(nombre.getText(), apellido.getText(), dni.getText(),
                DateValidator.obtainDay(fNac), DateValidator.obtainMonth(fNac),
                DateValidator.obtainYear(fNac));
        
        this.aplicacion.registrarCliente(nombre.getText(), apellido.getText(), dni.getText(),
                DateValidator.obtainDay(fNac), DateValidator.obtainMonth(fNac),
                DateValidator.obtainYear(fNac));
        
        return cliente;
    }
    
    private void comprobarFecha(String fecha) throws FechaInvalidaEx {
        DateValidator validator = new DateValidator();
        
        if(validator.validate(fecha) == false) {
            throw new FechaInvalidaEx();
        }
    }
}
