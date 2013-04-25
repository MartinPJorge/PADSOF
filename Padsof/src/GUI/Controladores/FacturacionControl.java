/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controladores;

import GUI.Recursos.DateValidator;
import GUI.Ventanas.Facturacion;
import cat.quickdb.db.AdminBase;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.table.DefaultTableModel;
import myexception.NoResultsExc;
import padsof.Booking;
import persona.Vendedor;

/**
 * Clase controladora de la Ventana Facturación
 *
 * @author Jorge Martín Pérez
 * @author Iván Márquez Pardo
 * @version 1.0
 */
public class FacturacionControl implements ActionListener {

    private Booking aplic;
    private Facturacion vista;

    /**
     * Constructor del controlador
     *
     * @param ventana
     * @param aplicacion
     */
    public FacturacionControl(Facturacion ventana, Booking aplicacion) {
        this.vista = ventana;
        this.aplic = aplicacion;
    }

    /**
     * Método que lleva a cabo el control efectivo.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object fuente = e.getSource();

        JRadioButton[] rb = vista.getBots();
        Object[] filas;
        if (fuente == rb[0]) {
            vista.getVendedorDNI().setEnabled(false);
            vista.getTipoServ().setEnabled(false);
        } else if (fuente == rb[1]) {
            vista.getVendedorDNI().setEnabled(false);
            vista.getTipoServ().setEnabled(true);
        } else if (fuente == rb[2]) {
            vista.getVendedorDNI().setEnabled(true);
            vista.getTipoServ().setEnabled(false);
        } else if (fuente == vista.getBuscar()) {
            if (rb[0].isSelected()) {
                //Facturacion total.
                filas = this.obtainTablaReservas(0, -1,
                        this.vista.getDesde().getDate(), this.vista.getHasta().getDate());

            } else if (rb[1].isSelected()) {

                int serv = vista.getTipoServ().getSelectedIndex();

                switch (serv) {
                    case 0:
                        //Facturación Hoteles
                        filas = obtainTablaReservas(1, -1, this.vista.getDesde().getDate(), this.vista.getHasta().getDate());
                        break;
                    case 1:
                        //facturacion Viajes
                        filas = obtainTablaReservas(2, -1, this.vista.getDesde().getDate(), this.vista.getHasta().getDate());
                        break;
                    default:
                        //facturacion Vuelos
                        filas = obtainTablaReservas(3, -1, this.vista.getDesde().getDate(), this.vista.getHasta().getDate());
                }

            } else if (rb[2].isSelected()) {
                String vendStr = vista.getVendedorDNI().getText();
                if (!(vendStr.length() > 0)) {
                    JOptionPane.showMessageDialog(null, "Introduzca el ID del vendedor en"
                            + " el campo designado para ello.",
                            "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                int vend = Integer.parseInt(vendStr);
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
                filas = obtainTablaReservas(0, vend, this.vista.getDesde().getDate(), this.vista.getHasta().getDate());

            } else {
                JOptionPane.showMessageDialog(null, "Elija el tipo de Facturación que desea ver.",
                        "Error - Tipo no especificado", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            DefaultTableModel tabMod = (DefaultTableModel) vista.getTabla().getModel();
            tabMod.getDataVector().clear();
            for (Object fila : filas) {
                if (fila != null) {
                    String[] newRow = (String[]) fila;
                    tabMod.addRow(newRow);
                }
            }
        } else if (fuente == vista.getAtras()) {
            String textBut = ((JButton) fuente).getText();
            this.vista.cambiarVentana(this.vista.claveVentana(textBut));
        }
    }

    /**
     * Obtiene las filas necesarias para rellenar la tabla.
     *
     * @param tipo
     * @param idUsr
     * @param desde
     * @param hasta
     * @return matriz de filas para la tabla
     */
    private Object[] obtainTablaReservas(int tipo, int idUsr, Date desde, Date hasta) {

        String query1 = "SELECT * FROM Reserva";

        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/YYYY");
        String from = sdf.format(desde);
        String until = sdf.format(hasta);


        //EJECUTAR QUERYS
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.aplic.getBookingDBName());


        Double facturacion = 0.0;

        //Obtenemos las reservas que nos interesan
        Object[] fReserva = admin.obtainJoin(query1, 5);
        List<Integer> resultReservaField = new ArrayList<Integer>();
        List<Double> precios = new ArrayList<Double>();
        for (Object obj : fReserva) {
            Object[] reservaFields = (Object[]) obj;
            String fechaInicio = (String) reservaFields[2];
            precios.add(Double.parseDouble((String) reservaFields[3]));
            if (compFechas(fechaInicio, from, until)) {
                resultReservaField.add(Integer.parseInt((String) reservaFields[0]));

            }
        }

        String query2 = "SELECT p.cliente, p.vendedor, p.idPaq, r.fechaInicio, r.tipoReserva, r.precio  "
                + "FROM Reserva as r JOIN PaqueteReserva as pr ON r.id=pr.related "
                + "JOIN Paquete as p ON pr.base=p.id ";

        switch (tipo) {
            case 0:
                //Total
                if (idUsr != -1) {
                    query2 = query2 + "WHERE p.vendedor=" + idUsr;

                } else {
                    query2 = query2 + "WHERE 1=1 ";
                }
                break;
            case 1:
                //Hoteles
                query2 = query2 + "WHERE r.tipoReserva='reservaHotel'";

                break;
            case 2:
                //Viajes
                query2 = query2 + "WHERE r.tipoReserva='reservaVO' "
                        + "OR r.tipoReserva='reservaIMSERSO'";

                break;
            default:
                //Vuelos
                query2 = query2 + "WHERE r.tipoReserva='reservaVuelo'";

                break;
        }

        String[][] matrixRow = new String[resultReservaField.size()][6];

        int i = 0;
        Object[] rowsObj;
        for (Integer idRes : resultReservaField) {
            rowsObj = admin.obtainJoin(query2 + " AND r.id=" + idRes, 6);
            if (rowsObj != null) {
                for (int j = 0; j < matrixRow[i].length; ++j) {
                    matrixRow[i][j] = ((String[]) rowsObj[0])[j];
                }
                ++i;
                facturacion += precios.get(idRes - 1);
            }
        }

        this.vista.getPrecio().setText(String.valueOf((Double) facturacion));
        admin.close();
        return matrixRow;

    }

    /**
     * Verifica si la fecha actual se encuentra entre from y until
     *
     * @param actual
     * @param from
     * @param until
     * @return boolean
     */
    private boolean compFechas(String actual, String from, String until) {
        if (DateValidator.compareDates(from, actual) <= 0 && DateValidator.compareDates(actual, until) <= 0) {
            return true;
        } else {
            return false;
        }
    }
}
