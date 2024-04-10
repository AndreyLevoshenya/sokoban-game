package graphics;

import logic.Game;
import logic.KeyboardEvents;

import javax.swing.*;

import static utils.Constants.SOKOBAN;

public class GraphicInterface implements Runnable {
    private final Game game;
    private final GraphicLevel graphicLevel;

    public GraphicInterface(Game game) {
        this.game = game;
        this.graphicLevel = new GraphicLevel(game);
    }

    public static void start(Game game) {
        SwingUtilities.invokeLater(new GraphicInterface(game));
    }

    @Override
    public void run() {
        JFrame frame = new JFrame(SOKOBAN);
        frame.add(graphicLevel);
        frame.addKeyListener(new KeyboardEvents(this, game));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1500, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void repaint() {
        graphicLevel.repaint();
    }
}
