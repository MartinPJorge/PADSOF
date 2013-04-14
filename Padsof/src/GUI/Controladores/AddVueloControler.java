/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controladores;

import GUI.Excepciones.FechaInvalidaEx;
import GUI.Excepciones.SinRellenarEx;
import GUI.Excepciones.SinSeleccionarEx;
import GUI.Recursos.DateValidator;
import GUI.Ventanas.AddVuelo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
 *
 * @author Jorge
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
            this.ventana.cambiarVentana(this.ventana.claveVentana(pulsado));
        }
    }
    
    
    public void filtrarResultados() throws FechaInvalidaEx, ParseException {
        String strHIda = this.ventana.gethSalida().getText();
        String strHLlegada = this.ventana.gethLlegada().getText();
        
        //Comprobamos las fechas introducidas
        DateValidator validator = new DateValidator();
        if(validator.validate(strHIda) == false || validator.validate(strHLlegada) == false){
            throw new FechaInvalidaEx();
        }
        
        
        //Obtenemos los campos
        GregorianCalendar cal = new GregorianCalendar(DateValidator.obtainYear(strHIda),
                DateValidator.obtainMonth(strHIda), DateValidator.obtainDay(strHIda));
        Date ida = cal.getTime();
        
        cal = new GregorianCalendar(DateValidator.obtainYear(strHLlegada),
                DateValidator.obtainMonth(strHLlegada), DateValidator.obtainDay(strHLlegada));
        Date vuelta = cal.getTime();
        
        String salida = this.ventana.getSalida().getText();
        String llegada = this.ventana.getIda().getText();
        
                
        
        this.localizadores = Vuelos.obtenerVuelos(salida, llegada, ida, vuelta);
        mostrarResultados(this.localizadores);
    }
    
    
    public void mostrarResultados(List<String> localizadores) {
        SimpleDateFormat dateFormater = new SimpleDateFormat("dd/mm/YYYY");
        this.ventana.getResultados().deleteRows();
        
        for(int i = 0; i < localizadores.size(); i++) {
            //Obtenemos los datos del resultado
            String origen = Vuelos.getOrigen(localizadores.get(i));
            String destino = Vuelos.getDestino(localizadores.get(i));
            String salida = dateFormater.format(Vuelos.getSalida(localizadores.get(i)));
            String llegada = dateFormater.format(Vuelos.getLlegada(localizadores.get(i)));
            String precio = Double.toString(Vuelos.getPrecio(localizadores.get(i)));
            
            //Insertamos la fila
            String[] fila = {origen,destino,salida,llegada,precio};
            
            DefaultTableModel modelo = (DefaultTableModel)this.ventana.getResultados().getModel();
            modelo.insertRow(i, fila);
            this.ventana.getResultados().repaint();
        }
    }
    
    
    public void addVuelo(List<String> localizadores) throws SinRellenarEx, SinSeleccionarEx {
        String nombre = ((JTextField)this.ventana.getDetalles().getTextos().get(0)).getText();
        String apellidos = ((JTextField)this.ventana.getDetalles().getTextos().get(1)).getText();
        String dni = ((JTextField)this.ventana.getDetalles().getTextos().get(2)).getText();
        
        //Miramos si hay campos sin rellenar
        if(nombre.equals("") || apellidos.equals("") || dni.equals("")) {
            throw new SinRellenarEx();
        }
        
        //Miramos si no hemos seleccionado un vuelo
        int filaSeleccionada = this.ventana.getResultados().getSelectedRow();
        if(filaSeleccionada == -1) {
            throw new SinSeleccionarEx();
        }
        
        //Obtenemos la info. del vuelo seleccionado
        Object[] fila = this.ventana.getResultados().getRow(filaSeleccionada);
        int dia = DateValidator.obtainDay((String)fila[2]);
        int mes = DateValidator.obtainMonth((String)fila[2]);
        int year = DateValidator.obtainYear((String)fila[2]);
        double precio = Double.parseDouble((String)fila[4]);
        
        ReservaVuelo reservaVuelo = new ReservaVuelo(dia, mes, year, nombre+" "+apellidos, 
           dni, localizadores.get(filaSeleccionada),  precio);
        
        //Metemos la reserva en el paquete
        try {
            this.ventana.getCurrentPaq().addReserva(reservaVuelo);
        } catch (ClosedPackageExc ex) {
            Logger.getLogger(AddVueloControler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
