 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Ventanas;

import GUI.Recursos.SpringUtilities;
import GUI.Recursos.ZebraJTable;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import reserva.Paquete;
import reserva.ReservaVuelo;
import reserva.Vuelos;

/**
 *
 * @author Jorge
 */
public class NuevoPaquete extends Ventana implements TableModelListener{
    private JLabel encabezado;
    private JTable tablaVuelos;
    private JTable tablaHoteles;
    private JTable tablaViajOrg;
    private JPanel addServicio;
    private JPanel suelo;
    private JButton addHotel;
    private JButton addVuelo;
    private JButton addViajOrg;
    private JButton finalizar;
    private Paquete paqActual;
    
    public NuevoPaquete(BookingFrame padre, String nombre) {
        super(new SpringLayout(), nombre, padre, 600,550);
        this.encabezado = new JLabel("Paquete 002 - Dni cliente: 47474934J");
        this.encabezado.setFont(new Font("Times", Font.PLAIN, 34));
        JLabel vuelo = new JLabel("Vuelos:");
        JLabel hotel = new JLabel("Hoteles:");
        JLabel viajOrg = new JLabel("Viajes organizados:");
        
        vuelo.setLabelFor(this.tablaVuelos);
        hotel.setLabelFor(this.tablaHoteles);
        viajOrg.setLabelFor(this.tablaViajOrg);
        
        //Inicializamos las tablas
        this.iniTablaVuelos();
        this.iniTablaHoteles();
        this.iniTablaViajOrgs();
        this.iniAddService();
        this.iniSuelo();
        
        //Añadimos los elementos
        this.add(this.encabezado);
        this.add(vuelo);
        this.add(new JScrollPane(this.tablaVuelos));
        this.add(hotel);
        this.add(new JScrollPane(this.tablaHoteles));
        this.add(viajOrg);
        this.add(new JScrollPane(this.tablaViajOrg));
        this.add(this.addServicio);
        this.add(this.suelo);
        
        SpringUtilities.makeCompactGrid(this,9,1,6,6 ,6,6);
        
        this.setVisible(true);
    }
    
    /**
     * A&ntilde;ade el cuadro que muestra el total y el bot&oacute;n de finalizar.
     */
    public void iniSuelo() {
        //Panel para mostrar precio
        JPanel total = new JPanel();
        JLabel totalLab = new JLabel("Total:");
        JTextField precio = new JTextField(10);
        totalLab.setLabelFor(precio);
        total.add(totalLab);
        total.add(precio);
        
        //Juntamos lo anterior con el botón de finalizar.
        JPanel general = new JPanel(new BorderLayout());
        this.finalizar = new JButton("Finalizar");
        finalizar.addActionListener(new ClickCambioVentana());
        JButton fantasma = new JButton("Fantasma");
        fantasma.setVisible(false);
        general.add(total, BorderLayout.WEST);
        general.add(this.finalizar, BorderLayout.EAST);
        general.add(fantasma, BorderLayout.CENTER);
        
        this.suelo = general;
    }
    
    /**
     * A&ntilde;ade los botones de meter m&aacute;s servicios.
     */
    public void iniAddService() {
        this.addServicio = new JPanel(new SpringLayout());
        
        JPanel botones = new JPanel();
        this.addHotel = new JButton("Hotel");
        this.addHotel.addActionListener(new ClickCambioVentana());
        this.addVuelo = new JButton("Vuelo");
        this.addVuelo.addActionListener(new ClickCambioVentana());
        this.addViajOrg = new JButton("Viaje Organizado");
        this.addViajOrg.addActionListener(new ClickCambioVentana());
        
        botones.add(this.addHotel);
        botones.add(this.addVuelo);
        botones.add(this.addViajOrg);
        
        JLabel addService = new JLabel("Añadir servicio");
        addService.setFont(new Font("Times", Font.PLAIN, 18));
        JPanel centrar = new JPanel();
        centrar.add(addService);
        
        this.addServicio.add(centrar);
        this.addServicio.add(botones);
        
        SpringUtilities.makeCompactGrid(this.addServicio,2,1,  1,1 ,1,1);
    }

