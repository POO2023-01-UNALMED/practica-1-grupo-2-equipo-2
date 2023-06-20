from random import randint
from math import sqrt,pow 
from dispositivo import Dispositivo
from cobertura import Cobertura

class Router(Cobertura):

    def __init__(self,up=0,down=0,online=False,servidor=None,antena=None,coordenadas=None,generacion=3):
        
        super().__init__(generacion)
        
        octeto1 = randint(100,256)
        octeto2 = randint(100,256)
        octeto3 = randint(100,256)
        octeto4 = randint(100,256)
        
        self._IP = str(octeto1) + "." + str(octeto2) + "." + str(octeto3) + "." + str(octeto4)

        self._up=up
        self._down=down
        self._online=online
        self._ping=0 
        self._coordenadas=coordenadas
        self._servidorAsociado=servidor
        self._antenaAsociada=antena
        self._velocidad=250

        if self._antenaAsociada!=None:
            self._sede=self._antenaAsociada.getSede()
            
        if self._servidorAsociado!=None: 
            self._servidorAsociado.getRouters().append(self)

    
    def actualizarVelocidad(self,cliente):

        megasUp = cliente.getPlan()[0]
        megasDown = cliente.getPlan()[1]
        routerCliente = cliente.getModem()
        dispositivos = Dispositivo.getDispositivosTotales()
        dispositivosCliente = self.verificarDispositivos(dispositivos,routerCliente)
        listaActualizada = []

        if(len(dispositivosCliente)==0):
            megasUp=26
            megasDown=18
        else:

            for dispoActual in dispositivosCliente:
                if(self._antenaAsociada.verificarZonaCobertura(self,self._antenaAsociada)):
                    if(dispoActual.getNombre() == "Celular"):
                        megasUp -= 4
                        megasDown -= 10
                    elif(dispoActual.getNombre() == "Computador"):
                        megasUp -= 8
                        megasDown -= 20
                    elif(dispoActual.getNombre() == "Televisor"):
                        megasUp -= 10
                        megasDown -= 30
                else:
                    megasDown=18
                    megasUp=26
                    break


        listaActualizada.append(megasDown)
        listaActualizada.append(megasUp)
        return listaActualizada

    
    def actualizarPing(self, listaActualizada):

        up = listaActualizada[1]
        down = listaActualizada[0]
        global ping 

        if(up >= 60 and down >= 400):
            ping = randint(0,20)
            return "Ping: " + str(ping) + " ms"

        elif(up >= 40 and down >= 200):
            ping = randint(21,40) 
            return "Ping: " + str(ping) + " ms"
        
        elif(up >= 20 and down >= 100):
            ping = randint(41,60)
            return "Ping: " + str(ping) + " ms"

        elif(up >= 0 and down >= 0):
            ping = randint(90,110)
            return "Ping: " + str(ping) + " ms"
        
        elif(up < 0 or down < 0):
            ping = randint(900,999)
            return "Ping: " + str(ping) + " ms" 
        
        else:
            return None

    
    def test(self, cliente):
        
        up_down = cliente.getModem().actualizarVelocidad(cliente)
        ping=int(cliente.getModem().actualizarPing(up_down).split(" ")[1])
        up = up_down[1]
        down = up_down[0]
        resultado1 = ""
        resultado2 = ""
        resultado3 = ""

        resultado1 = "Red saturada" if (900 < ping) else "Test de velocidad finalizado"
        resultado2 = "Inaccesible a la zona de cobertura" if up_down[0]==18 else "Test de velocidad finalizado"
        resultado3 = "Test de velocidad finalizado" if self.generacion==self._antenaAsociada.getGeneracion() else "Generacion incompatible"


        if("cobertura" in resultado2):
            return resultado2

        elif("saturada" in resultado1):
            return self.actualizarPing(up_down) + "\n" + resultado1

        elif("incompatible" in resultado3):
            return resultado3

        else:
            ping = self.actualizarPing(up_down)
            return ping

    
    def verificarDispositivos(self, dispositivosTotales,router):

        dispositivosConectados = []

        for dispoActual in dispositivosTotales :
            if(dispoActual.getIpAsociada() == router.getIP() and router.isOnline() == True ):
                dispositivosConectados.append(dispoActual)

        return dispositivosConectados

    

    def protocoloSeleccionServidor(self, servidores,c):

        from conexion import Conexion

        #1.COORDENADAS ROUTER CLIENTE
        coordenadaX = self._coordenadas.getX()
        coordenadaY = self._coordenadas.getY()

        #2.DISPOSITIVOS CONECTADOS AL ROUTER
        dispositivosConectados = self.verificarDispositivos(Dispositivo.getDispositivosTotales(),self)

        #3.DISTANCIA DEL ROUTER AL SERVIDOR ASOCIADO
        distancia = int(sqrt(pow(self._servidorAsociado.getCoordenadas().getX() - coordenadaX, 2) + pow(self._servidorAsociado.getCoordenadas().getY() - coordenadaY, 2)))

        #4.VELOCIDAD TOTAL
        velocidadTotal = Conexion(self._IP,self._down,self._up,self._online,self.generacion,c,self._servidorAsociado).getVelocidad()

        #5.INTENSIDAD DE FLUJO INICIAL O ACTUAL
        intensidadFlujo = int((self._servidorAsociado.getFLUJO_RED_NETO() + (velocidadTotal/len(dispositivosConectados)) - distancia) * self._servidorAsociado.getPORCENTAJE_EFICIENCIA())

        #6.NOMBRE PLAN DEL CLIENTE
        planCliente = c.getNombrePlan()

        intensidadMayor = self.intensidadFlujoOptima(servidores, c)

        servidorAproximado = intensidadMayor[0]
        intensidadServidorAproximado = int(intensidadMayor[1])

        #CARACTERISTICAS DE LA RECOMENDACION PARA RETORNAR
        caracteristicas = []

        if(intensidadServidorAproximado == intensidadFlujo):
        
            if(servidorAproximado == self._servidorAsociado):
                return None
            else:

                if(planCliente == "BASIC"):
                    if(c.getPlan()[2] > servidorAproximado.getProveedor().getPlanes().getBASIC()[2]):
                        
                        caracteristicas.append(intensidadFlujo)
                        caracteristicas.append(servidorAproximado)
                        caracteristicas.append(servidorAproximado.getProveedor())
                        caracteristicas.append(intensidadServidorAproximado)
                        caracteristicas.append(servidorAproximado.getProveedor().getPlanes().getBASIC())
                        return caracteristicas
                    
                elif(planCliente == "STANDARD"):
                    if(c.getPlan()[2] > servidorAproximado.getProveedor().getPlanes().getSTANDARD()[2]):
                        
                        caracteristicas.append(intensidadFlujo)
                        caracteristicas.append(servidorAproximado)
                        caracteristicas.append(servidorAproximado.getProveedor())
                        caracteristicas.append(intensidadServidorAproximado)
                        caracteristicas.append(servidorAproximado.getProveedor().getPlanes().getSTANDARD())
                        return caracteristicas
                    
                elif(planCliente == "PREMIUM"):
                    if(c.getPlan()[2] > servidorAproximado.getProveedor().getPlanes().getPREMIUM()[2]):
                        
                        caracteristicas.append(intensidadFlujo)
                        caracteristicas.append(servidorAproximado)
                        caracteristicas.append(servidorAproximado.getProveedor())
                        caracteristicas.append(intensidadServidorAproximado)
                        caracteristicas.append(servidorAproximado.getProveedor().getPlanes().getPREMIUM())
                        return caracteristicas
            
        elif(intensidadFlujo < intensidadServidorAproximado):
            if(planCliente == "BASIC"):

                caracteristicas.append(intensidadFlujo)
                caracteristicas.append(servidorAproximado)
                caracteristicas.append(servidorAproximado.getProveedor())
                caracteristicas.append(intensidadServidorAproximado)
                caracteristicas.append(servidorAproximado.getProveedor().getPlanes().getBASIC())
                return caracteristicas
            
            elif(planCliente == "STANDARD"):

                caracteristicas.append(intensidadFlujo)
                caracteristicas.append(servidorAproximado)
                caracteristicas.append(servidorAproximado.getProveedor())
                caracteristicas.append(intensidadServidorAproximado)
                caracteristicas.append(servidorAproximado.getProveedor().getPlanes().getSTANDARD())
                return caracteristicas
            
            elif(planCliente == "PREMIUM"):

                caracteristicas.append(intensidadFlujo)
                caracteristicas.append(servidorAproximado)
                caracteristicas.append(servidorAproximado.getProveedor())
                caracteristicas.append(intensidadServidorAproximado)
                caracteristicas.append(servidorAproximado.getProveedor().getPlanes().getPREMIUM())
                return caracteristicas
            
        return None
    

    def intensidadFlujoOptima(self, servidoresLocalidad, c):

        from conexion import Conexion

        distancia = int(sqrt(pow(self._servidorAsociado.getCoordenadas().getX() - self._coordenadas.getX(), 2) + pow(self._servidorAsociado.getCoordenadas().getY() - self._coordenadas.getY(), 2)))

        conexion = Conexion(self._IP, self._down, self._up, self._online, self.generacion, c, c.getModem().getServidorAsociado())
        velocidadTotal = conexion.getVelocidad()

        distanciaTemporal = 0
        servidorTemporal = None

        for servidor in servidoresLocalidad:
        
            distanciaTemporal = int (sqrt(pow(servidor.getCoordenadas().getX() - self._coordenadas.getX(), 2) + pow(servidor.getCoordenadas().getY() - self._coordenadas.getY(), 2)))
            
            if(distanciaTemporal <= distancia):
                distancia = distanciaTemporal
                servidorTemporal = servidor
        
        intensidadFlujoMayor = int((servidorTemporal.getFLUJO_RED_NETO()+(velocidadTotal/len(self.verificarDispositivos(Dispositivo.getDispositivosTotales(), self))) - distancia)* servidorTemporal.getPORCENTAJE_EFICIENCIA())
        return [servidorTemporal,intensidadFlujoMayor]     


    def intensidadFlujoClientes(self, ctes, servidor,reales):

        from conexion import Conexion
        
        lista = []

        for c in ctes:
            intensidad=0
            ip = c.getModem().getIP()
            up = c.getModem().getUp()
            down = c.getModem().getDown()
            online = c.getModem().isOnline()
            generacion = c.getModem().getGeneracion()
            velocidad = Conexion(ip, up, down, online, generacion, c, servidor).getVelocidad()
            dispositivos = len(c.getModem().verificarDispositivos(Dispositivo.getDispositivosTotales(), c.getModem()))
            distancia = int(sqrt(pow(servidor.getCoordenadas().getX() - c.getModem().getCoordenadas()[0], 2) + pow(servidor.getCoordenadas().getY() - c.getModem().getCoordenadas()[1], 2)))
            porcentaje = servidor.getPORCENTAJE_EFICIENCIA()

            if(dispositivos!=0):
                intensidad = int((servidor.getFLUJO_RED_NETO()+velocidad / dispositivos - distancia) * porcentaje)
            else:
                intensidad = int((servidor.getFLUJO_RED_NETO()- distancia) * porcentaje)
            
            intensidad1 = int(intensidad/porcentaje)

            if reales:
                lista.append(intensidad)
            else:
                lista.append(intensidad1)

        return lista         


    def rastrearGeneracionCompatible(self, antenasSede,r):
        return None
    
    @classmethod
    def adminRouter(cls):
        return Router()
    
    #SETTERS Y GETTERS

    def getIP(self):
        return self._IP
    
    def getUp(self):
        return self._up

    def setUp(self, up):
        self._up = up

    def getDown(self):
        return self._down
    
    def setDown(self, down):
        self._down = down
    
    def isOnline(self):
        return self._online

    def setOnline(self, online):
        self._online = online
    
    def getPing(self):
        return self._ping

    def setPing(self, ping):
        self._ping = ping
    
    def getCoordenadas(self):
        return self._coordenadas.crearPunto()
    
    def setCoordenadas(self, coordenadas):
        self._coordenadas = coordenadas
    
    def getServidorAsociado(self, ):
        return self._servidorAsociado

    def setServidorAsociado(self, servidorAsociado):
        self._servidorAsociado = servidorAsociado
    
    def getAntenaAsociada(self):
        return self._antenaAsociada
    
    def setAntenaAsociada(self, antenaAsociada):
        self._antenaAsociada = antenaAsociada

    def getSede(self):
        return self._sede
    
    def setSede(self, sede):
        self._sede = sede
    
    def getVelocidad(self):
        return self._velocidad
    
    def setVelocidad(self, velocidad):
        self._velocidad = velocidad