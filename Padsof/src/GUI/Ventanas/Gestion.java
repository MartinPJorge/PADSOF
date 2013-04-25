/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Ventanas;

import GUI.Recursos.SpringUtilities;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Clase para la GUI que representa la Ventana de transición en la Gestión de
 * Usuarios.
 *
 * @author Jorge Martín Pérez
 * @author Iván Márquez Pardo
 * @version 1.0
 */
public class Gestion extends Ventana {

    private JButton altaV;
    private JButton bajaV;
    private JButton modifC;
    private JButton atras;

    /**
     * Constructor de la clase.
     *
     * @param padre
     * @param nombre
     */
    public Gestion(BookingFrame padre, String nombre) {
        super(nombre, padre, 600, 500);

        this.iniGestion();

        this.setBorder(BorderFactory.createTitledBorder(nombre));

    }

    /**
     * Método auxiliar para diseñar el panel entero de esta clase.
     */
    private void iniGestion() {
        altaV = new JButton("Dar de Alta Vendedor");
        altaV.setPreferredSize(new Dimension(300, 80));
        altaV.setVisible(true);
        bajaV = new JButton("Dar de Baja Vendedor");
        bajaV.setPreferredSize(new Dimension(300, 80));
        bajaV.setVisible(true);
        modifC = new JButton("Modificar Contraseña");
        modifC.setPreferredSize(new Dimension(300, 80));
        modifC.setVisible(true);

        JPanel aux = new JPanel(new SpringLayout());
        aux.add(altaV);
        aux.add(bajaV);
        aux.add(modifC);
        aux.setVisible(true);
        SpringUtilities.makeCompactGrid(aux, 3, 1, 7, 7, 20, 50);


        atras = new JButton("Atrás");
        JPanel aux2 = new JPanel(new FlowLayout());
        aux2.add(atras);
        aux2.setVisible(true);

        JPanel aux3 = new JPanel();
        aux3.setLayout(new BoxLayout(aux3, BoxLayout.Y_AXIS));
        aux3.add(Box.createVerticalGlue());
        aux3.add(aux);
        aux3.add(Box.createVerticalGlue());
        aux3.add(aux2);
        aux3.add(Box.createVerticalGlue());
        aux3.setVisible(true);
        this.add(aux3);
    }

    /**
     * Devuelve, a partir de textoBoton, el nombre de la ventana a la que
     * cambiaremos.
     *
     * @param textoBoton
     * @return nombre de la siguiente ventana
     */
    @Override
    public String claveVentana(String textoBoton) {
        if (textoBoton.equals("Dar de Alta Vendedor")) {
            return "AltaVendedor";
        } else if (textoBoton.equals("Dar de Baja Vendedor")) {
            return "BajaVendedor";
        } else if (textoBoton.equals("Modificar Contraseña")) {
            return "ModifPass";
        } else {
            return "Inicio";
        }
    }

    /**
     * Especifica el controlador a usar por la ventana de Gestión.
     *
     * @param controlador
     */
    @Override
    public void setControlador(ActionListener controlador) {
        this.controlador = controlador;
        this.altaV.addActionListener(controlador);
        this.bajaV.addActionListener(controlador);
        this.modifC.addActionListener(controlador);
        this.atras.addActionListener(controlador);
    }
}
