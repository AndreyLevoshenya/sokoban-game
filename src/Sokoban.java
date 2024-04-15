import graphics.GraphicInterface;
import logic.Game;
import logic.LevelBuilder;
import utils.Utils;

import java.io.InputStream;

import static utils.Constants.LEVELS_INFO;
import static utils.Constants.SOMETHING_WENT_WRONG;

public class Sokoban {
    public static void main(String[] args) {
        try {
            InputStream in = Utils.getResource(LEVELS_INFO);
            LevelBuilder levelBuilder = new LevelBuilder(in);
            Game game = new Game(levelBuilder);
            GraphicInterface.start(game);
        } catch (Exception e) {
            System.err.println(SOMETHING_WENT_WRONG + e);
        }
    }
}
