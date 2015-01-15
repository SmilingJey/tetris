package tetris;

public class Game {

    private static Game instance;
    
    public Ticker tic = new Ticker();
    public Timer time = new Timer(1000);
    public boolean stop = true;
    int now_level = 1;
    int level[] = {800, 600, 500, 400, 300, 200, 100};
    int score = 0;
    int lines = 0;
    int cost[] = {100, 300, 700, 1500};

    public int[][] playingField = new int[20][10];
    
    public int currentTetrimonosX = 4;
    public int currentTetrimonosY = -2;
    public int currentTetrimonos;
    public int nextTetrimonos;
    public boolean[][] currentTetrimonosShape;
    
    private boolean[][][] tetriminosShapes = {
        {
            {false, false, false, false}, //I
            {true, true, true, true},
        },
        {
            {true, false, false, false}, //J
            {true, true, true, true},
        },
        {
            {false, false, true, false}, //L
            {true, true, true, false},
        },
        {
            {false, true, true, false}, //O
            {false, true, true, false},
        },
        {
            {false, true, true, false}, //S
            {true, true, false, false},
        },
        {
            {false, true, false, false}, //T
            {true, true, true, false},
        },
        {
            {true, true, false, false}, //Z
            {false, true, true, false},
        }
    };

    public int[][] tetriminosColor = {
        {0x00, 0xf0, 0xf0}, //I
        {0x00, 0x00, 0xf0}, //J
        {0xf0, 0xa0, 0x00}, //L
        {0xf0, 0xf0, 0x00}, //O
        {0x00, 0xf0, 0x00}, //S
        {0xa0, 0x00, 0xf0}, //T
        {0xf0, 0x00, 0x00} //Z
    };
    
    private Game() {

    }
    
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void down() {
        if (!stop && !tic.pause) {
            if (bottom()) {
                flush();
                line_analise();
                next();
            } else {
                currentTetrimonosY++;
            }
        }
    }

    public void next() {
        currentTetrimonosX = 4;
        currentTetrimonosY = -2;
        currentTetrimonos = nextTetrimonos;
        currentTetrimonosShape = tetriminosShapes[currentTetrimonos];
        nextTetrimonos = (int) (Math.random() * 6.99);
        NextTetriminosPanel.instanes.repaint();
    }

    public void right() {
        if (!stop && !tic.pause) {
            if (can_right()) {
                currentTetrimonosX++;
            }
        }
    }

    public boolean can_right() {
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

    public boolean can_left() {
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

    public void left() {
        if (!stop && !tic.pause) {
            if (can_left()) {
                currentTetrimonosX--;
            }
        }
    }

    public void rotate() {
        if (!stop && !tic.pause) {

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

    public void speed_down() {
        while (!bottom()) {
            down();
        }
    }

    public boolean bottom() {
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
                        end_game();
                    }
                }
            }
        }
    }

    public void line_analise() {
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
            ;
        }
        if (count != 0) {
            lines += count;
            score += cost[count - 1];
            now_level = (int) (score / 2000) + 1;
            if (now_level < 8) {
                tic.period = level[now_level - 1];
            }
            Mainframe.getInstance().set_labels(score, lines, now_level);
        }

    }

    public void end_game() {
        tic.stop = true;
        time.stop = true;

        if (!stop) {
            if (score > Recordframe.instance.min_rec) {
                String title = "You score in 20 liders";
                if (score > Recordframe.instance.max_rec) {
                    title = "It is new record";
                }
                SaveRecordDialog.instance.show(title, true);
            }
        }
        stop = true;
    }

    public void new_game() {
        currentTetrimonosX = 4;
        currentTetrimonosY = -2;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                playingField[i][j] = 0;
            }
        }
        stop = false;
        currentTetrimonos = (int) (Math.random() * 6.99);
        nextTetrimonos = (int) (Math.random() * 6.99);
        tic.stop = true;
        tic = null;
        tic = new Ticker();
        now_level = 1;
        tic.period = level[now_level - 1];
        tic.start();
        time.stop = true;
        time = null;
        time.start();
        score = 0;
        lines = 0;
        Mainframe.getInstance().set_record();
        Mainframe.getInstance().set_labels(score, lines, now_level);
        Mainframe.getInstance().redrawPlayingPanel();
        NextTetriminosPanel.instanes.repaint();
    }
}
