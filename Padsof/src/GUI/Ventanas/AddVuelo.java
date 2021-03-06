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
 * Clase para la GUI que representa la Ventana de buscar y añadir Vuelos.
 *
 * @author Jorge Martín Pérez
 * @author Iván Márquez Pardo
 * @version 1.0
 */
public class AddVuelo extends Ventana {

    private JPanel filtrarRes;
    private JButton filtrar;
    private JTextField salida;
    private JTextField hSalida;
    private JTextField ida;
    private JTextField hLlegada;
    private JScrollPane scrollResults;
    private Formulario detalles;
    private FooterServicios footer;
    private Paquete currentPaq;
    private ReservaVuelo currReserva;

    public AddVuelo(BookingFrame padre, String nombre) {
        super(new SpringLayout(), nombre, padre, 650, 600);
        this.iniFiltro();
        this.iniResultados();
        this.iniDetalles();
        this.footer = new FooterServicios(padre, "FooterVuelo");
        this.footer.getTotal().setVisible(false);
        this.footer.getTotalL().setVisible(false);

        this.add(this.filtrarRes);
        this.add(this.scrollResults);
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
        JLabel dia = new JLabel("F.Salida:");
        this.salida = new JTextField(5);
        this.hSalida = new JTextField(5);
        JTextField diaC = new JTextField(5);

        iz.addTexto(origen, this.salida);
        iz.addTexto(dia, this.hSalida);
        iz.aplicarCambios();



        //Parte derecha del filtro
        Formulario der = new Formulario();

        JLabel destino = new JLabel("Destino:");
        JLabel company = new JLabel("F.LLegada:");
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
    private void iniResultados() {
        String[] tituloColumnas = {"Origen", "Destino", "Fecha", "H.salida",
            "H.llegada", "Precio"};
        Object[][] filas = {{"Origen", "Destino", "Fecha", "H.salida",
                "H.llegada", "Precio"}};

        ZebraJTable resultados = new ZebraJTable(filas, tituloColumnas, -1);

        this.scrollResults = new JScrollPane(resultados);
    }

    /**
     * Inicializa la secci&oacute;n el la que mostramos los campos para
     * especificar los detalles.
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

    /**
     *
     * @return el footer
     */
    public FooterServicios getFooter() {
        return footer;
    }

    /**
     *
     * @return el paquete actual
     */
    public Paquete getCurrentPaq() {
        return currentPaq;
    }

    /**
     *
     * @param currentPaq
     */
    public void setCurrentPaq(Paquete currentPaq) {
        this.currentPaq = currentPaq;
    }

    /**
     *
     * @return la reserva de vuelo actual
     */
    public ReservaVuelo getCurrReserva() {
        return currReserva;
    }

    /**
     *
     * @param currReserva
     */
    public void setCurrReserva(ReservaVuelo currReserva) {
        this.currReserva = currReserva;
    }

    /**
     *
     * @return el bot&oacute;n de filtrar resultados
     */
    public JButton getFiltrar() {
        return filtrar;
    }

    /**
     *
     * @return el campo de texto con la salida
     */
    public JTextField getSalida() {
        return salida;
    }

    /**
     *
     * @return el campo de texto con la fecha de salida
     */
    public JTextField gethSalida() {
        return hSalida;
    }

    /**
     *
     * @return el campo de texto con la ida
     */
    public JTextField getIda() {
        return ida;
    }

    /**
     *
     * @return el campo de texto con la fecha de llegada
     */
    public JTextField gethLlegada() {
        return hLlegada;
    }

    /**
     *
     * @return el panel de scroll con los resultados
     */
    public JScrollPane getScrollResults() {
        return scrollResults;
    }

    /**
     *
     * @param scrollResults
     */
    public void setScrollResults(JScrollPane scrollResults) {
        this.scrollResults = scrollResults;
    }

    /**
     *
     * @return el formulario de detalles
     */
    public Formulario getDetalles() {
        return detalles;
    }

    /**
     * Especifica el controlador a usar por la ventana de Inicio.
     *
     * @param controlador
     */
    public void setControlador(ActionListener controlador) {
        this.controlador = controlador;
        this.filtrar.addActionListener(this.controlador);
        this.footer.getAdd().addActionListener(this.controlador);
        this.footer.getVolver().addActionListener(this.controlador);
    }
}
