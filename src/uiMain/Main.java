package uiMain;

import java.util.*;
import java.awt.geom.*;
import gestorAplicacion.enrutadorHFC.Antena;
import gestorAplicacion.enrutadorHFC.Dispositivo;
import gestorAplicacion.enrutadorHFC.Router;
import gestorAplicacion.enrutadorHFC.Servidor;
import gestorAplicacion.host.Cliente;
import gestorAplicacion.host.ProveedorInternet;
import gestorAplicacion.servicio.Factura;
import gestorAplicacion.servicio.Plano;

public class Main {

  static ArrayList<ProveedorInternet> proveedores;

  static {

    //Proveedores
    proveedores = new ArrayList<>();

    ProveedorInternet tigo = new ProveedorInternet("tigo",15,new ArrayList<>(Arrays.asList(10,30,30000)),new ArrayList<>(Arrays.asList(30,50,55000)),new ArrayList<>(Arrays.asList(80,100,87000)),5,8,2);
    ProveedorInternet claro = new ProveedorInternet("claro",10,new ArrayList<>(Arrays.asList(15,35,45000)),new ArrayList<>(Arrays.asList(25,45,76500)),new ArrayList<>(Arrays.asList(83,115,150000)),3,3,4);
    ProveedorInternet movistar = new ProveedorInternet("movistar",18,new ArrayList<>(Arrays.asList(20,30,50000)),new ArrayList<>(Arrays.asList(28,48,60000)),new ArrayList<>(Arrays.asList(70,97,95000)),8,6,4);
    ProveedorInternet wom = new ProveedorInternet("wom",25,new ArrayList<>(Arrays.asList(5,7,25000)),new ArrayList<>(Arrays.asList(23,34,46000)),new ArrayList<>(Arrays.asList(50,70,80000)),10,10,5);
  
    proveedores.add(tigo);
    proveedores.add(claro);
    proveedores.add(movistar);
    proveedores.add(wom);

    //SEDES: A-B-C-D
    //--Tigo:ABCD----Claro:AB-----Movistar:ACD----Wom:BD

    //PLANOS
    
        //PLANOS--SEDES
    
        //SEDE A
        Rectangle2D  sedeA = Plano.crearSede(40, 40, 0, 40);
        //SEDE B
        Rectangle2D  sedeB = Plano.crearSede(40, 40, 0, 100);
        //SEDE C
        Rectangle2D  sedeC = Plano.crearSede(30, 30, 50, 30);
        //SEDE D
        Rectangle2D  sedeD = Plano.crearSede(40, 40, 60, 80);
    
        //PLANOS-SERVIDORES
        //SEDE A
        Plano sTA = new Plano(5,35);
        sTA.setSede(sedeA);
        Plano sCA = new Plano(30,5);
        sCA.setSede(sedeA);
        Plano sMA = new Plano(5,15);
        sMA.setSede(sedeA);

        //SEDE B
        Plano sTB = new Plano(10,90);
        sTB.setSede(sedeB);
        Plano sCB = new Plano(10,70);
        sCB.setSede(sedeB);
        Plano sWB = new Plano(30,95);
        sWB.setSede(sedeB);

        //SEDE C
        Plano sTC = new Plano(60,15);
        sTC.setSede(sedeC);
        Plano sMC = new Plano(75,25);
        sMC.setSede(sedeC);

        //SEDE D
        Plano sTD = new Plano(80,60);
        sTD.setSede(sedeD);
        Plano sMD = new Plano(70,45);
        sMD.setSede(sedeD);
        Plano sWD = new Plano(95,50);
        sWD.setSede(sedeD);

        //PLANOS-ANTENAS

        //SEDE A
        Plano Antena1TA = new Plano(20,25);
        Antena1TA.setSede(sedeA);
        Plano Antena2TA = new Plano(35,35);
        Antena2TA.setSede(sedeA);
        Plano Antena1CA = new Plano(15,5);
        Antena1CA.setSede(sedeA);
        Plano Antena2CA = new Plano(5,25);
        Antena2CA.setSede(sedeA);
        Plano Antena1MA = new Plano(25,15);
        Antena1MA.setSede(sedeA);

        //SEDE B
        Plano Antena1TB = new Plano(30,80);
        Antena1TB.setSede(sedeB);
        Plano Antena2TB = new Plano(5,95);
        Antena2TA.setSede(sedeB);
        Plano Antena1CB = new Plano(30,65);
        Antena1CB.setSede(sedeB);
        Plano Antena2CB = new Plano(5,85);
        Antena2CB.setSede(sedeB);
        Plano Antena1WB = new Plano(15,80);
        Antena1WB.setSede(sedeB);
        Plano Antena2WB = new Plano(20,65);
        Antena2WB.setSede(sedeB);

        //SEDE C
         Plano Antena1TC = new Plano(70,5);
        Antena1TC.setSede(sedeC);
        Plano Antena1MC = new Plano(55,25);
        Antena1MC.setSede(sedeC);
        Plano Antena2MC = new Plano(55,5);
        Antena2MC.setSede(sedeC);

        //SEDE D
        Plano Antena1TD = new Plano(65,75);
        Antena1TD.setSede(sedeD);
        Plano Antena1MD = new Plano(65,60);
        Antena1MD.setSede(sedeD);
        Plano Antena2MD = new Plano(95,70);
        Antena2MD.setSede(sedeD);
        Plano Antena1WD = new Plano(85,75);
        Antena1WD.setSede(sedeD);
        Plano Antena2WD = new Plano(90,45);
        Antena2WD.setSede(sedeD);
        Plano Antena3WD = new Plano(75,55);
        Antena3WD.setSede(sedeD);
        
    
    //SERVIDORES
    
    //SEDE A
    Servidor serATigo = new Servidor("A",false,tigo, sTA, 6, 0.97);
    Servidor serAClaro = new Servidor("A",false,claro, sCA, 4, 0.78);
    Servidor serAMovistar = new Servidor("A",false,movistar, sMA,3, 0.80);

    //SEDE B
    Servidor serBTigo = new Servidor("B",false,tigo, sTB,3, 0.94);
    Servidor serBClaro = new Servidor("B",false,claro, sCB,6, 0.87);
    Servidor serBWom = new Servidor("B",false,wom, sWB,15, 0.45);

    //SEDE C
    Servidor serCTigo = new Servidor("C",false,tigo, sTC,2, 0.97);
    Servidor serCMovistar = new Servidor("C",false, movistar, sMC,10, 0.89);

    //SEDE D
    Servidor serDTigo = new Servidor("D",false,tigo, sTD,4, 0.99);
    Servidor serDMovistar = new Servidor("D",false,movistar, sMD,5, 0.97);
    Servidor serDWom = new Servidor("D",false,wom, sWD,10, 0.97);


    //ANTENAS---Falta mirar lo de generacion y radio

    //SEDE A
    
    Antena a1TA = new Antena(1, Antena1TA, "A", int generacion, int radio, tigo);
    Antena a2TA = new Antena(2, Antena2TA, "A", int generacion, int radio, tigo);
    Antena a1CA = new Antena(1, Antena1CA, "A", int generacion, int radio, claro);
    Antena a2CA = new Antena(2, Antena2CA, "A", int generacion, int radio, claro);
    Antena a1MA = new Antena(1, Antena2TA, "A", int generacion, int radio, movistar);

    //SEDE B
    Antena a1TB = new Antena(1, Antena1TB, "B", int generacion, int radio, tigo);
    Antena a2TB = new Antena(2, Antena2TB, "B", int generacion, int radio, tigo);
    Antena a1CB = new Antena(1, Antena1CB, "B", int generacion, int radio, claro);
    Antena a2CB = new Antena(2, Antena2CB, "B", int generacion, int radio, claro);
    Antena a1WB = new Antena(1, Antena1WB, "B", int generacion, int radio, wom);
    Antena a2WB = new Antena(2, Antena1WB, "B", int generacion, int radio, wom);

    //SEDE C
    Antena a1TC = new Antena(1, Antena1TC, "C", int generacion, int radio, tigo);
    Antena a1MC = new Antena(1, Antena1MC, "C", int generacion, int radio, movistar);
    Antena a2MC = new Antena(1, Antena2MC, "C", int generacion, int radio, movistar);

    //SEDE D
    Antena a1TD = new Antena(1, Antena1TD, "D", int generacion, int radio, tigo);
    Antena a1MD = new Antena(1, Antena1MD, "D", int generacion, int radio, movistar);
    Antena a2MD = new Antena(2, Antena2MD, "D", int generacion, int radio, movistar);
    Antena a1WD = new Antena(1, Antena1WB, "D", int generacion, int radio, wom);
    Antena a2WD = new Antena(2, Antena2WB, "D", int generacion, int radio, wom);
    Antena a3WD = new Antena(3, Antena3WB, "D", int generacion, int radio, wom);

    //Clientes--Facturas-Routers-Dispositivos
    
  }

