package gestorAplicacion.enrutadorHFC;

import java.util.ArrayList;
import gestorAplicacion.host.Cliente;

public abstract class Cobertura{
  
  protected int generacion;
  protected int intensidadFlujo;

  protected Cobertura(int g){
    generacion=g; //inicializar
  } 

  //Metodos
  public abstract Antena rastrearGeneracionCompatible(ArrayList<Antena> antenasSede, Router r);

  public abstract ArrayList<Object> intensidadFlujoOptima(ArrayList<Servidor> ss,Cliente c);
  
  public abstract ArrayList<Integer> intensidadFlujoClientes(ArrayList<Cliente> ctes, Servidor servidor, boolean Reales);

  @Override
  public String toString(){
    return "";
  }

  //Getters and setters

  public int getGeneracion(){
    return generacion;
  }

  public void setGeneracion(int generacion) {
    this.generacion = generacion;
  }

  public int getIntensidadFlujo() {
    return intensidadFlujo;
  }

  public void setIntensidadFlujo(int intensidadFlujo) {
    this.intensidadFlujo = intensidadFlujo;
  }
 
}