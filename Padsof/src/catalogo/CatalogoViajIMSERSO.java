/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo;

import cat.quickdb.db.AdminBase;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Clase CatalogoViajIMSERSO
 *
 * @author Jorge Martin, Ivan Marquez
 * @version 1.0
 */
public class CatalogoViajIMSERSO {

    private String archivoCSV;
    private String nombreBD;

    CatalogoViajIMSERSO() {
    }

    /**
     * <br/><u>Nota:</u><br/> Es necesario cerrar toda conexi&oacute;n con la BD
     * antes de llamar a este m&eacute;todo.
     *
     * @param archivoCSV
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ParseException
     */
    public CatalogoViajIMSERSO(String archivoCSV) throws FileNotFoundException, IOException, ParseException {
        this.archivoCSV = archivoCSV;

        StringTokenizer tokens = new StringTokenizer(this.archivoCSV);
        this.nombreBD = tokens.nextToken(".");

        // Si existia la BD, borramos lo que habia.
        File BD = new File(this.nombreBD + ".db");
        if (BD.exists()) {
            this.cleanSQL();
        }

        this.leerCSV();
    }

    /**
     *
     * @return nombre del archivo de la BS (sin extensi&oacute;n)
     */
    public String getNombreBD() {
        return nombreBD;
    }

    /**
     *
     * @return nombre del archivo que contiene el cat&aacute;logo.
     */
    public String getArchivoCSV() {
        if (this == null) {
            return "";
        }
        return archivoCSV;
    }

    /**
     * Lee el fichero CSV pasado como parametro, y lo vuelca en forma de BD en
     * el fichero que toma como nombre el valor almacenado por el atributo
     * nombreBD. <br/><u>Nota:</u><br/> Es necesario cerrar toda conexi&oacute;n
     * con la BD antes de llamar a este m&eacute;todo.
     *
     * @param admin
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void leerCSV() throws FileNotFoundException, IOException, ParseException {
        BufferedReader buf = new BufferedReader(new InputStreamReader(
                new FileInputStream(this.archivoCSV), "ISO-8859-1"));
        String nombre;
        double precioCliente;
        int dias;
        int noches;
        String fechasSalida;
        String localidadSalida;
        String localidades;
        String descripcion;
        String linea;
        StringTokenizer tokens;
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.nombreBD);

        linea = buf.readLine();  // Saltamos la cabecera
        while ((linea = buf.readLine()) != null) {
            // Quitamos los ';' que aparecen juntos
            String antes = linea;
            String despues = linea.replaceAll(";;", ";BLANK;");
            while (antes != despues) {
                antes = despues;
                despues = despues.replaceAll(";;", ";BLANK;");
            }
            linea = despues;
            tokens = new StringTokenizer(linea);


            // nombre
            String aux = tokens.nextToken(";");
            nombre = (aux.equals("BLANK")) ? "" : aux;

            // precio cliente
            aux = tokens.nextToken(";");
            precioCliente = (aux.equals("BLANK")) ? -1 : Double.parseDouble(aux);

            // dias
            aux = tokens.nextToken(";");
            dias = (aux.equals("BLANK")) ? -1 : Integer.parseInt(aux);

            // noches
            aux = tokens.nextToken(";");
            noches = (aux.equals("BLANK")) ? -1 : Integer.parseInt(aux);

            // fechas de salida
            aux = tokens.nextToken(";");
            fechasSalida = (aux.equals("BLANK")) ? "" : aux;

            // localidad de salida
            aux = tokens.nextToken(";");
            localidadSalida = (aux.equals("BLANK")) ? "" : aux;

            // localidades
            aux = tokens.nextToken(";");
            localidades = (aux.equals("BLANK")) ? "" : aux;

            // descripcion
            aux = tokens.nextToken(";");
            descripcion = (aux.equals("BLANK")) ? "" : aux;

            // Instanciamos el objeto y lo guardamos en la BD.
            InfoViajeIMSERSO viajIMSERSO = new InfoViajeIMSERSO(nombre,
                    precioCliente, dias, noches,
                    fechasSalida, localidadSalida,
                    localidades, descripcion);


            admin.save(viajIMSERSO);
        }

        admin.close();
    }

    /**
     * Busca informaci&oacute;n de viajes del IMSERSO a partir de los datos
     * proporcionados al m&eacute;todo para filtrar la b&uacute;squeda.
     *
     * <br/><u>Nota</u>:<br/> Las cadenas que no queramos especificar para la
     * b&uacute;squeda, han de pasarse como 'null', mientras que nos
     * par&aacute;metros num&eacute;ricos han de ser '-1'.
     * <br/><u>Nota:</u><br/> Es necesario cerrar toda conexi&oacute;n con la BD
     * antes de llamar a este m&eacute;todo.
     *
     * @param nombre
     * @param dias
     * @param noches
     * @param precio
     * @param localidades
     * @param fechaSalida
     * @return una lista con los viajes del IMSERSO encontrados.
     */
    public ArrayList<InfoViajeIMSERSO> buscarViajeOrg(String nombre, int dias, int noches,
            double precio, String localidades, String fechaSalida) {
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.nombreBD);
        String query;
        InfoViajeIMSERSO infoVO = new InfoViajeIMSERSO();
        ArrayList<InfoViajeIMSERSO> resultados = new ArrayList<InfoViajeIMSERSO>();

