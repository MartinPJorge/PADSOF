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
import GUI.Ventanas.AddVuelo;
import GUI.Ventanas.NuevoPaquete;
import es.uam.eps.pads.services.InvalidParameterException;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import myexception.ClosedPackageExc;
import reserva.ReservaVuelo;
import reserva.Vuelos;

/**
 * Clase controladora de la Ventana AddVuelo
 *
 * @author Jorge Martín Pérez
 * @author Iván Márquez Pardo
 * @version 1.0
 */
public class AddVueloControler implements ActionListener{
    private AddVuelo ventana;
    private List<String> localizadores;
    
    public AddVueloControler(AddVuelo ventana) {
        this.ventana = ventana;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //Cambiamos de ventana
        String pulsado = ((JButton)e.getSource()).getText();
        
        if(pulsado.equals(this.ventana.getFooter().getAdd().getText())) {
            try {
                //Intentamos meter el vuelo en el paquete
                addVuelo(this.localizadores);
                
                //Cambiamos de ventana
                this.ventana.cambiarVentana(this.ventana.claveVentana(pulsado));
                NuevoPaquete ventanaPaq = (NuevoPaquete)this.ventana.cambiarVentana(this.ventana.claveVentana(pulsado));
                ventanaPaq.mostrarInfo();
            } catch (SinRellenarEx ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            } catch (SinSeleccionarEx ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                return;
            }
        }
        else if(pulsado.equals(this.ventana.getFiltrar().getText())) {
            try {
                filtrarResultados();
            } catch (FechaInvalidaEx ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
        
        else {
            NuevoPaquete ventanaPaq = (NuevoPaquete)this.ventana.cambiarVentana(this.ventana.claveVentana(pulsado));
            ventanaPaq.mostrarInfo();
        }
    }
    
    /**
     * Se encarga de realizar la b&uacute;squeda de los vuelos a partir de los 
     * datos introducidos, y adem&aacute;s llama a la funci&oacute;n de mostrar 
     * los resultados en la tabla.
     * @throws FechaInvalidaEx
     * @throws ParseException 
     */
    public void filtrarResultados() throws FechaInvalidaEx, ParseException {
        String strHIda = this.ventana.gethSalida().getText();
        String strHLlegada = this.ventana.gethLlegada().getText();
        
        //Comprobamos las fechas introducidas
        DateValidator validator = new DateValidator();
        if(validator.validate(strHIda) == false || validator.validate(strHLlegada) == false){
            throw new FechaInvalidaEx();
        }
        
        
        //Obtenemos los campos
        int dia = DateValidator.obtainYear(strHIda);
        int mes =  DateValidator.obtainMonth(strHIda);
        int year = DateValidator.obtainDay(strHIda);
        
        GregorianCalendar cal = new GregorianCalendar(DateValidator.obtainYear(strHIda),
                DateValidator.obtainMonth(strHIda) - 1, DateValidator.obtainDay(strHIda) + 1);
        Date ida = cal.getTime();
        
        int diaca = cal.get(Calendar.DAY_OF_MONTH);
        int mesaco = cal.get(Calendar.MONTH);
        int aniaco = cal.get(Calendar.YEAR);
        
        
        cal = new GregorianCalendar(DateValidator.obtainYear(strHLlegada),
                DateValidator.obtainMonth(strHLlegada) - 1,DateValidator.obtainDay(strHLlegada));
        Date vuelta = cal.getTime();
        
        diaca = cal.get(Calendar.DAY_OF_MONTH);
        mesaco = cal.get(Calendar.MONTH);
        aniaco = cal.get(Calendar.YEAR);
        
        
        
        String salida = this.ventana.getSalida().getText();
        String llegada = this.ventana.getIda().getText();
        
        try {
            this.localizadores = Vuelos.obtenerVuelos(salida, llegada, ida, vuelta);
        } catch (InvalidParameterException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return;
        }
        
        mostrarResultados(this.localizadores);
    }
    
    
    /**
     * Muestra la informaci&oacute;n de los vuelos con los localizadores especificados.
     * @param localizadores 
     */
    public void mostrarResultados(List<String> localizadores) {
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy/dd/MM");
        ZebraJTable resultsTable = (ZebraJTable)this.ventana.getScrollResults().getViewport().getView();
        resultsTable.deleteRows();
        
        for(int i = 0; i < localizadores.size(); i++) {
            //Obtenemos los datos del resultado
            String origen = Vuelos.getOrigen(localizadores.get(i));
            String destino = Vuelos.getDestino(localizadores.get(i));
            Date salidaca = Vuelos.getSalida(localizadores.get(i));
            
            //Fecha y precio
            String fecha = dateFormater.format(Vuelos.getSalida(localizadores.get(i)));
            String precio = Double.toString(Vuelos.getPrecio(localizadores.get(i)));
            
            //Hora llegada
            Calendar cal = new GregorianCalendar();
            cal.setTime(Vuelos.getLlegada(localizadores.get(i)));
            String llegada = DateValidator.formatHour(cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE));
            
            //Hora salida
            cal.setTime(Vuelos.getSalida(localizadores.get(i)));
            String salida = DateValidator.formatHour(cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE));
            
            //Insertamos la fila
            String[] fila = {origen,destino,fecha,salida,llegada,precio};
            
            DefaultTableModel modelo = (DefaultTableModel)resultsTable.getModel();
            modelo.insertRow(i, fila);
            resultsTable.repaint();
        }
    }
    
