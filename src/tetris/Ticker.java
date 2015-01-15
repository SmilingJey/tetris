package tetris;

public class Ticker extends Thread {

    int period = 100;
    boolean stop = false;
    boolean pause = false;

    public Ticker() {
        super();
    }

    public void run() {
        try {
            while (!stop) {
                sleep(period);
                if (!pause) {
                    Game.getInstance().down();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
