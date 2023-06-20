import pickle
import sys
sys.path.append("src/gestorAplicacion/enrutadorHFC")
sys.path.append("src/gestorAplicacion/servicio")

from antena import Antena
from servidor import Servidor
from dispositivo import Dispositivo

class Deserializador:

    @classmethod
    def deserializar(cls):

        Antena.setAntenasTotales(pickle.load(open("src/baseDatos/temp/Antena","rb"))) #NO SE SI PONER EL CLOSE
        Dispositivo.setDispositivosTotales(pickle.load(open("src/baseDatos/temp/Dispositivo","rb")))
        Servidor.setServidoresTotales(pickle.load(open("src/baseDatos/temp/Servidor","rb")))
        