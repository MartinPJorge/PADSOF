/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Ventanas;

import GUI.Controladores.AddHotelControler;
import GUI.Controladores.AddViajOrgControler;
import GUI.Controladores.AddVueloControler;
import GUI.Controladores.DatosClienteControler;
import GUI.Controladores.InicioControler;
import GUI.Controladores.LoginControler;
import GUI.Controladores.ModificarPaqueteControler;
import GUI.Controladores.NuevoPaqueteControler;
import com.sun.istack.internal.logging.Logger;
import java.awt.Container;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import javax.swing.JFrame;
import padsof.Booking;

/**
 *
 * @author Jorge
 */
public class BookingFrame extends JFrame{
    private HashMap<String,Ventana> ventanas;
    
    /**
     * Cuidado, los nombres puestos en el hashMap deben de coincidir con los que 
     * se devuelven en los m&eacute;todos 'claveVentana()' de cada una de las distintas 
     * ventanas insertadas.
     */
    public BookingFrame(Booking aplicacion) {
        super();
        
        //Nombres de ventanas
        String nomInicio = "Inicio";
        String nomLogin = "Login";
        String nomMod = "ModificarPaquete";
        String hotel = "Hotel";
        String vuelo = "Vuelo";
        String viajeOrga = "ViajeOrganizado";
        String nuevoPaq = "NuevoPaquete";
        String datosCliente = "DatosCliente";

        //Crear ventanas
        NuevoPaquete newPaq = new NuevoPaquete(this,nuevoPaq);
        DatosCliente datos = new DatosCliente(this, datosCliente);
        AddHotel hot = new AddHotel(this,hotel);
        AddViajOrg viajOrg = new AddViajOrg(this,viajeOrga);
        AddVuelo vuelos = new AddVuelo(this,vuelo);
        Login login = new Login(this, nomLogin);
        Inicio ventanaIni = new Inicio(this,nomInicio);
        ModificarPaquete paquete = new ModificarPaquete(this,nomMod);
        Container contenedor = this.getContentPane();
        
        //Especificamos los controladores
        login.setControlador(new LoginControler(login, aplicacion));
        ventanaIni.setControlador(new InicioControler(ventanaIni));
        datos.setControlador(new DatosClienteControler(datos, aplicacion));
        vuelos.setControlador(new AddVueloControler(vuelos));
        newPaq.setControlador(new NuevoPaqueteControler(newPaq, aplicacion));
        hot.setControlador(new AddHotelControler(hot, aplicacion));
        viajOrg.setControlador(new AddViajOrgControler(viajOrg, aplicacion));
        paquete.setControlador(new ModificarPaqueteControler(paquete, aplicacion));

        //Metemos las ventanas en el hashMap
        this.ventanas = new HashMap<String,Ventana>();
        this.ventanas.put(nuevoPaq, newPaq);
        this.ventanas.put(nomInicio, ventanaIni);
        this.ventanas.put(nomLogin, login);
        this.ventanas.put(nomMod, paquete);
        this.ventanas.put(vuelo, vuelos);
        this.ventanas.put(hotel, hot);
        this.ventanas.put(viajeOrga, viajOrg);
        this.ventanas.put(nuevoPaq, newPaq);
        this.ventanas.put(datosCliente, datos);
        
        //Mostramos la ventana
        this.setMinimumSize(new Dimension(400, 300));
        contenedor.add(login);
        this.setVisible(true);
        LogManager.getLogManager().reset();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public HashMap<String,Ventana> getVentanas() {
        return this.ventanas;
    }
}
