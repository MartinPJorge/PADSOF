/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controladores;

import GUI.Recursos.DateValidator;
import GUI.Ventanas.Login;
import cat.quickdb.db.AdminBase;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import myexception.NoResultsExc;
import padsof.Booking;
import persona.Vendedor;
import reserva.Paquete;
import reserva.Reserva;

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
            
            //Comprobamos si hay paquetes que cerrar
            comprobarPaquetes();
            
            //Cambiamos de ventana
            String pulsado = ((JButton)e.getSource()).getText();
            this.loginVentana.cambiarVentana(this.loginVentana.claveVentana(pulsado));
            
        } catch (NoResultsExc ex) {
            JOptionPane.showMessageDialog(null, "Fallo de autentificación.\nNo se ha encontrado ningún usuario con esos datos.");
        }
    }
    
    
    /**
     * Se encarga de cerrar los paquetes cuya fecha de inicio haya expirado.
     */
    public void comprobarPaquetes() {
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.aplicacion.getBookingDBName());
        Date now = new Date();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy/dd/MM");
        String ahora = formater.format(now);
        ahora = "" + DateValidator.obtainDayV2(ahora) + "/" + DateValidator.obtainMonthV2(ahora) + 
                "/" +DateValidator.obtainYearV2(ahora);
        
        //Obtenemos los ids de los paquetes
        Object[] filas = admin.obtainJoin("SELECT id FROM Paquete WHERE 1 = 1", 1);
        List<Integer> ids = new ArrayList<Integer>();
        for(Object fila : filas) {
            Object[] filaAct = (Object []) fila;
            ids.add(Integer.parseInt((String) filaAct[0]));
        }
        
        //Obtenemos los paquetes
        List<Paquete> paquetes = new ArrayList<Paquete>();
        for(Integer id : ids) {
            Paquete paqAct = new Paquete();
            admin.obtain(paqAct, "id = " + id);
            paqAct.cargarEstadoReservas(admin);
            paquetes.add(paqAct);
        }
        
        
        
        //Cancelamos los paquetes que esten por detras de hoy
        for(Paquete paq : paquetes) {
            if(paq.getAbierto() != 0) {
                Reserva resCercana = paq.obtenerPrimeraReserva();
                if(DateValidator.compareDates(resCercana.getFechaInicio(), ahora) < 0) {
                    admin = paq.actualizarEstado(admin, "cancelado");
                    JOptionPane.showMessageDialog(null, "El paquete "+paq.getId()+" se ha cerrado debido a que alguna de sus reservas a expirado.");
                }
            }
        }
        
        admin.close();
    }
}