    /**
     * Inicializa la tabla de vuelos
     */
    private void iniTablaVuelos() {
        String[] titulos = {"Origen", "Destino", "Salida", "Llegada", "Pasajero","Precio", "Estado"};
        Object[][] filas = {{"","","","","","",""}};//{{"BAR", "MAD", "11:00", "12:00", "474747934J",34, "Confirmado"}};
        
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("Confirmado");
        comboBox.addItem("Cancelado");

        
        //Creamos la tabla
        this.tablaVuelos = new ZebraJTable(new MiModeloTabla(titulos, filas,6));
        this.tablaVuelos.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(comboBox));
        this.tablaVuelos.getModel().addTableModelListener(this);
    }
    
    /**
     * Inicializa la tabla de Hoteles
     */
    private void iniTablaHoteles() {
        String[] titulos = {"Hotel", "A nombre", "#Hab.", "Tipo", "Comida", "Entrada",
        "H.entrada", "Días", "Precio", "Estado"};
        Object[][] filas = {{"","","","","","","","","",""}};//{{"Palace", "43786789I", "1", "Individual", "PC", "1/1/15", "12:34",3,45.6, "Confirmado"}};
        
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("Confirmado");
        comboBox.addItem("Cancelado");
        comboBox.addItem("10%");
        
        //Creamos la tabla
        this.tablaHoteles = new ZebraJTable(new MiModeloTabla(titulos, filas,9));
        this.tablaHoteles.getColumnModel().getColumn(9).setCellEditor(new DefaultCellEditor(comboBox));
        this.tablaHoteles.getModel().addTableModelListener(this);
    }
    
    /**
     * Inicializa la tabla de vuelos
     */
    private void iniTablaViajOrgs() {
        String[] titulos = {"Viaje", "A nombre", "Personas", "F.inicio", "Días", "Precio", "Estado"};
        Object[][] filas = {{"","","","","","",""}};//{{"Tour", "43786789I", "1", "1/10/13", 3,45.3, "Confirmado"}};
        
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("Confirmado");
        comboBox.addItem("Cancelado");
        comboBox.addItem("10%");
        //Creamos la tabla
        this.tablaViajOrg = new ZebraJTable(new MiModeloTabla(titulos, filas,6));
        this.tablaViajOrg.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(comboBox));
        this.tablaViajOrg.getModel().addTableModelListener(this);
    }

    @Override
    public String claveVentana(String textoBoton) {
        if(textoBoton.equals(this.addHotel.getText())) {
            return "Hotel";
        }
        else if(textoBoton.equals(this.addVuelo.getText())){
            return "Vuelo";
        }
        else if(textoBoton.equals(this.addViajOrg.getText())) {
            return "ViajeOrganizado";
        }
        else {
            return "ModificarPaquete";
        }
    }
    
    
    /**
     * Sobreescribumox el modelo de tabla por defecto
     */
    private class MiModeloTabla extends AbstractTableModel {
        private String[] columnNames;
        private Object[][] data;
        private int editable;
        
        public MiModeloTabla(String[] columnNames, Object[][] data, int edit) {
            this.columnNames = columnNames;
            this.data = data;
            this.editable = edit;
        }
        
        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        @Override
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        @Override
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col == this.editable) {
                return true;
            } else {
                return false;
            }
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        @Override
        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }
    }

    public JButton getAddHotel() {
        return addHotel;
    }

    public JButton getAddVuelo() {
        return addVuelo;
    }

    public JButton getAddViajOrg() {
        return addViajOrg;
    }

    public Paquete getPaqActual() {
        return paqActual;
    }
    
    

    
    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel)e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);
        System.out.println("ACTUALIZA LA BASE DE DATOS");
    }

    public void setPaqActual(Paquete paqActual) {
        this.paqActual = paqActual;
    }
    
    public void mostrarInfo(){//String dni) {
        ArrayList<ReservaVuelo> vuelos = this.paqActual.getReservasVuelos();
        String[][] infoVuelos = new String[vuelos.size()][6];
        
        //Metemos la informacion en la tabla
        for(int i = 0; i < vuelos.size(); i++) {
            ReservaVuelo vuelo = vuelos.get(i);
            SimpleDateFormat dateFormater = new SimpleDateFormat("dd/mm/YYYY");
            
            String loc = vuelo.getLocalizador();
            this.tablaVuelos.getModel().setValueAt(Vuelos.getOrigen(loc), i, 0);
            this.tablaVuelos.getModel().setValueAt(Vuelos.getDestino(loc), i, 1);
            this.tablaVuelos.getModel().setValueAt(dateFormater.format(Vuelos.getSalida(loc)), i, 2);
            this.tablaVuelos.getModel().setValueAt(dateFormater.format(Vuelos.getLlegada(loc)), i, 3);
            this.tablaVuelos.getModel().setValueAt(vuelo.getPasajeroDNI(), i, 4);
            this.tablaVuelos.getModel().setValueAt(Double.toString(vuelo.getPrecio()), i, 5);
        }

        
    }
    
    /**
     * Especifica el controlador a usar por la ventana de Inicio.
     * @param controlador 
     */
    public void setControlador(ActionListener controlador) {
        this.controlador = controlador;
        this.addVuelo.addActionListener(this.controlador);
    }
}
