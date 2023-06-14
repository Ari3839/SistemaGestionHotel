package hotel.Cuentas;

import hotel.Usuarios.WrongDataException;

/**
 * Esta clase administra los objetos de tipo General con ayuda de los respectivos métodos de acceso de los atributos establecidos, extiende de la clase Cuentas.
 * @author Andrade, Ambrosio, Lázaro y Mendoza
 */
public class General extends Cuentas {
    
    /**
     * Constructor de la clase General.
     * @param correo Representa un correo electronico. 
     * @param telefono Representa un numero de telefono.
     * @param tarjeta Representa un numero de tarjeta.
     * @throws WrongDataException Lanza una excepcion si alguno de los datos no fue ingresado correctamente.
     */
    public General(String correo, String telefono, String tarjeta) throws WrongDataException {
        super(correo, telefono, tarjeta);
    }
    
    /**
     * Constructor vacio de la clase General.
     */
    public General(){
    }
}
