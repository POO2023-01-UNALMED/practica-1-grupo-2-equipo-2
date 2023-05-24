package uiMain;

import java.util.*;

import baseDatos.Deserializador;
import baseDatos.Serializador;

import java.awt.geom.*;
import gestorAplicacion.enrutadorHFC.Antena;
import gestorAplicacion.enrutadorHFC.Cobertura;
import gestorAplicacion.enrutadorHFC.Dispositivo;
import gestorAplicacion.enrutadorHFC.Router;
import gestorAplicacion.enrutadorHFC.Servidor;
import gestorAplicacion.host.Cliente;
import gestorAplicacion.host.ProveedorInternet;
import gestorAplicacion.servicio.Factura;
import gestorAplicacion.servicio.Plano;
import gestorAplicacion.servicio.Mes;

public class Main {

  static {

   Deserializador.deserializar(); 
   ProveedorInternet.proveedores(); //SE AÑADEN LOS PROVEEDORES ADECUADOS AL ARREGLO PROVEEDORESTOTALES DE LA CLASE PROVEEDORINTERNET
   Antena.antenas(); //SE REASIGNAN LOS PROVEEDORES ASOCIADOS A LAS ANTENAS

  }

  public static void main(String[] args) {

    ProveedorInternet proveedorActual = null;
    Scanner sc = new Scanner(System.in);

    boolean Vcontrol1 = false;

    while (Vcontrol1 == false) {

      System.out.println("\n\tMenú Inicial" + "\n\n1. Registro y Adquisición de Plan."+ "\n2. Visualizar los dispositivos conectados." + "\n3. Mejora tu plan." + "\n4. Test." + "\n5. Reporte de fallas en el servidor." + "\n6. Salir." + "\n");
      System.out.print("Digita por favor el número de la opcion que deseas: ");
      int opc = sc.nextInt();
    
      if (opc == 1 || opc == 2 || opc == 3 || opc == 4 || opc == 5 || opc == 6) { //SE VERIFICA QUE LA OPCION ELEGIDA SEA VÁLIDA

        Vcontrol1 = true;

        switch (opc) {

          case 1: // FUNCIONALIDAD ADQUISICIÓN DE UN PLAN

            //SE SOLICITAN LOS DATOS AL USUARIO
            System.out.println("Adquisición de un plan" + "\nIngresa tu nombre: ");
            sc.nextLine();
            String nombre = sc.nextLine().toLowerCase();

            System.out.println("Ingresa tu número de cédula: ");
            long ID = sc.nextLong();

            System.out.println("Ingresa el nombre de tu sede: ");
            sc.nextLine();
            String sede = sc.nextLine().toUpperCase();


            boolean Vcontrol15 = false;
            while(Vcontrol15==false){
              if((sede.equals("A") )|| (sede.equals("B") )||  (sede.equals("C")) || (sede.equals("D"))){ //SE VERIFICA QUE LA SEDE INGRESADA EXISTA

                Vcontrol15=true;

                //SE BUSCAN LOS PROVEEDORES DE LA SEDE ESPECIFICADA
                ArrayList<ProveedorInternet> proveedorDispo = ProveedorInternet.proveedorSede(sede); //PRIMER METODO DE LA FUNCIONALIDAD

                //SE MUESTRAN POR PANTALLA LOS PROVEEDORES DISPONIBLES
                System.out.print("Los proveedores disponibles en tu sede son: " + "\n");
                int indice = 1;
                for (int i = 0; i < proveedorDispo.size(); i++) {
                  System.out.print(indice + ". " + proveedorDispo.get(i).getNombre());
                  System.out.print("\n");

                  if(proveedorDispo.size()-1!=i){
                    indice++;
                  }
                }

                boolean valPlan = false;
                while (valPlan == false) {

                  System.out.print("Ingresa la opción que deseas elegir: ");
                  opc = sc.nextInt();
              
                  if ((opc >= 1) && (opc <= indice)) { //SE VERIFICA QUE LA OPCION ESCOGIDA SEA CORRECTA Y SE ASIGNA AL APUNTADOR PROVEEDORACTUAL EL PROVEEDOR ESCOGIDO
                    
                    proveedorActual = proveedorDispo.get(opc-1);
                    valPlan = true;
                  } else {
                    System.out.println("Por favor ingresa una opción válida.");
                  }
                }


                //SE CREA EL CLIENTE
                Cliente clienteActual = proveedorActual.crearClienteNuevo(nombre, ID); //SEGUNDO METODO DE LA FUNCIONALIDAD

                //SE COMPRUEBA QUE HAYAN CUPOS DISPONIBLES CON EL PROVEEDOR, EN CASO CONTRARIO SALE POR CONSOLA EL MENSAJE RESPECTIVO INDICANDO ESTO
                if (proveedorActual.getClientes().size() < proveedorActual.getCLIENTES_MAX()) {

                  System.out.println("Los planes disponibles del proveedor son: ");
                  ArrayList<String> planesDisponibles = proveedorActual.planesDisponibles(clienteActual); //TERCER METODO DE LA FUNCIONALIDAD

                  //SE MUESTRAN POR CONSOLA LOS PLANES DISPONIBLES CON SUS CARACTERÍSTICAS
                  for (String planes : planesDisponibles) {
                    if (planes.equals("BASIC")) {
                      System.out.println("BASIC" + "\nEl plan basic ofrece: " + "\nMegas de subida: "
                          + proveedorActual.getPlanes().getBASIC().get(0) + "\nMegas de bajada: "
                          + proveedorActual.getPlanes().getBASIC().get(1) + "\nEl precio del plan: "
                          + proveedorActual.getPlanes().getBASIC().get(2));
                    } else if (planes.equals("STANDARD")) {
                      System.out.println("STANDARD" + "\nEl plan standard ofrece: " + "\nMegas de subida: "
                          + proveedorActual.getPlanes().getSTANDARD().get(0) + "\nMegas de bajada: "
                          + proveedorActual.getPlanes().getSTANDARD().get(1) + "\nEl precio del plan: "
                          + proveedorActual.getPlanes().getSTANDARD().get(2));
                    } else if (planes.equals("PREMIUM")) {
                      System.out.println("PREMIUM" + "\nEl plan premium ofrece: " + "\nMegas de subida: "
                          + proveedorActual.getPlanes().getPREMIUM().get(0) + "\nMegas de bajada: "
                          + proveedorActual.getPlanes().getPREMIUM().get(1) + "\nEl precio del plan: "
                          + proveedorActual.getPlanes().getPREMIUM().get(2));
                    }
                  }

                  //SE SOLICITA EL NOMBRE DEL PLAN QUE SE DESEA AQUIRIR Y SE VERIFICA QUE SEA UN NOMBRE CORRECTO
                  String planEscogido = "";
                  boolean valido = false;
                  int contadorRepe = 0;
                  while (valido == false) {
                    System.out.println("Ingresa el nombre del plan que deseas adquirir: ");
                    if (contadorRepe == 0) {
                      sc.nextLine();
                    }
                    String planEscogido1 = sc.nextLine().toUpperCase();
                    if ((planEscogido1.equals("BASIC") || planEscogido1.equals("STANDARD")
                        || planEscogido1.equals("PREMIUM"))) {
                      if (proveedorActual.planesDisponibles(clienteActual).contains(planEscogido1)) {
                        valido = true;
                        planEscogido=planEscogido1;
                      }
                    } else {
                      System.out.println("Por favor ingresa un nombre correcto.");
                      contadorRepe++;
                    }
                  }

                
                  //LIMITES DE ACUERDO CON LA SEDE DEL CLIENTE

                  int LimInfX=0;
                  int LimSupX=0;
                  int LimInfY=0;
                  int LimSupY=0;

                  if(sede.equals("A")){ LimInfX=0; LimSupX=40 ; LimInfY=0; LimSupY=40; }
                  else if(sede.equals("B")){ LimInfX=0; LimSupX=40 ; LimInfY=60; LimSupY=100; }
                  else if(sede.equals("C")){ LimInfX=50; LimSupX=80 ; LimInfY=0; LimSupY=30;}
                  else if(sede.equals("D")){ LimInfX=60; LimSupX=100 ; LimInfY=40; LimSupY=80;}


                  System.out.print("Digita las coordenadas de tu ubicación, estas tienen un límite de acuerdo con tu sede." + "\nCoordenadas en el eje X:" + "\nLímite Inferior: " + LimInfX +" \nLímite Superior: " + LimSupX + "\nCoordenadas en el eje Y:" + "\nLímite Inferior: " + LimInfY + "\nLímite Superior: "  + LimSupY + "\n");
                  
                  //SE PIDEN LAS COORDENADAS, VALIDANDO QUE SE ENCUENTREN EN EL RANGO ESTABLECIDO
                  boolean ValSede = false;
                  int coordenadaX=0;
                  int coordenadaY=0;
                  while(ValSede==false){

                    System.out.print("Ingresa la coordenada X: ");
                    coordenadaX = sc.nextInt();
                    System.out.print("Ingresa la coordenada Y: ");
                    coordenadaY = sc.nextInt();

                    if((coordenadaX>=LimInfX) && (coordenadaX<=LimSupX)){
                      if((coordenadaY>=LimInfY) && (coordenadaY<=LimSupY)){
                        ValSede=true;
                      }else{
                        System.out.print("Coordenadas fuera del rango permitido." + "\n");
                      }
                    }else{
                      System.out.print("Coordenadas fuera del rango permitido." + "\n");
                    }
                  }

                  //SE PROCEDE A CONFIGURAR EL PLAN AL CLIENTE
                  Factura facturaCliente = clienteActual.configurarPlan(planEscogido, clienteActual, sede,coordenadaX, coordenadaY); //CUARTO METODO DE LA FUNCIONALIDAD
                
                  if (facturaCliente instanceof Factura) { 
                    System.out.println("Tu plan ha sido configurado correctamente. A continuación puedes visualizar tu factura.");
                    facturaCliente = facturaCliente.generarFactura(clienteActual, proveedorActual); //QUINTO METODO DE LA FUNCIONALIDAD
                    System.out.print(
                        facturaCliente + "\nRealiza el pago de tu membresía a continuación (Ingrese el valor a pagar): ");

                    //SE SOLICITA EL PAGO DE AL FACTURA
                    boolean pagoVal = false;
                    while (pagoVal == false) {
                      if (clienteActual.getFactura().accionesPagos(2) != null) {
                        System.out.print("Tu adquisición del plan ha finalizado con éxito."); //EL PROCESO TERMINA CON EXITO
                        pagoVal = true;
                        Vcontrol1=false;
                      } else {
                        System.out.print("Por favor ingresa la totalidad del precio requerido: ");
                      }
                    }

                  } else {
                    System.out.print("Lo sentimos, no se logró tramitar el plan. Intenta en otro momento.");
                    Vcontrol1=false;
                    break;
                  }
                } else {
                  System.out.println("Lo sentimos, este proveedor ya no cuenta con cupos disponibles.Intenta en otro momento.");
                  Vcontrol1=false;
                  break;
                }
              }else{
                System.out.print("Sede incorrecta, por favor ingresa de nuevo el nombre de tu sede: ");
                sede = sc.nextLine().toUpperCase();
              }
            }

            break;

        case 2: //FUNCIONALIDAD VISUALIZAR DISPOSITIVOS 

            //SE SOLICITA LOS DATOS

            System.out.println("Ingresa tu Id: ");
            ID = sc.nextLong();
            
            System.out.println("Ingresa el nombre de tu proveedor: ");
            sc.nextLine();
            String proveedor = sc.nextLine().toLowerCase();

            proveedorActual = null;

            //SE HALLA EL PROVEEDOR INDICADO
            boolean Vcontrol3 = false;
            while (Vcontrol3 == false) {
              for (ProveedorInternet proveedor1 : ProveedorInternet.getProveedoresTotales()) {
                if (proveedor1.getNombre().equals(proveedor)) {
                  proveedorActual = proveedor1;
                }
              }

              if (proveedorActual != null) {

                //SE INSTANCIA EL CLIENTE
                Cliente clienteActual = proveedorActual.verificarCliente(ID); //PRIMER METODO DE LA FUNCIONALIDAD
                  
                  if (clienteActual != null) { 
                    System.out.println("\n¡Bienvenido!" + "\nAquí podrás visualizar los dispositivos conectados a tu router.");

                    Vcontrol3 = true;                                                                  
                    if (clienteActual.getFactura().verificarFactura(clienteActual.getFactura()) == false) { //SE COMPRUEBA SI TIENE PAGOS ATRASADOS

                        //SE INDICAN LAS OPCIONES PARA SALDAR LA DEUDA
                        System.out.println("\nTienes "+clienteActual.getFactura().getPagosAtrasados()+" pagos atrasados, para acceder a esta función debes tener 2 o menos pagos atrasados."+"\nTu factura actualmente está por un valor de $"+clienteActual.getFactura().getPrecio()+", recuerda que el valor mensual de tu plan es de $"+clienteActual.getPlan().get(2)+"\nTenemos las siguientes opciones de pago: "
                        + "\n1. Abonar a la deuda." + "\n2. Pagar la totalidad de la deuda." + "\n3. Regresar al menú inicial.");

                      boolean Vcontrol2 = false;
                      while (Vcontrol2 == false) {

                        System.out.print("\n" + "Ingresa el número de la opción que deseas: ");
                        opc = sc.nextInt();

                        if (opc == 1 || opc == 2) { //SE PROCEDE A PEDIR EL DINERO Y HACER LAS COMPROBACIONES QUE SE REQUIEREN DE ACUERDO A LA OPCION ELEGIDA
                          Vcontrol2 = true;

                          boolean Vcontrol4 = false;
                          while (Vcontrol4 == false) {
                            if (opc == 1) {
                              System.out.println("Digita por favor la cantidad a abonar: ");
                            } else if (opc == 2) {
                              System.out.println("Digita por favor la cantidad total a pagar: ");
                            }

                            Router routerCliente = clienteActual.getFactura().accionesPagos(opc);

                            if (routerCliente != null) { //SEGUNDO METODO DE LA FUNCIONALIDAD
                              Vcontrol4 = true;
                              
                              boolean Vcontrol7 = false;
                              while (Vcontrol7 == false) {

                                System.out.println("Digita por favor la opción que deseas: " + "\n1. Visualizar dispositivos de 3G." + "\n2. Visualizar dispositivos de 4G." + "\n3. Visualizar dispositivos de 5G."
                                 + "\n4. Visualizar todos los dispositivos conectados."+ "\n5. Regresar al menú inicial.");
                                opc = sc.nextInt();

                                if (opc == 1 || opc == 2 || opc == 3 || opc == 4) { //SE PROCEDE A MOSTRAR POR CONSOLA LOS DISPOSITIVOS DE ACUERDO A LA OPCION ELEGIDA
                                  Vcontrol7 = true;
                                  
                                  ArrayList<Dispositivo> lista = routerCliente.verificarDispositivos(Dispositivo.getDispositivosTotales(), routerCliente); //TERCER METODO DE LA FUNCIONALIDAD
                                  
                                  if (opc == 1) {
                                    System.out.println("Dispositivos de 3G conectados: ");
                                    int contador=0;
                                    for (Dispositivo dispositivo : lista) {
                                      if (dispositivo.getGeneracion().equals("3G")) {
                                        System.out.println("Id: "+(int)(Dispositivo.getDispositivosTotales().indexOf(dispositivo)+1));
                                        System.out.println(dispositivo);
                                        contador+=1;
                                        Vcontrol1 = false;
                                      }
                                    }
                                    if (contador==0){
                                      System.out.println("No tienes dispositivos conectados con esta caracteristica.");  
                                      Vcontrol7 = false;
                                      Vcontrol1 = false;
            
                                    }
                                  } else if (opc == 2) {
                                    int contador=0;
                                    System.out.println("Dispositivos de 4G conectados: ");
                                    for (Dispositivo dispositivo : lista) {
                                      if (dispositivo.getGeneracion().equals("4G")) {
                                        System.out.println("Id: "+(int)(Dispositivo.getDispositivosTotales().indexOf(dispositivo)+1));
                                        System.out.println(dispositivo);
                                        contador+=1;
                                        Vcontrol1 = false;
                                      }
                                    }

                                    if (contador==0){
                                      System.out.println("No tienes dispositivos conectados con esta caracteristica.");  
                                      Vcontrol7 = false;
                                      Vcontrol1 = false;
                                      
                                    }
                                  
                                  } else if (opc == 3) {
                                    int contador=0;
                                    System.out.println("Dispositivos de 5G conectados: ");
                                    for (Dispositivo dispositivo : lista) {
                                      if (dispositivo.getGeneracion().equals("5G")) {
                                       System.out.println("Id: "+(int)(Dispositivo.getDispositivosTotales().indexOf(dispositivo)+1));
                                        System.out.println(dispositivo);
                                        contador+=1;
                                        Vcontrol1 = false;
                                      }
                                    }
                                    if (contador==0){
                                      System.out.println("No tienes dispositivos conectados con esta caracteristica.");  
                                      Vcontrol7 = false;
                                      Vcontrol1 = false;
                                    }

                                  } else {
                                    System.out.println("Lista de dispositivos conectados a tu Router:");
                                    for (Dispositivo dispositivo : lista) {
                                      System.out.println("Id: "+(int)(Dispositivo.getDispositivosTotales().indexOf(dispositivo)+1));
                                      System.out.println(dispositivo);
                                      Vcontrol1 = false;
                                      
                                    }
                                  }
                                  
                                } else if (opc == 5) { //OPCION REGRESAR AL MENU
                                  Vcontrol7 = true;
                                  Vcontrol1 = false;
                                  break;

                                } else {
                                  System.out.println("Por favor ingresa una opción válida");
                                }
                              }

                            } else if (clienteActual.getFactura().getPagosAtrasados() > 2 && opc == 1) { //EN CASO QUE DESPUES DE USAR LAS OPCIONES DE PAGO, EL CLIENTE SIGUE DEBIENDO MÁS DE 2 PAGOS
                              System.out.println("La cantidad ingresada no es suficiente para acceder a esta función.");
                              Vcontrol1 = false;
                              break;

                            } else if (clienteActual.getFactura().getPagosAtrasados() > 2 && opc == 2) { //EN CASO QUE DESPUES DE USAR LAS OPCIONES DE PAGO, EL CLIENTE SIGUE DEBIENDO MÁS DE 2 PAGOS
                              System.out.println("La cantidad ingresada no es suficiente para acceder a esta funcion.");
                              Vcontrol1 = false;
                              break;
                            }
                          }

                        } else if (opc == 3) { //OPCION SALIR
                          Vcontrol2 = true;
                          Vcontrol1 = false;
                          break; 

                        } else {
                          System.out.println("Ingresa una opcion valida.");
                        }
                      }
                    } else { // CUANDO EL CLIENTE NO TIENE PAGOS ATRASADOS

                      boolean Vcontrol7 = false;
                      while (Vcontrol7 == false) {
                        System.out.println("Digita por favor la opción que deseas: " + "\n1. Visualizar dispositivos de 3G." + "\n2. Visualizar dispositivos de 4G." + "\n3. Visualizar dispositivos de 5G."
                        + "\n4. Visualizar todos los dispositivos conectados."+ "\n5. Regresar al menú inicial.");
                        opc = sc.nextInt();

                        if (opc == 1 || opc == 2 || opc == 3 || opc == 4) { //SE PROCEDE A MOSTRAR POR CONSOLA LOS DISPOSITIVOS DE ACUERDO A LA OPCION ELEGIDA
                            Vcontrol7 = true;
                                  
                            ArrayList<Dispositivo> lista = clienteActual.getModem().verificarDispositivos(Dispositivo.getDispositivosTotales(), clienteActual.getModem()); //TERCER METODO DE LA FUNCIONALIDAD
                                  
                            if (opc == 1) {
                              System.out.println("Dispositivos de 3G conectados: ");
                              int contador=0;
                              for (Dispositivo dispositivo : lista) {
                                if (dispositivo.getGeneracion().equals("3G")) {
                                  System.out.println("Id: "+(int)(Dispositivo.getDispositivosTotales().indexOf(dispositivo)+1));
                                  System.out.println(dispositivo);
                                  contador+=1;
                                  Vcontrol1 = false;
                                  
                                }
                              }
                              if (contador==0){
                                System.out.println("No tienes dispositivos conectados con esta caracteristica.");  
                                Vcontrol7 = false;
                                Vcontrol1 = false;
                                
                              }
                            } else if (opc == 2) {
                              int contador=0;
                              System.out.println("Dispositivos de 4G conectados: ");
                              for (Dispositivo dispositivo : lista) {
                                  if (dispositivo.getGeneracion().equals("4G")) {
                                    System.out.println("Id: "+(int)(Dispositivo.getDispositivosTotales().indexOf(dispositivo)+1));
                                    System.out.println(dispositivo);
                                    contador+=1;
                                    Vcontrol1 = false;
                                    
                                  }
                              }
                              if (contador==0){
                                System.out.println("No tienes dispositivos conectados con esta caracteristica.");  
                                Vcontrol7 = false;
                                Vcontrol1 = false; 
                              }  
                                  
                            }else if (opc == 3) {
                              int contador=0;
                              System.out.println("Dispositivos de 5G conectados: ");
                              for (Dispositivo dispositivo : lista) {
                                if (dispositivo.getGeneracion().equals("5G")) {
                                  System.out.println("Id: "+(int)(Dispositivo.getDispositivosTotales().indexOf(dispositivo)+1));
                                  System.out.println(dispositivo);
                                  contador+=1;
                                  Vcontrol1 = false;
                                }
                              }      
                              if (contador==0){
                                      System.out.println("No tienes dispositivos conectados con esta caracteristica.");  
                                      Vcontrol7 = false;
                                      Vcontrol1 = false;
                              }
                            } else {
                                System.out.println("Lista de dispositivos conectados a tu Router:");
                                  for (Dispositivo dispositivo : lista) {
                                    System.out.println("Id: "+(int)(Dispositivo.getDispositivosTotales().indexOf(dispositivo)+1));
                                    System.out.println(dispositivo);
                                    Vcontrol1 = false;
                                      
                                  }
                            }
                                  
                        } else if (opc == 5) { //REGRESO AL MENU PRINCIPAL
                            Vcontrol7 = true;
                            Vcontrol1 = false;
                            break;
                        } else {
                          System.out.println("Por favor ingresa una opción válida");
                        }
                     }
                    }
                  } else {
                    System.out.println("No tienes permitido visualizar los dispositivos conectados con este proveedor.");
                    break;
                  }
                  Vcontrol3 = false;
                  break; 

              } else {
                  System.out.println("Datos incorrectos, introdúcelos de nuevo.");
                  System.out.println("Ingrese tu Id: ");
                  ID = sc.nextLong();
                  System.out.println("Ingresa el nombre de tu proveedor: ");
                  sc.nextLine();
                  proveedor = sc.nextLine().toLowerCase();
              }
            }

            break;

          case 3: // FUNCIONALIDAD MEJORA TU PLAN 

          //SOLCITUD DE DATOS 
          System.out.println("Ingresa tu Id: ");
          ID = sc.nextLong();
          System.out.println("Ingresa tu nombre: ");
          sc.nextLine();
          nombre = sc.nextLine().toLowerCase();
          System.out.println("Ingresa el nombre de tu proveedor: ");
          String proveedor1 = sc.nextLine().toLowerCase();

          proveedorActual = null;

          //SE HALLA EL PROVEEDOR INDICADO
          boolean Vcontrol6 = false;
          while (Vcontrol6 == false) {

            for (ProveedorInternet proveedor2 : ProveedorInternet.getProveedoresTotales()) {
              if (proveedor2.getNombre().equals(proveedor1)) {
                proveedorActual = proveedor2;
              }
            }

            if (proveedorActual != null) {
              if (proveedorActual.verificarCliente(nombre, ID) != null) {

                Vcontrol6 = true;
                Cliente clienteActual = proveedorActual.verificarCliente(nombre, ID); //SE OBTIENE EL CLIENTE--PRIMER METODO DE LA FUNCIONALIDAD
                String localidad = clienteActual.getModem().getSede(); //SE OBTIENE LA SEDE DEL CLIENTE
                ArrayList<Servidor> listaServLoc = Servidor.buscarServidores(localidad); //SE OBTIENEN LOS SERVIDORES DE UNA SEDE ESPECIFICA---SEGUNDO METODO DE LA FUNCIONALIDAD
                ArrayList<Object> mejoraIdeal = clienteActual.getModem().protocoloSeleccionServidor(listaServLoc, clienteActual); //SE OBTIENE LA RECOMENDACION--TERCER METODO DE LA FUNCIONALIDAD
                
                if (mejoraIdeal == null) { //CLIENTE TIENE LA MEJOR OPCION DE PLAN
                  System.out.println("Al realizar las verificaciones encontramos que hasta el momento el plan de tu proveedor es el mejor.");
                  Vcontrol1=false;
                } else { //SE ENCONTRÓ UNA OPCION MEJOR---SE LE INFORMA AL USUARIO POR CONSOLA

                  System.out.println("Al realizar las verificaciones encontramos que este nuevo servicio puede ser mejor para ti." + "\nTe presentamos la siguiente recomendación: "+ "\nIntensidad de flujo: " + (int) (mejoraIdeal.get(3)) + "\nProveedor: "
                  + ((ProveedorInternet) (mejoraIdeal.get(2))).getNombre() + "\nPlan "+ clienteActual.getNombrePlan() + "\nMegas de subida: "+ ((ArrayList<Integer>) (mejoraIdeal.get(4))).get(0) + "\nMegas de bajada: "
                  + ((ArrayList<Integer>) (mejoraIdeal.get(4))).get(1) + "\nPrecio: "+ ((ArrayList<Integer>) (mejoraIdeal.get(4))).get(2));
                  
                  System.out.println("Tu plan actual tiene las siguientes caracteristicas: "+ "\nIntensidad de flujo: " + mejoraIdeal.get(0) + "\nProveedor: "+ clienteActual.getProveedor().getNombre() + "\nPlan " + clienteActual.getNombrePlan()
                  + "\nMegas de subida: " + clienteActual.getPlan().get(0) + "\nMegas de bajada: "+ clienteActual.getPlan().get(1) + "\nPrecio: " + clienteActual.getPlan().get(2));
                  
                  //SE PREGUNTA SI DESEA REALIZAR EL CAMBIO O NO
                  System.out.println("¿Deseas cambiar tu plan por la recomendación generada?" + "\n1. Sí" + "\n2. No");
                  boolean Vcontrol9 = false;
                  while (Vcontrol9 == false) {
                    opc = sc.nextInt();

                    if (opc == 1) { //DESEA REALIZAR EL CAMBIO
                      Vcontrol9 = true;

                      //SE COMPRUEBRA SI EL CLIENTE TIENE PAGOS ATRASADOS
