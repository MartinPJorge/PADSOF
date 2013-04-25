/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controladores;

import GUI.Excepciones.SinSeleccionarEx;
import GUI.Recursos.ZebraJTable;
import GUI.Ventanas.AddHotel;
import GUI.Ventanas.AddViajOrg;
import GUI.Ventanas.AddVuelo;
import GUI.Ventanas.ModificarPaquete;
import GUI.Ventanas.NuevoPaquete;
import GUI.Ventanas.Ventana;
import cat.quickdb.db.AdminBase;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import padsof.Booking;
import reserva.Paquete;
import reserva.ReservaHotel;
import reserva.ReservaViajOrg;
import reserva.ReservaViajeIMSERSO;
import reserva.ReservaVuelo;

/**
 * Clase controladora de la Ventana NuevoPaquete
 *
 * @author Jorge Martín Pérez
 * @author Iván Márquez Pardo
 * @version 1.0
 */
public class NuevoPaqueteControler implements ActionListener{
    private NuevoPaquete ventana;
    private Booking aplicacion;
    
    public NuevoPaqueteControler(NuevoPaquete ventana, Booking aplicacion) {
        this.ventana = ventana;
        this.aplicacion = aplicacion;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String pulsado = ((JButton)e.getSource()).getText();
        boolean vamosAModificar = false;
        
        //Calcular el precio del paquete
        if(pulsado.equals(this.ventana.getCalcular().getText())) {
            calcularTotal();
            return;
        }
        
        //Terminar de meter reservas
        if(pulsado.equals(this.ventana.getFinalizar().getText())) {
            try {
                this.checkEstados();
                if(this.ventana.getClaveVentanaAnt().equals("ModificarPaquete")) {
                    AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.aplicacion.getBookingDBName());
                    
                    Paquete pacAct = this.ventana.getPaqActual();
                    //admin = pacAct.modificar(admin);
                    admin = pacAct.actualizarSQL(admin);
                    admin = this.aplicacion.desbordaAsociacion(admin);
                    admin.close();
                    vamosAModificar = true;
                }
                else {
                    this.aplicacion.savePaquete(this.ventana.getPaqActual());
                }
                
            } catch (SQLException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
            catch(SinSeleccionarEx ex) {
                JOptionPane.showMessageDialog(null, "Todavía hay reservas sin estado asignado.");
                return;
            }
        }
        
        Ventana nuevaVen = this.ventana.cambiarVentana(this.ventana.claveVentana(pulsado));
        
        //Si volvemos a la ventana de modificar el paquete, reseteamos los campos
        if(vamosAModificar) {
            ModificarPaquete modificarVentana = (ModificarPaquete)nuevaVen;
            ((ModificarPaqueteControler)modificarVentana.getControlador()).resetearCampos();
        }
        
        //En las nuevas ventanas les damos el paquete
        if(pulsado.equals(this.ventana.getAddVuelo().getText())) {
            AddVueloControler controler = (AddVueloControler) nuevaVen.getControlador();
            controler.resetearCampos();
            ((AddVuelo)nuevaVen).setCurrentPaq(this.ventana.getPaqActual());
        }
        else if(pulsado.equals(this.ventana.getAddHotel().getText())) {
            AddHotelControler controlador = (AddHotelControler)nuevaVen.getControlador();
            controlador.resetearCampos();
            ((AddHotel)nuevaVen).setCurrentPaq(this.ventana.getPaqActual());
        }
        else if(pulsado.equals(this.ventana.getAddViajOrg().getText())) {
            ((AddViajOrg)nuevaVen).setCurrPaq(this.ventana.getPaqActual());
        }
    }
    
    
    /**
     * Se encarga de actualizar los estados de cada una de als reservas realizadas, 
     * y en caso de que se encuentre con una reserva que no tenga un estado asociado, 
     * salta una excepci&oacute;n.
     * @throws SinSeleccionarEx 
     */
    public void checkEstados() throws SinSeleccionarEx {
        checkEstadosVuelos();
        checkEstadosHoteles();
        checkEstadosViajesOrg();
    }
    
