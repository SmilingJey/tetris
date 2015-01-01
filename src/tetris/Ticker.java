package tetris;

public class Ticker extends Thread {

    int time = 100;
    boolean stop = false;
    boolean pause = false;

    public Ticker() {
        super();
    }

    public void run() {
        try {
            while (!stop) {
                sleep(time);
                if (!pause) {
                    Game.instanes.down();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
