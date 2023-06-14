package hotel.Servicios;

import java.io.Serializable;

/**
 * Esta clase administra los objetos de tipo Servicios con ayuda de los respectivos métodos de acceso de los atributos establecidos.
 * @author Andrade, Ambrosio, Lázaro y Mendoza
 */
public class Servicio implements Serializable{
    private String nombre;
    private int costo;
    private int numAd=0;
    
    /**
     * Constructor vacio de la clase Servicio.
     */
    public Servicio(){
    }
    
    /**
     * Constructor de la clase Servicio.
     * @param n Representa el nombre sel servicio.
     */
    public Servicio(String n){
        setNombre(n);
    }
    
    /**
     * Regresa el nombre del servicio. 
     * @return Una caden que representa el nombre del servicio.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre del servicio.
     * @param nombre Representa el nombre del servicio.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Regresa el costo del servicio.
     * @return Un entero que representa el costo del servicio.
     */
    public int getCosto() {
        return costo;
    }

    /**
     * Asigna el costo del servicio.
     * @param costo Representa el costo del servicio.
     */
    public void setCosto(int costo) {
        this.costo = costo;
    }

    /**
     * Regresa el numero de adquisiciones del servicio.
     * @return Un entero que representa el numero de adquisiciones del servicio.
     */
    public int getNumAd() {
        return numAd;
    }

    /**
     * Asigna el numero de adquisiciones del servicio.
     * @param numAd Representa el numero de adquisiciones del servicio.
     */
    public void setNumAd(int numAd) {
        this.numAd = numAd;
    }
    
    /**
     * Regresa una cadena que representa una instancia del la clase Servicio.
     * @return Una cadena que representa una instancia del la clase Servicio.
     */
    @Override
    public String toString() {
        return nombre + "\u261B\tCosto $" + costo;
    }
}