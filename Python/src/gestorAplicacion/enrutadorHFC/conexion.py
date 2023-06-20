from router import Router

class Conexion(Router):

  # //ESTA CLASE ES FUNDAMENTAL EN LAS FUNCIONALIDADES DEL TEST Y REPORTE PARA EL CALCULO DE LAS INTENSIDADES DE FLUJO---DE ELLA SE OBTIENEN LAS VELOCIDADES EN "TIEMPO REAL"

  # CONSTRUCTORES
  def __init__(self,ip,up,down,online,generacion,cliente,servidor):

    # ATRIBUTOS
    self._cliente=cliente
    super().__init__(up,down,online,servidor)

  # // SETTERS Y GETTERS

  # //METODO HEREDADO DE ROUTER SOBREESCRITO CON EL CUAL SE APLICA "LIGADURA DINÁMICA"
  def getVelocidad(self):
    velocidadActual=(((self._cliente.getModem().actualizarVelocidad(self._cliente)[0] + self._cliente.getModem().actualizarVelocidad(self._cliente)[0] )/2)*self.generacion*self.generacion) 
    return velocidadActual
  
  def getCliente(self):
    return self._cliente
  
  def setCliente(self,cliente):
    self._cliente=cliente