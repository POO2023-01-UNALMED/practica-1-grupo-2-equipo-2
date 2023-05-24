package gestorAplicacion.enrutadorHFC;
import java.util.*;

import gestorAplicacion.host.Cliente;
import gestorAplicacion.servicio.Plano;

public class Router extends Cobertura {

  //ESTA CLASE ES UNA DE LAS PRINCIPALES DADO QUE ESTABLECE LA RELACIÓN ENTRE CLIENTE-PROVEEDOR Y ES UTILIZADA EN TODAS LAS FUNCIONALIDADES

  //ATRIBUTOS
  private final String IP;
  private int up;
  private int down;
  private boolean online;
  private int ping;
  private Plano coordenadas; 
  private Servidor servidorAsociado;
  private Antena antenaAsociada;
  private String sede;
  private int velocidad;

  //CONSTRUCTORES
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
    sede=servidorAsociado.getSede();   
    this.servidorAsociado=servidorAsociado;
    velocidad=250;
    servidorAsociado.getRouters().add(this);
  }

  public Router(int up, int down, boolean online, Antena antenaAsociada,Plano coordenadas,int g) {
    
    super(g);

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
    this.coordenadas=coordenadas;
    this.antenaAsociada=antenaAsociada;
    velocidad=250;
    sede=antenaAsociada.getSede();
  }

  public Router(){
    super(3);
    IP="";
    velocidad=250;
  }

  //METODOS

  //METODO DE INSTANCIA---FUNCIONALIDAD TEST-- SE ENCARGA DE ACTUALIZAR LAS MEGAS DEL CLIENTE DE ACUERDO CON LOS DISPOSITIVOS Y SI EL CLIENTE SE ENCUENTRA DENTRO O NO DE LA ZONA DE COBERTURA DE LA ANTENA QUE TIENE ASOCIADA
  public ArrayList<Integer> actualizarVelocidad(Cliente cliente) {
    int megasUp = cliente.getPlan().get(0);
    int megasDown = cliente.getPlan().get(1);
    Router routerCliente = cliente.getModem();
    ArrayList<Dispositivo> dispositivos = Dispositivo.getDispositivosTotales();
    ArrayList<Dispositivo> dispositivosCliente = verificarDispositivos(dispositivos, routerCliente);
    ArrayList<Integer> listaActualizada = new ArrayList<Integer>();
    if (dispositivosCliente.size() == 0) {
        megasUp=26;
        megasDown=18;
    } else {
      for (Dispositivo dispoActual : dispositivosCliente) {
       
          if (antenaAsociada.verificarZonaCobertura(this,antenaAsociada)){
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
              megasUp=26;
              megasDown=18;
              break;
          }
        }

      }
      listaActualizada.add(megasDown);
      listaActualizada.add(megasUp);
      return listaActualizada;
    }

  //METODO INSTANCIA---FUNCIONALIDAD TEST---ACTUALIZA EL PING DE ACUERDO A LAS MEGAS
  public String actualizarPing(ArrayList<Integer> listaActualizada) {
    int up = listaActualizada.get(1);
    int down = listaActualizada.get(0);
    int ping = 0;
 
      if (up >= 60 && down >= 400) {
        ping = (int) (Math.random() * 20);
   
        return "\rPing: " + ping + " ms";

      } else if (up >= 40 && down >= 200) {
        ping = (int) (Math.random() * 21) + 20;
        return "\rPing: "+ ping+ " ms";
      } else if (up >= 20 && down >= 100) {
        ping = (int) (Math.random() * 21) + 41;
        return "\rPing: " + ping+" ms";
      } else if (up >= 0 && down >= 0) {
        ping = (int) (Math.random() * 21) + 90;
        return "\rPing: " + ping+" ms";
      } else if (up < 0 || down < 0) {
        ping = (int) (Math.random() * 99) + 900;
        return "\rPing: " + ping+" ms";
      }else{
        return null;
      }
  }

  //METODO DE ISNTANCIA--FUNCIONALIDAD TEST--SE ENCARGA DE LLAMAR OTROS METODOS Y RETORNAR UN RESULTADO DE ACUERDO A ESTO
  public String test(Cliente cliente) {
    ArrayList<Integer> up_down = cliente.getModem().actualizarVelocidad(cliente);

    int ping = Integer.parseInt(cliente.getModem().actualizarPing(up_down).split(" ")[1]);
   
    int up = up_down.get(1);
    int down = up_down.get(0);
    String resultado1 = " ";
    String resultado2 = " ";
    String resultado3 = " ";
    resultado1 = (900 < ping) ? "Red saturada": "Test de velocidad finalizado";
    resultado2 = (up_down.get(0)==18) ? "Motivo: Inaccesible a la zona de cobertura.":"Test de velocidad finalizado.";
    resultado3= (generacion==antenaAsociada.getGeneracion()) ? "Test de velocidad finalizado.":"Motivo: Generación incompatible";

    if (resultado1.contains("saturada")) {
      return actualizarPing(up_down) + "\n" + resultado1;
    } 
    else if(resultado2.contains("cobertura")){
      return resultado2;
    } 
    else if(resultado3.contains("incompatible")){
      return resultado3;
    }
    else{
      return "Ping: " + actualizarPing(up_down);
    }
  }

  //METODO INSTANCIA--FUNCIONALIDAD VISUALIZAR DISPOSITIVOS--SE ENCARGA DE VERIFICAR CUALES SON LOS DISPOSITIVOS QUE TIENE EL CLIENTE 
  public ArrayList<Dispositivo> verificarDispositivos(ArrayList<Dispositivo> dispositivosTotales, Router router) { // Crear
    ArrayList<Dispositivo> dispositivosConectados = new ArrayList<>();
    for (Dispositivo dispoActual : dispositivosTotales) {
      if (dispoActual.getIpAsociada().equals(router.getIP()) && (router.isOnline() == true)) {
        dispositivosConectados.add(dispoActual);
      }
    }
    return dispositivosConectados;
  }


  //METODO INSTANCIA--FUNCIONALIDAD MEJORA TU PLAN----SE ENCARGA DE CALCULAR DISTINTAS COSAS Y LLAMA OTRO METODO QUE HALLA EL SERVIDOR MÁS CERCANO Y CON MAYOR INTENSIDAD DE FLUJO
  public ArrayList<Object> protocoloSeleccionServidor(ArrayList<Servidor> servidores, Cliente c) {

    //1.COORDENADAS ROUTER CLIENTE
    int coordenadaX = coordenadas.getX();
    int coordenadaY = coordenadas.getY();

    //2.DISPOSITIVOS CONECTADOS AL ROUTER
    ArrayList<Dispositivo> dispositivosConectados = verificarDispositivos(Dispositivo.getDispositivosTotales(), this);

    //3.DISTANCIA DEL ROUTER AL SERVIDOR ASOCIADO
    int distancia = (int) Math.sqrt(Math.pow(servidorAsociado.getCoordenadas().getX() - coordenadaX, 2) + Math.pow(servidorAsociado.getCoordenadas().getY() - coordenadaY, 2));

    //4.VELOCIDAD TOTAL
    int velocidadTotal = new Conexion(IP, down, up, online, generacion, c,c.getModem().getServidorAsociado()).getVelocidad();

    //5.INTENSIDAD DE FLUJO INICIAL O ACTUAL
    intensidadFlujo = (int)((getServidorAsociado().getFLUJO_RED_NETO()+(velocidadTotal / dispositivosConectados.size()) - distancia)*getServidorAsociado().getPORCENTAJE_EFICIENCIA());

    //6.NOMBRE PLAN DEL CLIENTE
    String planCliente = c.getNombrePlan();

    //SE INVOCA EL METODO INTENSIDADFLUJOOPTIMA QUE ENCUENTRA EL SERVIDOR MÁS CERNCANO
    ArrayList<Object> intensidadMayor = intensidadFlujoOptima(servidores, c);
    Servidor servidorAproximado = (Servidor)(intensidadMayor.get(0)); 
    int intensidadServidorAproximado = (int)(intensidadMayor.get(1));

    //CARACTERISTICAS DE LA RECOMENDACION PARA RETORNAR
    ArrayList<Object> caracteristicas = new ArrayList<>();

    //SE REALIZAN LAS COMPARACIONES RESPECTIVAS
     if ( intensidadServidorAproximado == intensidadFlujo) {
      if (servidorAproximado == servidorAsociado) { //CUANDO LAS INTENSIDADES SON IGUALES Y SE TRATA DEL MISMO SERVIDOR (EL CLIENTE YA TIENE LA MEJOR OPCION)
        return null;
      } else { //CUANDO LAS INTENSIDADES COINCIDEN PERO EL SERVIDOR ES DISTINTO---SE COMPARA EL PRECIO DEL PLAN DE ACUERDO AL TIPO DEL PLAN DEL CLIENTE
        if (planCliente.equals("BASIC")){
          if(c.getPlan().get(2)>servidorAproximado.getProveedor().getPlanes().getBASIC().get(2)){

            caracteristicas.add(intensidadFlujo);
            caracteristicas.add(servidorAproximado);
            caracteristicas.add(servidorAproximado.getProveedor());
            caracteristicas.add(intensidadServidorAproximado);
            caracteristicas.add(servidorAproximado.getProveedor().getPlanes().getBASIC());
            
            return caracteristicas;
          }
    
        } else if (planCliente.equals("STANDARD")) {
          
          if(c.getPlan().get(2)>servidorAproximado.getProveedor().getPlanes().getSTANDARD().get(2)){

            caracteristicas.add(intensidadFlujo);
            caracteristicas.add(servidorAproximado);
            caracteristicas.add(servidorAproximado.getProveedor());
            caracteristicas.add(intensidadServidorAproximado);
            caracteristicas.add(servidorAproximado.getProveedor().getPlanes().getSTANDARD());
            
            return caracteristicas;
          }
  
        } else if (planCliente.equals("PREMIUM")) {
          
          if(c.getPlan().get(2)>servidorAproximado.getProveedor().getPlanes().getPREMIUM().get(2)){

            caracteristicas.add(intensidadFlujo);
            caracteristicas.add(servidorAproximado);
            caracteristicas.add(servidorAproximado.getProveedor());
            caracteristicas.add(intensidadServidorAproximado);
            caracteristicas.add(servidorAproximado.getProveedor().getPlanes().getPREMIUM());
            
            return caracteristicas;
          }
        }
        
      } 

    } else if (intensidadFlujo < intensidadServidorAproximado) { //CUANDO LA INTENSIDAD ACTUAL ES MENOR A LA CALCULADA CON EL SERVIDOR MÁS CERCANO
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

  //METODO DE INSTANCIA-- IMPLEMENTACION DEL METODO ABSTRACTO HEREDADO DE COBERTURA---BUSCA EL SERVIDOR MÁS CERCANO Y POR TANTO CON MAYOR INTENSIDAD DE FLUJO--FUNCIONALIDAD MEJORA TU PLAN
  public ArrayList<Object> intensidadFlujoOptima(ArrayList<Servidor> servidoresLocalidad, Cliente c) {

    //1.DISTANCIA DEL ROUTER AL SERVIDOR ASOCIADO INICIALMENTE
    int distancia = (int) Math.sqrt(Math.pow(servidorAsociado.getCoordenadas().getX() - coordenadas.getX(), 2) + Math.pow(servidorAsociado.getCoordenadas().getY() - coordenadas.getY(), 2));
    
    //APLICACION DE LIGADURA DINÁMICA Y SE HALLA LA VELOCIDAD TOTAL DE CONEXION
    Router conexion = new Conexion(IP, down, up, online, generacion, c, c.getModem().getServidorAsociado());
    int velocidadTotal = conexion.getVelocidad();
    
    //VARIABLES
    int distanciaTemporal = 0;
    Servidor servTemporal = null;

    //SE RECORREN LOS SERVIDORES DE LA SEDE Y SE HALLA EL MÁS CERCANO
    for (Servidor servidor : servidoresLocalidad) {

      distanciaTemporal = (int) Math.sqrt(Math.pow(servidor.getCoordenadas().getX() - coordenadas.getX(), 2) + Math.pow(servidor.getCoordenadas().getY() - coordenadas.getY(), 2));

      if (distanciaTemporal <= distancia) {
        distancia = distanciaTemporal;
        servTemporal = servidor;
      }
    }

    Integer intensidadFlujoMayor = (int)((servTemporal.getFLUJO_RED_NETO()+(velocidadTotal/verificarDispositivos(Dispositivo.getDispositivosTotales(), this).size()) - distancia)* servTemporal.getPORCENTAJE_EFICIENCIA());
    ArrayList<Object> lista = new ArrayList<Object>();
    
    lista.add(servTemporal);
    lista.add(intensidadFlujoMayor);
    
    return lista;
  }


  //METODOS INSTANCIA-- IMPLEMENTACION DEL METODO ABSTRACTO HEREDADO DE COBERTURA--FUNCIONALIDAD REPORTE
  public ArrayList<Integer> intensidadFlujoClientes(ArrayList<Cliente> ctes, Servidor servidor, boolean Reales) { //SI REALES==TRUE CALCULA LAS INTENSIDADES REALES, DE LO CONTRARIO LAS QUE DEBERIAN LLEGAR
   
    ArrayList<Integer> lista = new ArrayList<>();

    for (Cliente c : ctes) {

      int intensidad=0;
      String ip = c.getModem().getIP();
      int up = c.getModem().getUp();
      int down = c.getModem().getDown();
      boolean online = c.getModem().isOnline();
      int generacion = c.getModem().getGeneracion();
      int velocidad = new Conexion(ip, up, down, online, generacion, c, servidor).getVelocidad();
      int dispositivos = c.getModem().verificarDispositivos(Dispositivo.getDispositivosTotales(), c.getModem()).size();
      int distancia = (int) Math.sqrt(Math.pow(servidor.getCoordenadas().getX() - c.getModem().getCoordenadas().getX(), 2) + Math.pow(servidor.getCoordenadas().getY() - c.getModem().getCoordenadas().getY(), 2));
      double porcentaje = servidor.getPORCENTAJE_EFICIENCIA();

      if(dispositivos!=0){
        intensidad = (int)((servidor.getFLUJO_RED_NETO()+velocidad / dispositivos - distancia) * porcentaje);
      }else{
        intensidad = (int)((servidor.getFLUJO_RED_NETO()- distancia) * porcentaje);
      }
      
      int intensidad1 = (int)(intensidad/porcentaje);
      
      if (Reales) {
        lista.add(intensidad); //INTENSIDAD REAL (LAS QUE ESTÁ LLEGANDO)
      } else {
        lista.add(intensidad1); //INTENSIDAD ADECUADA (LA QUE DEBERIA LLEGAR)
      }
    }
    return lista;
  }

  //DEFINICIÓN DEL METODO ABSTRACTO QUE HEREDA DE COBERTURA--EVITAR ERRORES DE COMPILACION
  public Antena rastrearGeneracionCompatible(ArrayList<Antena> antenasSede,Router r){
    return null;
  }

  //METODO ESTATICO QUE CREA UN ROUTER PARA EL ADMINISTRADOR---UN ROUTER ALEATORIO--FUNCIONALIDAD REPORTE
  public static Router adminRouter(){
    return new Router();
  }

  //SETTERS Y GETTERS

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

  public Servidor getServidorAsociado() {
    return servidorAsociado;
  }

  public void setServidorAsociado(Servidor servidorAsociado) {
    this.servidorAsociado = servidorAsociado;
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

  public int getVelocidad() {
    return velocidad;
  }

  public void setVelocidad(int velocidad) {
    this.velocidad = velocidad;
  }
  
}