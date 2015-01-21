package tetris;

import java.applet.*;

public class TetrisApplet extends Applet {

    public void init() {
        TetrisMainFrame.isApplet = true;
        TetrisMainFrame.getInstance().setVisible(true);
        TetrisMainFrame.getInstance().repaintPlayingPanel();
    }
}
