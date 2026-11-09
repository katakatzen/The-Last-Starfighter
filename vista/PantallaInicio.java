package vista;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class PantallaInicio extends JPanel {
    
    private final FondoAnimado fondo = new FondoAnimado();
       private String nombre;

        public PantallaInicio(Ventanas ventana){
        setLayout(null);//Posicionamiento Manual


        //Titulo Principal
        ImageIcon imagentitulo = new ImageIcon("Assets/titulo.png");
        Image titulo = imagentitulo.getImage().getScaledInstance(1200,800 , Image.SCALE_SMOOTH);
        ImageIcon imgTitulo = new ImageIcon(titulo);
        JLabel Labeltitulo = new JLabel(imgTitulo);
        Labeltitulo.setBounds(150, -150, 1200, 800);
        add(Labeltitulo);

        //Boton Jugar
        ImageIcon imagenPlay = new ImageIcon("Assets/boton_empezar.png");
        Image jugar = imagenPlay.getImage().getScaledInstance(200,80 , Image.SCALE_SMOOTH);
        ImageIcon imgJugar2 = new ImageIcon(jugar);
        JButton btnJugar = new JButton(imgJugar2);
        btnJugar.setBounds(675, 500, 200, 80);
        btnJugar.setBorderPainted(false);
        btnJugar.setContentAreaFilled(false);
        btnJugar.setFocusPainted(false);
        btnJugar.addActionListener(e -> {
              nombre = JOptionPane.showInputDialog(this, "Ingresar nombre:");
            ventana.reiniciarJuego();
        });
        add(btnJugar);

        //Boton Como jugar
        ImageIcon ImagenComo = new ImageIcon("Assets/boton_comojugar.png");
        Image Howto = ImagenComo.getImage().getScaledInstance(300,80 , Image.SCALE_SMOOTH);
        ImageIcon imgHowto = new ImageIcon(Howto);
        JButton btnComoJugar = new JButton(imgHowto);
        btnComoJugar.setBounds(620, 650, 300, 80);
        btnComoJugar.addActionListener(e -> ventana.mostrarPantalla("COMO_JUGAR"));
         btnComoJugar.setBorderPainted(false);
        btnComoJugar.setContentAreaFilled(false);
        btnComoJugar.setFocusPainted(false);
        add(btnComoJugar);

        //Boton de Ranking
        ImageIcon ImagenRank = new ImageIcon("Assets/rank.png");
        Image rank = ImagenRank.getImage().getScaledInstance(150,150 , Image.SCALE_SMOOTH);
        ImageIcon imgrank = new ImageIcon(rank);
        JButton btnRank = new JButton(imgrank);
        btnRank.setBounds(1300, 630, 150, 150);
        btnRank.addActionListener(e ->{ 
            System.out.println("Botón Ranking clickeado");
            ventana.getPantallaRanking().actualizarRanking();
            System.out.println("Ranking actualizado");
            ventana.mostrarPantalla("RANKING");
        });
        btnRank.setBorderPainted(false);
        btnRank.setContentAreaFilled(false);
        btnRank.setFocusPainted(false);
        add(btnRank);

       
        javax.swing.Timer fondoLoop = new javax.swing.Timer(16, e -> {
            fondo.actualizarOffsets(); // actualiza el fondo animado
            repaint();
            });
        fondoLoop.start();

};

 public String getNombre(){
        return this.nombre;
 }

@Override
 public void paintComponent(Graphics g){
        super.paintComponent(g);
        fondo.render(g, getWidth(), getHeight());
        
 }

   
}