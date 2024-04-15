package graphics;

import logic.Game;
import logic.KeyboardEvents;
import logic.LevelBuilder;
import utils.Config;
import utils.Utils;

import javax.swing.*;
import java.awt.*;

import static utils.Constants.*;
import static utils.Utils.deserialize;

public class GraphicInterface implements Runnable {

    private LevelBuilder levelBuilder;
    private final JFrame frame = new JFrame(SOKOBAN);
    private final JPanel menu = new JPanel();
    private final JTextField enterLevelTextField;
    private final JButton enterLevelButton;

    private KeyboardEvents keyboardEvents;

    public GraphicInterface() {
        enterLevelTextField = new JTextField(ENTER_NUMBER_OF_LEVEL);
        enterLevelButton = new JButton(PLAY);
    }

    public static void start() {
        SwingUtilities.invokeLater(new GraphicInterface());
    }

    @Override
    public void run() {
        JButton continueButton = new JButton(CONTINUE);
        continueButton.addActionListener(e -> {
            Game game;
            GraphicLevel graphicLevel;
            Config config = deserialize();
            levelBuilder = new LevelBuilder(Utils.getResource(LEVELS_INFO));
            if (config != null) {
                game = new Game(levelBuilder, config.level());
            } else {
                game = new Game(levelBuilder, 1);
            }
            graphicLevel = new GraphicLevel(game);
            play(game, graphicLevel);
        });

        JButton newGameButton = new JButton(NEW_GAME);
        newGameButton.addActionListener(e -> {
            levelBuilder = new LevelBuilder(Utils.getResource(LEVELS_INFO));
            Game game = new Game(levelBuilder, 1);
            GraphicLevel graphicLevel = new GraphicLevel(game);
            play(game, graphicLevel);
        });

        JButton chooseLevelButton = new JButton(LEVELS);
        chooseLevelButton.addActionListener(e -> {
            enterLevelTextField.setVisible(true);
            enterLevelButton.setVisible(true);
            enterLevelButton.addActionListener(ex -> {
                levelBuilder = new LevelBuilder(Utils.getResource(LEVELS_INFO));
                Game game = new Game(levelBuilder, Integer.parseInt(enterLevelTextField.getText()));
                GraphicLevel graphicLevel = new GraphicLevel(game);
                play(game, graphicLevel);
            });
        });

        JButton exitButton = new JButton(EXIT);
        exitButton.addActionListener(e -> System.exit(1));
        menu.setLayout(new FlowLayout());
        menu.add(continueButton);
        menu.add(newGameButton);

        enterLevelTextField.setVisible(false);
        enterLevelButton.setVisible(false);
        menu.add(enterLevelTextField);
        menu.add(enterLevelButton);

        menu.add(chooseLevelButton);
        menu.add(exitButton);

        frame.add(menu);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1500, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    private void play(Game game, GraphicLevel graphicLevel) {
        menu.setVisible(false);
        frame.remove(menu);
        frame.add(graphicLevel);
        frame.requestFocusInWindow();
        keyboardEvents = new KeyboardEvents(this, game);
        frame.addKeyListener(keyboardEvents);
    }

    public void toMenu() {
        frame.removeKeyListener(keyboardEvents);
        Component[] components = frame.getContentPane().getComponents();
        for (var c : components) {
            c.setVisible(false);
            frame.remove(c);
        }
        menu.setVisible(true);
        frame.add(menu);
    }

    public void repaint() {
        frame.repaint();
    }
}
