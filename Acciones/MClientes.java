package hotel.Acciones;

import hotel.Cuentas.*;
import hotel.Cuentas.Socio;
import hotel.Files;
import hotel.Servicios.Habitaciones.*;
import hotel.Usuarios.Clientes.Cliente;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeMap;
import hotel.Servicios.Servicio;
import hotel.Usuarios.WrongDataException;
import java.util.Hashtable;
import java.util.Random;
/**
 *
 * @author Andrade, Ambrosio, Lázaro y Mendoza
 */
public class MClientes {
    
    /**
     * Este metodo sirve para realizar una reservacion, pide los datos necesarios para realizarla y manda a llamar a distintos metodos para poder realizar la reservacion, por ultimo guarda la reservacion, tanto en el objeto cliente como en el archivo de reservaciones
     * @param sc Representa un objeto Scanner para poder leer del teclado  
     * @param usuario 
     */
    public static void reservar(Scanner sc, Cliente usuario){
        int numtotal=0;
        int p=0;
        String ClaveRes;
        Hashtable<String,Reservacion> reservaciones=new Hashtable<>();
        if(usuario.getReservacion() == null){
            boolean aceptado=false, omitir=true;   
            System.out.println("¿Cuántas personas se van a hospedar?");
            do{
                try{
                    p=Integer.parseInt(sc.nextLine());
                }catch(NumberFormatException e){
                    System.out.println("Ingresa un número, por favor");
                    p=0;
                }
            }while(p==0);
            Date finicial=null,ffinal=null;
            int dias=0;
            do{
                do{
                    finicial=pedirFechas(sc,finicial,"*Fecha de llegada");
                    ffinal=pedirFechas(sc,finicial,"**Fecha de partida");
                    if(finicial.after(ffinal))
                        System.out.println("\u2716 El orden de las fechas es incorrecto");
                }while(finicial.after(ffinal));
                dias=(int)((ffinal.getTime()-finicial.getTime())/86400000);
                if(dias==0)
                        System.out.println("Lo siento, debes hospedarte mínimo una noche en el hotel");
            }while(dias==0);
            Reservacion nueva=new Reservacion(p,finicial,ffinal,dias);
            ArrayList<Habitacion> habitaciones=null;
            do{
            System.out.println("\nA continuación se muestran las habitaciones que se le pueden ofrecer");
            System.out.println("* Elige el número asociado a la habitación de su preferencia");
            do{
                if(omitir==true){
                habitaciones=MClientes.habitaciones();//Se muestran las habitaciones
                omitir=false;
                }else{
                    int i=1;
                    for(Habitacion a: habitaciones){
                        System.out.println(i+". "+a.toString());
                        i++;
                    } 
                }   
                int op=Integer.parseInt(sc.nextLine());

            //Se verificara si el tipo de habitacion esta disponible
                if(op-1 < habitaciones.size()) {
                    aceptado=habitaciondisp(habitaciones,op-1,finicial,ffinal,nueva);
                    if(aceptado==true){
                        nueva.setHuesped(usuario.getName()+" "+usuario.getLastName());
                        usuario.setReservacion(nueva);
                        numtotal+=habitaciones.get(op-1).getNumpersonas();
                    }else{
                        System.out.println("Lo sentimos, ninguna habitación de la elección dada se encuentra disponible"
                                + "favor de escoger otra opción");
                        habitaciones.remove(op-1);
                        
                    }
                }else{
                    System.out.println("\u2716 No hay habitaciones con ese número");
                }
            }while(aceptado==false);
            if(p>numtotal){
                    System.out.println("La habitación ha sido guardada, solo que ha ocurrido un error");
                    System.out.println("\u2716 Hay "+ (p-numtotal)
                            + " persona(s) sin una habitación. Favor de seleccionar otra habitación");
            }
            }while(p>numtotal);
            System.out.println("\u2714 Reservación exitosa: ");
            System.out.println(usuario.getReservacion().toString());
            System.out.println("Se le proporcionará una clave la cual le permitirá "
                    + "el acceso a los servicios \nque le proporciona \u2606L'hôtel Quatre Saison\u2606");
            System.out.println("Tu clave es: ");
            ClaveRes = generarClave(usuario);
            System.out.println("  "+ClaveRes);
            System.out.println("\n\u2714Total a pagar: $"+Cobrototal(nueva));
            usuario.setClave(ClaveRes);
            reservaciones = Files.leerReservaciones();
            reservaciones.put(ClaveRes,nueva);
            Files.guardareservaciones(reservaciones);
            
        }else {
            System.out.println("\u203C Solo puede hacer una reservacion.Para cambiarla, \ncancele la reservación actual o haga check-out\n");
        }
        
    }
    
    /**
     * Este metodo genera una clave de reservacion para el usuario a partir de un patron determinado 
     * @param usuario Representa el ciente al que se le va a generar la clave de reservacion 
     * @return Regresa una cadena que representa la clave de reservaicon 
     */
    public static String generarClave(Cliente usuario){
        Random r=new Random();
        String clave=(r.nextInt(10)*13+""+usuario.getReservacion().getPersonas()+""+usuario.getLastName().charAt(0)+r.nextInt(9)+""+usuario.getName().charAt(1)+""+r.nextInt(50)*2+usuario.getUser().charAt(3));//r.nextInt(10)*23+usuario.getLastName().charAt(0)+""+r.nextInt()*7+
        return clave;
    }
    
