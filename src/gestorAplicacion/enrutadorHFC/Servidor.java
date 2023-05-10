package gestorAplicacion.enrutadorHFC;

import java.util.ArrayList;
import gestorAplicacion.host.Cliente;
import gestorAplicacion.host.ProveedorInternet;
import gestorAplicacion.servicio.Plano;

public class Servidor implements Red {
  private String sede;
  private final double PORCENTAJE_EFICIENCIA;
  private boolean saturado = false;
  private ProveedorInternet proveedor;
  private Plano coordenadas;  //Aqui se obtienen las coordenadas
  private static ArrayList<Servidor> servidoresTotales = new ArrayList<>();
  private ArrayList<Router> routers = new ArrayList<>();
  private final int INDICE_SATURACION; // numero de cientes max que puede tener el servidor
  private final int flujoDeRedNeto;

  //CONSTRUCTORES

 public Servidor(String sede, boolean saturado, ProveedorInternet proveedor, Plano coordenadas, int indice, double pe) {
    this(saturado,indice,pe);
    this.sede = sede;
    this.proveedor = proveedor;
    this.coordenadas = coordenadas;
    
  }

  public Servidor(boolean saturado, int INDICE_SATURACION, double pe){
    this.saturado=saturado;
    this.INDICE_SATURACION=INDICE_SATURACION;
    this.flujoDeRedNeto=((int)(pe))*flujoDeRedPreeliminar;
    PORCENTAJE_EFICIENCIA = pe;
    Servidor.getServidoresTotales().add(this);
  }

  
  // Métodos
  // Funcionalidad MejoratuPlan()
  public static ArrayList<Servidor> buscarServidores(String localidad) {
    ArrayList<Servidor> serviSede = new ArrayList<Servidor>();
    for (Servidor servidor : servidoresTotales) {
      if ((servidor.getSede().equals(localidad)) && (!servidor.isSaturado())) {
        serviSede.add(servidor);
      }
    }
    return serviSede;
  }


  //Funcionalidad Reporte()
  //Verifica admin y retorna servidores del proveedor en todas las localidades//Se le pasa una lista con todos los proveedores
  public  ArrayList<Servidor> verificarAdmin(ArrayList<ProveedorInternet> proveedores, String nombre){
    ArrayList<Servidor> servidoresProveedor = new ArrayList<>();
    for(ProveedorInternet proveedor: proveedores){
      if(proveedor.getNombre().equals(nombre)){
        for(Servidor servidor: servidoresTotales){
          if(servidor.getProveedor().getNombre().equals(nombre)){
            servidoresProveedor.add(servidor);
          }
        }
      }else{ return null;}
    }
    return servidoresProveedor;
  }
  
  //Método DistanciasOptimas por Plan
  public ArrayList<Integer> distanciasOptimas(ArrayList<Cliente> clientes,ArrayList<Integer> listaIntensiRealesClientes, ArrayList<Integer> listaIntensidadesClientes){
    ArrayList<Integer> distancias = new ArrayList<>();
    for(int i=0; i<listaIntensiRealesClientes.size(); i++){

      Router modem = clientes.get(i).getModem();
      int intensidadFaltante = listaIntensidadesClientes.get(i) - listaIntensiRealesClientes.get(i);
      int velocidad = new Conexion(modem.getIP(), modem.getUp(),modem.getDown(),modem.isOnline(),modem.getGeneracion(),clientes.get(i),modem.getServidorAsociado()).getVelocidad();
      int dispositivos=modem.verificarDispositivos(Dispositivo.getDispositivosTotales(), modem).size();
      
      int distanciaOptima = (int)((velocidad/dispositivos)-(intensidadFaltante/modem.getServidorAsociado().getPORCENTAJE_EFICIENCIA()));
      distancias.add(distanciaOptima);
    }
    return distancias;
  }

  //NECESITAMOS UN SERVIDOR PARA PODER EJECUTAR METODOS PARA REPORTE
  public static Servidor adminServidor(){
    return new Servidor(false,0,0);
  }
  
   // Getters and Setters
  public String getSede() {
    return sede;
  }


  public void setSede(String sede) {
    this.sede = sede;
  }


  public double getPORCENTAJE_EFICIENCIA() {
    return PORCENTAJE_EFICIENCIA;
  }


  public boolean isSaturado() {
    return saturado;
  }


  public void setSaturado(boolean saturado) {
    this.saturado = saturado;
  }


  public ProveedorInternet getProveedor() {
    return proveedor;
  }


  public void setProveedor(ProveedorInternet proveedor) {
    this.proveedor = proveedor;
  }


  public Plano getCoordenadas() {
    return coordenadas;
  }


  public void setCoordenadas(Plano coordenadas) {
    this.coordenadas = coordenadas;
  }


  public static ArrayList<Servidor> getServidoresTotales() {
    return servidoresTotales;
  }


  public static void setServidoresTotales(ArrayList<Servidor> servidoresTotales) {
    Servidor.servidoresTotales = servidoresTotales;
  }


  public ArrayList<Router> getRouters() {
    return routers;
  }


  public void setRouters(ArrayList<Router> routers) {
    this.routers = routers;
  }


  public int getINDICE_SATURACION() {
    return INDICE_SATURACION;
  }


  public int getFlujoDeRedNeto() {
    return flujoDeRedNeto;
  }

}