/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import Recursos.SpringUtilities;
import Recursos.Formulario;
import Recursos.ZebraJTable;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 *
 * @author Jorge
 */
public class AddVuelo extends Ventana{
    private JPanel filtrarRes;
    private Formulario detalles;
    private JScrollPane resultados;
    private JPanel footer;
    
    public AddVuelo(BookingFrame padre, String nombre) {
        super(new SpringLayout(), nombre,padre, 300,300);
        this.iniFiltro();
        this.iniResultados();
        this.iniDetalles();
        this.footer = new FooterServicios(padre, "FooterVuelo");
        
        this.add(this.filtrarRes);
        this.add(this.resultados);
        this.add(this.detalles);
        this.add(this.footer);
        
        SpringUtilities.makeCompactGrid(this, 4, 1, 6, 6, 6, 6);
        this.setVisible(true);
    }
    
    /**
     * Inicializa la secci&oacute;n en la que mostramos los datos a filtrar.
     */
    private void iniFiltro() {
        this.filtrarRes = new JPanel();
        
        //Parte izquierda del filtro
        Formulario iz = new Formulario();
        
        JLabel origen = new JLabel("Origen:");
        JLabel dia = new JLabel("Día:");
        JLabel precio = new JLabel("Precio:");
        JTextField origenC = new JTextField(5);
        JTextField diaC = new JTextField(5);
        JTextField precioC = new JTextField(5);
        
        iz.addTexto(origen, origenC);
        iz.addTexto(dia, diaC);
        iz.addTexto(precio, precioC);
        iz.aplicarCambios();
        
        
        
        //Parte derecha del filtro
        Formulario der = new Formulario();
        
        JLabel destino = new JLabel("Destino:");
        JLabel company = new JLabel("Compañía:");
        JTextField destinoC = new JTextField(5);
        JTextField companyC = new JTextField(5);
        JLabel vacio = new JLabel("");
        JTextField vacioC = new JTextField(0);
        vacioC.setVisible(false);
        
        der.addTexto(destino, destinoC);
        der.addTexto(company, companyC);
        der.addTexto(vacio, vacioC);
        der.aplicarCambios();

        
        this.filtrarRes.add(iz);
        this.filtrarRes.add(der);
        this.filtrarRes.setBorder(BorderFactory.createTitledBorder("Filtrar resultados"));
    }
    
    /**
     * Inicializa la secci&pacute;n en la que se muestran los resultados.
     */
    private void iniResultados() {
        String[] tituloColumnas = {"Origen","Destino", "Día", "H.salida", 
            "H.llegada","Precio","Compañía"};
        String[][] valores = {{"MAD","LON","1/1/13","20:00","23:57","80","EJ"},
        {"MAD","LON","1/1/13","20:00","23:57","80","EJ"},
        {"MAD","LON","1/1/13","20:00","23:57","80","EJ"},
        {"MAD","LON","1/1/13","20:00","23:57","80","EJ"},
        {"MAD","LON","1/1/13","20:00","23:57","80","EJ"}};
        JTable tabla = new ZebraJTable(valores, tituloColumnas);
        this.resultados = new JScrollPane(tabla);
    }
    
    /**
     * Inicializa la secci&oacute;n el la que mostramos los campos para especificar 
     * los detalles.
     */
    private void iniDetalles() {
        this.detalles = new Formulario("Detalles");
        
        //Creamos los elementos
        JLabel nombre = new JLabel("Nombre:");
        JTextField nombreC = new JTextField(10);
        JLabel apellidos = new JLabel("Apellidos:");
        JTextField apellidosC = new JTextField(10);
        JLabel dni = new JLabel("DNI:");
        JTextField dniC = new JTextField(10);
        
        //Introducimos los elementos
        this.detalles.addTexto(nombre, nombreC);
        this.detalles.addTexto(apellidos, apellidosC);
        this.detalles.addTexto(dni, dniC);
        this.detalles.aplicarCambios();
    }

    @Override
    public String claveVentana(String textoBoton) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
