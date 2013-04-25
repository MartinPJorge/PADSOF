/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Ventanas;

import GUI.Recursos.Formulario;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import GUI.Ventanas.Ventana.ClickCambioVentana;
import java.awt.Dimension;
import javax.swing.JTextField;

/**
 *
 * @author Jorge
 */
public class FooterServicios extends Ventana{
    private JTextField total;
    private JButton volver;
    private JButton add;
    private JButton invisible;
    private JLabel totalL;
    
    /**
     * Constructor de la clase FooterServicios.<br/>
     * Esta clase se encarga de crear un pie de ventana para los paneles de 
     * a&ntilde;adir servicios.
     */
    public FooterServicios(BookingFrame padre, String nombre) {
        super(new BorderLayout(), nombre,padre,0,0);
        
        //Mostrar el total
        this.totalL = new JLabel("Total:");
        this.total = new JTextField("");
        this.total.setEditable(false);
        this.total.setMinimumSize(new Dimension(100, 20));
        //this.total.setSize(100, 40);
        this.total.setPreferredSize(new Dimension(100, 20));
        Formulario left = new Formulario();
        left.addTexto(this.totalL, this.total);
        left.aplicarCambios();

        //Boton invisible
        this.invisible = new JButton();
        this.invisible.setVisible(false);
        
        //Botones de navegacion
        JPanel right = new JPanel();
        this.volver = new JButton("Atrás");
        //this.volver.addActionListener(new ClickCambioVentana());
        this.add = new JButton("Añadir");
        //this.add.addActionListener(new ClickCambioVentana());
        right.add(this.volver);
        right.add(this.add);
        
        //Ajustar los elementos
        this.add(left, BorderLayout.WEST);
        this.add(invisible, BorderLayout.CENTER);
        this.add(right, BorderLayout.EAST);
    }

    /**
     *
     * @return el bot&oacute;n de volver
     */
    public JButton getVolver() {
        return volver;
    }

    /**
     *
     * @return el bot&oacute;n de a&ntilde;adir un servicio
     */
    public JButton getAdd() {
        return add;
    }

    @Override
    public String claveVentana(String textoBoton) {
        return "NuevoPaquete";
    }

    /**
     *
     * @return el campo de texto con el precio total
     */
    public JTextField getTotal() {
        return total;
    }

    /**
     *
     * @return la etiqueta del precio total
     */
    public JLabel getTotalL() {
        return totalL;
    }
    
    
}
