/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controladores;

import GUI.Ventanas.Facturacion;
import cat.quickdb.db.AdminBase;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.table.DefaultTableModel;
import myexception.NoResultsExc;
import padsof.Booking;
import persona.Vendedor;
import reserva.Paquete;

/**
 * http://chuwiki.chuidiang.org/index.php?title=ActionListener
 *
 * @author e265923
 */
public class FacturacionControl implements ActionListener {

    private Booking aplic;
    private Facturacion vista;

    public FacturacionControl(Facturacion ventana, Booking aplicacion) {
        this.vista = ventana;
        this.aplic = aplicacion;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object fuente = e.getSource();
        
        JRadioButton[] rb = vista.getBots();
        /*if (fuente == rb[0]) {
            JComboBox comb = (JComboBox) e.getSource();
            int index = comb.getSelectedIndex();
            if (index == 0) {
                vista.getVendedorDNI().setEnabled(false);
                vista.getTipoServ().setEnabled(false);
            } else if (index == 1) {
                vista.getVendedorDNI().setEnabled(false);
                vista.getTipoServ().setEnabled(true);
            } else if (index == 2) {
                vista.getVendedorDNI().setEnabled(true);
                vista.getTipoServ().setEnabled(false);
            }
        } else*/ if (fuente == rb[0]) {
            vista.getVendedorDNI().setEnabled(false);
                vista.getTipoServ().setEnabled(false);
        } else if (fuente == rb[1]) {
            vista.getVendedorDNI().setEnabled(false);
                vista.getTipoServ().setEnabled(true);
        } else if (fuente == rb[2]) {
            vista.getVendedorDNI().setEnabled(true);
                vista.getTipoServ().setEnabled(false);
        }else if(fuente == vista.getBuscar()) {
            JButton buscar = (JButton) fuente;

            if (rb[0].isSelected()) {
                //Facturacion total.
                Object[] filas = this.obtainTablaReservas(0, -1,
                        this.vista.getDesde().getDate(), this.vista.getHasta().getDate());

                this.vista.getPrecio().setText(String.valueOf(
                        this.aplic.factTotal(this.vista.getDesde().getDate(), this.vista.getHasta().getDate())));

                DefaultTableModel tabMod = (DefaultTableModel) vista.getTabla().getModel();
                tabMod.getDataVector().clear();
                for(Object fila : filas){
                    String[] newRow = (String[])fila;
                    tabMod.addRow(newRow);
                }
            
            } else if (rb[1].isSelected()) {
                DefaultTableModel tabMod = (DefaultTableModel) vista.getTabla().getModel();
                tabMod.getDataVector().clear();
                int serv = vista.getTipoServ().getSelectedIndex();
                Object[] filas;
                switch (serv) {
                    case 0:
                        //Facturación Hoteles
                        filas = obtainTablaReservas(1, -1, this.vista.getDesde().getDate(), this.vista.getHasta().getDate());
                        this.vista.getPrecio().setText(String.valueOf(
                                this.aplic.factHoteles(this.vista.getDesde().getDate(), this.vista.getHasta().getDate())));
                        
                        break;
                    case 1:
                        //facturacion Viajes
                        filas = obtainTablaReservas(2, -1, this.vista.getDesde().getDate(), this.vista.getHasta().getDate());
                        this.vista.getPrecio().setText(String.valueOf(
                                this.aplic.factViajesOrg(this.vista.getDesde().getDate(), this.vista.getHasta().getDate())));
                        break;
                    default:
                    //facturacion Vuelos
                        filas = obtainTablaReservas(3, -1, this.vista.getDesde().getDate(), this.vista.getHasta().getDate());
                        this.vista.getPrecio().setText(String.valueOf(
                                this.aplic.factVuelos(this.vista.getDesde().getDate(), this.vista.getHasta().getDate())));
                }
                for(Object fila : filas){
                    String[] newRow = (String[])fila;
                    tabMod.addRow(newRow);
                }
            } else {
                int vend = Integer.parseInt(vista.getVendedorDNI().getText());
                //Buscar vendedor en la base de datos
                Vendedor v;
                try {
                    v = this.aplic.buscarVendedor(vend);
                } catch (NoResultsExc ex) {
                    JOptionPane.showMessageDialog(null, "El Vendedor introducido no "
                            + "se encuentra en la Base de Datos.",
                            "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                this.vista.getPrecio().setText(String.valueOf(this.aplic.factTotal(vend)));
            }

        } else if (fuente == vista.getAtras()) {
            String textBut = ((JButton) fuente).getText();
            this.vista.cambiarVentana(this.vista.claveVentana(textBut));
        }
    }

    private Object[] obtainTablaReservas(int tipo, int idUsr, Date desde, Date Hasta){
        try {
            Paquete p = new Paquete();
            
            String query = "SELECT Persona.DNI, Vendedor.idUsr, Paquete.idPaq,"
                    + "Reserva.fechaInicio, Reserva.tipoReserva, Reserva.precio "
                    + "FROM Persona JOIN Vendedor ON Vendedor.parent_id=Persona.id"
                    + "JOIN Paquete ON Paquete.vendedor=Vendedor.idUsr "
                    + "JOIN PaqueteReserva ON PaqueteReserva.base=Paquete.idPaq "
                    + "JOIN Reserva ON Reserva.id=PaqueteReserva.related ";
            switch(tipo){
                case 0:
                    //Total
                    if(idUsr!=-1){
                        query = query + "WHERE Vendedor.idUsr="+idUsr;
                    }
                    break;
                case 1:
                    //Hoteles
                    query = query + "WHERE Reserva.tipoReserva='reservaHotel'";
                    break;
                case 2:
                    //Viajes
                    query = query + "WHERE Reserva.tipoReserva='reservaVO' "
                            + "OR Reserva.tipoReserva='reservaIMSERSO'";
                    break;
                default:
                    //Vuelos
                    query = query + "WHERE Reserva.tipoReserva='ReservaVuelo'";
                    break;
            }
            
            //EJECUTAR QUERY
            AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.aplic.getBookingDBName());
            Connection c = Paquete.quickDBtoJDBC(admin);
            String[][] filas;
            try (Statement stmt = c.createStatement()) {
                ResultSet res = stmt.executeQuery(query);
                res.beforeFirst();
                filas = null;
                int i = 0;
                while(res.next()){
                    for(int j=0; j<6; ++j){
                        filas[i][j]=res.getString(j);
                    }
                }
            }
            return filas;
            
        } catch (SQLException ex) {
            Logger.getLogger(FacturacionControl.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error en los parámetros de búsqueda.");
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FacturacionControl.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error en los parámetros de búsqueda.");
            return null;
        }
    }
    
//    SELECT Persona.DNI, Vendedor.idUsr, Paquete.idPaq, Reserva.fechaInicio, Reserva.tipoReserva, Reserva.precio  FROM Persona JOIN Vendedor ON Vendedor.parent_id=Persona.id JOIN Paquete ON Paquete.vendedor=Vendedor.idUsr  JOIN PaqueteReserva ON PaqueteReserva.base=Paquete.idPaq  JOIN Reserva ON Reserva.id=PaqueteReserva.related WHERE fechaInicio > '01/01/2014'
//SELECT Persona.DNI, Vendedor.idUsr, Paquete.idPaq, Reserva.fechaInicio, Reserva.tipoReserva, Reserva.precio  FROM Persona JOIN Vendedor ON Vendedor.parent_id=Persona.id JOIN Paquete ON Paquete.vendedor=Vendedor.idUsr  JOIN PaqueteReserva ON PaqueteReserva.base=Paquete.idPaq  JOIN Reserva ON Reserva.id=PaqueteReserva.related WHERE  str_to_date(Reserva.fechaInicio, '%d/%m/%Y') between cast('01/01/2014' as date) and cast('01/01/2016' as date);
//SELECT Persona.DNI, Vendedor.idUsr, Paquete.idPaq, Reserva.fechaInicio, Reserva.tipoReserva, Reserva.precio  FROM Persona JOIN Vendedor ON Vendedor.parent_id=Persona.id JOIN Paquete ON Paquete.vendedor=Vendedor.idUsr  JOIN PaqueteReserva ON PaqueteReserva.base=Paquete.idPaq  JOIN Reserva ON Reserva.id=PaqueteReserva.related WHERE strftime(Reserva.fechaInicio) < strftime('01/01/2014') 
}
