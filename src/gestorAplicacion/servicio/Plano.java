package gestorAplicacion.servicio;

import java.awt.geom.*;
import java.io.Serializable;

public class Plano implements Serializable{

  //CONTIENE LA INFORMACION DE LA UBICACION DE LOS CLIENTES Y LOS SERVIDORES

  //ATRIBUTOS
  private int y;
  private int x;
  private Rectangle2D sede;
  private Ellipse2D zonaCobertura;
  private static Rectangle2D cuadrante;

  //CONSTRUCTOR
  public Plano(int x, int y){
    this.x=x;
    this.y=y;
    cuadrante = new Rectangle2D.Double(0,50,50,50);
  }


  //METODOS 

  //METODO ESTÁTICO QUE PERMITE CREAR EL CUADRADO QUE DETERMINA LA FORMA DE LA SEDE Y SUS LIMITES
  public static Rectangle2D crearSede(int h, int w, int x, int y){
    return new Rectangle2D.Double(x,y,w,h);
  }

  //METODO QUE PERMITE CREAR UNA ZONA DE COBERTURA--USADO EN LA CLASE ANTENA
  public Ellipse2D crearZonaCobertura(int w, int centroX, int centroY){
    Ellipse2D.Double z=new Ellipse2D.Double();
    z.setFrameFromCenter(centroX,centroY,centroX+w/2,centroY+w/2);
    return z;
  }

  //METODO QUE CONVIERTE LAS COORDENADAS A UN DATO TIPO POINT2D PARA FACILITAR CÁLCULOS
  public Point2D crearPuntoOrdenado(int x, int y){
    return new Point2D.Double(x,y);
  }

  //SETTERS Y GETTERS

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public Rectangle2D getSede() {
    return sede;
  }

  public void setSede(Rectangle2D sede) {
    this.sede = sede;
  }

  public Ellipse2D getZonaCobertura() {
    return zonaCobertura;
  }

  public void setZonaCobertura(Ellipse2D zonaCobertura) {
    this.zonaCobertura = zonaCobertura;
  }

  public static Rectangle2D getCuadrante() {
    return cuadrante;
  }

  public static void setCuadrante(Rectangle2D cuadrante) {
    Plano.cuadrante = cuadrante;
  }

}