    /**
     * Este metodo calcula el costo total de una reservacion dependiendo del tipo, numero de dias y cantidad de habitaciones que se hayan reservado 
     * @param rev Representa la reservacion de la cual se va a calcular el total 
     * @return Regresa un entero que representa el total de la reservacion
     */
    public static int Cobrototal(Reservacion rev){
        int total=0;
        Integer Clave;
        Iterator<Integer> i = rev.getHab().keySet().iterator();
                while(i.hasNext()){
                    Clave = i.next();
                    total+= rev.getHab().get(Clave).getCosto();
                }
                total*=rev.getDias();
        return total;
    }
    
    /**
     * Este metodo pide una fecha y comprueba que la fecha recibida sea ingresada de forma correcta
     * @param sc Representa un objeto Scanner para poder leer del teclado 
     * @param fecha Representa un objeto Date para recibir las fechas 
     * @param mensaje Representa una cadena que se va imprimir en pantalla para distinguir que fecha se esta pidiendo
     * @return Regresa la fecha ingresada de forma correcta
     */
    public static Date pedirFechas(Scanner sc, Date fecha,String mensaje){
        //Tiene que revisar
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        boolean rep=false;
        do{
          System.out.println(mensaje+" (expresada de la manera dd/MM/yyyy)");
          String k=sc.nextLine();
            try {
                if(Integer.parseInt(k.substring(0, 2))<31|| Integer.parseInt(k.substring(3, 5))<13){
                    fecha=dateFormat.parse(k);
                    rep=false;
                }
                else{
                    System.out.println("Error: la fecha se encuentra fuera del rango");
                    rep=true;
                }
            } catch (ParseException ex) {
                System.out.println("\u2717 Error, la fecha no ha sido proporcionada segun el formato solicitado\n");
                rep=true;
            }  
        }while(rep==true);
        return fecha;
    }
    
    /**
     * Este metodo verifica si la habitacion pedida esta disponible en el rango de fechas, si lo esta la agrega a la reservacion
     * @param habitacion Representa la lista de habitaciones  en la que s eva a busacr la que se pidio
     * @param opcion Representa el numero de opcion que s epidio de habitacion
     * @param ini Representa la fecha apartir de la cual se va a reservar la habitacion
     * @param fin Representa la fecha hastra la cual se va a reservar la habitacion
     * @param rev Representa la reservacion a la que se va a asignar la habitacion
     * @return Regresa true su la habitacion esta disponible, en caso contrario regresa false
     */
    public static boolean habitaciondisp(ArrayList<Habitacion> habitacion, int opcion,Date ini,Date fin,Reservacion rev){
        boolean disponible;
        Habitacion  elegida = habitacion.get(opcion);
        TreeMap<Integer,Habitacion> aux = new TreeMap<>();
        aux = Files.leerHabitaciones(elegida.getNombre());
        Iterator<Integer> lista = aux.keySet().iterator();
        while(lista.hasNext()){
            Integer val=lista.next();
            disponible=aux.get(val).verificafechas(ini, fin);
            if(disponible==true){
                aux.get(val).setNum(val);
                rev.setHab(val, aux.get(val));
                Files.guardaHabitaciones(aux, elegida.getNombre());
                aux.clear();
                return true;
                }
        }  
     return false;   
    }
    
