package gestorAplicacion.enrutadorHFC;
import java.util.*;

import gestorAplicacion.host.Cliente;
import gestorAplicacion.servicio.Plano;

import java.lang.reflect.*;
import java.lang.reflect.InvocationTargetException;
import java.io.IOException;

public class Router extends Cobertura {

  // Atributos
  private final String IP;
  private int up;
  private int down;
  private boolean online;
  private int ping;
  private Plano coordenadas; // Aqui se obtienen las coordenadas del router
  private Servidor servidorAsociado;
  private Antena antenaAsociada;
  private String sede;

  // Constructores
  public Router(int up, int down, boolean online, Servidor servidorAsociado) {
    
    super(3);

    Random random = new Random();
    int octeto1 = random.nextInt(256);
    int octeto2 = random.nextInt(256);
    int octeto3 = random.nextInt(256);
    int octeto4 = random.nextInt(256);
    String ip = octeto1 + "." + octeto2 + "." + octeto3 + "." + octeto4;

    IP = ip;
    this.up = up;
    this.down = down;
    this.online = online;
    this.servidorAsociado=servidorAsociado;
    servidorAsociado.getRouters().add(this);
  }

  
  // Métodos
  // Metodo actualizarvelocidad---Funcionalidad test
  public ArrayList<Integer> actualizarVelocidad(Cliente cliente) {
    int megasUp = cliente.getPlan().get(1);
    int megasDown = cliente.getPlan().get(0);
    Router routerCliente = cliente.getModem();
    ArrayList<Dispositivo> dispositivos = Dispositivo.getDispositivosTotales();
    ArrayList<Dispositivo> dispositivosCliente = verificarDispositivos(dispositivos, routerCliente);
    ArrayList<Integer> listaActualizada = new ArrayList<Integer>();
    if (dispositivosCliente.size() == 0) {
        return null;
    } else {
      for (Dispositivo dispoActual : dispositivosCliente) {
        // if (antenaAsociada.verificarZonaCobetura(this) && generacion==antenaAsociada.getGeneracion()) {

          if (antenaAsociada.verificarZonaCobertura(this)){
            if (dispoActual.getNombre().equals("Celular")) {
              megasUp -= 4;
              megasDown -= 10;
            }
            if (dispoActual.getNombre().equals("Computador")) {
              megasUp -= 8;
              megasDown -= 20;
            }
            if (dispoActual.getNombre().equals("Televisor")) {
              megasUp -= 10;
              megasDown -= 30;
            }
          }else{
              megasUp=-1;
              megasDown=-1;
              break;
            }
        }

      }
      listaActualizada.add(megasDown);
      listaActualizada.add(megasUp);
      return listaActualizada;
    }
  //}

