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

public class PlayingPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private BufferedImage bufferImage;
    private Graphics2D bufferGraphics2D;
    private int blockHeight = 0;
    private int blockWidth = 0;

    public PlayingPanel() {
        super();
        bufferImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

        final String downPressed = "pressed DOWN";
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(downPressed), downPressed);
        this.getActionMap().put(downPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
                Game.getInstance().down();
                draw_all();
            }
        });

        final String upPressed = "pressed UP";
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(upPressed), upPressed);
        this.getActionMap().put(upPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
                Game.getInstance().rotate();
                draw_all();
            }
        });

        final String leftPressed = "pressed LEFT";
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(leftPressed), leftPressed);
        this.getActionMap().put(leftPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
                Game.getInstance().left();
                draw_all();
            }
        });

        final String rightPressed = "pressed RIGHT";
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(rightPressed), rightPressed);
        this.getActionMap().put(rightPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
                Game.getInstance().right();
                draw_all();
            }
        });

        final String sPressed = "pressed S";
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(sPressed), sPressed);
        this.getActionMap().put(sPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
                Game.getInstance().down();
                draw_all();
            }
        });

        final String wPressed = "pressed W";
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(wPressed), wPressed);
        this.getActionMap().put(wPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
                Game.getInstance().rotate();
                draw_all();
            }
        });

        final String aPressed = "pressed A";
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(aPressed), aPressed);
        this.getActionMap().put(aPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
                Game.getInstance().left();
                draw_all();
            }
        });

        final String dPressed = "pressed D";
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(dPressed), dPressed);
        this.getActionMap().put(dPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
                Game.getInstance().right();
                draw_all();
            }
        });
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(bufferImage, 0, 0, null);
    }

    public void draw_all() {
        bufferImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        bufferGraphics2D = bufferImage.createGraphics();
        blockHeight = this.getHeight() / 20;
        blockWidth = this.getWidth() / 10;
        bufferGraphics2D.fillRect(0, 0, this.getWidth(), this.getHeight());
        bufferGraphics2D.setColor(Color.BLACK);
        bufferGraphics2D.drawRect(0, 0, blockWidth * 10 + 1, blockHeight * 20 + 1);
        
        drawExistBlocks(bufferGraphics2D);
        drawCurrentTetrimonos(bufferGraphics2D);
        repaint();
    }

    private void drawExistBlocks(Graphics g) {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                if (Game.getInstance().playingField[i][j] != 0) {
                    int c = Game.getInstance().playingField[i][j];
                    g.setColor(Color.BLACK);
                    g.drawRect(j * blockWidth, i * blockHeight, blockWidth, blockHeight);
                    g.setColor(new Color(Game.getInstance().tetriminosColor[c][0], Game.getInstance().tetriminosColor[c][1], Game.getInstance().tetriminosColor[c][2]));
                    g.fillRect(j * blockWidth + 1, i * blockHeight + 1, blockWidth - 2, blockHeight - 2);
                }
            }
        }
    }

    private void drawCurrentTetrimonos(Graphics g) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                if (Game.getInstance().tetriminosShapes[Game.getInstance().curr_figur][i][j]) {
                    g.setColor(Color.BLACK);
                    g.drawRect((Game.getInstance().currentTetrimonosX + j) * blockWidth, (Game.getInstance().currentTetrimonosY + i) * blockHeight, blockWidth, blockHeight);
                    g.setColor(new Color(Game.getInstance().tetriminosColor[Game.getInstance().curr_color][0],
                            Game.getInstance().tetriminosColor[Game.getInstance().curr_color][1],
                            Game.getInstance().tetriminosColor[Game.getInstance().curr_color][2]));
                    g.fillRect((Game.getInstance().currentTetrimonosX + j) * blockWidth + 1, (Game.getInstance().currentTetrimonosY + i) * blockHeight + 1, blockWidth - 2, blockHeight - 2);
                }
            }
        }
    }
}
