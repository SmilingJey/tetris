package tetris;

import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Color;

public class NextTetriminosPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public NextTetriminosPanel() {
        super();
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (TetrisEngine.tetriminosShapes[TetrisEngine.getInstance().getNextTetriminos()][i][j]) {
                    g.setColor(new Color(0, 0, 0));
                    g.drawRect(j * 15, i * 15, 15, 15);
                    g.setColor(TetrisEngine.getInstance().getTetriminosColor(TetrisEngine.getInstance().getNextTetriminos()));
                    g.fillRect(j * 15 + 1, i * 15 + 1, 14, 14);
                }
            }
        }
    }
}
