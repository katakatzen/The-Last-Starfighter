package modelo;
import java.util.Iterator;
import java.util.List;

public class Gestor_Combate {

    public static int Verificar_Impacto(List<misil> misilesJugador, List<Enemigo> enemigos) {
        int puntosGanados = 0;
        
        Iterator<misil> iterMisiles = misilesJugador.iterator();

        while (iterMisiles.hasNext()) {
            misil misil = iterMisiles.next();
            Iterator<Enemigo> iterEnemigos = enemigos.iterator();

            while (iterEnemigos.hasNext()) {
                Enemigo e = iterEnemigos.next();

                if (misil.colision(e)) {
                    e.recibirDanio(misil.getDamage()); // El enemigo recibe daño
                    iterMisiles.remove();         

                    if (!e.estaVivo()) {
                        puntosGanados += e.getPuntos(); 
                        iterEnemigos.remove();    
                    }
                    
                    break; 
                }
            }
        }
        return puntosGanados;
    }

    public static void Verificar_Impacto(List<misil> misilesEnemigos, spaceship_1 jugador) {

        if (jugador.estaHerido()) {
            return; 
        }

        Iterator<misil> iterMisiles = misilesEnemigos.iterator();

        while (iterMisiles.hasNext()) {
            misil misil = iterMisiles.next();

            if (misil.colision(jugador)) {
                jugador.recibirDanio(misil.getDamage()); // El jugador recibe daño
                System.out.println("Vida del jugador: " + jugador.getLife());
                iterMisiles.remove();            
                break; 
            }
        }
    }

 
    // Devuelve los puntos ganados si el choque destruye al enemigo
    public static int Verificar_Colision(spaceship_1 jugador, List<Enemigo> enemigos) {
        if (jugador.estaHerido()) {
            return 0;
        }

        Iterator<Enemigo> iterEnemigos = enemigos.iterator();
        while (iterEnemigos.hasNext()) {
            Enemigo enemigo = iterEnemigos.next();

            if (jugador.getBounds().intersects(enemigo.getBounds())) {
                jugador.recibirDanio(1);
                enemigo.recibirDanio(1);

                if (!enemigo.estaVivo()) {
                    iterEnemigos.remove(); // antes quedaba en pantalla con vida 0
                    return enemigo.getPuntos();
                }
                return 0;
            }
        }
        return 0;
    }
    
}