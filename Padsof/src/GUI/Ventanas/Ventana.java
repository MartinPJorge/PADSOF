/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Ventanas;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Jorge
 */
public abstract class Ventana extends JPanel{
    protected String nombre;
    protected BookingFrame padre;
    private int dimX; //ancho minimo a mostrar
    private int dimY; //alto minimo a mostrar
    protected ActionListener controlador;
    
    public Ventana(LayoutManager lay, String nombre, BookingFrame padre, 
            int dimX, int dimY) {
        super(lay);
        this.nombre = nombre;
        this.padre = padre;
        this.dimX = dimX;
        this.dimY = dimY;
    }
    
    public Ventana(String nombre, BookingFrame padre, int dimX, int dimY) {
        this(new FlowLayout(), nombre, padre, dimX, dimY);
    }
    
    /**
     * Recibe el nombre de la nueva ventana a la que ir.
     * @param nueva
     * @return la ventana a la que se ha cambiado
     */
    public Ventana cambiarVentana(String nueva) {
        //Obtenemos la nueva ventana
        Container contenedor = this.padre.getContentPane();
        Ventana nuevaVen = this.padre.getVentanas().get(nueva);
        
        //Quitamos la ventana anterior y metemos la nueva
        contenedor.removeAll();
        contenedor.add(nuevaVen);
        
        //Redimensionamos al tamano de la nueva ventana
        this.redimensionar(nuevaVen);
        nuevaVen.setVisible(true);
        this.padre.pack();
        
        return nuevaVen;
    }
    
    /**
     * Redimensiona el tama&ntilde; del JFrame en el que est&aacute; contenida 
     * la ventana, al tama&ntilde;o m&iacute;nimo de la nueva ventana.
     * @param nueva 
     */
    public void redimensionar(Ventana nueva) {
        this.padre.setMinimumSize( new Dimension( nueva.dimX, nueva.dimY ) );
        this.padre.setSize(nueva.dimX, nueva.dimY);
    }
    
    /**
     * Averigua la ventana que le corresponde al bot&oacute;n.
     * @param textoBoton - texto del bot&oacute;n pulsado
     * @return la clave hash de la ventana que muestra el bot&oacute;n recibido
     */
    public abstract String claveVentana(String textoBoton);
    
    /**
     * Listener asociado a los botones encargados de cambiar de ventana.
     */
    class ClickCambioVentana implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String pulsado = ((JButton)e.getSource()).getText();
            cambiarVentana(claveVentana(pulsado));
        }
    }

    /**
     * 
     * @return el controlador asignado a la ventana
     */
    public ActionListener getControlador() {
        return controlador;
    }
    
    /**
     * Especifica el controlador que utilizar&aacute; la ventana.
     * @param controlador 
     */
    public void setControlador(ActionListener controlador) {
        this.controlador = controlador;
    }
}
