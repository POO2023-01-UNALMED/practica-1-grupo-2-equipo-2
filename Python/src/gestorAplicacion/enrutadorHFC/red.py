from abc import ABC,abstractmethod

# CLASE ABSTRACTA
class Red(ABC):
  
  # // "CONSTANTES"
  FLUJO_RED_PRELIMINAR=500

  # //MÉTODOS
  @abstractmethod
  def verificarAdmin(self,proveedores,nombre):
    pass

  @abstractmethod
  def distanciasOptimas(self,clientes,listaIntensidadesClientes):
    pass