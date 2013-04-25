/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Ventanas;

import GUI.Recursos.Formulario;
import GUI.Recursos.SpringUtilities;
import GUI.Recursos.ZebraJTable;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.table.TableColumnModel;
import reserva.Paquete;

/**
 *
 * @author Jorge
 */
public class AddHotel extends Ventana {
    private JPanel filtrarRes;
    private JPanel detalles;
    private JScrollPane resultados;
    private FooterServicios footer;
    private JButton filtar;
    private ButtonGroup botones;
    private List<JRadioButton> listBotones;
    private JTextField entrada;
    private JTextField dias;
    private JTextField ciudad;
    private JTextField precioNoche;
    private JComboBox tipoHab;
    private JComboBox estrellas;
    private Paquete currentPaq;
    private JButton calcular;

    /**
     *
     * @param padre
     * @param nombre
     */
    public AddHotel(BookingFrame padre, String nombre) {
        super(new SpringLayout(), nombre,padre,650,650);
        this.iniFiltro();
        this.resultados();
        this.ajustarTamCols();
        this.iniDetalles();
        this.footer = new FooterServicios(padre,"FooterHotel");
        
        this.calcular = new JButton("Calcular");
        JPanel panCalcula = new JPanel(new GridBagLayout());
        panCalcula.add(this.calcular);
        
        this.add(panCalcula);
        this.add(footer);
        
        SpringUtilities.makeCompactGrid(this, 5, 1, 6, 6, 6, 6);
    }
    
    /**
     * Inicializa la secci&oacute;n en la que se muestran los campos para filtrar.
     */
    private void iniFiltro() {
        JPanel campos = new JPanel();
        JPanel panBoton = new JPanel(new GridBagLayout());
        this.filtar = new JButton("Buscar");
        this.filtrarRes = new JPanel(new SpringLayout());
        JLabel ciudad = new JLabel("Ciudad:");
        JLabel tipoHabitacion = new JLabel("Habitación:");
        JLabel precioNoche = new JLabel("Precio noche:");
        JLabel estrellas = new JLabel("Estrellas:");
        
        //Creamos los campos
        this.ciudad = new JTextField();
        String[] tiposHab = {"Individual", "Matrimonio", "Triple"};
        this.tipoHab = new JComboBox(tiposHab);
        this.precioNoche = new JTextField();
        Integer[] estrellasI = {1,2,3,4,5};
        this.estrellas = new JComboBox(estrellasI);
        
        //Creamos los formularios
        Formulario iz = new Formulario();
        Formulario der = new Formulario();
        
        
        //Introducimos los campos
        iz.addTexto(ciudad, this.ciudad);
        iz.addTexto(tipoHabitacion, this.tipoHab);
        der.addTexto(precioNoche, this.precioNoche);
        der.addTexto(estrellas, this.estrellas);
        
        //Metemos el panel en la ventana
        iz.aplicarCambios();
        der.aplicarCambios();
        campos.add(iz);
        campos.add(der);
        
        //Metemos los elementos
        panBoton.add(this.filtar);
        this.filtrarRes.add(campos);
        this.filtrarRes.add(panBoton);
        this.filtrarRes.setBorder(BorderFactory.createTitledBorder("Filtrar resultados"));
        
        SpringUtilities.makeCompactGrid(this.filtrarRes, 2, 1, 6, 6, 6, 6);
        
        this.add(this.filtrarRes);
    }
    
    /**
     * Inicializa la tabla de resultados.
     */
    private void resultados() {
        String[] titulos = {"Nombre", "País","Ciudad", "★★★", "Simple", "Doble", 
                            "Triple", "Desayuno", "M.P", "P.C", "Características"};
        JTable tablaRes = new ZebraJTable(null, titulos, -1);
        this.resultados = new JScrollPane(tablaRes);
        
        this.add(resultados);
    }
    
