/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import Recursos.Formulario;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Jorge
 */
public class DatosCliente extends JPanel {
    private Formulario nuevoCliente;
    private Formulario registradoCliente;
    private JButton crearPaquete;
    private JButton atras;
    private JTextField nSegSocialF;
    private final int colsTexto = 15;
    
    /**
     * Constructor de la ventana para introducir los datos de un cliente.
     */
    public DatosCliente() {
        //Creamos los 2 paneles
        this.crearPaquete = new JButton("Crear Paquete");
        this.iniNuevoCliente();
        this.iniRegistradoCliente();
                        
        //Creamos el panel de datos
        JPanel pDatos = new JPanel(new GridBagLayout());
        pDatos.add(this.nuevoCliente);
        pDatos.add(this.registradoCliente);
        
        //Juntamos todo en el panel principal
        this.setLayout(new BorderLayout());
        this.add(pDatos, BorderLayout.CENTER);
        JPanel flowPanel = new JPanel(new FlowLayout());
        flowPanel.add(this.crearPaquete);
        this.add(BorderLayout.SOUTH, flowPanel);
    }
    
    /**
     * Crea el formulario de los clientes registrados.
     */
    private void iniRegistradoCliente() {
        this.registradoCliente = new Formulario("Cliente registrado");
        
        JLabel etDNI = new JLabel("DNI:");
        JTextField dniReg = new JTextField(colsTexto);
        
        this.registradoCliente.addTexto(etDNI,dniReg);
        this.registradoCliente.aplicarCambios();
    }
    
    /**
     * Crea el formulario de los nuevos clientes.
     */
    private void iniNuevoCliente() {
        this.nuevoCliente = new Formulario("Nuevo cliente");
        
        //Etiquetas
        JLabel nombre = new JLabel("Nombre:");
        JLabel apellidos = new JLabel("Apellidos:");
        JLabel dni = new JLabel("DNI:");
        JLabel fNacimiento = new JLabel("Fecha de nacimiento:");
        JLabel nSegSocial = new JLabel("Nº S.Social:");
        JLabel vacia = new JLabel("");
        
        //Campos
        JTextField nombreF = new JTextField(colsTexto);
        JTextField apellidosF = new JTextField(colsTexto);
        JTextField dniF = new JTextField(colsTexto);
        JTextField fNacimientoF = new JTextField(colsTexto);
        nSegSocialF = new JTextField("Deshabilitado", colsTexto);
        nSegSocialF.setEnabled(false);
        JCheckBox edad3 = new JCheckBox("3ª edad", false);
        ItemListener escucha = new ItemListener(){
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if(e.getStateChange() == ItemEvent.SELECTED){
                        nSegSocialF.setEnabled(true);
                        nSegSocialF.setText("");
                    }
                    else if(e.getStateChange() == ItemEvent.DESELECTED){
                        nSegSocialF.setEnabled(false);
                        nSegSocialF.setText("Deshabilitado");
                    }

                    validate();
                    repaint();
                }
            };
        edad3.addItemListener(escucha);
        
        //Introducimos los elementos en el formulario
        this.nuevoCliente.addTexto(nombre, nombreF);
        this.nuevoCliente.addTexto(apellidos, apellidosF);
        this.nuevoCliente.addTexto(dni, dniF);
        this.nuevoCliente.addTexto(fNacimiento, fNacimientoF);
        this.nuevoCliente.addTexto(vacia, edad3);
        this.nuevoCliente.addTexto(nSegSocial, nSegSocialF);
        
        this.nuevoCliente.aplicarCambios();
    }   
}
