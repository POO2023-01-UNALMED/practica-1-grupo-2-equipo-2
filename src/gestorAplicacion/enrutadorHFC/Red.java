package gestorAplicacion.enrutadorHFC;

import java.util.ArrayList;
import gestorAplicacion.host.Cliente;
import gestorAplicacion.host.ProveedorInternet;

interface Red{
  
  static final int flujoDeRedPreeliminar=500;

  ArrayList<Servidor> verificarAdmin(ArrayList<ProveedorInternet> proveedores, String nombre);

  ArrayList<Integer> distanciasOptimas(ArrayList<Cliente> clientes, ArrayList<Integer> listaIntensiRealesClientes, ArrayList<Integer> listaIntensidadesClientes);

}