package hotel.Cuentas;

import hotel.Usuarios.WrongDataException;

/**
 *  Esta clase administra los objetos de tipo Extranjero con ayuda de los respectivos métodos de acceso de los atributos establecidos, estiende de la clase General.
 * @author Andrade, Ambrosio, Lázaro y Mendoza
 */
public class Extranjero extends General{
    private String pasaporte;
    private String pais;
    private String idioma;
    
    /**
     * Constructor vacio de la clase Extranjero.
     */
    public Extranjero(){
    }

    /**
     * Constructor de la clase extranjero.
     * @param correo Representa un correo electronico. 
     * @param telefono Representa un numero de telefono.
     * @param tarjeta Representa un numero de tarjeta.
     * @param pais Representa el pais de origen del propietario de la cuenta. 
     * @param pasaporte Representa el numero de pasaporte del propietario de la cuenta.
     * @param idioma Representa el idioma de origan del propietario de la cuenta. 
     * @throws WrongDataException Lanza una excepcion si alguno de los datos no fue ingresado correctamente.
     */
    public Extranjero(String correo, String telefono, String tarjeta,String pais,String pasaporte,String idioma) throws WrongDataException {
        super(correo, telefono, tarjeta);
        this.setPasaporte(pasaporte);
        this.setPais(pais);
        this.setIdioma(idioma);
    }

    /**
     * Regresa el numero de pasaporte del huesped.
     * @return Una cadena que representa el numero de pasaporte del huesped.
     */
    public String getPasaporte() {
        return pasaporte;
    }

    /**
     * Asigna el numero de pasaporte del huesped.
     * @param pasaporte Representa el numero de pasaporte del huesped.
     * @throws WrongDataException Lanza una excepcion si alguno de los datos no fue ingresado correctamente.
     */
    public void setPasaporte(String pasaporte) throws WrongDataException {
        if(pasaporte.length()>2){
            this.pasaporte = pasaporte;
        }else{
            throw new WrongDataException();
        }
    }

    /**
     * Regresa el pais del que es originario el huesped.
     * @return Una cadena que representa el pais del que es originario el huesped.
     */
    public String getPais() {
        return pais;
    }

    /**
     * Asigna el pasi de origen del huesped.
     * @param pais Representa el pais del que es originario el huesped.
     * @throws WrongDataException Lanza una excepcion si alguno de los datos no fue ingresado correctamente.
     */
    public void setPais(String pais) throws WrongDataException {
        if(pais.length()>2 && pais.matches("[a-zA-Z]*")==true){
            this.pais = pais;
        }else{
            throw new WrongDataException();
        }
        
    }

    /**
     * Regresa el idioma de origen del huesped.
     * @return Una cadena que representa el idioma de origen del huesped.
     */
    public String getIdioma() {
        return idioma;
    }

    /**
     * Asigna el idioma de origen del huesped.
     * @param idioma Representa el idioma de origen del huesped.
     * @throws WrongDataException Lanza una excepcion si alguno de los datos no fue ingresado correctamente.
     */
    public void setIdioma(String idioma) throws WrongDataException {
        if(idioma.length()>2 && idioma.matches("[a-zA-Z]*")==true){
            this.idioma = idioma;
        }else{
            throw new WrongDataException();
        }
    }

    /**
     * Regresa una cadena que representa una instancia de la clase Extranjero.
     * @return Una cadena que representa una instancia de la clase Extranjero.
     */
    @Override
    public String toString() {
        return super.toString() + "\n\tPasaporte=" + pasaporte + "\n\tPais=" + pais + "\n\tIdioma=" + idioma;
    }
}