    /**
     * Este metodo funciona como menu para poder realizar distintos tipos de modificaciones al objeto Cliente recibido 
     * @param usuario Representa el objeto cliente al que se le van a hacer modificaciones
     * @param sc Representa un objeto de tipo Scanner par poder leer del teclado
     */
    public static void configuracion(Cliente usuario,Scanner sc){
        String ayuda, pass;
        int op=10;
        do{
        System.out.println("¿Qué desea hacer?\n 1)Verificar datos personales\n 2)Recordar contrasenia\n"
                + " 3)Recodar clave de reservación\n 4)Cambiar contraseña \n 5)Cambiar No.Tarjeta \n "
                + "6)Cambiar tipo de cuenta \n7)Salir");//Cambiar numero de tarjeta y tipo de cuenta
        try{
            op=Integer.parseInt(sc.nextLine());
        }catch(NumberFormatException e){
            System.out.println("Ingresa un número, por favor");
        }
        switch(op){
            case 1:
                System.out.println(1+".Nombre:  "+usuario.getName());
                System.out.println(2+".Apellido:  "+usuario.getLastName());
                System.out.println(3+".*Nombre de usuario:  "+usuario.getUser());
                System.out.println(4+".Tipo de usuario "+usuario.getTipo());
                System.out.println("Proporcione el número asociado al campo que desee modificar"
                        + "\nEn caso de no realizar modificaciones, presione 5");
                int op1=10;
                try{
                    op1=Integer.parseInt(sc.nextLine());
                }catch(NumberFormatException e){
                    System.out.println("Ingresa un número, por favor");
                }
                switch(op1){
                    case 1:
                        System.out.println("Proporcione su nombre");
                        try{
                            usuario.setName(sc.nextLine());
                        }catch(WrongDataException e){}
                        System.out.println("\u2714 El nombre ha sido cambiado con éxito!");
                        break;
                    case 2:
                        System.out.println("Proporcione su apellido");
                        try{
                            usuario.setLastName(sc.nextLine());
                        }catch(WrongDataException e){}
                        System.out.println("\u2714 Su apellido ha sido modificado con exito!");
                        break;
                    case 3:
                        System.out.println("\u203C ADVERTENCIA si cambia el nombre de usuario"
                                + "habría posibilidades de olvidarlo con facilidad.Desea cambiarlo \n1)Si\n2)No");
                        int k=Integer.parseInt(sc.nextLine());//quitar nombre de usuario
                        if(k==1){
                            System.out.println("Proporcione su nuevo usuario");
                            try{
                                usuario.setUser(sc.nextLine());
                            }catch(WrongDataException e){}
                            System.out.println("\u2714 Su nombre de usuario ha sido modificado");
                        }else if(k==2)
                            System.out.println("Ha sido muy considerado, gracias por atender nuestras recomendaciones..");
                        else if(k!=1 && k!=2)
                            System.out.println("\u2717 Esa opción es invalida");
                        break;
                    case 4:
                        System.out.println("Si desea cambiar el tipo de usuario, podrian realizarse "
                                + "cargos extra(dependiendo de la nueva membresia a escoger");
                        cambiarTipoDeCuenta(usuario);
                        break;
                    default:
                        System.out.println("\u2717 No es una opción válida");
                        break;
                     
                }        
                break;
            case 2:
                System.out.println("Para verfificar su contraseña, favor de proporcionar su nombre de Usuario");
                if(usuario.getUser().equals(sc.nextLine()))
                    System.out.println("La contraseña es: "+usuario.getPassword());
                else
                    System.out.println("\u2717 Usuario incorrecto, no es posible proporcionale la contraseña");
                break;
            case 3:
                if(usuario.getClave().equals("")==true){
                    System.out.println("Aun no cuenta con una clave por que no ha realizado su reservación");
                }else{
                System.out.println("Para recuperar su clave, favor de introducir su contraseña");
                if(usuario.getPassword().equals(sc.nextLine()))
                    System.out.println("La clave es: " + usuario.getClave());
                else
                    System.out.println("\u2717 Contraseña incorrecta, no es posible proporcionarle la clave");
                }
                break;
            case 4:
                System.out.println("Favor de introducir la contraseña actual");
                String contra=sc.nextLine();
                System.out.println("Ingrese la nueva contraseña (Recordar que minimo son 6 digitos)");
                String newcontra=sc.nextLine();
                if(usuario.getPassword().equals(contra))
                    try{
                        usuario.setPassword(newcontra);
                    }catch(WrongDataException e){}
                else
                    System.out.println("La contraseña actual que ha proporcionado es incorrecta."
                            + " Favor de intentar nuevamente");
                
                break;
            case 5:
                System.out.println("Favor de ingresar su contraseña");
                pass = sc.nextLine();
                if(usuario.getPassword().equals(pass)){
                    System.out.println("Ingrese su nuevo numero de tarjeta");
                    String No = sc.nextLine();
                    try{
                        usuario.getCuenta().setTarjeta(No);
                        System.out.println("\u2714 Se ha actualizado el No. de tarjeta");
                    }catch(Exception e){
                    
                    }
                }else
                    System.out.println("\u2717 La contraseña actual que ha proporcionado es incorrecta."
                            + " Favor de intentar nuevamente");
                break;
            case 6:
                System.out.println("Favor de ingresar su contraseña");
                pass = sc.nextLine();
                if(usuario.getPassword().equals(pass)){
                    cambiarTipoDeCuenta(usuario);
                }else
                    System.out.println("\u2717 La contraseña actual que ha proporcionado es incorrecta."
                            + " Favor de intentar nuevamente");
                break;
            case 7:
                System.out.println("Saliendo...");
                break;
            default:
                System.out.println("Opción inválida");
                break;
        }
        }while(op!=7);
        
    }
    
