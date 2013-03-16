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
    
    private int id;
    private String nombre;
    private double precio;
    private int dias;
    private int noches;
    private String fechaSalida;
    private String locSalida;
    private String localidades;
    private String descripcion;

    
    public InfoViajeIMSERSO() {}
    
    public InfoViajeIMSERSO(String nombre, double precio, int dias, int noches,
                            String fechaSalida, String locSalida, 
                            String localidades, String descripcion) {
        this.nombre = nombre;
        this.precio = precio;
        this.dias = dias;
        this.noches = noches;
        this.fechaSalida = fechaSalida;
        this.locSalida = locSalida;
        this.localidades = localidades;
        this.descripcion = descripcion;
    }

    /**
     * 
     * @return el id de la clase (usado para la BD)
     */
    public int getId() {
        return id;
    }

    /**
     * 
     * @return la descripci&oacute;n del viaje organizado
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * 
     * @return d&iaacute;s que dura el viaje
     */
    public int getDias() {
        return dias;
    }

    /**
     * 
     * @return fechas de salida del viaje
     */
    public String getFechaSalida() {
        return fechaSalida;
    }

    /**
     * 
     * @return localidad de salida
     */
    public String getLocSalida() {
        return locSalida;
    }

    /**
     * 
     * @return localidades por las que pasa el viaje
     */
    public String getLocalidades() {
        return localidades;
    }

    /**
     * 
     * @return noches que dura el viaje
     */
    public int getNoches() {
        return noches;
    }

    /**
     * 
     * @return nombre del viaje del IMSERSO
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * 
     * @return precio del viaje
     */
    public double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return "InfoViajeIMSERSO{" + "nombre=" + nombre + ", precio=" + precio + ", dias=" + dias + ", noches=" + noches + ", fechaSalida=" + fechaSalida + ", locSalida=" + locSalida + ", localidades=" + localidades + ", descripcion=" + descripcion + '}';
    }

    /**
     * 
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 
     * @param nombre 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * 
     * @param precio 
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * 
     * @param dias 
     */
    public void setDias(int dias) {
        this.dias = dias;
    }

    /**
     * 
     * @param noches 
     */
    public void setNoches(int noches) {
        this.noches = noches;
    }

    /**
     * 
     * @param fechaSalida 
     */
    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    /**
     * 
     * @param locSalida 
     */
    public void setLocSalida(String locSalida) {
        this.locSalida = locSalida;
    }

    /**
     * 
     * @param localidades 
     */
    public void setLocalidades(String localidades) {
        this.localidades = localidades;
    }

    /**
     * 
     * @param descripcion 
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
