/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Ventanas;

import GUI.Recursos.SpringUtilities;
import GUI.Recursos.ZebraJTable;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 *
 * @author Jorge
 */
public class ModificarPaquete extends Ventana{
    private JLabel etiq;
    private JTextField dniCliente;
    private JButton buscar;
    private JPanel resultados;
    private JButton modificar;
    private JButton atras;
    private JScrollPane tablaResults;
    
    
    public ModificarPaquete(BookingFrame padre, String nombre) {
        super(new SpringLayout(), nombre, padre, 400,600);
        JPanel introduce = new JPanel();
        this.etiq = new JLabel("DNI cliente:");
        this.dniCliente = new JTextField(10);
        this.buscar = new JButton("Buscar");
        
        introduce.add(this.etiq);
        introduce.add(this.dniCliente);
        introduce.add(this.buscar);
        this.add(introduce);
        
        this.iniResultados();
        this.add(this.resultados);
                
        JPanel botones = new JPanel();
        this.modificar = new JButton("Modificar");
        this.atras = new JButton("Atrás");
        botones.add(this.atras);
        botones.add(this.modificar);

        this.add(botones);
        
        SpringUtilities.makeCompactGrid(this, 3, 1, 6, 6, 6, 6);
    }
    
    private void iniResultados() {
        JLabel  abiertos = new JLabel("Paquetes abiertos:");
        String[] titulos = {"IdPaquete", "DNI cliente", "Productos", "Estado"};
                
        ZebraJTable tabla = new ZebraJTable(null, titulos);
        this.tablaResults = new JScrollPane(tabla);
        
        this.resultados = new JPanel(new SpringLayout());
        this.resultados.add(abiertos);
        this.resultados.add(this.tablaResults);
        
        SpringUtilities.makeCompactGrid(this.resultados, 2, 1, 6, 6, 6, 6);
    }


   @Override
    public String claveVentana(String textoBoton) {
        if(this.modificar.getText().equals(textoBoton)) {
            return "NuevoPaquete";
        }
        else {
            return "Inicio";
        }
    }
    
    /**
     * Especifica el controlador a usar por la ventana de Inicio.
     * @param controlador 
     */
    public void setControlador(ActionListener controlador) {
        this.controlador = controlador;
        this.modificar.addActionListener(controlador);
        this.buscar.addActionListener(controlador);
        this.atras.addActionListener(controlador);
    }

    /**
     *
     * @return campo de texto con el DNI del cliente
     */
    public JTextField getDniCliente() {
        return dniCliente;
    }

    /**
     *
     * @return el bot&oacute;n de buscar
     */
    public JButton getBuscar() {
        return buscar;
    }

    /**
     *
     * @return el scroll pane de los resultados
     */
    public JScrollPane getTablaResults() {
        return tablaResults;
    }

    /**
     *
     * @return el bot&oacute;n de modificar
     */
    public JButton getModificar() {
        return modificar;
    }
    
}
