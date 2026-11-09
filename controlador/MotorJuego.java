package controlador;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.Timer;
import modelo.*;
import vista.*;
public class MotorJuego {

    private final panel panel_juego;
    private Ventanas ventana; 
    
    private int puntos = 0;
    private List<misil> misiles = new ArrayList<>();
    private List<misil> misilesEnemigos = new ArrayList<>();
    private List<Enemigo> enemies = new ArrayList<>(); 

    private boolean VOLAR_IZQUIERDA;
    private boolean VOLAR_DERECHA;
    private boolean VOLAR_ARRIBA;
    private boolean VOLAR_ABAJO;
    private boolean DISPARAR;

    private AplicacionDecorators aplicacionDecorators;

    private final Timer gameLoop;
    private final Random r = new Random(); 
    
    private long ultimoDisparo = 0;
    private boolean usarSpriteenemie = true;
    private int contadorFramesEnemigos = 0;
    private controladorEnemigos controladorEnemigos;
    private static final int VIDA_INICIAL = 12;
    private static final double PENALIZACION_ESCAPE = 0.5; // pierde la mitad de lo que valia el enemigo

    public MotorJuego(panel panel_juego) {
        this.panel_juego = panel_juego; // Guarda la referencia al panel
        spaceship_1 player = new spaceship_1(80, 270, 150, 150, VIDA_INICIAL, 12, 12, false);
        this.aplicacionDecorators = new AplicacionDecorators(player);
        this.controladorEnemigos = new controladorEnemigos(this.enemies, this.r);
        gameLoop = new Timer(16, (ActionEvent e) -> {
            update();
            panel_juego.getFondo().actualizarOffsets(); // anima el fondo
            panel_juego.repaint(); // redibuja la escena
        });
    }

    public void update() {
        spaceship_1 player = aplicacionDecorators.getPlayer();
        
        int speed = player.getSpeed();
        if (VOLAR_IZQUIERDA) player.setX(player.getX() - speed);
        if (VOLAR_DERECHA) player.setX(player.getX() + speed);
        if (VOLAR_ARRIBA) player.setY(player.getY() - speed);
        if (VOLAR_ABAJO) player.setY(player.getY() + speed);
        mantenerDentroDePantalla(player);
        if (DISPARAR) {
            long now = System.currentTimeMillis();
            if (now - ultimoDisparo >= 300) {
                misiles.add(player.disparar()); 
                ultimoDisparo = now;
            }
        }
        // Animación de enemigos
        contadorFramesEnemigos++;
        if (contadorFramesEnemigos >= 5) {
            usarSpriteenemie = !usarSpriteenemie;
            contadorFramesEnemigos = 0;
        }
        // Logica de enemigos
        controladorEnemigos.gestionarDisparosEnemigos(player, misilesEnemigos);
        misilesEnemigos.forEach(m -> m.MovimientoMisil());
        enemies.forEach(e -> e.movimiento());
        misiles.forEach(m -> m.MovimientoMisil());
        controladorEnemigos.gestionarAparicionFinal();
        eliminarFueraDePantalla();
        // Logica de combate
        int puntosObtenidos = Gestor_Combate.Verificar_Impacto(misiles, enemies);
        this.puntos += puntosObtenidos;
        Gestor_Combate.Verificar_Impacto(misilesEnemigos, player);
        this.puntos += Gestor_Combate.Verificar_Colision(player, enemies);
        //Logica powerups
        aplicacionDecorators.gestionarPowerUps();
        // Verifica si el jugador perdio
        if (!player.estaVivo()) {
            pausajuego();
            if (ventana != null) {
                ventana.mostrarGameOver(puntos);
                cargaBDD carga = new cargaBDD();
                carga.guardarPuntajeFinal(ventana.getNombreJugador(), puntos);
            }
        }
    }

