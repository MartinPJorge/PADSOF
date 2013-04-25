/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controladores;

import GUI.Excepciones.FechaInvalidaEx;
import GUI.Excepciones.SinRellenarEx;
import GUI.Excepciones.SinSeleccionarEx;
import GUI.Recursos.DateValidator;
import GUI.Recursos.ZebraJTable;
import GUI.Ventanas.AddHotel;
import GUI.Ventanas.NuevoPaquete;
import catalogo.InfoHotel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.table.DefaultTableModel;
import myexception.ClosedPackageExc;
import padsof.Booking;
import reserva.ReservaHotel;

/**
 * Clase controladora de la Ventana AddHotel
 *
 * @author Jorge Martín Pérez
 * @author Iván Márquez Pardo
 * @version 1.0
 */
public class AddHotelControler implements ActionListener{
    private AddHotel ventana;
    private Booking aplicacion;
    private List<InfoHotel> resBusquedaActual;
    private ReservaHotel reservaActual;
    
    /**
     * Constructor de la clase
     * @param ventana
     * @param aplicacion
     */
    public AddHotelControler(AddHotel ventana, Booking aplicacion) {
        this.ventana = ventana;
        this.aplicacion = aplicacion;
    }
    
    /**
     * Gestiona el comportamiento de la aplicaci&oacute;n atendiendo a los campos 
     * seleccionados.
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String pulsado = ((JButton)e.getSource()).getText();
        
        //Boton de busqueda
        if(pulsado.equals(this.ventana.getFiltar().getText())) {
            try {
                this.resBusquedaActual = buscarHoteles();
            } catch (SinRellenarEx ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
        else if(pulsado.equals(this.ventana.getFooter().getAdd().getText())) {
            try {
                addHotel();
                NuevoPaquete ventanaPaq = (NuevoPaquete)this.ventana.cambiarVentana(this.ventana.claveVentana(pulsado));
                ventanaPaq.mostrarInfoHotel();
            } catch (    SinSeleccionarEx | SinRellenarEx | FechaInvalidaEx ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
        else if(pulsado.equals(this.ventana.getCalcular().getText())) {
            try {
                calcularTotal();
            } catch (    SinSeleccionarEx | SinRellenarEx | FechaInvalidaEx ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
        else {
            this.ventana.cambiarVentana(this.ventana.claveVentana(pulsado));
        }
    }
    
    /**
     * Se encarga de realizar la b&uacute;squeda del hotel seg&uacute;n los 
     * valores introducidos y seleccionados en la ventana 'AddHotel' asociada.
     * @return lista de 'InfoHotel' con los resultados obtenidos
     * @throws SinRellenarEx 
     */
    public List<InfoHotel> buscarHoteles() throws SinRellenarEx{
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
        else if(tipoHab.equals("Matrimonio")) {
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
        
        return resHoteles;
    }
    
    /**
     * Se encarga de intentar a&ntilde;adir una reserva de hotel al paquete.
     * @throws SinSeleccionarEx
     * @throws SinRellenarEx
     * @throws FechaInvalidaEx 
     */
    public void addHotel() throws SinSeleccionarEx, SinRellenarEx, FechaInvalidaEx {
        ReservaHotel resHotel = this.generarReserva();
        
        try {
            this.ventana.getCurrentPaq().addReserva(resHotel);
        } catch (ClosedPackageExc ex) {
            Logger.getLogger(AddHotelControler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Se encarga de vaciar todos los campos de la ventana 'AddVuelo'.
     */
    public void resetearCampos() {
        //Vaciamos los campos del filtro
        this.ventana.getCiudad().setText(null);
        this.ventana.getPrecioNoche().setText(null);
        this.ventana.getEntrada().setText(null);
        this.ventana.getDias().setText(null);
        this.ventana.getFooter().getTotal().setText(null);
        
        //Si es la 1 vez que entramos, anadimos un boton vacio.
        if(this.ventana.getBotones().getButtonCount() == 3) {
            JRadioButton botInvisible = new JRadioButton("Invisible");
            this.ventana.getListBotones().add(botInvisible);
            this.ventana.getBotones().add(botInvisible);
            botInvisible.setVisible(false);
        }
        
        //Seleccionamos el boton invisible y deseleccionamos el resto.
        for(JRadioButton boton : this.ventana.getListBotones()) {
            if(boton.getText().endsWith("Invisible")) {
                this.ventana.getBotones().setSelected(boton.getModel(), true);
            }
            else {
                this.ventana.getBotones().setSelected(boton.getModel(), false);
            }
        }
        
        //Ponemos una tabla vacia
        String[] titulos = {"Nombre", "País","Ciudad", "★★★", "Simple", "Doble", 
                            "Triple", "Desayuno", "M.P", "P.C", "Características"};
        ZebraJTable tablaAntigua = (ZebraJTable)this.ventana.getResultados().getViewport().getView();
        ZebraJTable tablaVacia = new ZebraJTable(null, titulos);
        
        this.ventana.getResultados().remove(tablaAntigua);
        this.ventana.getResultados().setViewportView(tablaVacia);
    }
    
    /**
     * Se encarga de mirar si todos los elementos necesarios para llevar a cabo 
     * la reserva est&acute;n seleccionados. En caso contrario el m&eacute;todo 
     * lanza las respectivas excepciones.
     * @throws SinRellenarEx
     * @throws SinSeleccionarEx
     * @throws FechaInvalidaEx 
     */
    public void checkSelectedEverything() throws SinRellenarEx, SinSeleccionarEx, FechaInvalidaEx {
        JRadioButton botonSelected = null;
        String dias = this.ventana.getDias().getText();
        String entrada = this.ventana.getEntrada().getText();
        String tipoHab = (String)this.ventana.getTipoHab().getSelectedItem();
        ZebraJTable tabla = (ZebraJTable)this.ventana.getResultados().getViewport().getView();
        int filaSel = tabla.getSelectedRow();
        
        //Miramos si hay elementos por rellenar o seleccionar
        if(dias.equals("") || entrada.equals("") || tipoHab.equals("")) {throw new SinRellenarEx();}
        if(filaSel == -1){throw new SinSeleccionarEx();}
        
        //Comprobamos que la fecha introducida es valida
        DateValidator validator = new DateValidator();
        if(validator.validate(entrada) == false) {
            throw new FechaInvalidaEx();
        }
        
        //Buscamos el radio boton escogido (si es que lo hay)
        for(JRadioButton boton : this.ventana.getListBotones()) {
            if(this.ventana.getBotones().isSelected(boton.getModel())
                    && boton.getText().equals("Invisible") == false) {
                botonSelected = boton;
                break;
            }
        }
        if(botonSelected == null) {
            throw new SinSeleccionarEx();
        }
    }
    
    /**
     * Calcula el total que supondr&iacute;a llevar a cabo una reserva con los 
     * datos especificados, y muestra dicha cantidad en el cuadro de texto bajo 
     * el nombre de 'Total'.
     * @throws SinRellenarEx
     * @throws SinSeleccionarEx
     * @throws FechaInvalidaEx 
     */
    public void calcularTotal() throws SinRellenarEx, SinSeleccionarEx, FechaInvalidaEx {
        ReservaHotel resHotel = this.generarReserva();
        this.ventana.getFooter().getTotal().setText(""+resHotel.getPrecio());
    }
    
    /**
     * Genera una reserva de hotel a partir de los datos especificados por el usuario.
     * @return la reserva generada
     * @throws SinRellenarEx
     * @throws SinSeleccionarEx
     * @throws FechaInvalidaEx 
     */
    private ReservaHotel generarReserva() throws SinRellenarEx, SinSeleccionarEx, FechaInvalidaEx {
        JRadioButton botonSelected = null;
        String dias = this.ventana.getDias().getText();
        String entrada = this.ventana.getEntrada().getText();
        String tipoHab = (String)this.ventana.getTipoHab().getSelectedItem();
        ZebraJTable tabla = (ZebraJTable)this.ventana.getResultados().getViewport().getView();
        int filaSel = tabla.getSelectedRow();
        
        checkSelectedEverything(); //Si hay elementos vacios saltaran excepciones
        
        //Buscamos el radio boton escogido
        for(JRadioButton boton : this.ventana.getListBotones()) {
            if(this.ventana.getBotones().isSelected(boton.getModel())
                    && boton.getText().equals("Invisible") == false) {
                botonSelected = boton;
                break;
            }
        }
        
        //Creamos la reserva la metemos en el paquete actual
        int dia = DateValidator.obtainDay(entrada);
        int mes = DateValidator.obtainMonth(entrada);
        int year = DateValidator.obtainYear(entrada);
        
        //Asignamos el tipo de habitacion que reconoce la aplicacion
        if(tipoHab.equals("Individual")) {
            tipoHab = "simple";
        }
        else if(tipoHab.equals("Matrimonio")) {
            tipoHab = "doble";
        }
        else if(tipoHab.equals("Triple")) {
            tipoHab = "triple";
        }
        
        //Asignamos el tipo de suplemento
        if(botonSelected.getText().equals("Media pensión")) {
            botonSelected.setText("supMP");
        }
        else if(botonSelected.getText().equals("Pensión completa")) {
            botonSelected.setText("supPC");
        }
        else if(botonSelected.getText().equals("Desayuno")) {
            botonSelected.setText("supD");
        }
        
        ReservaHotel reserva = new ReservaHotel(dia, mes, year, tipoHab, 
                botonSelected.getText(), Integer.parseInt(dias), this.resBusquedaActual.get(filaSel));
        reserva.setEstado("10%");
        
        return reserva;
    }
}
