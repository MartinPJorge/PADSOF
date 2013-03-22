/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo;

/**
 * Clase InfoViajOrg
 *
 * @author Jorge Martin, Ivan Marquez
 * @version 1.0
 */
public class InfoViajOrg {

    private int id;  // Atributo requerido para la base de datos.
    private String nombre;
    private String compania;
    private String telefono;
    private double precio;
    private int dias;
    private int noches;
    private String fechasSalida;
    private String localidadSalida;
    private String localidades;
    private String descripcion;

    public InfoViajOrg() {
    }

    public InfoViajOrg(String nombre, String compania, String telefono,
            double precio, int dias, int noches, String fechasSalida,
            String localidadSalida, String localidades, String descripcion) {
        this.nombre = nombre;
        this.compania = compania;
        this.telefono = telefono;
        this.precio = precio;
        this.dias = dias;
        this.noches = noches;
        this.fechasSalida = fechasSalida;
        this.localidadSalida = localidadSalida;
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
     * @return la compañ&iacute;a que lo organiza.
     */
    public String getCompania() {
        return compania;
    }

    /**
     *
     * @return la descripci&oacute;n del viaje
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     *
     * @return los d&iacute;s del viaje
     */
    public int getDias() {
        return dias;
    }

    /**
     *
     * @return las fechas de salida
     */
    public String getFechasSalida() {
        return fechasSalida;
    }

    /**
     *
     * @return la localodad de salida
     */
    public String getLocalidadSalida() {
        return localidadSalida;
    }

    /**
     *
     * @return las localidades por las que pasa el viaje
     */
    public String getLocalidades() {
        return localidades;
    }

    /**
     *
     * @return el n&uacute;mero de noches
     */
    public int getNoches() {
        return noches;
    }

    /**
     *
     * @return el nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @return el precio
     */
    public double getPrecio() {
        return precio;
    }

    /**
     *
     * @return el tel&eacute;fono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * <u>No usar</u>.<br/>Este m&eacute;todo solo est&aacute; destinado al uso
     * espec&iacute;fico que hace de &eacute;l la base de datos.
     *
     * @param id - nuevo id para la BD
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
     * @param compania
     */
    public void setCompania(String compania) {
        this.compania = compania;
    }

    /**
     *
     * @param telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
     * @param fechasSalida
     */
    public void setFechasSalida(String fechasSalida) {
        this.fechasSalida = fechasSalida;
    }

    /**
     *
     * @param localidadSalida
     */
    public void setLocalidadSalida(String localidadSalida) {
        this.localidadSalida = localidadSalida;
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

    /**
     *
     * @return una cadena con el formato de impresi&oacute;n del objeto.
     */
    @Override
    public String toString() {
        return "InfoViajOrg{" + "nombre=" + nombre + ", compania=" + compania + ", telefono=" + telefono + ", precio=" + precio + ", dias=" + dias + ", noches=" + noches + ", fechasSalida=" + fechasSalida + ", localidadSalida=" + localidadSalida + ", localidades=" + localidades + ", descripcion=" + descripcion + '}';
    }

    /**
     * Compara un objeto de tipo 'InfoViajOrg' con los par&aacute;metros
     * disponibles que se le pasa al m&eacute;todo.
     *
     * @param nombre
     * @param dias
     * @param noches
     * @param precio
     * @param localidades
     * @param fechaSalida
     * @return true - el viaje organizado coincide con los campos disponibles
     * @return false - el viaje organizado no coincide con los campos
     * disponibles
     *
     * <u>Nota</u>:<br/> Los par&aacute;metros que no queramos tener en cuenta
     * los pondremos como -1 si es un n&uacute;mero, o como "" si es un String.
     */
    public boolean compareTo(String nombre, int dias, int noches,
            double precio, String localidades, String fechaSalida) {

        if (nombre.equals("") == false) {
            if (this.getNombre().equals(nombre) == false) {
                return false;
            }
        } else if (dias != -1) {
            if (dias != this.getDias()) {
                return false;
            }
        } else if (noches != -1) {
            if (noches != this.getNoches()) {
                return false;
            }
        } else if (precio != -1) {
            if (precio != this.getPrecio()) {
                return false;
            }
        } else if (localidades.equals("") == false) {
            if (localidades.equals(this.getLocalidades()) == false) {
                return false;
            }
        } else if (fechaSalida.equals("") == false) {
            if (fechaSalida.equals(this.getFechasSalida()) == false) {
                return false;
            }
        }

        return true;
    }
}
