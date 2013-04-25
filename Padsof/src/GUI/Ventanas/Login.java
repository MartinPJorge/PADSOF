/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Ventanas;

import GUI.Recursos.SpringUtilities;
import GUI.Recursos.Formulario;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 * Clase para la GUI que representa la Ventana de Login.
 *
 * @author Jorge Martín Pérez
 * @author Iván Márquez Pardo
 * @version 1.0
 */
public class Login extends Ventana{
    private JLabel titulo;
    private Formulario form;
    private JTextField idC;
    private JTextField passwordC;
    private JButton login;
    private JButton olvidado;
    
    public Login(BookingFrame padre, String nombre) {
        super(new SpringLayout(), nombre,padre, 400,300);
        
        //Titulo
        this.titulo = new JLabel("Booking");
        JPanel titPanel = new JPanel();
        this.titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        titPanel.add(this.titulo);
        this.add(titPanel);
        
        
        //Formulario
        this.form = new Formulario();
        this.login = new JButton("Login");
        JLabel id = new JLabel("ID Usuario:");
        JLabel password = new JLabel("Contraseña:");
        this.idC = new JTextField(15);
        this.passwordC = new JPasswordField(15);
        
        this.form.addTexto(id, this.idC);
        this.form.addTexto(password, this.passwordC);
        this.form.aplicarCambios();
        this.add(this.form);
        
        //Boton del log
        JPanel boLog = new JPanel();
        boLog.add(this.login);
        this.add(boLog);
        
        //Boton de olvidado
        JPanel olvida = new JPanel();
        this.olvidado = new JButton("¿Has olvidado tu contraseña?");
        olvida.add(this.olvidado);
        this.add(olvida);
        
        
        SpringUtilities.makeCompactGrid(this, 4, 1, 6, 6, 6, 6);
    }

    @Override
    public String claveVentana(String textoBoton) {
        if(textoBoton.equals("Login")) {
            return "Inicio";
        }
        else{return null;}
    }
    
    /**
     * Devuelve el texto introducido en el campo del ID.
     * @return String
     */
    public String getId() {
        return this.idC.getText();
    }
    
    /**
     * Devuelve el texto introducido en el campo de la contrase&ntilde;a.
     * @return password
     */
    public String getPassword() {
        return this.passwordC.getText();
    }
    
    /**
     * Especifica el controlador a usar por la ventana de Inicio.
     * @param controlador 
     */
    @Override
    public void setControlador(ActionListener controlador) {
        this.controlador = controlador;
        this.login.addActionListener(this.controlador);
        this.olvidado.addActionListener(this.controlador);
    }
}
