/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Ventanas;

import GUI.Recursos.SpringUtilities;
import GUI.Recursos.ZebraJTable;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import reserva.Paquete;

/**
 *
 * @author Jorge
 */
public class AddViajOrg extends Ventana{
    private JTextField precio;
    private JTextField noches;
    private JTextField personas;
    private JTextField fecha;
    private JScrollPane resultados;
    private FooterServicios footer;
    private JButton buscar;
    private JButton calcular;
    private JComboBox tipoViaje;
    private Paquete currPaq;
    
    public AddViajOrg(BookingFrame padre, String nombre) {
        super(new SpringLayout(), nombre, padre, 300,300);
        this.iniResultados();
        JPanel abajo = this.iniDetalles();
        this.footer = new FooterServicios(padre,"FooterVO");
        
        this.add(this.iniFiltro());
        this.add(this.resultados);
        this.add(abajo);
        this.add(this.footer);
        
        SpringUtilities.makeCompactGrid(this, 4, 1, 6, 6, 6, 6);
        this.setVisible(true);
    }
    
    /**
     * Inicializa la secci&oacute;n en la que mostramos los datos a filtrar.
     */
    private JPanel iniFiltro() {
        JPanel filtro = new JPanel(new SpringLayout());
        this.buscar = new JButton("Buscar");
        
        //Creamos los elementos
        JLabel precio = new JLabel("Precio:");
        JLabel noches = new JLabel("Noches:");
        this.precio = new JTextField(5);
        this.noches = new JTextField(5);
        
        //Introducimos los elementos
        filtro.add(precio);
        filtro.add(this.precio);
        filtro.add(noches);
        filtro.add(this.noches);
        SpringUtilities.makeCompactGrid(filtro, 2, 2, 6, 6, 6, 6);
        
        //Hacemos un panel para evitar que se expandan los JTextField
        JPanel corset = new JPanel(new GridBagLayout());
        corset.add(filtro);
        
        String[] elems = {"Organizado", "IMSERSO"};
        this.tipoViaje = new JComboBox(elems);
        
        JPanel campos = new JPanel();
        campos.add(corset);
        campos.add(this.tipoViaje);
        
        //Metemos el formulario y el boton
        JPanel params = new JPanel(new SpringLayout());
        params.add(campos);
        JPanel botonPan = new JPanel(new GridBagLayout());
        botonPan.add(this.buscar);
        params.add(botonPan);
        
        SpringUtilities.makeCompactGrid(params, 2, 1, 6, 6, 6, 6);
        params.setBorder(BorderFactory.createTitledBorder("Filtrar resultados"));
        
        return params;
    }
    
    /**
     * Inicializa la secci&pacute;n en la que se muestran los resultados.
     */
    private void iniResultados() {
        String[] titulos = {"Nombre", "Precio", "Días", "Noches", "F.Salida", "Loc.Salida",
        "Localidades", "Descripción"};
        JTable tabla = new ZebraJTable(null, titulos);
        this.resultados = new JScrollPane(tabla);
    }
    
    /**
     * Inicializa la secci&oacute;n el la que mostramos los campos para especificar 
     * los detalles.
     */
    private JPanel iniDetalles() {
        JPanel detallesPan = new JPanel(new SpringLayout());
        JPanel corset = new JPanel(new GridBagLayout());
        JPanel corsetCampos = new JPanel(new GridBagLayout());
        JPanel panCampos = new JPanel(new SpringLayout());
        this.calcular = new JButton("Calcular");
        corset.add(this.calcular);
        
        JLabel personas = new JLabel("Personas:");
        this.personas = new JTextField(3);
        JLabel fecha = new JLabel("Fecha:");
        this.fecha = new JTextField(7);
        
        panCampos.add(personas);
        panCampos.add(this.personas);
        panCampos.add(fecha);
        panCampos.add(this.fecha);
        SpringUtilities.makeCompactGrid(panCampos, 2, 2, 6, 6, 6, 6);
        corsetCampos.add(panCampos);
        
        detallesPan.add(corsetCampos);
        detallesPan.add(corset);
        
        SpringUtilities.makeCompactGrid(detallesPan, 2, 1, 6, 6, 6, 6);
        return detallesPan;
    }

    @Override
    public String claveVentana(String textoBoton) {
        return "NuevoPaquete";
    }

    public JTextField getPrecio() {
        return precio;
    }

    public JTextField getNoches() {
        return noches;
    }

    public JScrollPane getResultados() {
        return resultados;
    }

    public FooterServicios getFooter() {
        return footer;
    }

    public JButton getBuscar() {
        return buscar;
    }

    public JComboBox getTipoViaje() {
        return tipoViaje;
    }

    public JTextField getPersonas() {
        return personas;
    }

    public JTextField getFecha() {
        return fecha;
    }

    public Paquete getCurrPaq() {
        return currPaq;
    }

    public void setCurrPaq(Paquete currPaq) {
        this.currPaq = currPaq;
    }

    public String getNombre() {
        return nombre;
    }

    public BookingFrame getPadre() {
        return padre;
    }

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
        this.buscar.addActionListener(controlador);
        this.calcular.addActionListener(controlador);
    }
}
