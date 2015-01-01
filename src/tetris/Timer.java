package tetris;

public class Timer extends Thread {

    boolean stop = false;
    boolean pause = false;
    int s = 0;
    int m = 0;
    int h = 0;

    public void run() {
        try {
            while (!stop) {
                sleep(1000);
                if (!pause) {
                    s++;
                    if (s == 60) {
                        s = 0;
                        m++;
                        if (m == 60) {
                            m = 0;
                            h++;
                        }
                    }
                    Mainframe.getInstance().set_time(h, m, s);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
