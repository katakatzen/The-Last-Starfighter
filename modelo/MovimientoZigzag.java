package modelo;

import java.util.Random;

public class MovimientoZigzag implements EstrategiaMovimiento {

    private final Random random = new Random();
    private int contador = 0;
    private int tCambio = random.nextInt(101) + 50; // entre 50 y 150 frames

    @Override
    public void mover(personaje_base p) {
        contador++;
        if (contador >= tCambio || p.getY() <= 0 || p.getY() >= Pantalla.ALTO - p.getHeight()) {
            p.setSpeedY(-p.getSpeedY()); // invierte velocidad en Y
            tCambio = random.nextInt(101) + 50;
            contador = 0;
        }
        p.setX(p.getX() - p.getSpeed());
        p.setY(p.getY() + p.getSpeedY());
    }
}
