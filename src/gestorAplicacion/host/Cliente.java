package gestorAplicacion.host;

import java.util.*;
import gestorAplicacion.enrutadorHFC.Router;
import gestorAplicacion.enrutadorHFC.Servidor;
import gestorAplicacion.servicio.Factura;
import gestorAplicacion.enrutadorHFC.Antena;;

public class Cliente {

  // Atributos
  private String nombre;
  private final long ID;
  private Router modem;
  private ProveedorInternet proveedor;
  private ArrayList<Integer> plan;
  private String nombrePlan;
  private Factura factura;

  // Constructores
  public Cliente(long id, Router modem, ProveedorInternet proveedor, ArrayList<Integer> plan, String nombrePlan, Factura factura) {

    this.ID= id;
    this.modem = modem;
    this.proveedor = proveedor;
    this.plan = plan;
    this.nombrePlan = nombrePlan;
    this.factura = factura;
    proveedor.getClientes().add(this);
  }
  
  //Sobrecarga del constructor para cliente nuevo---Funcionalidad Adquisicion plan()
  public Cliente(String nombre, long id,ProveedorInternet proveedor) {
    this.nombre = nombre;
    ID = id;
    this.proveedor = proveedor;
    proveedor.getClientes().add(this);
  }

  // Métodos
  
  // Método buscarCliente() ---Funcionalidad AdquisicionPlan()
  public ArrayList<Cliente> buscarCliente(ArrayList<Cliente> clientes, String nombrePlan) {
    ArrayList<Cliente> clientesPorPlan = new ArrayList<Cliente>();
    for (Cliente cliente : clientes) {
      if (cliente.getNombrePlan()!=null){
        if (cliente.getNombrePlan().equals(nombrePlan)) {
          clientesPorPlan.add(cliente);
        }
      }
      }
      return clientesPorPlan;
  }

  //Método buscarCliente1()---Funcionalidad Reporte()
  public static ArrayList<Cliente> buscarCliente1(String sede,String nombrePlan,ProveedorInternet proveedor){
    ArrayList<Cliente> clientesPlan = new ArrayList<Cliente>();
    for(Cliente cliente: proveedor.getClientes()){
      if (cliente.getNombrePlan()!=null){
        if ((cliente.getNombrePlan().equals(nombrePlan)) && (cliente.getModem().getSede().equals(sede))){
          clientesPlan.add(cliente);
        }
      }
    }
    return clientesPlan;
  }

  // Método configurarPlan()
  public Factura configurarPlan(String plan, Cliente cliente, String sede) {

    //FALTA VER LO DE LA ANTENA Y LO DE LAS COORDENADAS
    Servidor servidorAsociado=null;
    for(Servidor servidor: Servidor.getServidoresTotales()){
      if(servidor.getProveedor().getNombre().equals(cliente.getProveedor().getNombre())){
        if(servidor.getSede().equals(sede)){
          servidorAsociado=servidor;
        }
      }
    }
    
    if (plan.equals("BASIC")) {
      cliente.setNombrePlan(plan);
      cliente.setPlan(cliente.getProveedor().getPlanes().getBASIC());
      Router routercliente = new Router(cliente.getProveedor().getPlanes().getBASIC().get(0), cliente.getProveedor().getPlanes().getBASIC().get(1),true,servidorAsociado);
      cliente.setModem(routercliente);
      routercliente.setSede(sede);

      for(Antena antena: Antena.getAntenasTotales()){
        if(antena.getSede().equals(sede)){
          if(antena.getProveedor().getNombre().equals(cliente.getProveedor().getNombre())){
            routercliente.setAntenaAsociada(antena);
            break;
          }
        }
      }
     
      
    } else if (plan.equals("STANDARD"))  {
      cliente.setNombrePlan(plan);
      cliente.setPlan(cliente.getProveedor().getPlanes().getSTANDARD());
      Router routercliente = new Router(cliente.getProveedor().getPlanes().getSTANDARD().get(0), cliente.getProveedor().getPlanes().getSTANDARD().get(1),true,servidorAsociado);
      cliente.setModem(routercliente);
      routercliente.setSede(sede);

      for(Antena antena: Antena.getAntenasTotales()){
        if(antena.getSede().equals(sede)){
          if(antena.getProveedor().getNombre().equals(cliente.getProveedor().getNombre())){
            routercliente.setAntenaAsociada(antena);
            break;
          }
        }
      }
      
      
    }else{
      cliente.setNombrePlan(plan);
      cliente.setPlan(cliente.getProveedor().getPlanes().getPREMIUM());
      Router routercliente = new Router(cliente.getProveedor().getPlanes().getPREMIUM().get(0), cliente.getProveedor().getPlanes().getPREMIUM().get(1),true,servidorAsociado);
      cliente.setModem(routercliente);
      routercliente.setSede(sede);
      
      for(Antena antena: Antena.getAntenasTotales()){
        if(antena.getSede().equals(sede)){
          if(antena.getProveedor().getNombre().equals(cliente.getProveedor().getNombre())){
            routercliente.setAntenaAsociada(antena);
            break;
          }
        }
      }
  
    }

    return new Factura(cliente);
  }

  // Getters y Setters
  
  public String getNombre() {
  	return nombre;
  }
  
  public void setNombre(String nombre) {
  	this.nombre = nombre;
  }
  
  public long getId() {
  	return ID;
  }
  
  public Router getModem() {
  	return modem;
  }
  
  public void setModem(Router modem) {
  	this.modem = modem;
  }
  
  public ProveedorInternet getProveedor() {
  	return proveedor;
  }
  
  public void setProveedor(ProveedorInternet proveedor) {
  	this.proveedor = proveedor;
  }
  
  public ArrayList<Integer> getPlan() {
  	return plan;
  }
  
  public void setPlan(ArrayList<Integer> plan) {
  	this.plan = plan;
  }
  
  public String getNombrePlan() {
  	return nombrePlan;
  }
  
  public void setNombrePlan(String nombrePlan) {
  	this.nombrePlan = nombrePlan;
  }
  
  public Factura getFactura() {
  	return factura;
  }
  
  public void setFactura(Factura factura) {
  	this.factura = factura;
  }

}