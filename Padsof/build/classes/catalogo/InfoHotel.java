/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo;

/**
 *
 * @author e265923
 */
public class InfoHotel {  
    
    private int id;
    private String nombre;
    private String pais;
    private String ciudad;
    private String telefono;
    private String direccion;
    private String CP;
    private int categoria;
    private double precioSimple;
    private double precioDoble;
    private double precioTriple;
    private double supDesayuno;
    private double supMP;
    private double supPC;
    private String caracteristicas;

    public InfoHotel () {}
    
    public InfoHotel(String nombre, String pais, String ciudad, String telefono, String direccion, String CP, 
            int categoria, double precioSimple, double precioDoble, double precioTriple, 
                double supDesayuno, double supMP, double supPC, String caracteristicas) {
        this.nombre = nombre;
        this.pais = pais;
        this.ciudad = ciudad;
        this.telefono = telefono;
        this.direccion = direccion;
        this.CP = CP;
        this.categoria = categoria;
        this.precioSimple = precioSimple;
        this.precioDoble = precioDoble;
        this.precioTriple = precioTriple;
        this.supDesayuno = supDesayuno;
        this.supMP = supMP;
        this.supPC = supPC;
        this.caracteristicas = caracteristicas;
    }

    
    /**
     * 
     * @return id del objeto (para la BD)
     */
    public int getId() {
        return id;
        }
    
    /**
     * 
     * @return nombre del hotel
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * 
     * @return CP
     */
    public String getCP() {
        return CP;
    }

    /**
     * 
     * @return String con las caracter&iacute;sticas.
     */
    public String getCaracteristicas() {
        return caracteristicas;
    }
    
    /**
     * 
     * @return categor&iacute;a
     */
    public int getCategoria() {
        return categoria;
    }

    /**
     * 
     * @return ciudad del hotel
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * 
     * @return direcci&oacute;n del hotel
     */
    public String getDireccion() {
        return direccion;
    }


    /**
     * 
     * @return pa&iacute;s en el que est&aacute; el hotel
     */
    public String getPais() {
        return pais;
    }

    /**
     * 
     * @return precio de habitaci&oacute;n doble
     */
    public double getPrecioDoble() {
        return precioDoble;
    }

    /**
     * 
     * @return precio de habitaci&oacute;n simple
     */
    public double getPrecioSimple() {
        return precioSimple;
    }

    /**
     * 
     * @return precio de habitaci&oacute;n triple
     */
    public double getPrecioTriple() {
        return precioTriple;
    }

    /**
     * 
     * @return precio del suplemento del desayuno
     */
    public double getSupDesayuno() {
        return supDesayuno;
    }

    /**
     * 
     * @return precio del suplemento de la media pensi&oacute;n
     */
    public double getSupMP() {
        return supMP;
    }

    /**
     * 
     * @return precio del suplemento de la pensi&oacute;n completa
     */
    public double getSupPC() {
        return supPC;
    }

    /**
     * 
     * @return n&uacute;mero de tel&eacute;fono
     */
    public String getTelefono() {
        return telefono;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCP(String CP) {
        this.CP = CP;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public void setPrecioSimple(double precioSimple) {
        this.precioSimple = precioSimple;
    }

    public void setPrecioDoble(double precioDoble) {
        this.precioDoble = precioDoble;
    }

    public void setPrecioTriple(double precioTriple) {
        this.precioTriple = precioTriple;
    }

    public void setSupDesayuno(double supDesayuno) {
        this.supDesayuno = supDesayuno;
    }

    public void setSupMP(double supMP) {
        this.supMP = supMP;
    }

    public void setSupPC(double supPC) {
        this.supPC = supPC;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }
    
    

    
    @Override
    /**
     * @return devuelve la info. en forma de String
     */
    public String toString() {
        return "InfoHotel{" + "nombre=" + nombre + ", pais=" + pais + ", ciudad=" + ciudad +
                ", telefono=" + telefono + ", direccion=" + direccion + ", CP=" + CP +
                ", categoria=" + categoria + ", precioSimple=" + precioSimple + 
                ", precioDoble=" + precioDoble + ", precioTriple=" + precioTriple + 
                ", supDesayuno=" + supDesayuno + ", supMP=" + supMP + 
                ", supPC=" + supPC + ", caracteristicas=" + caracteristicas + '}';
    }
    
    
}
