
package hotel;

import hotel.Acciones.MSuper;
import hotel.Acciones.Reservacion;
import hotel.Servicios.Habitaciones.Habitacion;
import hotel.Usuarios.Administradores.Administrador;
import hotel.Usuarios.Clientes.Cliente;
import hotel.Usuarios.Usuario;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.TreeMap;

/**
 * Esta clase contiene funciones para poder leer y guardar de archivos los distintos tipos de datos que se establecieron
 * @author Andrade, Ambrosio, Lázaro y Mendoza
 */
public class Files {
    
    /**
     * Esta clase guarda los objetos de tipo usuario que hay en la LinkedList dependiendo del tipo de que clase sean instancia  
     * @param users Representa la lista ligada que se quiere guardar en  los archivos
     */
    public static void guardarUsuarios(LinkedList<Usuario> users){
        ObjectOutputStream fo;
        Usuario aux;
        
        try {
            fo = new ObjectOutputStream(new FileOutputStream("src\\hotel\\Usuarios\\Clientes\\Clientes.dat"));
            for(int i=0;i<users.size();i++){
                if(users.get(i) instanceof Cliente){
                   aux=users.get(i);
                   fo.writeObject(aux);
                }
            }
            fo.writeObject(null);
            fo.close();
        } 
        catch (IOException e){
            System.out.println("Error de IO: ");
            e.printStackTrace();
        }
        
        try {
            fo = new ObjectOutputStream(new FileOutputStream("src\\hotel\\Usuarios\\Administradores\\Administradores.dat"));
            for(int i=0;i<users.size();i++){
                if(users.get(i) instanceof Administrador){
                   aux=users.get(i);
                   fo.writeObject(aux);
                }
            }
            fo.writeObject(null);
            fo.close();
        } 
        catch (IOException e){
            System.out.println("Error de IO: ");
            e.printStackTrace();
        }
        
        users.clear();
    }
    
    /**
     * Este metodo lee los objetos de tipo Cliente del archivo Clientes.dat 
     * @param users Representa la lista en la que se van a guardar los objetos clientes que se van a leer del archivo
     */
    public static void leerClientes(LinkedList<Usuario> users){
        ObjectInputStream fi;
        Usuario user;
        try{
            boolean aux = false;
            fi = new ObjectInputStream(new FileInputStream("src\\hotel\\Usuarios\\Clientes\\Clientes.dat"));
            user = null;
            while(!aux) {
                user=(Cliente)(fi.readObject());
                if (user!=null&&!users.contains(user)){
                    users.add(user);
                }
                else
                    aux = true;
            }
            fi.close();
        }
        
        catch (ClassNotFoundException e) {
            System.out.println("Error de clase: ");
            e.printStackTrace();
        }
        catch (IOException e){
            System.out.println("Error de IO: ");
            e.printStackTrace();
        } 
    }
    
    /**
     * Este metodo lee los objetos de tipo Administrador del archivo Administradores.dat 
     * @param users Representa la lista en la que se van a guardar los objetos administrador que se van a leer del archivo
     */
    public static void leerAdmins(LinkedList<Usuario> users){
        ObjectInputStream fi;
        Usuario user;
        try{
            boolean aux = false;
            fi = new ObjectInputStream(new FileInputStream("src\\hotel\\Usuarios\\Administradores\\Administradores.dat"));
            user = null;
            while(!aux) {
                user=(Administrador)(fi.readObject());
                if (user!=null&&!users.contains(user)){
                    users.add(user);
                }
                else
                    aux = true;
            }
            fi.close();
        }
        
        catch (ClassNotFoundException e) {
            System.out.println("Error de clase: ");
            e.printStackTrace();
        }
        catch (IOException e){
            System.out.println("Error de IO: ");
            e.printStackTrace();
        } 
    }
    
    /**
     * Este metodo imprime los elementos de la lista ligada que recibe como parametro usando un for-each
     * @param users Representa la lista ligada que se va a imprimir
     */
    public static void imprimir(LinkedList<Usuario> users){
        for(Usuario user: users){
            System.out.println(user);
        }
    }
    