    /**
     * Comprueba que todos los vuelos del paquete tengan un estado asignado. En 
     * caso de que exista uno que no lo tenga, lanzamos una excepci&oacute;n.
     * @throws SinSeleccionarEx 
     */
    public void checkEstadosVuelos() throws SinSeleccionarEx {
        ZebraJTable tabla = (ZebraJTable)this.ventana.getScrollVuelos().getViewport().getView();
        List<ReservaVuelo> reservasVuelos = this.ventana.getPaqActual().getReservasVuelos();
        
        //Vamos asignando los estados, si una reserva no lo tiene lanzamos excep.
        for(int i = 0; i < tabla.getModel().getRowCount(); i++) {
            String estado = (String) (tabla.getRow(i)[6]);
            
            if(estado == null || estado.equals("")) {
                throw new SinSeleccionarEx();
            }
            else {
                reservasVuelos.get(i).setEstado(estado);
            }
        }
    }
    
    
    /**
     * Comprueba que todos los vuelos del paquete tengan un estado asignado. En 
     * caso de que exista uno que no lo tenga, lanzamos una excepci&oacute;n.
     * @throws SinSeleccionarEx 
     */
    public void checkEstadosHoteles() throws SinSeleccionarEx {
        ZebraJTable tabla = (ZebraJTable)this.ventana.getScrollHoteles().getViewport().getView();
        List<ReservaHotel> reservasHoteles = this.ventana.getPaqActual().getReservasHotel();
        
        //Miramos si hay filas sin estado seleccionado
        for(int i = 0; i < tabla.getModel().getRowCount(); i++) {
            String estado = (String) (tabla.getRow(i)[6]);
            
            if(estado == null || estado.equals("")) {
                throw new SinSeleccionarEx();
            }
            else {
                reservasHoteles.get(i).setEstado(estado);
            }
        }
    }
    
    /**
     * Comprueba que todos los vuelos del paquete tengan un estado asignado. En 
     * caso de que exista uno que no lo tenga, lanzamos una excepci&oacute;n.
     * @throws SinSeleccionarEx 
     */
    public void checkEstadosViajesOrg() throws SinSeleccionarEx {
        ZebraJTable tabla = (ZebraJTable)this.ventana.getScrollViajOrg().getViewport().getView();
        
        //Miramos si hay filas sin estado seleccionado
        for(int i = 0; i < tabla.getModel().getRowCount(); i++) {
            String estado = (String) (tabla.getRow(i)[8]);
            
            if(estado == null || estado.equals("")) {
                throw new SinSeleccionarEx();
            }
        }
        
        //Ponemos el estado de los viajes organizados
        List<ReservaViajOrg> reservasViajOrgs = this.ventana.getPaqActual().getReservasVO();
        int numVO = reservasViajOrgs.size();
        for(int i = 0; i < numVO; i++) {
            String estado = (String) (tabla.getRow(i)[8]);
            reservasViajOrgs.get(i).setEstado(estado);
        }
        
        //Ponemos el estado de los viajes del IMSERSO
        List<ReservaViajeIMSERSO> reservasViajesIMSERSO = this.ventana.getPaqActual().getReservasIMSERSO();
        for(int i = 0; i < reservasViajesIMSERSO.size(); i++) {
            String estado = (String) (tabla.getRow(i + numVO)[8]);
            
            if(estado.equals("10%")) {estado = "reservado";}
            else {estado = estado.toLowerCase();}
            
            reservasViajesIMSERSO.get(i).setEstado(estado);
        }
    }
    
    /**
     * Se encarga de calcular el precio total del paquete, y lo muestra en el 
     * campo 'total'.
     */
    private void calcularTotal() {
        ZebraJTable vuelos = (ZebraJTable) this.ventana.getScrollVuelos().getViewport().getView();
        ZebraJTable viajes = (ZebraJTable) this.ventana.getScrollViajOrg().getViewport().getView();
        ZebraJTable hoteles = (ZebraJTable) this.ventana.getScrollHoteles().getViewport().getView();
        
        double total = 0;
        
        //Obtenemos los precios
        for(int i = 0; i < vuelos.getModel().getRowCount(); i++) {
            Object[] fila = vuelos.getRow(i);
            total += Double.parseDouble((String)fila[5]);
        }
        
        for(int i = 0; i < viajes.getModel().getRowCount(); i++) {
            Object[] fila = viajes.getRow(i);
            total += Double.parseDouble((String)fila[1]);
        }
        
        for(int i = 0; i < hoteles.getModel().getRowCount(); i++) {
            Object[] fila = hoteles.getRow(i);
            total += Double.parseDouble((String)fila[5]);
        }
        
        this.ventana.getPrecio().setText(""+total);
    }
}
