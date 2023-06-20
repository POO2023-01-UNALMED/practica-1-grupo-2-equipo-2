class ErrorAplicacion(Exception):

    def __init__(self, mensajeEApp):
        self.mensajeErrorApp ="Manejo de errores de la Aplicación: \n" + mensajeEApp
        super().__init__(self.mensajeErrorApp)