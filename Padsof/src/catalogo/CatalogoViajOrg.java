/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo;

import cat.quickdb.annotation.Table;
import cat.quickdb.db.AdminBase;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author e265923
 */
public class CatalogoViajOrg {
    
    private String archivoCSV;
    private String nombreBD;


    public CatalogoViajOrg(String archivoCSV) throws FileNotFoundException, IOException {
        this.archivoCSV = archivoCSV;
        StringTokenizer tokens = new StringTokenizer(this.archivoCSV);
        this.nombreBD = tokens.nextToken(".");
        
        // Si existia la BD, borramos lo que habia.
        File BD = new File(this.nombreBD + ".db");
        if(BD.exists()) {
           this.cleanSQL(); 
        }
        
        this.leerCSV();
    }

    
    /**
     * 
     * @return nombre del archivo que contiene el cat&aacute;logo.
     */
    public String getArchivoCSV() {
        if(this == null) {
            return "";
        }
        return archivoCSV;
    }

    /**
     * 
     * @return nombre del archivo de la BS (sin extensi&oacute;n)
     */
    public String getNombreBD() {
        return nombreBD;
    }
    
    
    
    /**
     * Lee el fichero CSV pasado como parametro, y lo vuelca en forma de BD en 
     * el fichero que toma como nombre el valor almacenado por el atributo nombreBD.
     * @param archivoCSV
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void leerCSV() throws FileNotFoundException, IOException {
        BufferedReader buf = new BufferedReader (new InputStreamReader(new FileInputStream(this.archivoCSV)));
        String linea;
        String nombre;  // PK
        String compania;
        String telefono;
        double precio;
        int dias;
        int noches;
        String fechasSalida;
        String localidadSalida;
        String localidades;
        String descripcion;
        
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite,this.nombreBD);
        
        linea = buf.readLine();  // Saltamos la cabecera.
        while( (linea = buf.readLine()) != null ) {
            StringTokenizer tokens = new StringTokenizer(linea);
            
            // Sacamos la informacion.
            nombre = tokens.nextToken(";");
            compania = tokens.nextToken(";");
            telefono = tokens.nextToken(";");
            precio = Double.parseDouble(tokens.nextToken(";"));
            dias = Integer.parseInt(tokens.nextToken(";"));
            noches = Integer.parseInt(tokens.nextToken(";"));
            fechasSalida = tokens.nextToken(";");
            localidadSalida = tokens.nextToken(";");
            localidades = tokens.nextToken(";");
            descripcion = tokens.nextToken(";");
            
            // Creamos la clase de informacion.
            InfoViajOrg infoVO = new InfoViajOrg(nombre, compania, telefono,
                    precio, dias, noches, fechasSalida, localidadSalida,
                    localidades, descripcion);
            
            
            admin.save(infoVO);
            }
        
        buf.close();
        admin.close();
    }
    
    
    /**
     * Imprime el cat&aacute;logo de viajes organizados.
     */
    public void mostrarCatalogo() {
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite,this.nombreBD);
        InfoViajOrg infoVO = new InfoViajOrg();
        List<InfoViajOrg> resultados = new ArrayList<InfoViajOrg>();
        
        resultados = admin.obtainAll(infoVO, "precio > 0");
        
        for(InfoViajOrg info : resultados) {
            System.out.println(info);
        }
        
        admin.close();
    }
    
    
    /**
     * Busca informaci&oacute;n de viajes organizados a partir de los datos 
     * proporcionados al m&eacute;todo para filtrar la b&uacute;squeda.
     * 
     * <br/><u>Nota</u>:<br/>
     * Las cadenas que no queramos especificar para la b&uacute;squeda, han de 
     * pasarse como 'null', mientras que nos par&aacute;metros num&eacute;ricos 
     * han de ser '-1'.
     * 
     * @param nombre
     * @param dias
     * @param noches
     * @param precio
     * @param localidades
     * @param fechaSalida
     * @return una lista con los viajes organizados encontrados.
     */
    public List<InfoViajOrg> buscarViajeOrg(String nombre, int dias, int noches,
            double precio, String localidades, String fechaSalida) {
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite,this.nombreBD);
        String query;
        InfoViajOrg infoVO = new InfoViajOrg();
        List<InfoViajOrg> resultados = new ArrayList<InfoViajOrg>();
        
        // Generamos la consulta a la BD.
        query = generaQuery(nombre, dias, noches, precio, localidades, fechaSalida);
        if(query.equals("") == true) {
            return resultados;
        }
              
        // Ejecutamos la query y cerramos la conexion.
        resultados = admin.obtainAll(infoVO, query);
        admin.close();
        
        return resultados;
    }
    
    
    /**
     * Genera una consulta SQL para realizar una b&uacute;squeda en la BD del 
     * cat&aacute;logo.
     * 
     * @param nombre
     * @param dias
     * @param noches
     * @param precio
     * @param localidades
     * @param fechaSalida
     * @return la consulta SQL generada.
     */
    public String generaQuery(String nombre, int dias, int noches,
            double precio, String localidades, String fechaSalida) {
        
        String query = "";
        
        if(nombre != null) {
            query += "nombre = '" + nombre + "'";
        }
        
        if(dias != -1) {
            if(query.equals("")) {
                query += "dias = " + Integer.toString(dias);
            }
            else {
                query += " AND dias = " + Integer.toString(dias);
            }
        }
        
        if(noches != -1) {
            if(query.equals("")) {
                query += "noches = " + Integer.toString(noches);
            }
            else {
                query += " AND noches = " + Integer.toString(noches);
            }
        }
        
        if(precio != -1) {
            if(query.equals("")) {
                query += "precio = " + Double.toString(precio);
            }
            else {
                query += " AND precio = " + Double.toString(precio);
            }
        }
        
        if(localidades != null) {
            if(query.equals("")) {
                query += "localidades = '" + localidades + "'";
            }
            else {
                query += " AND localidades = '" + localidades + "'";
            }
        }
        
        if(fechaSalida != null) {
            if(query.equals("")) {
                query += "fechaSalida = '" + fechaSalida + "'";
            }
            else {
                query += " AND fechaSalida = '" + fechaSalida + "'";
            }
        }
        
        return query;
    }
    
    /**
     * Vac&iacute;a el archivo SQL.
     */
    public void cleanSQL() {
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite,this.nombreBD);
        InfoViajOrg infoVO = new InfoViajOrg();
        List<InfoViajOrg> registros = new ArrayList<InfoViajOrg>();
        
        registros = admin.obtainAll(infoVO, "1 = 1");
        for(InfoViajOrg inf : registros) {
            admin.delete(inf);
        }
        
        admin.close();
    }
}
