package logic;

import java.io.InputStream;
import java.util.Scanner;

import static utils.Constants.INVALID_CHARACTER;

public class LevelBuilder {

    private final Scanner s;

    public LevelBuilder(InputStream in) {
        s = new Scanner(in);
    }

    public Level readNextLevel() {
        Level level = new Level();
        String line;
        int i = 0;
        try {
            line = s.nextLine();
        } catch (Exception e) {
            return null;
        }
        while (line.length() > 0) {
            if (line.charAt(0) == ';') {
                int j = 1;
                while (line.charAt(j) == ' ') {
                    j++;
                }
            } else {
                for (int j = 0; j < line.length(); j++) {
                    switch (line.charAt(j)) {
                        case '#' -> level.addWall(i, j);
                        case '@' -> level.addPlayer(i, j);
                        case '$' -> level.addBox(i, j);
                        case '+' -> {
                            level.addPlayer(i, j);
                            level.addTarget(i, j);
                        }
                        case '*' -> {
                            level.addBox(i, j);
                            level.addTarget(i, j);
                        }
                        case '.' -> level.addTarget(i, j);
                        case ' ' -> {
                        }
                        default -> {
                            System.err.println(INVALID_CHARACTER);
                            System.exit(1);
                        }
                    }
                }
            }
            line = s.nextLine();
            i++;
        }

        return level;
    }
}

