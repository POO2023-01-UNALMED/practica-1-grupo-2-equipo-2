package baseDatos;

import gestorAplicacion.enrutadorHFC.*;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.*;
import java.util.*;

public class Deserializador{

    private static File rutaTemp = new File("src/baseDatos/temp");

    public static void deserializar(){

        File[] docs = rutaTemp.listFiles();
        FileInputStream fIn;
        ObjectInputStream oIn;

        for(File file: docs){
            if(file.getAbsolutePath().contains("Antena")){
                try{
                    fIn = new FileInputStream("src/baseDatos/temp/objetosAntena");
                    oIn = new ObjectInputStream(fIn);
    
                    Antena.setAntenasTotales((ArrayList<Antena>) (oIn.readObject()));
                    oIn.close();
    
                }catch(FileNotFoundException e){
                    e.printStackTrace();

                }catch(IOException e){
                    e.printStackTrace();
                }catch(Exception e){
                    e.printStackTrace();
                }

            }else if(file.getAbsolutePath().contains("Dispositivo")){
                try{
                    fIn = new FileInputStream("src/baseDatos/temp/objetosDispositivo");
                    oIn = new ObjectInputStream(fIn);
    
                    Dispositivo.setDispositivosTotales((ArrayList<Dispositivo>) (oIn.readObject()));
                    oIn.close();
    
                }catch(FileNotFoundException e){
                    e.printStackTrace();
                }catch(IOException e){
                    e.printStackTrace();
                }catch(Exception e){
                    e.printStackTrace();
                }
    
            }else if(file.getAbsolutePath().contains("Servidor")){
                try{
                    fIn = new FileInputStream("src/baseDatos/temp/objetosServidor");
                    oIn = new ObjectInputStream(fIn);
    
                    Servidor.setServidoresTotales((ArrayList<Servidor>)(oIn.readObject()));
                    oIn.close();

                }catch(FileNotFoundException e){
                    e.printStackTrace();
                }catch(IOException e){
                    e.printStackTrace();
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        } 
    }
}