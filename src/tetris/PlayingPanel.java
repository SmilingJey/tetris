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
                TetrisEngine.getInstance().moveDown();
                draw_all();
            }
        });

        final String upPressed = "pressed UP";
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(upPressed), upPressed);
        this.getActionMap().put(upPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
                TetrisEngine.getInstance().rotate();
                draw_all();
            }
        });

        final String leftPressed = "pressed LEFT";
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(leftPressed), leftPressed);
        this.getActionMap().put(leftPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
                TetrisEngine.getInstance().moveLeft();
                draw_all();
            }
        });

        final String rightPressed = "pressed RIGHT";
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(rightPressed), rightPressed);
        this.getActionMap().put(rightPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
                TetrisEngine.getInstance().moveRight();
                draw_all();
            }
        });

        final String sPressed = "pressed S";
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(sPressed), sPressed);
        this.getActionMap().put(sPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
                TetrisEngine.getInstance().moveDown();
                draw_all();
            }
        });

        final String wPressed = "pressed W";
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(wPressed), wPressed);
        this.getActionMap().put(wPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
                TetrisEngine.getInstance().rotate();
                draw_all();
            }
        });

        final String aPressed = "pressed A";
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(aPressed), aPressed);
        this.getActionMap().put(aPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
                TetrisEngine.getInstance().moveLeft();
                draw_all();
            }
        });

        final String dPressed = "pressed D";
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(dPressed), dPressed);
        this.getActionMap().put(dPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
                TetrisEngine.getInstance().moveRight();
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

        //draw exist blocks
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                if (TetrisEngine.getInstance().playingField[i][j] != 0) {
                    int c = TetrisEngine.getInstance().playingField[i][j];
                    bufferGraphics2D.setColor(Color.BLACK);
                    bufferGraphics2D.drawRect(j * blockWidth, i * blockHeight, blockWidth, blockHeight);
                    bufferGraphics2D.setColor(TetrisEngine.getTetriminosColor(TetrisEngine.getInstance().playingField[i][j]));
                    bufferGraphics2D.fillRect(j * blockWidth + 1, i * blockHeight + 1, blockWidth - 2, blockHeight - 2);
                }
            }
        }
        
        //draw current tetrimonos
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (TetrisEngine.getInstance().tetriminosShapes[TetrisEngine.getInstance().currentTetrimonos][i][j]) {
                    bufferGraphics2D.setColor(Color.BLACK);
                    bufferGraphics2D.drawRect((TetrisEngine.getInstance().currentTetrimonosX + j) * blockWidth, (TetrisEngine.getInstance().currentTetrimonosY + i) * blockHeight, blockWidth, blockHeight);
                    bufferGraphics2D.setColor(TetrisEngine.getTetriminosColor(TetrisEngine.getInstance().currentTetrimonos));
                    bufferGraphics2D.fillRect((TetrisEngine.getInstance().currentTetrimonosX + j) * blockWidth + 1, (TetrisEngine.getInstance().currentTetrimonosY + i) * blockHeight + 1, blockWidth - 2, blockHeight - 2);
                }
            }
        }

        repaint();
    }
}
