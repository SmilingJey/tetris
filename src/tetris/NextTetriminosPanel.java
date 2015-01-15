package tetris;

import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Color;

public class NextTetriminosPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    public static NextTetriminosPanel instanes;

    public NextTetriminosPanel() {
        super();
        instanes = this;
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                if (Game.getInstance().tetriminosShapes[Game.getInstance().next_figur][i][j]) {
                    g.setColor(new Color(0, 0, 0));
                    g.drawRect(j * 15, i * 15, 15, 15);
                    g.setColor(new Color(Game.getInstance().tetriminosColor[Game.getInstance().next_color][0],
                            Game.getInstance().tetriminosColor[Game.getInstance().next_color][1],
                            Game.getInstance().tetriminosColor[Game.getInstance().next_color][2]));
                    g.fillRect(j * 15 + 1, i * 15 + 1, 13, 13);
                }
            }
        }
    }
}
