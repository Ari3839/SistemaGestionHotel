package hotel;

import hotel.Servicios.Habitaciones.Habitacion;
import hotel.Usuarios.Administradores.Administrador;
import hotel.Usuarios.Usuario;
import hotel.Usuarios.WrongDataException;
import java.util.LinkedList;
import java.util.TreeMap;

/**
 * En esta clase se encuentran metodos para poder inicalizar distintos archivos
 * @author Andrade, Ambrosio, Lázaro y Mendoza
 */
public class Herramientas {
    
    /**
     * Este metodo crea y guarda instancias de tipo administrador para inicializar el archivo
     * @param users Representa la lista ligada en la que se van a guardar los objetos de tipo administrador
     */
    public void iniUsuarios(LinkedList<Usuario> users){
        Usuario user1=null,user2=null,user3=null,user4=null,user5=null,user6=null;
        try{
            user1=new Administrador("admin1","contrasenia","Ariadna","Lazaro","Administrador");
            user2=new Administrador("admin2","contrasenia2","Lirio","Mendoza","Administrador");
            user3=new Administrador("admin3","contrasenia3","Cesar","Ambrosio","Administrador");
            user4=new Administrador("admin4","contrasenia4","Lesly","Andrade","Administrador");
            user5=new Administrador("admin5","contrasenia5","Edgar","Tista","Administrador");
        }catch(WrongDataException e){
            System.out.println("Error al generar un usuario");
        }
        users.clear();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        
        Files.guardarUsuarios(users);
    }
    
    /**
     * Crea y guarda instancias de tipo Habitacion para poder inicializar los distintos archivos de los que se van a leer 
     */
    public void iniHabitaciones(){
        TreeMap<Integer,Habitacion> individual =new TreeMap<>();
        individual.put(106,new Habitacion(1,"Individual","individual","completo",350));
        individual.put(107,new Habitacion(1,"Individual","individual","completo",350));
        individual.put(108,new Habitacion(1,"Individual","individual","completo",350));
        individual.put(109,new Habitacion(1,"Individual","individual","completo",350));
        individual.put(110,new Habitacion(1,"Individual","individual","completo",350)); 
        Files.guardaHabitaciones(individual,"Individual");
        
        TreeMap<Integer,Habitacion> doble =new TreeMap<>();
        doble.put(220, new Habitacion(2,"Doble"," individual","completo",650));
        doble.put(221, new Habitacion(2,"Doble"," individual","completo",650));
        doble.put(222, new Habitacion(2,"Doble"," individual","completo",650));
        doble.put(223, new Habitacion(2,"Doble"," individual","completo",650));
        doble.put(224, new Habitacion(2,"Doble"," individual","completo",650));
        Files.guardaHabitaciones(doble, "Doble");
        
        TreeMap<Integer,Habitacion> pareja =new TreeMap<>(); 
        pareja.put(355, new Habitacion(2,"Doble para pareja","matrimonial","completo",500));
        pareja.put(356, new Habitacion(2,"Doble para pareja","matrimonial","completo",500));
        pareja.put(357, new Habitacion(2,"Doble para pareja","matrimonial","completo",500));
        pareja.put(358, new Habitacion(2,"Doble para pareja","matrimonial","completo",500));
        pareja.put(359, new Habitacion(2,"Doble para pareja","matrimonial","completo",500));
        Files.guardaHabitaciones(pareja,"Doble para pareja");
        
        TreeMap<Integer,Habitacion> suite =new TreeMap<>();
        suite.put(410,new Habitacion(4,"Suite","matrimonial","completo",1000));
        suite.put(411,new Habitacion(4,"Suite","matrimonial","completo",1000));
        suite.put(412,new Habitacion(4,"Suite","matrimonial","completo",1000));
        suite.put(413,new Habitacion(4,"Suite","matrimonial","completo",1000));
        suite.put(414,new Habitacion(4,"Suite","matrimonial","completo",1000));
        Files.guardaHabitaciones(suite,"Suite");
        
        TreeMap<Integer,Habitacion> mastersuite =new TreeMap<>();
        mastersuite.put(547,new Habitacion(6,"Master-Suite","matrimonial","completo",1350));
        mastersuite.put(548,new Habitacion(6,"Master-Suite","matrimonial","completo",1350));
        mastersuite.put(549,new Habitacion(6,"Master-Suite","matrimonial","completo",1350));
        mastersuite.put(550,new Habitacion(6,"Master-Suite","matrimonial","completo",1350));
        mastersuite.put(551,new Habitacion(6,"Master-Suite","matrimonial","completo",1350));
        Files.guardaHabitaciones(mastersuite,"Master-Suite");
    }
}
