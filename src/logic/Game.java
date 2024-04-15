package logic;

import static utils.Constants.CANNOT_MOVE;

public class Game {
    private Level current;
    private final LevelBuilder levelBuilder;

    public Game(LevelBuilder levelBuilder) {
        this.levelBuilder = levelBuilder;
        nextLevel();
    }

    public Level getLevel() {
        return current;
    }

    public boolean nextLevel() {
        current = levelBuilder.readNextLevel();
        return current != null;
    }

    public void move(int l, int c) {
        if (!current.move(l, c)) {
            System.err.println(CANNOT_MOVE);
        }
        if (current.isFinished()) {
            if (!nextLevel()) {
                System.exit(0);
            }
        }
    }
}
