package gestorAplicacion.servicio;

import java.util.*;

public class Plan {
    private final ArrayList<Integer> BASIC;  //Contiene velUP,velDown,Cobertura,Precio
    private final ArrayList<Integer> STANDARD; //Contiene velUP,velDown,Cobertura,Precio
    private final ArrayList<Integer> PREMIUM; //Contiene velUP,velDown,Cobertura,Precio

    // Constructor de Plan
    public Plan(ArrayList<Integer> basic, ArrayList<Integer> standard, ArrayList<Integer> premium) {
      this.BASIC = basic;
      this.STANDARD = standard;
      this.PREMIUM = premium;
    }

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