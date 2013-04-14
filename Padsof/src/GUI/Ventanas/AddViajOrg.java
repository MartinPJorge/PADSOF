/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Ventanas;

import GUI.Recursos.SpringUtilities;
import GUI.Recursos.Formulario;
import GUI.Recursos.ZebraJTable;
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
public class AddViajOrg extends Ventana{
    private Formulario filtro;
    private JScrollPane resultados;
    private Formulario detalles;
    private FooterServicios footer;
    
    public AddViajOrg(BookingFrame padre, String nombre) {
        super(new SpringLayout(), nombre, padre, 300,300);
        this.iniFiltro();
        this.iniResultados();
        this.iniDetalles();
        this.footer = new FooterServicios(padre,"FooterVO");
        
        this.add(this.filtro);
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
        this.filtro = new Formulario("Filtrar resultados");
        
        //Creamos los elementos
        JLabel precio = new JLabel("Precio:");
        JLabel noches = new JLabel("Noches:");
        JTextField precioC = new JTextField(5);
        JTextField nochesC = new JTextField(5);
        
        //Introducimos los elementos
        this.filtro.addTexto(precio, precioC);
        this.filtro.addTexto(noches, nochesC);
        
        this.filtro.aplicarCambios();
    }
    
    /**
     * Inicializa la secci&pacute;n en la que se muestran los resultados.
     */
    private void iniResultados() {
        String[] tituloColumnas = {"Nombre","Compañía", "Teléfono", "Precio", 
            "Días", "Noches","Fechas salida","Localidad salida","Localidades","Descripción"};
        String[][] valores = {{"Ibiza","Halcón Viajes","912182128","251","6","5","Todos los lunes de Enero-Mayo y Octubre-Diciembre. Todos los dÌas de Junio a Septiembre","Madrid","Ibiza","Hotel Sol Pinet Playa 3* en Media Pensión."},
        {"Ibiza","Halcón Viajes","912182128","251","6","5","Todos los lunes de Enero-Mayo y Octubre-Diciembre. Todos los dÌas de Junio a Septiembre","Madrid","Ibiza","Hotel Sol Pinet Playa 3* en Media Pensión."},
        {"Ibiza","Halcón Viajes","912182128","251","6","5","Todos los lunes de Enero-Mayo y Octubre-Diciembre. Todos los dÌas de Junio a Septiembre","Madrid","Ibiza","Hotel Sol Pinet Playa 3* en Media Pensión."},
        {"Ibiza","Halcón Viajes","912182128","251","6","5","Todos los lunes de Enero-Mayo y Octubre-Diciembre. Todos los dÌas de Junio a Septiembre","Madrid","Ibiza","Hotel Sol Pinet Playa 3* en Media Pensión."},
        {"Ibiza","Halcón Viajes","912182128","251","6","5","Todos los lunes de Enero-Mayo y Octubre-Diciembre. Todos los dÌas de Junio a Septiembre","Madrid","Ibiza","Hotel Sol Pinet Playa 3* en Media Pensión."},
        {"Ibiza","Halcón Viajes","912182128","251","6","5","Todos los lunes de Enero-Mayo y Octubre-Diciembre. Todos los dÌas de Junio a Septiembre","Madrid","Ibiza","Hotel Sol Pinet Playa 3* en Media Pensión."},
        {"Ibiza","Halcón Viajes","912182128","251","6","5","Todos los lunes de Enero-Mayo y Octubre-Diciembre. Todos los dÌas de Junio a Septiembre","Madrid","Ibiza","Hotel Sol Pinet Playa 3* en Media Pensión."},
        {"Ibiza","Halcón Viajes","912182128","251","6","5","Todos los lunes de Enero-Mayo y Octubre-Diciembre. Todos los dÌas de Junio a Septiembre","Madrid","Ibiza","Hotel Sol Pinet Playa 3* en Media Pensión."},
        {"Ibiza","Halcón Viajes","912182128","251","6","5","Todos los lunes de Enero-Mayo y Octubre-Diciembre. Todos los dÌas de Junio a Septiembre","Madrid","Ibiza","Hotel Sol Pinet Playa 3* en Media Pensión."},
        {"Ibiza","Halcón Viajes","912182128","251","6","5","Todos los lunes de Enero-Mayo y Octubre-Diciembre. Todos los dÌas de Junio a Septiembre","Madrid","Ibiza","Hotel Sol Pinet Playa 3* en Media Pensión."},
        {"Ibiza","Halcón Viajes","912182128","251","6","5","Todos los lunes de Enero-Mayo y Octubre-Diciembre. Todos los dÌas de Junio a Septiembre","Madrid","Ibiza","Hotel Sol Pinet Playa 3* en Media Pensión."},
        {"Ibiza","Halcón Viajes","912182128","251","6","5","Todos los lunes de Enero-Mayo y Octubre-Diciembre. Todos los dÌas de Junio a Septiembre","Madrid","Ibiza","Hotel Sol Pinet Playa 3* en Media Pensión."},
        {"Ibiza","Halcón Viajes","912182128","251","6","5","Todos los lunes de Enero-Mayo y Octubre-Diciembre. Todos los dÌas de Junio a Septiembre","Madrid","Ibiza","Hotel Sol Pinet Playa 3* en Media Pensión."},
        {"Ibiza","Halcón Viajes","912182128","251","6","5","Todos los lunes de Enero-Mayo y Octubre-Diciembre. Todos los dÌas de Junio a Septiembre","Madrid","Ibiza","Hotel Sol Pinet Playa 3* en Media Pensión."},
        {"Ibiza","Halcón Viajes","912182128","251","6","5","Todos los lunes de Enero-Mayo y Octubre-Diciembre. Todos los dÌas de Junio a Septiembre","Madrid","Ibiza","Hotel Sol Pinet Playa 3* en Media Pensión."},
        {"Ibiza","Halcón Viajes","912182128","251","6","5","Todos los lunes de Enero-Mayo y Octubre-Diciembre. Todos los dÌas de Junio a Septiembre","Madrid","Ibiza","Hotel Sol Pinet Playa 3* en Media Pensión."},
        {"Ibiza","Halcón Viajes","912182128","251","6","5","Todos los lunes de Enero-Mayo y Octubre-Diciembre. Todos los dÌas de Junio a Septiembre","Madrid","Ibiza","Hotel Sol Pinet Playa 3* en Media Pensión."},
        {"Ibiza","Halcón Viajes","912182128","251","6","5","Todos los lunes de Enero-Mayo y Octubre-Diciembre. Todos los dÌas de Junio a Septiembre","Madrid","Ibiza","Hotel Sol Pinet Playa 3* en Media Pensión."},
        {"Ibiza","Halcón Viajes","912182128","251","6","5","Todos los lunes de Enero-Mayo y Octubre-Diciembre. Todos los dÌas de Junio a Septiembre","Madrid","Ibiza","Hotel Sol Pinet Playa 3* en Media Pensión."},
        {"Ibiza","Halcón Viajes","912182128","251","6","5","Todos los lunes de Enero-Mayo y Octubre-Diciembre. Todos los dÌas de Junio a Septiembre","Madrid","Ibiza","Hotel Sol Pinet Playa 3* en Media Pensión."},
        {"Ibiza","Halcón Viajes","912182128","251","6","5","Todos los lunes de Enero-Mayo y Octubre-Diciembre. Todos los dÌas de Junio a Septiembre","Madrid","Ibiza","Hotel Sol Pinet Playa 3* en Media Pensión."}};
        JTable tabla = new ZebraJTable(valores, tituloColumnas);
        this.resultados = new JScrollPane(tabla);
    }
    
    /**
     * Inicializa la secci&oacute;n el la que mostramos los campos para especificar 
     * los detalles.
     */
    private void iniDetalles() {
        this.detalles = new Formulario("Detalles");
        JLabel personas = new JLabel("Personas:");
        JTextField personasC = new JTextField(2);
        this.detalles.addTexto(personas, personasC);
        this.detalles.aplicarCambios();
    }

    @Override
    public String claveVentana(String textoBoton) {
        return "NuevoPaquete";
    }
}
