/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controladores;

import GUI.Excepciones.FechaInvalidaEx;
import GUI.Excepciones.NoEsAncianoException;
import GUI.Excepciones.SinRellenarEx;
import GUI.Excepciones.SinSeleccionarEx;
import GUI.Recursos.DateValidator;
import GUI.Recursos.ZebraJTable;
import GUI.Ventanas.AddViajOrg;
import GUI.Ventanas.NuevoPaquete;
import catalogo.InfoViajOrg;
import catalogo.InfoViajeIMSERSO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import myexception.ClosedPackageExc;
import myexception.NoResultsExc;
import padsof.Booking;
import persona.Cliente;
import reserva.ReservaViajOrg;
import reserva.ReservaViajeIMSERSO;

/**
 * Clase controladora de la Ventana AddViajOrg
 *
 * @author Jorge Martín Pérez
 * @author Iván Márquez Pardo
 * @version 1.0
 */
public class AddViajOrgControler implements ActionListener{
    private AddViajOrg ventana;
    private Booking aplicacion;
    private List<InfoViajOrg> viajsOrgAct;
    private List<InfoViajeIMSERSO> viajsIMSAct;
    private String ultimaBusqueda;  //VO - VIMS
    
    /**
     * 
     * @param ventana
     * @param aplicacion
     */
    public AddViajOrgControler(AddViajOrg ventana, Booking aplicacion) {
        this.ventana = ventana;
        this.aplicacion = aplicacion;
        this.ultimaBusqueda = null;
    }
    
    /**
     * 
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        
        String pulsado = ((JButton)e.getSource()).getText();
        
        //Boton de busqueda
        if(pulsado.equals(this.ventana.getBuscar().getText())) {
            if(((String)this.ventana.getTipoViaje().getSelectedItem()).equals("Organizado")) {
                try {
                    this.buscarViajeOrg();
                    mostrarResultados("VO");
                } catch (        SinSeleccionarEx | SinRellenarEx | FechaInvalidaEx ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
            else {
                try {
                    this.buscarViajeIMSERSO();
                    mostrarResultados("VIMS");
                } catch (            NoEsAncianoException | SinSeleccionarEx | SinRellenarEx | FechaInvalidaEx | NoResultsExc ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        }
        //Boton de añadir servicio
        else if(pulsado.equals(this.ventana.getFooter().getAdd().getText())) {
            if(this.ultimaBusqueda == null) {
                JOptionPane.showMessageDialog(null, "No has realizado ninguna búsqueda.");
                return;
            }
            
            if(this.ultimaBusqueda.equals("VO")) {
                ReservaViajOrg reserva;
                try {
                    reserva = reservaViajOrg();
                } catch (        SinSeleccionarEx | SinRellenarEx | FechaInvalidaEx ex) {
                    JOptionPane.showMessageDialog(null, ex);
                    return;
                }
                
                try {
                    this.ventana.getCurrPaq().addReserva(reserva);
                    NuevoPaquete nuevaVentana = (NuevoPaquete)this.ventana.cambiarVentana(this.ventana.claveVentana(pulsado));
                    this.resetearCampos();
                    nuevaVentana.mostrarViajOrg();
                } catch (ClosedPackageExc ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
          
            else  if(this.ultimaBusqueda.equals("VIMS")){
                ReservaViajeIMSERSO reserva;
                try {
                    reserva = this.reservaViajeIMSERSO();
                } catch (        SinSeleccionarEx | SinRellenarEx | FechaInvalidaEx ex) {
                    JOptionPane.showMessageDialog(null, ex);
                    return;
                }
                
                try {
                    this.ventana.getCurrPaq().addReserva(reserva);
                    NuevoPaquete nuevaVentana = (NuevoPaquete)this.ventana.cambiarVentana(this.ventana.claveVentana(pulsado));
                    this.resetearCampos();
                    nuevaVentana.mostrarViajOrg();
                } catch (ClosedPackageExc ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        }
        
        
        //Boton de calcular precio
        else if(pulsado.equals(this.ventana.getCalcular().getText())) {
            if(this.ultimaBusqueda.equals("VO")) {
                ReservaViajOrg reserva = null;
                try {
                    reserva = reservaViajOrg();
                } catch (        SinSeleccionarEx | SinRellenarEx | FechaInvalidaEx ex) {
                    JOptionPane.showMessageDialog(null, ex);
                    return;
                }
                
                //Rellenamos el texto con el precio
                JTextField texto = (JTextField)this.ventana.getFooter().getTotal();
                texto.setText("" + reserva.getPrecio());
            }
            
            else  if(this.ultimaBusqueda.equals("VIMS")){
                ReservaViajeIMSERSO reserva = null;
                try {
                    reserva = reservaViajeIMSERSO();
                } catch (        SinSeleccionarEx | SinRellenarEx | FechaInvalidaEx ex) {
                    JOptionPane.showMessageDialog(null, ex);
                    return;
                }

                //Rellenamos el texto con el precio
                JTextField texto = (JTextField)this.ventana.getFooter().getTotal();
                texto.setText("" + reserva.getPrecio());
            }
        }

        
        else {
            this.ventana.cambiarVentana(this.ventana.claveVentana(pulsado));
            this.resetearCampos();
        }
        
    }
    
    /**
     * Se encarga de comprobar si los campos solicitados est&aacute;n rellenados, 
     * y lanza las correspondientes excepciones en caso contrario.<br/>
     * Dependiendo del argumento el m&eacute;todo comprobar&aacute; solo los campos 
     * de b&uacute;squeda, o tambi&eacute;n los campos de detalles.
     * @param checkNPersonas 
     * @throws SinSeleccionarEx
     * @throws SinRellenarEx
     * @throws FechaInvalidaEx  
     */
    public void checkDatos(boolean checkNPersonas) throws SinSeleccionarEx, SinRellenarEx, FechaInvalidaEx {
        String tipoViaje = (String)this.ventana.getTipoViaje().getSelectedItem();
        String noches = this.ventana.getNoches().getText();
        String precio = this.ventana.getPrecio().getText();
        
        //Comprobamos si estan rellenos los campos de busqueda
        if(tipoViaje.equals("") || noches.equals("") || precio.equals("")) {
            throw new SinRellenarEx();
        }
        
        //------ Check selection --------
        if(!checkNPersonas) {return;}
        
        //Hay fila seleccionada
        ZebraJTable tabla = (ZebraJTable)this.ventana.getResultados().getViewport().getView();
        int filaSel = tabla.getSelectedRow();
        if(filaSel == -1) {throw new SinSeleccionarEx();}
        
        //Hay un num. de personas especificado y una fecha correcta
        JTextField numPersonas = (JTextField)this.ventana.getPersonas();
        JTextField fecha = (JTextField)this.ventana.getFecha();
        
        DateValidator validator = new DateValidator();
        if(validator.validate(fecha.getText()) == false) {throw new FechaInvalidaEx();}
        
        if(numPersonas.getText().equals("") || fecha.getText().equals("")) {
            throw new SinRellenarEx();
        }
    }
    
