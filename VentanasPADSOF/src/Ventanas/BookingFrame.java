/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;

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
    public BookingFrame() {
        super();
        
        //Nombres de ventanas
        String nomInicio = "Inicio";
        String nomLogin = "Login";
        String nomMod = "ModificarPaquete";
        String hotel = "Hotel";
        String vuelo = "Vuelo";
        String viajeOrga = "ViajeOrganizado";
        String nuevoPaq = "NuevoPaquete";

        //Crear ventanas
        NuevoPaquete newPaq = new NuevoPaquete(this,nuevoPaq);
        DatosCliente datos = new DatosCliente();
        AddHotel hot = new AddHotel(this,hotel);
        AddViajOrg viajOrg = new AddViajOrg(this,viajeOrga);
        AddVuelo vuelos = new AddVuelo(this,vuelo);
        Login login = new Login(this, nomLogin);
        Inicio ventanaIni = new Inicio(this,nomInicio);
        ModificarPaquete paquete = new ModificarPaquete(this,nomMod);
        Container contenedor = this.getContentPane();

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
        
        //Mostramos la ventana
        this.setMinimumSize(new Dimension(400, 300));
        contenedor.add(login);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public HashMap<String,Ventana> getVentanas() {
        return this.ventanas;
    }
}
