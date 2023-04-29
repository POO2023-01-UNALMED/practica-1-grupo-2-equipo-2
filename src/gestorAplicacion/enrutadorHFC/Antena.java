package gestorAplicacion.enrutadorHFC;

import java.util.*;
import java.util.stream.*;
import gestorAplicacion.host.Cliente;
import gestorAplicacion.host.ProveedorInternet;
import gestorAplicacion.servicio.Plano;
import java.awt.geom.*;

public class Antena extends Cobertura{
    private int identificador;
    private Point2D coordenadas;
    private String sede;
    private Ellipse2D zonaCobertura;
    private ProveedorInternet proveedor;
    private static ArrayList<Antena> antenasTotales = new ArrayList<>(); 
  
    public Antena(int id,Plano coordenadas,String sede, int generacion, int radio, ProveedorInternet proveedor){
      super(generacion);
      this.identificador = id;
      this.coordenadas=new Point2D.Double(coordenadas.getX(),coordenadas.getY());
      this.sede=sede;
      this.proveedor=proveedor;
      zonaCobertura=(Ellipse2D)coordenadas.crearZonaCobertura(radio,coordenadas.getX(),coordenadas.getY());
      antenasTotales.add(this);     
    }

  //METODOS
  
  public static ArrayList<Antena> antenasSede(String sede){
    ArrayList<Antena> antenas = new ArrayList<>();
    for(Antena antena: antenasTotales){
      if(antena.getSede().equals(sede)){
        antenas.add(antena);
      }
    }
    return antenas;
  }

  @Override
  public Antena rastrearGeneracionCompatible(ArrayList<Antena> antenasSede, Router r){//generacion 
      
     List<Antena> antenasCercanas=antenasSede.stream().filter(antenas -> antenas.verificarZonaCobertura(r)).collect(Collectors.toList());
    
    ArrayList<Antena> antenaRecomendada=new ArrayList<Antena>();

      Antena antenaEncontrada=antenasCercanas.stream().filter(antenas->antenas.getGeneracion()==r.getGeneracion()).findFirst().orElse(null);

    if(antenaRecomendada!=null){
      antenaRecomendada.add(antenaEncontrada);
      return antenaRecomendada.get(0);
    }
    else{
      return null;
      
    }
  }
  
  public boolean verificarZonaCobertura(Router r){
      int rx = r.getCoordenadas().getX();
      int ry = r.getCoordenadas().getY();
      int radioAntena= (int)(((Ellipse2D.Double)r.getAntenaAsociada().getZonaCobertura()).getWidth())/2;
      boolean  ec=rx*rx+ry*ry<=radioAntena;
      return ec;
    }

  @Override
  public ArrayList<Integer> intensidadFlujoClientes(ArrayList<Cliente> ctes, Servidor servidor, boolean Reales){
    return null; 
  }

  @Override
  public ArrayList<Object> intensidadFlujoOptima(ArrayList<Servidor> ss,Cliente c){
  return null;
  }
  
 
  public String toString(){
   return "Identificador: " + identificador + "\nSede: " + sede + " Ubicación: " + "(" + coordenadas.getX() + "," + coordenadas.getY() + ")" + " Generación: " + generacion;
  }

  //Getters and setters
    
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

    public static ArrayList<Antena> getAntenasTotales() {
      return antenasTotales;
    }

    public static void setAntenasTotales(ArrayList<Antena> antenasTotales) {
      Antena.antenasTotales = antenasTotales;
    }
  
  public ProveedorInternet getProveedor() {
      return proveedor;
  }

  public void setProveedor(ProveedorInternet proveedor) {
      this.proveedor = proveedor;
  }
}