/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Ventanas;

import GUI.Recursos.SpringUtilities;
import GUI.Recursos.ZebraJTable;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.*;


/**
 * Clase para la GUI que representa la Ventana en la que visualizamos la Facturación realizada.
 * @author Jorge Martín Pérez
 * @author Iván Márquez Pardo
 * @version 1.0
 */
public class Facturacion extends Ventana {
    private JDateChooser desde;
    private JDateChooser hasta;
    
    private JLabel vend;
    private JTextField vendedorDNI;
    private JButton buscar;
    private JLabel serv;
    private JComboBox tipoServ; 
    
    private ButtonGroup botones;

    private JRadioButton[] bots;

    private JTextField precio;

    private JPanel busqueda;
    private JTable tabla;
    private JPanel resultados;
    private JButton atras;

    /**
     * Constructor de la clase.
     * @param padre
     * @param nombre
     */
    public Facturacion(BookingFrame padre, String nombre) {
        super(new SpringLayout(), nombre, padre, 600, 650);

        this.setLayout(new SpringLayout());

        this.iniBusqueda();

        this.iniResultados();

        JPanel pAux = new JPanel(new FlowLayout());
        atras = new JButton("Atrás");
        pAux.add(atras);
        this.add(pAux);

        SpringUtilities.makeCompactGrid(this, 3, 1, 9, 9, 9, 9);
    }

    /**
     * Método auxiliar para diseñar el panel de búsqueda de esta clase.
     */
    private void iniBusqueda() {
        //Panel fechas
        JPanel fechas = new JPanel(new SpringLayout());

        JLabel from = new JLabel("Desde: ");
        this.desde = new JDateChooser(new Date(), "dd/MM/YYYY");
        from.setLabelFor(this.desde);
        fechas.add(from);
        fechas.add(desde);
        
        JLabel until = new JLabel("Hasta: ");
        this.hasta = new JDateChooser(new Date(), "dd/MM/YYYY");
        until.setLabelFor(this.hasta);
        fechas.add(until);
        fechas.add(hasta);
        fechas.setVisible(true);
        SpringUtilities.makeCompactGrid(fechas, 2, 2, 5, 5, 5, 5);
        
        JPanel aux= new JPanel(new GridBagLayout());
        aux.add(fechas);
        fechas = aux;
        
        //Radio botones
        JPanel radioBot = new JPanel(new SpringLayout());
        this.botones = new ButtonGroup();
        bots = new JRadioButton[3];
        this.bots[0] = new JRadioButton("Total");
        this.bots[1] = new JRadioButton("Por servicios");
        this.bots[2] = new JRadioButton("Por vendedor");
        botones.add(bots[0]);
        botones.add(bots[1]);
        botones.add(bots[2]);
        radioBot.add(new JLabel("Tipo de Facturación"));
        radioBot.add(bots[0]);
        radioBot.add(bots[1]);
        radioBot.add(bots[2]);
        radioBot.setVisible(true);
        SpringUtilities.makeCompactGrid(radioBot, 4, 1, 6, 6, 6, 6);
        
        
        this.vend = new JLabel("Vendedor: ");
        this.vendedorDNI = new JTextField(10);
        vend.setLabelFor(this.vendedorDNI);
        vendedorDNI.setEnabled(false);
        this.vend.setVisible(true);
        this.vendedorDNI.setVisible(true);
        JPanel paux1 = new JPanel(new FlowLayout());
        paux1.add(vend);
        paux1.add(vendedorDNI);
        paux1.setVisible(true);
               
        
        this.serv = new JLabel("Servicio: ");
        this.tipoServ = new JComboBox(new String[]{"Hoteles", "Viajes", "Vuelos"});
        serv.setLabelFor(this.tipoServ);
        tipoServ.setEnabled(false);
        this.serv.setVisible(true);
        this.tipoServ.setVisible(true);
        JPanel paux2 = new JPanel(new FlowLayout());
        paux2.add(serv);
        paux2.add(tipoServ);
        paux2.setVisible(true);
        
        JPanel opc = new JPanel(new SpringLayout());
        opc.add(serv);
        opc.add(tipoServ);
        opc.add(vend);
        opc.add(vendedorDNI);
        opc.setVisible(true);
        SpringUtilities.makeCompactGrid(opc, 2, 2, 8, 55, 8, 8);
        JPanel aux2= new JPanel(new GridBagLayout());
        aux2.add(opc);
        opc = aux2;
        
        
        
        JPanel general = new JPanel();
        general.setLayout(new BoxLayout(general, BoxLayout.LINE_AXIS));
        
        general.add(fechas);
        general.add(Box.createHorizontalGlue());
        general.add(radioBot);
        general.add(Box.createHorizontalGlue());
        general.add(opc);
        general.setVisible(true);
        
        
        this.buscar = new JButton("Buscar");
        JPanel boton = new JPanel();
        boton.add(buscar);
        boton.setVisible(true);
        
        JPanel auxArriba = new JPanel();
        auxArriba.setLayout(new BoxLayout(auxArriba, BoxLayout.Y_AXIS));
        auxArriba.add(general);
        auxArriba.add(boton);
        
        auxArriba.setBorder(BorderFactory.createTitledBorder("Búsqueda"));
        this.add(auxArriba);
        
    }
    
