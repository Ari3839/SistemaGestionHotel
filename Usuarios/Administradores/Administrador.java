package hotel.Usuarios.Administradores;

import hotel.Usuarios.Usuario;
import hotel.Usuarios.WrongDataException;

/**
 * Esta clase administra los objetos de tipo Administrador con ayuda de los respectivos métodos de acceso de los atributos establecidos, extiende de la clase Usuario.
 * @author Andrade, Ambrosio, Lázaro y Mendoza
 */
public class Administrador extends Usuario{
    
    /**
     * Constructor vacio de la clase Administrador.
     */
    public Administrador(){
    }
    
    /**
     * Constructor de la clase Administrador.
     * @param user Representa el nombre de usuario.
     * @param password Representa la contraseña del usuario.
     * @param name Representa el nombre del usuario.
     * @param lastName Representa los apellido del usuario.
     * @param tipo Representa el tipo de usuario.
     * @throws WrongDataException Lanza una excepcion si alguno de los datos no fue ingresado correctamente.
     */
    public Administrador(String user, String password, String name, String lastName, String tipo) throws WrongDataException {
        super(user, password, name, lastName, tipo);
    }
    
}
