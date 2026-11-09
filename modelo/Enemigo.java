package modelo;

// Clase base de todos los enemigos
public abstract class Enemigo extends personaje_base {

    private final EstrategiaMovimiento estrategia;

    public Enemigo(int x, int y, int width, int height, int life, int speed, int speedY,
                   EstrategiaMovimiento estrategia) {
        super(x, y, width, height, life, speed, speedY, false);
        this.estrategia = estrategia;
    }

    public final void movimiento() {
        estrategia.mover(this);
    }

    public abstract misil atacar(personaje_base objetivo);

    public abstract int getPuntos();

    public abstract TipoEnemigo getTipo();
}
