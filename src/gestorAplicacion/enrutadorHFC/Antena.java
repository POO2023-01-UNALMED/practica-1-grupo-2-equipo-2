package gestorAplicacion.enrutadorHFC;

import java.util.*;
import java.util.stream.*;
import gestorAplicacion.host.Cliente;
import gestorAplicacion.host.ProveedorInternet;
import gestorAplicacion.servicio.Plano;
import java.awt.geom.*;

public class Antena extends Cobertura{

  //ESTA CLASE ES USADA PRINCIPALMENTE EN LA FUNCIONALIDAD DEL TEST, DONDE SE LE RECOMIENDA AL CLIENTE CAMBIARSE DE ANTENA

  //ATRIBUTOS
  private int identificador;
  private Point2D coordenadas;
  private String sede;
  private Ellipse2D zonaCobertura;
  private ProveedorInternet proveedor;
  private static ArrayList<Antena> antenasTotales = new ArrayList<>(); 
  

  //CONSTRUCTORES
  public Antena(int identificador, Plano coordenadas,String sede, int generacion, int radio, ProveedorInternet proveedor){
    super(generacion);
    this.identificador = identificador;
    this.coordenadas=new Point2D.Double(coordenadas.getX(),coordenadas.getY());
    this.sede=sede;
    this.proveedor=proveedor;
    zonaCobertura=(Ellipse2D)coordenadas.crearZonaCobertura(radio*2,coordenadas.getX(),coordenadas.getY());  
    antenasTotales.add(this);     
  }

  //METODOS

  //METODO INSTANCIA---BUSCA LAS ANTENAS QUE PERTENECEN A UNA SEDE EN ESPECIFICO-FUNCIONALIDAD TEST
  public static ArrayList<Antena> antenasSede(String sede){
    ArrayList<Antena> antenas = new ArrayList<>();
    for(Antena antena: antenasTotales){
      if(antena.getSede().equals(sede)){
        antenas.add(antena);
      }
    }
    return antenas;
  }

  //METODO INSTANCIA---BUSCA LAS ANTENAS QUE ESTAN EN UNA SEDE Y QUE PERTENECEN A UN PROVEEDOR ESPECIFICO--FUNCIONALIDAD MEJORA TU PLAN (PARA SETEAR UNA ANTENA DEL PROVEEDOR NUEVO CUANDO SE REALICE EL CAMBIO)
  public static Antena antenasSede(String sede, String proveedor){
    for(Antena antena: antenasTotales){
      if(antena.getSede().equals(sede)){
        if(antena.getProveedor().getNombre().equals(proveedor)){
          return antena;
        }
        
      }
    }
    return null;
  }

  //METODO INSTANCIA---IMPLEMENTACION Y SOBREESCRITURA METODO ABSTRACTO QUE SE HEREDA DE COBERTURA-FUNCIONALIDAD TEST--BUSCA UNA ANTENA EN LA QUE EL CLIENTE SE ENCUENTRE DENTRO DE SU ZONA DE COBERTURA Y LA GENERACION DE ESTA Y EL ROUTER SEAN LAS MISMAS
  @Override
  public Antena rastrearGeneracionCompatible(ArrayList<Antena> antenasSede, Router r){
      
     List<Antena> antenasCercanas=antenasSede.stream().filter(antenas -> antenas.verificarZonaCobertura(r,antenas)).collect(Collectors.toList());
    ArrayList<Antena> antenaRecomendada=new ArrayList<Antena>();

      Antena antenaEncontrada=antenasCercanas.stream().filter(antenas->antenas.getGeneracion()==r.getGeneracion()).findFirst().orElse(null);

    if(antenaRecomendada!=null){
      antenaRecomendada.add(antenaEncontrada);
      return antenaRecomendada.get(0);
    }else{
      return null;
    }
  }
    
  
  //METODO INSTANCIA---CALCULA SI LA UBICACION DEL ROUTER (COORDENADAS) SE ENCUENTRA DENTRO DE LA ZONA DE COBERTURA DE LA ANTENA--FUNCIONALIDAD TEST
  public boolean verificarZonaCobertura(Router r,Antena a){
      int rx = r.getCoordenadas().getX();
      int ry = r.getCoordenadas().getY();
      int radioAntena= (int)(((Ellipse2D.Double)a.getZonaCobertura()).getWidth())/2;
      boolean  d=((int)Math.sqrt(Math.pow(rx - a.getCoordenadas().getX(), 2)+ Math.pow(ry - a.getCoordenadas().getY(), 2)))<=radioAntena;
      return d;
  }

  //DEFINICIÓN DEL METODO ABSTRACTO QUE HEREDA DE COBERTURA--EVITAR ERRORES DE COMPILACION
  @Override
  public ArrayList<Integer> intensidadFlujoClientes(ArrayList<Cliente> ctes, Servidor servidor, boolean Reales){
    return null; 
  }

  //DEFINICIÓN DEL METODO ABSTRACTO QUE HEREDA DE COBERTURA--EVITAR ERRORES DE COMPILACION
  @Override
  public ArrayList<Object> intensidadFlujoOptima(ArrayList<Servidor> ss,Cliente c){
    return null;
  }
  
  //SOBREESCRITURA DEL METODO TOSTRING HEREDADO DE COBERTURA
  public String toString(){
   return "Identificador: " + identificador + "\nSede: " + sede + " Ubicación: " + "(" + coordenadas.getX() + "," + coordenadas.getY() + ")" + " Generación: " + generacion;
  }


  //METODO ESTATICO QUE A LA HORA DE DESERIALIZAR SE ENCARGA DE RESETEAR LOS PROVEEDORES PARA QUE NO HAYAN PROBLEMAS POR LAS REFERENCIAS A LOS OBJETOS DESPUÉS DE SERIALIZAR
  public static void antenas(){

    for(Antena antena: Antena.getAntenasTotales()){

      ProveedorInternet provee= antena.getProveedor();

      for(ProveedorInternet prove: ProveedorInternet.getProveedoresTotales()){

        if(provee.getNombre().equals(prove.getNombre())){
          antena.setProveedor(prove);
        }
      }

    }
  }

  //GETTERS Y SETTERS

  public int getIdentificador() {
    return identificador;
  }

  public void setIdentificador(int identificador) {
    this.identificador = identificador;
  }

  public Point2D getCoordenadas() {
    return coordenadas;
  }

  public void setCoordenadas(Point2D coordenadas) {
    this.coordenadas = coordenadas;
  }

  public String getSede() {
    return sede;
  }

  public void setSede(String sede) {
    this.sede = sede;
  }

  public Ellipse2D getZonaCobertura() {
    return zonaCobertura;
  }

  public void setZonaCobertura(Ellipse2D zonaCobertura) {
    this.zonaCobertura = zonaCobertura;
  }

  public ProveedorInternet getProveedor() {
    return proveedor;
  }

  public void setProveedor(ProveedorInternet proveedor) {
    this.proveedor = proveedor;
  }

  public static ArrayList<Antena> getAntenasTotales() {
    return antenasTotales;
  }

  public static void setAntenasTotales(ArrayList<Antena> antenasTotales) {
    Antena.antenasTotales = antenasTotales;
  }

}