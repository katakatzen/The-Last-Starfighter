package modelo;

import java.awt.Rectangle;
import java.util.Random;

public class AplicacionDecorators {
    
    private final spaceship_1 basePlayer;
    private spaceship_1 player;
    
    private long tiempoUltimoPowerUp = 0;
    private long tiempoInicioEfecto = 0;
    private boolean efectoActivo = false;
    private boolean itemVisible = false;
    private Rectangle itemRect;
    private TipoPowerUp tipoItem;
    private final long INTERVALO_APARICION = 30000; // 30 segundos
    private final long DURACION_EFECTO = 15000;     // 15 segundos
    
    private final Random r = new Random();
    
    public AplicacionDecorators(spaceship_1 player) {
        this.basePlayer = player;
        this.player = player;
        this.tiempoUltimoPowerUp = System.currentTimeMillis();
    }
    
    public void gestionarPowerUps() {
        long now = System.currentTimeMillis();
        // 1. Si hay un efecto activo, contamos su duración
        if (efectoActivo) {
            if (now - tiempoInicioEfecto > DURACION_EFECTO) {
                removerDecorador();
            }
        // 2. Si no hay efecto activo Y no hay item en pantalla, contamos para spawnear uno
        } else if (!itemVisible) {
            if (now - tiempoUltimoPowerUp > INTERVALO_APARICION) {
                aparecePowerUp();
            }
        // 3. Si hay un item visible, verificamos la colisión con el jugador
        } else if (itemVisible) {
            if (player.getBounds().intersects(itemRect)) {
                aplicarDecorador();
            }
        }
    }
    
    private void aparecePowerUp() {
        itemVisible = true;//activa la imagen 
        TipoPowerUp[] tipos = TipoPowerUp.values();
        tipoItem = tipos[r.nextInt(tipos.length)];
        System.out.println("aparece " + tipoItem);
        int x = 100 + r.nextInt(1000); 
        int y = 100 + r.nextInt(500); 
        
        itemRect = new Rectangle(x, y, 70, 70); // tamaño
    }
    
    private void aplicarDecorador() {
        itemVisible = false;//para el panel
        efectoActivo = true;
        tiempoInicioEfecto = System.currentTimeMillis();

        player = tipoItem.aplicar(player); // cada power-up sabe que decorador aplicar
    }
    
    private void removerDecorador() {
        efectoActivo = false;
        tiempoUltimoPowerUp = System.currentTimeMillis(); 
        player = basePlayer;
    }
    
    public long getTiempoEfectoRestante() {
        if (!efectoActivo) return 0;
        long transcurrido = System.currentTimeMillis() - tiempoInicioEfecto;
        return Math.max(0, DURACION_EFECTO - transcurrido);
    }
    
    // Getters
    public spaceship_1 getPlayer() { return player; }
    public boolean isItemVisible() { return itemVisible; }
    public Rectangle getItemRect() { return itemRect; }
    public TipoPowerUp getTipoItem() { return tipoItem; }
    public boolean isEfectoActivo() { return efectoActivo; }
}