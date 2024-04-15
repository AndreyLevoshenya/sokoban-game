package logic;

import java.io.InputStream;
import java.util.Scanner;

import static utils.Constants.INVALID_CHARACTER;

public class LevelBuilder {

    private final Scanner s;

    public LevelBuilder(InputStream in) {
        s = new Scanner(in);
    }

    public void setScanner(int num) {
        String line;
        if (num > 1 && num <= 91) {
            while (s.hasNextLine()) {
                line = s.nextLine();
                if (line.contains(String.valueOf(num - 1))) {
                    break;
                }
            }
        }
        s.nextLine();
    }

    public Level readLevel(int num) {
        setScanner(num);
        return readNextLevel();
    }

    public Level readNextLevel() {
        Level level = new Level();
        String line;
        int i = 0;
        line = s.nextLine();

        while (line.length() > 0) {
            if (line.charAt(0) == ';') {
                int j = 1;
                while (line.charAt(j) == ' ') {
                    j++;
                }
                StringBuilder levelNumber = new StringBuilder();
                do {
                    levelNumber.append(line.charAt(j));
                    j++;
                } while (j < line.length());
                level.setNumberOfLevel(Integer.parseInt(levelNumber.toString()));
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

