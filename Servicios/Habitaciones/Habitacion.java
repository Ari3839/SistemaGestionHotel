package hotel.Servicios.Habitaciones;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Esta clase administra los objetos de tipo Habitacion con ayuda de los respectivos métodos de acceso de los atributos establecidos.
 * @author Andrade, Ambrosio, Lázaro y Mendoza
 */ 
public class Habitacion implements Serializable{
    private int numpersonas;
    private String nombre;
    private String cama;
    private String bano;
    private int costo, num;
    private ArrayList<Date> llegada =new ArrayList<>();
    private ArrayList<Date> salida =new ArrayList<>();
    
    /**
     * Constructor de la clase Habitacion.
     * @param numpersonas Representa la capacidad de personas de la habitacion.
     * @param nombre Representa el nombre de la habitacion.
     * @param cama Representa el tipo de cama de la habitacion.
     * @param bano Representa el tipo de baño de la habitacion.
     * @param costo Representa el costo de la habitacion por dia.
     */
    public Habitacion(int numpersonas, String nombre, String cama, String bano, int costo) {
        this.setNumpersonas(numpersonas);
        this.setNombre(nombre);
        this.setCama(cama);
        this.setBano(bano);
        this.setCosto(costo);
    }

    /**
     * Regresa la capacidad de personas de la habitacion.
     * @return Un entero que representa la capacidad de personas de la habitacion.
     */
    public int getNumpersonas() {
        return numpersonas;
    }

    /**
     * Asigna la capacidad de personas de la habitacion.
     * @param numpersonas Representa la capacidad de personas de la habitacion.
     */
    public void setNumpersonas(int numpersonas) {
        this.numpersonas = numpersonas;
    }

    /**
     * Regresa el nombre de la habitacion.
     * @return Una cadena que representa el nombre de la habitacion.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre de la habitacion.
     * @param nombre Representa el nombre de la habitacion.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Regresa el tipo de cama de la habitacion.
     * @return Una cadena que representa el tipo de cama de la habitacion.
     */
    public String getCama() {
        return cama;
    }

    /**
     * Asigna el tipo de cama de la habitacion.
     * @param cama Representa el tipo de cama de la habitacion.
     */
    public void setCama(String cama) {
        this.cama = cama;
    }

    /**
     * Regresa el tipo de baño de la habitacion.
     * @return Una cadena que representa el tipo de baño de la habitacion.
     */
    public String getBano() {
        return bano;
    }

    /**
     * Asigna el tipo de baño de la habitacion.
     * @param bano Representa el tipo de baño de la habitacion.
     */
    public void setBano(String bano) {
        this.bano = bano;
    }

    /**
     * Regresa el costo de la habitacion por dia.
     * @return un entero que representa el costo de la habitacion por dia.
     */
    public int getCosto() {
        return costo;
    }

    /**
     * Asigna el costo de la habitacion por dia.
     * @param costo Representa el costo de la habitacion por dia.
     */
    public void setCosto(int costo) {
        this.costo = costo;
    }

    /**
     * Regresa la lista de fechas de llegada a la habitacion.
     * @return Una ArrayList que representa la lista de fechas de llegada a la habitacion.
     */
    public ArrayList<Date> getLlegada() {
        return llegada;
    }

    /**
     * Agrega una fecha de llegada a la lista de fechas de llegada de la habitacion.
     * @param index Representa el indice en el que se va a agregar la fecha.
     * @param llegada Representa la fecha de llegada que se va a agregar a la lista.
     */
    public void setLlegada(int index,Date llegada) {
        this.llegada.add(index,llegada);
    }

    /**
     * Regresa la lista de fechas de salida de la habitacion.
     * @return Una ArrayList que representa la lista de fechas de salida de la habitacion.
     */
    public ArrayList<Date> getSalida() {
        return salida;
    }

    /**
     * Agrega una fecha de salida a la lista de fechas de salida de la habitacion.
     * @param index Representa el indice en el que se va a agregar la fecha.
     * @param salida Representa la fecha de salida que se va a agregar a la lista.
     */
    public void setSalida(int index,Date salida) {
        this.salida.add(index, salida);
    }

    /**
     * Regresa el numero de habitacion.
     * @return Un entero que representa el numero de habitacion.
     */
    public int getNum() {
        return num;
    }

    /**
     * Asigna el numero de habitacion.
     * @param num Representa el numero de habitacion.
     */
    public void setNum(int num) {
        this.num = num;
    }
    
    /**
     * Este metodo verifica que no se atraviesen fechas de reservacion de la habitacion, con las que hay en su lista.
     * @param ini Representa la fecha de llegada a la habitacion.
     * @param fin Representa la fecha de salida de la habitacion.
     * @return Regresa un booleano si la habitacion esta disponible en esas fechas, de lo contrario regresa false.
     */
    public boolean verificafechas(Date ini, Date fin){
        if(getLlegada().isEmpty()){
            setLlegada(0,ini);
            setSalida(0,fin);
            return true;
        }else if(getLlegada().get(0).after(fin)){
            setLlegada(0,ini);
            setSalida(0,fin);
            return true;
        }else if(getSalida().get(getSalida().size()-1).before(ini)){
            setLlegada(getLlegada().size()-1,ini);
            setSalida(getSalida().size()-1,fin);
            return true;
        }else{
            for(int i=0;i<getLlegada().size()-1;i++){
                if(getSalida().get(i).before(ini) && getLlegada().get(i+1).after(fin)){
                    setLlegada(i+1,ini);
                    setSalida(i+1,fin);  
                    return true;
                } else if(getLlegada().get(i).before(ini) && getSalida().get(i).after(fin))
                    return false;
                else if(getLlegada().get(i).after(ini) && getSalida().get(i).after(fin))
                    return false;
                else if(getLlegada().get(i).before(ini) && getSalida().get(i).before(fin))
                    return false;
            } 
        }
        return false;
    }
    
    /**
     * Regresa una cadena que representa una instancia de la Clase Habitacion.
     * @return Una cadena que representa una instancia de la Clase Habitacion.
     */
    @Override
    public String toString() {
        return "Habitacion "+ nombre +" para "+ numpersonas + " personas \n\tCama " + cama + "\n\tBaño " + bano + "\n\tCosto: $" + costo+ " por noche";
    }
}
