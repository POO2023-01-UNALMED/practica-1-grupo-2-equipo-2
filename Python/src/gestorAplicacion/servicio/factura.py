import sys

sys.path.append("src/uiMain/excepciones")

from mes import Mes
from exceptionC1 import ErrorPagoTotal

class Factura:

  # //CLASE QUE CONTIENE LA INFORMACION DE LOS PAGOS QUE DEBE REALIZAR UN CLIENTE
  
  # CONSTRUCTOR
  def __init__(self, usuario=None, ip="", pagosAtrasados=0, precio=0, mesActivacion=None):

    # ATRIBUTOS
    self._usuario=usuario
    self._ip=ip
    self._pagosAtrasados=pagosAtrasados
    self._precio=precio*pagosAtrasados
    self._mesActivacion=mesActivacion

  #METODOS
  # //METODO INSTANCIA---UTILIZADO PRINCIPALMENTE EN FUNCIONALIDAD VISUALIZAR DISPOSITIVOS
  def verificarFactura(self,factura):
    if factura.getPagosAtrasados()>2:
      return False
    else:
      return True
  
  # //METODO INSTANCIA--FUNCIONALIDAD VISUALIZAR DISPOSITIVOS
  def accionesPagos(self, opcElegida, abonoUsuario):
    if opcElegida==1:
      abonoUsuario = int(abonoUsuario)
      precioPlan=self._usuario.getPlan()[2]
      if abonoUsuario<self._precio:
        self._precio=self._precio-(abonoUsuario/precioPlan)*precioPlan
        self._pagosAtrasados= int(self._pagosAtrasados-abonoUsuario/precioPlan)

        if self._pagosAtrasados <=2:
          return self._usuario.getModem()
        else:
          return None
      else:
        return None
    elif opcElegida==2:
      abonoUsuario = int(abonoUsuario)
      if abonoUsuario==self._precio:
        self._precio=0
        self._pagosAtrasados=0
        return self._usuario.getModem()
      else:
        raise ErrorPagoTotal()
    return None 

  # //METODO INSTANCIA--FUNCIONALIDAD ADQUISICION DE UN PLAN--ACABA DE CONFIGURAR LA NUEVA FACTURA DEL CLIENTE
  def generarFactura(self, cliente, proveedor):
    precioplan=0
    plan=cliente.getNombrePlan()

    if plan=="BASIC":
      precioplan=proveedor.getPlanes().getBASIC()[2]
    elif plan=="STANDARD":
      precioplan=proveedor.getPlanes().getSTANDARD()[2]
    elif plan=="PREMIUM":
      precioplan=proveedor.getPlanes().getPREMIUM()[2]
      
    self._ip=cliente.getModem().getIP()  
    self._pagosAtrasados=0
    self._precio= precioplan
    self._mesActivacion= Mes.JUNIO.value
    cliente.setFactura(self) 

    return self

  # //METODO TOSTRING
  def __str__(self):
    return f"Factura \nUsuario: {self._usuario.getNombre()} \nCédula: {self._usuario.getID()} \nIP: {self._ip} \nMes de Activación: {self._mesActivacion} \nNúmero de pagos atrasados: {self._pagosAtrasados} \nPrecio total a pagar: {self._precio} "

  #SETTERS Y GETTERS
  def getUsuario(self):
    return self._usuario
    
  def setUsuario(self, usuario):
    self._usuario=usuario
    
  def getIp(self):
    return self._ip
  
  def setIp(self, ip):
    self._ip=ip

  def getPagosAtrasados(self):
    return self._pagosAtrasados
  
  def setPagosAtrasados(self, pagosAtrasados):
    self._pagosAtrasados=pagosAtrasados

  def getPrecio(self):
    return self._precio
  
  def setPrecio(self,precio):
    self._precio=precio

  def getMesActivacion(self):
    return self._mesActivacion
  
  def setMesActivacion(self,mesActivacion):
    self._mesActivacion=mesActivacion