    /**
     * Muestra los resultados de la &uacute;ltima b&uacute;squeda realizada.
     * @param tipoViaje - "VO",organizado____"VIMS",IMSERSO
     */
    public void mostrarResultados(String tipoViaje) {
        if(tipoViaje.equals("VO")) {
            String[] titulos = {"Nombre", "Precio", "Días", "Noches", "F.Salida", "Loc.Salida",
                "Localidades", "Descripción"};
            Object[][] resultados = new Object[this.viajsOrgAct.size()][titulos.length];
            
            for(int i = 0; i < this.viajsOrgAct.size(); i++) {
                InfoViajOrg info = this.viajsOrgAct.get(i);
                
                resultados[i][0] = info.getNombre();
                resultados[i][1] = ""+info.getPrecio();
                resultados[i][2] = ""+info.getDias();
                resultados[i][3] = ""+info.getNoches();
                resultados[i][4] = info.getFechasSalida();
                resultados[i][5] = info.getLocalidadSalida();
                resultados[i][6] = info.getLocalidades();
                resultados[i][7] = info.getDescripcion();
            }
            
            //Actualizamos la tabla
            ZebraJTable tabla = (ZebraJTable)this.ventana.getResultados().getViewport().getView();
            this.ventana.getResultados().remove(tabla);
            this.ventana.getResultados().setViewportView(new ZebraJTable(resultados, titulos));
        }
        
        else {
            String[] titulos = {"Nombre", "Precio", "Días", "Noches", "F.Salida", "Loc.Salida",
                "Localidades", "Descripción"};
            Object[][] resultados = new Object[this.viajsIMSAct.size()][titulos.length];
            
            for(int i = 0; i < this.viajsIMSAct.size(); i++) {
                InfoViajeIMSERSO info = this.viajsIMSAct.get(i);
                
                resultados[i][0] = info.getNombre();
                resultados[i][1] = ""+info.getPrecio();
                resultados[i][2] = ""+info.getDias();
                resultados[i][3] = ""+info.getNoches();
                resultados[i][4] = info.getFechaSalida();
                resultados[i][5] = info.getLocSalida();
                resultados[i][6] = info.getLocalidades();
                resultados[i][7] = info.getDescripcion();
            }
            
            //Actualizamos la tabla
            ZebraJTable tabla = (ZebraJTable)this.ventana.getResultados().getViewport().getView();
            this.ventana.getResultados().remove(tabla);
            this.ventana.getResultados().setViewportView(new ZebraJTable(resultados, titulos));
        }
    }
    
    /**
     * Busca un viaje organizado a partir de los datos proporcionados, y cambia 
     * el valor de la variable viajsOrgAct por los resultados de la b&uacute;squeda.
     * @throws SinSeleccionarEx 
     * @throws SinRellenarEx
     * @throws FechaInvalidaEx  
     */
    public void buscarViajeOrg() throws SinSeleccionarEx, SinRellenarEx, FechaInvalidaEx {
        checkDatos(false);
        this.ultimaBusqueda = "VO";
        double precio = Double.parseDouble(this.ventana.getPrecio().getText());
        int noches = Integer.parseInt(this.ventana.getNoches().getText());

        this.viajsOrgAct = 
             this.aplicacion.getCatalogoViajOrg().buscarViajeOrg(null, -1, 
                noches, precio, null, null);
    }
    
