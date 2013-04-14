/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Ventanas;

import GUI.Recursos.Formulario;
import GUI.Recursos.SpringUtilities;
import GUI.Recursos.ZebraJTable;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import reserva.Paquete;
import reserva.ReservaVuelo;

/**
 *
 * @author Jorge
 */
public class AddVuelo extends Ventana{
    private JPanel filtrarRes;
    private JButton filtrar;
    
    private JTextField salida;
    private JTextField hSalida;
    private JTextField ida;
    private JTextField hLlegada;
    
    private Formulario detalles;
    private ZebraJTable resultados;
    private FooterServicios footer;
    private Paquete currentPaq;
    private ReservaVuelo currReserva;
    
    
    public AddVuelo(BookingFrame padre, String nombre) {
        super(new SpringLayout(), nombre,padre, 300,100);
        this.iniFiltro();
        JScrollPane tabla = this.iniResultados();
        this.iniDetalles();
        this.footer = new FooterServicios(padre, "FooterVuelo");
        this.footer.getTotal().setVisible(false);
        this.footer.getTotalL().setVisible(false);
        
        this.add(this.filtrarRes);
        this.add(tabla);
        this.add(this.detalles);
        this.add(this.footer);
        this.currReserva = new ReservaVuelo();
        
        SpringUtilities.makeCompactGrid(this, 4, 1, 6, 6, 6, 6);
        this.setVisible(true);
    }
    
    /**
     * Inicializa la secci&oacute;n en la que mostramos los datos a filtrar.
     */
    private void iniFiltro() {
        this.filtrarRes = new JPanel(new SpringLayout());
        
        //Parte izquierda del filtro
        Formulario iz = new Formulario();
        
        JLabel origen = new JLabel("Salida:");
        JLabel dia = new JLabel("H.Salida:");
        this.salida = new JTextField(5);
        this.hSalida = new JTextField(5);
        JTextField diaC = new JTextField(5);
        
        iz.addTexto(origen, this.salida);
        iz.addTexto(dia, this.hSalida);
        iz.aplicarCambios();
        
        
        
        //Parte derecha del filtro
        Formulario der = new Formulario();
        
        JLabel destino = new JLabel("Destino:");
        JLabel company = new JLabel("H.LLegada:");
        this.ida = new JTextField(5);
        this.hLlegada = new JTextField(5);

       
        der.addTexto(destino, this.ida);
        der.addTexto(company, this.hLlegada);
        der.aplicarCambios();

        
        JPanel campos = new JPanel();
        campos.add(iz);
        campos.add(der);
        
        this.filtrarRes.add(campos);
        this.filtrar = new JButton("Buscar");
        JPanel panBoton = new JPanel(new GridBagLayout());
        panBoton.add(this.filtrar);
        this.filtrarRes.add(panBoton);
        SpringUtilities.makeCompactGrid(this.filtrarRes, 2, 1, 6, 6, 6, 6);
        
        this.filtrarRes.setBorder(BorderFactory.createTitledBorder("Filtrar resultados"));
    }
    
    /**
     * Inicializa la secci&pacute;n en la que se muestran los resultados.
     */
    private JScrollPane iniResultados() {
        String[] tituloColumnas = {"Origen","Destino", "H.salida", 
            "H.llegada","Precio"};
        String[][] valores = {{"","","","",""}};
        this.resultados = new ZebraJTable(valores, tituloColumnas, -1);
        return new JScrollPane(this.resultados);
    }
    
    /**
     * Inicializa la secci&oacute;n el la que mostramos los campos para especificar 
     * los detalles.
     */
    private void iniDetalles() {
        this.detalles = new Formulario("Detalles");
        
        //Creamos los elementos
        JLabel nombre = new JLabel("Nombre:");
        JTextField nombreC = new JTextField(10);
        JLabel apellidos = new JLabel("Apellidos:");
        JTextField apellidosC = new JTextField(10);
        JLabel dni = new JLabel("DNI:");
        JTextField dniC = new JTextField(10);
        
        //Introducimos los elementos
        this.detalles.addTexto(nombre, nombreC);
        this.detalles.addTexto(apellidos, apellidosC);
        this.detalles.addTexto(dni, dniC);
        this.detalles.aplicarCambios();
    }

    @Override
    public String claveVentana(String textoBoton) {
        return "NuevoPaquete";
    }

    public FooterServicios getFooter() {
        return footer;
    }

    public Paquete getCurrentPaq() {
        return currentPaq;
    }

    public void setCurrentPaq(Paquete currentPaq) {
        this.currentPaq = currentPaq;
    }

    public ReservaVuelo getCurrReserva() {
        return currReserva;
    }

    public void setCurrReserva(ReservaVuelo currReserva) {
        this.currReserva = currReserva;
    }

    public JButton getFiltrar() {
        return filtrar;
    }

    public JTextField getSalida() {
        return salida;
    }

    public JTextField gethSalida() {
        return hSalida;
    }

    public JTextField getIda() {
        return ida;
    }

    public JTextField gethLlegada() {
        return hLlegada;
    }

    public ZebraJTable getResultados() {
        return resultados;
    }

    public void setResultados(ZebraJTable resultados) {
        this.resultados = resultados;
    }

    public Formulario getDetalles() {
        return detalles;
    }
    
    
        
    /**
     * Especifica el controlador a usar por la ventana de Inicio.
     * @param controlador 
     */
    public void setControlador(ActionListener controlador) {
        this.controlador = controlador;
        this.filtrar.addActionListener(this.controlador);
        this.footer.getAdd().addActionListener(this.controlador);
        this.footer.getVolver().addActionListener(this.controlador);
    }
    
}
