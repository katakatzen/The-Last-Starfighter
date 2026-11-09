package modelo;

public class Factory_Enemigos {
    public static Enemigo crearEnemigo(TipoEnemigo tipo, int y, int speedY) {
        switch (tipo) {
            case VIOLETA:
                return new Enemigo_Violeta(1600, y, 100, 100, 6, 1, speedY);
            case ROJO:
                return new Enemigo_Rojo(1600, y, 100, 100, 1, 1, speedY);
            case AZUL:
                return new Enemigo_Azul(1600, y, 100, 100, 3, 2, speedY);
            default:
                throw new IllegalArgumentException("Tipo de enemigo desconocido: " + tipo);
        }
    }
}
