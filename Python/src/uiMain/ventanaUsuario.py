import sys

sys.path.append("src/gestorAplicacion/host")
sys.path.append("src/gestorAplicacion/enrutadorHFC")
sys.path.append("src/baseDatos")
sys.path.append("src/uiMain/excepciones")

import tkinter as tk
from tkinter import  messagebox
from fieldFrame import FieldFrame
from proveedorInternet import ProveedorInternet
from servidor import Servidor
from cliente import Cliente
from router import Router
from dispositivo import Dispositivo
from antena import Antena
from exceptionC2 import ErrorLetra
from exceptionC2 import ErrorEspacios
from exceptionC2 import ErrorOpElegida
from exceptionC1 import DatosIncorrectos
from exceptionC1 import ErrorPagoTotal
from exceptionC1 import ErrorCoordenadas
from serializador import Serializador
from mes import Mes

class VentanaUsuario:

   _abierto = False
   frame_display=None
   proveedor_cliente=None
   cliente_=None
   titulo=None
   descripcion=None

   def __init__(self):

      def programa():
         for widget in ventana.winfo_children():
            widget.destroy()
       # PARA VOLVER A LA VENTANA DE INICIO
         def eventoSalir():

            from ventanaInicio import VentanaInicio

            ventana.destroy()
            VentanaUsuario._abierto = False
            Serializador.serializar()
            ventanaInicio = VentanaInicio()

         # REGRESO A LA PAGINA INICIAL PARA LA FUNCIONALIDAD TEST
         def eventoUsuario():
            self.frame_display.destroy()
            self.titulo.config(text="BIENVENIDO A LA VENTANA PRINCIPAL", font=("Times", 12), bg="gray85")
            self.descripcion.config( 
            text=" A continuación te daremos las instrucciones sobre cómo utilizar la aplicación:\n\n1. En la parte superior de esta ventana encontrarás un menú con tres pestañas: Archivo, Procesos y Consultas, Ayuda.\n\n2. En Archivo se desplegarán dos opciones:\n\n\t2.1. Aplicación, te dará información sobre el programa.\n\t2.2. Salir, sirve para regresar a la ventana de inicio. \n\n3. En Procesos y Consultas se listarán cada una de las funcionalidades. Da click en la opción que desees. \n\n4. Finalmente, en Ayuda tendrás la opción de Acerca de, esto te dará información sobre los autores del programa.", font=("Times",12), bg="gray85", justify="left")
            frameInformacion = tk.Frame(mainFrame, bg="gray85", height=270, width=670) #gray82
            frameInformacion.pack(expand=True, fill="both", padx=5, pady=5)
            
         # ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         # PARA ABRIR LA VENTANA DE DIÁLOGO CON LA INFO DE LA APP
         def eventoAplicacion():
            messagebox.showinfo(title = "Información de la Aplicación", message = "SISTEMA DE REDES",
            detail = "La aplicacion permite adquirir un plan de Internet con una compañía determinada y realizar cambios en el mismo, así como algunas funciones de administrador.")

         # ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         # PARA ABRIR LA VENTANA DE DIALOGO CON LA INFORMACION DE LOS AUTORES
         def eventoAcercaDe():
            messagebox.showinfo(title = "Información Autores", message = "AUTORES:",
            detail = "Angelica María Arce Parra \nLuisa María Marin Ceferino \nYesica Andrea Henao Ceballos \nJuan Camilo Misas Tabares \nFreddy Quintero Colorado")
         
         # RETORNO AL LA PANTALLA DE INICIO AL FINAL DE CADA FUNCIONALIDA
         def finalizar(): 
            programa()

# ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         # 1. FUNCIONALIDAD ADQUISICIÓN DE UN PLAN
         def adquisicionPlan():

            def eventoAceptar():

               def aceptar():

                  # CUANDO ACEPTA EL PLAN
                  def aceptarPlan():

                     # CUANDO ACEPTA LAS COORDENADAS
                     def aceptarCoordenadas():
                        
                        def aceptarPago():

                           try:

                              pago = entryPago.get()

                              if pago == "":
                                 raise ErrorEspacios()
                              
                              try:
                                 pago = int(pago)
                              except ValueError:
                                 raise ErrorLetra()
                              
                              facturaCliente.accionesPagos(2, entryPago.get())

                           except ErrorEspacios as e:

                              for widget in frameInformacion.winfo_children():
                                 widget.destroy()

                              messagebox.showwarning(title="Error", message="ERROR ESPACIOS", detail=f"{str(e)}")
                              return adquisicionPlan()
                           
                           except ErrorLetra as e:

                              for widget in frameInformacion.winfo_children():
                                 widget.destroy()

                              messagebox.showerror(title="Error", message="ERROR TIPO DE DATO", detail=f"{str(e)}")
                              return adquisicionPlan()

                           except ErrorPagoTotal as e:

                              for widget in frameInformacion.winfo_children():
                                 widget.destroy()

                              messagebox.showerror(title="Error", message="ERROR PAGO TOTAL", detail=f"{str(e)}")
                              return adquisicionPlan()


                              # SE ELIMINAN LOS FRAMES YA CREADOS
                           for widget in frameInformacion.winfo_children():
                              widget.destroy()
                           messagebox.showinfo(title = "Fin funcionalidad", message = "Proceso Finalizado",detail = "¡Tu adquisición de plan ha finalizado con éxito!")
                           proveedorActual.getClientes().append(clienteActual)
                           clienteActual.setProveedor(proveedorActual)
                           finalizar()
                           

                        # ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        # EXCEPCIONES
                        try:

                           coordenadaX = fp.getValor("Coordenada en X")
                           coordenadaY = fp.getValor("Coordenada en Y")

                           try:
                              coordenadaX = int(coordenadaX)
                              coordenadaY = int(coordenadaY)

                           except ValueError:
                              raise ErrorLetra()
                                 
                           if sede=="A":
                              if not(coordenadas[0][0] <= coordenadaX <= coordenadas[0][1]) or not(coordenadas[0][2] <= coordenadaY <= coordenadas[0][3]):
                                 raise ErrorCoordenadas()
                        
                           elif sede=="B":
                              if not(coordenadas[1][0] <= coordenadaX <= coordenadas[1][1]) or not(coordenadas[1][2] <= coordenadaY <= coordenadas[1][3]):
                                 raise ErrorCoordenadas()
                           
                           elif sede=="C":
                              if not(coordenadas[2][0] <= coordenadaX <= coordenadas[2][1]) or not(coordenadas[2][2] <= coordenadaY <= coordenadas[2][3]):
                                 raise ErrorCoordenadas()
                                 
                           elif sede=="D":
                              if not(coordenadas[3][0] <= coordenadaX <= coordenadas[3][1]) or not(coordenadas[3][2] <= coordenadaY <= coordenadas[3][3]):
                                 raise ErrorCoordenadas()
                           
                        except ErrorLetra as e:

                           for widget in frameInformacion.winfo_children():
                              widget.destroy()

                           messagebox.showerror(title="Error", message="ERROR TIPO DE DATO", detail=f"{str(e)}")
                           return adquisicionPlan()
                              
                        except ErrorCoordenadas as e:

                           for widget in frameInformacion.winfo_children():
                              widget.destroy()

                           messagebox.showerror(title="Error", message="ERROR COORDENADAS", detail=f"{str(e)}")
                           return adquisicionPlan()

                        except ErrorEspacios as e:

                              for widget in frameInformacion.winfo_children():
                                 widget.destroy()

                              messagebox.showwarning(title="Error", message="ERROR ESPACIOS", detail=f"{str(e)}")
                              return adquisicionPlan()
                        

                        # SE ELIMINAN LOS FRAMES YA CREADOS
                        for widget in frameInformacion.winfo_children():
                           widget.destroy()

                        # SE CONFIGURA EL PLAN Y SE GENERA UNA FACTURA
                        facturaCliente = clienteActual.configurarPlan(planEscogido, clienteActual, proveedorActual, sede,coordenadaX, coordenadaY)

                        labelInfo = tk.Label(frameInformacion,bg="gray82", text="Tu plan ha sido configurado correctamente. A continuación puedes visualizar tu factura.", font=("Times", 12))
                        labelInfo.place(relx=0.15, relwidth=0.7)

                        # SE PROCEDE A CONFIGURAR LA FACTURA 
                        facturaCliente = facturaCliente.generarFactura(clienteActual,proveedorActual)

                        labelFactura = tk.Label(frameInformacion,bg="gray82", text=f"{facturaCliente.__str__()}", font=("Times", 12), borderwidth=2, relief='solid')
                        labelFactura.place(relx=0.15, rely=0.1, relwidth=0.7)

                        labelInfo1 = tk.Label(frameInformacion,bg="gray82", text="Realiza el pago de tu membresía a continuación (Ingrese el valor a pagar): ", font=("Times", 12), borderwidth=2,relief='solid')
                        labelInfo1.place(relx=0.15, rely=0.6, relwidth=0.7)

                        entryPago = tk.Entry(frameInformacion, state="normal")
                        entryPago.place(relx=0.35, rely= 0.7, relheight = 0.1, relwidth = 0.3)

                        # # # SE AJUSTAN LOS BOTONES PARA INGRESAR LA OPCION DEL PROVEEDOR
                        aceptar_buttonCoor = tk.Button(frameInformacion, text="Aceptar", command=aceptarPago)
                        borrar_buttonCoor = tk.Button( frameInformacion, text="Cancelar proceso", command=programa)
                        aceptar_buttonCoor.place(relx=0.4, rely=0.85)
                        borrar_buttonCoor.place(relx=0.55, rely=0.85)

                     # ////////////////////////////////////////////////////////////////////////////////////////////////
                     # EXCEPCIONES
                     try:
                        planEscogido = entryPlan.get().upper()

                        if planEscogido=="":
                           raise ErrorEspacios()

                        if not (planEscogido in planes):
                           raise DatosIncorrectos()
                        
                     except DatosIncorrectos as e:

                        for widget in frameInformacion.winfo_children():
                           widget.destroy()

                        messagebox.showwarning(title="Error", message="ERROR DATOS", detail=f"{str(e)}")
                        return adquisicionPlan()
                     
                     except ErrorEspacios as e:

                        for widget in frameInformacion.winfo_children():
                           widget.destroy()

                        messagebox.showwarning(title="Error", message="ERROR ESPACIOS", detail=f"{str(e)}")
                        return adquisicionPlan()


                     labelPlan.destroy()
                     entryPlan.destroy()
                     aceptar_buttonPlan.destroy()
                     borrar_buttonPlan.destroy()
                     planesDisponibles.destroy()

                     coordenadas = [(0,40,0,40),(0,40,60,100), (50,80,0, 30), (60,100,40,80) ]
                     labelCoordenadas = tk.Label(frameInformacion,bg="gray82", text="Digita las coordenadas de tu ubicación, estas tienen un límite de acuerdo con tu sede.\n\n", font=("Times", 12), borderwidth=2,relief='solid')
                     labelCoordenadas.place(relx=0.15, relwidth=0.7)

                     if sede=="A":
                        textoCoor = labelCoordenadas["text"]
                        labelCoordenadas.config(text=f"{textoCoor}Límite Inferior en X: {coordenadas[0][0]}\nLímite Superior en X: {coordenadas[0][1]}\n Límite Inferior en Y: {coordenadas[0][2]}\n Límite Superior en Y: {coordenadas[0][3]}")
                     elif sede=="B":
                        textoCoor = labelCoordenadas["text"]
                        labelCoordenadas.config(text=f"{textoCoor}Límite Inferior en X: {coordenadas[1][0]}\nLímite Superior en X: {coordenadas[1][1]}\n Límite Inferior en Y: {coordenadas[1][2]}\n Límite Superior en Y: {coordenadas[1][3]}")
                     elif sede=="C":
                        textoCoor = labelCoordenadas["text"]
                        labelCoordenadas.config(text=f"{textoCoor}Límite Inferior en X: {coordenadas[2][0]}\nLímite Superior en X: {coordenadas[2][1]}\n Límite Inferior en Y: {coordenadas[2][2]}\n Límite Superior en Y: {coordenadas[2][3]}")
                     elif sede=="D":
                        textoCoor = labelCoordenadas["text"]
                        labelCoordenadas.config(text=f"{textoCoor}Límite Inferior en X: {coordenadas[3][0]}\nLímite Superior en X: {coordenadas[3][1]}\n Límite Inferior en Y: {coordenadas[3][2]}\n Límite Superior en Y: {coordenadas[3][3]}")

                     # SE CONSTRUYE EL FIELDFRAME QUE SOLICITA LOS DATOS CORRESPONDIENTES
                     criterios = ["Tipo de Usuario", "Coordenada en X", "Coordenada en Y"]
                     fp =FieldFrame(frameInformacion, "Datos solicitados", criterios, "Valor", ["Cliente","", ""])
                     fp.configure(borderwidth=1, relief="groove",bg="gray82")
                     fp.place(relx=0.15, rely=0.45, relheight=0.5)

                     # # # SE AJUSTAN LOS BOTONES PARA INGRESAR LA OPCION DEL PROVEEDOR
                     aceptar_buttonCoor = tk.Button(frameInformacion, text="Aceptar", command=aceptarCoordenadas)
                     borrar_buttonCoor = tk.Button( frameInformacion, text="Borrar", command=fp.borrarValores)
                     aceptar_buttonCoor.place(relx=0.7, rely=0.7)
                     borrar_buttonCoor.place(relx=0.8, rely=0.7)

                  # /////////////////////////////////////////////////////////////////////////////////////////////////////
                  # SE ELIMINAN LOS ELEMENTOS YA CREADOS
                  opcionEscogida = entry.get()
                  # EXCEPCIONES
                  try:

                     if opcionEscogida == "":
                        raise ErrorEspacios()
                     
                     try:
                        opcionEscogida = int(opcionEscogida)

                     except ValueError:
                        raise ErrorLetra()
                     
                     if not(1<= opcionEscogida <= len(proveedoresSede)):
                        raise ErrorOpElegida()

                     
                  except ErrorEspacios as e:

                     for widget in frameInformacion.winfo_children():
                        widget.destroy()

                     messagebox.showerror(title="Error", message="ERROR ESPACIOS", detail=f"{str(e)}")
                     return adquisicionPlan()

                  except ErrorLetra as e:

                     for widget in frameInformacion.winfo_children():
                        widget.destroy()

                     messagebox.showerror(title="Error", message="ERROR TIPO DE DATO", detail=f"{str(e)}")
                     return adquisicionPlan()
                  
                  except ErrorOpElegida as e:

                     for widget in frameInformacion.winfo_children():
                        widget.destroy()

                     messagebox.showerror(title="Error", message="ERROR OPCIÓN ELEGIDA", detail=f"{str(e)}")
                     return adquisicionPlan()

                  label.destroy()
                  entry.destroy()
                  proveedoresDisponibles.destroy()
                  aceptar_button.destroy()
                  borrar_button.destroy()

                  # SE GUARDAN LOS OBJETOS
                  proveedorActual = proveedoresSede[opcionEscogida-1]
                  clienteActual = proveedorActual.crearCliente(nombre,documento)
                  

                  # SE VERIFICA QUE HAYAN CUPOS DISPONIBLES
                  if len(proveedorActual.getClientes()) < proveedorActual.getCLIENTES_MAX():

                     planesDisponibles = tk.Label(frameInformacion,bg="gray82", text="Los planes disponibles con este proveedor son: \n\n", font=("Times", 12), borderwidth=2, relief='solid')
                     planesDisponibles.place(relx=0.05, rely=0.02, relheight=0.9, relwidth=0.5)

                     planes = proveedorActual.planesDisponibles(clienteActual)

                     for plan in planes:

                        texto = planesDisponibles["text"]

                        if plan == "BASIC":
                           planesDisponibles.config(text=f"{texto} PLAN BASIC:\n Megas de subida: {proveedorActual.getPlanes().getBASIC()[0]} \n Megas de bajada: {proveedorActual.getPlanes().getBASIC()[1]} \n Precio del plan: {proveedorActual.getPlanes().getBASIC()[2]} \n\n")
                        elif plan == "STANDARD":
                           planesDisponibles.config(text=f"{texto} PLAN STANDARD:\n Megas de subida: {proveedorActual.getPlanes().getSTANDARD()[0]} \n Megas de bajada: {proveedorActual.getPlanes().getSTANDARD()[1]} \n Precio del plan: {proveedorActual.getPlanes().getSTANDARD()[2]} \n\n")
                        elif plan == "PREMIUM":
                           planesDisponibles.config(text=f"{texto} PLAN PREMIUM:\n Megas de subida: {proveedorActual.getPlanes().getPREMIUM()[0]} \n Megas de bajada: {proveedorActual.getPlanes().getPREMIUM()[1]} \n Precio del plan: {proveedorActual.getPlanes().getPREMIUM()[2]} \n\n")
