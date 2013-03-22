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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test de la clase CatalogoViajIMSERSO
 *
 * @author Jorge Martin, Ivan Marquez
 * @version 1.0
 */
public class CatalogoViajIMSERSOTest {
    private static CatalogoViajIMSERSO catalogo;
    private static AdminBase admin;
    
    public CatalogoViajIMSERSOTest() {}
    
    @BeforeClass
    public static void setUpClass() throws FileNotFoundException, IOException, ParseException {
        CatalogoViajIMSERSOTest.catalogo = new CatalogoViajIMSERSO("ViajesIMSERSO.csv");
        CatalogoViajIMSERSOTest.admin = AdminBase.initialize(AdminBase.DATABASE.SQLite,catalogo.getNombreBD());
    }
    
    @AfterClass
    public static void tearDownClass() {
        admin.close();
    }
    
    @Before
    public void setUp() {
        admin.close();
    }
    
    @After
    public void tearDown() {}

    /**
     * Test of leerCSV method, of class CatalogoViajIMSERSO.
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
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite,catalogo.getNombreBD());
        Object[] o = admin.obtainJoin("SELECT * FROM InfoViajeIMSERSO", 9);
        
        // Se ha ejecutado previamente 'cleanSQL()'
        if(o == null) {
            admin.close();
            catalogo.leerCSV();      
            admin = AdminBase.initialize(AdminBase.DATABASE.SQLite,catalogo.getNombreBD());
            o = admin.obtainJoin("SELECT * FROM InfoViajeIMSERSO", 9);
        }
        
        externo: for(Object nav : o) {
            String[] fila = (String[]) nav;
            for(String col : fila) {
                if(col.equals("-1.0") || col.equals("")) {
                    vacioEncontrado = true;
                    break externo;
                }
            }
        }
        
        assertEquals(campoVacio, vacioEncontrado);
    }

    /**
     * Test of buscaHotel method, of class CatalogoViajIMSERSO.<br/>
     * Buscamos un viaje organizado que no exista y comprobamos que devuelve una
     *  lista vac&iacute;a.
     */
    @Test
    public void testBuscarViajeOrg() {
        ArrayList<InfoViajeIMSERSO> arr = new ArrayList<InfoViajeIMSERSO>();
        arr = catalogo.buscarViajeOrg(null, -1, -1, -1, null, null);
        
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite,catalogo.getNombreBD());
        assertEquals(arr.size(), 0);
    }

    /**
     * Test of generaQuery method, of class CatalogoViajIMSERSO.<br/>
     * Generamos una consulta vac&iacute;a para comprobar que se crea bien.
     */
    @Test
    public void testGeneraQuery() {
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite,catalogo.getNombreBD());
        String query = catalogo.generaQuery(null, -1, -1, -1, null, null);
        assertEquals(query, "");
    }

    /**
     * Test of mostrarCatalogo method, of class CatalogoViajIMSERSO.
     */
    @Test
    public void testMostrarCatalogo() {
        catalogo.mostrarCatalogo();
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite,catalogo.getNombreBD());
    }
    
    /**
     * Test of cleanSQL method, of class CatalogoViajIMSERSO.
     */
    @Test
    public void testCleanSQL() {
        catalogo.cleanSQL();
        
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite,catalogo.getNombreBD());
        Object[] o = admin.obtainJoin("SELECT * FROM InfoViajeIMSERSO", 9);
        Object nada = null;
        
        assertEquals(o, nada);
    }
}
