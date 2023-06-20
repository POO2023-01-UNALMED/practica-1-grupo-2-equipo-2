import sys

sys.path.append("src/uiMain/excepciones")

import tkinter as tk
from tkinter import  messagebox
from exceptionC2 import ErrorEspacios

class FieldFrame(tk.Frame):

    # CONSTRUCTOR
    def __init__(self, parent, tituloCriterios, criterios, tituloValores, valores=None, habilitado=None):

        super().__init__(parent)
        self.criterios = criterios

        if valores is not None:
            self.valores = valores
        else:
            self.valores = [""] * len(criterios)

        if habilitado is None:
            self.habilitado = [False] + [True] * (len(criterios) - 1)

        self.labels = []
        self.entries = []

        label_criterios = tk.Label(self, text=tituloCriterios)
        label_valores = tk.Label(self, text=tituloValores)
        label_criterios.grid(row=0, column=0, sticky='we', padx=10, pady=10)
        label_valores.grid(row=0, column=1, sticky='we', padx=10, pady=10)

        for i, criterio in enumerate(criterios, start=1):
            
            label = tk.Label(self, text=criterio)
            entry = tk.Entry(self,state="normal")
            entry.insert('end', self.valores[i-1])

            if i == 1:  # Primer campo
                estado = "disabled"
                
            else:  # Otros campos
                estado = "normal"

            entry.config(state=estado)

            label.grid(row=i, column=0, sticky='we', padx=30, pady=10)
            entry.grid(row=i, column=1, sticky='we', padx=30, pady=10)
            self.labels.append(label)
            self.entries.append(entry)



    # Obtener el valor de cada campo
    def getValor(self, criterio):
        index = self.criterios.index(criterio)

        if (self.entries[index].get()) == "":
            raise ErrorEspacios()
        else:
            return self.entries[index].get()
        
    # Eliminar el valor digitado en los campos 
    def borrarValores(self):
        for entry in self.entries:
            entry.delete(0,tk.END)