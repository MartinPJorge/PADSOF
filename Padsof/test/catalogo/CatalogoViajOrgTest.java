/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import catalogo.CatalogoViajOrg;
import java.io.IOException;
import static org.junit.Assert.*;

/**
 *
 * @author Jorge
 */
public class CatalogoViajOrgTest {

    private CatalogoViajOrg catalogo;
    
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws IOException {
        catalogo = new CatalogoViajOrg("ViajesOrganizados.csv");
    }
    
    @After
    public void tearDown() {
    }


    /**
     * Test of addInfoViajesOrg method, of class CatalogoViajOrg.
     */
    @Test
    public void testAddInfoViajesOrg() {
        System.out.println("addInfoViajesOrg");
        InfoViajOrg infoViajeOrg = null;
        catalogo.addInfoViajesOrg(infoViajeOrg);
    }

    /**
     * Test of leerCSV method, of class CatalogoViajOrg.
     */
    @Test
    public void testLeerCSV() throws Exception {
        System.out.println("leerCSV");
        int registrosIni, registrosEnd;
        
        registrosIni = catalogo.getInfoViajesOrg().size();
        catalogo.leerCSV(catalogo.getArchivoCSV());
        registrosEnd = catalogo.getInfoViajesOrg().size();
        
        assertEquals("Se vuelven a añadir las info. existentes.",registrosIni, registrosEnd);
    }

    /**
     * Test of mostrarCatalogo method, of class CatalogoViajOrg.
     */
    @Test
    public void testMostrarCatalogo() {
        System.out.println("mostrarCatalogo");
        catalogo.mostrarCatalogo();
    }

    /**
     * Test of buscarViajeOrg method, of class CatalogoViajOrg.
     */
    @Test
    public void testBuscarViajeOrg() {
        System.out.println("buscarViajeOrg");
        String nombre = "Ibiza";
        int dias = -1;
        int noches = -1;
        double precio = -1;
        String localidades = "";
        String fechaSalida = "";
        boolean coinciden;
        
        // Creamos un viaje a Ibiza, y luego buscamos lo mismo en el catalogo.   
        List<InfoViajOrg> result = catalogo.buscarViajeOrg(nombre, dias, noches, 
                precio, localidades, fechaSalida);
        coinciden = result.get(0).compareTo(nombre, dias, noches, 
                precio, localidades, fechaSalida);
        
        assertSame("No coincide la busqueda con el resultado esperado.",true, coinciden);
    }
}
