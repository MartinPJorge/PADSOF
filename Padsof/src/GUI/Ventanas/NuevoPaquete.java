 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Ventanas;

import GUI.Recursos.SpringUtilities;
import GUI.Recursos.ZebraJTable;
import catalogo.InfoHotel;
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
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import reserva.Paquete;
import reserva.ReservaHotel;
import reserva.ReservaVuelo;
import reserva.Vuelos;

/**
 *
 * @author Jorge
 */
public class NuevoPaquete extends Ventana implements TableModelListener{
    private JLabel encabezado;
    
    private JScrollPane scrollVuelos;
    private JScrollPane scrollHoteles;
    private JScrollPane scrollViajOrg;
    
    private JPanel addServicio;
    private JPanel suelo;
    private JButton addHotel;
    private JButton addVuelo;
    private JButton addViajOrg;
    private JButton finalizar;
    private Paquete paqActual;
    
    public NuevoPaquete(BookingFrame padre, String nombre) {
        super(new SpringLayout(), nombre, padre, 600,550);
        this.encabezado = new JLabel("");
        this.encabezado.setFont(new Font("Times", Font.PLAIN, 34));
        JLabel vuelo = new JLabel("Vuelos:");
        JLabel hotel = new JLabel("Hoteles:");
        JLabel viajOrg = new JLabel("Viajes organizados:");
        
        
        //Inicializamos las tablas
        this.iniTablaVuelos();
        this.iniTablaHoteles();
        this.iniTablaViajOrgs();
        this.iniAddService();
        this.iniSuelo();
        
        vuelo.setLabelFor(this.scrollVuelos);
        hotel.setLabelFor(this.scrollHoteles);
        viajOrg.setLabelFor(this.scrollViajOrg);
        
        //Añadimos los elementos
        this.add(this.encabezado);
        this.add(vuelo);
        this.add(this.scrollVuelos);
        this.add(hotel);
        this.add(this.scrollHoteles);
        this.add(viajOrg);
        this.add(new JScrollPane(this.scrollViajOrg));
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
    @SuppressWarnings("empty-statement")
    private void iniTablaVuelos() {
        String[] titulos = {"Origen", "Destino", "Salida", "Llegada", "Pasajero","Precio", "Estado"};
        
        //Creamos el comboBox de estados
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("Confirmado");
        comboBox.addItem("Cancelado");

        //Creamos la tabla con su listener
        ZebraJTable tablaVuelos = new ZebraJTable(null,titulos,6);
        tablaVuelos.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(comboBox));
        tablaVuelos.getModel().addTableModelListener(this);
        
        this.scrollVuelos = new JScrollPane(tablaVuelos);
    }
    
    /**
     * Inicializa la tabla de Hoteles
     */
    private void iniTablaHoteles() {
        String[] titulos = {"Hotel", "★★★", "Comida", "Entrada","Días", "Precio", "Estado"};
        
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("Confirmado");
        comboBox.addItem("Cancelado");
        comboBox.addItem("10%");
        
        //Creamos la tabla
        ZebraJTable tablaHoteles = new ZebraJTable(null,titulos,6);
        tablaHoteles.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(comboBox));
        tablaHoteles.getModel().addTableModelListener(this);
        
