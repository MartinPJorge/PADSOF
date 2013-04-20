/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package padsof;

import es.uam.eps.pads.services.InvalidParameterException;
import es.uam.eps.pads.services.ServicesFactory;
import es.uam.eps.pads.services.UnavailableResourceException;
import es.uam.eps.pads.services.flights.AirportInfo;
import es.uam.eps.pads.services.flights.FlightsProvider;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import reserva.Vuelos;

/**
 *
 * @author Jorge
 */
public class pruebaTabla {
    public static void main(String[] args) throws InvalidParameterException, UnavailableResourceException {
        /*FlightsProvider fp= ServicesFactory.getServicesFactory().getFlightsProvider();
        Date start= new GregorianCalendar(2014, 0, 1).getTime();
        Date end= new GregorianCalendar(2014, 0, 6).getTime();
        
        
        //Vuelos.obtenerVuelos("BRUSSELS NATL", "STANSTED", start, end);
        List<String> results = fp.queryFlights("BRUSSELS NATL", "STANSTED", start, end);
        
        for(String s : results) {
            System.out.println(Vuelos.getLlegada(s));
            System.out.println(Vuelos.getSalida(s));
            System.out.println("-----------------");
        }*/
        
        FlightsProvider fp= ServicesFactory.getServicesFactory().getFlightsProvider();
        List<AirportInfo> airports= fp.queryAirports();
        AirportInfo source= airports.get(0);
        AirportInfo destination= airports.get(1);
        //Query flights from 2014/1/1 to 2014/1/6 (using Calendar to use System Time Zone)
        Date start= new GregorianCalendar(2013, 10, 22).getTime();
        Date end= new GregorianCalendar(2013, 10, 22).getTime();
        List<String> flights=fp.queryFlights("BRU", "STN", start, end);
        String loc=fp.book(flights.get(0), "Miguel Mora");
        fp.cancel(loc);
        
        for(String s : flights) {
            System.out.println(Vuelos.getLlegada(s));
            System.out.println(Vuelos.getSalida(s));
            System.out.println("-----------------");
        }
    }
    
}
