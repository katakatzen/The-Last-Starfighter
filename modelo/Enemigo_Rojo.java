package modelo;

// Enemigo Rojo: se mueve en ZIGZAG y dispara misiles horizontales
public class Enemigo_Rojo extends Enemigo {

    public Enemigo_Rojo(int x, int y, int width, int height, int life, int speed, int speedY) {
        super(x, y, width, height, life, speed, speedY, new MovimientoZigzag());
    }


    @Override
    public misil atacar(personaje_base objetivo){// misiles del enemigo con movimiento horizontal
        return new misil(getX(), getY() + getHeight() / 2 - 5, 10, 10, -15);
    }

    @Override
    public int getPuntos(){
        return 200;
    }

    @Override
    public TipoEnemigo getTipo(){
        return TipoEnemigo.ROJO;
    }
    
}