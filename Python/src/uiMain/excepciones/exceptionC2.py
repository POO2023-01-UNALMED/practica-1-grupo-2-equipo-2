from errorAplicacion import ErrorAplicacion

class Exception2(ErrorAplicacion):

    def __init__(self, mensajeEx2):
        self.mensajeEx2= f"Exception2. {mensajeEx2}"
        super().__init__(self.mensajeEx2)

class ErrorOpElegida(Exception2):

    def __init__(self):
        self.mensajeEO = f"La opción que elegiste no es válida. Ingresala de nuevo."
        super().__init__(self.mensajeEO)

class ErrorLetra(Exception2):

    def __init__(self):
        self.mensajeEL = f"Ingresaste una letra en lugar de un número. Ingresa un valor correcto."
        super().__init__(self.mensajeEL)

class ErrorEspacios(Exception2):

    def __init__(self):
        self.mensajeES= f"Estas dejando espacios en blanco. Ingresa todos los datos solicitados."
        super().__init__(self.mensajeES)