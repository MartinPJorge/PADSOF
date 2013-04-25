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
 * Clase para la GUI que representa la Ventana en la que damos de alta a un
 * Vendedor nuevo.
 *
 * @author Jorge Martín Pérez
 * @author Iván Márquez Pardo
 * @version 1.0
 */
public class AltaVendedor extends Ventana {

    private JTextField name;
    private JTextField surname;
    private JTextField dni;
    private JDateChooser fechaNac;
    private JTextField id;
    private JTextField pass;
    private JButton atras;
    private JButton crear;

    /**
     * Constructor de la clase.
     *
     * @param padre
     * @param nombre
     */
    public AltaVendedor(BookingFrame padre, String nombre) {
        super(new FlowLayout(), nombre, padre, 550, 550);

        this.iniForm();

        this.setBorder(BorderFactory.createTitledBorder(nombre));
    }

    /**
     * Método auxiliar para diseñar el panel del formulario de esta clase.
     */
    private void iniForm() {
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

    /**
     *
     * @return JButton
     */
    public JButton getAtras() {
        return atras;
    }

    /**
     *
     * @param atras
     */
    public void setAtras(JButton atras) {
        this.atras = atras;
    }

    /**
     *
     * @return JButton
     */
    public JButton getCrear() {
        return crear;
    }

    /**
     *
     * @param crear
     */
    public void setCrear(JButton crear) {
        this.crear = crear;
    }

    /**
     *
     * @return JTextField
     */
    public JTextField getDni() {
        return dni;
    }

    /**
     *
     * @param dni
     */
    public void setDni(JTextField dni) {
        this.dni = dni;
    }

    /**
     *
     * @return JDateChooser - fechaNac
     */
    public JDateChooser getFechaNac() {
        return fechaNac;
    }

    /**
     *
     * @param fechaNac
     */
    public void setFechaNac(JDateChooser fechaNac) {
        this.fechaNac = fechaNac;
    }

    /**
     *
     * @return JTextField
     */
    public JTextField getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(JTextField id) {
        this.id = id;
    }

    /**
     *
     * @return JTextField
     */
    public JTextField getNameT() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(JTextField name) {
        this.name = name;
    }

    /**
     *
     * @return JTextField
     */
    public JTextField getPass() {
        return pass;
    }

    /**
     *
     * @param pass
     */
    public void setPass(JTextField pass) {
        this.pass = pass;
    }

    /**
     *
     * @return JTextField
     */
    public JTextField getSurname() {
        return surname;
    }

    /**
     *
     * @param surname
     */
    public void setSurname(JTextField surname) {
        this.surname = surname;
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
        return "Gestión";
    }

    /**
     * Especifica el controlador a usar por la ventana de AltaVendedor.
     *
     * @param controlador
     */
    @Override
    public void setControlador(ActionListener controlador) {
        this.controlador = controlador;
        this.crear.addActionListener(controlador);
        this.atras.addActionListener(controlador);
    }
}
