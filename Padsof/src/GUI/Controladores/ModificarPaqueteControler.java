/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controladores;

import GUI.Excepciones.SinRellenarEx;
import GUI.Excepciones.SinSeleccionarEx;
import GUI.Recursos.ZebraJTable;
import GUI.Ventanas.ModificarPaquete;
import GUI.Ventanas.NuevoPaquete;
import cat.quickdb.db.AdminBase;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import myexception.NoResultsExc;
import padsof.Booking;
import reserva.Paquete;

/**
 *
 * @author Jorge
 */
public class ModificarPaqueteControler implements ActionListener{
    private ModificarPaquete ventana;
    private Booking aplicacion;
    private List<Paquete> currPaqs;
    
    public ModificarPaqueteControler(ModificarPaquete ventana, Booking aplicacion) {
        this.ventana = ventana;
        this.aplicacion = aplicacion;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String pulsado = ((JButton)e.getSource()).getText();
        
        //Buscar los paquetes
        if(pulsado.equals(this.ventana.getBuscar().getText())) {
            try {
                buscarPaquetes();
            } catch (    SinRellenarEx | NoResultsExc ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
        
        //Modificar uno de los paquetes
        else if(pulsado.equals(this.ventana.getModificar().getText())) {
            try {
                Paquete paqSeleccionado = null;
                
                checkSeleccionado();
                paqSeleccionado = getPaqSeleccionado();
                
                actualizarEstados();
                NuevoPaquete nuevaVentana = (NuevoPaquete)this.ventana.cambiarVentana(this.ventana.claveVentana(pulsado));
                nuevaVentana.setPaqActual(paqSeleccionado);
                nuevaVentana.setClaveVentanaAnt("ModificarPaquete");
                nuevaVentana.actualizarEncabezado();
                nuevaVentana.mostrarInfo();
            } catch (    NoResultsExc | SinSeleccionarEx ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }
    
    /**
     * Se encarga de buscar los paquetes del cliente especificado. En caso de que 
     * el campo en el que se especifica el DNI est&aacute; sin rellenar, se lanza 
     * una excepci&oacute;n.<br/>Adem&aacute;s muestra los resultados.
     * @throws SinRellenarEx
     * @throws NoResultsExc 
     */
    public void buscarPaquetes() throws SinRellenarEx, NoResultsExc {
        if(this.ventana.getDniCliente().getText().equals("")) {
            throw new SinRellenarEx();
        }
        
        //Buscamos los paquetes por el DNI del cliente
        List<Paquete> paquetes = new ArrayList<Paquete>();
        paquetes = this.aplicacion.buscarPaquetesPorCliente(this.ventana.getDniCliente().getText());
        this.currPaqs = paquetes;
        
        //Obtenemos los paquetes abiertos
        List<Paquete> paqAbiertos = new ArrayList<>();
        for(Paquete paq : paquetes) {
            if(paq.getAbierto() == 1) {
                paqAbiertos.add(paq);
            }
        }
        
        //Quitamos la tabla anterior
        String[] titulos = {"IdPaquete", "DNI cliente", "Productos", "Estado"};
        ZebraJTable tablaAntes = (ZebraJTable)this.ventana.getTablaResults().getViewport().getView();
        this.ventana.getTablaResults().remove(tablaAntes);
        
        //Ponemos las filas
        Object[][] res = new Object[paqAbiertos.size()][4];
        for(int i = 0; i < paqAbiertos.size(); i++) {
            Paquete currPaq = paqAbiertos.get(i);
            
            res[i][0] = currPaq.getIdPaq();
            res[i][1] = currPaq.getCliente();
            res[i][2] = currPaq.getReservas().size();
            res[i][3] = (currPaq.getAbierto() == 1) ? "Abierto" : "Cerrado";
        }
        
        String[] elemsCombo = {"Abierto", "Cerrado"};
        JComboBox comboBox = new JComboBox(elemsCombo);
        
        //Ponemos la nueva tabla
        tablaAntes = new ZebraJTable(res,titulos, 3);
        this.ventana.getTablaResults().add(tablaAntes);
        tablaAntes.repaint();
        tablaAntes.setVisible(true);
        this.ventana.getTablaResults().setViewportView(tablaAntes);
        tablaAntes.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(comboBox));
    }
    
    
    /**
     * Se encarga de actualizar los estados de los paquetes mostrados en la tabla 
     * de resultados.
     */
    public void actualizarEstados() {
        ZebraJTable tabla = (ZebraJTable)this.ventana.getTablaResults().getViewport().getView();
        int numFilas = tabla.getModel().getRowCount();
        
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.aplicacion.getBookingDBName());
        for(int i = 0; i < numFilas; i++) {
            try {
                Integer paqId = (Integer)tabla.getRow(i)[0];
                String estado = (String)tabla.getRow(i)[3];
                
                Paquete paq = this.aplicacion.buscarPaquete(paqId);
                int abierto = (estado.equals("Abierto")) ? 1:0;
                paq.setAbierto(abierto);
                admin = paq.modificar(admin);
            } catch (    SQLException | ClassNotFoundException | NoResultsExc ex) {
                Logger.getLogger(ModificarPaqueteControler.class.getName()).log(Level.SEVERE, null, ex);
                admin.close();
                return;
            }
        }
        admin.close();
    }
    
    
    /**
     * El m&eacute;todo comprueba si hay alg&uacute;n paquete seleccionado entre 
     * todos los mostrados en la tabla de resultados. En caso de que no exista 
     * lanzamos una excepci&oacute;n.
     * @throws SinSeleccionarEx 
     */
    public void checkSeleccionado() throws SinSeleccionarEx {
        Paquete paqSelected = null;
        ZebraJTable tabla = (ZebraJTable) this.ventana.getTablaResults().getViewport().getView();
        int filaSel = tabla.getSelectedRow();
        
        //Miramos si no hay un paquete seleccionado
        if(filaSel == -1) {throw new SinSeleccionarEx();}
    }
    
    /**
     * Obtiene el paquete seleccionado entre todos los obtenidos tras una b&uacute;squeda. 
     * En caso de que no se encuentre en la BD, lanzamos una excepci&oacute;n.
     * <br/><br/>
     * <u>Nota</u>:<br/>
     * Antes de llamar a este m&eacute;todo hay que llamar a 'checkSeleccionado()'.
     * @return el paquete seleccionado
     * @throws SinSeleccionarEx
     * @throws NoResultsExc 
     */
    public Paquete getPaqSeleccionado() throws SinSeleccionarEx, NoResultsExc {        
        Paquete paqSelected = null;
        ZebraJTable tabla = (ZebraJTable) this.ventana.getTablaResults().getViewport().getView();
        int filaSel = tabla.getSelectedRow();
        
        //Obtenemos el paquete seleccionado
        Object[] fila = tabla.getRow(filaSel);
        paqSelected = this.aplicacion.buscarPaquete((Integer)fila[0]);
        
        return paqSelected;
    }
    
    /**
     * Se encarga de vaciar todos los campos de la ventana 'ModificarPaquete'.
     */
    public void resetearCampos() {
        //Vaciamos los campos del filtro
        this.ventana.getDniCliente().setText(null);
        
        
        //Ponemos una tabla vacia
        String[] titulos = {"IdPaquete", "DNI cliente", "Productos", "Estado"};
        ZebraJTable tablaAntigua = (ZebraJTable)this.ventana.getTablaResults().getViewport().getView();
        ZebraJTable tablaVacia = new ZebraJTable(null, titulos);
        
        this.ventana.getTablaResults().remove(tablaAntigua);
        this.ventana.getTablaResults().setViewportView(tablaVacia);
    }
}
