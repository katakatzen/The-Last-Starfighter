package vista;

import controlador.MotorJuego;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import modelo.*; 

public class panel extends JPanel  {

    private MotorJuego motor;

    private final FondoAnimado fondo = new FondoAnimado();
    private BufferedImage spritePlayer1;
    private BufferedImage spritePlayer2;
    //sprites enemigos: [0]=frame 1, [1]=frame 2, [2]=dañado
    private final Map<TipoEnemigo, BufferedImage[]> spritesEnemigos = new EnumMap<>(TipoEnemigo.class);
    //sprites misiles
    private BufferedImage misilSprite;
    private BufferedImage mslSprite_Enemigorojo;
    // sprite vida
    private BufferedImage vida;
    //sprite powerups
    private final Map<TipoPowerUp, BufferedImage> spritesPowerUp = new EnumMap<>(TipoPowerUp.class);
    // hilo
    private Thread animationThread;
    private volatile boolean running = false; // 'volatile' es importante para hilos
    //Fuente
    private Font FuentePixel;
    private boolean usarSpriteplayer = true;
    private int contadorFrames = 0; 
    

    public panel() {

        this.setDoubleBuffered(true);
        loadSprites();
    }
    
    public void setMotor(MotorJuego motor) {
        this.motor = motor;
    }


    ///////// DIBUJO /////////
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        fondo.render(g, getWidth(), getHeight());

        if (motor == null) {
            return;
        }

        BufferedImage spriteActual = usarSpriteplayer ? spritePlayer1 : spritePlayer2;
        personaje_base player = motor.getPlayer(); // Pide el jugador al motor
        if (spriteActual != null) {
            g.drawImage(spriteActual, player.getX(), player.getY(), player.getWidth(), player.getHeight(), null);
        }

        // Dibuja misiles enemigos 
        for (misil m : motor.getMisilesEnemigos()) {
            // No dibujar misiles de colisión (enemigo azul)
            if (m.esVisible()) {
                g.drawImage(mslSprite_Enemigorojo, m.getX(), m.getY(), 30, 30, null);
            }
        }
        // Dibuja los misiles (pidiéndoselos al motor)
        for (misil m : motor.getMisiles()) {
            g.drawImage(misilSprite, m.getX(), m.getY(), 40, 40, null);
        }

    // Dibuja enemigos 
     boolean spriteAnimacion = motor.getUsarSpriteenemie();

    for (Enemigo enemy : motor.getEnemies()) {
        BufferedImage[] sprites = spritesEnemigos.get(enemy.getTipo()); // cada enemigo dice su tipo
        if (sprites == null) continue; // por si fallo la carga de imagenes
        BufferedImage spriteADibujar = spriteAnimacion ? sprites[0] : sprites[1];
        g.drawImage(spriteADibujar, enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight(), null);
        if (enemy.estaHerido()) {
            g.drawImage(sprites[2], enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight(), null);
        }
    }
    //Dibuja contador de puntaje 
        g.setColor(Color.WHITE);
if (FuentePixel != null) {
            g.setFont(FuentePixel.deriveFont(Font.BOLD, 50f));
        } else {
            g.setFont(new Font("Arial", Font.BOLD, 30));
        }
        g.drawString("PUNTOS  " + motor.getPuntos(), 520, 70);

       
    //Dibuja vida del jugador
        if(vida!= null){
            int cantidadVida = motor.getPlayer().getLife();

            for (int i = 0; i < cantidadVida; i++) {
                g.drawImage(vida, 230 + (i * 18), 30, 15, 50, null); // 12 vidas: segmentos mas anchos
            }
    }
 
    //Dibuja decorators en pantalla
    if (motor.isItemVisible()) {
        Rectangle itemRect = motor.getItemRect();
        g.drawImage(spritesPowerUp.get(motor.getTipoItem()), itemRect.x, itemRect.y, itemRect.width, itemRect.height, null);
    }

    // Dibuja decorator activo en la esquina superior derecha
    if (motor.isEfectoActivo()) {
        g.drawImage(spritesPowerUp.get(motor.getTipoItem()), 1000, 10, 100, 100, null);
    }
}

    public FondoAnimado getFondo() { return fondo; }

    private void loadSprites() {
        try {
            //nave
            this.spritePlayer1 = ImageIO.read(new File("Assets/nave/nave1.png"));
            this.spritePlayer2 = ImageIO.read(new File("Assets/nave/nave2.png"));
            //enemigos
            spritesEnemigos.put(TipoEnemigo.ROJO, cargar(
                    "Assets/Enemigo_rojo/Enemigo_rojo_1.png",
                    "Assets/Enemigo_rojo/Enemigo_rojo_2.png",
                    "Assets/Enemigo_rojo/Enemigo_rojo_dañado.png"));
            spritesEnemigos.put(TipoEnemigo.VIOLETA, cargar(
                    "Assets/Enemigo_violeta/Enemigo_violeta_1.png",
                    "Assets/Enemigo_violeta/Enemigo_violeta_2.png",
                    "Assets/Enemigo_violeta/Enemigo_violeta_dañado.png"));
            spritesEnemigos.put(TipoEnemigo.AZUL, cargar(
                    "Assets/Enemigo_azul/Enemigo_azul_1.png",
                    "Assets/Enemigo_azul/Enemigo_azul_2.png",
                    "Assets/Enemigo_azul/Enemigo_azul_dañado.png"));
            //misiles
            this.misilSprite = ImageIO.read(new File("Assets/misil.png"));
            this.mslSprite_Enemigorojo = ImageIO.read(new File("Assets/misilEnemigoRojo.png"));
            //vida
            this.vida = ImageIO.read(new File("Assets/vida.png"));
            //powerups
            spritesPowerUp.put(TipoPowerUp.ESCUDO, ImageIO.read(new File("Assets/Escudo.png")));
            spritesPowerUp.put(TipoPowerUp.MISIL, ImageIO.read(new File("Assets/Misilx3.png")));
            File fontFile = new File("Assets/Fuente/Retro_Gaming.ttf"); 
            Font fuenteBase = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fuenteBase);
            this.FuentePixel = fuenteBase;
        } catch (IOException | FontFormatException var2) { 
            if (this.FuentePixel == null) {
                System.err.println("¡Error al cargar fuente! Usando 'Arial' como respaldo.");
                this.FuentePixel = new Font("Arial", Font.BOLD, 20);
            }
        }
    }

    // Carga varias imagenes de una vez (frames de animacion de un enemigo)
    private BufferedImage[] cargar(String... rutas) throws IOException {
        BufferedImage[] imagenes = new BufferedImage[rutas.length];
        for (int i = 0; i < rutas.length; i++) {
            imagenes[i] = ImageIO.read(new File(rutas[i]));
        }
        return imagenes;
    }
}
