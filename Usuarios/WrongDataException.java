package hotel.Usuarios;

/**
 * Esta clase representa un excepcion si algun tipo de dato es ingresado incorrectamente.
 * @author Andrade, Ambrosio, Lázaro y Mendoza
 */
public class WrongDataException extends Exception{
    
    /**
     * Constructor de la clase WrongDataException
     */
    public WrongDataException(){
        super("Información incorrecta");
    }
}
