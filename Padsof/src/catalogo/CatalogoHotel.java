/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo;

import cat.quickdb.db.AdminBase;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 *
 * @author Jorge
 */
public class CatalogoHotel {
    
    private String archivoCSV;
    private String nombreBD;
    private List<InfoHotel> arrayHoteles;
    
    public CatalogoHotel(String archivoCSV) throws FileNotFoundException, IOException, ParseException {
        this.archivoCSV = archivoCSV;
        StringTokenizer tokens = new StringTokenizer(this.archivoCSV);
        this.nombreBD = tokens.nextToken(".");
        
        this.leerCSV();
    }

    /**
     * 
     * @return nombre del archivo que contiene el cat&aacute;logo.
     */
    public String getArchivoCSV() {
        return archivoCSV;
    }

    /**
     * 
     * @return nombre del archivo de la BS (sin extensi&oacute;n)
     */
    public String getNombreBD() {
        return nombreBD;
    }

    
    public void leerCSV() throws FileNotFoundException, IOException, ParseException {
        BufferedReader buf = new BufferedReader(new InputStreamReader(
                new FileInputStream(this.archivoCSV)));
        String nombre;
        String pais;
        String ciudad;
        String telefono;
        String direccion;
        String CP;
        int categoria;
        double precioSimple;
        double precioDoble;
        double precioTriple;
        double supDesayuno;
        double supMP;
        double supPC;
        String caracteristicas;
        String linea;
        InfoHotel hotel;
        int sigPtoComa;
        StringTokenizer tokens;
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite,this.nombreBD);
        
        linea = buf.readLine();  // Saltamos la cabecera
        while((linea = buf.readLine()) != null) {
            // Quitamos los ';' que aparecen juntos
            String antes = linea;
            String despues = linea.replaceAll(";;", ";BLANK;");
            while(antes != despues) {
                antes = despues;
                despues = despues.replaceAll(";;", ";BLANK;");
            }
            linea = despues;
            tokens = new StringTokenizer(linea);
                        
            
            pais = tokens.nextToken(";");
            ciudad = tokens.nextToken(";");
            nombre = tokens.nextToken(";");
            telefono = tokens.nextToken(";");
            direccion = tokens.nextToken(";");
            CP = tokens.nextToken(";");
            
            // categoria
            String numAux = tokens.nextToken(";");
            categoria = (numAux.equals("BLANK")) ? 0 : Integer.parseInt(numAux);
            
            // precioSimple
            numAux = tokens.nextToken(";").replace(',', '.');
            precioSimple = (numAux.equals("BLANK")) ? -1 : Double.parseDouble(numAux);
            
            // precioDoble
            numAux = tokens.nextToken(";").replace(',', '.');
            precioDoble = (numAux.equals("BLANK")) ? -1 : Double.parseDouble(numAux);
            
            // precioTriple
            numAux = tokens.nextToken(";").replace(',', '.');
            precioTriple = (numAux.equals("BLANK")) ? -1 : Double.parseDouble(numAux);
            
            // supDesayuno
            numAux = tokens.nextToken(";").replace(',', '.');
            supDesayuno = (numAux.equals("BLANK")) ? -1 : Double.parseDouble(numAux);
            
            // supMP
            numAux = tokens.nextToken(";").replace(',', '.');
            supMP = (numAux.equals("BLANK")) ? -1 : Double.parseDouble(numAux);
            
            // supPC
            numAux = tokens.nextToken(";").replace(',', '.');
            supPC = (numAux.equals("BLANK")) ? -1 : Double.parseDouble(numAux);
            
            caracteristicas = tokens.nextToken(";");
            
            // Ponemos comillas simples como dobles para el SQL            
            nombre = nombre.replace("'", "''");
            
            // Instanciamos el objeto y lo guardamos en la BD.
            hotel = new InfoHotel(nombre, pais, ciudad, telefono, direccion, CP, 
            categoria, precioSimple, precioDoble, precioTriple, 
                supDesayuno, supMP, supPC, caracteristicas);

            admin.save(hotel);
        }
        
        admin.close();
    }
    
}
