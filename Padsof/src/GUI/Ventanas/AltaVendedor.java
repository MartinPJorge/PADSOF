/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Ventanas;

import GUI.Recursos.SpringUtilities;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author ivan
 */
public class AltaVendedor extends Ventana{
    
    private JTextField name;
    private JTextField surname;
    private JTextField dni;
    private JDateChooser fechaNac;    
    
    private JTextField id;
    private JTextField pass;

    private JButton atras;
    private JButton crear;
    
    
    public AltaVendedor(BookingFrame padre, String nombre){
        super(new FlowLayout(), nombre, padre, 550, 500);
        
        this.iniForm();
        
        this.setBorder(BorderFactory.createTitledBorder(nombre));
        
        
    }
    
    private void iniForm(){
        JPanel form = new JPanel(new SpringLayout());
        form.setBorder(BorderFactory.createTitledBorder("Datos del Nuevo Vendedor"));
        
        this.name = new JTextField(20);
        JLabel nom = new JLabel("Nombre: ");
        nom.setLabelFor(name);
        form.add(nom);
        form.add(name);         
                
        this.surname = new JTextField();
        JLabel apel = new JLabel("Apellidos: ");
        apel.setLabelFor(surname);
        form.add(apel);
        form.add(surname);
        
        this.dni = new JTextField();
        JLabel dnilab = new JLabel("DNI: ");
        dnilab.setLabelFor(dni);
        form.add(dnilab);
        form.add(dni);
        
        this.fechaNac = new JDateChooser(null, "dd/MM/YYYY");
        JLabel fecha = new JLabel("Fecha de Nacimiento: ");
        fecha.setLabelFor(fechaNac);
        form.add(fecha);
        form.add(fechaNac);
        
   
        this.id = new JTextField();
        JLabel idlab = new JLabel("ID Vendedor: ");
        idlab.setLabelFor(id);
        form.add(idlab);
        form.add(id);
        
        this.pass = new JTextField();
        JLabel contra = new JLabel("Contraseña: ");
        contra.setLabelFor(pass);
        form.add(contra);
        form.add(pass);
        
        SpringUtilities.makeCompactGrid(form, 6, 2, 8, 8, 20, 20);
        form.setVisible(true);
        
        JPanel abajo = new JPanel(new FlowLayout());
        this.atras = new JButton("Atrás");
        atras.setVisible(true);
        this.crear = new JButton("Añadir Nuevo Vendedor");
        crear.setVisible(true);
        abajo.add(atras);
        abajo.add(crear);
        
        abajo.setVisible(true);
        
        JPanel general = new JPanel();
        general.setLayout(new BoxLayout(general, BoxLayout.Y_AXIS));
        general.add(form);
        general.add(Box.createRigidArea(new Dimension(100, 100)));
        general.add(Box.createVerticalGlue());
        general.add(abajo);

        general.setVisible(true);
        this.add(general);
    }

    public JButton getAtras() {
        return atras;
    }

    public void setAtras(JButton atras) {
        this.atras = atras;
    }

    public JButton getCrear() {
        return crear;
    }

    public void setCrear(JButton crear) {
        this.crear = crear;
    }

    public JTextField getDni() {
        return dni;
    }

    public void setDni(JTextField dni) {
        this.dni = dni;
    }

    public JDateChooser getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(JDateChooser fechaNac) {
        this.fechaNac = fechaNac;
    }

    public JTextField getId() {
        return id;
    }

    public void setId(JTextField id) {
        this.id = id;
    }

    public JTextField getNameT() {
        return name;
    }

    public void setName(JTextField name) {
        this.name = name;
    }

    public JTextField getPass() {
        return pass;
    }

    public void setPass(JTextField pass) {
        this.pass = pass;
    }

    public JTextField getSurname() {
        return surname;
    }

    public void setSurname(JTextField surname) {
        this.surname = surname;
    }
    
    
    @Override
    public String claveVentana(String textoBoton) {
        return "Gestión";
    }
    
    /**
     * Especifica el controlador a usar por la ventana de AltaVendedor.
     * @param controlador 
     */
    @Override
    public void setControlador(ActionListener controlador) {
        this.controlador = controlador;
        this.crear.addActionListener(controlador);
        this.atras.addActionListener(controlador);
    }
}
