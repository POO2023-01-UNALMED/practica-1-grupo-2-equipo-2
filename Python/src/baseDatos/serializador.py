import pickle
import sys

sys.path.append("src/gestorAplicacion/enrutadorHFC")

from antena import Antena
from servidor import Servidor
from dispositivo import Dispositivo

class Serializador:

    @classmethod
    def serializar(cls):

        listas = [Antena.getAntenasTotales(), Servidor.getServidoresTotales(), Dispositivo.getDispositivosTotales()]
        
        for lista in listas:

            picklefile = open(f"src/baseDatos/temp/{type(lista[0]).__name__}","wb")
            pickle.dump(lista, picklefile)
            picklefile.close()
        
    

