class Plan:

    # //ESTA CLASE CONTIENE LA INFORMACION DE LOS PLANES DE LOS PROVEEDORES

    def __init__(self, BASIC, STANDARD, PREMIUM):

        # ATRIBUTOS
        self._BASIC = BASIC #//CONTIENE MEGAS SUBIDA, MEGAS BAJADA, PRECIO DEL PLAN
        self._STANDARD = STANDARD
        self._PREMIUM = PREMIUM
    
    # //SETTERS Y GETTERS
    def getBASIC(self):
        return self._BASIC
    
    def getSTANDARD(self):
        return self._STANDARD
    
    def getPREMIUM(self):
        return self._PREMIUM