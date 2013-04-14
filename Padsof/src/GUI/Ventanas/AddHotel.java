/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Ventanas;

import GUI.Recursos.SpringUtilities;
import GUI.Recursos.Formulario;
import GUI.Recursos.MiModeloTabla;
import GUI.Recursos.ZebraJTable;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 *
 * @author Jorge
 */
public class AddHotel extends Ventana {
    private JPanel filtrarRes;
    private JPanel detalles;
    private JScrollPane resultados;
    private FooterServicios footer;
    

    public AddHotel(BookingFrame padre, String nombre) {
        super(new SpringLayout(), nombre,padre,300,300);
        this.iniFiltro();
        this.resultados();
        this.iniDetalles();
        this.footer = new FooterServicios(padre,"FooterHotel");
        this.add(footer);
        
        SpringUtilities.makeCompactGrid(this, 4, 1, 6, 6, 6, 6);
    }
    
    /**
     * Inicializa la secci&oacute;n en la que se muestran los campos para filtrar.
     */
    private void iniFiltro() {
        this.filtrarRes = new JPanel();
        JLabel ciudad = new JLabel("Ciudad:");
        JLabel tipoHabitacion = new JLabel("Habitación:");
        JLabel precioNoche = new JLabel("Precio noche:");
        JLabel estrellas = new JLabel("Estrellas:");
        
        //Creamos los campos
        JTextField ciudadC = new JTextField();
        String[] tiposHab = {"Individual", "Matrimonio", "Triple", "Suite"};
        JComboBox tipoHabC = new JComboBox(tiposHab);
        JTextField precioNocheC = new JTextField();
        Integer[] estrellasI = {1,2,3,4,5};
        JComboBox estrellasC = new JComboBox(estrellasI);
        
        //Creamos los formularios
        Formulario iz = new Formulario();
        Formulario der = new Formulario();
        
        
        //Introducimos los campos
        iz.addTexto(ciudad, ciudadC);
        iz.addTexto(tipoHabitacion, tipoHabC);
        der.addTexto(precioNoche, precioNocheC);
        der.addTexto(estrellas, estrellasC);
        
        //Metemos el panel en la ventana
        iz.aplicarCambios();
        der.aplicarCambios();
        this.filtrarRes.add(iz);
        this.filtrarRes.add(der);
        this.filtrarRes.setBorder(BorderFactory.createTitledBorder("Filtrar resultados"));
        
        this.add(this.filtrarRes);
    }
    
    /**
     * Inicializa la tabla de resultados.
     */
    private void resultados() {
        String[] titulos = {"Nombre", "Ciudad", "Estrellas", "Habitación", 
                            "Noche(€)", "Desayunp Incl."};
        Object[][] resus = {{"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"},
        {"NH Valencia", "Valencia", "5", "Doble", "23.5", "Si"}};
        JTable tablaRes = new ZebraJTable(new  MiModeloTabla(titulos, resus, -1));
        this.resultados = new JScrollPane(tablaRes);
        
        this.add(resultados);
    }
    
    /**
     * Inicializa la secci&oacute;n en la que se muestran los detalles a elegir.
     */
    private void iniDetalles() {
        this.detalles = new JPanel();
        JLabel comidas = new JLabel("Comidas:");
        JLabel entrada = new JLabel("Entrada:");
        JLabel dias = new JLabel("Días:");
        JTextField entradaC = new JTextField(5);
        entradaC.setToolTipText("dd/mm/yyyy");
        JTextField diasC = new JTextField(5);
        
        
        //Radio botones
        JPanel radioBot = new JPanel(new SpringLayout());
        ButtonGroup botones = new ButtonGroup();
        JRadioButton opcion1 = new JRadioButton("Media pensión");
        JRadioButton opcion2 = new JRadioButton("Pensión completa");
        JRadioButton opcion3 = new JRadioButton("Desayuno");
        botones.add(opcion1);
        botones.add(opcion2);
        botones.add(opcion3);
        radioBot.add(comidas);
        radioBot.add(opcion1);
        radioBot.add(opcion2);
        radioBot.add(opcion3);
        SpringUtilities.makeCompactGrid(radioBot, 4, 1, 6, 6, 6, 6);
        
        //Parte de la derecha
        Formulario derecha = new Formulario();
        derecha.addTexto(entrada, entradaC);
        derecha.addTexto(dias, diasC);
        derecha.aplicarCambios();
        
        //Introducimos las 2 partes del panel
        this.detalles.setBorder(BorderFactory.createTitledBorder("Detalles"));
        this.detalles.add(radioBot);
        this.detalles.add(derecha);
        this.add(this.detalles);
    }

    @Override
    public String claveVentana(String textoBoton) {
        return "NuevoPaquete";
    }
}
