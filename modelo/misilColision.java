package modelo;

public class misilColision extends misil {
private personaje_base enemigo;
    public misilColision(int x, int y, int width, int height, int speed, int finalX, int finalY,personaje_base enemigo) {
        super(x, y, width, height, speed); 
        this.enemigo = enemigo;
    }

  @Override
  public void MovimientoMisil() {// hace seguimiento del enemigo
       if (enemigo != null) {
        this.setX(enemigo.getX());
        this.setY(enemigo.getY());
  } 
}

  @Override
  public boolean esVisible() { // representa el choque con el enemigo azul, no se dibuja
      return false;
  }
}
