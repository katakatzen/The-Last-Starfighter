package modelo;

public class MovimientoRecto implements EstrategiaMovimiento {

    @Override
    public void mover(personaje_base p) {
        p.setX(p.getX() - p.getSpeed());
    }
}
