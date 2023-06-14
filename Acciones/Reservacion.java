package hotel.Acciones;

import hotel.Servicios.Habitaciones.Habitacion;
import java.util.Date;
import java.util.HashMap;
import java.io.Serializable;
import java.util.Iterator;

/**
 * Esta clase administra los objetos de tipo Reservacion con ayuda de los respectivos métodos de acceso de los atributos establecidos.
 * @author Andrade, Ambrosio, Lázaro y Mendoza
 */
public class Reservacion implements Serializable{
    private String huesped;
    private int personas;
    private Date llegada;
    private Date salida;
    private int dias;
    HashMap<Integer,Habitacion> hab=new HashMap<>();
    
    /**
     * Constructor de la clase Reservacion
     * @param personas Parámetro que representa el numero de personas que se van a hospedar.
     * @param llegada Parámetro que representa la fecha de llegada al hotel. 
     * @param salida Parámetro que representa la fecha de salida del hotel.
     * @param dias Parámetro que representa los dias de estancia en el hotel.
     */
    public Reservacion(int personas, Date llegada, Date salida, int dias) {
        this.personas = personas;
        this.llegada = llegada;
        this.salida = salida;
        this.dias = dias;
    }
    
    /**
     * Regresa el nombre del huesped que hizo la reservacion.
     * @return Una cadena de caracteres que representa el nombre del huesped.
     */
    public String getHuesped() {
        return huesped;
    }
    
    /**
     * Asigna el nombre del huesped que realiza la reservacion.
     * @param huesped Este parametro representa el nombre del huesped que realiza la reservacion.
     */
    public void setHuesped(String huesped) {
        this.huesped = huesped;
    }
    
    /**
     * Regresa el numero de personas que se van a hospedar.
     * @return Regresa un entero que representa la cantidad de personas que se van a hospedar. 
     */
    public int getPersonas() {
        return personas;
    }

    /**
     * Asigna el numero de personas que se van a hospedar.
     * @param personas Representa el numero de personas que se van a hospedar. 
     */
    public void setPersonas(int personas) {
        this.personas = personas;
    }

    /**
     * Regresa la fecha de llegada al hotel.
     * @return Un objeto de tipo Date que representa la fecha de llegada al hotel.
     */
    public Date getLlegada() {
        return llegada;
    }
    
    /**
     * Asigna la fecha de llegada al hotel.
     * @param llegada Este parámetro representa la fecha de llegada al hotel.
     */
    public void setLlegada(Date llegada) {
        this.llegada = llegada;
    }

    /**
     * Regresa la fecha de salida del hotel.
     * @return Un objeto de tipo Date que representa la fecha de salida del hotel. 
     */
    public Date getSalida() {
        return salida;
    }

    /**
     * Asigan la fecha de salida del hotel.
     * @param salida Representa la fecha de salida del hotel. 
     */
    public void setSalida(Date salida) {
        this.salida = salida;
    }

    /**
     * Regresa la cantidad de dias que los huespedes se van a hospedar en el hotel. 
     * @return Un entero que representa la cantidad de dias que los huespedes se van a hospedar en el hotel. 
     */
    public int getDias() {
        return dias;
    }

    /**
     * Asigna la cantidad de dias que los huespedes se van a hospedar en el hotel.
     * @param dias Representa la cantidad de dias que los huespedes se van a hospedar en el hotel.
     */
    public void setDias(int dias) {
        this.dias = dias;
    }

    /**
     * Regresa la HashMap que representa la lista de habitaciones que el huesped va a reservar.
     * @return Un HashMap que representa la lista de habitaciones que el huesped va a reservar.
     */
    public HashMap<Integer,Habitacion> getHab() {
        return hab;
    }

    /**
     * Asigna un numero de habitacion y una instancia de tipo habitacion en el HashMap de reservaciones.
     * @param num Representa el numero de habitacion.
     * @param habitacion Representa el objeto habitacion que se va a agregar al HashMap.
     */
    public void setHab(int num, Habitacion habitacion) {
        this.hab.put(num, habitacion);
    }
    
    /**
     * Recorre el HashMap de habitaciones de la reservacion e imprime uno por uno su informacion.
     */
    public void imprime(){
        HashMap<Integer,Habitacion> h=getHab();
        Iterator<Integer> i = h.keySet().iterator();
            while(i.hasNext()){
                Integer Clave = i.next();
                System.out.println(Clave +""+h.get(Clave));
            }
    }
    
    /**
     * Regresa una cadena para representar el objeto reservacion.
     * @return una cadena para representar el objeto reservacion.
     */
    @Override
    public String toString() {
        return "\u2726 Reservacion" + "\n\tHuesped:" + huesped + "\n\tPersonas:" + personas + "\n\tLlegada:" + llegada + "\n\tSalida:" + salida + "\n\tDías:" + dias + "\n\tHabitación:";
    }
}
