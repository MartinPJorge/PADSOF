/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controladores;

import GUI.Ventanas.ModificarPass;
import cat.quickdb.db.AdminBase;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import myexception.NoResultsExc;
import padsof.Booking;
import persona.Vendedor;
import reserva.Paquete;

/**
 * Clase controladora de la Ventana ModificarPass
 * 
* @author Jorge Martín Pérez
 * @author Iván Márquez Pardo
 * @version 1.0
 */
public class ModificarPassControl implements ActionListener {

    private Booking aplic;
    private ModificarPass vista;
    private Vendedor actual;

    /**
     * Constructor del controlador
     *
     * @param vista
     * @param aplic
     */
    public ModificarPassControl(ModificarPass vista, Booking aplic) {
        this.aplic = aplic;
        this.vista = vista;
        this.actual = null;
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
            actual = null;
            this.vista.cambiarVentana(this.vista.claveVentana(textBut));
        } else if (fuente == vista.getBuscar()) {
            String idStr = vista.getIdInput().getText();
            Vendedor v;
            try {
                v = aplic.buscarVendedor(Integer.parseInt(idStr));
            } catch (NoResultsExc ex) {
                JOptionPane.showMessageDialog(vista, "La búsqueda en la Base de Datos de vendedores con el"
                        + "ID especificado no produjo ningún resultado.",
                        "ERROR - Búsqueda sin resultados", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            vista.getNameT().setText(v.getNombre());
            vista.getSurname().setText(v.getApellido());
            vista.getDni().setText(v.getDNI());
            vista.getFechaNac().setText(v.getFechaNac());
            vista.getId().setText(String.valueOf(v.getIdUsr()));
            vista.getPass().setText(v.getPassword());

            actual = v;

        } else if (fuente == vista.getModify()) {
            int opc;
            if (actual == null) {
                JOptionPane.showMessageDialog(null, "Para modificar la contraseña de un vendedor, antes debe "
                        + "buscarlo por su ID.",
                        "ERROR - Vendedor no seleccionado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String npass = vista.getNewPass().getText();
                String npass2 = vista.getRepeatNewP().getText();
                if (!npass.equals(npass2)) {
                    JOptionPane.showMessageDialog(null, "La nueva contraseña y su confirmación no"
                            + "coinciden.\nRevíselas antes de continuar.",
                            "ERROR - Contraseñas distintas", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                opc = JOptionPane.showConfirmDialog(vista, "¿Está seguro de querer cambiar la contraseña "
                        + "de este vendedor?", "Petición de Confirmación", JOptionPane.OK_CANCEL_OPTION);
                if (opc == JOptionPane.CANCEL_OPTION || opc == JOptionPane.CLOSED_OPTION) {
                    return;
                }
                cambiarPassword(npass);

                actual = null;
                this.vista.cambiarVentana(this.vista.claveVentana(textBut));
            }

        }
    }

    /**
     * Método auxiliar que realiza el cambio de contraseña en la base de datos.
     *
     * @param pass
     */
    private void cambiarPassword(String pass) {
        try {
            AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.aplic.getBookingDBName());
            Connection c = Paquete.quickDBtoJDBC(admin);
            try (Statement stmt = c.createStatement()) {
                String query = "UPDATE Vendedor SET password='" + pass + "' WHERE idUsr=" + actual.getIdUsr();
                stmt.executeUpdate(query);
                stmt.close();
            }

            JOptionPane.showMessageDialog(null, "La contraseña del vendedor especificado ha sido "
                    + "cambiada correctamente.", "Modificación realizada", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            Logger.getLogger(BajaVControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BajaVControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
