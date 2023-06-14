package hotel.Cuentas;

import hotel.Usuarios.WrongDataException;

/**
 *
 * @author Andrade, Ambrosio, Lázaro y Mendoza
 */
public class Socio extends General{
    private int numSocio;
    
    /**
     * Constructor vacio de la clase Socio.
     */
    public Socio(){
    }

    /**
     * Constructor de la clase Socio.
     * @param correo Representa un correo electronico. 
     * @param telefono Representa un numero de telefono.
     * @param tarjeta Representa un numero de tarjeta.
     * @param numSocio Representa el numero de socio.
     * @throws WrongDataException Lanza una excepcion si alguno de los datos no fue ingresado correctamente.
     */
    public Socio(String correo, String telefono, String tarjeta,int numSocio) throws WrongDataException {
        super(correo, telefono, tarjeta);
        this.setNumSocio(numSocio);
    }

    /**
     * Regresa el numero de socio asociado a la cuenta de tipo Socio.
     * @return Un entero que representa el numero de socio asociado a la cuenta de tipo Socio.
     */
    public int getNumSocio() {
        return numSocio;
    }

    /**
     * Asigna el numero de soci a la cuenta.
     * @param numSocio Representa el numero de socio.
     * @throws WrongDataException WrongDataException Lanza una excepcion si alguno de los datos no fue ingresado correctamente. 
     */
    public void setNumSocio(int numSocio) throws WrongDataException {
        if(numSocio>0){
            this.numSocio = numSocio;
        }else{
            throw new WrongDataException();
        }
        
    }

    /**
     * Regresa una cadena que representa a una instancia de la clase Socio.
     * @return Una cadena que representa a una instancia de la clase Socio.
     */
    @Override
    public String toString() {
        return super.toString()+ "\n\tNumero de Socio=" + numSocio;
    }
    
    
    
}
