/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import Recursos.SpringUtilities;
import Recursos.Formulario;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 *
 * @author Jorge
 */
public class Login extends Ventana{
    private JLabel titulo;
    private Formulario form;
    private JButton login;
    private JButton olvidado;
    
    public Login(BookingFrame padre, String nombre) {
        super(new SpringLayout(), nombre,padre, 300,200);
        
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
        JTextField idC = new JTextField(15);
        JPasswordField passwordC = new JPasswordField(15);
        
        this.form.addTexto(id, idC);
        this.form.addTexto(password, passwordC);
        this.form.aplicarCambios();
        this.add(this.form);
        
        //Boton del log
        JPanel boLog = new JPanel();
        boLog.add(this.login);
        this.add(boLog);
        this.login.addActionListener(new ClickCambioVentana());
        
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
}
