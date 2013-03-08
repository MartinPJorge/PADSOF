/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo;

/**
 *
 * @author e265923
 */
public class InfoViajeIMSERSO {
    
    final private String nombre;
    final private double precio;
    final private int dias;
    final private int noches;
    final private String fechaSalida;
    final private String locSalida;
    final private String localidades;
    final private String descripcion;

    public InfoViajeIMSERSO(String nombre, double precio, int dias, int noches, String fechaSalida, String locSalida, String localidades, String descripcion) {
        this.nombre = nombre;
        this.precio = precio;
        this.dias = dias;
        this.noches = noches;
        this.fechaSalida = fechaSalida;
        this.locSalida = locSalida;
        this.localidades = localidades;
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getDias() {
        return dias;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public String getLocSalida() {
        return locSalida;
    }

    public String getLocalidades() {
        return localidades;
    }

    public int getNoches() {
        return noches;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return "InfoViajeIMSERSO{" + "nombre=" + nombre + ", precio=" + precio + ", dias=" + dias + ", noches=" + noches + ", fechaSalida=" + fechaSalida + ", locSalida=" + locSalida + ", localidades=" + localidades + ", descripcion=" + descripcion + '}';
    }
    
    
}