  // Metodo de Actualizar Ping -- Retornamos
  public String actualizarPing(ArrayList<Integer> listaActualizada) {
    int up = listaActualizada.get(1);
    int down = listaActualizada.get(0);
    int ping = 0;
    while (true) {
      if (up >= 60 && down >= 400) {
        ping = (int) (Math.random() * 20);
        // cliente.getModem().setPing(ping);
        return "\rPing: \033[32m" + ping + " \033[0mms";

      } else if (up >= 40 && down >= 200) {
        ping = (int) (Math.random() * 21) + 20;
        return "\rPing: \033[32m" + ping + " \033[0mms";
      } else if (up >= 20 && down >= 100) {
        ping = (int) (Math.random() * 21) + 41;
        return "\rPing: \033[33m" + ping + " \033[0mms";
      } else if (up >= 0 && down >= 0) {
        ping = (int) (Math.random() * 21) + 90;
        return "\rPing: \033[33m" + ping + " \033[0mms";
      } else if (up < 0 && down < 0) {
        ping = (int) (Math.random() * 99) + 900;
        return "\rPing: \033[31m" + ping + " \033[0mms";
      }

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public String test(Cliente cliente) {
    ArrayList<Integer> up_down = cliente.getModem().actualizarVelocidad(cliente);
    int ping = Integer.parseInt(cliente.getModem().actualizarPing(up_down).split(" ")[1]);
    // int ping_numero = ping.substring(ping.indexOf("\033[32m") + 7, ping.indexOf("
    // \033[0mms"));
    int up = up_down.get(1);
    int down = up_down.get(0);
    String resultado1 = " ";
    String resultado2 = " ";
    String resultado3 = " ";
    resultado1 = (900 < ping) ? "Red saturada"
        : "Test de velocidad finalizado";
    resultado2 = (antenaAsociada.verificarZonaCobertura(this)) ? "Test de velocidad finalizado." : "Inaccesible a la zona de cobertura.";
    resultado3= (generacion==antenaAsociada.getGeneracion()) ? "Test de velocidad finalizado.":"Generación incompatible";


    if (resultado1.contains("saturada")) {
      return actualizarPing(up_down) + "\n" + resultado1;

    } 
    else if(resultado2.contains("cobertura")){
      return resultado2;
    } 
    else if(resultado3.contains("incompatible")){
      return resultado3;
    }
    else {
      return up + "\n" + down + "\n" + actualizarPing(up_down) + resultado1;
    }
  }

  // Verificar Dispositivos de un cliente
  public ArrayList<Dispositivo> verificarDispositivos(ArrayList<Dispositivo> dispositivosTotales,
      Router router) { // Crear

    ArrayList<Dispositivo> dispositivosConectados = new ArrayList<>();
    for (Dispositivo dispoActual : dispositivosTotales) {
      if (dispoActual.getIpAsociada().equals(router.getIP()) && (router.isOnline() == true)) {
        dispositivosConectados.add(dispoActual);
      }
      return dispositivosConectados;
    }
    return null;
  }

  // Funcionalidad Recomendacion()

  public ArrayList<Object> protocoloSeleccionServidor(ArrayList<Servidor> servidores, Cliente c) {

    // 1.Coordenadas Routercliente
    int coordenadaX = coordenadas.getX();
    int coordenadaY = coordenadas.getY();

    // 2.ListaDispositivos conectados al router
    ArrayList<Dispositivo> dispositivosConectados = verificarDispositivos(Dispositivo.getDispositivosTotales(), this);

    // 3. Intensidad de flujo Inicial
    int distancia = (int) Math.sqrt(Math.pow(servidorAsociado.getCoordenadas().getX() - coordenadaX, 2)
        + Math.pow(servidorAsociado.getCoordenadas().getY() - coordenadaY, 2));

    int velocidadTotal = new Conexion(IP, down, up, online, generacion, c,c.getModem().getServidorAsociado()).getVelocidad();

    intensidadFlujo = (velocidadTotal / dispositivosConectados.size()) - distancia;

    // 4. Precio Inicial
    int precioCliente = c.getPlan().get(2);

    // 5. Generacion Inicial
    //int generacion = getGeneracion();

    // 6. Plan del cliente
    String planCliente = c.getNombrePlan();

    // 7.Servidor más

    ArrayList<Object> intensidadMayor = intensidadFlujoOptima(servidores, c);
    Servidor servidorAproximado = (Servidor)(intensidadMayor.get(0)); // Ligadura dinamica
    int intensidadServidorAproximado = (int)(intensidadMayor.get(1)); // Ligadura dinamica
    ArrayList<Object> caracteristicas = new ArrayList<>();

    // Variables comparativas/temporales
     if ( intensidadServidorAproximado == intensidadFlujo) {
      if (servidorAproximado == servidorAsociado) { // Cuando la intensidad coincide y el servidor es el mismo.
        return null;
      } else {
        try {
          Field planServidor = servidorAproximado.getClass().getDeclaredField(planCliente);
          Method m = servidorAproximado.getProveedor().getPlanes().getClass().getMethod("get" + planServidor);
          Object listaPlan= m.invoke(servidorAproximado);
          ArrayList<Integer> listaP=(ArrayList<Integer>)(listaPlan);
          if (precioCliente > listaP.get(2)) {
            
            caracteristicas.add(intensidadFlujo);
            caracteristicas.add(servidorAproximado);
            caracteristicas.add(servidorAproximado.getProveedor());
            caracteristicas.add(intensidadServidorAproximado);
            caracteristicas.add(listaP);
            return caracteristicas; 
          }else{
            return null;
          }
        } catch (NoSuchFieldException | NoSuchMethodException | IllegalAccessException e) {
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          e.getCause().printStackTrace();
        }
      } /// Cuando la intensidad coincide pero el servidor es distinto..

    } else if (intensidadFlujo < intensidadServidorAproximado) {
      if (planCliente.equals("BASIC")) {
        
        caracteristicas.add(intensidadFlujo);
        caracteristicas.add(servidorAproximado);
        caracteristicas.add(servidorAproximado.getProveedor());
        caracteristicas.add(intensidadServidorAproximado);
        caracteristicas.add(servidorAproximado.getProveedor().getPlanes().getBASIC());
        
        return caracteristicas;

      } else if (planCliente.equals("STANDARD")) {
        
        caracteristicas.add(intensidadFlujo);
        caracteristicas.add(servidorAproximado);
        caracteristicas.add(servidorAproximado.getProveedor());
        caracteristicas.add(intensidadServidorAproximado);
        caracteristicas.add(servidorAproximado.getProveedor().getPlanes().getSTANDARD());
        
        return caracteristicas;

      } else if (planCliente.equals("PREMIUM")) {
        
        caracteristicas.add(intensidadFlujo);
        caracteristicas.add(servidorAproximado);
        caracteristicas.add(servidorAproximado.getProveedor());
        caracteristicas.add(intensidadServidorAproximado);
        caracteristicas.add(servidorAproximado.getProveedor().getPlanes().getPREMIUM());
        
        return caracteristicas;
      }
    }
    return null;
  }

  // Metodo abstracto de Cobertura()//Funcionalidad Mejora tu Plan
  public ArrayList<Object> intensidadFlujoOptima(ArrayList<Servidor> servidoresLocalidad, Cliente c) {

    int distancia = (int) Math.sqrt(Math.pow(servidorAsociado.getCoordenadas().getX() - coordenadas.getX(), 2)
        + Math.pow(servidorAsociado.getCoordenadas().getY() - coordenadas.getY(), 2));
    

    int velocidadTotal = new Conexion(IP, down, up, online, generacion, c, c.getModem().getServidorAsociado()).getVelocidad();

    int distanciaTemporal = 0;
    Servidor servTemporal = null;

    for (Servidor servidor : servidoresLocalidad) {

      // Distancia

      distanciaTemporal = (int) Math.sqrt(Math.pow(servidor.getCoordenadas().getX() - coordenadas.getX(), 2)
          + Math.pow(servidor.getCoordenadas().getY() - coordenadas.getY(), 2));

      if (distanciaTemporal < distancia) {
        distancia = distanciaTemporal;
        servTemporal = servidor;
      }

    }

     Integer intensidadFlujoMayor = (int)(((velocidadTotal/verificarDispositivos(Dispositivo.getDispositivosTotales(), this).size()) - distancia)* servidorAsociado.getPORCENTAJE_EFICIENCIA());
    ArrayList<Object> lista = new ArrayList<Object>();
    lista.add(servTemporal);
    lista.add(intensidadFlujoMayor);

    return lista;
  }

  // Intensidades---Funcionalidad Reporte
  //Tambien se hereda de cobertura
  public ArrayList<Integer> intensidadFlujoClientes(ArrayList<Cliente> ctes, Servidor servidor, boolean Reales) { //Si reales es true-InteReales
    ArrayList<Integer> lista = new ArrayList<>();

    for (Cliente c : ctes) {

      String ip = c.getModem().getIP();
      int up = c.getModem().getUp();
      int down = c.getModem().getDown();
      boolean online = c.getModem().isOnline();
      int generacion = c.getModem().getGeneracion();
      int velocidad = new Conexion(ip, up, down, online, generacion, c, c.getModem().getServidorAsociado()).getVelocidad();
      int dispositivos = c.getModem().verificarDispositivos(Dispositivo.getDispositivosTotales(), c.getModem()).size();
      int distancia = (int) Math.sqrt(Math.pow(c.getModem().getServidorAsociado().getCoordenadas().getX() - c.getModem().getCoordenadas().getX(), 2)
      + Math.pow(c.getModem().getServidorAsociado().getCoordenadas().getY() - c.getModem().getCoordenadas().getY(), 2));
      double porcentaje = c.getModem().getServidorAsociado().getPORCENTAJE_EFICIENCIA();

      int intensidad = (int)((velocidad / dispositivos - distancia) * porcentaje);
      int intensidad1 = (int)(intensidad/porcentaje);
      
      if (Reales) {
        lista.add(intensidad); // Intensidad neta
      } else {
        lista.add(intensidad1); //Intensidad que deberia llegarle
      }
    }
    return lista;
  }

  //SOLO PARA QUE NO DE ERROR EN COMPILACION PORQUE HEREDA DE COBERTURA
  public Antena rastrearGeneracionCompatible(ArrayList<Antena> antenasSede,Router r){
    return null;
  }

  //PARA REPORTE
  public static Router adminRouter(){
    return new Router(0, 0, false, null);
  }

  // Metodos getters y setters de los atributos
  public Servidor getServidorAsociado() {
    return servidorAsociado;
  }

  public void setServidorAsociado(Servidor servidor) {
    this.servidorAsociado = servidor;
    servidorAsociado.getRouters().add(this);
  }

  public String getIP() {
    return IP;
  }

  public int getUp() {
    return up;
  }

  public void setUp(int up) {
    this.up = up;
  }

  public int getDown() {
    return down;
  }

  public void setDown(int down) {
    this.down = down;
  }

  public boolean isOnline() {
    return online;
  }

  public void setOnline(boolean online) {
    this.online = online;
  }

  public int getPing() {
    return ping;
  }

  public void setPing(int ping) {
    this.ping = ping;
  }

  public Plano getCoordenadas() {
    return coordenadas;
  }

  public void setCoordenadas(Plano coordenadas) {
    this.coordenadas = coordenadas;
  }

  public Antena getAntenaAsociada() {
    return antenaAsociada;
  }

  public void setAntenaAsociada(Antena antenaAsociada) {
    this.antenaAsociada = antenaAsociada;
  }

  public String getSede() {
    return sede;
  }

  public void setSede(String sede) {
    this.sede = sede;
  }
}