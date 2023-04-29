package gestorAplicacion.enrutadorHFC;

import gestorAplicacion.host.Cliente;

public class Conexion extends Router{

	// Atributos
	  private int velocidad;
	  private Cliente cliente;

	  // Contructores
	  public Conexion(String ip, int up, int down, boolean online, int generacion, Cliente cliente, Servidor servidor) {
	    super(up, down, online, servidor);
	    velocidad = ((cliente.getModem().actualizarVelocidad(cliente).get(0) + cliente.getModem().actualizarVelocidad(cliente).get(1)) / 2)*generacion*generacion;
	    this.cliente=cliente;
	  }
  
	  // Setters y getter
  
	public int getVelocidad() {
    return velocidad;
	}

  public void setVelocidad(int velocidad) {
    this.velocidad = velocidad;
  }  
  
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}