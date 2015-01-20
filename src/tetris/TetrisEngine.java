package tetris;

import java.awt.Color;

public class TetrisEngine implements Periodic {

    private static TetrisEngine instance;
    public Timer timer;
    public boolean stop = true;
    int level = 1;
    int score = 0;
    int lines = 0;
    int cost[] = {100, 300, 700, 1500};
    public int[][] playingField = new int[20][10];
    public int currentTetrimonosX = 4;
    public int currentTetrimonosY = -2;
    public int currentTetrimonos;
    private int nextTetrimonos;
    public boolean[][] currentTetrimonosShape = new boolean[4][4];

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

    private TetrisEngine() {
        nextTetriminos();
    }

    public int getNextTetriminos() {
        return nextTetrimonos;
    }

    public static Color getTetriminosColor(int tetriminos) {
        switch (tetriminos) {
            case 1:
                return new Color(0x00, 0xf0, 0xf0);   //I
            case 2:
                return new Color(0x00, 0x00, 0xf0);   //J
            case 3:
                return new Color(0xf0, 0xa0, 0x00);   //L
            case 4:
                return new Color(0xf0, 0xf0, 0x00);   //O
            case 5:
                return new Color(0x00, 0xf0, 0x00);   //S
            case 6:
                return new Color(0xa0, 0x00, 0xf0);   //T
            case 7:
                return new Color(0xf0, 0x00, 0x00);   //Z
            default:
                return Color.BLACK;
        }
    }

    public static TetrisEngine getInstance() {
        if (instance == null) {
            instance = new TetrisEngine();
        }
        return instance;
    }

    public void nextTetriminos() {
        currentTetrimonosX = 4;
        currentTetrimonosY = -2;
        currentTetrimonos = nextTetrimonos;
        currentTetrimonosShape = tetriminosShapes[currentTetrimonos];
        nextTetrimonos = (int) (Math.random() * 7) + 1;
    }

    public void moveRight() {
        if (!stop && !timer.isPause()) {
            if (canMoveRight()) {
                currentTetrimonosX++;
            }
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

    public void moveLeft() {
        if (!stop && !timer.isPause()) {
            if (canMoveLeft()) {
                currentTetrimonosX--;
            }
        }
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

    public void rotate() {
        if (!stop && !timer.isPause()) {
            if (currentTetrimonos == 4) return;
            
            boolean temp[][] = new boolean[4][4];
            boolean result[][] = new boolean[4][4];
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    temp[i][j] = currentTetrimonosShape[i][j];
                }
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
                    for (int j = 0; j < 4; j++) {
                        currentTetrimonosShape[i][j] = result[i][j];
                    }
                }
            }
        }
    }

    public void doSomeThing() {
        moveDown();
    }

    public void moveDown() {
        if (!stop && !timer.isPause()) {
            if (isBottom()) {
                flush();
                lineAnalyse();
                nextTetriminos();
            } else {
                currentTetrimonosY++;
            }

            Mainframe.getInstance().setScoreLabels(score, lines, level);
            Mainframe.getInstance().repaintPlayingPanel();
        }
    }

    public void moveSpeedDown() {
        while (!isBottom()) {
            moveDown();
        }
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

    public void flush() {
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

    public void lineAnalyse() {
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
                timer.setPeriod(800 - level * 30);
            } else {
                timer.setPeriod(600);
            }
            Mainframe.getInstance().setScoreLabels(score, lines, level);
        }
    }

    public void gameEnd() {
        timer.interrupt();
        if (!stop) {
            if (score > Mainframe.getInstance().getHiScore()) {
                Mainframe.getInstance().setHiScore(score);
            }
        }
        stop = true;
    }

    public void newGame() {
        playingField = new int[20][10];
        nextTetriminos();
        if (timer != null) {
            timer.interrupt();
            timer = null;
        }
        stop = false;
        timer = new Timer(this, 800);
        timer.start();
        level = 1;
        score = 0;
        lines = 0;
        Mainframe.getInstance().setScoreLabels(score, lines, level);
        Mainframe.getInstance().repaintPlayingPanel();
    }
}
