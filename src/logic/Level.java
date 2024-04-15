package logic;

import java.io.Serializable;

public class Level implements Serializable {
    private int numberOfLevel;
    private static final int WALL = 1;
    private static final int PLAYER = 2;
    private static final int TARGET = 4;
    private static final int BOX = 8;

    private int[][] board;
    private int l;
    private int c;

    private int playerL;
    private int playerC;

    private int countTarget;
    private int countBoxOnTarget;

    public Level() {
        board = new int[1][1];
        l = 0;
        c = 0;
    }

    private void resizeBoard(int l, int c) {
        int oldL = board.length;
        int oldC = board[0].length;

        if ((oldL <= l) || (oldC <= c)) {
            int newL = adjust(oldL, l);
            int newC = adjust(oldC, c);

            int[][] newBoard = new int[newL][newC];
            for (int i = 0; i < oldL; i++)
                System.arraycopy(board[i], 0, newBoard[i], 0, oldC);
            board = newBoard;
        }
        if (this.l <= l)
            this.l = l + 1;
        if (this.c <= c)
            this.c = c + 1;
    }

    private int adjust(int i, int j) {
        while (i <= j)
            i *= 2;
        return i;
    }

    private void addContent(int content, int i, int j) {
        resizeBoard(i, j);
        board[i][j] |= content;
        if (content == BOX) {
            if (hasTarget(i, j)) {
                countBoxOnTarget++;
            }
        }
    }

    public void addWall(int i, int j) {
        addContent(WALL, i, j);
    }

    public void addPlayer(int i, int j) {
        addContent(PLAYER, i, j);
        playerL = i;
        playerC = j;
    }

    public void addBox(int i, int j) {
        addContent(BOX, i, j);
    }

    public void addTarget(int i, int j) {
        addContent(TARGET, i, j);
        countTarget++;
        if (hasBox(i, j)) {
            countBoxOnTarget++;
        }
    }

    public int getLines() {
        return l;
    }

    public int getColumns() {
        return c;
    }

    public boolean hasWall(int i, int j) {
        return (board[i][j] & WALL) != 0;
    }

    public boolean hasTarget(int i, int j) {
        return (board[i][j] & TARGET) != 0;
    }

    public boolean hasPlayer(int i, int j) {
        return (board[i][j] & PLAYER) != 0;
    }

    public boolean hasBox(int i, int j) {
        return (board[i][j] & BOX) != 0;
    }

    public boolean move(int l, int c) {
        int toL = playerL + l;
        int toC = playerC + c;
        if (hasBox(toL, toC)) {
            int boxL = toL + l;
            int boxC = toC + c;
            if (isFree(boxL, boxC)) {
                delete(BOX, toL, toC);
                addContent(BOX, boxL, boxC);
            }
        }
        if (isFree(toL, toC)) {
            delete(PLAYER, playerL, playerC);
            playerL = toL;
            playerC = toC;
            addContent(PLAYER, playerL, playerC);
            return true;
        }
        return false;
    }

    private boolean isFree(int l, int c) {
        return !hasWall(l, c) && !hasBox(l, c);
    }

    private void delete(int element, int i, int j) {
        board[i][j] &= ~element;
        if (element == BOX) {
            if (hasTarget(i, j)) {
                countBoxOnTarget--;
            }
        }
    }

    public boolean isFinished() {
        return countBoxOnTarget == countTarget;
    }

    public int getNumberOfLevel() {
        return numberOfLevel;
    }

    public void setNumberOfLevel(int numberOfLevel) {
        this.numberOfLevel = numberOfLevel;
    }
}
