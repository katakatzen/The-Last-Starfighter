package modelo;

// Enemigo Azul: se mueve en ZIGZAG y ataca por colision
public class Enemigo_Azul extends Enemigo {

    public Enemigo_Azul(int x, int y, int width, int height, int life, int speed, int speedY) {
        super(x, y, width, height, life, speed, speedY, new MovimientoZigzag());
    }
 

   @Override
    public misil atacar(personaje_base objetivo){
        // El azul no dispara: hace dano al chocar (Gestor_Combate.Verificar_Colision)
        return null;
    }

    @Override
    public int getPuntos(){
        return 300;
    }

    @Override
    public TipoEnemigo getTipo(){
        return TipoEnemigo.AZUL;
    }
    
}
