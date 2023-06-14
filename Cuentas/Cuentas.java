package hotel.Cuentas;

import hotel.Usuarios.WrongDataException;
import java.io.Serializable;

/**
 * Esta clase administra los objetos de tipo Cuentas con ayuda de los respectivos métodos de acceso de los atributos establecidos.
 * @author Andrade, Ambrosio, Lázaro y Mendoza
 */
public abstract class Cuentas implements Serializable{
    private String correo;
    private String telefono;
    private String tarjeta;
    
    /**
     * Constructor vacio de la clase Cuentas.
     */
    public Cuentas(){
    }
    
    /**
     * Constructor de la clase Cuentas.
     * @param correo Representa un correo electronico. 
     * @param telefono Representa un numero de telefono.
     * @param tarjeta Representa un numero de tarjeta.
     * @throws WrongDataException Lanza una excepcion si alguno de los datos no fue ingresado correctamente.
     */
    public Cuentas( String correo, String telefono, String tarjeta) throws WrongDataException{
        this.setCorreo(correo);
        this.setTarjeta(tarjeta);
        this.setTelefono(telefono);
    }

    /**
     * Regresa el correo electornico vinculado a la cuenta.
     * @return Una cadena que representa el correo electornico vinculado a la cuenta.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Asigna el correo electronico a la cuenta, pero solo si cumple las especificaciones.
     * @param correo Representa un correo electronico. 
     * @throws WrongDataException Lanza una excepcion si alguno de los datos no fue ingresado correctamente. 
     */
    public void setCorreo(String correo) throws WrongDataException {
        if(correo.length()>2 && correo.contains(".com") && correo.contains("@")){
            this.correo = correo;
        }else{
            throw new WrongDataException();
        }
    }

    /**
     * Regresa el numero de telefono de la cuenta.
     * @return Una cadena que representa el numero de telefono de la cuenta. 
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Asigna el numero de telefono de la cuenta.
     * @param telefono Representa el numero de telefono de la cuenta.
     * @throws WrongDataException Lanza una excepcion si alguno de los datos no fue ingresado correctamente. 
     */
    public void setTelefono(String telefono) throws WrongDataException {
        try{
            long k=Long.parseLong(telefono);
        }catch(NumberFormatException e){
            System.out.println("La informacion debe ser numérica");
        }
        
        if(telefono.length()==10  ){
            this.telefono = telefono;
            
        }else{
            throw new WrongDataException();
        }
    }

    /**
     * Regresa el numero de tarjeta vinculado a la cuenta.
     * @return Una cadena que representa el numero de tarjeta vinculado a la cuenta.
     */
    public String getTarjeta() {
        return tarjeta;
    }

    /**
     * Asigna el numero de tarjeta de la cuenta.
     * @param tarjeta Representa el numero de tarjeta vinculado a la cuenta.
     * @throws WrongDataException Lanza una excepcion si alguno de los datos no fue ingresado correctamente. 
     */
    public void setTarjeta(String tarjeta) throws WrongDataException {
        try{
            long k=Long.parseLong(tarjeta);
        }catch(NumberFormatException e){
            System.out.println("Ingresa un número, por favor");
        }
        if(tarjeta.length()==16){
            this.tarjeta=tarjeta;
        }else{
            System.out.println("El numero de tarjeta debe ser de 16 digitos");
            throw new WrongDataException();
        }
    }

    /**
     * Regresa una cadena que representa al objeto de tipo Cuentas.
     * @return Una cadena que representa al objeto de tipo Cuentas.
     */
    @Override
    public String toString() {
        return "Cuenta "+"\n\tCorreo=" + correo + "\n\tTelefono=" + telefono + "\n\tNumero de tarjeta=" + tarjeta;
    }
    
}
