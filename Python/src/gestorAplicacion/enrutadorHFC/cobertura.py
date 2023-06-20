from abc import ABC, abstractmethod

class Cobertura(ABC):
  
  # CONSTRUCTOR
  def __init__(self,generacion):

    # //ATRIBUTOS
    self.generacion=generacion
    self.intensidadFlujo=0
  
  # //METODOS ABSTRACTOS

  # //METODO UTILIZADO PRINCIPALMENTE POR LA CLASE ANTENA--FUNCIONALIDAD DEL TEST
  @abstractmethod
  def rastrearGeneracionCompatible(self,antenasSede,router):
    pass

  # //METODO UTILIZADO PRINCIPALMENTE POR LA CLASE ROUTER--FUNCIONALIDAD MEJORA TU PLAN
  @abstractmethod
  def intensidadFlujoOptima(self,ss,c):
    pass

  # //METODO UTILIZADO PRINCIPALMENTE POR LA CLASE ROUTER--FUNCIONALIDAD REPORTE
  @abstractmethod
  def intensidadFlujoClientes(self,ctes,sevidor,reales):
    pass
  
  # //METODO TOSTRING
  def __str__(self):
    return ""
  
  # //GETTERS Y SETTERS

  def getGeneracion(self) :
    return self.generacion
  

  def setGeneracion(self, generacion) :
    self.generacion = generacion
  

  def getIntensidadFlujo(self): 
    return self.intensidadFlujo
  

  def setIntensidadFlujo(self, intensidadFlujo):
    self.intensidadFlujo = intensidadFlujo