package logic;

import graphics.GraphicInterface;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardEvents extends KeyAdapter {
    private final GraphicInterface graphicInterface;
    private final Game game;

    public KeyboardEvents(GraphicInterface graphicInterface, Game game) {
        this.graphicInterface = graphicInterface;
        this.game = game;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> move(-1, 0);
            case KeyEvent.VK_DOWN -> move(1, 0);
            case KeyEvent.VK_LEFT -> move(0, -1);
            case KeyEvent.VK_RIGHT -> move(0, 1);
            case KeyEvent.VK_Q -> System.exit(1);
            case KeyEvent.VK_N -> game.nextLevel();
        }
    }

    private void move(int l, int c) {
        game.move(l, c);
        graphicInterface.repaint();
    }
}
