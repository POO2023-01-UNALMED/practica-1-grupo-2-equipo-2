from red import Red
from dispositivo import Dispositivo
from conexion import Conexion

class Servidor(Red):
  
  # //CLASE FUNDAMENTAL PARA LA IMPLEMENTACION DE LA FUNCIONALIDAD MEJORA TU PLAN Y REPORTE
  
  # ATRIBUTO DE CLASE
  _servidoresTotales=[]
  
  # CONSTRUCTOR
  def __init__(self, sede="", saturado=False, proveedor=None, coordenadas=None, indice=0, pe=0 ):
    
    # ATRIBUTOS
    self._sede=sede
    self._PORCENTAJE_EFICIENCIA=pe
    self._saturado=saturado
    self._proveedor=proveedor
    self._coordenadas=coordenadas
    self._routers=[]
    self._INDICE_SATURACION=indice
    self._FLUJO_RED_NETO=self._PORCENTAJE_EFICIENCIA*super().FLUJO_RED_PRELIMINAR
    
    if proveedor!=None :
      Servidor._servidoresTotales.append(self)

  # MÉTODOS

  # //METODO ESTATICO---FUNCIONALIDAD MEJORA TU PLAN--BUSCA Y RETORNA TODOS LOS SERVIDORES QUE EXISTAN EN UNA SEDE EN ESPECIFICO
  @classmethod
  def buscarServidores(cls,localidad):
    serviSede=[]
    for servidor in Servidor._servidoresTotales:
      if((servidor.getSede()==localidad) and (not(servidor.isSaturado()))):
        serviSede.append(servidor)
        
    return serviSede

  # //IMPLEMENTA EL METODO DE LA CLASE RED--FUNCIONALIDAD REPORTE--VERIFICA ADMIN Y RETORNA SERVIDORES DE UN PROVEEDOR EN TODAS LAS LOCALIDADES
  def verificarAdmin(self, proveedores, nombre):
    servidoresProveedor=[]
    for proveedor in proveedores:
      if(proveedor.getNombre()==nombre):
        for servidor in Servidor._servidoresTotales:
          if servidor.getProveedor()!=None:
            if(servidor.getProveedor().getNombre()==nombre):
              servidoresProveedor.append(servidor)
            
    return servidoresProveedor
  
  # //IMPLEMENTA METODO DE LA CLASE RED---FUNCIONALIDAD REPORTE-CALCULA LAS DISTANCIAS A LA QUE DEBERIA UBICARSE EL SERVIDOR PARA GENERAR UNA AMYOR INTENSIDAD DE FLUJO
  def distanciasOptimas(self, clientes,listaIntensidadesClientes):
    distancias=[]
    for i in range(0,len(listaIntensidadesClientes)):
      distanciaOptima=0
      modem = clientes[i].getModem()
      velocidad =  Conexion(modem.getIP(),modem.getUp(),modem.getDown(),modem.isOnline(),modem.getGeneracion(),clientes[i],modem.getServidorAsociado()).getVelocidad()
      dispositivos=len(modem.verificarDispositivos(Dispositivo.getDispositivosTotales(), modem))

      if (dispositivos!=0):
        distanciaOptima=(modem.getServidorAsociado().getFLUJO_RED_NETO()+(velocidad/dispositivos)-(listaIntensidadesClientes[i]/modem.getServidorAsociado().getPORCENTAJE_EFICIENCIA()))
  
      else:
        distanciaOptima = (int)(modem.getServidorAsociado().getFLUJO_RED_NETO()-(listaIntensidadesClientes[i]/modem.getServidorAsociado().getPORCENTAJE_EFICIENCIA()))
        
      distancias.append(distanciaOptima)
      
    return distancias

  # /METODO ESTATICO--FUNCIONALIDAD REPORTE--INSTANCIA UN OBJETO SERVIDOR ALEATORIO QUE SIRVE PARA INVOCAR EL MÉTODO VERIFICARADMIN DE ESTA CLASE
  @classmethod
  def adminServidor(cls):
    return Servidor()

  #GETTERS Y SETTERS
  def getSede(self):
    return self._sede
  def setSede(self, sede):
    self._sede=sede
    
  def getPORCENTAJE_EFICIENCIA(self):
    return self._PORCENTAJE_EFICIENCIA

  def isSaturado(self):
    return self._saturado
  
  def setSaturado(self, saturado):
    self._saturado=saturado

  def getProveedor(self):
    return self._proveedor
  
  def setProveedor(self, proveedor):
    self._proveedor=proveedor

  def getCoordenadas(self):
    return self._coordenadas
  
  def setCoordenadas(self,coordenadas):
    self._coordenadas=coordenadas

  def getRouters(self):
    return self._routers
  
  def setRouters(self,routers):
    self._routers=routers

  def getINDICE_SATURACION(self):
    return self._INDICE_SATURACION

  def getFLUJO_RED_NETO(self):
    return self._FLUJO_RED_NETO
  
  def setFLUJO_RED_NETO(self, flujo):
    self._FLUJO_RED_NETO = flujo

  @classmethod
  def getServidoresTotales(cls):
    return cls._servidoresTotales
  
  @classmethod
  def setServidoresTotales(cls, servidoresTotales):
    cls._servidoresTotales=servidoresTotales