    /**
     * Obtiene el vuelo seleccionado, y lo a&ntilde;ade al paquete.
     * @param localizadores
     * @throws SinRellenarEx
     * @throws SinSeleccionarEx 
     */
    public void addVuelo(List<String> localizadores) throws SinRellenarEx, SinSeleccionarEx {
        String nombre = ((JTextField)this.ventana.getDetalles().getTextos().get(0)).getText();
        String apellidos = ((JTextField)this.ventana.getDetalles().getTextos().get(1)).getText();
        String dni = ((JTextField)this.ventana.getDetalles().getTextos().get(2)).getText();
        
        ZebraJTable resultsTable = (ZebraJTable)this.ventana.getScrollResults().getViewport().getView();
        
        //Miramos si hay campos sin rellenar
        if(nombre.equals("") || apellidos.equals("") || dni.equals("")) {
            throw new SinRellenarEx();
        }
        
        //Miramos si no hemos seleccionado un vuelo
        int filaSeleccionada = resultsTable.getSelectedRow();
        if(filaSeleccionada == -1) {
            throw new SinSeleccionarEx();
        }
        
        //Obtenemos la info. del vuelo seleccionado
        Object[] fila = resultsTable.getRow(filaSeleccionada);
        int dia = DateValidator.obtainDayV2((String)fila[2]);
        int mes = DateValidator.obtainMonthV2((String)fila[2]);
        int year = DateValidator.obtainYearV2((String)fila[2]);
        double precio = Double.parseDouble((String)fila[5]);
        
        ReservaVuelo reservaVuelo = new ReservaVuelo(dia, mes, year, nombre+" "+apellidos, 
           dni, localizadores.get(filaSeleccionada),  precio);
        reservaVuelo.setEstado("Confirmado");
        
        //Metemos la reserva en el paquete
        try {
            this.ventana.getCurrentPaq().addReserva(reservaVuelo);
        } catch (ClosedPackageExc ex) {
            Logger.getLogger(AddVueloControler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Se encarga de vaciar todos los campos de la ventana 'AddVuelo'.
     */
    public void resetearCampos() {
        //Vaciamos los campos del filtro
        this.ventana.getSalida().setText(null);
        this.ventana.gethSalida().setText(null);
        this.ventana.gethLlegada().setText(null);
        this.ventana.getIda().setText(null);
        
        //Ponemos una tabla vacia
        String[] titulos = {"Origen","Destino", "Fecha", "H.salida", "H.llegada","Precio"};
        ZebraJTable tablaAntigua = (ZebraJTable)this.ventana.getScrollResults().getViewport().getView();
        ZebraJTable tablaVacia = new ZebraJTable(null, titulos);
        
        this.ventana.getScrollResults().remove(tablaAntigua);
        this.ventana.getScrollResults().setViewportView(tablaVacia);
        
        //Vaciamos los campos de los detalles
        for(Component cuadro : this.ventana.getDetalles().getTextos()) {
            ((JTextField)cuadro).setText(null);
        }
    }
}
