class Plano:

    # /CONTIENE LA INFORMACION DE LA UBICACION DE LOS CLIENTES Y LOS SERVIDORES

    # //CONSTRUCTOR
    def __init__(self,x,y):

        # ATRIBUTOS
        self._x = x
        self._y = y
        self._sede = ""
        self._zonaCobertura = None
    
    # //METODOS 

#   //METODO ESTÁTICO QUE PERMITE CREAR EL CUADRADO QUE DETERMINA LA FORMA DE LA SEDE Y SUS LIMITES
    @classmethod
    def crearSede(cls,h,w,x,y):
        return (h,w,x,y)
    
    # //METODO QUE PERMITE CREAR UNA ZONA DE COBERTURA--USADO EN LA CLASE ANTENA
    def crearZonaCobertura(self,centrox,centroy,r):
        return (centrox,centroy,r)
    
    # //METODO QUE CONVIERTE LAS COORDENADAS A UN DATO TIPO POINT2D PARA FACILITAR CÁLCULOS
    def crearPunto(self):
        return (self._x,self._y)
    
    # //SETTERS Y GETTERS
    
    def getY(self):
        return self._y
    
    def setY(self,y):
        self._y = y
        
    def getX(self):
        return self._x
    
    def setX(self,x):
        self._x = x
    
    def getSede(self):
        return self._sede
    
    def setSede(self,sede):
        self._sede = sede
    
    def getZonaCobertura(self,):
        return self._zonaCobertura
    
    def setZonaCobertura(self,zonaCobertura):
        self._zonaCobertura = zonaCobertura

    @classmethod
    def getCuadrante(cls):
        return cls._cuadrante
    
    @classmethod
    def setCuadrante(cls, cuadrante):
        cls._cuadrante = cuadrante