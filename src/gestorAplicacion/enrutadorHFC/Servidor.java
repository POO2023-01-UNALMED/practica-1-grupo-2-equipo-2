package gestorAplicacion.enrutadorHFC;

import java.io.Serializable;
import java.util.ArrayList;
import gestorAplicacion.host.Cliente;
import gestorAplicacion.host.ProveedorInternet;
import gestorAplicacion.servicio.Plano;

public class Servidor implements Red, Serializable{

  //CLASE FUNDAMENTAL PARA LA IMPLEMENTACION DE LA FUNCIONALIDAD MEJORA TU PLAN Y REPORTE

  //ATRIBUTOS
  private String sede;
  private final double PORCENTAJE_EFICIENCIA; //HACE REFERENCIA A QUÉ TAN BUENO ES EL SERVIDOR
  private boolean saturado = false;
  private ProveedorInternet proveedor;
  private Plano coordenadas;  
  private static ArrayList<Servidor> servidoresTotales = new ArrayList<>();
  private ArrayList<Router> routers = new ArrayList<>();
  private final int INDICE_SATURACION; //NUMERO MAX DE CLIENTES QUE PUEDE TENER UN SERVIDOR
  private final int FLUJO_RED_NETO; //ES EL FLUJO QUE LE QUEDA AL SERVIDOR

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
    PORCENTAJE_EFICIENCIA = pe;
    this.FLUJO_RED_NETO=(int)(PORCENTAJE_EFICIENCIA*FLUJO_RED_PRELIMINAR);
    servidoresTotales.add(this);
  }

  public Servidor(){
    INDICE_SATURACION=0;
    PORCENTAJE_EFICIENCIA = 0;
    FLUJO_RED_NETO=0;
  }

  
  // METODOS
  
  //METODO ESTATICO---FUNCIONALIDAD MEJORA TU PLAN--BUSCA Y RETORNA TODOS LOS SERVIDORES QUE EXISTAN EN UNA SEDE EN ESPECIFICO
  public static ArrayList<Servidor> buscarServidores(String localidad) {
    ArrayList<Servidor> serviSede = new ArrayList<Servidor>();
    for (Servidor servidor : servidoresTotales) {
      if ((servidor.getSede().equals(localidad)) && (!servidor.isSaturado())) {
        serviSede.add(servidor);
      }
    }
    return serviSede;
  }


  //IMPLEMENTA EL METODO DE LA INTERFACE(RED)--FUNCIONALIDAD REPORTE--VERIFICA ADMIN Y RETORNA SERVIDORES DE UN PROVEEDOR EN TODAS LAS LOCALIDADES
  public  ArrayList<Servidor> verificarAdmin(ArrayList<ProveedorInternet> proveedores, String nombre){
    ArrayList<Servidor> servidoresProveedor = new ArrayList<>();
    for(ProveedorInternet proveedor: proveedores){
      if(proveedor.getNombre().equals(nombre)){
        for(Servidor servidor: servidoresTotales){
          if(servidor.getProveedor().getNombre().equals(nombre)){
            servidoresProveedor.add(servidor);
          }
        }
      }
    }
    return servidoresProveedor;
  }
  
  //IMPLEMENTA METODO DE LA INTERFACE(RED)---FUNCIONALIDAD REPORTE-CALCULA LAS DISTANCIAS A LA QUE DEBERIA UBICARSE EL SERVIDOR PARA GENERAR UNA AMYOR INTENSIDAD DE FLUJO
  public ArrayList<Integer> distanciasOptimas(ArrayList<Cliente> clientes, ArrayList<Integer> listaIntensidadesClientes){
    ArrayList<Integer> distancias = new ArrayList<>();
    for(int i=0; i<listaIntensidadesClientes.size(); i++){

      int distanciaOptima=0;
      Router modem = clientes.get(i).getModem();
      int velocidad = new Conexion(modem.getIP(), modem.getUp(),modem.getDown(),modem.isOnline(),modem.getGeneracion(),clientes.get(i),modem.getServidorAsociado()).getVelocidad();
      int dispositivos=modem.verificarDispositivos(Dispositivo.getDispositivosTotales(), modem).size();
      
      if(dispositivos!=0){
        distanciaOptima = (int)(modem.getServidorAsociado().getFLUJO_RED_NETO()+(velocidad/dispositivos)-(listaIntensidadesClientes.get(i)/modem.getServidorAsociado().getPORCENTAJE_EFICIENCIA()));
      }else{
        distanciaOptima = (int)(modem.getServidorAsociado().getFLUJO_RED_NETO()-(listaIntensidadesClientes.get(i)/modem.getServidorAsociado().getPORCENTAJE_EFICIENCIA()));
      }
      
      distancias.add(distanciaOptima);
    }
    return distancias;
  }

  //METODO ESTATICO--FUNCIONALIDAD REPORTE--INSTANCIA UN OBJETO SERVIDOR ALEATORIO QUE SIRVE PARA INVOCAR EL MÉTODO VERIFICARADMIN DE ESTA CLASE
  public static Servidor adminServidor(){
    return new Servidor();
  }

  //GETTERS Y SETTERS

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

  public int getFLUJO_RED_NETO() {
    return FLUJO_RED_NETO;
  }

}