    /**
     * Este metodo pide los datos necesarios para crear una cuenta de tipo cliente y verifica que estos seaningresados de forma correcta
     * @param c Representa la cuenta a la cual se van a asignar los datos recibidos
     * @return Regresa un objeto de tipo Cuentas a la que se le asignaron los datos
     */
    public static Cuentas datosCuenta(Cuentas c){
        Scanner sc=new Scanner(System.in);
        String Tel,No,Correo;
        boolean validar=false;
        System.out.println("\n\u203C Primero tiene que dar de alta su información\n");
        
        validar=false;
        while(validar==false){
            System.out.println("\nIngrese su correo de contacto: ");
            Correo = sc.nextLine();
            try{
                c.setCorreo(Correo);
                validar=true;
            }catch(WrongDataException e){
                System.out.println("\nIntenta de nuevo");
            }
        };
        
        validar=false;
        while(validar==false){
            System.out.println("\nIngrese su número de teléfono: ");
            Tel = sc.nextLine();
            try{
                c.setTelefono(Tel);
                validar=true;
            }catch(WrongDataException e){
                System.out.println("\nIntenta de nuevo");
            }
        };
        
        validar=false;
        while(validar==false){
            System.out.println("\nIngrese su número de tarjeta de credito a 16 digitos:  ");
            No = sc.nextLine();
            try{
                c.setTarjeta(No);
                validar=true;
            }catch(WrongDataException e){
                System.out.println("\nIntenta de nuevo");
            }
        };
        
        return c;
    }
    
    /**
     * Est emetodo cra un objeto del tipo que se indique y le asigna los valores ingresados a los atribuots de est eobjeto, para finalmente asignarla a un Cliente
     * @param usuario Representa el Cliente al que se le va a asignar la cuenta creada 
     */
    public static void Alta(Cliente usuario){
        int opc=10;
        Scanner sc=new Scanner(System.in);
        Cuentas c=null;
        do{
            System.out.println("\n\n¿Qué tipo de cuenta quiere?\n[1].Extranjera\t[2].General(Local)\t[3].Socio(Se harán cargos a su tarjeta)");
            try{
                opc=Integer.parseInt(sc.nextLine());
            }catch(NumberFormatException e){
                System.out.println("Ingresa un número, por favor");
            }
            switch(opc){
                case 1:
                    c=new Extranjero();
                    Extranjero ex=(Extranjero)datosCuenta(c);
                    String Pasaporte,Idioma,Pais;
                    boolean validar=false;
                    while(validar==false){
                        System.out.println("\nIngrese su número de pasaporte: ");
                        Pasaporte = sc.nextLine();
                        try{
                            ex.setPasaporte(Pasaporte);
                            validar=true;
                        }catch(WrongDataException e){
                            System.out.println("\nIntenta de nuevo");
                        }
                    };
                    validar=false;
                    while(validar==false){
                        System.out.println("Ingrese el idioma de origen: ");
                        Idioma = sc.nextLine();
                        try{
                            ex.setIdioma(Idioma);
                            validar=true;
                        }catch(WrongDataException e){
                            System.out.println("\nIntenta de nuevo");
                        }
                    };
                    validar=false;
                    while(validar==false){
                        System.out.println("Ingrese su país de origen: ");
                        Pais = sc.nextLine();
                        try{
                            ex.setPais(Pais);
                            validar=true;
                        }catch(WrongDataException e){
                            System.out.println("\nIntenta de nuevo");
                        }
                    };
                    usuario.setCuenta(ex);
                    System.out.println("\u2714 Su cuenta se dio de alta");
                    break;
                case 2:
                    c=new General();
                    General g=(General)datosCuenta(c);
                    usuario.setCuenta(g);
                    System.out.println("\u2714 Su cuenta se dio de alta");
                    break;
                case 3:
                    c=new Socio();
                    Socio so=(Socio)datosCuenta(c);
                    try{
                        so.setNumSocio(usuario.getUser().hashCode());
                        System.out.println("\nSu código de socio es: " + usuario.getUser().hashCode());
                    }catch(Exception e){}
                    usuario.setCuenta(so);
                    System.out.println("\u2714 Su cuenta se dio de alta");
                    break;
                default:
                    System.out.println("\u2717 Opción incorrecta");
                    opc = 4;
                    break;
            }
            if(c==null){
                System.out.println("\u2717 No se pudo dar de alta la cuenta, intenta otra vez");
                opc = 4;
            }
        }while(opc!=1&&opc!=2&&opc!=3);
    }
    
    /**
     * Este metodo imprime en pantalla los servicios de los que dispone el hotel
     */
    public static void ofrecemos(){
        System.out.println("\n \t   \u2606\u2606L'hôtel Quatre Saisons\u2606\u2606 ofrece para usted:");
        System.out.println("\u2606 Restaurante:");
        System.out.println("\tServicio de Buffet y de desayuno en ciertos paquetes");
        System.out.println("\u2606 Bar Club");
        System.out.println("\u2606 Casino");
        System.out.println("\u2606 Estancia para niños:");
        System.out.println("\tServicio desde las 10:00 hasta las 16:00 hrs");
        System.out.println("\u2606 Servicio de lavandería");
        System.out.println("\u2606 Piscina");
        System.out.println("\u2606 Wi-fi");
        System.out.println("\u2606 Televisión");
        System.out.println("\u2606 Linea telefónica");
        
        System.out.println("\u2606 Tipos de habitaciones");
        habitaciones();
        
        System.out.println("* Algunos de los servicios son exclusivos para clientes socios ");
    }
    
