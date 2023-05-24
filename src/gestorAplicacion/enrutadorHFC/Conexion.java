package gestorAplicacion.enrutadorHFC;

import gestorAplicacion.host.Cliente;

public class Conexion extends Router{

	//ESTA CLASE ES FUNDAMENTAL EN LAS FUNCIONALIDADES DEL TEST Y REPORTE PARA EL CALCULO DE LAS INTENSIDADES DE FLUJO---DE ELLA SE OBTIENEN LAS VELOCIDADES EN "TIEMPO REAL"

	//ATRIBUTOS
	private Cliente cliente;

	//CONSTRUCTORES
	public Conexion(String ip, int up, int down, boolean online, int generacion, Cliente cliente, Servidor servidor) {
		super(up, down, online, servidor);
	    this.cliente=cliente;
	}
  
	// SETTERS Y GETTERS

	//METODO HEREDADO DE ROUTER SOBREESCRITO CON CUAL SE APLICA LIGADURA DINÁMICA
	public int getVelocidad() {
    	int velocidadActual = ((cliente.getModem().actualizarVelocidad(cliente).get(0) + cliente.getModem().actualizarVelocidad(cliente).get(1)) / 2)*generacion*generacion;
		return velocidadActual;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}