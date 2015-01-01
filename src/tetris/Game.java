package tetris;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Game {

    public static Game instanes;
    public Ticker tic = new Ticker();
    public Timer time = new Timer();
    public boolean stop = true;
    int now_level = 1;
    int level[] = {700, 600, 500, 400, 300, 200, 100};
    int score = 0;
    int lines = 0;
    int cost[] = {100, 500, 1000, 4000};
    int color[][] = {{0, 0, 0},
    {172, 211, 115},
    {109, 207, 249},
    {116, 124, 82},
    {237, 20, 97},
    {135, 129, 189},
    {253, 198, 137},
    {255, 242, 0},
    {237, 20, 97}};
    public int pole[][] = new int[20][10];
    public int curr_color = 1;
    public int curr_figur = 1;
    public int next_color = 2;
    public int next_figur = 2;
    public boolean figura[][][] = {{{false, true, false, false},
    {false, true, false, false},
    {false, true, false, false},
    {false, true, false, false}},
    {{false, true, false, false},
    {true, true, true, false},
    {false, false, false, false},
    {false, false, false, false}},
    {{false, true, true, false},
    {true, true, false, false},
    {false, false, false, false},
    {false, false, false, false}},
    {{true, true, false, false},
    {false, true, true, false},
    {false, false, false, false},
    {false, false, false, false}},
    {{true, true, false, false},
    {true, true, false, false},
    {false, false, false, false},
    {false, false, false, false}},
    {{false, true, true, false},
    {false, true, false, false},
    {false, true, false, false},
    {false, false, false, false}},
    {{true, true, false, false},
    {false, true, false, false},
    {false, true, false, false},
    {false, false, false, false}}};
    public final boolean buff_figura[][][] = {{{false, true, false, false},
    {false, true, false, false},
    {false, true, false, false},
    {false, true, false, false}},
    {{false, true, false, false},
    {true, true, true, false},
    {false, false, false, false},
    {false, false, false, false}},
    {{false, true, true, false},
    {true, true, false, false},
    {false, false, false, false},
    {false, false, false, false}},
    {{true, true, false, false},
    {false, true, true, false},
    {false, false, false, false},
    {false, false, false, false}},
    {{true, true, false, false},
    {true, true, false, false},
    {false, false, false, false},
    {false, false, false, false}},
    {{false, true, true, false},
    {false, true, false, false},
    {false, true, false, false},
    {false, false, false, false}},
    {{true, true, false, false},
    {false, true, false, false},
    {false, true, false, false},
    {false, false, false, false}}};
    public int x = 4;
    public int y = -2;

    public Game() {
        instanes = this;
    }

    public void down() {
        if (!stop && !tic.pause) {
            if (bottom()) {
                flush();
                line_analise();
                next();
            } else {
                y++;
            }
        }
        Game_canvas.instanes.draw_all();
    }

    public void next() {
        figura = buff_figura;
        x = 4;
        y = -2;
        curr_color = next_color;
        curr_figur = next_figur;
        next_figur = (int) (Math.random() * 6.99);
        next_color = (int) (Math.random() * 6.99) + 1;
        Next_figura.instanes.repaint();
    }

    public void right() {
        if (!stop && !tic.pause) {
            if (can_right()) {
                x++;
            }
            Game_canvas.instanes.draw_all();
        }

    }

    public boolean can_right() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if ((figura[Game.instanes.curr_figur][i][j]) && x + j <= 9) {
                    if (y + i < 0 && x + j == 9) {
                        return false;
                    } else {
                        if (y + i >= 0) {
                            if (x + j == 9) {
                                return false;
                            }
                            if (pole[y + i][x + j + 1] != 0) {
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
                if ((figura[Game.instanes.curr_figur][i][j]) && x + j >= 0) {
                    if (y + i < 0 && x + j == 0) {
                        return false;
                    } else {
                        if (y + i >= 0) {
                            if (x + j == 0) {
                                return false;
                            }
                            if (pole[y + i][x + j - 1] != 0) {
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
                x--;
            }
            Game_canvas.instanes.draw_all();
        }
    }

    public void rotate() {
        if (!stop && !tic.pause) {
            if (curr_figur != 4) {
                boolean temp[][] = new boolean[4][4];
                boolean result[][] = new boolean[4][4];
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        temp[i][j] = figura[curr_figur][i][j];
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
                            if (y + i > 19 || x + j < 0 || x + j > 9) {
                                can_rotate = false;
                            } else if (y + i >= 0) {
                                if (pole[y + i][x + j] != 0) {
                                    can_rotate = false;
                                }
                            }
                        }
                    }
                }

                if (can_rotate) {
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            figura[curr_figur][i][j] = result[i][j];
                        }
                    }
                    Game_canvas.instanes.draw_all();
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
                if ((figura[Game.instanes.curr_figur][i][j]) && y + i >= 0) {
                    if (y + i >= 19) {
                        return true;
                    }
                    if (pole[y + i + 1][x + j] != 0) {
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
                if (figura[Game.instanes.curr_figur][i][j]) {
                    if (y + i >= 0) {
                        pole[y + i][x + j] = curr_color;
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
                if (pole[i][j] != 0) {
                    len++;
                }
            }
            if (len == 10) {
                count++;
                for (int k = i; k >= 0; k--) {
                    for (int l = 0; l < 10; l++) {
                        pole[k][l] = (k != 0) ? pole[k - 1][l] : 0;
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
                tic.time = level[now_level - 1];
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
        x = 4;
        y = -2;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                pole[i][j] = 0;
            }
        }
        stop = false;
        curr_figur = (int) (Math.random() * 6.99);
        curr_color = (int) (Math.random() * 6.99) + 1;
        next_figur = (int) (Math.random() * 6.99);
        next_color = (int) (Math.random() * 6.99) + 1;
        tic.stop = true;
        tic = null;
        tic = new Ticker();
        now_level = 1;
        tic.time = level[now_level - 1];
        tic.start();
        time.stop = true;
        time = null;
        time = new Timer();
        time.start();
        score = 0;
        lines = 0;
        Mainframe.getInstance().set_record();
        Mainframe.getInstance().set_labels(score, lines, now_level);
        Game_canvas.instanes.draw_all();
        Next_figura.instanes.repaint();
    }
}