    /**
     * Guarda el TreeMap que recibe como parametro en el archivo con el nombre que tambien recibe como parametro
     * @param habitaciones Representa el TreeMap que se va a guardar en el archivo 
     * @param name Representa el nombre del archivo en el que se va a guardar el TreeMap
     */
    public static void guardaHabitaciones(TreeMap<Integer,Habitacion> habitaciones,String name){
        ObjectOutputStream fo;
        Habitacion aux;
        try{    
            fo = new ObjectOutputStream(new FileOutputStream("src\\hotel\\Servicios\\Habitaciones\\"+name+".dat"));

            fo.writeObject(habitaciones);
            fo.close();
        
        } catch (IOException e){
            System.out.println("Error de IO: ");
            e.printStackTrace();
        }  
    }
    
    /**
     * Regresa el TreeMap que lee del archivo con el nombre que reciba como parametro 
     * @param name Representa el nombvre del archivo del que se va a leer el TreeMap
     * @return Regresa el TreeMap leido
     */
    public static TreeMap<Integer,Habitacion> leerHabitaciones( String name){
            ObjectInputStream fi;
            int algo=0;
            TreeMap<Integer,Habitacion> hab = null;
        try{
            fi = new ObjectInputStream(new FileInputStream("src\\hotel\\Servicios\\Habitaciones\\"+name+".dat"));
            hab = (TreeMap<Integer,Habitacion>) fi.readObject();
            fi.close();
            return hab;
        }
        
        catch (ClassNotFoundException e) {
            System.out.println("Error de clase: ");
            e.printStackTrace();
        }
        catch (IOException e){
            System.out.println("Error de IO: ");
            e.printStackTrace();
        } 
        
        return hab;
        
    }
    
    /**
     * Guarda en el archivo reservaciones.dat la HashTable de objetos de tipo Reservacion que recibe como parametro
     * @param reservaciones Representa la HashTable a guardar en el archivo
     */
    public static void guardareservaciones(Hashtable<String,Reservacion> reservaciones){
        ObjectOutputStream fo;
        Habitacion aux;
        try{    
            fo = new ObjectOutputStream(new FileOutputStream("src\\hotel\\Acciones\\reservaciones.dat"));

            fo.writeObject(reservaciones);
            fo.close();
        
        } catch (IOException e){
            System.out.println("Error de IO: ");
            e.printStackTrace();
        }  
    }
    
    /**
     * Lee del archivo reservaciones.dat la HashTable de objetos de tipo Reservaciones
     * @return Regresa la HasTable leida 
     */
    public static Hashtable<String,Reservacion> leerReservaciones(){
        ObjectInputStream fi;
        Hashtable<String,Reservacion> rev = null;
        try{
            fi = new ObjectInputStream(new FileInputStream("src\\hotel\\Acciones\\reservaciones.dat"));
            rev = (Hashtable<String,Reservacion>) fi.readObject();
            fi.close();
            return rev;
        }catch (ClassNotFoundException e) {
            System.out.println("Error de clase: ");
            e.printStackTrace();
        }catch (IOException e){
            System.out.println("Error de IO: ");
            e.printStackTrace();
        }
        return rev;
    }
    
    /**
     * Guarda las ganancias del Hotel en el archivo Recaudado.txt
     */
    public static void guardarRecaudado(){
        PrintWriter fileOut;
        try{
            fileOut = new PrintWriter(new FileWriter("src\\hotel\\Recaudado.txt", true));
            Date fecha = new Date();
            if(MSuper.getGanancias() > 0){
                fileOut.println(fecha);
                fileOut.print("\t$" + MSuper.getGanancias());
            }
            fileOut.close();
        }
        catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }
    
    /**
     * Lee e imprime en pantalla el texto que hay en el archivo Recaudado.txt
     */
    public static void leerRecaudado(){
        FileReader f;
        try {
            f=new FileReader("src\\hotel\\Recaudado.txt");
            BufferedReader BR = new BufferedReader(f);
            String Contenido = BR.readLine();
            while(Contenido != null){
                System.out.println("\t"+Contenido);
                Contenido = BR.readLine();
            }
                BR.close();
        }
        catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
   
}