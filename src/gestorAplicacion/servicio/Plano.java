package gestorAplicacion.servicio;

import java.awt.geom.*;

public class Plano{
  private int y;
  private int x;
  private Rectangle2D sede;
  private Ellipse2D zonaCobertura;
  private static Rectangle2D cuadrante;

  // Constructor
  public Plano(int x, int y){
    this.x=x;
    this.y=y;
    cuadrante = new Rectangle2D.Double(0,50,50,50);
  }

  // Metodo Get y set
  // Metodo Get y set
  
  public static Rectangle2D crearSede(int h, int w, int x, int y){

    return new Rectangle2D.Double(x,y,w,h);
  }

  public Ellipse2D crearZonaCobertura(int w, int x, int y){
    
     return new Ellipse2D.Double(x,y,w,w);
  }

  public Point2D crearPuntoOrdenado(int x, int y){
    return new Point2D.Double(x,y);
  }

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
