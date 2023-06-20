import tkinter as tk
from tkinter import *
from ventanaUsuario import VentanaUsuario

class VentanaInicio:

 def __init__(self):

# CONTADOR PARA IR MOSTRANDO LAS IMÁGENES DEL SISTEMA Y LOS DESARROLLADORES
  self._contadorImgSis = 1
  self._contadorImgDll = 1
  
  # SALIR DEL PROGRAMA
  def eventoSalir():
    ventana.destroy()
  
  # DESCRIPCIÓN DE LA APP
  def eventoDescripcion():
    mensajeBienvenida.destroy()
    texto = tk.Text(P3,font=("Times",10))
    texto.insert(1.0,"Este es un sistema de redes reducido, con distintas generalidades   abstraídas de la realidad, mediante una aplicación implementada en   Python que se basa en el paradigma de programación orientada a    objetos. Cuenta con cinco funcionalidades que manejan planes de   contratación de servicio, cada plan le permite al usuario elegir entre una gama de características relacionadas con el servicio doméstico de Internet, entre otras acciones como cambio de plan .")
    texto.config(padx=15, pady=15, state="disabled")
    texto.place(x=0, y=0, height=144, width=394)
    
   
  # IR A LA VENTANA PRINCIPAL
  def abrirVentanaUsuario():
    if VentanaUsuario._abierto != True:
      ventanaUsuario = VentanaUsuario()
      ventana.destroy()
  
  # CAMBIO DE IMAGEN DEL SISTEMA
  def cambiarImagenSis(event):
    self._contadorImgSis+=1

    # CUANDO LLEGA A 6, DEBE VOLVER A LA IMAGEN INICIAL, LA NÚMERO 1
    if self._contadorImgSis == 6:
      self._contadorImgSis=1
  
    imagenSistema.config(file=f"src/uiMain/imgApp/imagen{self._contadorImgSis}.png")
    contenedorImagenSis.config(image=imagenSistema)
  
  # CAMBIO DE IMAGEN DE LOS DESARROLLADORES
  def cambioImg():
    self._contadorImgDll+=1
    if self._contadorImgDll==2:
      imagen1.config(file="src/uiMain/imgD/yesica.png")
      imagen2.config(file="src/uiMain/imgD/imagen4.png")
      imagen3.config(file="src/uiMain/imgD/imagen5.png")
      imagen4.config(file="src/uiMain/imgD/imagen6.png")

      d1.config(textvariable=tk.StringVar(P5,value=" Yesica Andrea Henao Ceballos"))

    elif self._contadorImgDll==3:
      imagen1.config(file="src/uiMain/imgD/camilo.png")
      imagen2.config(file="src/uiMain/imgD/imagen7.png")
      imagen3.config(file="src/uiMain/imgD/imagen8.png")
      imagen4.config(file="src/uiMain/imgD/imagen9.png")
      d1.config(textvariable=tk.StringVar(P5,value=" Juan Camilo Misas Tabares"))
      d2.config(textvariable=tk.StringVar(P5,value=" Ciencias de la Computación"))

    elif self._contadorImgDll==4:
      imagen1.config(file="src/uiMain/imgD/capry.png")
      imagen2.config(file="src/uiMain/imgD/imagen10.png")
      imagen3.config(file="src/uiMain/imgD/imagen11.png")
      imagen4.config(file="src/uiMain/imgD/imagen12.png")
      d1.config(textvariable=tk.StringVar(P5,value=" Freddy Quintero Colorado"))
      d2.config(textvariable=tk.StringVar(P5,value=" Ingeniería de Sistemas e Informática"))

    elif self._contadorImgDll==5:
      imagen1.config(file="src/uiMain/imgD/luisa.png")
      imagen2.config(file="src/uiMain/imgD/imagen13.png")
      imagen3.config(file="src/uiMain/imgD/imagen14.png")
      imagen4.config(file="src/uiMain/imgD/imagen15.png")
      d1.config(textvariable=tk.StringVar(P5,value=" Luisa María Marín Ceferino"))
      d2.config(textvariable=tk.StringVar(P5,value=" Ingeniería de Sistemas e Informática"))
      d3.config(textvariable=tk.StringVar(P5,value=" 21 años"))

    if self._contadorImgDll==6:
      imagen1.config(file="src/uiMain/imgD/angelica.png")
      imagen2.config(file="src/uiMain/imgD/imagen2.png")
      imagen3.config(file="src/uiMain/imgD/imagen1.png")
      imagen4.config(file="src/uiMain/imgD/imagen3.png") 
      d1.config(textvariable=tk.StringVar(P5,value=" Angelica María Arce Parra"))
      d3.config(textvariable=tk.StringVar(P5,value=" 19 años"))
      self._contadorImgDll=1

  ventana = tk.Tk()
  ventana.title("Ventana Inicio")
  ventana.geometry("800x500")
  ventana.resizable(0, 0)
  