        // Generamos la consulta a la BD.
        query = generaQuery(nombre, dias, noches, precio, localidades, fechaSalida);
        if (query.equals("") == true) {
            return resultados;
        }

        // Ejecutamos la query y cerramos la conexion.
        resultados = admin.obtainAll(infoVO, query);
        admin.close();
        
        for(InfoViajeIMSERSO info : resultados) {
            info.setId(0);
        }

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
    public String generaQuery(String nombre, int dias, int noches, double precio,
            String localidades, String fechaSalida) {
        String query = "";

        if (nombre != null) {
            query += "nombre = '" + nombre + "'";
        }

        if (dias != -1) {
            if (query.equals("")) {
                query += "dias = " + Integer.toString(dias);
            } else {
                query += " AND dias = " + Integer.toString(dias);
            }
        }

        if (noches != -1) {
            if (query.equals("")) {
                query += "noches = " + Integer.toString(noches);
            } else {
                query += " AND noches = " + Integer.toString(noches);
            }
        }

        if (precio != -1) {
            if (query.equals("")) {
                query += "precio <= " + Double.toString(precio);
            } else {
                query += " AND precio <= " + Double.toString(precio);
            }
        }

        if (localidades != null) {
            if (query.equals("")) {
                query += "localidades = '" + localidades + "'";
            } else {
                query += " AND localidades = '" + localidades + "'";
            }
        }

        if (fechaSalida != null) {
            if (query.equals("")) {
                query += "fechaSalida = '" + fechaSalida + "'";
            } else {
                query += " AND fechaSalida = '" + fechaSalida + "'";
            }
        }

        return query;
    }

    /**
     * Vac&iacute;a el archivo SQL. <br/><u>Nota:</u><br/> Es necesario cerrar
     * toda conexi&oacute;n con la BD antes de llamar a este m&eacute;todo.
     */
    public void cleanSQL() {
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.nombreBD);
        InfoViajeIMSERSO infoVO = new InfoViajeIMSERSO();
        List<InfoViajeIMSERSO> registros = new ArrayList<InfoViajeIMSERSO>();

        registros = admin.obtainAll(infoVO, "id >= 1");
        for (InfoViajeIMSERSO inf : registros) {
            admin.delete(inf);
        }

        admin.close();
    }

    /**
     * Imprime el cat&aacute;logo de viajes del IMSERSO. <br/><u>Nota:</u><br/>
     * Es necesario cerrar toda conexi&oacute;n con la BD antes de llamar a este
     * m&eacute;todo.
     */
    public void mostrarCatalogo() {
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.nombreBD);
        InfoViajeIMSERSO infoVIMSERSO = new InfoViajeIMSERSO();
        List<InfoViajeIMSERSO> resultados = new ArrayList<InfoViajeIMSERSO>();

        resultados = admin.obtainAll(infoVIMSERSO, "precio > -2");

        System.out.println("id\tnombre\tprecio\tdías\tnoches\tfechasSalida\tlocalidades\tdescripción");
        for (InfoViajeIMSERSO info : resultados) {
            System.out.printf("%d\t", info.getId());
            System.out.printf("%s\t", info.getNombre());
            System.out.printf("%s\t", info.getPrecio());
            System.out.printf("%d\t", info.getDias());
            System.out.printf("%d\t", info.getNoches());
            System.out.printf("%s\t", info.getFechaSalida());
            System.out.printf("%s\t", info.getLocalidades());
            System.out.printf("%s\n", info.getDescripcion());
        }

        admin.close();
    }
}