    /**
     * Este metodo permite cancelar una reservacion ya realizada, eliminando todo lo relacionado a ella
     * @param client Representa el Cliente que va al que se le va a cancelar la reservacion
     */
    public static void cancelar(Cliente client){
        String clave;
        int op;
        Scanner sc=new Scanner(System.in);
        if(client.getReservacion()==null){
            System.out.println("\n\u203C Debe hacer una reservación antes");
        }else if(client.getEstancia()){
            System.out.println("\n\u2717 No puedes cancelar tu reservacion si ya hiciste Check-in");
        } else{
            System.out.println("\nIngrese la clave de reservación que se le otorgó");
            clave=sc.nextLine();
            if(client.getClave().equals(clave)){
                do{
                    System.out.println("¿Está seguro de querer cancelar la reservación?");
                    System.out.println("\n1.Sí\n2.No");
                    op=Integer.parseInt(sc.nextLine());
                    switch(op){
                        case 1:
                            int TotalRes = 0;
                            TotalRes = Cobrototal(client.getReservacion());
                            eliminarReservacion(client);
                            System.out.println("\n\u2714 Se ha reembolsado un 80% del costo de su reservacion(Se cobró $"+(.2 * TotalRes)+")");
                            MSuper.sumarGanancias((.2 * TotalRes));
                            break;
                        case 2:
                            System.out.println("\nGracias por reservar con nosotros");
                            break;
                        default:
                            System.out.println("\n\u2717 Opción incorrecta");
                            break;
                    }
                }while(op!=1&&op!=2);
            }
        }
    }
    
    /**
     * Este metodo funciona como menu para poder realizar operaciones relacionadas con los servicios de los cuales dispone y puede contratar el cliente, los servcios dependeran del tipo de cuenta del cliente
     * @param sc Representa un objeto de tipo Scanner para poder leer del teclado
     * @param cliente Representa el cliente que va a ver o contratar los servicios disponibles del hotel
     */
    public static void servicios(Scanner sc, Cliente cliente){
        Cuentas c=cliente.getCuenta();
        if(!cliente.getEstancia()){
            System.out.println("Haz check-in para acceder a los servicios que te proporciona \u2606L'hôtel Quatre Saisons\u2606");
            
        }else{
            int op=10;
             do{
                System.out.println("1. Ver mis servicios gratuitos");
                System.out.println("2. Adquirir servicios extra");
                System.out.println("0. Volver");
                System.out.println("Qué deseas hacer?");
                try{
                    op=Integer.parseInt(sc.nextLine());
                }catch(NumberFormatException e){
                    System.out.println("Ingresa un número, por favor");
                }
                switch(op){
                    case 1:
                        if(c instanceof Socio)
                            mostrarServDisp((Socio)c);
                        else if(c instanceof Extranjero)
                            mostrarServDisp((Extranjero)c);
                        else if(c instanceof General)
                            mostrarServDisp((General)c);
                        break;
                    case 2:
                        if(c instanceof Socio){
                            System.out.println("¡¡¡Tienes todos los servicios!!!");
                            break;
                        }else if(c instanceof Extranjero)
                            mostrarServSinAd((Extranjero)c);
                        else if(c instanceof General)
                            mostrarServSinAd((General)c);
                         int serv=0;
                        do{
                           System.out.print("Selecciona el servicio a adquirir: ");
                           serv=Integer.parseInt(sc.nextLine());
                            System.out.println(serv);
                        }while(serv<=0&&serv<4);
                        
                        adquirirServ(sc, cliente, serv);
                        break;
                    case 0:
                        System.out.println("Regrsando...");
                        
                }
             }while(op!=0);
        }
        
    }
    
    /**
     * Este metodo imprime los servicios disponibles para un usuario con cuenta de tipo socio
     * @param socio Representa una cuenta de tipo socio
     */
    public static void mostrarServDisp(Socio socio){
        System.out.println("\n¡¡¡Tienes acceso a todos los servicios del Hotel!!!");
        System.out.println("\t\u25B6 Piscina: Entrada libre");
        System.out.println("\t\u25B6 Restaurante: Servicio de Buffet");
        System.out.println("\t\u25B6 Bar-Club: Enrada y barra, libres");
        System.out.println("\t\u25B6 Casino: Entrada libre");
        System.out.println("\t\u25B6 Estancia para niños: Desde las 10:00 hasta 16:00 hrs");
        System.out.println("\t\u25B6 Servicio de lavanderia: Un servicio gratis cada 2 días");
    }
    
    /**
     * Este metodo imprime los servicios disponibles para un usuario con cuenta de tipo extranjero
     * @param extranjero Representa una cuenta de tipo extranjero
     */
    public static void mostrarServDisp(Extranjero extranjero){
        System.out.println("\nTienes acceso gratuito a los siguientes servicios:");
        System.out.println("\t\u25B6 Piscina: Entrada libre después de medio día");
        System.out.println("\t\u25B6 Restaurante: Servicio de desayuno");
        System.out.println("\t\u25B6 Estancia para niños: Desde las 10:00 hasta 16:00 hrs");
        System.out.println("\t\u25B6 Servicio de lavanderia: Un servicio gratis cada 2 días");
    }
    