#   FRAMES ANIDADOS
  P1 = tk.Frame(ventana, bg="black", height=500, width=400)
  P1.pack(side="left") 

  P2 = tk.Frame(ventana, bg="black", height=500, width=400)
  P2.pack(side="right") 

  P3 = tk.Frame(P1, width=395, height=145)
  P3.place(x=5,y=5,width="395",height="145")

  P4 = tk.Frame(P1, width=395, height=338)
  P4.place(x=5,y=156,width="395",height="338")

  P5 = tk.Frame(P2, width=390, height=145)
  P5.place(x=5,y=5,width="390",height="145")

  P6 = tk.Frame(P2, width=390, height=338)
  P6.place(x=5,y=156,width="390",height="338")

# BIENVENIDA
  mensajeBienvenida = tk.Label(P3, text="TE DAMOS LA BIENVENIDA AL SISTEMA DE REDES.\n\nEsperamos que tu experiencia sea agradable.\n\nHaz click en la parte inferior para ingresar a la ventana del usuario :)", font=("Times",9))
  mensajeBienvenida.place(x=0,y=35,width=400,height=75)

# IMÁGENES DEL SISTEMA
  contenedorImagenSis = tk.Label(P4)
  contenedorImagenSis.place(x=10, y=10, width="375", height="310")
  imagenSistema = tk.PhotoImage(file="src/uiMain/imgApp/imagen1.png")
  contenedorImagenSis.config(image=imagenSistema)

  # CONFIGURACIÓN PARA QUE CAMBIE DE IMAGEN
  contenedorImagenSis.bind("<Enter>",cambiarImagenSis)

# BOTÓN PARA IR A LA VENTANA PRINCIPAL
  botonVentanaU = tk.Button(P4,text="Ir a la Ventana Principal", command=abrirVentanaUsuario)
  botonVentanaU.pack(side="bottom",anchor="c", pady=12)

 # CREAR MENU -> SALIR Y DESCRIPCION
  menuBar = tk.Menu(ventana)
  ventana.config(menu=menuBar)
  menu1 = tk.Menu(menuBar, tearoff = False)
  menuBar.add_cascade(label="Inicio",menu=menu1)
  menu1.add_command(label="Descripción", command=eventoDescripcion)
  menu1.add_command(label="Salir",command=eventoSalir)


 # CONFIGURACIONES IMAGENES DESARROLLADORES
  imagen1 = tk.PhotoImage(file="src/uiMain/imgD/angelica.png")
  imagen2 = tk.PhotoImage(file="src/uiMain/imgD/imagen2.png")
  imagen3 = tk.PhotoImage(file="src/uiMain/imgD/imagen1.png")
  imagen4 = tk.PhotoImage(file="src/uiMain/imgD/imagen3.png")

  pInterior = tk.Frame(P6, padx=25, pady=25)
  pInterior.pack(fill="both", expand=True)
  limg1=tk.Label(pInterior, image=imagen1, padx=10, pady=10)
  limg1.grid(row=0,column=0, sticky="nsew")
        
  limg2=tk.Label(pInterior,image=imagen2, padx=10, pady=10)
  limg2.grid(row=0,column=1,sticky="nsew")
        
  limg3=tk.Label(pInterior,image=imagen3, padx=10, pady=10)
  limg3.grid(row=1,column=0,sticky="nsew")
        
  limg4=tk.Label(pInterior,image=imagen4, padx=10, pady=10)
  limg4.grid(row=1,column=1,sticky="nsew") 

  # BOTÓN PARA CAMBIO IMAGENES DESARROLLADORES
  botonCambimg = tk.Button(P5, text="Desarrollador",command=cambioImg)
  botonCambimg.pack(side="bottom", anchor="c", pady=12)

  # DESCRIPCION DESARROLLADORES
  l1=tk.Label(P5,text="Nombre: ")
  l1.place(x=30, y=20)
  l2=tk.Label(P5,text="Programa: ")
  l2.place(x=30, y=50)
  l3=tk.Label(P5,text="Edad: ")
  l3.place(x=30, y=80)
  d1=tk.Entry(P5,state="disabled",textvariable=tk.StringVar(P5,value=" Angelica María Arce Parra"))
  d1.place(x=100, y=20, height=20, width=200)
  d2=tk.Entry(P5,state="disabled",textvariable=tk.StringVar(P5,value=" Ingeniería de Sistemas e Informática"))
  d2.place(x=100, y=50, height=20, width=200)
  d3=tk.Entry(P5,state="disabled",textvariable=tk.StringVar(P5,value=" 19 años"))
  d3.place(x=100, y=80, height=20, width=200)

  ventana.mainloop() 