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
import java.text.ParseException;
import java.util.ArrayList;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Jorge
 */
public class CatalogoHotelTest {
    
    private static CatalogoHotel catalogo;
    private static AdminBase admin;
    
    public CatalogoHotelTest() {}
    
    @BeforeClass
    public static void setUpClass() throws FileNotFoundException, IOException, ParseException {
        CatalogoHotelTest.catalogo = new CatalogoHotel("Hoteles.csv");
        CatalogoHotelTest.admin = AdminBase.initialize(AdminBase.DATABASE.SQLite,catalogo.getNombreBD());
    }
    
    @AfterClass
    public static void tearDownClass() {
        CatalogoHotelTest.admin.close();
    }
    

    /**
     * Test of leerCSV method, of class CatalogoHotel.<br/>
     * Comprobamos que si el fichero CSV tiene campos sin rellenar, nuestra BD 
     * tambi&eacute;n los tenga.
     */
    @Test
    public void testLeerCSV() throws Exception {
        BufferedReader buff = new BufferedReader(new InputStreamReader(
                                new FileInputStream(catalogo.getArchivoCSV())));
        boolean campoVacio = false;
        boolean vacioEncontrado = false;
        String linea;
        
        // Buscamos filas con campos vacios
        while((linea = buff.readLine()) != null) {
            if(linea.indexOf(";;") > -1) {
                campoVacio = true;
                break;
            }
        }
        
        // Comprobamos que se hayan guardado campos vacios
        AdminBase admin2 = AdminBase.initialize(AdminBase.DATABASE.SQLite,catalogo.getNombreBD());
        Object[] o = admin2.obtainJoin("SELECT * FROM InfoHotel", 15);
        externo: for(Object nav : o) {
            String[] fila = (String[]) nav;
            for(String col : fila) {
                if(col.equals("-1.0") || col.equals("")) {
                    vacioEncontrado = true;
                    break externo;
                }
            }
        }
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite,catalogo.getNombreBD());
        assertEquals(campoVacio, vacioEncontrado);
    }

    /**
     * Test of mostrarCatalogo method, of class CatalogoHotel.
     * <br/>Mostramos el cat&aacute;logo.
     */
    @Test
    public void testMostrarCatalogo() {
        admin.close();
        catalogo.mostrarCatalogo();
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite,catalogo.getNombreBD());
    }

    /**
     * Test of buscaHotel method, of class CatalogoHotel.<br/>
     * Buscamos un hotel que no exista y comprobamos que devuelve una lista vac&iacute;a.
     */
    @Test
    public void testBuscaHotel() {
        ArrayList result = CatalogoHotelTest.catalogo.buscaHotel("Nada", "Ninguno",
                "-", -1, -1, -1, -1, -1, -1, -1, "nada");
        assertEquals(result.size(), 0);
    }

    /**
     * Test of generaQuery method, of class CatalogoHotel.<br/>
     * Generamos una consulta vac&iacute;a para comprobar que se crea bien.
     */
    @Test
    public void testGeneraQuery() {
        String result = CatalogoHotelTest.catalogo.generaQuery(null, null, 
                null, -1, -1, -1, -1, -1, -1, -1, null);
        assertEquals(result, "");
    }
    
    /**
     * Test of cleanSQL method, of class CatalogoHotel.
     */
    @Test
    public void testCleanSQL() throws FileNotFoundException, IOException, ParseException {
        CatalogoHotelTest.catalogo.cleanSQL();
        
        Object[] o = admin.obtainJoin("SELECT * FROM InfoHotel", 15);
        Object nada = null;
        
        assertEquals(o, nada);
    }
}
