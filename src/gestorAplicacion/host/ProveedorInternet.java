package gestorAplicacion.host;

import java.util.*;
import gestorAplicacion.enrutadorHFC.Servidor;
import gestorAplicacion.servicio.Plan;

public class ProveedorInternet{
  
  // Atributos
  private Plan planes;
  private String nombre;
  private ArrayList<Cliente> clientes=new ArrayList<>();
  private final int CLIENTES_MAX;
  private final int CLIENTES_BASIC;
  private final int CLIENTES_STANDARD;
  private final int CLIENTES_PREMIUM;
  //private ArrayList<Antena> antenas;
  
  // Constructor de Proveedor
  
  public ProveedorInternet(String nombre, int clientes_max, ArrayList<Integer> b, ArrayList<Integer> s, ArrayList<Integer> p,int cmb,int cms, int cmp) {
    this(clientes_max,cmb,cms,cmp);
    this.nombre = nombre;
    this.planes = new Plan(b, s, p);
    // this.antenas=antenas;
  }

  public ProveedorInternet(int clientes_max, int clientes_basic, int clientes_standard, int clientes_premium){
    this.CLIENTES_MAX=clientes_max;
    this.CLIENTES_BASIC=clientes_basic;
    this.CLIENTES_STANDARD=clientes_standard;
    this.CLIENTES_PREMIUM=clientes_premium;
  }

  //Método verificarCliente()---Funcionalidad Visualizar dispositivos--Funcionalidad Juego()
  public Cliente verificarCliente(long id){

    for (Cliente cliente : clientes) {
      if (cliente.getId() == id) {
         if (cliente.getNombrePlan().equals("PREMIUM")){
           return cliente;
         }else if(cliente.getNombrePlan().equals("STANDARD")){
           return cliente;
         }else{
          return null;
         }
      }
    }
   return null;
  }

  //Método verificarCliente()--Sobrecargado--Funcionalidad Test()
  public Cliente verificarCliente(String nombre,long id){
    for (Cliente cliente : clientes) {
      if ((cliente.getId() == id) && (cliente.getNombre().equals(nombre))){
          return cliente;
      }
    }
   return null;
  }

  // Método PlanesDisponibles --- Funcionalidad Adquisición de Plan()
  public ArrayList<String> planesDisponibles(Cliente cliente) {
    ArrayList<Cliente> clientesBasic = cliente.buscarCliente(this.clientes, "BASIC");
    ArrayList<Cliente> clientesStandar = cliente.buscarCliente(this.clientes, "STANDARD");
    ArrayList<Cliente> clientesPremium = cliente.buscarCliente(this.clientes, "PREMIUM");
    ArrayList<String> planesD = new ArrayList<String>();
    if ((clientesBasic.size() < this.CLIENTES_BASIC)) {
      planesD.add("BASIC");
    }
    if (clientesStandar.size() < this.CLIENTES_STANDARD) {
      planesD.add("STANDARD");
    }
    if (clientesPremium.size() < this.CLIENTES_PREMIUM) {
      planesD.add("PREMIUM");
    }
    return planesD;
  }

  //Método que permite crear un cliente nuevo---Funcionalidad Adquisición Plan
  public Cliente crearClienteNuevo(String nombre,long id){
    Cliente cliente= new Cliente(nombre,id, this);
    return cliente;
  }

  //Método para buscar proveedores que estan en una sede--Funcionalidad Adquisicion Plan
  public static ArrayList<ProveedorInternet> proveedorSede(String sede){
    ArrayList<ProveedorInternet> proveedores = new ArrayList<>();
    for(Servidor servidor: Servidor.getServidoresTotales()){
      if(servidor.getSede().equals(sede)){
        proveedores.add(servidor.getProveedor());        
      }
    }
    return proveedores;
  }
  

  // Setters y Getters
  public Plan getPlanes() {
    return planes;
  }

  public void setPlanes(Plan planes) {
    this.planes = planes;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public ArrayList<Cliente> getClientes() {
    return clientes;
  }

  public void setClientes(ArrayList<Cliente> clientes) {
    this.clientes = clientes;
  }

  public int getCLIENTES_MAX() {
    return CLIENTES_MAX;
  }

  public int getCLIENTES_BASIC() {
    return CLIENTES_BASIC;
  }

  public int getCLIENTES_STANDARD() {
    return CLIENTES_STANDARD;
  }

  public int getCLIENTES_PREMIUM() {
    return CLIENTES_PREMIUM;
  }



}