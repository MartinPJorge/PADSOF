/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Ventanas;

import GUI.Recursos.SpringUtilities;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 *
 * @author Jorge
 */
public class Inicio extends Ventana{
    private JPanel otros;
    private JButton crear;
    private JButton modificar;
    private JButton facturacion;
    private JButton margen;
    private JButton gestion;
    
    public Inicio(BookingFrame padre, String nombre) {
        super(new SpringLayout(), nombre, padre, 400,350);
        this.iniPaquetes();
        this.iniIngresos();
        this.iniOtros();

        
        SpringUtilities.makeCompactGrid(this, 3, 1, 20,20,20,20);
    }
    
    /**
     * Inicializa la parte de paquetes
     */
    private void iniPaquetes() {
        JPanel paquetes = new JPanel(new SpringLayout());
        JLabel labPaq = new JLabel("Paquetes");
        labPaq.setFont(new Font("",Font.PLAIN, 20));
        labPaq.setHorizontalAlignment(JTextField.CENTER);
        
        //Creamos los botones
        JPanel botones = new JPanel();
        this.crear = new JButton("Crear");
        this.modificar = new JButton("Modificar");
        botones.add(this.crear);
        botones.add(this.modificar);
        
        //Introducimos los elementos
        paquetes.add(labPaq);
        paquetes.add(botones);
        
        SpringUtilities.makeCompactGrid(paquetes, 2, 1, 6, 6, 6, 6);
        this.add(paquetes);
    }
    
    /**
     * Inicializa la parte de ingresos
     */
    private void iniIngresos() {
        JPanel ingresos = new JPanel(new SpringLayout());
        JLabel labIngreso = new JLabel("Ingresos");
        labIngreso.setFont(new Font("",Font.PLAIN, 20));
        labIngreso.setHorizontalAlignment(JTextField.CENTER);
        
        //Creamos los botones
        JPanel botones = new JPanel();
        this.facturacion = new JButton("Facturación");
        this.margen = new JButton("Modificar márgenes");
        botones.add(this.facturacion);
        botones.add(this.margen);
        
        //Introducimos los elementos
        ingresos.add(labIngreso);
        ingresos.add(botones);
        
        
        
        SpringUtilities.makeCompactGrid(ingresos, 2, 1, 6, 6, 6, 6);
        this.add(ingresos);
    }
    
    /**
     * Inicializa la parte de otros servicios
     */
    private void iniOtros() {
        JPanel otrosPan = new JPanel(new SpringLayout());
        JLabel labOtros = new JLabel("Otros");
        labOtros.setFont(new Font("",Font.PLAIN, 20));
        labOtros.setHorizontalAlignment(JTextField.CENTER);
        
        //Creamos los botones
        JPanel botones = new JPanel();
        this.gestion = new JButton("Gestión de usuarios");
        botones.add(this.gestion);
        
        //Introducimos los elementos
        otrosPan.add(labOtros);
        otrosPan.add(botones);
       
        SpringUtilities.makeCompactGrid(otrosPan, 2, 1, 6, 6, 6, 6);
        this.add(otrosPan);
    }

    @Override
    public String claveVentana(String textoBoton) {
        if(textoBoton.equals(this.crear.getText())) {
            return "DatosCliente";
        }
        else if(textoBoton.equals(this.modificar.getText())){
            return "ModificarPaquete";
        }
        else{
            return "Login";
        }
    }
    
    /**
     * Especifica el controlador a usar por la ventana de Inicio.
     * @param controlador 
     */
    public void setControlador(ActionListener controlador) {
        this.controlador = controlador;
        this.crear.addActionListener(this.controlador);
        this.modificar.addActionListener(this.controlador);
        this.facturacion.addActionListener(this.controlador);
        this.modificar.addActionListener(this.controlador);
        this.gestion.addActionListener(this.controlador);
    }
}