    /**
     * Este metodo imprime los servicios disponibles para un usuario con cuenta de tipo general
     * @param gen Representa una cuenta de tipo general
     */
    public static void mostrarServDisp(General gen){
        System.out.println("\nTienes acceso gratuito a los siguientes servicios:");
        System.out.println("\t\u25B6 Estancia para niños \u21D2 Desde las 10:00 hasta 16:00 hrs");
        System.out.println("\t\u25B6 Servicio de lavanderia: Un servicio gratis cada 3 días");
    }
    
    /**
     * Este metodo muestra los servicios que un cliente con cuenta general puede adquirir
     * @param gen Representa una cuenta de tipo general
     */
    public static void mostrarServSinAd(General gen){
        System.out.println("\n¡¡¡Tienes acceso a todos los servicios del Hotel!!!");
        System.out.println("\t1. Piscina \u21D2 Por $70 entrada libre un día completo");
        System.out.println("\t2. Restaurante \n\t\t\u21D2 Por $65 Servicio de Buffet");
        System.out.println("\t3. Bar-Club \u21D2 Costo de entrada: $50 ");
        System.out.println("\t4. Casino \u21D2 Costo de entrada: $60 ");
    }
    
    /**
     * Este metodo muestra los servicios que un cliente con cuenta extranjera puede adquirir
     * @param e Representa una cuenta de tipo extranjero
     */
    public static void mostrarServSinAd(Extranjero e){
        System.out.println("n¡¡¡Tienes acceso a todos los servicios del Hotel!!!");
        System.out.println("\t1. Piscina \u21D2 Adquiere el acceso libre a cualquier hora por solo $30 más");
        System.out.println("\t2. Restaurante \n\t\t\u21D2 Por $45 Servicio de Buffet");
        System.out.println("\t3. Bar-Club \u21D2 Costo de entrada: $30 ");
        System.out.println("\t4. Casino \u21D2 Costo de entrada: 60 ");
    }
    
    /**
     * Este metodo permite adquirir un servicio, agrega el servicio adqquirido a la lista de servicios y asigan el precio dependiendo del tipo de cuenta del usuario
     * @param sc Representa un objeto de tipo Scanner par poder leer del teclado
     * @param c Representa al cliente al que se le va asiganr el servicio
     * @param serv Representa la opcion elegida de servicio
     */
    public static void adquirirServ(Scanner sc, Cliente c, int serv){
        int confirmar=0;
        int costo=0;
        Servicio s=new Servicio();
        switch (serv){
            case 1:
                s.setNombre("Piscina");
                System.out.println("\nEstas seguro de que deseas adquirir el siguiente servicio?");
                System.out.println("\tPiscina \u21D2 Acceso libre por un día");
                
                if(c.getCuenta() instanceof General){
                    costo=70;
                    System.out.println("\t\tSe cobrarán $70");
                }                    
                else{
                    costo=30;
                    System.out.println("\t\tSe cobraran $30");
                }
                
                break;
            case 2:
                s.setNombre("Restaurante");
                System.out.println("\n¿Estás seguro de que deseas adquirir el siguiente servicio?");
                System.out.println("\tRestaurante \u21D2 Buffete");
                
                if(c.getCuenta() instanceof General){
                    costo= 65;
                    System.out.println("\t\tSe cobrarán $65");
                }                    
                else{
                    costo= 45;
                    System.out.println("\t\tSe cobrarán $45");
                }
                break;
            case 3:
                s.setNombre("Bar-Club");
                System.out.println("\n¿Estás seguro de que deseas adquirir el siguiente servicio?");
                System.out.println("\tBar-Club \u21D2 Acceso libre por un día");
                
                if(c.getCuenta() instanceof General){
                    costo=50;
                    System.out.println("\t\tSe cobrarán $50");
                }                    
                else{
                    costo= 30;
                    System.out.println("\t\tSe cobrarán $30");
                }
                break;
            case 4:
                s.setNombre("Bar-Club");
                System.out.println("\n¿Estás seguro de que deseas adquirir el siguiente servicio?");
                System.out.println("\tCasino \u21D2 Acceso libre por un día");
                costo= 60;
                System.out.println("\t\tSe cobrarán $60");
                break;
        }
        do{
            System.out.println("1. Si\t2.No");
            confirmar=Integer.parseInt(sc.nextLine());
                
            if(confirmar==1){
                System.out.println("\n\u2714 Servicio adquirido");
                    if(c.services.get(serv-1).getNombre()==null){
                        s.setNumAd(1);
                        c.services.add(serv-1,s);
                    }else
                        c.services.get(serv-1).setNumAd(c.services.get(serv-1).getNumAd()+1);
                    c.services.get(serv-1).setCosto(costo*c.services.get(serv-1).getNumAd());
            }else if(confirmar==2){
                System.out.println("\nCompra cancelada...");
                break;
            }                        
        }while(confirmar!=1&&confirmar!=2);
    }
    
