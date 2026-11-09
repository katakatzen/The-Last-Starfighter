package vista;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
public class PantallaGameOver extends JPanel {
    private final Ventanas ventana;
    private JLabel lblPuntaje; // Etiqueta para mostrar el puntaje
    private BufferedImage gameover;
    public PantallaGameOver(Ventanas ventana) {
        this.ventana = ventana;
        setLayout(null); // Usamos layout manual
        loadSprites(); // Cargar la imagen de fondo

        // Cargar la fuente retro_gaming
        Font retroFont = cargarFuente("Assets/Fuente/Retro_Gaming.ttf", 48f);

        // 2. Etiqueta para el puntaje
        lblPuntaje = new JLabel("Puntaje final: 0", SwingConstants.CENTER);
        lblPuntaje.setFont(retroFont != null ? retroFont : new Font("Arial", Font.BOLD, 48));
        lblPuntaje.setForeground(Color.WHITE);
        lblPuntaje.setBounds(0, 300, 1920, 60);
        add(lblPuntaje);

        // 3. Botón "Reintentar" con imagen
        JButton btnReintentar = crearBotonImagen("Assets/boton_reintentar.png", 500, 70);
        btnReintentar.setBounds(710, 500, 500, 70);
        btnReintentar.addActionListener(e -> {
            ventana.reiniciarJuego();
        });
        add(btnReintentar);

        // 4. Botón "Volver al Menú" con imagen
        JButton btnMenu = crearBotonImagen("Assets/boton_menu.png", 500, 70);
        btnMenu.setBounds(710, 600, 500, 70);
        btnMenu.addActionListener(e -> {
            ventana.mostrarPantalla("INICIO");
        });
        add(btnMenu);
        
      
    }


    public void setPuntajeFinal(int puntaje) {
        lblPuntaje.setText("Puntaje final: " + puntaje);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Dibuja la imagen de fondo escalada a toda la pantalla
        if (gameover != null) {
            g.drawImage(gameover, 0, 0, getWidth(), getHeight(), null);
        }
    }
    private void loadSprites( ) {
        try {
        this.gameover = ImageIO.read(new File("Assets/gameover.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private Font cargarFuente(String ruta, float tamaño) {
        try {
            Font fuente = Font.createFont(Font.TRUETYPE_FONT, new File(ruta));
            return fuente.deriveFont(tamaño);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private JButton crearBotonImagen(String rutaImagen, int ancho, int alto) {
        try {
            BufferedImage img = ImageIO.read(new File(rutaImagen));
            ImageIcon icon = new ImageIcon(img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH));
            JButton boton = new JButton(icon);
            boton.setBorderPainted(false);
            boton.setContentAreaFilled(false);
            boton.setFocusPainted(false);
            boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            return boton;
        } catch (Exception e) {
            e.printStackTrace();
            return new JButton("ERROR");
        }
    }
}