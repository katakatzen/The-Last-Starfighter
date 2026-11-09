package modelo;

// Enemigo Violeta: se mueve en linea recta y su misil apunta a la ultima posicion de la nave
public class Enemigo_Violeta extends Enemigo {

    public Enemigo_Violeta(int x, int y, int width, int height, int life, int speed, int speedY) {
        super(x, y, width, height, life, speed, speedY, new MovimientoRecto());
    }

    @Override
    public misil atacar(personaje_base objetivo){
        // Solo dispara si está a la derecha del jugador
        if (getX() > objetivo.getX()) {
            int objetivoX = objetivo.getX() + (objetivo.getWidth() / 2);
            int objetivoY = objetivo.getY() + (objetivo.getHeight() / 2);
            return new misilTeledirigido(
                getX(),
                getY() + getHeight() / 2 - 5,
                10, 10,
                12, // velocidad del misil
                objetivoX,
                objetivoY
            );
    }
    return null; // no dispara si ya pasó del jugador
}

    @Override
    public int getPuntos(){
        return 500;
    }
    @Override
    public TipoEnemigo getTipo(){
        return TipoEnemigo.VIOLETA;
    }
}