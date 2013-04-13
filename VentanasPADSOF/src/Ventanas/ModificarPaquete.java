/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ventanas;

import Recursos.SpringUtilities;
import Recursos.ZebraJTable;
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
    private JTextField campo;
    private JButton buscar;
    private JPanel resultados;
    private JButton modificar;
    private JButton cancelar;
    private JButton addHotel;
    private JButton addVuelo;
    private JButton addViajeOrg;
    
    public ModificarPaquete(BookingFrame padre, String nombre) {
        super(new SpringLayout(), nombre, padre, 400,600);
        JPanel introduce = new JPanel();
        this.etiq = new JLabel("DNI cliente:");
        this.campo = new JTextField(10);
        this.buscar = new JButton("Buscar");
        
        introduce.add(this.etiq);
        introduce.add(this.campo);
        introduce.add(this.buscar);
        this.add(introduce);
        
        this.iniResultados();
        this.add(this.resultados);
                
        JPanel botones = new JPanel();
        this.modificar = new JButton("Modificar");
        this.modificar.addActionListener(new ClickCambioVentana());
        this.cancelar = new JButton("Cancelar");
        this.cancelar.addActionListener(new ClickCambioVentana());
        botones.add(this.modificar);
        botones.add(this.cancelar);
        
        this.add(botones);
        
        SpringUtilities.makeCompactGrid(this, 3, 1, 6, 6, 6, 6);
    }
    
    private void iniResultados() {
        JLabel  abiertos = new JLabel("Paquetes abiertos:");
        String[] titulos = {"IdPaquete", "DNI cliente", "Productos", "Fecha"};
        Object[][] resuls = {{"Paquete", "1271289D", "4", "12/1/13"},
        {"Paquete", "1271289D", "4", "12/1/13"},
        {"Paquete", "1271289D", "4", "12/1/13"},
        {"Paquete", "1271289D", "4", "12/1/13"},
        {"Paquete", "1271289D", "4", "12/1/13"}};
        
        ZebraJTable tabla = new ZebraJTable(resuls, titulos);
        JScrollPane scroll = new JScrollPane(tabla);
        
        this.resultados = new JPanel(new SpringLayout());
        this.resultados.add(abiertos);
        this.resultados.add(scroll);
        
        SpringUtilities.makeCompactGrid(this.resultados, 2, 1, 6, 6, 6, 6);
    }


    @Override
    public String claveVentana(String textoBoton) {
        if(this.modificar.getText().equals(textoBoton)) {
            return "NuevoPaquete";
        }
        else
            return "Inicio";
    }
}
