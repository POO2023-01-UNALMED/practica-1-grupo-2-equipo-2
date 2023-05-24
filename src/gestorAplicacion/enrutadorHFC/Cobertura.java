package gestorAplicacion.enrutadorHFC;

import java.io.Serializable;
import java.util.ArrayList;
import gestorAplicacion.host.Cliente;

public abstract class Cobertura implements Serializable{
  
  //ATRIBUTOS
  protected int generacion;
  protected int intensidadFlujo;

  //CONSTRUCTOR
  protected Cobertura(int g){
    generacion=g; 
  } 

  //METODOS 

  //METODOS ABSTRACTOS

  //METODO UTILIZADO PRINCIPALMENTE POR LA CLASE ANTENA--FUNCIONALIDAD DEL TEST
  public abstract Antena rastrearGeneracionCompatible(ArrayList<Antena> antenasSede, Router r);

  //METODO UTILIZADO PRINCIPALMENTE POR LA CLASE ROUTER--FUNCIONALIDAD MEJORA TU PLAN
  public abstract ArrayList<Object> intensidadFlujoOptima(ArrayList<Servidor> ss,Cliente c);
  
  //METODO UTILIZADO PRINCIPALMENTE POR LA CLASE ROUTER--FUNCIONALIDAD REPORTE
  public abstract ArrayList<Integer> intensidadFlujoClientes(ArrayList<Cliente> ctes, Servidor servidor, boolean Reales);

  //METODO TOSTRING
  @Override
  public String toString(){
    return "";
  }

  //GETTERS Y SETTERS

  public int getGeneracion() {
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
