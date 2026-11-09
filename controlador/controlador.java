package controlador;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class controlador implements KeyListener {

    private final MotorJuego motor;    

    public controlador(MotorJuego motor) {
        this.motor = motor; 
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Llama a los setters del MotorJuego
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:  motor.setVOLAR_IZQUIERDA(true); break;
            case KeyEvent.VK_RIGHT: motor.setVOLAR_DERECHA(true); break;
            case KeyEvent.VK_UP:    motor.setVOLAR_ARRIBA(true); break;
            case KeyEvent.VK_DOWN:  motor.setVOLAR_ABAJO(true); break;
            case KeyEvent.VK_SPACE: motor.setDISPARAR(true); break;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e){
        // Llama a los setters del MotorJuego
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:   motor.setVOLAR_IZQUIERDA(false); break;
            case KeyEvent.VK_RIGHT:  motor.setVOLAR_DERECHA(false); break;
            case KeyEvent.VK_UP:     motor.setVOLAR_ARRIBA(false); break;
            case KeyEvent.VK_DOWN:   motor.setVOLAR_ABAJO(false); break;
            case KeyEvent.VK_SPACE:  motor.setDISPARAR(false); break;
        }
    }
}