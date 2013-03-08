/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package padsof;

import catalogo.CatalogoHotel;
import catalogo.CatalogoViajOrg;
import catalogo.InfoViajOrg;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 *
 * @author e265923
 */
public class Padsof {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
        /*CatalogoViajOrg catalogo = new CatalogoViajOrg("ViajesOrganizados.csv");
        List<InfoViajOrg> busqueda = new ArrayList<InfoViajOrg>();

        busqueda = catalogo.buscarViajeOrg("Ibiza", -1, -1, -1, null, null);
       
        for(InfoViajOrg info : busqueda) {
            System.out.println(info);
        }*/
        
        CatalogoHotel catalogo = new CatalogoHotel("Hoteles.csv");
        
    }
}
