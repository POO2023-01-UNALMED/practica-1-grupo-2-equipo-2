package gestorAplicacion.host;

import java.io.Serializable;
import java.util.*;
import gestorAplicacion.enrutadorHFC.Router;
import gestorAplicacion.enrutadorHFC.Servidor;
import gestorAplicacion.servicio.Factura;
import gestorAplicacion.servicio.Plano;
import gestorAplicacion.enrutadorHFC.Antena;

public class Cliente implements Serializable{

  //CLASE PRINCIPAL YA QUE CASI TODAS LAS FUNCIONALIDADES ESTÁN ORIENTADAS A LOS CLIENTES

  //ATRIBUTOS
  private String nombre;
  private final long ID;
  private Router modem;
  private ProveedorInternet proveedor;
  private ArrayList<Integer> plan;
  private String nombrePlan;
  private Factura factura;
  

  //CONSTRUCTORES
  public Cliente(String nombre, long id, Router modem, ProveedorInternet proveedor, ArrayList<Integer> plan, String nombrePlan, Factura factura) {
    this.ID= id;
    this.nombre=nombre;
    this.modem = modem;
    this.proveedor = proveedor;
    this.plan = plan;
    this.nombrePlan = nombrePlan;
    this.factura = factura;
    proveedor.getClientes().add(this);
  }

  public Cliente(String nombre,long id, Router modem, ProveedorInternet proveedor, ArrayList<Integer> plan, String nombrePlan) { 

    this.ID= id;
    this.nombre=nombre;
    this.modem = modem;
    this.proveedor = proveedor;
    this.plan = plan;
    this.nombrePlan = nombrePlan;
    proveedor.getClientes().add(this);
  }
  
  public Cliente(String nombre, long id,ProveedorInternet proveedor) {
    this.nombre = nombre;
    ID = id;
    this.proveedor = proveedor;
    proveedor.getClientes().add(this);
  }

  
  //METODOS

  //METODO INSTANCIA--FUNCIONALIDAD ADQUISICION DE PLAN---RETORNA UN ARREGLO CON CLIENTES QUE PERTENECEN A UN PLAN ESPECÍFICO
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

  //METODO ESTÁTICO---FUNCIONALIDAD REPORTE---ETORNA UN ARREGLO CON CLIENTES QUE PERTENECEN A UN MISMO PROVEEDOR, SEDE Y PLAN
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

  //METODO INSTANCIA---FUNCIONALIDAD ADQUISICION DE PLAN---REALIZA TODOS LOS CAMBIOS NECESARIOS PARA REGISTRAR A UN CLIENTE NUEVO, BUSCA UN SERVIDOR Y UNA ANTENA DEL PROVEEDOR INDICADO PARA ASOCIARLOS AL NUEVO ROUTER 
  public Factura configurarPlan(String plan, Cliente cliente, String sede, int coordenadaX, int coordenadaY) {

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
      Plano planoCliente = new Plano(coordenadaX,coordenadaY);
      planoCliente.setSede(servidorAsociado.getCoordenadas().getSede());
      routercliente.setCoordenadas(planoCliente);

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
      Plano planoCliente = new Plano(coordenadaX,coordenadaY);
      planoCliente.setSede(servidorAsociado.getCoordenadas().getSede());
      routercliente.setCoordenadas(planoCliente);

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
      Router routercliente = new Router(cliente.getProveedor().getPlanes().getPREMIUM().get(0),cliente.getProveedor().getPlanes().getPREMIUM().get(1),true,servidorAsociado);
      cliente.setModem(routercliente);
      routercliente.setSede(sede);
      Plano planoCliente = new Plano(coordenadaX,coordenadaY);
      planoCliente.setSede(servidorAsociado.getCoordenadas().getSede());
      routercliente.setCoordenadas(planoCliente);
      
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

  //SETTERS Y GETTERS
  
  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public long getID() {
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