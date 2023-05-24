package gestorAplicacion.servicio;

import java.io.Serializable;
import java.util.*;
import gestorAplicacion.enrutadorHFC.Router;
import gestorAplicacion.host.Cliente;
import gestorAplicacion.host.ProveedorInternet;

public class Factura implements Serializable {

	//CLASE QUE CONTIENE LA INFORMACION DE LOS PAGOS QUE DEBE REALIZAR UN CLIENTE

	//ATRIBUTOS
	private Cliente usuario;
	private String ip;
	private int pagosAtrasados;
	private int precio;
    private Mes mesActivacion;

	//CONSTRUCTORES
	public Factura(Cliente usuario, String ip,int pagosAtrasados, int precio, Mes mesActivacion){ 
	    this.usuario=usuario;
	    this.ip=ip;
	    this.pagosAtrasados=pagosAtrasados;
	    this.precio=precio*pagosAtrasados;
	    this.mesActivacion = mesActivacion; 
	}

	public Factura(Cliente usuario){
	    this.usuario=usuario;
	}


	//METODOS
	  
	//METODO INSTANCIA---UTILIZADO PRINCIPALMENTE EN FUNCIONALIDAD VISUALIZAR DISPOSITIVOS
	public boolean verificarFactura(Factura factura) {
	    if(factura.getPagosAtrasados()>2){
	      return false;
	    }else{
	      return true;
	    }
	}

	//METODO INSTANCIA--FUNCIONALIDAD VISUALIZAR DISPOSITIVOS

	public Router accionesPagos(int opcElegida){
	    if (opcElegida == 1){ //OPCION ABONAR
	      Scanner entrada = new Scanner(System.in);
	      int abonoUsuario = entrada.nextInt();
	      int precioPlan=usuario.getPlan().get(2);
	    
		  //SI EFECTIVAMENTE ABONA(DA MENOS QUE LO QUE DEBE)
	      if (abonoUsuario < precio){
	        this.precio=this.precio-(int)((abonoUsuario/precioPlan))*precioPlan;
	        this.pagosAtrasados=this.pagosAtrasados-(int)((abonoUsuario/precioPlan));

	        if (this.pagosAtrasados<=2){
	          return usuario.getModem();
	        }else{
            return null;
          	}

	      }else{
	        return null;
	      }

	    }else if(opcElegida==2){ //OPCION PAGAR TODO LO QUE SE DEBE
	      Scanner entrada = new Scanner(System.in);
	      int precioUsuario=entrada.nextInt();
	      if (precioUsuario==precio){ 
	        this.precio=0;
	        this.pagosAtrasados=0;
	        return usuario.getModem();
	      }else{
	        return null;
	      }   
	    }
	   return null;
	}
	  
	//METODO INSTANCIA--FUNCIONALIDAD ADQUISICION DE UN PLAN--ACABA DE CONFIGURAR LA NUEVA FACTURA DEL CLIENTE
	public Factura generarFactura(Cliente cliente,ProveedorInternet proveedor){

	    int precioplan=0;
	    String plan=cliente.getNombrePlan();

	    if (plan.equals("BASIC")){
	      precioplan=proveedor.getPlanes().getBASIC().get(2);
	      
	    }else if(plan.equals("STANDARD")){
	      precioplan=proveedor.getPlanes().getSTANDARD().get(2); 
	    
	    }else if(plan.equals("PREMIUM")){
	      precioplan=proveedor.getPlanes().getPREMIUM().get(2); 
	    
	    }

	    ip=cliente.getModem().getIP();  
	    pagosAtrasados=0;
		precio= precioplan;
      	mesActivacion=Mes.MAYO;
	    cliente.setFactura(this);
	    return this;  
	}

	//METODO TOSTRING
	public String toString(){
		return "Factura" + "\nUsuario: " + usuario.getNombre() + "\nCédula: " + usuario.getID() + "\nIP: "+ ip + "\nMes de Activacion: " + mesActivacion + "\nNúmero de pagos atrasados: " + pagosAtrasados  + "\nPrecio total a pagar: " + precio;
	}

	//SETTERS Y GETTERS
	
	public Cliente getUsuario() {
		return usuario;
	}

	public void setUsuario(Cliente usuario) {
		this.usuario = usuario;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPagosAtrasados() {
		return pagosAtrasados;
	}

	public void setPagosAtrasados(int pagosAtrasados) {
		this.pagosAtrasados = pagosAtrasados;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public Mes getMesActivacion() {
		return mesActivacion;
	}

	public void setMesActivacion(Mes mesActivacion) {
		this.mesActivacion = mesActivacion;
	}
	
}