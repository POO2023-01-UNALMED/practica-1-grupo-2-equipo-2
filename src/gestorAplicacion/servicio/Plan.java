package gestorAplicacion.servicio;

import java.io.Serializable;
import java.util.*;

public class Plan implements Serializable {
  
  //ESTA CLASE CONTIENE LA INFORMACION DE LOS PLANES DE LOS PROVEEDORES

  private final ArrayList<Integer> BASIC;  //CONTIENE MEGAS SUBIDA, MEGAS BAJADA, PRECIO DEL PLAN
  private final ArrayList<Integer> STANDARD; //CONTIENE MEGAS SUBIDA, MEGAS BAJADA, PRECIO DEL PLAN
  private final ArrayList<Integer> PREMIUM; //CONTIENE MEGAS SUBIDA, MEGAS BAJADA, PRECIO DEL PLAN

  //CONSTRUCTOR
  public Plan(ArrayList<Integer> BASIC, ArrayList<Integer> STANDARD, ArrayList<Integer> PREMIUM) {
    this.BASIC = BASIC;
    this.STANDARD = STANDARD;
    this.PREMIUM = PREMIUM;
  }

  //SETTERS Y GETTERS

  public ArrayList<Integer> getBASIC() {
    return BASIC;
  }

  public ArrayList<Integer> getSTANDARD() {
    return STANDARD;
  }

  public ArrayList<Integer> getPREMIUM() {
    return PREMIUM;
  }

}