    /**
     * Este metodo crea objetos de cada tipo de habitacion y las asigna a una ArrayList 
     * @return Regresa una ArrayList con una instancia de cada tipo de habitacion
     */
    public static ArrayList habitaciones(){
        int i=1;
        ArrayList<Habitacion> Habitaciones= new ArrayList<>();
        Habitaciones.add(new Habitacion(1,"Individual","individual (1)","medio",350));
        Habitaciones.add(new Habitacion(2,"Doble","individual (2)","completo",650));
        Habitaciones.add(new Habitacion(2,"Doble para pareja","matrimonial (1)","completo",500));
        Habitaciones.add(new Habitacion(4,"Suite","matrimonial (2)","baño completo (2)",1000));
        Habitaciones.add(new Habitacion(6,"Master-Suite","Matrimonial (3)","baño completo (3)",1350));
        for(Habitacion a: Habitaciones){
            System.out.println(i+". "+a.toString());
            i++;
        }
        return Habitaciones;
    }
    
    /**
     * Este metodo permite cambiar el tipo  de cuenta de un cliente
     * @param c Representa el cliente al que s ele va a cambiar el tipo de cuenta
     */
    public static void cambiarTipoDeCuenta(Cliente c){
        Scanner sc = new Scanner(System.in);
        if(c.getCuenta().getClass() == Socio.class){
            System.out.println("Actualmente es socio, ¿desea cancelar su suscripción?\n\t[1]Si, a una cuenta general\t[2]Si, a una cuenta extranjera\t[3]No");
            int opc=10;
            try{
                opc=Integer.parseInt(sc.nextLine());
            }catch(NumberFormatException e){
                System.out.println("Ingresa un número, por favor");
            }
            switch(opc){
                case 1:
                    try{
                        Cuentas NCG = new General(c.getCuenta().getCorreo(),c.getCuenta().getTelefono(),c.getCuenta().getTarjeta());
                        c.setCuenta(NCG);
                        System.out.println("\u2714 Se ha actualizado su tipo de cuenta, se cancelará el cargo mensual a su tarjeta de credito");
                    }catch(Exception e){
                        System.out.println("\u2717 No se pudo actualizar el tipo de cuenta");
                    }
                    break;
                case 2:
                    String Pasaporte,Idioma,Pais;
                    System.out.println("Ingrese su número de pasaporte: ");
                    Pasaporte = sc.nextLine();
                    System.out.println("Ingrese el idioma de origen: ");
                    Idioma = sc.nextLine();
                    System.out.println("Ingrese su pais de origen: ");
                    Pais = sc.nextLine();
                    try{
                        Cuentas NCE = new Extranjero(c.getCuenta().getCorreo(),c.getCuenta().getTelefono(),c.getCuenta().getTarjeta(),Pais,Pasaporte,Idioma);
                        System.out.println("\u2714 Se ha actualizado su tipo de cuenta, se cancelará el cargo mensual a su tarjeta de crédito");
                    }catch (Exception e){
                        System.out.println("\u2717 No se pudo actualizar el tipo de cuenta");
                    }
                    break;
                default:
                    System.out.println("No se ha realizado ninguna operación, volviendo al menu principal....");
                    break;
            }
            System.out.println("");
        }
        else if(c.getCuenta().getClass() == General.class||c.getCuenta().getClass() == Extranjero.class){
            System.out.println("¿Desea actualizar su cuenta a Socio?\n\t[1]Si\t[2]No");
            int opc=10;
            try{
                opc=Integer.parseInt(sc.nextLine());
            }catch(NumberFormatException e){
                System.out.println("Ingresa un número, por favor");
            }
            switch(opc){
                case 1:
                    try{
                        Cuentas NCS = new Socio(c.getCuenta().getCorreo(),c.getCuenta().getTelefono(),c.getCuenta().getTarjeta(),c.getUser().hashCode());
                        System.out.println("\nSu código de socio es: " + c.getUser().hashCode());
                        c.setCuenta(NCS);
                        System.out.println("\u2714 Se ha actualizado su tipo de cuenta, se hará un cargo mensual a su tarjeta de crédito");
                    }catch(Exception e){
                        System.out.println("\u2717 No se pudo actualizar el tipo de cuenta");
                    }
                    break;
                default:
                    System.out.println("No se ha realizado ninguna operación, volviendo al menu principal....");
                    break;
            }
        }
    }
    
    /**
     * Este metodo imprime la cuenta actual del cliente, imprimiendo uno por uno los servicios que ha adquirido y el total 
     * @param c Representa el objeto Cliente del cual se va a imprimir la cuenta
     */
    public static void verCuentaActual(Cliente c){
        int Total = 0;
        System.out.println("Servicios: ");
        if(c.sinServ()){
            System.out.println("\t\u203CAún no ha usado algún servicio");
        }else{
            for(Servicio s : c.getServices()){
                if(s.getNombre()!=null)
                    System.out.println("\t" + s);
                Total += s.getCosto();
            }
            System.out.println("\t\u2666 Su total es de: $" + Total);
        }
    }
    
