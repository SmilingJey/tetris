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
                repaint();
            }
        });

        final String upPressed = "pressed UP";
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(upPressed), upPressed);
        this.getActionMap().put(upPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
                TetrisEngine.getInstance().rotate();
                repaint();
            }
        });

        final String leftPressed = "pressed LEFT";
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(leftPressed), leftPressed);
        this.getActionMap().put(leftPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
                TetrisEngine.getInstance().moveLeft();
                repaint();
            }
        });

        final String rightPressed = "pressed RIGHT";
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(rightPressed), rightPressed);
        this.getActionMap().put(rightPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
                TetrisEngine.getInstance().moveRight();
                repaint();
            }
        });

        final String sPressed = "pressed S";
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(sPressed), sPressed);
        this.getActionMap().put(sPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
                TetrisEngine.getInstance().moveDown();
                repaint();
            }
        });

        final String wPressed = "pressed W";
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(wPressed), wPressed);
        this.getActionMap().put(wPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
                TetrisEngine.getInstance().rotate();
                repaint();
            }
        });

        final String aPressed = "pressed A";
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(aPressed), aPressed);
        this.getActionMap().put(aPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
                TetrisEngine.getInstance().moveLeft();
                repaint();
            }
        });

        final String dPressed = "pressed D";
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(dPressed), dPressed);
        this.getActionMap().put(dPressed, new AbstractAction() {
            public void actionPerformed(ActionEvent ignored) {
                TetrisEngine.getInstance().moveRight();
                repaint();
            }
        });
    }

    public void paint(Graphics g) {
        super.paint(g);
        bufferImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        bufferGraphics2D = bufferImage.createGraphics();
        blockHeight = this.getHeight() / 20;
        blockWidth = this.getWidth() / 10;
        
        int startX = 2;
        int startY = 2;
        
        bufferGraphics2D.setColor(Color.WHITE);
        bufferGraphics2D.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        //draw exist blocks
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                if (TetrisEngine.getInstance().playingField[i][j] != 0) {
                    int c = TetrisEngine.getInstance().playingField[i][j];
                    bufferGraphics2D.setColor(Color.BLACK);
                    bufferGraphics2D.drawRect(startX + j * blockWidth, startY + i * blockHeight, 
                            blockWidth, blockHeight);
                    bufferGraphics2D.setColor(TetrisEngine.getTetriminosColor(TetrisEngine.getInstance().playingField[i][j]));
                    bufferGraphics2D.fillRect(startX + j * blockWidth + 1, startY + i * blockHeight + 1,
                            blockWidth - 1, blockHeight - 1);
                }
            }
        }
        
        //draw current tetrimonos
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (TetrisEngine.tetriminosShapes[TetrisEngine.getInstance().currentTetrimonos][i][j]) {
                    bufferGraphics2D.setColor(Color.BLACK);
                    bufferGraphics2D.drawRect(startX + (TetrisEngine.getInstance().currentTetrimonosX + j) * blockWidth, 
                            startY + (TetrisEngine.getInstance().currentTetrimonosY + i) * blockHeight, 
                            blockWidth, blockHeight);
                    bufferGraphics2D.setColor(TetrisEngine.getTetriminosColor(TetrisEngine.getInstance().currentTetrimonos));
                    bufferGraphics2D.fillRect(startX + (TetrisEngine.getInstance().currentTetrimonosX + j) * blockWidth + 1, 
                            startY + (TetrisEngine.getInstance().currentTetrimonosY + i) * blockHeight + 1, 
                            blockWidth - 1, blockHeight - 1);
                }
            }
        }
        
        bufferGraphics2D.setColor(Color.WHITE);
        bufferGraphics2D.fillRect(0, 0, this.getWidth(), startY);
        bufferGraphics2D.setColor(Color.BLACK);
        bufferGraphics2D.drawRect(startX, startY, blockWidth * 10 + 1, blockHeight * 20 + 1);
        g.drawImage(bufferImage, 0, 0, null);
    }
}
