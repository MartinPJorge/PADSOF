/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controladores;

import GUI.Ventanas.AltaVendedor;
import cat.quickdb.db.AdminBase;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import myexception.NoResultsExc;
import padsof.Booking;
import persona.Vendedor;

/**
 * Clase controladora de la Ventana AltaVendedor
 *
 * @author Jorge Martín Pérez
 * @author Iván Márquez Pardo
 * @version 1.0
 */
public class AltaVControl implements ActionListener {

    private Booking aplic;
    private AltaVendedor vista;

    /**
     * Constructor del controlador
     *
     * @param vista
     * @param aplic
     */
    public AltaVControl(AltaVendedor vista, Booking aplic) {
        this.aplic = aplic;
        this.vista = vista;
    }

    /**
     * Método que lleva a cabo el control efectivo.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object fuente = e.getSource();
        String textBut = ((JButton) fuente).getText();
        if (fuente == vista.getAtras()) {
            this.vista.cambiarVentana(this.vista.claveVentana(textBut));
        } else if (fuente == vista.getCrear()) {
            String nombre = vista.getNameT().getText();
            String apellidos = vista.getSurname().getText();
            String DNI = vista.getDni().getText();
            Date fechaNac = vista.getFechaNac().getDate();
            String idUsrStr = vista.getId().getText();
            String pass = vista.getPass().getText();

            if (nombre == null || apellidos == null || DNI == null || fechaNac == null || idUsrStr.isEmpty() || pass == null) {
                JOptionPane.showMessageDialog(null, "Existe al menos un campo obligatorio sin rellenar.\n"
                        + "Revise los datos introducidos y vuelva a intentar registrar al vendedor.",
                        "Datos insuficientes", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaNac);
            int dia = cal.get(Calendar.DAY_OF_MONTH);
            int mes = cal.get(Calendar.MONTH);
            int anio = cal.get(Calendar.YEAR);
            int idUsr = Integer.parseInt(idUsrStr);
            Vendedor nuevo = new Vendedor(nombre, apellidos, DNI, dia, mes, anio, idUsr, pass, this.aplic.getSesion().getIdUsr());
            AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.aplic.getBookingDBName());
            Vendedor v = null;
            boolean repeat = true;
            try {
                v = aplic.buscarVendedor(idUsr);
            } catch (NoResultsExc ex) {
                repeat = false;
            }
            if (repeat) {
                JOptionPane.showMessageDialog(null, "Ya existe un vendedor en la base de datos con"
                        + "ese ID de Vendedor.\nIntroduzca otro distinto y vuelva a intentarlo.",
                        "ERROR - Datos repetidos", JOptionPane.INFORMATION_MESSAGE);
                admin.close();
            } else {
                int opc = -1;
                JOptionPane.showConfirmDialog(vista, "¿Desea registrar a este nuevo vendedor?",
                        "Petición de Confirmación", JOptionPane.OK_CANCEL_OPTION);
                if (opc == JOptionPane.CANCEL_OPTION) {
                    admin.close();
                    return;
                }
                admin.save(nuevo);
                JOptionPane.showMessageDialog(null, "El nuevo vendedor ha sido registrado correctamente.",
                        "Registro terminado", JOptionPane.INFORMATION_MESSAGE);
                admin.close();
                this.vista.cambiarVentana(this.vista.claveVentana(textBut));
            }


        }
    }
}
