package hotel.Usuarios.Clientes;

import hotel.Acciones.Reservacion;
import hotel.Cuentas.Cuentas;
import hotel.Servicios.Servicio;
import hotel.Usuarios.Usuario;
import hotel.Usuarios.WrongDataException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *  
 * @author Andrade, Ambrosio, Lázaro y Mendoza
 */
public class Cliente extends Usuario implements Serializable{
    private Cuentas cuenta;
    private Reservacion reservacion;
    private boolean estancia = false;
    public ArrayList<Servicio> services=new ArrayList(4);
    private String clave;
    
    /**
     * Constructor vacio de las clase Cliente, inicializa la lista de servicios con 4 espacios vacios.
     */
    public Cliente(){
        for(int i=0;i<4;i++){
            services.add(new Servicio());
        }
    }
    
    /**
     * Constructor de las clase Cliente, inicializa la lista de servicios con 4 espacios vacios.
     * @param user Representa el nombre de usuario.
     * @param password Representa la contraseña del usuario.
     * @param name Representa el nombre del usuario.
     * @param lastName Representa los apellido del usuario.
     * @param tipo Representa el tipo de usuario.
     * @throws WrongDataException Lanza una excepcion si alguno de los datos no fue ingresado correctamente.
     */
    public Cliente(String user, String password, String name, String lastName, String tipo) throws WrongDataException {
        super(user, password, name, lastName, tipo);
        for(int i=0;i<4;i++){
            services.add(new Servicio());
        }
        System.out.println(services.size());
    }
    
    /**
     * Regresa la cuenta relacionada a la instancia de la clase Cliente.
     * @return Un objeto que representa la cuenta relacionada a la instancia de la clase Cliente.
     */
    public Cuentas getCuenta() {
        return cuenta;
    }

    /**
     * Asigna la cuenta relacionada a la instancia de la clase Cliente.
     * @param cuenta Representa la cuenta relacionada a la instancia de la clase Cliente.
     */
    public void setCuenta(Cuentas cuenta) {
        this.cuenta = cuenta;
    }

    /**
     * Regresa la reservacion relacionada a la instancia de la clase Cliente.
     * @return Un objeto que representa la reservacion relacionada a la instancia de la clase Cliente.
     */
    public Reservacion getReservacion() {
        return reservacion;
    }

    /**
     * Asigna la reservacion relacionada a la instancia de la clase Cliente.
     * @param reservacion Representa la reservacion relacionada a la instancia de la clase Cliente.
     */
    public void setReservacion(Reservacion reservacion) {
        this.reservacion = reservacion;
    }

    /**
     * Regresa un booleano que indica si el cliente esta en el hotel. 
     * @return Un booleano que indica si el cliente esta en el hotel. 
     */
    public boolean getEstancia() {
        return estancia;
    }

    /**
     * Asigna el booleano que indica si el cliente esta en el hotel. 
     * @param estancia Representa el booleano que indica si el cliente esta en el hotel.  
     */
    public void setEstancia(boolean estancia) {
        this.estancia = estancia;
    }

    /**
     * Regresa la lista de servicos relacionada a la instancia de la clase Cliente.
     * @return Una ArrayList que representa la lista de servicos relacionada a la instancia de la clase Cliente.
     */
    public ArrayList<Servicio> getServices() {
        return services;
    }

    /**
     * Asigna la lista de servicos relacionada a la instancia de la clase Cliente.
     * @param services Representa la lista de servicos relacionada a la instancia de la clase Cliente.
     */
    public void setServices(ArrayList<Servicio> services) {
        this.services = services;
    }
    
    /**
     * Regresa la clave de reservacion del cliente.
     * @return Una cadena que representa la clave de reservacion del cliente.
     */
    public String getClave() {
        return clave;
    }

    /**
     * Asigna la clave de reservacion del cliente.
     * @param clave Representa la clave de reservacion del cliente.
     */
    public void setClave(String clave) {
        this.clave = clave;
    }
    
    /**
     * Recorre la lista de servicios para ver si el cliente tiene servicios.
     * @return Regresa true si tiene servicios, en caso contrario regresa false.
     */
    public boolean sinServ(){
        for(Servicio s:services){
            if(s.getNombre()!=null)
                return false;
        }
        return true;
    }
}
