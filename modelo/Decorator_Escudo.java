package modelo;

public class Decorator_Escudo extends Decorator_spaceship {

    public Decorator_Escudo(spaceship_1 naveDecorada) {
        super(naveDecorada);
    }
     @Override
    public void recibirDanio(int danio) {
        // no modifica la vida cuando recibe danio
    }

}
