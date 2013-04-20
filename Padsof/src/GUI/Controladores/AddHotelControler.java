/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controladores;

import GUI.Excepciones.SinRellenarEx;
import GUI.Recursos.ZebraJTable;
import GUI.Ventanas.AddHotel;
import catalogo.InfoHotel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import padsof.Booking;

/**
 *
 * @author Jorge
 */
public class AddHotelControler implements ActionListener{
    private AddHotel ventana;
    public Booking aplicacion;
    
    public AddHotelControler(AddHotel ventana, Booking aplicacion) {
        this.ventana = ventana;
        this.aplicacion = aplicacion;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String pulsado = ((JButton)e.getSource()).getText();
        
        //Boton de busqueda
        if(pulsado.equals(this.ventana.getFiltar().getText())) {
            try {
                buscarHoteles();
            } catch (SinRellenarEx ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
        else if(pulsado.equals(this.ventana.getFooter().getAdd().getText())) {
            /*try {
                addHotel();
            }
            catch(SinRellenarEx ex) {
                
            }
            catch(SinSeleccionarEx ex) {
                
            }*/
        }
    }
    
    public void buscarHoteles() throws SinRellenarEx{
        String ciudad, tipoHab;
        Double precio;
        Integer estrellas;
        
        try {
            ciudad = this.ventana.getCiudad().getText();
            tipoHab = (String)this.ventana.getTipoHab().getSelectedItem();
            precio = Double.parseDouble(this.ventana.getPrecioNoche().getText());
            estrellas = (Integer)this.ventana.getEstrellas().getSelectedItem();
        }
        catch(NumberFormatException ex) {
            throw new SinRellenarEx();
        }
        
        //Comprobamos si hay campos sin rellenar
        if(ciudad.equals("") || tipoHab.equals("") || precio==null || estrellas==null) {
            throw new SinRellenarEx();
        }
        
        //Ejecutamos la busqueda dependiendo del tipo de habitacion
        String[] titulos = {"Nombre", "País","Ciudad", "★★★", "Simple", "Doble", 
                            "Triple", "Desayuno", "M.P", "P.C", "Características"};
        List<InfoHotel> resHoteles = new ArrayList<InfoHotel>();
        
        if(tipoHab.equals("Individual")) {
            resHoteles = this.aplicacion.getCatalogoHotel().buscaHotel(null, null, ciudad, estrellas, precio, -1, -1, -1, -1, -1, null);
        }
        else if(tipoHab.equals("Doble")) {
            resHoteles = this.aplicacion.getCatalogoHotel().buscaHotel(null, null, ciudad, estrellas, -1, precio, -1, -1, -1, -1, null);
        }
        else if(tipoHab.equals("Triple")) {
            resHoteles = this.aplicacion.getCatalogoHotel().buscaHotel(null, null, ciudad, estrellas, -1, -1, precio, -1, -1, -1, null);
        }
        
        //Obtenemos la tabla e inicializamos el array de filas de hoteles.
        ZebraJTable tabla = (ZebraJTable)this.ventana.getResultados().getViewport().getView();
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        Object[][] filas = new Object[resHoteles.size()][titulos.length];
        
        int i = 0;
        for(InfoHotel info : resHoteles) {
            filas[i][0] = (String) info.getNombre();
            filas[i][1] = (String) info.getPais();
            filas[i][2] = (String) info.getCiudad();
            filas[i][3] = (int) info.getCategoria();
            filas[i][4] = (double) info.getPrecioSimple();
            filas[i][5] = (double) info.getPrecioDoble();
            filas[i][6] = (double) info.getPrecioTriple();
            filas[i][7] = (double) info.getSupDesayuno();
            filas[i][8] = (double) info.getSupMP();
            filas[i][9] = (double) info.getSupPC();
            filas[i][10] = (String) info.getCaracteristicas();
            
            i++;
        }
        
        //Actualizamos la tabla
        this.ventana.getResultados().remove(tabla);
        this.ventana.getResultados().setViewportView(new ZebraJTable(filas, titulos));
        this.ventana.ajustarTamCols();
    }
    
    public void addHotel() {
        
    }
}
