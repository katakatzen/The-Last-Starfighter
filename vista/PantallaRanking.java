package vista;

import dao.JugadorDAO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import modelo.JugadorBD;

public class PantallaRanking extends JPanel {
    private JTextArea areaRanking;
    private Image fondoRanking;
    private Font fuenteRetro;

    public PantallaRanking(Ventanas ventana) {
        setLayout(null);
        
        // Cargar imagen de fondo
        ImageIcon imgFondo = new ImageIcon("Assets/Ranking.png");
        fondoRanking = imgFondo.getImage();
        
        // Cargar fuente Retro Gaming
        fuenteRetro = cargarFuente();


        //area de ranking
        areaRanking = new JTextArea();
        areaRanking.setEditable(false);
        areaRanking.setOpaque(false); //fondo transparente
        areaRanking.setForeground(Color.WHITE);
        areaRanking.setFont(fuenteRetro != null ? fuenteRetro.deriveFont(28f) : new Font("Consolas", Font.BOLD, 28));

        JScrollPane scroll = new JScrollPane(areaRanking);
        scroll.setBounds(650, 180, 600, 400);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false); //scroll transparente también
        add(scroll);

        //Botón Volver
        JButton volver = new JButton("VOLVER");
        volver.setBounds(850, 650, 200, 60);
        volver.setFont(new Font("Arial", Font.BOLD, 22));
        volver.addActionListener(e -> ventana.mostrarPantalla("INICIO"));
        add(volver);
    }

    public void actualizarRanking() {
        JugadorDAO dao = new JugadorDAO();
        List<JugadorBD> top5 = dao.top5();

        StringBuilder sb = new StringBuilder();
        int pos = 1;
        for (JugadorBD j : top5) {
            sb.append(pos++)
              .append(". ")
              .append(j.getNombre())
              .append(" - ")
              .append(j.getPuntaje())
              .append(" pts\n");
        }
        areaRanking.setText(sb.toString());
    }
    
    private Font cargarFuente() {
        try {
            Font fuente = Font.createFont(Font.TRUETYPE_FONT, new java.io.File("Assets/Fuente/Retro_Gaming.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fuente);
            return fuente;
        } catch (Exception e) {
            System.err.println("No se pudo cargar Retro_Gaming.ttf");
            return null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibujar la imagen de fondo escalada al tamaño del panel
        if (fondoRanking != null) {
            g.drawImage(fondoRanking, 0, 0, getWidth(), getHeight(), this);
        }
    }
}