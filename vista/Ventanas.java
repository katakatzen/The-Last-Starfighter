package vista;

import controlador.MotorJuego;
import controlador.controlador;
import java.awt.*;
import javax.swing.*;

public class Ventanas extends JFrame {
    private CardLayout layout;
    private JPanel contenedor;
    private panel panelJuego;
    private MotorJuego motor;
    private boolean pausa = false;
    private PantallaInstrucciones PantallaInstrucciones;
    private PantallaRanking pantallaRanking;
    private PantallaGameOver pantallaGameOver;
    private PantallaInicio pantallaInicio ;

    public Ventanas() {
        layout = new CardLayout();
        contenedor = new JPanel(layout);

        // Pantalla de inicio
        pantallaInicio = new PantallaInicio(this);
        contenedor.add(pantallaInicio, "INICIO");
        

        panelJuego = new panel();
        motor = new MotorJuego(panelJuego); // El motor necesita al panel para llamar a repaint()
        motor.setVentana(this); // Inyecta la referencia de Ventanas al motor

        panelJuego.setMotor(motor); 

        controlador control = new controlador(motor);
        panelJuego.addKeyListener(control);

        // Panel de juego con botón de pausa
        
        panelJuego.setLayout(null); // El panel de juego principal
        JButton btnPausa = new JButton("Pausar");
        btnPausa.setBounds(20, 20, 100, 40);
        btnPausa.setFocusable(false); // Para que no robe el foco del KeyListener
        btnPausa.addActionListener(e -> {
            if (!pausa) {
                motor.pausajuego(); 
                btnPausa.setText("Reanudar");
            } else {
                motor.empezarjuego();
                btnPausa.setText("Pausar");
            }
            pausa = !pausa;
            panelJuego.requestFocusInWindow();
        });
        
        panelJuego.add(btnPausa); // Añade el botón directamente al panel de juego
        contenedor.add(panelJuego, "JUEGO");

        // Pantalla de instrucciones
        PantallaInstrucciones = new PantallaInstrucciones();
        contenedor.add(PantallaInstrucciones, "COMO_JUGAR");
        // Pantalla de ranking
        pantallaRanking = new PantallaRanking(this);
        contenedor.add(pantallaRanking, "RANKING");
        
        // Pantalla de Game Over
        pantallaGameOver = new PantallaGameOver(this);
        contenedor.add(pantallaGameOver, "GAMEOVER");

        add(contenedor);
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);



    }

    public void mostrarPantalla(String nombre) {
        layout.show(contenedor, nombre);

        if (nombre.equals("JUEGO")) {
            panelJuego.setFocusable(true);
            panelJuego.requestFocusInWindow();
        }
    }

    public void empezarjuego() {
        motor.empezarjuego();
    }

    public void pausajuego() {
        motor.pausajuego();
    }



    
    public void mostrarGameOver(int puntajeFinal) {
        pantallaGameOver.setPuntajeFinal(puntajeFinal);
        mostrarPantalla("GAMEOVER");
    }
    
    public void reiniciarJuego() {
        motor.resetear();
        mostrarPantalla("JUEGO");
        panelJuego.requestFocusInWindow();
        motor.empezarjuego();
    }

    public PantallaRanking getPantallaRanking() {
        return pantallaRanking;
        
    }
    public String getNombreJugador() {
    return pantallaInicio.getNombre();
}}