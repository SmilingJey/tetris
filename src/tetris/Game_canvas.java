package tetris;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class Game_canvas extends JPanel {

    private static final long serialVersionUID = 1L;
    public static Game_canvas instanes;
    BufferedImage buffer_image;
    Graphics2D buffer_g;
    int c_hegth = 0;
    int c_width = 0;

    public Game_canvas() {
        super();
        instanes = this;
        buffer_image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

        final String downPressed = "pressed DOWN"; // modifier required!
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(downPressed), downPressed);
        this.getActionMap().put(downPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
                Game.instanes.down();
            }
        });

        final String upPressed = "pressed UP"; // modifier required!
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(upPressed), upPressed);
        this.getActionMap().put(upPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
                Game.instanes.rotate();
                repaint();
            }
        });

        final String leftPressed = "pressed LEFT"; // modifier required!
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(leftPressed), leftPressed);
        this.getActionMap().put(leftPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
                Game.instanes.left();
            }
        });

        final String rightPressed = "pressed RIGHT"; // modifier required!
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(rightPressed), rightPressed);
        this.getActionMap().put(rightPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
                Game.instanes.right();
            }
        });

        final String sPressed = "pressed S"; // modifier required!
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(sPressed), sPressed);
        this.getActionMap().put(sPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
                Game.instanes.down();
            }
        });

        final String wPressed = "pressed W"; // modifier required!
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(wPressed), wPressed);
        this.getActionMap().put(wPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
                Game.instanes.rotate();
            }
        });

        final String aPressed = "pressed A"; // modifier required!
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(aPressed), aPressed);
        this.getActionMap().put(aPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
                Game.instanes.left();
            }
        });

        final String dPressed = "pressed D"; // modifier required!
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(dPressed), dPressed);
        this.getActionMap().put(dPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
                Game.instanes.right();
            }
        });
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(buffer_image, 0, 0, null);
    }

    public void draw_all() {
        buffer_image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        buffer_g = buffer_image.createGraphics();
        c_hegth = this.getHeight() / 20;
        c_width = this.getWidth() / 10;

        buffer_g.fillRect(0, 0, this.getWidth(), this.getHeight());

        buffer_g.setColor(Color.BLACK);
        buffer_g.drawRect(0, 0, c_width * 10 + 1, c_hegth * 20 + 1);

        draw_exist(buffer_g);
        draw_current(buffer_g);
        repaint();
    }

    public void draw_exist(Graphics g) {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                if (Game.instanes.pole[i][j] != 0) {
                    int c = Game.instanes.pole[i][j];
                    g.setColor(Color.BLACK);
                    g.drawRect(j * c_width, i * c_hegth, c_width, c_hegth);
                    g.setColor(new Color(Game.instanes.color[c][0], Game.instanes.color[c][1], Game.instanes.color[c][2]));
                    g.fillRect(j * c_width + 1, i * c_hegth + 1, c_width - 2, c_hegth - 2);
                }
            }
        }
    }

    public void draw_current(Graphics g) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (Game.instanes.figura[Game.instanes.curr_figur][i][j]) {
                    g.setColor(Color.BLACK);
                    g.drawRect((Game.instanes.x + j) * c_width, (Game.instanes.y + i) * c_hegth, c_width, c_hegth);
                    g.setColor(new Color(Game.instanes.color[Game.instanes.curr_color][0],
                            Game.instanes.color[Game.instanes.curr_color][1],
                            Game.instanes.color[Game.instanes.curr_color][2]));
                    g.fillRect((Game.instanes.x + j) * c_width + 1, (Game.instanes.y + i) * c_hegth + 1, c_width - 2, c_hegth - 2);
                }
            }
        }
    }
}
