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
 * Clase CatalogoHotel
 *
 * @author Jorge Martin, Ivan Marquez
 * @version 1.0
 */
public class CatalogoHotel {

    private String archivoCSV;
    private String nombreBD;

    /**
     * <br/><u>Nota:</u><br/> Es necesario cerrar toda conexi&oacute;n con la BD
     * antes de llamar a este m&eacute;todo.
     *
     * @param archivoCSV
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ParseException
     */
    public CatalogoHotel(String archivoCSV) throws FileNotFoundException, IOException, ParseException {
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

    /**
     *
     * @param nombre
     */
    public void setNombreBD(String nombre) {
        this.nombreBD = nombre;
    }

    /**
     * Lee el fichero CSV pasado como parametro, y lo vuelca en forma de BD en
     * el fichero que toma como nombre el valor almacenado por el atributo
     * nombreBD. <br/><u>Nota:</u><br/> Es necesario cerrar toda conexi&oacute;n
     * con la BD antes de llamar a este m&eacute;todo.
     *
     * @param archivoCSV
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void leerCSV() throws FileNotFoundException, IOException, ParseException {
        BufferedReader buf = new BufferedReader(new InputStreamReader(
                new FileInputStream(this.archivoCSV), Charset.forName("ISO-8859-1")));
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


            // pais
            String aux = tokens.nextToken(";");
            pais = (aux.equals("BLANK")) ? "" : aux;

            // ciudad
            aux = tokens.nextToken(";");
            ciudad = (aux.equals("BLANK")) ? "" : aux;

            // nombre
            aux = tokens.nextToken(";");
            nombre = (aux.equals("BLANK")) ? "" : aux;

            // telefono
            aux = tokens.nextToken(";");
            telefono = (aux.equals("BLANK")) ? "" : aux;

            // direccion
            aux = tokens.nextToken(";");
            direccion = (aux.equals("BLANK")) ? "" : aux;

            // CP
            aux = tokens.nextToken(";");
            CP = (aux.equals("BLANK")) ? "" : aux;

            // categoria
            aux = tokens.nextToken(";");
            categoria = (aux.equals("BLANK")) ? 0 : Integer.parseInt(aux);

            // precioSimple
            aux = tokens.nextToken(";").replace(',', '.');
            precioSimple = (aux.equals("BLANK")) ? -1 : Double.parseDouble(aux);

            // precioDoble
            aux = tokens.nextToken(";").replace(',', '.');
            precioDoble = (aux.equals("BLANK")) ? -1 : Double.parseDouble(aux);

            // precioTriple
            aux = tokens.nextToken(";").replace(',', '.');
            precioTriple = (aux.equals("BLANK")) ? -1 : Double.parseDouble(aux);

            // supDesayuno
            aux = tokens.nextToken(";").replace(',', '.');
            supDesayuno = (aux.equals("BLANK")) ? -1 : Double.parseDouble(aux);

            // supMP
            aux = tokens.nextToken(";").replace(',', '.');
            supMP = (aux.equals("BLANK")) ? -1 : Double.parseDouble(aux);

            // supPC
            aux = tokens.nextToken(";").replace(',', '.');
            supPC = (aux.equals("BLANK")) ? -1 : Double.parseDouble(aux);

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

    /**
     * Imprime el cat&aacute;logo de hoteles. <br/><u>Nota:</u><br/> Es
     * necesario cerrar toda conexi&oacute;n con la BD antes de llamar a este
     * m&eacute;todo.
     */
    public void mostrarCatalogo() {
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.getNombreBD());
        InfoHotel infoVO = new InfoHotel();
        List<InfoHotel> resultados = new ArrayList<InfoHotel>();

        resultados = admin.obtainAll(infoVO, "id >= 1");

        System.out.println("id\tnombre\tpais\tciudad\ttelefono\tdireccion\tCP\tcategoria\tprecioS\tprecioD\tprecioT\tsupDes\tsupMP\tsipPC\tcaracteristicas");
        for (InfoHotel info : resultados) {
            System.out.printf("%d\t", info.getId());
            System.out.printf("%s\t", info.getPais());
            System.out.printf("%s\t", info.getCiudad());
            System.out.printf("%s\t", info.getTelefono());
            System.out.printf("%s\t", info.getDireccion());
            System.out.printf("%s\t", info.getCP());
            System.out.printf("%d\t", info.getCategoria());
            System.out.printf("%s\t", info.getPrecioSimple());
            System.out.printf("%s\t", info.getPrecioDoble());
            System.out.printf("%s\t", info.getPrecioTriple());
            System.out.printf("%s\t", info.getSupDesayuno());
            System.out.printf("%s\t", info.getSupMP());
            System.out.printf("%s\t", info.getSupPC());
            System.out.printf("%s\n", info.getPais());
        }

        admin.close();
    }

    /**
     * Vac&iacute;a el archivo SQL. <br/><u>Nota:</u><br/> Es necesario cerrar
     * toda conexi&oacute;n con la BD antes de llamar a este m&eacute;todo.
     */
    public void cleanSQL() {
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.nombreBD);
        InfoHotel hotel = new InfoHotel();
        List<InfoHotel> hoteles = new ArrayList<InfoHotel>();

