package tetris;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class TetrisPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private BufferedImage bufferImage;
    private Graphics2D bufferGraphics2D;
    private int blockHeight = 0;
    private int blockWidth = 0;

    public TetrisPanel() {
        super();
        bufferImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

        final String downPressed = "pressed DOWN";
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(downPressed), downPressed);
        this.getActionMap().put(downPressed, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ignored) {
                TetrisEngine.getInstance().moveDown();
                repaint();
            }
        });

        final String upPressed = "pressed UP";
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(upPressed), upPressed);
        this.getActionMap().put(upPressed, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ignored) {
                TetrisEngine.getInstance().rotate();
                repaint();
            }
        });

        final String leftPressed = "pressed LEFT";
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(leftPressed), leftPressed);
        this.getActionMap().put(leftPressed, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ignored) {
                TetrisEngine.getInstance().moveLeft();
                repaint();
            }
        });

        final String rightPressed = "pressed RIGHT";
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(rightPressed), rightPressed);
        this.getActionMap().put(rightPressed, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ignored) {
                TetrisEngine.getInstance().moveRight();
                repaint();
            }
        });

        final String sPressed = "pressed S";
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(sPressed), sPressed);
        this.getActionMap().put(sPressed, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ignored) {
                TetrisEngine.getInstance().moveDown();
                repaint();
            }
        });

        final String wPressed = "pressed W";
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(wPressed), wPressed);
        this.getActionMap().put(wPressed, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ignored) {
                TetrisEngine.getInstance().rotate();
                repaint();
            }
        });

        final String aPressed = "pressed A";
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(aPressed), aPressed);
        this.getActionMap().put(aPressed, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ignored) {
                TetrisEngine.getInstance().moveLeft();
                repaint();
            }
        });

        final String dPressed = "pressed D";
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(dPressed), dPressed);
        this.getActionMap().put(dPressed, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ignored) {
                TetrisEngine.getInstance().moveRight();
                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        bufferImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        bufferGraphics2D = bufferImage.createGraphics();

        RenderingHints rh = new RenderingHints(
        RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        bufferGraphics2D.setRenderingHints(rh);
        
        blockHeight = (this.getHeight() - 4) / 20;
        blockWidth = (this.getWidth() - 4) / 10;
        
        if (blockWidth>blockHeight) blockWidth = blockHeight;
        else blockHeight = blockWidth;

        int startX = 2;
        int startY = 2;
        
        bufferGraphics2D.setColor(TetrisMainFrame.frameBackgroundColor);
        bufferGraphics2D.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        bufferGraphics2D.setColor(TetrisMainFrame.playingPanelColor);
        bufferGraphics2D.fillRect(startX+1, startY+1, blockWidth * 10, blockHeight * 20);
        
        //draw exist blocks
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                if (TetrisEngine.getInstance().playingField[i][j] != 0) {
                    int c = TetrisEngine.getInstance().playingField[i][j];
                    bufferGraphics2D.setColor(Color.BLACK);
                    bufferGraphics2D.drawRect(startX + j * blockWidth, startY + i * blockHeight, 
                            blockWidth, blockHeight);
                    bufferGraphics2D.setColor(TetrisMainFrame.tetriminosColor[TetrisEngine.getInstance().playingField[i][j]]);
                    bufferGraphics2D.fillRect(startX + j * blockWidth + 1, startY + i * blockHeight + 1,
                            blockWidth - 1, blockHeight - 1);
                }
            }
        }
        
        //draw current tetrimonos
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (TetrisEngine.getInstance().currentTetrimonosShape[i][j]) {
                    bufferGraphics2D.setColor(Color.BLACK);
                    bufferGraphics2D.drawRect(startX + (TetrisEngine.getInstance().currentTetrimonosX + j) * blockWidth, 
                            startY + (TetrisEngine.getInstance().currentTetrimonosY + i) * blockHeight, 
                            blockWidth, blockHeight);
                    bufferGraphics2D.setColor(TetrisMainFrame.tetriminosColor[TetrisEngine.getInstance().currentTetrimonos]);
                    bufferGraphics2D.fillRect(startX + (TetrisEngine.getInstance().currentTetrimonosX + j) * blockWidth + 1, 
                            startY + (TetrisEngine.getInstance().currentTetrimonosY + i) * blockHeight + 1, 
                            blockWidth - 1, blockHeight - 1);
                }
            }
        }
        
        bufferGraphics2D.setColor(TetrisMainFrame.frameBackgroundColor);
        bufferGraphics2D.fillRect(0, 0, this.getWidth(), startY);
        bufferGraphics2D.setColor(Color.BLACK);
        bufferGraphics2D.drawRect(startX, startY, blockWidth * 10 + 1, blockHeight * 20 + 1);
        
        
        if (TetrisEngine.getInstance().isGameOver()){
            bufferGraphics2D.setColor(TetrisMainFrame.gameOverTextColor);
            bufferGraphics2D.setFont(new Font("Verdana", Font.PLAIN, 46));
            bufferGraphics2D.drawString("GAME", this.getWidth()/2-65, this.getHeight()/2-25);
            bufferGraphics2D.drawString("OVER", this.getWidth()/2-61, this.getHeight()/2+25);
        }

        g.drawImage(bufferImage, 0, 0, null);
    }
}
