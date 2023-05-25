package gestorAplicacion.enrutadorHFC;

import gestorAplicacion.host.Cliente;

import java.io.Serializable;
import java.util.ArrayList;

public class Dispositivo implements Serializable{

	//CLASE IMPORTANTE PARA LA FUNCIONALIDAD DE VISUALIZAR DISPOSITIVOS, SIN EMBARGO TAMBIÉN TIENE IMPLICACIONES EN OTRAS EN PROCESOS SECUNDARIOS (TEST--REPORTE--MEJORA TU PLAN)

	//ATRIBUTOS
	private Router modem;
	private String ipAsociada;
	private String nombre;
  	private String generacion;
	private static ArrayList<Dispositivo> dispositivosTotales = new ArrayList<>();
  

	//CONSTRUCTORS
	public Dispositivo(Router modem,String nombre,String generacion) {
	    this.modem = modem;
	    this.nombre = nombre;
	    ipAsociada=this.modem.getIP();
      	this.generacion=generacion;
      	dispositivosTotales.add(this);
	}

    //MÉTODOS

    //METODO DE ESTATICO---FUNCIONALIDAD TEST
	public static String desconectarDispositivo(Cliente cliente,Dispositivo dispositivo) {
	    if(cliente.getModem().verificarDispositivos(dispositivosTotales,cliente.getModem()).contains(dispositivo)){
	      dispositivo.setIpAsociada("");
	      return "Dispositivo desconectado correctamente.";
	    }else{
	      return "Dispositivo inválido, intente de nuevo.";
	    } 
	}

	//METODO TOSTRING
	public String toString() {
		return "Dispositivo: "+ this.nombre + "\nDirección IP: "+ this.ipAsociada+"\nGeneración: "+this.generacion;
	}


	//GETTERS Y SETTERS
	public Router getModem() {
		return modem;
	}

	public void setModem(Router modem) {
		this.modem = modem;
	}

	public String getIpAsociada() {
		return ipAsociada;
	}

	public void setIpAsociada(String ipAsociada) {
		this.ipAsociada = ipAsociada;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getGeneracion() {
		return generacion;
	}

	public void setGeneracion(String generacion) {
		this.generacion = generacion;
	}

	public static ArrayList<Dispositivo> getDispositivosTotales() {
		return dispositivosTotales;
	}

	public static void setDispositivosTotales(ArrayList<Dispositivo> dispositivosTotales) {
		Dispositivo.dispositivosTotales = dispositivosTotales;
	}
}