        hoteles = admin.obtainAll(hotel, "1 = 1");
        for (InfoHotel nav : hoteles) {
            admin.delete(nav);
        }

        admin.close();
    }

    /**
     * Realiza la b&uacute;squeda de un hotel dentro de la BD, teniendo en
     * cuenta los par&aacute;metros recibidos.
     *
     * <br/><u>Nota</u>:<br/> Las cadenas que no queramos especificar para la
     * b&uacute;squeda, han de pasarse como 'null', mientras que nos
     * par&aacute;metros num&eacute;ricos han de ser '-1'.
     *
     * <br/><u>Nota:</u><br/> Es necesario cerrar toda conexi&oacute;n con la BD
     * antes de llamar a este m&eacute;todo.
     *
     * @param nombre
     * @param pais
     * @param ciudad
     * @param categoria
     * @param precioSimple
     * @param precioDoble
     * @param precioTriple
     * @param supDesayuno
     * @param supMP
     * @param supPC
     * @param caracteristicas
     * @return Lista de obetos de tipo 'InfoHotel', que coinciden con la
     * b&uacute;squeda
     */
    public ArrayList<InfoHotel> buscaHotel(String nombre, String pais, String ciudad,
            int categoria, double precioSimple, double precioDoble,
            double precioTriple, double supDesayuno, double supMP,
            double supPC, String caracteristicas) {
        AdminBase admin = AdminBase.initialize(AdminBase.DATABASE.SQLite, this.nombreBD);
        String query;
        InfoHotel infoHotel = new InfoHotel();
        ArrayList<InfoHotel> resultados = new ArrayList<InfoHotel>();

        // Generamos la consulta a la BD.
        query = generaQuery(nombre, pais, ciudad, categoria, precioSimple, precioDoble,
                precioTriple, supDesayuno, supMP, supPC, caracteristicas);
        if (query.equals("") == true) {
            return resultados;
        }

        // Ejecutamos la query y cerramos la conexion.
        resultados = admin.obtainAll(infoHotel, query);
        admin.close();

        return resultados;
    }

    /**
     * Genera una consulta SQL para realizar una b&uacute;squeda en la BD del
     * cat&aacute;logo.
     *
     * @param nombre
     * @param pais
     * @param ciudad
     * @param categoria
     * @param precioSimple
     * @param precioDoble
     * @param precioTriple
     * @param supDesayuno
     * @param supMP
     * @param supPC
     * @param caracteristicas
     * @return la consulta SQL generada.
     */
    public String generaQuery(String nombre, String pais, String ciudad,
            int categoria, double precioSimple, double precioDoble,
            double precioTriple, double supDesayuno, double supMP,
            double supPC, String caracteristicas) {
        String query = "";

        if (nombre != null) {
            query += "nombre = '" + nombre + "'";
        }

        if (pais != null) {
            if (query.equals("") == false) {
                query += " AND pais = '" + pais + "'";
            } else {
                query += "pais = '" + pais + "'";
            }
        }

        if (ciudad != null) {
            if (query.equals("") == false) {
                query += " AND ciudad = '" + ciudad + "'";
            } else {
                query += "ciudad = '" + ciudad + "'";
            }
        }

        if (categoria != -1) {
            if (query.equals("") == false) {
                query += " AND categoria = " + Integer.toString(categoria);
            } else {
                query += "categoria = " + Integer.toString(categoria);
            }
        }

        if (precioSimple != -1) {
            if (query.equals("") == false) {
                query += " AND precioSimple = " + Double.toString(precioSimple);
            } else {
                query += "precioSimple = " + Double.toString(precioSimple);
            }
        }

        if (precioDoble != -1) {
            if (query.equals("") == false) {
                query += " AND precioDoble = " + Double.toString(precioDoble);
            } else {
                query += "precioDoble = " + Double.toString(precioDoble);
            }
        }

        if (precioTriple != -1) {
            if (query.equals("") == false) {
                query += " AND precioTriple = " + Double.toString(precioTriple);
            } else {
                query += "precioTriple = " + Double.toString(precioTriple);
            }
        }

        if (supDesayuno != -1) {
            if (query.equals("") == false) {
                query += " AND supDesayuno = " + Double.toString(supDesayuno);
            } else {
                query += "supDesayuno = " + Double.toString(supDesayuno);
            }
        }

        if (supMP != -1) {
            if (query.equals("") == false) {
                query += " AND supMP = " + Double.toString(supMP);
            } else {
                query += "supMP = " + Double.toString(supMP);
            }
        }

        if (supPC != -1) {
            if (query.equals("") == false) {
                query += " AND supPC = " + Double.toString(supPC);
            } else {
                query += "supPC = " + Double.toString(supPC);
            }
        }

        if (caracteristicas != null) {
            if (query.equals("") == false) {
                query += " AND caracteristicas = '" + caracteristicas + "'";
            } else {
                query += "caracteristicas = '" + caracteristicas + "'";
            }
        }

        return query;
    }
}
