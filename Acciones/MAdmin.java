package hotel.Acciones;

import hotel.Cuentas.Extranjero;
import hotel.Cuentas.Socio;
import hotel.Files;
import hotel.Servicios.Habitaciones.Habitacion;
import hotel.Usuarios.Clientes.Cliente;
import hotel.Usuarios.Usuario;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeMap;

/**
 *
 * @author Andrade, Ambrosio, Lázaro y Mendoza
 */
public class MAdmin implements Operaciones{
    
    /**
     * Menu de la clase MAdmin desde aqui se puede acceder a las distintas opciones que un administrador puede realizar
     */
    public void menuAdmin (){
        Scanner sc=new Scanner(System.in);
        int op=10;
        System.out.println("\nElige una opción");
        do{
            System.out.println("1. Ver Usuarios registrados");
            System.out.println("2. Ver clientes registrados");
            System.out.println("3. Ver reservaciones");
            System.out.println("4. Ver habitaciones **(y disponibilidad por fecha)");
            System.out.println("5. Modificar precios de las habitaciones");
            System.out.println("6. Log-out");
            try{
                op=Integer.parseInt(sc.nextLine());
            }catch(NumberFormatException e){
                System.out.println("Ingresa un número, por favor");
            }
            switch(op){
                case 1:
                    usuarios();
                    break;
                case 2:
                    clientes();
                    break;
                case 3:
                    reservaciones();
                    break;
                case 4:
                    habitaciones();
                    break;
                case 5:
                    modificar();
                    break;
                case 6:
                    System.out.println("\nRegresando...");
                    break;
                default:
                    System.out.println("\nIngrese otra opción");
                    break;
            }
        }while(op!=6);
    }
    
    /**
     * Este metodo lee de los archivos los usuarios de tipo Administrador y Cliente, y los imprime 
     */
    @Override
    public void usuarios(){
        System.out.println("\nLos usuarios registrados hasta el momento son:");
        LinkedList<Usuario> users=new LinkedList<>();
        Files.leerAdmins(users);
        if(!users.isEmpty()){
            System.out.println("\n\u27a3 Usuarios administradores:");
            Files.imprimir(users);
            users.clear();
        }
        
        Files.leerClientes(users);
        if(!users.isEmpty()){
            System.out.println("\n\u27a3Usuarios clientes:");
            Files.imprimir(users);
            users.clear();
        }
        
        if(!users.isEmpty()){
            System.out.println("\n\u27a3Usuario root:");
            Files.imprimir(users);
            users.clear();
        }
    }
    
    /**
     * Este metodo lee de los archivos los usuarios de tipo Cliente, y los imprime dependiendo de su tipo de cuenta asignada
     */
    @Override
    public void clientes(){
        LinkedList<Usuario> users=new LinkedList<>();
        LinkedList<Cliente> clients=new LinkedList<>();
        LinkedList<Cliente> socios=new LinkedList<>();
        LinkedList<Cliente> extranjeros=new LinkedList<>();
        Files.leerClientes(users);
        if(users.isEmpty()){
            System.out.println("\n \u2716 No hay clientes registrados");
        }else{
            for(Usuario user:users){
                if(user.getTipo().equals("Cliente"))
                    clients.add((Cliente)user);
            }
            for(Cliente client:clients){
                if(client.getCuenta() instanceof Socio){
                    socios.add(client);
                }
                    
            }
            for(Cliente client:clients){
                if(client.getCuenta() instanceof Extranjero)
                    extranjeros.add(client);
            }
            System.out.println("Lista de clientes:");
            if(!clients.isEmpty()){
                for(Cliente client:clients){
                    if(!(client.getCuenta() instanceof Socio)&&!(client.getCuenta() instanceof Extranjero)){
                        System.out.println("\n\u27a3 Generales:");
                        System.out.println(client);
                    }
                }
            }
            
            if(!socios.isEmpty()){
                
                for(Cliente client:socios){
                    System.out.println("\n\u27a3 Socios:");
                    System.out.println(client);
                }
            }
            if(!extranjeros.isEmpty()){
                for(Cliente client:extranjeros){
                     System.out.println("\n\u27a3 Extranjeros:");
                    System.out.println(client);
                }
            }
            
        }
    }
    
    /**
     * Este metodo lee las reservaciones de su archivo y las imprime en pantalla
     */
    @Override
    public void reservaciones(){
        Hashtable<String,Reservacion> res;
        res=Files.leerReservaciones();
        if(res.isEmpty()){
            System.out.println("\n \u2716 No hay reservaciones registradas");
        }else{
            Enumeration e = res.keys();
            while(e.hasMoreElements()){
                Object o=e.nextElement();
                System.out.println("\n\t"+res.get(o));
            }
        }
    }
    
