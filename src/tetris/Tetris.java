package tetris;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Tetris {

    public static void main(String[] args) {
        new SaveRecordDialog(Mainframe.getInstance());
        Recordframe rf = new Recordframe(Mainframe.getInstance());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = Mainframe.getInstance().getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        Mainframe.getInstance().setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
        rf.setLocation((screenSize.width - frameSize.width) / 2 + 30,
                (screenSize.height - frameSize.height) / 2 + 30);
        Mainframe.getInstance().setVisible(true);
        new Game();
        Game_canvas.instanes.draw_all();
    }
}
