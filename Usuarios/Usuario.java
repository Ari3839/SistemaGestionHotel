package hotel.Usuarios;

import java.io.Serializable;

/**
 *
 * @author Andrade, Ambrosio, Lázaro y Mendoza
 */
public abstract class Usuario implements Serializable{
    private String password;
    private String user;
    private String name;
    private String lastName;
    private String tipo;
    
    /**
     * Constructor vacio de la clase Usuario.
     */
    public Usuario(){
    }
    
    /**
     * Constructor de la clase Usuario.
     * @param user Representa el nombre de usuario.
     * @param password Representa la contraseña del usuario.
     * @param name Representa el nombre del usuario.
     * @param lastName Representa los apellido del usuario.
     * @param tipo Representa el tipo de usuario.
     * @throws WrongDataException Lanza una excepcion si alguno de los datos no fue ingresado correctamente.
     */
    public Usuario(String user, String password, String name, String lastName, String tipo) throws WrongDataException {
        this.setUser(user);
        this.setPassword(password);
        this.setName(name);
        this.setLastName(lastName);
        this.setTipo(tipo);
    }

    /**
     * Regresa el nombre de usuario.
     * @return Una cadena que representa el nombre de usuario.
     */
    public String getUser() {
        return user;
    }

    /**
     * Asigna el nombre de usuario.
     * @param user Representa el nombre de usuario.
     * @throws WrongDataException Lanza una excepcion si alguno de los datos no fue ingresado correctamente.
     */
    public void setUser(String user) throws WrongDataException{
        if(user.length()>2){
           this.user = user;
        } else{
            System.out.println("El usuario debe tener al menos 3 letras");
            throw new WrongDataException();
        }
    }
    
    /**
     * Regresa la contraseña del usuario.
     * @return Una cadena que representa el nombre de usuario. 
     */
    public String getPassword() {
        return password;
    }

    /**
     * Asigna la contraseña del usuario.
     * @param password Representa la contraseña del usuario.
     * @throws WrongDataException Lanza una excepcion si alguno de los datos no fue ingresado correctamente.
     */
    public void setPassword(String password) throws WrongDataException{
        if(password.length()>5){
           this.password = password; 
        } else{
            System.out.println("La contraseña debe tener al menos 6 caracteres");
            throw new WrongDataException();
        }
    }

    /**
     * Regresa el nombre del usuario.
     * @return Una cadena que representa el nombre del usuario. 
     */
    public String getName() {
        return name;
    }

    /**
     * Asigna el nombre del usuario.
     * @param name Representa el nombre del usuario.
     * @throws WrongDataException Lanza una excepcion si alguno de los datos no fue ingresado correctamente.
     */
    public void setName(String name) throws WrongDataException{
        if(name.length()>2 && name.matches("[a-zA-Z]*")==true){
           this.name = name;
        } else{
            System.out.println("Nombre incorrecto");
            throw new WrongDataException();
        }
    }

    /**
     * Regresa el apellido del usuario.
     * @return Una cadena que representa el apellido del usuario.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Asigna el apellido del usuario.
     * @param lastName Representa el apellido del usuario.
     * @throws WrongDataException Lanza una excepcion si alguno de los datos no fue ingresado correctamente.
     */
    public void setLastName(String lastName)throws WrongDataException {
        if(lastName.length()>3 && lastName.matches("[a-zA-Z]*")==true){
           this.lastName = lastName;
        } else{
            System.out.println("Apellido incorrecto");
            throw new WrongDataException();
        }
    }

    /**
     * Regresa el tipo de usuario.
     * @return Una cadena que representa el tipo de usuario.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Asigna el tipo de usuario.
     * @param tipo REpresenta el tipo de usuario.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    /**
     * Regersa una cadena que representa una instancia de la clase Usuario.
     * @return Una cadena que representa una instancia de la clase Usuario.
     */
    @Override
    public String toString() {
        return "Usuario:"+ user + "\n\tPassword=" + password + "\n\tName=" + name + "\n\tLastName=" + lastName + "\n\tTipo=" + tipo;
    }
    
    
}