    /**
     * Método auxiliar para diseñar el panel de resultados de esta clase.
     */
    private void iniResultados(){
        String[][] predef={{"","","","",""}};
        Object[] cols = {"DNI Cliente", "Id Vendedor", "Paquete", "Fecha", "Tipo", "Precio"};
        this.tabla = new ZebraJTable(predef, cols);
        tabla.setName("Resultados: ");
        JScrollPane scroll = new JScrollPane(this.tabla);
        scroll.setVisible(true);
        scroll.setName("Resultados: ");
        tabla.setPreferredScrollableViewportSize(new Dimension(400,300));
        
        
        JLabel etiq = new JLabel("Facturación: ");
        this.precio = new JTextField();
        etiq.setLabelFor(precio);
        precio.setPreferredSize(new Dimension(150, 50));
     
        etiq.setVisible(true);
        precio.setVisible(true);
        
        JPanel aux = new JPanel(new FlowLayout());
        aux.add(etiq);
        aux.add(precio);
        
        this.resultados = new JPanel(new SpringLayout());
        resultados.add(scroll);
        resultados.add(aux);
        
        
        SpringUtilities.makeCompactGrid(resultados, 2, 1, 6, 6, 6, 6);
        resultados.setVisible(true);
        this.add(resultados);
        resultados.setPreferredSize(new Dimension(750, 500));
    }


    public ButtonGroup getBotones() {
        return botones;
    }

    public void setBotones(ButtonGroup botones) {
        this.botones = botones;
    }

    public JRadioButton[] getBots() {
        return bots;
    }

    public void setBots(JRadioButton[] bots) {
        this.bots = bots;
    }

    public JButton getBuscar() {
        return buscar;
    }

    public void setBuscar(JButton buscar) {
        this.buscar = buscar;
    }

    public JPanel getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(JPanel busqueda) {
        this.busqueda = busqueda;
    }

    public JDateChooser getDesde() {
        return desde;
    }

    public void setDesde(JDateChooser desde) {
        this.desde = desde;
    }

    public JDateChooser getHasta() {
        return hasta;
    }

    public void setHasta(JDateChooser hasta) {
        this.hasta = hasta;
    }

    public JTextField getPrecio() {
        return precio;
    }

    public void setPrecio(JTextField precio) {
        this.precio = precio;
    }

    public JPanel getResultados() {
        return resultados;
    }

    public void setResultados(JPanel resultados) {
        this.resultados = resultados;
    }

    public JLabel getServ() {
        return serv;
    }

    public void setServ(JLabel serv) {
        this.serv = serv;
    }

    public JTable getTabla() {
        return tabla;
    }

    public void setTabla(JTable tabla) {
        this.tabla = tabla;
    }

    public JComboBox getTipoServ() {
        return tipoServ;
    }

    public void setTipoServ(JComboBox tipoServ) {
        this.tipoServ = tipoServ;
    }

    public JLabel getVend() {
        return vend;
    }

    public void setVend(JLabel vend) {
        this.vend = vend;
    }

    public JTextField getVendedorDNI() {
        return vendedorDNI;
    }

    public void setVendedorDNI(JTextField vendedorDNI) {
        this.vendedorDNI = vendedorDNI;
    }

    public JButton getAtras() {
        return atras;
    }

    public void setAtras(JButton atras) {
        this.atras = atras;
    }

    
    /**
     * Devuelve, a partir de textoBoton, el nombre de la ventana a la que cambiaremos.
     * @param textoBoton
     * @return nombre de la siguiente ventana
     */
    @Override
    public String claveVentana(String textoBoton) {
        return "Inicio";
    }
    
    /**
     * Especifica el controlador a usar por la ventana de Facturación.
     * @param controlador 
     */
    @Override
    public void setControlador(ActionListener controlador) {
        this.controlador = controlador;
        this.buscar.addActionListener(controlador);
        this.tipoServ.addActionListener(controlador);
        for(JRadioButton r:bots){
            r.addActionListener(controlador);
        }
        this.atras.addActionListener(controlador);
    }
}
