/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Ventanas;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * Clase para la GUI - Main de Pruebas de Estilo de Ventana.
 *
 * @author Jorge Martín Pérez
 * @author Iván Márquez Pardo
 * @version 1.0
 */
public class MainPruebas {
    public static void main(String[] args) {
        //BookingFrame ppal = new BookingFrame(null);
        JFrame frame = new JFrame("Margenes");
//        frame.add(new Facturacion("Ver Facturación", null));
//        frame.add(new Margenes("Modificar Márgenes de Beneficios", null));
//        frame.add(new Gestion("Gestión de Usuarios", null));
        frame.add(new AltaVendedor(null, "Dar de Alta Vendedor"));
//        frame.add(new BajaVendedor("Dar de Baja Vendedor", null));
//        frame.add(new ModificarPass("Modificar Contraseña", null));
        frame.setSize(new Dimension(580, 550));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