    /**
     * Busca un viaje del IMSERSO a partir de los datos proporcionados, y cambia 
     * el valor de la variable viajsOrgAct por los resultados de la b&uacute;squeda.
     * @throws NoEsAncianoException 
     * @throws SinSeleccionarEx 
     * @throws NoResultsExc
     * @throws SinRellenarEx
     * @throws FechaInvalidaEx  
     */
    public void buscarViajeIMSERSO() throws NoEsAncianoException, SinSeleccionarEx, SinRellenarEx, FechaInvalidaEx, NoResultsExc {
        checkDatos(false);
        this.ultimaBusqueda = "VIMS";

        double precio = Double.parseDouble(this.ventana.getPrecio().getText());
        int noches = Integer.parseInt(this.ventana.getNoches().getText());

        this.viajsIMSAct = 
                this.aplicacion.getCatalogoViajIMSERSO().buscarViajeOrg(
                null, -1, noches, precio, null, null);

        
        //Miramos si el cliente es un anciano y puede llevara cabo la operacion
        try {
            Cliente c = this.aplicacion.buscarCliente(this.ventana.getCurrPaq().getCliente());
            int nacimiento = DateValidator.obtainYear(c.getFechaNac());
            if(Calendar.getInstance().get(Calendar.YEAR) - nacimiento < 65) {
                throw new NoEsAncianoException();
            }
        } catch (NoResultsExc ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    /**
     * Devuelve una nueva reserva de viaje organizado bas&aacute;ndose en los 
     * datos seleccionados.
     * @return nueva reserva de viaje organizado
     * @throws SinSeleccionarEx 
     * @throws SinRellenarEx
     * @throws FechaInvalidaEx  
     */
    public ReservaViajOrg reservaViajOrg() throws SinSeleccionarEx, SinRellenarEx, FechaInvalidaEx {
        String fecha = null;
        int personas = 0;
        InfoViajOrg info = null;
        
        checkDatos(true);
        personas = Integer.parseInt(this.ventana.getPersonas().getText());

        ZebraJTable tabla = (ZebraJTable)this.ventana.getResultados().getViewport().getView();
        int filaSel = tabla.getSelectedRow();
        info = this.viajsOrgAct.get(filaSel);

        fecha = this.ventana.getFecha().getText();

        ReservaViajOrg reserva = new ReservaViajOrg(DateValidator.obtainDay(fecha), DateValidator.obtainMonth(fecha), 
                DateValidator.obtainYear(fecha), personas, info);
        reserva.setEstado("10%");
        
        return reserva;
    }
    
    /**
     * Devuelve una nueva reserva de viaje del IMSERSO bas&aacute;ndose en los 
     * datos seleccionados.
     * @return nueva reserva de viaje organizado
     * @throws SinSeleccionarEx
     * @throws SinRellenarEx
     * @throws FechaInvalidaEx  
     */
    public ReservaViajeIMSERSO reservaViajeIMSERSO() throws SinSeleccionarEx, SinRellenarEx, FechaInvalidaEx {
        String fecha = null;
        int personas = 0;
        InfoViajeIMSERSO info = null;
        
        checkDatos(true);
        personas = Integer.parseInt(this.ventana.getPersonas().getText());

        ZebraJTable tabla = (ZebraJTable)this.ventana.getResultados().getViewport().getView();
        int filaSel = tabla.getSelectedRow();
        info = this.viajsIMSAct.get(filaSel);

        fecha = this.ventana.getFecha().getText();

        ReservaViajeIMSERSO reserva = new ReservaViajeIMSERSO(DateValidator.obtainDay(fecha), DateValidator.obtainMonth(fecha), 
                DateValidator.obtainYear(fecha), personas, info);
        reserva.setEstado("10%");
        
        return reserva;
    }
    
    /**
     * Se encarga de vaciar todos los campos de la ventana 'AddVuelo'.
     */
    public void resetearCampos() {
        //Vaciamos los campos de texto
        this.ventana.getPrecio().setText(null);
        this.ventana.getNoches().setText(null);
        this.ventana.getFecha().setText(null);
        this.ventana.getPersonas().setText(null);
        this.ventana.getFooter().getTotal().setText(null);
        
        this.ultimaBusqueda = null;
        
        //Ponemos una tabla vacia
        String[] titulos = {"Nombre", "Precio", "Días", "Noches", "F.Salida", "Loc.Salida",
        "Localidades", "Descripción"};
        ZebraJTable tablaAntigua = (ZebraJTable)this.ventana.getResultados().getViewport().getView();
        ZebraJTable tablaVacia = new ZebraJTable(null, titulos);
        
        this.ventana.getResultados().remove(tablaAntigua);
        this.ventana.getResultados().setViewportView(tablaVacia);
    }
}