  public static void main(String[] args) {

    ProveedorInternet proveedorActual = null;
    Scanner sc = new Scanner(System.in);
    System.out
        .println(
            "\tMenú Inicial" + "\n1. Registro y Adquisición de Plan." + "\n2. Visualizar los dispositivos conectados."
                + "\n3. Mejorar tu plan." + "\n4. Test." + "\n5. Reporte de fallas en el servidor." + "\n6. Salir.");
    boolean Vcontrol1 = false;

    while (Vcontrol1 == false) {
      int opc = sc.nextInt();

      if (opc == 1 || opc == 2 || opc == 3 || opc == 4 || opc == 5 || opc == 6) {

        Vcontrol1 = true;
        switch (opc) {

          case 1: // ADQUISICIÓN DE UN PLAN

            System.out.println("Adquisición de un plan" + "\nIngrese su nombre: ");
            sc.nextLine();
            String nombre = sc.nextLine();

            System.out.println("Ingrese su número de cédula: ");
            long ID = sc.nextLong();

            System.out.println("Ingrese el nombre de su sede: ");
            String sede = sc.nextLine();

            // Busca los proveedores disponibles en la sede y se los muestra por pantalla
            ArrayList<ProveedorInternet> proveedorDispo = ProveedorInternet.proveedorSede(sede);

            int indice = 1;
            for (int i = 0; i < proveedorDispo.size(); i++) {
              System.out.print(indice + ". " + proveedorDispo.get(i).getNombre());
              indice++;
            }

            // Verifica que la opcion elegida sea válida
            boolean valPlan = false;
            while (valPlan == false) {
              opc = sc.nextInt();
              if ((opc <= indice) && (opc >= 1)) {
                proveedorActual = proveedorDispo.get(indice);
                valPlan = true;
              } else {
                System.out.println("Por favor ingrese una opción válida");
              }
            }

            // Se crea el cliente
            Cliente cliente = proveedorActual.crearClienteNuevo(nombre, ID);

            // Se verifica que haya disponibilidad en el proveedor elegido, sino, sale el
            // mensaje de que no hay cupos
            if (proveedorActual.getClientes().size() < proveedorActual.getCLIENTES_MAX()) {

              System.out.println("Los planes disponibles del proveedor son: ");
              ArrayList<String> PlanesDisponibles = proveedorActual.planesDisponibles(cliente); // Primer
                                                                                                // método--Proveedor

              // Imprime los planes disponibles con sus características
              for (String planes : PlanesDisponibles) {
                if (planes.equals("BASIC")) {
                  System.out.println("BASIC" + "\nEl plan básico ofrece: " + "\nMegas de subida: "
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

              // Solicita el plan que se desea, y se deja en un ciclo mientras ingresa un
              // nombre correcto
              String planEscogido = "";
              boolean valido = false;
              int contadorRepe = 0;
              while (valido == false) {
                System.out.println("Ingrese el nombre del plan que desea adquirir: ");
                if (contadorRepe == 0) {
                  sc.nextLine();
                }
                String planEscogido1 = sc.nextLine().toUpperCase();
                if ((planEscogido1.equals("BASIC") || planEscogido1.equals("STANDARD")
                    || planEscogido1.equals("PREMIUM"))) {
                  if (proveedorActual.planesDisponibles(cliente).contains(planEscogido1)) {
                    valido = true;
                  }
                } else {
                  System.out.println("Por favor ingrese un nombre correcto.");
                  contadorRepe++;
                }
              }

              // Procede a configurar el plan/sacar la factura y permitirle pagar
              if (cliente.configurarPlan(planEscogido, cliente, sede) instanceof Factura) { // Segundo método--Cliente
                System.out
                    .println("Su plan ha sido configurado correctamente. A continuación puede visualizar su factura.");
                Factura facturaCliente = cliente.configurarPlan(planEscogido, cliente, sede);
                facturaCliente = facturaCliente.generarFactura(cliente, proveedorActual); // Tercer método
                System.out.print(
                    facturaCliente + "\nRealice el pago de su membresía a continuación (Ingrese el valor a pagar): ");

                boolean pagoVal = false;
                while (pagoVal == false) {
                  if (cliente.getFactura().accionesPagos(2) != null) {
                    System.out.print("Su adquisición del plan ha finalizado con éxito.");
                    pagoVal = true;
                  } else {
                    System.out.print("Por favor ingrese la totalidad del precio requerido: ");
                  }
                }

              } else {
                System.out.print("Lo sentimos, no se logró tramitar el plan. Intente en otro momento.");
                break;
              }
            } else {
              System.out
                  .println("Lo sentimos, este proveedor ya no cuenta con cupos disponibles.Intente en otro momento.");
              break;
            }

            break;

          case 2: // VISUALIZAR DISPOSITIVOS ////////////////////////////////////////////////
            /// REFERENCIA PROVEEDOR? MIRAR NULL DE PAGOS ATRASADOS DESPUES DE ABONAR
            System.out.println("Ingrese su Id: ");
            ID = sc.nextLong();
            System.out.println("Ingrese su nombre: ");
            sc.nextLine();
            nombre = sc.nextLine();
            System.out.println("Ingrese el nombre de su proveedor: ");
            sc.nextLine();
            String proveedor = sc.nextLine();

            for (ProveedorInternet proveedor1 : proveedores) {
              if (proveedor1.getNombre().equals(proveedor)) {
                proveedorActual = proveedor1;
              }
            }

            boolean Vcontrol3 = false;
            while (Vcontrol3 == false) {
              Cliente clienteActual = proveedorActual.verificarCliente(nombre, ID);
              if (clienteActual != null) {// ok
                System.out.println("Bienvenido" + "\nAquí podrás visualizar los dispositivos conectados a tu router.");
                Vcontrol3 = true;

                // Funcionalidad Visualizar dispositivos conectados
                if (proveedorActual.verificarCliente(clienteActual.getId()) != null) { // Primer metodo clase
                                                                                       // ProveedorInternet
                  if (clienteActual.getFactura().verificarFactura(clienteActual.getFactura()) == false) {
                    System.out.println(
                        "Es posible que tenga mas de 2 pagos atrasados, tenemos las siguientes opciones de pago:"
                            + "\n1. Abonar a la deuda" + "\n2. Pagar la totalidad de la deuda"
                            + "\n3. Regresar al menú inicial.");

                    boolean Vcontrol2 = false;
                    while (Vcontrol2 == false) {
                      opc = sc.nextInt();
                      if (opc == 1 || opc == 2) {
                        Vcontrol2 = true;

                        boolean Vcontrol4 = false;
                        while (Vcontrol4 == false) {
                          if (opc == 1) {
                            System.out.println("Digite por favor la cantidad a abonar: ");
                          } else if (opc == 2) {
                            System.out.println("Digite por favor la cantidad total a pagar: ");
                          }

                          if (clienteActual.getFactura().accionesPagos(opc) != null) { // Segundo metodo clase Factura
                            Vcontrol4 = true;
                            System.out.println(
                                "Digita por favor la opcion que deseas: " + "\n1. Visualizar dispositivos de 3G."
                                    + "\n2. Visualizar dispositivos de 4G." + "\n3. Visualizar dispositivos de 5G."
                                    + "\n4. Visualizar todos los dispositivos conectados."
                                    + "\n5. Regresar al menú inicial.");
                            boolean Vcontrol7 = false;
                            while (Vcontrol7 == false) {
                              opc = sc.nextInt();
                              if (opc == 1 || opc == 2 || opc == 3 || opc == 4) {
                                Vcontrol7 = true;
                                ArrayList<Dispositivo> lista = clienteActual.getModem().verificarDispositivos(
                                    Dispositivo.getDispositivosTotales(), clienteActual.getModem());
                                if (opc == 1) {
                                  System.out.println("Dispositivos de 3G conectados: ");
                                  for (Dispositivo dispositivo : lista) {
                                    if (dispositivo.getGeneracion().equals("3G")) {
                                      System.out.println(dispositivo);
                                    }
                                  }
                                } else if (opc == 2) {
                                  System.out.println("Dispositivos de 4G conectados: ");
                                  for (Dispositivo dispositivo : lista) {
                                    if (dispositivo.getGeneracion().equals("4G")) {
                                      System.out.println(dispositivo);
                                    }
                                  }
                                } else if (opc == 3) {
                                  System.out.println("Dispositivos de 5G conectados: ");
                                  for (Dispositivo dispositivo : lista) {
                                    if (dispositivo.getGeneracion().equals("5G")) {
                                      System.out.println(dispositivo);
                                    }
                                  }
                                } else {
                                  System.out.println("Lista de dispositivos conectados a su Router:");
                                  for (Dispositivo dispositivo : lista) {
                                    System.out.println(dispositivo);
                                  }
                                }
                              } else if (opc == 5) {
                                Vcontrol7 = true;
                                break;// Regreso menu principal
                              } else {
                                System.out.println("Por favor ingrese una opción válida");
                              }
                            } // cierre while

                          } else if (clienteActual.getFactura().getPagosAtrasados() > 2 && opc == 1) {
                            System.out.println("La cantidad ingresada no es suficiente para acceder a esta función.");
                            break;

                          } else if (clienteActual.getFactura().getPagosAtrasados() > 2 && opc == 2) {
                            System.out.println("La cantidad ingresada no es suficiente para acceder a esta funcion.");
                            break;
                          }
                        }

                      } else if (opc == 3) {
                        Vcontrol2 = true;
                        break; // elige opcion salir

                      } else {
                        System.out.println("Ingrese una opcion valida.");
                      }
                    }
                  } else { // Caso en el que no hay pagos atrasados
                    ArrayList<Dispositivo> lista = clienteActual.getModem()
                        .verificarDispositivos(Dispositivo.getDispositivosTotales(), clienteActual.getModem()); // Tercer
                                                                                                                // metodo
                                                                                                                // clase
                                                                                                                // Router
                    System.out.println("Lista de dispositivos conectados a su Router:");
                    for (Dispositivo dispositivo : lista) {
                      System.out.println(dispositivo);

                    }
                  }

                } else {
                  System.out.println("Tu tipo de plan no te permite visualizar los dispositivos conectados.");// por el
                                                                                                              // tipo de
                                                                                                              // plan
                                                                                                              // que
                                                                                                              // tiene
                  break;// sale caso 2 funcionalidad visualizar
                }
                Vcontrol3 = false;
                break; // cierra case dos del principal-Visualizar

              } else { // Cuando no reconoce el cliente

                System.out.println("Datos incorrectos, introduzcalos de nuevo");
                System.out.println("Ingrese su Id: ");
                ID = sc.nextLong();
                System.out.println("Ingrese su nombre: ");
                sc.nextLine();
                nombre = sc.nextLine();
              }
            }

            break;

          case 3: // MEJORA TU PLAN /////////////////////////////////////////////////////////////
            /// REFERENCIA PROVEEDOR?

            System.out.println("Ingrese su Id: ");
            ID = sc.nextLong();
            System.out.println("Ingrese su nombre: ");
            sc.nextLine();
            nombre = sc.nextLine();
            System.out.println("Ingrese el nombre de su proveedor: ");
            sc.nextLine();
            String proveedor1 = sc.nextLine();

            for (ProveedorInternet proveedor2 : proveedores) {
              if (proveedor2.getNombre().equals(proveedor1)) {
                proveedorActual = proveedor2;
              }
            }

            boolean Vcontrol6 = false;
            while (Vcontrol6 == false) {
              if (proveedorActual.verificarCliente(nombre, ID) != null) {// ok
                Vcontrol6 = true;
                Cliente clienteActual = proveedorActual.verificarCliente(nombre, ID); // Primer método funcionalidad
                String localidad = clienteActual.getModem().getSede();
                ArrayList<Servidor> listaServLoc = Servidor.buscarServidores(localidad); // Segundo método funcionalidad
                ArrayList<Object> mejoraIdeal = clienteActual.getModem().protocoloSeleccionServidor(listaServLoc,
                    clienteActual);// tercer método funcionalidad
                if (mejoraIdeal == null) {
                  System.out.println(
                      "Al realizar las verificaciones encontramos que hasta el momento el plan de tu proveedor es el mejor.");
                } else {
                  System.out.println(
                      "Al realizar las verificaciones encontramos que este nuevo servicio puede ser mejor para ti."
                          + "\nIntensidad de flujo: " + (int) (mejoraIdeal.get(0)) + "\nProveedor: "
                          + ((ProveedorInternet) (mejoraIdeal.get(2))).getNombre() + "\nPlan "
                          + clienteActual.getNombrePlan() + "\nMegas de subida: "
                          + ((ArrayList<Integer>) (mejoraIdeal.get(4))).get(0) + "\nMegas de bajada: "
                          + ((ArrayList<Integer>) (mejoraIdeal.get(4))).get(1) + "\nPrecio: "
                          + ((ArrayList<Integer>) (mejoraIdeal.get(4))).get(2));
                  System.out.println(
                      "Tu plan actual tiene las siguientes caracteristicas: "
                          + "\nIntensidad de flujo: " + mejoraIdeal.get(0) + "\nProveedor: "
                          + clienteActual.getProveedor().getNombre() + "\nPlan " + clienteActual.getNombrePlan()
                          + "\nMegas de subida: " + clienteActual.getPlan().get(0) + "\nMegas de bajada: "
                          + clienteActual.getPlan().get(1) + "\nPrecio: " + clienteActual.getPlan().get(2));
                  System.out.println("¿Deseas cambiar tu plan por la recomendación generada?" + "\n1. Sí" + "\n2. No");
                  boolean Vcontrol9 = false;
                  while (Vcontrol9 == false) {
                    opc = sc.nextInt();
                    if (opc == 1) {
                      Vcontrol9 = true;
                      if ((clienteActual.getFactura().verificarFactura(clienteActual.getFactura()) == false)
                          || (clienteActual.getFactura().getPagosAtrasados() > 0)) {
                        System.out.println(
                            "Es posible que tengas pagos atrasados, tenemos las siguientes opciones para ti: "
                                + "\n1. Pagar la totalidad de la deuda" + "\n2. Salir.");

                        boolean Vcontrol10 = false;
                        while (Vcontrol10 == false) {
                          opc = sc.nextInt();
                          if (opc == 1) {
                            Vcontrol10 = true;
                            System.out.println("Digite por favor la cantidad total a pagar: ");
                            if (clienteActual.getFactura().accionesPagos(2) != null) {
                              clienteActual.getModem().getServidorAsociado().getRouters()
                                  .remove(clienteActual.getModem());
                              clienteActual.setPlan((ArrayList<Integer>) (mejoraIdeal.get(4)));
                              clienteActual.setProveedor((ProveedorInternet) (mejoraIdeal.get(2)));
                              Servidor servidorNuevo = (Servidor) (mejoraIdeal.get(1));
                              clienteActual.getModem().setServidorAsociado(servidorNuevo);
                              servidorNuevo.getRouters().add(clienteActual.getModem());
                              clienteActual.getModem().setIntensidadFlujo((int) (mejoraIdeal.get(3)));

                              System.out.println("Tu plan ha sido actualizado con éxito");
                            }

                          } else if (opc == 2) {
                            Vcontrol10 = true;
                            break; // elige opcion salir

                          } else {
                            System.out.println("Ingrese una opcion valida.");
                          }
                        }

                      } else if (clienteActual.getFactura().getPagosAtrasados() == 0) { // no tiene pagos atrasados==0
                        clienteActual.setPlan((ArrayList<Integer>) (mejoraIdeal.get(4)));
                        clienteActual.setProveedor((ProveedorInternet) (mejoraIdeal.get(2)));
                        Servidor servidorNuevo = (Servidor) (mejoraIdeal.get(1));
                        servidorNuevo.getRouters().add(clienteActual.getModem());
                        clienteActual.getModem().getServidorAsociado().getRouters().remove(clienteActual.getModem());
                        System.out.println("Tu plan ha sido actualizado con éxito");
                      }
                    } else if (opc == 2) {
                      Vcontrol9 = true;
                      System.out.println("Ha sido un gusto servirte :)");
                      break;
                    } else {
                      System.out.println("Ingrese una opcion valida.");

                    }
                  }

                }
              } else { // Cuando no reconoce el cliente

                System.out.println("Datos incorrectos, introduzcalos de nuevo");
                System.out.println("Ingrese su Id: ");
                ID = sc.nextLong();
                System.out.println("Ingrese su nombre: ");
                sc.nextLine();
                nombre = sc.nextLine();
              }

            }

            break;

          case 4: // TEST //////////

            System.out.println("Ingrese su Id: ");
            ID = sc.nextLong();
            System.out.println("Ingrese su nombre: ");
            sc.nextLine();
            nombre = sc.nextLine();
            System.out.println("Ingrese el nombre de su proveedor: ");
            sc.nextLine();
            String proveedor2 = sc.nextLine();

            for (ProveedorInternet proveedor3 : proveedores) {
              if (proveedor3.getNombre().equals(proveedor2)) {
                proveedorActual = proveedor3;
              }
            }

            boolean Vcontrol5 = false;
            while (Vcontrol5 == false) {
              if (proveedorActual.verificarCliente(nombre, ID) != null) { /// Primer método de la clase Proveedor
                Vcontrol5 = true;
                Cliente clienteActual = proveedorActual.verificarCliente(nombre, ID);
                String resultado = clienteActual.getModem().test(clienteActual); // Segundo método de la clase Router
                ArrayList<Antena> antenasSede = Antena.antenasSede(clienteActual.getModem().getSede()); // Cuarto metodo
                                                                                                        // clase Antena

                if (resultado.contains("saturada")) {
                  System.out.println("Se recomienda remover dispositivos");
                  System.out.println("Dispositvos:\n");
                  ArrayList<Dispositivo> dispositivosClientes = clienteActual.getModem()
                      .verificarDispositivos(Dispositivo.getDispositivosTotales(), clienteActual.getModem());

                  dispositivosClientes.stream().forEach(System.out::println);

                } else if (resultado.contains("incompatible")) {
                  System.out.println(resultado);
                  System.out.println("Esta Antena es compatible y accesible: ");
                  System.out.println(clienteActual.getModem().getAntenaAsociada()
                      .rastrearGeneracionCompatible(antenasSede, clienteActual.getModem())); // Tercer metodo clase
                                                                                             // Antena
                  System.out.println("Deseas cambiar a la nueva antena:" + "\n1.Si" + "\n2.No");

                  boolean Vcontrol11 = false;
                  while (Vcontrol11 == false) {
                    opc = sc.nextInt();
                    if (opc == 1) {
                      Vcontrol11 = true;
                      Antena antenaNueva = clienteActual.getModem().getAntenaAsociada()
                          .rastrearGeneracionCompatible(antenasSede, clienteActual.getModem());
                      if (antenaNueva.getProveedor().getNombre().equals(clienteActual.getProveedor().getNombre())) {
                        clienteActual.getModem().setAntenaAsociada(antenaNueva);
                        System.out.print("Tu proceso ha finalizado con éxito");
                      } else {
                        int costo = 100000;
                        System.out.print(
                            "Esta antena pertenece a otro proveedor, por tanto el roaming tiene un costo de " + costo);

                        boolean Vcontrol12 = false;
                        while (Vcontrol12 == false) {
                          int pagoCliente = sc.nextInt();
                          if (pagoCliente >= costo) {
                            Vcontrol12 = true;
                            System.out.print("Te han quedado " + (pagoCliente - costo) + "pesos."
                                + "\nTu proceso ha finalizado con éxito.");
                          } else {
                            System.out.print("Saldo insuficiente, por favor ingrese de nuevo la cantidad requerida: ");
                          }
                        }
                      }

                    } else if (opc == 2) {
                      System.out.print("Ha sido un placer servirte.");
                      break;
                    } else {
                      System.out.println("Ingrese una opcion valida.");
                    }

                  }

                } else if (resultado.contains("inaccesible")) {
                  System.out.println(resultado);
                  System.out.println("Esta Antena es compatible y accesible");
                  System.out.println(clienteActual.getModem().getAntenaAsociada()
                      .rastrearGeneracionCompatible(antenasSede, clienteActual.getModem()));

                  System.out.println("Deseas cambiar a la nueva antena:" + "\n1.Si" + "\n2.No");

                  boolean Vcontrol13 = false;
                  while (Vcontrol13 == false) {
                    opc = sc.nextInt();
                    if (opc == 1) {
                      Vcontrol13 = true;
                      Antena antenaNueva = clienteActual.getModem().getAntenaAsociada()
                          .rastrearGeneracionCompatible(antenasSede, clienteActual.getModem());
                      if (antenaNueva.getProveedor().getNombre().equals(clienteActual.getProveedor().getNombre())) {
                        clienteActual.getModem().setAntenaAsociada(antenaNueva);
                        System.out.print("Tu proceso ha finalizado con éxito");
                      } else {
                        int costo = 100000;
                        System.out.print(
                            "Esta antena pertenece a otro proveedor, por tanto el roaming tiene un costo de " + costo);

                        boolean Vcontrol14 = false;
                        while (Vcontrol14 == false) {
                          int pagoCliente = sc.nextInt();
                          if (pagoCliente >= costo) {
                            Vcontrol14 = true;
                            System.out.print("Te han quedado " + (pagoCliente - costo) + "pesos."
                                + "\nTu proceso ha finalizado con éxito.");
                          } else {
                            System.out.print("Saldo insuficiente, por favor ingrese de nuevo la cantidad requerida: ");
                          }
                        }
                      }
                    }
                  }
                } else {
                  System.out.println("Datos incorrectos, introduzcalos de nuevo.");
                  System.out.println("Ingrese su Id: ");
                  ID = sc.nextLong();
                  System.out.println("Ingrese su nombre: ");
                  sc.nextLine();
                  nombre = sc.nextLine();
                  System.out.println("Ingrese el nombre de su proveedor: ");
                  sc.nextLine();
                  proveedor = sc.nextLine();
                }
              }
            }

            break;

          case 5: //////// FUNCIONALIDAD REPORTE /////////////////

            System.out.println("Ingrese el nombre de su compañía: ");
            nombre = sc.nextLine();

            boolean Vcontrol7 = false;
            while (Vcontrol7 == false) {
              if (Servidor.adminServidor().verificarAdmin(proveedores, nombre) != null) {// ok Primer método de la clase
                                                                                         // Servidor
                Vcontrol7 = true;

                ArrayList<Servidor> servidoresProveedor = Servidor.adminServidor().verificarAdmin(proveedores, nombre);
                System.out.println("La compañía de Internet " + nombre + " tiene servidores en las siguientes sedes: ");

                int contador = 1;
                for (Servidor servidor : servidoresProveedor) {
                  System.out.println(contador + ". " + servidor.getSede());
                  contador++;
                }

                boolean Vcontrol8 = false;
                while (Vcontrol8 == false) {
                  System.out.println("Ingresa la sede que deseas revisar (Indica el número de la opción): ");
                  opc = sc.nextInt();
                  if ((opc <= contador) && (opc >= 1)) {
                    Vcontrol8 = true;

                    Servidor servidorLocalidad = servidoresProveedor.get(opc - 1);

                    // Clientes separados por plan del proveedor asociado al servidor---Segundo
                    // metodo de la clase Cliente
                    ArrayList<Cliente> clientesBasic = Cliente.buscarCliente1(servidorLocalidad.getSede(), "BASIC",
                        servidorLocalidad.getProveedor());
                    ArrayList<Cliente> clientesStandard = Cliente.buscarCliente1(servidorLocalidad.getSede(),
                        "STANDARD", servidorLocalidad.getProveedor());
                    ArrayList<Cliente> clientesPremium = Cliente.buscarCliente1(servidorLocalidad.getSede(), "PREMIUM",
                        servidorLocalidad.getProveedor());

                    // Tercer método de la clase Router

                    // Calculo de la intensidades Reales
                    ArrayList<Integer> intensidadBasic = Router.adminRouter().intensidadFlujoClientes(clientesBasic,
                        servidorLocalidad, true);
                    ArrayList<Integer> intensidadStandard = Router.adminRouter()
                        .intensidadFlujoClientes(clientesStandard, servidorLocalidad, true);
                    ArrayList<Integer> intensidadPremium = Router.adminRouter().intensidadFlujoClientes(clientesPremium,
                        servidorLocalidad, true);

                    // Calculo intensidades que deberian llegar
                    ArrayList<Integer> intensidadB = Router.adminRouter().intensidadFlujoClientes(clientesBasic,
                        servidorLocalidad, false);
                    ArrayList<Integer> intensidadS = Router.adminRouter().intensidadFlujoClientes(clientesStandard,
                        servidorLocalidad, false);
                    ArrayList<Integer> intensidadP = Router.adminRouter().intensidadFlujoClientes(clientesPremium,
                        servidorLocalidad, false);

                    // Comparaciones

                    // Promedio intensidades reales
                    int PromedioBasic = (intensidadBasic.stream().mapToInt(Integer::intValue).sum())
                        / intensidadBasic.size();
                    int PromedioStandard = (intensidadStandard.stream().mapToInt(Integer::intValue).sum())
                        / intensidadStandard.size();
                    int PromedioPremium = (intensidadPremium.stream().mapToInt(Integer::intValue).sum())
                        / intensidadPremium.size();

                    // Promedio intensidades que deberia llegar
                    int PromedioB = (intensidadB.stream().mapToInt(Integer::intValue).sum()) / intensidadB.size();
                    int PromedioS = (intensidadS.stream().mapToInt(Integer::intValue).sum()) / intensidadS.size();
                    int PromedioP = (intensidadP.stream().mapToInt(Integer::intValue).sum()) / intensidadP.size();

                    if ((PromedioBasic < PromedioB) || (PromedioStandard < PromedioS)
                        || (PromedioPremium < PromedioP)) {

                      System.out.print("De acuerdo con el análisis realizado al servidor de la sede "
                          + servidorLocalidad.getSede()
                          + "se hallaron errores en el servidor, a continuación se presentan las recomendaciones y el reporte.");

                      // Distancias optimas por plan
                      ArrayList<Integer> dOptBasic = servidorLocalidad.distanciasOptimas(clientesBasic, intensidadBasic,
                          intensidadB);
                      ArrayList<Integer> dOptStandard = servidorLocalidad.distanciasOptimas(clientesStandard,
                          intensidadStandard, intensidadS);
                      ArrayList<Integer> dOptPremium = servidorLocalidad.distanciasOptimas(clientesPremium,
                          intensidadPremium, intensidadP);

                      // Promedio distancias por plan
                      int PromedioDB = (dOptBasic.stream().mapToInt(Integer::intValue).sum()) / dOptBasic.size();
                      int PromedioDS = (dOptStandard.stream().mapToInt(Integer::intValue).sum()) / dOptStandard.size();
                      int PromedioDP = (dOptPremium.stream().mapToInt(Integer::intValue).sum()) / dOptPremium.size();

                      System.out.println("REPORTE" + "\nIntensidades:"
                          + "\nSe encontraron diferencias entre las intensidades que deberían presentar los clientes y las que presentan en realidad. Para ello, se clasificó las intensidades por planes y de esta manera observar cuál es el plan más afectado."
                          + "\nPLAN BASIC" + "\nIntensidad de Flujo Promedio Neta: " + PromedioBasic
                          + "\nIntensidad de Flujo Promedio Adecuado: " + PromedioB + "\nPLAN STANDARD"
                          + "\nIntensidad de Flujo Promedio Neta: " + PromedioStandard
                          + "\nIntensidad de Flujo Promedio Adecuada: " + PromedioS + "\nPLAN PREMIUM"
                          + "\nIntensidad de Flujo Promedio Real: " + PromedioStandard
                          + "\nIntensidad de Flujo Promedio Adecuada: " + PromedioS
                          + "\nLas intensidades dependen en gran medida de las distancias entre el router de los clientes y el servidor, por ello se recomienda cambiar la ubicación de este último. Por tanto, se presentan las distancias promedios recomendadas a la cual debe estar el servidor para que proporcione la intensidad de flujo adecuada en cada plan."
                          + "\nDISTANCIAS ÓPTIMAS" + "\nDistancia Promedio-Plan Basic: " + PromedioDB
                          + "\nDistancia Promedio-Plan Standard: " + PromedioDS + "\nDistancia Promedio-Plan Premium: "
                          + PromedioDP
                          + "\nFinalmente, las intensidades de flujo reales están afectadas por el porcentaje de eficiencia del servidor, esto indica que el servidor está consumiendo una cantidad significativa del flujo de red inicial que le proporciona la red. Por ende, se recomienda cambiarlo o en su defecto, implementar la solución de las distancias, expuesta anteriormente.");

                      if (servidorLocalidad.getRouters().size() == servidorLocalidad.getINDICE_SATURACION()) {
                        System.out.print(
                            "ALERTA: El servidor ya se encuentra saturado, es decir, ya tiene la cantidad de routers límite conectados. Se recomienda ampliar su capacidad.");
                        break;
                      }

                      break;

                    } else {
                      System.out.print(
                          "El servidor funciona correctamente y proporciona las intensidades de flujo adecuadas.");
                      break;
                    }

                  } else {
                    System.out.print("Ingresa una opción válida.");
                  }
                }

              } else { // Cuando no reconoce al admin
                System.out.println("Datos incorrectos, introduzcalos de nuevo");
                System.out.println("Ingrese el nombre de su compañía: ");
                nombre = sc.nextLine();
              }
            }

            break;

          case 6: // Salir///////////////////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("Ha sido un gusto servirte :D");
            break;
        }
      } else {
        System.out.println("Ingresa una opcion válida");
      }
    }
  }
}
                          
