/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reserva;

import es.uam.eps.pads.services.InvalidParameterException;
import es.uam.eps.pads.services.ServicesFactory;
import es.uam.eps.pads.services.UnavailableResourceException;
import es.uam.eps.pads.services.flights.AirportInfo;
import es.uam.eps.pads.services.flights.FlightInfo;
import es.uam.eps.pads.services.flights.FlightsProvider;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jorge
 */
public class Vuelos {
    
    /**
     * Obtiene el listado de Vuelos con origen en el aeropuerto con nombre 
     * 'salida', destino 'llegada', fecha de ida 'ida' y fecha de vuelta 'vuelta'.
     * @param salida
     * @param llegada
     * @param ida
     * @param vuelta
     * @return listado de Vuelos solicitados.
     */    
    public static List<String> obtenerVuelos(String salida, String llegada, 
                                    Date ida, Date vuelta) {
        List<String> vuelos = new ArrayList<String>();
        int i,j;
        
        if(salida == null || llegada == null || ida == null || vuelta == null) {
            return vuelos;
        }
        
        FlightsProvider fp = ServicesFactory.getServicesFactory().getFlightsProvider();
        List<AirportInfo> airports = fp.queryAirports();
         
        // Obtenemos las posiciones que ocupan los aeropuertos.
        for(i = 0; i < airports.size(); i++) {
            if(salida.equals(airports.get(i).getName())) {break;}
        }
        for(j = 0; j < airports.size(); j++) {
            if(llegada.equals(airports.get(j).getName())) {break;}
        }
        
        // Comprobamos que se hayan encontrado
        if(i == airports.size() || j == airports.size()) {
            return vuelos;
        }
        
        // Obtenemos el listados de id's de los vuelos encontrados.
        try {
            vuelos = fp.queryFlights(airports.get(i).getCode(), 
                    airports.get(j).getCode(), ida, vuelta);
        } catch (InvalidParameterException ex) {
            Logger.getLogger(Vuelos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error en la busqueda de vuelos - parámetro inválido.");
        }
        
        
        return vuelos;
    }
    
    
    /**
     * Muestra la informaci&oacute;n de los Vuelos cuyo id est&aacute;n en el 
     * array 'Vuelos'.
     * @param Vuelos 
     */
    private static void mostrarVuelos(List<String> vuelos) {
        System.out.println("id\tsalida\tllegada\tprecio");
        FlightsProvider fp = ServicesFactory.getServicesFactory().getFlightsProvider();
        
        if(vuelos == null) {return;}
        
        for(String nav : vuelos) {
            FlightInfo info = new FlightInfo();
            try {
                info = fp.flightInfo(nav);
            } catch (InvalidParameterException ex) {
                Logger.getLogger(Vuelos.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error en la busqueda de vuelos - parámetro inválido.");
            }
            
            System.out.println(info.getCode() + "\t" + info.getSource() + "\t" + info.getDestination() + "\t" + info.getPrice());
        }
    }
    
    
    /**
     * Reserva el vuelo con id 'vuelo' para la persona con nombre y apellido 
     * 'nombreApellido'.
     * @param vuelo
     * @param nombreApellido
     * @return el localizador de la reserva realizada.
     */
    public static ReservaVuelo reservar(String vuelo, String nombreApellido, 
                                        String DNI) {
        FlightsProvider fp = ServicesFactory.getServicesFactory().getFlightsProvider();
        String localizador;
        
        if(vuelo == null || nombreApellido == null || DNI == null) {
            return null;
        }
        
        // Obtenemos el precio del vuelo pasado como argumento.
        double precio;
        try {
            precio = fp.flightInfo(vuelo).getPrice();
        } catch (InvalidParameterException ex) {
            Logger.getLogger(Vuelos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error en la reserva - parámetro inválido.");
            return null;
        }
        
        // Hacemos la reserva del vuelo.
        try {
            localizador = fp.book(vuelo, nombreApellido);
        } catch (InvalidParameterException ex) {
            Logger.getLogger(Vuelos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error en la reserva - parámetro inválido.");
            localizador = null;
        } catch (UnavailableResourceException ex) {
            Logger.getLogger(Vuelos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error en la reserva - servicio no disponible.");
            localizador = null;
        }
        
        // Obtenemos la fecha de salida del vuelo
        GregorianCalendar cal = new GregorianCalendar();
        try {
            cal.setTime(fp.flightInfo(vuelo).getDeparture());
        } catch (InvalidParameterException ex) {
            Logger.getLogger(Vuelos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error en la reserva - no se ha podido obtener la "
                    + "fecha de salida.");
        }
        
        return new ReservaVuelo(cal.get(Calendar.DAY_OF_WEEK), cal.get(Calendar.MONTH),
                cal.get(Calendar.YEAR), nombreApellido, DNI, localizador, precio);
    }
    
    
    /**
     * Cancela la reserva con el localizador devuelto tras la realizaci&oacute;n 
     * de la reserva.
     * @param localizador 
     */
    public static void cancelar(String localizador) {
        FlightsProvider fp = ServicesFactory.getServicesFactory().getFlightsProvider();
        
        try {
            fp.cancel(localizador);
        } catch (InvalidParameterException ex) {
            Logger.getLogger(Vuelos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error en la cancelacion - parámetro inválido.");
        }
    }
    
    
    /**
     * Confirma la reserva del vuelo con localizador 'localizador'.
     * @param localizador 
     */
    public static void confirmar(String localizador) {
        try {
            FlightsProvider fp = ServicesFactory.getServicesFactory().getFlightsProvider();
            fp.confirm(localizador);
        } catch (InvalidParameterException ex) {
            Logger.getLogger(Vuelos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error en la confirmacion - parámetro inválido.");
        }
    }
}
