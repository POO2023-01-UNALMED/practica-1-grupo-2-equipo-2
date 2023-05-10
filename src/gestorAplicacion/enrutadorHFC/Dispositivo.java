package gestorAplicacion.enrutadorHFC;
import java.util.*;

import gestorAplicacion.host.Cliente;

public class Dispositivo{

	// Atributos
	private Router modem;
	private String ipAsociada;
	private String nombre;
  private String generacion;
	private static ArrayList<Dispositivo> dispositivosTotales = new ArrayList<>();
  

	  // Constructores
	  public Dispositivo(Router modem,String nombre,String generacion) {
	    this.modem = modem;
	    this.nombre = nombre;
	    this.ipAsociada=this.modem.getIP();
      this.generacion=generacion;
      Dispositivo.dispositivosTotales.add(this);
	  }

    //MÉTODOS

    //Funcionalidad Test()
	  public String desconectarDispositivo(Cliente cliente,Dispositivo dispositivo) {
	    if(cliente.getModem().verificarDispositivos(dispositivosTotales,cliente.getModem()).contains(dispositivo)){
	      dispositivo.setIpAsociada("");
	      return "Desconectado correctamente.";

	      
	    }else{
	      return "Dispositivo inválido, intente de nuevo.";
	    } 
	  }

    //Funcionalidad Test()
	  public String conectarDispositivo(Cliente cliente,Dispositivo dispositivo){
	    ArrayList<Dispositivo> d_conectados= cliente.getModem().verificarDispositivos(dispositivosTotales, cliente.getModem());
	    if(d_conectados.contains(dispositivo)){

	      return "El dispositivo ya está conectado.";
	    }else{
	      
	      dispositivo.setIpAsociada(cliente.getModem().getIP());
	      return "Dispositivo conectado.";
	    }  
	  }

  public String toString() {
    return "Dispositivo: "+this.nombre+"\nDirección IP: "+this.ipAsociada+"\nGeneración: "+this.generacion;
  }
  // Setters y Getters
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