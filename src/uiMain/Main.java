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
                       if ((clienteActual.getFactura().verificarFactura(clienteActual.getFactura()) == false) || (clienteActual.getFactura().getPagosAtrasados() > 0)) {

                        //SE OFRECE LA POSIBILIDAD DE CANCELAR LA DEUDA
                        System.out.println("\nTienes " + clienteActual.getFactura().getPagosAtrasados()+ " pagos atrasados, para continuar debes saldar la deuda."+ "\nTu factura actualmente está por un valor de $"
                        + clienteActual.getFactura().getPrecio() + ", elige una opción:"+ "\n1. Pagar la totalidad de la deuda." + "\n2. Salir.");
                        
                        boolean Vcontrol10 = false;
                        while (Vcontrol10 == false) {
                          opc = sc.nextInt();
                          if (opc == 1) {

                            Vcontrol10 = true;
                            boolean Vcontrol11=false;
                            while (Vcontrol11==false){
                              System.out.println("Digite por favor la cantidad total a pagar: ");
                              if (clienteActual.getFactura().accionesPagos(2) != null) { //CUARTO METODO DE LA FUNCIONALIDAD QUE COMPRUEBA QUE SE HAYA CANCELADO TODO
                                
                                Vcontrol11=true;

                                //SE REALIZAN LOS CAMBIOS
                                clienteActual.getModem().getServidorAsociado().getRouters().remove(clienteActual.getModem());
                                clienteActual.getProveedor().getClientes().remove(clienteActual);
                            
                                clienteActual.setPlan((ArrayList<Integer>) (mejoraIdeal.get(4)));
                              
                                Servidor servidorNuevo = (Servidor) (mejoraIdeal.get(1));
              
                                ProveedorInternet proveedorNuevo = servidorNuevo.getProveedor();

                                clienteActual.setProveedor(proveedorNuevo);
                                clienteActual.getModem().setServidorAsociado(servidorNuevo);
                                clienteActual.getModem().setUp(clienteActual.getPlan().get(0));
                                clienteActual.getModem().setDown(clienteActual.getPlan().get(1));
                                clienteActual.getModem().setAntenaAsociada(Antena.antenasSede(clienteActual.getModem().getSede(), clienteActual.getProveedor().getNombre()));
                                clienteActual.getModem().setIntensidadFlujo((int) (mejoraIdeal.get(3)));
                        
                                servidorNuevo.getRouters().add(clienteActual.getModem());
                                clienteActual.getProveedor().getClientes().add(clienteActual);
    
                                clienteActual.getFactura().setPrecio(clienteActual.getPlan().get(2));
                                clienteActual.getFactura().setMesActivacion(Mes.MAYO);
                        
                                System.out.println("Tu plan ha sido actualizado con éxito.");
                                Vcontrol1=false;

                              }else{
                                System.out.println("Valor incorrecto.");
                                Vcontrol11 = false;
                              }
                            }

                          } else if (opc == 2) { //DECIDE NO CANCELAR
                            System.out.print("Ha sido un placer servirte :D");
                            Vcontrol10 = true;
                            Vcontrol1=false;
                             // elige opcion salir

                          } else {
                            System.out.println("Ingresa una opción válida.");
                          }
                        }

                      } else if (clienteActual.getFactura().getPagosAtrasados() == 0) { //EL CLIENTE NO TIENE PAGOS ATRASADOS

                        //SE REALIZAN LOS CAMBIOS
                        System.out.print("Tu plan ha sido configurado con éxito.");
                        clienteActual.getModem().getServidorAsociado().getRouters().remove(clienteActual.getModem());
                        clienteActual.getProveedor().getClientes().remove(clienteActual);

                        clienteActual.setPlan((ArrayList<Integer>) (mejoraIdeal.get(4)));
                        clienteActual.setProveedor((ProveedorInternet) (mejoraIdeal.get(2)));
                        Servidor servidorNuevo = (Servidor) (mejoraIdeal.get(1));
                        clienteActual.getModem().setServidorAsociado(servidorNuevo);
                        clienteActual.getModem().setUp(clienteActual.getPlan().get(0));
                        clienteActual.getModem().setDown(clienteActual.getPlan().get(1));
                        clienteActual.getModem().setAntenaAsociada(Antena.antenasSede(clienteActual.getModem().getSede(), clienteActual.getProveedor().getNombre()));
                        clienteActual.getModem().setIntensidadFlujo((int) (mejoraIdeal.get(3)));
                        
                        servidorNuevo.getRouters().add(clienteActual.getModem());
                        clienteActual.getProveedor().getClientes().add(clienteActual);
                        clienteActual.getFactura().setPrecio(clienteActual.getPlan().get(2));
                        clienteActual.getFactura().setMesActivacion(Mes.MAYO);
                    
                        Vcontrol1=false;
                      }
                    } else if (opc == 2) { //DECIDE NO REALIZAR EL CAMBIO
                      Vcontrol9 = true;
                      System.out.println("Ha sido un gusto servirte :)");
                      Vcontrol1=false;
                      break;
                    } else {
                      System.out.println("Ingresa una opción válida.");

                    }
                  }

                }
              } else { //NO SE RECONOCE EL CLIENTE
                System.out.println("Datos incorrectos, introdúcelos de nuevo.");
                System.out.println("Ingresa su Id: ");
                ID = sc.nextLong();
                System.out.println("Ingresa su nombre: ");
                sc.nextLine();
                nombre = sc.nextLine().toLowerCase();
                System.out.println("Ingresa el nombre de tu proveedor: ");
                proveedor1 = sc.nextLine().toLowerCase();
              }

            } else { //EL PROVEEDOR NO EXISTE
                System.out.println("Datos incorrectos, introdúcelos de nuevo.");
                System.out.println("Ingresa su Id: ");
                ID = sc.nextLong();
                System.out.println("Ingresa su nombre: ");
                sc.nextLine();
                nombre = sc.nextLine().toLowerCase();
                System.out.println("Ingresa el nombre de tu proveedor: ");
                proveedor1 = sc.nextLine().toLowerCase();
            }
          }

          break;

        case 4: // FUNCIONALIDAD TEST

            //SE SOLICITAN LOS DATOS
            System.out.println("Ingresa tu Id: ");
            ID = sc.nextLong();
            System.out.println("Ingresa tu nombre: ");
            sc.nextLine();
            nombre = sc.nextLine().toLowerCase();
            System.out.println("Ingresa el nombre de tu proveedor: ");
            String proveedor2 = sc.nextLine().toLowerCase();

            //SE OBTIENE EL PROVEEDOR
            boolean Vcontrol5 = false;
            while (Vcontrol5 == false) {

                proveedorActual = null;
                for (ProveedorInternet proveedor3 : ProveedorInternet.getProveedoresTotales()) {
                  if (proveedor3.getNombre().equals(proveedor2)) {
                    proveedorActual = proveedor3;
                  }
                }

              if(proveedorActual!=null){
                if (proveedorActual.verificarCliente(nombre, ID) != null) { //SE VERIFICA QUE EL CLIENTE EXISTA--PRIMER METODO DE LA FUNCIONALIDAD
                  Vcontrol5 = true;
              
                  Cliente clienteActual = proveedorActual.verificarCliente(nombre, ID); //SE INTANCIA EL CLIENTE
                  String resultado = clienteActual.getModem().test(clienteActual); // SE REALIZA EL TEST Y SE INSTANCIA EL RESULTADO--SEGUNDO METODO DE LA FUNCIONALIDAD
                  ArrayList<Antena> antenasSede = Antena.antenasSede(clienteActual.getModem().getSede()); // SE OBTIENEN LAS ANTENAS DE UNA SEDE-- TERCER METODO DE LA FUNCIONALIDAD

                  //SE PROCEDE DE ACUERDO AL RESULTADO DEL TEST
                  if (resultado.contains("saturada")) {
                      System.out.println(resultado);
                      System.out.println("Te recomendamos remover dispositivos");
                      System.out.println("Dispositvos:\n");
                      ArrayList<Dispositivo> dispositivosClientes = clienteActual.getModem().verificarDispositivos(Dispositivo.getDispositivosTotales(), clienteActual.getModem());
                      dispositivosClientes.stream().forEach(System.out::println);
                      System.out.print("Indica el número del dispositivo que deseas remover: ");
                      opc=sc.nextInt();

                      if(opc<=dispositivosClientes.size()){
                        System.out.println(Dispositivo.desconectarDispositivo(clienteActual, dispositivosClientes.get(opc-1)));
                        Vcontrol1=false;
                      }else{
                        System.out.print("Opcion incorrecta.");
                        Vcontrol1=false;
                      }

                  }else if (resultado.contains("incompatible")) {
                    System.out.print("El test ha detectado problemas con tu red." + "\n");
                    System.out.println(resultado);
                    System.out.print("Lo anterior quiere decir que la generación de tu router es incompatible con la antena a la que estás conectado." +"\n");
                    System.out.println("Esta antena es compatible y accesible: ");
                    System.out.println(clienteActual.getModem().getAntenaAsociada().rastrearGeneracionCompatible(antenasSede, clienteActual.getModem())); //SE OBTIENE LA ANTENA RECOMENDADA-CUARTO METODO DE LA FUNCIONALIDAD

                    System.out.println("Deseas cambiar a la nueva antena:" + "\n1.Si" + "\n2.No");

                    boolean Vcontrol11 = false;
                    while (Vcontrol11==false) {
                        opc=sc.nextInt();
                        if (opc==1) { //DECIDE HACER EL CAMBIO
                            Vcontrol11=true;

                            //SE APLICA LIGADURA DINAMICA
                            Cobertura coberturaActual = clienteActual.getModem().getAntenaAsociada();
                            Antena antenaNueva = coberturaActual.rastrearGeneracionCompatible(antenasSede, clienteActual.getModem());  //SE OBTIENE LA ANTENA RECOMENDADA-CUARTO METODO DE LA FUNCIONALIDAD
                            if (antenaNueva.getProveedor().getNombre().equals(clienteActual.getProveedor().getNombre())) { //SI LA ANTENA NUEVA ES DEL MISMO PROVEEDOR QUE TIENE EL CLIENTE
                                  clienteActual.getModem().setAntenaAsociada(antenaNueva);
                                  System.out.print("Tu proceso ha finalizado con éxito.");
                                  Vcontrol1=false;
                            }else{ //SI LA ANTENA NUEVA ES DE UN PROVEEDOR DISTINTO AL DEL CLIENTE
                                  int costo = 100000;
                                  System.out.println("Esta antena pertenece a otro proveedor, por tanto el roaming tiene un costo de " + costo);
                                  System.out.print("Ingresa el total a pagar: ");
                                  boolean Vcontrol12 = false;
                                  int pagoCliente;
                                  while (Vcontrol12==false) {
                                      pagoCliente=sc.nextInt();
                                      if (pagoCliente>=costo) {
                                          Vcontrol12 = true;
                                          clienteActual.getModem().setAntenaAsociada(antenaNueva);
                                          System.out.print("Te han quedado " + (pagoCliente - costo) + " pesos."+ "\nTu proceso ha finalizado con éxito.");
                                          Vcontrol1=false;
                                      }else {
                                          System.out.print("Saldo insuficiente, por favor ingresa de nuevo la cantidad requerida: ");
                                      }
                                  }
                             }
                         }else if (opc==2) { //NO DECIDE HACER EL CAMBIO
                              Vcontrol11=true;
                              System.out.print("Ha sido un placer servirte :D");
                              Vcontrol1=false;

                         }else{
                              System.out.println("Ingrese una opción válida.");
                         }

                     }

                  }else if(resultado.contains("Inaccesible")){
                      System.out.print("El test ha detectado problemas con tu red." + "\n");
                      System.out.println(resultado);
                      System.out.print("Lo anterior quiere decir que te encuentras fuera de la zona de cobertura de la antena que tienes asociada." + "\n");
                      System.out.println("Esta antena es compatible y accesible: ");
                      System.out.println(clienteActual.getModem().getAntenaAsociada().rastrearGeneracionCompatible(antenasSede, clienteActual.getModem())); //SE OBTIENE LA ANTENA RECOMENDADA-CUARTO METODO DE LA FUNCIONALIDAD
                      System.out.println("Deseas cambiar a la nueva antena:" + "\n1.Si" + "\n2.No");

                      boolean Vcontrol13 = false;
                      while (Vcontrol13==false) {
                          opc=sc.nextInt();
                          if (opc==1) {
                              Vcontrol13 = true;

                              //SE APLICA LIGADURA DINAMICA
                              Cobertura coberturaActual = clienteActual.getModem().getAntenaAsociada();
                              Antena antenaNueva = coberturaActual.rastrearGeneracionCompatible(antenasSede, clienteActual.getModem()); //SE OBTIENE LA ANTENA RECOMENDADA-CUARTO METODO DE LA FUNCIONALIDAD
                              if (antenaNueva.getProveedor().getNombre().equals(clienteActual.getProveedor().getNombre())) { //SI LA ANTENA NUEVA ES DEL MISMO PROVEEDOR QUE TIENE EL CLIENTE
                                  clienteActual.getModem().setAntenaAsociada(antenaNueva);
                                  System.out.print("Tu proceso ha finalizado con éxito.");
                                  Vcontrol1=false;
                              }else{ //SI LA ANTENA NUEVA ES DE UN PROVEEDOR DISTINTO AL DEL CLIENTE
                                  int costo = 100000;
                                  System.out.println("Esta antena pertenece a otro proveedor, por tanto el roaming tiene un costo de " + costo);
                                  System.out.print("Ingresa el total a pagar: ");
                                  boolean Vcontrol14 = false;
                                  while (Vcontrol14==false) {
                                      int pagoCliente = sc.nextInt();
                                      if (pagoCliente>=costo) {
                                          Vcontrol14 = true;
                                          clienteActual.getModem().setAntenaAsociada(antenaNueva);
                                          System.out.print("Te han quedado " + (pagoCliente - costo) + " pesos."+ "\nTu proceso ha finalizado con éxito.");
                                          Vcontrol1=false;
                                      }else{
                                          System.out.print("Saldo insuficiente, por favor ingresa de nuevo la cantidad requerida: ");
                                      }
                                  }
                              }

                          }else if(opc==2){ //NO DECIDE HACER EL CAMBIO
                              Vcontrol13=true;
                              System.out.print("Ha sido un placer servirte :D");
                              Vcontrol1=false;
                              
                          }else{
                              System.out.println("Ingresa una opcion valida.");
                          }
                      }

                  }else if(resultado.contains("Ping")){ //EN CASO DE QUE NO SE DETECTEN FALLAS EN EL TEST
                    System.out.print("El test realizado indica que tu red no se encuentra saturada y la antena a la que estás conectado es la ideal entre todas las alternativas posibles." + "\n");
                    System.out.print(resultado + "\n" + "Tu proceso ha finalizado con éxito.");
                    Vcontrol1=false;

                  }

              }else{ //NO RECONOCE EL CLIENTE
                    System.out.println("Datos incorrectos, introdúcelos de nuevo.");
                    System.out.println("Ingresa su Id: ");
                    ID = sc.nextLong();
                    System.out.println("Ingresa su nombre: ");
                    sc.nextLine();
                    nombre = sc.nextLine().toLowerCase();
                    System.out.println("Ingresa el nombre de su proveedor: ");
                    proveedor2 = sc.nextLine().toLowerCase();
              }   

            }else{ //NO EXISTE EL PROVEEDOR
              System.out.println("Datos incorrectos, introdúcelos de nuevo.");
              System.out.println("Ingresa su Id: ");
              ID = sc.nextLong();
              System.out.println("Ingresa su nombre: ");
              sc.nextLine();
              nombre = sc.nextLine().toLowerCase();
              System.out.println("Ingresa el nombre de su proveedor: ");
              proveedor2 = sc.nextLine().toLowerCase();

            }
            
          } 

            break;

        case 5: // FUNCIONALIDAD REPORTE 

            //SE PIDE EL NOMBRE DEL PROVEEDOR
            System.out.println("Ingresa el nombre de tu compañía: ");
            sc.nextLine();
            nombre = sc.nextLine().toLowerCase();

            boolean Vcontrol7 = false;
            while (Vcontrol7 == false) {
              if (Servidor.adminServidor().verificarAdmin(ProveedorInternet.getProveedoresTotales(), nombre).size() != 0) {//SE VERIFICA QUE EL PROVEEDOR EXISTA Y CUENTE CON SERVIDORES Y SE OBTIENEN DICHOS SERVIDORES
                                                                                         
                Vcontrol7 = true;

                ArrayList<Servidor> servidoresProveedor = Servidor.adminServidor().verificarAdmin(ProveedorInternet.getProveedoresTotales(), nombre); //PRIMER METODO DE LA FUNCIONALIDAD
                System.out.println("La compañía de Internet " + nombre + " tiene servidores en las siguientes sedes: ");

                //SE MUESTRA POR CONSOLA LAS SEDES EN LAS QUE SE ENCUENTRA EL PROVEEDOR
                int contador = 1;
                for (Servidor servidor : servidoresProveedor) {
                  System.out.println(contador + ". " + servidor.getSede());

                  if(servidoresProveedor.size() != contador){
                    contador++;
                  }

                }

              //SE PIDE QUE INDIQUE LA SEDE Y SE VALIDA SI ES CORRECTA
                boolean Vcontrol8 = false;
                while (Vcontrol8 == false) {
                  System.out.println("Ingresa la sede que deseas revisar (Indica el número de la opción): ");
                  opc = sc.nextInt();
                  if ((opc <= contador) && (opc >= 1)) {
                    Vcontrol8 = true;

                    Servidor servidorLocalidad = servidoresProveedor.get(opc - 1); //SE OBTIENE EL SERVIDOR DE LA SEDE ELEGIDA

                    //SE IMPLEMENTA TRES VECES EL SEGUNDO METODO DE LA FUNCIONALIDAD--SE SEPARAN LOS CLIENTES DEL PROVEEDOR DE ACUERDO CON SU PLAN
                    ArrayList<Cliente> clientesBasic = Cliente.buscarCliente1(servidorLocalidad.getSede(), "BASIC",
                        servidorLocalidad.getProveedor());
                    ArrayList<Cliente> clientesStandard = Cliente.buscarCliente1(servidorLocalidad.getSede(),
                        "STANDARD", servidorLocalidad.getProveedor());
                    ArrayList<Cliente> clientesPremium = Cliente.buscarCliente1(servidorLocalidad.getSede(), "PREMIUM",
                        servidorLocalidad.getProveedor());

                    //SE IMPLEMENTA EL TERCER METODO DE LA FUNCIONALIDAD

                    // SE CALCULA LAS INTENSIDADES REALES DE LOS CLIENTES, PERO ESTO ES CLASIFICADO POR PLAN
                    ArrayList<Integer> intensidadBasic = Router.adminRouter().intensidadFlujoClientes(clientesBasic,servidorLocalidad, true);
                    ArrayList<Integer> intensidadStandard = Router.adminRouter().intensidadFlujoClientes(clientesStandard, servidorLocalidad, true);
                    ArrayList<Integer> intensidadPremium = Router.adminRouter().intensidadFlujoClientes(clientesPremium,servidorLocalidad, true);

                    // SE CALCULA LAS INTENSIDADES QUE DEBERIAN LLEGARLE A LOS CLIENTES, PERO ESTO ES CLASIFICADO POR PLAN
                    ArrayList<Integer> intensidadB = Router.adminRouter().intensidadFlujoClientes(clientesBasic,servidorLocalidad, false);
                    ArrayList<Integer> intensidadS = Router.adminRouter().intensidadFlujoClientes(clientesStandard,servidorLocalidad, false);
                    ArrayList<Integer> intensidadP = Router.adminRouter().intensidadFlujoClientes(clientesPremium,servidorLocalidad, false);

                    
                    // SE REALIZAN LAS COMPARACIONES-SE OBTIENEN LOS PROMEDIOS DE DICHAS INTENSIDADES

                    //PROMEDIO INTENSIDADES REALES
                    int PromedioBasic = (intensidadBasic.stream().mapToInt(Integer::intValue).sum()) / intensidadBasic.size();
                    int PromedioStandard = (intensidadStandard.stream().mapToInt(Integer::intValue).sum()) /intensidadStandard.size();
                    int PromedioPremium = (intensidadPremium.stream().mapToInt(Integer::intValue).sum()) / intensidadPremium.size();

                    //PROMEDIO INTENSIDADES ADECUADAS
                    int PromedioB = (intensidadB.stream().mapToInt(Integer::intValue).sum()) / intensidadB.size();
                    int PromedioS = (intensidadS.stream().mapToInt(Integer::intValue).sum()) / intensidadS.size();
                    int PromedioP = (intensidadP.stream().mapToInt(Integer::intValue).sum()) / intensidadP.size();

                    if ((PromedioBasic < PromedioB) || (PromedioStandard < PromedioS) || (PromedioPremium < PromedioP)) { //SE COMPRUEBA SI ALGUN PROMEDIO DE LAS INTENSIDADES REALES ES MENOR A LAS ADECUADAS

                      //SE REALIZA EL REPORTE
                      System.out.print("De acuerdo con el análisis realizado al servidor de la sede " + servidorLocalidad.getSede()+ " se hallaron errores en el servidor, a continuación se presentan las recomendaciones y el reporte.");

                      //SE CALCULAN LAS DISTANCIAS PROMEDIOS A LAS QUE DEBERIA ENCONTRARSE EL SERVIDOR DE SUS CLIENTES
                      //DISTANCIAS OPTIMAS POR PLAN
                      ArrayList<Integer> dOptBasic = servidorLocalidad.distanciasOptimas(clientesBasic, intensidadB);
                      ArrayList<Integer> dOptStandard = servidorLocalidad.distanciasOptimas(clientesStandard, intensidadS);
                      ArrayList<Integer> dOptPremium = servidorLocalidad.distanciasOptimas(clientesPremium,intensidadP);

                      //PROMEDIO DE LAS DISTANCIAS OPTIMAS
                      int PromedioDB = (dOptBasic.stream().mapToInt(Integer::intValue).sum()) / dOptBasic.size();
                      int PromedioDS = (dOptStandard.stream().mapToInt(Integer::intValue).sum()) / dOptStandard.size();
                      int PromedioDP = (dOptPremium.stream().mapToInt(Integer::intValue).sum()) / dOptPremium.size();

                      //SE PRESENTA EL REPORTE
                      System.out.println("\nREPORTE" + "\nIntensidades:"
                          + "\nSe encontraron diferencias entre las intensidades netas y las que se deberían registrar. Para ello, se clasificaron las intensidades por planes y de esta manera identificar el plan más afectado."
                          + "\nPLAN BASIC" + "\nIntensidad de Flujo Promedio Neta: " + PromedioBasic
                          + "\nIntensidad de Flujo Promedio Adecuado: " + PromedioB + "\nPLAN STANDARD"
                          + "\nIntensidad de Flujo Promedio Neta: " + PromedioStandard
                          + "\nIntensidad de Flujo Promedio Adecuada: " + PromedioS + "\nPLAN PREMIUM"
                          + "\nIntensidad de Flujo Promedio Neta: " + PromedioPremium
                          + "\nIntensidad de Flujo Promedio Adecuada: " + PromedioP
                          + "\nLas intensidades dependen en gran medida de las distancias entre el router de los clientes y el servidor, por ello se recomienda cambiar la ubicación de este último. A continuación, se presentan las distancias promedios recomendadas a las cuales debe estar el servidor para que proporcione la intensidad de flujo adecuada en cada plan."
                          + "\nDISTANCIAS ÓPTIMAS" + "\nDistancia Promedio-Plan Basic: " + PromedioDB + " metros."
                          + "\nDistancia Promedio-Plan Standard: " + PromedioDS + " metros." + "\nDistancia Promedio-Plan Premium: "
                          + PromedioDP + " metros."
                          + "\nFinalmente, las intensidades de flujo reales están afectadas por el porcentaje de eficiencia del servidor, esto indica que el servidor está consumiendo una cantidad significativa del flujo de red inicial que le proporciona el proveedor. Por ende, se recomienda cambiarlo o en su defecto, implementar la solución de las distancias, expuesta anteriormente.");

                      if (servidorLocalidad.getRouters().size() == servidorLocalidad.getINDICE_SATURACION()) { //SI EL SERVIDOR ESTÁ SATURADO, SE INDICA POR CONSOLA
                        servidorLocalidad.setSaturado(true);
                        System.out.print("ALERTA: El servidor ya se encuentra saturado, es decir, ya tiene la cantidad de routers límite conectados. Se recomienda ampliar su capacidad.");
                        Vcontrol1=false;
                        break;
                      }else{
                        Vcontrol1=false;
                      }

                      break;

                    } else { //NO SE ENCONTRARON ANOMALIAS
                      System.out.print("El servidor funciona correctamente y proporciona las intensidades de flujo adecuadas.");
                      Vcontrol1=false;
                      break;
                    }

                  } else {
                    System.out.print("Ingresa una opción válida." + "\n");
                  }
                }

              } else { //NO RECONOCE AL ADMIN
                System.out.println("Datos incorrectos, introdúcelos de nuevo");
                System.out.println("Ingresa el nombre de tu compañía: ");
                nombre = sc.nextLine().toLowerCase();
              }
            }

            break;

          case 6: //OPCION SALIR DEL PROGRAMA--SE REALIZA EL PROCESO DE SERIALIZACION
            System.out.println("Ha sido un gusto servirte :D");
            Serializador.serializar();
            break;
        }
      } else {
        System.out.println("Ingresa una opcion válida");
      }
    }
  }
}
