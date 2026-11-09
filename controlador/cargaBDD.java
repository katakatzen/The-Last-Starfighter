package controlador;

public class cargaBDD {

      public void guardarPuntajeFinal(String nombre,  int puntaje) {
         
        if (nombre != null && !nombre.trim().isEmpty()) {
            modelo.JugadorBD jugador = new modelo.JugadorBD(nombre.trim(), puntaje);
            dao.JugadorDAO dao = new dao.JugadorDAO();
            dao.guardarPuntaje(jugador);

    }
}}