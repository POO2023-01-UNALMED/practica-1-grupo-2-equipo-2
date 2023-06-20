import sys

sys.path.append("src/gestorAplicacion/enrutadorHFC")
sys.path.append("src/gestorAplicacion/servicio")
sys.path.append("src/gestorAplicacion/host")
sys.path.append("src/baseDatos")

from antena import Antena
from router import Router
from proveedorInternet import ProveedorInternet
from cliente import Cliente
from plan import Plan
from plano import Plano
from servidor import Servidor
from dispositivo import Dispositivo
from factura import Factura
from mes import Mes
from serializador import Serializador 
from deserializador import Deserializador
from ventanaInicio import VentanaInicio
from ventanaUsuario import VentanaUsuario


if __name__ == "__main__":

    # SE DESERIALIZA
    Deserializador.deserializar()

    # SE AJUSTAN LAS REFERENCIA DE LOS PROVEEDORES
    ProveedorInternet.proveedores()
    Antena.antenas()

    # SE CONSTRUYE LA VENTANA DE INICIO
    ventana = VentanaInicio()

   

    
