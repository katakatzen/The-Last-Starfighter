package controlador;

import java.util.List;
import java.util.Random;
import modelo.*;

// Dinamica del juego en cuanto aparicion de enemigos.
// La dificultad sube de nivel cada 30 segundos de juego.
public class controladorEnemigos {

    private List<Enemigo> enemies;
    private Random r;
    private long ultimaNave = 0;
    private int Fase = 1;

    private static final long duracionFase1 = 30000; // 30 segundos
    private static final long duracionFase2 = 10000; // 10 segundos

    // Dificultad progresiva (se cuenta en frames para que la pausa no la afecte)
    private static final int FRAMES_POR_NIVEL = 1800; // 30 s a 60 fps
    private static final int NIVEL_MAXIMO = 8;
    private int framesJugados = 0;

    private long tInicioFase;

    public controladorEnemigos(List<Enemigo> enemies, Random r) {
        this.enemies = enemies;
        this.r = r;
        this.ultimaNave = System.currentTimeMillis();
        this.tInicioFase = System.currentTimeMillis();
        this.Fase = 1;
    }

    // 0 al empezar, sube 1 cada 30 segundos hasta NIVEL_MAXIMO
    public int getNivel() {
        return Math.min(framesJugados / FRAMES_POR_NIVEL, NIVEL_MAXIMO);
    }

    private void crearEnemigo(TipoEnemigo tipo) {
        int y = 30 + r.nextInt(Pantalla.ALTO - 100 - 60); // dentro del alto real de la pantalla
        int speedY = r.nextInt(5) + 1;
        if (r.nextBoolean()) speedY = -speedY;
        Enemigo nuevoEnemigo = Factory_Enemigos.crearEnemigo(tipo, y, speedY);
        nuevoEnemigo.setSpeed(nuevoEnemigo.getSpeed() + getNivel() / 2); // mas rapidos con el nivel
        enemies.add(nuevoEnemigo);
    }

    public void gestionarAparicionTranquila() {
        long now = System.currentTimeMillis();
        long intervalo = Math.max(1200, 3000 - getNivel() * 300); // de 3 s baja hasta 1,2 s

        if (now - ultimaNave >= intervalo) {
            crearEnemigo(r.nextBoolean() ? TipoEnemigo.VIOLETA : TipoEnemigo.AZUL);
            ultimaNave = now;
        }
    }

    public void gestionarAparicionOla() {
        long now = System.currentTimeMillis();
        long intervalo = Math.max(400, 1000 - getNivel() * 100); // de 1 s baja hasta 0,4 s

        if (now - ultimaNave >= intervalo) {
            crearEnemigo(r.nextBoolean() ? TipoEnemigo.ROJO : TipoEnemigo.AZUL);
            ultimaNave = now;
        }
    }

    public void gestionarAparicionFinal() {
        framesJugados++;
        long now = System.currentTimeMillis();
        long tFase = now - this.tInicioFase;

        if (this.Fase == 1) {
            gestionarAparicionTranquila();
            if (tFase >= duracionFase1) {
                this.Fase = 2; // Cambia a Fase 2
                this.tInicioFase = now;
                System.out.println("Fase 2 - nivel " + getNivel());
            }

        } else if (this.Fase == 2) {
            gestionarAparicionOla();
            if (tFase >= duracionFase2) {
                this.Fase = 1; // Vuelve a Fase 1
                this.tInicioFase = now;
                System.out.println("Fase 1 - nivel " + getNivel());
            }
        }
    }

    public void gestionarDisparosEnemigos(spaceship_1 player, List<misil> misilesEnemigos) {
        int probabilidad = 4 + getNivel(); // de 1% sube hasta 3% por frame (sobre 400)
        for (Enemigo e : this.enemies) {
            if (r.nextInt(400) < probabilidad) {
                misil misilEnemigo = e.atacar(player);
                if (misilEnemigo != null) {
                    misilesEnemigos.add(misilEnemigo);
                }
            }
        }
    }
}
