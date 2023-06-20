import sys

sys.path.append("src/gestorAplicacion/enrutadorHFC")
sys.path.append("src/gestorAplicacion/servicio")

from dispositivo import Dispositivo
from servidor import Servidor
from plan import Plan
from cliente import Cliente

class ProveedorInternet:
	
	# /CLASE IMPORTANTE YA QUE SE RELACIONA CON EL CLIENTE

	# ATRIBUTO DE CLASE
	_proveedoresTotales = []

	# CONSTRUCTOR
	def __init__(self, nombre, clientes_max ,b,s,p,cmb,cms,cmpr):
		self._clientes = []
		self._nombre = nombre
		self._planes = Plan(b,s,p)	
		self._CLIENTES_MAX = clientes_max
		self._CLIENTES_BASIC = cmb
		self._CLIENTES_STANDARD = cms
		self._CLIENTES_PREMIUM = cmpr
		ProveedorInternet._proveedoresTotales.append(self)

	# MÉTODOS

	#  //METODO DE INSTANCIA-FUNCIONALIDAD VISUALIZAR DISPOSITIVOS--COMPRUEBA QUE EL ID PASADO POR PARÁMETRO COINCIDA CON ALGUN CLIENTE DE SU ARREGLO DE CLIENTES TOTALES.ADEMÁS VERIFICA QUE EL CLIENTE TENGA UN TIPO DE PLAN DETERMINADO
	# COMPRUEBA QUE EL ID Y NOMBRE COINCIDA CON UN CLIENTE DEL ARREGLO DE CLIENTES ASOCIADOS AL PROVEEDOR Y QUE EL CLIENTE TENGA MÁS DE 2 DISPOSITIVOS CONECTADODS
	def verificarCliente(self, id_=0, nombre=""):
		if nombre == "":
			for cliente in self._clientes:
				if cliente.getID()==id_:
					if cliente.getNombrePlan()=="PREMIUM":
						return cliente
					elif cliente.getNombrePlan()=="STANDARD":
						return cliente 
					else:
						return None
		else:
			for cliente in self._clientes:
				if (cliente.getID() == id_) and (cliente.getNombre() == nombre):
					if len(cliente.getModem().verificarDispositivos(Dispositivo.getDispositivosTotales(),cliente.getModem())) > 2:
						return cliente
			return None

	# //METOODO INSTANCIA-- FUNCIONALIDAD ADQUISICION DE PLAN-- OBTIENE LOS CLIENTES ASOCIADOS SEPARADOS POR EL TIPO DE PLAN, PARA ELLO SE INVOCA EL METODO BUSCARCLIENTE---LUEGO COMPRUEBA QUE HAYAN CUPOS EN CADA UNO DE LOS PLANES 
	def planesDisponibles(self, cliente):

		clientes_basic=cliente.buscarCliente(self._clientes,"BASIC")
		clientes_standard=cliente.buscarCliente(self._clientes,"STANDARD")
		clientes_premium=cliente.buscarCliente(self._clientes,"PREMIUM")
		planes_disponibles=[]

		if len(clientes_basic) < self._CLIENTES_BASIC:
			planes_disponibles.append("BASIC")

		if len(clientes_standard) < self._CLIENTES_STANDARD:
			planes_disponibles.append("STANDARD")

		if len(clientes_premium) < self._CLIENTES_PREMIUM:
			planes_disponibles.append("PREMIUM")

		return planes_disponibles


	#  //METODO INSTANCIA---FUNCIONALIDAD ADQUISICION DE PLAN--PERMITE CREAR UN NUEVO CLIENTE CON LOS DATOS INDICADOS
	def crearCliente(self,nombre,id_):
		cliente = Cliente(nombre,id_)
		return cliente

	# //METODO ESTÁTICO--FUNCIONALIDAD AQUISICION DE PLAN---MEDIANTE EL ARREGLO ESTÁTICO DE TODOS LOS SERVIDORES DE LA CLASE SERVIDOR, SE HALLAN LOS PROVEEDORES QUE PERTENEZCAN A UNA SEDE ESPECIFICA
	@classmethod
	def proveedorSede(cls,sede):
		proveedores=[]
		for servidor in Servidor.getServidoresTotales():
			if servidor.getSede() == sede:
				proveedores.append(servidor.getProveedor())

		return proveedores
	
	# //METODO ESTÁTICO---SE EJECUTA DESPUÉS DE LA DESERIALIZACION EN EL MAIN. LO QUE HACE ES OBTENER LOS PROVEEDORES DE LOS SERVIDORES Y AGREGAR ESTOS A LA LISTA DE PROVEEDORES TOTALES
	@classmethod
	def proveedores(cls):
		for servidor in Servidor.getServidoresTotales():
			if( not(servidor.getProveedor() in cls._proveedoresTotales) ):
				cls._proveedoresTotales.append(servidor.getProveedor())

	
	def getPlanes(self):
		return self._planes

	def setPlanes(self, planes):
		self._planes = planes

	def getNombre(self):
		return self._nombre

	def setNombre(self, nombre):
		self._nombre = nombre

	def getClientes(self):
		return self._clientes

	def setClientes(self, clientes):
		self._clientes = clientes

	def getCLIENTES_MAX(self):
		return self._CLIENTES_MAX

	def getCLIENTES_BASIC(self):
		return self._CLIENTES_BASIC

	def getCLIENTES_STANDARD(self):
		return self._CLIENTES_STANDARD

	def getCLIENTES_PREMIUM(self):
		return self._CLIENTES_PREMIUM

	@classmethod
	def getProveedoresTotales(cls):
		return cls._proveedoresTotales

	@classmethod
	def setProveedoresTotales(cls, proveedores_totales):
		cls._proveedoresTotales = proveedores_totales