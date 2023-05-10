package baseDatos;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.File;
import gestorAplicacion.enrutadorHFC.*;
import gestorAplicacion.host.*;
import gestorAplicacion.servicio.*;
import java.util.*;

public class Serializador {

    static ArrayList<ArrayList<?>>  listaDeListas = new ArrayList<ArrayList<?>>();
    private static File rutaTemp = new File ///ACTUALICEN DE ACUERDO A SU RUTA
    ("C:/Users/Usuario/OneDrive - Universidad Nacional de Colombia/Escritorio/UNAL/CLASES 2023-1S/POO/Pruebas/Aplicacion/src/baseDatos/temp");

    public static void serializar(){ 

        listaDeListas.add(Antena.getAntenasTotales());listaDeListas.add(Dispositivo.getDispositivosTotales());
        listaDeListas.add(Router.getRoutersTotales());listaDeListas.add(Servidor.getServidoresTotales());
        listaDeListas.add(Cliente.getClientesTotales());listaDeListas.add(ProveedorInternet.getProveedoresTotales());
        listaDeListas.add(Factura.getFacturasTotales());listaDeListas.add(Plano.getPlanosTotales());

        FileOutputStream file;
        ObjectOutputStream output;
        File[] docs = rutaTemp.listFiles();
        PrintWriter pw;

        for(File fos : docs){
            try{
                pw = new PrintWriter(fos);
            }
            catch(FileNotFoundException e){
                System.out.println("Archivo no encontrado");
            }
        }
        
        for (ArrayList<?> lista : listaDeListas ) {
        
            try{
                file = new FileOutputStream(String.format
                ("C:/Users/Usuario/OneDrive - Universidad Nacional de Colombia/Escritorio/UNAL/CLASES 2023-1S/POO/Pruebas/Aplicacion/src/baseDatos/temp/objetos%s",lista.get(0).getClass().getSimpleName()));
                output = new ObjectOutputStream(file);

                output.writeObject(lista);
                output.close();
        
            }catch(IOException e){
                System.out.println("No esta el archivo");
            }
            
        } 
    } 
}
