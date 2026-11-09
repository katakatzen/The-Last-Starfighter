package modelo;

public class misilTeledirigido extends misil {
    
    private int finalX;
    private int finalY;
    private boolean llegoFin = false;
    public misilTeledirigido(int x, int y, int width, int height, int speed, int finalX, int finalY) {
        super(x, y, width, height, speed);
        this.finalX = finalX;
        this.finalY = finalY;    
    }

    public void setFinalX(int finalX) {
        this.finalX = finalX;
    }

    public void setFinalY(int finalY) {
        this.finalY = finalY;
    }

    @Override
 public void MovimientoMisil() {
    
    if (!llegoFin) {

        double deltaX = finalX - getX();
        double deltaY = finalY - getY();
        double distancia = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        if (distancia > getSpeed()) { 
            double movimientoX = (deltaX / distancia) * getSpeed();
            double movimientoY = (deltaY / distancia) * getSpeed();
            setX(getX() + (int)movimientoX); //posicion actual + movimiento
            setY(getY() + (int)movimientoY);
        } else {
            setX(finalX); 
            setY(finalY);
            llegoFin = true; 
        }
    } else {
        setX(getX() - getSpeed()); // solo se actualiza en x
    }
} 
}