    /**
     * Esta clase lee las habitaciones de los archivos e imprime su informacion dependiendo de su tipo
     */
    @Override
    public void habitaciones(){
        TreeMap<Integer,Habitacion> simple,doble,doble2,master,suite;
        simple=Files.leerHabitaciones("Individual");
        Iterator it = simple.keySet().iterator();
        while(it.hasNext()){
            Object h = it.next();
            System.out.println("\nNúmero: "+h+"\n"+simple.get(h));
            if(!simple.get(h).getLlegada().isEmpty()){
                System.out.println("Fechas reservadas de la habitación:");
                for(int i=0;i<simple.get(h).getLlegada().size();i++){
                    System.out.println("Llegada:"+simple.get(h).getLlegada().get(i));
                    System.out.println("Salida:"+simple.get(h).getSalida().get(i));
                }
            }
            
        }
        
        doble=Files.leerHabitaciones("Doble");
        it = doble.keySet().iterator();
        while(it.hasNext()){
            Object h = it.next();
            System.out.println("\nNúmero: "+h+"\n"+doble.get(h).toString());
            if(!doble.get(h).getLlegada().isEmpty()){
                System.out.println("Fechas reservadas de la habitación:");
                for(int i=0;i<doble.get(h).getLlegada().size();i++){
                    System.out.println("Llegada:"+doble.get(h).getLlegada().get(i));
                    System.out.println("Salida:"+doble.get(h).getSalida().get(i));
                }
            }
            
        }
        
        doble2=Files.leerHabitaciones("Doble para pareja");
        it = doble2.keySet().iterator();
        while(it.hasNext()){
            Object h = it.next();
            System.out.println("\nNúmero: "+h+"\n"+doble2.get(h));
            if(!doble2.get(h).getLlegada().isEmpty()){
                System.out.println("Fechas reservadas de la habitación:");
                for(int i=0;i<doble2.get(h).getLlegada().size();i++){
                    System.out.println("Llegada:"+doble2.get(h).getLlegada().get(i));
                    System.out.println("Salida:"+doble2.get(h).getSalida().get(i));
                }
            }
        }   
        
        suite=Files.leerHabitaciones("Suite");
        it = suite.keySet().iterator();
        while(it.hasNext()){
            Object h = it.next();
            System.out.println("\nNúmero: "+h+"\n"+suite.get(h));
            if(!suite.get(h).getLlegada().isEmpty()){
                System.out.println("Fechas reservadas de la habitación:");
                for(int i=0;i<suite.get(h).getLlegada().size();i++){
                    System.out.println("Llegada:"+suite.get(h).getLlegada().get(i));
                    System.out.println("Salida:"+suite.get(h).getSalida().get(i));
                }
            }
        }
        
        master=Files.leerHabitaciones("Master-Suite");
        it = master.keySet().iterator();
        while(it.hasNext()){
            Object h = it.next();
            System.out.println("\nNúmero: "+h+"\n"+master.get(h));
            if(!master.get(h).getLlegada().isEmpty()){
                System.out.println("Fechas reservadas de la habitación:");
                for(int i=0;i<master.get(h).getLlegada().size();i++){
                    System.out.println("Llegada:"+master.get(h).getLlegada().get(i));
                    System.out.println("Salida:"+master.get(h).getSalida().get(i));
                }
            }
        }
    }
    
    /**
     * Este metodo sirve como menu para acceder a los metodos que modifican el precio de las habitaciones
     */
    @Override
    public void modificar(){
        Scanner sc=new Scanner(System.in);
        int op=10;
        do{
            System.out.println("¿Qué tipo de habitacion quiere modificar?");
            System.out.println("1. Individual");
            System.out.println("2. Doble");
            System.out.println("3. Doble para pareja");
            System.out.println("4. Suite");
            System.out.println("5. Master suite");
            System.out.println("6. Regresar");
            try{
                op=Integer.parseInt(sc.nextLine());
            }catch(NumberFormatException e){
                System.out.println("Ingresa un número, por favor");
            }
            switch(op){
                case 1:
                    modificarH("Individual");
                    break;
                case 2:
                    modificarH("Doble");
                    break;
                case 3:
                    modificarH("Doble para pareja");
                    break;
                case 4:
                    modificarH("Suite");
                    break;
                case 5:
                    modificarH("Master-Suite");
                    break;
                case 6:
                    System.out.println("\nRegresando...");    
                    break;
                default:
                    System.out.println("\n\u2716 Opcion incorrecta");
                    break;
            }
        }while(op!=6);
    }
    
    /**
     * Este metodo sirve para modificar el precio del tipo de habitacion que haya recibido como parametro
     * @param habitacion Representa el tipo de habitacion del que se quiere cambiar el precio 
     */
    @Override
    public void modificarH(String habitacion){
        TreeMap<Integer,Habitacion> h=Files.leerHabitaciones(habitacion);
        Scanner sc=new Scanner(System.in);
        int costo=0,op=10;
        boolean next=false,verificar=false;
        
        Integer primero=h.firstKey();
        System.out.println("\u261b El precio actual de la habitación "+habitacion+" es de $"+h.get(primero).getCosto());
        
        while(next==false||verificar==false){
            try{
                System.out.println("\nIngresa un nuevo precio:");
                costo=Integer.parseInt(sc.nextLine());
                next=true;
                verificar=(costo>0)?true:false;
            }catch(NumberFormatException e){
                System.out.println("Ingresa un número, por favor");
            }
        }
        
        next=false;
        verificar=false;
        while(next==false||verificar==false){
            try{
                System.out.println("\n¿Estas seguro de querer modificar la información?");
                System.out.println("1.Sí\n2.No");
                op=Integer.parseInt(sc.nextLine());
                next=true;
                verificar=(op==1)?true:((op==2)?true:false);
            }catch(NumberFormatException e){
                System.out.println("Ingresa un número, por favor");
            }
        }
        
        if(op==1){
            Iterator it = h.keySet().iterator();
            while(it.hasNext()){
                Object o = it.next();
                h.get(o).setCosto(costo);
            }
            System.out.println("\u2714 Precios modificados");
        }else{
            System.out.println("\u2716 Precios no modificados");
        }
        
        Files.guardaHabitaciones(h,habitacion);
    }
}