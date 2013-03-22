/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reserva;

import java.util.Comparator;

/**
 * Clase IdComparator. Esta clase, que implementa la interface
 * Comparator<Paquete>, la usaremos para ordenar los Paquetes de la BD de mayor
 * a menor id (usado para asignar el idPaq correcto a los nuevos Paquete que
 * creemos)
 *
 * @author Jorge Martin, Ivan Marquez
 * @version 1.0
 */
public class IdComparator implements Comparator<Paquete> {

    @Override
    public int compare(Paquete p1, Paquete p2) {
        return p2.getId() - p1.getId();
    }
}
