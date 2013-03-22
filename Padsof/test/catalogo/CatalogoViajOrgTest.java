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
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test de la clase CatalogoViajeOrg
 *
 * @author Jorge Martin, Ivan Marquez
 * @version 1.0
 */
public class CatalogoViajOrgTest {
    private static CatalogoViajOrg catalogo;
    private static AdminBase admin;
    
    public CatalogoViajOrgTest() {}
    
    @BeforeClass
    public static void setUpClass() throws FileNotFoundException, IOException {
        CatalogoViajOrgTest.catalogo = new CatalogoViajOrg("ViajesOrganizados.csv");
        CatalogoViajOrgTest.admin = AdminBase.initialize(AdminBase.DATABASE.SQLite,catalogo.getNombreBD());
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
    public void tearDown() {
    }

    /**
     * Test of leerCSV method, of class CatalogoViajOrg.<br/>
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
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite,catalogo.getNombreBD());
        Object[] o = admin.obtainJoin("SELECT * FROM InfoViajOrg", 11);
        
        // Se ha ejecutado previamente 'cleanSQL()'
        if(o == null) {
            admin.close();
            catalogo.leerCSV();
            admin = AdminBase.initialize(AdminBase.DATABASE.SQLite,catalogo.getNombreBD());
            o = admin.obtainJoin("SELECT * FROM InfoViajOrg", 11);
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
     * Test of buscaHotel method, of class CatalogoViajOrg.<br/>
     * Buscamos un viaje organizado que no exista y comprobamos que devuelve una
     *  lista vac&iacute;a.
     */
    @Test
    public void testBuscarViajeOrg() {
        ArrayList<InfoViajOrg> arr = new ArrayList<InfoViajOrg>();
        
        arr = catalogo.buscarViajeOrg(null, -1, -1, -1, null, null);
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite,catalogo.getNombreBD());
        
        assertEquals(arr.size(), 0);
    }

    /**
     * Test of generaQuery method, of class CatalogoViajOrg.<br/>
     * Generamos una consulta vac&iacute;a para comprobar que se crea bien.
     */
    @Test
    public void testGeneraQuery() {
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite,catalogo.getNombreBD());
        String query = catalogo.generaQuery(null, -1, -1, -1, null, null);
        assertEquals(query, "");
    }

    /**
     * Test of mostrarCatalogo method, of class CatalogoHotel.
     * <br/>Mostramos el cat&aacute;logo.
     */
    @Test
    public void testMostrarCatalogo() {
            catalogo.mostrarCatalogo();
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite,catalogo.getNombreBD());
    }
    
    /**
     * Test of cleanSQL method, of class CatalogoViajOrg.
     */
    @Test
    public void testCleanSQL() {
        catalogo.cleanSQL();
        
        admin = AdminBase.initialize(AdminBase.DATABASE.SQLite,catalogo.getNombreBD());
        Object[] o = admin.obtainJoin("SELECT * FROM InfoViajOrg", 11);
        Object nada = null;
        
        assertEquals(o, nada);
    }
    
}
