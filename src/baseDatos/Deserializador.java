package baseDatos;

import gestorAplicacion.host.*;
import gestorAplicacion.enrutadorHFC.*;
import gestorAplicacion.servicio.*;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.*;
import java.util.*;

public class Deserializador{

  //ARREGLEN LA RUTA SEGUN SUS PCS
    private static File rutaTemp = new File("C:/Users/Usuario/OneDrive - Universidad Nacional de Colombia/Escritorio/UNAL/CLASES 2023-1S/POO/Pruebas/Aplicacion/src/baseDatos/temp");

    public static void deserializar(){

        File[] docs = rutaTemp.listFiles();
        FileInputStream fIn;
        ObjectInputStream oIn;

        for(File file: docs){
            if(file.getAbsolutePath().contains("Antena")){
                try{
                    fIn = new FileInputStream(String.format
                    ("C:/Users/Usuario/OneDrive - Universidad Nacional de Colombia/Escritorio/UNAL/CLASES 2023-1S/POO/Pruebas/Aplicacion/src/baseDatos/temp/objetosAntena"));
                    oIn = new ObjectInputStream(fIn);
    
                    Antena.setAntenasTotales((ArrayList<Antena>) (oIn.readObject()));
                    oIn.close();
                    System.out.print("ok");
    
                }catch(FileNotFoundException e){
                    System.out.print("F");
                }catch(IOException e){
                    System.out.println("No esta el archivo");
                }catch(Exception e){
                    System.out.print("FX2");
                }

            }else if(file.getAbsolutePath().contains("Dispositivo")){
                try{
                    fIn = new FileInputStream(String.format
                    ("C:/Users/Usuario/OneDrive - Universidad Nacional de Colombia/Escritorio/UNAL/CLASES 2023-1S/POO/Pruebas/Aplicacion/src/baseDatos/temp/objetosDispositivo"));
                    oIn = new ObjectInputStream(fIn);
    
                    Dispositivo.setDispositivosTotales((ArrayList<Dispositivo>) oIn.readObject());
                    oIn.close();
                    System.out.print("ok");
    
                }catch(FileNotFoundException e){
                    System.out.print("F");
                }catch(IOException e){
                    System.out.println("No esta el archivo");
                }catch(Exception e){
                    System.out.print("FX2");
                }

            }else if(file.getAbsolutePath().contains("Router")){
                try{
                    fIn = new FileInputStream(String.format
                    ("C:/Users/Usuario/OneDrive - Universidad Nacional de Colombia/Escritorio/UNAL/CLASES 2023-1S/POO/Pruebas/Aplicacion/src/baseDatos/temp/objetosRouter"));
                    oIn = new ObjectInputStream(fIn);
    
                    Router.setRoutersTotales((ArrayList<Router>) oIn.readObject());
                    oIn.close();
                    System.out.print("ok");
    
                }catch(FileNotFoundException e){
                    System.out.print("F");
                }catch(IOException e){
                    System.out.println("No esta el archivo");
                }catch(Exception e){
                    System.out.print("FX2");
                }

            }else if(file.getAbsolutePath().contains("Servidor")){
                try{
                    fIn = new FileInputStream(String.format
                    ("C:/Users/Usuario/OneDrive - Universidad Nacional de Colombia/Escritorio/UNAL/CLASES 2023-1S/POO/Pruebas/Aplicacion/src/baseDatos/temp/objetosServidor"));
                    oIn = new ObjectInputStream(fIn);
    
                    Servidor.setServidoresTotales((ArrayList<Servidor>) oIn.readObject());
                    oIn.close();
                    System.out.print("ok");
    
                }catch(FileNotFoundException e){
                    System.out.print("F");
                }catch(IOException e){
                    System.out.println("No esta el archivo");
                }catch(Exception e){
                    System.out.print("FX2");
                }

            }else if(file.getAbsolutePath().contains("Cliente")){
                try{
                    fIn = new FileInputStream(String.format
                    ("C:/Users/Usuario/OneDrive - Universidad Nacional de Colombia/Escritorio/UNAL/CLASES 2023-1S/POO/Pruebas/Aplicacion/src/baseDatos/temp/objetosCliente"));
                    oIn = new ObjectInputStream(fIn);
    
                    Cliente.setClientesTotales((ArrayList<Cliente>) oIn.readObject());
                    oIn.close();
                    System.out.print("ok");
    
                }catch(FileNotFoundException e){
                    System.out.print("F");
                }catch(IOException e){
                    System.out.println("No esta el archivo");
                }catch(Exception e){
                    System.out.print("FX2");
                }

            }else if(file.getAbsolutePath().contains("Proveedor")){
                try{
                    fIn = new FileInputStream(String.format
                    ("C:/Users/Usuario/OneDrive - Universidad Nacional de Colombia/Escritorio/UNAL/CLASES 2023-1S/POO/Pruebas/Aplicacion/src/baseDatos/temp/objetosProveedorInternet"));
                    oIn = new ObjectInputStream(fIn);
    
                    ProveedorInternet.setProveedoresTotales((ArrayList<ProveedorInternet>) oIn.readObject());
                    oIn.close();
                    System.out.print("ok");
    
                }catch(FileNotFoundException e){
                    System.out.print("F");
                }catch(IOException e){
                    System.out.println("No esta el archivo");
                }catch(Exception e){
                    System.out.print("FX2");
                }

            }else if(file.getAbsolutePath().contains("Factura")){
                try{
                    fIn = new FileInputStream(String.format
                    ("C:/Users/Usuario/OneDrive - Universidad Nacional de Colombia/Escritorio/UNAL/CLASES 2023-1S/POO/Pruebas/Aplicacion/src/baseDatos/temp/objetosFactura"));
                    oIn = new ObjectInputStream(fIn);
    
                    Factura.setFacturasTotales((ArrayList<Factura>) oIn.readObject());
                    oIn.close();
                    System.out.print("ok");
    
                }catch(FileNotFoundException e){
                    System.out.print("F");
                }catch(IOException e){
                    System.out.println("No esta el archivo");
                }catch(Exception e){
                    System.out.print("FX2");
                }

            }else if(file.getAbsolutePath().contains("Plano")){
                try{
                    fIn = new FileInputStream(String.format
                    ("C:/Users/Usuario/OneDrive - Universidad Nacional de Colombia/Escritorio/UNAL/CLASES 2023-1S/POO/Pruebas/Aplicacion/src/baseDatos/temp/objetosPlano"));
                    oIn = new ObjectInputStream(fIn);
    
                    Plano.setPlanosTotales((ArrayList<Plano>) oIn.readObject());
                    oIn.close();
                    System.out.print("ok");
    
                }catch(FileNotFoundException e){
                    System.out.print("F");
                }catch(IOException e){
                    System.out.println("No esta el archivo");
                }catch(Exception e){
                    System.out.print("FX2");
                }
            }

        } 
    }
}
