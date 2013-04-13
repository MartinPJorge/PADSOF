/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import Recursos.Formulario;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import Ventanas.Ventana.ClickCambioVentana;

/**
 *
 * @author Jorge
 */
public class FooterServicios extends Ventana{
    private JTextArea total;
    private JButton volver;
    private JButton add;
    private JButton invisible;
    
    /**
     * Constructor de la clase FooterServicios.<br/>
     * Esta clase se encarga de crear un pie de ventana para los paneles de 
     * a&ntilde;adir servicios.
     */
    public FooterServicios(BookingFrame padre, String nombre) {
        super(new BorderLayout(), nombre,padre,0,0);
        
        //Mostrar el total
        JLabel totalL = new JLabel("Total:");
        this.total = new JTextArea(1, 4);
        Formulario left = new Formulario();
        left.addTexto(totalL, this.total);
        left.aplicarCambios();

        //Boton invisible
        this.invisible = new JButton();
        this.invisible.setVisible(false);
        
        //Botones de navegacion
        JPanel right = new JPanel();
        this.volver = new JButton("Atrás");
        this.volver.addActionListener(new ClickCambioVentana());
        this.add = new JButton("Añadir");
        this.add.addActionListener(new ClickCambioVentana());
        right.add(this.volver);
        right.add(this.add);
        
        //Ajustar los elementos
        this.add(left, BorderLayout.WEST);
        this.add(invisible, BorderLayout.CENTER);
        this.add(right, BorderLayout.EAST);
    }

    public JButton getVolver() {
        return volver;
    }

    public JButton getAdd() {
        return add;
    }

    @Override
    public String claveVentana(String textoBoton) {
        return "NuevoPaquete";
    }

}
