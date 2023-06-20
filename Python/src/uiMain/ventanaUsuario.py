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


                     # SE CONFIGURA PARA PEDIR LA OPCION DESEADA
                     labelPlan = tk.Label(frameInformacion, text="Digita el nombre del plan que deseas: ", font=("Times", 12), bg="gray82")
                     labelPlan.place(relx=0.55, rely = 0.2, relheight = 0.15, relwidth = 0.5)
                     entryPlan = tk.Entry(frameInformacion, state="normal")
                     entryPlan.place(relx=0.65, rely= 0.4, relheight = 0.1, relwidth = 0.3)

                     # # SE AJUSTAN LOS BOTONES PARA INGRESAR LA OPCION DEL PROVEEDOR
                     aceptar_buttonPlan = tk.Button(frameInformacion, text="Aceptar", command=aceptarPlan)
                     borrar_buttonPlan = tk.Button( frameInformacion, text="Cancelar proceso", command=programa)
                     aceptar_buttonPlan.place(relx=0.7, rely=0.55)
                     borrar_buttonPlan.place(relx=0.8, rely=0.55)
                     
                  else:
                     info = tk.Label(frameInformacion,bg="gray82", text="El proveedor no tiene cupos disponibles. Intenta en otro momento.", font=("Times", 12), borderwidth=2,relief='solid')
                     info.place(relx=0.25, rely=0.02, relheight=0.5, relwidth=0.5)

               # ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
               # EXCEPCIONES
               try:
                  # SE GUARDAN LOS DATOS INGRESADOS
                  nombre = fp.getValor("Nombre").lower()
                  documento = fp.getValor("Documento")
                  sede = fp.getValor("Sede").upper()

                  try:
                     documento = int(documento)
                  except ValueError:
                     raise ErrorLetra()

                  if sede!="A" and sede!="B" and sede!="C" and sede!="D":
                     raise DatosIncorrectos()

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
               
               except DatosIncorrectos as e:

                  for widget in frameInformacion.winfo_children():
                     widget.destroy()

                  messagebox.showwarning(title="Error", message="ERROR DATOS", detail=f"{str(e)}")
                  return adquisicionPlan()


               # SE ELIMINAN LOS FRAMES YA CREADOS
               for widget in frameInterior.winfo_children():
                  widget.destroy()
               fp.destroy()
               frameInterior.destroy()

               # PRIMER MÉTODO DE LA FUNCIONALIDAD-BUSCA LOS PROVEEDORES DE LA SEDE INDICADA---SE CONFIGURA EL LABEL
               proveedoresSede = ProveedorInternet.proveedorSede(sede)
               proveedoresDisponibles = tk.Label(frameInformacion,bg="gray82", text="Los proveedores disponibles en tu sede son: \n\n", font=("Times", 12), borderwidth=2,relief='solid')
               proveedoresDisponibles.place(relx=0.25, rely=0.1, relheight=0.35, relwidth=0.5)

               for pro in range(0, len(proveedoresSede)):
                  texto = proveedoresDisponibles["text"]
                  proveedoresDisponibles.config(text=f"{texto} {pro+1}. {proveedoresSede[pro].getNombre()} \n")
               

               # SE CONFIGURA PARA PEDIR LA OPCION DESEADA
               label = tk.Label(frameInformacion, text="Digita el número de la opción que deseas: ", font=("Times", 12), bg="gray82")
               label.place(relx=0.25, rely = 0.5, relheight = 0.15, relwidth = 0.5)
               entry = tk.Entry(frameInformacion, state="normal")
               entry.place(relx=0.35, rely= 0.65, relheight = 0.1, relwidth = 0.3)

               # SE AJUSTAN LOS BOTONES PARA INGRESAR LA OPCION DEL PROVEEDOR
               aceptar_button = tk.Button(frameInformacion, text="Aceptar", command=aceptar)
               borrar_button = tk.Button( frameInformacion, text="Cancelar", command=programa)
               aceptar_button.place(relx=0.37, rely=0.8)
               borrar_button.place(relx=0.55, rely=0.8)

            # //////////////////////////////////////////////////////////////////////////////////////////////////////
            # SE ELIMINAN LOS FRAMES YA CREADOS----PARA CAMBIAR ENTRE FUNCIONALIDADES
            for widget in frameInformacion.winfo_children():
               widget.destroy()


            # SE AJUSTA EL TITULO Y DESCRIPCION DE LA FUNCIONALIDAD
            self.titulo.config(text="ADQUISICIÓN DE UN PLAN", font=("Times",20))
            self.descripcion.config(wraplength=500, text="Esta funcionalidad tiene por objetivo que un usuario nuevo pueda registrarse en el sistema y a su vez compre un plan con las características específicas que brinda el proveedor de su preferencia.")

            # SE CONSTRUYE EL FIELDFRAME QUE SOLICITA LOS DATOS CORRESPONDIENTES
            criterios = ["Tipo de Usuario", "Nombre", "Documento", "Sede"]
            fp =FieldFrame(frameInformacion, "Datos solicitados", criterios, "Valor", ["Cliente", "", "", ""])
            fp.configure(borderwidth=1, relief="raised")
            fp.pack(fill="y",pady=10, padx=5)

            # SE AJUSTAN LOS BOTONES DE ACEPTAR Y BORRAR
            frameInterior = tk.Frame(frameInformacion, bg="gray84", height=100, width=300)
            frameInterior.pack( expand=True, fill="none", padx=100, pady=20,anchor="n")
            aceptar_button = tk.Button(frameInterior, text="Aceptar", command=eventoAceptar)
            borrar_button = tk.Button( frameInterior, text="Borrar", command=fp.borrarValores)
            aceptar_button.pack( side="left", pady=20, padx=50, anchor="sw")
            borrar_button.pack( side="left", pady=20, padx=40, anchor="se")

