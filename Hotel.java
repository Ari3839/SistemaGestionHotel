package hotel;

import hotel.Acciones.MAdmin;
import hotel.Usuarios.Usuario;
import hotel.Usuarios.Clientes.Cliente;
import hotel.Acciones.MClientes;
import hotel.Acciones.MSuper;
import hotel.Acciones.Reservacion;
import hotel.Usuarios.WrongDataException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Esta clase contiene el metodo principal y otros metodos necesarios para poder inicar el programa
 * @author Andrade, Ambrosio, Lázaro y Mendoza
 */
public class Hotel {
    
    /**
     * Metodo principal del proyecto desde aqui se puede iniciar sesion o crear un usuario para poder usar el programa 
     * @param args 
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LinkedList<Usuario> users = new LinkedList<>();
        Hashtable<String,Reservacion> reservaciones=new Hashtable<>();
        Herramientas h=new Herramientas();
        
        int op=0,a=10;
        boolean next=false,verificar=false;
        System.out.println("\t \u2606\u2606\u2606 Bienvenidos a  L'hôtel Quatre Saisons \u2606\u2606\u2606");
        do{
            System.out.println("\nEscoge una opción");
            System.out.println("1. Log in");
            System.out.println("2. Sign up");
            System.out.println("3. Salir");
            try{
                op=Integer.parseInt(sc.nextLine());
            }catch(NumberFormatException e){
                System.out.println("Ingresa un número, por favor");
            }
            switch(op){
                case 1:
                    LogIn(users);
                    break;
                case 2:
                    System.out.println("\nPara crear una cuenta con nosotros deberás ingresar información personal");
                    next=false;
                    verificar=false;
                    while(next==false||verificar==false){
                        try{
                            System.out.println("\n¿Estás seguro de querer crear una cuenta?");
                            System.out.println("1.Sí\n2.No");
                            a=Integer.parseInt(sc.nextLine());
                            next=true;
                            verificar=(a==1)?true:((a==2)?true:false);
                        }catch(NumberFormatException e){
                            System.out.println("Ingresa un número, por favor");
                        }
                    }
                    if(a==1){
                        SignUp(users);
                    }else{
                        System.out.println("\nRegresando...");
                    }
                    break;
                case 3:
                    System.out.println("\nHasta luego, vuelva pronto");
                    Files.guardarRecaudado();
                    break;
                default:
                    System.out.println("\nElige otra opción");
                    break;
            }
        }while(op!=3);
    }
    
    /**
     * Este metodo lee los usuarios existentes en los distintos archivos y sirve como control de metodos para poder crear un usuario
     * @param users Representa la lista ligada en la que se van a guardar los usuarios leidos
     */
    private static void SignUp(LinkedList<Usuario> users){
        users.clear();
        Files.leerClientes(users);
        Files.leerAdmins(users);
        
        Usuario nuevo = pedirDatos(users);
        
        if(nuevo!=null){
            MClientes.Alta((Cliente)nuevo);
            users.add(nuevo);
            System.out.println("\nUsuario registrado correctamente");
        }
        Files.guardarUsuarios(users);
    }
    
    /**
     * Pide los datos necesario para crear un nuevo usuario y se asegura de que estos sean ingresados correctamente, la instancia creada la agrega a la lista ligada de usuarios
     * @param users Representa la lista ligada en la que se guardan los usuarios
     * @return regresa la instancia de tipo Cliente que fue creada
     */
    public static Usuario pedirDatos(LinkedList<Usuario> users){
        Scanner sc=new Scanner(System.in);
        Usuario nuevo=new Cliente();
        boolean validar=false;
        
        while(validar==false){
            System.out.println("\nIngresa tu usuario:");
            String user=sc.nextLine();
            try{
                for(Usuario usuario:users){
                    while(usuario.getUser().equals(user)){
                        System.out.println("\nNombre de usuario no disponible");
                        System.out.println("\nIngresa otro usuario: ");
                        user=sc.nextLine();
                    }
                }
                nuevo.setUser(user);
                validar=true;
            }catch(WrongDataException e){
                System.out.println("\nIntenta de nuevo");
            }
        };
        
        validar=false;
        while(validar==false){
            System.out.println("Ingresa una contraseña (minimo 6 caracteres) ");
            String pasword=sc.nextLine();
            try{
                nuevo.setPassword(pasword);
                validar=true;
            }catch(WrongDataException e){
                System.out.println("\nIntenta de nuevo");
            }
        };
        
        validar=false;
        while(validar==false){
            System.out.println("Ingresa tu nombre: ");
            String nombre=sc.nextLine();
            try{
                nuevo.setName(nombre);
                validar=true;
            }catch(WrongDataException e){
                System.out.println("\nIntenta de nuevo");
            }
        };
        
        validar=false;
        while(validar==false){
            System.out.println("Ingresa tu apellido: ");
            String apellido=sc.nextLine();
            try{
                nuevo.setLastName(apellido);
               
                validar=true;
            }catch(WrongDataException e){
                System.out.println("\nIntenta de nuevo");
            }
        };
        
        nuevo.setTipo("Cliente");
        
        return nuevo;
    }
   
    /**
     * Este metodo pide un usuario y contraseña para poder iniciar sesion, si el usuario no existe imprime un mensaje, si el usuario existe y es el super usuario, manda a llamar el metodo de clase menuSuper, en caso contrario manda a llmar al metodo tipoUsuario
     * @param users Representa la lista en la que se va a busacr el usuario que se ingreso
     */
    public static void LogIn(LinkedList<Usuario> users){
        users.clear();
        Files.leerClientes(users);
        Files.leerAdmins(users);
       
        Scanner sc=new Scanner(System.in);
        boolean encontrado=false;
        int posUser=0;
        
        System.out.println("\nIngresa tu usuario");
        String usuario=sc.nextLine();
        System.out.println("Ingresa tu contraseña");
        String contra=sc.nextLine();
        for(int i=0;i<users.size();i++){
            if(users.get(i).getUser().equals(usuario) && users.get(i).getPassword().equals(contra)){
                encontrado=true;
                posUser=i;
                break;
            }    
        }
        if(encontrado){
            tipoUsuario(users.get(posUser));
            Files.guardarUsuarios(users);
        } else if(usuario.equals("poo04alu")&&contra.equals("04040404")){
            MSuper.getMSuper().menuSuper();
        }else{
            System.out.println("\nError, usuario no registrado o datos incorrectos");
        }
    }
    
     /**
      * Dependiendo del tipo de usuario, manda a llamar al metodo menu que corresponda 
      * @param usuario Representa el usuario del que se va a comprobar el tipo
      */
    public static void tipoUsuario(Usuario usuario){
        switch (usuario.getTipo()) {
            case "Administrador":
                MAdmin mAdmin=new MAdmin();
                mAdmin.menuAdmin();
                break;
            case "Cliente":
                MClientes.menuCliente((Cliente)usuario);
                break;
        }
    }
    
}
