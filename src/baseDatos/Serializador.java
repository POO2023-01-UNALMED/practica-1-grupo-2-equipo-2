package baseDatos;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.File;
import gestorAplicacion.enrutadorHFC.*;

import java.util.*;

public class Serializador {

    static ArrayList<ArrayList<?>>  listaDeListas = new ArrayList<ArrayList<?>>();
    private static File rutaTemp = new File("src/baseDatos/temp");

    public static void serializar(){ 

        listaDeListas.add(Antena.getAntenasTotales()); listaDeListas.add(Dispositivo.getDispositivosTotales()); listaDeListas.add(Servidor.getServidoresTotales());
        
        FileOutputStream file;
        ObjectOutputStream output;
        File[] docs = rutaTemp.listFiles();
        PrintWriter pw;

        for(File fos : docs){
            try{
                pw = new PrintWriter(fos);
            }
            catch(FileNotFoundException e){
                e.printStackTrace();
            }
        }
        
        for (ArrayList<?> lista : listaDeListas ) {
        
            try{
                file = new FileOutputStream(String.format
                ("src/baseDatos/temp/objetos%s",lista.get(0).getClass().getSimpleName()));
                output = new ObjectOutputStream(file);

                output.writeObject(lista);
                output.close();
        
            }catch(IOException e){
                e.printStackTrace();
            }
            
        } 
    } 
}