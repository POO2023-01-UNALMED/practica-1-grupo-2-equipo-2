class Dispositivo:

  # //CLASE IMPORTANTE PARA LA FUNCIONALIDAD DE VISUALIZAR DISPOSITIVOS, SIN EMBARGO TAMBIÉN TIENE IMPLICACIONES EN OTRAS EN PROCESOS SECUNDARIOS (TEST--REPORTE--MEJORA TU PLAN)
  
  # ATRIBUTO ESTÁTICO
  _dispositivosTotales=[]

  # CONSTRUCTORES
  def __init__(self, modem, nombre, generacion):

    # //ATRIBUTOS
    self._modem=modem
    self._nombre=nombre
    self._ipAsociada=modem.getIP()
    self._generacion=generacion
    Dispositivo._dispositivosTotales.append(self)
  
  # //MÉTODOS

  # //METODO DE ESTATICO---FUNCIONALIDAD TEST

  @classmethod
  def desconectarDispositivo(cls, cliente, dispositivo):
    if (cliente.getModem().verificarDispositivos(Dispositivo._dispositivosTotales,cliente.getModem()).contains(dispositivo) ):
      dispositivo.setIpAsociada("")
      return "Dispositivo desconectado correctamente."
    else:
      return "Dispositivo inválido, intente de nuevo."
  
  # //METODO TOSTRING
  def __str__(self):
    return f"Dispositivo {self._nombre} \nDirección IP: {self._ipAsociada} \nGeneración: {self._generacion}"
    
  # //GETTERS Y SETTERS
  def getModem(self):
    return self._modem
  
  def setModem(self, modem):
    self._modem=modem
    
  def getIpAsociada(self):
    return self._ipAsociada
  
  def setIpAsociado(self, ipAsociada):
    self._ipAsociada=ipAsociada

  def getNombre(self):
    return self._nombre
  
  def setNombre(self, nombre):
    self._nombre=nombre

  def getGeneracion(self):
    return self._generacion
  
  def setGeneracion(self, generacion):
    self._generacion=generacion

  @classmethod
  def getDispositivosTotales(cls):
    return cls._dispositivosTotales
  
  @classmethod
  def setDispositivosTotales(cls, _dispositivosTotales):
    cls._dispositivosTotales=_dispositivosTotales
  