package tetris;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class TetrisEngine {

    public boolean stop = true;
    public boolean pause = false;
    public int[][] playingField = new int[20][10];
    public int currentTetrimonosX = 4;
    public int currentTetrimonosY = -3;
    public int currentTetrimonos;
    public boolean[][] currentTetrimonosShape = new boolean[4][4];
    public int nextTetrimonos;

    public static final boolean[][][] tetriminosShapes = {
        {
            {false, false, false, false}, //empty
            {false, false, false, false},
            {false, false, false, false},
            {false, false, false, false},},
        {
            {false, true, false, false}, //I
            {false, true, false, false},
            {false, true, false, false},
            {false, true, false, false},},
        {
            {false, true, true, false}, //J
            {false, true, false, false},
            {false, true, false, false},
            {false, false, false, false},},
        {
            {false, true, true, false}, //L
            {false, false, true, false},
            {false, false, true, false},
            {false, false, false, false},},
        {
            {false, true, true, false}, //O
            {false, true, true, false},
            {false, false, false, false},
            {false, false, false, false},},
        {
            {false, true, true, false}, //S
            {true, true, false, false},
            {false, false, false, false},
            {false, false, false, false},},
        {
            {false, true, false, false}, //T
            {true, true, true, false},
            {false, false, false, false},
            {false, false, false, false},},
        {
            {true, true, false, false}, //Z
            {false, true, true, false},
            {false, false, false, false},
            {false, false, false, false},}
    };

    private static TetrisEngine instance;
    private final Timer timer;
    private final ActionListener taskPerformer;
    private int level = 1;
    private int score = 0;
    private int lines = 0;
    private final int cost[] = {100, 300, 700, 1500};
    private boolean gameOver = false;

    private TetrisEngine() {
        nextTetriminos();
        taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                moveDown();
            }
        };
        timer = new Timer(800, taskPerformer);
        timer.setRepeats(true);
        timer.start();
    }

    public static TetrisEngine getInstance() {
        if (instance == null) {
            instance = new TetrisEngine();
        }
        return instance;
    }

    public void newGame() {
        playingField = new int[20][10];
        nextTetriminos();
        stop = false;
        pause = false;
        gameOver = false;
        timer.setDelay(800);
        level = 1;
        score = 0;
        lines = 0;
    }

    public int getScore() {
        return score;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void moveRight() {
        if (!stop && !pause) {
            if (canMoveRight()) {
                currentTetrimonosX++;
            }
        }
    }

    public void moveLeft() {
        if (!stop && !pause) {
            if (canMoveLeft()) {
                currentTetrimonosX--;
            }
        }
    }

    public void rotate() {
        if (!stop && !pause) {
            if (currentTetrimonos == 4) {
                return;
            }

            boolean temp[][] = new boolean[4][4];
            boolean result[][] = new boolean[4][4];
            for (int i = 0; i < 4; i++) {
                System.arraycopy(currentTetrimonosShape[i], 0, temp[i], 0, 4);
            }
            result[0][0] = temp[0][2];
            result[0][1] = temp[1][2];
            result[0][2] = temp[2][2];
            result[0][3] = temp[0][3];
            result[1][0] = temp[0][1];
            result[1][1] = temp[1][1];
            result[1][2] = temp[2][1];
            result[1][3] = temp[3][1];
            result[2][0] = temp[0][0];
            result[2][1] = temp[1][0];
            result[2][2] = temp[2][0];
            result[2][3] = temp[2][3];
            result[3][0] = temp[3][0];
            result[3][1] = temp[1][3];
            result[3][2] = temp[3][2];
            result[3][3] = temp[3][3];

            boolean can_rotate = true;

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (result[i][j]) {
                        if (currentTetrimonosY + i > 19 || currentTetrimonosX + j < 0 || currentTetrimonosX + j > 9) {
                            can_rotate = false;
                        } else if (currentTetrimonosY + i >= 0) {
                            if (playingField[currentTetrimonosY + i][currentTetrimonosX + j] != 0) {
                                can_rotate = false;
                            }
                        }
                    }
                }
            }

            if (can_rotate) {
                for (int i = 0; i < 4; i++) {
                    System.arraycopy(result[i], 0, currentTetrimonosShape[i], 0, 4);
                }
            }
        }
    }

    public void moveDown() {
        if (!stop && !pause) {
            if (isBottom()) {
                flush();
                lineAnalyse();
                nextTetriminos();
            } else {
                currentTetrimonosY++;
            }

            TetrisMainFrame.getInstance().repaintPlayingPanel();
            TetrisMainFrame.getInstance().setScoreLabels(score, lines, level);
        }
    }

    public void moveSpeedDown() {
        while (!isBottom()) {
            moveDown();
        }
    }

    private boolean canMoveRight() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if ((currentTetrimonosShape[i][j]) && currentTetrimonosX + j <= 9) {
                    if (currentTetrimonosY + i < 0 && currentTetrimonosX + j == 9) {
                        return false;
                    } else {
                        if (currentTetrimonosY + i >= 0) {
                            if (currentTetrimonosX + j == 9) {
                                return false;
                            }
                            if (playingField[currentTetrimonosY + i][currentTetrimonosX + j + 1] != 0) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean canMoveLeft() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if ((currentTetrimonosShape[i][j]) && currentTetrimonosX + j >= 0) {
                    if (currentTetrimonosY + i < 0 && currentTetrimonosX + j == 0) {
                        return false;
                    } else {
                        if (currentTetrimonosY + i >= 0) {
                            if (currentTetrimonosX + j == 0) {
                                return false;
                            }
                            if (playingField[currentTetrimonosY + i][currentTetrimonosX + j - 1] != 0) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean isBottom() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if ((currentTetrimonosShape[i][j]) && currentTetrimonosY + i >= 0) {
                    if (currentTetrimonosY + i >= 19) {
                        return true;
                    }
                    if (playingField[currentTetrimonosY + i + 1][currentTetrimonosX + j] != 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void flush() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (currentTetrimonosShape[i][j]) {
                    if (currentTetrimonosY + i >= 0) {
                        playingField[currentTetrimonosY + i][currentTetrimonosX + j] = currentTetrimonos;
                    } else {
                        gameEnd();
                    }
                }
            }
        }
    }

    private void lineAnalyse() {
        int count = 0;
        int len = 0;
        for (int i = 0; i < 20; i++) {
            len = 0;
            for (int j = 0; j < 10; j++) {
                if (playingField[i][j] != 0) {
                    len++;
                }
            }
            if (len == 10) {
                count++;
                for (int k = i; k >= 0; k--) {
                    for (int l = 0; l < 10; l++) {
                        playingField[k][l] = (k != 0) ? playingField[k - 1][l] : 0;
                    }
                }
            }
        }
        if (count != 0) {
            lines += count;
            score += cost[count - 1];
            level = lines / 10 + 1;
            if (level < 20) {
                timer.setDelay(800 - level * 35);
            } else {
                timer.setDelay(100);
            }
        }
    }

    private void gameEnd() {
        gameOver = true;
        if (!stop) {
            if (score > TetrisMainFrame.getInstance().getHiScore()) {
                TetrisMainFrame.getInstance().setHiScore(score);
            }
        }
        stop = true;
    }

    private void nextTetriminos() {
        currentTetrimonosX = 4;
        currentTetrimonosY = -3;
        currentTetrimonos = nextTetrimonos;
        for (int i = 0; i < 4; i++) {
            System.arraycopy(tetriminosShapes[currentTetrimonos][i], 0, currentTetrimonosShape[i], 0, 4);
        }
        nextTetrimonos = (int) (Math.random() * 7) + 1;
        TetrisMainFrame.getInstance().repaintNextTetriminosPanel();
    }
}
