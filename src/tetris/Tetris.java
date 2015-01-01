package tetris;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Tetris {

    public static void main(String[] args) {
        Mainframe frame = new Mainframe();
        new SaveRecordDialog(frame);
        Recordframe rf = new Recordframe(frame);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        frame.setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
        rf.setLocation((screenSize.width - frameSize.width) / 2 + 30,
                (screenSize.height - frameSize.height) / 2 + 30);
        frame.setVisible(true);
        new Game();
        Game_canvas.instanes.draw_all();
    }
}