    // La nave no puede salir de la ventana
    private void mantenerDentroDePantalla(spaceship_1 player) {
        int ancho = getAnchoPantalla();
        int alto = getAltoPantalla();
        player.setX(Math.max(0, Math.min(player.getX(), ancho - player.getWidth())));
        player.setY(Math.max(0, Math.min(player.getY(), alto - player.getHeight())));
    }

    // Borra misiles y enemigos que ya salieron de la pantalla (antes se acumulaban para siempre)
    private void eliminarFueraDePantalla() {
        int ancho = getAnchoPantalla();
        int alto = getAltoPantalla();
        int margen = 200;
        misiles.removeIf(m -> m.getX() > ancho + margen);
        misilesEnemigos.removeIf(m -> m.getX() < -margen || m.getX() > ancho + margen
                                   || m.getY() < -margen || m.getY() > alto + margen);
        // Enemigos que se escaparon por la izquierda: se borran y restan puntos
        java.util.Iterator<Enemigo> it = enemies.iterator();
        while (it.hasNext()) {
            Enemigo e = it.next();
            if (e.getX() + e.getWidth() < 0) {
                puntos = Math.max(0, puntos - (int) (e.getPuntos() * PENALIZACION_ESCAPE));
                it.remove();
            }
        }
    }

    private int getAnchoPantalla() {
        return panel_juego.getWidth() > 0 ? panel_juego.getWidth() : 1920;
    }

    private int getAltoPantalla() {
        return panel_juego.getHeight() > 0 ? panel_juego.getHeight() : 1080;
    }

    ////// METODOS TECLADO////
    public void setVOLAR_IZQUIERDA(boolean estado) { this.VOLAR_IZQUIERDA = estado; }
    public void setVOLAR_DERECHA(boolean estado) { this.VOLAR_DERECHA = estado; }
    public void setVOLAR_ARRIBA(boolean estado) { this.VOLAR_ARRIBA = estado; }
    public void setVOLAR_ABAJO(boolean estado) { this.VOLAR_ABAJO = estado; }
    public void setDISPARAR(boolean estado) { this.DISPARAR = estado; }
    ////// GETTERS VISTA /////
    public spaceship_1 getPlayer() { return aplicacionDecorators.getPlayer(); }
    public List<misil> getMisiles() { return misiles; }
    public List<misil> getMisilesEnemigos() { return misilesEnemigos; }
    public List<Enemigo> getEnemies() { return enemies; }
    public int getPuntos() { return puntos; }
    public boolean getUsarSpriteenemie() { return usarSpriteenemie; }
    public FondoAnimado getFondo() { return panel_juego.getFondo();}
    public boolean isItemVisible() { return aplicacionDecorators.isItemVisible(); }
    public Rectangle getItemRect() { return aplicacionDecorators.getItemRect(); }
    public TipoPowerUp getTipoItem() { return aplicacionDecorators.getTipoItem(); }
    public boolean isEfectoActivo() { return aplicacionDecorators.isEfectoActivo(); }
    public long getTiempoEfectoRestante() { return aplicacionDecorators.getTiempoEfectoRestante(); }
    ////// CONTROL DEL JUEGO //////
    public void empezarjuego() { gameLoop.start(); }
    public void pausajuego() { gameLoop.stop(); }
    
    public void resetear() {
        pausajuego();
        
        puntos = 0;
        
        misiles.clear();
        misilesEnemigos.clear();
        enemies.clear();

        spaceship_1 nuevoPlayer = new spaceship_1(80, 270, 150, 150, VIDA_INICIAL, 12, 12, false);
        aplicacionDecorators = new AplicacionDecorators(nuevoPlayer);
        controladorEnemigos = new controladorEnemigos(enemies, r); // la dificultad vuelve a nivel 0
        
        VOLAR_IZQUIERDA = false;
        VOLAR_DERECHA = false;
        VOLAR_ARRIBA = false;
        VOLAR_ABAJO = false;
        DISPARAR = false;
        
        ultimoDisparo = 0;
        contadorFramesEnemigos = 0;
    }
    
    public void setVentana(Ventanas ventana) {
        this.ventana = ventana;
    }
}