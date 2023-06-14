package hotel.Acciones;

import hotel.Cuentas.Extranjero;
import hotel.Cuentas.Socio;
import hotel.Files;
import hotel.Herramientas;
import hotel.Servicios.Habitaciones.Habitacion;
import hotel.Usuarios.Administradores.Administrador;
import hotel.Usuarios.Clientes.Cliente;
import hotel.Usuarios.Usuario;
import hotel.Usuarios.WrongDataException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Esta clase contiene el menu y todos los metodos necesarios para realizar acciones como SuperUsuario
 * @author Andrade, Ambrosio, Lázaro y Mendoza
 */
public class MSuper implements Operaciones{
    private static MSuper instancia;
    protected static double ganancias;
    
    /**
     * Constructor vacio de la Clase
     */
    private MSuper(){
    }
    
    /**
     * Funcion para crear una instancia de tipo MSuper y asegurarse de que solo exista una en todo el programa
     * @return Regresa la instancia creada
     */
    public static MSuper getMSuper(){
        if(instancia==null){
            instancia=new MSuper();
        }
        return instancia;
    }
    
    /**
     * Menu de la clase MSuper desde aqui se puede acceder a las distintas opciones que el Super Usuario puede realizar
     */
    public void menuSuper(){
        Scanner sc=new Scanner(System.in);
        int op=10;
        System.out.println("\nBienvenido(a), Usuario Principal \u265a \nElige una opcion");
        do{
            System.out.println("\n1. Ver Usuarios registrados");
            System.out.println("2. Ver clientes registrados");
            System.out.println("3. Ver reservaciones");
            System.out.println("4. Ver habitaciones **(y disponibilidad por fecha)");
            System.out.println("5. Modificar precios de las habitaciones");
            System.out.println("6. Agregar administradores");
            System.out.println("7. Eliminar administradores");
            System.out.println("8. Reiniciar la información de los usuarios");
            System.out.println("9. Cantidad de dinero recaudada");
            System.out.println("10. Log-out");
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
                    agregar();
                    break;
                case 7:
                    eliminar();
                    break;
                case 8:
                    reiniciar();
                    break;
                case 9:
                    recaudado();
                    break;
                case 10:
                    System.out.println("\nRegresando...");
                    break;
                default:
                    System.out.println("\nIngrese otra opción");
                    break;
            }
        }while(op!=10);
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
            System.out.println("\n\u27a3 Usuarios clientes:");
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
                System.out.println("\n\u27a3 Generales:");
                for(Cliente client:clients){
                    if(!(client.getCuenta() instanceof Socio)&&!(client.getCuenta() instanceof Extranjero))
                        System.out.println(client);
                }
            }
            
            if(!socios.isEmpty()){
                System.out.println("\n\u27a3 Socios:");
                for(Cliente client:socios){
                    System.out.println(client);
                }
            }
            if(!extranjeros.isEmpty()){
                System.out.println("\n\u27a3 Extranjeros:");
                for(Cliente client:extranjeros){
                    System.out.println(client);
                }
            }
            
        }
    }
    
    /**
     * Este metodo lee las reservaciones de su archivo y las imprime en pantalla
     */
    @Override
    public  void reservaciones(){
        Hashtable<String,Reservacion> res;
        res=Files.leerReservaciones();
        if(res.isEmpty()){
            System.out.println("\n\u2716 No hay reservaciones registrados");
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
                    System.out.println("\n \u2716 Opción incorrecta");
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
        System.out.println("\u261bEl precio actual de la habitación "+habitacion+" es de $"+h.get(primero).getCosto());
        
        while(next==false||verificar==false){
            try{
                System.out.println("Ingresa un nuevo precio:");
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
                System.out.println("¿Estás seguro de querer modificar la información?");
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
    
    /**
     * Este metodo sirve para sumar a las ganacias del hotel
     * @param sum Representa la cantidad a sumar a las ganancias
     */
    public static void sumarGanancias(double sum){
        ganancias += sum;
    }
    
    /**
     * Este metodo devuelve un entero que representa las ganacias del hotel 
     * @return 
     */
    public static double getGanancias(){
        return ganancias;
    }
    
    /**
     * Este metodo sirve para crear una cuenta de tipo administrador y guardar la lista en el arhivo correspondiente
     */
    public static void agregar(){
        Scanner sc=new Scanner(System.in);
        LinkedList<Usuario> users=new LinkedList<>();
        Files.leerAdmins(users);
        Usuario nuevo=new Administrador();
        boolean validar=false;
        
        while(validar==false){
            System.out.println("\nIngrese el nuevo usuario:");
            String user=sc.nextLine();
            try{
                for(Usuario usuario:users){
                    while(usuario.getUser().equals(user)){
                        System.out.println("\nNombre de usuario no disponible");
                        System.out.println("\nIngrese otro usuario: ");
                        user=sc.nextLine();
                    }
                }
                nuevo.setUser(user);
                validar=true;
            }catch(WrongDataException e){
                System.out.println("\nIntente de nuevo");
            }
        };
        
        validar=false;
        while(validar==false){
            System.out.println("Ingrese una contraseña: ");
            String pasword=sc.nextLine();
            try{
                nuevo.setPassword(pasword);
                validar=true;
            }catch(WrongDataException e){
                System.out.println("\nIntente de nuevo");
            }
        };
        
        validar=false;
        while(validar==false){
            System.out.println("Ingrese el nombre del administrador: ");
            String nombre=sc.nextLine();
            try{
                nuevo.setName(nombre);
                validar=true;
            }catch(WrongDataException e){
                System.out.println("\nIntente de nuevo");
            }
        };
        
        validar=false;
        while(validar==false){
            System.out.println("Ingrese el apellido del usuario: ");
            String apellido=sc.nextLine();
            try{
                nuevo.setLastName(apellido);
                validar=true;
            }catch(WrongDataException e){
                System.out.println("\nIntente de nuevo");
            }
        };
        
        nuevo.setTipo("Administrador");
        users.add(nuevo);
        Files.leerClientes(users);
        Files.guardarUsuarios(users);
        System.out.println("\n\u2714Usuario agregado correctamente");
    }
    
    /**
     * Este metodo sirve para eliminar una cuenta de tipo administrador y guardar la lista en el archivo correspondiente
     */
    public static void eliminar(){
        Scanner sc=new Scanner(System.in);
        boolean next=false,verificar=false,encontrado=false;
        int op=10;
        Usuario eliminar=null;
        
        System.out.println("\nLos usuarios registrados hasta el momento son:");
        LinkedList<Usuario> users=new LinkedList<>();
        Files.leerAdmins(users);
        if(!users.isEmpty()){
            System.out.println("\nUsuarios administradores:");
            Files.imprimir(users);
        }
        System.out.println("Ingrese el usuario del administrador que desee eliminar");
        String usuario=sc.nextLine();
        for(Usuario user:users){
            if(user.getUser().equals(usuario)){
                eliminar=user;
                encontrado=true;
            }
        }
        if(encontrado){
            next=false;
            verificar=false;
            while(next==false||verificar==false){
                try{
                    System.out.println("¿Estás seguro de querer modificar la información?");
                    System.out.println("1.Sí\n2.No");
                    op=Integer.parseInt(sc.nextLine());
                    next=true;
                    verificar=(op==1)?true:((op==2)?true:false);
                }catch(NumberFormatException e){
                    System.out.println("Ingresa un número, por favor");
                }
            }
            if(op==1){
                users.remove(eliminar);
                Files.leerClientes(users);
                Files.guardarUsuarios(users);
                System.out.println("\u2714 Usuario eliminado");
            }else{
                System.out.println("Regresando...");
            }
        }else{
            System.out.println("El usuario no se encuentra");
        }
    }
    
    /**
     * Este metodo sirve para reiniciar el archivo de cuentas de usuario
     */
    public static void reiniciar(){
        Scanner sc=new Scanner(System.in);
        LinkedList<Usuario> users;
        boolean next=false;
        boolean verificar=false;
        int op=10;
        
        while(next==false||verificar==false){
            try{
                System.out.println("¿Está seguro de querer reiniciar las cuentas de los usuarios?");
                System.out.println("Esta operación es IRREVERSIBLE y se pueden perder datos importantes");
                System.out.println("1.Sí\n2.No");
                op=Integer.parseInt(sc.nextLine());
                    next=true;
                    verificar=(op==1)?true:((op==2)?true:false);
            }catch(NumberFormatException e){
                    System.out.println("Ingresa un número, por favor");
            }
        }
        if(op==1){
            users=users=new LinkedList<>();
            Herramientas h=new Herramientas();
            h.iniUsuarios(users);
            System.out.println(" \u2714 Cuentas de usuarios modificadas correctamente");
        }else{
            System.out.println("Regresando...");
        }   
    }
    
    /**
     * Este metodo lee e imprime en pantalla el registro de ganacias del hotel
     */
    public static void recaudado(){
        System.out.println("Las ganancias del hotel son :\n");
        Files.leerRecaudado();
    }

}
