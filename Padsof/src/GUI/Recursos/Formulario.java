/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Recursos;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

/**
 *
 * @author Jorge
 */
public class Formulario extends JPanel {
    private ArrayList<Component> textos;
    private ArrayList<JLabel> etiq;
    private JPanel panelForm;
    
    /**
     * Constructor del formulario.<br/>
     * Este formulario tiene la peculiaridad de que los campos de texto no se 
     * expandem a medida que aumentamos el tama&ntilde;o de la ventana en la que 
     * se incluye.
     * @param titulo - el t&iacute;tulo que aparece en el borde del panel
     */
    public Formulario(String titulo) {
        super();
        
        //Inicializamos los paneles
        this.setLayout(new GridBagLayout());
        this.panelForm = new JPanel(new SpringLayout());
        
        if(titulo.equals("") == false) {
            this.panelForm.setBorder(BorderFactory.createTitledBorder(titulo));
        }
        this.add(this.panelForm);
        
        this.textos = new ArrayList<Component>();
        this.etiq = new ArrayList<JLabel>();
    }
    
    /**
     * Crea un formulario sin borde.
     */
    public Formulario() {
        this("");
    }
    
    /**
     * Introduce en el panel un campo con su etiqueta y campo de texto correspondiente.
     * @param etiq
     * @param texto 
     */
    public void addTexto(JLabel etiq, Component texto) {
        this.textos.add(texto);
        this.etiq.add(etiq);
        etiq.setLabelFor(texto);
        
        //Metemos los elementos en el panel del formulario
        this.panelForm.add(etiq);
        this.panelForm.add(texto);
    }
    
    /**
     * Se encarga de cuadrar todos los elementos que se han ido a&ntilde;adiendo 
     * al panel.
     */
    public void aplicarCambios() {        
        SpringUtilities.makeCompactGrid(this.panelForm, this.textos.size(), 2, 6, 6, 6, 6);
        this.setVisible(true);
        this.setMaximumSize(new Dimension(300, 100));
    }

    public ArrayList<Component> getTextos() {
        return textos;
    }

    public ArrayList<JLabel> getEtiq() {
        return etiq;
    }
}
