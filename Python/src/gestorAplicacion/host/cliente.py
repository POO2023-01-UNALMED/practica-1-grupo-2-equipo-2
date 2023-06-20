import sys

sys.path.append("src/gestorAplicacion/enrutadorHFC")
sys.path.append("src/gestorAplicacion/servicio")

from antena import Antena
from servidor import Servidor 
from router import Router
from antena import Antena
from factura import Factura
from plano import Plano

class Cliente:
  
  # //CLASE PRINCIPAL YA QUE CASI TODAS LAS FUNCIONALIDADES ESTÁN ORIENTADAS A LOS CLIENTES

  # CONSTRUCTOR

  def __init__(self, nombre=None, ID=0, proveedor=None, modem=None,  plan=[], nombrePlan=None, factura=None):
    
    # ATRIBUTOS
    self._nombre=nombre
    self._ID = ID
    self._modem = modem
    self._proveedor = proveedor
    self._plan = plan
    self._nombrePlan = nombrePlan
    self._factura = factura

    if proveedor!=None: #NO AÑADE CUANDO EL PROVEEDOR ES POR DEFECTO
      proveedor.getClientes().append(self)


  #METODOS

  # //METODO INSTANCIA--FUNCIONALIDAD ADQUISICION DE PLAN---RETORNA UNA LISTA CON CLIENTES QUE PERTENECEN A UN PLAN ESPECÍFICO
  def buscarCliente(self, clientes, nombrePlan):
    clientesPorPlan=[]
    for cliente in clientes:
      if cliente.getNombrePlan()!=None:
        if cliente.getNombrePlan() == nombrePlan:
          clientesPorPlan.append(cliente)
    return clientesPorPlan

  #  //METODO ESTÁTICO---FUNCIONALIDAD REPORTE---RETORNA UNA LISTA  CON CLIENTES QUE PERTENECEN A UN MISMO PROVEEDOR, SEDE Y PLAN
  @classmethod
  def buscarCliente1(cls,sede, nombrePlan, proveedor):
    clientesPlan=[]
    for cliente in proveedor.getClientes():
      if cliente.getNombrePlan()!=None:
        if (cliente.getNombrePlan() == nombrePlan) and (cliente.getModem().getSede() == sede):
          clientesPlan.append(cliente)

    return clientesPlan

  # //METODO INSTANCIA---FUNCIONALIDAD ADQUISICION DE PLAN---REALIZA TODOS LOS CAMBIOS NECESARIOS PARA REGISTRAR A UN CLIENTE NUEVO, BUSCA UN SERVIDOR Y UNA ANTENA DEL PROVEEDOR INDICADO PARA ASOCIARLOS AL NUEVO ROUTER 
  def configurarPlan(self, plan, cliente, proveedor, sede, coordenadaX, coordenadaY):

    servidorAsociado=None

    for servidor in Servidor.getServidoresTotales():
      if servidor.getProveedor().getNombre()==proveedor.getNombre():
        if servidor.getSede()==sede:
          servidorAsociado=servidor

    if plan=="BASIC":
      cliente.setNombrePlan(plan)
      cliente.setPlan(proveedor.getPlanes().getBASIC())
      routercliente = Router(proveedor.getPlanes().getBASIC()[0], proveedor.getPlanes().getBASIC()[1],True,servidorAsociado)
      cliente.setModem(routercliente)
      routercliente.setSede(sede)
      planoCliente = Plano(coordenadaX,coordenadaY)
      planoCliente.setSede(servidorAsociado.getCoordenadas().getSede())
      routercliente.setCoordenadas(planoCliente)

      for antena in Antena.getAntenasTotales():
        if antena.getSede()==sede:
          if antena.getProveedor().getNombre()==proveedor.getNombre():
            routercliente.setAntenaAsociada(antena)
            break  
            
    elif plan=="STANDARD":
      cliente.setNombrePlan(plan)
      cliente.setPlan(proveedor.getPlanes().getSTANDARD())
      routercliente = Router(proveedor.getPlanes().getSTANDARD()[0], proveedor.getPlanes().getSTANDARD()[1],True,servidorAsociado);
      cliente.setModem(routercliente)
      routercliente.setSede(sede)
      planoCliente = Plano(coordenadaX,coordenadaY)
      planoCliente.setSede(servidorAsociado.getCoordenadas().getSede())
      routercliente.setCoordenadas(planoCliente)

      for antena in Antena.getAntenasTotales():
        if antena.getSede()==sede:
          if antena.getProveedor().getNombre()==proveedor.getNombre():
            routercliente.setAntenaAsociada(antena)
            break  
    else:
      cliente.setNombrePlan(plan)
      cliente.setPlan(proveedor.getPlanes().getPREMIUM())
      routercliente = Router(proveedor.getPlanes().getPREMIUM()[0],proveedor.getPlanes().getPREMIUM()[1],True,servidorAsociado);
      cliente.setModem(routercliente)
      routercliente.setSede(sede)
      planoCliente = Plano(coordenadaX,coordenadaY)
      planoCliente.setSede(servidorAsociado.getCoordenadas().getSede())
      routercliente.setCoordenadas(planoCliente)

      for antena in Antena.getAntenasTotales():
        if antena.getSede()==sede:
          if antena.getProveedor().getNombre()==proveedor.getNombre():
            routercliente.setAntenaAsociada(antena)
            break
          
    return Factura(cliente)

  #SETTERS Y GETTERS
  def getNombre(self):
    return self._nombre
  
  def setNombre(self, nombre):
    self._nombre=nombre
    
  def getID(self):
    return self._ID

  def getModem(self):
    return self._modem
  
  def setModem(self, modem):
    self._modem=modem

  def getProveedor(self):
    return self._proveedor
  
  def setProveedor(self, proveedor):
    self._proveedor=proveedor

  def getPlan(self):
    return self._plan
  
  def setPlan(self,plan):
    self._plan=plan

  def getNombrePlan(self):
    return self._nombrePlan
  
  def setNombrePlan(self,nombrePlan):
    self._nombrePlan=nombrePlan

  def getFactura(self):
    return self._factura
  
  def setFactura(self,factura):
    self._factura=factura