from errorAplicacion import ErrorAplicacion

class Exception1(ErrorAplicacion):

    def __init__(self, mensajeEx1):
        self.mensajeEx1= f"Exception1: {mensajeEx1}"
        super().__init__(self.mensajeEx1)

class ErrorCoordenadas(Exception1):

    def __init__(self):
        self.mensajeEC = f"Las coordenadas están fueran del rango. Vuelve a ingresarlas."
        super().__init__(self.mensajeEC)

class ErrorPagoTotal(Exception1):

    def __init__(self):
        self.mensajeEP = f"El valor ingresado no es correcto. Ingresa la cantidad completa."
        super().__init__(self.mensajeEP)

class DatosIncorrectos(Exception1):

    def __init__(self):
        self.mensajeED = f"Los datos ingresados no son correctos. Verifica y vuelve a digitarlos."
        super().__init__(self.mensajeED)