# /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         # 2.  FUNCIONALIDAD VISUALIZAR DISPOSITIVOS
         def visuaDispo():

            for widget in frameInformacion.winfo_children():
                     widget.destroy()

            #  VERIFICACIONES Y RESULTADOS
            def funcionalidadVdispo():
               
               # FRAME DE CONTROL
               frameInterior1=tk.Frame(frameInformacion, bg="gray92", height=100, width=500)
               frameInterior1.pack_configure(expand=True, fill="both", padx=130, pady=40)
               frameInterior2=tk.Frame(frameInterior1, bg="gray92", height=100, width=300,borderwidth=2,relief='solid')
               frameInterior2.pack( expand=True, fill="none", padx=50, pady=20,anchor="c")
               
               # VERIFICAR EXCEPCIONES DATOS INGRESADOS
               try:
                  documento = fp.getValor("Documento")
                  proveedor= fp.getValor("Proveedor").lower()

                  try:
                     documento = int(fp.getValor("Documento"))
                  except ValueError:
                     raise ErrorLetra()
                  
                  # OBTENER LOS OBJETOS CLIENTE Y PROVEEDOR
                  for p in ProveedorInternet.getProveedoresTotales():
                     if p.getNombre()==proveedor:
                        proveedorActual=p
                        break
                     else:
                        proveedorActual=None

                  if proveedorActual==None:
                     raise DatosIncorrectos()
                  
                  clienteActual = proveedorActual.verificarCliente(int(documento)) #PRIMER METODO  
                  
                  if clienteActual==None :
                     raise DatosIncorrectos()
                  
                  
               except ErrorEspacios as e:
                  messagebox.showwarning(title="Error",message="ERROR ESPACIOS",detail=f"{str(e)}")
                  for widget in frameInformacion.winfo_children():
                     widget.destroy()
                  return visuaDispo()
               
               except ErrorLetra as l:                     
                  messagebox.showerror(title="Error",message="ERROR TIPO DATO",detail=f"{str(l)}")                  
                  for widget in frameInformacion.winfo_children():
                     widget.destroy()
                  return visuaDispo()
               
               except DatosIncorrectos as d:
                  messagebox.showwarning(title="Error",message="ERROR DATOS INCORRECTOS",detail=f"{str(d)}")                  
                  for widget in frameInformacion.winfo_children():
                     widget.destroy()
                  return visuaDispo()
               
               # ELIMINAR Y MOSTRAR INFO NUEVA
               frameInterior.destroy()
               
               # METODO PARA MOSTRAR LOS RESULTADOS FINALES
               def mostrarResultado():

                  # ELIMINAR Y MOSTRAR INFO NUEVA
                  for widget in frameInterior2.winfo_children():
                     widget.destroy()

                  # SE MUESTRAN LOS DISPOSITIVOS 
                  def dispositivos():
                     try:
                        if not (entry.get()==""):

                           # INVOCACIÓN EXCEPCION LETRA
                           try:
                              generacion=int(entry.get())
                           except ValueError:
                              raise ErrorLetra
                           
                           if not(int(entry.get())==1 or int(entry.get())==2 or int(entry.get())==3 ):
                              raise ErrorOpElegida()
                           
                           frameInterior2.pack_configure(expand=True, fill="both", padx=20, pady=20)
                           generacion = entry.get()

                           # ELIMINAR Y MOSTRAR INFO NUEVA
                           for widget in frameInterior2.winfo_children():
                              widget.destroy()

                           # PRESENTACIÓN RESULTADOS
                           lista = clienteActual.getModem().verificarDispositivos(Dispositivo.getDispositivosTotales(), clienteActual.getModem())
                           row = 1 
                           column = 0  
                           
                           if int(generacion) == 1:
                              for dispositivo in lista:
                                    if dispositivo.getGeneracion() == "4G":
                                       label_dispositivo = tk.Label(frameInterior2, text=str(dispositivo))
                                       label_dispositivo.grid(row=row, column=column, padx=15, pady=15, sticky='w')
                                       row += 1  

                                       if row >= 4:
                                          row = 1  
                                          column = 1  
                           elif int(generacion) == 2:
                              for dispositivo in lista:
                                    if dispositivo.getGeneracion() == "5G":
                                       label_dispositivo = tk.Label(frameInterior2, text=str(dispositivo))
                                       label_dispositivo.grid(row=row, column=column, padx=15, pady=15, sticky='w')
                                       row += 1  

                                       if row >= 4:
                                          row = 1  
                                          column = 1  
                           elif int(generacion) == 3:
                              for dispositivo in lista:
                                    label_dispositivo = tk.Label(frameInterior2, text=str(dispositivo))
                                    label_dispositivo.grid(row=row, column=column, padx=25, pady=25, sticky='w')
                                    row += 1  

                                    if row >= 4:
                                       row = 1  
                                       column = 1 
                           
                           aceptar_buttonFinal = tk.Button(frameInterior2, text="Finalizar", command=finalizar)
                           aceptar_buttonFinal.place(relx=0.98, rely=0.029, anchor="ne")
                        elif (entry.get()==""):
                           raise ErrorEspacios()
                        
                     # EXCEPCONES DATOS ENTRADA  
                     except ErrorEspacios as e:                    
                        messagebox.showwarning(title="Error",message="ERROR ESPACIOS",detail=f"{str(e)}")                 
                        for widget in frameInterior2.winfo_children():
                           widget.destroy()
                        return mostrarResultado()
                     
                     except ErrorLetra as l:                     
                        messagebox.showerror(title="Error",message="ERROR TIPO DATO",detail=f"{str(l)}")                  
                        for widget in frameInterior2.winfo_children():
                           widget.destroy()
                        return mostrarResultado()
                     
                     except ErrorOpElegida as o:                     
                        messagebox.showerror(title="Error",message="ERROR OPCIÓN INVÁLIDA",detail=f"{str(o)}")                  
                        for widget in frameInterior2.winfo_children():
                           widget.destroy()
                        return mostrarResultado()
                     
                  # CONTROL FRAME PARA ELEGIR OPC VISUALIZACIÓN
                  frameInterior2.pack_configure( padx=10, pady=10)
                  labelD=tk.Label(frameInterior2, text="Digita por favor el número de la opción que deseas: \nPor favor expande la ventana completamente \npara visualizar todos los dispositivos")
                  labelD.config(bg="gray92")
                  labelD.grid(row=0, column=1, padx=20, pady=5,sticky='w')
                  labelD1 = tk.Label(frameInterior2, text="1. Visualizar dispositivos de 4G.",bg="gray92",)
                  labelD1.grid(row=1, column=1, padx=25, pady=10, sticky="w") 
                  labelD2 = tk.Label(frameInterior2, text="2. Visualizar dispositivos de 5G.",bg="gray92",)
                  labelD2.grid(row=2, column=1, padx=25, pady=10,sticky="w")
                  labelD4 = tk.Label(frameInterior2, text="3. Visualizar todos los dispositivos conectados.",bg="gray92",)
                  labelD4.grid(row=3, column=1, padx=25, pady=10,sticky="w")
                  entry = tk.Entry(frameInterior2, state="normal",width=15)
                  entry.grid(row=0, column=2, sticky='w', padx=25, pady=25)
                  aceptar_button = tk.Button(frameInterior2, text="Aceptar", command=dispositivos)
                  aceptar_button.grid(row=1, column=2, pady=10)

               # REALIZAR EL ABONO A LA DEUDA
               def abonar():

                  # EJECUTAR EL METODO PARA REALIZAR EL ABONO
                  def realizarAbono():

                     try:
                        if not (entry.get()==""):

                           # INVOCACIÓN EXCEPCION LETRA
                           try:
                              valor=int(entry.get())
                           except ValueError:
                              raise ErrorLetra
                           
                           if (clienteActual.getFactura().accionesPagos(1,int(valor)) != None):
                              mostrarResultado()
                        else:
                           raise ErrorEspacios()
                        
                     # EXCEPCIONES DATO ENTRADA 
                     except ErrorEspacios as e:    
                        messagebox.showwarning(title="Error",message="ERROR ESPACIOS",detail=f"{str(e)}")
                        for widget in frameInterior2.winfo_children():
                           widget.destroy()
                        return abonar()
                     
                     except ErrorLetra as l:                     
                        messagebox.showerror(title="Error",message="ERROR TIPO DATO",detail=f"{str(l)}")                  
                        for widget in frameInterior2.winfo_children():
                           widget.destroy()
                        return abonar()
                     
                  # ELIMINAR Y MOSTRAR INFO NUEVA
                  for widget in frameInterior2.winfo_children():
                     widget.destroy()

                  # CONFIGURACIONES DATO ENTRADA PARA ABONAR
                  label = tk.Label(frameInterior2, text="Digita la cantidad a abonar: ")
                  label.grid(row=0, column=0, sticky='we', padx=50, pady=10, columnspan=2)
                  entry = tk.Entry(frameInterior2, state="normal")
                  entry.grid(row=1, column=0, sticky='we', padx=50, pady=20,columnspan=2)
                  aceptar_button = tk.Button(frameInterior2, text="Aceptar", command=realizarAbono)
                  aceptar_button.grid(row=3, column=0, pady=10)
                  cancelar_button = tk.Button(frameInterior2, text="Cancelar", command=programa)
                  cancelar_button.grid(row=3, column=1, pady=10)

               # //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  
               #  Método para pagar el total de la deuda 
               def Pagar():

                  def realizarPago():
                     try:
                        if not (entry.get()==""):

                           # INVOCACIÓN EXCEPCION LETRA
                           try:
                              valor=int(entry.get())
                           except ValueError:
                              raise ErrorLetra()
                           
                           # Segundo Metodo Funcionalidad
                           if (clienteActual.getFactura().accionesPagos(2,int(entry.get())) != None):
                              mostrarResultado()

                        elif (entry.get()==""):
                           raise ErrorEspacios()
                        
                     # EXCEPCIONES DATO ENTRADA  
                     except ErrorEspacios as e:                    
                        messagebox.showwarning(title="Error",message="ERROR ESPACIOS",detail=f"{str(e)}")                 
                        for widget in frameInterior2.winfo_children():
                           widget.destroy()
                        return Pagar()
                     
                     except ErrorPagoTotal as a:                     
                        messagebox.showerror(title="Error",message="ERROR PAGO TOTAL",detail=f"{str(a)}")                  
                        for widget in frameInterior2.winfo_children():
                           widget.destroy()
                        return Pagar()
                     
                     except ErrorLetra as l:                     
                        messagebox.showerror(title="Error",message="ERROR TIPO DATO",detail=f"{str(l)}")                  
                        for widget in frameInterior2.winfo_children():
                           widget.destroy()
                        return Pagar()
                     
                  # /////////////////////////////////////////////////////////////////////////////////////////////////  
                  # ELIMINAR Y MOSTRAR INFO NUEVA
                  for widget in frameInterior2.winfo_children():
                     widget.destroy()

                  # CONFIGURACIONES DATO ENTRADA PARA ABONAR
                  label = tk.Label(frameInterior2, text="Digita la cantidad total a pagar ")
                  label.grid(row=0, column=0, sticky='we', padx=50, pady=10,columnspan=2)
                  entry = tk.Entry(frameInterior2, state="normal")
                  entry.grid(row=1, column=0, sticky='we', padx=50, pady=20,columnspan=2)
                  aceptar_button = tk.Button(frameInterior2, text="Aceptar", command=realizarPago)
                  aceptar_button.grid(row=3, column=0, pady=10)
                  cancelar_button = tk.Button(frameInterior2, text="Cancelar", command=programa)
                  cancelar_button.grid(row=3, column=1, pady=10)

               # CONFIGURACIONES WITGETS Y VERIFICACION DE PAGOS ATRASADOS
               if clienteActual.getFactura().verificarFactura(clienteActual.getFactura()) == False:
                  frameInterior2.pack_configure( padx=10, pady=10)
                  L1=tk.Label(frameInterior2, text=f"\nTienes {clienteActual.getFactura().getPagosAtrasados()} pagos atrasados, para acceder a esta función debes tener 2 o menos pagos atrasados.\nTu factura actualmente está por un valor de $ {clienteActual.getFactura().getPrecio()}, recuerda que el valor mensual de tu plan es de ${clienteActual.getPlan()[2]}\nTenemos las siguientes opciones de pago:")
                  L1.config(wraplength=300,bg="gray92",)
                  L1.grid(row=0, column=0, padx=50, pady=5,sticky="w", columnspan=2) 

                  l1 = tk.Label(frameInterior2, text="1. Abonar a la deuda.",bg="gray92",)
                  l1.grid(row=1, column=0, padx=50, pady=5, sticky="w") 
                  l2 = tk.Label(frameInterior2, text="2. Pagar la totalidad de la deuda.",bg="gray92",)
                  l2.grid(row=2, column=0, padx=50, pady=10,sticky="w") 
                        
                  b1 = tk.Button(frameInterior2, text="Seleccionar", command=abonar)
                  b1.grid(row=1, column=1, padx=20, pady=10)
                  b2 = tk.Button(frameInterior2, text="Seleccionar", command=Pagar)
                  b2.grid(row=2, column=1, padx=20, pady=10)

            # ///////////////////////////////////////////////////////////////////////////////////////////////////////////
            # CONFIGURACION TITULO FUNCIONALIDAD   
            self.titulo.config(text="VISUALIZAR DISPOSITIVOS CONECTADOS", font=("Times",20))
            self.descripcion.config(wraplength=500, text = "Es la encargada de mostrar por pantalla la lista de dispositivos, que se encuentran vinculados a un router asociado a un cliente, presentando por consola algunas de las características básicas que posee cada dispositivo.", font=("Times",12))
            
            # CONSTRUCCION FIELDFRAME
            criterios = ["Tipo de Usuario","Documento", "Proveedor"]
            valores=["\tCliente","",""]

            frameInterior=tk.Frame(frameInformacion, bg="gray95", height=100, width=300)
            frameInterior.pack( expand=True, fill="none", padx=100, pady=10,anchor="n")

            fp = FieldFrame(frameInterior,  "Datos", criterios, "Valor", valores)
            fp.configure(borderwidth=0,bg="gray95",)
            fp.pack(fill="y",pady=3, padx=5)
            
            # BOTONES DE INICIO PARA VERIFICAR CLIENTE Y PROVEEDOR INGRESADO
            aceptar_button = tk.Button(frameInterior, text="Aceptar", command=funcionalidadVdispo)
            borrar_button = tk.Button( frameInterior, text="Borrar", command=fp.borrarValores)
            aceptar_button.pack( side="left", pady=20, padx=50, anchor="sw")
            borrar_button.pack( side="left", pady=20, padx=40, anchor="se")

# ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         # 3. FUNCIONALIDAD MEJORA TU PLAN
         def mejoraTuPlan():

            def eventoAceptar():

               def negar(): #Para salir a la pagina principal otra vez
                  programa()
               def aceptar(): #Este metodo es para cambiar el respectivo plan

                  def aceptarPago():
                     
                     try:
                        if not (entryPagos.get()==""):

                           # INVOCACIÓN EXCEPCION LETRA
                           try:
                              valor=int(entryPagos.get())
                           except ValueError:
                              raise ErrorLetra()
                           
                        elif (entryPagos.get()==""):
                           raise ErrorEspacios()
                        
                        clienteActual.getFactura().accionesPagos(2, entryPagos.get())

                     # EXCEPCIONES DATO ENTRADA   
                     except ErrorPagoTotal as e:
                        for widget in frameInformacion.winfo_children():
                           widget.destroy()
                        messagebox.showerror(title="Error", message="ERROR PAGO TOTAL", detail=f"{str(e)}")
                        return mejoraTuPlan()
 
                     except ErrorEspacios as e:                    
                        messagebox.showwarning(title="Error",message="ERROR ESPACIOS",detail=f"{str(e)}")                 
                        for widget in frameInformacion.winfo_children():
                           widget.destroy()
                        return mejoraTuPlan()
                     
                     except ErrorLetra as l:                     
                        messagebox.showerror(title="Error",message="ERROR TIPO DATO",detail=f"{str(l)}")                  
                        for widget in frameInformacion.winfo_children():
                           widget.destroy()
                        return mejoraTuPlan()

                     # SE ELIMINAN LOS FRAMES YA CREADOS
                     for widget in frameInformacion.winfo_children():
                        widget.destroy()

                     messagebox.showinfo(title = "Fin funcionalidad", message = "Proceso Finalizado",detail = "¡Tu cambio de servidor y proveedor se ha realizado con éxito!")
                     finalizar()

                     clienteActual.getModem().getServidorAsociado().getRouters().remove(clienteActual.getModem())
                     clienteActual.getProveedor().getClientes().remove(clienteActual)
                              
                     clienteActual.setPlan(mejoraIdeal[4])
                                 
                     servidorNuevo = mejoraIdeal[1]
               
                     proveedorNuevo = servidorNuevo.getProveedor()

                     clienteActual.setProveedor(proveedorNuevo)
                     clienteActual.getModem().setServidorAsociado(servidorNuevo)
                     clienteActual.getModem().setUp(clienteActual.getPlan()[0])
                     clienteActual.getModem().setDown(clienteActual.getPlan()[1])
                     clienteActual.getModem().setAntenaAsociada(Antena.antenasSede(clienteActual.getModem().getSede(), clienteActual.getProveedor().getNombre()))
                     clienteActual.getModem().setIntensidadFlujo(int(mejoraIdeal[3]))
                           
                     servidorNuevo.getRouters().append(clienteActual.getModem())
                     clienteActual.getProveedor().getClientes().append(clienteActual)
      
                     clienteActual.getFactura().setPrecio(clienteActual.getPlan()[2])
                     clienteActual.getFactura().setMesActivacion(Mes.MAYO.value)
                     

                  for widget in frameInformacion.winfo_children():
                     widget.destroy()

                  if (not clienteActual.getFactura().verificarFactura(clienteActual.getFactura()) or clienteActual.getFactura().getPagosAtrasados() >0 ):

                     labelPagosAtrasados = tk.Label(frameInformacion, bg="gray82", text = f"\nTienes   {clienteActual.getFactura().getPagosAtrasados()}   pagos atrasados, para continuar debes saldar la deuda. \nTu factura actualmente está por un valor de ${clienteActual.getFactura().getPrecio()}. Ingresa la cantidad: ", borderwidth=2, relief="solid")
                     labelPagosAtrasados.place(relx=0.2, rely=0.1, relwidth=0.6, relheight=0.2)

                     entryPagos = tk.Entry(frameInformacion, state="normal")
                     entryPagos.place(relx=0.4, rely=0.5)

                     aceptarPago_button = tk.Button(frameInformacion, text="Aceptar", command=aceptarPago)
                     aceptarPago_button.place(relx=0.45, rely=0.75)

               try:
                  #Se guardan los datos 
                  nombre = fp.getValor("Nombre").lower()
                  documento = fp.getValor("Documento")
                  proveedor = fp.getValor("Proveedor").lower()

                  try:
                     documento = int(documento)
                  except ValueError:
                     raise ErrorLetra()

                  for p in ProveedorInternet.getProveedoresTotales():
                     if p.getNombre()==proveedor:
                        proveedorActual=p
                        break
                     else:
                        proveedorActual=None

                  if proveedorActual==None:
                     raise DatosIncorrectos()
                     
                  clienteActual = proveedorActual.verificarCliente( documento, nombre)
                  
                  if  clienteActual == None:
                     raise DatosIncorrectos()

               except ErrorEspacios as e:
                  for widget in frameInformacion.winfo_children():
                        widget.destroy()
                  messagebox.showwarning(title="Error", message="ERROR ESPACIOS", detail=f"{str(e)}")
                  return mejoraTuPlan()
               
               except DatosIncorrectos as e:
                  for widget in frameInformacion.winfo_children():
                           widget.destroy()
                  messagebox.showwarning(title="Error", message="ERROR DATOS", detail=f"{str(e)}")
                  return mejoraTuPlan()
               
               except ErrorLetra as e:    
                  messagebox.showerror(title="Error",message="ERROR TIPO DATO",detail=f"{str(e)}")  
                  for widget in frameInformacion.winfo_children():
                     widget.destroy()
                  return mejoraTuPlan()
                  
      
               localidad = clienteActual.getModem().getSede() #Sede del cliente 
               listaServLoc = Servidor.buscarServidores(localidad) #Segundo metodo - Total servidores de la localidad
               mejoraIdeal = clienteActual.getModem().protocoloSeleccionServidor(listaServLoc, clienteActual) #Tercer metodo - se obtiene la recomendacion
               
               if mejoraIdeal == None: #Avisamos que ya esta con el mejor plan 
                  etiquetaDatos = tk.Label(frameInformacion, bg="gray82", text="Al realizar las verificaciones encontramos que hasta el momento el plan de tu proveedor es el mejor.", borderwidth=2, relief="solid")
                  etiquetaDatos.pack(expand=True)
                        
               else: ##Presentamos los datos del plan recomendado y del plan actual

                  labelIn = tk.Label(frameInformacion,bg="gray82", text= f"Al realizar las verificaciones encontramos que este nuevo servicio puede ser mejor para ti.", font=("Times", 12))
                  labelIn.place(relx=0.15, relwidth=0.75)

                  labelRec = tk.Label(frameInformacion, bg="gray82", text=f"Te presentamos la siguiente recomendación:\n Intensidad de flujo:  {str(int(mejoraIdeal[3]))}\nProveedor: {mejoraIdeal[2].getNombre()}\n Plan: {clienteActual.getNombrePlan()}\nMegas de subida: {str(mejoraIdeal[4][0])}\nMegas de bajada: {str(mejoraIdeal[4][1])}\nPrecio:{str(mejoraIdeal[4][2])}", font=("Times",12), borderwidth=2, relief="solid")
                  labelRec.place(relx=0.05, rely=0.15 , relwidth=0.4) 

                  etiquetaDatosActuales = tk.Label(frameInformacion, bg="gray82",text = f"Tu plan actual tiene las siguientes características:\nIntensidad de flujo: {str(mejoraIdeal[0])} \nProveedor: {clienteActual.getProveedor().getNombre()} \nPlan: {clienteActual.getNombrePlan()} \nMegas de subida:{str(clienteActual.getPlan()[0])} \nMegas de bajada: {str(clienteActual.getPlan()[1])} \nPrecio: {str(clienteActual.getPlan()[2])}", font=("Times",12), borderwidth=2, relief="solid")
                  etiquetaDatosActuales.place(relx=0.5, rely=0.15 , relwidth=0.45)

                  # SE CONFIGURA PARA PEDIR LA OPCION DESEADA
                  label1 = tk.Label(frameInformacion, text="¿Deseas cambiar tu plan por la recomendacion dada? ", font=("Times", 12), bg="gray82")
                  label1.place(relx=0.25, rely = 0.7, relheight = 0.15, relwidth = 0.5)
                     
                  # SE AJUSTAN LOS BOTONES PARA INGRESAR LA OPCION DEL PROVEEDOR
                  aceptar_button = tk.Button(frameInformacion, text=" Si ", command=aceptar)
                  aceptar_button.place(relx=0.5 , rely=0.85)
                  negar_button = tk.Button( frameInformacion, text="No", command = negar)
                  negar_button.place(relx=0.55, rely=0.85)
                        
               
               #elimina los frames crados 
               for widget in frameInterior.winfo_children():
                  widget.destroy()
               fp.destroy()
               frameInterior.destroy()     


            # PARA PASAR DE UNA FUNCIONALIDAD A OTRA
            for widget in frameInformacion.winfo_children():
                     widget.destroy()
            
            # SE AJUSTA EL TITULO Y DESCRIPCION DE LA FUNCIONALIDAD
            self.titulo.config(text="MEJORA TU PLAN", font=("Times",20))
            self.descripcion.config(wraplength=500, text = "Es la encargada de verificar si el usuario tiene posibilidad de mejorar su plan. Caso tal de que se pueda y el usuario quiere cambiarse a ese plan sugerido, hara todo el proceso para cambiar el plan anterior por el nuevo.", font=("Times",12))
               
            ##Crear field frame inicial 
            criterios = ["Tipo de Usuario", "Documento","Nombre", "Proveedor"]
            fp = FieldFrame(frameInformacion,  "Datos", criterios, "Valor", ["Cliente","","",""])
            fp.configure(borderwidth=1, relief="raised")
            fp.pack(fill="y",pady=10, padx=5)

            ## Botones aceptar y borrar 
            frameInterior = tk.Frame(frameInformacion, bg="gray82", height=100, width=300)
            frameInterior.pack( expand=True, fill="none", padx=100, pady=10,anchor="n")
            aceptar_button = tk.Button(frameInterior, text="Aceptar", command=eventoAceptar)
            borrar_button = tk.Button( frameInterior, text="Borrar", command = fp.borrarValores)
            aceptar_button.pack( side="left", pady=20, padx=50, anchor="sw")
            borrar_button.pack( side="left", pady=20, padx=40, anchor="se")

# ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         # 4. FUNCIONALIDAD TEST
         def test_command():

            for widget in frameInformacion.winfo_children():
                     widget.destroy()

            # global self.frame_display,proveedor_cliente,self.cliente_

            self.titulo.config(text="TEST DE VELOCIDAD", font=("Times", 20))
            self.descripcion.config(wraplength=500, text = "Evalua y experimenta la velocidad de subida y de bajada y la fluctación de la red examinada.", font=("Times",12))
            
            criterios = ["Tipo de Usuario","ID", "Proveedor"]
            valores=["\tCliente","",""]
            
            self.frame_display=tk.Frame(frameInformacion, bg="gray95", height=100, width=500)
            self.frame_display.pack( expand=True, pady=20, anchor="n")

            fp = FieldFrame(self.frame_display,  "Datos", criterios, "Valor", valores)
            fp.configure(borderwidth=0,bg="gray95",)
            fp.pack(fill="y",pady=3, padx=5)
            
            def inv():

               try:
                  
                  documento = fp.getValor("ID")
                  proveedor= fp.getValor("Proveedor").lower()

                  try:
                     documento = int(fp.getValor("ID"))
                  except ValueError:
                     raise ErrorLetra()

                  for p in ProveedorInternet.getProveedoresTotales():
                     if p.getNombre()==proveedor:
                        self.proveedor_cliente=p
                        break

                  if self.proveedor_cliente==None:
                     raise DatosIncorrectos()
                  
                  for cliente in self.proveedor_cliente.getClientes():
                     
                     if cliente.getID()==int(documento):
                        self.cliente_=cliente
                        self.frame_display.destroy()
                        func_test()
                        break

                  if self.cliente_ == None:
                     raise DatosIncorrectos()
                  
               except ErrorLetra as l:                     
                  messagebox.showerror(title="Error",message="ERROR TIPO DATO",detail=f"{str(l)}")                  
                  for widget in frameInformacion.winfo_children():
                     widget.destroy()
                  return test_command()
               
               except ErrorEspacios as e:

                  messagebox.showwarning(title="Error",message="ERROR ESPACIOS",detail=f"{str(e)}")
                  for widget in frameInformacion.winfo_children():
                     widget.destroy()
                  return test_command()
               
               except DatosIncorrectos as e:

                  messagebox.showwarning(title="Error",message="ERROR DATOS",detail=f"{str(e)}")
                  for widget in frameInformacion.winfo_children():
                     widget.destroy()
                  return test_command()
 
            aceptar_button = tk.Button(self.frame_display, text="Aceptar", command=inv)
            borrar_button = tk.Button( self.frame_display, text="Borrar", command=fp.borrarValores)
            aceptar_button.pack( side="left", pady=20, padx=50, anchor="sw")
            borrar_button.pack( side="left", pady=20, padx=40, anchor="se")

         def func_test():

            def function():

               def pagar():

                  self.cliente_.getModem().setAntenaAsociada(antena_nueva)
                  messagebox.showinfo(title = "Procedimiento", 
                        message = "Proceso Finalizado",
                        detail = "Se ha cambiado de antena con éxito")
                  eventoUsuario()

               def cancelar():

                  eventoUsuario()
               
               cobertura_actual=self.cliente_.getModem().getAntenaAsociada()
               antena_nueva=cobertura_actual.rastrearGeneracionCompatible(antenas_sede,self.cliente_.getModem())
               
               if antena_nueva.getProveedor().getNombre()==self.cliente_.getProveedor().getNombre():
                  self.cliente_.getModem().setAntenaAsociada(antena_nueva)
                  messagebox.showinfo(title = "Procedimiento", 
                        message = "Proceso Finalizado",
                        detail = "Se ha cambiado de antena")
               else:
                  self.frame_display.destroy()
                  self.frame_display=tk.Frame(frameInformacion, bg="grey85", height=200, width=500)
                  self.frame_display.config(borderwidth=2,  relief="solid")
                  self.frame_display.place(relx=0.5, rely=0.5, relheight=0.5,relwidth=0.5, anchor="center")

                  text_info=tk.Label(self.frame_display,text="Esta antena pertenece a otro proveedor, por tanto \nel roaming tiene un costo de 100000")
                  text_info.config(bg="grey85")
                  text_info.grid(row=0,column=0,padx=20,pady=10)
                  entry_costo=tk.Entry(self.frame_display)
                  entry_costo.insert(tk.END,"$ 100.000")
                  entry_costo.config(state="disabled")
                  entry_costo.grid(row=1,column=0,pady=10)
                  pagar_button=tk.Button(self.frame_display,text="Pagar",command=pagar)
                  pagar_button.grid(row=2,column=0,pady=5)
                  cancelar_button=tk.Button(self.frame_display,text="Cancelar",command=cancelar)
                  cancelar_button.grid(row=3,column=0,pady=5)




            def function2():
               eventoUsuario()

            antena=self.cliente_.getModem().getAntenaAsociada()
            resultado=self.cliente_.getModem().test(self.cliente_)
            antenas_sede=Antena.antenasSede(self.cliente_.getModem().getSede())
            
            dispositivos_cliente=self.cliente_.getModem().verificarDispositivos(Dispositivo.getDispositivosTotales(),self.cliente_.getModem())
            
            self.frame_display=tk.Frame(frameInformacion, bg="grey85", height=100, width=500)
            self.frame_display.place(relx=0.5, rely=0.5, relheight=0.5,relwidth=0.5, anchor="center")

            if "saturada" in resultado:

               messagebox.showinfo(title = "Saturación en Red", 
               message = resultado,
               detail = "Se recomienda remover dispositivos")


               text_disp = tk.Label(self.frame_display, text="Dispositivos:")
               text_disp.grid(row=0, column=0, padx=50, pady=10,sticky="w")


               for i,dispositivo in enumerate(dispositivos_cliente):
                  text_disps = tk.Label(self.frame_display, text=dispositivo)
                  text_disps.grid(row=i+1, column=0, padx=50, pady=10,sticky="w")  


            elif "incompatible" in resultado:

               messagebox.showinfo(title = "Incompatibilidad", 
               message = resultado,
               detail = "El test ha detectado problemas con tu red.\nLa generación de tu router es incompatible con la antena a la que estás conectado.")
               self.frame_display.config(borderwidth=2, relief="solid",height=200, width=500)
               text_antena = tk.Label(self.frame_display, bg="grey85",text="Se ha encontrado una antena \ncompatible y accesible:")
               text_antena.grid(row=0, column=0, padx=50, pady=10,sticky="w") 
               text_antenas = tk.Label(self.frame_display, bg="grey85",text=antena.rastrearGeneracionCompatible(antenas_sede,self.cliente_.getModem()))
               text_antenas.grid(row=1, column=0, padx=50, pady=10,sticky="w")

               text_pregunta=tk.Label(self.frame_display,bg="grey85",text="¿Cambiar?")

               text_pregunta.grid(row=2,column=0,padx=50, sticky="w")

               si_button = tk.Button(self.frame_display, text=" Sí ", command=function)
               si_button.grid(row=2,column=1, padx=10)

               no_button = tk.Button(self.frame_display, text=" No ", command=function2) 
               no_button.grid(row=2,column=2)

            elif "Inaccesible" in resultado:

               messagebox.showinfo(title = "Inaccesibilidad", 
               message = resultado,
               detail = "El test ha detectado problemas con tu red.\n Te encuentras fuera de la zona de cobertura de la antena que tienes asociada.")
               self.frame_display.config(borderwidth=2, relief="solid",height=200, width=500)
               text_antena = tk.Label(self.frame_display, bg="grey85", text="Se ha encontrado una antena \ncompatible y accesible:")
               text_antena.grid(row=0, column=0, padx=50, pady=10,sticky="w")
               text_antenas = tk.Label(self.frame_display, bg="grey85", text=antena.rastrearGeneracionCompatible(antenas_sede,self.cliente_.getModem()))
               text_antenas.grid(row=1, column=0, padx=50, pady=10,sticky="w")  


               text_pregunta=tk.Label(self.frame_display,bg="grey85",text="¿ Deseas cambiarte ?")

               text_pregunta.grid(row=2,column=0,padx=50, sticky="w")

               si_button = tk.Button(self.frame_display, text="Si", command=function)
               si_button.grid(row=2,column=1, padx=10)

               no_button = tk.Button(self.frame_display, text="No", command=function2) 
               no_button.grid(row=2,column=2)

            else: 
               self.frame_display.config(bg="grey82") 
               frameInformacion.config(borderwidth=2, relief="solid")          
               megas=self.cliente_.getModem().actualizarVelocidad(self.cliente_)
               tit = tk.Label(frameInformacion, text="Resultado del Test",bg="gray82",font=("Serif",25))
               tit.grid(row=0, column=0, padx=70, pady=10,sticky="w") 
               calc = tk.Label(frameInformacion, text="Calculando....",bg="gray82",font=("Serif",20))
               calc.grid(row=1, column=0, padx=70, pady=10,sticky="w") 
               download = tk.Label(frameInformacion, text=f"Download: {megas[0]} Mbps",bg="gray82",font=("Bahnshrift",15))
               download.grid(row=2, column=0, padx=70, pady=10,sticky="w")
               upload   = tk.Label(frameInformacion, text=f"Upload: {megas[1]} Mbps",bg="gray82",font=("Bahnshrift",15))
               upload.grid(row=3,column=0,padx=70, pady=10,sticky="w")
               aceptar_buttonFinal = tk.Button(frameInformacion, text="Aceptar", command=finalizar)
               aceptar_buttonFinal.place(relx=0.98, rely=0.029, anchor="ne")

# ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
         # 5. FUNCIONALIDAD DE REPORTE
         def reporte():

            for widget in frameInformacion.winfo_children():
                     widget.destroy()  

            def aceptarDatos():
               
               # BORRA LOS VALORES INGRESADOS
               def borrarValor():
                  entrySede.delete(0,tk.END)

               def aceptarSede():

                  # SE ALMACENA EN UN VARIABLE LA OPCION ELEGIDA
                  opcion = entrySede.get()

                  # EXCEPCIONES
                  try:
                     
                     if opcion=="":
                        raise ErrorEspacios()
                     
                     try:
                        opcion = int(opcion)
                     except ValueError:
                        raise ErrorLetra()
                     
                     if not(1<= opcion <= len(servidoresProveedor)):
                        raise ErrorOpElegida()
                     
                  except ErrorEspacios as e:

                     for widget in frameInformacion.winfo_children():
                        widget.destroy()

                     messagebox.showwarning(title="Error", message="ERROR ESPACIOS", detail=f"{str(e)}")
                     return reporte()
                  
                  except ErrorLetra as e:

                     for widget in frameInformacion.winfo_children():
                        widget.destroy()

                     messagebox.showerror(title="Error", message="ERROR TIPO DE DATO", detail=f"{str(e)}")
                     return reporte()
                  
                  except ErrorOpElegida as e:

                     for widget in frameInformacion.winfo_children():
                        widget.destroy()

                     messagebox.showerror(title="Error", message="ERROR OPCIÓN ELEGIDA", detail=f"{str(e)}")
                     return reporte()

                  # SE ELIMINAN LOS FRAMES YA CREADOS
                  for widget in frameInformacion.winfo_children():
                     widget.destroy()

               
                  # SE ALMACENA EL SERVIDOR ELEGIDO
                  servidorLocalidad = servidoresProveedor[opcion-1]

                  # //SE IMPLEMENTA TRES VECES EL SEGUNDO METODO DE LA FUNCIONALIDAD--SE SEPARAN LOS CLIENTES DEL PROVEEDOR DE ACUERDO CON SU PLAN
                  clientesBasic = Cliente.buscarCliente1(servidorLocalidad.getSede(), "BASIC",servidorLocalidad.getProveedor())
                  clientesStandard = Cliente.buscarCliente1(servidorLocalidad.getSede(), "STANDARD",servidorLocalidad.getProveedor())
                  clientesPremium = Cliente.buscarCliente1(servidorLocalidad.getSede(), "PREMIUM",servidorLocalidad.getProveedor())

                  # SE IMPLEMENTA EL TERCER METODO DE LA FUNCIONALIDAD

                  #   SE CALCULA LAS INTENSIDADES REALES DE LOS CLIENTES, PERO ESTO ES CLASIFICADO POR PLAN
                  intensidadBasic = Router.adminRouter().intensidadFlujoClientes(clientesBasic,servidorLocalidad, True)
                  intensidadStandard = Router.adminRouter().intensidadFlujoClientes(clientesStandard, servidorLocalidad, True)
                  intensidadPremium = Router.adminRouter().intensidadFlujoClientes(clientesPremium,servidorLocalidad, True)

                  # SE CALCULA LAS INTENSIDADES QUE DEBERIAN LLEGARLE A LOS CLIENTES, PERO ESTO ES CLASIFICADO POR PLAN
                  intensidadB = Router.adminRouter().intensidadFlujoClientes(clientesBasic,servidorLocalidad, False)
                  intensidadS = Router.adminRouter().intensidadFlujoClientes(clientesStandard,servidorLocalidad, False)
                  intensidadP = Router.adminRouter().intensidadFlujoClientes(clientesPremium,servidorLocalidad, False)

                  # PROMEDIO INTENSIDADES REALES
                  try:
                     PromedioBasic = sum(intensidadBasic)/ len(intensidadBasic)
                  except ZeroDivisionError:
                     PromedioBasic = 0
                  
                  try:
                     PromedioStandard = sum(intensidadStandard) / len(intensidadStandard)
                  except ZeroDivisionError:
                     PromedioStandard = 0
                  
                  try:
                     PromedioPremium = sum(intensidadPremium) / len(intensidadPremium)
                  except ZeroDivisionError:
                     PromedioPremium = 0

                  # PROMEDIO INTENSIDADES ADECUADAS
                  try:
                     PromedioB = sum(intensidadB) / len(intensidadB)
                  except ZeroDivisionError:
                     PromedioB = 0
                  
                  try:
                     PromedioS = sum(intensidadS) / len(intensidadS)
                  except ZeroDivisionError:
                     PromedioS = 0
                  
                  try:
                     PromedioP = sum(intensidadP) / len(intensidadP)
                  except ZeroDivisionError:
                     PromedioP = 0

                  if ((PromedioBasic < PromedioB) or (PromedioStandard < PromedioS) or (PromedioPremium < PromedioP)): # SE COMPRUEBA SI ALGUN PROMEDIO DE LAS INTENSIDADES REALES ES MENOR A LAS ADECUADAS

                     # SE CALCULAN LAS DISTANCIAS PROMEDIOS A LAS QUE DEBERIA ENCONTRARSE EL SERVIDOR DE SUS CLIENTES
                     # DISTANCIAS OPTIMAS POR PLAN
                     dOptBasic = servidorLocalidad.distanciasOptimas(clientesBasic, intensidadB)
                     dOptStandard = servidorLocalidad.distanciasOptimas(clientesStandard, intensidadS)
                     dOptPremium = servidorLocalidad.distanciasOptimas(clientesPremium,intensidadP)
                     
                      #PROMEDIO DE LAS DISTANCIAS OPTIMAS
                     try:
                       
                        PromedioDB = round(sum(dOptBasic) / len(dOptBasic) , 2)
                     except ZeroDivisionError:
                        PromedioDB = 0

                     try:
                        PromedioDS = round(sum(dOptStandard) / len(dOptStandard), 2)
                     except ZeroDivisionError:
                        PromedioDS = 0
                     
                     try:
                        PromedioDP = round(sum(dOptPremium) / len(dOptPremium), 2)
                     except ZeroDivisionError:
                        PromedioDP = 0

                     resultados = tk.Text(frameInformacion, bg="gray82", font=("Times",10))
                     resultados.insert(1.0, f"De acuerdo con el análisis hecho al servidor, se encontraron fallas en este. A continuación se presentan las recomendaciones:\nREPORTE \nIntensidades:\nSe encontraron diferencias entre las intensidades netas y las que se deberían registrar.\nPLAN BASIC \nIntensidad de Flujo Promedio Neta: {PromedioBasic}\nIntensidad de Flujo Promedio Adecuado:  {PromedioB} \nPLAN STANDARD\nIntensidad de Flujo Promedio Neta:  {PromedioStandard}\nIntensidad de Flujo Promedio Adecuada:  {PromedioS} \nPLAN PREMIUM\nIntensidad de Flujo Promedio Neta:  {PromedioPremium}\nIntensidad de Flujo Promedio Adecuada:  {PromedioP}\nLas intensidades dependen de las distancias entre el router de los clientes y el servidor, por ello se recomienda cambiar la ubicación de este último. A continuación, se presentan las distancias promedios recomendadas a las cuales debe estar el servidor para que proporcione la intensidad de flujo adecuada en cada plan.\nDISTANCIAS ÓPTIMAS \nDistancia Promedio-Plan Basic:  {PromedioDB} metros.\nDistancia Promedio-Plan Standard:  {PromedioDS} metros. \nDistancia Promedio-Plan Premium: {PromedioDP} metros.\nFinalmente, esto indica que el servidor está consumiendo una cantidad significativa del flujo de red inicial que le proporciona el proveedor. Por ende, se recomienda cambiarlo o en su defecto, implementar la solución de las distancias, expuesta anteriormente.")  # Aquí debes reemplazar "Texto del resultado" por el contenido que deseas mostrar
                     resultados.place(relx=0.5, rely=0.5, relheight=0.9, relwidth=0.8, anchor="center")
                     resultados.config(state="disabled")
                     aceptar_buttonFinal = tk.Button(frameInformacion, text="Finalizar", command=finalizar)
                     aceptar_buttonFinal.place(relx=0.98, rely=0.029, anchor="ne")
                  else:
                     resultados = tk.Text(frameInformacion, bg="gray82", font=("Times",10))
                     resultados.insert(1.0,"El servidor no presenta fallas en su funcionamiento.")
                     resultados.place(relx=0.5, rely=0.5, relheight=0.9, relwidth=0.8, anchor="center")
                     resultados.config(state="disabled")
                     aceptar_buttonFinal = tk.Button(frameInformacion, text="Finalizar", command=finalizar)
                     aceptar_buttonFinal.place(relx=0.98, rely=0.029, anchor="ne")


               # /////////////////////////////////////////////////////////////////////////////////////////////
               # EXCEPCIONES
               try:
                  # SE GUARDAN LO DATOS INTRODUCIDOS
                  nombre = fp.getValor("Nombre de la compañía").lower()

                  if not(nombre=="tigo" or nombre=="claro" or nombre=="movistar" or nombre=="wom"):
                     raise DatosIncorrectos()

               except ErrorEspacios as e:

                  for widget in frameInformacion.winfo_children():
                     widget.destroy()

                  messagebox.showwarning(title="Error", message="ERROR ESPACIOS", detail=f"{str(e)}")
                  return reporte()

               except DatosIncorrectos as e:

                  for widget in frameInformacion.winfo_children():
                     widget.destroy()

                  messagebox.showwarning(title="Error", message="ERROR DATOS", detail=f"{str(e)}")
                  return reporte()


               # SE ELIMINAN LOS FRAMES YA CREADOS
               for widget in frameInterior.winfo_children():
                  widget.destroy()
               frameInterior.destroy()

               # PRIMER MÉTODO DE LA FUNCIONALIDAD
         
               if len(Servidor.adminServidor().verificarAdmin(ProveedorInternet.getProveedoresTotales(), nombre)) != 0:

                  servidoresProveedor = Servidor.adminServidor().verificarAdmin(ProveedorInternet.getProveedoresTotales(), nombre)

                  labelServidores = tk.Label(frameInformacion,bg="gray82", text=f"La compañía de Internet {nombre} tiene servidores en las siguientes sedes:  \n\n", font=("Times", 12), borderwidth=2, relief="solid")
                  labelServidores.place(relx=0.15, rely=0.02, relheight=0.5, relwidth=0.7)

                  for serv in range(0, len(servidoresProveedor)):
                     texto = labelServidores["text"]
                     labelServidores.config(text=f"{texto} {serv+1}. {servidoresProveedor[serv].getSede()} \n")
                  
                  # SE CONFIGURA PARA PEDIR LA OPCION DESEADA
                  labelSede = tk.Label(frameInformacion, text="Digita el número de la opción que deseas: ", font=("Times", 12), bg="gray82")
                  labelSede.place(relx=0.25, rely = 0.57, relheight = 0.15, relwidth = 0.5)
                  entrySede = tk.Entry(frameInformacion, state="normal")
                  entrySede.place(relx=0.35, rely= 0.7, relheight = 0.1, relwidth = 0.3)

                  # SE AJUSTAN LOS BOTONES PARA INGRESAR LA OPCION DEL PROVEEDOR
                  aceptar_button = tk.Button(frameInformacion, text="Aceptar", command=aceptarSede)
                  borrar_button = tk.Button( frameInformacion, text="Borrar", command=borrarValor)
                  aceptar_button.place(relx=0.37, rely=0.87)
                  borrar_button.place(relx=0.55, rely=0.87)
                  

            # ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            # SE ELIMINAN LOS FRAMES YA CREADOS----PARA CAMBIAR ENTRE FUNCIONALIDADES
            for widget in frameInformacion.winfo_children():
               widget.destroy()

            # SE AJUSTA EL TITULO Y DESCRIPCION DE LA FUNCIONALIDAD
            self.titulo.config(text="REPORTE DE FALLAS EN EL SERVIDOR", font=("Times",17))
            self.descripcion.config(wraplength=500, font=("Times",11), text="Esta funcionalidad tiene como objetivo alertar sobre posibles fallas en los servidores de un proveedor de internet específico. Su principal ventaja es proporcionar una notificación inmediata a los proveedores, permitiéndoles identificar y solucionar problemas de manera oportuna.")

            # SE CONSTRUYE EL FIELDFRAME QUE SOLICITA LOS DATOS CORRESPONDIENTES
            criterios = ["Tipo de Usuario", "Nombre de la compañía"]
            fp = FieldFrame(frameInformacion, "Datos solicitados", criterios, "Valor", ["Proveedor", ""])
            fp.configure(borderwidth=1, relief="raised")
            fp.pack(fill="y",pady=10, padx=5)

            # SE AJUSTAN LOS BOTONES DE ACEPTAR Y BORRAR
            frameInterior = tk.Frame(frameInformacion, bg="gray82", height=100, width=300)
            frameInterior.pack( expand=True, fill="none", padx=100, pady=10,anchor="n")
            aceptar_button = tk.Button(frameInterior, text="Aceptar", command=aceptarDatos)
            borrar_button = tk.Button( frameInterior, text="Borrar", command=fp.borrarValores)
            aceptar_button.pack( side="left", pady=20, padx=50, anchor="sw")
            borrar_button.pack( side="left", pady=20, padx=40, anchor="se")

 
         # FRAME PRINCIPAL
         mainFrame = tk.Frame(ventana,bg="gray85", height=470,width=770,padx=10, pady=10)
         mainFrame.pack(expand=True, fill="both", padx=10, pady=10)

         # FRAME TITULO
         frameTitulo = tk.Frame(mainFrame, bg="gray85", height=50, width=200)
         frameTitulo.pack(fill="both",side="top", padx=2, pady=2)

         # FRAME DESCRIPCIÓN
         frameDescripcion = tk.Frame(mainFrame, bg="gray85", height=70, width=700)
         frameDescripcion.pack(fill="both", padx=10, pady=10)

         # # FRAME QUE CONTIENE EL FIELDFRAME
         frameInformacion = tk.Frame(mainFrame, bg="gray82", height=260, width=670)
         frameInformacion.pack(expand=True, fill="both", padx=5, pady=5)

         # CREAR MENU
         menuBar = tk.Menu(ventana)
         ventana.config(menu=menuBar)

         # MENU ARCHIVO
         menuArchivo = tk.Menu(menuBar, tearoff = False)
         menuBar.add_cascade(label="Archivo", menu=menuArchivo)
         menuArchivo.add_command(label="Aplicación", command=eventoAplicacion)
         menuArchivo.add_command(label="Salir", command=eventoSalir)

         # MENSAJE INICIAL
         self.titulo = tk.Label(frameTitulo,text="BIENVENIDO A LA VENTANA PRINCIPAL", font=("Times", 12), bg="gray85")
         self.titulo.pack(fill="x")

         self.descripcion = tk.Label(frameDescripcion, text=" A continuación te daremos las instrucciones sobre cómo utilizar la aplicación:\n\n1. En la parte superior de esta ventana encontrarás un menú con tres pestañas: Archivo, Procesos y Consultas, Ayuda.\n\n2. En Archivo se desplegarán dos opciones:\n\n\t2.1. Aplicación, te dará información sobre el programa.\n\t2.2. Salir, sirve para regresar a la ventana de inicio. \n\n3. En Procesos y Consultas se listarán cada una de las funcionalidades. Da click en la opción que desees. \n\n4. Finalmente, en Ayuda tendrás la opción de Acerca de, esto te dará información sobre los autores del programa.", font=("Times",12), bg="gray85", justify="left")
         self.descripcion.pack(fill="x")
         

         # MENU FUNCIONALIDADES
         menuFuncionalidades = tk.Menu(menuBar, tearoff = False)
         menuBar.add_cascade(label="Procesos y Consultas",menu=menuFuncionalidades)
         menuFuncionalidades.add_command(label="Adquisición de un Plan", command=adquisicionPlan)
         menuFuncionalidades.add_command(label="Visualizar Dispositivos Conectados", command=visuaDispo)
         menuFuncionalidades.add_command(label="Mejora tu Plan",command=mejoraTuPlan)
         menuFuncionalidades.add_command(label="Test de Velocidad", command=test_command)
         menuFuncionalidades.add_command(label="Reporte", command=reporte)

         # MENU AYUDA
         menuAyuda = tk.Menu(menuBar, tearoff = False)
         menuAyuda.add_command(label="Acerca de",command=eventoAcercaDe)
         menuBar.add_cascade(label="Ayuda", menu=menuAyuda)


# ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
      # SE CREA LA VENTANA
      ventana = tk.Tk()
      ventana.title("Sistema de Redes")
      ventana.geometry("800x500")
      VentanaUsuario._abierto = True
      programa()
