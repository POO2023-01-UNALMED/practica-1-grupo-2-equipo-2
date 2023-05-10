package gestorAplicacion.servicio;

import java.util.*;
import gestorAplicacion.enrutadorHFC.Router;
import gestorAplicacion.host.Cliente;
import gestorAplicacion.host.ProveedorInternet;

public class Factura {

	 //Atributos
	private Cliente usuario;
	private String IP;
	private int pagosAtrasados;
	private int precio;
    private Mes mesActivacion;

	  //Constructores
	public Factura(Cliente usuario, String ip,int pagosAtrasados, int precio, Mes mesActivacion){ //Mes se tiene que utilizar para aplicar el enum
	    this.usuario=usuario;
	    this.IP=ip;
	    this.pagosAtrasados=pagosAtrasados;
	    this.precio=precio;
	    this.mesActivacion = mesActivacion; 
	}

	  //Sobrecarga para la funcionalidad de AdquisiciónPlan()
	public Factura(Cliente usuario){
	    this.usuario=usuario;
	}


	  //Métodos
	  
	  //Método verificarFactura()---Funcionalidad Visualizar dispositivos conectados
	public boolean verificarFactura(Factura factura) {
	    if(factura.getPagosAtrasados()>2){
	      return false;
	    }else{
	      return true;
	    }
	}

	  //Método accionesPagos()---Funcionalidad Visualizar dispositivos conectados
	public Router accionesPagos(int opcElegida){
	    if (opcElegida == 1){//Opcion abonar a la deuda
	      Scanner entrada = new Scanner(System.in);
	      int abonoUsuario = entrada.nextInt();
	      int precioPlan=usuario.getPlan().get(2);
	    //condicion abono mayor deuda//
	      if (abonoUsuario < precio){
	        this.precio=this.precio-abonoUsuario;
	        this.pagosAtrasados=this.pagosAtrasados-(int)((abonoUsuario/precioPlan));
	        if (this.pagosAtrasados<=2){
	          return usuario.getModem();
	        }else{
            return null;
          }
	      }else{
	        return null;
	      }
	    }else if(opcElegida==2){ //Opción Pagar todo
	      Scanner entrada = new Scanner(System.in);
	      int precioUsuario=entrada.nextInt();
	      if (precioUsuario==precio){ 
	      // Verifica que el precio que ingresa por consola sea el correcto, sino, devuelve null y en el cuerpo de la funcionalidad se ejecuta de nuevo el meotod hasta que meta el precio correcto
	        this.precio=0;
	        this.pagosAtrasados=0;
	        return usuario.getModem();
	      }else{
	        return null;
	      }   
	    }
	   return null;
	}
	  
	  //Método generarFactura()--Funcionalidad AdquisicionPlan()
	  public Factura generarFactura(Cliente cliente,ProveedorInternet proveedor){
	    
	    String plan=cliente.getNombrePlan();

	    if (plan.equals("BASIC")){
	      int precioplan=proveedor.getPlanes().getBASIC().get(2);
	      this.precio= precioplan;
	    }else if(plan.equals("STANDARD")){
	      int precioplan=proveedor.getPlanes().getSTANDARD().get(2); 
	      this.precio= precioplan;
	    }else{
	      int precioplan=proveedor.getPlanes().getPREMIUM().get(2); 
	      this.precio= precioplan;
	    }

	    String ip=cliente.getModem().getIP();
	    this.IP=ip;   
	    this.pagosAtrasados=0;
      this.mesActivacion=Mes.ABRIL;
	    cliente.setFactura(this);
	    return this;  //De aqui que lo lleve al metodo de accionesPagos()--opcion2 
	  }

	  //Método toString()
	  public String toString(){

	    return "Factura" + "\nUsuario: " + usuario.getNombre() + "\nCédula: " + usuario.getId() + "\nIP: "+ IP + "\nMes de Activacion:" + mesActivacion
	    		+ "\nNúmero de pagos atrasados: " + pagosAtrasados  + "\nPrecio total a pagar: " + precio;
	  }


	  //Setters y Getters
	public Cliente getUsuario() {
		return usuario;
	}

	public void setUsuario(Cliente usuario) {
		this.usuario = usuario;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		this.IP = iP;
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
	    
}