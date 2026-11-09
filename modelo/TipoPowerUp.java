package modelo;

public enum TipoPowerUp {
    ESCUDO {
        @Override
        public spaceship_1 aplicar(spaceship_1 nave) {
            return new Decorator_Escudo(nave);
        }
    },
    MISIL {
        @Override
        public spaceship_1 aplicar(spaceship_1 nave) {
            return new Decorator_misilx3(nave);
        }
    };

    public abstract spaceship_1 aplicar(spaceship_1 nave);
}