        this.scrollHoteles = new JScrollPane(tablaHoteles);
    }
    
    /**
     * Inicializa la tabla de vuelos
     */
    private void iniTablaViajOrgs() {
        String[] titulos = {"Viaje", "A nombre", "Personas", "F.inicio", "Días", "Precio", "Estado"};
        
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("Confirmado");
        comboBox.addItem("Cancelado");
        comboBox.addItem("10%");
        //Creamos la tabla
        ZebraJTable tablaViajOrg = new ZebraJTable(null,titulos,6);
        tablaViajOrg.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(comboBox));
        tablaViajOrg.getModel().addTableModelListener(this);
        
        this.scrollViajOrg = new JScrollPane(tablaViajOrg);
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
        Object[][] infoVuelos = new String[vuelos.size()][7];
        
        //Metemos la informacion en la tabla
        for(int i = 0; i < vuelos.size(); i++) {
            ReservaVuelo vuelo = vuelos.get(i);
            SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy/dd/MM");
            
            String loc = vuelo.getLocalizador();
            String origen = Vuelos.getOrigen(loc);
            String destino = Vuelos.getDestino(loc);
            String salida = dateFormater.format(Vuelos.getSalida(loc));
            String llegada = dateFormater.format(Vuelos.getLlegada(loc));
            String dni = vuelo.getPasajeroDNI();
            String precio = Double.toString(vuelo.getPrecio());
            
            Object[] arr = {origen,destino,salida,llegada,dni,precio,"Confirmado"};
            
            infoVuelos[i][0] = origen;
            infoVuelos[i][1] = destino;
            infoVuelos[i][2] = salida;
            infoVuelos[i][3] = llegada;
            infoVuelos[i][4] = dni;
            infoVuelos[i][5] = precio;
            infoVuelos[i][6] = "Confirmado";
            
            ZebraJTable tablaVuelos = (ZebraJTable)this.scrollVuelos.getViewport().getView();
            DefaultTableModel modelo = (DefaultTableModel)tablaVuelos.getModel();
            int numCols = modelo.getColumnCount();
            int numRows = modelo.getRowCount();
            //modelo.insertRow(i, arr);
            //modelo.insertRow(1, arr);
            //origen destino salida llegada pasajero prcio estado
        }
        
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("Confirmado");
        comboBox.addItem("Cancelado");
        comboBox.addItem("10%");
        
        String[] titulos = {"Origen", "Destino", "Salida", "Llegada", "Pasajero","Precio", "Estado"};
        
        ZebraJTable tablaVuelos = (ZebraJTable)this.scrollVuelos.getViewport().getView();
        this.scrollVuelos.remove(tablaVuelos);
        
        tablaVuelos = new ZebraJTable(infoVuelos,titulos,6);
        this.scrollVuelos.add(tablaVuelos);
        tablaVuelos.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(comboBox));
        tablaVuelos.getModel().addTableModelListener(this);
        tablaVuelos.repaint();
        tablaVuelos.setVisible(true);
        
        this.scrollVuelos.setViewportView(tablaVuelos);
    }
    
    public void mostrarInfoHotel() {
        ArrayList<ReservaHotel> vuelos = this.paqActual.getReservasHotel();
        Object[][] infoVuelos = new String[vuelos.size()][7];
        
        //Metemos la informacion en la tabla
        for(int i = 0; i < vuelos.size(); i++) {
            ReservaHotel reserva = vuelos.get(i);
            InfoHotel info = reserva.getInfoHotel();
            
            infoVuelos[i][0] = info.getNombre();
            infoVuelos[i][1] = ""+info.getCategoria();
            infoVuelos[i][2] = reserva.getSuplemento();
            infoVuelos[i][3] = reserva.getFechaInicio();
            infoVuelos[i][4] = ""+reserva.getDia();
            infoVuelos[i][5] = ""+reserva.getPrecio();
            infoVuelos[i][6] = "";
        }
        
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("Confirmado");
        comboBox.addItem("Cancelado");
        comboBox.addItem("10%");
        
        String[] titulos = {"Hotel", "★★★", "Comida", "Entrada","Días", "Precio", "Estado"};
        
        ZebraJTable tablaVuelos = (ZebraJTable)this.scrollHoteles.getViewport().getView();
        this.scrollHoteles.remove(tablaVuelos);
        
        tablaVuelos = new ZebraJTable(infoVuelos,titulos,6);
        this.scrollHoteles.add(tablaVuelos);
        tablaVuelos.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(comboBox));
        tablaVuelos.getModel().addTableModelListener(this);
        tablaVuelos.repaint();
        tablaVuelos.setVisible(true);
        
        this.scrollHoteles.setViewportView(tablaVuelos);
    }
    
    public void actualizarEncabezado() {
        this.encabezado.setText("Paquete: " + this.paqActual.getIdPaq() + 
                " - Cliente: " + this.paqActual.getCliente());
    }
    
    /**
     * Especifica el controlador a usar por la ventana de Inicio.
     * @param controlador 
     */
    public void setControlador(ActionListener controlador) {
        this.controlador = controlador;
        this.addVuelo.addActionListener(this.controlador);
        this.addHotel.addActionListener(this.controlador);
        this.addViajOrg.addActionListener(this.controlador);
    }
}
