import sys

sys.path.append("src/gestorAplicacion/host")

from cobertura import Cobertura
from math import sqrt, pow

class Antena(Cobertura):

  #  //ESTA CLASE ES USADA PRINCIPALMENTE EN LA FUNCIONALIDAD DEL TEST, DONDE SE LE RECOMIENDA AL CLIENTE CAMBIARSE DE ANTENA

  # ATRIBUTO DE CLASE
  _antenasTotales = []

  def __init__(self, identificador, coordenadas, sede, generacion,radio, proveedor):

    # /ATRIBUTOS
    super().__init__(generacion)
    self._identificador = identificador
    self._coordenadas = coordenadas
    self._sede = sede
    self._zonaCobertura = self._coordenadas.crearZonaCobertura(self._coordenadas.getX(),self._coordenadas.getY(),radio)
    self._proveedor = proveedor
    Antena._antenasTotales.append(self)

  # METODOS

  # //METODO INSTANCIA---BUSCA LAS ANTENAS QUE ESTAN EN UNA SEDE Y QUE PERTENECEN A UN PROVEEDOR ESPECIFICO
  @classmethod
  def antenasSede(cls, sede="", proveedor=None):

    if proveedor == None:
      antenas = []
      for antena in cls._antenasTotales:
        if antena.getSede() == sede:
          antenas.append(antena)

      return antenas
    else:
      for antena in cls._antenasTotales:
        if antena.getSede() == sede and antena.getProveedor().getNombre() == proveedor:
          return antena
      return None

  # //METODO INSTANCIA---IMPLEMENTACION Y SOBREESCRITURA METODO ABSTRACTO QUE SE HEREDA DE COBERTURA-FUNCIONALIDAD TEST--BUSCA UNA ANTENA EN LA QUE EL CLIENTE SE ENCUENTRE DENTRO DE SU ZONA DE COBERTURA Y LA GENERACION DE ESTA Y EL ROUTER SEAN LAS MISMAS
  def rastrearGeneracionCompatible(self, antenas_sede, router):

    antenas_cercanas = list(filter(lambda antena: antena.verificarZonaCobertura(router, antena),antenas_sede))
    antena_encontrada = next(antena for antena in antenas_cercanas if antena.getGeneracion() == router.getGeneracion() or None)

    return antena_encontrada
  
  # //METODO INSTANCIA---CALCULA SI LA UBICACION DEL ROUTER (COORDENADAS) SE ENCUENTRA DENTRO DE LA ZONA DE COBERTURA DE LA ANTENA--FUNCIONALIDAD TEST
  def verificarZonaCobertura(self, router, antena):
    rx = router.getCoordenadas()[0]
    ry = router.getCoordenadas()[1]
    radio_antena = antena.getZonaCobertura()[2]/2 

    
    d = sqrt(pow(rx - antena.getCoordenadas()[0], 2) + pow(ry - antena.getCoordenadas()[1], 2)) <= radio_antena

    return d

  # //DEFINICIÓN DEL METODO ABSTRACTO QUE HEREDA DE COBERTURA--EVITAR ERRORES DE COMPILACION
  def intensidadFlujoClientes(self, ctes, servidor, reales):
    pass

  # //DEFINICIÓN DEL METODO ABSTRACTO QUE HEREDA DE COBERTURA--EVITAR ERRORES DE COMPILACION
  def intensidadFlujoOptima(self, ss, c):
    pass

  # //SOBREESCRITURA DEL METODO TOSTRING HEREDADO DE COBERTURA
  def __str__(self):
    return f"Identificador Antena: {self._identificador}  \nUbicación ({self.getCoordenadas()[0],self.getCoordenadas()[1]}) \nSede: {self._sede}"

  # //METODO ESTATICO QUE A LA HORA DE DESERIALIZAR SE ENCARGA DE RESETEAR LOS PROVEEDORES PARA QUE NO HAYAN PROBLEMAS POR LAS REFERENCIAS A LOS OBJETOS DESPUÉS DE SERIALIZAR
  @classmethod
  def antenas(cls):

    from proveedorInternet import ProveedorInternet

    for antena in Antena.getAntenasTotales():
      for proveedor in ProveedorInternet.getProveedoresTotales() :
        if antena.getProveedor().getNombre() == proveedor.getNombre():
          antena.setProveedor(proveedor)

  
  # SETTERS AND GETTERS

  def getCoordenadas(self):
    return self._coordenadas.crearPunto()
  
  def setCoordenadas(self, coordenadas):
    self._coordenadas = coordenadas
  
  def getIdentificador(self):
    return self._identificador
  
  def setIdentificador(self, identificador):
    self._identificador = identificador
  
  def getProveedor(self):
    return self._proveedor

  def setProveedor(self, proveedor):
    self._proveedor = proveedor

  def getZonaCobertura(self):
    return self._zonaCobertura
  
  def setZonaCobertura(self, zonaCobertura):
    self._zonaCobertura = zonaCobertura

  def getSede(self):
    return self._sede
  
  def setSede(self, sede):
    self._sede = sede
  
  @classmethod
  def getAntenasTotales(cls):
    return cls._antenasTotales
  
  @classmethod
  def setAntenasTotales(cls, antenas):
    cls._antenasTotales = antenas