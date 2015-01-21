package tetris;

import java.applet.*;

public class TetrisApplet extends Applet {

    public TetrisApplet() {
    }

    public void init() {
        TetrisMainFrame.isApplet = true;
        TetrisMainFrame.getInstance().setVisible(true);
        TetrisMainFrame.getInstance().repaintPlayingPanel();
    }

    public void start() {
    }

    public void stop() {
    }

    public void destroy() {
    }

}
