/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Ventanas;

import GUI.Recursos.SpringUtilities;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author e265923
 */
public class Margenes extends Ventana{
    private JFormattedTextField mHoteles;
    private JFormattedTextField mViajes;
    private JFormattedTextField mVuelos;
    
    private JButton modify;
    private JButton atras;
    
    public Margenes(BookingFrame padre, String nombre) {
        super(nombre, padre, 550, 550);
        
        this.iniMargenes();
        
        this.setBorder(BorderFactory.createTitledBorder(nombre));
        
    }

    private void iniMargenes(){
        JPanel form = new JPanel(new GridBagLayout());
        JPanel campos = new JPanel(new SpringLayout());
        
        JLabel perc = new JLabel(" %.");
        perc.setVisible(true);
        
        this.mHoteles = new JFormattedTextField(new Double(0));
        mHoteles.setValue(null);
        mHoteles.setPreferredSize(new Dimension(35, 30));
        JLabel hotel = new JLabel("Hoteles: ");
        hotel.setVisible(true);
        hotel.setLabelFor(mHoteles);
        mHoteles.setVisible(true);
        campos.add(hotel);
        campos.add(mHoteles);
        campos.add(new JLabel(" %."));
         
        this.mViajes = new JFormattedTextField(new Double(0));
        mViajes.setColumns(3);
        mViajes.setValue(null);
        mViajes.setPreferredSize(new Dimension(35, 30));
        JLabel viaj = new JLabel("Viajes: ");
        viaj.setLabelFor(mViajes);
        viaj.setVisible(true);
        mViajes.setVisible(true);
        campos.add(viaj);
        campos.add(mViajes);
        campos.add(new JLabel(" %."));
        
        this.mVuelos = new JFormattedTextField(new Double(0));
        mVuelos.setColumns(3);
        mVuelos.setValue(null);
        mVuelos.setPreferredSize(new Dimension(35, 30));
        JLabel vuel = new JLabel("Vuelos: ");
        vuel.setLabelFor(mVuelos);
        vuel.setVisible(true);
        mVuelos.setVisible(true);
        campos.add(vuel);
        campos.add(mVuelos);
        campos.add(new JLabel(" %."));
        
        
        campos.setVisible(true);
        SpringUtilities.makeCompactGrid(campos, 3, 3, 20, 20, 10, 50);
        
        form.add(campos);
        form.setVisible(true);
        this.add(form);
        
        JPanel aux = new JPanel(new FlowLayout());
        this.modify = new JButton("Modificar y guardar");
        this.atras = new JButton("Atrás");
        aux.add(modify);
        aux.add(atras);
        
        JPanel general = new JPanel();
        general.setLayout(new BoxLayout(general, BoxLayout.Y_AXIS));
        general.add(form);
        general.add(Box.createVerticalGlue());
        general.add(aux);
        
        general.setVisible(true);
        general.setPreferredSize(new Dimension(400, 500));
        
        this.add(general);
             
    }

    public JButton getAtras() {
        return atras;
    }

    public void setAtras(JButton atras) {
        this.atras = atras;
    }

    public JFormattedTextField getmHoteles() {
        return mHoteles;
    }

    public void setmHoteles(JFormattedTextField mHoteles) {
        this.mHoteles = mHoteles;
    }

    public JFormattedTextField getmViajes() {
        return mViajes;
    }

    public void setmViajes(JFormattedTextField mViajes) {
        this.mViajes = mViajes;
    }

    public JFormattedTextField getmVuelos() {
        return mVuelos;
    }

    public void setmVuelos(JFormattedTextField mVuelos) {
        this.mVuelos = mVuelos;
    }

    public JButton getModify() {
        return modify;
    }

    public void setModify(JButton modify) {
        this.modify = modify;
    }

    
    @Override
    public String claveVentana(String textoBoton) {
        return "Inicio";
    }
    
    /**
     * Especifica el controlador a usar por la ventana de Gestión.
     * @param controlador 
     */
    @Override
    public void setControlador(ActionListener controlador) {
        this.controlador = controlador;
        this.atras.addActionListener(controlador);
        this.modify.addActionListener(controlador);
    }
    
}
