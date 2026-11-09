package modelo;

public class Decorator_misilx3 extends Decorator_spaceship {

    public Decorator_misilx3(spaceship_1 naveDecorada) {
        super(naveDecorada);
    }

    @Override
    public misil disparar() {
        int x = getX() + getWidth() - 40;
        int y = getY() + getHeight() / 2 - 22;
        
        return new misil(x, y, 5, 5, 15, 3);
    }
}