package gameState;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface Statemethods {
    void update();
    void draw(Graphics g);
    void mouseClicked(MouseEvent e);
    void mousePressed(MouseEvent e);
    void mouseReleased(MouseEvent e);
    void mousedMoved(MouseEvent e);
    void keyPressed(KeyEvent e);
    void keyReleased(KeyEvent e);
}
