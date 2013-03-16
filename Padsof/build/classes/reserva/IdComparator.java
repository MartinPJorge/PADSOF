/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reserva;

import java.util.Comparator;

/**
 *
 * @author ivan
 */
public class IdComparator implements Comparator<Paquete>{
    @Override
    public int compare(Paquete p1, Paquete p2) {
        return p1.getId() - p2.getId();
    }
    
}