    /**
     * Este metodo cambia el atributo estancia del Cliente para indicar que ya se encuentra en el hotel
     * @param c Representa el Cliente al cual se va modificar el atributo estancia
     * @param sc Representa un objeto de tipo Scanner para poder leer del teclado
     */
    public static void CheckIn(Cliente c, Scanner sc){
        String clave;
        if(c.getReservacion()!=null&&c.getEstancia()==false){
            System.out.println("Ingrese su c+odigo de reservaci+on: ");
            clave = sc.nextLine();
            if(c.getClave().equals(clave)){
                c.setEstancia(true);
                System.out.println("\nBienvenido(a) al Hotel, para nosotros es un placer tenerlo en nuestras instalaciones");
                System.out.println("Disfrute su estancia. Estamos a sus ordenes");
            }else{
                System.out.println("\n \u203C Su código de reservaci+on no coincide, vuelva a intentarlo");
            }
                
        } else if(c.getReservacion()!=null){
            System.out.println("\n \u203C El check-in solo se realiza una vezpoc cada reservación");
        } else{
            System.out.println("\n \u203C Tienes que haber reservado primero una habitacion");
        }
    }
    
    /**
     * Este metodo permite hacer Check-out, elimina la reservacion de un cliente y agrega las ganancias a la cuenta del hotel
     * @param c Representa el Cliente que va a hacer Check-out
     */
    public static void checkOut(Cliente c){
        String clave;
        int Total = 0,TotalRes = 0;
        Integer Clave;
        Scanner sc = new Scanner(System.in);
        if(c.getEstancia()==true){
            System.out.println("Ingrese su código de reservación: ");
            clave = sc.nextLine();
            if(c.getClave().equals(clave)){
                TotalRes=Cobrototal(c.getReservacion());
                c.setEstancia(false);
                eliminarReservacion(c);
                System.out.println("Su cuenta de gastos durante su estancia es de: ");
                for(Servicio s : c.getServices()){
                    if(s.getNombre()!=null)
                        System.out.println("\t" + s);
                    Total += s.getCosto();
                }
                System.out.println("\t\u2666 Su total es de: $" + (Total+TotalRes));
                MSuper.sumarGanancias(Total+TotalRes);
                System.out.println("\nSe hará el cargo a su tarjeta");
                c.getServices().clear();
                System.out.println("\nGracias por visitarnos, vuelva pronto");
            }else{
                System.out.println("\n\u2717 Su código de reservaci+on no coincide, vuelva a intentarlo");
            }
        } else{
            System.out.println("\n\u203C Para hacer Check-out primero tienes que hacer check-in");
        }
    }
    
    /**
     * 
     * Este metodo elimina todo lo relacionado con la reservacion de un cliente
     * @param client Representa el cliente del cual se va aeliminar la reservacion
     */
    static void eliminarReservacion(Cliente client){
        TreeMap<Integer,Habitacion> habitacion; 
        Hashtable<String,Reservacion> res;
        res = Files.leerReservaciones();
        Integer Clave;
        Iterator<Integer> i = client.getReservacion().getHab().keySet().iterator();
        while(i.hasNext()){
            Clave = i.next();
            Habitacion j = client.getReservacion().hab.get(Clave);
            habitacion = Files.leerHabitaciones(j.getNombre());
            habitacion.get(Clave).getLlegada().remove(client.getReservacion().getLlegada());
            habitacion.get(Clave).getLlegada().remove(client.getReservacion().getLlegada());
            Files.guardaHabitaciones(habitacion, j.getNombre());
        }
        res.remove(client.getClave());
        Files.guardareservaciones(res);
        client.setReservacion(null);
        client.setClave("");
    }
    
    /**
     * Menu de la clase MClientes desde aqui se puede acceder a las distintas opciones que un cliente puede realizar
     * @param usuario 
     */
    public static void menuCliente(Cliente usuario){
        Scanner sc=new Scanner(System.in);
        int op=20;
        do{
            System.out.println("\n\u2665 Bienvenido(a), " + usuario.getName() );
            System.out.println("Escoge una opción");
            System.out.println("0. Lo que te ofrecemos");
            System.out.println("1. Reservar");
            System.out.println("2. Check-in");
            System.out.println("3. Cancelar");
            System.out.println("4. Servicios");
            System.out.println("5. Cuenta actual");
            System.out.println("6. Check-out");
            System.out.println("7. Configuración");
            System.out.println("8. Log-out");
            try{
                op=Integer.parseInt(sc.nextLine());
            }catch(NumberFormatException e){
                System.out.println("Ingresa un número, por favor");
            }
            switch(op){
                case 0:
                    ofrecemos();
                    break;
                case 1:
                    reservar(sc,usuario);
                    break;
                case 2:
                    CheckIn(usuario,sc);
                    break;
                case 3:
                    cancelar(usuario);
                    break;
                case 4:
                    servicios(sc,usuario);
                    break;
                case 5:
                    verCuentaActual(usuario);
                    break;
                case 6:
                    checkOut(usuario);
                    break;
                case 7:
                    configuracion(usuario,sc);
                    break;
                case 8:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("\u2717 Opción incorrecta");
                    break;
            }
        }while(op!=8);
    }
}
