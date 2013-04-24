/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controladores;

import GUI.Ventanas.BajaVendedor;
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
 *
 * @author ivan
 */
public class BajaVControl implements ActionListener {

    private Booking aplic;
    private BajaVendedor vista;
    private Vendedor actual;

    public BajaVControl(BajaVendedor vista, Booking aplic) {
        this.aplic = aplic;
        this.vista = vista;
        this.actual = null;
    }

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
                JOptionPane.showMessageDialog(null, "La búsqueda en la Base de Datos de vendedores con el"
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

        } else if (fuente == vista.getBorrar()) {
            int opc = -1;
            if (actual == null) {
                JOptionPane.showMessageDialog(null, "Para dar de baja a un vendedor, antes debe"
                        + "buscarlo por su ID.",
                        "ERROR - Vendedor no seleccionado", JOptionPane.INFORMATION_MESSAGE);
            } else if (actual.getIdUsr() == 0) {
                JOptionPane.showMessageDialog(null, "No se puede dar de baja a un administrador.",
                        "ERROR - Administrador imborrable", JOptionPane.INFORMATION_MESSAGE);
            } else {
                opc = JOptionPane.showInternalConfirmDialog(null, "¿Está seguro de querer dar de baja"
                        + "a este vendedor?",
                        "Petición de Confirmación", JOptionPane.OK_CANCEL_OPTION);
                if (opc == JOptionPane.CANCEL_OPTION || opc == JOptionPane.CLOSED_OPTION) {
                    return;
                }
                borrarVendedor();
                actual = null;
                this.vista.cambiarVentana(this.vista.claveVentana(textBut));
            }

        }
    }

    private void borrarVendedor() {
        try {
            AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.aplic.getBookingDBName());
            Connection c = Paquete.quickDBtoJDBC(admin);
            try (Statement stmt = c.createStatement()) {
                String query = "UPDATE Vendedor SET idUsr=-1, password='---' WHERE idUsr=" + actual.getIdUsr();
                stmt.executeUpdate(query);
                stmt.close();
            }
            JOptionPane.showMessageDialog(null, "El vendedor especificado ha sido borrado correctamente.",
                    "Baja terminada", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            Logger.getLogger(BajaVControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BajaVControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
