package tetris;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Tetris {

    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = Mainframe.getInstance().getSize();
        Mainframe.getInstance().setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
        Mainframe.getInstance().setVisible(true);
    }
}
