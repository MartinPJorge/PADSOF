/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Ventanas;

import GUI.Recursos.Formulario;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 * Clase para la GUI que representa la Ventana de datos de Cliente.
 *
 * @author Jorge Martín Pérez
 * @author Iván Márquez Pardo
 * @version 1.0
 */
public class DatosCliente extends Ventana {
    private Formulario nuevoCliente;
    private Formulario registradoCliente;
    private JButton crearPaquete;
    private JButton atras;
    private JTextField nSegSocialF;
    private final int colsTexto = 15;
    private JCheckBox edad3;
    
    /**
     * Constructor de la ventana para introducir los datos de un cliente.
     */
    public DatosCliente(BookingFrame padre, String nombre) {
        super(new SpringLayout(), nombre, padre, 700,350);
        
        //Creamos los 2 paneles
        this.crearPaquete = new JButton("Crear Paquete");
        this.atras = new JButton("Atrás");
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
        flowPanel.add(this.atras);
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
        this.edad3 = new JCheckBox("3ª edad", false);
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
        this.edad3.addItemListener(escucha);
        
        //Introducimos los elementos en el formulario
        this.nuevoCliente.addTexto(nombre, nombreF);
        this.nuevoCliente.addTexto(apellidos, apellidosF);
        this.nuevoCliente.addTexto(dni, dniF);
        this.nuevoCliente.addTexto(fNacimiento, fNacimientoF);
        this.nuevoCliente.addTexto(vacia, this.edad3);
        this.nuevoCliente.addTexto(nSegSocial, nSegSocialF);
        
        this.nuevoCliente.aplicarCambios();
    }   

     @Override
    public String claveVentana(String textoBoton) {
        if(textoBoton.equals("Atrás")) {
            return "Inicio";
        }
        else {
            return "NuevoPaquete";
        }
    }

    /**
     *
     * @return el formulario de nuevo cliente
     */
    public Formulario getNuevoCliente() {
        return nuevoCliente;
    }

    /**
     *
     * @return el formulario de cliente registrado
     */
    public Formulario getRegistradoCliente() {
        return registradoCliente;
    }

    /**
     *
     * @return el checkBox de la 3 edad
     */
    public JCheckBox getEdad3() {
        return edad3;
    }
    
    
    
    /**
     * Especifica el controlador a usar por la ventana de Inicio.
     * @param controlador 
     */
    public void setControlador(ActionListener controlador) {
        this.controlador = controlador;
        this.crearPaquete.addActionListener(this.controlador);
        this.atras.addActionListener(this.controlador);
    }
}
