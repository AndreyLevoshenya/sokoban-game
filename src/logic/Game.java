package logic;

import static utils.Constants.CANNOT_MOVE;

public class Game {
    private Level current;
    private final LevelBuilder levelBuilder;

    public Game(LevelBuilder levelBuilder, int num) {
        this.levelBuilder = levelBuilder;
        readLevel(num);
    }

    public Game(LevelBuilder levelBuilder, Level current) {
        this.levelBuilder = levelBuilder;
        this.current = current;
        levelBuilder.setScanner(current.getNumberOfLevel() + 1);
    }

    public Level getLevel() {
        return current;
    }

    public void readLevel(int num) {
        current = levelBuilder.readLevel(num);
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
