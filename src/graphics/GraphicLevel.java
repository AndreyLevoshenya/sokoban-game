package graphics;

import logic.Game;
import logic.Level;
import utils.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.*;

public class GraphicLevel extends JComponent {
    private final Game game;

    private final Image goal;
    private final Image box;
    private final Image boxOnGoal;
    private final Image wall;
    private final Image player;
    private final Image floor;

    public GraphicLevel(Game game) {
        this.game = game;
        goal = getImage(GOAL);
        box = getImage(BOX);
        boxOnGoal = getImage(BOX_ON_GOAL);
        wall = getImage(WALL);
        player = getImage(PLAYER);
        floor = getImage(FLOOR);
    }

    private Image getImage(String name) {
        Image image = null;
        InputStream in = Utils.getResource(IMAGES_PATH + name + PNG);
        try {
            image = ImageIO.read(in);
        } catch (IOException e) {
            System.err.println(CANNOT_GET_IMAGE);
            System.exit(1);
        }
        return image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;

        int width = getSize().width;
        int height = getSize().height;

        graphics2D.clearRect(0, 0, width, height);

        Level level = game.getLevel();

        int cellWidth = width / level.getColumns();
        int cellHeight = height / level.getLines();

        for (int i = 0; i < level.getLines(); i++) {
            for (int j = 0; j < level.getColumns(); j++) {
                int x = j * cellWidth;
                int y = i * cellHeight;

                if (level.hasTarget(i, j)) {
                    graphics2D.drawImage(goal, x, y, cellWidth, cellHeight, null);
                } else {
                    graphics2D.drawImage(floor, x, y, cellWidth, cellHeight, null);
                }

                if (level.hasWall(i, j)) {
                    graphics2D.drawImage(wall, x, y, cellWidth, cellHeight, null);
                } else if (level.hasBox(i, j)) {
                    if (level.hasTarget(i, j)) {
                        graphics2D.drawImage(boxOnGoal, x, y, cellWidth, cellHeight, null);
                    } else {
                        graphics2D.drawImage(box, x, y, cellWidth, cellHeight, null);
                    }
                } else if (level.hasPlayer(i, j)) {
                    graphics2D.drawImage(player, x, y, cellWidth, cellHeight, null);
                }
            }
        }
    }
}
