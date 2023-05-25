package gestorAplicacion.enrutadorHFC;

import java.util.ArrayList;
import gestorAplicacion.host.Cliente;
import gestorAplicacion.host.ProveedorInternet;

interface Red{
  
  //INTERFACE CREADA PARA QUE LA CLASE SERVIDOR LA IMPLEMENTE

  //CONSTANTES
  static final int FLUJO_RED_PRELIMINAR=500;

  //MÉTODOS
  ArrayList<Servidor> verificarAdmin(ArrayList<ProveedorInternet> proveedores, String nombre);
  ArrayList<Integer> distanciasOptimas(ArrayList<Cliente> clientes,ArrayList<Integer> listaIntensidadesClientes);

}