    /**
     * Inicializa la secci&oacute;n en la que se muestran los detalles a elegir.
     */
    private void iniDetalles() {
        this.detalles = new JPanel(new SpringLayout());
        JLabel comidas = new JLabel("Comidas:");
        JLabel entrada = new JLabel("Entrada:");
        JLabel dias = new JLabel("Días:");
        this.entrada = new JTextField(5);
        this.entrada.setToolTipText("dd/mm/yyyy");
        this.dias = new JTextField(5);
        JPanel datosRellenar = new JPanel();
        
        
        //Radio botones
        this.listBotones = new ArrayList<>();
        JPanel radioBot = new JPanel(new SpringLayout());
        this.botones = new ButtonGroup();
        JRadioButton opcion1 = new JRadioButton("Media pensión");
        JRadioButton opcion2 = new JRadioButton("Pensión completa");
        JRadioButton opcion3 = new JRadioButton("Desayuno");
        this.botones.add(opcion1); this.listBotones.add(opcion1);
        this.botones.add(opcion2); this.listBotones.add(opcion2);
        this.botones.add(opcion3); this.listBotones.add(opcion3);
        radioBot.add(comidas);
        radioBot.add(opcion1);
        radioBot.add(opcion2);
        radioBot.add(opcion3);
        SpringUtilities.makeCompactGrid(radioBot, 4, 1, 6, 6, 6, 6);
        
        //Parte de la derecha
        Formulario derecha = new Formulario();
        derecha.addTexto(entrada, this.entrada);
        derecha.addTexto(dias, this.dias);
        derecha.aplicarCambios();
        
        //Introducimos las 2 partes del panel
        datosRellenar.add(radioBot);
        datosRellenar.add(derecha);
        
        this.detalles.setBorder(BorderFactory.createTitledBorder("Detalles"));
        this.detalles.add(datosRellenar);
        this.add(this.detalles);
        
        SpringUtilities.makeGrid(this.detalles, 1, 1, 6, 6, 6, 6);
    }
    
    /**
     * Ajusta el tama&ntilde;o de las columnas de la tabla de b&uacute;squeda.
     */
    public void ajustarTamCols() {
        ZebraJTable tabla = (ZebraJTable) this.resultados.getViewport().getView();
        TableColumnModel modelCol = tabla.getColumnModel();
        
        modelCol.getColumn(0).setMinWidth(100);
    }

    @Override
    public String claveVentana(String textoBoton) {
        return "NuevoPaquete";
    }

    /**
     *
     * @return el bot&oacute;n de filtrar
     */
    public JButton getFiltar() {
        return filtar;
    }

    /**
     *
     * @return el campo de texto de la ciudad
     */
    public JTextField getCiudad() {
        return ciudad;
    }

    /**
     *
     * @return el campo de texto del num. de noches
     */
    public JTextField getPrecioNoche() {
        return precioNoche;
    }

    /**
     *
     * @return el comboBox del tipo de habitaci&oacute;n
     */
    public JComboBox getTipoHab() {
        return tipoHab;
    }

    /**
     *
     * @return el comboBox de las estrellas del hotel
     */
    public JComboBox getEstrellas() {
        return estrellas;
    }

    /**
     *
     * @return el scroll panel de los resultados de al b&uacute;squeda
     */
    public JScrollPane getResultados() {
        return resultados;
    }

    /**
     *
     * @return el footer
     */
    public FooterServicios getFooter() {
        return footer;
    }

    /**
     *
     * @return el grupo de radio botones
     */
    public ButtonGroup getBotones() {
        return botones;
    }

    /**
     *
     * @return la lista de radio botones
     */
    public List<JRadioButton> getListBotones() {
        return listBotones;
    }

    /**
     *
     * @return el campo de texto de la entrada
     */
    public JTextField getEntrada() {
        return entrada;
    }

    /**
     *
     * @return el campo de texto de los d&iacute;as
     */
    public JTextField getDias() {
        return dias;
    }
    
    /**
     *
     * @return el paquete actual
     */
    public Paquete getCurrentPaq() {
        return currentPaq;
    }

    /**
     *
     * @param currentPaq
     */
    public void setCurrentPaq(Paquete currentPaq) {
        this.currentPaq = currentPaq;
    }

    /**
     *
     * @return el bot&oacute;n de calcular el precio
     */
    public JButton getCalcular() {
        return calcular;
    }
    
    
    
    /**
     * Especifica el controlador a usar por la ventana de a&ntilde;adir hoteles.
     * @param controlador 
     */
    public void setControlador(ActionListener controlador) {
        this.controlador = controlador;
        this.footer.getAdd().addActionListener(controlador);
        this.footer.getVolver().addActionListener(controlador);
        this.filtar.addActionListener(controlador);
        this.calcular.addActionListener(controlador);
    }
}
